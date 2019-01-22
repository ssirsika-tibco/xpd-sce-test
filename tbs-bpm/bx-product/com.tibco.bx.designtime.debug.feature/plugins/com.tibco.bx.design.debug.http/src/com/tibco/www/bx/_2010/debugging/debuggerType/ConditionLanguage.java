package com.tibco.www.bx._2010.debugging.debuggerType;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ConditionLanguage.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="ConditionLanguage">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="JSCRIPT"/>
 *     &lt;enumeration value="XPATH"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ConditionLanguage")
@XmlEnum
public enum ConditionLanguage {

	JSCRIPT, XPATH;

	public String value() {
		return name();
	}

	public static ConditionLanguage fromValue(String v) {
		return valueOf(v);
	}

}
