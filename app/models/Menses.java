package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.data.validation.Required;

import com.sudocn.play.BasicModel;

/**
 * 月经实体类
 *
 * @author caterZhong
 * @since 14/12/16
 * 
 */
@Entity
public class Menses extends BasicModel{
	
	/**
	 * 日期
	 */
	@Required
	@Column(name = "m_date")
	public Date mDate;
	
	/**
	 * 日期字符串
	 */
	@Required
	@Column(name = "dateStr")
	public String dateStr;
	
	/**
	 * 颜色（暗红、鲜红、浅红）
	 */
	@Required
	@Column(name = "m_color")
	public String mColor; 
	
	/**
	 * 经量（小、中、大）
	 */
	@Required
	@Column(name = "m_measure")
	public String mMeasure; 
	
	/**
	 * 有无色块(有、无)
	 */
	@Required
	@Column(name = "m_piece")
	public String mPiece;
	
	/**
	 * 是否痛经
	 */
	@Required
	@Column(name = "is_mcramp")
	public String isMcramp;
	
	/**
	 * 稠度（稠、稀）
	 */
	@Required
	@Column(name = "vicidity")
	public String vicidity;
	
	/**
	 * 用户Id
	 */
	@Required
	@Column(name = "user_id")
	public String userId;
	
	/**
	 * 阶段
	 */
	@Required
	@Column(name = "time")
	public String time;
	
	public Menses(){}
	public Menses(String userId, Date mDate,String mColor, String mMeasure, String mPiece, String isMcramp,String vicidity){
		this.userId = userId;
		this.mDate = mDate;
		this.mColor = mColor;
		this.mPiece = mPiece;
		this.isMcramp = isMcramp;
		this.vicidity = vicidity;
	}
}
