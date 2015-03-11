package models;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.sudocn.play.BasicModel;

import play.data.validation.Email;
import play.data.validation.Phone;
import play.data.validation.Valid;
import play.db.jpa.Model;

/**
 * 用户表
 *
 * @author boxizen
 * @since 12/16/14
 */

@Entity
@Table(name = "user")
public class User extends BasicModel{

	/**
     * 用户名
     */
	@Column(name = "username")
	public String userName;
	
	/**
     * 丁香号
     */
	@Column(name = "cloveId")
	public String cloveId;

	/**
     * 密码
     */
	@Column(name = "passwd")
	public String passwd;
	
	/**
     * 真实姓名
     */
	@Column(name = "realname")
	public String realName;
	
	/**
     * 手机信息
     */
	@Phone
	@Column(name = "phonenum")
	public String phoneNum;
	
	/**
     * 邮箱信息
     */
	@Email
	@Column(name = "email")
	public String email;

	/**
     * 身份证号
     */
	@Column(name = "idcard")
	public String IDcard;

	/**
     * QQ号码
     */
	@Column(name = "qq")
	public String QQ;

	/**
     * 微博号
     */
	@Column(name = "weibo")
	public String weibo;

	/**
     * 微信号
     */
	@Column(name = "weixin")
	public String weixin;


	/**
     * 身份
     */
	@Column(name = "role")
	public String role;

	/**
     * 注册时间
     */
	@Column(name = "registdate")
	public Date registDate;

	/**
     * 用户等级
     */
	@Column(name = "usergrade")
	public String userGrade;

	/**
     * 性别
     */
	@Column(name = "sex")
	public String sex;

	/**
     * 自我介绍
     */
	@Column(name = "introduction")
	public String introduction;

	/**
     * 个性签名
     */
	@Column(name = "signname")
	public String signName;

	/**
     * 出生日期
     */
	@Column(name = "date")
	public Date date;

	/**
     * 用户状态
     */
	@Column(name = "state")
	public String state;

	/**
     * 是否加V
     */
	@Column(name = "isaddv")
	public Boolean isAddV;
	
	/**
     * ???
     */
	@Column(name = "womanstage")
	public String womanStage;
	
}