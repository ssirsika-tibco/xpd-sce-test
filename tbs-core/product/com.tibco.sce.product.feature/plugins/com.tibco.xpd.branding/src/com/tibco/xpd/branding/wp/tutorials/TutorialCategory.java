/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.branding.wp.tutorials;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;

import com.tibco.xpd.branding.internal.Messages;

/**
 * 
 * 
 * A "Tutorials" Welcome page category.
 * 
 * 
 * @author rgreen
 *
 */
public class TutorialCategory {
    private static final String DEFAULT_ORDER_VALUE = "ZZZZZZ"; //$NON-NLS-1$

    private String id;
    private String categoryLabel;
    private String persona;

    private List<TutorialPackage> tutorialProjects;

    /**
     * @param IConfigurationElement element
     */
    public TutorialCategory(IConfigurationElement element) {
        this.id = element.getAttribute("id"); //$NON-NLS-1$
        this.categoryLabel = element.getAttribute("label"); //$NON-NLS-1$
        
        
        if (element.getAttribute("persona")!= null){ //$NON-NLS-1$
            persona = element.getAttribute("persona"); //$NON-NLS-1$
        }
        

        IConfigurationElement[] children = element.getChildren("project"); //$NON-NLS-1$

        tutorialProjects = new ArrayList<TutorialPackage>();

        for (int i = 0; i < children.length; i++) {
            IConfigurationElement configurationElement = children[i];
            TutorialPackage proj = TutorialElementFactory
                    .CreateTutorialPackage(configurationElement);

            if (proj != null) {
                tutorialProjects.add(proj);
            }
        }

    }

    public String getPersona() {
        return persona;
    }

    public List getTutorialProjects() {
        return tutorialProjects;
    }

    public String getCategoryLabel() {
        return categoryLabel;
    }

    public String getId() {
        return id;
    }

}
