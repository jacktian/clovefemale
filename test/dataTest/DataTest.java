package dataTest;

import java.util.List;

import javax.persistence.Query;

import models.GradeCondition;

import org.junit.Test;

import play.db.jpa.JPA;

public class DataTest {

	@Test
	public void test() {
		try {
			Query q = JPA
					.em()
					.createQuery(
							"select GradeCondition.* from GradeCondition,"
									+ "(select id,max(date) date,subject from GradeCondition group by id,subject) t1"
									+ " where GradeCondition.date=t1.date and GradeCondition.id=t1.id and"
									+ " GradeCondition.subject=t1.subject and GradeCondition.subject = '语文' ");
			int a = q.getMaxResults();
			System.out.println(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2(){
		List<GradeCondition> list = GradeCondition.find("select GradeCondition.* from GradeCondition,"+
                "(select id,max(date) date, subject from GradeCondition group by id,subject) t1"+
                " where GradeCondition.date=t1.date and GradeCondition.id=t1.id and"+
                " mark.subject=t1.subject and GradeCondition.subject=?","语文").fetch();
	}

}
