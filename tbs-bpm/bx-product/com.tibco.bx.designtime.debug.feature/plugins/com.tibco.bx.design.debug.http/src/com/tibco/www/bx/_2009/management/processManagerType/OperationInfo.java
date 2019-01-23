package com.tibco.www.bx._2009.management.processManagerType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Type to describe a starter operation.
 * 
 * <p>
 * Java class for OperationInfo complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="OperationInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="operationName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="parameters" type="{http://www.tibco.com/bx/2009/management/processManagerType}TemplateAttributes"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperationInfo", propOrder = { "operationName", "parameters" })
public class OperationInfo {

	@XmlElement(required = true)
	protected String operationName;
	@XmlElement(required = true, nillable = true)
	protected TemplateAttributes parameters;

	/**
	 * Gets the value of the operationName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOperationName() {
		return operationName;
	}

	/**
	 * Sets the value of the operationName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setOperationName(String value) {
		this.operationName = value;
	}

	/**
	 * Gets the value of the parameters property.
	 * 
	 * @return possible object is {@link TemplateAttributes }
	 * 
	 */
	public TemplateAttributes getParameters() {
		return parameters;
	}

	/**
	 * Sets the value of the parameters property.
	 * 
	 * @param value
	 *            allowed object is {@link TemplateAttributes }
	 * 
	 */
	public void setParameters(TemplateAttributes value) {
		this.parameters = value;
	}

}
