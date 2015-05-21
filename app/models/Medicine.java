package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;

/**
 * 药物表
 *
 * @author Yingpeng
 * @since 12/16/14
 */
@Entity
@Table(name = "medicine")
public class Medicine extends BasicModel{

	@Column(name = "name")
	public String name;//药物名称
	@Column(name = "type")
	public String type;//药物类型
	@Column(name = "deadline")
	public Date deadline;//有效期
	@Column(name = "produce")
	public Date produce;//生产期
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
	
	/**
	 * 	规格
	 */
	@Column(name = "specification")
	public String specification;
	
	/**
	 * 	产地
	 */
	@Column(name = "prodAddr")
	public String prodAddr;
	
	/**
	 * 	厂家
	 */
	@Column(name = "productor")
	public String productor;
	
	/**
	 * 	价格
	 */
	@Column(name = "price")
	public String price;
	
	/**
	 * 	是否OTC
	 */
	@Column(name = "isOtc")
	public Boolean isOtc;
	
	/**
	 * 	是否基础药物
	 */
	@Column(name = "isFound")
	public Boolean isFound;

	/**
	 * 	供应商
	 */
	@Column(name = "supplier")
	public String supplier;
	
	/**
	 * 	批准文号
	 */
	@Column(name = "license")
	public String license;
	
	public Medicine(){}
	
	/*
	 * 功能：当新增一种药时，建立药和药箱的联系
	 * 参数：用户ID，药箱ID
	 * 
	 public static void createMBtoM(String MedicineBoxId,String medicineId){
		 new MedicBToMedic(MedicineBoxId, medicineId).save();
	 }*/
}

