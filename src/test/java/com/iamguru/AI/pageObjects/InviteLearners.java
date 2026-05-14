package com.iamguru.AI.pageObjects;

import java.time.Duration;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.iamguru.AI.utilities.ClickActions;
import com.iamguru.AI.utilities.JavaScriptUtils;

public class InviteLearners extends BasePage{

	ClickActions ca;
	JavaScriptUtils ju;

	public InviteLearners(WebDriver driver) {
		super(driver);
		ca = new ClickActions(driver);
		ju=new JavaScriptUtils(driver);

	}

	// Web elements locators using @FindBy
	@FindBy(xpath = "//button[normalize-space()='Invite Learner']")
	private WebElement InviteLearnerButton;


	@FindBy(xpath = "//textarea[@id='emails']")
	private WebElement EmailTextBox;

	@FindBy(xpath = "//button[normalize-space()='Send Invitations']")
	private WebElement SendInvitationsButton;

	public void sendInvitation(String email) throws InterruptedException {
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	    ju.javaScriptClick(InviteLearnerButton);

	    EmailTextBox.clear();
	    Thread.sleep(1000);


	        EmailTextBox.sendKeys(email);
	        EmailTextBox.sendKeys(Keys.ENTER);  // confirm each email
	        Thread.sleep(500);                  // small wait between


	    ju.javaScriptClick(SendInvitationsButton);
	}





}
