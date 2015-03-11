package controllers;

import java.util.Date;

import models.Settings;
import models.User;
/**
 * 用户设置controller
 * @author Yingpeng
 * @since 03/05/15
 * */
public class SettingOp extends WebService{
	/**
	 * 功能：查询用户所有提醒设置
	 * 返回：settings类
	 * */
	public static void selectSettings(String userId){
		try{
			Settings settings = (Settings)Settings.find("userId = ?", userId).fetch().get(0);
			if(settings!=null){
			wsOkAsJsonP(settings);
			}else{
			wsOkAsJsonP("查询失败，不存在该用户的设置");
			}
			}catch(Exception e){
			wsErrorAsJsonP("操作异常");
			}
	}
	/**
	 * 功能：修改生理记录提醒设置
	 * 返回：是否修改成功
	 * */
	public static void modifyPhy(String userId){
		try{
			Settings settings = (Settings)Settings.find("userId", userId).fetch().get(0);
			if(settings!=null){
			if(settings.physiology!=true){
				settings.physiology = true;
				settings.save();
			}else{
				settings.physiology = false;
				settings.save();
			}
			wsOkAsJsonP("修改成功");
			}else{
			wsOkAsJsonP("修改失败，不存在该用户设置");
			}
			}catch(Exception e){
			wsErrorAsJsonP("操作异常");
			}
	}
	/**
	 * 功能：修改药品到期提醒设置
	 * 返回：是否修改成功
	 * */
	public static void modifyMed(String userId){
		try{
			Settings settings = (Settings)Settings.find("userId", userId).fetch().get(0);
			if(settings!=null){
			if(settings.medicine!=true){
				settings.medicine = true;
				settings.save();
			}else{
				settings.medicine = false;
				settings.save();
			}
			wsOkAsJsonP("修改成功");
			}else{
			wsOkAsJsonP("修改失败，不存在该用户设置");
			}
			}catch(Exception e){
			wsErrorAsJsonP("操作异常");
			}
	}
	/**
	 * 功能：修改疫苗接种提醒设置
	 * 返回：是否修改成功
	 * */
	public static void modifyVac(String userId){
		try{
			Settings settings = (Settings)Settings.find("userId", userId).fetch().get(0);
			if(settings!=null){
			if(settings.vaccine!=true){
				settings.vaccine = true;
				settings.save();
			}else{
				settings.vaccine = false;
				settings.save();
			}
			wsOkAsJsonP("修改成功");
			}else{
			wsOkAsJsonP("修改失败，不存在该用户设置");
			}
			}catch(Exception e){
			wsErrorAsJsonP("操作异常");
			}
	}
	/**
	 * 功能：修改是否打开提醒声音设置
	 * 返回：是否修改成功
	 * */
	public static void modifyVoice(String userId){
		try{
			Settings settings = (Settings)Settings.find("userId", userId).fetch().get(0);
			if(settings!=null){
			if(settings.voice!=true){
				settings.voice = true;
				settings.save();
			}else{
				settings.voice = false;
				settings.save();
			}
			wsOkAsJsonP("修改成功");
			}else{
			wsOkAsJsonP("修改失败，不存在该用户设置");
			}
			}catch(Exception e){
			wsErrorAsJsonP("操作异常");
			}
	}
	/**
	 * 功能：修改提醒开始时间
	 * 返回：是否修改成功
	 * */
	public static void modifyRemindS(String userId,Date remindStart){
		try{
			Settings settings = (Settings)Settings.find("userId", userId).fetch().get(0);
			if(settings!=null){
			settings.remindStart = remindStart;
			settings.save();
			wsOkAsJsonP("修改成功");
			}else{
			wsOkAsJsonP("修改失败，不存在该用户设置");
			}
			}catch(Exception e){
			wsErrorAsJsonP("操作异常");
			}
	}
	/**
	 * 功能：修改提醒结束时间
	 * 返回：是否修改成功
	 * */
	public static void modifyRemindE(String userId,Date remindEnd){
		try{
			Settings settings = (Settings)Settings.find("userId", userId).fetch().get(0);
			if(settings!=null){
			settings.remindEnd = remindEnd;
			settings.save();
			wsOkAsJsonP("修改成功");
			}else{
			wsOkAsJsonP("修改失败，不存在该用户设置");
			}
			}catch(Exception e){
			wsErrorAsJsonP("操作异常");
			}
	}
	/**
	 * 功能：修改是否打开软件密码保护
	 * 返回：是否修改成功
	 * */
	public static void modifyOpenSwPwd(String userId){
		try{
			Settings settings = (Settings)Settings.find("userId", userId).fetch().get(0);
			if(settings!=null){
			if(settings.openSoftewarePwd!=true){
				settings.openSoftewarePwd = true;
				settings.save();
			}else{
				settings.openSoftewarePwd = false;
				settings.save();
			}
			wsOkAsJsonP("修改成功");
			}else{
			wsOkAsJsonP("修改失败，不存在该用户设置");
			}
			}catch(Exception e){
			wsErrorAsJsonP("操作异常");
			}
		
	}
	/**
	 * 功能：修改是否开通笔记密码保护
	 * 返回：是否修改成功
	 * */
	public static void modifyOpenNotePwd(String userId){
		try{
			Settings settings = (Settings)Settings.find("userId", userId).fetch().get(0);
			if(settings!=null){
			if(settings.openNotePwd!=true){
				settings.openNotePwd = true;
				settings.save();
			}else{
				settings.openNotePwd = false;
				settings.save();
			}
			wsOkAsJsonP("修改成功");
			}else{
			wsOkAsJsonP("修改失败，不存在该用户设置");
			}
			}catch(Exception e){
			wsErrorAsJsonP("操作异常");
			}
	}
	/**
	 * 功能：修改手势密码
	 * 返回：是否修改成功
	 * */
	public static void modifyGesturePwd(String userId,String gesturePwd){
		try{
			Settings settings = (Settings)Settings.find("userId", userId).fetch().get(0);
			if(settings!=null){
			settings.gesturePwd = gesturePwd;
			settings.save();
			wsOkAsJsonP("修改成功");
			}else{
			wsOkAsJsonP("修改失败，不存在该用户设置");
			}
			}catch(Exception e){
			wsErrorAsJsonP("操作异常");
			}
	}
	/**
	 * 功能：查询手势密码
	 * 返回：手势密码字符串
	 * */
	public static void searchGesturePwd(String userId){
		try{
			Settings settings = (Settings)Settings.find("userId", userId).fetch().get(0);
			if(settings!=null){
			wsOkAsJsonP(settings.gesturePwd);
			}else{
			wsOkAsJsonP("查询失败，不存在该用户设置");
			}
			}catch(Exception e){
			wsErrorAsJsonP("操作异常");
			}
	}
}
