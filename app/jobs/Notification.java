package jobs;

import models.Medicine;
import models.Remind;
import play.Play;
import play.jobs.Every;
import play.jobs.Job;

import java.util.List;
import java.util.Date;

import utils.*;

/**
 * Created by boxizen on 15/8/22.
 */

@Every("1min")
public class Notification extends Job {

    @Override
    public void doJob(){

        List<Remind> remindList = Remind.findAll();
        Date now = new Date();

        try {
            // 遍历提醒表
            for (int i = 0; i < remindList.size(); i++) {

                String openid = remindList.get(i).openid;
                int medRemind = remindList.get(i).medremind;
                int ymRemind = remindList.get(i).yiremind;
                int pregRemind = remindList.get(i).healremind;

                // 药品提醒
                if (medRemind == 1) {
                    List<Medicine> mList = Medicine.find("byOpenid", openid).fetch();
                    for (int m = 0; m < mList.size(); m++) {
                        Date dead = mList.get(m).deadline;
                        if (now.compareTo(dead) == 1) {
                            // 设置消息内容
                            String msg = "您的药品即将过期";
                            String remark = "请及时清空药箱";
                            String name = mList.get(m).name;
                            String endDate = StringUtils.formatDate_yyyy_MM_dd(mList.get(m).deadline);
                            MsgUtil.sendMedMsg(openid, msg, name, endDate, remark);
                        }
                    }
                }

                // 月经提醒
                if (pregRemind == 1) {

                }

                // 疫苗提醒
                if (ymRemind == 1) {
                    //MsgUtil.sendYmMsg(openid, msg, stage, endDate, remark);
                }

            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
