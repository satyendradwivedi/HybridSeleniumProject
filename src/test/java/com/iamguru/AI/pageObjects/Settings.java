package com.iamguru.AI.pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iamguru.AI.utilities.ClickActions;
import com.iamguru.AI.utilities.JavaScriptUtils;

public class Settings extends BasePage{
	private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(20);
	private final ClickActions ca;
	private final JavaScriptUtils ju;
	private final WebDriverWait wait;

	public Settings(WebDriver driver) {
		super(driver);
		this.ca = new ClickActions(driver);
		this.ju = new JavaScriptUtils(driver);
		this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
	}

	@FindBy(xpath = "//span[normalize-space()='Settings']")
	private WebElement SettingsMenu;

	@FindBy(xpath = "//button[@class='btn btn-primary edit-btn']")
	private WebElement EditProfileButton;

	@FindBy(xpath = "//button[@type='button'][normalize-space()='Save Changes']")
	private WebElement SaveChangesButton;

	@FindBy(xpath = "//div[@class='row mt-4']//button[@class='btn btn-primary save-btn'][normalize-space()='Save Changes']")
	private WebElement SaveChangesButtonInPassword;

	@FindBy(xpath = "//div[@aria-label='Password Updated successfully']")
	private WebElement PasswordUpdateSuccessMessage;

	@FindBy(xpath = "//input[@placeholder='First Name']")
	private WebElement FirstNameTextBox;

	@FindBy(xpath = "//div[@aria-label='Updated successfully']")
	private WebElement UpdateSuccessMessage;

	@FindBy(xpath = "//input[@placeholder='Current Password']")
	private WebElement CurrentPasswordTextBox;

	@FindBy(xpath = "//input[@placeholder='New Password']")
	private WebElement NewPasswordTextBox;

	@FindBy(xpath = "//input[@placeholder='Confirm Password']")
	private WebElement ConfirmNewPasswordTextBox;



	public void settingPageAccess(String firstName) throws InterruptedException {
		ca.clickElement(SettingsMenu);
		ca.clickElement(EditProfileButton);
		Thread.sleep(5000);
		FirstNameTextBox.clear();
		FirstNameTextBox.sendKeys(firstName);
		ca.clickElement(SaveChangesButton);
	}

	public void changePassword(String currentPassword, String newPassword,String confirmPassword) throws InterruptedException {
		ca.clickElement(SettingsMenu);
		ca.clickElement(EditProfileButton);
		Thread.sleep(5000);
		CurrentPasswordTextBox.sendKeys(currentPassword);
		NewPasswordTextBox.sendKeys(newPassword);
		ConfirmNewPasswordTextBox.sendKeys(newPassword);
		ju.javaScriptClick(SaveChangesButtonInPassword);
	}

	public String passwordUpdateConfirmation() {
		return PasswordUpdateSuccessMessage.getText();
	}
	public boolean isUpdateSuccessful() {
		return UpdateSuccessMessage.isDisplayed();
	}



}
