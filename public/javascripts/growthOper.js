$(function(){
	/*分页跳转*/
	var pageJump = function(){
		/*总页码*/
		var pageNum = $('.babyList nav ul li a').eq(-2).html();
		
		/*激活所有的面包屑点击功能*/
		$('.babyList nav ul li').removeClass("disabled");
		
		/*当前的页码*/
		var curPage = $('.babyList nav ul li.active a').html();
		
		/*点击的页码*/
		var nextPage = $(this).html();
		
		/*激活当前页码的高亮显示*/
		$('.babyList nav ul li').removeClass('active');
		$(this).parents('.babyList nav ul li').addClass('active');
		
		/*如果点击上一页*/
		if($(this).hasClass('prevPage')){	 
			if(curPage==1){
				$('.babyList nav ul li').removeClass('active');
				$('.babyList nav ul li').eq(1).addClass('active');
				$('.babyList nav ul li:first').addClass('disabled');
				return false;
			}
			else {
				nextPage = parseInt(curPage)-parseInt(1);
				$('.babyList nav ul li').removeClass('active');
				$('.babyList nav ul li').eq(nextPage).addClass('active');
			}
		}
		
		/*如果点击下一页*/
		if($(this).hasClass('nextPage')){	 
			if(curPage==pageNum){
				$('.babyList nav ul li').removeClass('active');
				$('.babyList nav ul li').eq(-2).addClass('active');
				$('.babyList nav ul li').eq(-1).addClass('disabled');
				return false;
			}
			else{
				nextPage = parseInt(curPage)+parseInt(1);
				$('.babyList nav ul li').removeClass('active');
				$('.babyList nav ul li').eq(nextPage).addClass('active');
			}
		}
		
		/*如果下一个要点击的页码是最后一页*/
		if(nextPage==pageNum)
			$('.babyList nav ul li:last').addClass("disabled");
		/*如果下一个要点击的页码是第一页*/
		else if(nextPage==1)
			$('.babyList nav ul li:first').addClass("disabled");
		
		/*页面跳转*/
		loadBabysByPage(nextPage);
		return false;
	};
	
	/*删除宝贝*/
	var deleBaby = function(){
		$(this).parents("table tr").remove();
		var id = $(this).attr('id');
		$.get(localhost+'/babyaction/delbaby?id='+id, function() {
			alert("删除成功");
			$(this).parents("table tr").remove();
			location.reload();
	    });
	}

	$('.babyList nav ul li a').click(pageJump);
	$(document).on('click','.babyDelBtn',deleBaby);
});