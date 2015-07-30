package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import beans.MedboxBean;
import play.db.jpa.JPA;
import models.Baby;
import models.DrugStore;
import models.Medicine;
import models.MedicineBox;

import utils.MedboxInit;

/**
 * 药箱控制器
 *
 * @author boxiZen
 * @since 2015/05/12
 */
public class CMedicine extends WebService{

	/**
	 * 添加药箱
	 */
	public static void addMedBox(String name,String mark){
		String openid = session.get("openid");
		//openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		if(openid == null){
			wsError("创建失败");
		}
		else{
			try{
				MedicineBox medBox = new MedicineBox();
				medBox.userId = openid;
				medBox.name = name;
				medBox.mark = mark;
				medBox.disabled = 0;
				medBox.save();
				wsOk("创建成功");
			}
			catch(Exception e){
				wsError("创建失败");
			}
		}
	}

	/**
	 * 删除药箱
	 */
	public static void removeMedBox(String mid){
		String[] medBoxId = mid.split(",");
		MedicineBox medBox = null;
		try{
			for(int i=0;i<medBoxId.length;i++){
				medBox = MedicineBox.findById(medBoxId[i]);
				if(medBox!=null){
					medBox.delete();
					List<Medicine> medList = Medicine.find("byMedicineBoxId",medBoxId[i]).fetch();
					for(int j=0;j<medList.size();j++){
						medList.get(j).delete();
					}
				}
			}
			wsOk("删除成功");
		}
		catch(Exception ex){
			wsError("删除失败");
		}
	}

	/**
	 * 加载药箱
	 */
	public static void loadMedboxList(){

		String openid = session.get("openid");
		//openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		List<MedicineBox> medboxList = MedicineBox.find("byUserId", openid).fetch();
		List<MedboxBean> medboxBean = new ArrayList<MedboxBean>();
		for(int i=0;i<medboxList.size();i++){
			MedboxBean bean = new MedboxBean();
			int count = (int) Medicine.count("byMedicineBoxId", medboxList.get(i).id);
			bean.id = medboxList.get(i).id;
			bean.name = medboxList.get(i).name;
			bean.count = count;
			bean.mark = medboxList.get(i).mark;
			bean.disabled = medboxList.get(i).disabled;
			medboxBean.add(bean);
		}
		wsOk(medboxBean);
	}

	/**
	 * 加载药品
	 */
	public static void loadMedicineList(){
		String medboxid = session.get("medboxid");
		List<Medicine> medicineList = Medicine.find("byMedicineBoxId", medboxid).fetch();
		//List<MedicineBox> medboxList = MedicineBox.findAll();
		wsOk(medicineList);
	}

	/**
	 * 加载所有药品
	 */
	public static void loadAllMedicine(){
		List<Medicine> medicineList = Medicine.findAll();
		//List<MedicineBox> medboxList = MedicineBox.findAll();
		wsOk(medicineList);
	}

	/**
	 * 添加药品
	 */
	public static void addMedicine(String name,String mark,String prodDate,String endDate){
		String medboxid = session.get("medboxid");
		if(medboxid == null){
			wsError("创建失败");
		}
		else{
			try{
				Medicine med = new Medicine();
				med.name = name;
				med.function = mark;
				if(prodDate!=null&&!prodDate.equals("undefined")&&!prodDate.equals(""))
					med.produce = new Date(prodDate);
				med.deadline = new Date(endDate);
				med.medicineBoxId = medboxid;
				med.save();
				wsOk("创建成功");
			}
			catch(Exception e){
				e.printStackTrace();
				wsError("创建失败");
			}
		}
	}

	/**
	 * 修改药品
	 */
	public static void altMedicine(String id,String name,String mark,String prodDate,String endDate,String code){
		Medicine medicine = Medicine.findById(id);
		try{
			medicine.name = name;
			medicine.function = mark;
			if(prodDate!=null&&!prodDate.equals("undefined")&&!prodDate.equals(""))
				medicine.produce = new Date(prodDate);
			else
				medicine.produce = null;
			if(endDate!=null&&!endDate.equals("undefined")&&!endDate.equals(""))
				medicine.deadline = new Date(endDate);
			else
				medicine.deadline = null;
			medicine.code = code;
			medicine.save();
			wsOk("更新成功");
		}
		catch(Exception e){
			e.printStackTrace();
			wsError("更新错误");
		}
	}

	/**
	 * 删除药品
	 */
	public static void removeMedicine(String mid){
		String[] medId = mid.split(",");
		Medicine medicine = null;
		try{
			for(int i=0;i<medId.length;i++){
				medicine = Medicine.findById(medId[i]);
				if(medicine!=null){
					medicine.delete();
				}
			}
			wsOk("删除成功");
		}
		catch(Exception ex){
			wsError("删除失败");
		}
	}

	/**
	 * 搜索药箱
	 */
	public static void searchMedbox(String medBoxName){
		String openid = session.get("openid");
		// openid = "ob1R-uD5CgT-x-FEdtMIgAWYr4Vs";
		List<MedicineBox> medbox = null;
		if(medBoxName.equals("")||medBoxName.equals(" ")){
			//medicine = Medicine.find("", medboxid).fetch();
			String sql = "select m.id,m.name,m.mark,m.disabled from MedicineBox m where m.user_Id = '" +openid+"'";
			medbox = JPA.em().createNativeQuery(sql).getResultList();
		}
		else{
			String sql = "select m.id,m.name,m.mark,m.disabled from MedicineBox m where m.name like '%"+medBoxName+"%' and m.user_Id = '"+openid+"'";
			medbox = JPA.em().createNativeQuery(sql).getResultList();
		}
		wsOk(medbox);
	}

	/**
	 * 搜索药品
	 */
	public static void searchMedicine(String medName){
		List<Medicine> medicine = null;
		String medboxid = session.get("medboxid");
		if(medName.equals("")||medName.equals(" ")||medName==null){
			System.out.println("走到这里来了");
			String sql = "select m.id,m.name,m.function,m.deadline from Medicine m where m.medicineBox_Id = '" +medboxid+"'";
			medicine = JPA.em().createNativeQuery(sql).getResultList();
			System.out.println(medicine.size());
		}
		else{
			String sql = "select m.id,m.name,m.function,m.deadline from Medicine m where m.name like '%"+medName+"%' and m.medicineBox_Id ='"+medboxid+"'";
			medicine = JPA.em().createNativeQuery(sql).getResultList();
		}
		wsOk(medicine);
	}

	/**
	 * 测试
	 */
	public static void test(){
		String openid = session.get("openid");
		List<MedicineBox> medbox = null;
		String sql = "select m.id,m.name,m.mark from MedicineBox m where m.user_Id= '"+openid+"";
		medbox = JPA.em().createNativeQuery(sql).getResultList();
		wsOk(medbox);
	}

	/**
	 * 查询药库
	 */
	public static void findDrug(String code){
		DrugStore drug = DrugStore.find("byCode", code).first();
		wsOk(drug);
	}

	/**
	 * 初始化药箱
	 */
	 public static void initMedbox(String openid){
		 try{
		 	MedboxInit.init(openid);
	 			wsOk("成功");
			}
			catch(Exception e){
				wsError("出错");
			}

	 }
}
