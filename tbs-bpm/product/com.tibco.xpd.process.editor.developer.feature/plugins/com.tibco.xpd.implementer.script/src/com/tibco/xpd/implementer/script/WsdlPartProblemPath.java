/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.implementer.script;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDTypeDefinition;

import com.tibco.xpd.xpdl2.WebServiceOperation;

/**
 * An implementation of IWsdlPartPath that represents some kind of problem in
 * presenting the content of the WSDL operation message part content.
 * 
 * @author aallway
 * @since 1 Jun 2012
 */
public class WsdlPartProblemPath implements IWsdlPath {

    private String problemDescription;

    private boolean isInput;

    /**
     * @param problemDescription
     */
    public WsdlPartProblemPath(String problemDescription, boolean isInput) {
        this.problemDescription = problemDescription;
        this.isInput = isInput;
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     * 
     * @param o
     * @return
     */
    @Override
    public int compareTo(IWsdlPath o) {
        return this.getPath().compareTo(o.getPath());
    }

    /**
     * @see com.tibco.xpd.implementer.script.IWsdlPath#getPath()
     * 
     * @return
     */
    @Override
    public String getPath() {
        return problemDescription;
    }

    /**
     * @see com.tibco.xpd.implementer.script.IWsdlPath#getIndexedPath()
     * 
     * @return
     */
    @Override
    public String getIndexedPath() {
        return getPath();
    }

    /**
     * @see com.tibco.xpd.implementer.script.IWsdlPath#getXPath(java.util.Set)
     * 
     * @param all
     * @return
     */
    @Override
    public String getXPath(Set<IWsdlPath> all) {
        return ""; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.implementer.script.IWsdlPath#getJavaScriptPath()
     * 
     * @return
     */
    @Override
    public String getJavaScriptPath() {
        return ""; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.implementer.script.IWsdlPath#getDescriptivePath()
     * 
     * @return
     */
    @Override
    public String getDescriptivePath() {
        return getPath();
    }

    /**
     * @see com.tibco.xpd.implementer.script.IWsdlPath#getWebServiceOperation()
     * 
     * @return
     */
    @Override
    public WebServiceOperation getWebServiceOperation() {
        return null;
    }

    /**
     * @see com.tibco.xpd.implementer.script.IWsdlPath#getChildList()
     * 
     * @return
     */
    @Override
    public List<IWsdlPath> getChildList() {
        return Collections.emptyList();
    }

    /**
     * @see com.tibco.xpd.implementer.script.IWsdlPath#getParent()
     * 
     * @return
     */
    @Override
    public IWsdlPath getParent() {
        return null;
    }

    /**
     * @see com.tibco.xpd.implementer.script.IWsdlPath#hasChildren()
     * 
     * @return
     */
    @Override
    public boolean hasChildren() {
        return false;
    }

    /**
     * @see com.tibco.xpd.implementer.script.IWsdlPath#isInput()
     * 
     * @return
     */
    @Override
    public boolean isInput() {
        return isInput;
    }

    /**
     * @see com.tibco.xpd.implementer.script.IWsdlPath#isOutput()
     * 
     * @return
     */
    @Override
    public boolean isOutput() {
        return !isInput;
    }

    /**
     * @see com.tibco.xpd.implementer.script.IWsdlPath#getPart()
     * 
     * @return
     */
    @Override
    public Part getPart() {
        return null;
    }

    /**
     * @see com.tibco.xpd.implementer.script.IWsdlPath#getNamespaceUris()
     * 
     * @return
     */
    @Override
    public Map<String, String> getNamespaceUris() {
        return Collections.emptyMap();
    }

    /**
     * @see com.tibco.xpd.implementer.script.IWsdlPath#getType()
     * 
     * @return
     */
    @Override
    public XSDTypeDefinition getType() {
        return null;
    }

    /**
     * @see com.tibco.xpd.implementer.script.IWsdlPath#getNamespaceUriArray()
     * 
     * @return
     */
    @Override
    public String[] getNamespaceUriArray() {
        return new String[0];
    }

    /**
     * @see com.tibco.xpd.implementer.script.IWsdlPath#isArray()
     * 
     * @return
     */
    @Override
    public boolean isArray() {
        return false;
    }

    /**
     * @see com.tibco.xpd.implementer.script.IWsdlPath#isOptional()
     * 
     * @return
     */
    @Override
    public boolean isOptional() {
        return true;
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        return getPath();
    }

}
