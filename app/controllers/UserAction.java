package controllers;

import java.util.Date;
import java.util.List;

import models.Baby;
import models.User;
import models.UserToBaby;
import play.mvc.*;

/**
 * 用户控制器
 * 
 * @author boxiZen
 * @since 2014/11/24
 */
public class UserAction extends WebService{
	
	public static void addUser(String userName,String passwd,String realName,String phoneNum,String email,String IDcard){
    	User user=new User(userName, passwd, realName, phoneNum, email, IDcard);
    	user.save();
    }
	
	/*添加小孩*/
    public static void addBaby(Long id,Date date,String sex,String name){
    	User user=User.findById(id);
    	Baby baby = new Baby(date,sex,name);
    	baby.save();
    	UserToBaby userBaby = new UserToBaby(user.getId(),baby.getId());
    	userBaby.save();
    }
     
     public static void listUsers(){
    	 List<User> users = User.findAll();
    	 wsOk(users);
     }
     
     public static void listBabys(){
    	 List<Baby> babys=Baby.findAll();
    	 wsOk(babys);
     }
}
