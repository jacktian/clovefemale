
$(function(){
	// console.log("你点击了list_children");
	mui.init({
	  gestureConfig:{
	   tap: true, //默认为true
	   // doubletap: true, //默认为false
	   longtap: true, //默认为false
	   // swipe: true, //默认为true
	   // drag: true, //默认为true
	   // hold:false,//默认为false，不监听
	   // release:false//默认为false，不监听
	  }
	});

	
    //孩子列表
	document.getElementById("list_children").addEventListener("tap",function(event){
 		mui('.babylist').popover('toggle');
	});

	document.getElementById("slider").addEventListener("swipeleft",function(){
     	scroll(0,0);
	});

	document.getElementById("slider").addEventListener("swiperight",function(){
     	scroll(0,0);
	});


	var ctx = document.getElementById("bodySimChart").getContext("2d");
	$("#bodySimChart").attr("width", $(window).get(0).innerWidth*0.95);
	var options = {};
	var data = {
		labels : ["0","1","2","3","4","5","6","7","8","9","10","11","1岁"],
		datasets : [
			{
				fillColor : "rgba(151,187,205,0.5)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				data : [50.4,54.8,58.7,62,64.6,66.7,68.4,69.8,71.2,72.6,74,75.3,79]
			}
			,
			{
				fillColor : "rgba(255,153,153,0.3)",
				strokeColor : "rgba(255,153,153,1)",
				pointColor : "rgba(255,153,153,1)",
				pointStrokeColor : "#fff",
				data : [48.4,51.2,56.7,61.8,64.6,66.9,68.9,70.8,72.5,74.6,77,80,84]
			}
		]
	}
	var myNewChart = new Chart(ctx).Line(data,options);
 	 

	var item2Show = false,item3Show = false;//子选项卡是否显示标志
	var activeTab_grow = 0; //当前tab选项卡为第一个
 	document.querySelector('.babySlider').addEventListener('slide', function(event) {
 		activeTab_grow = event.detail.slideNumber;//当前活跃tab
		if (event.detail.slideNumber === 1&&!item2Show) {
		    //切换到第二个选项卡
		    //根据具体业务，动态获得第二个选项卡内容；
		   // var content = ....
		    //显示内容
		   // document.getElementById("item2").innerHTML = content;
		    //改变标志位，下次直接显示
			$("#item2mobile>.mui-loading").hide();//隐藏加载
		    item2Show = true;
		    

		    // 成绩表单
			document.getElementById("subjectBox").addEventListener("tap",changeSubject);
			document.getElementById("subjectBox").addEventListener("longtap",changeSubject);

			var ctx_mark = document.getElementById("markSimChart").getContext("2d");
			$("#markSimChart").attr("width", $(window).get(0).innerWidth*0.95);
			var markOptions = {};
			var markData = {
				labels : ["0","1","2","3","4","5","6","7","8","9","10","11"],
				datasets : [
					{
						fillColor : "rgba(151,187,205,0.5)",
						strokeColor : "rgba(151,187,205,1)",
						pointColor : "rgba(151,187,205,1)",
						pointStrokeColor : "#fff",
						data : [65,73,76,79,82,80,85,80,71.2,72.6,74,75.3]
					}
				]
			}
			var markChart = new Chart(ctx_mark).Line(markData,markOptions);

		} else if (event.detail.slideNumber === 2&&!item3Show) {
		    //切换到第三个选项卡
		    //根据具体业务，动态获得第三个选项卡内容；
		   // var content = ....
		    //显示内容
		   // document.getElementById("item3").innerHTML = content;
		    //改变标志位，下次直接显示
			$("#item3mobile>.mui-loading").hide();//隐藏加载
			item3Show = true;
		}
	});

	

    $('#scroller').mobiscroll().scroller({
        theme: 'mobiscroll',
        lang: 'zh',
        display: 'inline',
        rows:3,
        multiline:2,
        // showLabel:true,
        layout:'liquid',
        height:40,
         wheels: [[
                {
                	label:'岁',
                    // keys: [3, 4, 5, 6],
                    values: ["0","1","2","3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"]
                },
                {
                	label:'月',
                    // keys: [3, 4, 5, 6],
                    values: ["0","1","2","3", "4", "5", "6", "7", "8", "9", "10", "11"]
                }
            ]]
    });

    $('#scroller_mark').mobiscroll().scroller({
        theme: 'mobiscroll',
        lang: 'zh',
        display: 'inline',
        rows:3,
        // multiline:3,
        showLabel:true,
        layout:'liquid',
        height:40,
        wheels: [[
                {
                	label:'年级',
                    // keys: [3, 4, 5, 6],
                    values: ["小班","中班","大班","一年级", "二年级", "三年级", "四年级", "五年级", "六年级", "初一", "初二", "初三", "高一", "高二", "高三"]
                },
                {
                	label:'科目',
                    // keys: [3, 4, 5, 6],
                    values: ["语文","数学","英语","美术", "音乐", "政治", "物理", "化学", "生物", "地理", "历史"]
                },
                {
                	label:'次数',
                    // keys: [3, 4, 5, 6],
                    values: ["0","1","2","3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"]
                }
            ]]
    });
    
    
	//添加身体指标
	document.getElementById("add_babygrow").addEventListener("tap",function(event){
 		switch(activeTab_grow){
 			case 0 : mui('.newbodyIndex').popover('toggle'); return false;break;
 			case 1 : mui('.newmark').popover('toggle');return false; break;
 			case 2 : return true;break;
 			default:break;
 		}
 		
	});

});

var changeSubject = function(event){
 		
 		// 切换科目箭头状态
		$('#subjectArrow').removeClass('mui-icon-arrowdown');
	 	$('#subjectArrow').addClass('mui-icon-arrowup');

	    mui('.subjectlist').popover('toggle');//切换蒙版状态

	    //点击蒙版时，如果科目选择箭头向上，则改为向下
	 	document.querySelector('.mui-backdrop').addEventListener("tap",function(event){
	 	 	if($('#subjectArrow').hasClass('mui-icon-arrowup')){
				$('#subjectArrow').removeClass('mui-icon-arrowup');
			 	$('#subjectArrow').addClass('mui-icon-arrowdown');
		 	}
	 	});
	}