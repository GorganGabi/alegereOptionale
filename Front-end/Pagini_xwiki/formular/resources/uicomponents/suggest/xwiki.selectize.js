require.config({paths:{selectize:"../../../webjars/selectize.js/0.12.4/js/standalone/selectize.min"},shim:{selectize:["jquery"]}});define("xwiki-selectize",["jquery","selectize","xwiki-events-bridge"],function(c){var h=['<div class="xwiki-selectize-option" data-value="">','<span class="xwiki-selectize-option-icon" />','<span class="xwiki-selectize-option-label" />',"</div>"].join("");var a=function(n){var i=c(h);var o=(n&&typeof n==="object")?n.value:n;i.attr("data-value",o);var m=n&&n.icon;if(typeof m==="string"){if(m.indexOf("/")>=0||m.indexOf(".")>=0){var p=c('<img class="xwiki-selectize-option-icon" alt="" />').attr("src",m);i.find(".xwiki-selectize-option-icon").replaceWith(p)}else{i.find(".xwiki-selectize-option-icon").addClass(m)}}else{i.find(".xwiki-selectize-option-icon").remove()}var l=n&&n.url;if(typeof l==="string"){var k=c('<a class="xwiki-selectize-option-label" />').attr("href",l).click(function(q){q.preventDefault()});i.find(".xwiki-selectize-option-label").replaceWith(k)}var j=(n&&typeof n==="object")?(n.label||n.value):n;i.find(".xwiki-selectize-option-label").text(j);return i};var b={copyClassesToDropdown:false,dropdownParent:"body",highlight:false,labelField:"label",loadThrottle:500,persist:false,preload:"focus",render:{item:a,option:a,option_create:function(j,k){var l="Select {0} ...";var i=c('<div class="create"/>').html(k(l).replace("{0}","<em/>"));i.find("em").text(j.input);return i}},searchField:["value","label"]};var g=function(){var i=c(this).val();if(!c.isArray(i)){i=[i]}var j=this.selectize;var k=j.$wrapper;k.addClass(j.settings.loadingClass);j.loading++;i.reduce(function(l,m){return l.then(function(){return f(j,m)})},c.Deferred().resolve()).always(function(){j.loading=Math.max(j.loading-1,0);if(!j.loading){k.removeClass(j.settings.loadingClass)}})};var f=function(j,k){var i=c.Deferred();if(k&&typeof j.settings.load==="function"){j.settings.load(k,function(l){c.isArray(l)&&l.forEach(function(m){var n=m[j.settings.valueField];if(j.options.hasOwnProperty(n)){j.updateOption(n,m)}else{j.addOption(m)}});i.resolve()})}else{i.resolve()}return i.promise()};var e=function(){if(d(this)==="auto"){var i=this.selectize.positionDropdown;this.selectize.positionDropdown=function(){i.call(this);var j=this.$dropdown;j.css({width:"","min-width":j.css("width")})}}};var d=function(i){if(i.selectize.settings.hasOwnProperty("dropdownWidth")){return this.selectize.settings.dropdownWidth}else{if(c(i).closest(".xform").length===0){return"auto"}}};c.fn.xwikiSelectize=function(i){return this.selectize(c.extend({},b,i)).each(g).each(e).on("change",function(j){var k=c(this).closest(".xwiki-livetable-display-header-filter").closest(".xwiki-livetable").attr("id");k&&c(document).trigger("xwiki:livetable:"+k+":filtersChanged")})}});