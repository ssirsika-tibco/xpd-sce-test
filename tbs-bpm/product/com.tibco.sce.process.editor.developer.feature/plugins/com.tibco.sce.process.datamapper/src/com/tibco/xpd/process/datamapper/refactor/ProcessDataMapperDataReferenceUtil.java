/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.process.datamapper.refactor;

import com.tibco.xpd.process.datamapper.common.ActivityInterfaceDataMapperContentContributor;
import com.tibco.xpd.process.datamapper.common.ProcessDataMapperContentContributor;
import com.tibco.xpd.process.datamapper.refactor.IDataReferenceContextProvider.ContextSide;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;

/**
 * Helper methods for data references from process data mapper mappings/scripts
 * 
 * @author aallway
 * @since 18 Jun 2015
 */
public class ProcessDataMapperDataReferenceUtil {

    /**
     * Gets the appropriate data reference for the given script data mapper
     * context <b>if it is one that utilises the process data related
     * {@link ActivityInterfaceDataMapperContentContributor#ACTIVITY_INTERFACE_CONTRIBUTOR_ID}
     * or
     * {@link ProcessDataMapperContentContributor#PROCESS_DATA_CONTRIBUTOR_ID}
     * contributions.
     * <p>
     * If the {@link ScriptDataMapper} is not related to process data side of
     * mappings then <code>null</code> is returned
     * 
     * @param scriptDataMapper
     * @param getContextForSourceSideSpecifically
     *            <code>true</code> if querying specifically about the mapping
     *            source side data. In which case will return <code>null</code>
     *            if the {@link ScriptDataMapper} context is for a scenario that
     *            does not have process data as source. If <code>false</code>
     *            then will return context if either source or target is process
     *            data related.
     * 
     * @return The appropriate data reference for the given script data mapper
     *         context if it is process data side of mapping or
     *         <code>null</code> if not.
     */
    public static DataReferenceContext getDataReferenceContext(
            ScriptDataMapper scriptDataMapper,
            boolean getContextForSourceSideSpecifically) {

        String context = scriptDataMapper.getMapperContext();

        if (ProcessScriptContextConstants.INITIATED_SCRIPT_TASK.equals(context)) {
            return DataReferenceContext.CONTEXT_INITIATE_SCRIPT;

        } else if (ProcessScriptContextConstants.COMPLETED_SCRIPT_TASK
                .equals(context)) {
            return DataReferenceContext.CONTEXT_COMPLETE_SCRIPT;

        } else if (ProcessScriptContextConstants.CANCELLED_SCRIPT_TASK
                .equals(context)) {
            return DataReferenceContext.CONTEXT_CANCEL_SCRIPT;

        } else if (ProcessScriptContextConstants.DEADLINE_EXPIRED_SCRIPT_TASK
                .equals(context)) {
            return DataReferenceContext.CONTEXT_TIMEOUT_SCRIPT;

        } else if (ProcessScriptContextConstants.ADHOC_PRECONDITION
                .equals(context)) {
            return DataReferenceContext.CONTEXT_ADHOC_PRECONDITION;

        } else if (ProcessScriptContextConstants.SCHEDULE_USER_TASK
                .equals(context)) {
            return DataReferenceContext.CONTEXT_SCHEDULE_SCRIPT;

        } else if (ProcessScriptContextConstants.RESCHEDULE_USER_TASK
                .equals(context)) {
            return DataReferenceContext.CONTEXT_RESCHEDULE_SCRIPT;

        } else if (ProcessScriptContextConstants.OPEN_USER_TASK.equals(context)) {
            return DataReferenceContext.CONTEXT_OPEN_SCRIPT;

        } else if (ProcessScriptContextConstants.CLOSE_USER_TASK
                .equals(context)) {
            return DataReferenceContext.CONTEXT_CLOSE_SCRIPT;

        } else if (ProcessScriptContextConstants.SUBMIT_USER_TASK
                .equals(context)) {
            return DataReferenceContext.CONTEXT_SUBMIT_SCRIPT;

        } else if (ProcessScriptContextConstants.SCRIPT_TASK.equals(context)) {
            return DataReferenceContext.CONTEXT_SCRIPT_TASK_SCRIPT;

        } else {
            return DataReferenceContextProviderHelper
                    .getDataReferenceContext(context,
                            getContextForSourceSideSpecifically ? ContextSide.SOURCE
                                    : ContextSide.ANY);
        }

    }

    /**
     * @param contributorId
     * 
     * @return <code>true</code> if the data mapper contributor idis known to
     *         represent process data in a data mapping
     */
    public static boolean isProcessDataContributionId(String contributorId) {

        if (ActivityInterfaceDataMapperContentContributor.ACTIVITY_INTERFACE_CONTRIBUTOR_ID
                .equals(contributorId)
                || ProcessDataMapperContentContributor.PROCESS_DATA_CONTRIBUTOR_ID
                        .equals(contributorId)) {
            return true;
        }
        return false;
    }

}
