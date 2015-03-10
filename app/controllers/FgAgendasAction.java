package controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import models.Agenda;
import utils.DatabaseUtil;
import utils.StringUtils;

/**
 * 日程控制器，专门用于与客户端交互
 * @author Tanshichang
 * @since  2015-2-11
 */
public class FgAgendasAction extends WebService{

	/**
	 * 获取日程事件
	 * @param userId  用户ID
	 * @param createTime 指定创建日期，返回的日程的创建日期比此值大
	 */
	public static void getAgendas(String userId, long createTime){
		System.out.println(String.format("getAgendas(%s, %s)", userId, ""+createTime));
		List<Agenda> agendas = Agenda.find("userId=? and createTime>?", 
				userId, createTime).fetch() ;
		System.out.println("calendars' size is : " + agendas.size());
		String callback = request.params.get("callback");
		if(StringUtils.isEmpty(callback)){
			renderJSON(agendas) ;
		}else{
			wsOkAsExtJsonP(agendas) ;
		}
	}
	
	
	public static void getTestUserId(){
		String userId = DatabaseUtil.getTestUser().id ;
		wsOkAsExtJsonP(userId) ;
	}
	
	public static void getTestUser(){
		wsOkAsExtJsonP(DatabaseUtil.getTestUser()) ;
	}

	public static void create(Agenda model){
//		System.out.println("create agenda : " + model.toString());
		System.out.println("create agenda!!!!");
		Agenda agenda = model.save() ;
		Map<String, String> map = new LinkedHashMap<String, String>() ;
		map.put("id", agenda.id) ;
		map.put("result", "success") ;
		wsOkAsExtJsonP(map) ;
	}
	
	public static void update(Agenda model){
		System.out.println("update agenda : " + model.toString());
		Agenda a = Agenda.findById(model.id) ;
		if(a != null){
			Agenda agenda = model.save() ;
			Map<String, String> map = new LinkedHashMap<String, String>() ;
			map.put("id", agenda.id) ;
			map.put("result", "success") ;
			wsOkAsExtJsonP(map) ;
		}else{
			wsOkAsExtJsonP("fail") ;
		}
	}
}
