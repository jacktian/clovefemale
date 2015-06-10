package controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import play.db.jpa.JPA;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import utils.DateUtil;
import beans.SimpleNoteBookBean;
import models.Baby;
import models.BabyVac;
import models.GradeForm;
import models.Note;
import models.BodyIndex;
import models.Vaccine;
import models.Vaccine;
import models.WeChat;

/**
 * 客户端婴儿控制器
 * 
 * @author Cater
 * @since 2015/5/13
 */
public class CBabyAction extends WebService{
	/**
	 * 加载宝宝信息
	 */
	public static void loadBabyList(){
		
//		String openid = "ob1R-uIRkLLp6lmmrT4w-2rrZ5jQ";
		String openid = session.get("openid");//从session中获取openid
		if(openid == null){
			openid = "ob1R-uIRkLLp6lmmrT4w-2rrZ5jQ";
		}
		String queryString = "select new Baby(b.id,b.userId,b.date,CONCAT(TIMESTAMPDIFF(YEAR,b.date,now()),''),b.headImgUrl,b.sex,b.name) " +
				" from Baby b where b.userId = ?1";
		
		List<Baby> babyList =  new ArrayList<Baby>();
		try{
			Query query = JPA.em().createQuery(queryString);
			query.setParameter(1, openid);//给编号为1的参数设值 
			babyList  = query.getResultList();
			if(babyList.size() != 0){
				wsOk(babyList);
			}else{
				wsError("找不到宝宝");
			}
			
		}catch(Exception e){
			wsError("操作异常");
		}
	}
	
	/**
	 * 添加宝宝
	 */
	public static void addBaby(String name,String sex,String headImgUrl,Date birthday){
		String openid = session.get("openid");//从session中获取openid
//		String openid = "ob1R-uIRkLLp6lmmrT4w-2rrZ5jQ";
		Baby baby = new Baby();
		baby.date = birthday;
		baby.sex = sex;
		baby.name = name;
		baby.userId = openid;
		baby.headImgUrl = headImgUrl;
		baby.dateStr = "" + DateUtil.getAge(birthday);;	
		List<Baby> babyList = new ArrayList<Baby>();
		try{
			baby.save();
//			String downloadResult = downloadBabyImg(baby.id,media_id);
//			if(downloadResult.equals("error")){
//				downloadResult = downloadBabyImg(baby.id,media_id);//再次调用
//				if(downloadResult.equals("error")){//两次调用失败
//					babyList.add(baby);
//					wsOk(babyList);
//				}
//			}
//			baby.headImgUrl = downloadResult;
//			baby.save();	
			initBabyVac(baby.date,baby.id);
			babyList.add(baby);
			wsOk(babyList);
		}catch(Exception e){
			wsError("添加失败");
		}
	}
	
	/**
	 *加载宝宝详细资料
	 *babyId:宝宝id 
	 */
	public static void loadBabyInf(String babyId){
		try{
			Baby baby = Baby.findById(babyId);
			if(baby!=null){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				try{
					baby.dateStr = format.format(baby.date);
				}catch(Exception e){
					baby.dateStr = "未设置";
				}
				wsOk(baby);
			}else{
				wsError("不存在该宝宝哦!");
			}
		}catch(Exception e){
			wsError("操作异常!");
		}
		
	}
	
	/**
	 * 修改宝宝小名
	 * name:宝宝名字
	 * babyId:宝宝Id
	 */
	public static void modifyBabyName(String babyId,String name){
		try{
			Baby baby = Baby.findById(babyId);
			if(baby!=null){
				baby.name = name;
				baby.save();
				wsOk("修改成功");
			}else{
				wsError("找不到该宝宝");
			}
		}catch(Exception e){
			wsError("操作异常");
		}
		
	}
	
	/**
	 * 修改宝宝性别
	 * sex:性别
	 * babyId:宝宝Id
	 */
	public static void modifyBabySex(String babyId,String sex){
		try{
			Baby baby = Baby.findById(babyId);
			if(baby!=null){
				baby.sex = sex;
				baby.save();
				wsOk("修改成功");
			}else{
				wsError("找不到该宝宝");
			}
		}catch(Exception e){
			wsError("操作异常");
		}
		
	}
	
	/**
	 * 修改宝宝出生日期
	 * date:出生日期
	 * babyId:宝宝Id
	 */
	public static void modifyBabyBtd(String babyId,Date date){
		try{
			Baby baby = Baby.findById(babyId);
			if(baby!=null){
				baby.date = date;		
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");	
				baby.dateStr = format.format(baby.date);
				baby.save();
				modifyVacEtm(babyId,date);//修改疫苗预计接种日期
				wsOk("修改成功");
			}else{
				wsError("找不到该宝宝");
			}
		}catch(Exception e){
			wsError("操作异常");
		}
		
	}
	
	/**
	 * 获取access_token
	 */
	public static void getAccess_token(){
		List<WeChat> weChatList = WeChat.all().fetch(1,1);
		wsOk(weChatList);
	}
	
	/**
	 * 从微信服务器下载用户上传的宝宝头像
	 */
	public static void downloadBabyImg(String babyId,String media_id){
		List<WeChat> weChatList = WeChat.all().fetch(1,1);
		String ACCESS_TOKEN = weChatList.get(0).access_token;

		String MEDIA_ID = media_id;
		Date now = new Date();
//		now.getTime()+"";
		String headImgUrl ="babyimage/" + babyId + now.getTime()+ media_id + ".jpg";

		HttpResponse resp = WS.url("http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + ACCESS_TOKEN + "&media_id=" + MEDIA_ID).get();
		try{
			File file = new File(headImgUrl);
			FileOutputStream output = new FileOutputStream(file);
			InputStream inputStream = resp.getStream();
			//得到图片的二进制数据，以二进制封装得到数据，具有通用性  
	        byte[] data = readInputStream(inputStream);  
			 
	        output.write(data);
			output.flush();
			output.close();
			headImgUrl = "/" +headImgUrl;
			wsOk(headImgUrl);
		}catch(Exception e){
			wsError("上传头像失败！");
		}
		
		
	}
	
	/**
	 * 从微信服务器下载用户上传的宝宝头像,内部使用
	 */
	private static String downBabyImg(String babyId,String media_id){
		List<WeChat> weChatList = WeChat.all().fetch(1,1);
		String ACCESS_TOKEN = weChatList.get(0).access_token;

		String MEDIA_ID = media_id;
		Date now = new Date();
		String headImgUrl ="babyimage/" + babyId + now.getTime()+ media_id + ".jpg";

		HttpResponse resp = WS.url("http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + ACCESS_TOKEN + "&media_id=" + MEDIA_ID).get();
		try{
			File file = new File(headImgUrl);
			FileOutputStream output = new FileOutputStream(file);
			InputStream inputStream = resp.getStream();
			//得到图片的二进制数据，以二进制封装得到数据，具有通用性  
	        byte[] data = readInputStream(inputStream);  
			 
	        output.write(data);
			output.flush();
			output.close();
			headImgUrl = "/" +headImgUrl;
			return headImgUrl;
		}catch(Exception e){
			return "error";
		}
		
		
	}
	
	/**
	 * 更换宝宝头像
	 */
	public static void replaceBabyImg(String babyId,String media_id){
		String downloadResult = downBabyImg(babyId,media_id);
		try{
			if(downloadResult.equals("error")){
				downloadResult = downBabyImg(babyId,media_id);//再次调用
				if(downloadResult.equals("error")){//两次调用失败
					wsError("头像获取失败,请重新上传!");
				}
			}
			Baby baby = Baby.findById(babyId);
			baby.headImgUrl = downloadResult ;
			baby.save();
			wsOk(baby);
		}catch(Exception e){
			wsError("更换头像失败,请重试!");
//			wsError(e.p);
		}
			
	}
	
	
	public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        //创建一个Buffer字符串  
        byte[] buffer = new byte[1024];  
        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
        int len = 0;  
        //使用一个输入流从buffer里把数据读取出来  
        while( (len=inStream.read(buffer)) != -1 ){  
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);  
        }  
        //关闭输入流  
        inStream.close();  
        //把outStream里的数据写入内存  
        return outStream.toByteArray();  
    }  
	
	/**
	 * 加载所有宝宝身体指标数据
	 * babyId:宝宝Id
	 */
	public static void loadAllBiData(String babyId){
		if(babyId == null || "".equals(babyId)){
			wsError("宝宝Id不能为空！");
		}
		try{
			List<BodyIndex> bodyIndexList = BodyIndex.find("select new BodyIndex(bi.date,bi.height,bi.weight,bi.babyId,bi.age,bi.ageDcb) from BodyIndex bi where babyId = ? Order by bi.age desc", babyId).fetch();
			if(bodyIndexList.size()!=0){
				wsOk(bodyIndexList);
			}else{
				wsError("null");
			}
		}catch(Exception e){
			wsError("出现异常！");
		}
	}
	/**
	 * 增加或修改宝宝身体指标
	 */
	public static void addOrMdfBabyIndex(String bodyIndexId,String babyId,double age,String ageDcb,double height,double weight){
		BodyIndex bodyIndex;
		
		if(!"".equals(bodyIndexId)){//bodyIndexId不为空，表示已经是已经记录过的身体指标，此操作为修改身体指标
			try{
				bodyIndex = BodyIndex.findById(bodyIndexId);
				bodyIndex.height = height;
				bodyIndex.weight = weight;
				bodyIndex.save();
				List<BodyIndex> bodyIndexList = BodyIndex.find("select new BodyIndex(bi.date,bi.height,bi.weight,bi.babyId,bi.age,bi.ageDcb) from BodyIndex bi where babyId = ? Order by bi.age desc", babyId).fetch(6);
				wsOk(bodyIndexList);
//				wsOk("修改记录成功");
			}catch(Exception e){
				wsError("修改记录失败");
			}
			
		}else{//bodyIndexId为空，表示未记录过的身体指标，此操作为增加身体指标
//			String ageDcb;
//			if(age < 1){//一岁以下
//				ageDcb = (int)age * 10 + "个月";
//			}else{
//				int age_int = (int) Math.floor(age);
//				String age_digit_string = "";
//				if((int)Math.round(age) - age != 0){
//					age_digit_string = "半";
//				}
//				 
//				ageDcb = age_int + "岁" + age_digit_string;
//			}
			bodyIndex = new BodyIndex();
			bodyIndex.babyId = babyId;
			bodyIndex.age = age;
			bodyIndex.ageDcb = ageDcb;
			bodyIndex.height = height;
			bodyIndex.weight = weight;
			try{
				bodyIndex.save();
				List<BodyIndex> bodyIndexList = BodyIndex.find("select new BodyIndex(bi.date,bi.height,bi.weight,bi.babyId,bi.age,bi.ageDcb) from BodyIndex bi where babyId = ? Order by bi.age desc", babyId).fetch(6);
				wsOk(bodyIndexList);
			}catch(Exception e){
				wsError("添加记录失败");
			}
			
			
		}
		
	}
	
	/**
	 * 加载成长记录数据
	 */
	public static void loadBabyGrowth(String babyId){
//		String babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		if("".equals(babyId) || babyId == null){
			babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		}
//		50.4,54.8,58.7,62,64.6,66.7,68.4,69.8,71.2,72.6,74,75.3,76.5,82.7,88.5,93.3,97.5,100.6,104.1,107.7,111.3,114.7,117.7,120.7,124,130,135.4,140.2,145.3,151.9,159.5,165.9,169.8,171.6,172.3,172.7
		
//		女：
//		49.7,53.7,57.4,60.6,63.1,65.2,66.8,68.2,69.6,71,72.4,73.7,75,81.5,87.2,92.1,96.3,99.4,103.1,106.7,110.2,113.5,116.6,119.4, 122.5,128.5,134.1,140.1,146.6,152.4,156.3,158.6,159.8,160.1,160.3,160.6
		try{
			List<BodyIndex> bodyIndexList = BodyIndex.find("select new BodyIndex(bi.date,bi.height,bi.weight,bi.babyId,bi.age,bi.ageDcb) from BodyIndex bi where babyId = ? Order by bi.age desc", babyId).fetch(6);
			if(bodyIndexList.size() == 0){
				wsError("没有记录");
			}else{
				wsOk(bodyIndexList);
			}
		}catch(Exception e){
			wsError("加载失败,请重试！");
		}
		
	}
	
	/*
	 * 加载身体指标（某一次）
	 * babyId：宝宝的Id
	 * age：年龄
	 */
	public static void loadBodyIndex_age(String babyId,double age){
		try{
			List<BodyIndex> bodyIndexList = BodyIndex.find("babyId=? and age = ?", babyId,age).fetch();
			if(bodyIndexList.size()!=0){
				wsOk(bodyIndexList.get(0));
			}else{
				wsError("null");
			}
		}catch(Exception e){
			wsError("异常");
		}
		
	}
	
	/**
	 * 分页加载成绩记录
	 */
	public static void loadSubjectMark(String babyId,String subject,int pageNum){
//			if("".equals(babyId)||babyId==null){
//				babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
//			}
			List<GradeForm> gradeFormList;
			try{
				if(subject == null || "".equals(subject)){//subject没选定的时候，加载最新记录的一次的科目
					List<String> subjectList = GradeForm.find("select gf.subject from GradeForm gf where gf.babyId = ?1 order by gf.date desc",babyId).fetch(1);
					if(subjectList.size()!= 0 ){//有记录
						subject = subjectList.get(0);
					}
				}
				if(pageNum == 0){
					gradeFormList = GradeForm.find("select new GradeForm(gc.date,gc.grade,gc.grade_int,gc.subject,gc.mark,gc.time,gc.babyId) from GradeForm gc where gc.babyId = ? and gc.subject=? Order by gc.grade_int desc,gc.time desc", babyId,subject).fetch();
				}else{
					gradeFormList = GradeForm.find("select new GradeForm(gc.date,gc.grade,gc.grade_int,gc.subject,gc.mark,gc.time,gc.babyId) from GradeForm gc where gc.babyId = ? and gc.subject=? Order by gc.grade_int desc,gc.time desc", babyId,subject).fetch(pageNum,6);
				}
			//	gradeFormList = GradeForm.find("select new GradeForm(gc.date,gc.grade,gc.grade_int,gc.subject,gc.mark,gc.time,gc.babyId) from GradeForm gc where gc.babyId = ? and gc.subject=? Order by gc.grade_int desc,gc.time desc", babyId,subject).fetch();
				

				if(gradeFormList.size() == 0){
					wsError("null");
				}else{
					wsOk(gradeFormList);
				}
			}catch(Exception e){
				wsError(e.getMessage());
				wsError("加载失败,请重试！");
			}
			
	}
	
	
	
	/**
	 * 增加或修改宝宝成绩表单
	 */
	public static void addOrMdfGradeForm(String gradeFormId,String babyId,String subject,String grade,double mark,int time){
		GradeForm gradeForm;
		List gradeStringList = Arrays.asList("小班","中班","大班","一年级", "二年级", "三年级", "四年级", "五年级", "六年级", "初一", "初二", "初三", "高一", "高二", "高三");
		List<Integer> gradeIntList = Arrays.asList(1,2,3,11, 12, 13, 14, 15, 16, 21, 22, 23, 31, 32, 33);
		int i = gradeStringList.indexOf(grade);
		int grade_int = 0;
		if(i >= 0){
			 grade_int= gradeIntList.get(i).intValue();
		}
		//		String grade_String[] = {};
//		grade_String.
		//		gradeList.indexOf();
		
		if(!"".equals(gradeFormId)){//gradeFormId不为空，表示已经是已经记录过的成绩，此操作为修改成绩记录
			try{
				gradeForm = GradeForm.findById(gradeFormId);
				gradeForm.mark = mark;
				gradeForm.save();
				List<GradeForm> gradeFormList = GradeForm.find("select new GradeForm(gc.date,gc.grade,gc.grade_int,gc.subject,gc.mark,gc.time,gc.babyId) from GradeForm gc where gc.babyId = ? and gc.subject=? Order by gc.grade_int desc,gc.time desc", babyId,subject).fetch(6);
				wsOk(gradeFormList);
//				wsOk("修改记录成功");
			}catch(Exception e){
				wsError("修改记录失败");
			}
			
		}else{//gradeFormId为空，表示未记录过的成绩，此操作为增加成绩记录

			gradeForm = new GradeForm();
			gradeForm.babyId = babyId;
			gradeForm.grade = grade;
			gradeForm.grade_int = grade_int;
			gradeForm.subject = subject;
			gradeForm.time = time;
			gradeForm.mark = mark;
			gradeForm.date = new Date();
			try{
				gradeForm.save();
				List<GradeForm> gradeFormList = GradeForm.find("select new GradeForm(gc.date,gc.grade,gc.grade_int,gc.subject,gc.mark,gc.time,gc.babyId) from GradeForm gc where gc.babyId = ? and gc.subject=? Order by gc.grade_int desc,gc.time desc", babyId,subject).fetch(6);
				wsOk(gradeFormList);
			}catch(Exception e){
				wsError("添加记录失败");
			}
			
			
		}
		
	}
	
	/**
	 * 加载成绩表单数据
	 * babyId：宝宝id
	 * 未完成
	 */
	public static void loadGradeForm(String babyId,String subject){
		if("".equals(babyId)||babyId==null){
			babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		}
		List<GradeForm> gradeFormList;
		try{
			if(subject == null || "".equals(subject)){//subject没选定的时候，加载最新记录的一次的科目
				List<String> subjectList = GradeForm.find("select gf.subject from GradeForm gf where gf.babyId = ?1 order by gf.date desc",babyId).fetch(1);
				if(subjectList.size()!= 0 ){//有记录
					subject = subjectList.get(0);
				}
			}
			gradeFormList = GradeForm.find("select new GradeForm(gc.date,gc.grade,gc.grade_int,gc.subject,gc.mark,gc.time,gc.babyId) from GradeForm gc where gc.babyId = ? and gc.subject=? Order by gc.grade_int desc,gc.time desc", babyId,subject).fetch(6);
			

			if(gradeFormList.size() == 0){
				wsError("没有记录");
			}else{
				wsOk(gradeFormList);
			}
		}catch(Exception e){
			wsError(e.getMessage());
			wsError("加载失败,请重试！");
		}
		
	}
	
	/*
	 * 加载成绩（某一次）
	 * babyId：宝宝的Id
	 * time：第几次
	 * subject:科目
	 * grade：年级
	 */
	public static void loadGrade(String babyId,String grade, String subject,int time){
		try{
			List<GradeForm> gradeFormList = GradeForm.find("babyId=? and grade = ? and subject = ? and time = ?", babyId,grade,subject,time).fetch(1);
			if(gradeFormList.size()!=0){
				wsOk(gradeFormList.get(0));
			}else{
				wsError("null");
			}
		}catch(Exception e){
			wsError("异常");
		}
		
	}
	
	/**
	 * 
	 */
	public static void modifyGrade(){
		String babyId = babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		List<GradeForm> gradeFormList = GradeForm.find("babyId=?", babyId).fetch(6);
		GradeForm grade;
		for(int i = 0; i < gradeFormList.size(); i++){
			grade = gradeFormList.get(i);
			grade.grade_int = 1;
			grade.delete();
		}
	}
	
	
	/**
	 *加载宝宝下次接种疫苗 
	 */
	public static void loadNextVac(String babyId){
//		wsOk(BabyVac.findAll());
		if("".equals(babyId)||babyId==null){
			babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		}

		try{
			List<BabyVac> vacList = BabyVac.find("select new BabyVac(bv.id,bv.etmDate,bv.date,bv.name,bv.monthAfter,bv.ageDcb,bv.babyId,bv.pvDisease,bv.isDone) from BabyVac bv where bv.babyId = ? and bv.isDone = 0 order by bv.monthAfter", babyId).fetch(3); 
			if(vacList.size()!=0){
				int length = vacList.size();
				int monthAfter = vacList.get(0).monthAfter;
				for(int i = length -1; i > 0; i--){
					if(vacList.get(i).monthAfter != monthAfter){
						vacList.remove(i);
					}
				}
				wsOk(vacList);
			}else{
				wsError("null");
			}
		}catch(Exception e){
			wsError(e.getMessage());
			wsError("操作异常");
		}	
	}
	
	/**
	 *加载宝宝所有未接种疫苗 
	 */
	public static void loadTodoVac(String babyId){
		if("".equals(babyId)||babyId==null){
			babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		}

		try{
			List<BabyVac> vacList = BabyVac.find("select new BabyVac(bv.id,bv.etmDate,bv.date,bv.name,bv.monthAfter,bv.ageDcb,bv.babyId,bv.pvDisease,bv.isDone) from BabyVac bv where bv.babyId = ? and bv.isDone = 0 order by bv.monthAfter", babyId).fetch(); 
			if(vacList.size()!=0){
				wsOk(vacList);
			}else{
				wsError("null");
			}
		}catch(Exception e){
			wsError("操作异常");
		}	
	}
	
	/**
	 *加载宝宝已接种疫苗 
	 *只加载最近接种的5个疫苗
	 */
	public static void loadVaccinated(String babyId){
//		wsOk(BabyVac.findAll());
		if("".equals(babyId)||babyId==null){
			babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		}

		try{
			List<BabyVac> vacList = BabyVac.find("select new BabyVac(bv.id,bv.etmDate,bv.date,bv.name,bv.monthAfter,bv.ageDcb,bv.babyId,bv.pvDisease,bv.isDone) from BabyVac bv where bv.babyId = ? and bv.isDone = 1 order by bv.monthAfter desc", babyId).fetch(5); 
			if(vacList.size()!=0){
				wsOk(vacList);
			}else{
				wsError("null");
			}
		}catch(Exception e){
			wsError("操作异常");
		}	
	}
	
	/**
	 *加载宝宝所有已接种疫苗 
	 *
	 */
	public static void loadAllDoneVac(String babyId){
		if("".equals(babyId)||babyId==null){
			babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		}

		try{
			List<BabyVac> vacList = BabyVac.find("select new BabyVac(bv.id,bv.etmDate,bv.date,bv.name,bv.monthAfter,bv.ageDcb,bv.babyId,bv.pvDisease,bv.isDone) from BabyVac bv where bv.babyId = ? and  bv.isDone = 1 order by bv.monthAfter desc", babyId).fetch(); 
			if(vacList.size()!=0){
				wsOk(vacList);
			}else{
				wsError("null");
			}
		}catch(Exception e){
			wsError("操作异常");
		}	
	}
	
	/**
	 * 搜索疫苗
	 * 
	 */
	public static void loadSearchVac(String babyId,String searchWord){
		if("".equals(babyId)||babyId==null){
			babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		}

		try{
			List<BabyVac> vacList = BabyVac.find("select new BabyVac(bv.id,bv.etmDate,bv.date,bv.name,bv.monthAfter,bv.ageDcb,bv.babyId,bv.pvDisease,bv.isDone) from BabyVac bv where bv.babyId = ? and  bv.name like '%"+searchWord+"%' order by bv.monthAfter", babyId).fetch(); 
			if(vacList.size()!=0){
				wsOk(vacList);
			}else{
				wsError("null");
			}
		}catch(Exception e){
			wsError(e.getMessage());
			wsError("操作异常");
		}	
	}
	
	/**
	 * 添加宝宝疫苗
	 */
	public static void addBabyVac(String babyId,String name,String pvDisease){
//		babyVac.etmDate = cld.getTime();
//		babyVac.ageDcb = vac.ageDcb;
//		babyVac.babyId = babyId;
//		babyVac.name = vac.name;
//		babyVac.monthAfter = vac.monthAfter;
//		babyVac.pvDisease = pvDisease;
//		babyVac.isDone = "0";//"0"表示未来接种
	}
	
	/**
	 *加载宝宝所有疫苗 
	 *
	 */
	public static void loadAllVac(String babyId){
		if("".equals(babyId)||babyId==null){
			babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		}
		try{
			List<BabyVac> vacList = BabyVac.find("select new BabyVac(bv.id,bv.etmDate,bv.date,bv.name,bv.monthAfter,bv.ageDcb,bv.babyId,bv.pvDisease,bv.isDone) from BabyVac bv where bv.babyId = ?  order by bv.monthAfter", babyId).fetch(); 
			if(vacList.size()!=0){
				wsOk(vacList);
			}else{
				wsError("null");
			}
		}catch(Exception e){
			wsError("操作异常");
		}	
	}
	
	
	/**
	 * 初始化宝宝疫苗接种数据
	 *	birthday:宝宝出生日期
	 *  babyId: 宝宝Id
	 */
	public static void initBabyVac(Date birthday, String babyId){
		if("".equals(babyId) || babyId == null){
			babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		}
		if(birthday == null){
			Baby baby = Baby.findById(babyId);
			birthday = baby.date;
		}
		List<Vaccine> vacList = Vaccine.findAll();
//		wsOk(vacList);
		int length = vacList.size();
		
		try{
			for(int i = 0 ; i < length ; i++){
				Vaccine vac =  vacList.get(i);
				Calendar cld = Calendar.getInstance();
				cld.setTime(birthday);
				BabyVac babyVac = new BabyVac();
				cld.add(Calendar.MONTH,vac.monthAfter);
				babyVac.etmDate = cld.getTime();
				babyVac.ageDcb = vac.ageDcb;
				babyVac.babyId = babyId;
				babyVac.name = vac.name;
				babyVac.monthAfter = vac.monthAfter;
				babyVac.pvDisease = vac.pvDisease;
				babyVac.isDone = "0";//"0"表示未来接种
				babyVac.save();
			}
			wsOk("初始化疫苗列表成功");
		}catch(Exception e){
			wsError("初始化疫苗列表失败");
		}
		
	}
	
	/**
	 * 修改疫苗
	 * 包括两种情况：
	 * 			1、把未接种疫苗设定实际接种日期，修改isDone字段为1已接种疫苗
	 * 			2、修改isDone字段为0变为未接种疫苗
	 */
	public static void modifyVac(String babyVacId,Date date){
		try{
			BabyVac babyVac = BabyVac.findById(babyVacId);
			if(date == null){
				babyVac.isDone = "0";
			}else{
				babyVac.isDone = "1";
				babyVac.date = date;
			}
			babyVac.save();
			wsOk("成功");
		}catch(Exception e){
			wsError(e.getMessage());
			wsError("操作异常！");
		}
		
		
	}
	
	/**
	 * 修改宝宝疫苗预计接种日期（全部一起修改）
	 */
	public static void modifyVacEtm(String babyId,Date etm){
		try{
			List<BabyVac> babyVacList = BabyVac.find("select new BabyVac(bv.id,bv.etmDate,bv.date,bv.name,bv.monthAfter,bv.ageDcb,bv.babyId,bv.pvDisease,bv.isDone) from BabyVac bv where bv.babyId = ?  order by bv.monthAfter", babyId).fetch(29); 
			int count = babyVacList.size();
			if(count != 0){
				for(int i = 0; i < count; i++ ){
					BabyVac babyVac = babyVacList.get(i);
					Calendar cld = Calendar.getInstance();
					cld.setTime(etm);
					cld.add(Calendar.MONTH,babyVac.monthAfter);
					babyVac.etmDate = cld.getTime();
					babyVac.save();
				}
			}
			wsOk("成功");
		}catch(Exception e){
			wsError("异常");
		}
	}
	
	/**
	 * 清除所有宝宝疫苗数据
	 */
	public static void clearAllBabyVac(){
		BabyVac.deleteAll();
	}
	
	/**
	 * 初始化疫苗列表
	 */
	public static void initVacList(){
		/*-----1-----*/
		Vaccine vac = new Vaccine();
		vac.name = "乙肝疫苗(第一次)";
		vac.monthAfter = 0;
		vac.pvDisease = "乙型病毒性肝炎";
		vac.time = "1";
		vac.ageDcb = "出生时";
		vac.save();
		
		/*-----2-----*/
		vac = new  Vaccine();
		vac.name = "卡介苗(第一次)";
		vac.monthAfter = 0;
		vac.pvDisease = "结核病";
		vac.time = "1";
		vac.ageDcb = "出生时";
		vac.save();

		/*-----3-----*/
		vac = new  Vaccine();
		vac.name = "乙肝疫苗(第二次)";
		vac.monthAfter = 1;
		vac.pvDisease = "乙型病毒性肝炎";
		vac.time = "2";
		vac.ageDcb = "1月龄";
		vac.save();

		/*-----4-----*/
		vac = new  Vaccine();
		vac.name = "脊灰疫苗(第一次)";
		vac.monthAfter = 2;
		vac.pvDisease = "脊髓灰质炎(小儿麻痹)";
		vac.time = "1";
		vac.ageDcb = "2月龄";
		vac.save();

		/*-----5-----*/
		vac = new  Vaccine();
		vac.name = "脊灰疫苗(第二次)";
		vac.monthAfter = 3;
		vac.pvDisease = "脊髓灰质炎(小儿麻痹)";
		vac.time = "2";
		vac.ageDcb = "3月龄";
		vac.save();

		/*-----6-----*/
		vac = new  Vaccine();
		vac.name = "无细胞百白破疫苗(第一次)";
		vac.monthAfter = 3;
		vac.pvDisease = "百日咳、白喉、破伤风";
		vac.time = "1";
		vac.ageDcb = "3月龄";
		vac.save();
		
		/*-----7-----*/
		vac = new  Vaccine();
		vac.name = "脊灰疫苗(第三次)";
		vac.monthAfter = 4;
		vac.pvDisease = "脊髓灰质炎(小儿麻痹)";
		vac.time = "3";
		vac.ageDcb = "4月龄";
		vac.save();
		
		/*-----8-----*/
		vac = new  Vaccine();
		vac.name = "无细胞百白破疫苗(第二次)";
		vac.monthAfter = 4;
		vac.pvDisease = "百日咳、白喉、破伤风";
		vac.time = "2";
		vac.ageDcb = "4月龄";
		vac.save();
		
		/*-----9-----*/
		vac = new  Vaccine();
		vac.name = "无细胞百白破疫苗(第三次)";
		vac.monthAfter = 5;
		vac.pvDisease = "百日咳、白喉、破伤风";
		vac.time = "3";
		vac.ageDcb = "5月龄";
		vac.save();
		
		/*-----10-----*/
		vac = new  Vaccine();
		vac.name = "乙肝疫苗(第三次)";
		vac.monthAfter = 6;
		vac.pvDisease = "乙型病毒性肝炎";
		vac.time = "3";
		vac.ageDcb = "6月龄";
		vac.save();
		
		/*-----11-----*/
		vac = new  Vaccine();
		vac.name = "流脑疫苗(第一次)";
		vac.monthAfter = 6;
		vac.pvDisease = "流行性脑脊髓膜炎";
		vac.time = "1";
		vac.ageDcb = "6月龄";
		vac.save();
		
		/*-----12-----*/
		vac = new  Vaccine();
		vac.name = "麻疹疫苗(第一次)";
		vac.monthAfter = 8;
		vac.pvDisease = "麻疹";
		vac.time = "1";
		vac.ageDcb = "8月龄";
		vac.save();
		
		/*-----13-----*/
		vac = new  Vaccine();
		vac.name = "流脑疫苗(第二次)";
		vac.monthAfter = 9;
		vac.pvDisease = "流行性脑脊髓膜炎";
		vac.time = "2";
		vac.ageDcb = "9月龄";
		vac.save();
		
		/*-----14-----*/
		vac = new  Vaccine();
		vac.name = "乙脑减毒疫苗(第一次)";
		vac.monthAfter = 12;
		vac.pvDisease = "流行性乙型脑炎";
		vac.time = "1";
		vac.ageDcb = "1岁";
		vac.save();
		
		/*-----15-----*/
		vac = new  Vaccine();
		vac.name = "甲肝疫苗(第一次)";
		vac.monthAfter = 18;
		vac.pvDisease = "甲型病毒性肝炎";
		vac.time = "1";
		vac.ageDcb = "1岁半";
		vac.save();
		
		/*-----16-----*/
		vac = new  Vaccine();
		vac.name = "无细胞百白破疫苗(第四次)";
		vac.monthAfter = 18;
		vac.pvDisease = "百日咳、白喉、破伤风";
		vac.time = "4";
		vac.ageDcb = "1岁半";
		vac.save();
		
		/*-----17-----*/
		vac = new  Vaccine();
		vac.name = "麻风腮疫苗(第一次)";
		vac.monthAfter = 18;
		vac.pvDisease = "麻疹、风疹、腮腺炎";
		vac.time = "1";
		vac.ageDcb = "1岁半";
		vac.save();
		
		/*-----18-----*/
		vac = new  Vaccine();
		vac.name = "乙脑减毒疫苗(第二次)";
		vac.monthAfter = 24;
		vac.pvDisease = "流行性乙型脑炎";
		vac.time = "2";
		vac.ageDcb = "2岁";
		vac.save();
		
		
		/*-----19-----*/
		vac = new  Vaccine();
		vac.name = "甲肝疫苗(第二次)";
		vac.monthAfter = 24;
		vac.pvDisease = "甲型病毒性肝炎(与前剂间隔6-12个月)";
		vac.time = "2";
		vac.ageDcb = "2岁";
		vac.save();
		
		/*-----20-----*/
		vac = new  Vaccine();
		vac.name = "A+C流脑疫苗(加强)";
		vac.monthAfter = 36;
		vac.pvDisease = "流行性脑脊髓膜炎";
		vac.time = "加强";
		vac.ageDcb = "3岁";
		vac.save();
		
		/*----21-----*/
		vac = new  Vaccine();
		vac.name = "脊灰疫苗(第四次)";
		vac.monthAfter = 48;
		vac.pvDisease = "脊髓灰质炎(小儿麻痹)";
		vac.time = "4";
		vac.ageDcb = "4岁";
		vac.save();
		
		/*-----22-----*/
		vac = new  Vaccine();
		vac.name = "无细胞百白破疫苗(白破)(加强)";
		vac.monthAfter = 72;
		vac.pvDisease = "百日咳、白喉、破伤风";
		vac.time = "加强";
		vac.ageDcb = "6岁";
		vac.save();
		
		/*-----23-----*/
		vac = new  Vaccine();
		vac.name = "麻风腮疫苗(第二次)";
		vac.monthAfter = 72;
		vac.pvDisease = "麻疹、风疹、腮腺炎";
		vac.time = "2";
		vac.ageDcb = "6岁";
		vac.save();
		
		/*-----24-----*/
		vac = new  Vaccine();
		vac.name = "乙脑减毒疫苗(第三次)";
		vac.monthAfter = 72;
		vac.pvDisease = "流行性乙型脑炎";
		vac.time = "3";
		vac.ageDcb = "6岁";
		vac.save();
		
		/*-----25-----*/
		vac = new  Vaccine();
		vac.name = "A+C流脑疫苗(加强)";
		vac.monthAfter = 108;
		vac.pvDisease = "流行性脑脊髓膜炎";
		vac.time = "加强";
		vac.ageDcb = "小学四年级(9岁)";
		vac.save();
		
		/*-----26-----*/
		vac = new  Vaccine();
		vac.name = "乙肝疫苗(第四次)";
		vac.monthAfter = 144;
		vac.pvDisease = "乙型病毒性肝炎";
		vac.time = "4";
		vac.ageDcb = "初中一年级(12岁)";
		vac.save();
		
		/*-----27-----*/
		vac = new  Vaccine();
		vac.name = "无细胞百白破疫苗(白破)(加强)";
		vac.monthAfter = 168;
		vac.pvDisease = "百日咳、白喉、破伤风";
		vac.time = "加强";
		vac.ageDcb = "初中三年级(14岁)";
		vac.save();
		
		/*-----28-----*/
		vac = new  Vaccine();
		vac.name = "无细胞百白破疫苗(白破)(加强)";
		vac.monthAfter = 216;
		vac.pvDisease = "百日咳、白喉、破伤风";
		vac.time = "加强";
		vac.ageDcb = "大一(18岁)";
		vac.save();
		
		/*-----29-----*/
		vac = new  Vaccine();
		vac.name = "麻疹疫苗(第二次)";
		vac.monthAfter = 216;
		vac.pvDisease = "麻疹";
		vac.time = "2";
		vac.ageDcb = "大一(18岁)";
		vac.save();
		
	}
//	
	public static void test() {
		String queryString = "select count(*) from Baby";
		queryString = "SELECT TIMESTAMPDIFF(YEAR, '2011-01-27 15:52:11',now())";
		Query query = JPA.em().createNativeQuery(queryString);
//		query.setParameter(1, openid);//给编号为1的参数设值 
		String year = query.getSingleResult().toString();
		System.out.println(year);
		wsOk(year);
	}
	
	
}
