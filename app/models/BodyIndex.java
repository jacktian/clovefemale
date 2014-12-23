package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;

import play.data.validation.Required;
import play.data.validation.Valid;
import play.db.jpa.Model;
/*
 * 身体指标类，主要属性包括Id、孩子Id、日期、身高和体重，其中id由系统自动创建，孩子id由关系类BabyToBody存储
 * */
@Entity
@Table(name = "bodyindex")
public class BodyIndex extends BasicModel {
	
	public Date  date;//出生日期
	@Required
	public Double height;//身高
	@Required
	public Double weight;//体重

	public BodyIndex(Baby baby,Date date,Double height,Double weight){
		
		this.date=date;
		this.height=height;
		this.weight=weight;
	}
	
	/*
	 * 功能：当新增一张身体指标记录时，建立身体指标和小孩的联系
	 * 参数：身体指标ID，小孩ID
	 * */
	 public static void createBtoB(String babyId,String bodyId){
		 new BabyToBody(babyId, bodyId).save();
	 }
}
