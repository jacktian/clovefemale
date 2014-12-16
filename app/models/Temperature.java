package models;

import java.util.Date;

import javax.persistence.Entity;

import com.sudocn.play.BasicModel;

import play.data.validation.Required;

/**
 * 
 *
 * @author caterZhong
 * @since 14/12/16
 * 基础体温实体类
 */
@Entity
public class Temperature extends BasicModel {
	@Required
	public Date tDate; //日期
	@Required
	public float tValue; //温度
	@Required
	public String userId;
	
	public Temperature(String userId,Date tDate, float tValue){
		this.userId = userId;
		this.tDate = tDate;
		this.tValue = tValue;
	}
}
