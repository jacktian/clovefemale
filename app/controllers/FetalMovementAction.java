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
 * @author caterZhong
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
}
