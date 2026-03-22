package stepdefs;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;

import hooks.Hooks;
import io.cucumber.java.en.Given;
import pages.HomePage;

public class HomeSteps {
	public WebDriver driver = Hooks.getDriver();
	public ExtentTest test = Hooks.getTest();
	public HomePage homePage = new HomePage(driver, test);
	
	@Given("I am on Homepage")
	public void onHomepage() {
		homePage.navigateToHomepage();
	}
	@Given("I am on {string} page")
	public void onPage(String string) {
		homePage.navigateToPage(string);
	}
}
