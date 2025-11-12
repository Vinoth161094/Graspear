package com.utilies;

import java.time.Duration;

import org.apache.xmlbeans.impl.xb.xsdschema.SelectorDocument.Selector.Xpath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonUtilies extends BaseClass  {
	
	
	
	public void acceptCookiesIfPresent() {
        try {
            WebDriverWait wdwait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement acceptCookies = wdwait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class,'js-cookie-notice-btn')]//span[text()='Accept Cookies']")));
            acceptCookies.click();
            System.out.println("Cookies accepted successfully!");
        } catch (Exception e) {
            System.out.println("No cookie popup found or already accepted");
        }
    }
	
	public void LauchUrl(String url) {
		
        driver.get(url);

		
	}
	
	public WebElement waitWebDriver(String xpath) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
	}

	}



