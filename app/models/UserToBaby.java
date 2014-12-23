package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;

/**
 * 用户与孩子的关系类，一个用户可以有多个孩子，一个孩子只能属于一个用户，此类只存储用户和孩子的主键ID
 *
 * @author boxizen
 * @since 14/11/24
 */
@Entity
@Table(name = "user_baby")
public class UserToBaby extends BasicModel{
	@Column(name = "user_id")
    public String userId;//用户ID
	@Column(name = "baby_id")
    public String babyId;//孩子ID
	
	public UserToBaby(String userId,String babyId){
		this.userId=userId;
		this.babyId=babyId;
	}
	/*
	 * 功能：根据用户ID查找记录
	 * 参数：用户ID
	 * */
	public static List<UserToBaby> findByUser(String userId){
		return find("userId = ?", userId).fetch();
	}
	/*
	 * 功能：根据孩子ID查找记录
	 * 参数：孩子ID
	 * */
	public static List<UserToBaby> findByBaby(String babyId){
		return find("babyId= ?",babyId).fetch();
	}
}
