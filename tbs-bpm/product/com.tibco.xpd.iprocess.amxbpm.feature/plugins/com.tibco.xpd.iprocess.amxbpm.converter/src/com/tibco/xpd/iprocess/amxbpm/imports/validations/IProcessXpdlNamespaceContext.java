/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.imports.validations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;

/**
 * [Copied from IPM com.tibco.xpd.ipm plugin.] Namespace Context for IProcess
 * XPDL.
 * 
 * @author aprasad
 * 
 */
public class IProcessXpdlNamespaceContext implements NamespaceContext {

    private Map<String, String> prefixToUri;

    public IProcessXpdlNamespaceContext() {
        prefixToUri = new HashMap<String, String>();
        prefixToUri.put("xpdl", "http://www.wfmc.org/2002/XPDL1.0"); //$NON-NLS-1$ //$NON-NLS-2$
        prefixToUri.put("sw", "http://bpm.tibco.com/2004/SWSPDXML2.0"); //$NON-NLS-1$ //$NON-NLS-2$
        prefixToUri.put("eaijava", //$NON-NLS-1$
                "http://www.staffware.com/2003/EAIJavaPlugin"); //$NON-NLS-1$
        prefixToUri.put("eaibw", "http://www.staffware.com/2002/BWEAIPlugin"); //$NON-NLS-1$ //$NON-NLS-2$
        prefixToUri.put("eaiws", //$NON-NLS-1$
                "http://www.staffware.com/2002/WebServicesEAIPlugin"); //$NON-NLS-1$
    }

    /**
     * @see javax.xml.namespace.NamespaceContext#getNamespaceURI(java.lang.String)
     * 
     * @param prefix
     * @return
     */
    @Override
    public String getNamespaceURI(String prefix) {
        if (prefix == null) {
            prefix = "xpdl"; //$NON-NLS-1$
        }
        return prefixToUri.get(prefix);
    }

    /**
     * @see javax.xml.namespace.NamespaceContext#getPrefix(java.lang.String)
     * 
     * @param namespaceURI
     * @return
     */
    @Override
    public String getPrefix(String namespaceURI) {
        String prefix = null;
        Iterator<String> i = getPrefixes(namespaceURI);
        if (i.hasNext()) {
            prefix = i.next();
        }
        return prefix;
    }

    /**
     * @see javax.xml.namespace.NamespaceContext#getPrefixes(java.lang.String)
     * 
     * @param namespaceURI
     * @return
     */
    @Override
    public Iterator<String> getPrefixes(String namespaceURI) {
        List<String> prefixes = new ArrayList<String>();
        if (namespaceURI != null) {
            for (String prefix : prefixToUri.keySet()) {
                if (namespaceURI.equals(prefixToUri.get(prefix))) {
                    prefixes.add(prefix);
                }
            }
        }
        return prefixes.iterator();
    }

}
