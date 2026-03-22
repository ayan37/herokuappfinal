package utils;

import io.cucumber.java.Scenario;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentTest;

import hooks.Hooks;

public class ExcelDataProvider {
    private static String featureName = Hooks.featureName;
    private static String tcTag = Hooks.tcTag;
    private static String testDataPath = Hooks.testDataPath;

    public static Map<String, String> getData(Scenario scenario, String id) {
        Map<String, String> data = new HashMap<>();
        ExtentTest extentTest = Hooks.getTest();
        WebDriver driver = Hooks.getDriver();

        try {

            String filePath = testDataPath + featureName + "/testdata.xlsx";

            try (FileInputStream fis = new FileInputStream(filePath);
                 Workbook wb = new XSSFWorkbook(fis)) {

                Sheet sheet = wb.getSheet(tcTag);
                if (sheet == null) {
                    throw new RuntimeException("No sheet found for tag: " + tcTag);
                }

                // First row = headers
                Row headerRow = sheet.getRow(0);
                if (headerRow == null) {
                    throw new RuntimeException("Header row missing in sheet: " + tcTag);
                }

                // Find row by ID
                Row targetRow = null;
                if (id == null || id.isEmpty()) {
                    // Plain scenario: pick the first data row
                	targetRow = sheet.getRow(1); // row after headers
                } else {
                    // Scenario Outline: find row by ID
	                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	                    Row row = sheet.getRow(i);
	                    if (row != null) {
	                        String rowId = getCellValue(row.getCell(0)); // ID column assumed at index 0
	                        if (id.equals(rowId)) {
	                            targetRow = row;
	                            break;
	                        }
	                    }
	                }
                }

                if (targetRow == null) {
                    throw new RuntimeException("No data found for ID: " + id);
                }
                
                int executionStatusColIndex = -1;
                for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                    String headerName = getCellValue(headerRow.getCell(j));
                    if ("Execution Status".equalsIgnoreCase(headerName.trim())) {
                        executionStatusColIndex = j;
                        break;
                    }
                }

                if (executionStatusColIndex == -1) {
                    throw new RuntimeException("ExecutionStatus column not found in sheet: " + tcTag);
                }

                // Read the value from the ExecutionStatus column
                String executionStatus = getCellValue(targetRow.getCell(executionStatusColIndex));
                if (executionStatus != null && executionStatus.equalsIgnoreCase("N")) {
                    throw new SkipException("Skipping execution for ID: " + id);
                }

                // Map headers to values
                for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                    String key = getCellValue(headerRow.getCell(j));
                    String value = getCellValue(targetRow.getCell(j));
                    if (key != null && !key.isEmpty()) {
                        data.put(key, value);
                    }
                }
            }

        } catch (Exception e) {
            if (e instanceof SkipException) {
                // Let TestNG/Cucumber handle it as skipped
            	if (extentTest != null) {
                    Reporter.addTestExecutionSteps(extentTest, "skip", "Scenario skipped: " + e.getMessage());
                }
            	throw new SkipException(e.getMessage());
            } else {
                // Handle all other exceptions
                if (extentTest != null) {
                    ExceptionHandler.handleException(driver, extentTest, e, "Excel Data Fetch");
                }
                throw new RuntimeException("Error in ExcelDataProvider: " + e.getMessage(), e);
            }
        }

        return data;
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue().trim();
            case NUMERIC: return String.valueOf((long) cell.getNumericCellValue()); // cast to long for IDs
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            case FORMULA: return cell.getCellFormula();
            default: return "";
        }
    }

}