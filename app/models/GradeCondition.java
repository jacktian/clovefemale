package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.sudocn.play.BasicModel;

import play.data.validation.Required;
import play.data.validation.Valid;
import play.db.jpa.Model;

@Entity
public class GradeCondition extends BasicModel {
	@Required
	@Valid
	@ManyToOne
	public Baby baby;
	public Date date;
	public String grade;
	@Required
	public String subject;
	@Required
	public String mark;

	public GradeCondition(Baby baby,Date date,String grade,String subject,String mark){
		this.baby=baby;
		this.date=date;
		this.grade=grade;
		this.subject=subject;
		this.mark=mark;
	}
}
