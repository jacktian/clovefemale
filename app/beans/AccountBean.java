package beans;

import javax.persistence.Column;

import play.data.validation.Email;
import play.data.validation.Phone;

public class AccountBean {
	/**
     * 丁香号
     */
	public String cloveId;
	/**
     * QQ号码
     */
	public String QQ;
	/**
     * 微博号
     */
	public String weibo;

	/**
     * 微信号
     */
	public String weixin;
	/**
     * 是否加V
     */
	public Boolean isAddV;
	/**
     * 手机信息
     */
	public String phoneNum;
	
	/**
     * 邮箱信息
     */
	public String email;
	
	public AccountBean(){}
	public AccountBean(String cloveId,String QQ,String weibo,String weixin,Boolean isAddV,String phoneNum,String email){
		this.cloveId=cloveId;
		this.QQ=QQ;
		this.weibo=weibo;
		this.weixin=weixin;
		this.isAddV=isAddV;
		this.phoneNum=phoneNum;
		this.email=email;
	}
}
