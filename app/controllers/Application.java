package controllers;

import play.*;
import play.db.jpa.JPA;
import play.mvc.*;

import java.util.*;

import javax.persistence.Query;


import models.*;

/**
 * 主控制器
 *
 * @author boxiZen
 * @since 12/16/14
 */
public class Application extends WebService {

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
     
     public static void statistics(){
    	 render("/statistic/statistic.html");
     }
     
     public static void test(){
    	 List<String> list = new ArrayList<String>();
    	 list.add("abcd");
    	 list.add("avsdf");
    	 String jpql = "select count(*) from User u";
    	 Query query = JPA.em().createQuery(jpql);
    	 /*new beans.TestBean(u.userName,u.passwd) where u.passwd in (:regions)*/
    	 /*query.setParameter("regions",list);*/
    	 wsOk(query.getResultList());
     }
}