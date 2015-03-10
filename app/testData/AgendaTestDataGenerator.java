package testData;

import utils.DatabaseUtil;
import models.User;

/**
 * 日程测试数据生成器
 * @author Tanshichnag
 * @since 2015-3-10
 *
 */
public class AgendaTestDataGenerator {

	private static User testUser ;
	
	public static void init(){
		generateCalendarTestData() ;
	}
	
	private static void generateCalendarTestData(){
		User user = (User) User.all().fetch(1).get(0) ;
		testUser = user ;
		java.util.Calendar c  = java.util.Calendar.getInstance();
		/*int year = c.get(c.YEAR) ;
		int month = c.get(c.MONTH) ;
		int day = c.get(c.DAY_OF_MONTH) ;
		int hour = c.get(c.HOUR_OF_DAY) ;
		int minute = c.get(c.MINUTE) ;*/
		long times = c.getTimeInMillis() ;
		int[] types = {models.Agenda.AGENDA_TYPE_VACCINATION, models.Agenda.AGENDA_TYPE_MEDECINE_STALE} ;
		
		for(int i = 1 ; i <= 3 ; i++){
			models.Agenda agenda = new models.Agenda() ;
			agenda.startTime = times + i*10*60*1000 ;	//i*10分钟后提醒
			agenda.endTime = times + i*10*60*1000 + 30*60*1000 ;
			agenda.userId = user.id ;
			agenda.location = "广州" ;
			agenda.remindMinutes = 30 ;	//提前30分钟提醒
			agenda.type = types[i%types.length] ;
			agenda.title = "事件"+i ;
			agenda.description = "测试事件"+i + ",userName:"+user.userName ;
			agenda.save() ;
		}
	}
	
	public static User getTestUser(){
		if(testUser == null){
//			testUser = (User) User.all().fetch(1).get(0) ;
			generateCalendarTestData() ;
		}
		return testUser ;
	}
	
}
