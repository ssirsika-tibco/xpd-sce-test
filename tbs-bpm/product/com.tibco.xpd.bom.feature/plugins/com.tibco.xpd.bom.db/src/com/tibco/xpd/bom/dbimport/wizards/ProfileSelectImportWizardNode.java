/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.bom.dbimport.wizards;

import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.swt.graphics.Point;

/**
 * Wizard node which provides a wizard that caters to importing tables from a
 * particular profile.
 * 
 * @author rsomayaj
 * 
 */
public class ProfileSelectImportWizardNode implements IWizardNode {

    private static final Point DEFAULT_EXTENT = new Point(-1, -1);

    private ProfileSelectImportWizard wizard;

    private IConnectionProfile connectionProfile;

    private final Object initialSelectedObject;

    /**
     * @param initialSelectedObject
     */
    public ProfileSelectImportWizardNode(Object initialSelectedObject) {
        this.initialSelectedObject = initialSelectedObject;

    }

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
            wizard = new ProfileSelectImportWizard(initialSelectedObject);
            wizard.setConnectionProfile(connectionProfile);
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

    public void setConnectionProfileObj(IConnectionProfile connectionProfile) {
        this.connectionProfile = connectionProfile;
        if (wizard != null) {
            wizard.setConnectionProfile(connectionProfile);
        }
    }

}
