package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.data.validation.Required;

import com.sudocn.play.BasicModel;

/**
 * 用户设置表
 *
 * @author Yan Yingpeng
 * @since 2015/3/8
 */
@Entity
@Table(name = "settings")
public class Settings extends BasicModel{
	@Column(name = "userId")
	@Required
	public String  userId;//用户ID
	
	@Column(name = "physiology")
	@Required
	public boolean  physiology;//是否开启生理记录提醒
	
	@Column(name = "medicine")
	@Required
	public boolean  medicine;//是否开启药品到期提醒
	
	@Column(name = "vaccine")
	@Required
	public boolean  vaccine;//是否开启疫苗到期提醒
	
	@Column(name = "voice")
	@Required
	public boolean  voice;//是否开启提醒声音
	
	@Column(name = "remindStart")
	public Date  remindStart;//提醒开始时间
	
	@Column(name = "remindEnd")
	public Date  remindEnd;//提醒结束时间
	
	@Column(name = "openSoftewarePwd")
	@Required
	public boolean  openSoftewarePwd;//是否打开软件密码保护
	
	@Column(name = "openNotePwd")
	@Required
	public boolean  openNotePwd;//是否打开笔记密码保护
	
	@Column(name = "gesturePwd")
	public String  gesturePwd;//手势密码
	
	public Settings(){}
	public Settings(String  userId,boolean  voice,boolean  openSoftewarePwd,boolean  openNotePwd){
		this.userId = userId;
		this.openSoftewarePwd = openSoftewarePwd;
		this.openNotePwd = openNotePwd;
		this.voice =voice;
	}
}
