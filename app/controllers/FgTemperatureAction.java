package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Menses;
import models.Temperature;

/**
 * 客户端专用体温控制器
 * @author Tanshichang
 * @since  2015-3-1
 */
public class FgTemperatureAction extends WebService {

	// 添加基础体温
	public static void addTemperature(Temperature model) {
		String result = "";
		
		if (model != null) {
			model.tDate = new Date();
			try {
				// 判断是否已有今日的数据，有则覆盖，否则重新添加
				List<Temperature> temp =  Temperature.find("dateStr=? and userId=?", model.dateStr,model.userId).fetch();
				if(temp.size()>0){
					temp.get(0).tValue = model.tValue;
					temp.get(0).save();
				}
				else{
					model.save();
				}
				result = "success";
			} catch (Exception e) {
				e.printStackTrace();
				result = "fail";
			}
		} else {
			result = "fail";
		}
		System.out.println(model.tValue);
		wsOkAsJsonP(result);
	}

}
