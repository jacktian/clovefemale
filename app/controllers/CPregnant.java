package controllers;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

import beans.*;

import models.Menses;

import play.db.jpa.JPA;

/**
 * 助孕记录
 * @author boxiZen
 * @since 2015/05/22
 */
public class CPregnant extends WebService {

	/*
	 * 添加月经记录
	 */
	public static void addMenses(String menseColor,String menseChou,String menseHurt,String menseLquid,String menseNum,String menseLiquid,String date) {
		String openid = session.get("openid");
		openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		System.out.println("date="+date);
		try{
			if(openid == null){
				wsError("openid过期");
			}
			else{
				Menses mense = new Menses();
				mense.mColor = menseColor;
				mense.mMeasure = menseNum;
				if(menseLiquid.equals("有"))
					mense.mPiece = true;
				else
					mense.mPiece = false;
				mense.vicidity = menseChou;
				if(menseHurt.endsWith("有"))
					mense.isMcramp = true;
				else
					mense.isMcramp = false;
				mense.userId = openid;
				if(date==null||date.equals("")){
					mense.mDate = new Date();
				}
				else{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					mense.mDate = sdf.parse(date);
				}
				mense.save();
				wsOk("保存成功");
			}
		}
		catch(Exception e){
			e.printStackTrace();
			wsError("保存失败");
		}
	}

	/*
	 * 查找上一次月经纪录
	 */
	 public static void lastMense(){
	 	String openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select m.m_color,m.m_measure,m.m_piece,m.is_mcramp,m.vicidity,date_format(m.m_date,'%Y-%m-%d') from Menses m where m.m_date in (select max(m_date) from Menses where user_id = '"+openid+"') and m.user_id = '"+openid +"'";
	 	List bean = JPA.em().createNativeQuery(sql).getResultList(); 
		wsOk(bean.get(0));
	 }	

	 /*
	 * 根据日期查找上一次月经纪录
	 */
	 public static void lastMenseByDate(String date){
	 	String openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select * from Menses m where m.m_date in (select max(m_date) from Menses where user_id = '"+openid+"') and m.user_id = '"+openid +"'";
	 	List<Menses> bean = JPA.em().createNativeQuery(sql).getResultList(); 
		wsOk(bean.get(0));
	 }	

	/*
	 * 渲染月经图表数据
	 */
	public static void renderMense(){
		ChartBean bean = new ChartBean();
		List labelList = new ArrayList();
		List dataList = new ArrayList();
		for(int i=0; i<7; i++){
			labelList.add("as");
			dataList.add(i);
		}
		bean.label = labelList;
		bean.data = dataList;
		wsOk(bean);
	}

	/*
	 * 测试方法
	 */
	public static void test(){
		Date date = new Date();
		System.out.println(date+","+date.getYear()+","+date.getMonth()+","+date.getDay());
		String openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select date_format(m.m_date,'%Y') from Menses m where m.m_date in (select max(m_date) from Menses where user_id = '"+openid+"') and m.user_id = '"+openid +"'";
		List bean = JPA.em().createNativeQuery(sql).getResultList(); 
		wsOk(bean);
	}
}
