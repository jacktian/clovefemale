package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.BodyIndex;
import models.GradeForm;
import models.Vaccination;
import beans.BIndexBean;
import beans.GradeBean;

/**
 * 宝贝成长控制器，与客户端交互专用
 * 
 * @author Tanshichang
 * @since 2015-2-10
 */
public class FgBabyGrowth extends WebService {

	public static void addVaccination(Vaccination model) {
		FgAction.saveModel(model);
	}

	public static void addGradeCondition(GradeForm model) {
		FgAction.saveModel(model);
	}

	public static void addBodyIndex(BodyIndex model) {
		String result = "";
		if (model != null) {
			if (model.height <= 0 || model.weight <= 0) {
				result = "fail";
			} else {
				FgAction.saveModel(model);
			}
		} else {
			result = "fail";
		}
		wsOkAsJsonP(result);
	}

}
