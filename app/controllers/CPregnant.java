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
    public static void addMenses(String menseColor,
                                 String menseHurt, String menseLquid, String menseNum,
                                 String menseLiquid, String date) {
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
                    String sql = "select id from Menses m where date_format(m.m_date,'%Y-%m-%d') = '"
                            + dateStr + "' and m.user_id = '" + openid + "'";
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
                    mense.mPiece = menseLiquid;
                    mense.isMcramp = menseHurt;
                    mense.userId = openid;
                    mense.mDate = newDate;

                    mense.save();
                    wsOk("保存成功");
                }
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
        //openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
        String sql = "select m.m_color,m.m_measure,m.m_piece,m.is_mcramp,date_format(m.m_date,'%Y-%m-%d') from Menses m where m.m_date in (select max(m_date) from Menses where user_id = '"
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
        //openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
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
        //openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
        String sql = "select m_date from Menses m where m.user_id = '"
                + openid + "' order by m.m_date asc";
        List resultList = JPA.em().createNativeQuery(sql).getResultList();
        List<String> dateStrList = new ArrayList<String>();
        List<Date> dateList = new ArrayList<Date>();
        List startList = new ArrayList();
        List<String> startStrList = new ArrayList<String>();
        List cons = new ArrayList();
        List timeList = new ArrayList();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            for (int i = 0; i < resultList.size(); i++) {
                String date = resultList.get(i).toString().split(" ")[0];
                dateStrList.add(date);
                dateList.add(format.parse(date));
            }

            startList.add(dateList.get(0));
            startStrList.add(dateStrList.get(0));
            Date curTime = dateList.get(0);
            for(int i = 1;i<dateList.size();i++){
                long dis = dateList.get(i).getTime() - curTime.getTime();
                long time = dis/1000/60/60;
                if(time>24) {
                    startList.add(dateList.get(i));
                    startStrList.add(dateStrList.get(i));
                }
                curTime = dateList.get(i);
            }
            /*for(int i = 0;i<startList.size();i++){
                System.out.println("开始日期:"+startStrList.get(i));
            }*/

            Date curDate = dateList.get(0);
            for(int i = 1;i<startStrList.size();i++){
                Date d = format.parse(startStrList.get(i));
                long timeDis = d.getTime() - curDate.getTime();
                System.out.println(timeDis/1000/60/60/24);
                timeList.add(timeDis / 1000 / 60 / 60 / 24);
                curDate = d;
            }
            startStrList.remove(startStrList.size()-1);
            ChartBean bean = new ChartBean();
            bean.label = startStrList;
            bean.data = timeList;
            System.out.println(startStrList);
            wsOk(bean);
        } catch (Exception e) {
            e.printStackTrace();
            wsError("error");
        }
        /*ChartBean bean = new ChartBean();
        ArrayList labelList = new ArrayList();
		ArrayList dataList = new ArrayList();
		String sql = "select new beans.MenseBean(m.mDate as mDate,m.time as time) from Menses m where m.userId = '"
				+ openid + "' order by m.mDate desc";
		List<MenseBean> resultList = JPA.em().createQuery(sql).setMaxResults(7)
				.getResultList();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < resultList.size(); i++) {
			Date date = resultList.get(resultList.size() - 1 - i).mDate;
			String dateStr = format.format(date);
			labelList.add(dateStr);
			dataList.add(resultList.get(resultList.size() - 1 - i).time);
		}
		bean.label = labelList;
		bean.data = dataList;
		wsOk(bean);*/
    }

    /*
     * 查找月经记录
     */
    public static void findMense(String date) {
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
            String sql = "select id from Menses m where date_format(m.m_date,'%Y-%m-%d') = '"
                    + dateStr + "' and m.user_id = '" + openid + "'";
            List menseList = JPA.em().createNativeQuery(sql).getResultList();
            if (menseList.size() > 0)
                wsOk(menseList.get(0));
            else
                wsError("无记录");
        } catch (Exception e) {
            wsError("出错");
        }
    }

    /*
     * 删除月经记录
     */
    public static void removeMense(String date) {
        String openid = session.get("openid");
        //openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
        String sql = "select id from Menses m where date_format(m.m_date,'%Y-%m-%d') = '"
                + date + "' and m.user_id = '" + openid + "'";
        List menseList = JPA.em().createNativeQuery(sql).getResultList();
        try {
            if (menseList.size() > 0) {
                String menseId = menseList.get(0).toString();
                Menses mense = Menses.findById(menseId);
                mense.delete();
                wsOk("删除成功");
            } else {
                wsError("删除失败");
            }
        } catch (Exception e) {
            wsError("删除失败");
        }
    }

    /*
     * 加载所有数据
     */
    public static void loadAllMense() {
        String openid = session.get("openid");
        //openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
        String sql = "select m.m_color,m.m_measure,m.m_piece,m.is_mcramp,date_format(m.m_date,'%Y-%m-%d') from Menses m where m.user_id = '"
                + openid + "' order by m.m_date";
        List bean = JPA.em().createNativeQuery(sql).getResultList();
		/*
		 * if (bean.size() == 0) { bean = new ArrayList(); bean.add("empty"); }
		 */
        wsOk(bean);

    }

    /*
     * 加载所有图表数据
     */
    public static void loadAllMenseChart() {
        String openid = session.get("openid");
        //openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
        String sql = "select m_date from Menses m where m.user_id = '"
                + openid + "' order by m.m_date asc";
        List resultList = JPA.em().createNativeQuery(sql).getResultList();
        List<String> dateStrList = new ArrayList<String>();
        List<Date> dateList = new ArrayList<Date>();
        List startList = new ArrayList();
        List<String> startStrList = new ArrayList<String>();
        List cons = new ArrayList();
        List timeList = new ArrayList();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            for (int i = 0; i < resultList.size(); i++) {
                String date = resultList.get(i).toString().split(" ")[0];
                dateStrList.add(date);
                dateList.add(format.parse(date));
            }

            startList.add(dateList.get(0));
            startStrList.add(dateStrList.get(0));
            Date curTime = dateList.get(0);
            for(int i = 1;i<dateList.size();i++){
                long dis = dateList.get(i).getTime() - curTime.getTime();
                long time = dis/1000/60/60;
                System.out.println("时间差:"+time+",");
                if(time>24) {
                    startList.add(dateList.get(i));
                    startStrList.add(dateStrList.get(i));
                }
                curTime = dateList.get(i);
            }
            /*for(int i = 0;i<startList.size();i++){
                System.out.println("开始日期:"+startStrList.get(i));
            }*/

            Date curDate = dateList.get(0);
            for(int i = 1;i<startStrList.size();i++){
                Date d = format.parse(startStrList.get(i));
                long timeDis = d.getTime() - curDate.getTime();
                timeList.add(timeDis/1000/60/60/24);
                curDate = d;
            }
            startStrList.remove(startStrList.size()-1);
            ChartBean bean = new ChartBean();
            bean.label = startStrList;
            bean.data = timeList;
            wsOk(bean);
        } catch (Exception e) {
            wsError("error");
        }
    }

    /*
     * 月经详情
     */
    public static void menseDetail() {
        render("/Client/record/menseDetail.html");
    }

    /*
     * 测试方法
     */
    public static void test() {
        String openid = session.get("openid");
        //openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
        String sql = "select new beans.MenseBean(m.mDate as mDate,m.time) from Menses m order by m.mDate";
        List<MenseBean> bean = JPA.em().createQuery(sql).getResultList();
        wsOk(bean);
    }
}
