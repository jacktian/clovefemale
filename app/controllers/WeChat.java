package controllers;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import models.*;

import models.Client;
import org.h2.store.Page;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import beans.PicArticle;
import beans.WeChatBean;
import beans.WeChatJsConfig;
import beans.WeChatPicMsgResponse;
import beans.WeChatResponse;

import com.google.gson.JsonArray;
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
import utils.MedboxInit;
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
	public static void process() {
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
						MedboxInit.init(openid);

					}

					// 自动回复用户信息
					/*WeChatResponse resp1 = new WeChatResponse();
					resp1.toUserName = bean.fromUserName;
					resp1.fromUserName = bean.toUserName;
					resp1.createTime = bean.createTime;
					resp1.msgType = "text";
					resp1.content = "欢迎您的到来。我们希望为家庭开发实用的工具小软件，如果还需增加您想要的功能，可以按屏幕左下角，发送语音或文字提醒我们，帮助我们成长。本软件的使用方法可在个人中心找到。本公众号的理念：家庭助理，助力家庭。";
					renderText(resp1);*/

					WeChatPicMsgResponse response = new WeChatPicMsgResponse();
					response.fromUserName = bean.toUserName;
					response.toUserName = bean.fromUserName;
					response.msgType = "news";
					response.createTime = System.currentTimeMillis();

					List<PicArticle> list = new ArrayList();
					PicArticle a1 = new PicArticle();
					//List<Article> articleList = Article.find("byTypeAndPrioty","2","1").fetch();
					//if(articleList.size()>0) {
						a1.title = "关于家庭助理";
						a1.picUrl = "http://mmbiz.qpic.cn/mmbiz/iad5fRhd8Vp1ImoPHN0tsMSTEomNVM8ae6JcicNmHicOxk9DzwWkJHRmCegNQbtTmKKcYCgTjjibycD6dq2UicobKGw/640?wx_fmt=jpeg&tp=webp&wxfrom=5";
						a1.url = "http://mp.weixin.qq.com/s?__biz=MzIyNTAxNDc2NA==&mid=403403758&idx=1&sn=ac6aa38fff7a7742d9c998c6e5f7c62a&scene=0#wechat_redirect";
						a1.desciption = "关于家庭助理";
						list.add(a1);
					//}

					PicArticle a2 = new PicArticle();
					//articleList = Article.find("byTypeAndPrioty","2","2").fetch();
					//if(articleList.size()>0) {
						a2.title = "使用声明";
						a2.picUrl = "http://mmbiz.qpic.cn/mmbiz/iad5fRhd8Vp1yKhCmQprGibcRl54ZZ3MCE9106mGMYT9O3D4xibvJeXP8hzNSc5TLAIG7ibUgJxKEbUNiapNaxIDrOA/640?wx_fmt=jpeg&tp=webp&wxfrom=5";
						a2.url = "http://mp.weixin.qq.com/s?__biz=MzIyNTAxNDc2NA==&mid=211340236&idx=2&sn=e7b269b7206ef58785af413f5ed19143&scene=4#wechat_redirect";
						a2.desciption = "使用声明";
						list.add(a2);
					//}

					PicArticle a3 = new PicArticle();
					//articleList = Article.find("byTypeAndPrioty","2","3").fetch();
					//if(articleList.size()>0) {
						a3.title = "常见问题";
						a3.picUrl = "http://mmbiz.qpic.cn/mmbiz/iad5fRhd8Vp1yKhCmQprGibcRl54ZZ3MCE3dxhlZGhPnxUYiaAYxvJ8eD5hTjluhuez5J1pib8ZJq9pXwhicY4xkySg/640?wx_fmt=jpeg&tp=webp&wxfrom=5";
						a3.url = "http://mp.weixin.qq.com/s?__biz=MzIyNTAxNDc2NA==&mid=211340236&idx=3&sn=26b5067951fab54db46677cb139aec8a&scene=4#wechat_redirect";
						a3.desciption = "常见问题";
						list.add(a3);
					//}

					response.articleList = list;
					response.articleCount = list.size();
					renderText(response);
		
				}
				//丁香资讯
				else if(bean.event.equals("CLICK")){
					Element eventKey = doc.getElementsByTag("EventKey").get(0);
					String key = eventKey.html();
					String developId = bean.toUserName;
					String userId = bean.fromUserName;
					WeChatPicMsgResponse response = new WeChatPicMsgResponse();
					response.fromUserName = developId;
					response.toUserName = userId;
					response.msgType = "news";
					response.createTime = System.currentTimeMillis();
					//丁香咨询
					if(key.equals("V001_CloveMsg")){

						PicArticle a1 = new PicArticle();
						List<PicArticle> list = new ArrayList();

						List<Article> articleList = Article.find("byTypeAndPrioty","1","1").fetch();
						if(articleList.size()>0) {
							a1.title = articleList.get(0).title;
							a1.picUrl = articleList.get(0).picUrl;
							a1.url = articleList.get(0).url;
							a1.desciption = articleList.get(0).description;
							list.add(a1);
						}

						PicArticle a2 = new PicArticle();
						articleList = Article.find("byTypeAndPrioty","1","2").fetch();
						if(articleList.size()>0) {
							a2.title = articleList.get(0).title;
							a2.picUrl = articleList.get(0).picUrl;
							a2.url = articleList.get(0).url;
							a2.desciption = articleList.get(0).description;
							list.add(a2);
						}

						PicArticle a3 = new PicArticle();
						articleList = Article.find("byTypeAndPrioty","1","3").fetch();
						if(articleList.size()>0) {
							a3.title = articleList.get(0).title;
							a3.picUrl = articleList.get(0).picUrl;
							a3.url = articleList.get(0).url;
							a3.desciption = articleList.get(0).description;
							list.add(a3);
						}
						response.articleList = list;
						response.articleCount = list.size();
						renderText(response);
					}else if(key.equals("V002_CloveMsg")){

						PicArticle a1 = new PicArticle();
						List<PicArticle> list = new ArrayList();
						List<Article> articleList = Article.find("byTypeAndPrioty","2","1").fetch();
						if(articleList.size()>0) {
							a1.title = articleList.get(0).title;
							a1.picUrl = articleList.get(0).picUrl;
							a1.url = articleList.get(0).url;
							a1.desciption = articleList.get(0).description;
							list.add(a1);
						}

						PicArticle a2 = new PicArticle();
						articleList = Article.find("byTypeAndPrioty","2","2").fetch();
						if(articleList.size()>0) {
							a2.title = articleList.get(0).title;
							a2.picUrl = articleList.get(0).picUrl;
							a2.url = articleList.get(0).url;
							a2.desciption = articleList.get(0).description;
							list.add(a2);
						}

						PicArticle a3 = new PicArticle();
						articleList = Article.find("byTypeAndPrioty","2","3").fetch();
						if(articleList.size()>0) {
							a3.title = articleList.get(0).title;
							a3.picUrl = articleList.get(0).picUrl;
							a3.url = articleList.get(0).url;
							a3.desciption = articleList.get(0).description;
							list.add(a3);
						}

						response.articleList = list;
						response.articleCount = list.size();
						renderText(response);
					}
					//会员绑定
					else if(key.equals("V002_MemberBind")){
						PicArticle a1 = new PicArticle();
						a1.title = "会员绑定";
						a1.picUrl = "http://dwz.cn/NIxL8";
						a1.url = "http://dwz.cn/NICAq";
						a1.desciption = "点击链接开始绑定";
						List<PicArticle> articleList = new ArrayList();
						articleList.add(a1);
						response.articleList = articleList;
						response.articleCount = articleList.size();
						renderText(response);
					}
					//常见问题
					else if(key.equals("V003_CommonQuestion")){
						PicArticle a1 = new PicArticle();
						a1.title = "常见问题";
						a1.picUrl = "http://t.im/questionsimg";
						a1.url = "http://t.im/questions";
						a1.desciption = "点击链接了解常见问题";
						PicArticle a2 = new PicArticle();
						a2.title = "女性助手";
						a2.picUrl = "http://t.im/preimg";
						a2.url = "http://t.im/pregnant";
						a2.desciption = "点击链接了解女性助手";
						PicArticle a3 = new PicArticle();
						a3.title = "宝宝成长";
						a3.picUrl = "http://t.im/growthimg";
						a3.url = "http://t.im/babygrowth";
						a3.desciption = "点击链接了宝宝成长";
						PicArticle a4 = new PicArticle();
						a4.title = "小药箱";
						a4.picUrl = "http://t.im/meding";
						a4.url = "http://t.im/medicine";
						a4.desciption = "点击链接了解小药箱";
						List<PicArticle> articleList = new ArrayList();
						articleList.add(a1);
						articleList.add(a2);
						articleList.add(a3);
						articleList.add(a4);
						response.articleList = articleList;
						response.articleCount = articleList.size();
						renderText(response);
					}
					//联系我们
					else if(key.equals("V004_ContactUs")){
						PicArticle a1 = new PicArticle();
						a1.title = "联系我们";
						a1.picUrl = "http://t.im/contactimg";
						a1.url = "http://t.im/contactus";
						a1.desciption = "点击链接联系我们";
						List<PicArticle> articleList = new ArrayList();
						articleList.add(a1);
						response.articleList = articleList;
						response.articleCount = articleList.size();
						renderText(response);
					}
					//关于我们
					else if(key.equals("V005_AboutUs")){
						PicArticle a1 = new PicArticle();
						a1.title = "关于我们";
						a1.picUrl = "http://t.im/aboutimg";
						a1.url = "http://t.im/aboutus";
						a1.desciption = "丁香女性助手是酷聚物联网科技有限公司旗下的一款基于母婴的公众号平台";
						List<PicArticle> articleList = new ArrayList();
						articleList.add(a1);
						response.articleList = articleList;
						response.articleCount = articleList.size();
						renderText(response);
					}
					
				}
			}
		}
	}
	
	/**
	 * 获取access_token
	 */
	public static void getAccessToken() {
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
		Map<String,String>ret = Sign.create_sign(url);
		String timestamp = ret.get("timestamp");
		String nonceStr = ret.get("nonceStr");
		String signature = ret.get("signature");
		WeChatJsConfig config = new WeChatJsConfig();
		config.timestamp = timestamp;
		config.nonceStr = nonceStr;
		config.signature = signature;
		System.out.println(timestamp+","+nonceStr+","+signature+",");
		wsOk(config);
	}
	
	/**
	 * 获取当前用户列表
	 */
	public static void getCurUserList(){
		/*首先获取accessToken的值，直接从数据库取出即可*/
		models.WeChat wxbean = new models.WeChat();
		wxbean = (models.WeChat) models.WeChat.findAll().get(0);
		String accessToken = wxbean.access_token;
		String api = "https://api.weixin.qq.com/cgi-bin/user/get?access_token="+accessToken;
		HttpResponse resp = WS.url(api).get();
		JsonElement jsonElement = resp.getJson();
		JsonObject json = jsonElement.getAsJsonObject();
		JsonObject jObj = json.getAsJsonObject("data");
		JsonArray openidArr = jObj.get("openid").getAsJsonArray();
		for(int i=0;i<openidArr.size();i++){
			System.out.println(openidArr.get(i).getAsString());
		}
		wsOk(openidArr);
	}
}

