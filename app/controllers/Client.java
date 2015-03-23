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
		render("/Client/record/index.html");
	}
}

