
var todoVacLoad = false;
var doneVacLoad = false;
var allVacLoad = false;
var text_sarchInput = "";
$(function(){
	//设置日期图标中的日期号码
	$('.dateNo').text(new Date().getDate());

	//自定义疫苗蒙版
	// document.getElementById("vaccineInput").addEventListener("tap",function(event){
 // 		mui('.vacInputBox').popover('toggle');
	// });
	
	//日期控件触发
	// $(".dateBox_vac").mobiscroll().calendar({
	// 		theme:'ios',
	// 		lang:'zh',
	// 		display:'bottom'
	// });

	/*加载宝宝资料*/
	$("#title-vac").text(localStorage.name+"的疫苗接种");
	// loadBabyInf();
	/*加载未接种疫苗*/
	loadTodoVac();

	//未接种疫苗tap事件
	document.getElementById("todoVacLink").addEventListener("tap",function(event){
		$(".vacListName").text("未接种疫苗");
 		$(".vacList").removeClass("activeVacList");
 		$("#todoVac").addClass("activeVacList");
 		$("#searchInput").val("");
 		if(todoVacLoad == false){
 			loadTodoVac();
 		}
 		
	});

	//已接种疫苗tap事件
	document.getElementById("doneVacLink").addEventListener("tap",function(event){
		$(".vacListName").text("已接种疫苗");
 		$(".vacList").removeClass("activeVacList");
 		$("#doneVac").addClass("activeVacList");
 		$("#searchInput").val("");
 		if(doneVacLoad == false){
 			loadDoneVac();
 		}
 		
	});

	//全部疫苗tap事件
	document.getElementById("allVacLink").addEventListener("tap",function(event){
		$(".vacListName").text("全部疫苗");
 		$(".vacList").removeClass("activeVacList");
 		$("#allVac").addClass("activeVacList");
 		$("#searchInput").val("");
 		if(allVacLoad == false){
 			loadAllVac();
 		}
	});

	// 搜索疫苗的focus事件
	$("#searchInput").focus(function(){
		$(".vacListName").text("搜索疫苗结果");
 		$(".vacList").removeClass("activeVacList");
 		$("#searchVac").addClass("activeVacList");
 		$(".searchVacForm").html("");
	});

	//搜索疫苗的keydown事件
	$("#searchInput").keyup(function(){
		var searchWord = $("#searchInput").val();
		if(text_sarchInput != searchWord&& searchWord !=""){//与上次不同
			loadSearchResult(searchWord);
		}
		text_sarchInput = searchWord;
		// console.log(searchWord);
		// if(searchWord != ""){
		// 	loadSearchResult(searchWord);
		// }
		console.log("up-"+searchWord);
		
	});

});//结束$(funciton)

/*加载未接种疫苗*/
function loadTodoVac(){
	$(".todoVacForm").html("");
	$(".nullTips-todoVac").hide();//text("宝宝还没有接种过疫苗哦！");
	$.post("/CBabyAction/loadTodoVac",{
		babyId:localStorage.babyId
	},function(data){
		if(data.result == 0){
			todoVacLoad = true;
			var length = data.data.length;
			var now = new Date();
			var nowStr = now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate();
			for(var i = 0; i < length; i++){
				var etmDate = new Date(data.data[i].etmDate);
				var etmDateStr = etmDate.getFullYear()+"-"+(etmDate.getMonth()+1)+"-"+etmDate.getDate();
				var vacItemBox = $("<div class='mui-input-row mui-checkbox mui-left'></div>");
				$(vacItemBox).attr("id",data.data[i].id);
				$(vacItemBox).html("<label class='vacName'>"+data.data[i].name+"</label>"
				+"<input name='radio1' id='checkbox-"+data.data[i].id+"' class='checkbox-vac-todo' type='checkbox' />"
				+"<div class='fullVacDate date-todoVac'>"+etmDateStr+"</div>");
				// +"<div class='dateBox_vac'>"
				// 	+"<img  class='dateImg_vac' src='/public/images/client/date.png' alt=''/>"
				// 	+"<span class='dateNo'></span>"
				// +"</div>");
				$(".todoVacForm").append(vacItemBox);

				$('.dateNo').text(new Date().getDate());
				//日期控件触发
				// $(".dateBox_vac").mobiscroll().calendar({
				// 		theme:'ios',
				// 		lang:'zh',
				// 		display:'bottom',
				// 		onSetDate:function(day,inst){
				// 			$(this).children(".dateNo").text(day.date.getDate());
				// 			$(this).siblings(".date-todoVac").text(day.date.getFullYear()+"-"+(day.date.getMonth()+1)+"-"+day.date.getDate());
				// 		},
				// 		onDayChange:function(day,inst){
				// 			console.log("changeDay");
				// 		}
				// });

				document.getElementById("checkbox-"+data.data[i].id).addEventListener("tap",function(){
					var objectId = $(this).parent().attr("id");
					// console.log($(this).checked);
					window.setTimeout("removeObject('"+objectId+"')",500);
					doneVacLoad = false;
					allVacLoad = false;


					$.post("/CBabyAction/modifyVac",{
						babyVacId:objectId,
						date:nowStr//$(this).siblings(".date-todoVac").text()
					},function(data){
						if(data.result == 0){//成功

						}else{//失败

						}
					});
				});
			}
		}else{
			if(data.data == "null"){
				$(".nullTips-todoVac").text("恭喜您,宝宝已经接种完全部疫苗哦！");
			}else{
				todoVacLoad = false;
				$(".nullTips-todoVac").text("操作异常,请退出重试！");
			}
			$(".nullTips-todoVac").hide();
		}
	})
}

/*加载已接种疫苗*/
function loadDoneVac(){
	$(".doneForm").html("");
	$(".nullTips-doneVac").hide();//text("宝宝还没有接种过疫苗哦！");
	$.post("/CBabyAction/loadAllDoneVac",{
		babyId:localStorage.babyId
	},function(data){
		doneVacLoad = true;
		if(data.result == 0){

			var length = data.data.length;
			for(var i = 0; i < length; i++){
				var vac = data.data[i];
				var date = new Date(vac.date);
				var $vacItemBox = $("<div class='mui-input-row mui-checkbox mui-left'></div>");
				$($vacItemBox).attr("id","doneVac-"+vac.id);

				$($vacItemBox).html("<label>"+vac.name+"</label>"
				+"<input name='radio1' id='checkbox-done-"+vac.id+"' type='checkbox' checked/>"
				+"<div class='fullVacDate'>"+date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+"</div>");
				
				$(".doneForm").append($vacItemBox);

				document.getElementById("checkbox-done-"+vac.id).addEventListener("tap",function(){
					var objectId = $(this).parent().attr("id");

					window.setTimeout("removeObject('"+objectId+"')",500);
					todoVacLoad = false;
					allVacLoad = false;

					var babyVacId = objectId.substring(8,objectId.length)

					$.post("/CBabyAction/modifyVac",{
						babyVacId:babyVacId,
						date:null
					},function(data){
						if(data.result == 0){//成功

						}else{//失败

						}
					});
				});
			}
		}else{
			if(data.data == "null"){//没有已接种疫苗
				$(".nullTips-doneVac").text("宝宝还没有接种过疫苗哦！");

			}else{
				doneVacLoad = false;
				$(".nullTips-doneVac").text("操作异常！");
			}
			$(".nullTips-doneVac").show();
		}
	})
}

/*加载宝宝资料*/
function loadBabyInf(){
	$.post("/CBabyAction/loadBabyInf",{
		babyId:localStorage.babyId//"1BFB8CDDECC24BE49F8D3C5B9528BBB0"//angela的babyId//babyId
	},function(data){
		if(data.result==0){//成功
			$("#title-vac").text(data.data.name+"的疫苗接种");
			localStorage.babyId = data.data.id;
		}
	});
}


/*加载宝宝的全部疫苗*/
function loadAllVac(){
	$(".allVacForm").html("");
	$.post("/CBabyAction/loadAllVac",{
		babyId:localStorage.babyId
	},function(data){
		allVacLoad = true;
		if(data.result == 0){
			var length = data.data.length;
			for(var i = 0; i < length; i++){
				var vac = data.data[i];
				var now = new Date();

				var vacItemBox = $("<div class='mui-input-row mui-checkbox mui-left'></div>");
				$(vacItemBox).attr("id","allVac-"+vac.id);
				if(vac.isDone == "0"){//未接种疫苗
					var etmDate = new Date(vac.etmDate);
					$(vacItemBox).html("<label>"+vac.name+"</label>"
					+"<input name='radio1' id='checkbox-all-"+vac.id+"' class='checkbox-vac-todo checkbox-todo' type='checkbox'/>"
					+"<div class='fullVacDate date-todoVac'>"+etmDate.getFullYear()+"-"+(etmDate.getMonth()+1)+"-"+etmDate.getDate()+"</div>");
					// +"<div class='dateBox_vac'>"
					// 	+"<img  class='dateImg_vac' src='/public/images/client/date.png' alt=''/>"
					// 	+"<span class='dateNo'></span>"
					// +"</div>");
				}else{
					var date = new Date(vac.date);
					$(vacItemBox).html("<label>"+vac.name+"</label>"
					+"<input name='radio1' id='checkbox-all-"+vac.id+"' class='checkbox-done' type='checkbox' checked/>"
					+"<div class='fullVacDate'>"+date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+"</div>");
					// +"<div class='dateBox_vac doteBox-hide'>"
					// 	+"<img  class='dateImg_vac' src='/public/images/client/date.png' alt=''/>"
					// 	+"<span class='dateNo'></span>"
					// +"</div>");
				}
				
				$(".allVacForm").append(vacItemBox);

				$('.dateNo').text(new Date().getDate());
				//日期控件触发
				$(".dateBox_vac").mobiscroll().calendar({
						theme:'ios',
						lang:'zh',
						display:'bottom',
						onSetDate:function(day,inst){
							console.log("Ok")
							$(this).children(".dateNo").text(day.date.getDate());
							$(this).siblings(".date-todoVac").text(day.date.getFullYear()+"-"+(day.date.getMonth()+1)+"-"+day.date.getDate());
						},
						onDayChange:function(day,inst){
							console.log("changeDay");
						}
				});

				document.getElementById("checkbox-all-"+data.data[i].id).addEventListener("tap",function(){
					var objectId = $(this).parent().attr("id");
					var date = new Date();
					// window.setTimeout("removeObject('"+objectId+"')",500);
					todoVacLoad = false;
					doneVacLoad = false;
					if($(this).hasClass("checkbox-todo")){//如果是选中,标记为已接种
						var now = new Date();
						date = now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate();
						console.log("已接种-" + date);
						$(this).removeClass("checkbox-todo");
						$(this).addClass("checkbox-done");
						$(this).siblings(".fullVacDate").removeClass("date-todoVac");
						$(this).siblings(".dateBox_vac").addClass("doteBox-hide");
					}else{
						date = null;
						console.log("未接种-"+date);
						$(this).removeClass("checkbox-done");
						$(this).addClass("checkbox-todo");
						$(this).siblings(".fullVacDate").addClass("date-todoVac");
						$(this).siblings(".dateBox_vac").removeClass("doteBox-hide");
					}

					var babyVacId = objectId.substring(7,objectId.length)
					$.post("/CBabyAction/modifyVac",{
						babyVacId:babyVacId,
						date:date
					},function(data){
						if(data.result == 0){//成功

						}else{//失败

						}
					});

				});
			}
		}else{
			if(data.data == "null"){

			}else{
				allVacLoad = false;
			}
		}
	})
}

/*加载搜索结果*/
function loadSearchResult(searchWord){
	$(".searchVacForm").html("");
	$.post("/CBabyAction/loadSearchVac",{
		babyId:localStorage.babyId,
		searchWord:searchWord
	},function(data){
		if(data.result == 0){
			var length = data.data.length;
			console.log(length);
			for(var i = 0; i < length; i++){
				var vac = data.data[i];
				var now = new Date();

				var vacItemBox = $("<div class='mui-input-row mui-checkbox mui-left'></div>");
				$(vacItemBox).attr("id","searchVac-"+vac.id);
				if(vac.isDone == "0"){//未接种疫苗
					var etmDate = new Date(vac.etmDate);
					$(vacItemBox).html("<label>"+vac.name+"</label>"
					+"<input name='radio1' id='checkbox-search-"+vac.id+"' class='checkbox-vac-todo checkbox-todo' type='checkbox'/>"
					+"<div class='fullVacDate date-todoVac'>"+etmDate.getFullYear()+"-"+(etmDate.getMonth()+1)+"-"+etmDate.getDate()+"</div>");
					// +"<div class='dateBox_vac'>"
					// 	+"<img  class='dateImg_vac' src='/public/images/client/date.png' alt=''/>"
					// 	+"<span class='dateNo'></span>"
					// +"</div>");
				}else{
					var date = new Date(vac.date);
					$(vacItemBox).html("<label>"+vac.name+"</label>"
					+"<input name='radio1' id='checkbox-search-"+vac.id+"' class='checkbox-done' type='checkbox' checked/>"
					+"<div class='fullVacDate'>"+date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+"</div>");
					// +"<div class='dateBox_vac doteBox-hide'>"
					// 	+"<img  class='dateImg_vac' src='/public/images/client/date.png' alt=''/>"
					// 	+"<span class='dateNo'></span>"
					// +"</div>");
				}
				
				$(".searchVacForm").append(vacItemBox);

				$('.dateNo').text(new Date().getDate());
				//日期控件触发
				$(".dateBox_vac").mobiscroll().calendar({
						theme:'ios',
						lang:'zh',
						display:'bottom',
						onSetDate:function(day,inst){
							console.log("Ok")
							$(this).children(".dateNo").text(day.date.getDate());
							$(this).siblings(".date-todoVac").text(day.date.getFullYear()+"-"+(day.date.getMonth()+1)+"-"+day.date.getDate());
						},
						onDayChange:function(day,inst){
							console.log("changeDay");
						}
				});

				document.getElementById("checkbox-search-"+data.data[i].id).addEventListener("tap",function(){
					var objectId = $(this).parent().attr("id");
					var date;
					// window.setTimeout("removeObject('"+objectId+"')",500);
					todoVacLoad = false;
					doneVacLoad = false;
					allVacLoad = false;
					if($(this).hasClass("checkbox-todo")){//如果是选中,标记为已接种
						
						date = now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate();//$(this).siblings(".fullVacDate").text();
						console.log("已接种-" + date);
						$(this).removeClass("checkbox-todo");
						$(this).addClass("checkbox-done");
						$(this).siblings(".fullVacDate").removeClass("date-todoVac");
						$(this).siblings(".dateBox_vac").addClass("doteBox-hide");
					}else{
						date = null;
						console.log("未接种-"+date);
						$(this).removeClass("checkbox-done");
						$(this).addClass("checkbox-todo");
						$(this).siblings(".fullVacDate").addClass("date-todoVac");
						$(this).siblings(".dateBox_vac").removeClass("doteBox-hide");
					}

					var babyVacId = objectId.substring(10,objectId.length)
					$.post("/CBabyAction/modifyVac",{
						babyVacId:babyVacId,
						date:date
					},function(data){
						if(data.result == 0){//成功

						}else{//失败

						}
					});

				});
			}
		}else{
			if(data.data == "null"){

			}else{
				allVacLoad = false;
			}
		}
	})
}


/*清除元素*/
function removeObject(objectId){
	console.log("remove"+objectId);
	$("#"+objectId).remove();
}