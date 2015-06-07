package models;

import javax.persistence.Column;
import javax.persistence.Entity;

import play.data.validation.Required;

import com.sudocn.play.BasicModel;

/**
 * 药品库
 * @author boxizen
 * @since 2015/06/08
 */

@Entity
public class DrugStore  extends BasicModel{

	/*
	 * 药品名 
	 */
	@Required
	@Column(name = "dname")
	public String dname;
	
	/*
	 * 规格
	 */
	@Required
	@Column(name = "specification")
	public String specification;
	
	/*
	 * 产地
	 */
	@Required
	@Column(name = "prodAddr")
	public String prodAddr;
	
	/*
	 * 生产厂家
	 */
	@Required
	@Column(name = "productor")
	public String productor;
	
	/*
	 * 条形码
	 */
	@Required
	@Column(name = "code")
	public String code;
	
	/*
	 * 价格
	 */
	@Required
	@Column(name = "price")
	public double price;
	
	/*
	 * 是否OTC
	 */
	@Required
	@Column(name = "isOtc")
	public boolean isOtcr;
	
	/*
	 * 是否基础药物
	 */
	@Required
	@Column(name = "isFound")
	public boolean isFound;
	
	/*
	 * 供应商
	 */
	@Required
	@Column(name = "supplier")
	public String supplier;
	
	/*
	 * 批准文号
	 */
	@Required
	@Column(name = "mark")
	public String mark;
}
