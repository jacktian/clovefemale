package controllers;

import java.util.Date;
import java.util.List;



import java.util.Map;

import jobs.AccessTokenRefresher;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import play.Play;
import play.cache.CacheFor;
import play.db.jpa.Model;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.WS.WSRequest;
import play.mvc.*;
import utils.Sign;

import models.User;
import beans.UserCenterBean;

/**
 * 客户端控制器
 * 
 * @author boxiZen
 * @since 2015/03/23
 */
public class Client extends WebService{
	/**
	 * 从配置文件中获取appKey与appSecret
	 */
	public static String appKey = Play.configuration.getProperty("wechat_appkey");
	public static String appSecret = Play.configuration.getProperty("wechat_secret");
	
	/**
	 * 用户openid
	 */
	public static String openid = null;
	
	
	/**
	 * 拦截器
	 */
	@Before(unless={"record","first"})
	public static void getCrtUser(){
		String code = params.get("code");
		try{
			String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appKey+"&secret="+appSecret+"&code="+code+"&grant_type=authorization_code";
			JsonElement jsonElement = WS.url(requestUrl).get().getJson();
			JsonObject json = jsonElement.getAsJsonObject();
			openid = json.get("openid").getAsString();
			//移除页面的code参数
			params.remove("code");
		}
		catch(Exception e){
			//此处做拦截操作
		}
	}
	
	
	/**
	 * 记录控首页
	 */
	public static void record(){
		render("/Client/record/mense.html");
	}

	/**
	 * 助孕记录
	 */
	public static void pregMense(){
		System.out.println("用户openid"+openid);
		render("/Client/record/mense.html");
	}

	/**
	 * 小药箱
	 */
	public static void medBox(){
		render("/Client/record/medBox.html");
	}	

	/**
	 * 药品详情
	 */
	public static void medicine(){
		render("/Client/record/medicine.html");
	}		

	/**
	 *我的孩子
	 **/
	public static void mybaby(){
		render("/Client/record/mybaby.html");
	}

	/**
	 *孩子成长记录
	 **/
	public static void babyRecord(){
		render("/Client/record/babyRecord.html");
	}


	/**
	 *疫苗接种
	 **/
	public static void vaccine(){
		render("/Client/record/vaccine.html");
	}

	/**
	 *会员中心
	 **/
	public static void psnCenter(){
		User userModel = User.find("byOpenid", openid).first();
		UserCenterBean user = new UserCenterBean();

		//头像
		user.headimgurl = userModel.headimgurl ;

		//昵称
		user.nickname = userModel.nickname;

		//丁香号
		if(userModel.cloveId == null || "".equals(userModel.cloveId)){
			user.cloveId = "未设置";
		}else{
			user.cloveId = userModel.cloveId;
		}

		//个性签名
		if(userModel.signName == null || "".equals(userModel.signName)){
			user.signName = "点击设置";
		}else{
			user.signName = userModel.signName;
		}

		//加V
		if(userModel.isAddV == null || userModel.isAddV == false)){
			user.isAddV = "未加V";
		}else{
			user.isAddV = "已加V";
		}

		//用户等级
		user.userGrade = userModel.userGrade;

		//手机
		if(userModel.phoneNum == null || "".equals(userModel.phoneNum)){
			user.phoneNum = "未绑定";
		}else{
			user.phoneNum = userModel.phoneNum;
		}

		//邮箱
		if(userModel.email == null || "".equals(userModel.email)){
			user.email = "未绑定";
		}else{
			user.email = userModel.phoneNum;
		}

		render("/Client/record/psnCenter.html",user);
	}

	/**
	 *提醒
	 **/
	public static void remind(){
		render("/Client/personal/remind.html");
	}


	/**
	 *首页
	 **/
	public static void first(){
		render("/Client/personal/first.html");
	}


}
