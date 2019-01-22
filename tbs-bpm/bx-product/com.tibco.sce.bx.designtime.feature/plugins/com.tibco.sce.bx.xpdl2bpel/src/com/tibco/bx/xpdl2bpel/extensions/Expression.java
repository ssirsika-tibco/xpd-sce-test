/**
 * Copyright (c) TIBCO Software Inc 2004-2011. All rights reserved.
 */
package com.tibco.bx.xpdl2bpel.extensions;


/**
 * Class that contains the Expression information
 *
 * @author mtorres
 *
 */
public class Expression {
    /**
     * The script grammar
     */
    private String scriptGrammar;
    /**
     * The expression text
     */
    private String text;
    
    public Expression(String scriptGrammar, String text) {
        this.scriptGrammar = scriptGrammar;
        this.text = text;
    }
    
    /**
     * Returns the Script Grammar
     * @return String
     */
    public String getScriptGrammar(){
        return scriptGrammar;
    }
    /**
     * Returns the text of the expression
     * @return String
     */
    public String getText(){
        return text;
    }
}
