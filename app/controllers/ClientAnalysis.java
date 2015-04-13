package controllers;

import java.util.LinkedList;
import java.util.List;

/**
 * 客户端分析页面控制器
 * 
 * @author Tan Shichang
 * @since 2015-4-13
 */
public class ClientAnalysis extends WebService {

	/**
	 * 月经分析
	 */
	public static void mense() {
		//状况评分，100分制
		renderArgs.put("percent", "90") ;
		renderArgs.put("assessment", "月经状况良好") ;
		//两次月经开始的时间相隔多少
		renderArgs.put("avgCycle", "25") ;
		//每次来月经持续的时间
		renderArgs.put("avgMCycle", "5") ;
		render("/Client/record/analysis/mense.html");
	}
	
	/**
	 * 体重分析
	 */
	public static void weight() {
		//状况评分，100分制
		renderArgs.put("bmiNumber", "33.1") ;
		renderArgs.put("assessment", "身材肥胖") ;
		//状况级别评价，4个级别：standard, lightheavy, heavy, light
		renderArgs.put("level", "heavy") ;
		//两次月经开始的时间相隔多少
		renderArgs.put("weight", "70") ;
		//每次来月经持续的时间
		renderArgs.put("height", "165") ;
		render("/Client/record/analysis/weight.html");
	}
	
	/**
	 * 成绩分析
	 */
	public static void grade(){
		List<String> subjects = new LinkedList<String>();
		subjects.add("语文") ;
		subjects.add("数学") ;
		subjects.add("英语") ;
		subjects.add("物理") ;
		renderArgs.put("subjects", subjects);
		renderArgs.put("number", "90");
		renderArgs.put("weight", "90");
		renderArgs.put("height", "90");
		renderArgs.put("subject0", "语文");
		renderArgs.put("level", "standard");
		render("/Client/record/analysis/grade.html");
	}

	public static void pregnantChart() {
		render("Client/record/chart/pregnant.html");
	}
}
