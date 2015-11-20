package utils;

/**
 * Created by boxizen on 15/11/20.
 */

import com.alibaba.fastjson.JSONObject;
import controllers.WebService;
import play.Play;
import play.deps.PlayConflictManager;
import play.libs.WS;
import play.libs.WS.WSRequest;
import play.libs.WS.HttpResponse;

public class ModuleTpl extends WebService {

    public static void sendMsg() {

        String accessToken = "QZAKZZBkrMDXNf5kMdLuMGKLouXQib0o-JRwW6HXniaUA_pWFfr_NOsli4CGGgnrEsfGPF5YSoLBgt5dcz2zokxGsuWOg87pxaPf81XI2Q8LGMhAHATOU", openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";

        String api = Play.configuration.getProperty("tplApi") + accessToken;

        String jsonStr = "{\n" +
                "           \"touser\":\""+openid+"\",\n" +
                "           \"template_id\":\"ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY\",\n" +
                "           \"url\":\"http://weixin.qq.com/download\",            \n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"恭喜你购买成功！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keynote1\":{\n" +
                "                       \"value\":\"巧克力\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keynote2\": {\n" +
                "                       \"value\":\"39.8元\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keynote3\": {\n" +
                "                       \"value\":\"2014年9月22日\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"欢迎再次购买！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";

        WSRequest request = WS.url(api);

        HttpResponse response = request.body(jsonStr).post();

        System.out.println(response.getString());
    }
}

class TestJson {
    public String touser;
    public String template_id;
    public String url;
    public String data;
}
