package runner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
@CucumberOptions(
	features = "src/test/resources/features",
	glue = {"stepdefs", "hooks"},
	tags = ""
)
public class TestRunner extends AbstractTestNGCucumberTests {
//	@Override
//    @DataProvider(parallel = true)   // ✅ enables parallel execution
//    public Object[][] scenarios() {
//        return super.scenarios();
//    }

}
