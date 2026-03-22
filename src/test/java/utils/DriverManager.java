package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {
	public static boolean isError = false;
	public static String errMsg = "";
	private static WebDriver driver;
	
	private static void initDriver() {
		if(driver == null) {
			String browser = ConfigLoader.getConfig("browser");
			switch(browser.toLowerCase()) {
				case "chrome":{
					try {
//						ChromeOptions options = new ChromeOptions();
//				        options.addArguments("--headless=new");   // required for Jenkins
//				        options.addArguments("--disable-gpu");
//				        options.addArguments("--window-size=1920,1080");
						driver = new WebDriver();
					}catch (Exception e) {
						isError = true;
						errMsg = "Error occurred while opening driver for browser " + browser + ": " + e.getMessage();
					}
					break;
				}
				case "edge":{
					try {
						driver = new EdgeDriver();
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
		driver.manage().window().maximize();
	}
	public static WebDriver getDriver() {
		if(driver == null) {
			initDriver();
		}
		return driver;
	}
	public static void quitDriver() {
		if(driver != null) {
			driver.quit();
			driver = null;
		}
	}
}
