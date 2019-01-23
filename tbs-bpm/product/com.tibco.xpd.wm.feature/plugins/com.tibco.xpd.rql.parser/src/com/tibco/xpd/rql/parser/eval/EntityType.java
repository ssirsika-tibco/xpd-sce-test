/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.rql.parser.eval;

import com.tibco.n2.de.rql.parser.ASTCapabilityQuery;
import com.tibco.n2.de.rql.parser.ASTGroupQuery;
import com.tibco.n2.de.rql.parser.ASTLocationQuery;
import com.tibco.n2.de.rql.parser.ASTOrgQuery;
import com.tibco.n2.de.rql.parser.ASTOrgUnitQuery;
import com.tibco.n2.de.rql.parser.ASTPositionQuery;
import com.tibco.n2.de.rql.parser.ASTPrivilegeQuery;
import com.tibco.n2.de.rql.parser.ASTResourceQuery;
import com.tibco.n2.de.rql.parser.base.AbstractRqlParserVisitor;
import com.tibco.n2.de.rql.parser.base.EntityNode;
import com.tibco.n2.de.rql.parser.base.RqlContext;

public enum EntityType
{
    RESOURCE, GROUP, POSITION, ORGANIZATIONAL_UNIT, LOCATION, ORGANIZATION, CAPABILITY, PRIVILEGE;

    public static EntityType getType(EntityNode aNode)
    {
        return((EntityType)aNode.jjtAccept(EntityVisitor.INSTANCE, null));
    }
    
    private static final class EntityVisitor extends AbstractRqlParserVisitor
    {
        static final EntityVisitor INSTANCE = new EntityVisitor();
        
        public Object visit(ASTGroupQuery node, RqlContext data)
        {
            return(EntityType.GROUP);
        }
    
        public Object visit(ASTPositionQuery node, RqlContext data)
        {
            return(EntityType.POSITION);
        }
    
        public Object visit(ASTOrgUnitQuery node, RqlContext data)
        {
            return(EntityType.ORGANIZATIONAL_UNIT);
        }
    
        public Object visit(ASTOrgQuery node, RqlContext data)
        {
            return(EntityType.ORGANIZATION);
        }
    
        public Object visit(ASTLocationQuery node, RqlContext data)
        {
            return(EntityType.LOCATION);
        }
    
        public Object visit(ASTPrivilegeQuery node, RqlContext data)
        {
            return(EntityType.PRIVILEGE);
        }
    
        public Object visit(ASTCapabilityQuery node, RqlContext data)
        {
            return(EntityType.CAPABILITY);
        }
    
        public Object visit(ASTResourceQuery node, RqlContext data)
        {
            return(EntityType.RESOURCE);
        }
    }
}
