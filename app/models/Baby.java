package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.data.validation.Valid;
import play.db.jpa.Model;
@Entity
public class Baby extends Model{
@ManyToOne
@Valid
public User user;
public Date date;
@Required
public String sex;
@Required
public String name;

public Baby(User user,Date date,String sex,String name){
	this.user=user;
	this.date=date;
	this.sex=sex;
	this.name=name;
}
}
