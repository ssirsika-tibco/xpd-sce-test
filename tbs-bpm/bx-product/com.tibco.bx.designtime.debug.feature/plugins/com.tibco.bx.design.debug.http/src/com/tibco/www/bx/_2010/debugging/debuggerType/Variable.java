package com.tibco.www.bx._2010.debugging.debuggerType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Variable complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Variable">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="namespace" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="variableId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="variableName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="parentInstanceId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Variable", propOrder = { "namespace", "type", "value", "variableId", "variableName", "parentInstanceId" })
public class Variable {

	@XmlElement(required = true)
	protected String namespace;
	@XmlElement(required = true)
	protected String type;
	@XmlElement(required = true)
	protected String value;
	@XmlElement(required = true)
	protected String variableId;
	@XmlElement(required = true)
	protected String variableName;
	@XmlElement(required = true)
	protected String parentInstanceId;

	/**
	 * Gets the value of the namespace property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * Sets the value of the namespace property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setNamespace(String value) {
		this.namespace = value;
	}

	/**
	 * Gets the value of the type property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the value of the type property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setType(String value) {
		this.type = value;
	}

	/**
	 * Gets the value of the value property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value of the value property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the value of the variableId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getVariableId() {
		return variableId;
	}

	/**
	 * Sets the value of the variableId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setVariableId(String value) {
		this.variableId = value;
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

}
