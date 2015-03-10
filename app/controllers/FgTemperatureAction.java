package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
				model.save();
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
