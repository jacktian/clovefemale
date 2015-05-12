package controllers;

import java.util.List;

import models.MedicineBox;

/**
 * 药箱控制器
 * 
 * @author boxiZen
 * @since 2015/05/12
 */
public class CMedicine extends WebService{
	
	/**
	 * 添加药箱
	 */
	public static void addMedBox(String name,String mark){
		String openid = session.get("openid");
		MedicineBox medBox = new MedicineBox();
		medBox.userId = openid;
		medBox.name = name;
		medBox.mark = mark;
		try{
			medBox.save();
			wsOk("创建成功");
		}
		catch(Exception e){
			wsError("创建失败");
		}
	}
	
	/**
	 * 删除药箱
	 */
	public static void delMedBox(String medBoxId){
		MedicineBox medbox = MedicineBox.findById(medBoxId);
		try{
			medbox.delete();
			wsOk("删除成功");
		}
		catch(Exception e){
			wsOk("删除失败");
		}
	}
	
	/**
	 * 加载药箱
	 */
	public static void loadMedboxList(){
		String openid = session.get("openid");
		List<MedicineBox> medboxList = MedicineBox.find("byUserId", openid).fetch(); 
		//List<MedicineBox> medboxList = MedicineBox.findAll();
		wsOk(medboxList);
	}
}
