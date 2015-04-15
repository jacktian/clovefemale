package jobs;

import play.jobs.Job;
import play.jobs.OnApplicationStart;
import testData.AgendaTestDataGenerator;

@OnApplicationStart
public class Bootstrap extends Job{
	public void doJob(){
		//AgendaTestDataGenerator.init();
	}
}
