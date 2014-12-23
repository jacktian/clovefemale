package controllers;

import java.util.ArrayList;
import java.util.List;

import org.ietf.jgss.Oid;

import models.Baby;
import models.BodyIndex;
import models.GradeCondition;
import models.Vaccination;
import play.mvc.Controller;

public class BabyGrowth extends WebService {
	/*
	 * 查询所有的疫苗接种记录
	 * 参数：无
	 * 返回：满足条件的列表
	 * */
	public static void listVaccinations() {
		List<Vaccination> vacclist = Vaccination.findAll();
		wsOk(vacclist);
	}
	
	/*
	 * 查询所有的成绩表记录
	 * 参数：无
	 * 返回：满足条件的列表
	 * */
	public static void listGradeConditions() {
		List<GradeCondition> gradelist = GradeCondition.findAll();
		wsOk(gradelist);
	}
	/*
	 * 查询所有的身体指标记录
	 * 参数：无
	 * 返回：满足条件的列表
	 * */
	public static void listBodyIndexs() {
		List<BodyIndex> indexlist = BodyIndex.findAll();
		wsOk(indexlist);
	}
	/*
	 * 新增疫苗接种记录
	 * 参数：前台传回的实体类，小孩id
	 * 返回：全部列表
	 * */
	public static void addVaccination(Vaccination v, String babyId) {
		Vaccination vacc = v.save();
//		Vaccination.createBtoV(babyId, vacc.id);
		findVaccByBaby(babyId);
	}
	/*
	 * 新增成绩表
	 * 参数：前台传回的实体类，小孩id
	 * 返回：全部列表
	 * */
	public static void addGradeCondition(GradeCondition grade, String babyId) {
		GradeCondition g = grade.save();
		/*GradeCondition.createBtoG(babyId, g.id);*/
		findGradeByBaby(babyId);
	}
	/*
	 * 新增身体指标记录
	 * 参数：前台传回的实体类，小孩id
	 * 返回：全部列表
	 * */
	public static void addBodyIndex(BodyIndex bodyIndex, String babyId) {
		BodyIndex b = bodyIndex.save();
		/*BodyIndex.createBtoB(babyId, b.id);*/
		findbodyByBaby(babyId);
	}
	/*
	 * 根据科目名称查询对应的成绩
	 * 参数：科目名称,小孩id
	 * 返回：对应的成绩单
	 * */
	public static void findBySubject(String subject,String babyId) {
		List<GradeCondition> list = GradeCondition
				.find("select * from GradeCondition c where c.babyId = ?  and c.subject= ?order by date",babyId,
						subject).fetch();
		/*List<GradeCondition> gradeList = findGradeByBaby2(babyId);
		for(GradeCondition c : list){
			if(!gradeList.contains(c)){
				list.remove(c);
			}
		}*/
		wsOk(list);
	}

	/*
	 * 身高按日期排序
	 * 参数：小孩id
	 * 返回：对应的列表
	 * */
	public static void findByHeight(String babyId) {
		List<BodyIndex> list = GradeCondition.find("babyId = ? order by date",babyId).fetch();
		
		/*List<BodyIndex> bodyList = findbodyByBaby2(babyId);
		for(BodyIndex b : list){
			if(!bodyList.contains(b)){
				list.remove(b);
			}
		}*/
		
		wsOk(list);
	}
	/*
	 * 根据id查询对应的身体指标
	 * 参数：id
	 * 返回：对应的身体指标
	 * */
	public static void editBodyIndex(Long id) {
		BodyIndex bodyIndex = BodyIndex.findById(id);
		wsOk(bodyIndex);
	}
	/*
	 * 根据id查询对应的成绩单
	 * 参数：id
	 * 返回：对应的成绩单
	 * */
	public static void editGradeCondition(Long id) {
		GradeCondition gradeCondition = GradeCondition.findById(id);
		wsOk(gradeCondition);
	}
	/*
	 * 根据id查询对应的疫苗接种记录
	 * 参数：id
	 * 返回：对应的记录
	 * */
	public static void editVaccination(Long id) {
		Vaccination vaccination = Vaccination.findById(id);
		wsOk(vaccination);
	}
	/*
	 * 更新身体指标
	 * 参数：bodyIndex类
	 * 返回：全部列表
	 * */
	public static void updateBodyIndex(BodyIndex bodyIndex,String babyId) {
		bodyIndex.save();
		findbodyByBaby(babyId);
	}
	/*
	 * 更新成绩表
	 * 参数：GradeCondition类
	 * 返回：全部列表
	 * */
	public static void updateGradeCondition(GradeCondition gradeCondition,String babyId) {
		gradeCondition.save();
		findGradeByBaby(babyId);
	}
	/*
	 * 更新疫苗接种记录
	 * 参数：vacc类
	 * 返回：全部列表
	 * */
	public static void updateVacci(Vaccination vaccination,String babyId) {
		vaccination.save();
		findVaccByBaby(babyId);
	}
	/*
	 * 根据id删除身体指标
	 * 参数：ID
	 * 返回：全部列表
	 * */
	public static void deleteBodyIndex(String id,String babyId) {

		/*BabyToBody.delete("bodyId = ?", id);*/
		BodyIndex.delete("id = ?", id);
		findbodyByBaby(babyId);
		
		
	}
	/*
	 * 根据id删除成绩单
	 * 参数：ID
	 * 返回：全部列表
	 * */
	public static void deleteGradeCondition(String id,String babyId) {

		/*BabyToGrade.delete("gradeId", id);*/
		GradeCondition.delete("id = ?", id);
		findGradeByBaby(babyId);
	}
	/*
	 * 根据id删除疫苗接种记录
	 * 参数：ID
	 * 返回：全部列表
	 * */
	public static void deleteVacci(String id,String babyId) {

		/*BabyToVacci.delete("vacciId", id);*/
		Vaccination.delete("id = ?", id);
		findVaccByBaby(babyId);
	}
	
	/*
	 * 根据babyid查询所有的身体指标
	 * 参数：babyID
	 * 返回：满足条件的身体指标列表
	 * */
	public static void findbodyByBaby(String babyId){
		/*List<BabyToBody> list = BabyToBody.findByBaby(babyId);
		List<BodyIndex> bodyList = new ArrayList();
		for(BabyToBody babyToBody:list){
			System.out.println(userBaby.babyId);
			BodyIndex body =BodyIndex.findById(babyToBody.bodyId);
			bodyList.add(body);
		}*/
		List<BodyIndex> bodyList = BodyIndex.find("select b from BodyIndex b where b.babyId = ? ", babyId).fetch();
		wsOk(bodyList);
	}
	
	public static List<BodyIndex> findbodyByBaby2(String babyId){
		/*List<BabyToBody> list = BabyToBody.findByBaby(babyId);
		List<BodyIndex> bodyList = new ArrayList();
		for(BabyToBody babyToBody:list){
			System.out.println(userBaby.babyId);
			BodyIndex body =BodyIndex.findById(babyToBody.bodyId);
			bodyList.add(body);
		}*/
		List<BodyIndex> bodyList = BodyIndex.find("select b from BodyIndex b where b.babyId = ? ", babyId).fetch();
		return bodyList;
	}
	/*
	 * 根据babyid查询所有的成绩表
	 * 参数：babyID
	 * 返回：满足条件的成绩列表
	 * */
	public static void findGradeByBaby(String babyId){
		/*List<BabyToGrade> list = BabyToGrade.findByBaby(babyId);
		List<GradeCondition> gradeList = new ArrayList<>();
		for(BabyToGrade babyToGrade : list){
			GradeCondition grade = GradeCondition.findById(babyToGrade.gradeId);
			gradeList.add(grade);
		}*/
		List<GradeCondition> gradeList = GradeCondition.find("select gc from GradeCondition gc where gc.babyId = ? ", babyId).fetch();
		wsOk(gradeList);
	}
	
	public static List<GradeCondition> findGradeByBaby2(String babyId){
		/*List<BabyToGrade> list = BabyToGrade.findByBaby(babyId);
		List<GradeCondition> gradeList = new ArrayList<>();
		for(BabyToGrade babyToGrade : list){
			GradeCondition grade = GradeCondition.findById(babyToGrade.gradeId);
			gradeList.add(grade);
		}*/
		List<GradeCondition> gradeList = GradeCondition.find("select gc from GradeCondition gc where gc.babyId = ? ", babyId).fetch();
		return gradeList;
	}
	
	/*
	 * 根据babyid查询所有的疫苗接种记录
	 * 参数：babyID
	 * 返回：满足条件的疫苗接种记录
	 * */
	public static void findVaccByBaby(String babyId){
		/*List<BabyToVacci> list = BabyToVacci.findByBaby(babyId);
		List<Vaccination>  vaccinations = new ArrayList<>();
		for(BabyToVacci babyToVacci : list){
			Vaccination vacc= Vaccination.findById(babyToVacci.vacciId);
			vaccinations.add(vacc);
		}*/
		List<Vaccination> vaccinations = Vaccination.find("select v from Vaccination v where v.babyId = ? ", babyId).fetch();
		wsOk(vaccinations);
	}
	
}
