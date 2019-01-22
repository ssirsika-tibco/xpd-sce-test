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
 *         &lt;element name="variableName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="variableValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "instanceId", "variableName", "variableValue" })
@XmlRootElement(name = "setVariableInput")
public class SetVariableInput {

	@XmlElement(required = true)
	protected String instanceId;
	@XmlElement(required = true)
	protected String variableName;
	@XmlElement(required = true)
	protected String variableValue;

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
	 * Gets the value of the variableName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getVariableName() {
		return variableName;
	}

	/**
	 * Sets the value of the variableName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setVariableName(String value) {
		this.variableName = value;
	}

	/**
	 * Gets the value of the variableValue property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getVariableValue() {
		return variableValue;
	}

	/**
	 * Sets the value of the variableValue property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setVariableValue(String value) {
		this.variableValue = value;
	}

}
