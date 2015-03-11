package beans;

import java.util.ArrayList;
import java.util.List;

import models.MedicineBox;
import models.NoteBook;
import models.User;

public class UserNBbean {
	
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
	public static List<UserNBbean> builList(List<User> userList){
		List<UserNBbean> beanList = new ArrayList();
		for(int i=0;i<userList.size();i++){
			UserNBbean unBean = new UserNBbean();
			unBean.userId = userList.get(i).id;
			unBean.name = userList.get(i).realName;
			List<NoteBook> counts =  MedicineBox.find("select nb from NoteBook nb where userId=?", userList.get(i).id).fetch();
			unBean.count = counts.size();
			beanList.add(unBean);
		}
		return beanList;
	}
	
}
