package utils;

import models.MedicineBox;

/**
 * 新建用户初始化工具
 * 
 * @author boxiZen
 * @since 2015/05/21
 */

public class UserInitUtil {
	public static void initMedBox(String openid){
		MedicineBox medBox = new MedicineBox();
		medBox.userId = openid;
		medBox.name = "内服药";
		medBox.disabled = true;
		medBox.save();
		medBox = new MedicineBox();
		medBox.userId = openid;
		medBox.name = "外服药";
		medBox.disabled = true;
		medBox.save();
		medBox = new MedicineBox();
		medBox.userId = openid;
		medBox.name = "儿童小药箱";
		medBox.disabled = true;
		medBox.save();
		medBox = new MedicineBox();
		medBox.userId = openid;
		medBox.name = "医用材料";
		medBox.disabled = true;
		medBox.save();
	}
}
