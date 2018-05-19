// Sometimes IE8 loads the font *after* the rendering of the page so it does not display the icon correctly.
// To fix this, we reload the stylesheet when we receive the 'document ready' event so that the browser recomputes
// the display. Although, it does not solve everything (see https://jira.xwiki.org/browse/XWIKI-10813).
require(['jquery'], function($) {
  // This hack concerns IE8 only
  if (navigator.userAgent.indexOf('MSIE 8.0') > -1) {
    $(function() {
      var link = $("link[href*='IconThemes/FontAwesome']");
      link[0].href = link[0].href;
    });
  }
});

