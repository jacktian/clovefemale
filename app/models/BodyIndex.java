package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.sudocn.play.BasicModel;

import play.data.validation.Required;
import play.data.validation.Valid;
import play.db.jpa.Model;

@Entity
public class BodyIndex extends BasicModel {
	@Required
	@Valid
	@ManyToOne
	public Baby baby;
	public Date  date;
	@Required
	public Double height;
	@Required
	public Double weight;

	public BodyIndex(Baby baby,Date date,Double height,Double weight){
		this.baby=baby;
		this.date=date;
		this.height=height;
		this.weight=weight;
	}
}
