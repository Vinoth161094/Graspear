import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

public class AthleteAllProfileclick2 {

    public static void main(String[] args) {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Athlete Data");

        try {
            driver.get("https://www.worldaquatics.com/athletes?gender=&discipline=&nationality=IND&name=");
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            Wait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofSeconds(2))
                    .ignoring(NoSuchElementException.class);

            try {
                WebDriverWait wdwait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement acceptCookies = wdwait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(@class,'js-cookie-notice-btn') and .//span[text()='Accept Cookies']]")
                ));
                acceptCookies.click();
                System.out.println("Cookies accepted successfully!");
            } catch (Exception e) {
                System.out.println("No cookie popup found or already accepted.");
            }

            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("Country");
            header.createCell(1).setCellValue("Name");
            header.createCell(2).setCellValue("Gender");
            header.createCell(3).setCellValue("DOB");
            header.createCell(4).setCellValue("Discipline");
            header.createCell(5).setCellValue("Profile Link");
            header.createCell(6).setCellValue("Event");
            header.createCell(7).setCellValue("Time");
            header.createCell(8).setCellValue("Medal");
            header.createCell(9).setCellValue("Age");
            header.createCell(10).setCellValue("Completion");

            int rowNum = 1;

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
                
                System.out.println(" Collected Details for : " + name);


                WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(20));
                wait2.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//table[@class='athlete-table__table js-scroller']/thead/tr")
                ));

                List<WebElement> eventRows = driver.findElements(
                        By.xpath("//table[@class='athlete-table__table js-scroller']/thead/tr")
                );
                
                System.out.println("Next Page Row Found: " + eventRows.size());


                for (WebElement eventRow : eventRows) {
                    List<WebElement> eventCols = driver.findElements(By.xpath("//table[@class='athlete-table__table js-scroller']/tbody/tr/td"));
                    System.out.println("Next Page Column Found: " + eventCols.size());

                    String event = eventCols.size() > 0 ? eventCols.get(0).getText().trim() : "";
                    String time = eventCols.size() > 1 ? eventCols.get(1).getText().trim() : "";
                    String medal = eventCols.size() > 2 ? eventCols.get(2).getText().trim() : "";
                    String age = eventCols.size() > 3 ? eventCols.get(3).getText().trim() : "";
                    String compition = eventCols.size() > 4 ? eventCols.get(4).getText().trim() : "";

                    XSSFRow excelRow = sheet.createRow(rowNum++);
                    excelRow.createCell(0).setCellValue(country);
                    excelRow.createCell(1).setCellValue(name);
                    excelRow.createCell(2).setCellValue(gender);
                    excelRow.createCell(3).setCellValue(dob);
                    excelRow.createCell(4).setCellValue(discipline);
                    excelRow.createCell(5).setCellValue(profileLink);
                    excelRow.createCell(6).setCellValue(event);
                    excelRow.createCell(7).setCellValue(time);
                    excelRow.createCell(8).setCellValue(medal);
                    excelRow.createCell(9).setCellValue(age);
                    excelRow.createCell(10).setCellValue(compition);
                }

                driver.navigate().back();
                Thread.sleep(3000);
            }

            String filePath = System.getProperty("user.dir") + "\\AthleteData.xlsx";
            FileOutputStream fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.close();

            System.out.println("Excel Export Done Successfully!");
            System.out.println("Saved at: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { workbook.close(); 
            } 
            catch (Exception e) {
            	
            }
            driver.quit();
        }
    }
}
