package controllers;

import java.util.List;

import models.Medicine;
import models.MedicineBox;
import models.User;
import beans.MBean;
import beans.userMBoxBean;

/**
 * 药物controller
 * @author Yingpeng
 * @since 03/05/15
 * */
public class MedicineAction extends WebService{

	/*查询所有的药箱
	 * */
	public static void findAllMB(){
		List<MedicineBox> list = MedicineBox.findAll();
		wsOk(list);
	}
	/*查询所有的药物
	 * */
	public static void findAllMedicine(){
		List<MedicineBox> list = MedicineBox.findAll();
		wsOk(list);
	}
	/*根据用户id查询所有药箱
	 * */
	public static void findMBbyUser(String userId,String userName,int curpage){
		if(curpage == 0){
	 		 curpage =1;
	 		}
	 		long count =0;
	 		long pageNum =0;
	 		List<MBean> listbean =null;
	 		List<MedicineBox> list = MedicineBox.find("userId = ?", userId).fetch(curpage,2);
	 		if(list!=null){
	 			count = MedicineBox.find("userId = ?", userId).fetch().size();
	 			if(count%2!=0)
	 	     		pageNum = count/2+1;
	 	     	else
	 	     		pageNum = count/2;
	 			
	 			listbean=MBean.builList(list);
	 		}
	 		
	 		render("/MedicineMgm/mBoxInfo.html",listbean,pageNum,curpage,userName,userId);
	}
	/**
	 * 根据药箱Id查询所有药物
	 * */
	public static void findMbyMB(String mbid,String mbname,int curpage){
		
		if(curpage == 0){
	 		 curpage =1;
	 		}
		long count =0;
 		long pageNum =0;
 		List<Medicine> listbean = Medicine.find("medicineBoxId = ?", mbid).fetch(curpage,2);
 		if(listbean!=null){
 			count = Medicine.find("medicineBoxId = ?", mbid).fetch().size();
 			if(count%2!=0)
 	     		pageNum = count/2+1;
 	     	else
 	     		pageNum = count/2;
 		}
 		render("/MedicineMgm/medicineInfo.html",listbean,pageNum,curpage,mbid,mbname);
	}
	
	/*根据用户id或姓名查询药箱信息
	 * */
	public static void findMBbyUser2(String searchContent){
		long pageNum = 1;
		int curpage =1;
		List<userMBoxBean> listbean = null;
		List<User> list = User.find("realName=? or id = ?", searchContent,searchContent).fetch();
		if(list != null){
			listbean = userMBoxBean.builList(list);
		}
		render("/MedicineMgm/userMboxList.html",listbean,pageNum,curpage);
	}
	/*根据药箱id查询所有药物
	 * */
	public static void findMbyMB1(String medicineBoxId){
		/*List<MedicBToMedic> list = MedicBToMedic.findByMedicineBox(medicineBoxId);
		List<Medicine> medicines = new ArrayList<>();
		for(MedicBToMedic mm : list){
			Medicine medicine = Medicine.findById(mm.medicineId);
			medicines.add(medicine);
		}*/
		List<Medicine> medicines = Medicine.find("medicineBoxId = ?", medicineBoxId).fetch();
		wsOk(medicines);
	}
	
	/*添加药箱
	 * */
	public static void addMedicineBox(MedicineBox medicineBox,String userId){
		MedicineBox mBox = medicineBox.save();
		/*MedicineBox.createUtoMB(userId,mBox.id);*/
		findMBbyUser2(userId);
	}
	/*添加药物
	 * */
	public static void addMedicine(Medicine medicine,String medicineBoxId){
		Medicine m = medicine.save();
		/*Medicine.createMBtoM(medicineBoxId, m.id);*/
		findMbyMB1(medicineBoxId);
		
	}
	/*根据药箱Id查询药箱信息
	 * */
	public static void findMBbyid(String id){
		MedicineBox mBox = MedicineBox.findById(id);
		wsOk(mBox);
	}
	/*根据药物Id查询药物信息
	 * */
	public static void  findMbyId(String id){
		Medicine medicine = Medicine.findById(id);
		wsOk(medicine);
	}
	/*根据药箱id更新药箱信息
	 * */
	public static void updateMedicineBox(MedicineBox mBox,String userId){
		mBox.save();
		findMBbyUser2(userId);

	}
	/*根据药物Id查询药物信息
	 * */
	public static void updateMedicine(Medicine medicine,String medicineBoxId){
		medicine.save();
		findMbyMB1(medicineBoxId);
	}
	/*根据药箱Id删除药箱信息
	 * */
	public static void deleteMBbyId(String id,String userId){
		/*UserToMedicineBox.delete("medicineBoxId = ?", id);*/
		//循环删除药物，再删除药箱
		List<Medicine> medicines = Medicine.find("medicineBoxId = ?", id).fetch();
		for(Medicine m : medicines){
			m.delete();
		}
		MedicineBox.delete("id = ?", id);
		findMBbyUser2(userId);
		
	}
	/*根据药物Id删除药物信息
	 * */
	public static void deleteMedicine(String id,String medicineBoxId){
		/*MedicBToMedic.delete("medicineId = ?", id);*/
		Medicine.delete("id = ?", id);
		findMbyMB1(medicineBoxId);
	}
	
}
