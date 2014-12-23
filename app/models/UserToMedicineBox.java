package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;
@Entity
@Table(name = "user_medicinebox")
public class UserToMedicineBox extends BasicModel{
	@Column(name = "user_id")
    public String userId;//用户ID
	@Column(name = "medicinebox_id")
    public String medicineBoxId;//药箱ID 
	
	public UserToMedicineBox(String userId,String medicineBoxId){
		this.userId=userId;
		this.medicineBoxId=medicineBoxId;
	}
	
	/*
	 * 功能：根据用户ID查找药箱记录
	 * 参数：用户ID
	 * */
	public static List<UserToMedicineBox> findByUser(String userId){
		return find("userId = ?", userId).fetch();
	}
	/*
	 * 功能：根据药箱ID查找记录
	 * 参数：药箱ID
	 * */
	public static List<UserToMedicineBox> findByMedicineBox(String medicineBoxId){
		return find("medicineBoxId= ?",medicineBoxId).fetch();
	}
}
