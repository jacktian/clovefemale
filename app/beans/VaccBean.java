package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Baby;
import models.User;
import models.Vaccination;

public class VaccBean {
	/**
     * 父母姓名
     */
	public String userName;
	
	/**
     * babyName
     */
	public String name;
	
	/**
     * 日期
     */
	public Date date;

	/**
     * 接种内容
     */
	public String content;
	/**
	 * 宝宝ID
	 * */
	public String babyId;
	

	/*构建GradeBeanList*/
	public static List<VaccBean> builList(List<Vaccination> list){
		List<VaccBean> beanList = new ArrayList();
		for(int i=0;i<list.size();i++){
			Baby baby = Baby.findById(list.get(i).babyId);
			User user = User.findById(baby.userId);
			VaccBean vBean = new VaccBean();
			vBean.babyId=list.get(i).babyId;
			vBean.userName = user.realName;
			vBean.name = baby.name;
			vBean.date = list.get(i).date;
			vBean.content = list.get(i).content;
			beanList.add(vBean);
		}
		return beanList;
	}
}
