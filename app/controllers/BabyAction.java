package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Baby;
import models.User;

import beans.BabyBean;

import com.sudocn.play.BasicModel;

/**
 * 宝贝控制器
 *
 * @author boxiZen
 * @since 12/16/14
 */
public class BabyAction extends WebService{
    
	/**
	 * 返回所有孩子
	 */
	public static void listBabys(int curpage){
	 List<Baby> babys = Baby.all().fetch(curpage, 10);
   	 wsOk(babys);
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
}
