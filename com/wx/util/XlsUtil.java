package com.wx.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;




public class XlsUtil {
	public static JSONArray readXls(String [] init) throws IOException, ParseException {
		String cityxls=ConfigUtil.getString("cityxls");
        InputStream is = new FileInputStream(cityxls);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        // 循环工作表Sheet
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            JSONArray array=new JSONArray();
            // 循环行Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow == null) {continue;}
                
                JSONObject object=new JSONObject();
                // 循环列Cell
                HSSFCell cityId = hssfRow.getCell((short) 0);
                if (cityId == null) {continue;}
                object.element("cityId", getValue(cityId));     
                
                HSSFCell cityName = hssfRow.getCell((short) 1);
                if (cityName == null) {continue;}
                object.element("cityName", getValue(cityName));
                
                HSSFCell lng = hssfRow.getCell((short) 2);
                if (lng == null) {continue;}
                object.element("lng", getValue(lng));
                
                HSSFCell lat = hssfRow.getCell((short) 3);
                if (lat == null) {continue;}
                object.element("lat", getValue(lat));
                
                HSSFCell initial = hssfRow.getCell((short) 4);
                if (initial == null) {continue;}
                object.element("initial", getValue(initial));
                for (int i = 0; i < init.length; i++) {
					String ini=getValue(initial);
					if(init[i].equals(ini)){
						array.add(object);
					}
				}
        }
           return array;
}
	public static JSONObject readXlsByCityName(String name) throws IOException, ParseException {
		String cityxls=ConfigUtil.getString("cityxls");
        InputStream is = new FileInputStream(cityxls);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        // 循环工作表Sheet
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            JSONObject object=new JSONObject();
            // 循环行Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow == null) {continue;}
                HSSFCell cellName=hssfRow.getCell((short)1); 
                if (cellName == null) {continue;}
                String  cName= getValue(cellName);
                if(cName.indexOf(name)!=-1){
                	// 循环列Cell
                    HSSFCell cityId = hssfRow.getCell((short) 0);
                    if (cityId == null) {continue;}
                    object.element("cityId", getValue(cityId));     
                    
                    HSSFCell cityName = hssfRow.getCell((short) 1);
                    if (cityName == null) {continue;}
                    object.element("cityName", getValue(cityName));
                    
                    HSSFCell lng = hssfRow.getCell((short) 2);
                    if (lng == null) {continue;}
                    object.element("lng", getValue(lng));
                    
                    HSSFCell lat = hssfRow.getCell((short) 3);
                    if (lat == null) {continue;}
                    object.element("lat", getValue(lat));
                    
                    HSSFCell initial = hssfRow.getCell((short) 4);
                    if (initial == null) {continue;}
                    object.element("initial", getValue(initial));
                    System.out.println(object);
                    return object;
                }                 
        }
           return null;
}
	public static JSONObject readXlsById(String id) throws IOException, ParseException {
		String cityxls=ConfigUtil.getString("cityxls");
        InputStream is = new FileInputStream(cityxls);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        // 循环工作表Sheet
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            JSONObject object=new JSONObject();
            // 循环行Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow == null) {continue;}
                HSSFCell cellId=hssfRow.getCell((short)0); 
                if (cellId == null) {continue;}
                String  cId= getValue(cellId);
                if(cId.equals(id)){
                	// 循环列Cell
                    HSSFCell cityId = hssfRow.getCell((short) 0);
                    if (cityId == null) {continue;}
                    object.element("cityId", getValue(cityId));     
                    
                    HSSFCell cityName = hssfRow.getCell((short) 1);
                    if (cityName == null) {continue;}
                    object.element("cityName", getValue(cityName));
                    
                    HSSFCell lng = hssfRow.getCell((short) 2);
                    if (lng == null) {continue;}
                    object.element("lng", getValue(lng));
                    
                    HSSFCell lat = hssfRow.getCell((short) 3);
                    if (lat == null) {continue;}
                    object.element("lat", getValue(lat));
                    
                    HSSFCell initial = hssfRow.getCell((short) 4);
                    if (initial == null) {continue;}
                    object.element("initial", getValue(initial));
                    System.out.println(object);
                    return object;
                }                 
        }
           return null;
}	
	
	
	
	public static String getValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			// 返回布尔类型的值
			return String.valueOf(hssfCell.getBooleanCellValue());
		} 
		else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			// 返回数值类型的值
			if(Math.round(hssfCell.getNumericCellValue())-hssfCell.getNumericCellValue()==0){
				   return String.valueOf((long)hssfCell.getNumericCellValue());
			}else{
				return String.valueOf(hssfCell.getNumericCellValue());
			}
		}
		else{
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}
	
	
	public static void main(String[] args) {
		try {
			readXlsById("87");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
