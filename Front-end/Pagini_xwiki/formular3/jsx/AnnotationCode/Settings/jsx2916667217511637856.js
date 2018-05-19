var XWiki = (function (XWiki) {
// Start XWiki augmentation.
XWiki.AnnotationSettings = Class.create({
  initialize : function() {
    this.addCheckboxListeners();
    this.addClearListeners();
    this.addToggleFilterVisibility('.criterion-label', '.criterion', 'collapsed');
    this.addToggleFilterVisibility('.annotation-filters-toggler a', '.annotation-filters', 'collapsed');
    // send a filter change event to notify annotations of the initial state of the loaded filter
    document.fire("xwiki:annotations:filter:changed", this.getCurrentFilter());
  },

  addCheckboxListeners : function() {
    $$('.criteria input[type=checkbox]').each(function(item) {
      item.observe('click', function(event) {
        // 1/ Update annotations
        var filter = this.getCurrentFilter();
        document.fire("xwiki:annotations:filter:changed", filter);
        // 2/ Some visual changes:
        var input = event.element();
        input.up().toggleClassName('selected');
        var criterion = input.up('.criterion');
        if (criterion.select('label.selected').size() > 0) {
          criterion.addClassName('active');
        } else {
          criterion.removeClassName('active');
        }
      }.bindAsEventListener(this));
    }, this);
  },

  addClearListeners : function () {
    $$('.criterion .clear').each(function(item) {
      item.observe('click', function(event) {
        event.stop();
        // 1/ Unckeck all options + some visual changes:
        var criterion = event.element().up('.criterion');
        criterion.select('label.selected').each(function(item) {
          item.removeClassName('selected');
          item.down('input').checked = false;
        });
        criterion.removeClassName('active');
        // 2/ Update annotations
        var filter = this.getCurrentFilter();
        document.fire("xwiki:annotations:filter:changed", filter);
      }.bindAsEventListener(this));
    }, this);
  },

  /**
   * Reads the current filter from the filtering checkboxes.
   */
  getCurrentFilter : function() {
    var filter = [];
    // for each criterion
    $$('.criteria .criterion').each(function(criterion) {
      // get its name
      var criterionName = criterion.down('input[type=hidden]');
      if (!criterionName) {
        return;
      }
      criterionName = criterionName.name;
      // now go down and gel all values checkboxes which are checked
      var values = [];
      criterion.select('input[type=checkbox]').each(function(checkbox) {
        if(checkbox.checked) {
          values.push(checkbox.value);
        }
      });
      // and stack them in the filter
      for (var i = 0; i < values.length; i++) {
        filter.push({'name' : criterionName, 'value' : values[i]});
      }
    });
    return filter;
  },

  /**
   * Expand/Collapse elements of the filter widget
   */
  addToggleFilterVisibility : function(triggerSelector, parentSelector, toggledClassName) {
    $$(triggerSelector).each(function(item) {
      item.observe('click', function(event) {
        event.stop();
        var parent = event.element().up(parentSelector);
        if (parent) {
          parent.toggleClassName(toggledClassName);
        }
      });
    });
  }
});
// End XWiki augmentation.
return XWiki;
}(XWiki || {}));

document.observe('xwiki:annotations:settings:loaded', function() {
  new XWiki.AnnotationSettings();
});
