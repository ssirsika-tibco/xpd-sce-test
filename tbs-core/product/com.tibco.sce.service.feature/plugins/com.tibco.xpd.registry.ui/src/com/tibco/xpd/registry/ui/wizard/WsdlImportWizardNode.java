/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.wizard;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.swt.graphics.Point;

/**
 * @author nwilson
 */
public class WsdlImportWizardNode implements IWizardNode {

    private boolean contentCreated;
    /** The wizard. */
    private final IWizard wizard;

    /**
     * @param wizard
     *            The wizard.
     */
    public WsdlImportWizardNode(IWizard wizard) {
        this.wizard = wizard;
    }

    /**
     * @see org.eclipse.jface.wizard.IWizardNode#dispose()
     */
    public void dispose() {
        wizard.dispose();
    }

    /**
     * @return The extent.
     * @see org.eclipse.jface.wizard.IWizardNode#getExtent()
     */
    public Point getExtent() {
        return new Point(-1, -1);
    }

    /**
     * @return The wizard.
     * @see org.eclipse.jface.wizard.IWizardNode#getWizard()
     */
    public IWizard getWizard() {
        return wizard;
    }

    /**
     * @return false.
     * @see org.eclipse.jface.wizard.IWizardNode#isContentCreated()
     */
    public boolean isContentCreated() {
        if (!contentCreated) {
            contentCreated = true;
            return false;
        }
        return contentCreated;
    }

}
