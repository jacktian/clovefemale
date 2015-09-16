package models;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by boxizen on 15/8/22.
 */
@Entity
@Table(name = "remind")
public class Remind  extends BasicModel {
    /**
     * 	用户openid
     */
    @Column(name = "openid")
    public String openid;

    /**
     * 	药箱提醒
     */
    @Column(name = "medremind")
    public int medremind;
    
    /**
     * 	药箱提醒-提醒天数
     */
    @Column(name = "med_adv_day")
    public int med_adv_day;

    /**
     * 	生理提醒
     */
    @Column(name = "healremind")
    public int healremind;
    
    /**
     * 	生理提醒-提醒天数
     */
    @Column(name = "heal_adv_day")
    public int heal_adv_day;


    /**
     * 	疫苗提醒
     */
    @Column(name = "yiremind")
    public int yiremind;
    
    /**
     * 	疫苗提醒-提醒天数
     */
    @Column(name = "yi_adv_day")
    public int yi_adv_day;
}
