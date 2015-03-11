package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Baby;
import models.User;

import beans.BabyBean;

import com.sudocn.play.BasicModel;
/**
 * 宝贝controller
 * @author Yingpeng
 * @since 03/05/15
 * */
public class BabyAction extends WebService{
    
	/**
	 * 返回所有孩子
	 */
	public static void listBabys(int curpage){
	 List<Baby> babys = Baby.all().fetch(curpage, 10);
   	 wsOk(babys);
   /*	wsOkAsJsonP(babys);*/
    }
	
	/**
	 * 返回所有孩子Bean对象
	 */
	public static void listBabyBeans(int curpage){
	 List<Baby> babyList = Baby.all().fetch(curpage, 10);
	 List<BabyBean> babys = BabyBean.builList(babyList);
   	 wsOk(babys);
    }
	
	/**
	 * 删除孩子
	 */
	public static void delBaby(String id){
   	 Baby baby = Baby.findById(id);
   	 baby.delete();
	}
	
	/**
	 * 修改孩子信息
	 */
	public static void alterBaby(String id,String name,String sex,Date date){
   	 Baby baby = Baby.findById(id);
   	 baby.name = name;
   	 baby.sex = sex;
   	 baby.date = date;
   	 baby.save();
	}
	
	 public static void searchBaby(int type,String name){
		 String flag="all";
		 long count=1;
		 if(type==1){
			  count=Baby.find("name", name).fetch().size();
			  flag="name";
		 }else{
			  List<User> list=User.find("IDcard",name).fetch();
				List<Baby> babylist = Baby.find("userId", list.get(0).id).fetch();
			 count=babylist.size();
			 flag="user";
		 }
		 /*分页显示*/
	     	long pageNum = 0;
	     	if(count%10!=0)
	     		pageNum = count/10+1;
	     	else
	     		pageNum = count/10;
	     	System.out.println(count);
	     	String seachInfo=name;
	         render("/RecordMgm/growthRecordMgm.html",pageNum,flag,seachInfo);
     }
	/**
	 * 根据孩子姓名查找孩子
	 */
	public static void findBabyByName(int curpage,String name){
		List<Baby> babylist = Baby.find("name", name).fetch();
		List<BabyBean> babys = BabyBean.builList(babylist);
		System.out.println(babys.size());
		wsOk(babys);
	}
	
	/**
	 * 根据用户姓名查找孩子
	 */
	public static void findBabyByUser(int curpage,String userIDcard){
		List<User> list=User.find("IDcard",userIDcard).fetch();
		List<Baby> babylist = Baby.find("userId", list.get(0).id).fetch();
		List<BabyBean> babys = BabyBean.builList(babylist);
		wsOk(babys);
	}
	
	
}