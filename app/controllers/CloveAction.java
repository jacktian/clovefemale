package controllers;

import java.util.ArrayList;
import java.util.List;

import beans.PicArticle;
import beans.WeChatPicMsgResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import play.Logger;
import play.Play;
import play.libs.WS;
import play.mvc.Before;

/**
 * 丁香资讯
 * @author boxizen
 * @since 2015/06/04
 */
public class CloveAction extends WebService{

	/*
	 * 获取用户信息 
	 */
	@Before
	public static void getUserOpenid(){
		
	}
	
	/*
	 * 获得最新资讯 
	 */
	public static void getNewMsg(){
		
	}
}
