package controllers;

import java.util.Date;
import java.util.List;

import models.Baby;
import models.User;
import play.mvc.*;

/**
 * 用户控制器
 * 
 * @author boxiZen
 * @since 2014/11/24
 */
public class UserAction extends WebService{
	
	/**
	 * 添加新用户
	 */
	public static void addUser(String userName,String passwd,String realName,String phoneNum,String email,String IDcard){
    	User user = new User();
    	user.userName = userName;
    	user.passwd = passwd;
    	user.realName = realName;
    	user.phoneNum = phoneNum;
    	user.email = email;
    	user.IDcard = IDcard;
    	user.save();
    }
	
	/**
	 * 修改用户信息
	 */
	public static void alterUser(String uId,String userName,String passwd,String realName,String phoneNum,String email,String IDcard){
    	User user = User.findById(uId);
    	user.userName = userName;
    	user.passwd = passwd;
    	user.realName = realName;
    	user.phoneNum = phoneNum;
    	user.email = email;
    	user.IDcard = IDcard;
    	user.save();
    	System.out.println("执行了");
    }
	
	/**
	 * 删除用户
	 */
	public static void delUser(String uId){
    	User user = User.findById(uId);
    	user.delete();
    }
	
	/**
	 * 添加新孩子
	 */
    public static void addBaby(String pId,Date date,String sex,String name){
    	Baby baby = new Baby();
    	baby.pId = pId;
    	baby.date = date;
    	baby.sex = sex;
    	baby.name = name;
    	baby.save();
    }

    /**
	 * 返回所有用户
	 */
     public static void listUsers(int curpage){
    	 List<User> users = User.all().fetch(curpage, 10);
    	 wsOk(users);
     }
     
}
