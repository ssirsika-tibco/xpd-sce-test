/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.systemActions;

import java.util.Collection;

/**
 * A system action component as defined in the
 * <code>com.tibco.xpd.om.resources.systemActions</code> extension.
 * 
 * @author njpatel
 * 
 */
public interface ISystemActionComponent extends
        Comparable<ISystemActionComponent> {

    /**
     * Unique id of this component.
     * 
     * @return id
     */
    String getId();

    /**
     * Human readable name of this component.
     * 
     * @return name
     */
    String getName();

    /**
     * Collection of {@link ISystemAction actions} that belong to this
     * component.
     * 
     * @return collection of actions.
     */
    Collection<ISystemAction> getActions();
}
