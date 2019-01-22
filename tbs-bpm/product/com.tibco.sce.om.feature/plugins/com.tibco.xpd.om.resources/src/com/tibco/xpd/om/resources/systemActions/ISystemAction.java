/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.systemActions;

import org.eclipse.emf.ecore.EObject;

/**
 * A system action as defined in the
 * <code>com.tibco.xpd.om.resources.systemActions</code> extension.
 * 
 * @author njpatel
 * 
 */
public interface ISystemAction {

    /**
     * Unique id of the system action.
     * 
     * @return id
     */
    String getId();

    /**
     * Human readable name of this system action
     * 
     * @return name
     */
    String getName();

    /**
     * Default value of the system action as defined in the extension.
     * 
     * @return default value (e.g., <code>true</code> or <code>false</code> if
     *         boolean)
     */
    String getDefaultValue();

    /**
     * Brief description of this system action.
     * 
     * @return description or <code>null</code> if none defined.
     */
    String getDescription();

    /**
     * The parent component this action belongs to.
     * 
     * @return {@link ISystemActionComponent}
     */
    ISystemActionComponent getComponent();

    /**
     * Check if this system action applies to the given Organization Model
     * element.
     * 
     * @param eo
     * @return <code>true</code> if system action applies, <code>false</code>
     *         otherwise.
     */
    boolean canApplyTo(EObject eo);
}
