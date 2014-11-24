package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;

/**
 * 用户的孩子
 *
 * @author boxizen
 * @since 14/11/24
 */
@Entity
@Table(name = "user_baby")
public class UserToBaby extends BasicModel{
	@Column(name = "user_id")
    public String userId;
	@Column(name = "baby_id")
    public String babyId;
	
	public UserToBaby(String userId,String babyId){
		this.userId=userId;
		this.babyId=babyId;
	}
	
	public static List<UserToBaby> findByUser(String userId){
		return find("userId = ?", userId).fetch();
	}
}
