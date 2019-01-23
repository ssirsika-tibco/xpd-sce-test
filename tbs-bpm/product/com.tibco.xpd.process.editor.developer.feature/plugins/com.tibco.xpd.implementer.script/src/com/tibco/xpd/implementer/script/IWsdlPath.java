/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDTypeDefinition;

import com.tibco.xpd.xpdl2.WebServiceOperation;

/**
 * <p>
 * Identifier inteface for WSDL path classes. A WSDL path is a string specifying
 * an exact location in a WSDL definition file message. The format of the string
 * is defined below:
 * </p>
 * <p>
 * The WSDL path string is composed of a number of segments, each separated by a
 * '/' character. The first section defines the name of the web service
 * operation, the second defines the message <i>Part</i>. Those are the only
 * two mandatory segments and any futher segments define a specfic point within
 * an XSD schema that constitutes the <i>Part</i>.
 * </p>
 * <p>
 * The first segment is the web service operation name prefixed by the string
 * "wso:", e.g.
 * <p>
 * 
 * <pre>
 * wso:addNumbers
 * </pre>
 * 
 * <p>
 * The second segment is the part name prefixed by the string "part:", e.g.
 * <p>
 * 
 * <pre>
 * wso:addNumbers/part:parameters
 * </pre>
 * 
 * <p>
 * If the type of the Part is defined by an XSD schema, then further segments
 * can specify an exact location in that schema. The following example defines
 * an element "firstNumber" within the Part "parameters":
 * <p>
 * 
 * <pre>
 * wso:addNumbers/part:parameters/firstNumber
 * </pre>
 * 
 * <p>
 * The path string also handles choices and sequences, representing them with
 * the segments "group:choice" and "group:sequence". It may also be necessary to
 * specify which choice, sequence or element we are referring to as there can be
 * more than one choice, sequence or element of the same name under a single
 * parent. This is done by giving the segment a zero based index number
 * corresponding to its position under the parent specified in square brackets,
 * e.g.
 * </p>
 * 
 * <pre>
 * wso:addNumbers/part:parameters/group:choice[1]/group:sequence[2]/firstNumber[0]
 * </pre>
 * 
 * <p>
 * In addition to having an index number under the parent, elements that have
 * "maxOccurs" greater than one also need an "array index" to show exactly which
 * instance of an element the path points to. This is specified in curly
 * brackets after the parent index, e.g.<p/>
 * 
 * <pre>
 * wso:addNumbers/part:parameters/group:choice[1]/group:sequence[2]/firstNumber[0]{2}
 * </pre>
 * 
 * <p>Paths to attributes are similar to paths to elements except that they
 * don't need any parent or array index and, like XPath, are prefixed with a
 * '@' character, e.g.</p>
 * 
 * <pre>
 * wso:addNumbers/part:parameters/@attribute
 * </pre>
 * 
 * @author nwilson
 */
public interface IWsdlPath extends Comparable<IWsdlPath> {
	/**
     * @return The path string defining this WSDL path.
     */
    String getPath();
    
    /**
     * @return The path string defining this WSDL path with array indexes including the 0.
     */
    String getIndexedPath();
    
    /**
     * @param all All of the existing paths for the WSDL.
     * @return The XPath describing the location of this WsdlPath.
     */
    String getXPath(Set<IWsdlPath> all);

    /**
     * @return The JavaScript describing the location of this WsdlPath.
     */
    String getJavaScriptPath();

    /**
     * @return The descriptive path.
     */
    String getDescriptivePath();
    
    /**
     * @return The WSO that is the root of this path.
     */
    WebServiceOperation getWebServiceOperation();

	/**
	 * @return A list of children.
	 */
	List<IWsdlPath> getChildList();

    /**
     * @return The parent IWsdlPath.
     */
    IWsdlPath getParent();

	/**
	 * @return true if this path has children.
	 */
	boolean hasChildren();
	
    /**
     * @return true if this is an input path.
     */
	boolean isInput();
	
	/**
	 * @return true if this is an output path.
	 */
	boolean isOutput();

    /**
     * @return The Part at the root of this path.
     */
    Part getPart();

    /**
     * @return Map of prefixes to URIs.
     */
    Map<String, String> getNamespaceUris();

    /**
     * @return The type definition for this component.
     */
    XSDTypeDefinition getType();

    /**
     * @return An array of namespace URIs.
     */
    String[] getNamespaceUriArray();

    /**
     * @return true if this path is an array type.
     */
    boolean isArray();

    /**
     * @return true if there are any optional components on the path.
     */
    boolean isOptional();
}
