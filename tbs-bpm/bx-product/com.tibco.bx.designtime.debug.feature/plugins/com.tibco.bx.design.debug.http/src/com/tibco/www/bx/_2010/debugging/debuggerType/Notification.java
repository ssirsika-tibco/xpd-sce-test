package com.tibco.www.bx._2010.debugging.debuggerType;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Notification complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Notification">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="data">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="eventAttribute" type="{http://www.tibco.com/bx/2010/debugging/debuggerType}NameValuePair" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="objectName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="seqNumber" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="timeStamp" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "Notification", propOrder = { "data", "message", "objectName", "seqNumber", "timeStamp", "type", "variables" })
public class Notification {

	@XmlElement(required = true)
	protected Notification.Data data;
	@XmlElement(required = true)
	protected String message;
	@XmlElement(required = true)
	protected String objectName;
	protected long seqNumber;
	protected long timeStamp;
	@XmlElement(required = true)
	protected String type;
	@XmlElement(required = true)
	protected Variables variables;

	/**
	 * Gets the value of the data property.
	 * 
	 * @return possible object is {@link Notification.Data }
	 * 
	 */
	public Notification.Data getData() {
		return data;
	}

	/**
	 * Sets the value of the data property.
	 * 
	 * @param value
	 *            allowed object is {@link Notification.Data }
	 * 
	 */
	public void setData(Notification.Data value) {
		this.data = value;
	}

	/**
	 * Gets the value of the message property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the value of the message property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMessage(String value) {
		this.message = value;
	}

	/**
	 * Gets the value of the objectName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getObjectName() {
		return objectName;
	}

	/**
	 * Sets the value of the objectName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setObjectName(String value) {
		this.objectName = value;
	}

	/**
	 * Gets the value of the seqNumber property.
	 * 
	 */
	public long getSeqNumber() {
		return seqNumber;
	}

	/**
	 * Sets the value of the seqNumber property.
	 * 
	 */
	public void setSeqNumber(long value) {
		this.seqNumber = value;
	}

	/**
	 * Gets the value of the timeStamp property.
	 * 
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Sets the value of the timeStamp property.
	 * 
	 */
	public void setTimeStamp(long value) {
		this.timeStamp = value;
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
	 *         &lt;element name="eventAttribute" type="{http://www.tibco.com/bx/2010/debugging/debuggerType}NameValuePair" maxOccurs="unbounded" minOccurs="0"/>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "eventAttribute" })
	public static class Data {

		protected List<NameValuePair> eventAttribute;

		/**
		 * Gets the value of the eventAttribute property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a
		 * snapshot. Therefore any modification you make to the returned list
		 * will be present inside the JAXB object. This is why there is not a
		 * <CODE>set</CODE> method for the eventAttribute property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getEventAttribute().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list
		 * {@link NameValuePair }
		 * 
		 * 
		 */
		public List<NameValuePair> getEventAttribute() {
			if (eventAttribute == null) {
				eventAttribute = new ArrayList<NameValuePair>();
			}
			return this.eventAttribute;
		}

	}

}
