/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.rql.parser.eval;

import com.tibco.n2.de.rql.parser.Node;

/**
 * Indicates an exception in the evaluation of a given RLQ expression.
 *
 * @copyright 2010 TIBCO Software Inc.
 * @author pwatson
 * @version 1.2
 */
public class RqlException extends RuntimeException
{
    private static final long serialVersionUID = -7340568325190481986L;

    private ValidationContext context;

    private Node node;

    /**
     * @param aRqlExpression the RQL expression that caused the exception.
     * @param aCause the underlying cause of the exception.
     */
    public RqlException(ValidationContext aContext,
                        Node aNode,
                        Throwable aCause)
    {
        super(aCause);
        context = aContext;
        node = aNode;
    }

    public String getExpression()
    {
        return(context.getExpression());
    }

    public Node getNode()
    {
        return(node);
    }
}
