package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Baby;
import models.GradeForm;
import models.User;

public class GradeBean {
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
		public String grade;
		/**
	     * 科目
	     */
		public String subject;
		
		/**
	     * 成绩
	     */
		public double mark;
		
		/*构建GradeBeanList*/
		public static List<GradeBean> builList(List<GradeForm> gradeList){
			List<GradeBean> beanList = new ArrayList();
			for(int i=0;i<gradeList.size();i++){
				Baby baby = Baby.findById(gradeList.get(i).babyId);
				User user = User.findById(baby.userId);
				GradeBean gradeBean = new GradeBean();
				gradeBean.userName = user.realName;
				gradeBean.name = baby.name;
				gradeBean.date = gradeList.get(i).date;
				gradeBean.grade = gradeList.get(i).grade;
				gradeBean.subject = gradeList.get(i).subject;
				gradeBean.mark = gradeList.get(i).mark;
				beanList.add(gradeBean);
			}
			return beanList;
		}
		

}
