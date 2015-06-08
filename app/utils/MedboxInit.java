package utils;

import models.MedicineBox;

/**
 * 药箱初始化
 * @author boxizen
 * @since 2015/06/09
 */
public class MedboxInit {
	/*
	 * 初始化药箱
	 */
	public static void init(String openid){
		openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
			try{
				// 内服药
				MedicineBox medBox = new MedicineBox();
				medBox.userId = openid;
				medBox.name = "内服药";
				medBox.mark = "常规口服药";
				medBox.disabled = 1;
				medBox.save();
				// 外用药箱
				medBox = new MedicineBox();
				medBox.userId = openid;
				medBox.name = "外用药";
				medBox.mark = "驱风油，皮肤外用软膏等";
				medBox.disabled = 1;
				medBox.save();
				// 儿童小药箱
				medBox = new MedicineBox();
				medBox.userId = openid;
				medBox.name = "儿童小药箱";
				medBox.mark = "专设儿童药箱";
				medBox.disabled = 1;
				medBox.save();
				// 医用材料
				medBox = new MedicineBox();
				medBox.userId = openid;
				medBox.name = "医用材料";
				medBox.mark = "血压计，血糖计，验孕避孕等";
				medBox.disabled = 1;
				medBox.save();
				// 其他
				medBox = new MedicineBox();
				medBox.userId = openid;
				medBox.name = "其他";
				medBox.disabled = 1;
				medBox.save();
			}
			catch(Exception e){
				
			}
		}
}
