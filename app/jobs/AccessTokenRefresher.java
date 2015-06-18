package jobs;

import java.util.List;

import models.WeChat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import play.Play;
import play.jobs.Every;
import play.jobs.Job;
import play.jobs.On;
import play.jobs.OnApplicationStart;
import play.libs.WS;
import play.libs.WS.HttpResponse;

/**
 * 微信公众平平台access_token定时获取
 * 
 * @author boxiZen
 * @since 2015/03/24
 */
@Every("50min")
public class AccessTokenRefresher extends Job{
	
	@Override
	public void doJob(){
		
		/* 从配置文件中获取appKey与appSecret */
		String appKey = Play.configuration.getProperty("wechat_appkey");
		String appSecret = Play.configuration.getProperty("wechat_secret");
		
		/* 调用微信接口获取access_token */
		HttpResponse resp = WS.url("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appKey+"&secret="+appSecret).get();
		JsonElement jsonElement = resp.getJson();
		JsonObject json = jsonElement.getAsJsonObject();
		
		try{
			if(json.get("access_token") != null){
				/* 将access_token保存到数据库中 */
				if(WeChat.findAll().size() == 0){
					WeChat wechat = new WeChat();
					wechat.access_token = json.get("access_token").getAsString();
					wechat.save();
				}
				else{
					WeChat wechat = (WeChat) WeChat.findAll().get(0);
					wechat.access_token = json.get("access_token").getAsString();
					wechat.save();
				}
			}
			else{
				System.out.println(json.get("errcode"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*立即获得access_token(测试时使用)*/
	public static void getAccessToken(){
		/* 从配置文件中获取appKey与appSecret */
		String appKey = Play.configuration.getProperty("wechat_appkey");
		String appSecret = Play.configuration.getProperty("wechat_secret");
		
		/* 调用微信接口获取access_token */
		HttpResponse resp = WS.url("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appKey+"&secret="+appSecret).get();
		JsonElement jsonElement = resp.getJson();
		JsonObject json = jsonElement.getAsJsonObject();
		/*打印accessToken的值*/
		// System.out.println("access_token:"+json.get("access_token").getAsString());
		try{
			if(json.get("access_token") != null){
				/* 将access_token保存到数据库中 */
				if(WeChat.findAll().size() == 0){
					WeChat wechat = new WeChat();
					wechat.access_token = json.get("access_token").getAsString();
					wechat.save();
				}
				else{
					WeChat wechat = (WeChat) WeChat.findAll().get(0);
					wechat.access_token = json.get("access_token").getAsString();
					wechat.save();
				}
			}
			else{
				System.out.println(json.get("errcode"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
