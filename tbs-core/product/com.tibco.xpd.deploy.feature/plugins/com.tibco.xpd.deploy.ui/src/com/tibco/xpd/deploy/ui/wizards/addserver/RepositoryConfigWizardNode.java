/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.addserver;

import java.util.List;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.graphics.Point;

import com.tibco.xpd.deploy.ConfigParameterInfo;
import com.tibco.xpd.deploy.RepositoryType;

/**
 * The wizard node used for choosing repository wizard according to selected
 * repository type.
 * <p>
 * <i>Created: 8 Sep 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class RepositoryConfigWizardNode implements IWizardNode {

    private static final Point DEFAULT_EXTENT = new Point(-1, -1);
    private RepositoryConfigWizard wizard;
    private final String id;
    private final RepositoryType repoType;
    private final boolean isConfigComplete;
    private final IWizard parentWizard;

    /**
     * Creates wizard node instance.
     */
    public RepositoryConfigWizardNode(IWizard parentWizard,
            RepositoryType repoType) {
        this.parentWizard = parentWizard;
        this.repoType = repoType;
        id = repoType.getId();
        isConfigComplete = isRepositoryConfigInfoComplete(repoType);
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
            wizard = new RepositoryConfigWizard(parentWizard, repoType);
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

    public boolean canFinish() {
        if (!isContentCreated()) {
            return isConfigComplete;
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
        final RepositoryConfigWizardNode other = (RepositoryConfigWizardNode) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @SuppressWarnings("unchecked")
    private static boolean isRepositoryConfigInfoComplete(
            RepositoryType repoType) {
        List<ConfigParameterInfo> parameterInfos = repoType
                .getRepositoryParameterInfos();
        for (ConfigParameterInfo info : parameterInfos) {
            if (info.isRequired()) {
                String defaultValue = info.getDefaultValue();
                if (defaultValue == null || defaultValue.length() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean hasDiscoveredPages() {
        return ((RepositoryConfigWizard) getWizard()).getDiscoveredPages()
                .size() > 0;
    }
}
