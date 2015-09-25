package jobs;

import play.Play;
import play.jobs.Every;
import play.jobs.Job;

@Every("110min")
public class MedicineNotification extends Job {

	@Override
	public void doJob(){

	}
}
