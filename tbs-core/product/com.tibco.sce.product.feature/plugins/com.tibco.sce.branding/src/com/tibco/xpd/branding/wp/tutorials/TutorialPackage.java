/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.branding.wp.tutorials;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;

import com.tibco.xpd.branding.internal.Messages;
import com.tibco.xpd.branding.wp.samples.postinstall.PostInstallStep;

/**
 * 
 * A "Tutorials" Welcome page tutorial package i.e. an entry within a tutorial
 * category.
 * 
 * 
 * @author rgreen
 * 
 */
public class TutorialPackage {

    private String id;
    private String category;
    private String label;
    private String helpPageURI;
    private String resourcesURI;
    private String solutionURI;
    private String contributor;

    private List<String> resourcesFolders;
    private List<String> solutionFolders;

    private Collection<PostInstallStep> postInstallSteps;

    public TutorialPackage(IConfigurationElement element) {

        resourcesFolders = new ArrayList<String>();
        solutionFolders = new ArrayList<String>();

        if (element.getAttribute("id") != null) { //$NON-NLS-1$
            id = element.getAttribute("id"); //$NON-NLS-1$
        }

        if (element.getAttribute("category") != null) { //$NON-NLS-1$
            category = element.getAttribute("category"); //$NON-NLS-1$
        }

        if (element.getAttribute("label") != null) { //$NON-NLS-1$
            label = element.getAttribute("label"); //$NON-NLS-1$
        }

        if (element.getAttribute("helpPageURI") != null) { //$NON-NLS-1$
            helpPageURI = element.getAttribute("helpPageURI"); //$NON-NLS-1$
        }

        if (element.getAttribute("resourcesURI") != null) { //$NON-NLS-1$
            resourcesURI = element.getAttribute("resourcesURI"); //$NON-NLS-1$
        }

        if (element.getAttribute("solutionURI") != null) { //$NON-NLS-1$
            solutionURI = element.getAttribute("solutionURI"); //$NON-NLS-1$
        }

        contributor = element.getContributor().getName();

        IConfigurationElement[] children = element
                .getChildren("resourcesFolder"); //$NON-NLS-1$

        if (children != null) {
            for (IConfigurationElement configurationElement : children) {
                String attribute = configurationElement
                        .getAttribute("resourcesURI"); //$NON-NLS-1$

                resourcesFolders.add(attribute);
            }
        }

        children = element.getChildren("solutionsFolder"); //$NON-NLS-1$

        if (children != null) {
            for (IConfigurationElement configurationElement : children) {
                String attribute = configurationElement
                        .getAttribute("solutionURI"); //$NON-NLS-1$
                solutionFolders.add(attribute);

            }
        }

        postInstallSteps = collectPostInstallSteps(element);

    }

    public String getLabel() {
        return label;
    }

    public String getHelpPageURI() {
        return helpPageURI;
    }

    public String getResourcesURI() {
        return resourcesURI;
    }

    public String getSolutionURI() {
        return solutionURI;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getContributor() {
        return contributor;
    }

    public List<String> getResourcesFolders() {
        return resourcesFolders;
    }

    public List<String> getSolutionFolders() {
        return solutionFolders;
    }

    public Collection<PostInstallStep> getPostInstallSteps() {
        return postInstallSteps;
    }

    private Collection<PostInstallStep> collectPostInstallSteps(
            IConfigurationElement element) {
        List<PostInstallStep> result = new ArrayList<PostInstallStep>();

        IConfigurationElement[] steps = element.getChildren("postInstall"); //$NON-NLS-1$
        for (IConfigurationElement step : steps) {
            result.add(new PostInstallStep(step));
        }

        Collections.sort(result, new InstallStepComparator());

        return result;
    }

    class InstallStepComparator implements Comparator<PostInstallStep> {
        public int compare(PostInstallStep s1, PostInstallStep p2) {
            int result;
            String order1 = s1.getOrder();
            String order2 = p2.getOrder();
            result = order1.compareTo(order2);

            return result;
        }
    }

}
