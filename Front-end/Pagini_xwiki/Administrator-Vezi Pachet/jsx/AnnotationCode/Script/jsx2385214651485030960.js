var XWiki = (function (XWiki) {
// Start XWiki augmentation.
XWiki.Selection = Class.create({
  container : false,
  selectionText : false,
  selectionContext : false,
  selectionOffset : false,
  // the selection range
  range : false,
  // the list of elements that wrap the selection to make it colored, in FF
  highlightWrappers : false,
  // the parent of the selection and the selection inner HTML to make it colored in IE and to be able to rollback this change
  selectionParent : false,
  selectionParentInnerHTML : false,
  // position of the last mouse up, to know where to display the dialog
  offsetX : false,
  offsetY : false,
  // how many characters to expand left & right in standard impl
  step : 5,
  // how may words to expand left & right on IE
  wordStep : 1,

  initialize : function (container) {
    if (!container) {
      return;
    }
    this.container = container;
    // on IE we listen to the mousedown and remember the position of the mousedown of the selection to know where to display the dialog for annotation creation
    if (!window.getSelection) {
      this.container.observe('mousedown', function(event) {
        // very ugly hack to get these values on body since the first positioned ancestor for the panel to be displayed is the body and we need this offsets
        this.offsetX = event.pointerX();
        this.offsetY = event.pointerY();
      }.bindAsEventListener(this));
    }
  },

  computeSelection : function () {
    // reset the selection state
    this.selectionText = false;
    this.range = false;
    this.selectionContext = false;
    this.selectionOffset = false;
    this.highlightWrappers = false;
    this.selectionParent = false;
    this.selectionParentInnerHTML = false;
    // if there is no container to get selection in, selection is always false
    if (!this.container) {
      return;
    }
    if (window.getSelection) {
      // FF selection & hopefully other browsers
      if (window.getSelection().rangeCount == 0) {
        return;
      }
      this.range = window.getSelection().getRangeAt(0);
      // ignore if the selection is in the passed container
      if (!this.isDescendantOrSelf(this.container, this.range.commonAncestorContainer)) {
        return;
      }
      this.selectionText = this.range.toString();
    } else {
      // IE selection
      if (document.selection.type == 'Text') {
        this.range = document.selection.createRange();
        // ignore if the selection is in the passed container
        if (!this.isDescendantOrSelf(this.container, this.range.parentElement())) {
          return;
        }
        this.selectionText = this.range.text;
      }
    }
    if (this.selectionText.strip() == '') {
      this.selectionText = false;
    }
  },

  isDescendantOrSelf : function(ancestor, descendant) {
    return ancestor == descendant || Element.descendantOf(descendant, ancestor);
  },

  computeContext : function() {
    if (window.getSelection) {
      this.computeContextFF();
    } else {
      this.computeContextIE();
    }
  },

  // these functions are here because they depend on selection

  highlightSelection : function(color) {
    if (!this.range) {
      // there should be some selection at this point
      return;
    }
    if (window.getSelection) {
      // standard
      // create an annotation highlight span around this content
      var highlightWrapperTemplate = new Element('span', {'style': 'background-color: ' + color, 'class' : 'selection-highlight'});
      // get all the text nodes of this range
      var rangeTextNodes = this.getRangeTextNodes();
      // and remove all the ranges in this selection, otherwise so messed up things will happen
      window.getSelection().removeAllRanges();
      this.highlightWrappers = new Array();
      rangeTextNodes.each(function(text) {
        // clone a highlightWrapper from the template
        var highlightWrapper = highlightWrapperTemplate.clone();
        highlightWrapper.update(text.textContent.escapeHTML());
        text.parentNode.replaceChild(highlightWrapper, text);
        this.highlightWrappers.push(highlightWrapper);
      }.bind(this));
    } else {
      // IE
      // get the selection parent and its inner HTML to be able to restore it after
      this.selectionParent = this.range.parentElement();
      this.selectionParentInnerHTML = this.selectionParent.innerHTML;
      // and finally color it
      this.range.execCommand("BackColor", false, color);
    }
  },

  /**
   * Returns all the text nodes in the range, in depth first preorder, and also splitting the startContainer & endContainer in text nodes by the start & end offset, if needed
   */
  getRangeTextNodes : function() {
    var startContainer = this.range.startContainer;
    var endContainer = this.range.endContainer;
    var startOffset = this.range.startOffset;
    var endOffset = this.range.endOffset;

    var firstLeaf = this.getFirstLeafInRange(this.range);
    var lastLeaf = this.getLastLeafInRange(this.range);

    var leafs = this.getLeafsBetween(firstLeaf, lastLeaf);
    // filter out the text leafs
    var textLeafs = leafs.findAll(function(item) {
      return item.nodeType == 3;
    });

    // and now split the ends, if necessary
    if (startContainer == textLeafs[0] && startOffset != 0) {
      // split the start
      textLeafs[0] = startContainer.splitText(startOffset);
      // if the start container was the same as the end container, the end container must move to the split part of the start container and its offset must be updated
      if (startContainer == endContainer) {
        endOffset = endOffset - startOffset;
        endContainer = textLeafs[0];
      }
    }
    if (endContainer == textLeafs[textLeafs.length - 1] && endOffset != endContainer.length) {
      // and hope that this will stay in the container
      endContainer = endContainer.splitText(endOffset);
    }

    return textLeafs;
  },

  getLeafsBetween : function(startLeaf, endLeaf) {
    var leafsArray = new Array();
    var currentLeaf = startLeaf;
    leafsArray.push(startLeaf);
    while (currentLeaf != endLeaf) {
      currentLeaf = this.getNextLeaf(currentLeaf);
      leafsArray.push(currentLeaf);
    }
    return leafsArray;
  },

  // and here we go, helper functions to help iterate through the nodes & leaves & all

  getFirstLeafInRange : function(range) {
    if (range.startContainer.hasChildNodes()) {
        if (range.collapsed) {
            return null;
        } else if (range.startOffset >= range.startContainer.childNodes.length) {
            return this.getNextLeaf(range.startContainer);
        } else {
            return this.getFirstLeaf(range.startContainer.childNodes[range.startOffset]);
        }
    } else {
        return range.startContainer;
    }
  },

  getLastLeafInRange : function(range) {
    if (range.endContainer.hasChildNodes()) {
        if (range.collapsed) {
            return null;
        } else if (range.endOffset == 0) {
            return this.getPreviousLeaf(range.endContainer);
        } else {
            return this.getLastLeaf(range.endContainer.childNodes[range.endOffset - 1]);
        }
    } else {
        return range.endContainer;
    }
  },

  getNextLeaf : function(node) {
    var ancestor = node;
    while (ancestor != null && ancestor.nextSibling == null) {
        ancestor = ancestor.parentNode;
    }
    if (ancestor == null) {
        // There's no next leaf.
        return null;
    } else {
        // Return the first leaf in the subtree whose root is the next sibling of the ancestor.
        return this.getFirstLeaf(ancestor.nextSibling);
    }
  },

  getPreviousLeaf : function(node) {
    var ancestor = node;
    while (ancestor != null && ancestor.previousSibling == null) {
      ancestor = ancestor.parentNode;
    }
    if (ancestor == null) {
      // There's no previous leaf.
      return null;
    } else {
      // Return the last leaf in the subtree whose root is the next sibling of the ancestor.
      return this.getLastLeaf(ancestor.previousSibling);
    }
  },

  getFirstLeaf : function(node) {
    var descendant = node;
    while (descendant.hasChildNodes()) {
      descendant = descendant.firstChild;
    }
    return descendant;
  },

  getLastLeaf : function(node) {
    var descendant = node;
    while (descendant.hasChildNodes()) {
      descendant = descendant.lastChild;
    }
    return descendant;
  },

  removeSelectionHighlight : function() {
    if (window.getSelection) {
      // standard
      // unwrap
      this.highlightWrappers.each(function(wrapper) {
        wrapper.replace(wrapper.innerHTML);
      });
    } else {
      // IE
      this.selectionParent.update(this.selectionParentInnerHTML);
    }
  },

  getPositionNextToSelection : function() {
    if (!this.range) {
      return {'left' : 0, 'top' : 0};
    }

    var left = 0;
    var top = 0;
    if (window.getSelection) {
      // set the position
      // get the offsetleft from the first highlightWrapper
      if (this.highlightWrappers.length > 0) {
        left = this.highlightWrappers[0].cumulativeOffset().left;
        var lastWrapper = this.highlightWrappers[this.highlightWrappers.length - 1];
        top = lastWrapper.cumulativeOffset().top + lastWrapper.getHeight();
      }
    } else {
      left = this.offsetX;
      top = this.offsetY;
    }

    return {'left' : left, 'top' : top};
  },

  /*
   * I don't understand what's under here, I will need to review it
   */
  getRightDocument : function(node) {
    var text = '';
    if (node == this.container) {
      text = this.getRightDocument(node.parentNode);
    }
    for (var current = node.nextSibling; current != null; current = current.nextSibling) {
      text += current.textContent;
    }
    return text;
  },

  getLeftDocument : function(node) {
    var text = '';
    if (node == this.container) {
      text = this.getLeftDocument(node.parentNode);
    }
    var parent = node.parentNode;
    if (parent.childNodes) {
      for (var i = 0; i < parent.childNodes.length && parent.childNodes[i] != node; ++i) {
        text += parent.childNodes[i].textContent;
      }
    }
    return text;
  },

  computeContextFF : function() {
    var left = this.getLeftDocument(this.range.startContainer) + this.range.startContainer.textContent.substring(0, this.range.startOffset);
    var subLeft = '';
    var right = this.range.endContainer.textContent.substring(this.range.endOffset, this.range.endContainer.textContent.length) + this.getRightDocument(this.range.endContainer);
    var subRight = '';
    var offset = 0;
    var context = this.range.toString();
    var leftExpansion = 0;
    var rightExpansion = 0;
    while (subRight != right || subLeft != left) {
      var k = this.container.textContent.indexOf(context);
      var l = this.container.textContent.indexOf(context, k + 1);
      if (l == -1) {
        break;
      }
      leftExpansion = Math.min(left.length, leftExpansion + this.step);
      rightExpansion = Math.min(right.length, rightExpansion + this.step);
      subRight = right.substring(0, rightExpansion);
      subLeft = left.substring(left.length - leftExpansion, left.length);
    }
    this.selectionContext = subLeft + this.selectionText + subRight;
    this.selectionOffset = Math.max(subLeft.length, 0);
  },

  computeContextIE : function() {
    var containerInnerText = this.container.innerText;
    // copy the range to make the expanding on a range copy
    var cRange = this.range.duplicate();
    // while the selection appears more than once, expand the selection to left and right with one or more words and get its text
    var leftOffset = 0;
    // if we managed to expand anything (to prevent a loop where it's not unique but we can't expand)
    var expanded = true;
    while(!this.isUnique(containerInnerText, cRange.text) && expanded) {
      var expanded = false;
      // expand left
      var initialLength = cRange.text.length;
      cRange.moveStart('word', -1);
      if (!this.isDescendantOrSelf(this.container, cRange.parentElement())) {
        // move back, cannot move to the left, cross fingers that this works the same in both directions
        cRange.moveStart('word', 1);
      } else {
        //update the offset with the word we just added
        leftOffset += cRange.text.length - initialLength;
        expanded = true;
      }

      // expand right
      cRange.moveEnd('word', 1);
      if (!this.isDescendantOrSelf(this.container, cRange.parentElement())) {
        // move back, cannot move to right, cross fingers that this works the same in both directions
        cRange.moveEnd('word', -1);
      } else {
        expanded = true;
      }
    }
    // is unique or we couldn't expand anymore, this is it, send it
    this.selectionContext = cRange.text;
    this.selectionOffset = leftOffset;
  },

  isUnique : function(subject, pattern) {
    var index1 = subject.indexOf(pattern);
    if (index1 >= 0) {
      return subject.indexOf(pattern, index1 + 1) < 0;
    }
    // assume (ass of u and me) that no encounter means unique
    return true;
  }
});
// End XWiki augmentation.
return XWiki;
}(XWiki || {}));

var XWiki = (function (XWiki) {
// Start XWiki augmentation.
XWiki.Annotation = Class.create({
  // the html element corresponding to the annotated content (where annotations are to be added, displayed, etc)
  annotatedElement : false,
  // tab name of the annotations tab
      annTabname : 'Comments',
    annTabTemplate : 'commentsinline.vm',
    // whether current displayed doc is the rendered annotated document
  fetchedAnnotations : false,
  // whether the annotations are being displayed; synchronizes with displayAnnotationsCheckbox if that element exists
  displayingAnnotations : false,
  // the display annotations check box in the settings panel
  displayAnnotationsCheckbox : false,
  // flag specifying if the annotations are displayed by default or not
  displayedByDefault : false,
  // whether the annotations should be displayed as highlighted or only the icons
  displayHighlight : true,
  // add annotation shortcuts
  addAnnotationShortcuts : ['Meta+M', 'Meta+I'],
  // show annotations shortcuts
  toggleAnnotationsShortcuts : ['Alt+A'],
  // shortcuts for closing the open dialog, be it create, edit or display
  closeDialogShortcuts : ['Esc'],
  // the selection service used to detect and handle selection related functions on the document
  selectionService : false,
  // the stack of bubbles, so that we can close them one by one if needed
  bubbles : new Array(),
  // the currently set filter (pair of field names and their values) that all annotations should be fetched according to.
  // It will be updated any time a changed filter event is received
  currentFilter : {},

  initialize : function (displayHighlighted, annotatedElt, displayedByDefault) {
    this.displayHighlight = displayHighlighted;
    this.annotatedElement = annotatedElt;
    this.displayedByDefault = displayedByDefault;

    // if the annotated element does not exist, don't load anything
    if (!this.annotatedElement) {
      // and show a warning if the annotations should be shown by default
      if (this.displayedByDefault) {
        new XWiki.widgets.Notification("Annotations could not be loaded because the content is not available.", 'warning');
      }
      return;
    }

    this.hookMenuButton();

    // add the delete, edit and validate listeners to the annotations in the annotations tab when the extra panels are loaded
    document.observe('xwiki:docextra:loaded', this.addDeleteListenersInTab.bindAsEventListener(this));
    document.observe('xwiki:docextra:loaded', this.addEditListenersInTab.bindAsEventListener(this));
    document.observe('xwiki:docextra:loaded', this.addValidateListenersInTab.bindAsEventListener(this));
    // refresh the annotations displayed on the document when an annotation is deleted as a comment, that is from the comments tab when annotations are merged with comments
    document.observe('xwiki:annotation:tab:deleted', this.refreshAnnotationsOnCommentDelete.bindAsEventListener(this));
    // register the key shortcuts for adding an annotation
    this.registerAddAnnotationShortcut();
    // register the key shortcuts for toggling annotation visibility
    this.registerToggleAnnotationsShortcut();
    // register the close dialog shortcut
    this.registerCloseDialogShortcut();

    // and initialize the selectionService
    this.selectionService = new XWiki.Selection(this.annotatedElement);

    // listen to the filter change events to re-fetch the annotations when it changes
    document.observe('xwiki:annotations:filter:changed', this.onFilterChange.bindAsEventListener(this));

    // and get the annotations if they're loaded by default
    if (this.displayedByDefault) {
      if (XWiki.docsyntax != 'xwiki/1.0') {
        this.fetchAnnotations(true);
      } else {
        // if the document syntax is 1.0, and annotations should be displayed by default, display a warning, and not display annotations
        new XWiki.widgets.Notification("Annotations are not available for pages in XWiki/1.0 syntax.", 'warning');
      }
    }
  },

  hookMenuButton : function() {
    // Since 7.4M1, the annotations trigger is inserted via an UIX.
    var annotationsTrigger = $('tmAnnotationsTrigger');
    if (annotationsTrigger) {
      annotationsTrigger.observe('click', this.toggleSettingsPanel.bind(this));
    } else {
      // If the UIX is not there, then it means we are in an old skin (such as colibri), and so we let the following
      // legacy code to maintain backward compatibility.
      var contentmenu = $('contentmenu');
      if (contentmenu) {
        var rightMenu = contentmenu.down('.rightmenu');
        // create and insert if it doesn't exist
        if (!rightMenu) {
          rightMenu = new Element('div', {'class':'rightmenu'});
          // insert it before the left menu
          var leftMenu = contentmenu.down('.leftmenu');
          if (leftMenu) {
            leftMenu.insert({before: rightMenu});
          } else {
            contentmenu.insert({bottom: rightMenu});
          }
        }
        // create the annotations button
        var annContainer = new Element('div', {'class': 'topmenuentry hasIcon', 'id': 'tmAnnotations'});
        var annLink = new Element('a', {'class': 'tme', 'href': '#' + this.annTabname});
        annLink.update("<strong>Annotate</strong>");
        annContainer.insert({top: annLink});
        rightMenu.insert({bottom: annContainer});

        annLink.observe('click', this.toggleSettingsPanel.bind(this));
      }
    }
  },

  setAnnotationVisibility : function (visibility) {
    this.displayingAnnotations = visibility;
    if (this.displayAnnotationsCheckbox) {
      this.displayAnnotationsCheckbox.checked = visibility;
    }
  },

  toggleSettingsPanel : function(event) {
    var menu = event.element();
    // prevent link
    event.stop();
    // if another click handling is in progress
    if (menu.disabled) {
      return;
    }
    if (window.document.body.hasClassName('skin-flamingo')) {
      // Hack: hide the bootstrap dropdown menu
      // TODO: find a way to let Bootstrap close the menu in a regular way.
      $('tmMoreActions').removeClassName('open');
    }
    if (!this.settingsPanel) {
      new Ajax.Request('http://localhost:8081/xwiki/bin/view/AnnotationCode/Settings?xpage=plain', {
        parameters : {'target' : XWiki.currentWiki + ':' + XWiki.currentSpace + '.' + XWiki.currentPage},
        onCreate: function() {
          // disable the button
          menu.disabled = true;
          // show nice loading message at page bottom
          menu._x_notification = new XWiki.widgets.Notification("Loading annotations settings", 'inprogress');
        },

        onSuccess: function(response) {
          // Unfortunately, this is skin dependent
          if (window.document.body.hasClassName('skin-flamingo')) {
            var place = $$('.xcontent > hr')[0];
            place.insert({after: response.responseText});
            this.settingsPanel = place.next();
          } else { // colibri
            $('contentmenu').insert({after: response.responseText});
            this.settingsPanel = $('contentmenu').next();
          }
          // fire a settings panel loaded event
          this.settingsPanel.fire('xwiki:annotations:settings:loaded');
          // hide message at page bottom
          menu._x_notification.hide();
          // store the displayed annotations checkbox
          this.displayAnnotationsCheckbox = $('annotationsdisplay');
          // show this checkbox as checked if the annotations are displayed by default and were loaded successfully
          if (this.displayedByDefault || this.displayingAnnotations) {
            this.displayAnnotationsCheckbox.checked = true;
          }
          this.attachSettingsListeners();
          $('tmAnnotations').toggleClassName('active');
        }.bind(this),

        onFailure: function(response) {
          var failureReason = response.statusText;
          if (response.statusText == '' /* No response */ || response.status == 12031 /* In IE */) {
            failureReason = 'Server not responding';
          }
          // show the error message at the bottom
          menu._x_notification.replace(new XWiki.widgets.Notification("Failed: " + failureReason, 'error', {timeout : 5}));
        },

        on0: function (response) {
          response.request.options.onFailure(response);
        },

        onComplete: function() {
          // In the end: re-enable the button
          menu.disabled = false;
        }
      });
    } else {
      this.settingsPanel.toggleClassName('hidden');
      $('tmAnnotations').toggleClassName('active');
    }
  },

  attachSettingsListeners : function() {
    this.displayAnnotationsCheckbox.observe('click', function(event) {
      var visible = this.displayAnnotationsCheckbox.checked;
      // don't do anything if another call is in progress
      if (this.displayAnnotationsCheckbox.disabled) {
        return;
      }
      this.displayAnnotationsCheckbox.disabled = true;
      if (!this.fetchedAnnotations && visible) {
        this.fetchAnnotations(true);
      } else {
        this.toggleAnnotations(visible);
        // and also enable back the checkbox
        this.displayAnnotationsCheckbox.disabled = false;
      }
    }.bindAsEventListener(this));
  },

  toggleAnnotations : function(visible) {
    // don't standard toggle because we want to ensure it matches visible
    if (this.displayHighlight) {
      this.annotatedElement.select('.annotation').invoke(visible ? 'addClassName' : 'removeClassName', 'annotation-highlight');
    }
    //and toggle all annotation markers
    this.annotatedElement.select('.annotation-marker').invoke(visible ? 'removeClassName' : 'addClassName', 'hidden');
    this.displayingAnnotations = visible;
    if (!visible) {
      // also close all open bubbles
      this.bubbles.each(function(bubble){
        // if it's the create panel, skip it
        if (bubble != this.createPanel) {
          bubble.remove();
        }
      }.bind(this));
      this.bubbles.clear();
    }
  },

  toggleAnnotationHighlight : function(annotationId, visible) {
    this.annotatedElement.select('.annotation.ID' + annotationId).invoke(visible ? 'addClassName' : 'removeClassName', 'annotation-highlight');
  },

  /**
   * Handles the update of the current filter by re-storing the new filter in this object's state info and re-fetching
   * the annotations.
   */
  onFilterChange : function(event) {
    // store the current filter
    if (event.memo) {
      this.currentFilter = event.memo;
    }
    // and, if the annotations are currently visible, re-fetch the annotations and display them
    var visible = this.displayAnnotationsCheckbox ? this.displayAnnotationsCheckbox.checked : false;
    if (visible) {
      this.fetchAnnotations(true);
    }
  },

  /**
   * Returns an array of extra fields that need to be requested from the annotations.
   */
  getExtraFields : function() {
    // TODO: request for color when it will be used by the annotation displayer and sent by the backend
    return [];
  },

  /**
   * Returns a map of fieldName, fieldValue pairs that encode the current filter that needs to be applied to the fetched
   * and rendered annotations.
   * Namely, the current filter, as set by last filter change event.
   */
  getFilter : function() {
    // return the current filter stored from the last update of the filter
    return this.currentFilter;
  },

  /**
   * Enriches the set of annotation parameters with the extra requested fields & the filter. The function alters its
   * hash parameter and returns the altered value.
   */
  prepareRequestParameters : function(parametersHash) {
    // get all the filter criteria and add them as request parameters
    var filterList = this.getFilter();
    for (var i = 0; i < filterList.length; i++) {
      var filter = filterList[i];
      var filterKey = 'filter_' + filter.name;
      if (!parametersHash.get(filterKey)) {
        parametersHash.set(filterKey, []);
      }
      parametersHash.get(filterKey).push(filter.value);
    }
    // get all the extra fields requested and add them to the request
    var extraFields = this.getExtraFields();
    if (extraFields.size() > 0) {
      parametersHash.set('request_field', []);
    }
    for (var i = 0; i < extraFields.length; i++) {
      parametersHash.get('request_field').push(extraFields[i]);
    }

    return parametersHash;
  },

  /*
   * @param andShow whether the annotations should also be shown (highlighted) on the content
   * @param force boolean specifying whether loading should be done even if there are no annotations to display (useful for deleting annotations, which should be reflected in the annotated element even if no annotations are still left to display)
   */
  fetchAnnotations : function(andShow, force) {
    require(['xwiki-meta'], function (xm) {
      var getAnnotationsURL = xm.restURL + '/annotations?media=json';
      new Ajax.Request(getAnnotationsURL, {method: 'GET',
        parameters: this.prepareRequestParameters(new Hash()),
        onCreate: function() {
          // show nice loading message at page bottom
          this._x_notification = new XWiki.widgets.Notification("Loading annotated page", 'inprogress');
        }.bind(this),

        onSuccess: function(response) {
          // check the response to make sure it suceeded
          if (this.checkResponseCodeAndFail(response)) {
            return;
          }
          // hide message at page bottom
          this._x_notification.hide();
          // load the received annotated content in the page, along with annotations and annotations markers.
          this.loadAnnotatedContent(response.responseJSON.annotatedContent, andShow, false, force);
          // store the state of the annotations
          this.fetchedAnnotations = true;
          if (this.displayAnnotationsCheckbox) {
            this.displayAnnotationsCheckbox.checked = true;
          }
        }.bind(this),

        onFailure: function(response) {
          var failureReason = response.statusText;
          if (response.statusText == '' /* No response */ || response.status == 12031 /* In IE */) {
            failureReason = 'Server not responding';
          }
          // show the error message at the bottom
          this._x_notification.replace(new XWiki.widgets.Notification("Failed: " + failureReason, 'error', {timeout : 5}));
          if (this.displayAnnotationsCheckbox) {
            this.displayAnnotationsCheckbox.checked = false;
          }
        }.bind(this),

        on0: function (response) {
          response.request.options.onFailure(response);
        }.bind(this),

        onComplete: function() {
          // In the end: re-enable the checkbox
          if (this.displayAnnotationsCheckbox) {
            this.displayAnnotationsCheckbox.disabled = false;
          }
        }.bind(this)
      });
    }.bind(this));
  },

  /**
   * Checks if the passed response contains a non-zero response code and, in this case, executes the failure callback
   * of the response.
   */
  checkResponseCodeAndFail : function(response) {
    if (response.responseJSON && response.responseJSON.responseCode != null && response.responseJSON.responseCode == 0) {
      // everything's fine
      return false;
    } else {
      // response returns a code and says that there is an error
      if (response.responseJSON) {
        response.statusText = response.responseJSON.responseMessage;
      } else {
        response.statusText = "Wrongly formatted server response";
      }
      response.request.options.onFailure(response);
      return true;
    }
  },

  /**
   * Loads the annotated content in the annotated element content, if there are any annotations or if forced.
   *
   * @param annotatedContent object holding the annotated content to update the annotated element with (stores the annotations list and the annotated html)
   * @param andShow whether the annotations should also be shown (highlighted) on the content
   * @param navigateToPane if the document should be repositioned to the annotations tab in the document extra section. Useful when the changes on annotations are done from the tab, when the document position should still stay in the tab
   * @param force boolean specifying whether loading should be done even if there are no annotations to display (useful for deleting annotations, which should be reflected in the annotated element even if no annotations are still left to display)
   */
  loadAnnotatedContent : function(annotatedContent, andShow, navigateToPane, force) {
    // only load the annotated content if there are any annotations
    if ((annotatedContent.annotations && annotatedContent.annotations.size() > 0) || force) {
      this.annotatedElement.update(annotatedContent.content);
      this.addAnnotationMarkers(annotatedContent.annotations);
      // Notify the content change.
      document.fire('xwiki:dom:updated', {elements: [this.annotatedElement]});
      // and also handle the tab 'downstairs' when the annotations list changes
      this.reloadTab(navigateToPane);
    }
    if (andShow) {
      this.toggleAnnotations(true);
    }
  },

  reloadTab : function(navigateToPane) {
    var annotationsPane = $( this.annTabname + 'pane');
    if (annotationsPane) {
      // reset to initial state
      annotationsPane.update('');
      annotationsPane.addClassName('empty');
      if (!annotationsPane.hasClassName('hidden')) {
        // reload
        XWiki.displayDocExtra(this.annTabname, this.annTabTemplate, navigateToPane);
      }
    }
  },

  addDeleteListenersInTab : function() {
    // This applies only to the annotations tab because merged annotations are currently displayed and deleted by the Comments system.
    // NOTE: don't forget to change this too if, in the future, annotations are no longer deleted by the Comments system from the Comments tab.
    $$('#Annotationspane .annotation a.delete').each(function(item) {
      this.addDeleteListener(item);
    }.bind(this));
  },

  addEditListenersInTab : function() {
    // This applies only to the annotations tab because merged annotations are currently displayed and edited by the Comments system.
    // NOTE: don't forget to change this too if, in the future, annotations are no longer edited by the Comments system from the Comments tab.
    // NOTE: Doing this does not allow us to see any extra properties that may be added to the XWikiComments class. These properties will still be displayed and editable in the annotation bubble, but not in the tab, because the bubble is handled by the Annotations system, while the tab is handled by the Comments system.
    $$('#Annotationspane .annotation a.edit').each(function(item) {
      var container = item.up('.annotation');
      // compute annotation id, which is right after annotation_list_ in the container ID... TODO: this is pretty wrongish...
      var annotationId = container.id.substring(16);
      this.addEditListener(item, annotationId, container.up());
    }.bind(this));
  },

  addValidateListenersInTab : function() {
    $$('.annotation a.validate').each(function(item) {
      var container = item.up('.annotation');
      // compute annotation id, which is right after annotation_list_ in the container ID... TODO: this is pretty wrongish...
      var annotationId = container.id.substring(16);
      this.addValidateListener(item, annotationId, container);
    }.bind(this));
  },

  addDeleteListener : function(item, inBubble, container) {
    item.observe('click', function(event) {
      item.blur();
      event.stop();
      if (item.disabled) {
        // Do nothing if the button was already clicked and it's waiting for a response from the server.
        return;
      } else {
        new XWiki.widgets.ConfirmedAjaxRequest(
          item.href,
          {
            parameters: this.prepareRequestParameters(new Hash()),
            onCreate : function() {
              // Disable the button, to avoid a cascade of clicks from impatient users
              item.disabled = true;
            },
            onSuccess : function(response) {
              // check the response to see if all went fine
              if (this.checkResponseCodeAndFail(response)) {
                return;
              }
              // hide the bubble if the delete takes place in a bubble
              if (inBubble) {
                this.hideBubble(container);
              }
              this.fetchedAnnotations = true;
              // reload the received annotated content forcing update so that deleting last annotation is reflected in the list of annotations, with scroll to tab if not in bubble
              this.loadAnnotatedContent(response.responseJSON.annotatedContent, this.displayAnnotationsCheckbox ? this.displayAnnotationsCheckbox.checked : this.displayedByDefault, !inBubble, true);
            }.bind(this),
            onComplete : function() {
              // In the end: re-enable the button
              item.disabled = false;
            }
          },
          /* Interaction parameters */
          {
             confirmationText: "Are you sure you want to delete this annotation?",
             progressMessageText : "Deleting annotation...",
             successMessageText : "Annotation deleted",
             failureMessageText : "Failed to delete annotation: "
          }
        );
      }
    }.bindAsEventListener(this));
  },

  addValidateListener : function(item, id, container, inBubble) {
    item.observe('click', function(event) {
      item.blur();
      event.stop();
      // and submit the update
      this.updateAnnotationAsync(container, id, inBubble, item.href, 'POST',
        new Hash({'state' : 'SAFE', 'originalSelection' : ''}),
        {
          successText : "Annotation has been successfully validated.",
          failureText : "Failed: "
        });
    }.bindAsEventListener(this));
  },

  addEditListener : function(item, id, container, inBubble) {
    item.observe('click', function(event) {
      item.blur();
      event.stop();
      if (item.disabled) {
        // Do nothing if the button was already clicked and it's waiting for a response from the server.
        return;
      } else {
        require(['xwiki-meta'], function (xm) {
          new Ajax.Request('http://localhost:8081/xwiki/bin/view/AnnotationCode/EditForm', {
            parameters: {
              'xpage' : 'plain',
              'wiki' : XWiki.currentWiki,
              'space' : XWiki.currentSpace,
              'page' : XWiki.currentPage,
              'reference' : xm.document,
              'id' : id
            },
            onCreate : function() {
              // save the original content to be able to cancel or to be able to recover at callback failure
              container.originalContentHTML = container.innerHTML;
              // Disable the button, to avoid a cascade of clicks from impatient users
              item.disabled = true;
              // set the container as loading -> might not really work on bubble since it doesn't have fixed size
              container.update(new Element('div', {'class' : 'loading'}));
            },
            onSuccess : function(response) {
              // fill the edit bubble
              this.fillEditForm(container, response.responseText, id, inBubble);
            }.bind(this),
            onFailure: function(response) {
              var failureReason = response.statusText;
              if (response.statusText == '' /* No response */ || response.status == 12031 /* In IE */) {
                failureReason = 'Server not responding';
              }
              // show the error message at the bottom
              this._x_notification = new XWiki.widgets.Notification("annotations.action.edit.form.loaderror" + failureReason, 'error', {timeout : 5});
              // load the original content of the container
              this.fillViewPanel(container, container.originalContentHTML, id, inBubble);
            }.bind(this),
            on0: function (response) {
              response.request.options.onFailure(response);
            }.bind(this),
            onComplete : function() {
              // In the end: re-enable the button
              item.disabled = false;
            }
          });
        }.bind(this));
      }
    }.bindAsEventListener(this));
  },

  addAnnotationMarkers : function(annotations) {
    annotations.each(function(item){
      // get the last span of this annotation
      var allSpans = this.annotatedElement.select('[class~=ID' + item.annotationId + ']');
      if (allSpans.size() == 0) {
        return;
      }
      var lastSpan = allSpans[allSpans.size() - 1];
      // create the annotation markers hidden by default, since annotations are added on the document hidden by default
      var markerSpan = new Element('span', {'id': 'ID' + item.annotationId, 'class' : 'hidden annotation-marker ' + item.state});
      lastSpan.insert({after: markerSpan});
      // annotations are displayed on mouseover
      markerSpan.observe('click', this.onMarkerClick.bindAsEventListener(this, item.annotationId));
    }.bind(this));
  },

  // maybe this should be moved in a function to display a bubble from an address, to call for all dialogs for different parameters
  onMarkerClick : function(event, id) {
    var bubbleId = 'annotation-bubble-' + id;
    var bubble = $(bubbleId);
    if (!this.displayHighlight) {
      this.toggleAnnotationHighlight(id, !bubble);
    }
    if (bubble) {
      // Close the bubble.
      this.hideBubble(bubble);
    } else {
      // Show the bubble and fetch the annotation display in it.
      var bubble = this.displayLoadingBubble(event.element().cumulativeOffset().top,
        event.element().cumulativeOffset().left);
      bubble.writeAttribute('id', bubbleId);
      this.fetchAndShowAnnotationDetails(id, bubble);
    }
  },

  fetchAndShowAnnotationDetails : function(annotationId, container) {
    require(['xwiki-meta'], function (xm) {
      new Ajax.Request('http://localhost:8081/xwiki/bin/view/AnnotationCode/DisplayForm', {
        parameters: {
          'id' : annotationId,
          'xpage' : 'plain',
          'wiki' : XWiki.currentWiki,
          'space' : XWiki.currentSpace,
          'page' : XWiki.currentPage,
          'reference' : xm.document
        },
        onSuccess: function(response) {
          // display the annotation creation form
          this.fillViewPanel(container, response.responseText, annotationId, true);
        }.bind(this),

        onFailure: function(response) {
          var failureReason = response.statusText;
          if (response.statusText == '' /* No response */ || response.status == 12031 /* In IE */) {
            failureReason = 'Server not responding';
          }
          // hide the loading bubble
          this.hideBubble(newBubble);
          // show the error message at the bottom
          this._x_notification = new XWiki.widgets.Notification("Failed: " + failureReason, 'error', {timeout : 5});
        }.bind(this),

        on0: function (response) {
          response.request.options.onFailure(response);
        }.bind(this)
      });
    }.bind(this));
  },

  displayLoadingBubble : function(top, left) {
    // create an element with the form
    var bubble = new Element('div', {'class' : 'annotation-bubble'});
    // and a nice loading panel inside
    bubble.insert({top : new Element('div', {'class' : 'loading'})});
    // and put it in the content
    document.body.insert({bottom : bubble});
    // make it hidden for the moment
    bubble.toggleClassName('hidden');
    // position it
    bubble.style.left = left + 'px';
    bubble.style.top = top + 'px';
    // make it visible
    bubble.toggleClassName('hidden');
    // put this bubble in the bubbles stack
    this.bubbles.push(bubble);

    return bubble;
  },

  displayAnnotationViewBubble : function(marker) {
  },

  /**
   * Updates the container with the passed content only if the container is still displayed, and returns true if this is the case.
   */
  safeUpdate : function(container, content) {
    if (!container.parentNode) {
      // it's not attached anymore: either mouseout or escape
      return false;
    }

    // put the content in
    container.update(content);
    return true;
  },

  /**
   * Fills the edit form in the passed container, with the content passed (which should be the edit form) and sets all
   * listeners for the annotation with the passed id. If inBubble is true, the edit form is in a bubble, not in the
   * bottom panel.
   */
  fillEditForm : function(container, content, annotationId, inBubble) {
    if (!this.safeUpdate(container, content)) {
      return;
    }
    // remove the mouseout listener (if any), edit form should stay on
    container.stopObserving('mouseout');
    // add the delete and validate listeners to the respective delete buttons
    var deleteButton = container.down('a.delete');
    if (deleteButton) {
      this.addDeleteListener(deleteButton, inBubble, container);
    }
    var validateButton = container.down('a.validate');
    if (validateButton) {
      this.addValidateListener(validateButton, annotationId, container, inBubble);
    }
    container.down('form').focusFirstElement();
    // and add the button listeners
    container.down('input[type=submit]').observe('click', this.onAnnotationEdit.bindAsEventListener(this, container, annotationId, inBubble));
    container.down('input[type=reset]').observe('click', function(event) {
      if (inBubble) {
        // close this bubble.
        this.hideBubble(container);
      } else {
        // reload the original content on cancel
        this.fillViewPanel(container, container.originalContentHTML, annotationId, false);
      }
    }.bindAsEventListener(this));
  },

  onAnnotationEdit : function(event, container, annotationId, inBubble) {
    event.stop();
    var form = container.down('form');
    var formData = new Hash(form.serialize(true));
    // aaand update
    this.updateAnnotationAsync(container, annotationId, inBubble, form.action, form.method, formData,
      {
        successText : "Annotation has been successfully updated.",
        failureText : "Failed: "
      });
  },

  /**
   * Handles the asynchronous update of annotation given by annotatinId, to the specified url, sending the specified
   * parameters and using the passed messages. Container will pass in loading state while the async call takes place,
   * and the tab update & form hiding will be handled as specified by inBubble. The passed messages must specify
   * successText and failureText.
   */
  updateAnnotationAsync : function(container, annotationId, inBubble, action, method, parameters, messages) {
    // create the async request to update the annotation
    new Ajax.Request(action, {
      method : method,
      parameters : this.prepareRequestParameters(parameters),
      onCreate : function() {
        // make it load when starting to send the async call
        if (container.parentNode) {
          container.update(new Element('div', {'class' : 'loading'}));
        }
      },
      onSuccess : function (response) {
        // check the response to see if all went fine
        if (this.checkResponseCodeAndFail(response)) {
          return;
        }
        this._x_notification = new XWiki.widgets.Notification(messages.successText, 'done');
        if (inBubble) {
          // close the bubble on successful update
          this.hideBubble(container);
        }
        this.fetchedAnnotations = true;
        // reload the received annotated content, with scroll to tab
        this.loadAnnotatedContent(response.responseJSON.annotatedContent, this.displayAnnotationsCheckbox ? this.displayAnnotationsCheckbox.checked : this.displayedByDefault, !inBubble);
      }.bind(this),
      onFailure : function(response) {
        var failureReason = response.statusText;
        if (response.statusText == '' /* No response */ || response.status == 12031 /* In IE */) {
          failureReason = 'Server not responding';
        }
        this._x_notification.replace(new XWiki.widgets.Notification(messages.failureText + failureReason, 'error', {timeout : 5}));
        if (inBubble) {
          // and close the bubble on failure to update
          this.hideBubble(container);
        } else {
          // reload the original content on failure
          this.fillViewPanel(container, container.originalContentHTML, annotationId, false);
        }
      }.bind(this),
      on0 : function (response) {
        response.request.options.onFailure(response);
      }
    });
  },

  /**
   * Fills the display panel for the passed container, with the passed content, for the passed annotation and sets the
   * edit and delete listeners. If inBubble is set to true, then the panel is in a view bubble, not in the bottom panel
   * (or other place).
   */
  fillViewPanel : function(container, content, annotationId, inBubble) {
    if (!this.safeUpdate(container, content)) {
      return;
    }
    // and add the button observers
    /*
    No hide button ftm
    bubble.down('a.annotation-view-hide').observe('click', function(event, bubble) {
      event.stop();
      this.hideBubble(bubble);
    }.bindAsEventListener(this, bubble));
    */
    // add the delete listener to the delete button
    var deleteButton = container.down('a.delete');
    if (deleteButton) {
      this.addDeleteListener(deleteButton, inBubble, container);
    }
    var validateButton = container.down('a.validate');
    if (validateButton) {
      this.addValidateListener(validateButton, annotationId, container, inBubble);
    }
    var editButton = container.down('a.edit');
    if (editButton) {
      this.addEditListener(editButton, annotationId, container, inBubble);
    }
    // Annotations can have a reply button when they are merged with comments (and thus stored as comments). Custom annotations will not have this button displayed.
    var replyButton = container.down('a.reply');
    if (replyButton) {
      // When a click is done on this button, fire a click on the corresponding button in the comments tab.

      // Locate the button in the comments tab.
      var replyButtonInTab = $$('#Commentspane #xwikicomment_' + annotationId + ' a.commentreply')[0];

      // If the replyButtonInTab is not found, then the comments tab is not visible so we hide the reply button as well, otherwise it just does not work.
      if (!replyButtonInTab) {
        replyButton.hide();
      } else {
        // When the reply button from the bubble is clicked, also click the reply button from the comments tab.
        replyButton.observe('click', function(event) {
          // Stop the bubble click event.
          event.stop();

          // The content of the Comments tab is reloaded when a comment is added so we can't cache the reference to the
          // reply button.
          replyButtonInTab = $$('#Commentspane #xwikicomment_' + annotationId + ' a.commentreply')[0];
          // Click the reply button from the comments tab button instead.
          replyButtonInTab.click();

          // Lose the focus on the bubble so that it can go away.
          container.blur();
        });
      }
    }
  },

  /**
   * Hides the passed bubble and removes it from the bubbles stack.
   */
  hideBubble : function(bubble) {
    if (!bubble.parentNode) {
      // it's not attached anymore: either mouseout or escape
      return;
    }
    bubble.remove();
    var bubbleIndex = this.bubbles.indexOf(bubble);
    if (bubbleIndex >= 0) {
      // remove it
      this.bubbles.splice(bubbleIndex, 1);
    }
  },

  registerShortcuts : function(annotationShorcuts, method) {
    for (var i = 0; i < annotationShorcuts.size(); ++i) {
      shortcut.add(annotationShorcuts[i], method.bindAsEventListener(this));
    }
  },
  unregisterShortcuts : function(annotationShorcuts) {
    for (var i = 0; i < annotationShorcuts.size(); ++i) {
      shortcut.remove(annotationShorcuts[i]);
    }
  },

  registerAddAnnotationShortcut : function() {
    this.registerShortcuts(this.addAnnotationShortcuts, this.onAddAnnotationShortcut);
  },
  unregisterAddAnnotationShortcut : function() {
    this.unregisterShortcuts(this.addAnnotationShortcuts);
  },

  registerCloseDialogShortcut : function() {
    this.registerShortcuts(this.closeDialogShortcuts, this.closeOpenBubble);
  },

  registerToggleAnnotationsShortcut : function() {
    this.registerShortcuts(this.toggleAnnotationsShortcuts, this.onToggleAnnotationsShortcut);
  },

  onToggleAnnotationsShortcut : function() {
    if (this.fetchedAnnotations) {
      this.setAnnotationVisibility(!this.displayingAnnotations);
      this.toggleAnnotations(this.displayingAnnotations);
    } else {
      this.fetchAnnotations(true);
    }
  },

  /**
   * Closes the last opened bubble (i.e. last bubble in the this.bubbles stack).
   */
  closeOpenBubble : function() {
    if (this.bubbles.length > 0) {
      // get the last one
      var lastBubble = this.bubbles[this.bubbles.length - 1];
      if (lastBubble == this.createPanel) {
        this.hideAnnotationCreationForm();
      } else {
        this.hideBubble(lastBubble);
      }
    }
  },

  /**
   * Execute the add annotation shortcut: get selection, compute context, open dialog, register listeners.
   */
  onAddAnnotationShortcut : function() {
    // if the document is in 1.0 syntax, prevent the create dialog to be displayed, display a warning and stop everything
    if (XWiki.docsyntax == 'xwiki/1.0') {
      new XWiki.widgets.Notification("Annotations are not available for pages in XWiki/1.0 syntax.", 'warning');
      return;
    }
    // parse the selection
    this.selectionService.computeSelection();
    var selectionText = this.selectionService.selectionText;
    if (!selectionText) {
      // show an 'invalid selection message'. Shorter time here, otherwise it's a bit confusing...
      new XWiki.widgets.Notification("Please select a non-empty text in the page content.", 'error', {timeout : 5});
    } else {
      this.selectionService.computeContext();
      require(['xwiki-meta'], function (xm) {
        // fetch the creation for this annotation and display it at the position of the selection
        new Ajax.Request('http://localhost:8081/xwiki/bin/view/AnnotationCode/CreateForm', {
          parameters: {
            'xpage' : 'plain',
            'selection' : selectionText,
            'selectionContext' : this.selectionService.selectionContext,
            'selectionOffset' : this.selectionService.selectionOffset,
            'reference' : xm.document
          },
          onCreate: function() {
            // create nice loading panel
            this.displayAnnotationCreationForm();
          }.bind(this),

          onSuccess: function(response) {
            // display the annotation creation form
            this.fillCreateForm(this.createPanel, response.responseText);
          }.bind(this),

          onFailure: function(response) {
            var failureReason = response.statusText;
            if (response.statusText == '' /* No response */ || response.status == 12031 /* In IE */) {
              failureReason = 'Server not responding';
            }
            // show the error message at the bottom
            this._x_notification = new XWiki.widgets.Notification("Failed: " + failureReason, 'error', {timeout : 5});
            // and hide the create form panel
            this.hideAnnotationCreationForm();
          }.bind(this),

          on0: function (response) {
            response.request.options.onFailure(response);
          }.bind(this)
        });
      }.bind(this));
    }
  },

  displayAnnotationCreationForm : function() {
    // TODO: get this color from the color theme
    this.selectionService.highlightSelection('#FFEE99');
    // get the position and build the loading bubble
    var position = this.selectionService.getPositionNextToSelection();
    this.createPanel = this.displayLoadingBubble(position.top, position.left);
    // remove the ctrl + M listeners, so that only one dialog is displayed at one moment
    this.unregisterAddAnnotationShortcut();
  },

  fillCreateForm : function(container, panelContent) {
    // put the content in. Safe update because an escape might have been hit
    if (!this.safeUpdate(this.createPanel, panelContent)) {
      return;
    }
    // set the focus in the first element of type input
    this.createPanel.select('form').first().focusFirstElement();
    // and add the button observers
    this.createPanel.down('input[type=submit]').observe('click', this.onAnnotationAdd.bindAsEventListener(this));
    this.createPanel.down('input[type=reset]').observe('click', function() {
      this.hideAnnotationCreationForm();
    }.bind(this));
  },

  hideAnnotationCreationForm : function(skipSelectionHighlightClear) {
    // remove it from document and remove it from the open bubbles
    this.hideBubble(this.createPanel);
    if (!skipSelectionHighlightClear) {
      // rollback selection coloring
      this.selectionService.removeSelectionHighlight();
    }
    // and listen to the create shortcut again
    this.registerAddAnnotationShortcut();
  },

  onAnnotationAdd : function(event) {
    // don't submit
    event.stop();
    var form = this.createPanel.down('form');
    var formData = new Hash(form.serialize(true));
    // aaand submit
    new Ajax.Request(form.action, {
      method : form.method,
      parameters : this.prepareRequestParameters(formData),
      onCreate : function() {
        // make it load while update is in progress
        this.createPanel.update(new Element('div', {'class' : 'loading'}));
      }.bind(this),
      onSuccess : function (response) {
        // check the response to see if all went fine
        if (this.checkResponseCodeAndFail(response)) {
          return;
        }
        if (this.displayAnnotationsCheckbox) {
          this.displayAnnotationsCheckbox.checked = true;
        }
        this.loadAnnotatedContent(response.responseJSON.annotatedContent, true);
        this.fetchedAnnotations = true;
        form._x_notification = new XWiki.widgets.Notification("Annotation has been successfully added", 'done');
        // and hide the create bubble, skipping selection highlight clear
        this.hideAnnotationCreationForm(true);
      }.bind(this),
      onFailure : function(response) {
        this.hideAnnotationCreationForm();
        var failureReason = response.statusText;
        if (response.statusText == '' /* No response */ || response.status == 12031 /* In IE */) {
          failureReason = 'Server not responding';
        }
        this._x_notification = new XWiki.widgets.Notification("Failed: " + failureReason, 'error', {timeout : 5});
      }.bind(this),
      on0 : function (response) {
        response.request.options.onFailure(response);
      }
    });
  },

  /**
   * Handles the refresh of the document content when an annotations is deleted from the comments tab.
   * It applies only to the case when annotations are merged with (and stored as) comments. Custom annotations will not use this.
   */
  refreshAnnotationsOnCommentDelete : function(event) {
    // if the annotations are currently visible, re-fetch the annotations and display them
    var visible = this.displayAnnotationsCheckbox ? this.displayAnnotationsCheckbox.checked : this.displayedByDefault;
    if (visible) {
      // Force the reloading in case this annotation was the last one.
      this.fetchAnnotations(true, true);
    } else {
      // Mark the loaded annotations as dirty and make sure the next time the annotations checkbox is checked, the annotations will be fetched.
      this.fetchedAnnotations = false;
    }
  }
});
// End XWiki augmentation.
return XWiki;
}(XWiki || {}));

document.observe('xwiki:dom:loaded', function() {
  // Load the annotations only in view mode, if the document content is displayed
  // (the document content is not displayed when viewer=history for instance).
  if (XWiki.contextaction != 'view' || !$('xwikicontent')) {
    return;
  }

      var displayHighlight = true;
        var displayed = false;
        var activated = true;
  
  // parse the exception spaces and check where is the current space in that list
  var exceptions = [];
    var currentSpaceInExceptions = exceptions.indexOf(XWiki.currentSpace);
  // if the annotations are activated and the current space is not an exception or the annotations are not activated but the current space is an exception
  if ((activated && currentSpaceInExceptions < 0) || (!activated && currentSpaceInExceptions >= 0)) {
    // initialize the annotations on the xwikicontent element which is the document content by default
    new XWiki.Annotation(displayHighlight, $('xwikicontent'), displayed);
  }
});

