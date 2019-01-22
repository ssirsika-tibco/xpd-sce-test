/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.branding.wp.tutorials;

import org.eclipse.core.runtime.IConfigurationElement;

/**
 * 
 * Factory to instantiate TutorialPackage and TutorialCategory.
 * 
 * @author rgreen
 *
 */
public class TutorialElementFactory {

    public static TutorialPackage CreateTutorialPackage(
            IConfigurationElement cfg) {

        TutorialPackage proj = new TutorialPackage(cfg);

        return proj;

    }
    
    
    public static TutorialCategory CreateTutorialCategory(
            IConfigurationElement cfg) {

        TutorialCategory cat = new TutorialCategory(cfg);

        return cat;

    }

}
