require(["jquery","xwiki-events-bridge"],function(a){a(document).ready(function(){var b=function(e){e.preventDefault();var d=a(this).parent("li");d.addClass("loading");var g=a(this).parents(".breadcrumb-expandable");var f=new XWiki.Document(XWiki.Model.resolve(g.data("entity"),XWiki.EntityType.DOCUMENT)).getURL("get","xpage=hierarchy_reference");a.ajax(f,{data:{id:g[0].id,displayTitle:g.data("displaytitle"),local:g.data("local"),excludeSelf:g.data("excludeself"),treeNavigation:g.data("treenavigation")}}).done(function(i){var h=a(i);g.replaceWith(h);a(document).trigger("xwiki:dom:updated",{elements:h.toArray()})}).fail(function(){new XWiki.widgets.Notification("Failed to get the full hierarchy.","error");d.removeClass("loading")})};var c=function(){a(".breadcrumb-expandable .ellipsis").each(function(){var d=a(this);if(!d.children().first().is("a")){d.wrapInner(function(){return a('<a href="#"></a>').click(b)})}})};c();a(document).on("xwiki:livetable:displayComplete",c)})});require(["../../../webjars/xwiki-platform-tree-webjar/10.3/require-config.min.js"],function(){require(["tree","bootstrap"],function(b){var a=function(c){c.children("li.dropdown").on("shown.bs.dropdown",function(d){b(this).find(".dropdown-menu > .breadcrumb-tree").each(function(){if(!b.jstree.reference(b(this))){b(this).xtree().one("ready.jstree",function(g,h){var e=h.instance;var f=e.element.attr("data-openTo");f&&e.openTo(f)})}})}).children(".dropdown-menu").click(function(d){d.stopPropagation()})};b(document).on("xwiki:dom:updated",function(c,e){var d=b(e.elements);d.is(".breadcrumb")&&a(d)});a(b("ol.breadcrumb"))})});