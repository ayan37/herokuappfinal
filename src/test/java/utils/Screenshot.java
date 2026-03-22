package utils;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import hooks.Hooks;

public class Screenshot {
	public static boolean isError = false;
	public static String errMsg = "";
	static String reportPath = Hooks.reportDir;
	static TimeStamp timeStamp = new TimeStamp();
	public static String takeScreenshot(WebDriver driver, String fileName) {
		String ssPath = "screenshots/" + fileName + "_" + timeStamp.getTimeStamp() + ".jpeg";
		String ssFullPath = reportPath + "/" + ssPath;
		try {
			File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(file, new File(ssFullPath));
		}catch (Exception e) {
			isError = true;
			errMsg = "Error Occurred while taking screenshot: " + e.getMessage();
		}
		return ssPath;
	}
}