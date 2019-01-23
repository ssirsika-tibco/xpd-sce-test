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
 *         &lt;element name="instanceId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="when" type="{http://www.tibco.com/bx/2010/debugging/debuggerType}BreakWhen"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "instanceId", "location", "when" })
@XmlRootElement(name = "runToActivityInput")
public class RunToActivityInput {

	@XmlElement(required = true)
	protected String instanceId;
	@XmlElement(required = true)
	protected String location;
	@XmlElement(required = true)
	protected BreakWhen when;

	/**
	 * Gets the value of the instanceId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getInstanceId() {
		return instanceId;
	}

	/**
	 * Sets the value of the instanceId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setInstanceId(String value) {
		this.instanceId = value;
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
	 * Gets the value of the when property.
	 * 
	 * @return possible object is {@link BreakWhen }
	 * 
	 */
	public BreakWhen getWhen() {
		return when;
	}

	/**
	 * Sets the value of the when property.
	 * 
	 * @param value
	 *            allowed object is {@link BreakWhen }
	 * 
	 */
	public void setWhen(BreakWhen value) {
		this.when = value;
	}

}
