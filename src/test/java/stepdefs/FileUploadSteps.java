package stepdefs;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;

import hooks.Hooks;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.FileUploadPage;
import utils.ExcelDataProvider;

public class FileUploadSteps {
	public WebDriver driver = Hooks.getDriver();
	public ExtentTest test = Hooks.getTest();
	public Scenario currentScenario = Hooks.currentScenario;
	public String featureName = Hooks.featureName;
	public String testDataPath = Hooks.testDataPath;
	public FileUploadPage fileUploadPage = new FileUploadPage(driver, test);
	
	@When("I select the file with id {string} to upload")
	public void selectFileToUpload(String id) {
		Map<String, String> data = ExcelDataProvider.getData(currentScenario, id);
		String folderPath = testDataPath + featureName;
		String path = folderPath + "/" + data.get("FileName");
		fileUploadPage.selectFileToUpload(path);
//		fileUploadPage.selectFileForUploading(path);
	}
	@When("I upload the file")
	public void uploadTheFile() {
		
	}
	@Then("the file should be uploaded successfully")
	public void fileUploadSuccess() {
		
	}
}
