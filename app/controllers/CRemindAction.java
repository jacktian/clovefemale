package controllers;

import java.util.List;

import models.Remind;

import utils.VacUtil;

/*
 * 提醒控制器
 * @author cater
 * @since 2015/09/15
 */
public class CRemindAction extends WebService {
	/*
	 * 打开提醒
	 * remindKind:提醒类型 “medremind”：药品到期提醒   "healremind"：生理记录提醒  “yiremind”:疫苗提醒
	 * status：0：关闭    1：打开
	 */
	public static void changeRemindStatus(String remindKind,int status){
		String openid = session.get("openid");//从session中获取openid
		Remind remind = null;
		/*if(openid == null){
			openid = "ob1R-uIRkLLp6lmmrT4w-2rrZ5jQ";
		}*/
		try{
			List<Remind> remindList = Remind.find("byOpenid", openid).fetch();
			if(remindList.size()!=0){
				remind = remindList.get(0);
			}else{
				wsError("没有找到该用户的提醒记录！");
			}
		}catch(Exception e){
			wsError("查找记录出错了");
		}
		
		if("medremind".equals(remindKind)){//药品到期提醒
			remind.medremind = status;
		}else if("healremind".equals(remindKind)){//生理记录提醒
			remind.healremind = status;
		}else{//疫苗提醒
			remind.yiremind = status;
		}
		
		try{
			remind.save();
		}catch(Exception e){
			wsError("保存提醒状态记录出错了");
		}
		wsOk("保存成功");
	}
	
	/*
	 * 修改提醒提前天数
	 * remindKind:提醒类型 “medremind”：药品到期提醒   "healremind"：生理记录提醒  “yiremind”:疫苗提醒
	 * day:提前天数
	 */
	public static void modifyAdvDay(String remindKind,int day){
		String openid = session.get("openid");//从session中获取openid
		Remind remind = null;
		/*if(openid == null){
			openid = "ob1R-uIRkLLp6lmmrT4w-2rrZ5jQ";
		}*/
		try{
			List<Remind> remindList = Remind.find("byOpenid", openid).fetch();
			if(remindList.size()!=0){
				remind = remindList.get(0);
			}else{
				wsError("没有找到该用户的提醒记录！");
			}
		}catch(Exception e){
			wsError("查找记录出错了");
		}
		
		if("medremind".equals(remindKind)){//药品到期提醒
			remind.med_adv_day = day;
		}else if("healremind".equals(remindKind)){//生理记录提醒
			remind.heal_adv_day = day;
		}else{//疫苗提醒
			remind.yi_adv_day = day;
			                    //修改疫苗接种提醒时间
		}
		
		try{
			remind.save();
			if("yiremind".equals(remindKind)){//疫苗提醒
				if(!VacUtil.modifyUserBabyRemind(openid)){
					wsError("修改宝宝疫苗预计接种时间出错!");
				}
			}
		}catch(Exception e){
			wsError("保存提醒提前天数出错了");
		}
		wsOk("保存成功");
	}
	
	/**
	 * 获取提醒设置信息
	 */
	public static void getRemindInf(){
		String openid = session.get("openid");//从session中获取openid
		/*if(openid == null){
			openid = "ob1R-uIRkLLp6lmmrT4w-2rrZ5jQ";
		}*/
		List<Remind> remindList = null;
		try{
			remindList = Remind.find("byOpenid", openid).fetch();
		}catch(Exception e){
			wsError("出错了");
		}
		if(remindList != null && remindList.size()>0){
			wsOk(remindList.get(0));
		}else{
			wsError("没有找到用户设置信息哦");
		}
	}
	 
}
