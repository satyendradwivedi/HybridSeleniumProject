package com.iamguru.AI.pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iamguru.AI.testdata.User;
import com.iamguru.AI.utilities.ClickActions;
import com.iamguru.AI.utilities.JavaScriptUtils;

public class Website extends BasePage {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);

    private final ClickActions ca;
    private final JavaScriptUtils ju;
    private final WebDriverWait wait;

    public Website(WebDriver driver) {
        super(driver);
        this.ca = new ClickActions(driver);
        this.ju = new JavaScriptUtils(driver);
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        PageFactory.initElements(driver, this);
    }

    // ================= COMMON METHODS =================

    private void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    private void waitForClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    private void click(WebElement element) {
        try {
            waitForClickable(element);
            ca.clickElement(element);
        } catch (Exception e) {
            ju.scrollIntoView(element);
            waitForClickable(element);
            ca.clickElement(element);
        }
    }

    private void type(WebElement element, String value) {
        waitForVisibility(element);
        element.clear();
        element.sendKeys(value);
    }

    private boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ================= LOCATORS =================

    @FindBy(xpath = "//input[@placeholder='Enter your first name' or @placeholder='First Name']")
    private WebElement firstName;

    @FindBy(xpath = "//input[@placeholder='Enter your last name' or @placeholder='Last Name']")
    private WebElement lastName;

    @FindBy(xpath = "//input[@placeholder='Enter your email' or @type='email']")
    private WebElement email;

    @FindBy(xpath = "//input[@type='tel' or @placeholder='Phone number' or @placeholder='Enter phone number' or @name='phone']")
    private WebElement phoneNumber;

    @FindBy(xpath = "//button[contains(@class,'button-gradient')]")
    private WebElement continueBtn;

    @FindBy(xpath = "//input[@placeholder='Create a password']")
    private WebElement password;

    @FindBy(xpath = "//input[@placeholder='Confirm your password']")
    private WebElement confirmPassword;

    @FindBy(xpath = "//input[@placeholder='Enter your street address']")
    private WebElement address;

    @FindBy(xpath = "(//button[@role='combobox'])[1]")
    private WebElement countryDropdown;

    @FindBy(xpath = "//div[@role='option'][contains(normalize-space(),'United States')]")
    private WebElement unitedStatesOption;

    @FindBy(xpath = "(//button[@role='combobox'])[2]")
    private WebElement stateDropdown;

    @FindBy(xpath = "//div[@role='option'][contains(normalize-space(),'New York')]")
    private WebElement newYorkOption;

    @FindBy(xpath = "(//button[@role='combobox'])[3]")
    private WebElement cityDropdown;

    @FindBy(xpath = "//div[@role='option'][contains(normalize-space(),'Alabama')]")
    private WebElement albamaCityOption;

    @FindBy(xpath = "//input[@placeholder='Enter your postal code']")
    private WebElement zipCode;

    @FindBy(xpath = "//button[normalize-space()='Get Solo G' or contains(normalize-space(),'Solo G')]")
    private WebElement getSoloGHeader;

    @FindBy(xpath = "//button[@aria-haspopup='dialog' and .//*[contains(@class,'lucide-calendar')]]")
    private WebElement Pickdate;

    @FindBy(xpath = "(//button[@name='day' and @role='gridcell' and not(@disabled)])[1]")
    private WebElement defaultDate;

    @FindBy(xpath = "//input[@placeholder='John Doe']")
    private WebElement paymentpage;

    @FindBy(xpath = "//input[@placeholder='John Doe']")
    private WebElement nameoncard;

    @FindBy(xpath = "//button[contains(normalize-space(),'Complete') or contains(normalize-space(),'Pay') or contains(normalize-space(),'Subscribe')]")
    private WebElement completesetup;
    
    @FindBy(xpath = "//span[normalize-space()='New Gooru']")
    private WebElement newGoorubtn;

    @FindBy(xpath = "//h3[normalize-space()='Education & Training']")
    private WebElement educationAndTraining;
    
    @FindBy(xpath = "//h3[contains(normalize-space(),'Learning') and contains(normalize-space(),'Development')]")
    private WebElement LearningAndDevelopment;
    
    

    @FindBy(xpath = "(//button[contains(@class,'button-gradient')])[last()]")
    private WebElement nextBtn;

    @FindBy(xpath = "//textarea[@id='urls']")
    private WebElement urlInput;

    @FindBy(xpath = "(//span[normalize-space()='Sophia'])[1]")
    private WebElement femaleVoice;

    @FindBy(xpath = "//input[@id='agentName']")
    private WebElement agentName;

    @FindBy(xpath = "//input[@placeholder='Enter domain (e.g., example.com)']")
    private WebElement domainInput;

    @FindBy(xpath = "(//button[contains(text(),'Create')])[last()]")
    private WebElement createAgentBtn;
    
    @FindBy(xpath = "//img[@alt='Gooru']")
    private WebElement gooruLogo;
    
    

    // ================= FLOW METHODS =================

    public void completeRegistrationFlow(User user) {
        type(firstName, user.getFirstName());
        type(lastName, user.getLastName());
        type(email, user.getEmail());
        type(phoneNumber, user.getPhone());
        click(continueBtn);

        type(password, user.getPassword());
        type(confirmPassword, user.getPassword());
        click(continueBtn);

        type(address, user.getAddress());
        click(countryDropdown);
        click(unitedStatesOption);
        click(stateDropdown);
        click(newYorkOption);
        click(cityDropdown);
        click(albamaCityOption);
        type(zipCode, user.getZipCode());
        click(continueBtn);
    }

    public void iamguruInegration() {
        click(getSoloGHeader);
        click(Pickdate);
        click(defaultDate);
        click(continueBtn);
    }

    public void completePaymentFlow() {
        try {
            // Name on card is in the main DOM
            type(nameoncard, "John Doe");

            // Wait for Stripe iframes to load
            List<WebElement> iframes = new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("iframe")));

            // Card number - first iframe
            driver.switchTo().frame(iframes.get(0));
            driver.findElement(By.xpath("//input[@name='cardnumber' or @placeholder='Card number']"))
                .sendKeys("4242424242424242");
            driver.switchTo().defaultContent();

            // Expiry - second iframe
            driver.switchTo().frame(iframes.get(1));
            driver.findElement(By.xpath("//input[@name='exp-date' or @placeholder='MM / YY' or @placeholder='MM/YY']"))
                .sendKeys("1229");
            driver.switchTo().defaultContent();

            // CVC - third iframe
            driver.switchTo().frame(iframes.get(2));
            driver.findElement(By.xpath("//input[@name='cvc' or @placeholder='CVC' or @placeholder='CVV']"))
                .sendKeys("123");
            driver.switchTo().defaultContent();

        } catch (Exception e) {
            driver.switchTo().defaultContent();
            throw new RuntimeException("Payment flow failed: " + e.getMessage(), e);
        }
        click(completesetup);
    }

    public void createDigitalTwinFlow(User user) {
        click(newGoorubtn);
		click(educationAndTraining);
		click(nextBtn);
		click(LearningAndDevelopment);
		click(nextBtn);
		type(urlInput, user.getknowledgebaseurl());
		click(nextBtn);
		click(femaleVoice);
		click(nextBtn);
		type(agentName, user.getAgentName());
		type(domainInput, user.getDomain());
		click(createAgentBtn);
	}

	
    
    public void logoclick() {
		click(gooruLogo);
	}

    public boolean isLogoDisplayed() {
		return isDisplayed(gooruLogo);
	}

    public boolean isCreateAgentVisible() {
        return isDisplayed(createAgentBtn);
    }

    public boolean isIntegrationSuccessful() {
        try {
            wait.until(ExpectedConditions.invisibilityOf(getSoloGHeader));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPaymentSuccessful() {
        try {
            wait.until(ExpectedConditions.urlContains("/digital-goorus"));
            return driver.getCurrentUrl().contains("/digital-goorus");
        } catch (Exception e) {
            return false;
        }
    }
}
