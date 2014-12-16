package controllers;

import java.util.Date;
import java.util.List;

import models.GestationalWeight;

/**
 * 孕重控制器
 * 
 * @author caterZhong
 * @since 2014/12/16
 */
public class GestationalWeightAction extends WebService {
	//增加孕重记录
	public static void addWeight(String userId, Date wDate, float wValue){
		GestationalWeight weight = new GestationalWeight(userId,wDate, wValue);
		weight.save();
	}
	
	//获取某个用户的所有孕重记录
	public static void listByUser(String userId){
		List<GestationalWeight> weights = GestationalWeight.find("userId = ?", userId).fetch();
		wsOk(weights);
	}
}
