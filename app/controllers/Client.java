package controllers;

import java.util.Date;
import java.util.List;


import play.cache.CacheFor;
import play.db.jpa.Model;
import play.mvc.*;

/**
 * 客户端控制器
 * 
 * @author boxiZen
 * @since 2015/03/23
 */
public class Client extends WebService{
	
	/**
	 * 记录控首页
	 */
	public static void record(){
		render("/Client/record/mense.html");
	}

	/**
	 * 助孕记录
	 */
	public static void pregMense(){
		render("/Client/record/mense.html");
	}

	public static void pregTemp(){
		render("/Client/record/temperature.html");
	}

	public static void pregWeight(){
		render("/Client/record/pregWeight.html");
	}
	
	public static void pregMove(){
		render("/Client/record/babyMove.html");
	}

	/**
	 *我的孩子
	 **/
	public static void mybaby(){
		render("/Client/record/mybaby.html");
	}

	/**
	 *孩子成长记录
	 **/
	public static void babyRecord(){
		render("/Client/record/babyRecord.html");
	}


	/**
	 *疫苗接种
	 **/
	public static void vaccine(){
		render("/Client/record/vaccine.html");
	}

    public static void pregnantChart() { render("Client/record/chart/pregnant.html"); }
}

