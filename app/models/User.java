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


	/**
     * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
     */
	@Column(name = "subscribe")
	public String subscribe;
	
	/**
     * 用户的标识，对当前公众号唯一
     */
	@Column(name = "openid")
	public String openid;
	
	/**
     * 用户的昵称
     */
	@Column(name = "nickname")
	public String nickname;
	
	/**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
	@Column(name = "sex")
	public String sex;
	
	/**
     * 用户语言
     */
	@Column(name = "language")
	public String language;
	
	/**
     * 用户所在城市
     */
	@Column(name = "city")
	public String city;
	
	/**
     * 用户所在省份
     */
	@Column(name = "province")
	public String province;
	
	/**
     * 用户所在城市
     */
	@Column(name = "country")
	public String country;
	
	/**
     * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
     */
	@Column(name = "headimgurl")
	public String headimgurl;
	
	/**
     * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     */
	@Column(name = "subscribe_time")
	public String subscribe_time;
	
	/**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     */
	@Column(name = "unionid")
	public String unionid;
	
}