/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.NamedElement;

import com.tibco.xpd.resources.wc.gmf.XpdResourceFactory;

/**
 * BOM resource factory. This extends the <code>XpdResourceFactory</code> to
 * provide implementation for the uri fragment query that will store the name of
 * each referenced <code>EObject</code>.
 * 
 * @author njpatel
 */
public class BOMResourceFactory extends XpdResourceFactory {

    @Override
    public String getURIFragmentQuery(EObject object) {
        String query = null;

        if (object instanceof NamedElement) {
            NamedElement elem = (NamedElement) object;

            query = elem.getQualifiedName();

            if (query == null) {
                // No qualified name found so return name
                query = elem.getName();
            }
        }

        return query;
    }
}
