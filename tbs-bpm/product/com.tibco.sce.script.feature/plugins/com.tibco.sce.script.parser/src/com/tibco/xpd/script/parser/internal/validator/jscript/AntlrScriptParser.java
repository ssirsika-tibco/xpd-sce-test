/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator.jscript;

import antlr.LLkParser;

import com.tibco.xpd.script.parser.internal.expr.IScriptParser;

/**
 * @author mtorres
 * 
 * Implementation of IScriptParser interface
 * 
 */
public class AntlrScriptParser implements IScriptParser{

    private LLkParser scriptParser;
    
    public AntlrScriptParser(LLkParser scriptParser) {
        this.scriptParser = scriptParser;
    }
    
    public Object getScriptParser() {
        return scriptParser;
    }
}
