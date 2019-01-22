package com.tibco.www.bx._2010.debugging.debuggerType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for NodeDefinition complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="NodeDefinition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="activityId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="activityName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="activityType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="parentActivityId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="canStepInto" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="canStepOut" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="sourceIds" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="targetIds" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NodeDefinition", propOrder = { "activityId", "activityName", "activityType", "parentActivityId", "canStepInto", "canStepOut",
		"sourceIds", "targetIds" })
public class NodeDefinition {

	@XmlElement(required = true)
	protected String activityId;
	@XmlElement(required = true)
	protected String activityName;
	@XmlElement(required = true)
	protected String activityType;
	@XmlElement(required = true)
	protected String parentActivityId;
	protected boolean canStepInto;
	protected boolean canStepOut;
	@XmlElement(required = true)
	protected String sourceIds;
	@XmlElement(required = true)
	protected String targetIds;

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
	 * Gets the value of the activityType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getActivityType() {
		return activityType;
	}

	/**
	 * Sets the value of the activityType property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setActivityType(String value) {
		this.activityType = value;
	}

	/**
	 * Gets the value of the parentActivityId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getParentActivityId() {
		return parentActivityId;
	}

	/**
	 * Sets the value of the parentActivityId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setParentActivityId(String value) {
		this.parentActivityId = value;
	}

	/**
	 * Gets the value of the canStepInto property.
	 * 
	 */
	public boolean isCanStepInto() {
		return canStepInto;
	}

	/**
	 * Sets the value of the canStepInto property.
	 * 
	 */
	public void setCanStepInto(boolean value) {
		this.canStepInto = value;
	}

	/**
	 * Gets the value of the canStepOut property.
	 * 
	 */
	public boolean isCanStepOut() {
		return canStepOut;
	}

	/**
	 * Sets the value of the canStepOut property.
	 * 
	 */
	public void setCanStepOut(boolean value) {
		this.canStepOut = value;
	}

	/**
	 * Gets the value of the sourceIds property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSourceIds() {
		return sourceIds;
	}

	/**
	 * Sets the value of the sourceIds property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSourceIds(String value) {
		this.sourceIds = value;
	}

	/**
	 * Gets the value of the targetIds property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTargetIds() {
		return targetIds;
	}

	/**
	 * Sets the value of the targetIds property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTargetIds(String value) {
		this.targetIds = value;
	}

}
