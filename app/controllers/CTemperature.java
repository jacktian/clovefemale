package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beans.ChartBean;
import beans.TempBean;
import models.Temperature;
import play.db.jpa.JPA;
/**
 * 温度控制器
 * @author boxiZen
 * @since 2015/05/25
 */
public class CTemperature extends WebService{
	/*
	 * 添加温度记录
	 */
	public static void addTemperature(String date,float temp){
		String openid = session.get("openid");
		//openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
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
				String sql = "select id from Temperature t where date_format(t.t_date,'%Y-%m-%d') = '"
						+ dateStr + "' and t.user_id = '"+ openid +"'";
				List tempList = JPA.em().createNativeQuery(sql)
						.getResultList();
				Temperature nTemp = new Temperature();
				if (tempList.size() > 0) {
					String tempId = tempList.get(0).toString();
					nTemp = Temperature.findById(tempId);
				} else {
					nTemp = new Temperature();
				}
				nTemp.tDate = newDate;
				nTemp.tValue = temp;
				nTemp.userId = openid;
				nTemp.save();
				wsOk("保存成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			wsError("保存失败");
		}
	}	

	
	/*
	 * 查找前7条体温
	 */
	public static void lastTemp(){
		String openid = session.get("openid");
		//openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select new beans.TempBean(t.tDate,t.tValue) from Temperature t where  t.userId = '" + openid + "' order by t.tDate desc";
		List<TempBean> bean = JPA.em().createQuery(sql).setMaxResults(7).getResultList();
		List<TempBean> rBean = new ArrayList<TempBean>();
		for(int i=0;i<bean.size();i++){
			rBean.add(bean.get(bean.size()-1-i));
		}
		wsOk(rBean);
	}
	
	/*
	 * 查找前7条体温
	 */
	public static void lastTempChart(){
		String openid = session.get("openid");
		//openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select new beans.TempBean(t.tDate,t.tValue) from Temperature t where  t.userId = '" + openid + "' order by t.tDate desc";
		List<TempBean> tempList = JPA.em().createQuery(sql).setMaxResults(7).getResultList();
		ChartBean bean = new ChartBean();
		List labelList = new ArrayList();
		List dataList = new ArrayList();
		for(int i=0;i<tempList.size();i++){
			labelList.add(tempList.get(tempList.size()-1-i).dateStr);
			dataList.add(tempList.get(tempList.size()-1-i).temp);
		}
		bean.label = labelList;
		bean.data = dataList;
		wsOk(bean);
	}
	
	/*
	 * 查找体温
	 */
	public static void findTemp(String date){
		String openid = session.get("openid");
		//openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
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
			String sql = "select id from Temperature t where date_format(t.t_date,'%Y-%m-%d') = '"
					+ dateStr + "' and t.user_id = '"+ openid +"'";
			List tempList = JPA.em().createNativeQuery(sql)
					.getResultList();
			if(tempList.size()>0)
				wsOk(tempList.get(0));
			else
				wsError("未找到");
		}
		catch(Exception e){
			wsError("错误");
		}
	}
	
	/*
	 * 删除体温数据
	 */
	public static void removeTemp(String date){
		String openid = session.get("openid");
		//openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select id from Temperature t where date_format(t.t_date,'%Y-%m-%d') = '"
				+ date + "' and t.user_id = '"+ openid +"'";
		List tempList = JPA.em().createNativeQuery(sql)
				.getResultList();
		try{
			if(tempList.size()>0){
				String id = tempList.get(0).toString();
				Temperature temp = Temperature.findById(id);
				temp.delete();
				wsOk("清除成功");
			}
			else{
				wsError("清除失败");
			}
				
		}
		catch(Exception e){
			wsError("失败");
		}
	}
	
	/*
	 * 加载所有体温记录
	 */
	public static void loadAllTemp(){
		String openid = session.get("openid");
		//openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select new beans.TempBean(t.tDate,t.tValue) from Temperature t where  t.userId = '" + openid + "' order by t.tDate";
		List<TempBean> bean = JPA.em().createQuery(sql).getResultList();
		List<TempBean> rBean = new ArrayList<TempBean>();
		for(int i=0;i<bean.size();i++){
			rBean.add(bean.get(bean.size()-1-i));
		}
		wsOk(rBean);
	}
	
	/*
	 * 加载所有体温图表记录 
	 */
	public static void loadAllTempChart(){
		String openid = session.get("openid");
		//openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select new beans.TempBean(t.tDate,t.tValue) from Temperature t where  t.userId = '" + openid + "' order by t.tDate";
		List<TempBean> tempList = JPA.em().createQuery(sql).getResultList();
		ChartBean bean = new ChartBean();
		List labelList = new ArrayList();
		List dataList = new ArrayList();
		for(int i=0;i<tempList.size();i++){
			labelList.add(tempList.get(tempList.size()-1-i).dateStr);
			dataList.add(tempList.get(tempList.size()-1-i).temp);
		}
		bean.label = labelList;
		bean.data = dataList;
		wsOk(bean);
	}
	
	/*
	 * 体温详情
	 */
	public static void tempDetail(){
		render("/Client/record/tempDetail.html");
	}
}
