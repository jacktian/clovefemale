$(function(){
    
        /*根据用户编号和姓名查询药箱的情况*/
	var findnbByUser = function(){
	var searchContent = $('.searchFlag').val();
	window.location.href=localhost+'/Application/listUserNotebookInfo?searchInfo='+searchContent;
	}
     $(document).on('click','.searchBtn',findnbByUser);

 });