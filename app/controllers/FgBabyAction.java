package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Baby;
import models.User;

import beans.BabyBean;

import com.sudocn.play.BasicModel;

/**
 * 宝贝控制器，与客户端交互专用
 * 
 * @author Tanshichang
 * @since 2015-2-10
 */
public class FgBabyAction extends WebService {

	public static void addBaby(Baby model) {
		FgAction.saveModel(model);
	}

}