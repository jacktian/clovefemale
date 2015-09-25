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
    * 提醒时间
    */
   @Column(name = "remindtime")
    public Date remindTime;
   
   /**
    * 疫苗Id
    */
   @Column(name = "vacid")
    public String vacId;
  
	/*
	 * 宝宝Id
	 */
   @Column(name = "baby_Id")
   public String babyId;
   
   
   /**
    * 是否已经接种
    * 0表示未接种
    * 1表示已接种
    */
   @Column(name = "isdone")
   public String isDone;
   
   
   public BabyVac(){}
   
	public BabyVac(Date etmDate,Date date,Date remindTime,String vacId,String babyId,String isDone){
		
		this.etmDate=etmDate;
		this.date = date;
		this.remindTime = remindTime;
		this.vacId = vacId;
		this.babyId=babyId;
		this.isDone  = isDone;
	}
	
	public BabyVac(String id,Date etmDate,Date date,Date remindTime,String vacId,String babyId,String isDone){
		this.id = id;
		this.etmDate=etmDate;
		this.date = date;
		this.remindTime = remindTime;
		this.vacId = vacId;
		this.babyId=babyId;
		this.isDone  = isDone;
	}

}

