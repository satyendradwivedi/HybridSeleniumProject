package com.iamguru.AI.testCases;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.iamguru.AI.pageObjects.Website;
import com.iamguru.AI.testdata.User;
import com.iamguru.AI.utilities.TestDataLoader;
import com.iamguru.AI.utilities.TestDataGenerator;

@Listeners(com.iamguru.AI.utilities.TestListener.class)
public class WebsiteTest extends BaseClass {

    private Website website;
    private User user;

    @BeforeMethod
    public void setUp() {

        logger.info("========== TEST SETUP START ==========");

        website = new Website(driver);

        user = TestDataLoader.getUserData();

        logger.info("Loading base user data from JSON");

        // Dynamic data generation
        user.setFirstName(TestDataGenerator.generateFirstName());
        user.setLastName(TestDataGenerator.generateLastName());
        user.setEmail(TestDataGenerator.generateEmail());
        user.setPhone(TestDataGenerator.generatePhoneNumber());
        user.setPassword(TestDataGenerator.generatePassword());
        user.setconfirmPassword(user.getPassword());
        user.setDomainUrl(user.getDomainUrl());
        user.setAgentName(user.getAgentName());
        user.setDomain(user.getDomain());
        //user.setknowledgebaseurl(user.getknowledgebaseurl());

        logger.info("Generated Test User:");
        logger.info("Email: " + user.getEmail());
        logger.info("Password: " + user.getPassword());
        logger.info("Phone: " + user.getPhone());

        logger.info("========== TEST SETUP END ==========");
    }

    // ================= TEST 1 =================

    @Test(priority = 1)
    public void testWebsiteNavigation() {

        logger.info("===== TEST 1: Website Navigation START =====");

        driver.get(user.getUrl());
        logger.info("Navigated to URL: " + user.getUrl());

        String actualTitle = driver.getTitle();
        logger.info("Page Title: " + actualTitle);

        try {
            Assert.assertEquals(actualTitle,
                "GooruAI | AI Infrastructure & Intelligence Platform for Businesses");

            logger.info("✅ Title validation PASSED");

        } catch (AssertionError e) {
            logger.error("❌ Title validation FAILED");
            logger.error("Actual Title: " + actualTitle);
            throw e;
        }

        logger.info("===== TEST 1: END =====");
    }

    // ================= TEST 2 =================

    @Test(priority = 2)
    public void agentInfo() {

        logger.info("===== TEST 2: Registration Flow START =====");

        driver.get(user.getUrl());
        logger.info("Navigated to URL for registration");

        try {
            website.completeRegistrationFlow(user);
            logger.info("✅ Registration Flow Completed Successfully");

        } catch (Exception e) {
            logger.error("❌ Registration Flow FAILED: " + e.getMessage());
            throw e;
        }

        logger.info("===== TEST 2: END =====");
    }

    // ================= TEST 3 =================

    @Test(priority = 3)
    public void iamGuruInetgrationTest() {

        logger.info("===== TEST 3: IAM Guru Integration START =====");

        website.iamguruInegration();
        logger.info("Integration triggered");

        boolean status = website.isIntegrationSuccessful();

        logger.info("Integration Status: " + status);

        Assert.assertTrue(status, "IAM Guru integration failed");

        logger.info("✅ Integration PASSED");

        logger.info("===== TEST 3: END =====");
    }

    // ================= TEST 4 =================

    @Test(priority = 4)
    public void paymentTest() {

        logger.info("===== TEST 4: Payment Flow START =====");

        logger.info("Navigated to URL for payment");

        website.completePaymentFlow();
        logger.info("Payment flow executed");

        boolean status = website.isPaymentSuccessful();

        logger.info("Payment Status: " + status);
        logger.info("Final URL: " + driver.getCurrentUrl());
        logger.info("Page Title: " + driver.getTitle());

        Assert.assertTrue(status,
            "Payment flow failed. Current URL: " + driver.getCurrentUrl());

        logger.info("✅ Payment PASSED");

        logger.info("===== TEST 4: END =====");
    }

    @Test(priority = 5)
    public void validateLogo() {
        logger.info("===== TEST 5: Logo Validation START =====");
        logger.info("Navigated to URL for logo validation");
        website.logoclick();

        boolean isLogoDisplayed = website.isLogoDisplayed();
        logger.info("Is Gooru Logo Displayed: " + isLogoDisplayed);

        Assert.assertTrue(isLogoDisplayed, "Gooru logo is not displayed on the homepage");
        logger.info("✅ Logo Validation PASSED");

        logger.info("===== TEST 5: END =====");
    }

    // ================= TEST 6 — DEBUG: dump DOM after educationAndTraining click =================

    @Test(priority = 6)
    public void validateCreateAgent() {
        logger.info("===== TEST 6: Create Agent Validation START =====");

        website.createDigitalTwinFlow(user);
        wait.until(ExpectedConditions.urlContains("/builder"));
        String actual = driver.getCurrentUrl();
        Assert.assertTrue(actual.contains("/builder"), "Create Agent flow failed. Current URL: " + actual);
        logger.info("===== TEST 6: END =====");
    }
    
    @Test(priority = 7)
    
    public void validateDashboardbedoreAgenetIntercation() {
		logger.info("==TEST 7: validateDashboardbedoreAgenetIntercation===");

		website.logoclick();
		

		logger.info("===== TEST 7: END =====");
    
    
}
}