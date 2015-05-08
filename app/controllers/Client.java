package controllers;

import java.util.Date;
import java.util.List;



import java.util.Map;

import jobs.AccessTokenRefresher;

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
	
	@Before(unless={"record","first"})
	public static void getCrtUser(){
		String code = params.get("code");
		System.out.println("code:"+code);
	}
	
	
	/**
	 * 记录控首页
	 */
	public static void record(){
		render("/Client/record/mense.html");
	}

	/**
	 * 助孕记录
	 */
	public static void pregMense(String code){
		System.out.println("preg code:"+code);
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
	 *会员中心
	 **/
	public static void psnCenter(String openid){
		models.Client client = models.Client.find("byOpenid", openid).first();
		render("/Client/record/psnCenter.html",client);
	}

	/**
	 *提醒
	 **/
	public static void remind(){
		render("/Client/personal/remind.html");
	}


	/**
	 *首页
	 **/
	public static void first(){
		render("/Client/personal/first.html");
	}


}

