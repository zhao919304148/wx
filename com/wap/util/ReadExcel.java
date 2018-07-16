package com.wap.util;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ReadExcel {


	@SuppressWarnings("resource")
	public static void main(String[] args) {
		List<RowBean> list = new ArrayList<RowBean>();
		InputStream instream;
		try {
			instream = new FileInputStream("C:/Users/Administrator/Desktop/网点.xls");
			 
			/*XSSFWorkbook xssfWorkbook = new XSSFWorkbook(instream);
			XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);*/
			HSSFWorkbook workbook = new HSSFWorkbook(instream);
//			HSSFSheet sheetAt = workbook.getSheet("Sheet1");
			HSSFSheet sheetAt = workbook.getSheet("新增");
			int lastRowNum = sheetAt.getLastRowNum();
			System.out.println(lastRowNum);
			int count = 0;
			for (int i = 0; i <= lastRowNum; i++) {
				HSSFRow row = sheetAt.getRow(i);
				String cell1 = row.getCell(0).getStringCellValue().trim();
				String cell2 = row.getCell(1).getStringCellValue().trim();
				String cell3 = row.getCell(2).getStringCellValue().trim();
				String cell4 = row.getCell(3).getStringCellValue().trim();
				String cell5 = row.getCell(4).getStringCellValue().trim();
				String cell6 = row.getCell(5).getStringCellValue().trim();
				String cell7 = row.getCell(6).getStringCellValue().trim();
				String cell8 = row.getCell(7).getStringCellValue().trim();
				String cell9 = row.getCell(8).getStringCellValue().trim();
				String cell10 = row.getCell(9).getStringCellValue().trim();
				String cell11 = row.getCell(10).getStringCellValue().trim();
				String cell12 = row.getCell(11).getStringCellValue().trim();
				String cell13 = row.getCell(12).getStringCellValue().trim();
				String cell14 = row.getCell(13).getStringCellValue().trim();
				String[] split = cell1.split(",");
				String[] split2 = cell2.split(",");
				count += (split.length*split2.length);
				for (int j = 0; j < split.length; j++) {
					for (int j2 = 0; j2 < split2.length; j2++) {
						RowBean rowBean = new RowBean();
						rowBean.setA1(split[j]);
						rowBean.setA2(split2[j2]);
						rowBean.setA3(cell3);
						rowBean.setA4(cell4);
						rowBean.setA5(cell5);
						rowBean.setA6(cell6);
						rowBean.setA7(cell7);
						rowBean.setA8(cell8);
						rowBean.setA9(cell9);
						rowBean.setA10(cell10);
						rowBean.setA11(cell11);
						rowBean.setA12(cell12);
						rowBean.setA13(cell13);
						rowBean.setA14(cell14);
						list.add(rowBean);
//						System.out.println(rowBean.toString());
					}
				}
			}
			
			System.out.println("count="+count);
			System.out.println("count="+list.size());
			FileWriter fw = null;
			BufferedWriter writer = null;
			java.io.File file = new java.io.File("C:/Users/Administrator/Desktop/networksql.txt");
			fw = new FileWriter(file);
			writer = new BufferedWriter(fw);
			for (RowBean row2 : list) {
				writer.write(row2.toString());
				writer.newLine();//换行
				writer.flush();
			}
		} catch ( Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void test(HSSFSheet ss){
		
	}
}
