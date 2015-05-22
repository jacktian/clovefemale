package jobs;

import models.Medicine;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import testData.AgendaTestDataGenerator;

@OnApplicationStart
public class Bootstrap extends Job{
	public void doJob(){
		//获取accessToken
		AccessTokenRefresher.getAccessToken();
		//创建自定义菜单
		CustomMenuRefresher.createMenuNow();
		//获取js_ticket
		JsTicketRefresher.getJsTicket();
	/*	Medicine med = new Medicine();
		med.name = "加味藿香正气丸";*/
	}
}
