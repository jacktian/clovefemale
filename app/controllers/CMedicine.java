package controllers;

import java.util.List;

import models.Medicine;
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
		medBox.disabled = true;
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
	public static void removeMedBox(String mid){
		String[] medBoxId = mid.split(",");
		MedicineBox medBox = null;
		try{
			for(int i=0;i<medBoxId.length;i++){
				medBox = MedicineBox.findById(medBoxId[i]);
				if(medBox!=null){
					medBox.delete();
				}
			}
			wsOk("删除成功");
		}
		catch(Exception ex){
			wsError("删除失败");
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
	
	/**
	 * 加载药品
	 */
	public static void loadMedicineList(){
		String medboxid = session.get("medboxid");
		List<Medicine> medicineList = Medicine.find("byMedicineBoxId", medboxid).fetch(); 
		//List<MedicineBox> medboxList = MedicineBox.findAll();
		wsOk(medicineList);
	}
	
	/**
	 * 加载所有药品
	 */
	public static void loadAllMedicine(){
		List<Medicine> medicineList = Medicine.findAll(); 
		//List<MedicineBox> medboxList = MedicineBox.findAll();
		wsOk(medicineList);
	}
	
	/**
	 * 
	 * 删除药品
	 */
	public static void removeMedicine(String mid){
		String[] medId = mid.split(",");
		Medicine medicine = null;
		try{
			for(int i=0;i<medId.length;i++){
				medicine = Medicine.findById(medId[i]);
				if(medicine!=null){
					medicine.delete();
				}
			}
			wsOk("删除成功");
		}
		catch(Exception ex){
			wsError("删除失败");
		}
	}
}
