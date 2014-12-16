package models;

import java.util.Date;

import javax.persistence.Entity;

import com.sudocn.play.BasicModel;

import play.data.validation.Required;

/**
 * 
 * @author caterZhong
 * @since 14/12/16
 * 孕重实体类
 */
@Entity
public class GestationalWeight extends BasicModel {
	@Required
	public Date wDate; //日期
	@Required
	public float wValue; //体重
	@Required
	public String userId;

	public GestationalWeight(String userId,Date wDate, float wValue){
		this.userId = userId;
		this.wDate = wDate;
		this.wValue = wValue;
	}
	
}
