package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.sudocn.play.BasicModel;

import play.data.validation.Required;

/**
 * 胎动实体类
 *
 * @author caterZhong
 * @since 14/12/16
 *
 */
@Entity
public class FetalMovement extends BasicModel {

	/**
	 * 日期
	 */
	@Required
	@Column(name = "f_date")
	public Date fDate;

	/**
	 * 日期字符串
	 */
	@Required
	@Column(name = "dateStr")
	public String dateStr;

	/**
	 * 次数
	 */
	@Required
	@Column(name = "num")
	public int num;

	/**
	 * 时间
	 */
	 @Required
	 @Column(name="time")
	 public int time;

	@Required
	@Column(name = "user_id")
	public String userId;

	public FetalMovement(){}
	public FetalMovement(String userId,Date fDate, int num){
		this.userId = userId;
		this.fDate = fDate;
		this.num = num;
	}
}
