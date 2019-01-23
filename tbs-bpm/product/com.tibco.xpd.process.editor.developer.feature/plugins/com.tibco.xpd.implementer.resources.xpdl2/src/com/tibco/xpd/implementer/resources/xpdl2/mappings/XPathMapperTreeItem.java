/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.mappings;

import java.util.StringTokenizer;

import com.tibco.xpd.implementer.script.IWsdlPath;
import com.tibco.xpd.implementer.script.WsdlXsdRootPath;
import com.tibco.xpd.processeditor.xpdl2.properties.util.MapperTreeItem;

/**
 * 
 * 
 * @author rsomayaj
 * @since 3.3 (16 Jun 2010)
 */
public class XPathMapperTreeItem extends MapperTreeItem {

    private final IWsdlPath wsdlPath;

    private final Object contextObject;

    /**
     * @param contextObject
     * @param mappingObject
     */
    public XPathMapperTreeItem(IWsdlPath mappingObject) {
        this(null, mappingObject);
    }

    /**
     * @param contextObject
     * @param wsdlPath
     */
    public XPathMapperTreeItem(Object contextObject, IWsdlPath wsdlPath) {
        super(wsdlPath);
        this.wsdlPath = wsdlPath;
        this.contextObject = contextObject;

    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.MapperTreeItem#getParent()
     * 
     * @return
     */
    @Override
    public MapperTreeItem getParent() {
        if (wsdlPath.getParent() != null) {
            return new XPathMapperTreeItem(wsdlPath.getParent());
        } else if (wsdlPath.getParent() == null
                && wsdlPath instanceof WsdlXsdRootPath) {
            return new XPathMapperTreeItem(((WsdlXsdRootPath) wsdlPath)
                    .getWsdlPartPath());

        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.MapperTreeItem#getNormalizedLabel()
     * 
     * @return
     */
    @Override
    protected String getNormalizedLabel() {
        String descriptivePath = wsdlPath.getJavaScriptPath();
        String lastToken = getLastToken(descriptivePath);
        return getAttrName(lastToken);
    }

    /**
     * @param tgtLastToken
     * @return
     */
    private String getAttrName(String tgtLastToken) {
        StringBuilder strBuilder = new StringBuilder(tgtLastToken);
        if (strBuilder.charAt(0) == '@') {
            // Avoiding the first character @
            strBuilder.deleteCharAt(0);
        }
        if (strBuilder.indexOf("[") != -1) { //$NON-NLS-1$
            // Not interested with the array delimiters
            strBuilder.delete(strBuilder.indexOf("["), //$NON-NLS-1$
                    strBuilder.indexOf("]") + 1); //$NON-NLS-1$
        }
        return strBuilder.toString();
    }

    /**
     * @param targetStringTokenizer
     * @return
     */
    private String getLastToken(String path) {
        StringTokenizer stringTokenizer = new StringTokenizer(path, "."); //$NON-NLS-1$
        String lastToken = null;
        do {
            lastToken = stringTokenizer.nextToken();
        } while (stringTokenizer.hasMoreTokens());

        return lastToken;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.MapperTreeItem#getType()
     * 
     * @return
     */
    @Override
    public Object getType() {
        return wsdlPath.getType();
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        return getNormalizedLabel();
    }
}
