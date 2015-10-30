$(function(){

	$('.returnBtn').click(function(){
		alert($('.typeFlag').val());
	});

	$('.submitBtn').click(function(){
		/*根据查询女性助手的类型筛选*/
		if($('.findgrcondition').find("option:selected").val()==1){
			
			var type = $('.typeFlag').val();
			var userId = $('.userIdFlag').val();
			var userName = $('.userNameFlag').val();
			var sDate = $('#dtp_input2').val();
            var eDate = $('#dtp_input3').val();
            // alert(sDate+" "+eDate);
			window.location.href=localhost+"/GestationalWeightAction/getWeightDataByDate2?userId="+userId+"&userName="+userName+"&sDate="+sDate+"&eDate="+eDate+"&curpage="+1;
		}

		if($('.findgrcondition').find("option:selected").val()==2){
         /* alert($('#dtp_input4').val()+" "+$('#dtp_input5').val()+" ");*/
         var type = $('.typeFlag').val();
			var userId = $('.userIdFlag').val();
			var userName = $('.userNameFlag').val();
			var sDate = $('#dtp_input2').val();
            var eDate = $('#dtp_input3').val();
            // alert(sDate+" "+eDate);
        window.location.href=localhost+"/FetalMovementAction/getMovementDataByDate2?userId="+userId+"&userName="+userName+"&sDate="+sDate+"&eDate="+eDate+"&curpage="+1;
		}
        /*月经查询*/
		if($('.findgrcondition').find("option:selected").val()==3){
			
            var type = $('.typeFlag').val();
			var userId = $('.userIdFlag').val();
			var userName = $('.userNameFlag').val();
			var sDate = $('#dtp_input2').val();
            var eDate = $('#dtp_input3').val();
            // alert(sDate+" "+eDate);
        window.location.href=localhost+"/MensesAction/getMensesDataByDate2?userId="+userId+"&userName="+userName+"&sDate="+sDate+"&eDate="+eDate+"&curpage="+1;

		}

		/*个体时间段身体指标查询*/
		/*if($('.findBaby').find("option:selected").val()==4){
			
          alert($('#dtp_input2').val()+" "+$('#dtp_input3').val()+" "+$('.babyNameInput').val());
          var name = $('.babyNameInput').val();
          var sDate = $('#dtp_input2').val();
          var eDate = $('#dtp_input3').val();
            window.location.href = localhost+"/BabyGrowth/findBIByBaby?name="+name+"&sDate="+sDate+"&eDate="+eDate+"&curpage="+1;
		}
*/

	});
/*设置默认选择，*/
 $('.findgrcondition').find("option[value='"+$('.typeFlag').val()+"']").attr('selected','selected');          

});