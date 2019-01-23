/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.om.omdoc.engine;

import org.eclipse.core.resources.IResource;

import com.tibco.xpd.ui.documentation.IDocGen;

/**
 * @author glewis
 * 
 * @deprecated see {@link IDocGen}
 */
public interface DocGen {
    void initialise(final String strReportFile);

    void initialise(final String strReportFile, String outputFile);

    String generate(IResource[] inputs, int outputType);

    /**
     * Disposes of the doc gen and any associated resources.
     */
    void dispose();
}
