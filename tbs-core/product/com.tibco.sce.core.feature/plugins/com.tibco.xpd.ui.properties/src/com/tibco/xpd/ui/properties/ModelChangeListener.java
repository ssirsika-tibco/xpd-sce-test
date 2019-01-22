/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import org.eclipse.emf.edit.provider.INotifyChangedListener;

/**
 * Extension to the <code>INotifyChangeListener</code> to allow for ignoring of
 * model events.
 */
public interface ModelChangeListener extends INotifyChangedListener {

    /**
     * If set to <code>true</code> all subsequent model events will be ignored
     * until this is set back to <code>false</code>.
     * 
     * @param ignore
     *            <code>true</code> to ignore model events.
     */
    void ignoreModelEvents(boolean ignore);

}
