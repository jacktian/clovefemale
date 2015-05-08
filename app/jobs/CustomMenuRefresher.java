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

@Every("50min")
public class CustomMenuRefresher extends Job {
	
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
		                     	"\"url\":\"http://clovefemale.boxizen.com/client/pregMense\""+
		                     "},"+
		                     "{"+
		                     	"\"type\":\"view\","+
		                     	"\"name\":\"宝宝成长\","+
		                     	"\"url\":\"http://clovefemale.boxizen.com/client/babyRecord\""+
		                     "},"+
		                     "{"+
		                     	"\"type\":\"view\","+
		                     	"\"name\":\"小药箱\","+
		                     	"\"url\":\"http://clovefemale.boxizen.com/client/medBox\""+
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
		                     	"\"url\":\"http://clovefemale.boxizen.com/client/psnCenter\""+
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
