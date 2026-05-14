package com.iamguru.AI.pageObjects;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iamguru.AI.utilities.ClickActions;
import com.iamguru.AI.utilities.JavaScriptUtils;

public class Profile extends BasePage{
	 private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

	    // Instance variables
	    private final ClickActions ca;
	    private final JavaScriptUtils ju;
	    private final WebDriverWait wait;

	public Profile(WebDriver driver) {
		super(driver);

	        this.ca = new ClickActions(driver);
	        this.ju = new JavaScriptUtils(driver);
	        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath = "//span[normalize-space()='Profile']")
	WebElement profileMenu;

	@FindBy(xpath = "//button[@title='Edit Background Image']//i[@class='fas fa-pencil-alt fa-lg circle-icon']")
	WebElement ProfileCoverPageIcon;

	@FindBy(xpath = "//i[@class='fas fa-pencil-alt fa-lg circle-icon']")
	WebElement ProfileBackgroundImage;

	@FindBy(xpath = "//button[normalize-space()='Delete']")
	WebElement DeleteButton;

	@FindBy(xpath = "//button[normalize-space()='Edit Profile Background']")
	WebElement EditProfileBackgroundButton;

	@FindBy(xpath = "//button[normalize-space()='Upload']")
	WebElement UploadButton;

	@FindBy(xpath = "//button[@title='Edit Profile Image']")
	WebElement EditProfileImageIcon;

	@FindBy(xpath = "//button[normalize-space()='Add Photo']")
	WebElement AddPhotoButton;

	@FindBy(xpath = "//i[@title='Edit Profile Details']")
	WebElement EditProfileDetailsIcon;

	@FindBy(xpath = "//h4[normalize-space()='EDIT PROFILE']")
	WebElement EditProfileDetailLabel;

	@FindBy(xpath = "//i[@class='mdi mdi-pencil']")
	WebElement EditProfileIcon;


@FindBy(xpath = "//div[@class=\"d-flex flex-wrap gap-2 mt-2 mb-4\"]")
List<WebElement> LanguageOptions;

@FindBy(xpath = "//button[normalize-space()='Save Changes']")
WebElement SaveChangesButton;

@FindBy(xpath = "//body//app-root//app-stats-section//div[3]")
WebElement FollowingList;

public void clickFollowingList() {
	ju.javaScriptClick(FollowingList);
}

public boolean isFollowingListDisplayed() {
	try {
		wait.until(ExpectedConditions.visibilityOf(FollowingList));
		return FollowingList.isDisplayed();
	} catch (Exception e) {
		return false;
	}
}

public void selectLanguage(String language) {
	for (WebElement option : LanguageOptions) {
		if (option.getText().equalsIgnoreCase(language)) {
			wait.until(ExpectedConditions.elementToBeClickable(option)).click();
			break;
		}
	}
}
	public void clickEditProfileIcon() {
		ju.javaScriptClick(EditProfileIcon);
	}

	public boolean isEditProfileDetailsLabelDisplayed() {
		try {
			wait.until(ExpectedConditions.visibilityOf(EditProfileDetailLabel));
			return EditProfileDetailLabel.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickEditProfileDetailsIcon() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	ju.javaScriptClick(EditProfileDetailsIcon);
	clickEditProfileIcon();


	selectLanguage("English");
	ju.javaScriptClick(SaveChangesButton);
		//wait.until(ExpectedConditions.elementToBeClickable(EditProfileDetailsIcon)).click();
	}


	public void uploadImage() throws AWTException, InterruptedException
	{
		// Wait for element to be clickable instead of implicit wait
		wait.until(ExpectedConditions.elementToBeClickable(EditProfileImageIcon));
		ju.javaScriptClick(EditProfileImageIcon);

		wait.until(ExpectedConditions.elementToBeClickable(AddPhotoButton));
		// Try multiple click strategies since element is difficult to interact with
		try {
			wait.until(ExpectedConditions.elementToBeClickable(AddPhotoButton)).click();
		} catch (Exception e) {
			try {
				ju.javaScriptClick(AddPhotoButton);
			} catch (Exception e2) {
				try {
					ca.clickElement(AddPhotoButton);
				} catch (Exception e3) {
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", AddPhotoButton);
					wait.until(ExpectedConditions.elementToBeClickable(AddPhotoButton)).click();
				}
			}
		}

		 File file = new File("C:\\Users\\Gursewak\\Downloads\\Gooru.AI\\Gooru.AI\\resources\\ProfileData\\Mike Carlos.jpg");
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
			ju.javaScriptClick(UploadButton);


	}



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

	public void clickProfileCoverPageIcon() {
		wait.until(ExpectedConditions.elementToBeClickable(ProfileCoverPageIcon)).click();
	}

	public void clickDeleteButton() {
		wait.until(ExpectedConditions.elementToBeClickable(DeleteButton)).click();
	}

	public void clickEditProfileBackgroundButton() {
		ju.javaScriptClick(EditProfileBackgroundButton);
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
			ju.javaScriptClick(UploadButton);

	 }



    public boolean isProfileImageUpdated() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//button[normalize-space()='Upload']")));
            WebElement profileImage = driver.findElement(By.xpath("//img[@class='img-thumbnail rounded-circle']"));
            return profileImage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}
