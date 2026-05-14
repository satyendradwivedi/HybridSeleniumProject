package com.iamguru.AI.utilities;



import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ClickActions {
    WebDriver driver;

    public ClickActions(WebDriver driver) {
        this.driver = driver;
    }

    // Method to click an element using WebDriverWait
    public void clickElement(WebElement element) {
        // Ensure we're in the main window context
        try {
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            // Ignore if already in default content
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));

        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.getMessage().contains("WaitForPendingNavigations")) {
                // Handle iframe context issue by using JavaScript click
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            } else {
                throw e;
            }
        }
    }

    // Optional: Method for clicking using Actions class
    public void clickUsingActions(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
    }

    // Optional: Method for handling JavaScript click if necessary
    public void clickUsingJavaScript(WebElement element, int i) {
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void waitForElementToBeClickable(WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }


}
