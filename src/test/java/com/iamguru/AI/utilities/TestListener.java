package com.iamguru.AI.utilities;

import java.io.IOException;

import org.testng.ITestListener;
import org.testng.ITestResult;
import com.iamguru.AI.testCases.BaseClass;

public class TestListener extends BaseClass implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {

        String testName = result.getName();

        logger.error("❌ Test Failed: " + testName);

        try {
			captureScreen(driver, testName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  // Your existing method
    }
}