/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.provider;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.WorkingCopy;

/**
 * A validation item is passed to an {@link IScopeProvider scope provider} to
 * tell it what objects have changed.
 * <p>
 * (Since 3.1 an extended {@link IValidationItem2} is available that provides a
 * list of resource change {@link Notification}s that caused this validation
 * item to be created.)
 * </p>
 * 
 * @author nwilson
 */
public interface IValidationItem {

    /**
     * @return Returns the working copy.
     */
    WorkingCopy getWorkingCopy();

    /**
     * @return The validation objects.
     */
    Collection<EObject> getObjects();

    /**
     * @return true if markers should be updated.
     */
    boolean affectMarkers();

    /**
     * This method is only checked if affectMarkers is true.
     * 
     * @return true if markers should be cleaned.
     */
    boolean getClean();
}
