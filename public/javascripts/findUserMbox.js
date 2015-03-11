$(function(){
    
        /*根据用户编号和姓名查询药箱的情况*/
	var findMbByUser1 = function(){
	var searchContent = $('.searchFlag').val();
	window.location.href=localhost+'/MedicineAction/findMBbyUser2?searchContent='+searchContent;
	}
     $(document).on('click','.searchBtn',findMbByUser1);

 });