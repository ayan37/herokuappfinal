package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

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
						driver = new ChromeDriver();
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
