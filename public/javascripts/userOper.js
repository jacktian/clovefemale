$(function(){
	
	/*删除用户*/
	var deleUser = function(){
		$(this).parents("table tr").remove();
		var uId = $(this).attr('uid');
		$.get('http://localhost:9000/useraction/deluser?uId='+uId, function() {
			alert("删除成功!");
			$(this).parents("table tr").remove();
			location.reload();
	    });
	}
	
	/*查看与修改视图切换*/
	var changePane = function(){
		$('.userList').addClass('hidden');
		$('.userDetail').removeClass('hidden');
		var id = $(this).parents("table tr").children("td").eq(0).children("input").val();
		var name = $(this).parents("table tr").children("td").eq(1).html();
		var nickname = $(this).parents("table tr").children("td").eq(2).html();
		var password = $(this).parents("table tr").children("td").eq(3).html();
		var email = $(this).parents("table tr").children("td").eq(4).html();
		var phone = $(this).parents("table tr").children("td").eq(5).html();
		var idcard = $(this).parents("table tr").children("td").eq(6).html();
		$('#uid').val(id);
		$('#userName').val(name);
		$('#nickName').val(nickname);
		$('#password').val(password);
		$('#email').val(email);
		$('#phone').val(phone);
		$('#idcard').val(idcard);
	};
	
	/*修改用户*/
	var alterUser = function(){
		var uid = $('#uid').val();
		var username = $('#userName').val();
		var nickname = $('#nickName').val();
		var password = $('#password').val();
		var email = $('#email').val();
		var phone = $('#phone').val();
		var idcard = $('#idcard').val();
		console.log(uid);
		$.get('http://localhost:9000/useraction/alteruser?uId='+uid + "&userName="+nickname+"&passwd="+password+"&realName="+username+"&phoneNum="+phone+"&email="+email+"&IDcard="+idcard, function() {
	       alert("修改成功!");
	    });
	};
	
	/*分页跳转*/
	var pageJump = function(){
		/*总页码*/
		var pageNum = $('.userList nav ul li a').eq(-2).html();
		
		/*激活所有的面包屑点击功能*/
		$('.userList nav ul li').removeClass("disabled");
		
		/*当前的页码*/
		var curPage = $('.userList nav ul li.active a').html();
		
		/*点击的页码*/
		var nextPage = $(this).html();
		
		/*激活当前页码的高亮显示*/
		$('.userList nav ul li').removeClass('active');
		$(this).parents('.userList nav ul li').addClass('active');
		
		/*如果点击上一页*/
		if($(this).hasClass('prevPage')){	 
			if(curPage==1){
				$('.userList nav ul li').removeClass('active');
				$('.userList nav ul li').eq(1).addClass('active');
				$('.userList nav ul li:first').addClass('disabled');
				return false;
			}
			else {
				nextPage = parseInt(curPage)-parseInt(1);
				$('.userList nav ul li').removeClass('active');
				$('.userList nav ul li').eq(nextPage).addClass('active');
			}
		}
		
		/*如果点击下一页*/
		if($(this).hasClass('nextPage')){	 
			if(curPage==pageNum){
				$('.userList nav ul li').removeClass('active');
				$('.userList nav ul li').eq(-2).addClass('active');
				$('.userList nav ul li').eq(-1).addClass('disabled');
				return false;
			}
			else{
				nextPage = parseInt(curPage)+parseInt(1);
				$('.userList nav ul li').removeClass('active');
				$('.userList nav ul li').eq(nextPage).addClass('active');
			}
		}
		
		/*如果下一个要点击的页码是最后一页*/
		if(nextPage==pageNum)
			$('.userList nav ul li:last').addClass("disabled");
		/*如果下一个要点击的页码是第一页*/
		else if(nextPage==1)
			$('.userList nav ul li:first').addClass("disabled");
		
		/*页面跳转*/
		loadUsersByPage(nextPage);
		return false;
	};
	
	/*返回按钮*/
	$('.returnBtn').click(function(){
		location.reload();
	});
	
	
	$(document).on('click','.deleteBtn',deleUser);
	$(document).on('click','.alterBtn',changePane);
	$('.userList nav ul li a').click(pageJump);
	$('.submitBtn').click(alterUser);
});