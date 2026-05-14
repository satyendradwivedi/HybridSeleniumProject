package com.iamguru.AI.pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iamguru.AI.utilities.ClickActions;
import com.iamguru.AI.utilities.JavaScriptUtils;

public class EditUserPage extends BasePage {

	private final ClickActions ca;
	private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(20);
	private final JavaScriptUtils ju;
	public final WebDriverWait wait;

    public EditUserPage(WebDriver driver) {
    	super(driver);
		this.ca = new ClickActions(driver);
		this.ju = new JavaScriptUtils(driver);
		this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    @FindBy(xpath = "//input[@name='firstName']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@name='lastName']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[@name='email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@name='phoneNumber']")
    private WebElement phoneNumberInput;

    @FindBy(xpath = "//button[contains(text(), 'Save')]")
    private WebElement saveButton;

    @FindBy(xpath = "//button[contains(text(), 'Cancel')]")
    private WebElement cancelButton;

    @FindBy(xpath = "//button[contains(text(), 'Delete')]")
    private WebElement deleteButton;

    @FindBy(xpath = "//input[@type='checkbox' and @name='active']")
    private WebElement activeCheckbox;

    @FindBy(xpath = "//select[@id='formrow-twinType-input']")
    private WebElement twintypeDropdown;

    @FindBy(xpath = "//select[@id='formrow-twinId-input']")
    private WebElement TwinDropdown;

    @FindBy(xpath = "//select[@name='school']")
    private WebElement schoolDropdown;

    @FindBy(xpath = "//a[contains(text(), 'Back to List')]")
    private WebElement backToListLink;

    @FindBy(xpath = "//img[@class='profile-image']")
    private WebElement profileImage;

    @FindBy(xpath = "//div[@class='error-message']")
    private WebElement errorMessages;

    @FindBy(xpath = "//div[@class='success-message']")
    private WebElement successMessage;



    public void enterFirstName(String firstName) {
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
    }

    public void enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void enterPhoneNumber(String phoneNumber) {
        phoneNumberInput.clear();
        phoneNumberInput.sendKeys(phoneNumber);
    }

    public void clickSaveButton() {
        saveButton.click();
    }

    public void clickCancelButton() {
        cancelButton.click();
    }

    public void clickDeleteButton() {
        deleteButton.click();
    }

    public void setActiveStatus(boolean isActive) {
        if (isActive != activeCheckbox.isSelected()) {
            activeCheckbox.click();
        }
    }

    public void selectTwinType(String value) {
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        longWait.until(org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loader-overlay")));
        longWait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf(twintypeDropdown));
        new Select(twintypeDropdown).selectByVisibleText(value);
    }

    public void selectTwin(String value) {
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf(TwinDropdown));
        new Select(TwinDropdown).selectByVisibleText(value);
    }

    public void selectSchool(String school) {
        Select select = new Select(schoolDropdown);
        select.selectByVisibleText(school);
    }

    public void clickBackToListLink() {
        backToListLink.click();
    }

    public String getFirstName() {
        return firstNameInput.getAttribute("value");
    }

    public String getLastName() {
        return lastNameInput.getAttribute("value");
    }

    public String getEmail() {
        return emailInput.getAttribute("value");
    }

    public String getPhoneNumber() {
        return phoneNumberInput.getAttribute("value");
    }

    public boolean isActiveSelected() {
        return activeCheckbox.isSelected();
    }



    public String getSelectedSchool() {
        Select select = new Select(schoolDropdown);
        return select.getFirstSelectedOption().getText();
    }

    public String getErrorMessage() {
        return errorMessages.getText();
    }

    public String getSuccessMessage() {
        return successMessage.getText();
    }

    public boolean isErrorMessageDisplayed() {
        return errorMessages.isDisplayed();
    }

    public boolean isSuccessMessageDisplayed() {
        return successMessage.isDisplayed();
    }



    // Dynamic locator methods
    public By getFieldByName(String fieldName) {
        return By.xpath("//input[@name='" + fieldName + "']");
    }

    public By getDropdownByName(String dropdownName) {
        return By.xpath("//select[@name='" + dropdownName + "']");
    }

    public By getButtonByText(String buttonText) {
        return By.xpath("//button[contains(text(), '" + buttonText + "')]");
    }

    public By getMessageByType(String messageType) {
        return By.xpath("//div[@class='" + messageType + "-message']");
    }

    public By getDropdownOptionByValue(String dropdownName, String optionValue) {
        return By.xpath("//select[@name='" + dropdownName + "']/option[@value='" + optionValue + "']");
    }
}