require(['jquery'], function ($) {
  'use strict';

  /**
   * The notification batch' size
   */
  var notificationsLimit = 10;

  /**
   * Maximum number of events to count
   */
  var maxCountNumber = 20;

  /**
   * The current number of unread notifications
   */
  var notificationCount = 0;

  /**
   * The list of the ids of events that have already been displayed and that we don't want to get again in next batches.
   */
  var blackList = [];

  /**
   * URL to the service that return the notifications to display
   */
  var url = new XWiki.Document(XWiki.Model.resolve('XWiki.Notifications.Code.NotificationsDisplayerUIX',
    XWiki.EntityType.DOCUMENT)).getURL('get', 'outputSyntax=plain');

  /**
   * Update notification counter
   */
  var updateNotificationCount = function (count) {
    // Get the counter
    var counter = $('.notifications-count');
    // Update the global variable
    notificationCount = count;
    // Remove the counter if there is no unread notifications
    if (count == 0) {
      counter.remove();
      return;
    }
    // Create the counter if it is not present
    if (counter.length == 0) {
      counter = $('<span>').addClass('notifications-count badge');
      $('#tmNotifications > a.icon-navbar').after(counter);
    }
    // Update the counter
    counter.text(count);
    if (count > maxCountNumber) {
      counter.text(maxCountNumber + '+');
    };
  };

  /**
   * Add a button to clear all the notifications (which actually only change the start date of the user).
   */
  var createCleanButton = function (startDate) {
    var markAllReadButton = $('<a href="#">')
      .addClass('notification-event-clean')
      .html('<span class="fa fa-trash"></span>&nbsp;Clear All')
      .click(function (event) {
        // Avoid the menu closing
        event.stopPropagation();
        // Ask confirmation
        new XWiki.widgets.ConfirmationBox({
          onYes: function(event) {
            // Avoid the menu closing
            event.stopPropagation();
            // Display a saving message
            var notification = new XWiki.widgets.Notification("Clearing the notifications", 'inprogress');
            // Send the request to change the start date
            $.post(url, {
              action: 'setStartDate',
              date: startDate
            }).success(function (){
              // Display the success message
              notification.hide();
              new XWiki.widgets.Notification("Notifications have been cleared", 'done');
              // Remove the notifications from the UI and display the "nothing!" message instead.
              $('.notifications-area').html($('<p>').addClass('text-center noitems').text("No notifications available!"));
              // Update the notifications counter
              updateNotificationCount(0);
            });
          },
          onNo: function(event) {
            // Avoid the menu closing
            event.stopPropagation();
          }
        });
      });
    // Append the button just before the "settings" link in the menu
    $('.notifications-header-uix').append(markAllReadButton);
  };

  /**
   * Display a notification entry
   */
  var displayEntry = function (entry) {
    // Add the id of the entry to the blacklist
    for (var i = 0; i < entry.ids.length; ++i) {
      blackList.push(entry.ids[i]);
    }
    // Create the container
    var notif = $('<div>').addClass('notification-event');
    notif.attr('data-eventtype', entry.type);
    // Put the content
    notif.append(entry.html);
    // Create the "read" button
    var readButton = $('<button>');
    if (!entry.read) {
      notif.addClass('notification-event-unread');
      // Add the "mark as read" button
      notif.find('.notification-content').prepend(readButton);
    }
    if (entry.exception) {
      var exceptionBox = $('<div>').addClass('box errormessage');
      exceptionBox.text(entry.exception);
      notif.append(exceptionBox);
    }
    // Store the data in the DOM element so that any javascript code can retrieve it
    notif.data('notif', entry);
    // Add the notification entry
    $('.notifications-area').append(notif);
    // Add the "mark as read" button if the notif is not already read
    if (!entry.read) {
      // Style the read button
      readButton.addClass('notification-event-read-button').addClass('btn btn-xs');
      // Insert the cross icon
      readButton.html('<span class="fa fa-check"></span>');
      // On click
      readButton.click(function() {
        var notif = $(this).parents('div.notification-event');
        notif.removeClass('notification-event-unread');
        $.post(url, {
          action: 'read',
          eventIds: notif.data('notif').ids.join(','),
          read: true
        });
        $(this).remove();
        if (notificationCount <= maxCountNumber) {
          updateNotificationCount(notificationCount - 1);
        }
      });
    }
    // Details
    var details = notif.find('.notification-event-details');
    details.hide();
    var arrow = notif.find('.notification-event-arrow');
    notif.find('.toggle-notification-event-details').click(function() {
      details.toggle();
      arrow.text(arrow.text() == '▸' ? '▾' : '▸');
    });
  };
  
  /**
   * Display a "no notification" message.
   */
  var displayNoNotification = function () {
    $('.notifications-area').removeClass('loading').html($('<p>').addClass('text-center noitems').text("No notifications available!"));
  };
  
  /**
   * Get the number of unread notifications.
   */
  var getUnreadNotificationsCount = function () {
    $.getJSON(url, {action: 'getUnreadCount'}).done(function (data) {
      updateNotificationCount(data.unread);
      // Display the "nothing!" message if there is no notification
      if (data.unread == 0) {
         displayNoNotification();
      }
    });
  };

  /**
   * Load the notifications.
   *
   * The parameter `untilDate` is used as an "offset" to get events in a paginate mode.
   * We cannot rely on an integer offset because new events could have been stored recently and we want to display older
   * ones only.
   */
  var loadNotifications = function (untilDate) {
    var params = {};
    if (untilDate) {
      params.untilDate = untilDate;
      params.blackList = blackList.join(',');
    }
    $.getJSON(url, params).done(function (data) {
      // Display notifications
      var area = $('.notifications-area');
      // Remove loading items
      area.removeClass('loading');
      $('.notifications-load-more').remove();
      // Display the "nothing!" message if there is no notification
      if (data.notifications.length == 0 && !untilDate) {
        displayNoNotification();
      } else if (untilDate == 0) {
        // Display the clean button if there is some notification, but only the first time (not when clicking on
        // "load more notification") otherwise we would have several clean buttons which would be useless and painful.
        // Note: we use the date of the more recent notification that we have displayed to avoid cleaning
        // notifications that may have been created in the meantime.
        createCleanButton(data.notifications[0].date);
      }
      // Display each entry
      for (var i = 0; i < data.notifications.length; ++i) {
        displayEntry(data.notifications[i]);
      }
      // If there is other notifications to load
      if (data.notifications.length == notificationsLimit) {
        var loadMore = $('<div>').addClass('text-center').addClass('notifications-load-more');
        loadMore.append($('<p>').text("Load older notifications"))
        area.append(loadMore);
        loadMore.click(function(event) {
          loadMore.text('').addClass('loading');
          // We use the date of the last displayed event as an offset to display those that come next
          var lastCompositeEvent = data.notifications[data.notifications.length - 1];
          var lastEventDate = lastCompositeEvent.dates[lastCompositeEvent.dates.length - 1];
          loadNotifications(lastEventDate);
        });
      }
    });
  };

  /**
   * Initialize the widget.
   */
  $(document).ready(function () {
    getUnreadNotificationsCount();

    /**
     * Prevent the dropdown menu for being closed when the user clicks on the notifications menu.
     */
    $('#tmNotifications .dropdown-menu').click(function(event) {
      event.stopPropagation();
    });

    /**
     * Load the notifications content when the user open the notification menu (lazy loading to have better scalability).
     */
    var notificationsMenusHasBeenOpened = false;
    $('#tmNotifications').on('show.bs.dropdown', function () {
      if (!notificationsMenusHasBeenOpened && notificationCount > 0) {
        loadNotifications(0);
      }
      notificationsMenusHasBeenOpened = true;
    });
  });

});

require.config({
  paths: {
    'bootstrap-switch': '../../../webjars/bootstrap-switch/3.3.2/js/bootstrap-switch.min.js'
  },
  shim: {
    'bootstrap-switch' : ['jquery']
  }
});
require(['jquery', 'xwiki-meta', 'bootstrap', 'bootstrap-switch'], function ($, xm) {
  'use strict';
  // Most of the code comes from the deprecated Watchlist Application
  $(document).ready(function() {

    ///
    /// Get the notification inputs for future usage
    ///
    var watchWikiSwitch  = $('#notificationWiki');
    var watchSpaceSwitch = $('#notificationPageAndChildren');
    var watchPageSwitch  = $('#notificationPageOnly');
    var allWatchSwitches = $([watchWikiSwitch, watchSpaceSwitch, watchPageSwitch]);

    ///
    /// Set the icon corresponding to each switch
    ///
    watchPageSwitch.bootstrapSwitch('labelText', '<span class=\"fa fa-file\"><\/span>');
    watchSpaceSwitch.bootstrapSwitch('labelText', '<span class=\"fa fa-sitemap\"><\/span>');
    watchWikiSwitch.bootstrapSwitch('labelText', '<span class=\"fa fa-globe\"><\/span>');

    ///
    /// Disabled switches if there is no enabled notification preferences
    ///
    if ($('.notifications-toggles').attr('data-enabled') == 'false') {
      allWatchSwitches.bootstrapSwitch('disabled', true);

      $('.notifications-toggles').tooltip({
        title: 'You need to enable notifications in your settings if you wish to watch these locations',
        placement: 'bottom'
      });
    } else {
      ///
      /// Add a tooltip to each switch
      ///
      $('.bootstrap-switch-id-notificationPageOnly').tooltip({
        title: 'Watch this page',
        placement: 'bottom'
      });
      var watchSpaceToolTip = 'Watch this page and its children';
      if (xm.documentReference.getName() != 'WebHome') {
        // Adapt the tooltip when the current document is terminal
        watchSpaceToolTip = 'Watch the parent page and its children';
      }
      $('.bootstrap-switch-id-notificationPageAndChildren').tooltip({
        title: watchSpaceToolTip,
        placement: 'bottom'
      });
      $('.bootstrap-switch-id-notificationWiki').tooltip({
        title: 'Watch this wiki',
        placement: 'bottom'
      });
    }
    
    allWatchSwitches.bootstrapSwitch('size', 'small');

    /**
     * Change the watchlist status of a document/space/wiki.
     */
    var changeWatchListStatus = function (action, location, type) {
      // Disable the toggles so that the user cannot click on them during the request
      allWatchSwitches.bootstrapSwitch('disabled', true);
      /**
       * URL to the service that return the notifications to display
       */
      var url = new XWiki.Document(XWiki.Model.resolve('XWiki.Notifications.Code.NotificationsDisplayerUIX', XWiki.EntityType.DOCUMENT)).getURL('get', 'outputSyntax=plain');
      $.post(url, {
        'action': action,
        'location': location,
        'type': type,
        'currentDoc': XWiki.Model.serialize(xm.documentReference)
      }).done(function (data) {
        // Unfortunately, bootstrap switch does not allow to change the state if it is disabled
        allWatchSwitches.bootstrapSwitch('disabled', false);
        // Update states
        watchPageSwitch.bootstrapSwitch('state', data.document, true);
        watchSpaceSwitch.bootstrapSwitch('state', data.space, true);
        watchWikiSwitch.bootstrapSwitch('state', data.wiki, true);
      }).fail (function() {
        new XWiki.widgets.Notification('Failed to change the status of the WatchList.', 'error');
        allWatchSwitches.bootstrapSwitch('disabled', false);
      });
    };

    ///
    /// Change the watchlist status when the switched are manipulated by the user
    ///
    watchPageSwitch.bootstrapSwitch('onSwitchChange', function (event, status) {
      changeWatchListStatus(status ? 'watchLocation' : 'unwatchLocation', XWiki.Model.serialize(xm.documentReference), 'document');
    });
    watchSpaceSwitch.bootstrapSwitch('onSwitchChange', function (event, status) {
      changeWatchListStatus(status ? 'watchLocation' : 'unwatchLocation', XWiki.Model.serialize(xm.documentReference.extractReference(XWiki.EntityType.SPACE)), 'space');
    });
    watchWikiSwitch.bootstrapSwitch('onSwitchChange', function (event, status) {
      changeWatchListStatus(status ? 'watchLocation' : 'unwatchLocation', XWiki.Model.serialize(xm.documentReference.extractReference(XWiki.EntityType.WIKI)), 'wiki');
    });

    ///
    /// Avoid the dropdown menu to be closed when the user click on the bootstrap switch
    ///
    $('.notifications-toggles .bootstrap-switch').click(function(event) {
      event.stopImmediatePropagation();
    });
 });
});

