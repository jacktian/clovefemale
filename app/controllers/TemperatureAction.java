package controllers;

import java.util.Date;
import java.util.List;

import models.Temperature;

/**
 * 基础体温控制器
 * 
 * @author caterZhong
 * @since 2014/12/16
 */
public class TemperatureAction extends WebService{
	//添加基础体温
	public static void addTemperature(String userId, Date tDate, float tValue){
		Temperature temperature = new Temperature(userId, tDate, tValue);
		temperature.save();
	}
	
	//查看某个用户的所有基础体温记录
	public static void findByUser(String userId){
		List<Temperature> temperatures = Temperature.find("userId = ?", userId).fetch();
		wsOk(temperatures);
	}
}
