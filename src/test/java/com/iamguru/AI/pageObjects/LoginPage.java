package com.iamguru.AI.pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import com.iamguru.AI.utilities.ClickActions;
import com.iamguru.AI.utilities.JavaScriptUtils;

public class LoginPage extends BasePage {

	ClickActions ca;
	JavaScriptUtils ju;

	public LoginPage(WebDriver driver) {
		super(driver);
		ca = new ClickActions(driver);
		ju=new JavaScriptUtils(driver);

	}

	@FindBy(xpath = "//input[@id='loginEmail']")
	@CacheLookup
	private WebElement textemail;

	@FindBy(xpath = "//input[@id='loginPassword']")
	@CacheLookup
	private WebElement txtPassword;

	@FindBy(xpath = "//button[@type='submit']")
	@CacheLookup
	private WebElement btnSubmit;

	@FindBy(xpath = "//i[@class='mdi mdi-chevron-down d-none d-xl-inline-block']")
	@CacheLookup
	private WebElement profilelink;

	@FindBy(xpath = "//a[normalize-space()='Logout']")
	@CacheLookup
	private WebElement lnkLogout;

	@FindBy(xpath = "//div[@aria-label='Invalid username or password.']")
	@CacheLookup
	WebElement validationmessage;

	@FindBy(xpath = "//div[text()='Email is required']")
	@CacheLookup
	WebElement valmessageuser;
	@FindBy(xpath = "//*[text()='Password is required']")
	@CacheLookup
	WebElement valmessagepass;

	@FindBy(xpath = "//a[normalize-space()='Google']")
	@CacheLookup
	WebElement glogin;

	@FindBy(xpath = "//input[@id='identifierId']")
	@CacheLookup
	private WebElement email;

	@FindBy(xpath = "//*[text()='Next']")
	@CacheLookup
	private WebElement next;

	@FindBy(xpath = "//input[@type='password']")
	@CacheLookup
	private WebElement password;

	@FindBy(xpath = "//input[@type='checkbox']")
    private WebElement rememberMeCheckbox;

    @FindBy(xpath = "//a[contains(text(), 'Forgot') or contains(@href, 'forgot')]")
    private WebElement forgotPasswordLink;

    @FindBy(xpath = "//img[contains(@src, 'logo')]")
    private WebElement logoImage;

	public void setEmail(String emailtext) {
		textemail.clear();
		textemail.sendKeys(emailtext);

	}

	public void setPassword(String pwd) {
		txtPassword.clear();
		txtPassword.sendKeys(pwd);
	}

	public void clickSigninButton() {
	ju.javaScriptClick(btnSubmit);
	}

	public void clickLogoutButton() throws InterruptedException {
		ca.clickElement(profilelink);
		Thread.sleep(4000);
		ca.clickElement(lnkLogout);
	}

	public String valmessage() {
		return validationmessage.getText();

	}

	public String valmessageemptyuser() {
		return valmessageuser.getText();

	}

	public String valmessageemptypass() {

		return valmessagepass.getText();

	}

	public void gLogin() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		ca.clickElement(glogin);

	}

	public void setGoogleemail(String temail) {

		email.sendKeys(temail);
		ca.clickElement(next);

	}

	public void setGooglPassword(String pass) {

		password.sendKeys(pass);

	}

	public boolean isDashboardVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isLoginPageVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isLoginErrorVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isLoginPageDisplayed() {
        try {
            WebElement dashboard = driver.findElement(By.xpath("//div[@class='dashboard-container']"));
            return dashboard.isDisplayed();
        } catch (Exception e) {
            return false;
        }
	}

	public void checkRememberMe() {
        if (!rememberMeCheckbox.isSelected()) {
            rememberMeCheckbox.click();
        }
    }

    public void uncheckRememberMe() {
        if (rememberMeCheckbox.isSelected()) {
            rememberMeCheckbox.click();
        }
    }
    public void clickForgotPasswordLink() {
        forgotPasswordLink.click();
    }

    public boolean isLogoDisplayed() {
        return logoImage.isDisplayed();
    }

    public boolean isRememberMeChecked() {
        return rememberMeCheckbox.isSelected();
    }



}
