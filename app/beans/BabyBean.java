package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Baby;
import models.User;

/**
 * 接口数据
 *
 * @author boxiZen
 * @since 12/16/14
 */
public class BabyBean {
	/**
     * 父母ID
     */
	public String pId;
	
	/**
     * 父母姓名
     */
	public String pName;
	
	/**
     * babyID
     */
	public String id;
	
	/**
     * 出生日期
     */
	public Date date;

	/**
     * 性别
     */
	public String sex;

	/**
     * 姓名
     */
	public String name;
	
	/*构建babyBeanList*/
	public static List<BabyBean> builList(List<Baby> babyList){
		List<BabyBean> beanList = new ArrayList();
		for(int i=0;i<babyList.size();i++){
			User user = User.findById(babyList.get(i).pId);
			BabyBean babyBean = new BabyBean();
			babyBean.id = babyList.get(i).id;
			babyBean.pId = babyList.get(i).pId;
			babyBean.name = babyList.get(i).name;
			babyBean.sex = babyList.get(i).sex;
			babyBean.pName = user.userName;
			babyBean.date = babyList.get(i).date;
			beanList.add(babyBean);
		}
		return beanList;
	}
}