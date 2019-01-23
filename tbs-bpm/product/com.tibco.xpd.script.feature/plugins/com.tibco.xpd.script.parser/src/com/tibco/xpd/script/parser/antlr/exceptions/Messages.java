/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.script.parser.antlr.exceptions;

import org.eclipse.osgi.util.NLS;

/**
 * @author mtorres
 *
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.script.parser.antlr.exceptions.messages"; //$NON-NLS-1$

    public static String TCNoViableAltException_Unexpected_Token;

    public static String TCNoViableAltException_UnexpectedASTNode;

    public static String TCNoViableAltException_UnexpectedEndOfSubtree;

    public static String TCNoViableAltException_ExpectedSemi;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
