package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import models.Baby;
import models.BodyIndex;
import models.GradeForm;
import models.User;
import models.Vaccination;
import play.db.jpa.JPA;
import beans.BIndexBean;
import beans.GradeBean;
import beans.UserNBbean;
import beans.VaccBean;
import beans.userMBoxBean;

import com.sudocn.play.BasicModel;

/**
 * 主控制器
 *
 * @author Yingpeng
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

 public static void index1() {
    	
    	
        render("/Application/index1.html");
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
 		List<GradeForm> list = GradeForm.find("babyId = ? order by date",babyId).fetch();
 		List<GradeBean> listbean = null;
 		long pageNum = 0;//总页数
     	long count = 0;
     	if(list!=null){
        	count=list.size();//计算总记录条数，以2条一页为基准
        	if(count%2!=0)
     		pageNum = count/2+1;
     	else
     		pageNum = count/2;
        	
        	list = GradeForm.find("babyId = ? order by date",babyId).fetch(curpage,2);
     		listbean=GradeBean.builList(list);
         	}
 		
     	render("/RecordMgm/subjectGrade.html",listbean,pageNum,curpage,babyId);
 	}
 	
 	/**
 	 * 根据孩子id查询孩子的及，疫苗记录
 	 * 参数：小孩Id
 	 * 返回：对应的列表
 	 * */
 	public static void findVacc(String babyId,int curpage) {
 		if(curpage == 0){
 			curpage = 1;
 		}
 		List<Vaccination> list = Vaccination.find("babyId = ? order by date",babyId).fetch();
 		List<VaccBean> listbean = null;
 		long pageNum = 0;//总页数
     	long count = 0;
     	if(list!=null){
        	count=list.size();//计算总记录条数，以2条一页为基准
        	if(count%2!=0)
     		pageNum = count/2+1;
     	else
     		pageNum = count/2;
        	
        	list = Vaccination.find("babyId = ? order by date",babyId).fetch(curpage,2);
     		listbean=VaccBean.builList(list);
         	}
 		
     	render("/RecordMgm/bodyVacc.html",listbean,pageNum,curpage,babyId);
 	}
 	
 	
 	
 	/**
 	 * 用户药箱管理列表页面
 	 * */
 	public static void listUserMbox(int curpage){
 		System.out.println("----"+curpage);
 		if(curpage == 0){
 		 curpage =1;
 		}
 		long count =0;
 		long pageNum =0;
 		List<userMBoxBean> listbean =null;
 		List<User> list = User.all().fetch(curpage, 10);
 		if(list!=null){
 			count = User.findAll().size();
 			if(count%10!=0)
 	     		pageNum = count/10+1;
 	     	else
 	     		pageNum = count/10;
 			
 			listbean=userMBoxBean.builList(list);
 		}
 		render("/MedicineMgm/userMboxList.html",listbean,pageNum,curpage);
 	}
 	/**
 	 * 查找特定用户的药箱
 	 * */
 	public static void findUerMbox(){
 		render("/MedicineMgm/findUserMbox.html");
 	}
 	/**
 	 * 查询用户的笔记本情况
 	 * */
 	public static void listUserNotebook(int curpage){
 		
 		if(curpage == 0){
 	 		 curpage =1;
 	 		}
 	 		long count =0;
 	 		long pageNum =0;
 	 		List<UserNBbean> listbean =null;
 	 		List<User> list = User.all().fetch(curpage, 10);
 	 		if(list!=null){
 	 			count = User.findAll().size();
 	 			if(count%10!=0)
 	 	     		pageNum = count/10+1; 
 	 	     	else
 	 	     		pageNum = count/10;
 	 			
 	 			listbean=UserNBbean.builList(list);
 	 		}
 	 		render("/NoteMgm/userNoteBookList.html",listbean,pageNum,curpage);
 	}
 	
 	/**
 	 * 根据用户id或姓名查询用户的笔记本情况
 	 * */
 	public static void listUserNotebookInfo(String searchInfo,int curpage){
 		if(curpage == 0){
 	 		 curpage =1;
 	 		}
 	 		long pageNum =1;
 	 		List<UserNBbean> listbean =null;
 	 		List<User> list = User.find("id = ? or realName = ?", searchInfo,searchInfo).fetch();
 	 		if(list!=null){	
 	 			listbean=UserNBbean.builList(list);
 	 		}
 	 		render("/NoteMgm/userNoteBookList.html",listbean,pageNum,curpage);
 	}
 	
 	
 	
 	/**
 	 * 查询所有用户的女性助手
 	 * */
 	public static void listUserGR(int curpage){
 		if(curpage==0){
 			curpage =1;
 		}
 		long count =0;
	 	long pageNum =0;
	 	List<User> listbean = User.all().fetch(curpage, 10);
	 	if(listbean!=null){
	 		count = User.findAll().size();
	 		if(count%10!=0)
	 	     		pageNum = count/10+1; 
	 	     	else
	 	     		pageNum = count/10;
	 	}
	 	render("/gestationMgm/userGestationList.html",listbean,pageNum,curpage);
 		
 	}
 	
 	
 	/**
 	 * 根据用户编号或姓名查询所有女性助手
 	 * */
 	public static void listUsergrInfo(String searchmsg,int curpage){
 		if(curpage==0){
 			curpage =1;
 		}
	 	long pageNum =1;
	 	List<User> listbean = User.find("id = ? or realName = ?",searchmsg,searchmsg).fetch();
	 	render("/gestationMgm/userGestationList.html",listbean,pageNum,curpage);
 		
 	}
 	
 	
 	/**
 	 * 查询特定用户的女性助手,返回选择日期区间页面
 	 * */
 	public static void selectGrcondition(String userId,String type){
 		User user = User.findById(userId);
 		String userName =user.realName;
 		render("/gestationMgm/searchGrInfo.html",userId,userName,type);
 	}
 	/**
 	 * 跳转至根据用户Id或姓名查找对应的女性助手
 	 * */
 	public static void  searchUsergr(){
 		render("/gestationMgm/searchUsergtr.html");
 	}
 	/**
 	 * 根据用户id或姓名查找对应的笔记记录
 	 * */
 	public static void searchUserNotebook(){
 		render("/NoteMgm/searchUsernote.html");
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