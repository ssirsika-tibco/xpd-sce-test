/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.policies.cycletypegadget;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorObjectType;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.processwidget.policies.EditPolicyEnablementProvider;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;

/**
 * Click Gadget policy for cycling task type.
 * 
 * @author aallway
 * @since 3.2
 */
public class CycleTaskTypeGadgetPolicy extends
        AbstractCycleObjectTypeGadgetPolicy {

    public CycleTaskTypeGadgetPolicy(AdapterFactory adapterFactory,
            EditingDomain editingDomain,
            EditPolicyEnablementProvider policyEnabledmentProvider) {
        super(adapterFactory, editingDomain, policyEnabledmentProvider);
    }

    @Override
    protected Command createCycleNextObjectTypeCommand(
            EditingDomain editingDomain, EditPart hostEditPart, Object newType) {
        if (hostEditPart instanceof TaskEditPart && newType instanceof TaskType) {
            TaskEditPart task = (TaskEditPart) hostEditPart;

            return new EMFCommandWrapper(editingDomain, task
                    .getActivityAdapter().getSetTaskTypeCommand(editingDomain,
                            (TaskType) newType));

        }

        return UnexecutableCommand.INSTANCE;
    }

    @Override
    protected Object getCurrentType(EditPart hostEditPart) {
        if (hostEditPart instanceof TaskEditPart) {
            TaskEditPart task = (TaskEditPart) hostEditPart;

            return task.getActivityAdapter().getTaskType();
        }
        return null;
    }

    @Override
    protected Object[] getObjectTypes(EditPart hostEditPart) {
        if (hostEditPart instanceof TaskEditPart) {
            TaskEditPart task = (TaskEditPart) hostEditPart;

            /*
             * XPD-2516: Add the task type to set if not excluded by some
             * processEditorConfiguration/ObjectTypeExclusion.
             */
            List<TaskType> types = new ArrayList<TaskType>();

            Set<ProcessEditorObjectType> excludedObjectTypes =
                    task.getProcessWidget().getExcludedObjectTypes();

            if (ProcessWidgetType.TASK_LIBRARY.equals(task
                    .getProcessWidgetType())) {

                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        TaskType.NONE_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        TaskType.USER_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        TaskType.MANUAL_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        TaskType.SERVICE_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        TaskType.SCRIPT_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        TaskType.SEND_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        TaskType.SUBPROCESS_LITERAL);

            } else if (ProcessWidgetType.DECISION_FLOW.equals(task
                    .getProcessWidgetType())) {
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        TaskType.DTABLE_LITERAL);

            } else {
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        TaskType.NONE_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        TaskType.USER_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        TaskType.MANUAL_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        TaskType.SERVICE_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        TaskType.SCRIPT_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        TaskType.SEND_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        TaskType.RECEIVE_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        TaskType.REFERENCE_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        TaskType.SUBPROCESS_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        TaskType.EMBEDDED_SUBPROCESS_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        TaskType.EVENT_SUBPROCESS_LITERAL);
            }

            return types.toArray();
        }
        return null;
    }

    /**
     * Add the task type to set if not excluded by some
     * processEditorConfiguration/ObjectTypeExclusion.
     * 
     * @param types
     * @param taskType
     * @param excludedObjectTypes
     */
    private void addTypeIfNotExcluded(List<TaskType> types,
            Set<ProcessEditorObjectType> excludedObjectTypes, TaskType taskType) {
        if (!excludedObjectTypes
                .contains(taskType.getProcessEditorObjectType())) {
            types.add(taskType);
        }
    }
}