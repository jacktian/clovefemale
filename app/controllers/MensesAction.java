package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.FetalMovement;
import models.Menses;

/**
 * 月经controller
 * @author Yingpeng
 * @since 03/05/15
 * */
public class MensesAction extends WebService{
	//添加月经记录
	public static void addMenses(String userId, Date mDate, String mColor, String mMeasure,boolean mPiece, boolean isMcramp, String vicidity){
    	Menses menses = new Menses(userId, mDate, mColor, mMeasure, mPiece, isMcramp, vicidity);
    	menses.save();
	}
	
	public static void listMenseses(){
		List<Menses> menseses = Menses.findAll();
		wsOk(menseses);
	}
	
	//获取某个用户的所有月经记录
	public static void listByUser(String userId){
		 List<Menses> menseses = Menses.find("userId = ?", userId).fetch();
    	 wsOk(menseses);
	}
	
	/**
	 * 根据日期区间获取特定用户的全部月经记录
	 * */
	 public static void getMensesDataByDate2(String userId,String userName,String sDate,String eDate,int curpage){
	 	   System.out.println("*******"+sDate+" "+eDate);
	 	 if(curpage==0){
	 			curpage =1;
	 		}
	 		long count =0;
	 	 	long pageNum =0;
	 		Date startDate=null;
	 		Date endDate=null;
	 		List<Menses> listbean=null;
	 		try{
	 		 startDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate+" 00:00:00");
	 		 endDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(eDate+" 23:59:59");
	 		}catch(Exception e){
	 			e.printStackTrace();
	 		}
	 		if(startDate.before(endDate)){
	 			listbean = Menses.find("select m from Menses m where userId = ? and mDate between ? and ? order by mDate", userId,startDate,endDate).fetch(curpage,2);
	 		 if(listbean!=null){
	 			    count = Menses.find("select m from Menses m where userId = ? and mDate between ? and ? order by mDate", userId,startDate,endDate).fetch().size();
	 		 		if(count%2!=0)
	 		 	     		pageNum = count/2+1; 
	 		 	    else
	 		 	     		pageNum = count/2;
	 		 }
	 		}
	 		render("/gestationMgm/userms.html",listbean,pageNum,curpage,userId,userName,sDate,eDate);
	    	}

}
