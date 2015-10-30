package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.monitor.GaugeMonitor;

import models.GestationalWeight;
import models.Menses;

/**
 * 体重控制器，与客户端交互专用
 * 
 * @author Tanshichang
 * @since 2015-2-10
 */
public class FgGestationalWeightAction extends WebService {

	// 增加体重记录
	public static void addWeight(GestationalWeight model) {
		String result = "";
		if (model != null) {
			
			List<GestationalWeight> weight =  GestationalWeight.find("dateStr=? and userId=?", model.dateStr,model.userId).fetch();				
				try {
					// 判断是否已有今日的数据，有则覆盖，否则重新添加
					if(weight.size()>0){
						weight.get(0).wValue = model.wValue;
						weight.get(0).save();
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
		wsOkAsJsonP(result);
	}
}
