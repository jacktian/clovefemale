package controllers;

import java.util.Date;
import java.util.List;

import models.Menses;

/**
 * 月经控制器，与客户端交互专用
 * 
 * @author Tanshichang
 * @since 2015-2-10
 */
public class FgMensesAction extends WebService {

	// 添加月经记录
	public static void addMenses(Menses model) {
		String result = "";
		if (model != null) {
			model.mDate = new Date();
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
