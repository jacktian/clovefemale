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
	
	/* 从配置文件中获取信息 */
	public static String appKey = Play.configuration.getProperty("wechat_appkey");
	public static String appSecret = Play.configuration.getProperty("wechat_secret");
	public static String pregUrl = Play.configuration.getProperty("pregUrl");
	public static String babyUrl = Play.configuration.getProperty("babyUrl");
	public static String medUrl = Play.configuration.getProperty("medUrl");
	public static String psnUrl = Play.configuration.getProperty("psnUrl");
	public static String cloveUrl = Play.configuration.getProperty("cloveUrl");
	
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
		                     	"\"name\":\"家庭药箱\","+
		                     	"\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appKey+"&redirect_uri="+medUrl+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect\""+
		                     "},"+
		                     "{"+
		                     	"\"type\":\"view\","+
		                     	"\"name\":\"常用工具\","+
		                     	"\"url\":\"http://sunyanmi.com/client/stools\""+
		                     "}"+
		                  "]"+
		               "},"+
		             //记录控结束
		             //丁香资讯开始
		             /*
		             "{" +
		             "\"name\":\"丁香资讯\","+
		             "\"type\":\"click\","+
		             "\"key\":\"V001_CloveMsg\""+
		             "},"+
		             */
                     "{\"name\":\"资讯\","+
	                     "\"sub_button\":["+
		                     "{"+
				             "\"name\":\"实用资讯\","+
				             "\"type\":\"click\","+
				             "\"key\":\"V001_CloveMsg\""+
				             "},"+
		                     "{"+
				             "\"name\":\"博士观点\","+
				             "\"type\":\"click\","+
				             "\"key\":\"V002_CloveMsg\""+
				             "},"+
		                  "]"+
		               "},"+
		             //丁香资讯结束
		             //丁香会员开始
				 	"{\"name\":\"个人中心\","+
				 	 "\"type\":\"view\","+
				 	 "\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appKey+"&redirect_uri="+psnUrl+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect\""+
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
