package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import models.Baby;
import models.BodyIndex;
import models.GradeCondition;
import models.User;
import play.db.jpa.JPA;
import beans.BIndexBean;
import beans.GradeBean;

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
    	 String flag="all";
    	 /*分页显示*/
     	long count = Baby.count();
     	long pageNum = 0;
     	
     	if(count%10!=0)
     		pageNum = count/10+1;
     	else
     		pageNum = count/10;
     	System.out.println(count);
         render("/RecordMgm/growthRecordMgm.html",pageNum,flag);
     }
     
     public static void statistics(){
    	 render("/statistic/statistic.html");
     }
     /**
      * 查找baby
      */
     public static void findBabyByPro(){
    	 render("/RecordMgm/findBaby.html");
    	 
     }
     /**
      * 根据不同条件筛选baby 负责跳转页面
      */
     public static void findBabyByCondition(){
    	 render("/RecordMgm/searchCondition.html");
     }
     
     public static void test1(){
    	 List<User> users=User.find("userName = ?", "user2").fetch();
    	 wsOk(users);
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
     
     /*
 	 * 根据孩子id查询孩子的身体指标
 	 * 参数：小孩id
 	 * 返回：对应的列表
 	 * */
 	public static void findBI(String babyId,int curpage) {
 		List<BodyIndex> list = BodyIndex.find("babyId = ? order by date",babyId).fetch();
 		List<BIndexBean> listbean = null;
 		long pageNum = 0;//总页数
     	long count = 0;
     	if(list!=null){
        	count=list.size();//计算总记录条数，以2条一页为基准
        	if(count%2!=0)
     		pageNum = count/2+1;
     	else
     		pageNum = count/2;
        	
        	list = BodyIndex.find("babyId = ? order by date",babyId).fetch(curpage,2);
     		listbean=BIndexBean.builList(list);
         	}
 		
     	render("/RecordMgm/bodyIndex.html",listbean,pageNum,curpage,babyId);
 	}
 	
 	 /*
 	 * 根据孩子id查询孩子的成绩指标
 	 * 参数：小孩id
 	 * 返回：对应的列表
 	 * */
 	public static void findGrade(String babyId,int curpage) {
 		List<GradeCondition> list = GradeCondition.find("babyId = ? order by date",babyId).fetch();
 		List<GradeBean> listbean = null;
 		long pageNum = 0;//总页数
     	long count = 0;
     	if(list!=null){
        	count=list.size();//计算总记录条数，以2条一页为基准
        	if(count%2!=0)
     		pageNum = count/2+1;
     	else
     		pageNum = count/2;
        	
        	list = GradeCondition.find("babyId = ? order by date",babyId).fetch(curpage,2);
     		listbean=GradeBean.builList(list);
         	}
 		
     	render("/RecordMgm/subjectGrade.html",listbean,pageNum,curpage,babyId);
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