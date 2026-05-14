package com.iamguru.AI.testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.iamguru.AI.pageObjects.AdminLogin;
import com.iamguru.AI.pageObjects.ChatBot;
import com.iamguru.AI.pageObjects.DigitalTwin;
import com.iamguru.AI.pageObjects.EditUserPage;
import com.iamguru.AI.pageObjects.Email;
import com.iamguru.AI.pageObjects.LoginPage;
import com.iamguru.AI.pageObjects.ManageUser;
import com.iamguru.AI.pageObjects.TimeZone;
import com.iamguru.AI.utilities.ReadConfig;
import com.iamguru.AI.utilities.TestDataGenerator;

public class AdminRegressionTest extends BaseClass {




	private ReadConfig rc = new ReadConfig();
	private String emailurl = rc.getTempemailURL();
	String AdminUrl=rc.getApplicationAdminURL();
	String adminEmail=rc.getAdminEmail();
	String adminpwd=rc.getAdminPassword();

	private com.iamguru.AI.pageObjects.AdminLogin adminlogin;
	private com.iamguru.AI.pageObjects.ManageUser manageuser;
	private com.iamguru.AI.pageObjects.Email e;
	private com.iamguru.AI.pageObjects.LoginPage lp;
	private com.iamguru.AI.pageObjects.TimeZone tz;
	private com.iamguru.AI.pageObjects.ChatBot cb;
	private com.iamguru.AI.pageObjects.EditUserPage edituser;
	private com.iamguru.AI.pageObjects.DigitalTwin digitaltwin;


	private String FName = TestDataGenerator.generateFirstName();
	private String LName = TestDataGenerator.generateLastName();
	private String Email = TestDataGenerator.generateEmail();
	private String password = TestDataGenerator.generatePassword();
	private String phone = TestDataGenerator.generatePhoneNumber();
	private String dob = TestDataGenerator.generateDOBcal();

	@BeforeMethod
	public void setUp() {

		adminlogin = new AdminLogin(driver);
		manageuser = new ManageUser(driver);
		e = new Email(driver);
		lp = new LoginPage(driver);
		tz = new TimeZone(driver);
		cb = new ChatBot(driver);
		edituser = new EditUserPage(driver);
		digitaltwin = new DigitalTwin(driver);
	}




	 @Test(priority = 1, enabled = true)
		public void validateAdmin() {

		    driver.get(AdminUrl);
			logger.info("********** Admin Login Test Started **********");
			adminlogin.login(adminEmail,adminpwd);

			wait.until(ExpectedConditions.urlContains("dashboard"));

			Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Admin login failed or dashboard not loaded.");
			adminlogin.logout();

			logger.info("********** Admin Login Test Ended **********");



}
	 @Test(priority = 2, enabled = true)
		public void validateAdminwithRememberMe() {


			logger.info("********** Admin Login Test Started **********");
			adminlogin.loginWithRememberMe(adminEmail,adminpwd);
			wait.until(ExpectedConditions.urlContains("dashboard"));


			adminlogin.logout();
			Assert.assertTrue(adminlogin.isRememberMeChecked(), "Remember Me checkbox is not selected.");

			logger.info("********** Admin Login Test Ended **********");


}

	 @Test(priority = 3, enabled = true)
	 		public void validateForgotPasswordLink() {


			logger.info("********** Admin Forgot Password Test Started **********");
			adminlogin.clickForgotPassword();

			wait.until(ExpectedConditions.urlContains("forgot-password"));

			Assert.assertTrue(driver.getCurrentUrl().contains("forgot-password"), "Forgot Password link is not working.");

			logger.info("********** Admin Forgot Password Test Ended **********");
			}

	 @Test(priority = 4, enabled = true)
	 		public void validateUserInManageUser() {

		    driver.get(AdminUrl);
			logger.info("********** Admin Login Test Started **********");
			adminlogin.login(adminEmail,adminpwd);

			wait.until(ExpectedConditions.urlContains("dashboard"));
			manageuser.manageUserMenuClick();
			wait.until(ExpectedConditions.urlContains("users"));
			Assert.assertTrue(driver.getCurrentUrl().contains("users"), "Manage Users page not loaded");


	 }

	 @Test(priority = 5, enabled = true)
 		public void validateaddnewlearner() {

	    manageuser.addUserButtonClick();
	    manageuser.createUser(FName, LName, Email, password, dob, phone);
	    wait.until(ExpectedConditions.urlContains("users"));
	    driver.navigate().refresh();
	    wait.until(ExpectedConditions.urlContains("users"));

	   //Assert.assertTrue(manageuser.isUserPresentInTable(Email), "New learner user not found in the user table.");
	 }

	 @Test(priority = 6, enabled = true)
	 public void validateemailverificationfornewlearner() throws InterruptedException {


			driver.get(emailurl);
			e.enterEmail(Email);

			e.clickGoToInbox();
			e.openVerificationEmail();

			// Add debug info before clicking
			System.out.println("About to click 'Log In to Your Account' button");
			try {
				e.clickLoginToYourAccount();
				System.out.println("Successfully clicked 'Log In to Your Account' button");
			} catch (Exception ex) {
				System.out.println("Failed to click 'Log In to Your Account' button: " + ex.getMessage());
				throw ex;
			}

			e.switchToNewWindow();
			String currentUrl = driver.getCurrentUrl();
			System.out.println("Current URL: " + currentUrl);
			//boolean isLoginPage = currentUrl.contains("login") || currentUrl.contains("signin") || currentUrl.contains("auth");
			//Assert.assertTrue(isLoginPage, "Email verification link is not working properly. Current URL: " + currentUrl);
			lp.setEmail(Email);
			logger.info("Email is:"+Email);
			lp.setPassword(password);
			logger.info("Password is:"+password);
			lp.clickSigninButton();
			e.switchToNewWindow();

	 }


	 @Test(priority = 7, enabled = true)
	 public void emailVerification() {

	     logger.info("Starting Email Verification Test");



	     driver.get(emailurl);

	     e.enterEmail(Email);
	     e.clickGoToInbox();

	     // 🔥 Wait & retry until email is available
	     for (int i = 0; i < 5; i++) {
	         try {
	             e.openVerificationEmail();
	             break;
	         } catch (Exception ex) {
	             logger.info("Email not loaded yet, retrying...");
	             driver.navigate().refresh();
	         }
	     }

	     // 🔥 Switch to iframe (MANDATORY)
	     wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("ifmail"));

	     logger.info("Switched to email iframe");

	     // 🔥 Robust locator for verify button
	     By verifyBtn = By.xpath("//a[contains(text(),'Verify') or contains(text(),'Log In') or contains(text(),'Activate')]");

	     WebElement verifyElement = wait.until(ExpectedConditions.elementToBeClickable(verifyBtn));

	     verifyElement.click();

	     logger.info("Clicked Verify Email button");

	     // 🔥 Switch back to main DOM
	     driver.switchTo().defaultContent();

	     // 🔥 Handle new tab
	     e.switchToNewWindow();

	     logger.info("Email verification completed.");
	 }
	 @Test(priority = 8, enabled = true)
	 public void registeredUserLogin() {

	     logger.info("Starting Login Test for Registered User");



	     String baseUrlLogin = rc.getApplicationURL();
	     driver.get(baseUrlLogin);
	     // Login
	     lp.setEmail(Email);
	     logger.info("Email is:" + Email);

	     lp.setPassword(password);
	     logger.info("Password is:" + password);

	     lp.clickSigninButton();

	     logger.info("User logged in successfully.");
	 }



	@Test(priority = 9, enabled = true)
	public void validateAdminisabletoaddDigitaTwintocreator() throws InterruptedException {

		logger.info("Starting assign digital Twin");
			driver.get(AdminUrl);
			wait.until(ExpectedConditions.urlContains("dashboard"));
			manageuser.manageUserMenuClick();
			wait.until(ExpectedConditions.urlContains("users"));
			Assert.assertTrue(driver.getCurrentUrl().contains("users"), "Manage Users page not loaded");
			adminlogin.clickEditIcon();
			adminlogin.selectAdminRole();
			Assert.assertTrue(adminlogin.isAdminRoleSelected(), "Admin role not selected");
			edituser.selectTwinType("ModernIP");
			edituser.selectTwin("Java Learning Twin");
			adminlogin.clickSaveButton();

			//adminlogin.logout();



	}

	@Test(priority = 10, enabled = true)

		public void accessDigitalTwin() throws InterruptedException {

			digitaltwin.clickDigitalTwin();
			wait.until(ExpectedConditions.urlContains("twin-listing"));
			Assert.assertTrue(driver.getCurrentUrl().contains("twin-listing"), "Digital Twin page not loaded");

}
	@Test(priority = 11, enabled = true)
	public void createDigitalTwin() throws InterruptedException {

		digitaltwin.createDigitalTwin();
		logger.info("Starting Create Digital Twin Test");
		digitaltwin.selectTwinIndustryType();
		digitaltwin.clickSaveAndContinueButton();
		digitaltwin.selectTwinUsecaseType();
		digitaltwin.clickSaveAndContinueButton();
		digitaltwin.enterTwinName("Java Learning Twin");
		Thread.sleep(2000);
		digitaltwin.clickCreateTwinButton();


}

@Test(priority = 12, enabled = true)

public void validateEditDigitalTwin() throws InterruptedException {

	digitaltwin.clickCreateTwinSuccessButton();
	logger.info("Starting Edit Digital Twin Agent Tab Test");
	digitaltwin.editAgentTab("Welcome to Java Support", "You are a helpful assistant for Java learners. Answer questions related to Java programming and provide code examples when necessary.");



}

@Test(priority = 13, enabled = true)
public void validateVoicetabFunctionality() throws InterruptedException {

	logger.info("Starting voice tab Functionality Test");
	digitaltwin.selectVoice();


}

@Test(priority = 14, enabled = true)

public void validateKnowledgeBaseTabFunctionality() throws InterruptedException {

	logger.info("Starting Knowledge Base Tab Functionality Test");
	digitaltwin.addKnowledgeBaseURL("https://www.geeksforgeeks.org/java/");

}

@Test(priority = 15, enabled = true)
public void validateDigitalTwinFunctionality() throws InterruptedException {

    logger.info("Starting interaction with Digital Twin");

    String audioPath = startVoiceCapture();
    digitaltwin.clickDigitalTwinAgent(audioPath);



    stopVoiceCapture();
    logger.info("Audio saved at: " + audioPath);
}
}
