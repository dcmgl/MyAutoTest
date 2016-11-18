package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import base.TestLogger;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class TestData implements Iterable<Object[]>{
	
	private Map[] dataMaps;
	public TestData(String filePath,String sheetName){
		DataMap dataMap = new DataMap(filePath, sheetName);
		dataMaps = dataMap.readData();
	}
	@Override
	public Iterator<Object[]> iterator() {
		// TODO Auto-generated method stub
		return new DataIterator();
	}
	
	private class DataIterator implements Iterator<Object[]>{
		int index=0;
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return index<dataMaps.length;
		}

		@Override
		public Object[] next() {
			// TODO Auto-generated method stub
			Object[] obj = new Object[1];
			obj[0] = dataMaps[index++];
			return obj;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}
		
	}

}

class DataMap{
	private static Logger log = TestLogger.getLogger(DataMap.class);
	private String filePath;
	private int row = 0;
	private int col = 0;
	private Workbook book;
	private Sheet sheet;
	private Map<String,String>[] dataMaps;
	public DataMap(String filePath,String sheetName){
		this.filePath = filePath;
		try {
			book = Workbook.getWorkbook(new File(filePath));
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		// 第一列默认是非空列，并且每一列是连续不能为空，否则后边的数据无法读取到
		for(Cell cell : sheet.getColumn(0)){
			if("".equals(cell.getContents().toString()) || cell.getContents().toString() == null){
				break;
			}else{
				row++;
			}
		}
		if(row == 1){
			log.info("次sheet没有可用数据");
			return;
		}
		col = sheet.getColumns();
		log.info("获取到的Excel行列信息是："+row+"行，"+col+"列");
		dataMaps = new Map[row-1];
	}
	
	public  Map[] readData(){
		List<Map<String,String>>list = new ArrayList();
		for (int i = 1; i <= row - 1; i++) {
			Map<String,String>dataMap = new TreeMap<String, String>();
			for (int j = 0; j <= col - 1; j++) {
				dataMap.put(sheet.getCell(j, 0).getContents().toString(), sheet
						.getCell(j, i).getContents().toString());
			}
			list.add(dataMap);
		}
		book.close();
		int n =0;
		for(Map<String,String>map : list){
			dataMaps[n]=new TreeMap<String, String>();
			dataMaps[n]=map;
			n++;
		}
		System.out.println("dataMaps length is "+dataMaps.length);
		return dataMaps;
	}
	
	public int getDataRow(){
		return row;
	}
	
	
}