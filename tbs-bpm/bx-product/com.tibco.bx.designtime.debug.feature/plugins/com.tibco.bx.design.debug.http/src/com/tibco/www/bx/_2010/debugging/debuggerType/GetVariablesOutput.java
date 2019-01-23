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
 *         &lt;element name="variables" type="{http://www.tibco.com/bx/2010/debugging/debuggerType}Variables"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "variables" })
@XmlRootElement(name = "getVariablesOutput")
public class GetVariablesOutput {

	@XmlElement(required = true)
	protected Variables variables;

	/**
	 * Gets the value of the variables property.
	 * 
	 * @return possible object is {@link Variables }
	 * 
	 */
	public Variables getVariables() {
		return variables;
	}

	/**
	 * Sets the value of the variables property.
	 * 
	 * @param value
	 *            allowed object is {@link Variables }
	 * 
	 */
	public void setVariables(Variables value) {
		this.variables = value;
	}

}
