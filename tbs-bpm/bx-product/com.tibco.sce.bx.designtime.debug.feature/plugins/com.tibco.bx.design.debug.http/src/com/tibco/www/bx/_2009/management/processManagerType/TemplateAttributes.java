package com.tibco.www.bx._2009.management.processManagerType;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Type to describe a list of template attributes.
 * 
 * <p>
 * Java class for TemplateAttributes complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="TemplateAttributes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="templateAttribute" type="{http://www.tibco.com/bx/2009/management/processManagerType}TemplateAttribute" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TemplateAttributes", propOrder = { "templateAttribute" })
public class TemplateAttributes {

	@XmlElement(nillable = true)
	protected List<TemplateAttribute> templateAttribute;

	/**
	 * Gets the value of the templateAttribute property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the templateAttribute property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getTemplateAttribute().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link TemplateAttribute }
	 * 
	 * 
	 */
	public List<TemplateAttribute> getTemplateAttribute() {
		if (templateAttribute == null) {
			templateAttribute = new ArrayList<TemplateAttribute>();
		}
		return this.templateAttribute;
	}

}
