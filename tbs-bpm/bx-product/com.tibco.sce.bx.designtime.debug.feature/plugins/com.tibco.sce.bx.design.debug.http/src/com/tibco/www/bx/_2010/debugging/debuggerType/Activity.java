package com.tibco.www.bx._2010.debugging.debuggerType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Activity complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Activity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="activityId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="activityName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="instanceId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="parentInstanceId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Activity", propOrder = { "activityId", "activityName", "instanceId", "parentInstanceId", "state" })
public class Activity {

	@XmlElement(required = true)
	protected String activityId;
	@XmlElement(required = true)
	protected String activityName;
	@XmlElement(required = true)
	protected String instanceId;
	@XmlElement(required = true)
	protected String parentInstanceId;
	@XmlElement(required = true)
	protected String state;

	/**
	 * Gets the value of the activityId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getActivityId() {
		return activityId;
	}

	/**
	 * Sets the value of the activityId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setActivityId(String value) {
		this.activityId = value;
	}

	/**
	 * Gets the value of the activityName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getActivityName() {
		return activityName;
	}

	/**
	 * Sets the value of the activityName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setActivityName(String value) {
		this.activityName = value;
	}

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
	 * Gets the value of the parentInstanceId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getParentInstanceId() {
		return parentInstanceId;
	}

	/**
	 * Sets the value of the parentInstanceId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setParentInstanceId(String value) {
		this.parentInstanceId = value;
	}

	/**
	 * Gets the value of the state property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the value of the state property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setState(String value) {
		this.state = value;
	}

}
