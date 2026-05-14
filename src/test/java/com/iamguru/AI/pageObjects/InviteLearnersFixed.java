package com.iamguru.AI.pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iamguru.AI.utilities.ClickActions;
import com.iamguru.AI.utilities.JavaScriptUtils;

public class InviteLearnersFixed extends BasePage{

	ClickActions ca;
	JavaScriptUtils ju;
	WebDriverWait wait;

	public InviteLearnersFixed(WebDriver driver) {
		super(driver);
		ca = new ClickActions(driver);
		ju = new JavaScriptUtils(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

		// Elements

	@FindBy(xpath = "//button[normalize-space()='Invite Learner']")
	private WebElement InviteLearnerButton;

	@FindBy(xpath = "//button[contains(text(),'Invite')]")
	private WebElement InviteLearnerButtonAlt;

	@FindBy(xpath = "//button[contains(@class,'invite') or contains(@id,'invite')]")
	private WebElement InviteLearnerButtonAlt2;

	@FindBy(xpath = "//textarea[@id='emails']")
	private WebElement EmailTextBox;

	@FindBy(xpath = "//button[normalize-space()='Send Invitations']")
	private WebElement SendInvitationsButton;

	public void sendInvitation(String[] emails) {
		// Method for bulk upload of email invitations
		WebElement buttonToClick = null;

		// Try multiple locators for Invite Learner button
		try {
			buttonToClick = wait.until(ExpectedConditions.elementToBeClickable(InviteLearnerButton));
			buttonToClick.click();
		} catch (Exception e1) {
			try {
				buttonToClick = wait.until(ExpectedConditions.elementToBeClickable(InviteLearnerButtonAlt));
				buttonToClick.click();
			} catch (Exception e2) {
				try {
					buttonToClick = wait.until(ExpectedConditions.elementToBeClickable(InviteLearnerButtonAlt2));
					buttonToClick.click();
				} catch (Exception e3) {
					try {
						buttonToClick = driver.findElement(By.xpath("//button[contains(text(),'Invite') or contains(text(),'invite')]"));
						buttonToClick.click();
					} catch (Exception e4) {
						throw new RuntimeException("Invite Learner button not found with any locator");
					}
				}
			}
		}

		// Join multiple email addresses with comma separator for bulk upload
		String bulkEmails = String.join(",", emails);
		wait.until(ExpectedConditions.elementToBeClickable(EmailTextBox));
		EmailTextBox.sendKeys(bulkEmails);

		wait.until(ExpectedConditions.elementToBeClickable(SendInvitationsButton));
		SendInvitationsButton.click();
	}

}