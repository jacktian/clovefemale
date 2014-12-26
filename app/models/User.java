package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;

import play.data.validation.Email;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.db.jpa.Model;
/*
 * 用户类，主要的属性有Id、用户名、密码、真实姓名、性别、出生年月、简介、签名、手机号码、邮箱、角色身份、注册时间、用户状态、账户等级、身份证号码、是否加V认证、QQ号、微博号
 * 微信号、服务等级、女性阶段（未婚女性，怀孕女性，孩子儿童，孩子少年），用户ID由系统自动创建,其中用户与孩子的关系由UserToBaby存储
 * */
@Entity
@Table(name = "user")
public class User extends BasicModel{
	@Required
	@Column(name = "userName")
	public String userName;//用户名
	@Required
	@Column(name = "passwd")
	public String passwd;//密码
	@Required
	@Column(name = "realName")
	public String realName;//真实姓名
	@Column(name = "sex")
	public String sex;//性别
	@Column(name = "date")
	public Date date;//出生日期
	@Column(name = "introduction")
	public String introduction;//简介
	@Column(name = "signName")
	public String signName;//签名
	@Phone
	@Column(name = "phoneNum")
	public String phoneNum;//手机号码
	@Email
	@Column(name = "email")
	public String email;//邮箱
	@Column(name = "role")
	public String role;//角色
	@Column(name = "registDate")
	public Date registDate;//注册时间
	@Column(name = "state")
	public String state;//用户状态
	@Column(name = "userGrade")
	public String userGrade;//用户等级
	@Required
	@Column(name = "IDcard")
	public String IDcard;//身份证号码
	@Column(name = "isAddV")
	public Boolean isAddV;//是否加V
	@Column(name = "QQ")
	public String QQ;//QQ号码
	@Column(name = "weibo")
	public String weibo;//微博
	@Column(name = "weixin")
	public String weixin;//微信
	@Column(name = "womanStage")
	public String womanStage;//女性阶段

	public User(){}
	public User(String userName,String passwd,String realName,String phoneNum,String email,String IDcard){
		this.userName=userName;
		this.passwd=passwd;
		this.realName=realName;
		this.phoneNum=phoneNum;
		this.email=email;
		this.IDcard=IDcard;
	}
}
