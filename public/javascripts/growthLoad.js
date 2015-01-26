$(function(){
	/*数据渲染*/
	var successCB = function(data){
		var html = juicer($('#babyTpl').html(),{babyList:data.data});
		$('.babyItem').remove();
		$('.tableHeader').after(html);
	}
	
	/*加载数据*/
	loadBabysByPage = function(curpage){
		$.get(localhost+'/babyaction/listBabyBeans?curpage='+curpage, function(data) {
	        successCB(data);
	    });
	}
	
    /*根据宝宝姓名查找baby*/
	loadSearchBaby1 = function(curpage,name){
		/*alert(name);*/
           $.get(localhost+'/babyaction/findBabyByName?curpage='+curpage+'&name='+name, function(data) {
	      /*  alert("123");*/
	        successCB(data);
	    });
	}
	/*根据用户身份证查找baby*/
    loadSearchBaby2 = function(curpage,userIDcard){
           $.get(localhost+'/babyaction/findBabyByUser?curpage='+curpage+'&userIDcard='+userIDcard, function(data) {
	        successCB(data);
	    });
	}



    /*加载表格*/
	$(".findBaby").change(function(){
		if($(this).val()==1){
			$(".findform").html('');
		}
		if($(this).val()==2){
			$(".findform").html("<label for='babyName'>宝宝姓名：</label><input type='text' class='form-control' name='babyName' id='textId' placeholder='输入要查找宝宝的名称'>");
		}
		if($(this).val()==3){
			$(".findform").html("<label for='userIdcard'>用户身份证号：</label>"+
		    "<input type='text' class='form-control' name='userIdcard' id='textId' placeholder='输入要查找的宝宝的父母身份证'>");
		}
         
          /*if($(this).val()==4){
			$(".findform").html("<label for='userName'>用户名称：</label>"+
		    "<input type='text' class='form-control' name='username' id='textId' placeholder='输入要查找宝宝的用户姓名''>");
		}*/
	});

	$('.returnBtn').click(function(){
		location.reload();
	});

	$('.submitBtn').click(function(){
		/*alert($('#textId').val());*/
		if($("select").find("option:selected").val()==1){
			window.location.href=localhost+'/Application/recordMgm';
		}

		if($("select").find("option:selected").val()==2){
			/*if($('#textId').val()=='' || isNaN($('#textId').val())){*/
		if($('#textId').val()==''){
				alert("The search box cannot be empty");
			}else{
				var type=1;
			window.location.href=localhost+'/babyaction/searchBaby?type='+type+'&name='+$('#textId').val();
		}
		}

		if($("select").find("option:selected").val()==3){
			/*if($('#textId').val()=='' || isNaN($('#textId').val())){*/
		if($('#textId').val()=='' || isNaN($('#textId').val())){
				alert("The search box cannot be empty and must be a number");
			}else{
				var type=2;
			window.location.href=localhost+'/babyaction/searchBaby?type='+type+'&name='+$('#textId').val();
		}
		}



	});


	$(".topNav li").removeClass("active");
	$(".topNav li").eq(1).addClass("active");
	$(this).bind("loadBabysByPage",loadBabysByPage);
	/*$(this).bind("loadSearchBaby1",loadSearchBaby1);
	$(this).bind("loadSearchBaby2",loadSearchBaby2);*/
	if($('.flag').val()=='all'){
	$(this).trigger('loadBabysByPage');
     }
    if($('.flag').val()=='name' && $('.searchInfo').val()!=''){
	    loadSearchBaby1(0,$('.searchInfo').val());
	    $('.foot').addClass('hidden');
	     }
     if($('.flag').val()=='user' && $('.searchInfo').val()!=''){
          loadSearchBaby2(0,$('.searchInfo').val());
          $('.foot').addClass('hidden');
     }

});