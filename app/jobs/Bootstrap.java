package jobs;

import play.jobs.Job;
import play.jobs.OnApplicationStart;
import dataTest.AgendaTestDataGenerator;

@OnApplicationStart
public class Bootstrap extends Job{
	public void doJob(){
		AgendaTestDataGenerator.init();
	}
}
