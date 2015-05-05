package utils;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;  

import models.WeChat;
import jobs.AccessTokenRefresher;
import play.libs.WS;
import play.libs.WS.HttpResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * 微信前端签名工具
 * @author boxiZen
 * @since 2015/05/05
 */

public class Sign {
    public static Map<String, String> process(String ticket,String crtUrl) {
        String jsapi_ticket = ticket;

        // 注意 URL 一定要动态获取，不能 hardcode
        String url = crtUrl;
        Map<String, String> ret = sign(jsapi_ticket, url);
        /*for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }*/
        return ret;
    }

    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
    
    public static Map<String,String> create_sign(String url){	
    	Map<String,String> ret = null;
    	/*获取js_ticket值*/
		WeChat wechat = (WeChat) WeChat.findAll().get(0);
		String ticket = wechat.js_ticket;
    	/*生成签名*/
		try{
			ret = Sign.process(ticket, url);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			return ret;
		}
    }
}
