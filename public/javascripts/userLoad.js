$(function(){
	
	/*数据渲染*/
	var successCB = function(data){
		var html = juicer($('#userTpl').html(),{userList:data.data});
		$('.userItem').remove();
		$('.tableHeader').after(html);
	}
	
	/*加载数据*/
	loadUsersByPage = function(curpage){
		$.get(localhost+'/useraction/listusers?curpage='+curpage, function(data) {
	        successCB(data);
	    });
	}
	
	$(".topNav li").removeClass("active");
	$(".topNav li").eq(0).addClass("active");
	$(this).bind("loadUsersByPage",loadUsersByPage);
	$(this).trigger('loadUsersByPage');
});