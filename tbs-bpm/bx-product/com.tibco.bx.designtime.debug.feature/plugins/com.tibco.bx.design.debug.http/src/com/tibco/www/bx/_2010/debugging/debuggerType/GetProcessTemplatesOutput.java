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
 *         &lt;element name="processTemplates" type="{http://www.tibco.com/bx/2010/debugging/debuggerType}ProcessTemplates"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "processTemplates" })
@XmlRootElement(name = "getProcessTemplatesOutput")
public class GetProcessTemplatesOutput {

	@XmlElement(required = true)
	protected ProcessTemplates processTemplates;

	/**
	 * Gets the value of the processTemplates property.
	 * 
	 * @return possible object is {@link ProcessTemplates }
	 * 
	 */
	public ProcessTemplates getProcessTemplates() {
		return processTemplates;
	}

	/**
	 * Sets the value of the processTemplates property.
	 * 
	 * @param value
	 *            allowed object is {@link ProcessTemplates }
	 * 
	 */
	public void setProcessTemplates(ProcessTemplates value) {
		this.processTemplates = value;
	}

}
