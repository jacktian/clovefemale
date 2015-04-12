$(function(){
	//设置日期图标中的日期号码
	$('.dateNo').text(new Date().getDate());

	//自定义疫苗蒙版
	document.getElementById("vaccineInput").addEventListener("tap",function(event){
 		mui('.vacInputBox').popover('toggle');
	});
	
	//日期控件触发
	$(".dateBox_vac").mobiscroll().calendar({
			theme:'ios',
			lang:'zh',
			display:'bottom'
	});

	//未接种疫苗tap事件
	document.getElementById("todoVacLink").addEventListener("tap",function(event){
		$(".vacListName").text("未接种疫苗");
 		$(".vacList").removeClass("activeVacList");
 		$("#todoVac").addClass("activeVacList");
	});

	//已接种疫苗tap事件
	document.getElementById("doneVacLink").addEventListener("tap",function(event){
		$(".vacListName").text("已接种疫苗");
 		$(".vacList").removeClass("activeVacList");
 		$("#doneVac").addClass("activeVacList");
	});

	//全部疫苗tap事件
	document.getElementById("allVacLink").addEventListener("tap",function(event){
		$(".vacListName").text("全部疫苗");
 		$(".vacList").removeClass("activeVacList");
 		$("#allVac").addClass("activeVacList");
	});
});