/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.resources.projectconfig.projectassets;

import java.beans.PropertyChangeEvent;

/**
 * Implement this listener from a page contributed to the new-project wizard to
 * be informed of project configuration changes (such as name change).
 * 
 * @author aallway
 * @since 3.5.2
 * 
 */
public interface IAssetProjectPropertyChangeListener {
    public static final String PROJECTNAME_PROPERTY =
            "IAssetProjectPropertyChangeListener.projectname.property"; //$NON-NLS-1$

    void projectPropertyChanged(PropertyChangeEvent event);
}
