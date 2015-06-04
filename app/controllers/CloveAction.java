package controllers;

import java.util.ArrayList;
import java.util.List;

import beans.PicArticle;
import beans.WeChatPicMsgResponse;

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
		String developId = Play.configuration.getProperty("developerId");
		String userId = session.get("openid");
		WeChatPicMsgResponse response = new WeChatPicMsgResponse();
		response.fromUserName = developId;
		response.toUserName = userId;
		response.msgType = "news";
		response.createTime = System.currentTimeMillis();
		PicArticle a1 = new PicArticle();
		a1.title = "丁香女性资讯平台";
		a1.picUrl = "http://ec8.images-amazon.com/images/I/71i7rL8F46L._SL500_AA300_.png";
		a1.url = "http://www.baidu.com/link?url=PQul1MKBtDqPgHh0qaQBv2HKRUeS0k4b2sYuEy2LGdRC9Mycm1Y_Jet2OPALOmyVLrTR6IZzYml1B0GgArokbq";
		a1.desciption = "相关描述可点击链接获得";
		PicArticle a2 = new PicArticle();
		a2.title = "丁香女性资讯平台";
		a2.picUrl = "http://ec8.images-amazon.com/images/I/71i7rL8F46L._SL500_AA300_.png";
		a2.url = "http://www.baidu.com/link?url=PQul1MKBtDqPgHh0qaQBv2HKRUeS0k4b2sYuEy2LGdRC9Mycm1Y_Jet2OPALOmyVLrTR6IZzYml1B0GgArokbq";
		a2.desciption = "相关描述可点击链接获得";
		List<PicArticle> articleList = new ArrayList();
		articleList.add(a1);
		articleList.add(a2);
		response.articleList = articleList;
		renderText(response);
	}
}
