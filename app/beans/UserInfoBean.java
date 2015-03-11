package beans;

import javax.persistence.Column;

public class UserInfoBean {
	/**
     * 丁香号
     */
	public String cloveId;
	/**
     * 用户名(昵称)
     */
	public String userName;
	/**
     * 性别
     */
	public String sex;
	/**
     * 个性签名
     */
	public String signName;
	
	public UserInfoBean(){}
	public UserInfoBean(String cloveId, String userName,String sex,String signName){
		this.cloveId=cloveId;
		this.userName=userName;
		this.sex=sex;
		this.signName=signName;
	}
}
