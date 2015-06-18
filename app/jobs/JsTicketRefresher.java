package jobs;

import models.WeChat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import play.Play;
import play.jobs.Every;
import play.jobs.Job;
import play.libs.WS;
import play.libs.WS.HttpResponse;

@Every("110min")
public class JsTicketRefresher extends Job {
	
	@Override
	public void doJob(){
		/*首先获取accessToken的值，直接从数据库取出即可*/
		WeChat bean = new WeChat();
		bean = (WeChat) WeChat.findAll().get(0);
		String accessToken = bean.access_token;
		/* 调用微信接口获取jsapi_ticket */
		HttpResponse resp = WS.url("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi").get();
		JsonElement jsonElement = resp.getJson();
		JsonObject json = jsonElement.getAsJsonObject();
		try{
			if(json.get("ticket").getAsString() != null){
				/* 将js_ticket保存到数据库中 */
				if(WeChat.findAll().size() == 0){
					WeChat wechat = new WeChat();
					wechat.access_token = accessToken;
					wechat.js_ticket = json.get("ticket").getAsString();
					wechat.save();
				}
				else{
					WeChat wechat = (WeChat) WeChat.findAll().get(0);
					wechat.access_token = accessToken;
					wechat.js_ticket = json.get("ticket").getAsString();
					wechat.save();
				}
			}
			else{
				System.out.println(json.get("ticket errcode"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*立即获得js_ticket(测试时使用)*/
	public static void getJsTicket(){
		/*首先获取accessToken的值，直接从数据库取出即可*/
		WeChat bean = new WeChat();
		bean = (WeChat) WeChat.findAll().get(0);
		String accessToken = bean.access_token;
		/* 调用微信接口获取jsapi_ticket */
		HttpResponse resp = WS.url("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi").get();
		JsonElement jsonElement = resp.getJson();
		JsonObject json = jsonElement.getAsJsonObject();
		/*打印js_ticket的值*/
		// System.out.println("accessToken:"+accessToken+"js_ticket:"+json.get("ticket").getAsString());
		try{
			if(json.get("ticket").getAsString() != null){
				/* 将js_ticket保存到数据库中 */
				if(WeChat.findAll().size() == 0){
					WeChat wechat = new WeChat();
					wechat.access_token = accessToken;
					wechat.js_ticket = json.get("ticket").getAsString();
					wechat.save();
				}
				else{
					WeChat wechat = (WeChat) WeChat.findAll().get(0);
					wechat.access_token = accessToken;
					wechat.js_ticket = json.get("ticket").getAsString();
					wechat.save();
				}
			}
			else{
				System.out.println(json.get("ticket errcode"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
