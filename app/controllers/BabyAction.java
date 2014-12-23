package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Baby;
import models.User;
import models.UserToBaby;

public class BabyAction extends WebService{
	/*
	 * 查询全部baby列表
	 * 参数：无
	 * 返回：baby列表
	 * */
	public static void findBabyList(){
		List<Baby> babyList = Baby.findAll();
		wsOk(babyList);
	}
	
	/*
	 * 根据用户id查询所有的baby
	 * 参数：用户ID
	 * 返回：满足条件的baby列表
	 * */
	public static void findBabyByUser(String userId){
		List<UserToBaby> userBabyList = UserToBaby.findByUser(userId);
		List<Baby> babyList = new ArrayList();
		for(UserToBaby userBaby:userBabyList){
			/*System.out.println(userBaby.babyId);*/
			Baby baby =Baby.findById(userBaby.babyId);
			babyList.add(baby);
		}
		wsOk(babyList);
	}
	/*
	 * 根据babyid查询对应的用户
	 * 参数：babyID
	 * 返回：对应的user
	 * */
	public static void findUserByBaby(String babyId){
		List<UserToBaby> userBabyList = UserToBaby.findByBaby(babyId);
		User user=User.findById(userBabyList.get(0).userId);
		wsOk(user);
	}
	
	/*
	 * 根据id查询对应的baby
	 * 参数：id
	 * 返回：对应的baby
	 * */
	public static void findById(String id){
		wsOk(Baby.findById(id));
	}
	/*
	 * 新增baby
	 * 参数：前台传回的实体类，用户id
	 * 返回：全部列表
	 * */
	public static void addBaby(Baby baby,String userId){
		Baby b=baby.save();
		Baby.createUtoB(userId, b.id);
		findBabyList();
	}
	/*
	 * 根据id删除baby
	 * 参数：babyID
	 * 返回：全部列表
	 * */
	public static void deleteBaby(String babyId){
		UserToBaby.delete("babyId = ?", babyId);
		Baby.delete("id = ?", babyId);
		findBabyList();
	}
	/*
	 * 更新baby
	 * 参数：baby类
	 * 返回：全部列表
	 * */
	public static void updateBaby(Baby baby){
		baby.save();
		findBabyList();
	}
}
