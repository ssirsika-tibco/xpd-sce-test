/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

/**
 * @author nwilson
 */
public final class ExpressionParserFactory {

    /** The factory instance. */
    private static ExpressionParserFactory factory;

    /**
     * Private contsructor.
     */
    private ExpressionParserFactory() {
    }

    /**
     * @return The factory instance.
     */
    public static synchronized ExpressionParserFactory getInstance() {
        if (factory == null) {
            factory = new ExpressionParserFactory();
        }
        return factory;
    }
    
    /**
     * @param grammar The grammar identifier.
     * @return The expression parser for the given grammar
     */
    public IExpressionParser getExpressionParser(String grammar) {
        IExpressionParser parser = null;
        if (grammar != null) {
            if (grammar.equals(JavaScriptExpressionParser.JAVASCRIPT)) {
                return new JavaScriptExpressionParser();
            } else if (grammar.equals(XPathExpressionParser.XPATH)) {
                return new XPathExpressionParser();
            }
        }
        return parser;
    }
}
