package com.utilies;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Excelcode  extends BaseClass{

       int rowNum = 1;

       WebDriver driver;
	    XSSFWorkbook workbook;
	    XSSFSheet sheet;

	    public Excelcode(String sheetName) {
	        workbook = new XSSFWorkbook();
	        sheet = workbook.createSheet(sheetName);
	    }

	
	    public static String getCellValue(List<WebElement> cols, Map<String, Integer> headerMap, String headerName) {
			 if (!headerMap.containsKey(headerName)) {
			        System.out.println("Column '" + headerName + "' is not available");
			        return "N/A"; 
			    }

			    int index = headerMap.get(headerName);
			    if (index < cols.size()) {
			        return cols.get(index).getText().trim();
			    } else {
			        System.out.println("Column index out of range for: " + headerName);
			        return "N/A";
			    }
			}
	 
	 public void DataFetch() {
		 
		 List<WebElement> rows = driver.findElements(By.xpath("//table[@class='athlete-table__table']/tbody/tr"));
         System.out.println("Total Athletes Found: " + rows.size());

         for (int i = 1; i <= rows.size(); i++) {
             List<WebElement> refreshedRows = driver.findElements(By.xpath("//table[@class='athlete-table__table']/tbody/tr"));
             WebElement currentRow = refreshedRows.get(i - 1);
             List<WebElement> cols = currentRow.findElements(By.tagName("td"));

             String country = cols.get(0).getText().trim();
             String name = cols.get(1).getText().trim();
             String gender = cols.get(2).getText().trim();
             String dob = cols.get(3).getText().trim();
             String discipline = cols.get(4).getText().trim();

             WebElement profileAnchor = currentRow.findElement(By.cssSelector("a.athlete-table__cta-link"));
             String profileLink = profileAnchor.getAttribute("href");
             profileAnchor.click();

             new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                     ExpectedConditions.visibilityOfElementLocated(
                             By.xpath("(//table[@class='athlete-table__table js-scroller'])[1]/tbody/tr")));

             List<WebElement> headerCells = driver.findElements(
                     By.xpath("(//table[@class='athlete-table__table js-scroller'])[1]/thead/tr/th"));
             
             Map<String, Integer> headerIndexMap = new HashMap<>();
             for (int j = 0; j < headerCells.size(); j++) {
                 headerIndexMap.put(headerCells.get(j).getText().trim(), j);
             }

             List<WebElement> eventRows = driver.findElements(
                     By.xpath("(//table[@class='athlete-table__table js-scroller'])[1]/tbody/tr"));

             for (WebElement eventRow : eventRows) {
                 List<WebElement> eventCols = eventRow.findElements(By.tagName("td"));
                 String event = Excelcode.getCellValue(eventCols, headerIndexMap, "Event");
                 String time = Excelcode.getCellValue(eventCols, headerIndexMap, "Time");
                 String medal = Excelcode.getCellValue(eventCols, headerIndexMap, "Medal");
                 String poolLength = Excelcode.getCellValue(eventCols, headerIndexMap, "Pool Length");
                 String age = Excelcode.getCellValue(eventCols, headerIndexMap, "Age");
                 String competition = Excelcode.getCellValue(eventCols, headerIndexMap, "Competition");
                 String compCountry = Excelcode.getCellValue(eventCols, headerIndexMap, "Comp Country");
                 String date = Excelcode.getCellValue(eventCols, headerIndexMap, "Date");

                 System.out.println("Event: " + event + " | Time: " + time + " | Medal: " + medal + " | Age: " + age);

                 XSSFRow row = sheet.createRow(rowNum++);
                 String[] values = {country, name, gender, dob, discipline, profileLink,
                         event, time, medal, poolLength, age, competition, compCountry, date};
                 for (int c = 0; c < values.length; c++) {
                     row.createCell(c).setCellValue(values[c]);
                 }
             }
	 }
	 }
}



	

