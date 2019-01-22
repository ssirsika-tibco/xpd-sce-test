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
 *         &lt;element name="activities" type="{http://www.tibco.com/bx/2010/debugging/debuggerType}Activities"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "activities" })
@XmlRootElement(name = "getActivitiesOutput")
public class GetActivitiesOutput {

	@XmlElement(required = true)
	protected Activities activities;

	/**
	 * Gets the value of the activities property.
	 * 
	 * @return possible object is {@link Activities }
	 * 
	 */
	public Activities getActivities() {
		return activities;
	}

	/**
	 * Sets the value of the activities property.
	 * 
	 * @param value
	 *            allowed object is {@link Activities }
	 * 
	 */
	public void setActivities(Activities value) {
		this.activities = value;
	}

}
