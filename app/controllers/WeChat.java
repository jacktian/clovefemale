package controllers;

import play.cache.CacheFor;
import play.db.jpa.Model;
import play.mvc.*;

import utils.SignUtil;

/**
 * 微信控制器
 * 
 * @author boxiZen
 * @since 2015/03/23
 */
public class WeChat extends WebService{
	
	/**
	 * 令牌验证
	 */
	public static void tokenverify(String signature,String timestamp,String nonce,String echostr){
		if(SignUtil.checkSignature(signature, timestamp, nonce)){
			render("/Client/WeChat/index.html",echostr);
		}	
	}
}

