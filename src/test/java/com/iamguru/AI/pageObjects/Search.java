package com.iamguru.AI.pageObjects;

import java.time.Duration;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iamguru.AI.utilities.ClickActions;
import com.iamguru.AI.utilities.JavaScriptUtils;

public class Search extends BasePage
{
	private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(20);

private final ClickActions ca;
private final JavaScriptUtils ju;
private final WebDriverWait wait;

public Search(WebDriver driver) {
    super(driver);
    this.ca = new ClickActions(driver);
    this.ju = new JavaScriptUtils(driver);
    this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
}

// ==== Elements ====

@FindBy(xpath = "//input[@placeholder='Press Enter to Search']")
private WebElement searchInput;

@FindBy(xpath = "//div[normalize-space()='Satya Dwivedi']")
private WebElement searchResultsContainer;
// ==== Actions ====
public void enterSearchText(String text) {
	ju.javaScriptSendKeys(searchInput, text);
	searchInput.sendKeys(Keys.ENTER);


}

public  String searchedData() {
	// TODO Auto-generated method stub
	String  data=searchResultsContainer.getText();
	return data;

}




}
