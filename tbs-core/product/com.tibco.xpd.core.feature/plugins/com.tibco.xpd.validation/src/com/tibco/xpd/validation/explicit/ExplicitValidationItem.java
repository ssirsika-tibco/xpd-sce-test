/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.explicit;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.validation.provider.IValidationItem;

/**
 * IValidationItem for explicit validation.
 *
 * @author nwilson
 */
public class ExplicitValidationItem implements IValidationItem {

    /** The working copy. */
    private WorkingCopy wc;
    
    /** The objects to validate. */
    private Collection <EObject> objects;
    
    /** True to affect markers. */
    private boolean affectMarkers;
    
    /** True to clean. */
    private boolean clean;
    
    /**
     * @param wc The working copy.
     */
    public ExplicitValidationItem(WorkingCopy wc) {
        this.wc = wc;
        objects = new ArrayList<EObject>();
        objects.add(wc.getRootElement());
        affectMarkers = false;
        clean = false;
    }
    
    /**
     * @return The working copy.
     * @see com.tibco.xpd.validation.provider.IValidationItem#getWorkingCopy()
     */
    public WorkingCopy getWorkingCopy() {
        return wc;
    }

    /**
     * @return false.
     * @see com.tibco.xpd.validation.provider.IValidationItem#affectMarkers()
     */
    public boolean affectMarkers() {
        return affectMarkers;
    }

    /**
     * @return false.
     * @see com.tibco.xpd.validation.provider.IValidationItem#getClean()
     */
    public boolean getClean() {
        return clean;
    }

    /**
     * @return The objects to validate.
     * @see com.tibco.xpd.validation.provider.IValidationItem#getObjects()
     */
    public Collection<EObject> getObjects() {
        return objects;
    }

    /**
     * @param affectMarkers the affectMarkers to set
     */
    protected void setAffectMarkers(boolean affectMarkers) {
        this.affectMarkers = affectMarkers;
    }

    /**
     * @param clean the clean to set
     */
    protected void setClean(boolean clean) {
        this.clean = clean;
    }

}
