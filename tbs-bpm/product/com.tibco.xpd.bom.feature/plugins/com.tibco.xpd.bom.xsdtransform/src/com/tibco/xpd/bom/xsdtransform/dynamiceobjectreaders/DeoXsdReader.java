/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.dynamiceobjectreaders;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;

/**
 * Abstract base class for XML Schema construct Reader classes.
 * 
 * @author rgreen
 * @since 23 Aug 2012
 */
public abstract class DeoXsdReader {

    private DynamicEObjectImpl deo;

    DeoXsdReader(DynamicEObjectImpl deo) {
        this.deo = deo;
    }

    protected Object getFeature(String featureName) {
        for (EStructuralFeature feat : deo.eClass().getEStructuralFeatures()) {
            String name = feat.getName();
            if (name.equals(featureName)) { //$NON-NLS-1$
                Object value = deo.eGet(feat);
                return value;
            }
        }
        return null;
    }

    /**
     * @return the deo
     */
    protected DynamicEObjectImpl getDeo() {
        return deo;
    }

}
