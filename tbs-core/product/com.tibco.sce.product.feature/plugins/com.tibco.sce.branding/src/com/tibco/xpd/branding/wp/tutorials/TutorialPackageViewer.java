/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.branding.wp.tutorials;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.intro.config.IIntroContentProvider;
import org.eclipse.ui.intro.config.IIntroContentProviderSite;

import com.tibco.xpd.branding.BrandingPlugin;
import com.tibco.xpd.branding.internal.Messages;


/**
 * 
 * 
 * 
 * 
 * @author rgreen
 *
 */
public class TutorialPackageViewer implements IIntroContentProvider {

    private IIntroContentProviderSite site;
    private static String ECLIPSE_HELP_PREFIX = "http://org.eclipse.ui.intro/showHelpTopic"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.intro.config.IIntroContentProvider#init(org.eclipse.ui
     * .intro.config.IIntroContentProviderSite)
     */
    public void init(IIntroContentProviderSite site) {

        this.site = site;
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint extensionPoint = registry
                .getExtensionPoint(BrandingPlugin.PLUGIN_ID
                        + ".tutorialProjects"); //$NON-NLS-1$
        IConfigurationElement[] configurationElements = extensionPoint
                .getConfigurationElements();

        for (IConfigurationElement element : configurationElements) {
            String id = element.getAttribute("id"); //$NON-NLS-1$
            String name = element.getName();

            if ("category".equals(name)) { //$NON-NLS-1$
                if (!TutorialPackageManager.getINSTANCE()
                        .containsCategoryId(id)) {
                    TutorialPackageManager.getINSTANCE().addCategory(element);
                }
            } else if ("project".equals(name)) { //$NON-NLS-1$
                if (!TutorialPackageManager.getINSTANCE().containsPackageId(id)) {
                    TutorialPackageManager.getINSTANCE().addPackage(element);
                }
            }
        }
    };

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.intro.config.IIntroContentProvider#createContent(java.
     * lang.String, java.io.PrintWriter)
     */
    public void createContent(String id, java.io.PrintWriter out) {

        Collection<TutorialCategory> category = TutorialPackageManager
                .getINSTANCE().getCategories();

        for (TutorialCategory tutorialCategory : category) {

            if (id.equals("tutorials-" + tutorialCategory.getPersona() //$NON-NLS-1$
                    + "-viewer") //$NON-NLS-1$
                    || "all".equals(tutorialCategory.getPersona())) { //$NON-NLS-1$

                // Collect all the projects contributing to this category
                // If there aren't any then we just skip this ccategory
                // as we don't want to show a heading with no projects
                // underneath
                List<TutorialPackage> packages = TutorialPackageManager
                        .getINSTANCE().getProjectsInCategory(tutorialCategory);

                if (!packages.isEmpty()) {

                    out.println("<h5 class=\"tutorialsTable\">"); //$NON-NLS-1$
                    out.println(tutorialCategory.getCategoryLabel());
                    out.println("</h5>"); //$NON-NLS-1$

                    // Display each project's info in a 3 colmun table row
                    // consisting of:
                    // col1: Project description label linking to help page
                    // col2: link to install tutorial resources
                    // col3: link to install completed tutorial (solution)
                    out.println("<table class=\"tutorialsTable\">"); //$NON-NLS-1$
                    for (TutorialPackage tutorialPackage : packages) {
                        String helpLocation = tutorialPackage.getHelpPageURI();
                        out.println("<tr>"); //$NON-NLS-1$

                        // Create the help link
                        out
                                .println("<td class=\"tutorialsHelp\" width=\"300\">" //$NON-NLS-1$
                                        + "<a href=" //$NON-NLS-1$
                                        + createHelpLink(helpLocation) + ">"); //$NON-NLS-1$
                        out.println(tutorialPackage.getLabel());
                        out.println("</a></td>"); //$NON-NLS-1$

                        // Install resources link
                        if (!tutorialPackage.getResourcesFolders().isEmpty()) {
                            out
                                    .print("<td class=\"resourcesInstall\"><a href=\""); //$NON-NLS-1$
                            out.print(createInstallLink(
                                    tutorialPackage.getId(),
                                    ITutorialConstants.InstallTarget.RESOURCES
                                            .getValue()));
                            out.print("\">"); //$NON-NLS-1$
                            out.print(Messages.TutorialPackageViewer_Resources_label);
                            out.println("</a></td>"); //$NON-NLS-1$
                        }

                        // Install complete solution link
                        if (!tutorialPackage.getSolutionFolders().isEmpty()) {
                            out
                                    .print("<td class=\"solutionInstall\"><a href=\""); //$NON-NLS-1$
                            out.print(createInstallLink(
                                    tutorialPackage.getId(),
                                    ITutorialConstants.InstallTarget.SOLUTION.getValue()));
                            out.print("\">"); //$NON-NLS-1$
                            out.print(Messages.TutorialPackageViewer_Solution_label);
                            out.println("</a></td>"); //$NON-NLS-1$
                        }
                        

                        out.println("</tr>"); //$NON-NLS-1$

                        
                        
                        
                        
                    }
                    out.println("<tr>"); //$NON-NLS-1$
                    
                    // Add an empty row for nice formatting
                    out.println("<tr>"); //$NON-NLS-1$
                    out.println("<td>&nbsp;</td>");
                    out.println("<td>&nbsp;</td>");
                    out.println("<td>&nbsp;</td>");
                    out.println("</tr>"); //$NON-NLS-1$
                    
                    out.println("</table>"); //$NON-NLS-1$

                }
            }
        }

    }

    /**
     * 
     * Builds the string containing the information needed to
     * run an install action from a hyperlink.
     * 
     * @param String projectId
     * @param String target
     * @return String link
     */
    private String createInstallLink(String projectId, String target) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://org.eclipse.ui.intro/runAction?pluginId="); //$NON-NLS-1$
        sb.append(BrandingPlugin.PLUGIN_ID);
        sb.append("&amp;class="); //$NON-NLS-1$
        sb.append(TutorialPackageInstallIntroAction.class.getName());
        sb.append("&amp;projectId="); //$NON-NLS-1$
        sb.append(projectId);
        sb.append("&amp;target="); //$NON-NLS-1$
        sb.append(target);
        return sb.toString();
    }

    
    /**
     * 
     * Builds the full hyperlink to the supplied help location.
     * 
     * 
     * @param String helpLocation
     * @return String link
     */
    private String createHelpLink(String helpLocation) {
        StringBuilder sb = new StringBuilder();
        sb.append(ECLIPSE_HELP_PREFIX + "?id=" + helpLocation); //$NON-NLS-1$
        return sb.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.intro.config.IIntroContentProvider#dispose()
     */
    public void dispose() {
    };

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.intro.config.IIntroContentProvider#createContent(java.
     * lang.String, org.eclipse.swt.widgets.Composite,
     * org.eclipse.ui.forms.widgets.FormToolkit)
     */
    public void createContent(String id, Composite parent, FormToolkit toolkit) {
    };
}
