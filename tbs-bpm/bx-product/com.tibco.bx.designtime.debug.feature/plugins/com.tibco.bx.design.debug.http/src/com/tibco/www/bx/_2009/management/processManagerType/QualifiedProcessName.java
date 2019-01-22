package com.tibco.www.bx._2009.management.processManagerType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Type to describe the fully qualified name of a process template.
 * 
 * <p>
 * Java class for QualifiedProcessName complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="QualifiedProcessName">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="moduleName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="processName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QualifiedProcessName", propOrder = { "moduleName", "processName", "version" })
public class QualifiedProcessName {

	@XmlElement(required = true)
	protected String moduleName;
	@XmlElement(required = true)
	protected String processName;
	@XmlElement(required = true, nillable = true)
	protected String version;

	/**
	 * Gets the value of the moduleName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * Sets the value of the moduleName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setModuleName(String value) {
		this.moduleName = value;
	}

	/**
	 * Gets the value of the processName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getProcessName() {
		return processName;
	}

	/**
	 * Sets the value of the processName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setProcessName(String value) {
		this.processName = value;
	}

	/**
	 * Gets the value of the version property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the value of the version property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setVersion(String value) {
		this.version = value;
	}

}
