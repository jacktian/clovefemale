package controllers;

import java.util.Date;
import java.util.List;

import beans.ResponseStatus;

import models.Baby;
import models.User;
import play.mvc.*;

/**
 * 客户端用户控制器
 * 
 * @author Cater
 * @since 2015/3/10
 */
public class CUserAction extends WebService{
	
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
	 *	加载用户信息 
	 */
	public static void loadUserInf(){
		String openid = session.get("openid");
		if(openid == null){
			openid = "ob1R-uIRkLLp6lmmrT4w-2rrZ5jQ";
		}
		try{
			List<User> userList = User.find("openid = ?", openid).fetch();
			if(userList.size()!=0){
				User user = userList.get(0);
				wsOk(user);
			}else{
				wsError("null");
			}
		}catch(Exception e){
			wsError("噢噢，出错了！");
		}
		
		
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
    }

    /**
     * 设置丁香号
     */
    public static void setCloveId(String cloveId){
    	List<User> users = User.find("cloveId = ?", cloveId).fetch();
    	ResponseStatus response = new ResponseStatus();
    	if(users.size()!=0){
    		response.status = 1;//1表示已经存在此cloveId
    		response.discribe = "此丁香号已被使用";//已经被使用
    	}else{
    		String openid = session.get("openid");
//    		System.out.println(openid);
    		if(openid == null || "".equals(openid)){
    			response.status = 3;//3表示获取openid失败
                response.discribe = "获取openid失败,请使用微信公众号进入系统!";
    		}else{
    			users = User.find("openid = ?", openid).fetch();
                if(users.size() != 0){
                	 User user = users.get(0);
                	 if(user.cloveId == null || "".equals(user.cloveId)){//从未设置丁香号
                		 user.cloveId = cloveId;
                         user.save();
                         response.status = 0;//0表示设置成功
                         response.discribe = "设置成功";
                	 }else{//已经设置过丁香号
                		 response.status = 2;//已经设置过丁香号
                         response.discribe = "丁香号只能设置一次哦！";
                	 }
                }
    		}	
    	}
    	System.out.println(response.status);
    	wsOk(response);
    }
    
    /**
     * 修改用户个性签名
     */
    public static void modifySignName(String signName){
    	String openid = session.get("openid");
		if(openid == null || "".equals(openid)){
			wsError("获取openid失败,请使用微信公众号进入系统!");
		}else{
			try{
				List<User> userList = User.find("openid = ?", openid).fetch();
				if(userList.size()!=0){
					User user = userList.get(0);
					user.signName = signName;
					wsOk("设置成功");
	 			}else{
	 				wsError("null");
	 			}
			}catch(Exception e){
				wsError("噢噢，出错了！");
			}
			
		}
    }
    
    /**
     * 绑定手机号
     */
    public static void setPhone(String openId,String phoneNum){
    	
    }
    
    /**
     * 绑定邮箱
     */
    public static void setEmail(String openId,String email){
    	
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
}