package controllers;

import java.util.Date;
import java.util.List;

import models.Menses;

/**
 * 助孕记录
 * @author boxiZen
 * @since 2015/05/22
 */
public class CPregnant extends WebService {

	/*
	 * 添加月经记录
	 */
	public static void addMenses(String menseColor,String menseChou,String menseHurt,String menseLquid,String menseNum,String menseLiquid,String date) {
		String openid = session.get("openid");
		openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		System.out.println("date="+date);
		try{
			if(openid == null){
				wsError("openid过期");
			}
			else{
				Menses mense = new Menses();
				mense.mColor = menseColor;
				mense.mMeasure = menseNum;
				if(menseLiquid.equals("有"))
					mense.mPiece = true;
				else
					mense.mPiece = false;
				mense.vicidity = menseChou;
				if(menseHurt.endsWith("有"))
					mense.isMcramp = true;
				else
					mense.isMcramp = false;
				mense.userId = openid;
				if(date==null||date.equals("")){
					mense.mDate = new Date();
				}
				else{
					mense.mDate = new Date(date);
				}
				mense.save();
				wsOk("保存成功");
			}
		}
		catch(Exception e){
			e.printStackTrace();
			wsError("保存失败");
		}
	}

}
