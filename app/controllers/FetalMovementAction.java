package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.FetalMovement;
import models.GestationalWeight;

/**
 * 胎动控制器
 * 
 * @author Yingpeng
 * @since 2014/12/16
 */
public class FetalMovementAction extends WebService{
	//增加胎动记录
	public static void addMovement(String userId, Date fDate, int num){
		FetalMovement movement = new FetalMovement(userId,fDate, num);
		movement.save();
	}
	
	//获取某个用户的所有胎动记录
	public static void listByUser(String userId){
		List<FetalMovement> movements = FetalMovement.find("userId = ?", userId).fetch();
		wsOk(movements);
	}
	
	/* 查询一段时间内的孩子胎动数据
	 * 参数：开始时间，结束时间，用户id
	 * 返回：时间+胎动的数据
	 * */
    public static void getMovementDataByDate(String sDate,String eDate,String userId){
		
    	List<Map<String,String>> movementList = new ArrayList();
		Date startDate=null;
		Date endDate=null;
		List<FetalMovement> list=null;
		try{
		 startDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate+" 00:00:00");
		 endDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(eDate+" 23:59:59");
		}catch(Exception e){
			e.printStackTrace();
		}
		if(startDate.before(endDate)){
		 list = FetalMovement.find("select m from FetalMovement m where userId = ? and fDate between ? and ? order by fDate", userId,startDate,endDate).fetch();
		 if(list !=null){
			 for(FetalMovement m :list){
				 Map<String, String> map  = new HashMap<String, String>();
				 map.put("date", m.fDate.toString());
				 map.put("temp",new Integer(m.num).toString());
				 movementList.add(map);
			 }
		 }
		}
		wsOk(movementList);
		
   	}
    
    public static void getMovementDataByDate2(String userId,String userName,String sDate,String eDate,int curpage){
 	   System.out.println("*******"+sDate+" "+eDate);
 	 
 	 if(curpage==0){
 			curpage =1;
 		}
 		long count =0;
 	 	long pageNum =0;
 		Date startDate=null;
 		Date endDate=null;
 		List<FetalMovement> listbean=null;
 		try{
 		 startDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate+" 00:00:00");
 		 endDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(eDate+" 23:59:59");
 		}catch(Exception e){
 			e.printStackTrace();
 		}
 		if(startDate.before(endDate)){
 			listbean = FetalMovement.find("select f from FetalMovement f where userId = ? and fDate between ? and ? order by fDate", userId,startDate,endDate).fetch(curpage,2);
 		 if(listbean!=null){
 			    count = FetalMovement.find("select f from FetalMovement f where userId = ? and fDate between ? and ? order by fDate", userId,startDate,endDate).fetch().size();
 		 		if(count%2!=0)
 		 	     		pageNum = count/2+1; 
 		 	    else
 		 	     		pageNum = count/2;
 		 }
 		}
 		render("/gestationMgm/userfm.html",listbean,pageNum,curpage,userId,userName,sDate,eDate);
    	}
    
    
}
