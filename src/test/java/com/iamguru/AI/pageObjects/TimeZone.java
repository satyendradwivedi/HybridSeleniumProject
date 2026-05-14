package com.iamguru.AI.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import com.iamguru.AI.utilities.ClickActions;
import com.iamguru.AI.utilities.JavaScriptUtils;

public class TimeZone extends BasePage {


	ClickActions ca;
	JavaScriptUtils ju;

	public TimeZone(WebDriver driver) {
		super(driver);
		ca = new ClickActions(driver);
		ju=new JavaScriptUtils(driver);

	}

	@FindBy(xpath = "//button[normalize-space()='Confirm']")
	@CacheLookup
	private WebElement timezoneconfirm;


public void timeZoneConfirm()
{
	// Ensure we're in the main window context
	try {
		driver.switchTo().defaultContent();

		// Try direct JavaScript click to bypass WebDriver issues
		try {
			((org.openqa.selenium.JavascriptExecutor) driver)
				.executeScript("var btn = document.querySelector(\"button[normalize-space()='Confirm']\") || document.querySelector(\"button:contains('Confirm')\"); if(btn) btn.click();");
		} catch (Exception jsError) {
			// Fallback to normal click
			ca.clickElement(timezoneconfirm);
		}

	} catch (Exception e) {
		// If element not found, it might not be present - skip silently
		System.out.println("Timezone confirmation dialog not present or already handled: " + e.getMessage());
	}
}
}
