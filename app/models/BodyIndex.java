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
 * 身体指标表，主要属性包括Id、孩子Id、日期、身高和体重，其中id由系统自动创建，孩子id由关系类BabyToBody存储
 *
 * @author Yingpeng
 * @since 12/16/14
 */
@Entity
@Table(name = "bodyindex")
public class BodyIndex extends BasicModel {
	@Column(name = "date")
	public Date  date;//日期
	/**
	 * 日期字符串
	 */
	@Required
	@Column(name = "dateStr")
	public String dateStr;
	@Column(name = "height")
	@Required
	public double height;//身高
	@Column(name = "weight")
	@Required
	public double weight;//体重
	@Column(name = "baby_Id")
	public String babyId;
	
	/**
	 * 年龄
	 */
	@Column(name="age")
	public double age;
	
	/**
	 * 年龄说明
	 */
	@Column(name="ageDcb")
	public String ageDcb;

	public BodyIndex(){}
	public BodyIndex(Date date,Double height,Double weight,String babyId,double age,String ageDcb){
		
		this.date=date;
		this.height=height;
		this.weight=weight;
		this.babyId = babyId;
		this.age = age;
		this.ageDcb = ageDcb;
	}
	
	/*
	 * 功能：当新增一张身体指标记录时，建立身体指标和小孩的联系
	 * 参数：身体指标ID，小孩ID
	 * 
	 public static void createBtoB(String babyId,String bodyId){
		 new BabyToBody(babyId, bodyId).save();
	 }*/
}
