package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.GestationalWeight;

/**
 * 孕重控制器
 * 
 * @author caterZhong
 * @since 2014/12/16
 */
public class GestationalWeightAction extends WebService {
	//增加孕重记录
	public static void addWeight(String userId, Date wDate, float wValue){
		GestationalWeight weight = new GestationalWeight(userId,wDate, wValue);
		weight.save();
	}
	
	//获取某个用户的所有孕重记录
	public static void listByUser(String userId){
		List<GestationalWeight> weights = GestationalWeight.find("userId = ?", userId).fetch();
		wsOk(weights);
	}
	
	/* 查询一段时间内的孩子孕重数据
	 * 参数：开始时间，结束时间，用户id
	 * 返回：时间+孕重的数据
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
}
