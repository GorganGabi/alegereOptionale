require(['jquery', 'bootstrap'], function($) {
  $(document).ready(function() {

    // Some variables used in the next 2 functions
    var globalSearch = $('#globalsearch');
    var globalSearchInput = globalSearch.find('input');
    var globalSearchButton = globalSearch.find('button');

    // Open the global search when the user click on the global search button
    globalSearchButton.click(function(event) {
      if (!globalSearch.hasClass('globalsearch-close') && globalSearchInput.val().length > 0) {
        return true;
      }
      globalSearch.removeClass('globalsearch-close');
      globalSearchInput.focus();
      return false;
    });

    // Close the global search when the focus is lost
    globalSearchInput.focusout(function() {
      // In order to let the main thread setting the focus to the new element, we execute the following code
      // in a callback.
      setTimeout( function () {
        // We close the global search only if the focus is not on the search input or the search button.
        // Without this, the global search would be close each time the user click on the button (even when it's for
        // actually performing the search).
        if (document.activeElement !== globalSearchButton[0] && document.activeElement !== globalSearchInput[0]) {
          globalSearch.addClass('globalsearch-close');
        }
      }, 1);
    });
    
    // Close dropdown menus when the search button is clicked
    globalSearchButton.click(function(event) {
      $('[data-toggle="dropdown"][aria-expanded="true"]').dropdown('toggle');
    });

  });
});
