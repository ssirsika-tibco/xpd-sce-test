/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.n2.resources.refactoring;

import com.tibco.xpd.script.parser.internal.refactoring.IExpressionRefactor;
import com.tibco.xpd.script.parser.internal.refactoring.jscript.JSExpressionRefactorFactory;
/**
 * 
 * Factory class for JavaScript for the creation of Expression Refactors
 * 
 * @author mtorres
 */
public class N2JSExpressionRefactorFactory extends JSExpressionRefactorFactory{
    
    @Override
    protected IExpressionRefactor getScriptIdentifierExpressionRefactor(){
        return new N2JScriptIdentifierExpressionRefactor();
    }
    
    @Override
    protected IExpressionRefactor getScriptDotExpressionRefactor() {
        return new N2JScriptDotExpressionRefactor();
    }

}
