package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.validation.Required;
import play.data.validation.Valid;

import com.sudocn.play.BasicModel;

/**
 * 成绩表单表，属性有Id、孩子Id、日期、年级、科目、成绩，其中ID由系统自动创建，孩子ID由对应关系类存储
 *
 * @author Yingpeng
 * @since 12/16/14
 */
@Entity
@Table(name = "gradeform")
public class GradeForm extends BasicModel {
   @Column(name = "date")
	public Date date;//日期
   
   /**
	 * 日期字符串
	 */
	@Required
	@Column(name = "dateStr")
	public String dateStr; 
	
   @Column(name = "grade")
	public String grade;//年级
   /**
    * 年级int形式
    */
   @Column(name = "gradeint")
   public int grade_int;
   
	@Required
	 @Column(name = "subject")
	public String subject;//科目
	@Required
	@Column(name = "mark")
	public double mark;//成绩
	@Column(name = "baby_Id")
	public String babyId;
	
	/**
	 * 次数：time
	 * 表示这门课的第几次考试
	 */
	public int time;

	public GradeForm(){}
	
	public GradeForm(Date date,String grade,int grade_int,String subject,double mark,int time,String babyId){
		
		this.date=date;
		this.grade=grade;
		this.grade_int = grade_int;
		this.subject=subject;
		this.mark=mark;
		this.time = time;
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
