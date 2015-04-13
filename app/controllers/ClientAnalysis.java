package controllers;

import java.util.Date;
import java.util.List;

import play.cache.CacheFor;
import play.db.jpa.Model;
import play.mvc.*;

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

	public static void pregnantChart() {
		render("Client/record/chart/pregnant.html");
	}
}
