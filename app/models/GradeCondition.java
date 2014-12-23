package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.validation.Required;
import play.data.validation.Valid;

import com.sudocn.play.BasicModel;
/*
 * 成绩表单表，属性有Id、孩子Id、日期、年级、科目、成绩，其中ID由系统自动创建，孩子ID由对应关系类存储
 * */
@Entity
@Table(name = "gradecondition")
public class GradeCondition extends BasicModel {
   @Column(name = "date")
	public Date date;//日期
   @Column(name = "grade")
	public String grade;//年级
	@Required
	 @Column(name = "subject")
	public String subject;//科目
	@Required
	@Column(name = "mark")
	public String mark;//成绩
	@Column(name = "baby_Id")
	public String babyId;

	public GradeCondition(Baby baby,Date date,String grade,String subject,String mark,String babyId){
		
		this.date=date;
		this.grade=grade;
		this.subject=subject;
		this.mark=mark;
		this.babyId = babyId;
	}
	
	/*
	 * 功能：当新增一张成绩表时，建立成绩表和小孩的联系
	 * 参数：成绩表ID，小孩ID
	 * 
	 public static void createBtoG(String babyId,String gradeId){
		 new BabyToGrade(babyId, gradeId).save();
	 }*/
}
