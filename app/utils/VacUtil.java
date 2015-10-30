package utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.Baby;
import models.BabyVac;
import models.Remind;
import models.User;
import models.Vaccine;

/**
 * 疫苗基础服务
 * @author cater
 * @since 2015/09/16
 */
public class VacUtil {
	
	/**
	 * 初始化疫苗列表
	 */
	public static boolean initVacList(){
		try{
			/*-----1-----*/
			Vaccine vac = new Vaccine();
			vac.name = "乙肝疫苗(第一次)";
			vac.monthAfter = 0;
			vac.hasInteral = true;
			vac.interalVacName = "乙肝疫苗(第二次)";
			vac.monthInteral = 0;
			vac.dayInteral = 28;//与第二针间隔28天以上
			vac.pvDisease = "乙型病毒性肝炎";
			vac.time = "1";
			vac.ageDcb = "出生时";
			vac.save();
			
			/*-----2-----*/
			vac = new  Vaccine();
			vac.name = "卡介苗(第一次)";
			vac.monthAfter = 0;
			vac.hasInteral = false;
			vac.interalVacName = "";
			vac.monthInteral = 0;
			vac.dayInteral = 0;
			vac.pvDisease = "结核病";
			vac.time = "1";
			vac.ageDcb = "出生时";
			vac.save();
	
			/*-----3-----*/
			vac = new  Vaccine();
			vac.name = "乙肝疫苗(第二次)";
			vac.monthAfter = 1;
			vac.hasInteral = false;
			vac.interalVacName = "";
			vac.monthInteral = 0;
			vac.dayInteral = 0;
			vac.pvDisease = "乙型病毒性肝炎";
			vac.time = "2";
			vac.ageDcb = "1月龄";
			vac.save();
	
			/*-----4-----*/
			vac = new  Vaccine();
			vac.name = "脊灰疫苗(第一次)";
			vac.monthAfter = 2;
			vac.hasInteral = true;
			vac.interalVacName = "脊灰疫苗(第二次)";
			vac.monthInteral = 0;
			vac.dayInteral = 28;//与第二针间隔28天以上
			vac.pvDisease = "脊髓灰质炎(小儿麻痹)";
			vac.time = "1";
			vac.ageDcb = "2月龄";
			vac.save();
	
			/*-----5-----*/
			vac = new  Vaccine();
			vac.name = "脊灰疫苗(第二次)";
			vac.monthAfter = 3;
			vac.hasInteral = true;
			vac.interalVacName = "脊灰疫苗(第三次)";
			vac.monthInteral = 0;
			vac.dayInteral = 28;//与第三针间隔28天以上
			vac.pvDisease = "脊髓灰质炎(小儿麻痹)";
			vac.time = "2";
			vac.ageDcb = "3月龄";
			vac.save();
	
			/*-----6-----*/
			vac = new  Vaccine();
			vac.name = "百白破疫苗(第一次)";
			vac.monthAfter = 3;
			vac.hasInteral = true;
			vac.interalVacName = "百白破疫苗(第二次)";
			vac.monthInteral = 0;
			vac.dayInteral = 28;//与第二针间隔28天以上
			vac.pvDisease = "百日咳、白喉、破伤风";
			vac.time = "1";
			vac.ageDcb = "3月龄";
			vac.save();
			
			/*-----7-----*/
			vac = new  Vaccine();
			vac.name = "脊灰疫苗(第三次)";
			vac.monthAfter = 4;
			vac.hasInteral = false;
			vac.interalVacName = "";
			vac.monthInteral = 0;
			vac.dayInteral = 0;
			vac.pvDisease = "脊髓灰质炎(小儿麻痹)";
			vac.time = "3";
			vac.ageDcb = "4月龄";
			vac.save();
			
			/*-----8-----*/
			vac = new  Vaccine();
			vac.name = "百白破疫苗(第二次)";
			vac.monthAfter = 4;
			vac.hasInteral = true;
			vac.interalVacName = "百白破疫苗(第三次)";
			vac.monthInteral = 0;
			vac.dayInteral = 28;//与第三针间隔28天以上
			vac.pvDisease = "百日咳、白喉、破伤风";
			vac.time = "2";
			vac.ageDcb = "4月龄";
			vac.save();
			
			/*-----9-----*/
			vac = new  Vaccine();
			vac.name = "百白破疫苗(第三次)";
			vac.monthAfter = 5;
			vac.hasInteral = false;
			vac.interalVacName = "";
			vac.monthInteral = 0;
			vac.dayInteral = 0;
			vac.pvDisease = "百日咳、白喉、破伤风";
			vac.time = "3";
			vac.ageDcb = "5月龄";
			vac.save();
			
			/*-----10-----*/
			vac = new  Vaccine();
			vac.name = "乙肝疫苗(第三次)";
			vac.monthAfter = 6;
			vac.hasInteral = false;
			vac.interalVacName = "";
			vac.monthInteral = 0;
			vac.dayInteral = 0;
			vac.pvDisease = "乙型病毒性肝炎";
			vac.time = "3";
			vac.ageDcb = "6月龄";
			vac.save();
			
			/*-----11-----*/
			vac = new  Vaccine();
			vac.name = "A群流脑疫苗(第一次)";
			vac.monthAfter = 6;
			vac.hasInteral = true;
			vac.interalVacName = "A群流脑疫苗(第二次)";
			vac.monthInteral = 3;//与第二针间隔>=3个月
			vac.dayInteral = 0;
			vac.pvDisease = "流行性脑脊髓膜炎";
			vac.time = "1";
			vac.ageDcb = "6月龄";
			vac.save();
			
			/*-----12-----*/
			vac = new  Vaccine();
			vac.name = "麻风疫苗";
			vac.monthAfter = 8;
			vac.hasInteral = false;
			vac.interalVacName = "";
			vac.monthInteral = 0;
			vac.dayInteral = 0;
			vac.pvDisease = "麻疹";
			vac.time = "1";
			vac.ageDcb = "8月龄";
			vac.save();
			
			/*-----13-----*/
			vac = new  Vaccine();
			vac.name = "乙脑减毒活疫苗(第一次)";
			vac.monthAfter = 8;
			vac.hasInteral = false;
			vac.interalVacName = "";
			vac.monthInteral = 0;
			vac.dayInteral = 0;
			vac.pvDisease = "流行性乙型脑炎";
			vac.time = "1";
			vac.ageDcb = "8月龄";
			vac.save();
			
			/*-----14-----*/
			vac = new  Vaccine();
			vac.name = "乙脑灭活疫苗(第一次)";
			vac.monthAfter = 8;
			vac.hasInteral = true;
			vac.interalVacName = "乙脑灭活疫苗(第二次)";
			vac.monthInteral = 0;
			vac.dayInteral = 8;//与第二针间隔8天
			vac.pvDisease = "流行性乙型脑炎";
			vac.time = "1";
			vac.ageDcb = "8月龄";
			vac.save();
			
			/*-----15-----另外处理*/
			vac = new  Vaccine();
			vac.name = "乙脑灭活疫苗(第二次)";
			vac.monthAfter = 8;
			vac.hasInteral = false;
			vac.interalVacName = "";
			vac.monthInteral = 0;
			vac.dayInteral = 0;
			vac.pvDisease = "流行性乙型脑炎";
			vac.time = "1";
			vac.ageDcb = "8月龄";
			vac.save();
			
			/*-----16-----*/
			vac = new  Vaccine();
			vac.name = "A群流脑疫苗(第二次)";
			vac.monthAfter = 9;
			vac.hasInteral = true;
			vac.interalVacName = "A+C流脑疫苗(第一次)";
			vac.monthInteral = 12;//与A+C流脑疫苗(第一次)间隔12个月
			vac.dayInteral = 0;
			vac.pvDisease = "流行性脑脊髓膜炎";
			vac.time = "2";
			vac.ageDcb = "9月龄";
			vac.save();
			
			
			
			/*-----17-----*/
			vac = new  Vaccine();
			vac.name = "甲肝减毒疫苗 ";
			vac.monthAfter = 18;
			vac.hasInteral = false;
			vac.interalVacName = "";
			vac.monthInteral = 0;
			vac.dayInteral = 0;
			vac.pvDisease = "甲型病毒性肝炎";
			vac.time = "1";
			vac.ageDcb = "1岁半";
			vac.save();
			
			/*-----18-----*/
			vac = new  Vaccine();
			vac.name = "甲肝灭活疫苗 (第一次)";
			vac.monthAfter = 18;
			vac.hasInteral = true;
			vac.interalVacName = "甲肝灭活疫苗 (第二次)";
			vac.monthInteral = 6;//与第二针间隔>=6个月
			vac.dayInteral = 0;
			vac.pvDisease = "甲型病毒性肝炎";
			vac.time = "1";
			vac.ageDcb = "1岁半";
			vac.save();
			
			/*-----19----*/
			vac = new  Vaccine();
			vac.name = "百白破疫苗(第四次)";
			vac.monthAfter = 18;
			vac.hasInteral = false;
			vac.interalVacName = "";
			vac.monthInteral = 0;
			vac.dayInteral = 0;
			vac.pvDisease = "百日咳、白喉、破伤风";
			vac.time = "4";
			vac.ageDcb = "1岁半";
			vac.save();
			
			/*-----20-----*/
			vac = new  Vaccine();
			vac.name = "麻腮风疫苗";
			vac.monthAfter = 18;
			vac.hasInteral = false;
			vac.interalVacName = "";
			vac.monthInteral = 0;
			vac.dayInteral = 0;
			vac.pvDisease = "麻疹、风疹、腮腺炎";
			vac.time = "1";
			vac.ageDcb = "1岁半";
			vac.save();
			
			/*-----21-----*/
			vac = new  Vaccine();
			vac.name = "乙脑减毒疫苗(第二次)";
			vac.monthAfter = 24;
			vac.hasInteral = false;
			vac.interalVacName = "";
			vac.monthInteral = 0;
			vac.dayInteral = 0;
			vac.pvDisease = "流行性乙型脑炎";
			vac.time = "2";
			vac.ageDcb = "2岁";
			vac.save();
			
			
			/*-----22-----*/
			vac = new  Vaccine();
			vac.name = "乙脑灭活疫苗(第三次)";
			vac.monthAfter = 24;
			vac.hasInteral = false;
			vac.interalVacName = "";
			vac.monthInteral = 0;
			vac.dayInteral = 0;
			vac.pvDisease = "流行性乙型脑炎";
			vac.time = "3";
			vac.ageDcb = "2岁";
			vac.save();
			
			/*-----23-----*/
			vac = new  Vaccine();
			vac.name = "甲肝灭活疫苗(第二次)";
			vac.monthAfter = 24;
			vac.hasInteral = false;
			vac.interalVacName = "";
			vac.monthInteral = 0;
			vac.dayInteral = 0;
			vac.pvDisease = "甲型病毒性肝炎(与前剂间隔6-12个月)";
			vac.time = "2";
			vac.ageDcb = "2岁";
			vac.save();
			
			/*-----24-----*/
			vac = new  Vaccine();
			vac.name = "A+C流脑疫苗(第一次)";
			vac.monthAfter = 36;
			vac.hasInteral = true;
			vac.interalVacName = "A+C流脑疫苗(第二次)";
			vac.monthInteral = 36;//与下一针间隔3年
			vac.dayInteral = 0;
			vac.pvDisease = "流行性脑脊髓膜炎";
			vac.time = "1";
			vac.ageDcb = "3岁";
			vac.save();
			
			/*----25-----*/
			vac = new  Vaccine();
			vac.name = "脊灰疫苗(第四次)";
			vac.monthAfter = 48;
			vac.hasInteral = false;
			vac.interalVacName = "";
			vac.monthInteral = 0;
			vac.dayInteral = 0;
			vac.pvDisease = "脊髓灰质炎(小儿麻痹)";
			vac.time = "4";
			vac.ageDcb = "4岁";
			vac.save();
			
			/*-----26-----*/
			vac = new  Vaccine();
			vac.name = "白破疫苗";
			vac.monthAfter = 72;
			vac.hasInteral = false;
			vac.interalVacName = "";
			vac.monthInteral = 0;
			vac.dayInteral = 0;
			vac.pvDisease = "百日咳、白喉、破伤风";
			vac.time = "1";
			vac.ageDcb = "6岁";
			vac.save();
			
			
			/*-----27-----*/
			vac = new  Vaccine();
			vac.name = "乙脑灭活疫苗(第四次)";
			vac.monthAfter = 72;
			vac.hasInteral = false;
			vac.interalVacName = "";
			vac.monthInteral = 0;
			vac.dayInteral = 0;
			vac.pvDisease = "流行性乙型脑炎";
			vac.time = "4";
			vac.ageDcb = "6岁";
			vac.save();
			
			/*-----28-----*/
			vac = new  Vaccine();
			vac.name = "A+C流脑疫苗(第二次)";
			vac.monthAfter = 72;
			vac.hasInteral = false;
			vac.interalVacName = "";
			vac.monthInteral = 0;
			vac.dayInteral = 0;
			vac.pvDisease = "流行性脑脊髓膜炎";
			vac.time = "2";
			vac.ageDcb = "6岁";
			vac.save();
		}catch(Exception e){
			return false;
		}
		return true;
			
	}
	
	/**
	 * 宝宝疫苗初始化
	 */
	public static boolean initBabyVac(String openid,String babyId){
		List<Remind> remindList = Remind.find("byOpenid", openid).fetch();
		Remind remind = null;
		if(remindList.size()!=0){
			remind = remindList.get(0);
		}else{
			//System.out.println("没有找到该用户的提醒记录！");
			return false;//wsError("没有找到该用户的提醒记录！");
		}
		Baby baby = Baby.findById(babyId);
		Date birthday = baby.date;
		List<Vaccine> vacList = Vaccine.findAll();
		int length = vacList.size();
		
		try{
			for(int i = 0 ; i < length ; i++){
				Vaccine vac =  vacList.get(i);
				Calendar cld = Calendar.getInstance();
				cld.setTime(birthday);
				BabyVac babyVac = new BabyVac();
				cld.add(Calendar.MONTH,vac.monthAfter);
				babyVac.babyId = babyId;
				if("乙脑灭活疫苗(第二次)".equals(vac.name)){//如果是乙脑灭活疫苗(第二次)，则从预计接种时间+8天
					cld.add(Calendar.DATE,8);
				}
				babyVac.etmDate = cld.getTime();
				babyVac.vacId = vac.id;
				babyVac.isDone = "0";//"0"表示未来接种
				babyVac.remindTime = DateUtil.dateAdd(babyVac.etmDate, remind.yi_adv_day);//生成接种提醒日期	
				babyVac.save();
			}
			
		}catch(Exception e){
			//System.out.println("初始化疫苗列表失败");
			return false;//"初始化疫苗列表失败";
		}
		return true;
	}
	
	/**
	 * 修改宝宝所有疫苗预计接种时间
	 * 同时修改宝宝疫苗提醒时间
	 */
	public static boolean modifyVacEtm(String openid,String babyId){
		try{
			List<BabyVac> babyVacList = BabyVac.find("babyId = ?",babyId).fetch(); 
			List<Vaccine> vacList = Vaccine.findAll();
			List<Remind> remindList = null;
			Baby baby = null;
			try{
				remindList = Remind.find("byOpenid", openid).fetch();
				baby = Baby.findById(babyId);
			}catch(Exception e){
				return false;
			}
			Remind remind = null;
			if(remindList != null && remindList.size() != 0){
				remind = remindList.get(0);
			}else{
				return false;//("没有找到该用户提醒信息");
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
					babyVac.remindTime = DateUtil.dateAdd(birthday,-advDay);
					babyVac.save();
				}			
			}
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * 修改用户所有宝宝的疫苗提醒时间
	 * 
	 */
	public static boolean modifyUserBabyRemind(String openid){
		List<Baby> babyList = Baby.find("ByUserId", openid).fetch();
		boolean result = true;
		if(babyList == null && babyList.size() == 0){
			return true;
		}
		for(int i = 0; i < babyList.size(); i++){
			result = modifyRemindTime(openid,babyList.get(i).userId);
			if(result == false){
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * 修改疫苗提醒时间
	 */
	public static boolean modifyRemindTime(String openid,String babyId){
		List<Remind> remindList = null;
		Baby baby = null;
		try{
			remindList = Remind.find("byOpenid", openid).fetch();
			baby = Baby.findById(babyId);
		}catch(Exception e){
			throw e;
		}
		Remind remind = null;
		if(remindList != null && remindList.size() != 0){
			remind = remindList.get(0);
		}else{
			return false;//("没有找到该用户提醒信息");
		}

		if(baby == null){
			return false;//wsError("没有找到宝宝信息");
		}
		
		Date birthday = baby.date;
		int advDay = remind.yi_adv_day;
		try{
			List<BabyVac> babyVacList = BabyVac.find("byBabyId", babyId).fetch();
			if(babyVacList != null && babyVacList.size() != 0){
				for(int i = 0;  i < babyVacList.size(); i++){
					BabyVac babyVac = babyVacList.get(i);
					babyVac.remindTime = DateUtil.dateAdd(birthday,-advDay);
					babyVac.save();
				}
			}
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	/**
	 * 清除所有宝宝疫苗数据
	 */
	public static boolean clearAllBabyVac(){
		try{
			BabyVac.deleteAll();
			return true;
		}catch(Exception e){
			return false;
		}	
	}
	
	/**
	 * 清除所有疫苗数据
	 */
	public static boolean clearAllVaccine(){
		try{
			Vaccine.deleteAll();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * 给现有的所有宝宝初始化疫苗数据
	 */
	public static boolean initExistBabyVac(){
		try{
			List<Baby> babyList = Baby.findAll();
			int count = babyList.size();
			List result = new ArrayList();
			for(int i = 0; i < count; i++){
				String userId = babyList.get(i).userId;
				User user = User.findById(userId);
				result.add(initBabyVac(user.openid,babyList.get(i).id));
			}
		}catch(Exception e){
			return false;
		}
		return true;
	}	
	
}
