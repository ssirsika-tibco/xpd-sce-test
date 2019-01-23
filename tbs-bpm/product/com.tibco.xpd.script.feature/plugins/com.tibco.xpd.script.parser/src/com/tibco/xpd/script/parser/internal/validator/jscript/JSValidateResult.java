/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator.jscript;

import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
/**
 * 
 * Implementation of {@link IValidateResult}
 * 
 * @author mtorres
 */
public class JSValidateResult implements IValidateResult {

    private IExpr expr;

    private IScriptRelevantData type;

    public IExpr getExpr() {
        return expr;
    }

    public IScriptRelevantData getType() {
        return type;
    }

    public void setExpr(IExpr expr) {
        this.expr = expr;
    }

    public void setType(IScriptRelevantData type) {
        this.type = type;
    }

}
