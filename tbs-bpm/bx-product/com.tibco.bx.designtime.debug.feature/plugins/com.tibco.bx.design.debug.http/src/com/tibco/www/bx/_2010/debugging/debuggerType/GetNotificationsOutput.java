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
 *         &lt;element name="notifications" type="{http://www.tibco.com/bx/2010/debugging/debuggerType}Notifications"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "notifications" })
@XmlRootElement(name = "getNotificationsOutput")
public class GetNotificationsOutput {

	@XmlElement(required = true)
	protected Notifications notifications;

	/**
	 * Gets the value of the notifications property.
	 * 
	 * @return possible object is {@link Notifications }
	 * 
	 */
	public Notifications getNotifications() {
		return notifications;
	}

	/**
	 * Sets the value of the notifications property.
	 * 
	 * @param value
	 *            allowed object is {@link Notifications }
	 * 
	 */
	public void setNotifications(Notifications value) {
		this.notifications = value;
	}

}
