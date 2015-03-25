package controllers;



import java.util.List;
import java.util.Map;

import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import beans.WeChatBean;
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
			//解析XML信息
			Document doc = Jsoup.parse(strBuilder.toString());
			Element toUserNameE = doc.getElementsByTag("ToUserName").get(0);
			Element fromUserNameE = doc.getElementsByTag("FromUserName").get(0);
			Element createTimeE = doc.getElementsByTag("CreateTime").get(0);
			Element msgTypeE = doc.getElementsByTag("MsgType").get(0);
			Element contentE = doc.getElementsByTag("Content").get(0);
			Element msgIdE = null;
			try{
				msgIdE = doc.getElementsByTag("MsgId").get(0);
			}catch(Exception e){
				e.printStackTrace();
			}
			//存储xml信息
			WeChatBean bean = new WeChatBean();
			bean.toUserName = toUserNameE.html();
			bean.fromUserName = fromUserNameE.html();
			bean.createTime = Long.parseLong(createTimeE.html());
			bean.msgType = msgTypeE.html();
			bean.msgId = Long.parseLong(msgIdE.html());
			bean.content = contentE.html();
			//如果发送的是文本消息
			if(bean.msgType.equals("text")){
				WeChatResponse resp = new WeChatResponse();
				resp.toUserName = bean.fromUserName;
				resp.fromUserName = bean.toUserName;
				resp.createTime = bean.createTime;
				resp.msgType = bean.msgType;
				if(bean.content.equals("1")){
					resp.content = bean.content;
				}
				else{
					resp.content = "测试";
				}
				renderText(resp);
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
	 * 控制器测试方法
	 */
	public static void test() {
		//存储xml信息
		WeChatResponse bean = new WeChatResponse();
		bean.fromUserName = "boxizen";
		renderText(bean.toString());
	}
}

