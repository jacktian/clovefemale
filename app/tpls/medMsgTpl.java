package tpls;

import com.alibaba.fastjson.JSONObject;
import play.Play;

/**
 * Created by boxizen on 15/11/21.
 */
public class medMsgTpl {

    public static JSONObject getJson(String openid, String msg,String name, String endDate, String remark) {

        String templateid = Play.configuration.getProperty("medTplId");

        String jsonStr = "{ \"touser\":\""+openid+"\", \"template_id\":\""+templateid+"\", \"data\":{ \"first\": { \"value\":\""+msg+"\", \"color\":\"#173177\" }, \"name\":{ \"value\":\""+name+"\", \"color\":\"#173177\" }, \"expDate\": { \"value\":\""+endDate+"\", \"color\":\"#173177\" },  \"remark\":{ \"value\":\""+remark+"\", \"color\":\"#173177\" } } }";

        JSONObject json = JSONObject.parseObject(jsonStr);

        return json;
    }
}
