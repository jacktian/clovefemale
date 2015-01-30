$(function(){
	/*$('#datetimepicker4').datetimepicker({
		
	})*/
 	$('.form_datetime').datetimepicker({
        //language:  'fr',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0,
        showMeridian: 1
    });
	$(document).on('datetimepicker','#datetimepicker4',function(){
		
	});
});