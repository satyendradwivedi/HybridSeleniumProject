package com.iamguru.AI.pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iamguru.AI.utilities.AudioCaptureUtil;
import com.iamguru.AI.utilities.ClickActions;
import com.iamguru.AI.utilities.JavaScriptUtils;

public class DigitalTwin extends BasePage {

	ClickActions ca;
	JavaScriptUtils ju;

	public DigitalTwin(WebDriver driver) {
		super(driver);
		ca = new ClickActions(driver);
		ju = new JavaScriptUtils(driver);
	}


	// Elements
		@FindBy(xpath = "//span[normalize-space()='Digital Twins']")
		private WebElement DigitalTwin;

		@FindBy(xpath = "//button[@id='button-animated']")
		private WebElement selectAIModel;



		@FindBy(xpath = "//a[normalize-space()='ModernIP']")
		private WebElement modernModelOption;




	public void createDigitalTwin() {

			 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
			    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loader-overlay")));
			ju.javaScriptClick(wait.until(ExpectedConditions.elementToBeClickable(selectAIModel)));
		    ju.javaScriptClick(wait.until(ExpectedConditions.elementToBeClickable(modernModelOption)));

	}

		@FindBy(xpath = "//button[normalize-space()='Create Digital Twin']")
		private WebElement createDigitalTwinButton;


		@FindBy(xpath = "//body//app-root//div[5]")
		private WebElement twinIndustrytype;

		@FindBy(xpath = "//span[normalize-space()='Save & Continue']")
		private WebElement saveAndContinueButton;

		@FindBy(xpath = "//body//app-root//div[9]")
		private WebElement twinUsecasetype;

		@FindBy(xpath = "//input[@id='twinName']")
		private WebElement twinName;


		@FindBy(xpath = "//span[normalize-space()='Create Twin']")
		private WebElement createTwinButton;

		@FindBy(xpath = "//button[@class='success-btn']")
		private WebElement createTwinSuccessButton;

		@FindBy(xpath = "//textarea[@placeholder='Write welcome message...']")
		private WebElement welcomeMessage;


		@FindBy(xpath = "//textarea[@placeholder='Write system prompt & dynamic variable...']")
		private WebElement systemPrompt;

		@FindBy(xpath = "//button[@class='btn btn-primary']")
		private WebElement saveAndProceedButton;

		//voice tab
	@FindBy(xpath = "//ul[@aria-label='Tabs']//li[2]//a[1]")
	private WebElement voiceTab;

	@FindBy(xpath = "//select[@formcontrolname='voiceProfile']")
	private WebElement selectVoiceProfile;

	@FindBy(xpath = "(//input[contains(@class,'form-range')])[1]")
	private WebElement voiceToneSlider;

	@FindBy(xpath = "(//input[contains(@class,'form-range')])[2]")
	private WebElement speedSlider;

	//knowledge base tab elements can be added here
	@FindBy(xpath = "//span[normalize-space()='Knowledge Base']")
	private WebElement knowledgeBaseTab;

	@FindBy(xpath = "//button[normalize-space()='Add URL']")
	private WebElement addURLButton;


	@FindBy(xpath = "//input[@id='urlInput']")
	private WebElement urlInputField;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement submitURLButton;

	@FindBy(xpath = "//img[@alt='AI Logo']")
	private WebElement DigitalTwinAgent;

	@FindBy(xpath = "//img[@alt='Start call']")
	private WebElement startCallButton;



 // Methods to interact with elements
		public void clickDigitalTwin() {

			driver.navigate().refresh();
			 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loader-overlay")));
			ju.javaScriptClick(DigitalTwin);
		}



		public void selectAIModel() {
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loader-overlay")));
		    ju.javaScriptClick(wait.until(ExpectedConditions.elementToBeClickable(selectAIModel)));
		    ju.javaScriptClick(wait.until(ExpectedConditions.elementToBeClickable(modernModelOption)));
		    ju.javaScriptClick(selectAIModel);
		    ju.javaScriptClick(modernModelOption);

		}

		public void clickCreateDigitalTwinButton() {
			ju.javaScriptClick(createDigitalTwinButton);
		}

		public void selectTwinIndustryType() {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loader-overlay")));
			ju.javaScriptClick(twinIndustrytype);
		}

			public void clickSaveAndContinueButton() {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loader-overlay")));
				ju.javaScriptClick(wait.until(ExpectedConditions.elementToBeClickable(saveAndContinueButton)));
			}

		public void selectTwinUsecaseType() {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loader-overlay")));
			ju.javaScriptClick(twinUsecasetype);
		}

		public void enterTwinName(String name) {
			twinName.sendKeys(name);
		}

		public void clickCreateTwinButton() {
			ju.javaScriptClick(createTwinButton);
		}
		public void clickCreateTwinSuccessButton() {
			ju.javaScriptClick(createTwinSuccessButton);
		}


		public void editAgentTab(String welcomeMsg, String systemPrmpt) throws InterruptedException {
			welcomeMessage.clear();
			welcomeMessage.sendKeys(welcomeMsg);
			systemPrompt.clear();
			systemPrompt.sendKeys(systemPrmpt);
			ju.javaScriptClick(saveAndProceedButton);
		}
		private void setSliderValue(WebElement slider, String value) {
			((JavascriptExecutor) driver).executeScript(
				"arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input')); arguments[0].dispatchEvent(new Event('change'));",
				slider, value);
		}

		public void selectVoice()
		{
			ju.javaScriptClick(voiceTab);
			Select s2 = new Select(selectVoiceProfile);
			s2.selectByVisibleText(" Jenny | en-US | female ");
			setSliderValue(voiceToneSlider, "50");
			setSliderValue(speedSlider, "0.9");
			ju.javaScriptClick(saveAndProceedButton);
		}
		public void addKnowledgeBaseURL(String url) {
			ju.javaScriptClick(knowledgeBaseTab);
			ju.javaScriptClick(addURLButton);
			urlInputField.sendKeys(url);
			ju.javaScriptClick(submitURLButton);
		}

		public void clickDigitalTwinAgent(String audioOutputPath)  {
			ju.javaScriptClick(DigitalTwinAgent);
			ju.javaScriptClick(startCallButton);
			 AudioCaptureUtil.startRecording(audioOutputPath);
			    System.out.println("[Twin] Audio capture started.");
		}
}
