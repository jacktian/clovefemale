package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beans.ChartBean;
import beans.PregmBean;
import beans.PregwBean;
import beans.MovementBean;
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
	public static void addMovement(String date, int pregm, int time) {
		String openid = session.get("openid");
		openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		System.out.println(time);
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
					// 判断今天是否第一次纪录胎动
					String sql = "select id from FetalMovement m where date_format(m.f_date,'%Y-%m-%d') = '"
							+ dateStr + "' and m.user_id = '" + openid + "' and m.time = '"+time+"'";
					List moveList = JPA.em().createNativeQuery(sql)
							.getResultList();
					FetalMovement movement = new FetalMovement();
					// 修改记录
					if (moveList.size() > 0) {
						String wId = moveList.get(0).toString();
						movement = FetalMovement.findById(wId);
						movement.fDate = newDate;
						movement.num = pregm;
						movement.userId = openid;
						movement.time = time;
						movement.save();
					}
					// 添加记录
					else {
						// 添加当前记录
						movement = new FetalMovement();
						movement.fDate = newDate;
						movement.num = pregm;
						movement.userId = openid;
						movement.time = time;
						movement.save();
						// 初始化其他时间段的记录，默认为0
						String sql2 = "select time from FetalMovement m where date_format(m.f_date,'%Y-%m-%d') = '"
								+ dateStr + "' and m.user_id = '" + openid + "'";
						List moveList2 = JPA.em().createNativeQuery(sql2)
								.getResultList();
						for(int i=0;i<3;i++){
							int flag = 0;
							for(int j=0;j<moveList2.size();j++){
								System.out.println(moveList2.get(j).toString());
								int wTime = Integer.parseInt(moveList2.get(j).toString());
								if(wTime == i){
									flag = 1;
									break;
								}
							}
							if(flag == 0){
								movement = new FetalMovement();
								movement.fDate = newDate;
								movement.num = -1;
								movement.userId = openid;
								movement.time = i;
								movement.save();
							}
						}
					}

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
		openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select new beans.PregmBean(m.fDate,m.num,m.time) from FetalMovement m where  m.userId = '"
				+ openid + "' order by m.fDate desc";
		List<PregmBean> bean = JPA.em().createQuery(sql).setMaxResults(21)
				.getResultList();
		/*for(int i = 0; i < bean.size(); i++){
			System.out.println("i=" + i + "," + bean.get(i).date + " " + bean.get(i).dateStr + " " + bean.get(i).num);
		}*/
		List<PregmBean> rBean = new ArrayList<PregmBean>();
		int sum=0,count = 0;
		for (int i = 0; i < bean.size(); i++) {
			//int num = bean.get(bean.size() - 1 - i).num;
			int num = bean.get(i).num;
			if(num == -1) {
				count++;
			}else{
				sum += num;
			}
			if((i+1)%3==0){
				PregmBean fbean = new PregmBean();
				fbean.date = bean.get(i).date;
				fbean.dateStr = bean.get(i).dateStr;
				System.out.println(fbean.dateStr);
				if(count == 0) {
					fbean.num = sum*4;
				}
				else if(count == 1){
					fbean.num = sum * 6;
				}
				else {
					fbean.num = sum * 12;
				}
				sum = 0;
				rBean.add(fbean);
				count = 0;
			}
		}

		wsOk(rBean);
	}

	/*
	 * 查找前7条胎动记录
	 */
	public static void lastMovementChart() {
		String openid = session.get("openid");
		openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select new beans.PregmBean(m.fDate,m.num,m.time) from FetalMovement m where  m.userId = '"
				+ openid + "' order by m.fDate desc";
		List<PregmBean> pregBean = JPA.em().createQuery(sql).setMaxResults(21)
				.getResultList();
		ChartBean bean = new ChartBean();
		List labelList = new ArrayList();
		List morning = new ArrayList();
		List afternoon = new ArrayList();
		List evening = new ArrayList();
		for (int i = 0; i < pregBean.size(); i++) {
			if((i+1)%3==0){
				labelList.add(pregBean.get(pregBean.size() - 1 - i).dateStr);
			}
			if(pregBean.get(pregBean.size() - 1 - i).time == 0){
				morning.add(pregBean.get(pregBean.size() - 1 - i).num);
			}
			else if(pregBean.get(pregBean.size() - 1 - i).time == 1){
				afternoon.add(pregBean.get(pregBean.size() - 1 - i).num);
			}
			else{
				evening.add(pregBean.get(pregBean.size() - 1 - i).num);
			}

		}
		System.out.println(evening.size());
		bean.label = labelList;
		bean.morning = morning;
		bean.afternoon = afternoon;
		bean.evening = evening;
		wsOk(bean);
	}

	/*
	 * 查找胎动记录
	 */
	public static void findMove(String date) {
		String openid = session.get("openid");
		openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
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
		openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		try {
			String sql = "select id from FetalMovement m where date_format(m.f_date,'%Y-%m-%d') = '"
					+ date + "' and m.user_id = '" + openid + "'";
			List moveList = JPA.em().createNativeQuery(sql).getResultList();
			if (moveList.size() > 0) {
				for(int i=0;i<moveList.size();i++){
					String id = moveList.get(i).toString();
					FetalMovement movement = FetalMovement.findById(id);
					movement.delete();
				}
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
	  	openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select new beans.PregmBean(m.fDate,m.num,m.time) from FetalMovement m where m.num != -1 and m.userId = '"
				+ openid + "' order by m.fDate desc,m.time desc";
		List<PregmBean> bean = JPA.em().createQuery(sql).getResultList();
		/*List<PregmBean> rBean = new ArrayList<PregmBean>();
		for (int i = 0; i < bean.size(); i++) {
			rBean.add(bean.get(bean.size() - 1 - i));
		}*/
		wsOk(bean);
	}

	/*
	 * 加载所有胎动图表记录
	 */
	public static void loadAllMovementChart() {
		String openid = session.get("openid");
		openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		String sql = "select new beans.PregmBean(m.fDate,m.num,m.time) from FetalMovement m where  m.userId = '" + openid + "' order by m.fDate desc";
		List<PregmBean> pregBean = JPA.em().createQuery(sql)
				.getResultList();

		/*for(int i = 0; i < pregBean.size(); i++){
			System.out.println("i=" + i + "," + pregBean.get(i).dateStr + "," + pregBean.get(i).time + "," + pregBean.get(i).num);
		}*/
		ChartBean bean = new ChartBean();
		List labelList = new ArrayList();
		List morning = new ArrayList();
		List afternoon = new ArrayList();
		List evening = new ArrayList();

		for (int i = 0; i < pregBean.size(); i++) {
			if((i+1)%3==0){
				labelList.add(pregBean.get(pregBean.size() - 1 - i).dateStr);
			}
			if(pregBean.get(pregBean.size() - 1 - i).time == 0){
				morning.add(pregBean.get(pregBean.size() - 1 - i).num);
			}
			else if(pregBean.get(pregBean.size() - 1 - i).time == 1){
				afternoon.add(pregBean.get(pregBean.size() - 1 - i).num);
			}
			else{
				evening.add(pregBean.get(pregBean.size() - 1 - i).num);
			}

		}
		System.out.println(evening.size());
		bean.label = labelList;
		bean.morning = morning;
		bean.afternoon = afternoon;
		bean.evening = evening;
		wsOk(bean);
	}

	/*
	 * 胎动详情页面
	 */
	public static void pregmDetail() {
		render("/Client/record/pregmDetail.html");
	}
}
