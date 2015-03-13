package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.sudocn.play.BasicModel;

import play.data.validation.Required;

/**
 * 孕重实体类
 * 
 * @author caterZhong
 * @since 14/12/16
 * 
 */
@Entity
public class GestationalWeight extends BasicModel {
	/**
	 * 日期
	 */
	@Required
	@Column(name = "w_date")
	public Date wDate; //日期
	
	/**
	 * 日期字符串
	 */
	@Required
	@Column(name = "dateStr")
	public String dateStr; 
	
	/**
	 * 体重
	 */
	@Required
	@Column(name = "w_value")
	public float wValue; 
	
	@Required
	@Column(name = "user_id")
	public String userId;

	public GestationalWeight(){}
	public GestationalWeight(String userId,Date wDate, float wValue){
		this.userId = userId;
		this.wDate = wDate;
		this.wValue = wValue;
	}
	
}
