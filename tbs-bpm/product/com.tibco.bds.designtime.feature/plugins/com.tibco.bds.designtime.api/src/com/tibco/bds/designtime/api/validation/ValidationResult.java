package com.tibco.bds.designtime.api.validation;

import java.util.ArrayList;
import java.util.List;

import com.tibco.bds.designtime.api.internal.Messages;

/**
 * Represents a result of a validation operation. This has a severity (error or
 * warning) and a message string.
 * 
 * The class also contains some static convenience methods for working with
 * Lists of these objects.
 * 
 * @author smorgan
 * 
 */
public class ValidationResult {

	private static final String NEWLINE = "\n";

	public enum Severity {
		ERROR, WARNING
	};

	private Severity severity;
	private String message;

	protected ValidationResult(Severity severity, String message) {
		this.severity = severity;
		this.message = message;
	}

	/**
	 * Returns true if one or more of the results in the supplied list is of
	 * error severity
	 * 
	 * @param results
	 * @return
	 */
	public static boolean containsErrors(List<ValidationResult> results) {
		boolean ret = false;
		for (ValidationResult result : results) {
			if (result.isError()) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	/**
	 * Returns true if one or more of the results in the supplied list is of
	 * warning severity
	 * 
	 * @param results
	 * @return
	 */
	public static boolean containsWarnings(List<ValidationResult> results) {
		boolean ret = false;
		for (ValidationResult result : results) {
			if (result.isWarning()) {
				ret = true;
				break;
			}
		}
		return ret;
	}
	
	/**
	 * Filters the supplied list to just errors and returns the result
	 * as a new list.
	 * 
	 * @param results
	 * @return
	 */
	public static List<ValidationResult> getErrors(List<ValidationResult> results)
	{
		List<ValidationResult> list = new ArrayList<ValidationResult>();
		for (ValidationResult result : results)
		{
			if (result.isError())
			{
				list.add(result);
			}
		}
		return list;
	}

	/**
	 * Filters the supplied list to just warnings and returns the result
	 * as a new list.
	 * 
	 * @param results
	 * @return
	 */
	public static List<ValidationResult> getWarnings(List<ValidationResult> results)
	{
		List<ValidationResult> list = new ArrayList<ValidationResult>();
		for (ValidationResult result : results)
		{
			if (result.isWarning())
			{
				list.add(result);
			}
		}
		return list;
	}
	
	/**
	 * A convenience method to obtain all of the messages from the supplied list
	 * of results, formatted one per line and prefixed with a severity label.
	 * 
	 * If finer-grained control is required, or this formatting is not suitable,
	 * then look at each result in turn, obtaining its message via getMessage
	 * and its severity via isError and isWarning.
	 * 
	 * @param results
	 * @return
	 */
	public static String getMessages(List<ValidationResult> results) {
		StringBuilder buf = new StringBuilder();
		for (ValidationResult result : results) {
			if (buf.length() != 0) {
				buf.append(NEWLINE);
			}
			if (result.isError()) {
				buf.append(String.format(
						Messages.ValidationResult_error_message,
						result.getMessage()));
			} else {
				// warning
				buf.append(String.format(
						Messages.ValidationResult_warning_message,
						result.getMessage()));
			}
		}
		return buf.toString();
	}

	/**
	 * Returns true if this result is of error severity
	 * 
	 * @return
	 */
	public boolean isError() {
		return severity == Severity.ERROR;
	}

	/**
	 * Returns true if this result is of warning severity
	 * 
	 * @return
	 */
	public boolean isWarning() {
		return severity == Severity.WARNING;
	}

	/**
	 * Returns a message describing the nature of this result
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}
}
