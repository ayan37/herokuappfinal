package utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

public class WebDriverHelper {
	public static void ClickElement(WebDriver driver, ExtentTest test, WebElement ele, String stepName, int timeout) {
		try {
			WebElement element = waitForElementVisible(driver, test, ele, stepName, timeout);
			element = waitForElementClickable(driver, test, ele, stepName, timeout);
			element.click();
			String ssPath = Screenshot.takeScreenshot(driver, stepName);
			Reporter.addTestExecutionSteps(test, "pass", ssPath, stepName);
		}catch (Exception e) {
			ExceptionHandler.handleException(driver, test, e, stepName);
			throw e;
		}
	}
	public static void sendKeysToElement(WebDriver driver, ExtentTest test, WebElement ele, String text, String stepName, int timeout) {
		try {
			WebElement element = waitForElementVisible(driver, test, ele, stepName, timeout);
			element.sendKeys(text);
			String ssPath = Screenshot.takeScreenshot(driver, stepName);
			Reporter.addTestExecutionSteps(test, "pass", ssPath, stepName);
		}catch (Exception e) {
			ExceptionHandler.handleException(driver, test, e, stepName);
			throw e;
		}
	}
	public static void uploadFile(WebDriver driver, ExtentTest test, String path, String stepName, int timeout) {
		try{
			StringSelection filePath = new StringSelection(path);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(filePath, null);
			Robot robot;
			try {
				robot = new Robot();
			}catch (AWTException awtE) {
				ExceptionHandler.handleException(driver, test, awtE, stepName);
				throw new RuntimeException("Robot initialization failed: " + awtE.getMessage(), awtE);
			}
			robot.delay(1000);
			
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			
			String ssPath = Screenshot.takeScreenshot(driver, "fileselected");
			Reporter.addTestExecutionSteps(test, "pass", ssPath, "Selected File to upload");
			
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			
			String ssPath2 = Screenshot.takeScreenshot(driver, stepName);
			Reporter.addTestExecutionSteps(test, "pass", ssPath2, stepName);
			
		}catch (Exception e) {
			ExceptionHandler.handleException(driver, test, e, stepName);
			throw e;
		}
	}
	public static WebElement waitForElementVisible(WebDriver driver, ExtentTest test, WebElement ele, String stepName, int timeout) {
		WebElement element = null;
		try{
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			element = wait.until(ExpectedConditions.visibilityOf(ele));
		}catch (Exception e) {
			ExceptionHandler.handleException(driver, test, e, stepName);
			throw e;
		}
		return element;
	}
	public static WebElement waitForElementClickable(WebDriver driver, ExtentTest test, WebElement ele, String stepName, int timeout) {
		WebElement element = null;
		try{
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			element = wait.until(ExpectedConditions.elementToBeClickable(ele));
		}catch (Exception e) {
			ExceptionHandler.handleException(driver, test, e, stepName);
			throw e;
		}
		return element;
	}
}
