$(function(){
	/*/*数据渲染*/
	/*var successCB = function(data){
		var html = juicer($('#babyTpl').html(),{babyList:data.data});
		$('.babyItem').remove();
		$('.tableHeader').after(html);
	}*/

/*根据宝宝姓名查找baby*/
	/*loadSearchBaby1 = function(name){
           $.get(localhost+'/babyaction/findBabyByName?name='+name, function(data) {
	        successCB(data);
	    });
	}*/
	/*根据用户身份证查找baby*/
    /*loadSearchBaby2 = function(userIDcard){
           $.get(localhost+'/babyaction/findBabyByUser?userIDcard='+userIDcard, function(data) {
	        successCB(data);
	    });
	}*/


	
	/*加载表格*/
	$(".findBaby").change(function(){
		if($(this).val()==1){
			$(".findform").html("<label for='subjectName'>选择科目：</label><select class='form-control selectSubject' id='selectSubject'>"+
         "<option value='语文'>语文</option>"+
         "<option value='数学'>数学</option>"+
         "<option value='英语'>英语</option>"+
         "<option value='美术'>美术</option>"+
         "<option value='音乐'>音乐</option></select>"+
         "<label for='subjectGrade'>选择成绩区间：</label><select class='form-control selectGrade' id='selectGrade'>"+
         "<option value='0'>全部记录</option>"+
         "<option value='1'>60以下</option>"+
         "<option value='2'>60-69</option>"+
         "<option value='3'>70-79</option>"+
         "<option value='4'>80-89</option>"+
         "<option value='5'>90-100</option>"+
         "</select>"
         );
		}
		if($(this).val()==2){
			$(".findform").html('');
		}
		if($(this).val()==3){
			$(".findform").html("<label for='babyId'>输入宝宝名称：</label>"+
              " <input type='text' class='form-control babyNameInput' name='babyName' id='babyName' placeholder='请输入宝宝名称'>"+
              "<label for='subjectName'>选择科目：</label><select class='form-control selectSubject' id='selectSubject'>"+
              "<option value='语文'>语文</option>"+
              "<option value='数学'>数学</option>"+
              "<option value='英语'>英语</option>"+
             "<option value='美术'>美术</option>"+
             "<option value='音乐'>音乐</option></select>"
				);
		}
		/*根据时间段查询身体指标*/
		if($(this).val()==4){
			$(".findform").html("<label for='babyId'>输入宝宝名称：</label>"+
              " <input type='text' class='form-control babyNameInput' name='babyName' id='babyName' placeholder='请输入宝宝名称'>"+
              "<label for='time'>选择时间段：Start:<div class='well'> <div id='datetimepicker4' class='input-append'>"+
              "<input data-format='yyyy-MM-dd' type='text'></input>"+
              "<span class='add-on'>"+
              "<i data-time-icon='icon-time' data-date-icon='icon-calendar'></i>"+
             "</span></div></div>"+
             "<script type='text/javascript'>"+
             "$(function() {"+
             "$('#datetimepicker4').datetimepicker({"+
              "pickTime: false"+
                "  }); });"+
              "</script>"
            
				);
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
		/*根据宝宝的科目和成绩区间进行筛选*/
		if($('.findBaby').find("option:selected").val()==1){
			alert("1234");
			var subject = $('.selectSubject').find("option:selected").val();
			var gradeInterval = $('.selectGrade').find("option:selected").val();
			window.location.href=localhost+"/BabyGrowth/selectBabyByGrade?subject="+subject+"&gradeInterval="+gradeInterval+"&curpage="+1;
		}

		if($('.findBaby').find("option:selected").val()==2){
			/*if($('#textId').val()=='' || isNaN($('#textId').val())){*/

		}
        /*个体成绩查询*/
		if($('.findBaby').find("option:selected").val()==3){
			/*if($('#textId').val()=='' || isNaN($('#textId').val())){*/
            var babyName = $('.babyNameInput').val();
            var subject = $('.selectSubject').find("option:selected").val();
            alert(babyName+" "+subject);
            window.location.href = localhost+"/BabyGrowth/findMarkByBaby?babyName="+babyName+"&subject="+subject+"&curpage="+1;

		}

		/*个体时间段身体指标查询*/
		if($('.findBaby').find("option:selected").val()==4){
			/*if($('#textId').val()=='' || isNaN($('#textId').val())){*/


		}


	});

  
                  /*$('#datetimepicker4').datetimepicker({
                   pickTime: false
                      });
                   $('#datetimepicker5').datetimepicker({
                   pickTime: false
                      });*/
                           

});