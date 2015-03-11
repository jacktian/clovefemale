package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Baby;
import models.MedicineBox;
import models.User;
/**
 * 接口数据
 *
 * @author YingpengYan
 * @since 12/16/14
 */
public class userMBoxBean {
		/**
	     * 父母ID
	     */
		public String userId;
		
		/**
	     * 父母姓名
	     */
		public String name;
		
		/**
	     * 药箱个数
	     */
		public int count;
		
		/*构建babyBeanList*/
		public static List<userMBoxBean> builList(List<User> userList){
			List<userMBoxBean> beanList = new ArrayList();
			for(int i=0;i<userList.size();i++){
				userMBoxBean umBean = new userMBoxBean();
				umBean.userId = userList.get(i).id;
				umBean.name = userList.get(i).realName;
				List<MedicineBox> counts =  MedicineBox.find("select m from MedicineBox m where userId=?", userList.get(i).id).fetch();
				umBean.count = counts.size();
				beanList.add(umBean);
			}
			return beanList;
		}
}
