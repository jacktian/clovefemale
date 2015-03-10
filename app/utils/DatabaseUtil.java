package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.Baby;
import models.BodyIndex;
import models.GradeCondition;
import models.Menses;
import models.Note;
import models.NoteBook;
import models.Temperature;
import models.User;

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
			u.IDcard = "44138119911024181" + i;
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
		/*月经测试数据*/
    	Menses menses = new Menses(u1.id, new Date(),"暗红", "多", true, true,"稠");
    	menses.save();
    	Menses menses2 = new Menses(u1.id, new Date(),"鲜红", "少", true, true,"稀");
    	menses2.save();
    	Menses menses3 = new Menses(u2.id, new Date(),"鲜红", "少", true, true,"稀");
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
    	notebook.userId = u1.id;
    	notebook.save();
    	
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
    	
    	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
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
			
		generateCalendarTestData() ;
	}
	
	private static void generateCalendarTestData(){
		User user = (User) User.all().fetch(1).get(0) ;
		DatabaseUtil.testUser = user ;
		java.util.Calendar c  = java.util.Calendar.getInstance();
		/*int year = c.get(c.YEAR) ;
		int month = c.get(c.MONTH) ;
		int day = c.get(c.DAY_OF_MONTH) ;
		int hour = c.get(c.HOUR_OF_DAY) ;
		int minute = c.get(c.MINUTE) ;*/
		long times = c.getTimeInMillis() ;
		int[] types = {models.Agenda.AGENDA_TYPE_VACCINATION, models.Agenda.AGENDA_TYPE_MEDECINE_STALE} ;
		
		for(int i = 1 ; i <= 3 ; i++){
			models.Agenda agenda = new models.Agenda() ;
			agenda.startTime = times + i*10*60*1000 ;	//i*10分钟后提醒
			agenda.endTime = times + i*10*60*1000 + 30*60*1000 ;
			agenda.userId = user.id ;
			agenda.location = "广州" ;
			agenda.remindMinutes = 30 ;	//提前30分钟提醒
			agenda.type = types[i%types.length] ;
			agenda.title = "事件"+i ;
			agenda.description = "测试事件"+i + ",userName:"+user.userName ;
			agenda.save() ;
		}
	}
	
	public static User getTestUser(){
		if(testUser == null){
//			testUser = (User) User.all().fetch(1).get(0) ;
			generateCalendarTestData() ;
		}
		return testUser ;
	}
}