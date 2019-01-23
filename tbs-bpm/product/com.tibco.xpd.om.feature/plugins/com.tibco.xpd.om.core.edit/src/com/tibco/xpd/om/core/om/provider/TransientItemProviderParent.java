/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.core.om.provider;

import java.util.Collection;

/**
 * This interface should be implemented by parent item provider which will
 * contain TransientItemProvider(s) as children.
 * 
 * This interface is usually implemented by generated ItemProvider for the
 * target object in a role of parent. Because this item provider usually holds
 * the reference to the target object and transient providers it cannot be
 * shared singletone instance. Please make sure when you generating emf.edit
 * model to set the target item provider to be STATEFUL.
 * 
 * <p>
 * <i>Created: 20 Jan 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public interface TransientItemProviderParent {

    /**
     * Returns collection of transient children.
     * 
     * @param object
     *            the target object.
     * @return the collection of transient children.
     */
    Collection<TransientItemProvider> getTransientChildren(Object object);

    /**
     * Returns the transient item provider for the feature. The feature must be
     * one of the target object's features.
     * 
     * @param object
     *            the target object.
     * @param feature
     *            the feature of the target object's features.
     * @return the transient item provider serving as a parent to the children
     *         assigned to the feature.
     */
    TransientItemProvider getTransientParent(Object object, Object feature);
}
