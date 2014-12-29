package controllers;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import play.mvc.Controller;
import play.mvc.Http.Request;

import com.google.gson.Gson;

/**
 * 
 * @author TanShichang
 *
 */
public class ChartTest extends Controller {

	public static void getLineHeightStore(){
		printParams() ;
		generateRandomData(10, "height", 150, "date", 0, "2014-01-0") ;
	}
	
	public static void getLineGradeStore(String startDate, String endDate){
		System.out.println("\t startDate : " + startDate);
		System.out.println("\t endDate : " + endDate);
		System.out.println();
		printParams() ;
		generateRandomData(10, "grade", 80, "date", 0, "2014-01-0") ;
	}
	
	public static void getLineTempStore(){
		printParams() ;
		generateRandomData(10, "temperature", 36, "date", 0, "2014-01-0") ;
	}
	
	public static void getLineBabyMoveStore(){
		printParams() ;
		generateRandomData(10, "time", 5, "date", 0, "2012-04-0") ;
	}
	
	public static void getLineGesWeightStore(){
		printParams() ;
		generateRandomData(10, "weight", 20, "date", 0, "2013-05-0") ;
	}
	
	public static void getPieGradeStore(){
		printParams() ;
		generateRandomData(10, "time", 3, "grade", 0, "分数区间") ;
	}
	
	public static void getPieTempStore(){
		printParams() ;
		generateRandomData(5, "time", 20, "temperature", 0, "温度区间") ;
	}
	
	public static void getPieBabyMoveStore(){
		printParams() ;
		generateRandomData(5, "time", 12, "num", 0, "次数区间") ;
	}
	
	private static void generateRandomData(int dataNum, String paramName1, double baseValue1
			, String paramName2, int baseValue2, String baseValue2String){
		List<Map<String, String>> list = new LinkedList<Map<String, String>>();
		Random random = new Random() ;
		for(int i = 0 ; i < dataNum ; i++){
			Map<String, String> map = new LinkedHashMap<String, String>() ;
			if(i%1 == 0)
				baseValue1 += random.nextDouble()*(random.nextDouble()*10) ;
			else
				baseValue1 -= random.nextDouble()*(random.nextDouble()*10) ;
			map.put(paramName1, formatDoubleWith2Decimal(baseValue1)) ;
			if(StringUtils.isEmpty(baseValue2String)){
				map.put(paramName2, "" + (baseValue2 + i)) ;
			}else{
				map.put(paramName2, baseValue2String + i) ;
			}
			list.add(map) ;
		}
		rend(list) ;
	}
	
	private static void printParams(){
		Request request = Request.current();
		Map<String, String[]> params = request.params.all() ;
		for(String key : params.keySet()){
			System.out.print("\t" + key + " : ");
			for(String s : params.get(key)){
				System.out.print("\t" + s + ",");
			}
			System.out.print("\n");
		}
	}
	
	private static void rend(List<Map<String, String>> list){
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

	/**
	 * 保留两位小数
	 * @param num
	 * @return
	 */
	private static String formatDoubleWith2Decimal(double num){
		DecimalFormat numF = (DecimalFormat) NumberFormat.getInstance() ;
		numF.setMaximumFractionDigits(2) ;
		numF.setMinimumFractionDigits(2);
		return numF.format(num) ;
	}
}
