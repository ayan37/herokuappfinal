package utils;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;

public class ExceptionHandler {
	public static void handleException(WebDriver driver, ExtentTest test, Exception e, String stepName) {
		String ssPath = Screenshot.takeScreenshot(driver, stepName + "_Error");
		Reporter.addTestExecutionSteps(test, "fail", ssPath, stepName + " failed, Error: " + e.getMessage());
	}
}
