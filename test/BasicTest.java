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
	}
	

}
