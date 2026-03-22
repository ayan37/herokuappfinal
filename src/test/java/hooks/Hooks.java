package hooks;

import java.util.Optional;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import utils.ConfigLoader;
import utils.DriverManager;
import utils.Reporter;

public class Hooks {
	public static ExtentReports extentReports;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	public static Scenario currentScenario;
	public static String featureName;
	public static String tcTag;
	public static String testDataPath;
	public static String env;
	public static String baseUrl;
	public static String reportDir;
	@BeforeAll
	public static void initialize() {
		ConfigLoader.loadConfig();
		env = ConfigLoader.getConfig("env");
		extentReports = Reporter.generatExtentReports("HerokuApp - " + env);
		reportDir = Reporter.reportDir;
	}
	@Before
	public void beforeScenario(Scenario scenario) {
		currentScenario = scenario;
		// Feature name
        featureName = Optional.ofNullable(currentScenario.getUri())
                .map(uri -> uri.toString().replaceAll(".*/", "").replace(".feature", ""))
                .orElseThrow(() -> new RuntimeException("Feature name could not be determined"));

        // TC tag
        tcTag = currentScenario.getSourceTagNames().stream()
                .filter(t -> t.startsWith("@TC"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No TC tag found"))
                .replace("@", "");
        ExtentTest extentTest  = extentReports.createTest(currentScenario.getName());
        test.set(extentTest);

        WebDriver webDriver = new DriverManager().getDriver();
        driver.set(webDriver);

		if(ConfigLoader.isError) {
			Reporter.addTestExecutionSteps(getTest(), "fail", ConfigLoader.errMsg);
		}
		if(DriverManager.isError) {
			Reporter.addTestExecutionSteps(getTest(), "fail", DriverManager.errMsg);
		}
		env = ConfigLoader.getConfig("env");
		baseUrl = ConfigLoader.getConfig(env + ".url");
		testDataPath = System.getProperty("user.dir") + ConfigLoader.getConfig("testdata");
	}
	@After
	public void afterScenario() {
		new DriverManager().quitDriver(getDriver());
		driver.remove();
        test.remove();
	}
	@AfterAll
	public static void tearDown() {
		extentReports.flush();
	}
	public static WebDriver getDriver() { return driver.get(); }
    public static ExtentTest getTest() { return test.get(); }
}
