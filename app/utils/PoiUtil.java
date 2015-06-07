package utils;

import java.io.File;
import java.io.FileOutputStream;

import models.DrugStore;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * POI工具包
 * @author boxizen
 * @since 2015/06/06
 */
public class PoiUtil {
	/*
	 *	导入药品数据 
	 */
	public static void importMedicineData(){
		try{
			String file = "data/medicine-bat.xls";
			Workbook wb = WorkbookFactory.create(new File(file));		
			int sheetNum = wb.getNumberOfSheets();
			Sheet sheet = wb.getSheetAt(1);
			int rows = sheet.getLastRowNum();
			for(int i=1;i<rows+1;i++){
				String name = null ,specification = null,prodAddr = null, productor = null,code = null, supplier = null, license = null;
				double price;
				boolean isOtc,isFound;
				if(sheet.getRow(i).getCell(1)!=null){
					name = sheet.getRow(i).getCell(1).getStringCellValue();
				}
				if(sheet.getRow(i).getCell(2)!=null){
					specification = sheet.getRow(i).getCell(2).getStringCellValue();
				}
				if(sheet.getRow(i).getCell(3)!=null){
					prodAddr = sheet.getRow(i).getCell(3).getStringCellValue();
				}
				if(sheet.getRow(i).getCell(4)!=null){
					productor = sheet.getRow(i).getCell(4).getStringCellValue();
				}
				if(sheet.getRow(i).getCell(5)!=null){
					code = sheet.getRow(i).getCell(5).getStringCellValue();
				}
				price = sheet.getRow(i).getCell(6).getNumericCellValue();
				if(sheet.getRow(i).getCell(7).getNumericCellValue()==0)
					isOtc = false;
				else
					isOtc = true;
				if(sheet.getRow(i).getCell(8).getNumericCellValue()==0)
					isFound = false;
				else
					isFound = true;
				if(sheet.getRow(i).getCell(9)!=null){
					supplier = sheet.getRow(i).getCell(9).getStringCellValue();
				}
				if(sheet.getRow(i).getCell(10)!=null){
					license = sheet.getRow(i).getCell(10).getStringCellValue();
				}
				DrugStore drug = new DrugStore();
				drug.dname = name;
				drug.specification = specification;
				drug.prodAddr = prodAddr;
				drug.productor = productor;
				drug.code = code;
				drug.supplier = supplier;
				drug.mark = license;
				drug.save();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void insertOneMsg(){
		DrugStore drug = new DrugStore();
		drug.dname = "加味藿香正气丸";
		drug.prodAddr = "广州白云区";
		drug.productor = "广州白云山和记黄埔中药有限公司";
		drug.code = "6938200751221";
		drug.supplier = "白云药业";
		drug.save();
	}
	
	public static void main(String[] args){
		importMedicineData();
	}
}
