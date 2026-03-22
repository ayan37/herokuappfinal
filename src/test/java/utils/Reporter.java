package utils;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import hooks.Hooks;

public class Reporter {
	public static String reportDir;
	static TimeStamp timeStamp = new TimeStamp();
	static ExtentReports extentReports = Hooks.extentReports;
	public static ExtentReports generatExtentReports(String fileName) {
		if(extentReports == null) {
			return createExtentReports(fileName);
		}else {
			return extentReports;
		}
	}
	private static ExtentReports createExtentReports(String fileName) {
		try {
			extentReports = new ExtentReports();
			reportDir = System.getProperty("user.dir") + "/reports/" + fileName + "_" + timeStamp.getTimeStamp();
			File file = new File(reportDir + "/report.html");
			ExtentSparkReporter spark = new ExtentSparkReporter(file);
			// Theme & Branding
			spark.config().setTheme(Theme.STANDARD); // or Theme.STANDARD for light mode
			spark.config().setDocumentTitle(fileName + " - Automation Report");
			spark.config().setReportName("HerokuApp Automation Suite");
			spark.config().setEncoding("utf-8");
			spark.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

			// Attach reporter
			extentReports.attachReporter(spark);

			// System Info (shows up in dashboard)
			extentReports.setSystemInfo("Environment", Hooks.env);
			extentReports.setSystemInfo("Base URL", Hooks.baseUrl);
			extentReports.setSystemInfo("Browser", ConfigLoader.getConfig("browser"));
			extentReports.setSystemInfo("Tester", "QA Automation Team");


		}catch(Exception e) {
			e.printStackTrace();
		}
		return extentReports;
	}
	public static void addTestExecutionSteps(ExtentTest test, String status, String ssPath, String details) {
		if(status.equalsIgnoreCase("pass")) {
			test.pass(details, MediaEntityBuilder.createScreenCaptureFromPath(ssPath).build());
		}
		else if(status.equalsIgnoreCase("fail")) {
			test.fail(details, MediaEntityBuilder.createScreenCaptureFromPath(ssPath).build());
		}
		else if (status.equalsIgnoreCase("skip")) {
			test.skip(details);
		}
	}
	public static void addTestExecutionSteps(ExtentTest test, String status, String details) {
		if(status.equalsIgnoreCase("pass")) {
			test.pass(details);
		}
		else if(status.equalsIgnoreCase("fail")) {
			test.fail(details);
		}
		else if (status.equalsIgnoreCase("skip")) {
			test.skip(details);
		}
	}
}
