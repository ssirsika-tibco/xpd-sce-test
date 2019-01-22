package com.tibco.www.bx._2009.management.processManagerType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Type to describe a starter operation.
 * 
 * A starter operation is a way for an external (to Process Manager) application
 * to create a process instance from a process template that has one and only
 * one starter event, which must be a none starter event.
 * 
 * <p>
 * Java class for StarterOperation complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="StarterOperation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="processQName" type="{http://www.tibco.com/bx/2009/management/processManagerType}QualifiedProcessName"/>
 *         &lt;element name="operation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StarterOperation", propOrder = { "processQName", "operation" })
public class StarterOperation {

	@XmlElement(required = true)
	protected QualifiedProcessName processQName;
	@XmlElement(required = true)
	protected String operation;

	/**
	 * Gets the value of the processQName property.
	 * 
	 * @return possible object is {@link QualifiedProcessName }
	 * 
	 */
	public QualifiedProcessName getProcessQName() {
		return processQName;
	}

	/**
	 * Sets the value of the processQName property.
	 * 
	 * @param value
	 *            allowed object is {@link QualifiedProcessName }
	 * 
	 */
	public void setProcessQName(QualifiedProcessName value) {
		this.processQName = value;
	}

	/**
	 * Gets the value of the operation property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * Sets the value of the operation property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setOperation(String value) {
		this.operation = value;
	}

}
