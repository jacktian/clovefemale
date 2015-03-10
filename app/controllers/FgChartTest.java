package controllers;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.Query;

import models.GradeCondition;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import play.db.jpa.JPA;
import play.mvc.Controller;
import play.mvc.Http.Request;

import com.google.gson.Gson;

/**
 * 图表数据测试控制器，与客户端交互专用
 * 
 * @author Tanshichang
 * @since 2015-2-10
 */
public class FgChartTest extends Controller {

	public static void getSubjects() {
		String[] texts = { "语文", "数学", "英语", "物理", "化学", "生物", "政治", "历史", "地理" };
		List<Map<String, String>> list = new LinkedList<Map<String, String>>();
		LinkedHashMap<String, String> map = null;
		Random random = new Random();
		int len = 3 + random.nextInt(5);
		for (int i = 0; i <= len; i++) {
			map = new LinkedHashMap<String, String>();
			map.put("text", texts[i]);
			map.put("value", "" + i);
			list.add(map);
		}
		try {
			rend(list);
		} catch (Exception e) {
			System.out.println("======");
			System.out.println(e.getMessage());
		}
	}

	public static void getLineHeightStore() {
		// printParams() ;
		generateRandomData(10, "height", 150, "date", 0, "2014-01-0");
	}

	public static void getLineGradeStore(String startDate, String endDate) {
		// System.out.println("\t startDate : " + startDate);
		// System.out.println("\t endDate : " + endDate);
		// System.out.println();
		// printParams() ;
		generateRandomData(10, "grade", 80, "date", 0, "2014-01-0");
	}

	public static void getLineTempStore() {
		// printParams() ;
		generateRandomData(10, "temperature", 36, "date", 0, "2014-01-0");
	}

	public static void getLineBabyMoveStore() {
		// printParams() ;
		generateRandomData(10, "time", 5, "date", 0, "2012-04-0");
	}

	public static void getLineGesWeightStore() {
		// printParams() ;
		generateRandomData(10, "weight", 20, "date", 0, "2013-05-0");
	}

	public static void getPieGradeStore() {
		// printParams() ;
		generateRandomData(10, "time", 3, "grade", 0, "分数区间");
	}

	public static void getPieTempStore() {
		// printParams() ;
		generateRandomData(5, "time", 20, "temperature", 0, "温度区间");
	}

	public static void getPieBabyMoveStore() {
		// printParams() ;
		generateRandomData(5, "time", 12, "num", 0, "次数区间");
	}

	private static void generateRandomData(int dataNum, String paramName1,
			double baseValue1, String paramName2, int baseValue2,
			String baseValue2String) {
		List<Map<String, String>> list = new LinkedList<Map<String, String>>();
		Random random = new Random();
		for (int i = 0; i < dataNum; i++) {
			Map<String, String> map = new LinkedHashMap<String, String>();
			if (i % 1 == 0)
				baseValue1 += random.nextDouble() * (random.nextDouble() * 10);
			else
				baseValue1 -= random.nextDouble() * (random.nextDouble() * 10);
			map.put(paramName1, formatDoubleWith2Decimal(baseValue1));
			if (StringUtils.isEmpty(baseValue2String)) {
				map.put(paramName2, "" + (baseValue2 + i));
			} else {
				map.put(paramName2, baseValue2String + i);
			}
			list.add(map);
		}
		rend(list);
	}

	private static void printParams() {
		Request request = Request.current();
		Map<String, String[]> params = request.params.all();
		for (String key : params.keySet()) {
			System.out.print("\t" + key + " : ");
			for (String s : params.get(key)) {
				System.out.print("\t" + s + ",");
			}
			System.out.print("\n");
		}
	}

	protected static void rend(List<Map<String, String>> list) {
		Request request = Request.current();
		String callback = request.params.get("callback");
		if (StringUtils.isEmpty(callback)) {
			renderJSON(list);
		} else {
			Gson gson = new Gson();
			String json = gson.toJson(list);
			String resultString = callback + "(" + json + ")";
			renderJSON(resultString);
		}
	}

	public static void test01() {
		System.out.println(String.format("%s\tChartTest.test01",
				getCurrentTimeMilli()));
		printParams();
		List<Map<String, String>> list = new LinkedList<Map<String, String>>();
		for (int i = 0; i < 5; i++) {
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("id", "1" + i);
			map.put("name", "Tom" + i);
			map.put("age", "2" + i);
			map.put("gender", "male");
			list.add(map);
		}
		rend(list);
	}

	public static void test() {
		List<GradeCondition> list = GradeCondition
				.find("select g.subject from GradeCondition g,\n"
						+ "(select g2.id, max(g2.date) as date, g2.subject from GradeCondition g2 group by g2.id, g2.subject) as tt\n"
						+ " where g.date=tt.date and g.id=tt.id and \n"
						+ " g.subject=tt.subject and g.subject=?", "语文")
				.fetch();
		renderJSON("hello");
	}

	public static void test2() {
		List<GradeCondition> list = GradeCondition.find(
				"select g.* from GradeCondition g").fetch();
		renderJSON("hello2");
	}

	public static void test3() {
		Query q = JPA
				.em()
				.createNativeQuery(
						"select g.date, g.grade,g.subject,g.mark, g.baby_id from GradeCondition g,\n"
								+ "(select g2.id, MAX(g2.date) as date, g2.subject from GradeCondition g2 group by g2.id, g2.subject) as tt\n"
								+ " where g.date=tt.date and g.id=tt.id and \n"
								+ " g.subject=tt.subject and g.subject='语文'");
		String s = "Hwllo hello hello hello.  hello hello hello hello. hello hello hello. hello hello hello. hello hello hello. hello hello hello. hello hello hello. hello hello hello. hello hello hello. hello hello hello. hello hello hello. hello hello hello. hello hello hello. hello hello hello. hello hello hello. hello hello hello. hello hello hello. hello hello hello. hello hello hello. hello hello hello. hello hello hello.";
		System.out.println(s);
		List list = q.getResultList();
		for (Object o : list) {
			Object[] cols = (Object[]) o;
			System.out.println("object is :" + o);
			System.out.println("cols[0] :" + cols[0]);
			System.out.println("cols[1] :" + cols[1]);
			System.out.println("cols[2] :" + cols[2]);
		}
		int a = q.getMaxResults();
		System.out.println("adsf" + a);
		renderJSON("hello2");
	}

	public static void test4() {
		String sql = "select g.subject from GradeCondition g,\n"
				+ "(select g2.id, MAX(g2.date) as date, g2.subject from GradeCondition g2 group by g2.id, g2.subject) as tt\n"
				+ " where g.date=tt.date and g.id=tt.id and \n"
				+ " g.subject=tt.subject and g.subject='Chine'";
		Session sess = (Session) JPA.em().getDelegate();
		Session s = sess.getSessionFactory().openSession();
		SQLQuery qu = s.createSQLQuery(sql);
		org.hibernate.Query q2 = s.createQuery(sql);
		// q2.
		renderJSON("hello4");
	}

	/**
	 * 保留两位小数
	 * 
	 * @param num
	 * @return
	 */
	private static String formatDoubleWith2Decimal(double num) {
		DecimalFormat numF = (DecimalFormat) NumberFormat.getInstance();
		numF.setMaximumFractionDigits(2);
		numF.setMinimumFractionDigits(2);
		return numF.format(num);
	}

	private static String getCurrentTimeMilli() {
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return sdf3.format(new Date());
	}
}
