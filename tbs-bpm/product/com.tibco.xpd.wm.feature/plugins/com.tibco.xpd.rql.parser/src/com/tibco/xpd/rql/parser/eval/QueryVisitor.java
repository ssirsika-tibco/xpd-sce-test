/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.rql.parser.eval;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import com.tibco.n2.de.rql.parser.ASTCapabilityQuery;
import com.tibco.n2.de.rql.parser.ASTGroupQuery;
import com.tibco.n2.de.rql.parser.ASTIntersect;
import com.tibco.n2.de.rql.parser.ASTLocationQuery;
import com.tibco.n2.de.rql.parser.ASTNot;
import com.tibco.n2.de.rql.parser.ASTOrgQuery;
import com.tibco.n2.de.rql.parser.ASTOrgUnitQuery;
import com.tibco.n2.de.rql.parser.ASTPositionQuery;
import com.tibco.n2.de.rql.parser.ASTPrivilegeQuery;
import com.tibco.n2.de.rql.parser.ASTResourceQuery;
import com.tibco.n2.de.rql.parser.ASTSubtract;
import com.tibco.n2.de.rql.parser.ASTUnion;
import com.tibco.n2.de.rql.parser.ASTparse;
import com.tibco.n2.de.rql.parser.Node;
import com.tibco.n2.de.rql.parser.RqlParserVisitor;
import com.tibco.n2.de.rql.parser.base.AbstractRqlParserVisitor;
import com.tibco.n2.de.rql.parser.base.EntityNode;
import com.tibco.n2.de.rql.parser.base.RqlContext;

/**
 * @author pwatson
 *
 */
public class QueryVisitor extends AbstractRqlParserVisitor
{
    private EntityNodeVisitor nodeEvaluator = new EntityNodeVisitor();

    public static QueryVisitor getInstance()
    {
        return(new QueryVisitor());
    }

    private QueryVisitor() {}


    /**
     * The beginning of the RQL evaluation, this will initiate the visitation of
     * each node of the expression. As each node is visited, the org entities
     * identified will be collected. Finally, the Resources directly associated
     * with those entities will be resolved.
     */
    @SuppressWarnings("unchecked")
    public Object visit(ASTparse aNode, RqlContext aContext)
    {
        try
        {
            Collection<Object> entities = new HashSet<Object>();
            for (int i = 0, n = aNode.jjtGetNumChildren(); i < n; i++)
            {
                Object childResult = aNode.jjtGetChild(i).jjtAccept(this, aContext);
                if (childResult != null)
                {
                    entities.addAll((Collection<Object>)childResult);
                }
            }

            return(entities);
        }
        catch (RqlException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new RqlException((ValidationContext)aContext, aNode, e);
        }
    }

    /**
     * Processes the given ASTUnion by evaluating the nodes it contains and
     * returning the union of their results.
     *
     * @see RqlParserVisitor#visit(ASTUnion, RqlContext)
     */
    @SuppressWarnings("unchecked")
    public Object visit(ASTUnion aNode, RqlContext aContext)
    {
        Collection<Object> result = new HashSet<Object>();
        for (int i = 0, n = aNode.jjtGetNumChildren(); i < n; i++)
        {
            Node childNode = aNode.jjtGetChild(i);
            Object childResult = childNode.jjtAccept(this, aContext);
            if (childResult != null)
            {
                result.addAll((Collection<Object>)childResult);
            }
        }

        return(result);
    }

    /**
     * Processes the given ASTIntersect by evaluating the nodes it contains and
     * returning the intersection of their results.
     *
     * @see RqlParserVisitor#visit(ASTIntersect, RqlContext)
     */
    @SuppressWarnings("unchecked")
    public Object visit(ASTIntersect aNode, RqlContext aContext)
    {
        Collection<Object> result = null;
        for (int i = 0, n = aNode.jjtGetNumChildren(); i < n; i++)
        {
            Node childNode = aNode.jjtGetChild(i);
            Object childResult = childNode.jjtAccept(this, aContext);
            if (childResult != null)
            {
                Collection<Object> entities = (Collection<Object>)childResult;

                // if this is the first result
                // OR no entities found by current expression
                if ((result == null) || (entities.isEmpty()))
                {
                    // assign the current set to the result
                    result = entities;
                }

                else // keep those that are also in the final result
                {
                    result.retainAll(entities);
                }

                // if result is empty - may as well stop now
                if (result.isEmpty())
                {
                    break;
                }
            }
        }

        return((result == null) ? Collections.emptySet() : result);
    }

    @SuppressWarnings("unchecked")
    public Object visit(ASTSubtract aNode, RqlContext aContext)
    {
        Collection<Object> result = null;
        for (int i = 0, n = aNode.jjtGetNumChildren(); i < n; i++)
        {
            Node childNode = aNode.jjtGetChild(i);
            Object childResult = childNode.jjtAccept(this, aContext);
            if (childResult != null)
            {
                Collection<Object> entities = (Collection<Object>)childResult;
                
                // if this is the first result
                if (result == null)
                {
                    // take all resources as a starting point
                    result = entities;
                }

                // if expression found any entities
                else if (! entities.isEmpty())
                {
                    // subtract them from the final result
                    result.removeAll(entities);
                }

                // if result is empty - may as well stop now
                if (result.isEmpty())
                {
                    break;
                }
            }
        }

        return((result == null) ? Collections.emptySet() : result);
    }

    /**
     * @see RqlParserVisitor#visit(ASTNot, RqlContext)
     */
    @SuppressWarnings("unchecked")
    public Object visit(ASTNot aNode, RqlContext aContext)
    {
        // process the child nodes - gather the results
        Collection<Object> result = new HashSet<Object>();
        for (int i = 0, n = aNode.jjtGetNumChildren(); i < n; i++)
        {
            Node childNode = aNode.jjtGetChild(i);
            Object childResult = childNode.jjtAccept(this, aContext);
            if (childResult != null)
            {
                result.addAll((Collection<Object>)childResult);
            }
        }
        
        EntityNode child = (EntityNode)aNode.jjtGetChild(0);
        
        // TODO: gather all entities of the same type and subtract the result from above

        return(result);
    }

    /**
     * @see RqlParserVisitor#visit(ASTGroupQuery, RqlContext)
     */
    public Object visit(ASTGroupQuery aNode, RqlContext aContext)
    {
        return(evaluateNode(aNode, aContext, ((ValidationContext)aContext).getGroupDao()));
    }

    /**
     * @see RqlParserVisitor#visit(ASTPositionQuery, RqlContext)
     */
    public Object visit(ASTPositionQuery aNode, RqlContext aContext)
    {
        return(evaluateNode(aNode, aContext, ((ValidationContext)aContext).getPositionDao()));
    }

    /**
     * @see RqlParserVisitor#visit(ASTOrgUnitQuery, RqlContext)
     */
    public Object visit(ASTOrgUnitQuery aNode, RqlContext aContext)
    {
        return(evaluateNode(aNode, aContext, ((ValidationContext)aContext).getOrgUnitDao()));
    }

    /**
     * @see RqlParserVisitor#visit(ASTOrgQuery, RqlContext)
     */
    public Object visit(ASTOrgQuery aNode, RqlContext aContext)
    {
        return(evaluateNode(aNode, aContext, ((ValidationContext)aContext).getOrgDao()));
    }

    /**
     * @see RqlParserVisitor#visit(ASTLocationQuery, RqlContext)
     */
    public Object visit(ASTLocationQuery aNode, RqlContext aContext)
    {
        return(evaluateNode(aNode, aContext, ((ValidationContext)aContext).getLocationDao()));
    }

    /**
     * @see RqlParserVisitor#visit(ASTPrivilegeQuery, RqlContext)
     */
    public Object visit(ASTPrivilegeQuery aNode, RqlContext aContext)
    {
        return(evaluateNode(aNode, aContext, ((ValidationContext)aContext).getPrivilegeDao()));
    }

    /**
     * @see RqlParserVisitor#visit(ASTCapabilityQuery, RqlContext)
     */
    public Object visit(ASTCapabilityQuery aNode, RqlContext aContext)
    {
        return(evaluateNode(aNode, aContext, ((ValidationContext)aContext).getCapabilityDao()));
    }

    /**
     * @see RqlParserVisitor#visit(ASTResourceQuery, RqlContext)
     */
    public Object visit(ASTResourceQuery aNode, RqlContext aContext)
    {
        return(evaluateNode(aNode, aContext, ((ValidationContext)aContext).getResourceDao()));
    }

    /**
     * Evaluates the org entities identified by the given {@link EntityNode},
     * and (by recursion) any EntityNodes it may contain.
     *
     * @param aNode the EntityNode to be evaluated.
     * @param aContext the context in which the expression is to be evaluated.
     * @param aDao the DAO to be used to retrieve the identified org entities.
     * @return the identified collection of org entities.
     */
    @SuppressWarnings("unchecked")
    private Collection<Object> evaluateNode(EntityNode aNode,
                                            RqlContext aContext,
                                            EntityDao aDao)
    {
        // find the entities that match the property and attribute expressions
        //TODO: if aNode is resource just skip this first loop
        //===================================
        nodeEvaluator.setDao(aDao);
        for (int i = 0, n = aNode.jjtGetNumChildren(); i < n; i++)
        {
            Node child = aNode.jjtGetChild(i);
            Object childResult = child.jjtAccept(nodeEvaluator, aContext);
            
            // record the results in the node
            aNode.addNodeResults((Collection<Object>)childResult);
        }

        // if the expressions have failed to find any org entities
        if (! aNode.hasNodeResults())
        {
            // return a empty result
            return(Collections.emptyList());
        }
        //======================================

        Object result = aNode.getNodeResults();

        // process any nested EntityNodes
        for (int i = 0, n = aNode.jjtGetNumChildren(); i < n; i++)
        {
            Node child = aNode.jjtGetChild(i);
            Object childResult = child.jjtAccept(this, aContext);
            if (childResult != null)
            {
                result = childResult;
            }
        }

        return((Collection<Object>)result);
    }
}
