package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;
@Entity
@Table(name = "medicinebox_medicine")
public class MedicBToMedic extends BasicModel{

	@Column(name = "medicineBox_id")
    public String medicineBoxId;//用户ID
	@Column(name = "medicine_id")
    public String medicineId;//孩子ID
	
	public MedicBToMedic(String medicineBoxId,String medicineId){
		this.medicineBoxId=medicineBoxId;
		this.medicineId=medicineId;
	}
	/*
	 * 功能：根据药箱ID查找记录
	 * 参数：药箱ID
	 * */
	public static List<MedicBToMedic> findByMedicineBox(String medicineBoxId){
		return find("medicineBoxId = ?", medicineBoxId).fetch();
	}
	/*
	 * 功能：根据药ID查找记录
	 * 参数：药ID
	 * */
	public static List<MedicBToMedic> findByMedicine(String medicineId){
		return find("medicineId= ?",medicineId).fetch();
	}
}
