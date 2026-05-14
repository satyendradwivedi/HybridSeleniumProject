package com.iamguru.AI.pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iamguru.AI.utilities.ClickActions;
import com.iamguru.AI.utilities.JavaScriptUtils;

public class CourseManagement extends BasePage {

	private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(20);

	private final ClickActions ca;
	private final JavaScriptUtils ju;
	private final WebDriverWait wait;

	// Define locators and methods for interacting with the course management interface

	public CourseManagement(WebDriver driver) {
		super(driver);
		// Initialize any additional csuper(driver);
		this.ca = new ClickActions(driver);
		this.ju = new JavaScriptUtils(driver);
		this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
	}
	@FindBy(xpath = "//span[normalize-space()='Course Management']")
	private WebElement courseManagementMenu;

	@FindBy(xpath = "//div[@class='loader-overlay']")
	private WebElement loaderOverlay;

	public void clickCourseManagementMenu() {
		try {
			waitForLoaderToDisappear();
			wait.until(ExpectedConditions.elementToBeClickable(courseManagementMenu));
			ca.clickElement(courseManagementMenu);
		} catch (Exception e) {
			ju.javaScriptClick(courseManagementMenu);
		}
	}

	private void waitForLoaderToDisappear() {
		try {
			// Wait for loader to be invisible (max 10 seconds)
			WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));
			shortWait.until(ExpectedConditions.invisibilityOf(loaderOverlay));
		} catch (Exception e) {
			// Loader might not be present, continue
			System.out.println("Loader not found or already disappeared");
		}
	}

	@FindBy(xpath = "//button[normalize-space()='Add New Course']")
	private WebElement createCourseButton;

	public void clickCreateCourseButton() throws Exception {
		try {
			waitForLoaderToDisappear();


			// Scroll to element first
			ju.scrollToElement(createCourseButton);


			wait.until(ExpectedConditions.elementToBeClickable(createCourseButton));

			// Try JavaScript click first to avoid interception
			ju.javaScriptClick(createCourseButton);
		} catch (Exception e) {
			System.out.println("Error clicking Create Course button: " + e.getMessage());
			throw e;
		}
	}


	@FindBy(xpath = "//input[@placeholder='Enter Title']")
	private WebElement courseTitleInput;

	public void enterCourseTitle(String title) {
		try {
			wait.until(ExpectedConditions.visibilityOf(courseTitleInput));
			courseTitleInput.clear();
			courseTitleInput.sendKeys(title);
		} catch (Exception e) {
			System.out.println("Error entering course title: " + e.getMessage());
			throw e;
		}
	}
	@FindBy(xpath = "//*[@id=\"layout-wrapper\"]/div/div[1]/app-create-new-course/div[2]/form/app-speech-to-text/div/div/ngx-editor/div/div/p")
	private WebElement courseDescriptionInput;

	public void enterCourseDescription(String description) {
		try {
			wait.until(ExpectedConditions.visibilityOf(courseDescriptionInput));
			ju.scrollToElement(courseDescriptionInput);
			courseDescriptionInput.clear();
			courseDescriptionInput.sendKeys(description);
		} catch (Exception e) {
			System.out.println("Error entering course description: " + e.getMessage());
			throw e;
		}
	}

	@FindBy(xpath = "//input[@placeholder='Search and select']")
	private WebElement courseCategoryInput;

	public void selectCourseCategory() throws Exception {
		try {
			waitForLoaderToDisappear();
			wait.until(ExpectedConditions.visibilityOf(courseCategoryInput));
			ju.scrollToElement(courseCategoryInput);
			courseCategoryInput.click();
			Thread.sleep(100);

			// Select Technology option
			WebElement technologyOption = driver.findElement(org.openqa.selenium.By.xpath("//div[normalize-space()='Technology']"));
			wait.until(ExpectedConditions.elementToBeClickable(technologyOption));
			technologyOption.click();
			System.out.println("Technology category selected");
		} catch (Exception e) {
			System.out.println("Error selecting course category: " + e.getMessage());
			throw e;
		}
	}
	@FindBy(xpath = "//div[@class='row g-3 mb-3']//div[2]//app-chip-select[1]//form[1]//div[1]//input[1]")
	private WebElement courseCategorysubInput;

	public void selectCourseSubCategory() throws Exception {
		try {
			waitForLoaderToDisappear();
			wait.until(ExpectedConditions.visibilityOf(courseCategorysubInput));
			ju.scrollToElement(courseCategorysubInput);
			courseCategorysubInput.click();
			Thread.sleep(100);
			WebElement subtechnologyOption = driver.findElement(org.openqa.selenium.By.xpath("//div[normalize-space()='AI & Machine Learning']"));
			wait.until(ExpectedConditions.elementToBeClickable(subtechnologyOption));
			subtechnologyOption.click();
			System.out.println("SubTechnology category selected");
		} catch (Exception e) {
			System.out.println("Error selecting course Sub category: " + e.getMessage());
			throw e;
		}
	}
	@FindBy(xpath = "//button[normalize-space()='Save & Continue']")
	private WebElement saveAndContinueButton;

	public void clickSaveAndContinueButton() throws Exception {
		try {
			// Add browser health check
			if (!isBrowserHealthy()) {
				throw new RuntimeException("Browser session is not healthy");
			}

			waitForLoaderToDisappear();
		 // Additional wait for stability

			// Re-find element to avoid stale reference
			WebElement freshElement = driver.findElement(org.openqa.selenium.By.xpath("//button[normalize-space()='Save & Continue']"));
			wait.until(ExpectedConditions.elementToBeClickable(freshElement));

			ju.scrollToElement(freshElement);
		// Wait after scroll

			// Try regular click first, then JavaScript click
			try {
				freshElement.click();
			} catch (Exception clickEx) {
				System.out.println("Regular click failed, trying JavaScript click");
				ju.javaScriptClick(freshElement);
			}

		} catch (Exception e) {
			System.out.println("Error clicking Save & Continue button: " + e.getMessage());
			throw e;
		}
	}

	// Helper method to check browser health
	private boolean isBrowserHealthy() {
		try {
			// Simple check to see if browser is responsive
			driver.getTitle();
			return true;
		} catch (Exception e) {
			System.out.println("Browser health check failed: " + e.getMessage());
			return false;
		}
	}

	public boolean isCourseManagementMenuVisible() {
		// TODO Auto-generated method stub
		try {
			wait.until(ExpectedConditions.visibilityOf(courseManagementMenu));
			return courseManagementMenu.isDisplayed();
		} catch (Exception e) {
			System.out.println("Course Management menu is not visible: " + e.getMessage());
		}
		return false;
	}

	public boolean isCourseTitleEntered() {
		// TODO Auto-generated method stub
		try {
			wait.until(ExpectedConditions.visibilityOf(courseTitleInput));
			return !courseTitleInput.getAttribute("value").isEmpty();
		} catch (Exception e) {
			System.out.println("Course title input is not visible or empty: " + e.getMessage());
		}
		return false;
	}

	public boolean isCourseDescriptionEntered() {
		// TODO Auto-generated method stub
		try {
			wait.until(ExpectedConditions.visibilityOf(courseDescriptionInput));
			return !courseDescriptionInput.getText().isEmpty();
		} catch (Exception e) {
			System.out.println("Course description input is not visible or empty: " + e.getMessage());
		}
		return false;
	}

	public boolean isCourseCategorySelected() {
		// TODO Auto-generated method stub
		try {
			wait.until(ExpectedConditions.visibilityOf(courseCategoryInput));
			return courseCategoryInput.getText() != null && !courseCategoryInput.getText().isEmpty();
		} catch (Exception e) {
			System.out.println("Course category input is not visible or empty: " + e.getMessage());
		}
		return false;
	}

	public boolean isCourseSubCategorySelected() {
		// TODO Auto-generated method stub
		try {
			wait.until(ExpectedConditions.visibilityOf(courseCategorysubInput));
			return courseCategorysubInput.getText() != null && !courseCategorysubInput.getText().isEmpty();
		} catch (Exception e) {
			System.out.println("Course sub-category input is not visible or empty: " + e.getMessage());
		}
		return false;
	}


	// Additional methods for managing courses can be added here

    @FindBy(xpath = "//i[@title='Add course']")
    private WebElement plusIcon;

    @FindBy(xpath = "//div[@class='image-options-container']//div[1]//div[1]//label[1]//input[1]")
    private WebElement moduleRadioButton;

    @FindBy(xpath = "//div[@class='right-panels ng-star-inserted']//div[2]//div[1]//label[1]//input[1]")
    private WebElement lessonRadioButton;

    @FindBy(xpath = "//button[normalize-space()='Proceed']")
    private WebElement proceedButton;

    public void clickPlusIcon() {
        try {
            waitForLoaderToDisappear();
            wait.until(ExpectedConditions.elementToBeClickable(plusIcon));
            ca.clickElement(plusIcon);
        } catch (Exception e) {
            System.out.println("Error clicking plus icon: " + e.getMessage());
            throw e;
        }
    }

    public void selectModuleRadioButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(moduleRadioButton));
            ca.clickElement(moduleRadioButton);
        } catch (Exception e) {
            System.out.println("Error selecting module radio button: " + e.getMessage());
            throw e;
        }
    }

    public void selectlessonRadioButton() throws Exception {
       ju.javaScriptClick(lessonRadioButton);
    }

    public void clickProceedButton() throws Exception {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(proceedButton));
            Thread.sleep(3000);
            ju.scrollToElement(proceedButton);
            ju.javaScriptClick(proceedButton);
            System.out.println("Proceed button clicked successfully");
        } catch (Exception e) {
            System.out.println("Error clicking proceed button: " + e.getMessage());
            throw e;
        }
    }


    @FindBy(xpath = "//p[@class='NgxEditor__Placeholder']")
    private WebElement moduledescription;

    @FindBy(xpath = "//button[normalize-space()='Save']")
    private WebElement saveButton;

    public void enterModuleDescription(String description) {
        try {
            wait.until(ExpectedConditions.visibilityOf(moduledescription));
            ju.scrollToElement(moduledescription);
            moduledescription.clear();
            moduledescription.sendKeys(description);
        } catch (Exception e) {
            System.out.println("Error entering module description: " + e.getMessage());
            throw e;
        }
    }

    public void clickSaveButton() throws Exception {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(saveButton));
            Thread.sleep(3000);
            ju.scrollToElement(saveButton);
            ju.javaScriptClick(saveButton);
            System.out.println("Save button clicked successfully");
        } catch (Exception e) {
            System.out.println("Error clicking save button: " + e.getMessage());
            throw e;
        }
    }



    @FindBy(xpath = "//i[@title='Add lesson']")
    private WebElement plusLessonIcon;

    public void clickPlusLessonIcon() throws Exception {
        try {
            Thread.sleep(3000);
            wait.until(ExpectedConditions.elementToBeClickable(plusLessonIcon));
            ju.scrollToElement(plusLessonIcon);
            ju.javaScriptClick(plusLessonIcon);
            System.out.println("Plus lesson icon clicked successfully");
        } catch (Exception e) {
            System.out.println("Error clicking plus lesson icon: " + e.getMessage());
            throw e;
        }
    }

    @FindBy(xpath = "//i[@title='Add content']")
    private WebElement plusContentIcon;

    public void clickPlusContentIcon() throws Exception {
        try {
            Thread.sleep(3000);

            // Try multiple selectors for plus content icon
            String[] selectors = {
                "//i[@title='Add content']",
                "//i[contains(@class,'fa-plus')]",
                "//button[contains(@title,'Add')]",
                "//i[@class='fas fa-plus']",
                "//span[contains(text(),'Add Content')]/parent::button"
            };

            for (String selector : selectors) {
                try {
                    WebElement element = driver.findElement(org.openqa.selenium.By.xpath(selector));
                    if (element.isDisplayed()) {
                        wait.until(ExpectedConditions.elementToBeClickable(element));
                        ju.scrollToElement(element);
                        ju.javaScriptClick(element);
                        System.out.println("Plus content icon clicked using: " + selector);
                        return;
                    }
                } catch (Exception ex) {
                    System.out.println("Selector failed: " + selector);
                }
            }
            throw new RuntimeException("Plus content icon not found");
        } catch (Exception e) {
            System.out.println("Error clicking plus content icon: " + e.getMessage());
            throw e;
        }
    }

    @FindBy(xpath = "(//div[contains(@class,'row') and contains(@class,'justify-content-center')]//div[contains(@class,'col')]//div)[1]")
    private WebElement RepositoryIcon;

    public void clickRepositoryIcon() throws Exception {
        try {
            Thread.sleep(3000);
            wait.until(ExpectedConditions.elementToBeClickable(RepositoryIcon));
            ju.scrollToElement(RepositoryIcon);
            ju.javaScriptClick(RepositoryIcon);
            System.out.println("Repository icon clicked successfully");
        } catch (Exception e) {
            System.out.println("Error clicking Repository icon: " + e.getMessage());
            throw e;
        }
    }

    @FindBy(xpath = "//tbody/tr[1]/td[1]/input[1]")
    private WebElement contentIcon;

    public void clickContentIcon() throws Exception {
        try {
            Thread.sleep(3000);
            wait.until(ExpectedConditions.elementToBeClickable(contentIcon));
            ju.scrollToElement(contentIcon);
            ju.javaScriptClick(contentIcon);
            System.out.println("Content icon clicked successfully");
        } catch (Exception e) {
            System.out.println("Error clicking Content icon: " + e.getMessage());
            throw e;
        }
    }
@FindBy(xpath = "//button[@class='btn btn-primary add-content ng-star-inserted']")
private WebElement addButton;

public void clickAddButton() throws Exception {
    try {
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        ju.scrollToElement(addButton);
        ju.javaScriptClick(addButton);
        System.out.println("Add button clicked successfully");
    } catch (Exception e) {
        System.out.println("Error clicking Add button: " + e.getMessage());
        throw e;
    }
}

@FindBy(xpath = "//button[@class='btn btn-primary']")
private WebElement nextButton;

public void clickNextButton() throws Exception {
    try {
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(nextButton));
        ju.scrollToElement(nextButton);
        ju.javaScriptClick(nextButton);
        System.out.println("Next button clicked successfully");
    } catch (Exception e) {
        System.out.println("Error clicking Next button: " + e.getMessage());
        throw e;
    }
}

@FindBy(xpath = "//button[@class='btn btn-primary']")
private WebElement SaveButton;

public void clickSaveButtonPreview() throws Exception {
    try {
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(SaveButton));
        ju.scrollToElement(SaveButton);
        ju.javaScriptClick(SaveButton);
        System.out.println("Save button clicked successfully");
    } catch (Exception e) {
        System.out.println("Error clicking Save button: " + e.getMessage());
        throw e;
    }
}

@FindBy(xpath = "//input[@id='freeCourseToggle']")
private WebElement freeToggleButton;

public void clickFreeToggleButton() throws Exception {
    try {
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(freeToggleButton));
        ju.scrollToElement(freeToggleButton);
        ju.javaScriptClick(freeToggleButton);
        System.out.println("Free toggle button clicked successfully");
    } catch (Exception e) {
        System.out.println("Error clicking Free toggle button: " + e.getMessage());
        throw e;
    }
}

@FindBy(xpath = "//input[@class='form-control ng-pristine ng-valid ng-star-inserted ng-touched']")
private WebElement priceInput;

public void enterPrice(String price) throws Exception {
    try {
        Thread.sleep(2000);
        // Try multiple selectors for price input
        String[] priceSelectors = {
            "//input[@class='form-control ng-pristine ng-valid ng-star-inserted ng-touched']",
            "//input[contains(@class,'form-control')][contains(@placeholder,'price')]",
            "//input[@type='number']",
            "//input[contains(@class,'price')]"
        };

        for (String selector : priceSelectors) {
            try {
                WebElement element = driver.findElement(org.openqa.selenium.By.xpath(selector));
                if (element.isDisplayed()) {
                    element.clear();
                    element.sendKeys(price);
                    System.out.println("Price entered: " + price);
                    return;
                }
            } catch (Exception ex) {
                System.out.println("Price selector failed: " + selector);
            }
        }
        System.out.println("Price input not found - skipping");
        return;
    } catch (Exception e) {
        System.out.println("Error entering price: " + e.getMessage());
        throw e;
    }
}

@FindBy(xpath = "//button[normalize-space()='Save & Continue']")
private WebElement SaveContinueButton;

public void clickSaveContinueButton() throws Exception {
    try {
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(SaveContinueButton));
        ju.scrollToElement(SaveContinueButton);
        ju.javaScriptClick(SaveContinueButton);
        System.out.println("Save & Continue button clicked successfully");
    } catch (Exception e) {
        System.out.println("Error clicking Save & Continue button: " + e.getMessage());
        throw e;
    }
}

@FindBy(xpath = "//button[@class='btn btn-primary ms-2 ng-star-inserted']")
private WebElement PublishCourseButton;

public void clickPublishCourseButton() throws Exception {
    try {
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(PublishCourseButton));
        ju.scrollToElement(PublishCourseButton);
        ju.javaScriptClick(PublishCourseButton);
        System.out.println("Publish Course button clicked successfully");
    } catch (Exception e) {
        System.out.println("Error clicking Publish Course button: " + e.getMessage());
        throw e;
    }
}
@FindBy(xpath = "//button[normalize-space()='Yes']")
private WebElement yesButton;

public void clickYesButton() throws Exception {
    try {
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(yesButton));
        ju.scrollToElement(yesButton);
        ju.javaScriptClick(yesButton);
        System.out.println("Yes button clicked successfully");
    } catch (Exception e) {
        System.out.println("Error clicking Yes button: " + e.getMessage());
        throw e;
    }
}

public void saveModule() {
	// TODO Auto-generated method stub

}



}