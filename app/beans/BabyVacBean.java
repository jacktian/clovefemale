package beans;

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
public class BabyVacBean{
	/**
	 * 疫苗Id
	 */
	public String id;
	
	/**
	 * 预计接种日期
	 */
	public Date etmDate;
	
	/**
	 * 实际接种日期
	 */
	public Date date;
   
   /**
    * 提醒时间
    */
    public Date remindTime;
   
	/**
	 * 疫苗名称
	 */
	public String name;
	
	/**
	 * 次数，表示同一疫苗的第几针
	 */
	public String time;
	
	/**
	 * 出生多少月后接种
	 */
	public int monthAfter;
	
	/**
	 * 年龄描述
	 */
	public String ageDcb;
	
	/*
	 * 宝宝Id
	 */
   public String babyId;
   
   /**
    * 月份间隔（与前一针）
    */
   public String monthInteral;
   
   /**
    * 天数间隔（与前一针）
    */
   public String dayInteral;
   
   /**
    * 可预防疾病
    */
   public String pvDisease;
   
   /**
    * 是否已经接种
    * 0表示未接种
    * 1表示已接种
    */
   public String isDone;
   
   
   public BabyVacBean(){}
   
	public BabyVacBean(Date etmDate,Date date,Date remindTime,String name,String time,int monthAfter,String ageDcb, String babyId,String pvDisease,String isDone){
		
		this.etmDate=etmDate;
		this.date = date;
		this.remindTime = remindTime;
		this.name = name;
		this.time = time;
		this.monthAfter = monthAfter;
		this.ageDcb = ageDcb;
		this.babyId=babyId;
		this.pvDisease = pvDisease;
		this.isDone  = isDone;
	}
	
	public BabyVacBean(String id,Date etmDate,Date date,Date remindTime,String name,String time,int monthAfter,String ageDcb, String babyId,String pvDisease,String isDone){
		this.id = id;
		this.etmDate=etmDate;
		this.date = date;
		this.remindTime = remindTime;
		this.name = name;
		this.time = time;
		this.monthAfter = monthAfter;
		this.ageDcb = ageDcb;
		this.babyId=babyId;
		this.pvDisease = pvDisease;
		this.isDone  = isDone;
	}

}

