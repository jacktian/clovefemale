$(function(){

       /*激活所有的面包屑点击功能*/
		$('.babyList nav ul li').removeClass("disabled");
		/*总页码*/
		var pageNum = $('.babyList nav ul li a').eq(-2).html();
		/*当前的页码*/
		var curPage = $('.curpageFlag').val();
		/*科目和分数段信息*/
		var babyId = $('.babyFlag').val();
		/*var gradeInterval = $('.gradeIntervalInfo').val();*/
       /*跳转后根据当前页和总页数初始化页码样式*/
       if(curPage==1){
        $('.babyList nav ul li:first').addClass("disabled");
       }
       if(curPage == pageNum){
        $('.babyList nav ul li:last').addClass("disabled");
       }
		/*激活当前页码的高亮显示*/
		$('.babyList nav ul li').removeClass('active');
		$('.babyList nav ul li').eq(curPage).addClass('active');

		/*分页跳转*/
		var pageJump = function(){
			/*点击的页码*/
		var nextPage = $(this).html();

		/*激活当前页码的高亮显示*/
		/*$('.babyList nav ul li').removeClass('active');
		$(this).parents('.babyList nav ul li').addClass('active');*/

		/*如果点击上一页*/
		if($(this).hasClass('prevPage')){	 
			var page = curPage - 1;
			/*alert(page);*/
			window.location.href=localhost+"/Application/findGrade?babyId="+babyId+"&curpage="+page;
			/*if(curPage==1){
				$('.babyList nav ul li').removeClass('active');
				$('.babyList nav ul li').eq(1).addClass('active');
				$('.babyList nav ul li:first').addClass('disabled');
				return false;
			}
			else {
				nextPage = parseInt(curPage)-parseInt(1);
				$('.babyList nav ul li').removeClass('active');
				$('.babyList nav ul li').eq(nextPage).addClass('active');
			}*/
		}
		
		/*如果点击下一页*/
		else if($(this).hasClass('nextPage')){	 
			var page = parseInt(curPage) + 1;
			/*alert(page);*/
			window.location.href=localhost+"/Application/findGrade?babyId="+babyId+"&curpage="+page;
		}
         else{
         	window.location.href=localhost+"/Application/findGrade?babyId="+babyId+"&curpage="+nextPage;
         }
          /*如果下一个要点击的页码是最后一页*/
		/*if(nextPage==pageNum)
			$('.babyList nav ul li:last').addClass("disabled");*/
		/*如果下一个要点击的页码是第一页*/
		/*else if(nextPage==1)
			$('.babyList nav ul li:first').addClass("disabled");*/


		}

         /*绑定分页跳转*/
         $('.babyList nav ul li a').click(pageJump);


});