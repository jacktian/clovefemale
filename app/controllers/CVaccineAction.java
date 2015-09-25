package controllers;

import java.util.Date;
import java.util.List;

import javax.mail.Session;
import javax.persistence.Query;

import play.db.jpa.JPA;
import beans.BabyVacBean;

import models.BabyVac;
import models.Remind;
import models.Baby;

import utils.DateUtil;
/**
 * 宝宝疫苗控制器
 * @author cater
 *
 */
public class CVaccineAction extends WebService{
	/**
	 *加载宝宝下次接种疫苗 
	 */
	public static void loadNextVac(String babyId){
		if("".equals(babyId)||babyId==null){
			wsError("操作异常-宝宝id为空");
		}

		try{
			String queryString = "select new beans.BabyVacBean(bv.id,bv.etmDate,bv.date,bv.remindTime,v.name,v.time,v.monthAfter,v.ageDcb,bv.babyId,v.pvDisease,bv.isDone) "
					+" from BabyVac as bv,Vaccine as v where bv.vacId = v.id and bv.babyId = ?1 and bv.isDone = 0 order by v.monthAfter";//
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
		if("".equals(babyId)||babyId==null){
			wsError("操作异常-宝宝id为空");
		}

		try{
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
		if("".equals(babyId)||babyId==null){
			wsError("操作异常-宝宝id为空");
		}
		try{
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
		if("".equals(babyId)||babyId==null){
			wsError("操作异常-宝宝id为空");
		}

		try{
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
		if("".equals(babyId)||babyId==null){
			wsError("操作异常-宝宝id为空");
		}

		try{
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
	 *加载宝宝所有疫苗 
	 *
	 */
	public static void loadAllVac(String babyId){
		if("".equals(babyId)||babyId==null){
			wsError("操作异常-宝宝id为空");
		}
		try{
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
	
}
