var XWiki = (function (XWiki) {

XWiki.EntryNamePopup = Class.create(XWiki.widgets.ModalPopup, {
  initialize : function($super, trigger) {
    trigger.observe('click', this.showDialog.bindAsEventListener(this));
    this.urlTemplate = trigger.up().next('input[type=hidden]').value;

    this.input = new Element('input', {type: 'text'});
    this.addButton = new Element('input', {type: 'image', src: '../../../resources/icons/silk/add.png', alt: 'Add'});

    var container = new Element('div', {id: 'entryNamePopup'});
    container.insert(this.input);
    container.insert(this.addButton);

    $super(container, {
      show: {method: this.showDialog, keys: []},
      add: {method: this._onAdd, keys: ['Enter']}
    }, {
      title: 'Entry name:',
      verticalPosition: 'top'
    });
  },
  createDialog : function($super, event) {
    this.addButton.observe('click', this._onAdd.bind(this));
    $super(event);
  },
  showDialog : function($super, event) {
    $super(event);
    this.input.clear().focus();
  },
  _onAdd : function() {
    if (this.input.value != '') {
      // We use the global flag because we need to replace both the page name and its title.
      window.self.location = this.urlTemplate.replace(/__entryName__/g, encodeURIComponent(this.input.value));
    } else {
      this.input.focus();
    }
  }
});

function init() {
  var actionBox = $('actionBox');
  if(actionBox) {
    new XWiki.EntryNamePopup(actionBox.down('.add'));
    return true;
  }
  return false;
}
(XWiki.domIsLoaded && init()) || document.observe('xwiki:dom:loaded', init);

return XWiki;
}(XWiki || {}));
