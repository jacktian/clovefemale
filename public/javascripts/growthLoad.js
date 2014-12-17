$(function(){
	/*数据渲染*/
	var successCB = function(data){
		var html = juicer($('#babyTpl').html(),{babyList:data.data});
		$('.babyItem').remove();
		$('.tableHeader').after(html);
	}
	
	/*加载数据*/
	loadBabysByPage = function(curpage){
		$.get('http://localhost:9000/babyaction/listBabyBeans?curpage='+curpage, function(data) {
	        successCB(data);
	    });
	}
	
	$(".topNav li").removeClass("active");
	$(".topNav li").eq(1).addClass("active");
	$(this).bind("loadBabysByPage",loadBabysByPage);
	$(this).trigger('loadBabysByPage');
});