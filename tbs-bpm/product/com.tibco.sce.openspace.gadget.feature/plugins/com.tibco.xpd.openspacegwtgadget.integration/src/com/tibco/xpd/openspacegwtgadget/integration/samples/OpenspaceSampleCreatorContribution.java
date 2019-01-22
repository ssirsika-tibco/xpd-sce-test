/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.samples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.xpd.openspacegwtgadget.integration.OpenspaceGadgetPlugin;

/**
 * Class representing a contribution to the
 * com.tibco.xpd.openspacegwtgadget.integration.OpenspaceSampleCreator extension
 * point.
 * <p>
 * Also maintains a load on demand cache of these contributions accessible via
 * {@link #getOpenspaceSampleContributions()}.
 * 
 * @author aallway
 * @since 15 Jan 2013
 */
public class OpenspaceSampleCreatorContribution {

    /**
     * Load on demand cache of Openspace Sample Contributions.
     */
    private static List<OpenspaceSampleCreatorContribution> contributionCache =
            null;

    /**
     * The name of this individual sample.
     */
    private String sampleName;

    /** Menu item icon. */
    private ImageDescriptor menuIcon = null;

    /** The wizard icon. */
    private ImageDescriptor wizardIcon = null;

    /**
     * The sample files to copy/generate from source plugin into the target
     * package/project.
     */
    private List<OpenspaceSampleFileContribution> sampleFileContributions;

    /**
     * Optional additional wizard pages.
     */
    private List<AdditionalWizardPageContribution> additionalWizardPageContributions;

    /**
     * Class representing a contribution to the
     * com.tibco.xpd.openspacegwtgadget.integration.OpenspaceSampleCreator
     * extension point.
     */
    public OpenspaceSampleCreatorContribution(IConfigurationElement element)
            throws Exception {

        /* Sample name. */
        sampleName = element.getAttribute("name"); //$NON-NLS-1$

        if (sampleName == null || sampleName.length() == 0) {
            throw new Exception(
                    String.format("%s contributes Openspace Sample with no name", //$NON-NLS-1$
                            element.getContributor().getName()));
        }

        /* Menu item icon. */
        String imgPath = element.getAttribute("menuIcon"); //$NON-NLS-1$
        if (imgPath != null && imgPath.length() > 0) {
            menuIcon =
                    OpenspaceGadgetPlugin.imageDescriptorFromPlugin(element
                            .getContributor().getName(), imgPath);
        }

        /* Menu item icon. */
        imgPath = element.getAttribute("wizardIcon"); //$NON-NLS-1$
        if (imgPath != null && imgPath.length() > 0) {
            wizardIcon =
                    OpenspaceGadgetPlugin.imageDescriptorFromPlugin(element
                            .getContributor().getName(), imgPath);
        }

        /*
         * Create sample file descriptor for each sampleFile element
         */
        loadSampleFileDefinitions(element);

        /*
         * Load configurations for the additional wizard pages.
         */
        loadAdditionalWizardPageContributions(element);
    }

    /**
     * Load SampleFile elements for this individual contribution
     * 
     * @param element
     * @param sampleFiles
     * @throws Exception
     */
    private void loadSampleFileDefinitions(IConfigurationElement element)
            throws Exception {
        IConfigurationElement[] sampleFiles = element.getChildren("SampleFile"); //$NON-NLS-1$

        if (sampleFiles == null || sampleFiles.length == 0) {
            throw new Exception(
                    String.format("%s contributes Openspace Sample with no sample files (sample='%s')", //$NON-NLS-1$
                            element.getContributor().getName(),
                            sampleName));
        }

        sampleFileContributions =
                new ArrayList<OpenspaceSampleFileContribution>();

        for (IConfigurationElement sampleFile : sampleFiles) {
            sampleFileContributions.add(new OpenspaceSampleFileContribution(
                    sampleFile));
        }
    }

    /**
     * Load configurations for the additional wizard pages.
     * 
     * @param element
     */
    private void loadAdditionalWizardPageContributions(
            IConfigurationElement element) {
        additionalWizardPageContributions =
                new ArrayList<AdditionalWizardPageContribution>();

        IConfigurationElement[] configurationElements =
                element.getChildren("AdditionalWizardPage"); //$NON-NLS-1$

        if (configurationElements != null) {
            for (IConfigurationElement configurationElement : configurationElements) {
                additionalWizardPageContributions
                        .add(new AdditionalWizardPageContribution(
                                configurationElement));
            }
        }
    }

    /**
     * @return the sampleName
     */
    public String getSampleName() {
        return sampleName;
    }

    /**
     * @return the menu icon
     */
    public ImageDescriptor getMenuIcon() {
        return menuIcon;
    }

    /**
     * @return the wizard Icon
     */
    public ImageDescriptor getWizardIcon() {
        return wizardIcon;
    }

    /**
     * @return the sampleFileContributions
     */
    public List<OpenspaceSampleFileContribution> getSampleFileContributions() {
        return sampleFileContributions;
    }

    /**
     * Return list of contributed additional wizard pages
     * 
     * @return
     * @throws CoreException
     */
    public List<AdditionalWizardPageContribution> getAdditonalWizardPages()
            throws CoreException {

        return additionalWizardPageContributions;
    }

    /**
     * Get load on demand cached list of all the Openspace sample extension
     * point contributions
     * 
     * @return The openspace sample extension points contributions
     */
    public static Collection<OpenspaceSampleCreatorContribution> getOpenspaceSampleContributions() {
        if (contributionCache == null) {
            contributionCache =
                    new ArrayList<OpenspaceSampleCreatorContribution>();

            IExtensionPoint point =
                    Platform.getExtensionRegistry()
                            .getExtensionPoint(OpenspaceGadgetPlugin.PLUGIN_ID,
                                    "OpenspaceSampleCreator"); //$NON-NLS-1$

            if (point != null) {
                IConfigurationElement[] configurationElements =
                        point.getConfigurationElements();

                if (configurationElements != null) {
                    for (IConfigurationElement element : configurationElements) {

                        if ("SampleContribution".equals(element.getName())) { //$NON-NLS-1$

                            try {
                                OpenspaceSampleCreatorContribution sampleContribution =
                                        new OpenspaceSampleCreatorContribution(
                                                element);

                                contributionCache.add(sampleContribution);

                            } catch (Exception e) {
                                OpenspaceGadgetPlugin.getDefault().getLogger()
                                        .error(e.getMessage());
                            }

                        }
                    }
                }
            }
        }

        return contributionCache;
    }
}
