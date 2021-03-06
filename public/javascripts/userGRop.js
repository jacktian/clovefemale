$(function(){

       /*激活所有的面包屑点击功能*/
		$('.userList nav ul li').removeClass("disabled");
		/*总页码*/
		var pageNum = $('.userList nav ul li a').eq(-2).html();
		/*当前的页码*/
		var curPage = $('.curpageFlag').val();
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
			window.location.href = localhost+"/Application/listUserGR?&curpage="+page;

		}
		
		/*如果点击下一页*/
		else if($(this).hasClass('nextPage')){	 
			var page = parseInt(curPage) + 1;
			// alert(page);
			window.location.href = localhost+"/Application/listUserGR?&curpage="+page;
		}
         else{
         	window.location.href = localhost+"/Application/listUserGR?&curpage="+nextPage;
         }
          

		}

         /*绑定分页跳转*/
         $('.userList nav ul li a').click(pageJump);


        /*查看特定用户的体重情况，跳转到日期选择区间页面*/
	var findGrByUser = function(){
	var uId = $(this).attr('uid');
	var type = $(this).attr('typeflag');
	window.location.href=localhost+'/Application/selectGrcondition?userId='+uId+"&type="+type;
	}
    $(document).on('click','.submitBtn',findGrByUser);
});