package controllers;

import java.util.Date;
import java.util.List;

import beans.PregDetailBean;

import models.FetalMovement;
import models.GestationalWeight;
import models.GradeForm;
import models.Menses;
import models.Temperature;
import play.mvc.*;

/**
 * 客户端专用女性助手控制器
 * 
 * @author boxiZen
 * @since 2015/03/13
 */
public class FgPregAction extends WebService{
	public static void showPregMsg(String dateStr){
		PregDetailBean pregDetail = new PregDetailBean();
		
		/* 存储月经信息 */
		List<Menses> mense =  Menses.find("dateStr=?", dateStr).fetch();
		if(mense.size()>0)
			pregDetail.mense = mense.get(0);
		else
			pregDetail.mense = null;
		
		/* 存储体温信息 */
		List<Temperature> temperature =  Temperature.find("dateStr=?", dateStr).fetch();
		if(temperature.size()>0)
			pregDetail.temp = temperature.get(0);
		else
			pregDetail.temp = null;
		
		/* 存储体重信息 */
		List<GestationalWeight> weight =  GestationalWeight.find("dateStr=?", dateStr).fetch();
		if(weight.size()>0)
			pregDetail.weight = weight.get(0);
		else
			pregDetail.weight = null;
		
		/* 存储孕动信息 */
		List<FetalMovement> condition =  FetalMovement.find("dateStr=?", dateStr).fetch();
		if(condition.size()>0)
			pregDetail.condition = condition.get(0);
		else
			pregDetail.condition = null;
		
		wsOkAsJsonP(pregDetail);
		
	}
}