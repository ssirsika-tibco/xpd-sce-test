/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.core.om.provider;

import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * This is a wrapper around EStructuralFeature providing label information which
 * can be used later to build commands/actions based on the feature.
 * <p>
 * <i>Created: 29 Jan 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class LabeledFeature {

    private final EStructuralFeature feature;
    private final String text;

    public LabeledFeature(EStructuralFeature feature, String text) {
        this.feature = feature;
        this.text = text;
    }

    /**
     * @return the feature
     */
    public EStructuralFeature getFeature() {
        return feature;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((feature == null) ? 0 : feature.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LabeledFeature other = (LabeledFeature) obj;
        if (feature == null) {
            if (other.feature != null)
                return false;
        } else if (!feature.equals(other.feature))
            return false;
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;
        return true;
    }
}
