package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import models.Baby;
import models.User;
import play.db.jpa.JPA;

import com.sudocn.play.BasicModel;

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
     
     /**
      * 保存实体类并返回JSONP回调结果
      * @param model
      */
     protected static void saveModel(BasicModel model){
    	 String result = "" ;
    	 if(model != null){
 			try{
 				model.save() ;
 				result = "success" ;
 			}catch(Exception e){
 				e.printStackTrace() ;
 				result = "fail" ;
 			}
 		}else{
 			result = "fail" ;
 		}
 		wsOkAsJsonP(result) ;
     }
}