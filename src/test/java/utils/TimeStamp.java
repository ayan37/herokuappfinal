package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp {
	public String getTimeStamp() {
		return new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
	}
}
