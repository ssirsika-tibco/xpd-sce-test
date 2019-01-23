/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.datamapper;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.process.datamapper.scriptdata.UserTaskScriptDataMapperProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;

/**
 * Specific validation rule for ScriptTask data mapper.
 * 
 * @author nwilson
 * @since 21 Apr 2015
 */
public class RescheduleUserTaskDataMapperMappingRule
        extends AbstractN2ProcessDataMapperMappingRule {

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractN2DataMapperMappingRule#getScriptDataMapperProvider()
     * 
     * @return
     */
    @Override
    protected IScriptDataMapperProvider getScriptDataMapperProvider() {
        return new UserTaskScriptDataMapperProvider(getDataMapperContext());
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getMappingDirection()
     * 
     * @return
     */
    @Override
    protected MappingDirection getMappingDirection() {
        return MappingDirection.IN;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getScriptType()
     * 
     * @return
     */
    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.DATA_MAPPER_WM_MAPPING_SCRIPTS;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractN2DataMapperMappingRule#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return ProcessScriptContextConstants.RESCHEDULE_USER_TASK;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#getMappingTypeDescription(org.eclipse.emf.ecore.EObject)
     *
     * @param objectToValidate
     * @return
     */
    @Override
    protected String getMappingTypeDescription(EObject objectToValidate) {
        return Messages.RescheduleUserTaskDataMapperMappingRule_ScriptDescription;
    }
}
