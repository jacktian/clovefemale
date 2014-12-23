package models;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;
@Entity
@Table(name = "medicine")
public class Medicine extends BasicModel{

	public String name;
	public String type;
	public String deadline;
	public String code;
	public String photoAddr;
	public String function;
	public String applicability ;//适用性
	
	public Medicine(String name,String type,String deadline,String code,String photoAddr,String function,String aString){
		this.name=name;
		this.applicability=aString;
		this.type=type;
		this.deadline=deadline;
		this.code=code;
		this.photoAddr=photoAddr;
		this.function=function;
	}
	
	/*
	 * 功能：当新增一种药时，建立药和药箱的联系
	 * 参数：用户ID，药箱ID
	 * */
	 public static void createMBtoM(String MedicineBoxId,String medicineId){
		 new MedicBToMedic(MedicineBoxId, medicineId).save();
	 }
}
