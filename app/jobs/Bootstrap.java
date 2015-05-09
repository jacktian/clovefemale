package jobs;

import play.jobs.Job;
import play.jobs.OnApplicationStart;
import testData.AgendaTestDataGenerator;

@OnApplicationStart
public class Bootstrap extends Job{
	public void doJob(){
		//获取accessToken
		AccessTokenRefresher.getAccessToken();
		//获取js_ticket
		JsTicketRefresher.getJsTicket();
		//创建自定义菜单
		CustomMenuRefresher.createMenuNow();
	}
}
