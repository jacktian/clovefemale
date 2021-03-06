package controllers;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import beans.TestBean;

import models.Baby;
import models.User;
import play.cache.CacheFor;
import play.db.jpa.Model;
import play.mvc.*;

/**
 * 用户控制器
 * 
 * @author boxiZen
 * @since 2014/11/24
 */
public class UserAction extends WebService{
	
	/**
	 * 用户登录
	 */
	public static void login(String accountNum, String password){
		//先进行密码加密
		
		//匹配
		try{
			List<User> userList = User.find("(email = ? and passwd = ?) or  (phoneNum = ? and passwd = ?) or (userName = ? and passwd = ?)",accountNum,password,accountNum,password,accountNum,password).fetch();
			if(userList.size() != 0){
				User user = userList.get(0);
				wsOkAsJsonP(user);
			}else{
				wsErrorAsJsonP("登录失败");
			}
		}catch(Exception e){
			wsErrorAsJsonP("数据库操作出错");
		}
	}
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
    	baby.userId = pId;
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

     
     /**
 	 * 返回所有用户
 	 */
     public static void listSearchUser(String username){
    	 List<User> userList = User.find("IDcard = ? or userName = ? or realName = ?",username,username,username).fetch();
    	/* List<User> userList = User.find("userName = ?",username).fetch();*/
    	 wsOk(userList);
     }

    
    @CacheFor("10s")
    public static void test(){
    	String sql = "select count(*) from User,Baby where User.id = Baby.pid";
    	Query query = Model.em().createNativeQuery(sql);
    	/*System.out.println(query.getResultList());*/
    	wsOk(query.getResultList());
    }
    
    @CacheFor("10s")
    public static void test2(){
    	String sql = "select * from User;";
    	Query query = Model.em().createNativeQuery(sql,User.class);
    	wsOk(query.getResultList());
    	//wsOk(query.getResultList());
    }
}

