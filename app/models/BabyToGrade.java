package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;
/*
 *孩子与成绩表单的关系类，一个孩子由多个成绩表记录，一个成绩表记录对应一个孩子，存储孩子和成绩表的主键ID
 * */
@Entity
@Table(name = "baby_grade")
public class BabyToGrade extends BasicModel{
   @Column(name = "baby_id")
	public String babyId;//孩子ID
   @Column(name = "grade_id")
	public String gradeId;//成绩表ID
	
	public BabyToGrade(String babyId,String gradeId){
		this.babyId=babyId;
		this.gradeId=gradeId;
	}
	
	/*
	 * 功能：根据孩子ID查找记录
	 * 参数：孩子ID
	 * */
	public static List<BabyToGrade> findByBaby(String babyId){
		return find("babyId = ?", babyId).fetch();
	}
	/*
	 * 功能：根据成绩表ID查找记录
	 * 参数：成绩表ID
	 * */
	public static List<BabyToGrade> findByGrade(String gradeId){
		return find("gradeId = ?", gradeId).fetch();
	}
}
