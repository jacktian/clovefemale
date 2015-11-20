package jobs;

import models.Medicine;
import models.Remind;
import play.jobs.Every;
import play.jobs.Job;

import java.util.List;
import java.util.Date;
/**
 * Created by boxizen on 15/8/22.
 */

@Every("1min")
public class Notification extends Job {

    @Override
    public void doJob(){
        List<Remind> remindList = Remind.findAll();

        for(int i = 0; i < remindList.size(); i++) {

            String openid = remindList.get(i).openid;

            int medr = remindList.get(i).medremind;
            // 查看药品是否过期
            if(medr == 1) {
                Date now = new Date();
                List<Medicine> mList = Medicine.find("byOpenid",openid).fetch();
                for(int m = 0; m < mList.size(); m++) {
                    Date dead = mList.get(m).deadline;
                    if(now.compareTo(dead) == -1) {
                        // 发送信息提醒
                    }
                }
            }
        }
    }
}
