/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.n2.resources;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.n2.resources.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Priority;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.resolvers.IFieldContextResolverExtension;
import com.tibco.xpd.xpdl2.resolvers.ProcessDataReferenceAndContexts;

/**
 * Data Reference resolver for N2 sub-process task with custom priority set to a
 * field.
 * 
 * @author aallway
 * @since 3 Jan 2014
 */
public class SubProcessTaskPriorityDataRefResolver implements
        IFieldContextResolverExtension {

    public SubProcessTaskPriorityDataRefResolver() {
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldContextResolverExtension#getDataReferences(com.tibco.xpd.xpdl2.Activity,
     *      java.util.Set)
     * 
     * @param activity
     * @param dataSet
     * @return
     */
    @Override
    public Set<ProcessDataReferenceAndContexts> getDataReferences(
            Activity activity, Set<ProcessRelevantData> dataSet) {
        Set<ProcessRelevantData> activityDataReferences =
                getActivityDataReferences(activity, dataSet);

        if (activityDataReferences != null) {
            Set<ProcessDataReferenceAndContexts> contextRefs =
                    new HashSet<ProcessDataReferenceAndContexts>();

            for (ProcessRelevantData data : activityDataReferences) {
                contextRefs
                        .add(new ProcessDataReferenceAndContexts(
                                data,
                                new DataReferenceContext(
                                        "CONTEXT_N2_SUBPROC_PRIORITY", //$NON-NLS-1$
                                        Messages.SubProcessTaskPriorityDataRefResolver_SubProcessPriorityDataRefContext_label2)));

            }

            return contextRefs;
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getActivityDataReferences(com.tibco.xpd.xpdl2.Activity,
     *      java.util.Set)
     * 
     * @param activity
     * @param dataSet
     * @return
     */
    @Override
    public Set<ProcessRelevantData> getActivityDataReferences(
            Activity activity, Set<ProcessRelevantData> dataSet) {

        if (TaskType.SUBPROCESS_LITERAL.equals(TaskObjectUtil
                .getTaskTypeStrict(activity))) {
            Priority priority = activity.getPriority();

            if (priority != null) {
                String priorityValue = priority.getValue();

                if (priorityValue != null && priorityValue.length() != 0) {

                    for (ProcessRelevantData data : dataSet) {
                        if (priorityValue.equals(data.getName())) {
                            Set<ProcessRelevantData> dataRefs =
                                    new HashSet<ProcessRelevantData>();

                            dataRefs.add(data);

                            return dataRefs;
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getSwapActivityDataNameReferencesCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Activity, java.util.Map)
     * 
     * @param editingDomain
     * @param activity
     * @param nameMap
     * @return
     */
    @Override
    public Command getSwapActivityDataNameReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> nameMap) {
        if (TaskType.SUBPROCESS_LITERAL.equals(TaskObjectUtil
                .getTaskTypeStrict(activity))) {
            Priority priority = activity.getPriority();

            if (priority != null) {
                String priorityValue = priority.getValue();

                if (priorityValue != null && priorityValue.length() != 0) {
                    if (nameMap.containsKey(priorityValue)) {
                        String newName = nameMap.get(priorityValue);

                        return SetCommand.create(editingDomain,
                                priority,
                                Xpdl2Package.eINSTANCE.getPriority_Value(),
                                newName);

                    }
                }
            }
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getTransitionDataReferences(com.tibco.xpd.xpdl2.Transition,
     *      java.util.Set)
     * 
     * @param transition
     * @param dataSet
     * @return
     */
    @Override
    public Set<ProcessRelevantData> getTransitionDataReferences(
            Transition transition, Set<ProcessRelevantData> dataSet) {
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getSwapActivityDataIdReferencesCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Activity, java.util.Map)
     * 
     * @param editingDomain
     * @param activity
     * @param idMap
     * @return
     */
    @Override
    public Command getSwapActivityDataIdReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> idMap) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getSwapTransitionDataIdReferencesCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Transition, java.util.Map)
     * 
     * @param editingDomain
     * @param transition
     * @param idMap
     * @return
     */
    @Override
    public Command getSwapTransitionDataIdReferencesCommand(
            EditingDomain editingDomain, Transition transition,
            Map<String, String> idMap) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getSwapTransitionDataNameReferencesCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Transition, java.util.Map)
     * 
     * @param editingDomain
     * @param transition
     * @param nameMap
     * @return
     */
    @Override
    public Command getSwapTransitionDataNameReferencesCommand(
            EditingDomain editingDomain, Transition transition,
            Map<String, String> nameMap) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getDeleteDataFromActivityCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Activity,
     *      com.tibco.xpd.xpdl2.ProcessRelevantData)
     * 
     * @param editingDomain
     * @param activity
     * @param data
     * @return
     */
    @Override
    public Command getDeleteDataFromActivityCommand(
            EditingDomain editingDomain, Activity activity,
            ProcessRelevantData data) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getDeleteDataFromTransitionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Transition,
     *      com.tibco.xpd.xpdl2.ProcessRelevantData)
     * 
     * @param editingDomain
     * @param transition
     * @param data
     * @return
     */
    @Override
    public Command getDeleteDataFromTransitionCommand(
            EditingDomain editingDomain, Transition transition,
            ProcessRelevantData data) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldContextResolverExtension#getDataReferences(com.tibco.xpd.xpdl2.Transition,
     *      java.util.Set)
     * 
     * @param transition
     * @param dataSet
     * @return
     */
    @Override
    public Set<ProcessDataReferenceAndContexts> getDataReferences(
            Transition transition, Set<ProcessRelevantData> dataSet) {
        // TODO Auto-generated method stub
        return null;
    }

}
