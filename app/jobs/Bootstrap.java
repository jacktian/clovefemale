package jobs;

import play.jobs.Job;
import play.jobs.OnApplicationStart;
import utils.MedboxInit;
import utils.PoiUtil;

@OnApplicationStart
public class Bootstrap extends Job{
	public void doJob(){
		//获取accessToken
		AccessTokenRefresher.getAccessToken();
		//创建自定义菜单
		CustomMenuRefresher.createMenuNow();
		//获取js_ticket
		JsTicketRefresher.getJsTicket();
	}
}
