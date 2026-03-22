package utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigLoader {
	private static Properties props = new Properties();
	public static boolean isError = false;
	public static String errMsg = "";
	public static void loadConfig() {
		try(FileInputStream fs = new FileInputStream("src/test/resources/config.properties")){
			props.load(fs);
		}catch (Exception e) {
			isError = true;
			errMsg = "Error Occurred while loading config file: " + e.getMessage();
		}
	}
	public static String getConfig(String key) {
		return props.getProperty(key);
	}
}
