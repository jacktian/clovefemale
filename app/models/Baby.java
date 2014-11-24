package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.sudocn.play.BasicModel;

import play.data.validation.Required;
import play.data.validation.Valid;
import play.db.jpa.Model;


@Entity
public class Baby extends BasicModel{
	
	@Column(name = "date")
	public Date date;
	@Column(name = "sex")
	public String sex;
	@Column(name = "name")
	public String name;

	public Baby(Date date,String sex,String name){
		this.date=date;
		this.sex=sex;
		this.name=name;
	}
	
}
