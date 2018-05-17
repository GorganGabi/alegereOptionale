var XWiki = (function(XWiki) {
  /**
   * Extends the SuggestPicker to customize the way the selected targets (users or email addresses) are displayed.
   */
  var TargetSelectionManager = Class.create(XWiki.widgets.SuggestPicker, {
    // @Override
    initialize: function($super, element, suggest, options) {
      $super(element, suggest, options);
      this.list.addClassName('targets-container');
    },

    // @Override
    matchesSelectedValue: function(value, suggestion) {
      // The given value can be a relative user reference. We need to parse it and match only the user alias.
      return XWiki.Model.resolve(value, XWiki.EntityType.DOCUMENT).name == suggestion.id;
    },

    // @Override
    displayItem: function(suggestion) {
      var targetInfo = new Element('span').update(suggestion.info).insert(this.createDeleteTool());
      var targetClass = 'target-item ' + (suggestion.value.indexOf('@') > 0 ? 'target-email' : 'target-user');
      return new Element('li', {'class': targetClass}).insert(targetInfo).insert(this.createItemInput(suggestion));
    }
  });

  /**
   * Extends the UserPicker to change the selection manager.
   */
  var UserAndEmailPicker = Class.create(XWiki.widgets.UserPicker, {
    // @Override
    _createSelectionManager: function(options) {
      return new TargetSelectionManager(this.fld, this, options);
    },

    // @Override
    setHighlightedValue: function ($super) {
      if (!this.iHighlighted) {
        // Select the current input value when there is no highlighted suggestion to allow the user to enter free
        // text (e.g. an email address).
        this.clearSuggestions();
        typeof this.options.callback == "function" && this.options.callback({
          'id': this.fld.value,
          'value': this.fld.value,
          'info': this.fld.value,
        });
      } else {
        $super();
      }
    },

    // @Override
    clearSuggestions: function($super) {
      $super();
      // Keep the picker active even after clearing the list of suggestions to force it to handle the Enter key. This
      // way the user can enter free text (e.g. an email address) without submitting the form.
      this.isActive = true;
    }
  });

  XWiki.SharePage = Class.create({
    initialize : function (parentContainer) {
      this.input = $('shareTarget');

      if (!this.input) {
        return;
      }

      var userEmailOptions = {
        script: XWiki.currentDocument.getURL('get', 'xpage=uorgsuggest&uorg=user&'),
        shownoresults : false
      };
      if (parentContainer) {
        userEmailOptions.parentContainer = parentContainer;
      }
      new UserAndEmailPicker(this.input, userEmailOptions);
      this.input.focus();

      document.observe('xwiki:multisuggestpicker:selectionchanged', this._onSelectionChanged.bindAsEventListener(this));

      this.form = this.input.up('form');
      if (this.form) {
        this.form.observe('submit', this._onSubmit.bindAsEventListener(this));
        dialog && this.form.down('.secondary').observe('click', function(event) {
          event.stop();
          dialog.closeDialog();
        }.bindAsEventListener(this));
      }
    },

    _onSubmit : function(event) {
      event.stop();
      if (!this.form.down('.target-item') && this.input.value.strip().length == 0) {
        this.input.addClassName('xErrorField');
        this.input.insert({'after' : '<div class="xErrorMsg">Please enter the recipient</div>'});
        return;
      }
      var params = this.form.serialize();
      this.form.up().update(loading);
      new Ajax.Updater(loading.up(), XWiki.currentDocument.getURL('get', 'xpage=shareinline'), {
        parameters: params,
        onComplete : function() {
          dialog && dialog.dialog.down('.share-backlink').observe('click', dialog.closeDialog.bind(dialog));
        }
      });
    },

    _onSelectionChanged : function(event) {
      if (event.memo.trigger == this.input) {
        this.input.removeClassName('xErrorField');
        var errorMessage = this.input.next('.xErrorMsg');
        errorMessage && errorMessage.remove();
      }
    }
  });

  var loading = new Element('div', {'class' : 'imgcenter'}).update("<img src=\"../../../resources/icons/xwiki/ajax-loader-large.gif\"/>");
  var dialog;

  document.observe('xwiki:dom:loaded', function() {
    $('shareTarget') && new XWiki.SharePage();
    if ($('tmActionShare')) {
      $('tmActionShare').observe('click', function(event) {
        event.stop();
        dialog = new XWiki.widgets.ModalPopup(
          loading, {}, {
            'verticalPosition' : 'top',
            'title' : "Share this page",
            'removeOnClose' : true
          }
        );
        dialog.showDialog();
        loading.up().up().addClassName('share-dialog');
        new Ajax.Updater(loading.up(), XWiki.currentDocument.getURL('get', 'xpage=shareinline'), {
          onComplete : function() { new XWiki.SharePage(dialog.dialogBox); }
        });
      });
    }
  });
  return XWiki;
}(XWiki || {}));
