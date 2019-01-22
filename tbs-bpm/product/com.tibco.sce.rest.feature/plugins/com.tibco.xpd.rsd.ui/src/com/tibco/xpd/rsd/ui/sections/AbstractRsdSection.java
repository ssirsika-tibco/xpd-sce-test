/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.sections;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.ui.properties.AbstractTransactionalSection;

/**
 * Abstract class used for for all RSD general sections.
 * 
 * @author jarciuch
 * @since 4 Feb 2015
 */
public abstract class AbstractRsdSection extends AbstractTransactionalSection {

    /**
     * Sets default height and no extra space to be used.
     */
    public AbstractRsdSection() {
        setMinimumHeight(SWT.DEFAULT);
        setShouldUseExtraSpace(false);
    }

    /**
     * Helper method if control is relevant in refresh call.
     * 
     * @param c
     *            the control to check.
     * @return 'true' if control is valid.
     */
    protected boolean isValidControl(Control c) {
        return c != null && !c.isDisposed();
    }

    /**
     * Returns a default layout factory for the group.
     */
    protected GridLayoutFactory getGroupControlLayoutFactory() {
        /*
         * The margins are used to avoid cut top/bottom/right sides of controls
         * (especially visible in combos)
         */
        return GridLayoutFactory.fillDefaults().margins(1, 1);
    }

}
