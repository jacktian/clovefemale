package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;
@Entity
@Table(name = "medicinebox")
public class MedicineBox extends BasicModel{

	@Column(name = "name")
	public String name;//名称
	@Column(name = "createDate")
	public Date createDate;//创建时间
	@Column(name = "mark")
	public String mark;//备注
	@Column(name = "user_Id")
	public String userId;//用户id
	
	public MedicineBox(String name,Date cDate,String mark,String userId){
		this.name=name;
		this.createDate=cDate;
		this.mark=mark;
		this.userId=userId;
		
	}
	
	/*
	 * 功能：当新增一个药箱时，建立药箱和用户的联系
	 * 参数：用户ID，药箱ID
	 * 
	 public static void createUtoMB(String userId,String medicineBoxId){
		 new UserToMedicineBox(userId, medicineBoxId).save();
	 }*/
}
