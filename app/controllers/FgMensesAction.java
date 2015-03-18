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
			// 判断是否已有今日的数据，有则覆盖，否则重新添加
			List<Menses> mense =  Menses.find("dateStr=? and userId=?", model.dateStr,model.userId).fetch();
			try {
				model.mDate = new Date();
				if(mense.size()>0){
					mense.get(0).mColor = model.mColor;
					mense.get(0).mMeasure = model.mMeasure;
					mense.get(0).mPiece = model.mPiece;
					mense.get(0).isMcramp = model.isMcramp;
					mense.get(0).vicidity = model.vicidity;
					mense.get(0).save();
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
