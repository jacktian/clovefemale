package jobs;

import models.Medicine;
import models.Remind;
import play.Play;
import play.jobs.Every;
import play.jobs.Job;

import play.db.jpa.JPA;
import javax.persistence.Query;

import java.util.List;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;


import utils.*;

import beans.VacNotificationBean;

/**
 * Created by boxizen on 15/8/22.
 */

@Every("24h")
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

                    String queryString = "select new beans.VacNotificationBean(b.id,b.name, bv.vacId,v.name,v.time,v.ageDcb,bv.remindTime,bv.etmDate)"
                            +" from Baby as b, BabyVac as bv, Vaccine as v where b.userId=?1 and b.id = bv.babyId and bv.vacId = v.id and datediff(bv.remindTime, current_date() ) = 0";//and bv.remindTime= ";//今天
                    List<VacNotificationBean> vacList =  null;
                    
                    Query query = JPA.em().createQuery(queryString);
                    query.setParameter(1, openid);//给编号为1的参数设值 
                    vacList  = query.getResultList();
                    if(vacList.size() != 0){
                        HashMap vacRemindMap = new HashMap();
                        VacNotificationBean babyVacNotification = new VacNotificationBean();
                        for(int j =0; j < vacList.size(); j++){
                            Date remindTime = vacList.get(j).remindTime;
                            
                            if(vacRemindMap.containsKey(vacList.get(j).babyId)){
                                babyVacNotification = (VacNotificationBean)vacRemindMap.get(vacList.get(j).babyId);
                                babyVacNotification.vacName = babyVacNotification.vacName +"、"+vacList.get(j).vacName;
                                vacRemindMap.put(vacList.get(j).babyId,babyVacNotification);
                            }else{
                                vacRemindMap.put(vacList.get(j).babyId,vacList.get(j));
                            }
                              
                        }
                        Iterator iter = vacRemindMap.entrySet().iterator();
                        while (iter.hasNext()) {
                            Map.Entry entry = (Map.Entry) iter.next();
                            VacNotificationBean notification = (VacNotificationBean)entry.getValue();
                            String msg = notification.bName+"该打疫苗了";  //宝宝＊＊该打疫苗了
                            String stage =notification.babyAgeStr;//1岁1个月
                            String endDate = notification.vacName;//A群流脑疫苗(第二针)
                            String remark ="请及时接种疫苗";//
                            MsgUtil.sendYmMsg(openid, msg, stage, endDate, remark);
                        }

                    }

                }

            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
