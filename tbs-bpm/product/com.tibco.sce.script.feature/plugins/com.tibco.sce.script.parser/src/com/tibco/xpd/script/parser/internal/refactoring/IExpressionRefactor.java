/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.refactoring;

import java.util.List;

import antlr.LLkParser;

import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.parser.internal.expr.IInfoObject;
import com.tibco.xpd.script.parser.internal.expr.IRefactoringInfoObject;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
/**
 * Factory class for the Expression Validation
 * 
 * @author mtorres
 */
public interface IExpressionRefactor extends IRefactor {

    /**
     * Sets the script parser
     * 
     * @param scriptParser
     */
    void setScriptParser(LLkParser scriptParser);

    /**
     * Sets the var name resolver
     * 
     * @param varNameResolver
     */
    void setVarNameResolver(IVarNameResolver varNameResolver);

    /**
     * Returns the supported Js Classes
     * 
     * @return list of supported js classes
     */
    List<JsClass> getSupportedJsClasses();
    
    /**
     * Sets the info Object 
     * 
     * @param infoObject
     */
    void setInfoObject(IRefactoringInfoObject infoObject);
    
    /**
     * Returns the info object
     * 
     * @return the info object
     */
    IRefactoringInfoObject getInfoObject();
}
