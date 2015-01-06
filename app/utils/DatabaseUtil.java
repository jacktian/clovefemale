package utils;

import java.util.Date;

import models.Baby;
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
		for(int i=0;i<10;i++){
			Baby baby = new Baby();
			baby.userId = u1.id;
			baby.name = "小喇叭" + i + "号";
			baby.sex = "male";
			baby.date = new Date();
			baby.save();
		}
    	
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
    	
	}
}