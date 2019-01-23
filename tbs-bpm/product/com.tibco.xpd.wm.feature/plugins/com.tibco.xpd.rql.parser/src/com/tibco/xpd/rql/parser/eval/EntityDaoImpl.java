/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.rql.parser.eval;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.internal.client.IModelScriptRelevantData;

/**
 * Used to separate the evaluation of the RQL query from the source of the
 * data on which the query will be evaluated. The DAO allows the query to
 * perform simple searches on the model data without knowing how that data
 * is accessed or traversed.
 * 
 * @author pwatson
 */
public class EntityDaoImpl implements EntityDao
{
    private EntityType entityType;
    
    private Collection<IScriptRelevantData> entityData;
    
    public EntityDaoImpl(EntityType aEntityType,
                  Collection<IScriptRelevantData> aEntityData)
    {
        entityType = aEntityType;
        entityData = aEntityData;
    }
    
    private boolean hasData()
    {
        return((entityData != null) && (! entityData.isEmpty()));
    }
    
    /* (non-Javadoc)
     * @see com.tibco.xpd.rql.parser.eval.EntityDao#findByName(com.tibco.xpd.rql.parser.eval.ComparisonNodeComparator)
     */
    public Collection<IScriptRelevantData> findByName(ComparisonNodeComparator aComparator)
    {
        if (! hasData())
        {
            return(Collections.emptyList());
        }
        
        Collection<IScriptRelevantData> result = new HashSet<IScriptRelevantData>();
        for (IScriptRelevantData dataElement : entityData)
        {
            if (dataElement == null)
            {
                continue;
            }
            
            if (aComparator.eval(dataElement.getName()))
            {
                result.add(dataElement);
            }
        }
        
        return(result);
    }
    
    /* (non-Javadoc)
     * @see com.tibco.xpd.rql.parser.eval.EntityDao#findByMetaType(com.tibco.xpd.rql.parser.eval.ComparisonNodeComparator)
     */
    public Collection<IScriptRelevantData> findByMetaType(ComparisonNodeComparator aComparator)
    {
        if (! hasData())
        {
            return(Collections.emptyList());
        }
        
        Collection<IScriptRelevantData> result = new HashSet<IScriptRelevantData>();
        for (IScriptRelevantData dataElement : entityData)
        {
            if (dataElement == null)
            {
                continue;
            }
            
            if (dataElement instanceof IModelScriptRelevantData)
            {
                IModelScriptRelevantData modelData = (IModelScriptRelevantData)dataElement;
                if (modelData.getModelType() instanceof OrgElementType)
                {
                    String typeName = ((OrgElementType)modelData.getModelType()).getName();
                    if (aComparator.eval(typeName))
                    {
                        result.add(dataElement);
                    }
                }
            }
        }
        
        return(result);
    }
    
    /* (non-Javadoc)
     * @see com.tibco.xpd.rql.parser.eval.EntityDao#findByGuid(com.tibco.xpd.rql.parser.eval.ComparisonNodeComparator)
     */
    public Collection<IScriptRelevantData> findByGuid(ComparisonNodeComparator aComparator)
    {
        if (! hasData())
        {
            return(Collections.emptyList());
        }
        
        Collection<IScriptRelevantData> result = new HashSet<IScriptRelevantData>();
        for (IScriptRelevantData dataElement : entityData)
        {
            if (dataElement == null)
            {
                continue;
            }
            
            if (aComparator.eval(dataElement.getId()))
            {
                result.add(dataElement);
            }
        }
        
        return(result);
    }
    
    /* (non-Javadoc)
     * @see com.tibco.xpd.rql.parser.eval.EntityDao#findByAttribute(com.tibco.xpd.rql.parser.eval.ComparisonNodeComparator)
     */
    public Collection<IScriptRelevantData> findByAttribute(ComparisonNodeComparator aComparator)
    {
        if (! hasData())
        {
            return(Collections.emptyList());
        }
        
        return(Collections.emptyList());
    }
    
    public Collection<IScriptRelevantData> getEntityData() {
        return entityData;
    }
}
