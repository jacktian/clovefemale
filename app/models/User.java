package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.data.validation.Email;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.db.jpa.Model;
@Entity
public class User extends Model{
	@Required
public String userName;
	@Required
public String passwd;
	@Required
public String realName;
public String sex;
public Date date;
public String introduction;
public String signName;
@Phone
public String phoneNum;
@Email
public String email;
public String role;
public Date registDate;
public String state;
public String userGrade;
@Required
public String IDcard;
public Boolean isAddV;
public String QQ;
public String weibo;
public String weixin;
public String womanStage;
@OneToMany(mappedBy="user",cascade=CascadeType.ALL)
@Valid
public List<Baby> babys;

public User(String userName,String passwd,String realName,String phoneNum,String email,String IDcard){
	this.userName=userName;
	this.passwd=passwd;
	this.realName=realName;
	this.phoneNum=phoneNum;
	this.email=email;
	this.IDcard=IDcard;
	this.babys=new ArrayList<Baby>();
}

public User addBaby(Date date,String sex,String name) {
    Baby baby=new Baby(this, date, sex, name).save();
    this.babys.add(baby);
    this.save();
    return this;
}
}
