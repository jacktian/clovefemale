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

        System.out.println("debug");

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
                        System.out.println("medicine name:" + mList.get(m).name);

                        Date dead = mList.get(m).deadline;
                        System.out.println("now:" + now);
                        System.out.println("dead:" + dead);
                        System.out.println(now.compareTo(dead));

                        if (now.compareTo(dead) == -1) {
                            System.out.println("medicine inside" + mList.get(m).name);
                            // 设置消息内容
                            String msg = "您的姨妈要来咯";
                            String remark = "请好好注意身体哦";
                            String name = mList.get(m).name;
                            String endDate = StringUtils.formatDate_yyyy_MM_dd(mList.get(m).deadline);
                            MsgUtil.sendMedMsg(openid, msg, name, endDate, remark);
                        }
                    }
                }

                // 月经提醒
                if (pregRemind == 1) {


                }


            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
