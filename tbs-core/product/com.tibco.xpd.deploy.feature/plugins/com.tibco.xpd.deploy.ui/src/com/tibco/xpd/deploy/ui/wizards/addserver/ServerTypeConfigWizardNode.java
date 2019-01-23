/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.addserver;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.graphics.Point;

import com.tibco.xpd.deploy.ServerType;

/**
 * The wizard node used for choosing server type configurations according to
 * selected server type.
 * 
 * @author glewis
 * 
 */
public class ServerTypeConfigWizardNode implements IWizardNode {

    private static final Point DEFAULT_EXTENT = new Point(-1, -1);
    private ServerTypeConfigWizard wizard;
    private final String id;
    private final ServerType serverType;
    private final IWizard parentWizard;

    /**
     * Creates wizard node instance.
     */
    public ServerTypeConfigWizardNode(IWizard parentWizard,
            ServerType serverType) {
        this.parentWizard = parentWizard;
        this.serverType = serverType;
        id = serverType.getId();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizardNode#dispose()
     */
    public void dispose() {
        if (wizard != null) {
            wizard.dispose();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizardNode#getExtent()
     */
    public Point getExtent() {
        return DEFAULT_EXTENT;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizardNode#getWizard()
     */
    public IWizard getWizard() {
        if (wizard == null) {
            wizard = new ServerTypeConfigWizard(parentWizard, serverType);
        }
        return wizard;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizardNode#isContentCreated()
     */
    public boolean isContentCreated() {
        return wizard != null && wizard.getPageCount() > 0;
    }

    /**
     * @return
     */
    public boolean canFinish() {
        if (!isContentCreated()) {
            return false;
        } else {
            IWizardPage[] pages = wizard.getPages();
            for (int i = 0; i < pages.length; i++) {
                if (!pages[i].isPageComplete()) {
                    return false;
                }
            }
            return true;
        }

    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ServerTypeConfigWizardNode other = (ServerTypeConfigWizardNode) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public boolean hasDiscoveredPages() {
        return ((ServerTypeConfigWizard) getWizard()).getDiscoveredPages()
                .size() > 0;
    }
}
