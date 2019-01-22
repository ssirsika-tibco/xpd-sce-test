/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.ui.misc;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Replacement for ControlDecoration that removes the "update" behaviour to
 * prevent the entire shell being updated.
 * 
 * @author nwilson
 * @since 25 Sep 2014
 */
public class ControlDecorationSafe extends ControlDecoration {

    /**
     * @param control
     * @param position
     * @param composite
     */
    public ControlDecorationSafe(Control control, int position,
            Composite composite) {
        super(control, position, composite);
    }

    /**
     * @param control
     * @param position
     */
    public ControlDecorationSafe(Control control, int position) {
        super(control, position);
    }

    /**
     * Do nothing to prevent entire shell being refreshed.
     * 
     * @see org.eclipse.jface.fieldassist.ControlDecoration#update()
     * 
     */
    @Override
    protected void update() {
    }

}
