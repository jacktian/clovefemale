package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import play.data.validation.Required;

import com.sudocn.play.BasicModel;

/**
 * 月经
 *
 * @author caterZhong
 * @since 14/12/16
 * 月经实体类
 */
@Entity
public class Menses extends BasicModel{
	@Required
	public Date mDate; //日期
	@Required
	public String mColor; //颜色（暗红、鲜红、浅红）
	@Required
	public String mMeasure; //经量（小、中、大）
	@Required
	public boolean mPiece; //有无色块(有、无)
	@Required
	public boolean isMcramp; //是否痛经
	@Required
	public String vicidity; //稠度（稠、稀）
	@Required
	public String userId; //用户Id
	
	public Menses(String userId, Date mDate,String mColor, String mMeasure, boolean mPiece, boolean isMcramp,String vicidity){
		this.userId = userId;
		this.mDate = mDate;
		this.mColor = mColor;
		this.mPiece = mPiece;
		this.isMcramp = isMcramp;
		this.vicidity = vicidity;
	}
}
