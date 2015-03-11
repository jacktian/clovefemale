package controllers;

import beans.AccountBean;
import beans.UserInfoBean;
import models.User;
/**
 * 客户端操作controller
 * @author Yingpeng
 * @since 03/05/15
 * */
public class ClientOp  extends WebService{
	/**
	 * 功能：修改昵称 (用户名)
	 * 返回：是否修改成功
	 * */
	public static void modifyNickname(String userId, String nickname){
		try{
				User user = User.findById(userId);
				if(user!=null){
				user.userName = nickname;
				user.save();
				wsOkAsJsonP("修改成功");
				}else{
				wsOkAsJsonP("修改失败，不存在该用户");
				}
				}catch(Exception e){
				wsErrorAsJsonP("操作异常");
				}
	}
	/**
	 * 功能：修改性別
	 * 返回：是否修改成功
	 * */
	public static void modifySex(String userId,String sex){
		try{
			User user = User.findById(userId);
			if(user!=null){
			user.sex=sex;
			user.save();
			wsOkAsJsonP("修改成功");
			}else{
			wsOkAsJsonP("修改失败，不存在该用户");
			}
			}catch(Exception e){
			wsErrorAsJsonP("操作异常");
			}
	}
	/**
	 * 功能：修改个性签名
	 * 返回：是否修改成功
	 * */
	public static void modifySignname(String userId,String signname){
		try{
			User user = User.findById(userId);
			if(user!=null){
			user.signName=signname;
			user.save();
			wsOkAsJsonP("修改成功");
			}else{
			wsOkAsJsonP("修改失败，不存在该用户");
			}
			}catch(Exception e){
			wsErrorAsJsonP("操作异常");
			}
	}
	/**
	 * 功能：查询个人信息 
	 * 返回：userInfoBean
	 * */
	public static void selectPsninfo(String userId){
		try{
			User user = User.findById(userId);
			if(user!=null){
			UserInfoBean userInfoBean = new UserInfoBean();
			userInfoBean.cloveId = user.cloveId;
			userInfoBean.sex = user.sex;
			userInfoBean.signName = user.signName;
			userInfoBean.userName = user.userName;
			wsOkAsJsonP(userInfoBean);
			}else{
			wsOkAsJsonP("查询个人信息失败，不存在该用户");
			}
			}catch(Exception e){
			wsErrorAsJsonP("操作异常");
			}
	}
		
	/**
	 * 功能：修改QQ
	 * 返回：是否修改成功
	 * */
	public static void modifyQq(String userId,String qq){
		try{
			User user = User.findById(userId);
			if(user!=null){
			user.QQ=qq;
			user.save();
			wsOkAsJsonP("修改成功");
			}else{
			wsOkAsJsonP("修改失败，不存在该用户");
			}
			}catch(Exception e){
			wsErrorAsJsonP("操作异常");
			}
	}
	/**
	 * 功能：修改微信号
	 * 返回：是否修改成功
	 * */
	public static void modifyMicromsg(String userId,String micromsg){
		try{
			User user = User.findById(userId);
			if(user!=null){
			user.weixin=micromsg;
			user.save();
			wsOkAsJsonP("修改成功");
			}else{
			wsOkAsJsonP("修改失败，不存在该用户");
			}
			}catch(Exception e){
			wsErrorAsJsonP("操作异常");
			}
	}
	/**
	 * 功能：修改微博号
	 * 返回：是否修改成功
	 * */
	public static void modifyWeibo(String userId,String weibo){
		try{
			User user = User.findById(userId);
			if(user!=null){
			user.weibo=weibo;
			user.save();
			wsOkAsJsonP("修改成功");
			}else{
			wsOkAsJsonP("修改失败，不存在该用户");
			}
			}catch(Exception e){
			wsErrorAsJsonP("操作异常");
			}
	}
	/**
	 * 功能：修改手机号
	 * 返回：是否修改成功
	 * */
	public static void modifyPhoneNum(String userId,String phonenum){
		try{
			User user = User.findById(userId);
			if(user!=null){
			user.phoneNum=phonenum;
			user.save();
			wsOkAsJsonP("修改成功");
			}else{
			wsOkAsJsonP("修改失败，不存在该用户");
			}
			}catch(Exception e){
			wsErrorAsJsonP("操作异常");
			}
	}
	/**
	 * 功能：修改邮件地址
	 * 返回：是否修改成功
	 * */
	public static void modifyEmail(String userId,String email){
		try{
			User user = User.findById(userId);
			if(user!=null){
			user.email=email;
			user.save();
			wsOkAsJsonP("修改成功");
			}else{
			wsOkAsJsonP("修改失败，不存在该用户");
			}
			}catch(Exception e){
			wsErrorAsJsonP("操作异常");
			}
	}
	/**
	 * 功能：查询账号与安全信息
	 * 返回：AccountBean
	 * */
	public static void selectAccountAndSecurity(String userId){
		try{
			User user = User.findById(userId);
			if(user!=null){
				AccountBean accountBean = new AccountBean();
				accountBean.cloveId=user.cloveId;
				accountBean.email=user.email;
				accountBean.isAddV=user.isAddV;
				accountBean.phoneNum=user.phoneNum;
				accountBean.QQ = user.QQ;
				accountBean.weibo = user.weibo;
				accountBean.weixin = user.weixin;
				wsOkAsJsonP(accountBean);
			}
			else{
			wsOkAsJsonP("查询账户信息失败，不存在该用户");
			}
			}catch(Exception e){
			wsErrorAsJsonP("操作异常");
			}
	}
	/**
	 * 功能：退出登录
	 * 返回：是否退出成功
	 * */
	public static void loginout(String userId){
		
	}
}
