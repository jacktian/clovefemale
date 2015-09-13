package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beans.ChartBean;
import beans.PregwBean;
import beans.TempBean;
import models.GestationalWeight;
import models.Temperature;
import play.db.jpa.JPA;

/**
 * 重量控制器
 *
 * @author boxiZen
 * @since 2015/05/25
 */
public class CWeight extends WebService {
	/*
	 * 添加重量记录
	 */
	public static void addWeight(String date, float pregw) {
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
				Date today = new Date();
				int compare = today.compareTo(newDate);
				if (compare == -1) {
					wsError("dateExp");
				} else {
					String sql = "select id from GestationalWeight w where date_format(w.w_date,'%Y-%m-%d') = '"
							+ dateStr + "' and w.user_id = '" + openid + "'";
					List weightList = JPA.em().createNativeQuery(sql)
							.getResultList();
					GestationalWeight nWeight = new GestationalWeight();
					if (weightList.size() > 0) {
						String wId = weightList.get(0).toString();
						nWeight = GestationalWeight.findById(wId);
					} else {
						nWeight = new GestationalWeight();
					}
					nWeight.wDate = newDate;
					nWeight.wValue = pregw;
					nWeight.userId = openid;
					nWeight.save();
					wsOk("保存成功");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			wsError("保存失败");
		}
	}

	/*
	 * 查找前7条重量记录
	 */
	public static void findWeight() {
		String openid = session.get("openid");
		//openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select new beans.PregwBean(w.wDate,w.wValue) from GestationalWeight w where  w.userId = '"
				+ openid + "' order by w.wDate desc";
		List<PregwBean> bean = JPA.em().createQuery(sql).setMaxResults(7)
				.getResultList();
		List<PregwBean> rBean = new ArrayList<PregwBean>();
		for (int i = 0; i < bean.size(); i++) {
			rBean.add(bean.get(bean.size() - 1 - i));
		}
		wsOk(rBean);
	}

	/*
	 * 查找前7条体温
	 */
	public static void lastWeightChart() {
		String openid = session.get("openid");
		//openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select new beans.PregwBean(w.wDate,w.wValue) from GestationalWeight w where  w.userId = '"
				+ openid + "' order by w.wDate desc";
		List<PregwBean> pregBean = JPA.em().createQuery(sql).setMaxResults(7)
				.getResultList();
		ChartBean bean = new ChartBean();
		List labelList = new ArrayList();
		List dataList = new ArrayList();
		for (int i = 0; i < pregBean.size(); i++) {
			labelList.add(pregBean.get(pregBean.size() - 1 - i).dateStr);
			dataList.add(pregBean.get(pregBean.size() - 1 - i).weight);
		}
		bean.label = labelList;
		bean.data = dataList;
		wsOk(bean);
	}

	/*
	 * 查找体重记录
	 */
	public static void findWeightByDate(String date) {
		String openid = session.get("openid");
	  	//openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		Date newDate;
		String dateStr;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (date == null || date.equals("")) {
				Date d = new Date();
				dateStr = format.format(d);
				newDate = format.parse(dateStr);
			} else {
				dateStr = date;
				newDate = format.parse(date);
			}
			String sql = "select id from GestationalWeight w where date_format(w.w_date,'%Y-%m-%d') = '"
					+ dateStr + "' and w.user_id = '" + openid + "'";
			List weightList = JPA.em().createNativeQuery(sql).getResultList();
			if (weightList.size() > 0)
				wsOk(weightList.get(0));
			else
				wsError("未找到");
		} catch (Exception e) {
			wsError("出错");
		}
	}

	/*
	 * 删除体重记录
	 */
	public static void removeWeight(String date) {
		String openid = session.get("openid");
		//openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		try {
			String sql = "select id from GestationalWeight w where date_format(w.w_date,'%Y-%m-%d') = '"
					+ date + "' and w.user_id = '" + openid + "'";
			List weightList = JPA.em().createNativeQuery(sql).getResultList();
			if (weightList.size() > 0) {
				String id = weightList.get(0).toString();
				GestationalWeight weight = GestationalWeight.findById(id);
				weight.delete();
				wsOk(weightList.get(0));
			} else
				wsError("未找到");
		} catch (Exception e) {
			wsError("清除失败");
		}
	}

	/*
	 * 加载所有体重记录
	 */
	public static void loadAllWeight() {
		String openid = session.get("openid");
		//openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select new beans.PregwBean(w.wDate,w.wValue) from GestationalWeight w where  w.userId = '"
				+ openid + "' order by w.wDate desc";
		List<PregwBean> bean = JPA.em().createQuery(sql).getResultList();
		/*List<PregwBean> rBean = new ArrayList<PregwBean>();
		for (int i = 0; i < bean.size(); i++) {
			rBean.add(bean.get(bean.size() - 1 - i));
		}*/
		wsOk(bean);
	}

	/*
	 * 加载所有体重图表记录
	 */
	public static void loadAllWeightChart() {
		String openid = session.get("openid");
		//openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select new beans.PregwBean(w.wDate,w.wValue) from GestationalWeight w where  w.userId = '"
				+ openid + "' order by w.wDate desc";
		List<PregwBean> pregBean = JPA.em().createQuery(sql).getResultList();
		ChartBean bean = new ChartBean();
		List labelList = new ArrayList();
		List dataList = new ArrayList();
		for (int i = 0; i < pregBean.size(); i++) {
			labelList.add(pregBean.get(pregBean.size() - 1 - i).dateStr);
			dataList.add(pregBean.get(pregBean.size() - 1 - i).weight);
		}
		bean.label = labelList;
		bean.data = dataList;
		wsOk(bean);
	}

	/*
	 * 体重详情页面
	 */
	public static void weightDetail() {
		render("/Client/record/pregwDetail.html");
	}
}
