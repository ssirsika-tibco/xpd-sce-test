/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.dynamiceobjectreaders;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;

/**
 * Reads feature values from a DynamicEObject representing an XML Schema
 * ComplexType.
 * 
 * @author rgreen
 * @since 23 Aug 2012
 */
public class DeoXsdComplexTypeReader extends DeoXsdReader {

    private String name;

    public DeoXsdComplexTypeReader(DynamicEObjectImpl deo) {
        super(deo);
    }

    public String getName() {
        if (name == null) {
            Object feature = getFeature("name"); //$NON-NLS-1$
            if (feature instanceof String) {
                name = (String) feature;
            }
        }
        return name;
    }

    /**
     * @see com.tibco.xpd.bom.xsdtransform.dynamiceobjectreaders.DeoXsdReader#getFeature(java.lang.String)
     * 
     *      Overidden because we need to use getEAllStructuralFeatures() instead
     *      of getEStructuralFeatures
     * 
     * @param featureName
     * @return
     */
    @Override
    protected Object getFeature(String featureName) {
        for (EStructuralFeature feat : getDeo().eClass()
                .getEAllStructuralFeatures()) {
            String name = feat.getName();
            if (name.equals(featureName)) { //$NON-NLS-1$
                Object value = getDeo().eGet(feat);
                return value;
            }
        }
        return null;
    }
}
