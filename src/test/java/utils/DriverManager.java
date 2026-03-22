package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {
	public static boolean isError = false;
	public static String errMsg = "";
	private WebDriver driver;
	
	private void initDriver() {
		if(this.driver == null) {
			String browser = ConfigLoader.getConfig("browser");
			switch(browser.toLowerCase()) {
				case "chrome":{
					try {
						WebDriverManager.chromedriver().setup();
						this.driver = new ChromeDriver();
					}catch (Exception e) {
						isError = true;
						errMsg = "Error occurred while opening driver for browser " + browser + ": " + e.getMessage();
					}
					break;
				}
				case "edge":{
					try {
						WebDriverManager.edgedriver().setup();
						this.driver = new EdgeDriver();
					}catch (Exception e) {
						isError = true;
						errMsg = "Error occurred while opening driver for browser " + browser + ": " + e.getMessage();
					}
					break;
				}
				default:
					isError = true;
					errMsg = "Error occurred while opening driver for browser " + browser + ": browser not found";
			}
		}
		this.driver.manage().window().maximize();
	}
	public WebDriver getDriver() {
		if(this.driver == null) {
			initDriver();
		}
		return this.driver;
	}
	public void quitDriver(WebDriver driver) {
		if(driver != null) {
			driver.quit();
			driver = null;
		}
	}
}
