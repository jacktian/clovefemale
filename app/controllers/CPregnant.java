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
 * 
 * @author boxiZen
 * @since 2015/05/22
 */
public class CPregnant extends WebService {

	/*
	 * 添加月经记录
	 */
	public static void addMenses(String menseColor, String menseChou,
			String menseHurt, String menseLquid, String menseNum,
			String menseLiquid, String date,String time) {
		String openid = session.get("openid");
		openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";

		try {
			if (openid == null) {
				wsError("openid过期");
			} else {
				Date newDate;
				String dateStr;
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				if (date == null || date.equals("")) {
					Date d = new Date();
					dateStr = format.format(d);
					newDate = format.parse(dateStr);
				} else {
					dateStr = date;
					newDate = format.parse(date);
				}
				String sql = "select id from Menses m where date_format(m.m_date,'%Y-%m-%d') = '"
						+ dateStr + "' and m.user_id = '"+ openid +"'";
				List menseList = JPA.em().createNativeQuery(sql)
						.getResultList();
				Menses mense = new Menses();
				if (menseList.size() > 0) {
					String menseId = menseList.get(0).toString();
					mense = Menses.findById(menseId);
				} else {
					mense = new Menses();
				}
				mense.mColor = menseColor;
				mense.mMeasure = menseNum;
				if (menseLiquid.equals("有"))
					mense.mPiece = true;
				else
					mense.mPiece = false;
				mense.vicidity = menseChou;
				if (menseHurt.endsWith("有"))
					mense.isMcramp = true;
				else
					mense.isMcramp = false;
				mense.time = time;
				mense.userId = openid;
				mense.mDate = newDate;
				mense.save();
				wsOk("保存成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			wsError("保存失败");
		}
	}

	/*
	 * 查找上一次月经纪录
	 */
	public static void lastMense() {
		String openid = session.get("openid");
		openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select m.m_color,m.m_measure,m.m_piece,m.is_mcramp,m.vicidity,date_format(m.m_date,'%Y-%m-%d'),m.time from Menses m where m.m_date in (select max(m_date) from Menses where user_id = '"
				+ openid + "') and m.user_id = '" + openid + "'";
		List bean = JPA.em().createNativeQuery(sql).getResultList();
		if (bean.size() == 0) {
			bean = new ArrayList();
			bean.add("empty");
		}
		wsOk(bean.get(0));
	}

	/*
	 * 根据日期查找上一次月经纪录
	 */
	public static void lastMenseByDate(String date) {
		String openid = session.get("openid");
		openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select * from Menses m where m.m_date in (select max(m_date) from Menses where user_id = '"
				+ openid + "') and m.user_id = '" + openid + "'";
		List<Menses> bean = JPA.em().createNativeQuery(sql).getResultList();
		wsOk(bean.get(0));
	}

	/*
	 * 渲染月经图表数据
	 */
	public static void renderMense() {
		String openid = session.get("openid");
		openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		ChartBean bean = new ChartBean();
		ArrayList labelList = new ArrayList();
		ArrayList dataList = new ArrayList();
		String sql = "select new beans.MenseBean(m.mDate as mDate,m.time as time) from Menses m where m.userId = '" + openid + "' order by m.mDate desc";
		List<MenseBean> resultList = JPA.em().createQuery(sql).setMaxResults(7).getResultList();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0;i<resultList.size();i++){
			Date date = resultList.get(resultList.size()-1-i).mDate;
			String dateStr = format.format(date);
			labelList.add(dateStr);
			dataList.add(resultList.get(resultList.size()-1-i).time);
		}
		bean.label = labelList;
		bean.data = dataList;
		wsOk(bean);
	}

	/*
	 *	查找月经记录 
	 */
	public static void findMense(String date){
		String openid = session.get("openid");
		openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		Date newDate;
		String dateStr;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try{
			if (date == null || date.equals("")) {
				Date d = new Date();
				dateStr = format.format(d);
				newDate = format.parse(dateStr);
			} else {
				dateStr = date;
				newDate = format.parse(date);
			}
			String sql = "select id from Menses m where date_format(m.m_date,'%Y-%m-%d') = '"
					+ dateStr + "' and m.user_id = '"+ openid +"'";
			List menseList = JPA.em().createNativeQuery(sql)
					.getResultList();
			if(menseList.size()>0)
				wsOk(menseList.get(0));
			else
				wsError("无记录");
		}
		catch(Exception e){
			wsError("出错");
		}
	}
	
	/*
	 * 删除月经记录 
	 */
	public static void removeMense(String date){
		String openid = session.get("openid");
		openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select id from Menses m where date_format(m.m_date,'%Y-%m-%d') = '"
				+ date + "' and m.user_id = '"+ openid +"'";
		List menseList = JPA.em().createNativeQuery(sql)
				.getResultList();
		try{
			if (menseList.size() > 0) {
				String menseId = menseList.get(0).toString();
				Menses mense = Menses.findById(menseId);
				mense.delete();
				wsOk("删除成功");
			}
			else{
				wsError("删除失败");
			}
		}
		catch(Exception e){
			wsError("删除失败");
		}
	}
	
	/*
	 * 月经详情 
	 */
	public static void menseDetail(){
		render("/Client/record/menseDetail.html");
	}
	
	/*
	 * 测试方法
	 */
	public static void test() {
		String openid = session.get("openid");
		openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select new beans.MenseBean(m.mDate as mDate,m.time) from Menses m order by m.mDate";
		List<MenseBean> bean = JPA.em().createQuery(sql).getResultList();
		wsOk(bean);
	}
}
