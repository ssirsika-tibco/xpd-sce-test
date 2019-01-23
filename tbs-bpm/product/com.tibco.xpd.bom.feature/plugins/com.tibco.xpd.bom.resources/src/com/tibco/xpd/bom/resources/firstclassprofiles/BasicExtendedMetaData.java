/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.resources.firstclassprofiles;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Implementation of the {@link ExtendedMetaData}.
 * 
 * @author njpatel
 * 
 */
public class BasicExtendedMetaData implements ExtendedMetaData {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.bom.resources.firstclassprofiles.ExtendedMetaData#getName
     * (org.eclipse.uml2.uml.Stereotype)
     */
    public String getName(Stereotype stereotype) {
        return getValue(stereotype, NAME);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.bom.resources.firstclassprofiles.ExtendedMetaData#getType
     * (org.eclipse.uml2.uml.Stereotype)
     */
    public String getType(Stereotype stereotype) {
        return getValue(stereotype, TYPE);
    }

    /**
     * Get the value of the given extended metadata key from the stereotype.
     * 
     * @param stereotype
     * @param key
     * @return
     */
    private String getValue(Stereotype stereotype, String key) {
        if (stereotype != null && key != null) {
            EAnnotation annot = stereotype.getEAnnotation(URI);
            if (annot != null) {
                EMap<String, String> details = annot.getDetails();
                return details.get(key);
            }
        }
        return null;
    }

}
