/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.rql.parser.eval;

import java.util.Collections;

import com.tibco.n2.de.rql.parser.ASTGroupQuery;
import com.tibco.n2.de.rql.parser.ASTOrgUnitQuery;
import com.tibco.n2.de.rql.parser.base.AbstractRqlParserVisitor;
import com.tibco.n2.de.rql.parser.base.RqlContext;

/**
 * Validates the use of a scope in a RQL expression. Only Groups and Org-Units
 * are valid uses of the scope term. Passing this visitor to the parent entity
 * of the scope term will determine if its use is valid. If the accept call
 * returns a non-null result then the scope is valid.
 */
public class ScopeVisitor extends AbstractRqlParserVisitor
{
    private static final ScopeVisitor INSTANCE = new ScopeVisitor();
    
    public static ScopeVisitor getInstance()
    {
        return(ScopeVisitor.INSTANCE);
    }
    
    private ScopeVisitor() {}
    
    public Object visit(ASTGroupQuery node, RqlContext context)
    {
        // could replace with code that determines what
        // groups would be selected by the scope
        return Collections.emptyList();
    }

    public Object visit(ASTOrgUnitQuery node, RqlContext context)
    {
        return Collections.emptyList();
    }
}
