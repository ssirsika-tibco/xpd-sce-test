/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.wst.wsdl.Fault;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDComponent;
import org.eclipse.xsd.XSDTypeDefinition;

import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdl2.WebServiceOperation;

/**
 * @author nwilson
 */
public class WsdlPartPath implements IWsdlPath {
    /** The path identifier for a web service operation. */
    public static final String WSO_ID = "wso:"; //$NON-NLS-1$

    /** The path identifier for a web service Part. */
    public static final String PART_ID = "part:"; //$NON-NLS-1$

    /** The path identifier for a web service Fault. */
    public static final String FAULT_ID = "fault:"; //$NON-NLS-1$

    /** The web service operation containing this path. */
    private WebServiceOperation operation;

    /** The WSDL part. */
    private Part part;

    /**
     * The WSDL Fault message (if this is not WSDL part from an operation
     * message)
     */
    private Fault fault;

    /** true if input. */
    private boolean isInput;

    /** true if output. */
    private boolean isOutput;

    private PortTypeOperation portTypeOperation;

    /**
     * @param operation
     *            The operation.
     * @param part
     *            The part.
     * @param isInput
     *            true if input.
     * @param isOutput
     *            true if output.
     */
    public WsdlPartPath(WebServiceOperation operation, Part part,
            boolean isInput, boolean isOutput) {
        this.operation = operation;
        this.part = part;
        this.isInput = isInput;
        this.isOutput = isOutput;
        this.fault = null;
    }

    public WsdlPartPath(WebServiceOperation operation, Fault fault, Part part) {
        this.operation = operation;
        this.part = part;
        this.fault = fault;
        this.isInput = false;
        this.isOutput = true;
    }

    /**
     * @param portTypeOperation
     * @param part2
     * @param in
     * @param isOutput2
     */
    public WsdlPartPath(PortTypeOperation portTypeOperation, Part part,
            boolean isInput, boolean isOutput) {
        this.part = part;
        this.isInput = isInput;
        this.isOutput = isOutput;
        this.fault = null;
        this.portTypeOperation = portTypeOperation;

    }

    /**
     * @return The part.
     */
    public Part getPart() {
        return part;
    }

    /**
     * @return The path of this part.
     */
    public String getPath() {

        StringBuffer path = new StringBuffer();
        if (operation != null) {

            if (part == null) {
                path.append(WSO_ID);
                path.append(operation.getOperationName());

                if (fault != null) {
                    path.append(FAULT_ID);
                    path.append(fault.getName());
                    path.append("/"); //$NON-NLS-1$
                }

            } else {
                path.append(WSO_ID);
                path.append(operation.getOperationName());
                path.append("/"); //$NON-NLS-1$

                if (fault != null) {
                    path.append(FAULT_ID);
                    path.append(fault.getName());
                    path.append("/"); //$NON-NLS-1$
                }

                path.append(PART_ID);
                if (part.getName() != null) {
                    path.append(part.getName());
                } else if (part.getElementName() != null) {
                    String prefix = part.getElementName().getPrefix();
                    if (prefix != null && prefix.length() != 0) {
                        path.append(prefix);
                        path.append(":"); //$NON-NLS-1$
                    }
                    path.append(part.getElementName().getLocalPart());
                }
            }

        } else if (portTypeOperation != null) {
            if (part == null) {
                path.append(WSO_ID);
                path.append(portTypeOperation.getOperationName());

                if (fault != null) {
                    path.append(FAULT_ID);
                    path.append(fault.getName());
                    path.append("/"); //$NON-NLS-1$
                }

            } else {
                path.append(WSO_ID);
                path.append(portTypeOperation.getOperationName());
                path.append("/"); //$NON-NLS-1$

                if (fault != null) {
                    path.append(FAULT_ID);
                    path.append(fault.getName());
                    path.append("/"); //$NON-NLS-1$
                }

                path.append(PART_ID);
                if (part.getName() != null) {
                    path.append(part.getName());
                } else if (part.getElementName() != null) {
                    String prefix = part.getElementName().getPrefix();
                    if (prefix != null && prefix.length() != 0) {
                        path.append(prefix);
                        path.append(":"); //$NON-NLS-1$
                    }
                    path.append(part.getElementName().getLocalPart());
                }
            }
        } else {

            if (fault != null) {
                path.append(FAULT_ID);
                path.append(fault.getName());
                path.append("/"); //$NON-NLS-1$
            }

            if (part != null) {
                path.append(PART_ID);
                if (part.getName() != null) {
                    path.append(part.getName());
                } else if (part.getElementName() != null) {
                    String prefix = part.getElementName().getPrefix();
                    if (prefix != null && prefix.length() != 0) {
                        path.append(prefix);
                        path.append(":"); //$NON-NLS-1$
                    }
                    path.append(part.getElementName().getLocalPart());
                }
            }

        }
        return path.toString();
    }
    
    public String getIndexedPath() {
        return getPath();
    }

    /**
     * @param all
     *            All of the existing paths for the WSDL.
     * @return The XPath defining the location of the item.
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.IWsdlPath
     *      #getXPath()
     */
    public String getXPath(Set<IWsdlPath> all) {
        String name;
        if (part.getElementName() == null) {
            name = part.getName();
        } else {
            name = part.getElementName().getLocalPart();
        }
        return name;
    }

    /**
     * @return The XPath defining the location of the item.
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.IWsdlPath
     *      #getXPath()
     */
    public String getDescriptivePath() {
        String name;
        if (part.getElementName() == null) {
            name = part.getName();
        } else {
            name = part.getElementName().getLocalPart();
        }
        return name;
    }

    /**
     * @return The XPath defining the location of the item.
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.IWsdlPath
     *      #getXPath()
     */
    public String getJavaScriptPath() {
        String name;
        if (part.getElementName() == null) {
            name = part.getName();
        } else {
            name = part.getElementName().getLocalPart();
        }
        return name;
    }

    /**
     * @return The web service operation.
     * @see com.tibco.xpd.implementer.script.IWsdlPath#getWebServiceOperation()
     */
    public WebServiceOperation getWebServiceOperation() {
        return operation;
    }

    /**
     * @return A list of children.
     * @see com.tibco.xpd.implementer.script.IWsdlPath#getChildList()
     */
    public List<IWsdlPath> getChildList() {
        List<IWsdlPath> children = new ArrayList<IWsdlPath>();
        XSDTypeDefinition type = WsdlUtil.getTypeDefinition(part);
        if (type != null) {
            List<XSDComponent> components = WsdlUtil.getTypeChildren(type);
            for (XSDComponent component : components) {
                children.add(new WsdlXsdRootPath(this, component));
            }
        }
        return children;
    }

    /**
     * @return true if there are children.
     * @see com.tibco.xpd.implementer.script.IWsdlPath#hasChildren()
     */
    public boolean hasChildren() {
        return !getChildList().isEmpty();
    }

    /**
     * @return null, no parent.
     * @see com.tibco.xpd.implementer.script.IWsdlPath#getParent()
     */
    public IWsdlPath getParent() {
        // This object is always at the top of the tree
        return null;
    }

    /**
     * @return the isInput.
     */
    public boolean isInput() {
        return isInput;
    }

    /**
     * @return the isOutput.
     */
    public boolean isOutput() {
        return isOutput;
    }

    /**
     * @param obj
     *            The object to compare.
     * @return true if they are equal, otherwise false.
     * @see java.lang.Object#equals(java.lang.Object)
     */

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj == this) {
            equal = true;
        } else if (obj instanceof WsdlPartPath) {
            WsdlPartPath other = (WsdlPartPath) obj;
            if (getPath().equals(other.getPath())) {
                equal = true;
            }
        }
        return equal;
    }

    /**
     * @return The path string hashcode.
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return getPath().hashCode();
    }

    /**
     * @return A map of prefixes to namespace URIs.
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.IWsdlPath
     *      #getNamespaceUris()
     */
    public Map<String, String> getNamespaceUris() {
        Map<String, String> uris = new HashMap<String, String>();
        if (part.getElementName() == null) {
            uris.put(part.getElement().getPrefix(), part.getElement()
                    .getNamespaceURI());
        } else {
            uris.put(part.getElementName().getPrefix(), part.getElementName()
                    .getNamespaceURI());
        }
        return uris;
    }

    /**
     * @param o
     *            The IWsdlPath to compare to.
     * @return 0.
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(IWsdlPath o) {
        if (o.equals(this)) {
            return 0;
        }
        return -1;
    }

    /**
     * @return The part type definition.
     * @see com.tibco.xpd.implementer.script.IWsdlPath#getType()
     */
    public XSDTypeDefinition getType() {
        return WsdlUtil.getTypeDefinition(part);
    }

    /**
     * @return A list of used namespace URIs.
     * @see com.tibco.xpd.implementer.script.IWsdlPath#getNamespaceUriArray()
     */
    public String[] getNamespaceUriArray() {
        String uri;
        if (part.getElementName() == null) {
            uri = part.getElement().getNamespaceURI();
        } else {
            uri = part.getElementName().getNamespaceURI();
        }
        return new String[] { uri };
    }

    /**
     * @return false.
     * @see com.tibco.xpd.implementer.script.IWsdlPath#isArray()
     */
    public boolean isArray() {
        return false;
    }

    /**
     * @return false
     * @see com.tibco.xpd.implementer.script.IWsdlPath#isOptional()
     */
    public boolean isOptional() {
        return false;
    }
}
