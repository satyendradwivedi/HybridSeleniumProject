package com.iamguru.AI.testCases;

import java.awt.AWTException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.iamguru.AI.pageObjects.ChatBot;
import com.iamguru.AI.pageObjects.Email;
import com.iamguru.AI.pageObjects.LoginPage;
import com.iamguru.AI.pageObjects.Messaging;
import com.iamguru.AI.pageObjects.Profile;
import com.iamguru.AI.pageObjects.Registration;
import com.iamguru.AI.pageObjects.TimeZone;
import com.iamguru.AI.utilities.ReadConfig;
import com.iamguru.AI.utilities.TestDataGenerator;

public class ProfileTest extends BaseClass{

	private LoginPage lp;
	private Profile pr;
    private Registration rp;
    private Messaging messaging;
    private TimeZone tz;
    private Email e;
    private ChatBot cb;
    private ReadConfig rc = new ReadConfig();
    private String emailurl = rc.getTempemailURL();
    private String title = rc.getTile();
    private String description = rc.getDescription();
    private String date = rc.getDate();
    private String hour = rc.getHour();
    private String minute = rc.getMinute();
    private String emailstage = rc.getEmailstage();
    private String staticPassword = rc.getPassword();
    private String FName = TestDataGenerator.generateFirstName();
    private String LName = TestDataGenerator.generateLastName();
    private String Email = TestDataGenerator.generateEmail();
    private String password = TestDataGenerator.generatePassword();
    private String phone = TestDataGenerator.generatePhoneNumber();
    private String dob = TestDataGenerator.generateDOB();
    private String bio = TestDataGenerator.generateLearnerBio();
    private String platformName = TestDataGenerator.getPlatformName();
	private String platformUrl = TestDataGenerator.getPlatformUrl();
	private String quality = TestDataGenerator.getQuality();

    @BeforeMethod
    public void setUp() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(120));
        lp = new LoginPage(driver);
        messaging = new Messaging(driver);
        rp = new Registration(driver);
        tz = new TimeZone(driver);
        e = new Email(driver);
        cb = new ChatBot(driver);
        pr=new Profile(driver);
    }

    @Test(priority = 1, enabled = true)
    public void registrationFormTest() {
        logger.info("Test Case: registrationFormTest - Started");

        logger.info("Entering registration details: First Name = {}, Last Name = {}, Email = {}");
        rp.setFirstName(FName);
        rp.setLastName(LName);
        rp.setEmail(Email);
        rp.setPassword(password);
        rp.setConfirmPassword(password);
        rp.setPhoneNumber(phone);
        rp.setDOB(dob);

        logger.info("Agreeing to terms and submitting the registration form");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='checkbox']")));
        rp.clickAgreeTerms();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Submit']")));
        rp.clickSubmit();

        logger.info("Verifying registration success message");
        Assert.assertTrue(rp.verificationMessage(), "Email verification message not displayed.");
        logger.info("Test Case: registrationFormTest - Completed Successfully");
    }

    @Test(priority = 2, enabled = true, dependsOnMethods = "registrationFormTest")
    public void emailVerification() {
        logger.info("Test Case: emailVerification - Started");

        logger.info("Navigating to temporary email site at {}");
        driver.get(emailurl);

        logger.info("Entering email: {}");
        e.enterEmail(Email);
        e.clickGoToInbox();

        logger.info("Opening verification email and clicking on verify button");
        e.openVerificationEmail();
        e.clickVerifyEmailButton();
        e.switchToNewWindow();

        logger.info("Checking for 'Authentication Successful' message");
        WebElement successMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Authentication Successful!!']")));
        Assert.assertTrue(successMsg.isDisplayed(), "Authentication not successful");

        logger.info("Test Case: emailVerification - Completed Successfully");
    }

    @Test(priority = 3, enabled = true, dependsOnMethods = "emailVerification")
    public void registeredUserLogin() {
        logger.info("Test Case: registeredUserLogin - Started");

        logger.info("Returning to login page");
        e.backToLoginPage();

        logger.info("Entering login credentials: Email = {}");
        lp.setEmail(Email);
        lp.setPassword(password);
        lp.clickSigninButton();

        logger.info("Confirming timezone settings");
        tz.timeZoneConfirm();

        logger.info("Test Case: registeredUserLogin - Completed Successfully");
    }

    @Test(priority = 4, enabled = true, dependsOnMethods = "registeredUserLogin")
    public void profileCompleteMandatory() {
        logger.info("Test Case: profileCompleteMandatory - Started");

        logger.info("Selecting category and entering bio");
        cb.selectCategory();
        cb.learnerBio(bio);

        logger.info("Selecting topics");
        cb.selectTopics();

        logger.info("Test Case: profileCompleteMandatory - Completed Successfully");
    }
    @Test(priority = 5, enabled = true)
	public void profileComplete100() throws AWTException, InterruptedException {
		logger.info("Completing full profile to 100%");
		cb.profile100Complete(FName, FName, platformName, platformUrl, quality);
	}
@Test(priority = 6, enabled = true, dependsOnMethods = "profileCompleteMandatory")
public void validateProfileMenuAccess() {
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
	logger.info("Logging in as 'kausarmehra@yopmail.com'");
    driver.get(baseUrlLogin);
    lp.setEmail(Email);
    lp.setPassword(password);
    lp.clickSigninButton();
    logger.info("Test Case: validateProfileMenuAccess - Started");

    logger.info("Clicking on the profile menu");
    pr.clickProfileMenu();



}



}
