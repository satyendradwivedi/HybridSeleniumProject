package com.iamguru.AI.pageObjects;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iamguru.AI.utilities.ClickActions;
import com.iamguru.AI.utilities.JavaScriptUtils;

public class ProfileFixed extends BasePage{
	 private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);

	    // Instance variables
	    private final ClickActions ca;
	    private final JavaScriptUtils ju;
	    private final WebDriverWait wait;

	public ProfileFixed(WebDriver driver) {
		super(driver);

	        this.ca = new ClickActions(driver);
	        this.ju = new JavaScriptUtils(driver);
	        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath = "//span[normalize-space()='Profile']")
	WebElement profileMenu;

	@FindBy(xpath = "//i[@class='fas fa-trash-alt circle-icon']")
	WebElement ProfileCoverPage;

	@FindBy(xpath = "//i[@class='fas fa-pencil-alt fa-lg circle-icon']")
	WebElement ProfileBackgroundImage;

	@FindBy(xpath = "//button[normalize-space()='Delete']")
	WebElement DeleteButton;

	@FindBy(xpath = "//button[normalize-space()='Edit Profile Background']")
	WebElement EditProfileBackgroundButton;

	@FindBy(xpath = "//button[contains(text(),'Edit Profile Background')]")
	WebElement EditProfileBackgroundButtonAlt;

	@FindBy(xpath = "//button[contains(@class,'edit') and contains(text(),'Background')]")
	WebElement EditProfileBackgroundButtonAlt2;

	public void clickProfileMenu() {
		// First try regular click
		try {
			wait.until(ExpectedConditions.elementToBeClickable(profileMenu)).click();
		} catch (Exception e) {
			// If regular click fails, try JavaScript click
			try {
				ju.javaScriptClick(profileMenu);
			} catch (Exception e2) {
				// If JavaScript click fails, try Actions click
				try {
					ca.clickElement(profileMenu);
				} catch (Exception e3) {
					// If all click attempts fail, try scrolling into view first
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", profileMenu);
					wait.until(ExpectedConditions.elementToBeClickable(profileMenu)).click();
				}
			}
		}
	}

	public void clickProfileCoverPage() {
		wait.until(ExpectedConditions.elementToBeClickable(ProfileCoverPage)).click();
	}

	public void clickDeleteButton() {
		wait.until(ExpectedConditions.elementToBeClickable(DeleteButton)).click();
	}

	public void clickEditProfileBackgroundButton() {
		// Try multiple locator strategies
		WebElement buttonToClick = null;

		try {
			buttonToClick = wait.until(ExpectedConditions.elementToBeClickable(EditProfileBackgroundButton));
		} catch (Exception e1) {
			try {
				buttonToClick = wait.until(ExpectedConditions.elementToBeClickable(EditProfileBackgroundButtonAlt));
			} catch (Exception e2) {
				try {
					buttonToClick = wait.until(ExpectedConditions.elementToBeClickable(EditProfileBackgroundButtonAlt2));
				} catch (Exception e3) {
					// Try finding by partial text
					try {
						buttonToClick = driver.findElement(By.xpath("//button[contains(text(),'Edit') and contains(text(),'Background')]"));
					} catch (Exception e4) {
						// Try even more generic approach
						buttonToClick = driver.findElement(By.xpath("//button[contains(text(),'Edit')]"));
					}
				}
			}
		}

		if (buttonToClick != null) {
			try {
				buttonToClick.click();
			} catch (Exception e) {
				// Try JavaScript click if regular click fails
				ju.javaScriptClick(buttonToClick);
			}
		} else {
			throw new RuntimeException("Edit Profile Background button not found with any locator strategy");
		}
	}

	 public void uploadBackGroundImage() throws Exception {
		 File file = new File("C:\\Users\\Gursewak\\Downloads\\Gooru.AI\\Gooru.AI\\resources\\ProfileData\\QABackgroound.jpg");
			String filePath = '"' + file.getAbsolutePath() + '"';

			StringSelection selection = new StringSelection(filePath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

			Robot robot = new Robot();
			robot.delay(1000);

			// Paste file path
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			robot.delay(500);

			// Press Enter
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			// Publish the post using JavaScript click

			robot.delay(4000);

	 }

}