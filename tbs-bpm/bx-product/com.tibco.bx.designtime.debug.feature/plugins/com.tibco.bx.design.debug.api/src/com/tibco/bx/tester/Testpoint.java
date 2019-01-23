package com.tibco.bx.tester;

import com.tibco.bx.debug.api.ConditionLanguage;

public class Testpoint {

	private String processId;
	private String location;
	private String expression;
	ConditionLanguage lang;
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public ConditionLanguage getLang() {
		return lang;
	}
	public void setLang(ConditionLanguage lang) {
		this.lang = lang;
	}
	
	
}
