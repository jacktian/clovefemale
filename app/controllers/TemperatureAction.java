package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Temperature;

/**
 * 基础体温控制器
 * 
 * @author caterZhong
 * @since 2014/12/16
 */
public class TemperatureAction extends WebService{
	
	//添加基础体温
	public static void addTemperature(Temperature model){
		String result = "" ;
		if(model != null){
			model.tDate = new Date() ;
			try{
				model.save() ;
				result = "success" ;
			}catch(Exception e){
				e.printStackTrace() ;
				result = "fail" ;
			}
		}else{
			result = "fail" ;
		}
		wsOkAsJsonP(result) ;
	}
	
	//查看某个用户的所有基础体温记录
	public static void findByUser(String userId){
		List<Temperature> temperatures = Temperature.find("userId = ?", userId).fetch();
		wsOk(temperatures);
	}
	
	/* 查询一段时间内的孕妇体温数据
	 * 参数：开始时间，结束时间，用户id
	 * 返回：时间+体温的数据
	 * */
    public static void getTemprDataByDate(String sDate,String eDate,String userId){
		
    	List<Map<String,String>> temprList = new ArrayList();
		Date startDate=null;
		Date endDate=null;
		List<Temperature> list=null;
		try{
		 startDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate+" 00:00:00");
		 endDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(eDate+" 23:59:59");
		}catch(Exception e){
			e.printStackTrace();
		}
		if(startDate.before(endDate)){
		 list = Temperature.find("select t from Temperature t where userId = ? and tDate between ? and ? order by tDate", userId,startDate,endDate).fetch();
		 if(list !=null){
			 for(Temperature temperature :list){
				 Map<String, String> map  = new HashMap<String, String>();
				 map.put("date", temperature.tDate.toString());
				 map.put("temp",new Float(temperature.tValue).toString());
				 temprList.add(map);
			 }
		 }
		}
		wsOk(temprList);
				
	}
    
    
    /* 查询一段时间内的孕妇不同体温区间的占比
	 * 参数：开始时间，结束时间，孕妇id，
	 * 返回：分数区间+次数
	 * */
    public static void getWomentprByDate(String sDate,String eDate,String userId){
		
    	List<Map<String,String>> temprList = new ArrayList();
		Date startDate=null;
		Date endDate=null;
		List<Temperature> list=null;
		int[] a =new int[5];
		try{
		 startDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate+" 00:00:00");
		 endDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(eDate+" 23:59:59");
		}catch(Exception e){
			e.printStackTrace();
		}
		if(startDate.before(endDate)){
		 list = Temperature.find("select t from Temperature t where userId = ? and tDate between ? and ? order by tDate", userId,startDate,endDate).fetch();
		 if(list !=null){
			 a=countTempNum(list);
			 for(int i=0;i<5;i++){
				 Map<String, String> map  = new HashMap<String, String>();
				 map.put("range", (36+i)+"-"+(36+i+1));
				 map.put("temp",new Integer(a[i]).toString());
				 temprList.add(map);
			 }
		 }
		}
		wsOk(temprList);
		
		
   	}
    
    public static int[] countTempNum(List<Temperature> list){
    	int[] a =new int[5];
    	for(int i=0;i<5;i++){
    		a[i]=0;
    	}
    	
    	for(Temperature t :list){
    		
    		if(t.tValue>36.0 && t.tValue<37.0){
    		    a[0]++;
    		}else if (t.tValue>=37.0 && t.tValue <38.0) {
				a[1]++;
			}else if (t.tValue>=38.0 && t.tValue <39.0) {
				a[2]++;
			}else if (t.tValue>=39.0 && t.tValue <40.0) {
				a[3]++;
			}else{
				a[4]++;
			}
    		
    		
    	}
    	
    	return a;
    }
    
    
    
}
