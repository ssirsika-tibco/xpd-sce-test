package com.tibco.inteng.xslfunctions;

import java.util.Date;
import java.text.SimpleDateFormat;

/** 
 * Collection of XSL extension functions. 
 * @author Tim Stephenson
 */
public class XslDateFunctions {

	/** 
	 * Get today's date formatted according to the supplier mask. 
	 * @param format Mask suitable for use in java.text.SimpleDateFormat. 
	 * @return Formatted date string. 
	 */
	public static String getTodayString(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(new Date());
	}

	/** 
	 * Get today's date formatted according to the supplier mask. 
	 * @param format Mask suitable for use in java.text.SimpleDateFormat. 
	 * @return Formatted date string. 
	 */
	public static String getTodayStringForXml() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return df.format(new Date());
	}
}