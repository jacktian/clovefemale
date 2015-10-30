package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.GestationalWeight;
import models.User;

/**
 * 体重控制器
 * 
 * @author Yingpeng
 * @since 2014/12/16
 */
public class GestationalWeightAction extends WebService {
	//增加体重记录
	public static void addWeight(String userId, Date wDate, float wValue){
		GestationalWeight weight = new GestationalWeight(userId,wDate, wValue);
		weight.save();
	}
	
	//获取某个用户的所有体重记录
	public static void listByUser(String userId){
		List<GestationalWeight> weights = GestationalWeight.find("userId = ?", userId).fetch();
		wsOk(weights);
	}
	
	/* 查询一段时间内的孩子体重数据
	 * 参数：开始时间，结束时间，用户id
	 * 返回：时间+体重的数据
	 * */
    public static void getWeightDataByDate(String sDate,String eDate,String userId){
		
    	List<Map<String,String>> weightList = new ArrayList();
		Date startDate=null;
		Date endDate=null;
		List<GestationalWeight> list=null;
		try{
		 startDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate+" 00:00:00");
		 endDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(eDate+" 23:59:59");
		}catch(Exception e){
			e.printStackTrace();
		}
		if(startDate.before(endDate)){
		 list = GestationalWeight.find("select w from GestationalWeight w where userId = ? and wDate between ? and ? order by wDate", userId,startDate,endDate).fetch();
		 if(list !=null){
			 for(GestationalWeight w :list){
				 Map<String, String> map  = new HashMap<String, String>();
				 map.put("date", w.wDate.toString());
				 map.put("temp",new Float(w.wValue).toString());
				 weightList.add(map);
			 }
		 }
		}
		wsOk(weightList);
   	}
    
 public static void getWeightDataByDate2(String userId,String userName,String sDate,String eDate,int curpage){
	   System.out.println("*******"+sDate+" "+eDate);
	 
	 if(curpage==0){
			curpage =1;
		}
		long count =0;
	 	long pageNum =0;
		Date startDate=null;
		Date endDate=null;
		List<GestationalWeight> listbean=null;
		try{
		 startDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate+" 00:00:00");
		 endDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(eDate+" 23:59:59");
		}catch(Exception e){
			e.printStackTrace();
		}
		if(startDate.before(endDate)){
			listbean = GestationalWeight.find("select w from GestationalWeight w where userId = ? and wDate between ? and ? order by wDate", userId,startDate,endDate).fetch(curpage,2);
		 if(listbean!=null){
			    count = GestationalWeight.find("select w from GestationalWeight w where userId = ? and wDate between ? and ? order by wDate", userId,startDate,endDate).fetch().size();
		 		if(count%2!=0)
		 	     		pageNum = count/2+1; 
		 	    else
		 	     		pageNum = count/2;
		 }
		}
		render("/gestationMgm/usergw.html",listbean,pageNum,curpage,userId,userName,sDate,eDate);
   	}
 
}
