package models;

import java.util.Date;

import javax.persistence.Entity;

import com.sudocn.play.BasicModel;

import play.data.validation.Required;

/**
 * 
 * @author caterZhong
 * @since 14/12/16
 * 胎动实体类
 */
@Entity
public class FetalMovement extends BasicModel {
	@Required
	public Date fDate; //日期
	@Required
	public int num; //次数
	@Required
	public String userId;
	
	public FetalMovement(String userId,Date fDate, int num){
		this.userId = userId;
		this.fDate = fDate;
		this.num = num;
	}
}
