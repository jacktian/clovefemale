$(function(){
    
        /*根据用户编号和姓名查询药箱的情况*/
	var findgrByUser = function(){
	var searchContent = $('.searchFlag').val();
	window.location.href=localhost+'/Application/listUsergrInfo?searchmsg='+searchContent;
	}
     $(document).on('click','.searchBtn',findgrByUser);

 });