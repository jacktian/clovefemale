package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.FetalMovement;
import models.GestationalWeight;

/**
 * 胎动控制器，与客户端交互专用
 * 
 * @author Tanshichang
 * @since 2015-2-10
 */
public class FgFetalMovementAction extends WebService {

	// 增加胎动记录
	public static void addMovement(FetalMovement model) {
		String result = "";
		if (model != null) {
			try {
				model.fDate = new Date();
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
