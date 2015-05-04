package controllers;

import java.util.Date;
import java.util.List;



import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import play.cache.CacheFor;
import play.db.jpa.Model;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.mvc.*;
import utils.Sign;

/**
 * 客户端控制器
 * 
 * @author boxiZen
 * @since 2015/03/23
 */
public class Client extends WebService{
	
	/**
	 * 记录控首页
	 */
	public static void record(){
		render("/Client/record/mense.html");
	}

	/**
	 * 助孕记录
	 */
	public static void pregMense(){
		render("/Client/record/mense.html");
	}

	/**
	 * 小药箱
	 */
	public static void medBox(){
		render("/Client/record/medBox.html");
	}	

	/**
	 * 药品详情
	 */
	public static void medicine(){
		render("/Client/record/medicine.html");
	}		

	/**
	 *我的孩子
	 **/
	public static void mybaby(){
		render("/Client/record/mybaby.html");
	}

	/**
	 *孩子成长记录
	 **/
	public static void babyRecord(){
		render("/Client/record/babyRecord.html");
	}


	/**
	 *疫苗接种
	 **/
	public static void vaccine(){
		render("/Client/record/vaccine.html");
	}
	
	/**
	 * 获取jsapi_ticket
	 */
	public static void medBoxWx(){
		String timestamp = "";
		String nonceStr = "";
		String signature = "";
		try{
			/* 获取当前页面*/
			String crtUrl = "http://clovefemale.boxizen.com/medboxwx";
			/* 获取access_token*/
			models.WeChat wechat = (models.WeChat) models.WeChat.findAll().get(0);
			String accessToken = wechat.access_token;
	
			/* 调用微信接口获取jsapi_ticket */
			HttpResponse resp = WS.url("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi").get();
			JsonElement jsonElement = resp.getJson();
			JsonObject json = jsonElement.getAsJsonObject();
			String ticket = json.get("ticket").getAsString();
			Map<String,String> ret = Sign.process(ticket, crtUrl);
			for (Map.Entry entry : ret.entrySet()) {
	            System.out.println(entry.getKey() + ", " + entry.getValue());
	        }
			timestamp = ret.get("timestamp");
			nonceStr = ret.get("nonceStr");
			signature = ret.get("signature");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			render("/Client/record/medBox.html",timestamp,nonceStr,signature);
		}
	}
}

