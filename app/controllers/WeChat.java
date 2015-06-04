package controllers;



import java.util.List;
import java.util.Map;






import models.Client;
import models.MedicineBox;
import models.User;

import org.h2.store.Page;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import beans.WeChatBean;
import beans.WeChatJsConfig;
import beans.WeChatResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import play.Logger;
import play.Play;
import play.cache.CacheFor;
import play.db.jpa.Model;
import play.libs.IO;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.WS.WSRequest;
import play.mvc.*;
import utils.SignUtil;
import utils.Sign;
import utils.UserInitUtil;


/**
 * 微信控制器
 * 
 * @author boxiZen
 * @since 2015/03/23
 */
public class WeChat extends WebService{
	
	/**
	 * 接收来自微信服务器的消息
	 */
	public static void process(){
		//如果是get请求，则执行令牌验证
		if(request.method.toLowerCase().equals("get")){
			if(SignUtil.checkSignature(params.get("signature"), params.get("timestamp"), params.get("nonce"))){
				renderText(params.get("echostr"));
			}	
		}
		//否则为微信服务器转发用户的信息
		else{
			//读取XML信息
			List<String> lines = IO.readLines(request.body);
			StringBuilder strBuilder = new StringBuilder();
			for(String s : lines){
				strBuilder.append(s);
			}
			//System.out.println(strBuilder.toString());
			//解析XML信息
			Document doc = Jsoup.parse(strBuilder.toString());
			Element toUserNameE = doc.getElementsByTag("ToUserName").get(0);
			Element fromUserNameE = doc.getElementsByTag("FromUserName").get(0);
			Element createTimeE = doc.getElementsByTag("CreateTime").get(0);
			Element msgTypeE = doc.getElementsByTag("MsgType").get(0);
			
			//存储xml信息
			WeChatBean bean = new WeChatBean();
			bean.toUserName = toUserNameE.html();
			bean.fromUserName = fromUserNameE.html();
			bean.createTime = Long.parseLong(createTimeE.html());
			bean.msgType = msgTypeE.html();

			//如果发送的是文本消息
			if(bean.msgType.equals("text")){
				Element content = doc.getElementsByTag("Content").get(0);
				bean.content = content.html();
				WeChatResponse resp = new WeChatResponse();
				resp.toUserName = bean.fromUserName;
				resp.fromUserName = bean.toUserName;
				resp.createTime = bean.createTime;
				resp.msgType = bean.msgType;
				resp.content = bean.content;
				if(bean.content.equals("1")){
					resp.content = bean.content;
				}
				else{
					resp.content = "欢迎关注丁香女性助手，请点击菜单获取你想要的信息";
				}
				renderText(resp);
			}
			//如果事件响应
			else if(bean.msgType.equals("event")){
				Element event = doc.getElementsByTag("Event").get(0);
				bean.event = event.html();
				//如果是取消关注事件
				if(bean.event.equals("unsubscribe")){
					System.out.println("用户openId:"+bean.fromUserName+",取消关注");
				}
				//如果是关注事件
				else if(bean.event.equals("subscribe")){
					/*首先获取accessToken的值，直接从数据库取出即可*/
					models.WeChat wxbean = new models.WeChat();
					wxbean = (models.WeChat) models.WeChat.findAll().get(0);
					String accessToken = wxbean.access_token;
					/*调用用户信息接口*/
					HttpResponse resp = WS.url("https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+bean.fromUserName+"&lang=zh_CN").get();
					JsonElement jsonElement = resp.getJson();
					JsonObject json = jsonElement.getAsJsonObject();
					/*存储用户的信息*/
					String openid = json.get("openid").getAsString();
					System.out.println(json.toString());
					User client = User.find("byOpenid", openid).first();
					if(client == null){
						client = new User();
						client.subscribe = json.get("subscribe").getAsString();
						client.openid = openid;
						client.nickname = json.get("nickname").getAsString();
						client.sex = json.get("sex").getAsString();
						client.language = json.get("language").getAsString();
						client.city = json.get("city").getAsString();
						client.province = json.get("province").getAsString();
						client.country = json.get("country").getAsString();
						client.headimgurl = json.get("headimgurl").getAsString();
						client.subscribe_time =json.get("subscribe_time").getAsString();
						//client.unionid = json.get("unionid").getAsString();
						client.save();
						//新建药箱
						UserInitUtil.initMedBox(openid);
					}
		
				}
			}
		}
	}
	
	/**
	 * 快速获取access_token
	 */
	public static void getAccessToken(){
		/* 从配置文件中获取appKey与appSecret */
		String appKey = Play.configuration.getProperty("wechat_appkey");
		String appSecret = Play.configuration.getProperty("wechat_secret");
		
		/* 调用微信接口获取access_token */
		HttpResponse resp = WS.url("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appKey+"&secret="+appSecret).get();
		JsonElement jsonElement = resp.getJson();
		JsonObject json = jsonElement.getAsJsonObject();
		
		/* 保存access_token*/
		models.WeChat wechat = (models.WeChat) models.WeChat.findAll().get(0);
		wechat.access_token = json.get("access_token").getAsString();
		wechat.save();
	}
	
	/**
	 * 获取jsapi_ticket
	 */
	public static void jsApiCall(String url){
		System.out.println(url);
		Map<String,String>ret = Sign.create_sign(url);
		String timestamp = ret.get("timestamp");
		String nonceStr = ret.get("nonceStr");
		String signature = ret.get("signature");
		WeChatJsConfig config = new WeChatJsConfig();
		config.timestamp = timestamp;
		config.nonceStr = nonceStr;
		config.signature = signature;
		wsOk(config);
	}
	
	/**
	 * 发送图文信息
	 */
	public static void sendPicMsg(){
		
	}
}

