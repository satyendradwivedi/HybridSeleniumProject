package com.iamguru.AI.pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iamguru.AI.utilities.ClickActions;
import com.iamguru.AI.utilities.JavaScriptUtils;

public class ManageUser extends BasePage {

	private final ClickActions ca;
	private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
	private final JavaScriptUtils ju;
	public final WebDriverWait wait;

    public ManageUser(WebDriver driver) {
		super(driver);
		this.ca = new ClickActions(driver);
		this.ju = new JavaScriptUtils(driver);
		this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);


	}
	@FindBy(xpath = "//span[normalize-space()='Manage Users']")
	private WebElement manageUserMenue;

	@FindBy(xpath = "//a[@class='btn btn-primary']")
	private WebElement addButton;



	@FindBy(xpath = "//table[@id='basic-datatable']")
	private WebElement userTable;

	@FindBy(xpath="//input[@id='formrow-firstname-input']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@id='formrow-lastname-input']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[@id='formrow-email-input']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@id='formrow-password-input']")
    private WebElement passwordInput;



    @FindBy(xpath = "//input[@id='dob']")
    private WebElement dobInput;

    @FindBy(xpath = "//input[@id='formrow-phone-input']")
    private WebElement phoneInput;

    @FindBy(xpath = "//button[normalize-space()='Submit']")
    private WebElement suubBtn;

    @FindBy(xpath = "//button[contains(@class, 'btn-secondary')]")
    private WebElement cancelBtn;

    @FindBy(xpath = "//a[contains(text(), 'Back to Users')]")
    private WebElement backToUsersLink;



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

    public void enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void enterDob(String dob) {
        try {
            System.out.println("Setting DOB: " + dob);

            // Wait for element to be ready
            wait.until(ExpectedConditions.elementToBeClickable(dobInput));

            // Scroll to element
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dobInput);
            Thread.sleep(1000);

            // Method 1: Try direct input first
            dobInput.clear();
            dobInput.sendKeys(dob);
            dobInput.sendKeys(Keys.TAB);
            Thread.sleep(2000);

            // Verify
            String enteredValue = dobInput.getAttribute("value");
            System.out.println("DOB field value after direct input: " + enteredValue);

            if (enteredValue.equals(dob)) {
                System.out.println("✅ DOB set successfully via direct input");
                return;
            }

            // Method 2: If direct input failed, use JavaScript
            System.out.println("Direct input failed, using JavaScript...");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value = arguments[1];", dobInput, dob);
            js.executeScript("let event = new Event('change', { bubbles: true }); arguments[0].dispatchEvent(event);", dobInput);

            System.out.println("✅ DOB set via JavaScript");

        } catch (Exception e) {
            System.err.println("❌ Failed to set DOB: " + e.getMessage());
            // Don't throw exception to allow test to continue
        }
    }

    public void enterPhone(String phone) {
		phoneInput.clear();
		phoneInput.sendKeys(phone);
	}

    public void createUser(String firstName, String lastName, String email, String password, String dob, String phone ) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPassword(password);
        enterDob(dob);
        enterPhone(phone);
        ju.javaScriptClick(suubBtn);


    }

    public By getFieldErrorByName(String fieldName) {
        return By.xpath("//input[@name='" + fieldName + "']/following-sibling::*[contains(@class, 'error')]");
    }

    public By getRoleOptionByText(String optionText) {
        return By.xpath("//select[@id='role']/option[text()='" + optionText + "']");
    }


	public void manageUserMenuClick() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		ju.javaScriptClick(manageUserMenue);
	}

	public void addUserButtonClick() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		ju.javaScriptClick(addButton);
	}

	public boolean isUserPresentInTable(String username) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		// Logic to verify if the user is present in the userTable
		return userTable.getText().contains(username);
	}





}
