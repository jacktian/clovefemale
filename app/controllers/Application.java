package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }
    
    public static void addUser(String userName,String passwd,String realName,String phoneNum,String email,String IDcard){
    	User user=new User(userName, passwd, realName, phoneNum, email, IDcard);
    	user.save();
    }

    public static void addBaby(Long id,Date date,String sex,String name){
    	User user=User.findById(id);
    	user.addBaby(date, sex, name);
    }
     
     public static void listUsers(){
    	 List<User> users=User.findAll();
    	 response.contentType = "application/json";
 		response.setHeader("Content-Type", "application/json;charset=UTF-8");
 		renderJSON(users);
     }
     
     public static void listBabys(){
    	 List<Baby> babys=Baby.findAll();
    	 response.contentType = "application/json";
 		response.setHeader("Content-Type", "application/json;charset=UTF-8");
 		renderJSON(babys);
     }

     public static void test(){
        System.out.println("ABCDEFG");      
    }
}