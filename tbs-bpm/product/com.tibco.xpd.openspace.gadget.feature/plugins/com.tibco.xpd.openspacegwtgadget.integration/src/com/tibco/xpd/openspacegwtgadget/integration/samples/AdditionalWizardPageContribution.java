/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.samples;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.xpd.openspacegwtgadget.integration.OpenspaceGadgetPlugin;
import com.tibco.xpd.openspacegwtgadget.integration.api.AbstractOpenspaceGadgetWizardPage;

/**
 * 
 * 
 * @author aallway
 * @since 22 Feb 2013
 */
public class AdditionalWizardPageContribution {

    private IConfigurationElement configurationElement;

    private String title;

    private String description;

    private ImageDescriptor icon = null;

    public AdditionalWizardPageContribution(
            IConfigurationElement configurationElement) {
        this.configurationElement = configurationElement;

        title = configurationElement.getAttribute("title");
        if (title == null) {
            title = ""; //$NON-NLS-1$
        }

        description = configurationElement.getAttribute("description");
        if (description == null) {
            description = ""; //$NON-NLS-1$
        }

        String imgPath = configurationElement.getAttribute("icon"); //$NON-NLS-1$
        if (imgPath != null && imgPath.length() > 0) {
            icon =
                    OpenspaceGadgetPlugin
                            .imageDescriptorFromPlugin(configurationElement
                                    .getContributor().getName(), imgPath);
        }

    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the icon
     */
    public ImageDescriptor getIcon() {
        return icon;
    }

    /**
     * @return The additional wizard page class.
     * 
     * @throws CoreException
     *             If bad class contribution.
     */
    public AbstractOpenspaceGadgetWizardPage getWizardPage()
            throws CoreException {
        try {
            Object page =
                    configurationElement.createExecutableExtension("class"); //$NON-NLS-1$

            if (!(page instanceof AbstractOpenspaceGadgetWizardPage)) {
                throw new CoreException(
                        new Status(
                                IStatus.ERROR,
                                OpenspaceGadgetPlugin.PLUGIN_ID,
                                String.format("Openspace Sample Gadget contribution (by %s) has AdditionalWizardPage of incorrect class type.", //$NON-NLS-1$
                                        configurationElement.getContributor()
                                                .getName())));
            }

            AbstractOpenspaceGadgetWizardPage wizardPage =
                    (AbstractOpenspaceGadgetWizardPage) page;
            wizardPage.setTitle(getTitle());
            wizardPage.setDescription(getDescription());
            wizardPage.setImageDescriptor(getIcon());

            return wizardPage;

        } catch (CoreException e) {
            throw new CoreException(
                    new Status(
                            IStatus.ERROR,
                            OpenspaceGadgetPlugin.PLUGIN_ID,
                            String.format("Openspace Sample Gadget contribution (by %s) has AdditionalWizardPage which can't be loaded.", //$NON-NLS-1$
                                    configurationElement.getContributor()
                                            .getName()), e));
        }
    }

}
