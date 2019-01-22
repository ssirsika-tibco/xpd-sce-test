/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator;


/**
 * Information of the variable
 * 
 * @author mtorres
 *
 */
public class VariableInfo {
    
    private String text;
    private int line;
    private int column;
    
    public VariableInfo(String text, int line, int column) {
        this.text = text;
        this.line = line;
        this.column = column;
    }
    
    public String getText() {
        return text;
    }
    public int getLine() {
        return line;
    }
    public int getColumn() {
        return column;
    }
}
