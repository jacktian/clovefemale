package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Medicine;
import models.MedicineBox;
/**
 * 接口数据
 *
 * @author YingpengYan
 * @since 12/16/14
 */
public class MBean {
	/**
	 * 药箱id
	 * */
	public String id;
	/**
     * 父母ID
     */
	public String userId;
	
	/**
     * 父母姓名
     */
	/*public String userName;*/
	
	/**
     * 药箱名称
     */
	public String name;
	/**
	 * 药物数量
	 * */
	public int count;
	/**
	 * 创建日期
	 * */
	public Date date;
	/**
	 * 说明
	 * */
	public String info;
	/*构建babyBeanList*/
	public static List<MBean> builList(List<MedicineBox> mList){
		List<MBean> beanList = new ArrayList();
		for(int i=0;i<mList.size();i++){
			MBean mBean = new MBean();
			mBean.userId = mList.get(i).userId;
			mBean.name = mList.get(i).name;
			mBean.date = mList.get(i).createDate;
			mBean.id =mList.get(i).id;
			mBean.info=mList.get(i).mark;
			List<Medicine> counts =  Medicine.find("select m from Medicine m where medicineBoxId=?", mList.get(i).id).fetch();
			mBean.count = counts.size();
			beanList.add(mBean);
		}
		return beanList;
	}
}
