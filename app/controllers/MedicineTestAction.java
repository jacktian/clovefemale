package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Medicine;
import models.MedicineBox;
import beans.JsonpResultBean;
import beans.TestBean;

public class MedicineTestAction extends WebService{
	
	/**
	 * 查找所有药箱
	 */
	public static void findAllMedBox(){
		List<MedicineBox> list = MedicineBox.findAll();
		wsOkAsExtJsonP(list);
	}
	
	/**
	 * 添加药箱
	 */
	public static void addMedBox(String medBoxName,String medBoxMark,String userId){
		Date date = new Date();
		MedicineBox medBox = new MedicineBox(medBoxName,date,medBoxMark,userId);
		JsonpResultBean bean = new JsonpResultBean();
		try{
			medBox.save();
			bean.result = 1;
			bean.rtnStr = "药箱添加成功!";
			wsOkAsExtJsonP(bean);
		}
		catch(Exception e){
			bean.result = 0;
			bean.rtnStr = "药箱添加失败!";
			wsOkAsExtJsonP(bean);
		}
	}
	
	/**
	 * 添加药品
	 */
	public static void addMedicine(String name,String func,String type,String deadline,String medBoxId){
		JsonpResultBean bean = new JsonpResultBean();
		
		Medicine med = new Medicine();
		med.name = name;
		med.function = func;
		med.medicineBoxId = medBoxId;
		med.type = type;
		med.deadline = deadline;
		try{
			med.save();
			bean.result = 1;
			bean.rtnStr = "药品添加成功!";
			wsOkAsExtJsonP(bean);
		}
		catch(Exception e){
			bean.result = 0;
			bean.rtnStr = "药品添加失败!";
			wsOkAsExtJsonP(bean);
		}
	}
	
	/**
	 * 修改药品
	 */
	public static void alterMedicine(String name,String func,String type,String deadline,String medBoxId,String medId){
		JsonpResultBean bean = new JsonpResultBean();
		
		Medicine med = Medicine.findById(medId);
		med.name = name;
		med.function = func;
		med.medicineBoxId = medBoxId;
		med.type = type;
		med.deadline = deadline;
		try{
			med.save();
			bean.result = 1;
			bean.rtnStr = "药品修改成功!";
			wsOkAsExtJsonP(bean);
		}
		catch(Exception e){
			bean.result = 0;
			bean.rtnStr = "药品修改失败!";
			wsOkAsExtJsonP(bean);
		}
	}
	
	/**
	 * 根据药箱查找药品
	 */
	public static void findMedyMedBox(String medBoxId){
		List<Medicine> medicines = Medicine.find("medicineBoxId = ?", medBoxId).fetch();
		wsOkAsExtJsonP(medicines);
	}
	
	/**
	 * 根据药品Id删除药品
	 */
	public static void delMedById(String id){
		JsonpResultBean bean = new JsonpResultBean();
		try{
			Medicine.delete("id = ?", id);
			bean.result = 1;
			bean.rtnStr = "删除成功!";
			wsOkAsExtJsonP(bean);
		}
		catch(Exception e){
			bean.result = 0;
			bean.rtnStr = "删除失败!";
			wsOkAsExtJsonP(bean);
		}
	}
	
	/**
	 * 显示药品列表
	 */
	public static void showMedicine(){
		List<Medicine> medList = new ArrayList();
		
		Medicine med = new Medicine();
		med.name = "小菜胡";
		med.type = "感冒类";
		med.function = "治疗感冒神器，中华良品";
		med.id = "1";
		med.deadline = "2015.10.23";
		medList.add(med);
		
		med = new Medicine();
		med.name = "牛黄解毒片";
		med.type = "清热解毒";
		med.function = "清热解毒，散风止痛。用于肺胃蕴热引起...";
		med.id = "2";
		med.deadline = "2014.10.23";
		medList.add(med);
		
		med = new Medicine();
		med.name = "红霉素药膏";
		med.type = "青春痘类";
		med.function = "攃痘痘，用红霉素药膏";
		med.id = "3";
		med.deadline = "2015.03.23";
		medList.add(med);
		
		med = new Medicine();
		med.name = "维生素B片";
		med.type = "保健类";
		med.function = "皮肤干燥，口舌生疮";
		med.id = "4";
		med.deadline = "2012.11.14";
		medList.add(med);
		wsOkAsExtJsonP(medList);
	}
	
	/**
	 * 显示单条药品
	 */
	public static void showSingleMedicine(){
		
		List<Medicine> medList = new ArrayList();
		
		Medicine med = new Medicine();
		med = new Medicine();
		med.name = "维生素B片";
		med.type = "保健类";
		med.function = "皮肤干燥，口舌生疮";
		med.id = "4";
		med.deadline = "2012.11.14";
		medList.add(med);
		
		med = new Medicine();
		med = new Medicine();
		med.name = "维生素B片";
		med.type = "保健类";
		med.function = "皮肤干燥，口舌生疮";
		med.id = "5";
		med.deadline = "2012.11.14";
		medList.add(med);
		
		wsOkAsExtJsonP(med);
	}
	
	/**
	 * 显示单条药品
	 */
	public static void findSingleMedicine(String medId){
		System.out.println(medId);
		List<Medicine> medList = Medicine.find("id = ?", medId).fetch();
		System.out.println(medList.get(0));
		wsOkAsExtJsonP(medList.get(0));
	}
}
