package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Baby;
import models.UserToBaby;

import com.sudocn.play.BasicModel;

public class BabyAction extends WebService{
	
	/*public static void findBabyByUser(String userId){
		List<UserToBaby>userBabyList = UserToBaby.findByUser(userId);
		List<Baby> babyList = new ArrayList();
		for(UserToBaby userBaby:userBabyList){
			System.out.println(userBaby.babyId);
			babyList.add(Baby.findById(userBaby.babyId));
		}
		wsOk(babyList);
	}*/
	
	public static void findBabyList(){
		List<Baby> babyList = Baby.findAll();
		wsOk(babyList);
	}
	
	/*public static void findA(){
		List<UserToBaby> list = UserToBaby.findAll();
		wsOk(list);
	}
	
	public static void findById(String id){
		wsOk(Baby.findById(id));
	}*/
	
}
