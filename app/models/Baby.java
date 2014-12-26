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
 * 宝宝类，主要属性有id、用户id、孩子出生年月 、孩子性别、孩子昵称，其中id由系统自动创建
 * 用户id有对应关系类存储
 * */
@Entity
@Table(name = "baby")
public class Baby extends BasicModel{
	
	@Column(name = "date")
	public Date date;//出生日期
	@Column(name = "sex")
	public String sex;//性别
	@Column(name = "name")
	public String name;//孩子昵称
	@Column(name = "user_Id")
	public String userId;

	public Baby(){}
	public Baby(Date date,String sex,String name,String userId){
		this.date=date;
		this.sex=sex;
		this.name=name;
		this.userId=userId;
	}
	
	/*
	 * 功能：当新增一个小孩时，建立小孩和用户的联系
	 * 参数：用户ID，小孩ID
	 * 
	 public static void createUtoB(String userId,String babyId){
		 new UserToBaby(userId, babyId).save();
	 }*/
	
}
