/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.rql.parser.eval;

import java.util.Collections;

import com.tibco.n2.de.rql.parser.ASTCapabilityQuery;
import com.tibco.n2.de.rql.parser.ASTPrivilegeQuery;
import com.tibco.n2.de.rql.parser.base.AbstractRqlParserVisitor;
import com.tibco.n2.de.rql.parser.base.RqlContext;

/**
 * Validates the use of a qualifier in a RQL expression. Only Privileges and
 * Capabilities are valid uses of the qualifier term. Passing this visitor to
 * the parent entity of the qualifier term will determine if its use is valid.
 * If the accept call returns a non-null result then the qualifier is valid. 
 */
public class QualifierVisitor extends AbstractRqlParserVisitor
{
    private static final QualifierVisitor INSTANCE = new QualifierVisitor();
    
    public static QualifierVisitor getInstance()
    {
        return(QualifierVisitor.INSTANCE);
    }
    
    private QualifierVisitor() {}
    
    public Object visit(ASTPrivilegeQuery node, RqlContext context)
    {
        return Collections.emptyList();
    }

    public Object visit(ASTCapabilityQuery node, RqlContext context)
    {
        return Collections.emptyList();
    }
}
