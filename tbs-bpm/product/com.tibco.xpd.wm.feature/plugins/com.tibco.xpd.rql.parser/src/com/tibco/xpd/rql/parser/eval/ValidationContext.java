/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.rql.parser.eval;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.tibco.n2.de.rql.parser.base.RqlContext;
import com.tibco.xpd.rql.model.RQLConsts;
import com.tibco.xpd.rql.parser.jccvalidator.AbstractJCCValidator;
import com.tibco.xpd.rql.parser.util.RQLParserUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;

/**
 * @author pwatson
 */
public class ValidationContext implements RqlContext
{
    private String expression;
    
    private AbstractJCCValidator validator;
    
    private EntityDao orgDao;
    private EntityDao orgUnitDao;
    private EntityDao positionDao;
    private EntityDao locationDao;
    private EntityDao privilegeDao;
    private EntityDao capabilityDao;
    private EntityDao groupDao;
    private EntityDao resourceDao;
    private Map<String, List<IScriptRelevantData>> supportedScriptRelevantDataMap;
    
    
    public ValidationContext(String aExpression,
                             AbstractJCCValidator aValidator)
    {
        expression = aExpression;
        validator = aValidator;
    }

    public String getExpression()
    {
        return(expression);
    }
    
    public void addErrorMessage(String errorMessage, List<String> additionalAttributes)
    {
        if(validator != null){
            validator.addErrorMessage(errorMessage, additionalAttributes);
        }
    }

    public EntityDao getOrgDao() {
        if (orgDao == null) {
            // get the collection of IScriptRelevantData that wrap
            // the instances of Organization from the model
            // create the DAO that will search those model entities
            orgDao =
                    new EntityDaoImpl(
                            EntityType.ORGANIZATION,
                            RQLParserUtil
                                    .getScriptRelevantDataOfType(EntityType.ORGANIZATION.toString(),
                                            getSupportedScriptRelevantData()));
        }
        return (orgDao);
    }
    
    public EntityDao getOrgUnitDao()
    {
        if (orgUnitDao == null)
        {
            // get the collection of IScriptRelevantData that wrap
            // the instances of OrgUnit from the model            
            // create the DAO that will search those model entities
            orgUnitDao = new EntityDaoImpl(EntityType.ORGANIZATIONAL_UNIT, RQLParserUtil
                    .getScriptRelevantDataOfType(EntityType.ORGANIZATIONAL_UNIT.toString(),
                            getSupportedScriptRelevantData()));
        }
        return(orgUnitDao);
    }
    
    public EntityDao getPositionDao()
    {
        if (positionDao == null)
        {
            // get the collection of IScriptRelevantData that wrap
            // the instances of Position from the model
            // create the DAO that will search those model entities
            positionDao = new EntityDaoImpl(EntityType.POSITION, RQLParserUtil
                    .getScriptRelevantDataOfType(EntityType.POSITION.toString(),
                            getSupportedScriptRelevantData()));
        }
        return(positionDao);
    }
    
    public EntityDao getLocationDao()
    {
        if (locationDao == null)
        {
            // get the collection of IScriptRelevantData that wrap
            // the instances of Location from the model
            // create the DAO that will search those model entities
            locationDao = new EntityDaoImpl(EntityType.LOCATION, RQLParserUtil
                    .getScriptRelevantDataOfType(EntityType.LOCATION.toString(),
                            getSupportedScriptRelevantData()));
        }
        return(locationDao);
    }
    
    public EntityDao getGroupDao()
    {
        if (groupDao == null)
        {
            // get the collection of IScriptRelevantData that wrap
            // the instances of Group from the model
            // create the DAO that will search those model entities
            groupDao = new EntityDaoImpl(EntityType.GROUP, RQLParserUtil
                    .getScriptRelevantDataOfType(EntityType.GROUP.toString(),
                            getSupportedScriptRelevantData()));
        }
        return(groupDao);
    }
    
    public EntityDao getPrivilegeDao()
    {
        if (privilegeDao == null)
        {
            // get the collection of IScriptRelevantData that wrap
            // the instances of Privilege from the model
            // create the DAO that will search those model entities
            privilegeDao = new EntityDaoImpl(EntityType.PRIVILEGE, RQLParserUtil
                    .getScriptRelevantDataOfType(EntityType.PRIVILEGE.toString(),
                            getSupportedScriptRelevantData()));
        }
        return(privilegeDao);
    }
    
    public EntityDao getCapabilityDao()
    {
        if (capabilityDao == null)
        {
            // get the collection of IScriptRelevantData that wrap
            // the instances of Capability from the model
            // create the DAO that will search those model entities
            capabilityDao = new EntityDaoImpl(EntityType.CAPABILITY, RQLParserUtil
                    .getScriptRelevantDataOfType(EntityType.CAPABILITY.toString(),
                            getSupportedScriptRelevantData()));
        }
        return(capabilityDao);
    }
    
    public EntityDao getResourceDao()
    {
        if (resourceDao == null)
        {
            resourceDao = new EntityDaoImpl(EntityType.RESOURCE, RQLParserUtil
                    .getScriptRelevantDataOfType(EntityType.RESOURCE.toString(),
                            getSupportedScriptRelevantData()));
        }
        return(resourceDao);
    }
    
    public Collection<List<IScriptRelevantData>> getSupportedScriptRelevantData(){
        if(getSupportedScriptRelevantDataMap() != null){
            return getSupportedScriptRelevantDataMap().values();
        }
        return Collections.emptyList();
    }
    
    public Map<String, List<IScriptRelevantData>> getSupportedScriptRelevantDataMap(){
        return supportedScriptRelevantDataMap;
    }
    
    public void setSupportedScriptRelevantDataMap(
            Map<String, List<IScriptRelevantData>> supportedScriptRelevantDataMap) {
        this.supportedScriptRelevantDataMap = supportedScriptRelevantDataMap;
    }
}
