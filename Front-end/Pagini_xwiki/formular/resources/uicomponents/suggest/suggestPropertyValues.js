require.config({paths:{"xwiki-selectize":"../../../../../../resources/uicomponents/suggest/xwiki.selectize.js?v=10.3"}});require(["jquery","xwiki-selectize"],function(b){var a=function(d){var e=["/xwiki","rest","wikis",encodeURIComponent(XWiki.currentWiki),"classes",encodeURIComponent(d.attr("data-className")),"properties",encodeURIComponent(d.attr("data-propertyName")),"values"].join("/");return{create:true,load:function(f,g){b.getJSON(e,{fp:f,limit:10}).then(function(h){if(h&&b.isArray(h.propertyValues)){return h.propertyValues.map(c)}else{return[]}}).done(g).fail(g)}}};var c=function(e){var d=e.metaData||{};return{value:e.value,label:d.label,icon:d.icon,url:d.url}};b.fn.suggestPropertyValues=function(){return this.each(function(){b(this).xwikiSelectize(a(b(this)))})};b(".suggest-propertyValues").suggestPropertyValues()});