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
/*
 * 疫苗接种实体类，主要属性有Id、孩子Id、、日期、备注，其中ID由系统自动创建，孩子ID由对应的关系类存储
 * */
@Entity
@Table(name = "vaccination")
public class Vaccination extends BasicModel{
   @Column(name = "date")
	public Date date;//日期
   @Column(name = "content")
	public String content;//备注
   @Column(name = "baby_Id")
   public String babyId;
   
	public Vaccination(Baby baby,Date date,String content,String babyId){
		
		this.date=date;
		this.content=content;
		this.babyId=babyId;
	}
	
	/*
	 * 功能：当新增一张疫苗接种记录时，建立小孩和疫苗接种记录的联系
	 * 参数：小孩ID，疫苗接种记录ID
	 * 
	 public static void createBtoV(String babyId,String vacciId){
		 new BabyToVacci(babyId, vacciId).save();
	 }*/
}
