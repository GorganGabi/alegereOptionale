var XWiki = (function(XWiki) {

var panels = XWiki.panels = XWiki.panels || {};

panels.MoreApplicationsButtonListener = Class.create({
  initialize: function(button) {
    this.button = button;
    this.container = button.up('.applicationsPanelMoreList').down('.applicationPanelMoreContainer');

    var moreAppsCount = this.container.select('.application-label').length;
    if (moreAppsCount > 0) {
      this.container.id = 'applicationPanelMoreContainer' + Math.floor(Math.random()*101);
      this.isToggling = false;
      this.button.observe('click', function(e) {
        e.stop();
        if (!this.isToggling) {
          this.toggle();
        }
      }.bindAsEventListener(this));
    } else {
      button.up('.applicationsPanelMoreList').hide();
    }

    return this;
  },

  toggle: function() {
    this.isToggling = true;
    if (this.container.hasClassName('hidden')) {
      this.container.style.display = "none";
      this.container.removeClassName('hidden');
      Effect.BlindDown(this.container.id, {
        duration: 0.1,
        afterFinish: function() {
          this.isToggling = false;
        }.bind(this)
      });
    } else {
      Effect.BlindUp(this.container.id, {
        duration: 0.1,
        // We do this to be able to get our hidden element back, we don't rely on
        // display:none since we don't allow nested style in HTML elements.
        afterFinish: function() {
          this.container.addClassName('hidden');
          this.isToggling = false;
        }.bind(this)
      });
    }
  }
});

return XWiki;
}(XWiki || {}));

document.observe('xwiki:dom:loaded', function(e) {
  $$('.applicationPanelMoreButton').each(function(button) {
    new XWiki.panels.MoreApplicationsButtonListener(button);
  });
});
