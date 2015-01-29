package jobs;

import play.jobs.Job;
import play.jobs.OnApplicationStart;
import utils.DatabaseUtil;

@OnApplicationStart
public class Bootstrap extends Job{
	public void doJob(){
		DatabaseUtil.init();
	}
}
