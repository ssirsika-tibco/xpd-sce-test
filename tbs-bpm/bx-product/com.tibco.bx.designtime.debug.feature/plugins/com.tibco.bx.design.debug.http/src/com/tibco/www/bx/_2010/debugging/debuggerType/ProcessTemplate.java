package com.tibco.www.bx._2010.debugging.debuggerType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ProcessTemplate complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ProcessTemplate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="moduleName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="moduleVersion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="processId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="processName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessTemplate", propOrder = { "moduleName", "moduleVersion", "processId", "processName" })
public class ProcessTemplate {

	@XmlElement(required = true)
	protected String moduleName;
	@XmlElement(required = true)
	protected String moduleVersion;
	@XmlElement(required = true)
	protected String processId;
	@XmlElement(required = true)
	protected String processName;

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
	 * Gets the value of the moduleVersion property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getModuleVersion() {
		return moduleVersion;
	}

	/**
	 * Sets the value of the moduleVersion property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setModuleVersion(String value) {
		this.moduleVersion = value;
	}

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

}
