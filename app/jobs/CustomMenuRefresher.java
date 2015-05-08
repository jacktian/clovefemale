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
		String json = "{'button':[{'type':'click','name':'今日歌曲','key':'V1001_TODAY_MUSIC'},{'type':'click','name':'今日歌曲','key':'V1001_TODAY_MUSIC'}]}";
		request.body = json;
		HttpResponse resp = request.post();
		JsonElement jsonElement = resp.getJson();
		JsonObject rtnJson = jsonElement.getAsJsonObject();
		System.out.println("创建自定义菜单结果:"+rtnJson.toString());
	}
}
