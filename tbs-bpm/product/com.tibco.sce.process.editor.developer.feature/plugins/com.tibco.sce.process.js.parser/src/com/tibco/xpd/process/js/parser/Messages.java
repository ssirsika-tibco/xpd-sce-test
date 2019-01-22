package com.tibco.xpd.process.js.parser;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.process.js.parser.messages"; //$NON-NLS-1$

    public static String JScriptASTTreeValidator_LastStatementEvaluatingToBoolean;

    public static String JScriptASTTreeValidator_LastStatementEvaluatingToNumber;

    public static String JScriptASTTreeValidator_DataTypeDoNotMatch;

    public static String JScriptASTTreeValidator_OnlyReturnStatementNotSupported;

    public static String JScriptASTTreeValidator_LastStatementEvaluatingToString;

    public static String JScriptASTTreeValidator_LastStatementEvaluatingToNumberOrBoolean;

    public static String JScriptASTTreeValidator_SingleStatementHardcodedNumber1;

    public static String JScriptASTTreeValidator_MIAddlInstanceScriptEvaluateToZero1;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
