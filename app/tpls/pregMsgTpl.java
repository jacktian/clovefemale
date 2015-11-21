package tpls;

import com.alibaba.fastjson.JSONObject;
import play.Play;

/**
 * Created by boxizen on 15/11/21.
 */
public class pregMsgTpl {

    public static JSONObject getJson(String openid, String msg,String startDate, String endDate, String remark) {

        String templateid = Play.configuration.getProperty("pregTplId");

        String jsonStr = "{ \"touser\":\""+openid+"\", \"template_id\":\""+templateid+"\", \"data\":{ \"first\": { \"value\":\""+msg+"\", \"color\":\"#173177\" }, \"keyword1\":{ \"value\":\""+startDate+"\", \"color\":\"#173177\" }, \"keyword2\": { \"value\":\""+endDate+"\", \"color\":\"#173177\" },  \"remark\":{ \"value\":\""+remark+"\", \"color\":\"#173177\" } } }";

        JSONObject json = JSONObject.parseObject(jsonStr);

        return json;
    }
}
