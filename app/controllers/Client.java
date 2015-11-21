package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import java.util.Map;

import javax.persistence.Query;

import com.alibaba.fastjson.JSONObject;
import jobs.AccessTokenRefresher;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import play.Logger;
import play.Play;
import play.cache.CacheFor;
import play.db.jpa.JPA;
import play.db.jpa.Model;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.WS.WSRequest;
import play.mvc.*;
import utils.Sign;
import models.Baby;
import models.Medicine;
import models.User;
import beans.MedicineBean;
import beans.UserCenterBean;
import utils.MsgUtil;

/**
 * 客户端菜单
 *
 * @author boxiZen
 * @since 2015/03/23
 */
public class Client extends WebService{

	/**
	 * 拦截器
	 */
	@Before(unless={"record","first","vaccine","remind","medicine"})
	public static void getCrtUser(){
		String appKey = Play.configuration.getProperty("wechat_appkey");
		String appSecret = Play.configuration.getProperty("wechat_secret");
		String code = params.get("code");
		try{
			String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appKey+"&secret="+appSecret+"&code="+code+"&grant_type=authorization_code";
			JsonElement jsonElement = WS.url(requestUrl).get().getJson();
			JsonObject json = jsonElement.getAsJsonObject();
			String openid = json.get("openid").getAsString();
			session.put("openid", openid);
		}
		catch(Exception e){
			Logger.info("Unable to get user's openid");
		}
	}


	/**
	 * 记录控首页
	 */
	public static void record(){
		render("/Client/record/mense.html");
	}

	/**
	 * 女性助手
	 */
	public static void pregMense(){
		System.out.println("用户openid"+session.get("openid"));
		render("/Client/record/mense.html");
	}

	/**
	 * 小药箱
	 */
	public static void medBox(){
		render("/Client/record/medBox.html");
	}

	/**
	 * 药品列表
	 */
	public static void medicine(String medBoxId){
		params.remove(medBoxId);
		session.put("medboxid", medBoxId);
		render("/Client/record/medicine.html");
	}

	/**
	 * 药品详情
	 */
	public static void medicineDetail(String medicineId){
		Medicine med = Medicine.findById(medicineId);
		MedicineBean medicine = new MedicineBean();
		medicine.id = med.id;
		medicine.name = med.name;
		if(med.produce!=null)
			medicine.produce = med.produce.toString().split(" ")[0].replaceAll("-", "/");
		if(med.deadline!=null)
			medicine.deadline = med.deadline.toString().split(" ")[0].replaceAll("-", "/");
		medicine.code = med.code;
		medicine.medicineBoxId = med.medicineBoxId;
		medicine.function = med.function;
		render("/Client/record/medicineDetail.html",medicine);
	}

	/**
	 *我的孩子
	 **/
	public static void mybaby(){
		redirect("/Client/babyRedirect");
	}

	public static void babyRedirect(){
		render("/Client/record/mybaby.html");
	}

	/**
	 *孩子资料
	 **/
	public static void babyInf(){
		render("/Client/record/babyInf.html");
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
	public static void psnCenter(){
		render("/Client/record/psnCenter.html");
	}

	/**
	 *会员制度
	 */
	public static void rules(){
		render("/Client/personal/rules.html");
	}

	/**
	 *提醒
	 **/
	public static void remind(){
		render("/Client/personal/remind.html");
	}

	/**
	 *修改手机号
	 **/
	public static void modifyPhone(){
		render("/Client/personal/modifyPhone.html");
	}


	/**
	 *测试
	 **/
	public static void infTest(){
		render("/Client/personal/infTest.html");
	}


	/**
	 *首页
	 **/
	public static void first(){
		render("/Client/personal/first.html");
	}

	/**
	*身体指标详细分析
	*/
	public static void biDetail(){
		render("/Client/record/growthBiDetail.html");
	}

	/**
	*成绩详细分析
	*/
	public static void gfDetail(){
		render("/Client/record/gradeDetail.html");
	}

	/**
	 *小工具
	 */
	public static void stools(){
		render("/Client/record/stools.html");
	}

	/**
	 *产检时间
	 */
	public static void checkTime(){
		render("/Client/record/checkTime.html");
	}

	/**
	 *产检项目
	 */
	public static void checkList(){
		render("/Client/record/checkList.html");
	}

	/*
	 *单位换算
	 */
	public static void unitChange(){
		render("/Client/record/unitChange.html");
	}

	/*
	 * 测试
	 */
	public static void tplSend() {

		String openid = "o7NE5wJ9U0Gjes3geAnq-0oly5vI";
		utils.MsgUtil.sengMsg(openid);
	}
}
