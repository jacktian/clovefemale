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
    		pageNum = count/10;
    	
        render("/UserMgm/userMgm.html",pageNum);
    }

     public static void userMgm(){
    	 /*分页显示*/
     	long count = User.count();
     	long pageNum = 0;
     	
     	if(count%10!=0)
     		pageNum = count/10+1;
     	else
     		pageNum = count/10;
     	
         render("/UserMgm/userMgm.html",pageNum);
     }
     
     public static void recordMgm(){
    	 /*分页显示*/
     	long count = Baby.count();
     	long pageNum = 0;
     	
     	if(count%10!=0)
     		pageNum = count/10+1;
     	else
     		pageNum = count/10;
     	System.out.println(count);
         render("/RecordMgm/growthRecordMgm.html",pageNum);
     }
}