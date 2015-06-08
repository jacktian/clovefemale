package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;

import play.data.validation.Required;
import play.data.validation.Valid;
import play.db.jpa.Model;

/**
 *
 * @author cater Zhong
 * @since 6/6/15
 */
@Entity
@Table(name = "babyvac")
public class BabyVac extends BasicModel{
	/**
	 * 预计接种日期
	 */
	@Column(name="etmdate")
	public Date etmDate;
	
	/**
	 * 实际接种日期
	 */
   @Column(name = "date")
	public Date date;
   
   
	/**
	 * 疫苗名称
	 */
	@Column(name = "name")
	public String name;
	
	/**
	 * 出生多少月后接种
	 */
	@Column(name = "monthafter")
	public int monthAfter;
	
	/**
	 * 年龄描述
	 */
	@Column(name = "agedcb")
	public String ageDcb;
	
	/*
	 * 宝宝Id
	 */
   @Column(name = "baby_Id")
   public String babyId;
   
   /**
    * 可预防疾病
    */
   @Column(name = "pvdisease")
   public String pvDisease;
   
   /**
    * 是否已经接种
    * 0表示未接种
    * 1表示已接种
    */
   @Column(name = "isdone")
   public String isDone;
   
   
   public BabyVac(){}
   
	public BabyVac(Date etmDate,Date date,String name,int monthAfter,String ageDcb, String babyId,String pvDisease,String isDone){
		
		this.etmDate=etmDate;
		this.date = date;
		this.name = name;
		this.monthAfter = monthAfter;
		this.ageDcb = ageDcb;
		this.babyId=babyId;
		this.pvDisease = pvDisease;
		this.isDone  = isDone;
	}
	
	public BabyVac(String id,Date etmDate,Date date,String name,int monthAfter,String ageDcb, String babyId,String pvDisease,String isDone){
		this.id = id;
		this.etmDate=etmDate;
		this.date = date;
		this.name = name;
		this.monthAfter = monthAfter;
		this.ageDcb = ageDcb;
		this.babyId=babyId;
		this.pvDisease = pvDisease;
		this.isDone  = isDone;
	}

}

