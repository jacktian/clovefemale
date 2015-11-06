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
import models.Remind;
import models.Vaccine;
import models.Vaccine;
import models.WeChat;

import beans.BabyVacBean;

import utils.VacUtil;

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
		/*if(openid == null){
			openid = "ob1R-uIRkLLp6lmmrT4w-2rrZ5jQ";
		}*/
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
		/*if(openid == null || openid.equals("")){
			openid =  "ob1R-uIRkLLp6lmmrT4w-2rrZ5jQ";
		}*/
		//String openid = "ob1R-uIRkLLp6lmmrT4w-2rrZ5jQ";
		Baby baby = new Baby();
		baby.date = birthday;
		baby.sex = sex;
		baby.name = name;
		baby.userId = openid;
		baby.headImgUrl = headImgUrl;
		baby.dateStr = "" + DateUtil.getAge(birthday);
		List<Baby> babyList = new ArrayList<Baby>();
		try{
			baby.save();
			if(!initBabyVac(openid,baby.date,baby.id)){
				wsError("初始化宝宝疫苗数据失败");
			}
			if("".equals(baby.headImgUrl) || baby.headImgUrl == null){//未上传头像
				if("男".equals(baby.sex)){
					baby.headImgUrl = "/public/images/client/default-boy.jpg";
				}else{
					baby.headImgUrl = "/public/images/client/default-girl.jpg";
				}
			}
			babyList.add(baby);
			wsOk(babyList);
		}catch(Exception e){
			wsError("添加失败");
		}
	}
	
	
	/**
	 *删除宝宝 
	 */
	public static void deleteBaby(String babyId){
		try{
			BabyVac.delete("babyId=?", babyId);
			BodyIndex.delete("babyId=?", babyId);
			GradeForm.delete("babyId=?", babyId);
			Baby.delete("id = ?",babyId);
			wsOk("删除成功");
		}catch(Exception e){
			wsError("失败");
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
				if("".equals(baby.headImgUrl) || baby.headImgUrl == null){//未上传头像
					if("男".equals(baby.sex)){
						baby.headImgUrl = "/public/images/client/default-boy.jpg";
					}else{
						baby.headImgUrl = "/public/images/client/default-girl.jpg";
					}
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
			String openid = session.get("openid");//从session中获取openid
			/*if(openid == null){
				openid = "ob1R-uIRkLLp6lmmrT4w-2rrZ5jQ";
			}*/
			if(baby!=null){
				baby.date = date;		
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");	
				baby.dateStr = format.format(baby.date);
				baby.save();
				if(!VacUtil.modifyVacEtm(openid,babyId)){
					wsError("修改预计接种时间失败！");
				}
				//modifyVacEtm(babyId,date);//修改疫苗预计接种日期
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
	 * 加载宝宝身体指标总记录数
	 * babyId:宝宝Id
	 */
	public static void loadBiPageSize(String babyId,boolean hasStander){
		try{
			String queryString;
			Query query;
			if(hasStander == true){
				queryString = "select count(*) from bodyindex bi where bi.baby_Id = ?1 and bi.hasStander = ?2";
				query = JPA.em().createNativeQuery(queryString);
				query.setParameter(1, babyId);//给编号为1的参数设值 	
				query.setParameter(2 , hasStander);//给编号为2的参数设值 
			}else{
				queryString = "select count(*) from bodyindex bi where bi.baby_Id = ?1";
				query = JPA.em().createNativeQuery(queryString);
				query.setParameter(1, babyId);//给编号为1的参数设值 
			} 		
			String size = query.getSingleResult().toString();
			wsOk(Math.ceil(Float.parseFloat(size) / 6));
		}catch(Exception e){
//			wsError(e.toString());
			wsError("噢噢，出错了！");
		}
	}
	
	/**
	 * 加载所有宝宝身体指标数据
	 * babyId:宝宝Id
	 * page:页码
	 */
	public static void loadAllBiData(String babyId,boolean hasStander,int page){
		if(babyId == null || "".equals(babyId)){
			wsError("宝宝Id不能为空！");
		}
		try{
//			List<BodyIndex> bodyIndexList = BodyIndex.find("select new BodyIndex(bi.date,bi.height,bi.weight,bi.babyId,bi.age,bi.ageDcb) from BodyIndex bi where babyId = ? Order by bi.age desc", babyId).fetch();
			List<BodyIndex> bodyIndexList;
			if(hasStander == true){
				bodyIndexList = BodyIndex.find("select new BodyIndex(bi.date,bi.height,bi.weight,bi.babyId,bi.age,bi.ageDcb,bi.hasStander,bi.detailAge,bi.dayCount) from BodyIndex bi where babyId = ? and hasStander = true Order by bi.age desc", babyId).fetch(page,6); //fetch(6);
			}else{
				bodyIndexList = BodyIndex.find("select new BodyIndex(bi.date,bi.height,bi.weight,bi.babyId,bi.age,bi.ageDcb,bi.hasStander,bi.detailAge,bi.dayCount) from BodyIndex bi where babyId = ? Order by bi.age desc", babyId).fetch(page,6); //fetch(6);
			}
			
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
	public static void addOrMdfBabyIndex(String bodyIndexId,String babyId,Date date,Date birthday,double height,double weight){
		BodyIndex bodyIndex;
		
		if(!"".equals(bodyIndexId)){//bodyIndexId不为空，表示已经是已经记录过的身体指标，此操作为修改身体指标
			try{
				bodyIndex = BodyIndex.findById(bodyIndexId);
				bodyIndex.height = height;
				bodyIndex.weight = weight;
				bodyIndex.save();
//				List<BodyIndex> bodyIndexList = BodyIndex.find("select new BodyIndex(bi.date,bi.height,bi.weight,bi.babyId,bi.age,bi.ageDcb) from BodyIndex bi where babyId = ? Order by bi.age desc", babyId).fetch(6);
				List<BodyIndex> bodyIndexList = BodyIndex.find("select new BodyIndex(bi.date,bi.height,bi.weight,bi.babyId,bi.age,bi.ageDcb,bi.hasStander,bi.detailAge,bi.dayCount) from BodyIndex bi where babyId = ? and hasStander = true Order by bi.age desc", babyId).fetch(6);
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

			//计算年龄
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int dayCountOfMonth = cal.getActualMaximum(Calendar.DATE);
			int[] ageList = getAgeByBirthday(date,birthday);//获取年龄
//			int age = ageList[0];
			int year = ageList[0];
			int month = ageList[1];
			int day = ageList[2];
			int dayCount;
			String ageString = "";
			if(year < 2){
				if(year < 1){//小于1岁
				/*	if(day >= 15){//超过15，进1
						ageString = month++ + "月"; 
						dayCount = dayCountOfMonth - day;
					}else{
						ageString = month + "月"; 
						dayCount = day;
					}
					*/
					if(month >=10){//1岁
						ageString = "1岁"; 
						dayCount = (12- month) * 30 -day;
					}else if(month >= 7){//9月
						ageString = "9月"; 
						dayCount = Math.abs((9 - month) * 30);
						if(month > 9){
							dayCount += day;
						}else{
							dayCount -= day;
						}
					}else if(month >= 5){//6月
						ageString = "6月"; 
						dayCount = Math.abs((6-month) * 30);
						if(month > 6){
							dayCount += day;
						}else{
							dayCount -= day;
						}
					}else if(month >= 3){//4月
						ageString = "4月"; 
						dayCount = Math.abs((4- month) * 30);
						if(month > 4){
							dayCount += day;
						}else{
							dayCount -= day;
						}
					}else if(month >= 1){//2月
						ageString = "2月"; 
						dayCount = Math.abs((2-month) * 30);
						if(month > 2){
							dayCount += day;
						}else{
							dayCount -= day;
						}
					}else{
						ageString = "初生儿"; 
						dayCount = Math.abs(month * 30 + day);
					}
				}else{//1岁多
					if(month>=10){//2岁
						ageString = "2岁";
						dayCount = (12-month) * 30 -day;
					}else if(month>=7){//1岁9个月
						ageString = "1岁9月";
						dayCount = Math.abs((9 - month) * 30);
						if(month > 9){
							dayCount += day;
						}else{
							dayCount -= day;
						}
						
					}else if(month>=4){//1岁半
						ageString = "1岁半";
						dayCount = Math.abs((6 - month) * 30);
						if(month > 6){
							dayCount += day;
						}else{
							dayCount -= day;
						}
					}else if(month>=2){//1岁3个月
						ageString = "1岁3月";
						dayCount = Math.abs((3 - month) * 30);
						if(month > 3){
							dayCount += day;
						}else{
							dayCount -= day;
						}
					}else{
						ageString = "1岁";
						dayCount = month * 30 + day;
					}
				}
			}else{
//				if(month >= 3){
//					if(month >=9){//+1岁
//						ageString = year++ + "岁";
//						dayCount = Math.abs((month - 9) * 30 -day);
//					}else{//+半岁
//						ageString = year + "岁半";
//						if()
//						dayCount = Math.abs((3 - month) * 30 -day);
//					}
//				}else{
//					
//				}
				if(month >=9){//+1岁
					ageString = year++ + "岁";
					dayCount = (12 - month) * 30 -day;
				}else if(month >= 6){//+半岁
					ageString = year + "岁半";
					dayCount = (month - 6) * 30 + day;
				}else if(month >= 3){//+半岁
					ageString = year + "岁半";
					dayCount = (6 - month) * 30 - day;
				}else{
					ageString = year + "岁";
					dayCount =  month * 30 + day;
				}
			}
			
			bodyIndex = new BodyIndex();
			bodyIndex.babyId = babyId;
			bodyIndex.ageDcb = ageString;
			bodyIndex.date = date;
			bodyIndex.detailAge = "" + ageList[0] + "岁" + ageList[1] + "月" + ageList[2] + "日";
			bodyIndex.age = Double.parseDouble(ageList[0]+"."+ageList[1]+ageList[2]);
			bodyIndex.dayCount = dayCount;
			bodyIndex.height = height;
			bodyIndex.weight = weight;
			
			//查找是否存在相同阶段且hasStander = true的记录
			if( year > 18 && !ageString.equals("18岁")){//大于18岁，没有标准
				bodyIndex.hasStander = false;
			}else if(ageString.equals("17岁半")){
				bodyIndex.hasStander = false;
			}else{
				List<BodyIndex> bodyIndexRecordList= BodyIndex.find("babyId =? and ageDcb = ? and hasStander = true",babyId,ageString).fetch(1);
				if(bodyIndexRecordList.size() >0 ){//存在
					BodyIndex bodyIndexRecord = bodyIndexRecordList.get(0);
					if(bodyIndexRecord.dayCount > bodyIndex.dayCount){//新记录更接近标准
						bodyIndexRecord.hasStander = false;
						bodyIndex.hasStander = true;
						try{
							bodyIndexRecord.save();
						}catch(Exception e){
							//异常
						}
						
					}else{
						bodyIndex.hasStander = false;
					}
				}else{//不存在
					bodyIndex.hasStander = true;
				}
			}
				
			try{
				bodyIndex.save();
				List<BodyIndex> bodyIndexList = BodyIndex.find("select new BodyIndex(bi.date,bi.height,bi.weight,bi.babyId,bi.age,bi.ageDcb,bi.hasStander,bi.detailAge,bi.dayCount) from BodyIndex bi where babyId = ? and hasStander = true Order by bi.age desc", babyId).fetch(6);
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
		/*if("".equals(babyId) || babyId == null){
			babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		}*/
//		babyId
//		50.4,54.8,58.7,62,64.6,66.7,68.4,69.8,71.2,72.6,74,75.3,76.5,82.7,88.5,93.3,97.5,100.6,104.1,107.7,111.3,114.7,117.7,120.7,124,130,135.4,140.2,145.3,151.9,159.5,165.9,169.8,171.6,172.3,172.7
		
//		女：
//		49.7,53.7,57.4,60.6,63.1,65.2,66.8,68.2,69.6,71,72.4,73.7,75,81.5,87.2,92.1,96.3,99.4,103.1,106.7,110.2,113.5,116.6,119.4, 122.5,128.5,134.1,140.1,146.6,152.4,156.3,158.6,159.8,160.1,160.3,160.6
		try{
//			List<BodyIndex> bodyIndexList = BodyIndex.find("select new BodyIndex(bi.date,bi.height,bi.weight,bi.babyId,bi.age,bi.ageDcb) from BodyIndex bi where babyId = ? Order by bi.age desc", babyId).fetch(6);
			List<BodyIndex> bodyIndexList = BodyIndex.find("select new BodyIndex(bi.date,bi.height,bi.weight,bi.babyId,bi.age,bi.ageDcb,bi.hasStander,bi.detailAge,bi.dayCount) from BodyIndex bi where bi.babyId = ? and bi.hasStander = true Order by bi.age desc", babyId).fetch(6);
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
	 * date：身高体重日期
	 */
	public static void loadBodyIndex_age(String babyId,Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = format.format(date);
		try{
			List<BodyIndex> bodyIndexList = BodyIndex.find("babyId=? and DATE_FORMAT(date, '%Y-%m-%d') = ?", babyId,dateStr).fetch();
//			List<GradeForm> gradeFormList = GradeForm.find("babyId=? and grade = ? and subject = ? and DATE_FORMAT(date, '%Y-%m-%d') = ?", babyId,grade,subject,dateStr).fetch(1);
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
	 * 删除所有身高体重数据
	 */
	public static void deleteAllBodyIndexData(){
		try{
			BodyIndex.deleteAll();
			wsOk("删除成功");
		}catch(Exception e){
			wsError("噢噢，出错了！");
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
					List<String> subjectList = GradeForm.find("select gf.subject from GradeForm gf where gf.babyId = ? order by gf.date desc",babyId).fetch(1);
					if(subjectList.size()!= 0 ){//有记录
						subject = subjectList.get(0);
					}
				}
				if(pageNum == 0){
					gradeFormList = GradeForm.find("select new GradeForm(gc.date,gc.grade,gc.grade_int,gc.subject,gc.mark,gc.time,gc.babyId) from GradeForm gc where gc.babyId = ? and gc.subject=? Order by gc.grade_int desc,gc.date desc", babyId,subject).fetch(6);
				}else{
					gradeFormList = GradeForm.find("select new GradeForm(gc.date,gc.grade,gc.grade_int,gc.subject,gc.mark,gc.time,gc.babyId) from GradeForm gc where gc.babyId = ? and gc.subject=? Order by gc.grade_int desc,gc.date desc", babyId,subject).fetch(pageNum,6);
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
	 * 获取某科目成绩记录数的总页数
	 */
	public static void loadPageSizeOfSubject(String babyId,String subject){
		try{
			String queryString = "select count(*) from gradeform gf where baby_Id = ?1 and subject = ?2";
			Query query = JPA.em().createNativeQuery(queryString);
			query.setParameter(1, babyId);//给编号为1的参数设值 	
			query.setParameter(2 , subject);//给编号为1的参数设值 
			String size = query.getSingleResult().toString();
			System.out.println(subject+"--"+size);
			wsOk(Math.ceil(Float.parseFloat(size) / 6));
		}catch(Exception e){
			//wsError(e.toString());
			wsError("噢噢，出错了！");
		}
	}
	
	
	/**
	 * 增加或修改宝宝成绩表单
	 */
	public static void addOrMdfGradeForm(String gradeFormId,String babyId,String subject,String grade,double mark,Date date){
		GradeForm gradeForm;
//		"小班","中班","大班",
		List gradeStringList = Arrays.asList("一年级", "二年级", "三年级", "四年级", "五年级", "六年级", "初一", "初二", "初三", "高一", "高二", "高三");
//		1,2,3,
		List<Integer> gradeIntList = Arrays.asList(11, 12, 13, 14, 15, 16, 21, 22, 23, 31, 32, 33);
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
				List<GradeForm> gradeFormList = GradeForm.find("select new GradeForm(gc.date,gc.grade,gc.grade_int,gc.subject,gc.mark,gc.time,gc.babyId) from GradeForm gc where gc.babyId = ? and gc.subject=? Order by gc.grade_int desc,gc.date desc", babyId,subject).fetch(6);
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
//			gradeForm.time = time;
			gradeForm.mark = mark;
			gradeForm.date = date;
//			gradeForm.date = new Date();
			try{
				gradeForm.save();
				List<GradeForm> gradeFormList = GradeForm.find("select new GradeForm(gc.date,gc.grade,gc.grade_int,gc.subject,gc.mark,gc.time,gc.babyId) from GradeForm gc where gc.babyId = ? and gc.subject=? Order by gc.grade_int desc,gc.date desc", babyId,subject).fetch(6);
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
		/*if("".equals(babyId)||babyId==null){
			babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		}*/
		List<GradeForm> gradeFormList;
		try{
			if(subject == null || "".equals(subject)){//subject没选定的时候，加载最新记录的一次的科目
				List<String> subjectList = GradeForm.find("select gf.subject from GradeForm gf where gf.babyId = ?1 order by gf.date desc",babyId).fetch(1);
				if(subjectList.size()!= 0 ){//有记录
					subject = subjectList.get(0);
				}
			}
			gradeFormList = GradeForm.find("select new GradeForm(gc.date,gc.grade,gc.grade_int,gc.subject,gc.mark,gc.time,gc.babyId) from GradeForm gc where gc.babyId = ? and gc.subject=? Order by gc.grade_int desc,gc.date desc", babyId,subject).fetch(6);
			

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
	public static void loadGrade(String babyId,String grade, String subject,Date date){
		Date testDate  = new Date(115,5,10);
	
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = format.format(date);
		
//		String queryString = "select DATE_FORMAT(date, '%Y-%c-%e') from GradeForm gf where gf.babyId = ?1 and gf.grade = ?2 and gf.subject = ?3 and DATE_FORMAT(date, '%Y-%c-%e') = '2015-6-10'";
//		
//		List<Baby> babyList =  new ArrayList<Baby>();
//			Query query = JPA.em().createQuery(queryString);
//			query.setParameter(1, babyId);//给编号为1的参数设值 
//			query.setParameter(2, grade);//给编号为1的参数设值 
//			query.setParameter(3, subject);//给编号为1的参数设值 
////			query.setParameter(4, dateStr);//给编号为1的参数设值 
//			babyList  = query.getResultList();
////		wsOk(babyList);
//		wsOk(babyList.get(0)+"---"+dateStr);
		try{
			List<GradeForm> gradeFormList = GradeForm.find("babyId=? and grade = ? and subject = ? and DATE_FORMAT(date, '%Y-%m-%d') = ?", babyId,grade,subject,dateStr).fetch(1);
			if(gradeFormList.size()!=0){
				wsOk(gradeFormList.get(0));
			}else{
//				wsOk(dateStr);
				wsError("null");
			}
		}catch(Exception e){
			wsError("异常");
		}
//		DATE_FORMAT(date, '%Y-%c-%e')
	}
	
	/**
	 * 
	 */
	/*public static void modifyGrade(){
		String babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		List<GradeForm> gradeFormList = GradeForm.find("babyId=?", babyId).fetch(6);
		GradeForm grade;
		for(int i = 0; i < gradeFormList.size(); i++){
			grade = gradeFormList.get(i);
			grade.grade_int = 1;
			grade.delete();
		}
	}*/
	
	
	
	/**
	 *加载宝宝下次接种疫苗 
	 */
	public static void loadNextVac(String babyId){
//		wsOk(BabyVac.findAll());
		/*if("".equals(babyId)||babyId==null){
			babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		}*/

		try{
//			List<BabyVac> vacList = BabyVac.find("select new BabyVac(bv.id,bv.etmDate,bv.date,bv.remindTime,bv.name,bv.monthAfter,bv.ageDcb,bv.babyId,bv.pvDisease,bv.isDone) from BabyVac bv where bv.babyId = ? and bv.isDone = 0 order by bv.monthAfter", babyId).fetch(3); 
			String queryString = "select new beans.BabyVacBean(bv.id,bv.etmDate,bv.date,bv.remindTime,v.name,v.time,v.monthAfter,v.ageDcb,bv.babyId,v.pvDisease,bv.isDone) "
					+" from BabyVac as bv,Vaccine as v where bv.vacId = v.id and bv.babyId = ?1 and bv.isDone = 0 and bv.etmDate >= curdate() order by v.monthAfter";//
			Query query = JPA.em().createQuery(queryString);
			query.setParameter(1, babyId);//给编号为1的参数设值 
			List<BabyVacBean> vacList  = query.setMaxResults(3).getResultList();
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
	 *加载宝宝所有未接种疫苗 
	 */
	public static void loadTodoVac(String babyId){
		/*if("".equals(babyId)||babyId==null){
			babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		}*/

		try{
//			List<BabyVac> vacList = BabyVac.find("select new BabyVac(bv.id,bv.etmDate,bv.date,bv.remindTime,bv.name,bv.monthAfter,bv.ageDcb,bv.babyId,bv.pvDisease,bv.isDone) from BabyVac bv where bv.babyId = ? and bv.isDone = 0 order by bv.monthAfter", babyId).fetch(); 
			String queryString = "select new beans.BabyVacBean(bv.id,bv.etmDate,bv.date,bv.remindTime,v.name,v.time,v.monthAfter,v.ageDcb,bv.babyId,v.pvDisease,bv.isDone) "
					+" from BabyVac as bv,Vaccine as v where bv.vacId = v.id and bv.babyId = ?1 and bv.isDone = 0 order by v.monthAfter";//
			Query query = JPA.em().createQuery(queryString);
			query.setParameter(1, babyId);//给编号为1的参数设值 
			List<BabyVacBean> vacList  = query.getResultList();
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
	 *只加载最近接种的3个疫苗
	 */
	public static void loadVaccinated(String babyId){
//		wsOk(BabyVac.findAll());
		/*if("".equals(babyId)||babyId==null){
			babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		}*/

		try{
//			List<BabyVac> vacList = BabyVac.find("select new BabyVac(bv.id,bv.etmDate,bv.date,bv.remindTime,bv.name,bv.monthAfter,bv.ageDcb,bv.babyId,bv.pvDisease,bv.isDone) from BabyVac bv where bv.babyId = ? and bv.isDone = 1 order by bv.monthAfter desc", babyId).fetch(3); 
//			List<BabyVac> vacList = BabyVac.find("select new BabyVac(bv.id,bv.etmDate,bv.date,bv.remindTime,bv.name,bv.monthAfter,bv.ageDcb,bv.babyId,bv.pvDisease,bv.isDone) from BabyVac bv where bv.babyId = ? and bv.isDone = 1 order by bv.monthAfter desc", babyId).fetch(3);
			String queryString = "select new beans.BabyVacBean(bv.id,bv.etmDate,bv.date,bv.remindTime,v.name,v.time,v.monthAfter,v.ageDcb,bv.babyId,v.pvDisease,bv.isDone) "
					+" from BabyVac as bv,Vaccine as v where bv.vacId = v.id and bv.babyId = ?1 and bv.isDone = 1 order by v.monthAfter desc";//
			Query query = JPA.em().createQuery(queryString);
			query.setParameter(1, babyId);//给编号为1的参数设值 
			List<BabyVacBean> vacList  = query.setMaxResults(3).getResultList();
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
		/*if("".equals(babyId)||babyId==null){
			babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		}*/

		try{
//			List<BabyVac> vacList = BabyVac.find("select new BabyVac(bv.id,bv.etmDate,bv.date,bv.remindTime,bv.name,bv.monthAfter,bv.ageDcb,bv.babyId,bv.pvDisease,bv.isDone) from BabyVac bv where bv.babyId = ? and  bv.isDone = 1 order by bv.monthAfter desc", babyId).fetch(); 
			String queryString = "select new beans.BabyVacBean(bv.id,bv.etmDate,bv.date,bv.remindTime,v.name,v.time,v.monthAfter,v.ageDcb,bv.babyId,v.pvDisease,bv.isDone) "
					+" from BabyVac as bv,Vaccine as v where bv.vacId = v.id and bv.babyId = ?1 and bv.isDone = 1 order by v.monthAfter";//
			Query query = JPA.em().createQuery(queryString);
			query.setParameter(1, babyId);//给编号为1的参数设值 
			List<BabyVacBean> vacList  = query.getResultList();
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
		/*if("".equals(babyId)||babyId==null){
			babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		}*/

		try{
//			List<BabyVac> vacList = BabyVac.find("select new BabyVac(bv.id,bv.etmDate,bv.date,bv.remindTime,bv.name,bv.monthAfter,bv.ageDcb,bv.babyId,bv.pvDisease,bv.isDone) from BabyVac bv where bv.babyId = ? and  bv.name like '%"+searchWord+"%' order by bv.monthAfter", babyId).fetch(); 
			String queryString = "select new beans.BabyVacBean(bv.id,bv.etmDate,bv.date,bv.remindTime,v.name,v.time,v.monthAfter,v.ageDcb,bv.babyId,v.pvDisease,bv.isDone) "
					+" from BabyVac as bv,Vaccine as v where bv.vacId = v.id and bv.babyId = ?1 and v.name like '%"+searchWord+"%'  order by v.monthAfter";//
			Query query = JPA.em().createQuery(queryString);
			query.setParameter(1, babyId);//给编号为1的参数设值 
			List<BabyVacBean> vacList  = query.getResultList();
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
		/*if("".equals(babyId)||babyId==null){
			babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		}*/
		try{
//			List<BabyVac> vacList = BabyVac.find("select new BabyVac(bv.id,bv.etmDate,bv.date,bv.remindTime,bv.name,bv.monthAfter,bv.ageDcb,bv.babyId,bv.pvDisease,bv.isDone) from BabyVac bv where bv.babyId = ?  order by bv.monthAfter", babyId).fetch(); 
			String queryString = "select new beans.BabyVacBean(bv.id,bv.etmDate,bv.date,bv.remindTime,v.name,v.time,v.monthAfter,v.ageDcb,bv.babyId,v.pvDisease,bv.isDone) "
					+" from BabyVac as bv,Vaccine as v where bv.vacId = v.id and bv.babyId = ?1 order by v.monthAfter";//
			Query query = JPA.em().createQuery(queryString);
			query.setParameter(1, babyId);//给编号为1的参数设值 
			List<BabyVacBean> vacList  = query.getResultList();
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
	public static boolean initBabyVac(String openid,Date birthday, String babyId){
		/*if("".equals(babyId) || babyId == null){
			babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		}*/
		List<Remind> remindList = null;
		Remind remind = null;
		if(birthday == null){
			Baby baby = Baby.findById(babyId);
			birthday = baby.date;
		}
		try{
			remindList = Remind.find("byOpenid", openid).fetch();
		}catch(Exception e){
			//throw e;
			return false;
		}
		if(remindList != null && remindList.size() != 0){
			remind = remindList.get(0);
		}else{
			//return false;//("没有找到该用户提醒信息");
			remind = new Remind();
			remind.openid = openid;
			remind.medremind = 0;
			remind.med_adv_day = 7;
			remind.healremind = 0;
			remind.heal_adv_day = 7;
			remind.yiremind = 0;
			remind.yi_adv_day = 7;
			remind.save();
		}
		
		List<Vaccine> vacList = Vaccine.findAll();
//		wsOk(vacList);
		int length = vacList.size();
		int advDay = remind.yi_adv_day;
		try{
			for(int i = 0 ; i < length ; i++){
				Vaccine vac =  vacList.get(i);
				Calendar cld = Calendar.getInstance();
				cld.setTime(birthday);
				BabyVac babyVac = new BabyVac();
				cld.add(Calendar.MONTH,vac.monthAfter);
				babyVac.babyId = babyId;
				if("乙脑灭活疫苗(第二次)".equals(vac.name)){//如果是乙脑灭活疫苗(第二次)，则从预计接种时间+8天
					cld.add(Calendar.DATE,8);
				}
				babyVac.etmDate = cld.getTime();
				babyVac.remindTime = DateUtil.dateAdd(birthday,-advDay);
				babyVac.vacId = vac.id;
				babyVac.isDone = "0";//"0"表示未来接种
				babyVac.save();
			}
		//	wsOk("初始化疫苗列表成功");
			return true;
		}catch(Exception e){
			return false;
		//	wsError("初始化疫苗列表失败");
		}
		
	}
	
	/**
	 * 初始化宝宝疫苗接种数据内用
	 *	birthday:宝宝出生日期
	 *  babyId: 宝宝Id
	 */
	public static String initBabyVacForIn(String babyId){
		
		Baby baby = Baby.findById(babyId);
		Date birthday = baby.date;
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
				babyVac.babyId = babyId;
				if("乙脑灭活疫苗(第二次)".equals(vac.name)){//如果是乙脑灭活疫苗(第二次)，则从预计接种时间+8天
					cld.add(Calendar.DATE,8);
				}
				babyVac.etmDate = cld.getTime();
				babyVac.vacId = vac.id;
				babyVac.isDone = "0";//"0"表示未来接种
				babyVac.save();
			}
			
		}catch(Exception e){
			return e.getMessage();//"初始化疫苗列表失败";
		}
		return "成功" + baby.name + "--" + baby.id;
		
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
//	public static void modifyVacEtm(String babyId,Date etm){
//		try{
//			List<BabyVac> babyVacList = BabyVac.find("babyId = ?",babyId).fetch();//BabyVac.find("select new BabyVac(bv.id,bv.etmDate,bv.date,bv.name,bv.monthAfter,bv.ageDcb,bv.babyId,bv.pvDisease,bv.isDone) from BabyVac bv where bv.babyId = ?  order by bv.monthAfter", babyId).fetch(29); 
//			List<Vaccine> vacList = Vaccine.findAll();
//			int count = babyVacList.size();
//			int vacNum = vacList.size();
//			if(count != 0){
//				for(int i = 0; i < count; i++ ){
//					BabyVac babyVac = babyVacList.get(i);
//					int monthAfter = 0;
//					Vaccine vac = new Vaccine();
//					for(int j = 0; j < vacNum; j++){
//						vac = vacList.get(j);
//						if(babyVac.vacId.equals(vac.id)){
//							monthAfter = vac.monthAfter;
//							break;
//						}
//					}
//					Calendar cld = Calendar.getInstance();
//					cld.setTime(etm);
//					cld.add(Calendar.MONTH,monthAfter);
//					if("乙脑灭活疫苗(第二次)".equals(vac.name)){//如果是乙脑灭活疫苗(第二次)，则从预计接种时间+8天
//						cld.add(Calendar.DATE,8);
//					}
//					babyVac.etmDate = cld.getTime();
//					babyVac.save();
//				}
//			
//			}
//			wsOk("成功");
//		}catch(Exception e){
//			wsError("异常");
//		}
//	}
	
	/**
	 * 给现有的所有宝宝初始化疫苗数据
	 */
	public static void initExistBabyVac(){
		List<Baby> babyList = Baby.findAll();
		int count = babyList.size();
		List result = new ArrayList();
		for(int i = 0; i < count; i++){
//			initBabyVac(null,babyList.get(i).id);
			result.add(initBabyVacForIn(babyList.get(i).id));
		}
		wsOk(result);
	}

	
	/**
	 * 清除所有宝宝疫苗数据
	 */
	public static void clearAllBabyVac(){
		try{
			BabyVac.deleteAll();
			wsOk("删除成功");
		}catch(Exception e){
			wsError("失败");
		}
		
		
	}
	
	/**
	 * 清除所有疫苗数据
	 */
	public static void clearAllVaccine(){
		Vaccine.deleteAll();
	}
	
	/**
	 * 加载所有宝宝id为BabyId疫苗数据
	 */
	public static void loadAllBabyVac(String babyId){
		/*if("".equals(babyId) || babyId == null){
			babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		}*/
		List<BabyVac> babyVacList = BabyVac.find("babyId = ?", babyId).fetch();
		wsOk(babyVacList);
	}
	
	public static void loadAllBabyVacForIn(){
		List<BabyVac> babyVacList = BabyVac.findAll();
		wsOk(babyVacList);
	
	}
	
	
//	
	/**
	 * 加载所有疫苗数据
	 */
	public static void loadAllVaccine(){
		List<Vaccine> vaccineList = Vaccine.findAll();
		wsOk(vaccineList); 
	}
	
	/**
	 * 加载所有疫苗数据
	 */
	public static void loadAllBaby(){
		List<Baby> babyList = Baby.findAll();
		wsOk(babyList);
	}
	
	/**
	 * 初始化疫苗列表
	 */
	public static void initVacList(){
		/*-----1-----*/
		Vaccine vac = new Vaccine();
		vac.name = "乙肝疫苗(第一次)";
		vac.monthAfter = 0;
		vac.hasInteral = true;
		vac.interalVacName = "乙肝疫苗(第二次)";
		vac.monthInteral = 0;
		vac.dayInteral = 28;//与第二针间隔28天以上
		vac.pvDisease = "乙型病毒性肝炎";
		vac.time = "1";
		vac.ageDcb = "出生时";
		vac.save();
		
		/*-----2-----*/
		vac = new  Vaccine();
		vac.name = "卡介苗(第一次)";
		vac.monthAfter = 0;
		vac.hasInteral = false;
		vac.interalVacName = "";
		vac.monthInteral = 0;
		vac.dayInteral = 0;
		vac.pvDisease = "结核病";
		vac.time = "1";
		vac.ageDcb = "出生时";
		vac.save();

		/*-----3-----*/
		vac = new  Vaccine();
		vac.name = "乙肝疫苗(第二次)";
		vac.monthAfter = 1;
		vac.hasInteral = false;
		vac.interalVacName = "";
		vac.monthInteral = 0;
		vac.dayInteral = 0;
		vac.pvDisease = "乙型病毒性肝炎";
		vac.time = "2";
		vac.ageDcb = "1月龄";
		vac.save();

		/*-----4-----*/
		vac = new  Vaccine();
		vac.name = "脊灰疫苗(第一次)";
		vac.monthAfter = 2;
		vac.hasInteral = true;
		vac.interalVacName = "脊灰疫苗(第二次)";
		vac.monthInteral = 0;
		vac.dayInteral = 28;//与第二针间隔28天以上
		vac.pvDisease = "脊髓灰质炎(小儿麻痹)";
		vac.time = "1";
		vac.ageDcb = "2月龄";
		vac.save();

		/*-----5-----*/
		vac = new  Vaccine();
		vac.name = "脊灰疫苗(第二次)";
		vac.monthAfter = 3;
		vac.hasInteral = true;
		vac.interalVacName = "脊灰疫苗(第三次)";
		vac.monthInteral = 0;
		vac.dayInteral = 28;//与第三针间隔28天以上
		vac.pvDisease = "脊髓灰质炎(小儿麻痹)";
		vac.time = "2";
		vac.ageDcb = "3月龄";
		vac.save();

		/*-----6-----*/
		vac = new  Vaccine();
		vac.name = "百白破疫苗(第一次)";
		vac.monthAfter = 3;
		vac.hasInteral = true;
		vac.interalVacName = "百白破疫苗(第二次)";
		vac.monthInteral = 0;
		vac.dayInteral = 28;//与第二针间隔28天以上
		vac.pvDisease = "百日咳、白喉、破伤风";
		vac.time = "1";
		vac.ageDcb = "3月龄";
		vac.save();
		
		/*-----7-----*/
		vac = new  Vaccine();
		vac.name = "脊灰疫苗(第三次)";
		vac.monthAfter = 4;
		vac.hasInteral = false;
		vac.interalVacName = "";
		vac.monthInteral = 0;
		vac.dayInteral = 0;
		vac.pvDisease = "脊髓灰质炎(小儿麻痹)";
		vac.time = "3";
		vac.ageDcb = "4月龄";
		vac.save();
		
		/*-----8-----*/
		vac = new  Vaccine();
		vac.name = "百白破疫苗(第二次)";
		vac.monthAfter = 4;
		vac.hasInteral = true;
		vac.interalVacName = "百白破疫苗(第三次)";
		vac.monthInteral = 0;
		vac.dayInteral = 28;//与第三针间隔28天以上
		vac.pvDisease = "百日咳、白喉、破伤风";
		vac.time = "2";
		vac.ageDcb = "4月龄";
		vac.save();
		
		/*-----9-----*/
		vac = new  Vaccine();
		vac.name = "百白破疫苗(第三次)";
		vac.monthAfter = 5;
		vac.hasInteral = false;
		vac.interalVacName = "";
		vac.monthInteral = 0;
		vac.dayInteral = 0;
		vac.pvDisease = "百日咳、白喉、破伤风";
		vac.time = "3";
		vac.ageDcb = "5月龄";
		vac.save();
		
		/*-----10-----*/
		vac = new  Vaccine();
		vac.name = "乙肝疫苗(第三次)";
		vac.monthAfter = 6;
		vac.hasInteral = false;
		vac.interalVacName = "";
		vac.monthInteral = 0;
		vac.dayInteral = 0;
		vac.pvDisease = "乙型病毒性肝炎";
		vac.time = "3";
		vac.ageDcb = "6月龄";
		vac.save();
		
		/*-----11-----*/
		vac = new  Vaccine();
		vac.name = "A群流脑疫苗(第一次)";
		vac.monthAfter = 6;
		vac.hasInteral = true;
		vac.interalVacName = "A群流脑疫苗(第二次)";
		vac.monthInteral = 3;//与第二针间隔>=3个月
		vac.dayInteral = 0;
		vac.pvDisease = "流行性脑脊髓膜炎";
		vac.time = "1";
		vac.ageDcb = "6月龄";
		vac.save();
		
		/*-----12-----*/
		vac = new  Vaccine();
		vac.name = "麻风疫苗";
		vac.monthAfter = 8;
		vac.hasInteral = false;
		vac.interalVacName = "";
		vac.monthInteral = 0;
		vac.dayInteral = 0;
		vac.pvDisease = "麻疹";
		vac.time = "1";
		vac.ageDcb = "8月龄";
		vac.save();
		
		/*-----13-----*/
		vac = new  Vaccine();
		vac.name = "乙脑减毒活疫苗(第一次)";
		vac.monthAfter = 8;
		vac.hasInteral = false;
		vac.interalVacName = "";
		vac.monthInteral = 0;
		vac.dayInteral = 0;
		vac.pvDisease = "流行性乙型脑炎";
		vac.time = "1";
		vac.ageDcb = "8月龄";
		vac.save();
		
		/*-----14-----*/
		vac = new  Vaccine();
		vac.name = "乙脑灭活疫苗(第一次)";
		vac.monthAfter = 8;
		vac.hasInteral = true;
		vac.interalVacName = "乙脑灭活疫苗(第二次)";
		vac.monthInteral = 0;
		vac.dayInteral = 8;//与第二针间隔8天
		vac.pvDisease = "流行性乙型脑炎";
		vac.time = "1";
		vac.ageDcb = "8月龄";
		vac.save();
		
		/*-----15-----另外处理*/
		vac = new  Vaccine();
		vac.name = "乙脑灭活疫苗(第二次)";
		vac.monthAfter = 8;
		vac.hasInteral = false;
		vac.interalVacName = "";
		vac.monthInteral = 0;
		vac.dayInteral = 0;
		vac.pvDisease = "流行性乙型脑炎";
		vac.time = "1";
		vac.ageDcb = "8月龄";
		vac.save();
		
		/*-----16-----*/
		vac = new  Vaccine();
		vac.name = "A群流脑疫苗(第二次)";
		vac.monthAfter = 9;
		vac.hasInteral = true;
		vac.interalVacName = "A+C流脑疫苗(第一次)";
		vac.monthInteral = 12;//与A+C流脑疫苗(第一次)间隔12个月
		vac.dayInteral = 0;
		vac.pvDisease = "流行性脑脊髓膜炎";
		vac.time = "2";
		vac.ageDcb = "9月龄";
		vac.save();
		
		
		
		/*-----17-----*/
		vac = new  Vaccine();
		vac.name = "甲肝减毒疫苗 ";
		vac.monthAfter = 18;
		vac.hasInteral = false;
		vac.interalVacName = "";
		vac.monthInteral = 0;
		vac.dayInteral = 0;
		vac.pvDisease = "甲型病毒性肝炎";
		vac.time = "1";
		vac.ageDcb = "1岁半";
		vac.save();
		
		/*-----18-----*/
		vac = new  Vaccine();
		vac.name = "甲肝灭活疫苗 (第一次)";
		vac.monthAfter = 18;
		vac.hasInteral = true;
		vac.interalVacName = "甲肝灭活疫苗 (第二次)";
		vac.monthInteral = 6;//与第二针间隔>=6个月
		vac.dayInteral = 0;
		vac.pvDisease = "甲型病毒性肝炎";
		vac.time = "1";
		vac.ageDcb = "1岁半";
		vac.save();
		
		/*-----19----*/
		vac = new  Vaccine();
		vac.name = "百白破疫苗(第四次)";
		vac.monthAfter = 18;
		vac.hasInteral = false;
		vac.interalVacName = "";
		vac.monthInteral = 0;
		vac.dayInteral = 0;
		vac.pvDisease = "百日咳、白喉、破伤风";
		vac.time = "4";
		vac.ageDcb = "1岁半";
		vac.save();
		
		/*-----20-----*/
		vac = new  Vaccine();
		vac.name = "麻腮风疫苗";
		vac.monthAfter = 18;
		vac.hasInteral = false;
		vac.interalVacName = "";
		vac.monthInteral = 0;
		vac.dayInteral = 0;
		vac.pvDisease = "麻疹、风疹、腮腺炎";
		vac.time = "1";
		vac.ageDcb = "1岁半";
		vac.save();
		
		/*-----21-----*/
		vac = new  Vaccine();
		vac.name = "乙脑减毒疫苗(第二次)";
		vac.monthAfter = 24;
		vac.hasInteral = false;
		vac.interalVacName = "";
		vac.monthInteral = 0;
		vac.dayInteral = 0;
		vac.pvDisease = "流行性乙型脑炎";
		vac.time = "2";
		vac.ageDcb = "2岁";
		vac.save();
		
		
		/*-----22-----*/
		vac = new  Vaccine();
		vac.name = "乙脑灭活疫苗(第三次)";
		vac.monthAfter = 24;
		vac.hasInteral = false;
		vac.interalVacName = "";
		vac.monthInteral = 0;
		vac.dayInteral = 0;
		vac.pvDisease = "流行性乙型脑炎";
		vac.time = "3";
		vac.ageDcb = "2岁";
		vac.save();
		
		/*-----23-----*/
		vac = new  Vaccine();
		vac.name = "甲肝灭活疫苗(第二次)";
		vac.monthAfter = 24;
		vac.hasInteral = false;
		vac.interalVacName = "";
		vac.monthInteral = 0;
		vac.dayInteral = 0;
		vac.pvDisease = "甲型病毒性肝炎(与前剂间隔6-12个月)";
		vac.time = "2";
		vac.ageDcb = "2岁";
		vac.save();
		
		/*-----24-----*/
		vac = new  Vaccine();
		vac.name = "A+C流脑疫苗(第一次)";
		vac.monthAfter = 36;
		vac.hasInteral = true;
		vac.interalVacName = "A+C流脑疫苗(第二次)";
		vac.monthInteral = 36;//与下一针间隔3年
		vac.dayInteral = 0;
		vac.pvDisease = "流行性脑脊髓膜炎";
		vac.time = "1";
		vac.ageDcb = "3岁";
		vac.save();
		
		/*----25-----*/
		vac = new  Vaccine();
		vac.name = "脊灰疫苗(第四次)";
		vac.monthAfter = 48;
		vac.hasInteral = false;
		vac.interalVacName = "";
		vac.monthInteral = 0;
		vac.dayInteral = 0;
		vac.pvDisease = "脊髓灰质炎(小儿麻痹)";
		vac.time = "4";
		vac.ageDcb = "4岁";
		vac.save();
		
		/*-----26-----*/
		vac = new  Vaccine();
		vac.name = "白破疫苗";
		vac.monthAfter = 72;
		vac.hasInteral = false;
		vac.interalVacName = "";
		vac.monthInteral = 0;
		vac.dayInteral = 0;
		vac.pvDisease = "百日咳、白喉、破伤风";
		vac.time = "1";
		vac.ageDcb = "6岁";
		vac.save();
		
		
		/*-----27-----*/
		vac = new  Vaccine();
		vac.name = "乙脑灭活疫苗(第四次)";
		vac.monthAfter = 72;
		vac.hasInteral = false;
		vac.interalVacName = "";
		vac.monthInteral = 0;
		vac.dayInteral = 0;
		vac.pvDisease = "流行性乙型脑炎";
		vac.time = "4";
		vac.ageDcb = "6岁";
		vac.save();
		
		/*-----28-----*/
		vac = new  Vaccine();
		vac.name = "A+C流脑疫苗(第二次)";
		vac.monthAfter = 72;
		vac.hasInteral = false;
		vac.interalVacName = "";
		vac.monthInteral = 0;
		vac.dayInteral = 0;
		vac.pvDisease = "流行性脑脊髓膜炎";
		vac.time = "2";
		vac.ageDcb = "6岁";
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
	
	/**
	 * 根据用户生日计算年龄
	 */
	public static int[] getAgeByBirthday(Date recordDate,Date birthday) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(recordDate);

		if (cal.before(birthday)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;
		int month = monthNow - monthBirth;
		int day = dayOfMonthNow - dayOfMonthBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth 
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
					month += 11;
				}
			} else {
				// monthNow>monthBirth 
				age--;
				month += 12;
			}
		}
		
		if(day < 0){
			day += cal.getActualMaximum(Calendar.DATE);
		}
		// age;
		int[] ageList = {age,month,day};
		return ageList;
	}
	
	
}
