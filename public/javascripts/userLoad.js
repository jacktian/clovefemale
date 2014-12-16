$(function(){
	
	/*数据渲染*/
	var successCB = function(data){
		var html = juicer($('#userTpl').html(),{userList:data.data});
		$('.userItem').remove();
		$('.tableHeader').after(html);
	}
	
	/*加载数据*/
	loadUsersByPage = function(curpage){
		$.get('http://localhost:9000/useraction/listusers?curpage='+curpage, function(data) {
	        successCB(data);
	    });
	}
	
	$(this).bind("loadUsersByPage",loadUsersByPage);
	$(this).trigger('loadUsersByPage');
});