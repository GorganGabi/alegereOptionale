
//----------------------------------
// RequireJS configuration
//----------------------------------
require.config({
  paths: {
          'bootstrap-tour': "../../../webjars/bootstrap-tour/0.11.0/js/bootstrap-tour.min.js"
      },
  shim: {
    'bootstrap-tour': {
      deps: ['bootstrap', 'jquery'],
      exports : 'Tour'
    }
  }
});
//----------------------------------
// Display a tour if needed
//----------------------------------
require(['jquery', 'xwiki-meta'], function ($, xm) {
  'use strict';
  
  /**
   * Escape strings so they respect the Tour API constraints.
   */
  var escapeTourName = function (tourName) {
    // The Tour API says tour name must contain only alphanumerics, underscores and hyphens.
    // So we replace any forbidden character by its ASCII value, surrounded by an underscore (that we forbid too to 
    // avoid collisions).
    return tourName.replace(/[^a-zA-Z0-9\-]/g, function(character) {
      return '_'+character.charCodeAt(0)+'_';
    });
  };
  
  /**
   * Add a resume button to start the tour again when it has been closed
   */
  var createResumeButton = function (tour, createPopover) {
    // Create a container when the button will be displayed. This container will also contains the "popover", so the "popover" stay near the button when the page is resized.
    // (see http://getbootstrap.com/javascript/#popovers-options 'container')
    var buttonContainer = $('<div id="tourResumeContainer" style="position: fixed; bottom: 0; right: 0; z-index: 2000; width: 300px; text-align: right;"></div>').appendTo($(document.body));
    // Create the button that will start the tour again
    var button = $('<button id="tourResume" class="btn btn-default btn-xs"><span class="fa fa-info-circle"></span> Show tour</button>').appendTo(buttonContainer);
    buttonContainer.hide();
    buttonContainer.fadeIn();
    button.click(function () {
      if (createPopover) {
        button.popover('destroy');
      }
      tour.start(true);
      button.remove();
    });
    var popoverContent = "You can restart the tour by clicking this button at anytime";
    if (createPopover) {
      // Add a popover to introduce that button
      button.popover({
        animation: true,
        content: popoverContent,
        placement: 'top',
        container: '#tourResumeContainer'
      });
      // Show it
      button.popover('show');
      // Hide it after 7 seconds
      setTimeout(function() {
        button.popover('destroy');
      }, 7000);
    }
  }
  
  /**
   * Add a close button on the top right of the tour popover.
   */
  var addCloseButton = function (tour) {
    var button = $('<button class="btn btn-xs btn-default" data-role="end" style="position: absolute; top: 3px; right: 5px;"><span class=\"fa fa-times\"><\/span></button>').appendTo($('.tour'));
    // Avoid having the close button on top of the title
    $('.tour .popover-title').css('padding-right', button.outerWidth() + 10 + 'px');
  }
  
  /**
   * The template to display a step.
   */
  var getTemplate = function (index, step) {
    var template = '<div class="popover tour" style="min-width: 300px;">\n'
                 + '  <div class="arrow"></div>\n'
                 + '  <h3 class="popover-title"></h3>\n'
                 + '  <div class="popover-content"></div>\n'
                 + '  <div class="popover-navigation row">\n'
                 + '    <div class="col-xs-6 text-left">\n';
    if (step.prev > -1) {
      template  += '      <button class="btn btn-default btn-sm" data-role="prev">\u00AB Prev</button>\n';
    }
    template    += '    </div>\n'
                 + '    <div class="col-xs-6 text-right">\n';
    if (step.next > -1) {
      template  += '      <button class="btn btn-primary btn-sm" data-role="next">Next \u00BB</button>\n';
    } else {
      template  += '      <button class="btn btn-success btn-sm" data-role="end">End tour</button>\n'
    }
    template    += '    </div>\n'
                 + '  </div>'
                 + '</div>';
    return template;
  }
      
  /**
   * Create a tour from a JSON file
   */
  var createTour = function (jsonData) {
    // Add stylesheet only when needed
    var cssURL = "../../../webjars/bootstrap-tour/0.11.0/css/bootstrap-tour.min.css";
    $('<link>').attr('rel', 'stylesheet').attr('type', 'text/css').attr('href', cssURL).appendTo($(document.head));
    
    // Require 'bootstrap-tour' only when needed
    require(['bootstrap-tour'], function(Tour) {
      
      // Create the tour
      var tourName = escapeTourName('tour_' + jsonData.name);
      var tour     = new Tour({
        name    : tourName,
        storage : window.localStorage,
        onEnd   : function() { createResumeButton(tour, true) },
        onShown : addCloseButton,
        orphan  : true,
        template: getTemplate
      });
      
      // Create the steps
      for (var i = 0; i < jsonData.steps.length; i++) {
        // depending on rights, some users may not see all the buttons presented in the tour and we don't need to display the steps is that cases
        // the target element must exists in the DOM or its value must be empty (placing step in the middle of the page)
        if ($(jsonData.steps[i].element).length || jsonData.steps[i].element == '') {
          tour.addStep(jsonData.steps[i]);
        }
      }
      
      // Look if the tour should be started regardless of its status on the local storage
      var getQueryStringParameterByName = function (name) {
        var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.search);
        return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
      }
      var forceStart = getQueryStringParameterByName('startTour') == 'true';
      
      // Launch the tour
      tour.init();
      if (forceStart) {
        tour.restart();
      } else {
        tour.start();
      }
      
      // Create a resume button if the tour has already been closed by the user in the past
      if (window.localStorage.getItem(tourName + '_end') == 'yes') {
        createResumeButton(tour, false);
      }
    });
  };
  
  /**
   * Load asynchronously the list of steps concerning the current page.
   * It's done asynchronously so it does not improve the page rendering time. It's important since this code is used
   * everywhere.
   */ 
  $(document).ready(function () {
  
    /**
     * The tour is not adapted for little screen sizes like mobile phones have.
     * The value 768 is taken from bootstrap in order to be consistent with their media queries.
     */
    if ($(window).innerWidth() <= 768) {
      return;
    }
  
    var ajaxOptions = {
      url         : new XWiki.Document('TourJson', 'TourCode').getURL('get', 'xpage=plain&outputSyntax=plain'),
      dataType    : 'json',
      data        : { tourDoc: xm.document }
    };
    $.ajax(ajaxOptions).success( function(json) {
      for (var i = 0; i < json.tours.length; ++i) {
        var tour = json.tours[i];
        if (tour.steps.length > 0) {
          createTour(tour);
        }
      }
    });
  });
});

