package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.WebDriverHelper;

public class FileUploadPage {
	public WebDriver driver;
	public ExtentTest test;
	public FileUploadPage(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = "#file-upload")
	private WebElement chooseFile;
	
	public void selectFileToUpload(String path) {
		String [] parts = path.split("/");
		String fileName = parts[parts.length - 1];
		WebDriverHelper.sendKeysToElement(driver, test, chooseFile, path, "Chose file " + fileName, 30);
	}
	public void selectFileForUploading(String path) {
		String [] parts = path.split("/");
		String fileName = parts[parts.length - 1];
		WebDriverHelper.ClickElement(driver, test, chooseFile, "Clicking Select File Button", 30);
		WebDriverHelper.uploadFile(driver, test, path, "Uploading File " + fileName, 30);
	}
}
