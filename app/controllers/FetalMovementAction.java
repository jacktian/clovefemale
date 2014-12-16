package controllers;

import java.util.Date;
import java.util.List;

import models.FetalMovement;

/**
 * 胎动控制器
 * 
 * @author caterZhong
 * @since 2014/12/16
 */
public class FetalMovementAction extends WebService{
	//增加胎动记录
	public static void addMovement(String userId, Date fDate, int num){
		FetalMovement movement = new FetalMovement(userId,fDate, num);
		movement.save();
	}
	
	//获取某个用户的所有胎动记录
	public static void listByUser(String userId){
		List<FetalMovement> movements = FetalMovement.find("userId = ?", userId).fetch();
		wsOk(movements);
	}
}
