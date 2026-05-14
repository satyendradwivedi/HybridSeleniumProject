package com.iamguru.AI.pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iamguru.AI.utilities.ClickActions;
import com.iamguru.AI.utilities.JavaScriptUtils;

public class Messaging extends BasePage {

    // Constants
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    // Instance variables
    private final ClickActions ca;
    private final JavaScriptUtils ju;
    private final WebDriverWait wait;

    // Web Elements
    @FindBy(xpath = "//button[@class='btn btn-md d-flex gap-1 btn-primary']")
    public WebElement InnerCircleButton;

    @FindBy(xpath = "//i[@class='bx bx-bell bx-tada']")
    public WebElement BellIcon;

    @FindBy(xpath = "//div[@class=\"user-chat\"]")
    public WebElement chatmatpopup;

    @FindBy(xpath = "//input[@placeholder=\"Type here...\"]")
    public List<WebElement> chattextbox;

    @FindBy(xpath = "//span[normalize-space()='Messaging']")
    public WebElement Messaging;

    @FindBy(xpath = "//button[normalize-space(.)='Message']")
    public WebElement message;

    @FindBy(xpath = "//button[normalize-space()='Send']")
    public WebElement sendButton;

    @FindBy(xpath = "//div[@aria-label='Connection request accepted successfully.']")
    public WebElement SuccessMessageAcceptance;

    @FindBy(xpath = "//button[contains(normalize-space(),'View Profile')]")
    public WebElement ViewProfileLink;

    @FindBy(xpath = "//a[@class='small']")
    public WebElement ViewallLink;

    @FindBy(xpath = "//button[normalize-space()='View all']")
    private WebElement VewAllGuru;

    @FindBy(xpath = "//div[@class='col-md-6']")
    private List<WebElement> AllGurulist;

    @FindBy(xpath = "//button[contains(normalize-space(),'Accept')]")
    public WebElement AcceptButton;

    @FindBy(xpath = "//button[@class='btn btn-sm p-1 show']")
    public WebElement threedoticon;

    @FindBy(xpath = "//span[normalize-space()='Edit']")
    public WebElement editIcon;

    @FindBy(xpath = "//span[normalize-space()='Delete']")
    public WebElement deleteIcon;

    @FindBy(xpath = "//i[@class='bx bx-check edit-check']")
    public WebElement editIconCheck;

    @FindBy(xpath = "//button[normalize-space()='Delete']")
    public WebElement deleteIconTrash;

    @FindBy(xpath = "//input[@id='searchContact']")
    public WebElement searchContact;

    // Constructor
    public Messaging(WebDriver driver) {
        super(driver);
        this.ca = new ClickActions(driver);
        this.ju = new JavaScriptUtils(driver);
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    // Public Methods
    public void acceptRequest() {
        ju.javaScriptClick(BellIcon);
        ju.javaScriptClick(ViewallLink);
        ju.javaScriptClick(AcceptButton);
    }

    public By clickViewAllGuru() {
        try {
            ju.javaScriptClick(VewAllGuru);
        } catch (Exception e) {
            System.out.println("Error clicking View All Guru button: " + e.getMessage());
            throw e;
        }
        return null;
    }

    public void clickMessage() {
        ju.javaScriptClick(message);
    }

    public void clickMessaging() {
        ju.javaScriptClick(Messaging);
    }

    public void clickSendButton() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(sendButton));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sendButton);

            try {
                sendButton.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendButton);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to click 'Send' button", e);
        }
    }

    public void clickViewProfileLink() {
        wait.until(ExpectedConditions.elementToBeClickable(ViewProfileLink));
        ju.javaScriptClick(ViewProfileLink);
    }

    public List<String> getAllGuruNames() {
        List<String> guruNames = AllGurulist.stream()
            .map(guru -> guru.getText().trim())
            .toList();
        System.out.println("Guru Names: " + guruNames);
        return guruNames;
    }

    public void setChatTextBoxText(String text) {
        ju.javaScriptClick(chattextbox.get(0));
        chattextbox.get(0).sendKeys(text);
    }

    public void clickThreeDotIcon() {
        // Try multiple click methods since element is hard to click
        try {
            // First try regular click
            threedoticon.click();
        } catch (Exception e) {
            try {
                // Try JavaScript click
                ju.javaScriptClick(threedoticon);
            } catch (Exception e2) {
                try {
                    // Try with explicit wait and JavaScript click
                    wait.until(ExpectedConditions.elementToBeClickable(threedoticon));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", threedoticon);
                } catch (Exception e3) {
                    throw new RuntimeException("Failed to click three dot icon after multiple attempts", e3);
                }
            }
        }
    }

    public void clickEditIcon() {
        ju.javaScriptClick(editIcon);
    }
    public void clickDeleteIcon() {
        ju.javaScriptClick(deleteIcon);
    }

    public void clickEditIconCheck() {
        ju.javaScriptClick(editIconCheck);
    }

    public void clickDeleteIconTrash() {
        ju.javaScriptClick(deleteIconTrash);
    }

    public void clickSearchContact(String user) {
        ju.javaScriptClick(searchContact);
    }
}
