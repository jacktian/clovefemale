package tpls;

import com.alibaba.fastjson.JSONObject;
import play.Play;

/**
 * Created by boxizen on 15/11/21.
 */
public class ymMsgTpl {

    /*
     *  @param msg      提醒消息
     *  @param stage    成长阶段
     *  @param tip      阶段提醒
     *  @param remark   备注消息
     */
    public static JSONObject getJson(String openid, String msg,String stage, String tip, String remark) {

        String templateid = Play.configuration.getProperty("ymTplId");

        String jsonStr = "{ \"touser\":\""+openid+"\", \"template_id\":\""+templateid+"\", \"data\":{ \"first\": { \"value\":\""+msg+"\", \"color\":\"#173177\" }, \"keyword1\":{ \"value\":\""+stage+"\", \"color\":\"#173177\" }, \"keyword2\": { \"value\":\""+tip+"\", \"color\":\"#173177\" },  \"remark\":{ \"value\":\""+remark+"\", \"color\":\"#173177\" } } }";

        JSONObject json = JSONObject.parseObject(jsonStr);

        return json;
    }
}
