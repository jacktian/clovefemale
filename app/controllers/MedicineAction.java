package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.MedicBToMedic;
import models.Medicine;
import models.MedicineBox;
import models.Note;
import models.NoteBook;
import models.NoteBookToNote;
import models.UserToMedicineBox;
import models.UserToNoteBook;

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
	public static void findMBbyUser(String userId){
		List<UserToMedicineBox> list =UserToMedicineBox.findByUser(userId);
		List<MedicineBox> medicineBoxs = new ArrayList();
		for(UserToMedicineBox um : list){
			MedicineBox mb= MedicineBox.findById(um.medicineBoxId);
			medicineBoxs.add(mb);
		}
		wsOk(medicineBoxs);
	}
	/*根据用户id查询所有药箱
	 * */
	public static List<MedicineBox> findMBbyUser2(String userId){
		List<UserToMedicineBox> list =UserToMedicineBox.findByUser(userId);
		List<MedicineBox> medicineBoxs = new ArrayList();
		for(UserToMedicineBox um : list){
			MedicineBox mb= MedicineBox.findById(um.medicineBoxId);
			medicineBoxs.add(mb);
		}
		return medicineBoxs;
	}
	/*根据药箱id查询所有药物
	 * */
	public static void findMbyMB(String medicineBoxId){
		List<MedicBToMedic> list = MedicBToMedic.findByMedicineBox(medicineBoxId);
		List<Medicine> medicines = new ArrayList<>();
		for(MedicBToMedic mm : list){
			Medicine medicine = Medicine.findById(mm.medicineId);
			medicines.add(medicine);
		}
		wsOk(medicines);
	}
	
	/*添加药箱
	 * */
	public static void addMedicineBox(MedicineBox medicineBox,String userId){
		MedicineBox mBox = medicineBox.save();
		MedicineBox.createUtoMB(userId,mBox.id);
		findMBbyUser(userId);
	}
	/*添加药物
	 * */
	public static void addMedicine(Medicine medicine,String medicineBoxId){
		Medicine m = medicine.save();
		Medicine.createMBtoM(medicineBoxId, m.id);
		findMbyMB(medicineBoxId);
		
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
		findMBbyUser(userId);

	}
	/*根据药物Id查询药物信息
	 * */
	public static void updateMedicine(Medicine medicine,String medicineBoxId){
		medicine.save();
		findMbyMB(medicineBoxId);
	}
	/*根据药箱Id删除药箱信息
	 * */
	public static void deleteMBbyId(String id,String userId){
		UserToMedicineBox.delete("medicineBoxId = ?", id);
		MedicineBox.delete("id = ?", id);
		findMBbyUser(userId);
		
	}
	/*根据药物Id查询药物信息
	 * */
	public static void deleteMedicine(String id,String medicineBoxId){
		MedicBToMedic.delete("medicineId = ?", id);
		Medicine.delete("id = ?", id);
		findMbyMB(medicineBoxId);
	}
	
}
