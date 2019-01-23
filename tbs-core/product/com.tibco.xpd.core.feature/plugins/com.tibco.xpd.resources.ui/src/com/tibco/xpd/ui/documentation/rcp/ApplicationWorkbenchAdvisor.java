/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.ui.documentation.rcp;

import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * @author mtorres
 */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

    private String projectName = null;

    private IPath outputPath = null;

    public ApplicationWorkbenchAdvisor(String projectName, IPath outputPath) {
        this.projectName = projectName;
        this.outputPath = outputPath;
    }

    @Override
    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
            IWorkbenchWindowConfigurer configurer) {
        return new ApplicationWorkbenchWindowAdvisor(configurer, projectName,
                outputPath);
    }

    @Override
    public String getInitialWindowPerspectiveId() {
        return null;
    }
}
