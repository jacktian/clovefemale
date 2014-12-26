package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;
@Entity
@Table(name = "medicine")
public class Medicine extends BasicModel{

	@Column(name = "name")
	public String name;//药物名称
	@Column(name = "type")
	public String type;//药物类型
	@Column(name = "deadline")
	public String deadline;//有效期
	@Column(name = "code")
	public String code;//药物条码
	@Column(name = "photoAddr")
	public String photoAddr;//相片地址
	@Column(name = "function")
	public String function;//药物功能
	@Column(name = "applicabbility")
	public String applicability ;//适用性
	@Column(name = "medicineBox_Id")
	public String medicineBoxId;//药箱编号
	
	public Medicine(){}
	public Medicine(String name,String type,String deadline,String code,String photoAddr,String function,String aString,String medicineBoxId){
		this.name=name;
		this.applicability=aString;
		this.type=type;
		this.deadline=deadline;
		this.code=code;
		this.photoAddr=photoAddr;
		this.function=function;
		this.medicineBoxId=medicineBoxId;
	}
	
	/*
	 * 功能：当新增一种药时，建立药和药箱的联系
	 * 参数：用户ID，药箱ID
	 * 
	 public static void createMBtoM(String MedicineBoxId,String medicineId){
		 new MedicBToMedic(MedicineBoxId, medicineId).save();
	 }*/
}
