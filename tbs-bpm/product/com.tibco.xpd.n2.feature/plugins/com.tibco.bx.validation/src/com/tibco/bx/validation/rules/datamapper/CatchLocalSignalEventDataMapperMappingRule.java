/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.bx.validation.rules.datamapper;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.process.localsignal.datamapper.LocalSignalCatchDataMapperFilter;
import com.tibco.xpd.n2.process.localsignal.datamapper.LocalSignalScriptDataMapperProvider;
import com.tibco.xpd.process.datamapper.signal.util.SignalDataMapperConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Mapping rule for Local Signal Catch Data Mapper mappings.
 *
 * @author sajain
 * @since Jul 17, 2019
 */
public class CatchLocalSignalEventDataMapperMappingRule extends AbstractN2DataMapperMappingRule {

    /**
     * Class level field to track the activity being validated.
     */
    Activity act;

    /**
     * Catch signal data mapping is only supported for non-cancelling signal
     * events on a user task boundary
     */
    private static final String ISSUE_SIGNAL_MAPPING_ON_USERTASK_ONLY = "bx.signalMappingOnUserTaskBoundaryOnly"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#objectIsApplicable(org.eclipse.emf.ecore.EObject)
     * 
     * @param eo
     * @return
     */
    @Override
    protected boolean objectIsApplicable(EObject eo) {

        act = null;

        if (eo instanceof Activity) {
            act = (Activity) eo;

            IFilter filter = new LocalSignalCatchDataMapperFilter();

            return filter.select(act) && super.objectIsApplicable(eo);
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#getScriptDataMapperProvider()
     * 
     * @return
     */
    @Override
    protected IScriptDataMapperProvider getScriptDataMapperProvider() {
        return new LocalSignalScriptDataMapperProvider(getMappingDirection());
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return SignalDataMapperConstants.LOCAL_SIGNAL_CATCH;
    }

    /**
     * @see com.tibco.bx.validation.rules.datamapper.AbstractN2DataMapperMappingRule#performAdditionalMappingsValidation(java.lang.Object,
     *      java.util.Collection)
     *
     * @param objectToValidate
     * @param mappings
     */
    /**
     * @see com.tibco.bx.validation.rules.datamapper.AbstractN2DataMapperMappingRule#performAdditionalMappingsValidation(java.lang.Object,
     *      java.util.Collection)
     *
     * @param objectToValidate
     * @param mappings
     */
    @Override
    protected void performAdditionalMappingsValidation(Object objectToValidate, Collection<Object> mappings) {
        super.performAdditionalMappingsValidation(objectToValidate, mappings);

        if (objectToValidate instanceof Activity) {

            Activity activity = (Activity) objectToValidate;

            ScriptDataMapper sdm = getScriptDataMapperProvider().getScriptDataMapper(activity);

            MappingDirection mappingDirection = getMappingDirection();

            if (MappingDirection.OUT.equals(mappingDirection)
                    && (null != sdm && null != sdm.getDataMappings() && !sdm.getDataMappings().isEmpty())) {
                boolean isAttachedToUserTask = false;

                Activity taskAttachedTo = EventObjectUtil.getTaskAttachedTo(activity);
                if (taskAttachedTo != null) {
                    if (TaskType.USER_LITERAL.equals(TaskObjectUtil.getTaskTypeStrict(taskAttachedTo))) {
                        isAttachedToUserTask = true;
                    }
                }

                if (!isAttachedToUserTask || !EventObjectUtil.isNonCancellingEvent(activity)) {
                    /*
                     * Catch signal data mapping is only supported for
                     * non-cancelling signal events on a user task boundary
                     */
                    addIssue(ISSUE_SIGNAL_MAPPING_ON_USERTASK_ONLY, activity);
                }
            }
        }

    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getMappingDirection()
     * 
     * @return
     */
    @Override
    protected MappingDirection getMappingDirection() {
        return MappingDirection.OUT;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getScriptType()
     * 
     * @return
     */
    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.CATCH_SIGNAL_EVENTMAPPING;
    }
}
