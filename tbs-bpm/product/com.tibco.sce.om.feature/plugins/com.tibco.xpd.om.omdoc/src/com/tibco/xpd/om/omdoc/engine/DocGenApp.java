/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.om.omdoc.engine;

import org.eclipse.core.resources.IProject;

import com.tibco.xpd.ui.documentation.IDocGen;

/**
 * @author glewis
 * 
 * @deprecated see {@link IDocGen}
 */
public interface DocGenApp {
    void initialise(final String strReportFile);

    void initialise(final String strReportFile, String outputFolder);

    String generate(IProject project, int outputType);

    /**
     * Disposes of the doc gen and any associated resources.
     */
    void dispose();
}
