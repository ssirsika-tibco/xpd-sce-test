/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * RCP application perspective.
 * 
 * @author njpatel
 * 
 */
public class Perspective implements IPerspectiveFactory {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui
     * .IPageLayout)
     */
    @Override
    public void createInitialLayout(IPageLayout layout) {
        /*
         * Sid XPD-8302 Perspective is now setup via extension point in plugin.xml
         */
    }
}
