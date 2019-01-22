/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.bom.dbimport.wizards;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.swt.graphics.Point;

/**
 * Wizard Node to create a new Database
 * 
 * @author rsomayaj
 * 
 */
public class NewDBCategoryWizardNode implements IWizardNode {

    private static final Point DEFAULT_EXTENT = new Point(-1, -1);

    private XpdCPWizard wizard;

    /**
     * @see org.eclipse.jface.wizard.IWizardNode#dispose()
     * 
     */
    public void dispose() {
        if (wizard != null) {
            wizard.dispose();
        }
    }

    /**
     * @see org.eclipse.jface.wizard.IWizardNode#getExtent()
     * 
     * @return
     */
    public Point getExtent() {
        return DEFAULT_EXTENT;
    }

    /**
     * @see org.eclipse.jface.wizard.IWizardNode#getWizard()
     * 
     * @return
     */
    public IWizard getWizard() {
        if (wizard == null) {
            wizard = new XpdCPWizard();
        }
        return wizard;
    }

    /**
     * @see org.eclipse.jface.wizard.IWizardNode#isContentCreated()
     * 
     * @return
     */
    public boolean isContentCreated() {
        return wizard != null && wizard.getPageCount() > 0;
    }

}
