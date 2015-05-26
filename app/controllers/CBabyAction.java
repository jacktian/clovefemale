package controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import play.db.jpa.JPA;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import beans.SimpleNoteBookBean;
import models.Baby;
import models.Note;
import models.BodyIndex;
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
		//String openid = session.get("openid");//从session中获取openid
		String openid = "ob1R-uIRkLLp6lmmrT4w-2rrZ5jQ";
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
	public static void addBaby(String name,String sex,Date birthday){
		//String openid = session.get("openid");//从session中获取openid
		String openid = "ob1R-uIRkLLp6lmmrT4w-2rrZ5jQ";
		Baby baby = new Baby();
		baby.date = birthday;
		baby.sex = sex;
		baby.name = name;
		baby.userId = openid;
		List<Baby> babyList = new ArrayList<Baby>();
		try{
			baby.save();
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
		String headImgUrl = "/public/images/client/" + MEDIA_ID + ".jpg";
		HttpResponse resp = WS.url("http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + ACCESS_TOKEN + "&media_id=" + MEDIA_ID).get();
		try{
			File file = new File(headImgUrl);
			FileOutputStream output = new FileOutputStream(file);
			InputStream inputStream = resp.getStream();
			byte[]  value = new byte[10];
			while(inputStream.read(value) != -1){
				output.write(value);
			}
			output.flush();
			output.close();
			Baby baby = Baby.findById(babyId);
			baby.headImgUrl = headImgUrl;
			baby.save();
		}catch(Exception e){
			
		}
		
		
	}
	
	/**
	 * 增加或修改宝宝身体指标
	 */
	public static void addOrMdfBabyIndex(String bodyIndexId,String babyId,double age,double height,double weight){
		BodyIndex bodyIndex;
		
		if(!"".equals(bodyIndexId)){//bodyIndexId不为空，表示已经是已经记录过的身体指标，此操作为修改身体指标
			try{
				bodyIndex = BodyIndex.findById(bodyIndexId);
				bodyIndex.height = height;
				bodyIndex.weight = weight;
				bodyIndex.save();
				wsOk("修改成功");
			}catch(Exception e){
				wsError("修改记录失败");
			}
			
		}else{//bodyIndexId为空，表示未级路过的身体指标，此操作为增加身体指标
			String ageDcb;
			if(age < 1){//一岁以下
				ageDcb = (int)age * 10 + "个月";
			}else{
				int age_int = (int) Math.floor(age);
				String age_digit_string = "";
				if((int)Math.round(age) - age != 0){
					age_digit_string = "半";
				}
				 
				ageDcb = age_int + "岁" + age_digit_string;
			}
			bodyIndex = new BodyIndex();
			bodyIndex.babyId = babyId;
			bodyIndex.age = age;
			bodyIndex.ageDcb = ageDcb;
			bodyIndex.height = height;
			bodyIndex.weight = weight;
			try{
				bodyIndex.save();
				wsOk("添加成功");
			}catch(Exception e){
				wsError("添加记录失败");
			}
			
			
		}
		
	}
	
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
