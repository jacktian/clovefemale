package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Baby;

import com.sudocn.play.BasicModel;

public class BabyAction extends WebService{
    
	/**
	 * 返回所有孩子
	 */
	public static void listBabys(){
   	 List<Baby> babys=Baby.findAll();
   	 wsOk(babys);
    }
}
