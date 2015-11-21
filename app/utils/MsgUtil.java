package utils;

import com.alibaba.fastjson.JSONObject;
import tpls.pregMsgTpl;
import tpls.medMsgTpl;
import tpls.ymMsgTpl;
import play.Play;
import play.libs.WS;

import java.util.Date;

/**
 * Created by boxizen on 15/11/21.
 */
public class MsgUtil {

    public static void sendMedMsg(String openid, String msg, String medicine, String endDate, String remark) {

        // 获取accessToken
        models.WeChat wxbean = new models.WeChat();
        wxbean = (models.WeChat) models.WeChat.findAll().get(0);
        String accessToken = wxbean.access_token;

        // 获得api
        String api = Play.configuration.getProperty("tplApi") + accessToken;
        JSONObject json = medMsgTpl.getJson(openid, msg, medicine , endDate, remark);

        // 发送模板消息
        WS.WSRequest request = WS.url(api);
        WS.HttpResponse response = request.body(json).post();
        System.out.println(response.getString());

    }

    public static void sendYmMsg(String openid) {

        // 获取accessToken
        models.WeChat wxbean = new models.WeChat();
        wxbean = (models.WeChat) models.WeChat.findAll().get(0);
        String accessToken = wxbean.access_token;
        String api = Play.configuration.getProperty("tplApi") + accessToken;

        // 设置消息内容
        String msg = "您的姨妈要来咯";
        String startDate = StringUtils.formatDate_yyyy_MM_dd(new Date());
        String endDate = StringUtils.formatDate_yyyy_MM_dd(new Date());
        String remark = "请好好注意身体哦";
        String url = "http://www.baidu.com/";

        //JSONObject json = pregMsgTpl.getJson(openid, msg, startDate, endDate, remark, url);
        //JSONObject json = medMsgTpl.getJson(openid, msg, msg , endDate, remark);
        JSONObject json = ymMsgTpl.getJson(openid, msg, startDate, endDate, remark);

        // 发送模板消息
        WS.WSRequest request = WS.url(api);
        WS.HttpResponse response = request.body(json).post();
        System.out.println(response.getString());

    }


    public static void sendPregMsg(String openid) {

        // 获取accessToken
        models.WeChat wxbean = new models.WeChat();
        wxbean = (models.WeChat) models.WeChat.findAll().get(0);
        String accessToken = wxbean.access_token;
        String api = Play.configuration.getProperty("tplApi") + accessToken;

        // 设置消息内容
        String msg = "您的姨妈要来咯";
        String startDate = StringUtils.formatDate_yyyy_MM_dd(new Date());
        String endDate = StringUtils.formatDate_yyyy_MM_dd(new Date());
        String remark = "请好好注意身体哦";
        String url = "http://www.baidu.com/";

        //JSONObject json = pregMsgTpl.getJson(openid, msg, startDate, endDate, remark, url);
        //JSONObject json = medMsgTpl.getJson(openid, msg, msg , endDate, remark);
        JSONObject json = ymMsgTpl.getJson(openid, msg, startDate, endDate, remark);

        // 发送模板消息
        WS.WSRequest request = WS.url(api);
        WS.HttpResponse response = request.body(json).post();
        System.out.println(response.getString());

    }
}
