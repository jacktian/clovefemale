package controllers;

import java.util.Date;
import java.util.List;

import models.Menses;

/**
 * 月经控制器
 * 
 * @author caterZhong
 * @since 2014/12/16
 */
public class MensesAction extends WebService{
	
	//添加月经记录
	public static void addMenses(Menses model){
		String result = "" ;
		if(model != null){
		 	model.mDate = new Date() ;
		 	try{
				model.save() ;
				result = "success" ;
		 	}catch(Exception e){
		 		e.printStackTrace() ;
		 		result = "fail" ;
		 	}
		}else{
			result = "fail" ;
		}
		wsOkAsJsonP(result) ;
	}
	
	public static void listMenseses(){
		List<Menses> menseses = Menses.findAll();
		wsOk(menseses);
	}
	
	//获取某个用户的所有月经记录
	public static void listByUser(String userId){
		 List<Menses> menseses = Menses.find("userId = ?", userId).fetch();
    	 wsOk(menseses);
	}

}
