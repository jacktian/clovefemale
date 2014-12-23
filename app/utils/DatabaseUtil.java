package utils;

import java.util.Date;

import models.Baby;
import models.User;
import models.UserToBaby;

public class DatabaseUtil {
	public static void init(){
		System.out.println("数据库初始化");
		/*人员测试数据*/
		User u0 = new User("曾柏羲","12344623","boxiZen","15989070640","boxizen@qq.com","441381199110241816");
		u0.save();
		User u1 = new User("测试1","43235242","test0","15989070640","test1@qq.com","441381199110241816");
		u1.save();
		User u2 = new User("测试2","67868769","test1","15989070640","test1@qq.com","441381199110241816");
		u2.save();
		User u3 = new User("测试3","08900756","test2","15989070640","test2@qq.com","441381199110241816");
		u3.save();
		User u4 = new User("测试4","fsdferfw","test3","15989070640","test3@qq.com","441381199110241816");
		u4.save();
		User u5 = new User("测试5","94562839","test4","15989070640","test4@qq.com","441381199110241816");
		u5.save();
		User u6 = new User("测试6","0fj49fj4","test5","15989070640","test5@qq.com","441381199110241816");
		u6.save();
		User u7 = new User("测试7","756g4634","test6","15989070640","test6@qq.com","441381199110241816");
		u7.save();
		User u8 = new User("测试8","6575ghf5","test7","15989070640","test7@qq.com","441381199110241816");
		u8.save();
		User u9 = new User("测试9","zxcsdgd5","test8","15989070640","test8@qq.com","441381199110241816");
		u9.save();
		/*宝贝测试数据*/
		Baby baby = new Baby(new Date(),"male","小喇叭",u0.id);
    	baby.save();
    /*	UserToBaby userBaby = new UserToBaby(u0.id,baby.id);
    	userBaby.save();*/
    	System.out.println("用户ID:"+u0.getId());
    	
    	Baby baby1 = new Baby(new Date(),"female","小飞机",u1.id);
    	baby1.save();
    	/*UserToBaby userBaby1 = new UserToBaby(u1.id,baby1.id);
    	userBaby1.save();*/
	}
}
