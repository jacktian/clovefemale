package controllers;

import java.util.Date;
import java.util.List;

import javax.mail.Session;

import models.BabyVac;
import models.Remind;
import models.Baby;

import utils.DateUtil;
/**
 * 宝宝疫苗控制器
 * @author cater
 *
 */
public class CVaccineAction extends WebService{
	/*
	 * 修改疫苗提醒时间
	 */
	public static boolean modifyRemindTime(String babyId){
		String openid = session.get("openid");
		List<Remind> remindList = null;
		try{
			remindList = Remind.find("byOpenid", openid).fetch();
		}catch(Exception e){
			throw e;
		}
		Remind remind = null;
		if(remindList != null && remindList.size() != 0){
			remind = remindList.get(0);
		}else{
			return false;//("没有找到该用户提醒信息");
		}
		Baby baby = Baby.findById(babyId);
		if(baby == null){
			return false;//wsError("没有找到宝宝信息");
		}
		
		Date birthday = baby.date;
		int advDay = remind.yi_adv_day;
		List<BabyVac> babyVacList = BabyVac.find("byBabyId", babyId).fetch();
		if(babyVacList != null && babyVacList.size() != 0){
			for(int i = 0;  i < babyVacList.size(); i++){
				BabyVac babyVac = babyVacList.get(i);
				babyVac.remindTime = DateUtil.dateAdd(birthday,advDay);
				babyVac.save();
			}
		}
		return true;
	}
}
