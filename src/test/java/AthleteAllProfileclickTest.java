import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;

import com.utilies.BaseClass;
import com.utilies.CommonUtilies;
import com.utilies.Excelcode;
import com.utilies.Locators;
import com.utilies.PropertyFile;

import java.io.FileOutputStream;
import java.time.Duration;
import java.util.*;


public class AthleteAllProfileclickTest extends BaseClass {
	
	Excelcode excel;
    CommonUtilies c;
    PropertyFile p;
    Locators l;
	    WebDriver driver;
	    XSSFWorkbook workbook;
	    XSSFSheet sheet;
	    int rowNum = 1;
	    String url ="https://www.worldaquatics.com/athletes?gender=&discipline=&nationality=IND&name=\\\"";
	    
	   

    	@BeforeClass
        public void setUp() {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Athlete Data");

        excel = new Excelcode("Athlete Data");
        c = new CommonUtilies();
        p = new PropertyFile();
        l= new Locators();

        String[] headers = {"Country", "Name", "Gender", "DOB", "Discipline", "Profile Link",
                "Event", "Time", "Medal", "Pool Length", "Age", "Competition", "Comp Country", "Date"};
        XSSFRow headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }
    }
    @Test
    public void collectAthleteData() {
        
            c.LauchUrl(url);
            c.acceptCookiesIfPresent();
            excel.DataFetch();
            NavigateBack();
            c.waitWebDriver(l.tableRow);
            

        
    }

    @AfterClass
    public void tearDown() {
        try {
            for (int i = 0; i <= 13; i++) 
            	sheet.autoSizeColumn(i);

            String filePath = System.getProperty("user.dir") + "\\AthleteData.xlsx";
            FileOutputStream out = new FileOutputStream(filePath);
            workbook.write(out);
            out.close();

            System.out.println(" Excel Export Done Successfully!");
            System.out.println(" Saved at: " + filePath);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try { workbook.close(); 
	            } 
	            catch (Exception ignored) {
	            	
	            }
	            driver.quit();
	        }
	    }

	    
	    

}
