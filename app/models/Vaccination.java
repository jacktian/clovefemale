package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.data.validation.Valid;
import play.db.jpa.Model;

@Entity
public class Vaccination extends Model{
	@Required
	@Valid
	@ManyToOne
public Baby baby;
public Date date;
public String content;
public Vaccination(Baby baby,Date date,String content){
	this.baby=baby;
	this.date=date;
	this.content=content;
}
}
