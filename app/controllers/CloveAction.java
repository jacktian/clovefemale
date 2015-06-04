package controllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import play.Logger;
import play.Play;
import play.libs.WS;
import play.mvc.Before;

/**
 * 丁香资讯
 * @author boxizen
 * @since 2015/06/04
 */
public class CloveAction extends WebService{

	/*
	 * 获取用户信息 
	 */
	@Before
	public static void getUserOpenid(){
		String appKey = Play.configuration.getProperty("wechat_appkey");
		String appSecret = Play.configuration.getProperty("wechat_secret");
		String code = params.get("code");
		try{
			String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appKey+"&secret="+appSecret+"&code="+code+"&grant_type=authorization_code";
			JsonElement jsonElement = WS.url(requestUrl).get().getJson();
			JsonObject json = jsonElement.getAsJsonObject();
			String openid = json.get("openid").getAsString();
			params.remove("code");
			session.put("openid", openid);
		}
		catch(Exception e){
			Logger.info("Unable to get user's openid");
		}
	}
	
	/*
	 * 获得最新资讯 
	 */
	public static void getNewMsg(){
		
	}
}
