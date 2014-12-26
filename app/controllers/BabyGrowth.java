package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.accessibility.internal.resources.accessibility;

import models.BodyIndex;
import models.FetalMovement;
import models.GestationalWeight;
import models.GradeCondition;
import models.Temperature;
import models.Vaccination;


public class BabyGrowth extends WebService {

	/* 查询一段时间内的孩子身高数据
	 * 参数：开始时间，结束时间，孩子id
	 * 返回：时间+身高的数据
	 * */
	public static void getHeightDataByDate(String sDate,String eDate,String babyId){
		List<Map<String,String>> heightList = new ArrayList();
		Date startDate=null;
		Date endDate=null;
		List<BodyIndex> list=null;
		try{
		 startDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate+" 00:00:00");
		 endDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(eDate+" 23:59:59");
		}catch(Exception e){
			e.printStackTrace();
		}
		if(startDate.before(endDate)){
		 list = BodyIndex.find("select b from BodyIndex b where babyId = ? and date between ? and ? order by date", babyId,startDate,endDate).fetch();
		 if(list !=null){
			 for(BodyIndex bodyIndex :list){
				 Map<String, String> map  = new HashMap<String, String>();
				 map.put("date", bodyIndex.date.toString());
				 map.put("height",bodyIndex.height.toString());
				 heightList.add(map);
			 }
		 }
		}
		wsOk(heightList);
		}
	
	/* 查询一段时间内的孩子体温数据
	 * 参数：开始时间，结束时间，孩子id
	 * 返回：时间+体温的数据
	 * */
    public static void getTemprDataByDate(String sDate,String eDate,String userId){
		
    	List<Map<String,String>> temprList = new ArrayList();
		Date startDate=null;
		Date endDate=null;
		List<Temperature> list=null;
		try{
		 startDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate+" 00:00:00");
		 endDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(eDate+" 23:59:59");
		}catch(Exception e){
			e.printStackTrace();
		}
		if(startDate.before(endDate)){
		 list = Temperature.find("select t from Temperature t where userId = ? and tDate between ? and ? order by tDate", userId,startDate,endDate).fetch();
		 if(list !=null){
			 for(Temperature temperature :list){
				 Map<String, String> map  = new HashMap<String, String>();
				 map.put("date", temperature.tDate.toString());
				 map.put("temp",new Float(temperature.tValue).toString());
				 temprList.add(map);
			 }
		 }
		}
		wsOk(temprList);
		
		
	}
	
	/* 查询一段时间内的孩子分数数据
	 * 参数：开始时间，结束时间，科目,孩子id
	 * 返回：时间+分数的数据
	 * */
    public static void getGradeDataByDate(String sDate,String eDate,String subject,String babyId){
		
    	List<Map<String,String>> gradeList = new ArrayList();
		Date startDate=null;
		Date endDate=null;
		List<GradeCondition> list=null;
		try{
		 startDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate+" 00:00:00");
		 endDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(eDate+" 23:59:59");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(startDate.before(endDate)){
			list = GradeCondition.find("select g from GradeCondition g where babyId = ? and subject = ? and date between ? and ? order by date", babyId,subject,startDate,endDate).fetch();
		
			if(list !=null){
				 for(GradeCondition g :list){
					 Map<String, String> map  = new HashMap<String, String>();
					 map.put("date", g.date.toString());
					 map.put("grade",g.mark);
					 gradeList.add(map);
				 }
			 }
		}
		wsOk(gradeList);
   	}
	/* 查询一段时间内的孩子孕重数据
	 * 参数：开始时间，结束时间，孩子id
	 * 返回：时间+孕重的数据
	 * */
    public static void getWeightDataByDate(String sDate,String eDate,String userId){
		
    	List<Map<String,String>> weightList = new ArrayList();
		Date startDate=null;
		Date endDate=null;
		List<GestationalWeight> list=null;
		try{
		 startDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate+" 00:00:00");
		 endDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(eDate+" 23:59:59");
		}catch(Exception e){
			e.printStackTrace();
		}
		if(startDate.before(endDate)){
		 list = GestationalWeight.find("select w from GestationalWeight w where userId = ? and wDate between ? and ? order by wDate", userId,startDate,endDate).fetch();
		 if(list !=null){
			 for(GestationalWeight w :list){
				 Map<String, String> map  = new HashMap<String, String>();
				 map.put("date", w.wDate.toString());
				 map.put("temp",new Float(w.wValue).toString());
				 weightList.add(map);
			 }
		 }
		}
		wsOk(weightList);
   	}
	/* 查询一段时间内的孩子胎动数据
	 * 参数：开始时间，结束时间，用户id
	 * 返回：时间+胎动的数据
	 * */
    public static void getMovementDataByDate(String sDate,String eDate,String userId){
		
    	List<Map<String,String>> movementList = new ArrayList();
		Date startDate=null;
		Date endDate=null;
		List<FetalMovement> list=null;
		try{
		 startDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate+" 00:00:00");
		 endDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(eDate+" 23:59:59");
		}catch(Exception e){
			e.printStackTrace();
		}
		if(startDate.before(endDate)){
		 list = FetalMovement.find("select m from FetalMovement m where userId = ? and fDate between ? and ? order by fDate", userId,startDate,endDate).fetch();
		 if(list !=null){
			 for(FetalMovement m :list){
				 Map<String, String> map  = new HashMap<String, String>();
				 map.put("date", m.fDate.toString());
				 map.put("temp",new Integer(m.num).toString());
				 movementList.add(map);
			 }
		 }
		}
		wsOk(movementList);
		
   	}
	/* 查询一段时间内的孩子学科不同分数区间的占比
	 * 参数：开始时间，结束时间，孩子id，科目
	 * 返回：分数区间+次数
	 * */
    public static void getMarkDataBysubject(String sDate,String eDate,String subject,String babyId){
		
    	List<Map<String,String>> gradeList = new ArrayList();
		Date startDate=null;
		Date endDate=null;
		List<GradeCondition> list=null;
		int[] a =new int[5];
		try{
		 startDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate+" 00:00:00");
		 endDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(eDate+" 23:59:59");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(startDate.before(endDate)){
			list = GradeCondition.find("select g from GradeCondition g where babyId = ? and subject = ? and date between ? and ? order by date", babyId,subject,startDate,endDate).fetch();
		
			if(list !=null){
				a=countMarkNum(list);
				for(int i=0;i<5;i++){
					Map<String, String> map = new HashMap<String,String>();
					if(i==0){
					map.put("range", "60以下");
					map.put("time", new Integer(a[i]).toString());
					gradeList.add(map);
					}else{
						map.put("range", (60+(i-1)*10)+"-"+(60+i*10));
						map.put("time",new Integer(a[i]).toString());
						gradeList.add(map);
					}
				}
			 }
		}
		wsOk(gradeList);
		
   	}
    
    public static int[] countMarkNum(List<GradeCondition> list){
    	int[] a =new int[5];
    	for(int i=0;i<5;i++){
    		a[i]=0;
    	}
    	
    	for(GradeCondition g :list){
    		
    		try{
    			double mark=(Double.parseDouble(g.mark));
    		if(mark<60.0){
    		  a[0]++;
    		}else if (mark>=60.0 && mark <70.0) {
				a[1]++;
			}else if (mark>=70.0 && mark <80.0) {
				a[2]++;
			}else if (mark>=80.0 && mark <90.0) {
				a[3]++;
			}else{
				a[4]++;
			}
    		
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
    		
    	}
    	
    	return a;
    }
	/* 查询一段时间内的孕妇不同体温区间的占比
	 * 参数：开始时间，结束时间，孕妇id，
	 * 返回：分数区间+次数
	 * */
    public static void getWomentprByDate(String sDate,String eDate,String userId){
		
    	List<Map<String,String>> temprList = new ArrayList();
		Date startDate=null;
		Date endDate=null;
		List<Temperature> list=null;
		int[] a =new int[5];
		try{
		 startDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate+" 00:00:00");
		 endDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(eDate+" 23:59:59");
		}catch(Exception e){
			e.printStackTrace();
		}
		if(startDate.before(endDate)){
		 list = Temperature.find("select t from Temperature t where userId = ? and tDate between ? and ? order by tDate", userId,startDate,endDate).fetch();
		 if(list !=null){
			 a=countTempNum(list);
			 for(int i=0;i<5;i++){
				 Map<String, String> map  = new HashMap<String, String>();
				 map.put("range", (36+i)+"-"+(36+i+1));
				 map.put("temp",new Integer(a[i]).toString());
				 temprList.add(map);
			 }
		 }
		}
		wsOk(temprList);
		
		
   	}
    
    public static int[] countTempNum(List<Temperature> list){
    	int[] a =new int[5];
    	for(int i=0;i<5;i++){
    		a[i]=0;
    	}
    	
    	for(Temperature t :list){
    		
    		if(t.tValue>36.0 && t.tValue<37.0){
    		    a[0]++;
    		}else if (t.tValue>=37.0 && t.tValue <38.0) {
				a[1]++;
			}else if (t.tValue>=38.0 && t.tValue <39.0) {
				a[2]++;
			}else if (t.tValue>=39.0 && t.tValue <40.0) {
				a[3]++;
			}else{
				a[4]++;
			}
    		
    		
    	}
    	
    	return a;
    }
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
