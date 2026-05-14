package com.iamguru.AI.pageObjects;


import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iamguru.AI.utilities.ClickActions;
import com.iamguru.AI.utilities.JavaScriptUtils;

public class Repository extends BasePage {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(20);

    private final ClickActions ca;
    private final JavaScriptUtils ju;
    private final WebDriverWait wait;

    public Repository(WebDriver driver) {
        super(driver);
        this.ca = new ClickActions(driver);
        this.ju = new JavaScriptUtils(driver);
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    // ==== Elements ====

    @FindBy(xpath = "//button[normalize-space()='Add New Content']")
    private WebElement addNewContentButton;

    @FindBy(xpath = "//a[normalize-space()='Browse']")
    private WebElement uploadButton;

    @FindBy(xpath = "//button[normalize-space()='Next']")
    private WebElement nextButton;

    @FindBy(xpath = "//select")
    private List<WebElement> ageDropdowns;

    @FindBy(xpath = "//button[normalize-space()='Save']")
    private WebElement saveButton;

    @FindBy(xpath = "//button[contains(translate(normalize-space(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'VISIT REPOSITORY')]")
    private WebElement visitRepositoryButton;

    @FindBy(xpath = "//input[@id='searchInput']")
    private WebElement searchInput;

    @FindBy(xpath = "//span[@class='ms-3']")
    private List<WebElement> searchedItems;

    @FindBy(xpath = "//tbody/tr[1]/td[8]/ul[1]/li[1]/a[1]/i[1]")
    private WebElement viewIcon;

    @FindBy(xpath = "//h5[@id='contentViewLabel']")
    private WebElement repositoryLabel;

    @FindBy(xpath="//button[@aria-label='Close']")
    private WebElement closePopup;

    @FindBy(xpath = "//tbody/tr[1]/td[8]/ul[1]/li[2]/a[1]/i[1]")
    private WebElement editIcon;

    @FindBy(xpath = "//p[@class='NgxEditor__Placeholder']")
    private WebElement descText;

    @FindBy(xpath = "//tbody/tr[1]/td[8]/ul[1]/li[3]/a[1]/i[1]")
    private WebElement deleteButton;

    @FindBy(xpath = "//button[normalize-space()='Yes']")
    private WebElement confirmDeleteButton;
    // ==== Actions ====

    public void clickAddNewContentButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addNewContentButton));
        ju.javaScriptClick(addNewContentButton);
    }

    public void clickUpload() {
       Actions actions = new Actions(driver);
       ju.javaScriptClick(uploadButton);
      actions.moveToElement(uploadButton).click().perform();
    }

    public void clickNextButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(nextButton));
            ju.javaScriptClick(nextButton);
        } catch (Exception e) {
            System.out.println("Next button not found or not clickable");
        }
    }

    public void selectAgeByIndex(int index, String ageValue) {
        try {
            wait.until(ExpectedConditions.visibilityOf(ageDropdowns.get(index)));
            ju.scrollToElement(ageDropdowns.get(index));
            new Select(ageDropdowns.get(index)).selectByVisibleText(ageValue);
            System.out.println("✅ Selected age " + ageValue + " at dropdown index " + index);
        } catch (Exception e) {
            System.out.println("❌ Error while selecting age: " + e.getMessage());
        }
    }



    public void clickSaveButton() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        ju.javaScriptClick(saveButton);
    }

    public void clickVisitRepository() {
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement btn = longWait.until(ExpectedConditions.elementToBeClickable(
            org.openqa.selenium.By.xpath("//button[contains(., 'Visit Repository')]"
        )));
        ju.javaScriptClick(btn);
    }

    public String getRepositoryLabelText() {
        try {
            wait.until(ExpectedConditions.visibilityOf(repositoryLabel));
            return repositoryLabel.getText().trim();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isRepositoryLabelDisplayed() {
        try {
            return repositoryLabel.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void search(String searchText) throws InterruptedException {
    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.clear();
        searchInput.sendKeys(searchText);



    }

    public List<WebElement> getSearchResults() {
        wait.until(ExpectedConditions.visibilityOfAllElements(searchedItems));
        return searchedItems;
    }

    public void clickViewIcon() {
        wait.until(ExpectedConditions.elementToBeClickable(viewIcon));
        ju.javaScriptClick(viewIcon);
    }

    // ==== File Upload (Robot Automation) ====

    public void uploadSingleFile(String filePath) {
        System.out.println("File upload skipped for: " + filePath);
        System.out.println("Manual upload required due to browser security restrictions");
    }

    public void bulkUploadMultipleFiles() throws Exception {
        // Folder containing files to upload
        String folderPath = "C:\\Users\\Gursewak\\Desktop\\Upload Data\\Bulk upload";
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null && files.length > 0) {
            // Build string with quoted file paths
            StringBuilder fileList = new StringBuilder();
            for (File file : files) {
                if (file.isFile()) {
                    if (fileList.length() > 0) {
                        fileList.append(" ");
                    }
                    fileList.append("\"").append(file.getAbsolutePath()).append("\"");
                }
            }

            // Copy paths to clipboard
            StringSelection selection = new StringSelection(fileList.toString());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

            Robot robot = new Robot();
            robot.setAutoDelay(500);

            // Paste into "File name:" box
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            Thread.sleep(1000);

            // Press Enter to upload
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }


    }
    public void closePopup()
    {
    	ju.javaScriptClick(closePopup);
    }

    public void clickEditIcon() {
        wait.until(ExpectedConditions.elementToBeClickable(editIcon));
        ju.javaScriptClick(editIcon);
    }

    public void updateDescription(String newDescription) {
        wait.until(ExpectedConditions.visibilityOf(descText));
        descText.clear();
        descText.sendKeys(newDescription);
    }

    public void clickDeleteButtonIcon() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
        ju.javaScriptClick(deleteButton);
        ju.javaScriptClick(confirmDeleteButton);
    }
}

