package controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	public static void addBaby(String name,String sex,String media_id,Date birthday){
		//String openid = session.get("openid");//从session中获取openid
		String openid = "ob1R-uIRkLLp6lmmrT4w-2rrZ5jQ";
		Baby baby = new Baby();
		baby.date = birthday;
		baby.sex = sex;
		baby.name = name;
		baby.userId = openid;
		baby.dateStr = "" + DateUtil.getAge(birthday);;	
		List<Baby> babyList = new ArrayList<Baby>();
		try{
			baby.save();
			String downloadResult = downloadBabyImg(baby.id,media_id);
			if(downloadResult.equals("error")){
				downloadResult = downloadBabyImg(baby.id,media_id);//再次调用
				if(downloadResult.equals("error")){//两次调用失败
//					wsOk("宝宝添加成功!</tr>头像获取失败,请在宝宝资料重新上传!");
					babyList.add(baby);
					wsOk(babyList);
				}
			}
			baby.headImgUrl = downloadResult;
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
	private static String downloadBabyImg(String babyId,String media_id){
		List<WeChat> weChatList = WeChat.all().fetch(1,1);
		String ACCESS_TOKEN = weChatList.get(0).access_token;

		String MEDIA_ID = media_id;
		String headImgUrl ="babyimage/" + babyId + media_id + ".jpg";

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
		String downloadResult = downloadBabyImg(babyId,media_id);
		try{
			if(downloadResult.equals("error")){
				downloadResult = downloadBabyImg(babyId,media_id);//再次调用
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
	
	
	public static void downloadBabyImg2(){
		String babyId = "";
		; 
		List<WeChat> weChatList = WeChat.all().fetch(1,1);
		String ACCESS_TOKEN = weChatList.get(0).access_token;
		String MEDIA_ID = "tFkgoV7R6idCdyHgOrg9jVIKv_7CPU6BBu6nm0UZwiO_jBgjX4F67PTMPHkvR4uc";//media_id;
		String headImgUrl ="babyimage/" + MEDIA_ID + ".jpg";
//		wsOk(MEDIA_ID+"---");
		
		HttpResponse resp = WS.url("http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + ACCESS_TOKEN + "&media_id=" + MEDIA_ID).get();
	//	HttpResponse resp = WS.url("http://localhost:9001/babyimage/boy.png").get();
		try{
			File file = new File(headImgUrl);
//			wsOk(file.getAbsolutePath());
			FileOutputStream output = new FileOutputStream(file);
//			resp.getString();
			
			InputStream inputStream = resp.getStream();
			//得到图片的二进制数据，以二进制封装得到数据，具有通用性  
	        byte[] data = readInputStream(inputStream);  
			 
	        output.write(data);
			output.flush();
			output.close();
			Baby baby = Baby.findById(babyId);
			baby.headImgUrl = "/" + headImgUrl;
			baby.save();
			wsOk(baby);
//			wsOk(resp.getString());
		}catch(Exception e){
			wsError(e.getMessage());
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
				wsOk("修改记录成功");
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
				wsOk("添加记录成功");
			}catch(Exception e){
				wsError("添加记录失败");
			}
			
			
		}
		
	}
	
	/**
	 * 加载成长记录数据
	 */
	public static void loadBabyGrowth(){
		String babyId = "1BFB8CDDECC24BE49F8D3C5B9528BBB0";
		List<BodyIndex> bodyIndexList = BodyIndex.find("select new BodyIndex(bi.date,bi.height,bi.weight,bi.babyId,bi.age,bi.ageDcb) from BodyIndex bi where babyId = ? Order by bi.age", babyId).fetch(6);
		wsOk(bodyIndexList);
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
