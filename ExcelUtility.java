import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExcelUtility {
  public static XSSFWorkbook openExcel(final String path) throws IOException {
		FileInputStream file = new FileInputStream(new File(path));
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		return workbook;
  }
  
  	public static void closeExcel(XSSFWorkbook currentWorkbook, final String path) throws IOException {
		// Save the Excel Report
		FileOutputStream outFile = new FileOutputStream(new File(path));
		currentWorkbook.write(outFile);
		outFile.close();
	}
  
  public static HashMap<String, String> readExcelData(final String path, final String sheetName, final int rowNo)
			throws IOException {
		LinkedHashMap<Integer, List> hashMap = new LinkedHashMap<Integer, List>();
		LinkedHashMap<String, String> actualMap = new LinkedHashMap<String, String>();
		XSSFWorkbook currWorkbook = openExcel(path);
		XSSFSheet currSheet = currWorkbook.getSheet(sheetName);
		Iterator rows = currSheet.rowIterator();
		while (rows.hasNext()) {
			XSSFRow row = (XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			List<XSSFCell> data = new LinkedList<XSSFCell>();
			while (cells.hasNext()) {
				XSSFCell cell = (XSSFCell) cells.next();
				cell.setCellType(CellType.STRING);
				data.add(cell);
			}
			hashMap.put(row.getRowNum(), data);
		}
		closeExcel(currWorkbook, path);
		List column = hashMap.get(0);
		List value = hashMap.get(rowNo);
          
        for (int i = 0; i < column.size(); i++) {
			String firstColumn = column.get(i).toString();
			String actualData = value.get(i).toString();
			actualMap.put(firstColumn, actualData);
		}
		return actualMap;
	}
}
