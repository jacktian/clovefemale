package utils;

import java.util.Date;

import models.Settings;
import models.User;

public class BackgroundUtil {
  public static void setData(User users[]){
	  for(int i = 0;i<users.length;i++){
		  Settings settings = new Settings();
		  settings.userId = users[i].id;
		  settings.medicine = false;
		  settings.gesturePwd = "123456";
		  settings.openNotePwd = false;
		  settings.openSoftewarePwd = true;
		  settings.physiology =false;
		  settings.remindEnd = new Date();
		  settings.remindStart = new Date();
		  settings.vaccine = false;
		  settings.voice = false;
		  settings.save();
	  }
  }
}
