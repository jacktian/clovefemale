package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.Baby;
import models.BodyIndex;
import models.FetalMovement;
import models.GestationalWeight;
import models.GradeCondition;
import models.Medicine;
import models.MedicineBox;
import models.Menses;
import models.Note;
import models.NoteBook;
import models.Temperature;
import models.User;
import models.Vaccination;

public class DatabaseUtil {
	public static void init(){
		System.out.println("database init");
		
		/*人员测试数据*/
		for(int i=0;i<50;i++){
			User u = new User();
			u.userName = "user" + i;
			u.realName = "测试者" + i;
			u.passwd = "abcd";
			u.phoneNum = "1598907064" + i;
			u.email = "31596495" + i + "@qq.com";
			u.QQ ="51436219"+i;
			u.weibo ="51436219"+i;
			u.weixin = "51436219"+i;
			u.IDcard = "44138119911024181" + i;
			u.isAddV =false;
			u.cloveId = "123"+i+"321";
			if(i%2==0){
				u.sex = "man";
			}else{
				u.sex="woman";
			}
			u.signName="test";
			u.save();
		}
		User u1 = (User) User.find("byUserName", "user1").fetch().get(0);
		User u2 = (User) User.find("byUserName", "user2").fetch().get(0);
		
		/*宝贝测试数据*/
		for(int i=0;i<11;i++){
			Baby baby = new Baby();
			baby.userId = u1.id;
			baby.name = "baby" + i;
			baby.sex = "male";
			baby.date = new Date();
			baby.save();
		}
    	
		Baby b1 = (Baby)Baby.find("name", "baby2").fetch().get(0);
		Baby b2 = (Baby)Baby.find("name", "baby3").fetch().get(0);
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		/*疫苗数据*/
		try{
			Vaccination vacc = new Vaccination();
			vacc.babyId = b1.id;
			vacc.content ="流感疫苗A";
			vacc.date = sf.parse("2015-1-18 20:17:45");
			vacc.save();
			
			Vaccination vacc2 = new Vaccination();
			vacc2.babyId = b1.id;
			vacc2.content ="流感疫苗A1";
			vacc2.date = sf.parse("2015-1-18 10:17:45");
			vacc2.save();
			
			Vaccination vacc3 = new Vaccination();
			vacc3.babyId = b1.id;
			vacc3.content ="流感疫苗A";
			vacc3.date = sf.parse("2015-1-28 20:17:45");
			vacc3.save();
			
			Vaccination vacc4 = new Vaccination();
			vacc4.babyId = b2.id;
			vacc4.content ="流感疫苗A2";
			vacc4.date = sf.parse("2015-1-18 20:17:45");
			vacc4.save();
			
			Vaccination vacc5 = new Vaccination();
			vacc5.babyId = b2.id;
			vacc5.content ="流感疫苗A3";
			vacc5.date = sf.parse("2015-1-28 20:17:45");
			vacc5.save();
		}catch(Exception e){
			
		}
		
		
		
		/*孕重测试数据*/
		try{
		GestationalWeight gw =new GestationalWeight();
		gw.userId = u1.id;
		gw.wDate = sf.parse("2015-1-10 20:17:45");
		gw.wValue = 56.4f;
		gw.save();
		
		GestationalWeight gw1 =new GestationalWeight();
		gw1.userId = u1.id;
		gw1.wDate = sf.parse("2015-1-11 20:17:45");
		gw1.wValue = 56.4f;
		gw1.save();
		
		
		GestationalWeight gw2 =new GestationalWeight();
		gw2.userId = u1.id;
		gw2.wDate = sf.parse("2015-1-20 20:17:45");
		gw2.wValue = 56.4f;
		gw2.save();
		
		GestationalWeight gw3 =new GestationalWeight();
		gw3.userId = u1.id;
		gw3.wDate = sf.parse("2015-1-15 20:17:45");
		gw3.wValue = 56.4f;
		gw3.save();
		
		GestationalWeight gw4 =new GestationalWeight();
		gw4.userId = u1.id;
		gw4.wDate = sf.parse("2015-1-12 20:17:45");
		gw4.wValue = 56.4f;
		gw4.save();
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		/*胎动测试数据*/
		try{
			FetalMovement fw = new FetalMovement();
			fw.userId = u1.id;
			fw.fDate = sf.parse("2015-1-10 20:17:45");
			fw.num = 80;
			fw.save();
			
			FetalMovement fw1 = new FetalMovement();
			fw1.userId = u1.id;
			fw1.fDate = sf.parse("2015-1-11 20:17:45");
			fw1.num = 85;
			fw1.save();
			
			FetalMovement fw2 = new FetalMovement();
			fw2.userId = u1.id;
			fw2.fDate = sf.parse("2015-1-13 20:17:45");
			fw2.num = 90;
			fw2.save();
			
			
			FetalMovement fw3 = new FetalMovement();
			fw3.userId = u1.id;
			fw3.fDate = sf.parse("2015-1-15 20:17:45");
			fw3.num = 80;
			fw3.save();
			
			
			FetalMovement fw4 = new FetalMovement();
			fw4.userId = u1.id;
			fw4.fDate = sf.parse("2015-1-20 20:17:45");
			fw4.num = 70;
			fw4.save();
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		/*月经测试数据*/
    	Menses menses = new Menses(u1.id, new Date(),"暗红", "多", true, true,"稠");
    	menses.mMeasure="适度";
    	menses.save();
    	Menses menses2 = new Menses(u1.id, new Date(),"鲜红", "少", true, true,"稀");
    	menses2.mMeasure="适度";
    	menses2.save();
    	Menses menses3 = new Menses(u1.id, new Date(),"鲜红", "少", true, true,"稀");
    	menses3.mMeasure="适度";
    	menses3.save();
    	
    	/*体温测试数据*/
    	Temperature temperature1 = new Temperature(u1.id,new Date(), 36.7F);
    	temperature1.save();
    	Temperature temperature2 = new Temperature(u2.id,new Date(), 37.7F);
    	temperature2.save();
    	Temperature temperature3 = new Temperature(u1.id,new Date(), 36.9F);
    	temperature3.save();
    	
    	NoteBook notebook = new NoteBook();
    	notebook.createDate = new Date();
    	notebook.name = "菜谱";
    	notebook.recentMFDate = new Date();
    	notebook.userId = u2.id;
    	notebook.introduceContent="菜谱记录";
    	notebook.save();
    	
    	NoteBook notebook1 = new NoteBook();
    	notebook1.createDate = new Date();
    	notebook1.name = "菜谱1";
    	notebook1.recentMFDate = new Date();
    	notebook1.userId = u2.id;
    	notebook1.introduceContent="菜谱记录1";
    	notebook1.save();
    	
    	NoteBook notebook2 = new NoteBook();
    	notebook2.createDate = new Date();
    	notebook2.name = "菜谱2";
    	notebook2.recentMFDate = new Date();
    	notebook2.userId = u2.id;
    	notebook2.introduceContent="菜谱记录2";
    	notebook2.save();
    	
    	/*菜谱下共5个笔记*/
    	Note note = new Note();
    	note.title = "鱼香茄子煲";
    	note.content = " 主料：紫皮茄子、青红椒、姜、蒜、葱,配料：郫县豆瓣酱、油、盐、淀粉、生抽、老抽、蚝油、醋、糖、麻油、水";
    	note.createDate = new Date();
    	note.recentMFDate = new Date();
    	note.noteBookId = notebook.id;
    	note.save();
    	
    	
    	note = new Note();
    	note.title = "红烧排骨";
    	note.content = " 主料：排骨 (适量) 杏鲍菇 (适量)，调料：干辣椒 (适量) 花椒 (适量) 八角 (适量) 丁香 (适量) 姜片 (适量) 盐 (适量) 老抽 (适量) 生抽 (适量) 料酒 (适量) 冰糖 (适量) 十三香 (适量)";
    	note.createDate = new Date(114,0,12);
    	note.recentMFDate = new Date(114,0,12);
    	note.noteBookId = notebook.id;
    	note.save();
    	
    	note = new Note();
    	note.title = "猪肚鸡汤";
    	note.content = " 材料：猪肚一个，鲜鸡一只（或半只），胡椒一30粒左右（体寒者 要多放，体热者少放）,配料：红枣十五粒，党参四支，玉竹一两，枸杞子适量，八角几粒，香叶两片，桂皮一小段，少许盐、料酒、鸡精、姜";
    	note.createDate = new Date(113,2,13);
    	note.recentMFDate = new Date(113,2,13);
    	note.noteBookId = notebook.id;
    	note.save();
    	
    	note = new Note();
    	note.title = "蜜汁鸡翅";
    	note.content = " 主料：鸡中翅(300克)，配料：葱(2根)姜片(适量)料酒(适量)酱油(适量)蜂蜜(适量)玉米淀粉(适量)柠檬汁(半个柠檬)";
    	note.createDate = new Date(114,12,12);
    	note.recentMFDate = new Date(114,12,12);
    	note.noteBookId = notebook.id;
    	note.save();
    	
    	note = new Note();
    	note.title = "花旗参乌鸡汤";
    	note.content = " 主料：乌鸡 (500克),配料：瘦肉 (100克) 海底椰 (100克) 花旗参 (10克) 虫草花 (10克) 枸杞子 (10克) 盐 (适量)";
    	note.createDate = new Date(113,3,2);
    	note.recentMFDate = new Date(113,3,2);
    	note.noteBookId = notebook.id;
    	note.save();
    	
    	/*孩子学习笔记本，共3个笔记*/
    	notebook = new NoteBook();
    	notebook.createDate = new Date();
    	notebook.name = "孩子学习";
    	notebook.recentMFDate = new Date();
    	notebook.userId = u1.id;
    	notebook.introduceContent="孩子学习笔记";
    	notebook.save();
    	
    	note = new Note();
    	note.title = "数学";
    	note.content = " 数学是一门基础学科，对于我们的广大中学生来说，数学水平的高低，直接影响到物理、化学等学科的学习成绩，数学的重要地位由此可见。 ";
    	note.createDate = new Date(113,2,23);
    	note.recentMFDate = new Date(113,2,23);
    	note.noteBookId = notebook.id;
    	note.save();
    	
    	note = new Note();
    	note.title = "语文";
    	note.content = "1、首兴趣：知而好，好而乐。2、次自主：观千剑，操千曲。3、必多写：思欲丽，笔磨秃。4、强积累：有诗书，有成竹。";
    	note.createDate = new Date(114,12,22);
    	note.recentMFDate = new Date(114,12,22);
    	note.noteBookId = notebook.id;
    	note.save();
    	
    	note = new Note();
    	note.title = "英语";
    	note.content = "英语学习应遵循以下五大原则。这些原则都是'常识'性的。正如美国总统林肯所说：一个人必须依据语言、逻辑和'简单的常识'来决定问题和建立自己的行动计划。在学习英语的过程中，按照常理去做，就可能成功。违背了常理，就不可能成功。当然，成功与否还取决于'努力'。";
    	note.createDate = new Date(113,3,2);
    	note.recentMFDate = new Date(113,3,2);
    	note.noteBookId = notebook.id;
    	note.save();
    	
    	
    	/*添加药箱和药物*/
    	try{
    		MedicineBox mb1 = new MedicineBox();
    		mb1.userId = u1.id;
    		mb1.createDate = sf.parse("2015-1-10 20:17:45");
    		mb1.mark = "请遵医嘱";
    		mb1.name = "药箱1";
    		mb1.save();
    		
    		MedicineBox mb2 = new MedicineBox();
    		mb2.userId = u2.id;
    		mb2.createDate = sf.parse("2015-1-10 20:17:45");
    		mb2.mark = "请遵医嘱";
    		mb2.name = "药箱2";
    		mb2.save();
    		
    		MedicineBox mb3 = new MedicineBox();
    		mb3.userId = u1.id;
    		mb3.createDate = sf.parse("2015-1-12 20:15:45");
    		mb3.mark = "请遵医嘱";
    		mb3.name = "药箱3";
    		mb3.save();
    		
    		MedicineBox mb4 = new MedicineBox();
    		mb4.userId = u1.id;
    		mb4.createDate = sf.parse("2015-1-12 20:15:45");
    		mb4.mark = "请遵医嘱";
    		mb4.name = "药箱4";
    		mb4.save();
    		
    		MedicineBox mb5 = new MedicineBox();
    		mb5.userId = u2.id;
    		mb5.createDate = sf.parse("2015-1-10 20:17:45");
    		mb5.mark = "请遵医嘱";
    		mb5.name = "药箱5";
    		mb5.save();
    		
    		MedicineBox mb6 = new MedicineBox();
    		mb6.userId = u2.id;
    		mb6.createDate =sf.parse("2015-1-12 20:15:45");
    		mb6.mark = "请遵医嘱";
    		mb6.name = "药箱6";
    		mb6.save();
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	/*添加药物到药箱*/
    	
    	MedicineBox mb1 = (MedicineBox)MedicineBox.find("name", "药箱1").fetch().get(0);
    	MedicineBox mb2 = (MedicineBox)MedicineBox.find("name", "药箱2").fetch().get(0);
    	for(int i =0; i<10;i++){
    		Medicine m = new Medicine();
    		m.name="药物"+i;
    		m.type="otc";
    		m.deadline="2016-12-3";
    		m.function="治疗感冒";
    		m.applicability="小孩及成人";
    		m.code="2014808A"+i;
    		m.photoAddr="no";
    		if(i%2==0){
    		m.medicineBoxId=mb1.id;
    		}else{
    			m.medicineBoxId=mb2.id;
    		}
    		m.save();
    	}
    	
    	/*添加宝宝身体指标*/
    	try{
    	BodyIndex bindex = new BodyIndex();
    	bindex.babyId = b1.id;
    	bindex.date = sf.parse("2015-1-10 20:17:45");
    	bindex.height=50.0;
    	bindex.weight=10.0;
    	bindex.save();
    	
    	BodyIndex bindex1 = new BodyIndex();
    	bindex1.babyId = b1.id;
    	bindex1.date = sf.parse("2015-1-20 20:17:45");
    	bindex1.height=53.2;
    	bindex1.weight=11.4;
    	bindex1.save();
    	
    	BodyIndex bindex2 = new BodyIndex();
    	bindex2.babyId = b2.id;
    	bindex2.date = sf.parse("2015-1-12 20:15:45");
    	bindex2.height=51.2;
    	bindex2.weight=12.3;
    	bindex2.save();
    	
    	BodyIndex bindex3 = new BodyIndex();
    	bindex3.babyId = b2.id;
    	bindex3.date = sf.parse("2015-1-15 20:17:45");
    	bindex3.height=52.0;
    	bindex3.weight=15.0;
    	bindex3.save();
    	
    	BodyIndex bindex4 = new BodyIndex();
    	bindex4.babyId = b2.id;
    	bindex4.date = sf.parse("2015-1-21 20:17:45");
    	bindex4.height=55.0;
    	bindex4.weight=16.0;
    	bindex4.save();
    	
    	BodyIndex bindex5 = new BodyIndex();
    	bindex5.babyId = b1.id;
    	bindex5.date = sf.parse("2015-1-22 20:17:45");
    	bindex5.height=55.6;
    	bindex5.weight=16.3;
    	bindex5.save();
    	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	
    	
    	try{
    	/*添加孩子学习成绩*/
    	GradeCondition grade = new GradeCondition();
        
    	grade.babyId=b1.id;
    	grade.date = sf.parse("2014-12-25 12:24:30");
    	grade.subject="语文";
    	grade.mark=59.0;
    	grade.grade="一年级";
    	grade.save();
    	
    	GradeCondition grade1 = new GradeCondition();
    	grade1.babyId=b1.id;
    	grade1.date = sf.parse("2014-12-26 12:24:30");
    	grade1.subject="数学";
    	grade1.mark=69.0;
    	grade1.grade="一年级";
    	grade1.save();
    	
    	GradeCondition grade2 = new GradeCondition();
    	grade2.babyId=b2.id;
    	grade2.date = sf.parse("2015-01-10 12:24:30");
    	grade2.subject="语文";
    	grade2.mark=79.0;
    	grade2.grade="一年级";
    	grade2.save();
    	
    	GradeCondition grade3 = new GradeCondition();
    	grade3.babyId=b2.id;
    	grade3.date = sf.parse("2015-01-15 12:24:30");
    	grade3.subject="数学";
    	grade3.mark=89.0;
    	grade3.grade="一年级";
    	grade3.save();
    	
    	GradeCondition grade4 = new GradeCondition();
    	grade4.babyId=b1.id;
    	grade4.date = sf.parse("2015-01-16 12:24:30");
    	grade4.subject="语文";
    	grade4.mark=59.0;
    	grade4.grade="一年级";
    	grade4.save();
    	
    	GradeCondition grade5 = new GradeCondition();
    	grade5.babyId=b1.id;
    	grade5.date = sf.parse("2015-01-16 12:24:30");;
    	grade5.subject="数学";
    	grade5.mark=57.0;
    	grade5.grade="一年级";
    	grade5.save();
    	
    	GradeCondition grade6 = new GradeCondition();
    	grade6.babyId=b2.id;
    	grade6.date = sf.parse("2015-01-17 12:24:30");
    	grade6.subject="语文";
    	grade6.mark=58.0;
    	grade6.grade="一年级";
    	grade6.save();
    	}catch(Exception e){
    		
    	}
    	String sub="语文";
    	List<GradeCondition> list = GradeCondition.find("select g from GradeCondition g where subject=?","语文").fetch();
    	/*Query q = JPA.em().createNativeQuery("select g.baby_Id,g.date,g.subject,g.mark from GradeCondition g,"+
                "(select g1.baby_Id,max(g1.date) as date,g1.subject from GradeCondition g1 group by g1.baby_Id,g1.subject) t1"+
                " where g.date=t1.date and g.baby_Id=t1.baby_Id and"+
                " g.subject=t1.subject and g.subject = '语文' ");*/
    	
    	/*Query q = JPA.em().createQuery( "select g from GradeCondition g");*/
                
    	
    	/*List<GradeCondition> list = q.getResultList();*/
    	for(GradeCondition g :list){
    	System.out.println(g.babyId+" "+g.subject+" "+g.mark+" "+g.date);
    	}
    	List<GradeCondition> list2 = GradeCondition.find("select g from GradeCondition g where babyId in (select id from Baby where name = ?)", "baby2").fetch();
    	for(GradeCondition g :list2){
        	System.out.println("test--"+g.babyId+" "+g.subject+" "+g.mark+" "+g.date);
        	}
			
		/**
    	 * 初始化设置表数据
    	 * */
    	User[] users = new User[50];
    	for(int i = 0;i<50;i++ ){
    		User user = (User) User.find("byUserName", "user"+i).fetch().get(0);
    		users[i] = user;
    	}
    	BackgroundUtil.setData(users);
    	/**
    	 * 设置表初始化结束
    	 * */
	}
}