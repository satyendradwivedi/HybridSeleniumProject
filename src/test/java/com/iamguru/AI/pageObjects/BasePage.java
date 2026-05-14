package com.iamguru.AI.pageObjects;

import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

public WebDriver driver;

	public BasePage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	// Method to switch to the newly opened tab
	public void switchToNewWindow() {
	    String currentWindow = driver.getWindowHandle();
	    Set<String> allWindows = driver.getWindowHandles();

	    for (String window : allWindows) {
	        if (!window.equals(currentWindow)) {
	            driver.switchTo().window(window);
	            break;
	        }
	    }
	}
	public void switchToLastTab() {
	    ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(tabs.size() - 1));  // focus on last tab
	    System.out.println("Now on: " + driver.getTitle());
	}


}
