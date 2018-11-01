package com.diboot.components.file.excel;

import com.diboot.framework.utils.S;
import com.diboot.framework.utils.V;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/***
 * Dibo Excel 读取类
 * @author Mazc@dibo.ltd
 * @version 20161107
 * Copyright @ www.dibo.ltd
 */
public class ExcelReader {
	private static final Logger logger = LoggerFactory.getLogger(ExcelReader.class);

	private static DecimalFormat fmtDecimal = new DecimalFormat("0");

	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String ERROR = "ERR:";

	/**
	 * 对excel文件的解析
	 * @param excelFilePath
	 * @return
	 */
	public static List<String[]> toList(String excelFilePath) throws Exception{
		File file = new File(excelFilePath);
		return toList(file);
	}

	/**
	 * 对excel文件的解析
	 * @param file
	 * @return
	 */
	public static List<String[]> toList(File file) throws Exception{
		if(file.getName().endsWith(".xls")){
			return readXls(new FileInputStream(file));
		}
		else if(file.getName().endsWith(".xlsx")){
			return readXlsx(new FileInputStream(file));
		}
		else{
			throw new Exception("无法识别的Excel格式！");
		}
	}

	/**
	 * 对excel文件的解析
	 * @param file
	 * @return
	 */
	public static List<String[]> toList(MultipartFile file) throws Exception{
        if(file.getOriginalFilename().endsWith(".xls")){
			return readXls(file.getInputStream());
		}
		else if(file.getOriginalFilename().endsWith(".xlsx")){
			return readXlsx(file.getInputStream());
		}
		else{
			throw new Exception("无法识别的Excel格式！");
		}
	}
	
	/**
	 * Read the Excel 2010
	 * @param is the path of the excel file
	 * @return
	 * @throws Exception
	 */
	 private static List<String[]> readXlsx(InputStream is) throws Exception {
		 XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		 List<String[]> list = new ArrayList<String[]>();
		 // Read the Sheet
		 for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			 XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			 if (xssfSheet == null) {
				 continue;
			 }
			 // Read the Row
			 int cellCount = 0;//获取总列数
			 for (int rowNum = 0; rowNum < xssfSheet.getLastRowNum() + 1; rowNum++) {
				 XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				 if(xssfRow == null){
					 break;
				 }
				 if (rowNum == 0){
					 cellCount = xssfRow.getPhysicalNumberOfCells();
				 }
				 String[] rowValues = buildRowValues(xssfRow, rowNum, cellCount);
				 list.add(rowValues);
			 }
		 }
		 try{
			 xssfWorkbook.close();			 
		 }
		 catch(Exception e){
			 logger.error("关闭Excel异常:", e);
		 }
		 return list;
	 }
	 
	 /**
	 * Read the Excel 2003-2007
	 * @param is the path of the Excel
	 * @return
	 * @throws Exception
	 */
	 private static List<String[]> readXls(InputStream is) throws Exception {
		 HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		 List<String[]> list = new ArrayList<String[]>();
		 // Read the Sheet
		 for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			 HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			 if (hssfSheet == null) {
				 continue;
			 }
			 // Read the Row
			 int cellCount = 0;//获取总列数
			 for (int rowNum = 0; rowNum < hssfSheet.getLastRowNum() + 1; rowNum++) {
				 HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				 if(hssfRow == null){
					 break;
				 }
				 if (rowNum == 0){//获取总列数
					 cellCount = hssfRow.getPhysicalNumberOfCells();
				 }
				 String[] rowValues = buildRowValues(hssfRow, rowNum, cellCount);
				 list.add(rowValues);
			 }
		 }
		 try{
			 hssfWorkbook.close();			 
		 }
		 catch(Exception e){
			 logger.error("关闭Excel异常:", e);
		 }
		 return list;
	}

	/**
	 * 组装行数据
	 * @param hssfRow
	 * @param rowNum
	 * @param cellCount
	 * @return
	 */
	private static String[] buildRowValues(Row hssfRow, int rowNum, int cellCount) throws Exception{
		String[] rowValues = new String[cellCount];
		List<String> errors = null;
		for(int i=0; i<cellCount; i++){
			Cell cell = hssfRow.getCell(i);
			String val = "";
			if(cell != null){
				val = getValue(cell);
				if(val == null){
					val = "";
				}
				if(V.notEmpty(val) && val.startsWith(ERROR)){
					String location = "["+(rowNum+1)+"行"+(cellCount+1)+"列]";
					val = val.replace(ERROR, ERROR+location);
					if(errors == null){
						errors = new ArrayList<>();
					}
					errors.add(val);
				}
				rowValues[i] = val;
			}
			rowValues[i] = val;
		}
		if(V.notEmpty(errors)){
			throw new Exception(S.join(errors));
		}
		// 返回正确结果
		return rowValues;
	}
	
	 /**
	  * 获取Excel单元格的值
	  * @param cell
	  * @return
	  */
	private static String getValue(Cell cell){
		if (cell.getCellType() == CellType.BOOLEAN.getCode()) {
			return String.valueOf(cell.getBooleanCellValue());
		}
		else if (cell.getCellType() == CellType.NUMERIC.getCode()) {
			if(DateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
				return dateFormat.format(cell.getDateCellValue()); //日期型
			}
			else {
				return fmtDecimal.format(cell.getNumericCellValue()); //数字
			}
		}
		else if(cell.getCellType() == CellType.BLANK.getCode()){
			return "";
		}
		else if(cell.getCellType() == CellType.FORMULA.getCode()){
			return ERROR+"公式错误!";
		}
		else if(cell.getCellType() == CellType.ERROR.getCode()){
			return ERROR+"数据格式错误!";
		}
		else{
			return String.valueOf(cell.getStringCellValue());			
		}
	}
	
}