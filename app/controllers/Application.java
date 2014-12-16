package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
    	/*分页显示*/
    	long count = User.count();
    	long pageNum = 0;
    	
    	if(count%10!=0)
    		pageNum = count/10+1;
    	else
    		pageNum = User.count()/10;
    	
        render("/Application/userMgm.html",pageNum);
    }

     public static void test(String name,int age){
        System.out.println(name+":"+age);      
     }
}