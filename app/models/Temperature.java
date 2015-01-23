package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.sudocn.play.BasicModel;

import play.data.validation.Required;

/**
 * 基础体温实体类
 *
 * @author caterZhong
 * @since 14/12/16
 * 
 */
@Entity
public class Temperature extends BasicModel {
	
	/**
	 * 日期
	 */
	@Required
	@Column(name = "t_date")
	public Date tDate; 
	
	/**
	 * 温度
	 */
	@Required
	@Column(name = "t_value")
	public float tValue; 
	
	@Required
	@Column(name = "user_id")
	public String userId;
	
	public Temperature(){}
	public Temperature(String userId,Date tDate, float tValue){
		this.userId = userId;
		this.tDate = tDate;
		this.tValue = tValue;
	}
	@Override
	public String toString() {
		return "Temperature [tDate=" + tDate + ", tValue=" + tValue
				+ ", userId=" + userId + "]";
	}
}
