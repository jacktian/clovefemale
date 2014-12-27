package utils;

import java.util.Date;

import models.Baby;
import models.FetalMovement;
import models.GestationalWeight;
import models.Menses;
import models.Temperature;
import models.User;

public class DatabaseUtil {
	public static void init(){
		System.out.println("database init");
		
		/*人员测试数据*/
		for(int i=0;i<50;i++){
			User u = new User();
			u.userName = "user" + i;
			u.realName = "测试者" + i;
			u.passwd = "abcd" + i;
			u.phoneNum = "1598907064" + i;
			u.email = "31596495" + i + "@qq.com";
			u.IDcard = "44138119911024181" + i;
			u.save();
		}
		User u1 = (User) User.find("byUserName", "user1").fetch().get(0);
		User u2 = (User) User.find("byUserName", "user2").fetch().get(0);
		
		/*宝贝测试数据*/
		for(int i=0;i<10;i++){
			Baby baby = new Baby();
			baby.pId = u1.id;
			baby.name = "小喇叭" + i + "号";
			baby.sex = "male";
			baby.date = new Date();
			baby.save();
		}
    	
    	Menses menses = new Menses(u1.id, new Date(),"暗红", "多", true, true,"稠");
    	menses.save();
    	Menses menses2 = new Menses(u1.id, new Date(),"鲜红", "少", true, true,"稀");
    	menses2.save();
    	Menses menses3 = new Menses(u2.id, new Date(),"鲜红", "少", true, true,"稀");
    	menses3.save(); 
    	
    	Temperature temperature1 = new Temperature(u1.id,new Date(), 36.7F);
    	temperature1.save();
    	Temperature temperature2 = new Temperature(u2.id,new Date(), 37.7F);
    	temperature2.save();
    	Temperature temperature3 = new Temperature(u1.id,new Date(), 36.9F);
    	temperature3.save();
    	
    	GestationalWeight weight1 = new GestationalWeight(u1.id,new Date(), 56.2F);
    	weight1.save();
    	GestationalWeight weight2 = new GestationalWeight(u2.id,new Date(), 56.5F);
    	weight2.save();
    	GestationalWeight weight3 = new GestationalWeight(u1.id,new Date(), 56.6F);
    	weight3.save();
    	
    	
    	FetalMovement movement1 = new FetalMovement(u1.id,new Date(), 8);
    	movement1.save();
    	FetalMovement movement2 = new FetalMovement(u1.id,new Date(), 6);
    	movement2.save();
    	FetalMovement movement3 = new FetalMovement(u1.id,new Date(), 10);
    	movement3.save();

	}
}
