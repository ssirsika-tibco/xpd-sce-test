/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import java.util.Set;

import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDConcreteComponent;

import com.tibco.xpd.xpdl2.WebServiceOperation;

/**
 * @author nwilson
 */
public class WsdlXsdRootPath extends XsdPath {
    /** WSDL Part path. */
    private WsdlPartPath partPath;

    /**
     * @param partPath The WSDL Part path.
     * @param component The component.
     */
    public WsdlXsdRootPath(WsdlPartPath partPath, XSDConcreteComponent component) {
        super(partPath, component);
        this.partPath = partPath;
        setInput(partPath.isInput());
        setOutput(partPath.isOutput());
    }

    /**
     * @return The WSDL Part path.
     */
    public WsdlPartPath getWsdlPartPath() {
        return partPath;
    }

    /**
     * @return The path of this item
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.XsdPath#
     *      getPath()
     */
    @Override
    public String getPath() {
        StringBuffer path = new StringBuffer();
        if (partPath != null) {
            path.append(partPath.getPath());
            path.append('/');
        }
        path.append(getName());
        return path.toString();
    }
    
    public String getIndexedPath() {
        StringBuffer path = new StringBuffer();
        if (partPath != null) {
            path.append(partPath.getIndexedPath());
            path.append('/');
        }
        path.append(getIndexedName());
        return path.toString();
    }

    /**
     * @return The XPath defining the location of the item.
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.IWsdlPath
     *      #getXPath()
     */
    public String getXPath(Set<IWsdlPath> all) {
        return partPath.getXPath(all) + super.getXPath(all);
    }

    /**
     * @return The XPath defining the location of the item.
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.IWsdlPath
     *      #getXPath()
     */
    public String getDescriptivePath() {
        return partPath.getDescriptivePath() + super.getDescriptivePath();
    }

    /**
     * @return The XPath defining the location of the item.
     */
    public String getJavaScriptPath() {
        return partPath.getJavaScriptPath() + super.getJavaScriptPath();
    }

    /**
     * @return The web service operation.
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.XsdPath#
     *      getWebServiceOperation()
     */
    public WebServiceOperation getWebServiceOperation() {
        return partPath.getWebServiceOperation();
    }

    /**
     * @return The part.
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.XsdPath#getPart()
     */
    @Override
    public Part getPart() {
        return partPath.getPart();
    }

    /**
     * @return A copy of the XsdPath with array index of -1.
     */
    public XsdPath getCopy() {
        XsdPath copy = new WsdlXsdRootPath(partPath, getComponent());
        copy.setIndex(getIndex());
        return copy;
    }
}
