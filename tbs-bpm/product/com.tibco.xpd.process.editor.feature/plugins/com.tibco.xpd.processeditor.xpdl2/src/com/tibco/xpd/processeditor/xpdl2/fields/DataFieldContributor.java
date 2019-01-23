/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.fields;

import java.util.List;

import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;

/**
 * @author nwilson
 */
public interface DataFieldContributor {
    public static String CONTEXT_DATABASE_SERVICE_TASK = "DatabaseServiceTask"; //$NON-NLS-1$

    public static String CONTEXT_WEB_SERVICE_TASK = "WebServiceTask"; //$NON-NLS-1$

    public static String CONTEXT_BW_SERVICE_TASK = "BWServiceTask"; //$NON-NLS-1$

    /** When passed CONTEXT_ALL_POSSIBLE the contributor should return all fields it can ever contribute. */
    public static String CONTEXT_ALL_POSSIBLE = "AllPossible"; //$NON-NLS-1$
    
    List<ConceptPath> getDataFields(String context);
}
