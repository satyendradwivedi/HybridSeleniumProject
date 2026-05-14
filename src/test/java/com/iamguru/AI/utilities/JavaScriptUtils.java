package com.iamguru.AI.utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtils {

    private WebDriver driver;

    // Constructor to initialize the WebDriver
    public JavaScriptUtils(WebDriver driver) {
        this.driver = driver;
    }

    // Method to send keys to an element using JavaScript
    public void javaScriptSendKeys(WebElement element, String keysToSend) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].value = arguments[1];", element, keysToSend);
    }

    // Method to click an element using JavaScript with retry mechanism
    public void javaScriptClick(WebElement element) {
        int maxRetries = 3;
        for (int i = 0; i < maxRetries; i++) {
            try {
                JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
                javascriptExecutor.executeScript("arguments[0].click();", element);
                return;
            } catch (Exception e) {
                System.out.println("JavaScript click attempt " + (i + 1) + " failed: " + e.getMessage());
                if (i == maxRetries - 1) {
                    throw new RuntimeException("Failed to click element after " + maxRetries + " attempts", e);
                }
                try { Thread.sleep(1000); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
            }
        }
    }

    // Method to scroll to an element using JavaScript with error handling
    public void scrollToElement(WebElement element) {
        try {
            // Check if element is valid before scrolling
            if (element != null && element.isDisplayed()) {
                JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
                javascriptExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
                Thread.sleep(500); // Brief pause after scroll
            }
        } catch (Exception e) {
            System.out.println("Error scrolling to element: " + e.getMessage());
            // Fallback: try simple scroll without smooth behavior
            try {
                JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
                javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
            } catch (Exception ex) {
                System.out.println("Fallback scroll also failed: " + ex.getMessage());
            }
        }
    }

	public void scrollIntoView(WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
			Thread.sleep(500);
		} catch (Exception e) {
			System.out.println("scrollIntoView failed: " + e.getMessage());
		}
	}






}
