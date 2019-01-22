/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.rql.parser.eval;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.tibco.n2.de.rql.parser.ASTAttributeExpr;
import com.tibco.n2.de.rql.parser.ASTFilterAnd;
import com.tibco.n2.de.rql.parser.ASTFilterOr;
import com.tibco.n2.de.rql.parser.ASTPropertyExpr;
import com.tibco.n2.de.rql.parser.ASTQualifierExpr;
import com.tibco.n2.de.rql.parser.ASTScope;
import com.tibco.n2.de.rql.parser.Node;
import com.tibco.n2.de.rql.parser.base.AbstractRqlParserVisitor;
import com.tibco.n2.de.rql.parser.base.EntityNode;
import com.tibco.n2.de.rql.parser.base.Property;
import com.tibco.n2.de.rql.parser.base.RqlContext;
import com.tibco.xpd.rql.parser.script.RQLScriptRelevantData;
import com.tibco.xpd.rql.parser.util.RQLParserUtil;
import com.tibco.xpd.rql.parser.validator.Messages;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.internal.client.IModelScriptRelevantData;

/**
 * An implementation of RqlParserVisitor that evaluates the ComparisonNodes that
 * filter the org entities by their name, type, GUID, attribute and/or qualifier
 * values. It also processes the boolean operators (AND and OR) that combine the
 * ComparisonNodes into more complex expressions. The result is a collection of
 * org entities that match the expression.
 * 
 * @copyright 2011 TIBCO Software Inc.
 * @author pwatson
 * @version 1.3
 */
class EntityNodeVisitor extends AbstractRqlParserVisitor {
    private EntityDao dao;

    /**
     * Sets the EntityDao that will be used to retrieve org entities. This must
     * be set before the EntityNodeEvaluator is passed to each RQL node for
     * processing.
     */
    public void setDao(EntityDao aDao) {
        dao = aDao;
    }

    /**
     * Processes the given ASTFilterAnd by evaluating the nodes it contains and
     * returning the intersection of their results.
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object visit(ASTFilterAnd aNode, RqlContext aContext) {
        Collection<Object> result = new HashSet<Object>();
        for (int i = 0, n = aNode.jjtGetNumChildren(); i < n; i++) {
            Node childNode = aNode.jjtGetChild(i);
            Object childResult = childNode.jjtAccept(this, aContext);
            if (childResult != null) {
                if (result.isEmpty()) {
                    result.addAll((Collection<Object>) childResult);
                } else {
                    result.retainAll((Collection<Object>) childResult);
                }

                // if result is empty - may as well stop now
                if (result.isEmpty()) {
                    break;
                }
            }
        }

        return (result);
    }

    /**
     * Processes the given ASTFilterOr by evaluating the nodes it contains and
     * returning the union of their results.
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object visit(ASTFilterOr aNode, RqlContext aContext) {
        Collection<Object> result = new HashSet<Object>();
        for (int i = 0, n = aNode.jjtGetNumChildren(); i < n; i++) {
            Node childNode = aNode.jjtGetChild(i);
            Object childResult = childNode.jjtAccept(this, aContext);
            if (childResult != null) {
                result.addAll((Collection<Object>) childResult);
            }
        }

        return (result);
    }

    /**
     * Processes the given ASTPropertyExpr by returning those org entities (of
     * the type indicated by the enclosing EntityNode) whose properties match
     * the values expressed in the ASTPropertyExpr.
     * <p>
     * If the ASTPropertyExpr is contained (by use of the &lt;DOT&gt; operator)
     * by another EntityNode, the results will be limited to those evaluated by
     * that EntityNode.
     */
    @Override
    public Object visit(ASTPropertyExpr aNode, RqlContext aContext) {
        try {
            EntityType propertyEntityType =
                    RQLParserUtil.getPropertyEntityType(aNode);
            // is this node part of a <DOT> expression
            EntityNode container = aNode.getContainer();
            if (container != null) {
                // limit the search to those results of the containing
                // EntityNode
                Collection<Object> result = new HashSet<Object>();
                if (container.getNodeResults() != null) {
                    for (Object entity : container.getNodeResults()) {
                        RQLScriptRelevantData data =
                                (RQLScriptRelevantData) entity;

                        // search each entity from the containing result for
                        // associated entities
                        Collection<IScriptRelevantData> match =
                                EntityProcessor.getInstance().process(data,
                                        aNode);
                        if (match != null) {
                            result.addAll(match);
                        }
                    }
                }
                if (result.isEmpty() && propertyEntityType != null
                        && !propertyEntityType.equals(EntityType.RESOURCE)) {
                    if (aNode.getProperty() == Property.NAME) {
                        List<String> additionalInfo = getAdditionalInfo(aNode);
                        ((ValidationContext) aContext)
                                .addErrorMessage(Messages.RQLExpressionValidator_NoEntitiesWithName,
                                        additionalInfo);
                    }

                    else if (aNode.getProperty() == Property.TYPE) {
                        List<String> additionalInfo = getAdditionalInfo(aNode);
                        ((ValidationContext) aContext)
                                .addErrorMessage(Messages.RQLExpressionValidator_NoEntitiesWithType,
                                        additionalInfo);
                    }
                }
                return (result);
            }

            // if no containing entity node
            // perform a raw search of all the entities available to the DAO
            ComparisonNodeComparator comparator =
                    new ComparisonNodeComparator(aNode);

            Collection<?> entities = Collections.emptyList();

            if (propertyEntityType != null
                    && !propertyEntityType.equals(EntityType.RESOURCE)) {

                if (aNode.getProperty() == Property.NAME) {
                    entities = dao.findByName(comparator);
                    if (entities == null || entities.isEmpty()) {
                        List<String> additionalInfo = getAdditionalInfo(aNode);
                        ((ValidationContext) aContext)
                                .addErrorMessage(Messages.RQLExpressionValidator_NoEntitiesWithName,
                                        additionalInfo);
                    } else if (containsOnlyDynamicRoots(entities)) {
                        List<String> additionalInfo = getAdditionalInfo(aNode);
                        ((ValidationContext) aContext)
                                .addErrorMessage(Messages.RQLExpressionValidator_DynamicRootReference,
                                        additionalInfo);
                    }
                }

                else if (aNode.getProperty() == Property.TYPE) {
                    entities = dao.findByMetaType(comparator);
                    if (entities == null || entities.isEmpty()) {
                        List<String> additionalInfo = getAdditionalInfo(aNode);
                        ((ValidationContext) aContext)
                                .addErrorMessage(Messages.RQLExpressionValidator_NoEntitiesWithType,
                                        additionalInfo);
                    }
                }

                else if (aNode.getProperty() == Property.GUID) {
                    entities = dao.findByGuid(comparator);
                    // We currently do not check for GUID at designtime
                }
            }
            return (entities);
        } catch (RqlException e) {
            throw e;
        } catch (Exception e) {
            throw new RqlException((ValidationContext) aContext, aNode, e);
        }
    }

    /**
     * @param entities
     *            A collection of entities to check.
     * @return true if the list contains only dynamic org model root references
     *         or nodes.
     */
    private boolean containsOnlyDynamicRoots(Collection<?> entities) {
        boolean onlyDynamic = true;
        for (Object entity : entities) {
            if (entity instanceof IScriptRelevantData) {
                IScriptRelevantData data = (IScriptRelevantData) entity;
                if (!RQLParserUtil.isDynamicRoot(data)) {
                    onlyDynamic = false;
                    break;
                }
            }
        }
        return onlyDynamic;
    }

    /**
     * @param aNode
     *            The node.
     * @return A list of additional info for the node.
     */
    private List<String> getAdditionalInfo(ASTPropertyExpr aNode) {
        String entityType = RQLParserUtil.getPropertyEntityTypeStr(aNode);
        List<String> additionalInfo = new ArrayList<String>();
        additionalInfo.add(entityType);
        additionalInfo.add(aNode.getValue());
        return additionalInfo;
    }

    /**
     * Processes the given ASTAttributeExpr by returning those org entities (of
     * the type indicated by the enclosing EntityNode) whose attributes match
     * the values expressed in the ASTAttributeExpr.
     * <p>
     * If the ASTAttributeExpr is contained (by use of the &lt;DOT&gt; operator)
     * by another EntityNode, the results will be limited to those evaluated by
     * that EntityNode.
     */
    @Override
    public Object visit(ASTAttributeExpr aNode, RqlContext aContext) {
        try {
            EntityNode container = aNode.getContainer();
            String attributeName = aNode.getName();
            if ((container != null) && (attributeName != null)) {
                // limit the search to those results of the containing
                // EntityNode
                boolean found = false;
                for (Object entity : container.getNodeResults()) {
                    if (entity instanceof IModelScriptRelevantData) {
                        IModelScriptRelevantData modelEntity =
                                (IModelScriptRelevantData) entity;
                        JsClass jsClass = modelEntity.getJsClass();
                        if (jsClass != null) {
                            List<JsAttribute> attributeList =
                                    jsClass.getAttributeList();
                            if (attributeList != null) {
                                for (JsAttribute jsAttribute : attributeList) {
                                    if (attributeName.equals(jsAttribute
                                            .getName())) {
                                        found = true;
                                        break;
                                    }
                                }
                                if (found) {
                                    break;
                                }
                            }
                        }
                    }
                }

                if (!found) {
                    EntityType type = EntityType.getType(aNode.getEntityNode());

                    List<String> additionalInfo = new ArrayList<String>(2);
                    additionalInfo.add(type.toString());
                    additionalInfo.add(attributeName);
                    ((ValidationContext) aContext)
                            .addErrorMessage(Messages.RQLExpressionValidator_NoAttributesWithName,
                                    additionalInfo);
                    return (null);
                }

            }

            return (Collections.emptyList());
        } catch (RqlException e) {
            throw e;
        } catch (Exception e) {
            throw new RqlException((ValidationContext) aContext, aNode, e);
        }
    }

    /**
     * Evaulates the visited Qualifier expression. If the qualifier is part of a
     * root node (that is, not connected to another node by a &ltDOT&gt), the
     * evaulation will consist of finding all Resources holding the Privilege or
     * Capability to which the qualifier belongs.
     * <p>
     * If the qualifier's node is contained within another node, the evaluation
     * will be performed as part of the evaluation of that containing node, and
     * this method will return an empty collection.
     * 
     * @see AbstractRqlParserVisitor#visit(ASTQualifierExpr, RqlContext)
     */
    @Override
    public Object visit(ASTQualifierExpr aNode, RqlContext aContext) {
        EntityNode entityNode = aNode.getEntityNode();
        Object result =
                entityNode.jjtAccept(QualifierVisitor.getInstance(), aContext);

        if (result == null) {
            ((ValidationContext) aContext)
                    .addErrorMessage(Messages.RQLExpressionValidator_QualifierNotValidForEntities,
                            Collections.singletonList(EntityType
                                    .getType(entityNode).toString()));
        }

        return (result);
    }

    /**
     * Processes the given ASTScope by returning the org entities associated
     * with those org entities already identified by the EntityNode to which the
     * ASTScope belongs.
     */
    @Override
    public Object visit(ASTScope aNode, RqlContext aContext) {
        EntityNode entityNode = aNode.getEntityNode();
        Object result =
                entityNode.jjtAccept(ScopeVisitor.getInstance(), aContext);

        if (result == null) {
            // TODO: scope is not allowed for the given entity
            // add error message to context
        }

        return (result);
    }

}
