package com.tibco.www.bx._2009.management.processManagerType;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="processQName" type="{http://www.tibco.com/bx/2009/management/processManagerType}QualifiedProcessName"/>
 *         &lt;element name="operationName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="parameterMap">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="parameter" type="{http://www.tibco.com/bx/2009/management/processManagerType}NameValuePair" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "processQName", "operationName", "parameterMap" })
@XmlRootElement(name = "createProcessInstanceInput")
public class CreateProcessInstanceInput {

	@XmlElement(required = true)
	protected QualifiedProcessName processQName;
	@XmlElement(required = true)
	protected String operationName;
	@XmlElement(required = true)
	protected CreateProcessInstanceInput.ParameterMap parameterMap;

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
	 * Gets the value of the parameterMap property.
	 * 
	 * @return possible object is
	 *         {@link CreateProcessInstanceInput.ParameterMap }
	 * 
	 */
	public CreateProcessInstanceInput.ParameterMap getParameterMap() {
		return parameterMap;
	}

	/**
	 * Sets the value of the parameterMap property.
	 * 
	 * @param value
	 *            allowed object is
	 *            {@link CreateProcessInstanceInput.ParameterMap }
	 * 
	 */
	public void setParameterMap(CreateProcessInstanceInput.ParameterMap value) {
		this.parameterMap = value;
	}

	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained
	 * within this class.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="parameter" type="{http://www.tibco.com/bx/2009/management/processManagerType}NameValuePair" maxOccurs="unbounded" minOccurs="0"/>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "parameter" })
	public static class ParameterMap {

		protected List<NameValuePair> parameter;

		/**
		 * Gets the value of the parameter property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a
		 * snapshot. Therefore any modification you make to the returned list
		 * will be present inside the JAXB object. This is why there is not a
		 * <CODE>set</CODE> method for the parameter property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getParameter().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list
		 * {@link NameValuePair }
		 * 
		 * 
		 */
		public List<NameValuePair> getParameter() {
			if (parameter == null) {
				parameter = new ArrayList<NameValuePair>();
			}
			return this.parameter;
		}

	}

}
