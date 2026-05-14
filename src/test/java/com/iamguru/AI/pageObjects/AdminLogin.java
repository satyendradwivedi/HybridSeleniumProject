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

public class AdminLogin extends BasePage {

	private final ClickActions ca;
	private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(20);
	private final JavaScriptUtils ju;
	public final WebDriverWait wait;

	public AdminLogin(WebDriver driver) {
		super(driver);
		this.ca = new ClickActions(driver);
		this.ju = new JavaScriptUtils(driver);
		this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
	}

	@FindBy(xpath = "//input[@id='loginEmail']")
	private WebElement emailInput;

	@FindBy(xpath = "//input[@id='loginPassword']")
	private WebElement passwordInput;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement loginButton;

	@FindBy(xpath = "//input[@id='rememberMe']")
	private WebElement rememberMeCheckbox;

	@FindBy(xpath = "//a[normalize-space()='Forgot Password?']")
	private WebElement forgotPasswordLink;

	@FindBy(className = "login-form")
	private WebElement loginFormContainer;

	@FindBy(xpath = "//i[@class='mdi mdi-chevron-down d-none d-xl-inline-block']")
	private WebElement profileDropdown;

	@FindBy(xpath = "//a[normalize-space()='Logout']")
	private WebElement logoutLink;

	@FindBy(xpath = "//tbody/tr[1]/td[8]/ul[1]/li[2]/a[1]/i[1]")
	private WebElement editIcon;

	@FindBy(xpath = "//input[@id='role-2']")
	private WebElement adminRoleRadioButton;

	@FindBy(xpath = "//button[normalize-space()='Save']")
	private WebElement saveButton;

	public void clickSaveButton() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
		wait.until(ExpectedConditions.visibilityOf(saveButton));
		ju.javaScriptClick(saveButton);
	}

	public void selectAdminRole() {

		wait.until(ExpectedConditions.visibilityOf(adminRoleRadioButton));
		ju.javaScriptClick(adminRoleRadioButton);
	}

	public void clickEditIcon() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
		wait.until(ExpectedConditions.visibilityOf(editIcon));
		ju.javaScriptClick(editIcon);
	}

	public void logout() {
		wait.until(ExpectedConditions.visibilityOf(profileDropdown));
		ju.javaScriptClick(profileDropdown);
		wait.until(ExpectedConditions.visibilityOf(logoutLink));
		ju.javaScriptClick(logoutLink);
	}

	public void enterEmail(String email) {
		wait.until(ExpectedConditions.visibilityOf(emailInput));
		emailInput.clear();
		emailInput.sendKeys(email);
	}

	public void enterPassword(String password) {
		passwordInput.clear();
		passwordInput.sendKeys(password);
	}

	public void clickLoginButton() {
		ju.javaScriptClick(loginButton);
	}

	public void checkRememberMe() {
		if (!rememberMeCheckbox.isSelected()) {
			ju.javaScriptClick(rememberMeCheckbox);
		}
	}

	public void uncheckRememberMe() {
		if (rememberMeCheckbox.isSelected()) {
			rememberMeCheckbox.click();
		}
	}

	public void clickForgotPassword() {
		forgotPasswordLink.click();
	}

	public boolean isLoginFormDisplayed() {
		return loginFormContainer.isDisplayed();
	}

	public String getEmailValue() {
		return emailInput.getAttribute("value");
	}

	public String getPasswordValue() {
		return passwordInput.getAttribute("value");
	}

	public boolean isRememberMeChecked() {
		return rememberMeCheckbox.isSelected();
	}

	public boolean isLoginButtonEnabled() {
		return loginButton.isEnabled();
	}

	public void login(String email, String password) {
		wait.until(ExpectedConditions.visibilityOf(emailInput));
		enterEmail(email);
		enterPassword(password);
		clickLoginButton();
	}

	public void loginWithRememberMe(String email, String password) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginEmail")));
		enterEmail(email);
		enterPassword(password);
		checkRememberMe();
		clickLoginButton();
	}

	public By getInputFieldByName(String fieldName) {
		return By.name(fieldName);
	}

	public By getButtonByText(String buttonText) {
		return By.xpath("//button[text()='" + buttonText + "']");
	}

	public By getFormFieldByLabel(String labelText) {
		return By.xpath("//label[text()='" + labelText + "']/following-sibling::input");
	}

	public boolean isAdminRoleSelected() {
		return adminRoleRadioButton.isSelected();
	}
}
