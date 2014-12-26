import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.Baby;
import models.BodyIndex;
import models.MedicineBox;
import models.User;

import org.junit.Before;
import org.junit.Test;

import com.sun.org.apache.bcel.internal.generic.NEW;

import play.test.Fixtures;
import play.test.UnitTest;

public class BasicTest extends UnitTest {

	@Before
    public void setup() {
        Fixtures.deleteDatabase();
    }
	/*
	 * 添加一个用户和宝宝，并通过宝宝id查询所属用户名称
	 * */
	@Test
	public  void UserBaby(){
		User user  = new User("yyp", "1234", "ppy","15800031641","yyp@163.com","4416231992").save();
		Baby baby = new Baby(new Date(), "man", "baby1",user.id).save();
		/*MedicineBox mBox = new MedicineBox();
		mBox.userId=user.id;
		mBox.name="yyp";*/
		/*UserToBaby userToBaby = new UserToBaby(user.id, baby.id).save();
		UserToBaby user2 = userToBaby.findByBaby(baby.id).get(0);
		User user3 = User.findById(user2.userId);
		String b = "baby1";
		List<Baby> list=Baby.find("select b from Baby b,UserToBaby ub where b.id = ub.babyId and b.name like ?","%"+b+"%" ).fetch();
		List<Baby> list1=Baby.find("select b from Baby b,UserToBaby ub where b.id = ub.babyId and b.name in (select name from Baby)").fetch();
		assertEquals("baby1", list1.get(0).name);
		assertEquals("yyp", user3.userName);*/
		List<BodyIndex> list=null;
		try{
			Date gDate=null;
			gDate=new SimpleDateFormat("YYYY-MM-dd").parse("2014-12-25");
		BodyIndex bodyIndex = new BodyIndex(gDate, 50.0,54.6, baby.id).save();
		
		
		
		 gDate= new SimpleDateFormat("YYYY-MM-dd").parse("2014-12-23");
		
		BodyIndex bodyIndex1 = new BodyIndex(gDate, 54.0,54.6, baby.id).save();
		 list= BodyIndex.find("select b from BodyIndex b where babyId = ? and date between ? and ?", baby.id,new SimpleDateFormat("YYYY-MM-dd").parse("2014-12-22 00:00:00"),new SimpleDateFormat("YYYY-MM-dd").parse("2014-12-27 23:59:59")).fetch();
		// list = BodyIndex.find("babyId = ?", baby.id).fetch();
		}catch(Exception e){
			e.printStackTrace();
		}
		//Date date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		assertEquals("50.0",list.get(1).height.toString());
	}
	

}
