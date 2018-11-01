package com.diboot.components.file.excel;

import com.diboot.framework.utils.V;

import java.io.Serializable;
import java.util.*;

/***
 * Excel包装对象
 * @author Mazc@dibo.ltd
 *
 */
public class SheetWrapper implements Serializable{
	private static final long serialVersionUID = 7821062901316789L;
	/**
	 * Sheet Name
	 */
	private String sheetName;

	/**
	 * Excel表头
	 */
	private List<String> headers = null;

	/***
	 * Excel内容：每行拼接在一起
	 */
	private List<String[]> rows = null;

	public SheetWrapper(List<LinkedHashMap> dataRows){
		if(V.isEmpty(dataRows)){
			return;
		}
		// Get max size of map
		int cnt = 0, maxKeysIndex = 0;
		for (int i=0; i<dataRows.size(); i++){
			LinkedHashMap map = dataRows.get(i);
			if (map.size() > cnt){
				cnt = map.size();
				maxKeysIndex = i;
			}
		}
		// 构造header
		this.headers = new ArrayList<String>();
		LinkedHashMap tempMap = dataRows.get(maxKeysIndex);
		for (Iterator it = tempMap.keySet().iterator(); it.hasNext();){
			Object key = it.next();
			this.headers.add(String.valueOf(key));
		}
		// 构造数据行
		this.rows = new ArrayList<>();
		for(int i=0; i<dataRows.size(); i++){
			String[] rowData = new String[cnt];
			LinkedHashMap map = dataRows.get(i);
			for (int j=0; j<this.headers.size(); j++){
				String key = this.headers.get(j);
				if(V.notEmpty(map.get(key))){
					rowData[j] = String.valueOf(map.get(key));
				}
				else{
					rowData[j] = "";
				}
			}
			this.rows.add(rowData);
		}
	}

	public SheetWrapper(List<String> headers, List<String[]> rows){
		setHeaders(headers);
		setRows(rows);
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public List<String[]> getRows() {
		return rows;
	}

	public void setRows(List<String[]> rows) {
		this.rows = rows;
	}
	
}
