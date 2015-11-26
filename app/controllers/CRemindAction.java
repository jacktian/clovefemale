package controllers;

import java.util.List;

import play.db.jpa.JPA;
import javax.persistence.Query;


import models.Remind;
import models.User;
import models.Baby;
import models.BabyVac;
import models.Vaccine;

import utils.VacUtil;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;


import utils.DateUtil;

import beans.VacNotificationBean;

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
		if(openid == null){
			openid = "ob1R-uIRkLLp6lmmrT4w-2rrZ5jQ";
		}
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
		if(openid == null){
			openid = "ob1R-uIRkLLp6lmmrT4w-2rrZ5jQ";
		}
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
			//remind.
			                    //修改疫苗接种提醒时间
		}
		
		try{
			remind.save();
			if("yiremind".equals(remindKind)){//疫苗提醒
				if(!VacUtil.modifyUserBabyRemind(openid)){
					//wsError();
					wsError("修改宝宝疫苗预计接种时间出错!");
				}
			}
		}catch(Exception e){
			wsError(e.toString());
			wsError("保存提醒提前天数出错了");
		}
		wsOk("保存成功");
	}
	
	/**
	 * 获取提醒设置信息
	 */
	public static void getRemindInf(){
		String openid = session.get("openid");//从session中获取openid
		if(openid == null){
			openid = "ob1R-uIRkLLp6lmmrT4w-2rrZ5jQ";
		}
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
	
	/*
	 * 初始化已存在的所有用户初始化提醒数据
	 */
	public static void initExistUserRemindData(){
		try{
			List<User> userList = User.findAll();
			Remind remind = null;
			User user = null;
			
			for(int i = 0; i < userList.size(); i++){
				user = userList.get(i);
				List<Remind> remindList = Remind.find("byOpenid", user.openid).fetch();
				if(remindList.size() == 0){//没有此用户的提醒记录
					remind = new Remind();
					remind.openid = user.openid;
					remind.medremind = 1;
					remind.med_adv_day = 7;
					remind.healremind = 0;
					remind.heal_adv_day = 7;
					remind.yiremind = 1;
					remind.yi_adv_day = 7;
					remind.save();
					System.out.println("不存在i=" +i +"---,openid="+user.openid);
				}else{
					System.out.println("已存在i=" +i +"---,openid="+user.openid);
				}
				
			}
			wsOk("初始化已存在的所有用户初始化提醒数据---成功");
		}catch(Exception e){
			wsError(e.toString());
			//wsOk("初始化已存在的所有用户初始化提醒数据---成功");
		}	
	}

	public static void test(){
		List<Baby> babyList = Baby.findAll();
		wsOk(babyList);
	}

	public static void vacNotifi(){
		String openid = "ob1R-uIRkLLp6lmmrT4w-2rrZ5jQ";
		String queryString = "select new beans.VacNotificationBean(b.id,b.name, bv.vacId,v.name,v.time,v.ageDcb,bv.remindTime,bv.etmDate)"
                            +" from Baby as b, BabyVac as bv, Vaccine as v where b.userId=?1 and b.id = bv.babyId and bv.vacId = v.id and datediff(bv.remindTime, current_date() ) < 28";//and bv.remindTime= ";//今天
  		List<VacNotificationBean> vacList =  null;//new ArrayList<VacNotificationBean>();
        
        try{
            Query query = JPA.em().createQuery(queryString);
            query.setParameter(1, openid);//给编号为1的参数设值 
            vacList  = query.getResultList();
            List<VacNotificationBean> notificationList = new ArrayList<VacNotificationBean>();
            // wsOk(vacList);
            if(vacList.size() != 0){
	            HashMap vacRemindMap = new HashMap();
	            VacNotificationBean babyVacNotification = new VacNotificationBean();
	            for(int j =0; j < vacList.size(); j++){
	                Date remindTime = vacList.get(j).remindTime;
	                
	                if(vacRemindMap.containsKey(vacList.get(j).babyId)){
	                    babyVacNotification = (VacNotificationBean)vacRemindMap.get(vacList.get(j).babyId);
	                    babyVacNotification.vacName = babyVacNotification.vacName +"、"+vacList.get(j).vacName;
	                    vacRemindMap.put(vacList.get(j).babyId,babyVacNotification);
	                }else{
	                    vacRemindMap.put(vacList.get(j).babyId,vacList.get(j));
	                }
	                  
				}
	            Iterator iter = vacRemindMap.entrySet().iterator();
	            while (iter.hasNext()) {
	                Map.Entry entry = (Map.Entry) iter.next();
	                VacNotificationBean notification = (VacNotificationBean)entry.getValue();
	                String msg = notification.bName+"该打疫苗了";  //宝宝＊＊该打疫苗了
	                String stage =notification.babyAgeStr;//1岁1个月
	                String endDate = notification.vacName;//A群流脑疫苗(第二针)
	                String remark ="请及时接种疫苗";//
	                notificationList.add(notification);
	            }

            }
            wsOk(notificationList);
                        
        }catch(Exception e){
        	wsError(e.toString());
            wsError("操作异常");
        }            

	}
	


	/**
	 * 修改所有宝宝所有疫苗预计接种时间
	 * 同时修改宝宝疫苗提醒时间
	 */
	public static void modifyVacEtm(){
		try{
			//Baby.Delete();
			// List<User> userList = User.findAll();
			// wsOk(userList);
			// List<String> remind_null_list = new ArrayList<String>();
			// for(int m =0 ; m < userList.size(); m++){
			// 	String openid = userList.get(m).openid;
			// 	List<Remind> remindList = Remind.find("byOpenid", openid).fetch();
			// 	if(remindList.size()==0){
			// 		remind_null_list.add(openid);
			// 	}
			// }
			// wsOk(remind_null_list);
			List<Baby> babyList = Baby.findAll();
			//wsOk(babyList);
			for(int m = 0;  m < babyList.size();  m++){
				String openid = babyList.get(m).userId;
				if(openid ==null){
					Baby.delete("id = ?",babyList.get(m).id);
					continue;
				}
				String babyId = babyList.get(m).id;
			
			List<BabyVac> babyVacList = BabyVac.find("babyId = ?",babyId).fetch(); 
			List<Vaccine> vacList = Vaccine.findAll();
			List<Remind> remindList = null;
			Baby baby = null;
			//for(int j = 0; j<babyVacList)
			try{
				remindList = Remind.find("byOpenid", openid).fetch();
				baby = Baby.findById(babyId);
			}catch(Exception e){
				wsError(e.toString());
				// return false;
			}
			Remind remind = null;
			if(remindList != null && remindList.size() != 0){
				remind = remindList.get(0);
			}else{
				Baby.delete("id = ?",babyList.get(m).id);
				continue;
				// wsError("openid:"+openid+"--babyId:"+babyId);
				// openid:o7NE5wHqIMVjce9G-jKizQbRyd2A--babyId:09BF34E2588541A4887EE533CE3B39E9
				//return false;//("没有找到该用户提醒信息");
			}
			Date birthday = baby.date;
			int advDay = remind.yi_adv_day;
			int count = babyVacList.size();
			int vacNum = vacList.size();
			if(count != 0){
				for(int i = 0; i < count; i++ ){
					BabyVac babyVac = babyVacList.get(i);
					int monthAfter = 0;
					Vaccine vac = new Vaccine();
					for(int j = 0; j < vacNum; j++){
						vac = vacList.get(j);
						if(babyVac.vacId.equals(vac.id)){
							monthAfter = vac.monthAfter;
							break;
						}
					}
					Calendar cld = Calendar.getInstance();
					cld.setTime(birthday);
					cld.add(Calendar.MONTH,monthAfter);
					if("乙脑灭活疫苗(第二次)".equals(vac.name)){//如果是乙脑灭活疫苗(第二次)，则从预计接种时间+8天
						cld.add(Calendar.DATE,8);
					}
					babyVac.etmDate = cld.getTime();
					babyVac.remindTime = DateUtil.dateAdd(babyVac.etmDate,-advDay);
					System.out.println("remind："+babyVac.remindTime+"----etm:"+babyVac.etmDate);
					babyVac.save();
				}			
			}
			//return true;
			
			}
			wsOk("成功");
		}catch(Exception e){
			wsError(e.toString());
			//return false;
		}
	}


	 
}
