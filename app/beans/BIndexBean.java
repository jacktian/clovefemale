package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Baby;
import models.BodyIndex;
import models.User;

public class BIndexBean {
	/**
	 * 接口数据
	 *
	 * @author Yingpeng
	 * @since 17/01/15
	 */
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
	     * 年级
	     */
		public double weight;
		/**
	     * 科目
	     */
		public double height;
		/**
		 * 宝宝ID
		 * */
		public String babyId;
		
	
		/*构建GradeBeanList*/
		public static List<BIndexBean> builList(List<BodyIndex> list){
			List<BIndexBean> beanList = new ArrayList();
			for(int i=0;i<list.size();i++){
				Baby baby = Baby.findById(list.get(i).babyId);
				User user = User.findById(baby.userId);
				BIndexBean bBean = new BIndexBean();
				bBean.babyId=list.get(i).babyId;
				bBean.userName = user.realName;
				bBean.name = baby.name;
				bBean.date = list.get(i).date;
				bBean.weight = list.get(i).weight;
				bBean.height = list.get(i).height;
				beanList.add(bBean);
			}
			return beanList;
		}
		
}
