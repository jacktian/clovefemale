package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beans.ChartBean;
import beans.PregmBean;
import beans.PregwBean;
import models.FetalMovement;
import models.GestationalWeight;
import play.db.jpa.JPA;

/**
 * 胎动控制器
 * 
 * @author boxiZen
 * @since 2015/05/25
 */

public class CMove extends WebService {
	/*
	 * 添加胎动记录
	 */
	public static void addMovement(String date, int pregm) {
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
					String sql = "select id from FetalMovement m where date_format(m.f_date,'%Y-%m-%d') = '"
							+ dateStr + "' and m.user_id = '" + openid + "'";
					List moveList = JPA.em().createNativeQuery(sql)
							.getResultList();
					FetalMovement movement = new FetalMovement();
					if (moveList.size() > 0) {
						String wId = moveList.get(0).toString();
						movement = FetalMovement.findById(wId);
					} else {
						movement = new FetalMovement();
					}
					movement.fDate = newDate;
					movement.num = pregm;
					movement.userId = openid;
					movement.save();
					wsOk("保存成功");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			wsError("保存失败");
		}
	}

	/*
	 * 查找前7条胎动记录
	 */
	public static void findMovement() {
		String openid = session.get("openid");
		//openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select new beans.PregmBean(m.fDate,m.num) from FetalMovement m where  m.userId = '"
				+ openid + "' order by m.fDate desc";
		List<PregmBean> bean = JPA.em().createQuery(sql).setMaxResults(7)
				.getResultList();
		List<PregmBean> rBean = new ArrayList<PregmBean>();
		for (int i = 0; i < bean.size(); i++) {
			rBean.add(bean.get(bean.size() - 1 - i));
		}
		wsOk(rBean);
	}

	/*
	 * 查找前7条胎动记录
	 */
	public static void lastMovementChart() {
		String openid = session.get("openid");
		//openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select new beans.PregmBean(m.fDate,m.num) from FetalMovement m where  m.userId = '"
				+ openid + "' order by m.fDate desc";
		List<PregmBean> pregBean = JPA.em().createQuery(sql).setMaxResults(7)
				.getResultList();
		ChartBean bean = new ChartBean();
		List labelList = new ArrayList();
		List dataList = new ArrayList();
		for (int i = 0; i < pregBean.size(); i++) {
			labelList.add(pregBean.get(pregBean.size() - 1 - i).dateStr);
			dataList.add(pregBean.get(pregBean.size() - 1 - i).num);
		}
		bean.label = labelList;
		bean.data = dataList;
		wsOk(bean);
	}

	/*
	 * 查找胎动记录
	 */
	public static void findMove(String date) {
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
			String sql = "select id from FetalMovement m where date_format(m.f_date,'%Y-%m-%d') = '"
					+ dateStr + "' and m.user_id = '" + openid + "'";
			List moveList = JPA.em().createNativeQuery(sql).getResultList();
			if (moveList.size() > 0)
				wsOk(moveList.get(0));
			else
				wsError("未找到");
		} catch (Exception e) {
			wsError("出错");
		}
	}

	/*
	 * 删除胎动记录
	 */
	public static void removeMovement(String date) {
		String openid = session.get("openid");
		//openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		try {
			String sql = "select id from FetalMovement m where date_format(m.f_date,'%Y-%m-%d') = '"
					+ date + "' and m.user_id = '" + openid + "'";
			List moveList = JPA.em().createNativeQuery(sql).getResultList();
			if (moveList.size() > 0) {
				String id = moveList.get(0).toString();
				FetalMovement movement = FetalMovement.findById(id);
				movement.delete();
				wsOk("清除成功");
			} else
				wsError("清除失败");
		} catch (Exception e) {
			wsError("清除失败");
		}
	}

	/*
	 * 加载所有胎动记录
	 */
	public static void loadAllMovement() {
		String openid = session.get("openid");
		//openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select new beans.PregmBean(m.fDate,m.num) from FetalMovement m where  m.userId = '"
				+ openid + "' order by m.fDate";
		List<PregmBean> bean = JPA.em().createQuery(sql).getResultList();
		List<PregmBean> rBean = new ArrayList<PregmBean>();
		for (int i = 0; i < bean.size(); i++) {
			rBean.add(bean.get(bean.size() - 1 - i));
		}
		wsOk(bean);
	}

	/*
	 * 加载所有胎动图表记录
	 */
	public static void loadAllMovementChart() {
		String openid = session.get("openid");
		//openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select new beans.PregmBean(m.fDate,m.num) from FetalMovement m where  m.userId = '"
				+ openid + "' order by m.fDate";
		List<PregmBean> pregBean = JPA.em().createQuery(sql).getResultList();
		ChartBean bean = new ChartBean();
		List labelList = new ArrayList();
		List dataList = new ArrayList();
		for (int i = 0; i < pregBean.size(); i++) {
			labelList.add(pregBean.get(pregBean.size() - 1 - i).dateStr);
			dataList.add(pregBean.get(pregBean.size() - 1 - i).num);
		}
		bean.label = labelList;
		bean.data = dataList;
		wsOk(bean);
	}

	/*
	 * 胎动详情页面
	 */
	public static void pregmDetail() {
		render("/Client/record/pregmDetail.html");
	}
}
