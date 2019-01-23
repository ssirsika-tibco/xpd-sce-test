package com.tibco.www.bx._2010.debugging.debuggerType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="processId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "processId", "location", "value" })
@XmlRootElement(name = "addAssertionInput")
public class AddAssertionInput {

	@XmlElement(required = true)
	protected String processId;
	@XmlElement(required = true)
	protected String location;
	protected boolean value;

	/**
	 * Gets the value of the processId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getProcessId() {
		return processId;
	}

	/**
	 * Sets the value of the processId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setProcessId(String value) {
		this.processId = value;
	}

	/**
	 * Gets the value of the location property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the value of the location property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setLocation(String value) {
		this.location = value;
	}

	/**
	 * Gets the value of the value property.
	 * 
	 */
	public boolean isValue() {
		return value;
	}

	/**
	 * Sets the value of the value property.
	 * 
	 */
	public void setValue(boolean value) {
		this.value = value;
	}

}
