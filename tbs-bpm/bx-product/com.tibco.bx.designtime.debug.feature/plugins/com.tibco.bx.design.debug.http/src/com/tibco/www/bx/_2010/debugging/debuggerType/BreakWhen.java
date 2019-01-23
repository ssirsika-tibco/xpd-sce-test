package com.tibco.www.bx._2010.debugging.debuggerType;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for BreakWhen.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="BreakWhen">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ENTRY"/>
 *     &lt;enumeration value="RETURN"/>
 *     &lt;enumeration value="BOTH"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BreakWhen")
@XmlEnum
public enum BreakWhen {

	ENTRY, RETURN, BOTH;

	public String value() {
		return name();
	}

	public static BreakWhen fromValue(String v) {
		return valueOf(v);
	}

}
