package pages;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import hooks.Hooks;
import utils.ExceptionHandler;
import utils.Reporter;
import utils.Screenshot;
import utils.WebDriverHelper;

public class HomePage {
	String baseUrl = Hooks.baseUrl;
	public WebDriver driver;
	public ExtentTest test;
	
	@FindBy(css = "a[href *= 'upload']")
	private WebElement fileUpload;
	
	private Map<String, WebElement> pageLinks = new HashMap<String, WebElement>();
	public HomePage(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
		PageFactory.initElements(driver, this);
		pageLinks.put("fileupload", fileUpload);
	}
	public void navigateToHomepage() {
		try {
			driver.get(baseUrl);
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
			String ssPath = Screenshot.takeScreenshot(driver, "homeUrlLaunch");
			Reporter.addTestExecutionSteps(test, "pass", ssPath, "Homepage Launched successfully");
		}catch (Exception e) {
			ExceptionHandler.handleException(driver, test, e, "Homepage Launch");
			throw e;
		}
	}
	
	public void navigateToPage(String pageName) {
		WebElement element = pageLinks.get(pageName.toLowerCase());
		if(element == null) {
			Reporter.addTestExecutionSteps(test, "fail", pageName + " page navigation button not found");
			// Fail fast and move to next scenario
            throw new RuntimeException("Page navigation failed: " + pageName + " not found");
		}
		WebDriverHelper.ClickElement(driver, test, element, "File Upload", 30);
	}
}
