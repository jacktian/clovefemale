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
 * 孕重控制器，与客户端交互专用
 * 
 * @author Tanshichang
 * @since 2015-2-10
 */
public class FgGestationalWeightAction extends WebService {

	// 增加孕重记录
	public static void addWeight(GestationalWeight model) {
		String result = "";
		if (model != null) {
			if (model.wValue <= 0 || model.wDate == null) {
				result = "fail";
			} else {
				try {
					model.save();
					result = "success";
				} catch (Exception e) {
					e.printStackTrace();
					result = "fail";
				}
			}
		} else {
			result = "fail";
		}
		wsOkAsJsonP(result);
	}
}
