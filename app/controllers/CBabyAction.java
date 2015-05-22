package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import play.db.jpa.JPA;
import beans.SimpleNoteBookBean;
import models.Baby;
import models.Note;

/**
 * 客户端婴儿控制器
 * 
 * @author Cater
 * @since 2015/5/13
 */
public class CBabyAction extends WebService{
	/**
	 * 加载宝宝信息
	 */
	public static void loadBabyList(){
		//String openid = session.get("openid");//从session中获取openid
		String openid = "ob1R-uIRkLLp6lmmrT4w-2rrZ5jQ";
		String queryString = "select new Baby(b.id,b.userId,b.date,CONCAT(TIMESTAMPDIFF(YEAR,b.date,now()),''),b.sex,b.name) " +
				" from Baby b where b.userId = ?1";
		
		List<Baby> babyList =  new ArrayList<Baby>();
		try{
			Query query = JPA.em().createQuery(queryString);
			query.setParameter(1, openid);//给编号为1的参数设值 
			babyList  = query.getResultList();
			if(babyList.size() != 0){
				wsOk(babyList);
			}else{
				wsError("找不到宝宝");
			}
			
		}catch(Exception e){
			wsError("操作异常");
		}
	}
	
	/**
	 * 添加宝宝
	 */
	public static void addBaby(String name,String sex,Date birthday){
		//String openid = session.get("openid");//从session中获取openid
		String openid = "ob1R-uIRkLLp6lmmrT4w-2rrZ5jQ";
		Baby baby = new Baby();
		baby.date = birthday;
		baby.sex = sex;
		baby.name = name;
		baby.userId = openid;
		List<Baby> babyList = new ArrayList<Baby>();
		try{
			baby.save();
			babyList.add(baby);
			wsOk(babyList);
		}catch(Exception e){
			wsError("添加失败");
		}
	}
	
	/**
	 *加载宝宝详细资料
	 *babyId:宝宝id 
	 */
	public static void loadBabyInf(String babyId){
		try{
			Baby baby = Baby.findById(babyId);
			if(baby!=null){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				baby.dateStr = format.format(baby.date);
				wsOk(baby);
			}else{
				wsError("不存在该宝宝哦!");
			}
		}catch(Exception e){
			wsError("操作异常!");
		}
		
	}
	
	/**
	 * 修改宝宝小名
	 * name:宝宝名字
	 * babyId:宝宝Id
	 */
	public static void modifyBabyName(String babyId,String name){
		try{
			Baby baby = Baby.findById(babyId);
			if(baby!=null){
				baby.name = name;
				baby.save();
				wsOk("修改成功");
			}else{
				wsError("找不到该宝宝");
			}
		}catch(Exception e){
			wsError("操作异常");
		}
		
	}
	
	/**
	 * 修改宝宝性别
	 * sex:性别
	 * babyId:宝宝Id
	 */
	public static void modifyBabySex(String babyId,String sex){
		try{
			Baby baby = Baby.findById(babyId);
			if(baby!=null){
				baby.sex = sex;
				baby.save();
				wsOk("修改成功");
			}else{
				wsError("找不到该宝宝");
			}
		}catch(Exception e){
			wsError("操作异常");
		}
		
	}
	
	/**
	 * 修改宝宝出生日期
	 * date:出生日期
	 * babyId:宝宝Id
	 */
	public static void modifyBabySex(String babyId,Date date){
		try{
			Baby baby = Baby.findById(babyId);
			if(baby!=null){
				baby.date = date;		
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");	
				baby.dateStr = format.format(baby.date);
				baby.save();
				wsOk("修改成功");
			}else{
				wsError("找不到该宝宝");
			}
		}catch(Exception e){
			wsError("操作异常");
		}
		
	}
	
	
	public static void test() {
		String queryString = "select count(*) from Baby";
		queryString = "SELECT TIMESTAMPDIFF(YEAR, '2011-01-27 15:52:11',now())";
		Query query = JPA.em().createNativeQuery(queryString);
//		query.setParameter(1, openid);//给编号为1的参数设值 
		String year = query.getSingleResult().toString();
		System.out.println(year);
		wsOk(year);
	}
}
