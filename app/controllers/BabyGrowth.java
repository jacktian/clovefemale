package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.BodyIndex;
import models.GradeForm;
import models.Vaccination;
import beans.BIndexBean;
import beans.GradeBean;

/**
 * 宝宝成长controller
 * @author Yingpeng
 * @since 03/05/15
 * */
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
				 map.put("height",bodyIndex.height+"");
				 heightList.add(map);
			 }
		 }
		}
		wsOk(heightList);
		}
	
	
	
    
	/* 查询一段时间内的孩子分数数据
	 * 参数：开始时间，结束时间，科目,孩子id
	 * 返回：时间+分数的数据
	 * */
    public static void getGradeDataByDate(String sDate,String eDate,String subject,String babyId){
		
    	List<Map<String,String>> gradeList = new ArrayList();
		Date startDate=null;
		Date endDate=null;
		List<GradeForm> list=null;
		try{
		 startDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate+" 00:00:00");
		 endDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(eDate+" 23:59:59");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(startDate.before(endDate)){
			list = GradeForm.find("select g from GradeCondition g where babyId = ? and subject = ? and date between ? and ? order by date", babyId,subject,startDate,endDate).fetch();
		
			if(list !=null){
				 for(GradeForm g :list){
					 Map<String, String> map  = new HashMap<String, String>();
					 map.put("date", g.date.toString());
					 map.put("grade",String.valueOf(g.mark));//已改
					 gradeList.add(map);
				 }
			 }
		}
		wsOk(gradeList);
   	}
	
    /**
     * 根据科目和成绩区间查询符合条件的宝宝情况（首次查询跳转，不包括页数跳转）
     */
	
    public static void selectBabyByGrade(String subject,int gradeInterval,int curpage){
    	System.out.println("-----fenshuduan"+":"+gradeInterval+"  curpage:"+curpage);
    	List<GradeForm> list=new ArrayList();
    	List<GradeForm> gradelist=null;
    	long count=0;
    	double maxMark=60.0;
    	double minMark=0;
    	long pageNum = 0;//总页数
    	
    	if(gradeInterval>0){
    		if(gradeInterval==1){
    			minMark=0;
    			maxMark=60.0;
    		}else {
    			minMark =60.0 +(gradeInterval-2)*10;
    			maxMark = 60.0 +(gradeInterval-1)*10;
    		}
    		
    		gradelist = GradeForm.find("select g from GradeCondition g where subject = ? and mark >= ? and mark < ?", subject,minMark,maxMark).fetch();
    	}else{
    		gradelist =GradeForm.find("select g from GradeCondition g where subject = ?", subject).fetch();
    	}
    	if(gradelist!=null){
    	 count= gradelist.size();//计算总记录条数，以2条一页为基准
    	}
    	if(count%2!=0)
    		pageNum = count/2+1;
    	else
    		pageNum = count/2;
    	
    	if(gradeInterval > 0){
    		gradelist = GradeForm.find("select g from GradeCondition g where subject = ? and mark >= ? and mark < ?", subject,minMark,maxMark).fetch(curpage,2);
    	}else{
    		gradelist =GradeForm.find("select g from GradeCondition g where subject = ?", subject).fetch(curpage,2);
    	}
    	 
    		System.out.println("-----size"+":"+gradelist.size()+"--count:"+count);
    		System.out.println("-----pagenum"+":"+pageNum);
    	
    	List<GradeBean> listbean =null;
    	if(gradelist!=null){
    		 listbean=GradeBean.builList(gradelist);
    	}
    	render("/RecordMgm/findBabyByGrade.html",listbean,pageNum,curpage,subject,gradeInterval);
        
    	
    }
    
    /**
     * 根据宝宝名称和科目查询个体成绩
     */
    public static void findMarkByBaby(String babyName,String subject,int curpage){
    	List<GradeForm> gradelistAll=GradeForm.find("select g from GradeCondition g where babyId in (select id from Baby where name = ?) and subject = ?", babyName,subject).fetch();
    	List<GradeForm> gradelist=GradeForm.find("select g from GradeCondition g where babyId in (select id from Baby where name = ?) and subject = ?", babyName,subject).fetch(curpage,2);
    	List<GradeBean> listbean = null;
    	long pageNum = 0;//总页数
    	long count = 0;
    	if(gradelist!=null){
    	count=gradelistAll.size();//计算总记录条数，以2条一页为基准
    	if(count%2!=0)
    		pageNum = count/2+1;
    	else
    		pageNum = count/2;
    	
    	listbean=GradeBean.builList(gradelist);
    	}
    	
    	render("/RecordMgm/findGradeByBaby.html",listbean,pageNum,curpage,subject,babyName);
    }
    
    
	/* 查询一段时间内的孩子学科不同分数区间的占比
	 * 参数：开始时间，结束时间，孩子id，科目
	 * 返回：分数区间+次数
	 * */
    public static void getMarkDataBysubject(String sDate,String eDate,String subject,String babyId){
		
    	List<Map<String,String>> gradeList = new ArrayList();
		Date startDate=null;
		Date endDate=null;
		List<GradeForm> list=null;
		int[] a =new int[5];
		try{
		 startDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate+" 00:00:00");
		 endDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(eDate+" 23:59:59");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(startDate.before(endDate)){
			list = GradeForm.find("select g from GradeCondition g where babyId = ? and subject = ? and date between ? and ? order by date", babyId,subject,startDate,endDate).fetch();
		
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
    
    public static int[] countMarkNum(List<GradeForm> list){
    	int[] a =new int[5];
    	for(int i=0;i<5;i++){
    		a[i]=0;
    	}
    	
    	for(GradeForm g :list){
    		
    		try{
    			/*double mark=(Double.parseDouble(g.mark));*/
    			double mark=g.mark;
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
		List<GradeForm> gradelist = GradeForm.findAll();
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
	public static void addGradeCondition(GradeForm grade, String babyId) {
		GradeForm g = grade.save();
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
		List<GradeForm> list = GradeForm
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
	 * 根据孩子id查询孩子的身体指标
	 * 参数：小孩id
	 * 返回：对应的列表
	 * */
	public static void findBI(String babyId,int curpage) {
		List<BodyIndex> list = BodyIndex.find("babyId = ? order by date",babyId).fetch(curpage,2);
		List<BodyIndex> listAll = BodyIndex.find("babyId = ? order by date",babyId).fetch();
		List<BIndexBean> listbean = null;
		long pageNum = 0;//总页数
    	long count = 0;
    	if(list!=null && listAll!=null){
       	count=listAll.size();//计算总记录条数，以2条一页为基准
       	if(count%2!=0)
    		pageNum = count/2+1;
    	else
    		pageNum = count/2;
       	
    		listbean=BIndexBean.builList(list);
        	}
		
    	render("/RecordMgm/bodyIndex.html",listbean,pageNum,curpage,babyId);
	}
	
	/**
	 * 根据宝宝姓名和查询日期区间查询宝宝身体指标
	 * 参数：宝宝姓名
	 * 返回：身体指标列表
	 * 
	 * */
	public static void findBIByBaby(String name, String sDate, String eDate,
			int curpage) {
		Date startDate = null;
		Date endDate = null;
		List<BIndexBean> listbean = null;
		long pageNum = 0;// 总页数
		long count = 0;

		if (sDate != "" && eDate != "") {

			try {
				startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(sDate + " 00:00:00");
				endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(eDate + " 23:59:59");
			} catch (Exception e) {
				e.printStackTrace();
			}

			// string要用equal比較
			if (startDate.before(endDate) && !name.equals("")) {

				List<BodyIndex> listAll = BodyIndex
						.find("select b from BodyIndex b where b.babyId in (select id from Baby where name = ? ) and b.date between ? and ?",
								name, startDate, endDate).fetch();
				List<BodyIndex> list = BodyIndex
						.find("select b from BodyIndex b where b.babyId in (select id from Baby where name = ? ) and b.date between ? and ?",
								name, startDate, endDate).fetch(curpage, 2);

				if (list != null && listAll != null) {
					count = listAll.size();// 计算总记录条数，以2条一页为基准
					if (count % 2 != 0)
						pageNum = count / 2 + 1;
					else
						pageNum = count / 2;

					listbean = BIndexBean.builList(list);
				}

			} else {
				if (startDate.before(endDate)) {

					List<BodyIndex> listAll = BodyIndex
							.find("select b from BodyIndex b where  b.date between ? and ?",
									startDate, endDate).fetch();
					System.out.println("5757575775" + listAll.size());
					List<BodyIndex> list = BodyIndex
							.find("select b from BodyIndex b where  b.date between ? and ?",
									startDate, endDate).fetch(curpage, 2);

					if (list != null && listAll != null) {
						count = listAll.size();// 计算总记录条数，以2条一页为基准
						if (count % 2 != 0)
							pageNum = count / 2 + 1;
						else
							pageNum = count / 2;

						listbean = BIndexBean.builList(list);
					}
				}

			}
		}

		render("/RecordMgm/findBIByBaby.html", listbean, pageNum, curpage,
				name, sDate, eDate);

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
		GradeForm gradeCondition = GradeForm.findById(id);
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
	public static void updateGradeCondition(GradeForm gradeCondition,String babyId) {
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
		GradeForm.delete("id = ?", id);
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
		/*List<BodyIndex> bodyList = findbodyByBaby2(babyId);
		for(BodyIndex b : list){
			if(!bodyList.contains(b)){
				list.remove(b);
			}
		}*/
		List<BodyIndex> bodyList = BodyIndex.find("select b from BodyIndex b where b.babyId = ? order by date", babyId).fetch();
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
		List<GradeForm> gradeList = GradeForm.find("select gc from GradeCondition gc where gc.babyId = ? ", babyId).fetch();
		wsOk(gradeList);
	}
	
	public static List<GradeForm> findGradeByBaby2(String babyId){
		/*List<BabyToGrade> list = BabyToGrade.findByBaby(babyId);
		List<GradeCondition> gradeList = new ArrayList<>();
		for(BabyToGrade babyToGrade : list){
			GradeCondition grade = GradeCondition.findById(babyToGrade.gradeId);
			gradeList.add(grade);
		}*/
		List<GradeForm> gradeList = GradeForm.find("select gc from GradeCondition gc where gc.babyId = ? ", babyId).fetch();
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
