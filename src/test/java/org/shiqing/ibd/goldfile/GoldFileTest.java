package org.shiqing.ibd.goldfile;

import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;
import org.shiqing.ibd.config.ConfigFactory;

import junit.framework.TestCase;

/**
 * 
 * Basic Test Suite.
 * Compare full / ibd50_plus_sector_leader / high_occurrence result.
 * 
 * RUN THIS BEFORE ANY COMMIT !!!
 * 
 * @author shiqing
 *
 */
public class GoldFileTest extends TestCase {
	
	@Test
	public void testBasicSuite() throws Exception {
		BasicTestAnalyst.generateTestResult();
		
		compareWithGoldFile(ConfigFactory.get().getPropertiesProvider().getValue("path.test.result") + "full.xls", 
				ConfigFactory.get().getPropertiesProvider().getValue("path.test.gold") + "full_gold.xls");
		compareWithGoldFile(ConfigFactory.get().getPropertiesProvider().getValue("path.test.result") + "high_occurrence.xls", 
				ConfigFactory.get().getPropertiesProvider().getValue("path.test.gold") + "high_occurrence_gold.xls");
		compareWithGoldFile(ConfigFactory.get().getPropertiesProvider().getValue("path.test.result") + "ibd50_plus_sector_leader.xls", 
				ConfigFactory.get().getPropertiesProvider().getValue("path.test.gold") + "ibd50_plus_sector_leader_gold.xls");
		
		// TODO Need some result to generate golden result, review this later
//		GoldenTestAnalyst.generateTestResult();
//		
//		compareWithGoldFile("/Users/shiqing/git/ibd-Analyzer/src/test/java/org/shiqing/ibd/goldfile/raw/results/Golden.xls", 
//				"/Users/shiqing/git/ibd-Analyzer/src/test/java/org/shiqing/ibd/goldfile/raw/results/goldFile/Golden.xls");
	}
	
	/**
	 * Compare the result file with gold file line by line, column by column
	 * @param resultFilePath
	 * @param goldFilePath
	 * @throws Exception
	 */
	private static void compareWithGoldFile(String resultFilePath, String goldFilePath) throws Exception {
		FileInputStream resultFile = new FileInputStream(resultFilePath);
		FileInputStream goldFile = new FileInputStream(goldFilePath);
		
		HSSFWorkbook resultFileWorkbook = new HSSFWorkbook(resultFile);
		HSSFWorkbook goldFileWorkbook = new HSSFWorkbook(goldFile);
		
		HSSFSheet resultSheet = resultFileWorkbook.getSheetAt(0);
		HSSFSheet goldSheet = goldFileWorkbook.getSheetAt(0);
		
		Iterator<Row> resultRowIterator = resultSheet.iterator();
		Iterator<Row> goldRowIterator = goldSheet.iterator();
		
		while (resultRowIterator.hasNext() && goldRowIterator.hasNext()) {
			Row resultRow = resultRowIterator.next();
			Row goldRow = goldRowIterator.next();
			
			Iterator<Cell> resultCellIterator = resultRow.iterator();
			Iterator<Cell> goldCellIterator = goldRow.iterator();
			
			while (resultCellIterator.hasNext() && goldCellIterator.hasNext()) {
				Cell resultCell = resultCellIterator.next();
				Cell goldCell = goldCellIterator.next();
				
				String errorAppendStr = " | " + resultFilePath + " : Row : " + resultRow.getRowNum() + " Column : " + resultCell.getColumnIndex();
				
				assertEquals("Cell type doesn't match : result : " + resultCell.getCellType() + " / gold " + goldCell.getCellType() + errorAppendStr, 
						resultCell.getCellType(), goldCell.getCellType());
				
				switch (resultCell.getCellType()) {
	                case Cell.CELL_TYPE_STRING:
	                	assertEquals("Value doesn't match : Result cell : " + resultCell.getStringCellValue() + " / Gold cell : " + goldCell.getStringCellValue() + errorAppendStr, 
	                			goldCell.getStringCellValue(), resultCell.getStringCellValue());
	                    break;
	                case Cell.CELL_TYPE_NUMERIC:
	                    if (DateUtil.isCellDateFormatted(resultCell)) {
	                    	assertEquals("Value doesn't match : Result cell : " + resultCell.getDateCellValue() + " / Gold cell : " + goldCell.getDateCellValue() + errorAppendStr, 
		                			goldCell.getDateCellValue(), resultCell.getDateCellValue());
	                    } else {
	                    	assertEquals("Value doesn't match : Result cell : " + resultCell.getNumericCellValue() + " / Gold cell : " + goldCell.getNumericCellValue() + errorAppendStr, 
		                			goldCell.getNumericCellValue(), resultCell.getNumericCellValue());
	                    }
	                    break;
	                case Cell.CELL_TYPE_BOOLEAN:
	                	assertEquals("Value doesn't match : Result cell : " + resultCell.getBooleanCellValue() + " / Gold cell : " + goldCell.getBooleanCellValue() + errorAppendStr, 
	                			goldCell.getBooleanCellValue(), resultCell.getBooleanCellValue());
	                    break;
	                case Cell.CELL_TYPE_FORMULA:
	                	assertEquals("Value doesn't match : Result cell : " + resultCell.getCellFormula() + " / Gold cell : " + goldCell.getCellFormula() + errorAppendStr, 
	                			goldCell.getCellFormula(), resultCell.getCellFormula());
	                    break;
	            }
			}
		}
		
		resultFile.close();
		goldFile.close();
		resultFileWorkbook.close();
		goldFileWorkbook.close();
	}
}
