package com.iamguru.AI.pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iamguru.AI.utilities.ClickActions;
import com.iamguru.AI.utilities.JavaScriptUtils;

public class Email extends BasePage {
    ClickActions ca;
    JavaScriptUtils ju;
    JavascriptExecutor je;

    public Email(WebDriver driver) {
        super(driver);
        ca = new ClickActions(driver);
    }

    @FindBy(xpath = "//input[@id='login']")
    private WebElement emailInput;

    @FindBy(xpath = "//i[@class='material-icons-outlined f36']")
    private WebElement goToInboxButton;

    @FindBy(xpath = "//span[contains(text(),'IamGuru')]")
    private WebElement gooruAPIMail;


    @FindBy(xpath = "//a[normalize-space()='Verify Your Email']")
    private WebElement verifyEmailButton;


    @FindBy(xpath = "//a[normalize-space()='Join Now']")
    private WebElement joinNowButton;

    @FindBy(xpath="//*[@id=\"mail\"]/div/div/div[2]/a")
    private WebElement ResetPassword;

    @FindBy(xpath = "//*[text()='Back to Login']")
    private WebElement BacktoLogin;

    @FindBy(xpath = "//a[normalize-space()='Log In to Your Account']")
    WebElement LoginToYourAccount;


    @Override
	public void switchToNewWindow() {
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    @Override
	public void switchToLastTab() {
        java.util.Set<String> allWindows = driver.getWindowHandles();
        String lastWindow = null;
        for (String window : allWindows) {
            lastWindow = window;
        }
        if (lastWindow != null) {
            driver.switchTo().window(lastWindow);
        }
    }
    /**
     * Enters the temporary email into Yopmail input field.
     */
    public void enterEmail(String yopmailAddress) {
    	emailInput.clear();
        emailInput.sendKeys(yopmailAddress);
    }

    public void clickLoginToYourAccount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Switch to the email content iframe
        driver.switchTo().frame("ifmail");

        try {
            WebElement loginBtn = null;

            try {
                // Try exact text match
                loginBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[normalize-space()='Log In to Your Account']")));
            } catch (Exception e1) {
                try {
                    // Try contains text match
                    loginBtn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(text(),'Log In')]")));
                } catch (Exception e2) {
                    // Try any login-related link
                    loginBtn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'login')]")));
                }
            }

            loginBtn.click();

        } finally {
            // Switch back to main page
            driver.switchTo().defaultContent();
        }
    }



    /**
     * Clicks the button to navigate to inbox.
     */
    public void clickGoToInbox() {
        ca.clickElement(goToInboxButton);
    }

    /**
     * Tries to open the verification email, handling potential delays.
     */
    public void openVerificationEmail() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 🚀 Switch to the inbox iframe before locating emails
        driver.switchTo().frame("ifinbox");

        for (int i = 0; i < 5; i++) { // Retry 5 times
            try {
                // Try multiple email locators
                WebElement emailElement = null;

                try {
                    // Try original locator
                    emailElement = wait.until(ExpectedConditions.elementToBeClickable(gooruAPIMail));
                } catch (Exception e1) {
                    try {
                        // Try any email from the sender
                        emailElement = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//span[contains(text(),'IamGuru') or contains(text(),'Gooru') or contains(text(),'noreply')]")));
                    } catch (Exception e2) {
                        // Try first email in the list
                        emailElement = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//div[@class='m']//span[1]")));
                    }
                }

                emailElement.click();
                driver.switchTo().defaultContent();  // Reset back to main page
                return;
            } catch (Exception e) {
                System.out.println("Retry fetching email attempt: " + (i + 1) + ". Error: " + e.getMessage());
                driver.switchTo().defaultContent();
                driver.navigate().refresh(); // Refresh inbox
                driver.switchTo().frame("ifinbox"); // Switch back to iframe
                try {
                    Thread.sleep(8000); // Wait 8 seconds before retrying
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        driver.switchTo().defaultContent();
        throw new RuntimeException("Email not received after 5 retries.");
    }

    /**
     * Clicks the "Verify Your Email" button inside the email.
     */
    public void clickVerifyEmailButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 🚀 Switch to the email content iframe
        driver.switchTo().frame("ifmail");

        try {
            WebElement verifyButton = null;

            try {
                // Try exact text match
                verifyButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[normalize-space()='Verify Your Email']")));
            } catch (Exception e1) {
                try {
                    // Try contains text match
                    verifyButton = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(text(),'Verify')]")));
                } catch (Exception e2) {
                    // Try any verification-related link
                    verifyButton = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'verify')]")));
                }
            }

            verifyButton.click();

        } finally {
            // 🚀 Switch back to main page
            driver.switchTo().defaultContent();
        }
    }

    public void clickjoinNButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Open the invitation email first
        openVerificationEmail();

        // Switch to the email content iframe
        driver.switchTo().frame("ifmail");

        try {
            WebElement joinBtn = null;
            try {
                joinBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[normalize-space()='Join Now']")));
            } catch (Exception e1) {
                try {
                    joinBtn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(text(),'Join')]")));
                } catch (Exception e2) {
                    joinBtn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'join')]")));
                }
            }

            String target = joinBtn.getAttribute("target");
            System.out.println("Join button target attribute: " + target);
            joinBtn.click();

        } finally {
            driver.switchTo().defaultContent();
        }
    }
    public void clickResetPasswordButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 🚀 Switch to the email content iframe
        driver.switchTo().frame("ifmail");

        wait.until(ExpectedConditions.elementToBeClickable(ResetPassword)).click();

        // 🚀 Switch back to main page
        driver.switchTo().defaultContent();
    }

    public void verificationsuccess(String msg)

    {
    	System.out.println(msg);

    }

    public void backToLoginPage() {
        try {
            // Check if browser is still responsive
            driver.getTitle();

            // Ensure we're in the main window context
            driver.switchTo().defaultContent();

            // Add a small wait before clicking
            Thread.sleep(1000);

            ca.clickElement(BacktoLogin);
        } catch (Exception e) {
            System.out.println("Browser may have crashed. Error: " + e.getMessage());
            throw new RuntimeException("Browser became unresponsive during backToLoginPage operation", e);
        }
    }
}
