package com.tibco.www.bx._2010.debugging.debuggerType;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for NodeDefinitions complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="NodeDefinitions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nodeDefinition" type="{http://www.tibco.com/bx/2010/debugging/debuggerType}NodeDefinition" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NodeDefinitions", propOrder = { "nodeDefinition" })
public class NodeDefinitions {

	protected List<NodeDefinition> nodeDefinition;

	/**
	 * Gets the value of the nodeDefinition property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the nodeDefinition property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getNodeDefinition().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link NodeDefinition }
	 * 
	 * 
	 */
	public List<NodeDefinition> getNodeDefinition() {
		if (nodeDefinition == null) {
			nodeDefinition = new ArrayList<NodeDefinition>();
		}
		return this.nodeDefinition;
	}

}
