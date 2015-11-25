package beans;


import java.util.Date;
/**
 * 接口数据
 *
 * @author cater
 * @since 11/24/15
 */
public class VacNotificationBean {
	/**
     * BabyID
     */
	public String babyId;
	
	/**
     * baby姓名
     */
	public String bName;
	
	/**
     * baby疫苗ID
     */
	public String vacId;
	
	/**
     * 疫苗名称
     */
	public String vacName;

	/**
     * 次数time
     */
	public String time;


	/**
     * baby岁数
     */
	public String babyAgeStr;

	/**
     * 提醒时间
     */
	public Date remindTime;        //  b.id,b.name, bv.vacId,v.name,v.time,v.ageDcb,bv.remindTime

	/**
     * 预计接种时间
     */
	public Date etmDate;
	
	public VacNotificationBean(String babyId,String bName, String vacId,String vacName,String time,String babyAgeStr,Date remindTime,Date etmDate){
		this.babyId = babyId;
		this.bName = bName;
		this.vacId = vacId;
		this.vacName = vacName;
		this.time = time;
		this.babyAgeStr = babyAgeStr;
		this.remindTime = remindTime;
		this.etmDate = etmDate;
	}

	public VacNotificationBean(){}

	public VacNotificationBean(String babyId,String bName,String vacId,Date remindTime){
		this.babyId = babyId;
		this.bName = bName;
		this.vacId = vacId;
		this.remindTime = remindTime;
	}
}