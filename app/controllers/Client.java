package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import java.util.Map;

import javax.persistence.Query;

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
			params.remove("code");
			params.remove("state");
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
	 * 助孕记录
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
		medicine.produce = med.produce.toString().split(" ")[0].replaceAll("-", "/");
		medicine.deadline = med.deadline.toString().split(" ")[0].replaceAll("-", "/");
		medicine.code = med.code;
		medicine.medicineBoxId = med.medicineBoxId;
		render("/Client/record/medicineDetail.html",medicine);
	}		
	
	/**
	 *我的孩子
	 **/
	public static void mybaby(){
		params.remove("code");
		params.remove("state");
		System.out.println(params.all());
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
		String openid = session.get("openid");
		if(openid == null){
			openid = "ob1R-uIRkLLp6lmmrT4w-2rrZ5jQ";
		}
		User userModel = User.find("byOpenid", openid).first();
		UserCenterBean user = new UserCenterBean();

		//头像
		user.headimgurl = userModel.headimgurl ;

		//昵称
		user.nickname = userModel.nickname;

		//丁香号
		if(userModel.cloveId == null || "".equals(userModel.cloveId)){
			user.cloveId = "未设置";
		}else{
			user.cloveId = userModel.cloveId;
		}

		//个性签名
		if(userModel.signName == null || "".equals(userModel.signName)){
			user.signName = "点击设置心情";
		}else{
			user.signName = userModel.signName;
		}

		//加V
		if(userModel.isAddV == null || userModel.isAddV == false){
			user.isAddV = "未加V";
		}else{
			user.isAddV = "已加V";
		}

		//用户等级
		user.userGrade = userModel.userGrade;

		//手机
		if(userModel.phoneNum == null || "".equals(userModel.phoneNum)){
			user.phoneNum = "未绑定";
		}else{
			user.phoneNum = userModel.phoneNum;
		}

		//邮箱
		if(userModel.email == null || "".equals(userModel.email)){
			user.email = "未绑定";
		}else{
			user.email = userModel.phoneNum;
		}

		render("/Client/record/psnCenter.html",user);
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

}
