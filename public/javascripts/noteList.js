$(function(){

       /*激活所有的面包屑点击功能*/
		$('.userList nav ul li').removeClass("disabled");
		/*总页码*/
		var pageNum = $('.userList nav ul li a').eq(-2).html();
		/*当前的页码*/
		var curPage = $('.curpageFlag').val();
		var nbFlag = $('.mbFlag').val();
		var nbId = $('.mbId').val();
       /*跳转后根据当前页和总页数初始化页码样式*/
       if(curPage==1){
        $('.userList nav ul li:first').addClass("disabled");
       }
       if(curPage == pageNum){
        $('.userList nav ul li:last').addClass("disabled");
       }
		/*激活当前页码的高亮显示*/
		$('.userList nav ul li').removeClass('active');
		$('.userList nav ul li').eq(curPage).addClass('active');

		/*分页跳转*/
		var pageJump = function(){
			/*点击的页码*/
		var nextPage = $(this).html();

		/*如果点击上一页*/
		if($(this).hasClass('prevPage')){	 
			var page = curPage - 1;
			// alert(page);
			window.location.href = localhost+"/RandomNote/findNoteByNotebook2?nbid="+nbId+"&nbname="+nbFlag+"&curpage="+page;

		}
		
		/*如果点击下一页*/
		else if($(this).hasClass('nextPage')){	 
			var page = parseInt(curPage) + 1;
			// alert(page);
			window.location.href = localhost+"/RandomNote/findNoteByNotebook2?nbid="+nbId+"&nbname="+nbFlag+"&curpage="+page;
		}
         else{
         	window.location.href = localhost+"/RandomNote/findNoteByNotebook2?nbid="+nbId+"&nbname="+nbFlag+"&curpage="+nextPage;
         }
          

		}

         /*绑定分页跳转*/
         $('.userList nav ul li a').click(pageJump);

         /*点击查看跳到Note信息显示页面*/

         var noteInfo = function(){
         	var nbid = $(this).attr('nbid');
	        window.location.href= localhost+"/RandomNote/findNoteById2?noteId="+nbid;
         }
          $(document).on('click','.submitBtn',noteInfo);
});