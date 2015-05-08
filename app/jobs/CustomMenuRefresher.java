package jobs;

import models.WeChat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import play.Play;
import play.jobs.Every;
import play.jobs.Job;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.WS.WSRequest;

public class CustomMenuRefresher extends Job {
	
	/* 从配置文件中获取appKey与appSecret */
	public static String appKey = Play.configuration.getProperty("wechat_appkey");
	public static String appSecret = Play.configuration.getProperty("wechat_secret");
	/* 返回的Url地址 */
	public static String pregUrl = "http://clovefemale.boxizen.com/client/pregMense";
	public static String babyUrl = "http://clovefemale.boxizen.com/client/mybaby";
	public static String medUrl = "http://clovefemale.boxizen.com/client/medBox";
	public static String psnUrl = "http://clovefemale.boxizen.com/client/psnCenter";
	
	@Override
	public void doJob(){
		/*首先获取accessToken的值，直接从数据库取出即可*/
		WeChat bean = new WeChat();
		bean = (WeChat) WeChat.findAll().get(0);
		String accessToken = bean.access_token;
		/* 调用微信接口获取创建菜单 */
		WSRequest request = WS.url("https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+accessToken);
		String json = "{'button':[{'type':'click','name':'今日歌曲','key':'V1001_TODAY_MUSIC'},{'type':'click','name':'今日歌曲','key':'V1001_TODAY_MUSIC'}]}";
		request.body = json;
		request.post();
	}
	
	/*立即创建自定义菜单*/
	public static void createMenuNow(){
		/*首先获取accessToken的值，直接从数据库取出即可*/
		WeChat bean = new WeChat();
		bean = (WeChat) WeChat.findAll().get(0);
		String accessToken = bean.access_token;
		/* 调用微信接口创建菜单 */
		WSRequest request = WS.url("https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+accessToken);
		 String responeJsonStr = "{"+
                 "\"button\":["+
				 	 //记录控开始
                     "{\"name\":\"记录控\","+
	                     "\"sub_button\":["+
		                     "{"+
		                     	"\"type\":\"view\","+
		                     	"\"name\":\"助孕记录\","+  
		                     	"\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appKey+"&redirect_uri="+pregUrl+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect\""+
		                     "},"+
		                     "{"+
		                     	"\"type\":\"view\","+
		                     	"\"name\":\"宝宝成长\","+
		                     	"\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appKey+"&redirect_uri="+babyUrl+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect\""+
		                     "},"+
		                     "{"+
		                     	"\"type\":\"view\","+
		                     	"\"name\":\"小药箱\","+
		                     	"\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appKey+"&redirect_uri="+medUrl+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect\""+
		                     "},"+
		                     "{"+
		                     	"\"type\":\"view\","+
		                     	"\"name\":\"小工具\","+
		                     	"\"url\":\"http://www.baidu.com\""+
		                     "}"+
		                  "]"+
		               "},"+
		             //记录控结束
		             //丁香资讯开始
		             "{\"name\":\"丁香资讯\","+
		             "\"type\":\"view\","+
		             "\"url\":\"http://www.baidu.com\""+
		             "},"+
		             //丁香资讯结束
		             //丁香会员开始
		             "{\"name\":\"丁香会员\","+
	                     "\"sub_button\":["+
		                     "{"+
		                     	"\"type\":\"view\","+
		                     	"\"name\":\"会员中心\","+
		                     	"\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appKey+"&redirect_uri="+psnUrl+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect\""+
		                     "},"+
		                     "{"+
		                     	"\"type\":\"view\","+
		                     	"\"name\":\"会员绑定\","+
		                     	"\"url\":\"http://www.baidu.com\""+
		                     "},"+
		                     "{"+
		                     	"\"type\":\"view\","+
		                     	"\"name\":\"常见问题\","+
		                     	"\"url\":\"http://www.baidu.com\""+
		                     "},"+
		                     "{"+
		                     	"\"type\":\"view\","+
		                     	"\"name\":\"联系我们\","+
		                     	"\"url\":\"http://www.baidu.com\""+
		                     "},"+
		                     "{"+
		                     	"\"type\":\"view\","+
		                     	"\"name\":\"关于我们\","+
		                     	"\"url\":\"http://www.baidu.com\""+
		                     "}"+
		                  "]"+
		               "}"+
                 "]"+
             "}";
		request.body = responeJsonStr;
		HttpResponse resp = request.post();
		JsonElement jsonElement = resp.getJson();
		JsonObject rtnJson = jsonElement.getAsJsonObject();
		System.out.println("创建自定义菜单结果:"+rtnJson.toString());
	}
}
