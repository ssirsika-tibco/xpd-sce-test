/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.om.omdoc.engine;

/**
 * @author glewis
 */
public class DocGenType {

    public static final String XLS_OUTPUTTYPE = "XLS"; //$NON-NLS-1$

    public static final String PPT_OUTPUTTYPE = "PPT"; //$NON-NLS-1$

    public static final String POSTSCRIPT_OUTPUTTYPE = "POSTSCRIPT"; //$NON-NLS-1$

    public static final String PDF_OUTPUTTYPE = "PDF"; //$NON-NLS-1$

    public static final String HTML_OUTPUTTYPE = "HTML"; //$NON-NLS-1$

    public static final String DOC_OUTPUTTYPE = "DOC"; //$NON-NLS-1$

    /** Identifier for DOC output. */
    public static final int OUTPUT_DOC = 0;

    /** Identifier for HTML output. */
    public static final int OUTPUT_HTML = 1;

    /** Identifier for PDF output. */
    public static final int OUTPUT_PDF = 2;

    /** Identifier for POSTSCRIPT output. */
    public static final int OUTPUT_POSTSCRIPT = 3;

    /** Identifier for PPT output. */
    public static final int OUTPUT_PPT = 4;

    /** Identifier for XLS output. */
    public static final int OUTPUT_XLS = 5;
}
