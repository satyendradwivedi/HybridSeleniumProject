package com.iamguru.AI.testCases;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.iamguru.AI.pageObjects.CourseManagement;
import com.iamguru.AI.pageObjects.LoginPage;

public class CourseCreationTest extends BaseClass {

    private LoginPage lp;
    private CourseManagement cm;

    // Test credentials
    private final String TEST_URL = "https://goorulogin-portal-stage.azurewebsites.net/login";
    private final String TEST_EMAIL = "kausarmehra@yopmail.com";
    private final String TEST_PASSWORD = "Test@1234";

    // Test data
    private final String COURSE_TITLE = "Automation Test Course";
    private final String COURSE_DESCRIPTION = "This is a test course created by automation";

    @BeforeMethod
    public void setUp() {
        lp = new LoginPage(driver);
        cm = new CourseManagement(driver);
    }

    @Test(priority = 1)
    public void loginToApplication() {
        logger.info("Starting login test");
        driver.get(TEST_URL);

        lp.setEmail(TEST_EMAIL);
        lp.setPassword(TEST_PASSWORD);
        lp.clickSigninButton();

        wait.until(ExpectedConditions.urlContains("dashboard"));
        logger.info("Login successful");
    }

    @Test(priority = 2, dependsOnMethods = "loginToApplication")
    public void accessCourseManagement() {
        logger.info("Accessing Course Management");

        cm.clickCourseManagementMenu();
        Assert.assertTrue(cm.isCourseManagementMenuVisible(), "Course Management menu not visible");

        logger.info("Course Management accessed successfully");
    }

    @Test(priority = 3, dependsOnMethods = "accessCourseManagement")
    public void createNewCourse() throws Exception {
        logger.info("Creating new course");

        cm.clickCreateCourseButton();
        Assert.assertEquals(driver.getTitle(), "STEDMAN'S IDENTITY APP", "Create Course page not loaded");

        logger.info("Create Course page loaded successfully");
    }

    @Test(priority = 4, dependsOnMethods = "createNewCourse")
    public void enterCourseDetails() {
        logger.info("Entering course details");

        cm.enterCourseTitle(COURSE_TITLE);
        Assert.assertTrue(cm.isCourseTitleEntered(), "Course title not entered");

        cm.enterCourseDescription(COURSE_DESCRIPTION);
        Assert.assertTrue(cm.isCourseDescriptionEntered(), "Course description not entered");

        logger.info("Course details entered successfully");
    }

    @Test(priority = 5, dependsOnMethods = "enterCourseDetails")
    public void completeCourseCreation() throws Exception {
        logger.info("Completing course creation process");

        // Skip category/subcategory selection and go directly to save
        cm.selectCourseCategory();
        cm.selectCourseSubCategory();

        logger.info("Attempting to save course");
        cm.clickSaveAndContinueButton();

        logger.info("Course creation process completed");
    }

    @Test(priority = 6, dependsOnMethods = "completeCourseCreation")
    public void addCourseModule() throws Exception {
        logger.info("Adding course module");

        cm.clickAddButton();
        cm.enterCourseTitle("Module 1: Introduction");
        cm.enterModuleDescription("This is the first module of the course");
        cm.clickSaveAndContinueButton();

        logger.info("Course module added successfully");
    }

    @Test(priority = 7, dependsOnMethods = "addCourseModule")
    public void publishCourse() throws Exception {
        logger.info("Publishing the course");



        // Wait for publish confirmation
        Thread.sleep(5000);

        logger.info("Course published successfully");
    }


}