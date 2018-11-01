package com.diboot.components.file.excel;

import com.diboot.components.file.FileHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/***
 * Dibo Excel Writer: 生成Excel适用
 * @author Mazc@dibo.ltd
 * @version 20161107
 * Copyright @ www.dibo.ltd
 */
public class ExcelWriter {
	private static final Logger logger = LoggerFactory.getLogger(ExcelWriter.class);
	/**
	 * 文件名
	 */
	private String fileName;
	/**
	 * 全路径
	 */
	private String fullPath;

	/**
	 * sheet列表
	 */
	private List<SheetWrapper> sheetList = null;
	
	public ExcelWriter(String fileName){
		this.fileName = fileName;
		sheetList = new ArrayList<SheetWrapper>();
	}

	/***
	 * 添加新的Sheet
	 * @param sheetName
	 * @param dataRows
	 */
	public void addSheet(String sheetName, List<LinkedHashMap> dataRows){
		SheetWrapper sheet = new SheetWrapper(dataRows);
		sheet.setSheetName(sheetName);
		sheetList.add(sheet);
	}

	/***
	 * 添加新的Sheet
	 * @param headers
	 * @param rows
	 */
	public void addSheet(List<String> headers, List<String[]> rows){
		SheetWrapper sheet = new SheetWrapper(headers, rows);
		sheetList.add(sheet);
	}

	/***
	 * 添加新的Sheet
	 * @param headers
	 * @param rows
	 * @param sheetName
	 */
	public void addSheet(String sheetName, List<String> headers, List<String[]> rows){
		SheetWrapper sheet = new SheetWrapper(headers, rows);
		sheet.setSheetName(sheetName);
		sheetList.add(sheet);
	}

	/****
	 * 生成Excel（多Sheet）
	 * @return
	 * @throws Exception
	 */
	public boolean generate(){
		// 补齐后缀
		if(!this.fileName.contains(".")){
			this.fileName += ".xls";
		}
		// 访问路径
		String accessPath = FileHelper.getFileStoragePath(this.fileName);
		// 全路径
		String fullPath = FileHelper.getFileStorageDirectory() + accessPath;
		// 生成xls文件
		return generateXlsFile(fullPath);
	}

	/****
	 * 生成Excel（多Sheet）
	 * @return
	 * @throws Exception
	 */
	public boolean generate2Path(String path){
		// 补齐后缀
		if(!this.fileName.contains(".")){
			this.fileName += ".xls";
		}
		String fullPath = path + this.fileName;
		// 生成xls文件
		return generateXlsFile(fullPath);
	}

	/***
	 * 获取生成后的文件路径
	 * @return
	 */
	public String getGeneratedFilePath(){
		return fullPath;
	}

	private boolean generateXlsFile(String fullPath){
		OutputStream stream = null;
		HSSFWorkbook wb = null;
		try {
			FileHelper.makeDirectory(fullPath);
			wb = generateXls(sheetList); //TODO xlsx
			//创建文件流
			stream = new FileOutputStream(fullPath);
			//写入数据
			wb.write(stream);

			// 设置路径
			this.fullPath = fullPath;
			return true;
		}
		catch (Exception e) {
			logger.error("导出excel文件出错", e);
			return false;
		}
		finally {
			//关闭文件流
			if(stream != null || wb != null){
				try{
					stream.close();
					wb.close();
				}
				catch (Exception e){
					logger.warn("关闭文件流异常", e);
				}
			}
		}
	}

	/***
	 * 生成03-07格式的Excel
	 * @param sheets
	 * @return
	 * @throws Exception
	 */
	private static HSSFWorkbook generateXls(List<SheetWrapper> sheets) throws Exception{
		if(sheets == null){
			return null;
		}
		HSSFWorkbook wb = new HSSFWorkbook();
		int sheetIndex = 1;
		for(SheetWrapper wrapper : sheets){
			String sheetName = wrapper.getSheetName();
			if(StringUtils.isBlank(sheetName)){
				sheetName = "sheet"+sheetIndex++;
			}
			//创建sheet对象   
	        HSSFSheet sheet = wb.createSheet(sheetName);  
	        //添加表头  
	        HSSFRow row = sheet.createRow(0);
	        // 设置样式
	        HSSFCellStyle headerCellStyle = buildHeaderStyle(wb); // 样式对象      
	        
	        //创建第一行    
	        HSSFCell cell = null;
	        for(int i=0; i<wrapper.getHeaders().size(); i++){
	            cell = row.createCell(i);
	            cell.setCellValue(wrapper.getHeaders().get(i));
	            cell.setCellStyle(headerCellStyle);

				HSSFCellStyle bodyCellStyle = wb.createCellStyle();
				bodyCellStyle.setWrapText(true);// 指定当单元格内容显示不下时自动换行
				bodyCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直
				cell.setCellStyle(headerCellStyle); // 样式，居中
	        }

	        //循环写入行数据   
	        int beginRow = 1;
	        for(int i = 0; i<wrapper.getRows().size(); i++){
	        	String[] cellvalues = wrapper.getRows().get(i);
	        	row = (HSSFRow) sheet.createRow(beginRow + i);
	        	for(int j = 0; j < cellvalues.length; j++ ){
	        		row.createCell(j).setCellValue(cellvalues[j]);
	        	}
	        }
		}
		// 返回Workbook
		return wb;
	}

	/***
	 * 创建样式
	 * @param wb
	 * @return
	 */
	private static HSSFCellStyle buildHeaderStyle(HSSFWorkbook wb){
		HSSFCellStyle headerCellStyle = wb.createCellStyle(); // 样式对象      
        // 设置单元格的背景颜色为淡蓝色  
        headerCellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index); 
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平
        headerCellStyle.setWrapText(true);// 指定当单元格内容显示不下时自动换行
        
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        headerCellStyle.setFont(font);
        
        return headerCellStyle;
	}

	/***
	 * 释放内存
	 */
	private void freeMemory(){
		sheetList = null;
	}
	
}
