/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.dynamiceobjectreaders;

import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.util.FeatureMapUtil.FeatureEList;

/**
 * Reads feature values from a DynamicEObject representing an XSD Schema
 * construct
 * 
 * @author rgreen
 * @since 23 Aug 2012
 */
public class DeoXsdSchemaReader extends DeoXsdReader {

    private String targetNamespace;

    private FeatureEList<?> includes;

    private FeatureEList<?> complexTypes;

    public DeoXsdSchemaReader(DynamicEObjectImpl deo) {
        super(deo);
    }

    public String getTargetNamespace() {
        if (targetNamespace == null) {
            Object feature = getFeature("targetNamespace"); //$NON-NLS-1$
            if (feature instanceof String) {
                targetNamespace = (String) feature;
            }
        }
        return targetNamespace;
    }

    public FeatureEList<?> getIncludes() {
        if (includes == null) {
            Object feature = getFeature("include"); //$NON-NLS-1$
            if (feature instanceof FeatureEList<?>) {
                includes = (FeatureEList<?>) feature;
            }
        }
        return includes;
    }

    public FeatureEList<?> getComplexTypes() {
        if (complexTypes == null) {
            Object feature = getFeature("complexType"); //$NON-NLS-1$
            if (feature instanceof FeatureEList<?>) {
                complexTypes = (FeatureEList<?>) feature;
            }
        }
        return complexTypes;
    }

}
