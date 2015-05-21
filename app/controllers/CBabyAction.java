package controllers;

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
//		String queryString = "select new Baby(b.userId,b.date,b.dateStr,b.sex,b.name) " +
//				" from Baby b,User u where b.userId= u.id  and u.openid = ?1";
		String queryString = "select new Baby(b.userId,b.date,CONCAT(TIMESTAMPDIFF(YEAR,b.date,now()),''),b.sex,b.name) " +
				" from Baby b where b.userId = ?1";
		
//		CONCAT(TIMESTAMPDIFF(YEAR,b.date,now()),'')
		
//		queryString = "select datediff(now(),'20150412')";
//		queryString = "select count(*) from Baby";
		List<Baby> babyList =  new ArrayList<Baby>();
		//try{
			Query query = JPA.em().createQuery(queryString);
			query.setParameter(1, openid);//给编号为1的参数设值 
//			int year = query.getFirstResult();
			babyList  = query.getResultList();
//			List<Baby> babyList = Baby.find("userId", openid).fetch();
			wsOk(babyList);
		//}catch(Exception e){
		//	wsError("操作异常");
			//render("/Client/record/mybaby.html",babyList);
	//	}
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
