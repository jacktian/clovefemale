var babyAge = ["初生儿","1个月","2个月","3个月", "4个月", "5个月", "6个月", "7个月", "8个月", "9个月", "10个月", "11个月", "1岁", "1岁半", "2岁", "2岁半", "3岁", "3岁半", "4岁", "4岁半",  "5岁", "5岁半", "6岁", "6岁半", "7岁", "8岁","9岁","10岁","11岁","12岁","13岁","14岁","15岁", "16岁", "17岁", "18岁"];
var standerH_male = [50.4,54.8,58.7,62,64.6,66.7,68.4,69.8,71.2,72.6,74,75.3,76.5,82.7,88.5,93.3,97.5,100.6,104.1,107.7,111.3,114.7,117.7,120.7,124,130,135.4,140.2,145.3,151.9,159.5,165.9,169.8,171.6,172.3,172.7];
var standerH_female =[49.7,53.7,57.4,60.6,63.1,65.2,66.8,68.2,69.6,71,72.4,73.7,75,81.5,87.2,92.1,96.3,99.4,103.1,106.7,110.2,113.5,116.6,119.4, 122.5,128.5,134.1,140.1,146.6,152.4,156.3,158.6,159.8,160.1,160.3,160.6];
var standerW_male = [3.32,4.51,5.68,6.7,7.45,8,8.41,8.76,9.05,9.33,9.58,9.83,10.05,11.29,12.54,13.64,14.65,15.63,16.64,17.75,18.98,20.18,21.26,22.45,24.06,27.33,30.46,33.74,37.69,42.49,48.08,53.37,57.08,59.35,60.68,61.40];
var standerW_female = [3.21,4.2,5.21,6.13,6.83,7.36,7.77,8.11,8.41,8.69,8.94,9.18,9.4,10.65,11.92,13.05,14.13,15.16,16.17,17.22,18.26,19.33,20.37,21.44,22.64,25.25,18.19,31.76,36.10,40.77,44.79,47.83,49.82,50.81,51.20,51.41];
var activeTab_grow = 0; //当前tab选项卡为第一个
var item1Show = false,item2Show = false,item3Show = false;//子选项卡是否显示标志
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


	//文本框只能输入数字，并屏蔽输入法和粘贴  
        $.fn.numeral = function() {     
            $(this).css("ime-mode", "disabled");     
            this.bind("keypress",function(e) {     
                var code = (e.keyCode ? e.keyCode : e.which);  //兼容火狐 IE      
                     //火狐下不能使用退格键   
                 //    if(!$.browser.msie&&(e.keyCode==0x8))   
                    // {     
                 //         return ;     
                 //    }     
                 //    return code >= 48 && code<= 57;     
            });  

            this.bind("blur", function() {     
                if (this.value.lastIndexOf(".") == (this.value.length - 1)) {     
                    this.value = this.value.substr(0, this.value.length - 1);     
                } else if (isNaN(this.value)) {     
                    this.value = "";     
                }     
            });     

            this.bind("paste", function(e) {    
                // alert(this.value); 
                 this.value=this.value.replace(/[^1-9]{1}[^0-9]*/g,"");
                // var s = window.clipboardData.getData('text/plain') 

                // if (!/\D/.test(s));     
                // value = s.replace(/^0*/, '');     
                // return false;     
            });     

            this.bind("dragenter", function() {     
                return false;     
            });     

            this.bind("keyup", function(e) {  
                
                // var char_code = e.charCode ? e.charCode : e.keyCode;  
                // console.log(this.value);
                // if (!/^[1-9][0-9]*\.?$/.test(this.value)) {     
                //     this.value=this.value.replace(/[^1-9]{1}[^0-9]*/g,"");
                //     console.log("false");
                // }     
                // var remainder = 11;
                // remainder = 11 - this.value.length;
                // $("#numRemainder").text(remainder);
                
            });     

            this.bind("keydown", function(e) {  
                var char_code = e.charCode ? e.charCode : e.keyCode;
                console.log(char_code); 
                if(char_code == 8 || char_code == 190){
                    return true;
                }else if(char_code<48 || char_code >57){      
                    return false;  
                }else{  
                    return true;    
                }   
                
            });  


        }; //结束文本框限定输入

    $("#baby_height").numeral();
    $("#baby_weight").numeral();
    $("#baby_mark").numeral();

	// ctx = document.getElementById("bodySimChart").getContext("2d");
	// $("#bodySimChart").attr("width", $(window).get(0).innerWidth*0.95);
	// var babyAge = ["初生儿","1个月","2个月","3个月", "4个月", "5个月", "6个月", "7个月", "8个月", "9个月", "10个月", "11个月", "1岁", "1岁半", "2岁", "2岁半", "3岁", "3岁半", "4岁", "4岁半",  "5岁", "5岁半", "6岁", "6岁半", "7岁", "8岁","9岁","10岁","11岁","12岁","13岁","14岁","15岁", "16岁", "17岁", "18岁", "19岁", "20岁", "21岁", "22岁"];
	// var standerH_male = [50.4,54.8,58.7,62,64.6,66.7,68.4,69.8,71.2,72.6,74,75.3,76.5,82.7,88.5,93.3,97.5,100.6,104.1,107.7,111.3,114.7,117.7,120.7,124,130,135.4,140.2,145.3,151.9,159.5,165.9,169.8,171.6,172.3,172.7];
	/*加载宝宝信息*/
	$.post("/CBabyAction/loadBabyInf",{
		babyId:localStorage.babyId//"1BFB8CDDECC24BE49F8D3C5B9528BBB0"//angela的babyId//babyId
	},function(data){
		if(data.result==0){//成功
			localStorage.name= data.data.name;
			$("#title-babyGrowth").text(data.data.name+"的成长记录");
			localStorage.babyId = data.data.id;
			localStorage.sex = data.data.sex;
			// alert(localStorage.babyId);
			loadHistoryData();
		}
	});

	/*加载宝宝列表*/
	loadBabyList();


	
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


	/*科目选择确定按钮tap事件*/
	document.getElementById("subject-confirm-btn").addEventListener("tap",function(event){
 		console.log("确定科目按钮");
 		var value = $('#scroller_subject').mobiscroll("getVal",true);
 		console.log(value);
 		loadGradeFormData(value);
 		$("#subject").text(value);
 		mui('.chooseSubject').popover('toggle');
 		if($('#subjectArrow').hasClass('mui-icon-arrowup')){
				$('#subjectArrow').removeClass('mui-icon-arrowup');
			 	$('#subjectArrow').addClass('mui-icon-arrowdown');
		 	}
	});

	/*科目选择取消按钮tap事件*/
	document.getElementById("subject-cancel-btn").addEventListener("tap",function(event){
 		mui('.chooseSubject').popover('toggle');
 		if($('#subjectArrow').hasClass('mui-icon-arrowup')){
				$('#subjectArrow').removeClass('mui-icon-arrowup');
			 	$('#subjectArrow').addClass('mui-icon-arrowdown');
		 	}
	});


 	 

	
	
 	document.querySelector('.babySlider').addEventListener('slide', function(event) {
 		activeTab_grow = event.detail.slideNumber;//当前活跃tab
 		if (event.detail.slideNumber === 0&&!item1Show){
 			loadHistoryData();
 		}
		else if (event.detail.slideNumber === 1&&!item2Show) {
		    //切换到第二个选项卡
		    //根据具体业务，动态获得第二个选项卡内容；
		   // var content = ....
		    //显示内容
		   // document.getElementById("item2").innerHTML = content;
		    //改变标志位，下次直接显示
			
		    // item2Show = true;
		    localStorage.gradeFormList = null;
		    // 成绩表单
			document.getElementById("subjectBox").addEventListener("tap",changeSubject);
			// document.getElementById("subjectBox").addEventListener("longtap",changeSubject);
			loadGradeFormData("");

		} else if (event.detail.slideNumber === 2&&!item3Show) {
		    //切换到第三个选项卡
		    //根据具体业务，动态获得第三个选项卡内容；
		   // var content = ....
		    //显示内容
		   // document.getElementById("item3").innerHTML = content;
		    //改变标志位，下次直接显示
			// $("#item3mobile>.mui-loading").hide();//隐藏加载
			// item3Show = true;
			$(".vaccine-Content").show();
			loadNextVac();
			loadVaccinated();
		}
	});

	
	/*身体指标添加滚轮*/
    $('#scroller').mobiscroll().scroller({
        theme: 'mobiscroll',
        lang: 'zh',
        display: 'inline',
        rows:3,
        multiline:1,
        // showLabel:true,
        layout:'liquid',
        height:40,
         wheels: [[
                {
                	label:'岁',
                	name:'years',
                    keys: [0,0.01,0.02,0.03, 0.04, 0.05, 0.06,0.07,0.08,0.09,0.10,0.11,1,1.5,2,2.5,3,3.5, 4,4.5, 5, 5.5,6,6.5,7,8,9,10,11,12,13,14,15,16,17,18],
                    values: ["初生儿","1个月","2个月","3个月", "4个月", "5个月", "6个月", "7个月", "8个月", "9个月", "10个月", "11个月", "1岁", "1岁半", "2岁", "2岁半", "3岁", "3岁半", "4岁", "4岁半",  "5岁", "5岁半", "6岁", "6岁半", "7岁", "8岁","9岁","10岁","11岁","12岁","13岁","14岁","15岁", "16岁", "17岁", "18岁"]
                }
            ]],
        onChange:function(valueText,inst){
        	console.log("change");
        	calBabyAge(valueText);
        	var age = parseFloat(valueText);
		 	loadBodyIndex_age(age);
        },
        onShow:function(html,valueText,inst){
        	// console.log(valueText);
        	// loadBodyIndex_age(0);
        }
    });



    /*成绩表单添加滚轮*/
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
                    // keys: [3, 4, 5, 6],"小班","中班","大班",
                    values: ["一年级", "二年级", "三年级", "四年级", "五年级", "六年级", "初一", "初二", "初三", "高一", "高二", "高三"]
                },
                {
                	label:'科目',
                    // keys: [3, 4, 5, 6],
                    values: ["语文","数学","英语","美术", "音乐", "政治", "物理", "化学", "生物", "地理", "历史"]
                }
                // ,
                // {
                // 	label:'次数',
                //     values: ["1","2","3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"]
                // }
            ]],
        onChange:function(valueText,inst){
        	var value = valueText.split(" ");
        	localStorage.grade = value[0];
        	localStorage.subject = value[1];
        	// localStorage.time = value[2];
        	loadMark();
        }
    });

	//日期控件触发
	// $("#gradeDate_input").mobiscroll().date({
	// 		theme:'ios',
	// 		lang:'zh',
	// 		display:'bottom',
	// 		onSetDate:function(day,inst){
	// 						localStorage.date = day.date.getFullYear()+"-"+(day.date.getMonth()+1)+"-"+day.date.getDate(); 
	// 					}
	// });

		$('#gradeDate_input').mobiscroll().date({
            theme: 'mobiscroll',
            display: 'bottom',
            lang: 'zh',
            endYear:new Date().getFullYear(),
            maxDate:new Date(),
            dateFormat:'yy-mm-dd',
            onSelect: function(valueText, inst) {
            	 var selectedDate = inst.getVal(); 
                 var date = $.mobiscroll.datetime.formatDate('yy-mm-dd', selectedDate);
            //      console.log(date);
            	localStorage.date = date;
				loadMark();

            }
        });

 /*科目选择滚轮*/
    $('#scroller_subject').mobiscroll().scroller({
        theme: 'mobiscroll',
        lang: 'zh',
        display: 'inline',
        rows:3,
        layout:'liquid',
        height:40,
        wheels: [[
                
                {
                	// label:'科目',
                    // keys: [3, 4, 5, 6],
                    values: ["语文","数学","英语","美术", "音乐", "政治", "物理", "化学", "生物", "地理", "历史"]
                }
            ]]
    });
    
    
	//成长记录添加按钮tap事件
	document.getElementById("add_babygrow").addEventListener("tap",function(event){
		console.log(activeTab_grow+"页tab");
 		switch(activeTab_grow){
 			case 0 : mui('.newbodyIndex').popover('toggle'); clearBiAddBox(); return false;break;
 			case 1 : mui('.newmark').popover('toggle');clearNewGradeFormBox();return false; break;
 			case 2 : return true;break;
 			default:break;
 		}
 		
	});

	//身体指标弹出框确定按钮tap事件
	document.getElementById("abi-confirm-btn").addEventListener("tap",function(event){
 		var bodyIndexId = $("#bodyIndexId").text();;
 		var height = $("#baby_height").val();
 		var weight = $("#baby_weight").val();
 		calBabyAge($('#scroller').mobiscroll("getVal",true));
 		var age = localStorage.age;
 		var ageDcb = localStorage.ageDcb;

 		if(height==""||height==null){
 			$(".nbi-Tips").text("身高不能为空哦!");
 			$(".nbi-Tips").show();
 			return false;
 		}

 		if(weight==""||weight==null){
 			$(".nbi-Tips").text("体重不能为空哦!");
 			$(".nbi-Tips").show();
 			return false;
 		}

 		var tips = checkHeight(height);
 		if(tips != ""){
 			$(".nbi-Tips").text(tips);
 			$(".nbi-Tips").show();
 			return false;
 		}

 		tips = checkWeight(weight);
 		if(tips != ""){
 			$(".nbi-Tips").text(tips);
 			$(".nbi-Tips").show();
 			return false;
 		}

 		//var babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";//angela的babyId
 		$.post("/CBabyAction/addOrMdfBabyIndex",{
 			bodyIndexId:bodyIndexId,
 			babyId:localStorage.babyId,
 			age:age,
 			ageDcb:ageDcb,
 			height:height,
 			weight:weight
 		},function(data){//显示操作结果
 			var resultText;
			mui('.newbodyIndex').popover('toggle');	
			if(data.result==0){//成功
				// showHtRecord(data.data);
				// showBodyIndexChart(data.data);
				if(bodyIndexId==""||bodyIndexId==null){
					resultText = "添加记录成功";
				}else{
					resultText = "修改记录成功";
				}
				$(".bodyIndex-content").show();
				$("#nullTips-bi").hide();
				showHtRecord(data.data);//展示历史记录	
				showBodyIndexChart(data.data);//展示动态图表
			}else{//失败
				resultText = data.data;
			}
			showResTips(resultText);

 		})
	});

	//身体指标弹出框取消按钮tap事件
	document.getElementById("abi-cancel-btn").addEventListener("tap",function(event){
 		mui('.newbodyIndex').popover('toggle');	
	});

	//成绩表单弹出框确定按钮tap事件
	document.getElementById("amark-confirm-btn").addEventListener("tap",function(event){
 		addOrMdfMark();
	});

	//成绩表单弹出框取消按钮tap事件
	document.getElementById("amark-cancel-btn").addEventListener("tap",function(event){
 		mui('.newmark').popover('toggle');	
	});
});

/*加载用户宝宝类表*/
function loadBabyList(){
	$.post("/CBabyAction/loadBabyList",function(data){
		if(data.result == 0){
			var length = data.data.length;
			for(var i = 0 ; i < length; i++){
				$("#babylist>ul").append("<li id="+ data.data[i].id+ " sex="+data.data[i].sex+" class='mui-table-view-cell'>"+data.data[i].name+"</li></div>");
				document.getElementById(data.data[i].id).addEventListener("tap",function(){
					localStorage.babyId = $(this).attr("id");
					localStorage.name = $(this).text();
					localStorage.sex = $(this).attr("sex");
					$("#title-babyGrowth").text($(this).text()+"的成长记录");
					mui(".babylist").popover('toggle');
					recoverTab();
					if(activeTab_grow == 0){
						loadHistoryData();
					}else if(activeTab_grow == 1){
						loadGradeFormData();
					}else{
						$(".vaccine-Content").show();
						loadNextVac();
						loadVaccinated();
					}
				});
			}

		}
	});
}

var changeSubject = function(event){
 		
 		// 切换科目箭头状态
		$('#subjectArrow').removeClass('mui-icon-arrowdown');
	 	$('#subjectArrow').addClass('mui-icon-arrowup');

	    mui('.chooseSubject').popover('toggle');//切换蒙版状态

	    //点击蒙版时，如果科目选择箭头向上，则改为向下
	 	document.querySelector('.mui-backdrop').addEventListener("tap",function(event){
	 	 	if($('#subjectArrow').hasClass('mui-icon-arrowup')){
				$('#subjectArrow').removeClass('mui-icon-arrowup');
			 	$('#subjectArrow').addClass('mui-icon-arrowdown');
		 	}
	 	});
	}

/*展示操作信息*/
function showResTips(result){
		$(".response-tips").text(result);
		$(".response-tips").show();
		window.setTimeout("hideTips()",1500);
}

/*隐藏信息提示，并清空*/
function hideTips(){
		$(".response-tips").text("");
		$(".response-tips").hide();
}

/*加载宝宝历史数据*/
function loadHistoryData(){
		// alert(localStorage.babyId);
		item1Show = true;
		$("#nullTips").hide();
		$("#bodyIndex-content").hide();
		$.post("/CBabyAction/loadBabyGrowth",{
			babyId:localStorage.babyId//angela的babyId//babyId
		},function(data){
			$(".loading-bi").hide();
			if(data.result == 0){//成功
				
				$(".bodyIndex-content").show();
				$("#nullTips-bi").hide();
				showHtRecord(data.data);//展示历史记录	
				showBodyIndexChart(data.data);//展示动态图表
				localStorage.maxAge = data.data[0].age;

			}else{//失败
				if(data.data == "没有记录"){
					$("#nullTips-bi").html("找不到宝宝的身体指标哦,<br/>赶紧去添加吧！");
					$("#nullTips-bi").show();
				}else{
					$("#nullTips-bi").html(data.data);
					$("#nullTips-bi").show();
				}
			}
		});
};

/*展示最近两条记录*/
function showHtRecord(bodyIndexList){
		$(".data-record-bi").html("");
		if(bodyIndexList.length>0){
			$("#nullTips-bi").hide();
			$("#lastData-bi-1").html("<td>" + bodyIndexList[0].height+ "</td>" + "<td>" + bodyIndexList[0].weight+ "</td>"+"<td>" + bodyIndexList[0].ageDcb+ "</td>");
		}
		if(bodyIndexList.length>1){
			$("#lastData-bi-2").html("<td>" + bodyIndexList[1].height+ "</td>" + "<td>" + bodyIndexList[1].weight+ "</td>"+"<td>" + bodyIndexList[1].ageDcb+ "</td>");
		}
		
}


/*展示身体指标图标动态*/
function showBodyIndexChart(bodyIndexList){
		console.log("showBodyIndexChart");
		var count = bodyIndexList.length;
		var maxAgeIndex = 0;

		var labels = new Array();
		var data_height = new Array();//宝宝身高数据
		var stander_height = new Array();//宝宝记录对应年龄的身高标准数据
		var starderH_all;//标准身高全部数据

		var data_weight = new Array();//宝宝体重数据
		var stander_weight = new Array();//宝宝记录对应年龄的体重标准数据
		var starderW_all;//标准身高全部数据

		if(localStorage.sex == "男"){
			starderH_all = standerH_male;
			starderW_all = standerW_male;
		}else{
			starderH_all = standerH_female;
			starderW_all = standerW_female;
		}

		var i = 0,vlen = babyAge.length;
			// console.log(data.data[count-1].ageDcb);
		for(var i = 0; i < count; i++){
			for(j=0 ; j < vlen; j++){ 
				
				if(bodyIndexList[count-1-i].ageDcb == babyAge[j]){ 
					stander_height[i] = starderH_all[j];
					stander_weight[i] = starderW_all[j];
					// console.log(stander_height[i]+"---"+j+"--"+babyAge[j]);
				}
			}
				
		}
		for(var i = 0;i<count ; i++){
			labels[i] = bodyIndexList[count-i-1].ageDcb;
			data_height[i] = bodyIndexList[count-i-1].height;
			data_weight[i] = bodyIndexList[count-i-1].weight;
			// stander_height[i] = standerH_male[maxAgeIndex+1-count+i];
		}

		if(count == 1){
			$(".oneData-height").show();
			$("#oneDataVal-age-h").text(bodyIndexList[0].ageDcb);
			$("#oneDataVal-baby-h").text(data_height[0]);
			$("#oneDataVal-stander-h").text(stander_height[0]);
			$("#oneDataVal-age-w").text(bodyIndexList[count-1].ageDcb);
			$("#oneDataVal-baby-w").text(data_weight[0]);
			$("#oneDataVal-stander-w").text(stander_weight[0]);
			$(".oneData-weight").show();
			$("#chartbox_bi_h").hide();
			$("#chartbox_bi_w").hide();
			showResTips("多添加数据，可以展示炫酷的图表哦！");
			return ;
		}

		$(".oneData-height").hide();
		$(".oneData-weight").hide();
		$("#chartbox_bi_h").show();
		$("#chartbox_bi_w").show();

		var options = {};
		var data = {
			labels : labels,
			datasets : [
				{
					fillColor : "rgba(151,187,205,0.5)",
					strokeColor : "rgba(151,187,205,1)",
					pointColor : "rgba(151,187,205,1)",
					pointStrokeColor : "#fff", 
					data : stander_height
				}
				,
				{
					fillColor : "rgba(255,153,153,0.3)",
					strokeColor : "rgba(255,153,153,1)",
					pointColor : "rgba(255,153,153,1)",
					pointStrokeColor : "#fff",
					data : data_height
				}
			]
		};

		var ctx_height = $("#bodySimChart").get(0).getContext("2d");//.getContext("2d");
			
		$("#bodySimChart").attr("width", $(window).get(0).innerWidth*0.95);
		$("#bodySimChart").attr("height", 150);
		var chart_h = new Chart(ctx_height).Line(data,options);

		//.getContext("2d");
			
		// data.datasets[0].data = stander_weight;
		// data.datasets[1].data = data_weight;
		var data_w = {
			labels : labels,
			datasets : [
				{
					fillColor : "rgba(151,187,205,0.5)",
					strokeColor : "rgba(151,187,205,1)",
					pointColor : "rgba(151,187,205,1)",
					pointStrokeColor : "#fff", 
					data : stander_weight
				}
				,
				{
					fillColor : "rgba(255,153,153,0.3)",
					strokeColor : "rgba(255,153,153,1)",
					pointColor : "rgba(255,153,153,1)",
					pointStrokeColor : "#fff",
					data : data_weight
				}
			]
		};
		console.log(stander_weight);
		console.log( data_weight);
		var ctx_weight = $("#bodyChart-weight").get(0).getContext("2d");
		$("#bodyChart-weight").attr("width", $(window).get(0).innerWidth*0.95);
		$("#bodyChart-weight").attr("height", 150);
		var chart_w = new Chart(ctx_weight).Line(data_w,options);
		// $("#chartbox_bi").html("");
};

/*计算宝宝年龄*/
function calBabyAge(valueText){
		var age = parseFloat(valueText);

        var ageDcb = "";
        console.log(valueText);
        if(age<1){
        	var month = parseInt(age*100);
        	if(month == 0){
        		ageDcb = "初生儿";
        	}else{
        		ageDcb = parseInt(age*100)+"个月";
        	}
        		
        		// $("#year_unit").text("月")
        }else{
        	var ceil  = Math.ceil(age);
        	var floor = Math.floor(age);
        	if(ceil > floor){
        		ageDcb = floor+"岁半";
        	}else{
        		ageDcb = floor+"岁";
        	}
        }
        localStorage.age = age;
        localStorage.ageDcb = ageDcb;
        $(".nbi-Tips").hide();
       
}

/*加载宝宝某个年龄的身体指标数据*/
var loadBodyIndex_age = function(age){
		// alert(localStorage.babyId);
		$.post("/CBabyAction/loadBodyIndex_age",{
			babyId:localStorage.babyId,
			age:age
		},function(data){
			if(data.result == 0){
				$("#baby_height").val(data.data.height);
				$("#baby_weight").val(data.data.weight);
				$("#bodyIndexId").text(data.data.id);
			}else{
				if(data.data=="null"){//没有该记录
					
				}else{
					showResTips(data.data);
				}
				$("#baby_height").val("");
				$("#baby_weight").val("");
				$("#bodyIndexId").text("");
				
			}
		});
};

/*清空身体指标添加输入框*/
function clearBiAddBox(){
	$("#baby_height").val("");
	$("#baby_weight").val("");
	var maxAge = localStorage.maxAge;
	console.log(maxAge);
	if(maxAge!=null){
		$('#scroller').mobiscroll('setVal', maxAge, true, true, false, 0);
		loadBodyIndex_age(maxAge);
	}
}

/*添加或修改成绩记录*/
function addOrMdfMark(){
	var value = $('#scroller_mark').mobiscroll("getVal",true).split(" ");
	console.log(value);
    localStorage.grade = value[0];
    localStorage.subject = value[1];
    // localStorage.time = value[2]; //去掉次数，改为日期
	var gradFormId = $("#gradeFormId").text();;
 	var mark = $("#baby_mark").val();
 	// var time = localStorage.time;
 	// var date = localStorage.date;
 	var subject = localStorage.subject;
 	var grade = localStorage.grade;
 	var babyId = localStorage.babyId;
 	var date = $("#gradeDate_input").val();
 	if(babyId==null || "" == babyId){
 		babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";//angela的babyId
 	}

 	if(mark==""||mark==null){
 		$(".ngf-Tips").text("分数不能为空哦!");
 		$(".ngf-Tips").show();
 		return false;
 	}

 	var tips = checkMark(mark);
 	if(tips!=""){
 		console.log("tips不为空"+tips+"---")
 		$(".ngf-Tips").text(tips);
 		$(".ngf-Tips").show();
 		return false;
 	}
 		
 	var gradeArray = new Array();

 	$.post("/CBabyAction/addOrMdfGradeForm",{
		gradeFormId:gradFormId,
		babyId:babyId,
		subject:subject,
		grade:grade,
		mark:mark,
		date:date
		// time:time//去掉次数，改为日期
	},function(data){
 		var resultText;
		mui('.newmark').popover('toggle');	
		if(data.result==0){//成功
			localStorage.grade_last = data.data[0].grade;
			localStorage.subject_last = data.data[0].subject;
			// localStorage.time_last = data.data[0].time;
			showHtRecord_mark(data.data);
			showGradeFormChart(data.data);
			$(".gradeFormDataBox").show();
			if(gradFormId==""||gradFormId==null){
				resultText = "添加记录成功";
			}else{
				resultText = "修改记录成功";
			}
		}else{//失败
			resultText = result.data;
		}
		showResTips(resultText);

 	});
}

/*加载成绩表单历史数据*/
function loadGradeFormData(subject){
	item2Show = true;
	$("#nullTips").hide();
	$.post("/CBabyAction/loadGradeForm",{
		babyId:localStorage.babyId,//angela的babyId//babyId
		subject:subject
	},function(data){
		$("#item2mobile>.mui-loading").hide();//隐藏加载
		if(data.result == 0){//成功
			$(".gradeFormDataBox").show();
			localStorage.grade_last = data.data[0].grade;
			localStorage.subject_last = data.data[0].subject;
			localStorage.time_last = data.data[0].time;
			$("#subject").text(data.data[0].subject);
			showHtRecord_mark(data.data);//展示历史记录	
			showGradeFormChart(data.data);//展示动态图表
		}else{//失败
			$(".gradeFormDataBox").hide();
				if(data.data == "没有记录"){
					$("#nullTips-gf").html("找不到成绩记录哦,<br/>赶紧去添加吧！");
					$("#nullTips-gf").show();
				}else{
					$("#nullTips-gf").html(data.data);
					$("#nullTips-gf").show();
				}
		}
	});
}
	

/*展示成绩表单的历史记录*/
function showHtRecord_mark(gradeFormList){
	console.log(gradeFormList);
	$(".data-record-gf").html("");
	if(gradeFormList.length>0){
			$("#nullTips-gf").hide();
			var date0 = new Date(gradeFormList[0].date);
			$("#lastData_mark_1").html("<td>" + gradeFormList[0].grade+ "</td>" + "<td>" + date0.getFullYear()+"-"+(date0.getMonth()+1) + "-"+ date0.getDate() + "</td>"+"<td>" + gradeFormList[0].mark+ "</td>");
		}
		if(gradeFormList.length>1){
			var date1 = new Date(gradeFormList[1].date);
			$("#lastData_mark_2").html("<td>" + gradeFormList[1].grade+ "</td>" + "<td>" + date1.getFullYear()+"-"+(date1.getMonth()+1) + "-"+ date1.getDate() + "</td>"+"<td>" + gradeFormList[1].mark+ "</td>");
		}
}

/*展示成绩表单的最近动态*/
function showGradeFormChart(gradeFormList){
	var count = gradeFormList.length;


	if(count == 1){
		$(".oneData-gf").show();
		$("#oneDataVal-grade").text(gradeFormList[0].grade);
		var date0 = new Date(gradeFormList[0].date);
		$("#oneDataVal-time").text(date0.getFullYear()+"-"+(date0.getMonth()+1) + "-"+ date0.getDate());
		$("#oneDataVal-mark").text(gradeFormList[0].mark);
			
		$("#chartbox_gf").hide();
		showResTips("多添加数据，可以展示炫酷的图表哦！");
		return ;
	}

	$(".oneData-gf").hide();
	$("#chartbox_gf").show();

	var maxAgeIndex = 0;

	var labels = new Array();
	var data_mark = new Array();//宝宝身高数据

	for(var i = 0;i<count ; i++){
		// labels[i] = gradeFormList[count-i-1].grade +"/" + gradeFormList[count-i-1].time;//去掉次数,改为成绩
		var datei = new Date(gradeFormList[count-i-1].date);
		labels[i] = gradeFormList[count-i-1].grade +"/" + (datei.getMonth()+1) + "-"+ datei.getDate();//去掉次数,改为成绩
		data_mark[i] = gradeFormList[count-i-1].mark;
	}

	var options = {};
	var data = {
		labels : labels,
		datasets : [
			{
				fillColor : "rgba(151,187,205,0.5)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff", 
				data : data_mark
			}
		]
	};

	var ctx_mark = $("#markSimChart").get(0).getContext("2d");//.getContext("2d");
			
	$("#markSimChart").attr("width", $(window).get(0).innerWidth*0.95);
	$("#markSimChart").attr("height", 200);
	var chart_m = new Chart(ctx_mark).Line(data,options);
}

/*展示成绩某一次，滚轮滚动的时候*/
function loadMark(){
	$.post("/CBabyAction/loadGrade",{
		babyId:localStorage.babyId,
		grade:localStorage.grade,
		subject:localStorage.subject,
		date:localStorage.date
		// time:localStorage.time
	},function(data){
		if(data.result == 0){
			$("#gradeFormId").text(data.data.id);
			$("#baby_mark").val(data.data.mark);
			$(".ngf-Tips").hide();
		}else{
			$("#baby_mark").val("");
			$("#gradeFormId").text("");
		}

	});

}

/*清空成绩添加框*/
function clearNewGradeFormBox(){
	console.log("清空成绩添加框");
	var date = new Date();
	var dateString = date.getFullYear()+"-"+(date.getMonth()+1) + "-"+ date.getDate();
	localStorage.date = dateString;
	$("#baby_mark").val("");
	$("#gradeFormId").text("");
	$("#gradeDate_input").val(dateString);
	if(localStorage.grade!=null){
		var grade = localStorage.grade_last;
		var subject = localStorage.subject_last;
		var time = parseInt(localStorage.time_last) + 1;
		$('#scroller_mark').mobiscroll('setVal', grade+" "+subject+" "+time, true, true, false, 0);
		loadMark();//加载今天该年级科目过录入的成绩
	}
	
}

/*-----------------疫苗函数---------------*/

/*加载下次接种疫苗*/
function loadNextVac(){
	$.post("/CBabyAction/loadNextVac",{
		babyId:localStorage.babyId
	},function(data){
		$("#item3mobile>.mui-loading").hide();//隐藏加载
		item3Show = true;
		if(data.result == 0){
			$(".nextVacList").show();
			$(".nullTips-vac-todo").hide();
			var length = data.data.length;
			 $(".nextVacList").html("");
			for(var i = 0; i < length; i++){
				var $list_item = $("<li></li>");
				$($list_item).html("<div class='item-nextVac item-vacName'><span class='realData'>"+data.data[i].name+"</span></div>"
						+"<div class='item-nextVac item-age'>"
							+"<i class='iconfont timeIcon'>&#xe635;</i><span class='timeData'>"+data.data[i].ageDcb+"</span>"
						+"</div>"); 
				 $(".nextVacList").append($list_item);
			}
		}else{
			$(".nextVacList").hide();
			if(data.data == "null"){
				$(".nullTips-vac-todo").text("恭喜您,宝宝已经接种完全部疫苗哦！");
			}else{
				$(".nullTips-vac-todo").text("操作异常,请退出重试！");
			}
			$(".nullTips-vac-todo").show();
		}
	});
}

/*加载已接种疫苗*/
function loadVaccinated(){
	$.post("/CBabyAction/loadVaccinated",{
		babyId:localStorage.babyId
	},function(data){
		if(data.result == 0){
			$(".vaccinatedTable").show();
			$(".nullTips-vac-done").hide();
			$("#more-vaced").show();
			var length = data.data.length;
			for(var i = 0; i < length; i++){
				var $line = $("<tr></tr>");
				var date = new Date(data.data[i].date);
				var dateString =  date.getFullYear()+"-"+(date.getMonth()+1) + "-"+ date.getDate();
				$($line).html("<td>"+data.data[i].name+"</td>"
						+"<td>"+dateString+"</td>"); 
				 $(".vaccinatedTable").append($line);
			}
		}else{
			$("#more-vaced").hide(); 
			$(".vaccinatedTable").hide();
			if(data.data == "null"){
				$(".nullTips-vac-done").text("宝宝还没有接种过疫苗哦！");
			}else{
				$(".nullTips-vac-done").text("操作异常,请退出重试！");
			}
			$(".nullTips-vac-done").show();
		}
	});
}

/*重置所有选项卡*/
function recoverTab(){
	item1Show = false;
	item2Show = false;
	item3Show = false;
	$(".mui-loading").show();
	$(".tab-Content").hide();
	$(".nullTips-tab").hide();
}

/*检查是否为float，返回true/false*/
function checkFloat(text){
	if(/^\d+\.?\d+$/.test(text)){
		return true;
	}else{
		return false;
	}
}

/*检查身高,返回相关提示*/
function checkHeight(text){
	var tips = "";
	if(!checkFloat(text)){
		tips = "请输入正确格式的身高！";
	}else{
		var height = parseFloat(text);
		if(height>200){
			tips = "宝宝真有那么高吗？请输入正确身高！";
		}
		if(height < 30){
			tips = "宝宝没那么矮吧？请输入正确身高！";
		}
	}
	return tips;
}

/*检查体重,返回相关提示*/
function checkWeight(text){
	var tips = "";
	if(!checkFloat(text)){
		tips = "请输入正确格式的体重！";
	}else{
		var weight = parseFloat(text);
		if(weight>120){
			tips = "宝宝真有那么重吗？请输入正确体重！";
		}
		if(weight < 1){
			tips = "宝宝没那么轻吧？请输入正确体重！";
		}
	}
	return tips;
}


/*检查分数,返回相关提示*/
function checkMark(text){
	var tips = "";
	if(!checkFloat(text)){
		tips = "请输入正确格式的分数！";
	}else{
		var weight = parseFloat(text);
		if(weight>100){
			tips = "分数不能超过100分哦！";
		}
		if(weight < 0){
			tips = "分数不能低于0分哦！";
		}
	}
	return tips;
}

