/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.policies.cycletypegadget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorObjectType;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.adapters.EventAdapter;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.parts.EventEditPart;
import com.tibco.xpd.processwidget.policies.EditPolicyEnablementProvider;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.AbstractClickOrDragGadgetInfo;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;

/**
 * Click Gadget policy for cycling event trigger type.
 * 
 * @author aallway
 * @since 3.2
 */
public class CycleEventTypeGadgetPolicy extends
        AbstractCycleObjectTypeGadgetPolicy {

    public CycleEventTypeGadgetPolicy(AdapterFactory adapterFactory,
            EditingDomain editingDomain,
            EditPolicyEnablementProvider policyEnabledmentProvider) {
        super(adapterFactory, editingDomain, policyEnabledmentProvider);
    }

    @Override
    protected List<AbstractClickOrDragGadgetInfo> getClickOrDragGadgetInfos() {
        //
        // Don't allow change of type for events that implement process
        // interface methods.
        //
        if (getHost() instanceof EventEditPart) {
            EventAdapter adapter =
                    ((EventEditPart) getHost()).getEventAdapter();
            if (adapter != null) {
                if (adapter.isImplementingEvent()) {
                    return Collections.emptyList();
                }
            }
        }

        return super.getClickOrDragGadgetInfos();
    }

    @Override
    protected Command createCycleNextObjectTypeCommand(
            EditingDomain editingDomain, EditPart hostEditPart, Object newType) {
        if (hostEditPart instanceof EventEditPart
                && newType instanceof EventTriggerType) {
            EventEditPart event = (EventEditPart) hostEditPart;

            return new EMFCommandWrapper(editingDomain, event.getEventAdapter()
                    .getSetEventTriggerTypeCommand(editingDomain,
                            (EventTriggerType) newType));
        }

        return UnexecutableCommand.INSTANCE;
    }

    @Override
    protected Object getCurrentType(EditPart hostEditPart) {
        if (hostEditPart instanceof EventEditPart) {
            EventEditPart event = (EventEditPart) hostEditPart;

            return event.getEventAdapter().getEventTriggerType();
        }
        return null;
    }

    @Override
    protected Object[] getObjectTypes(EditPart hostEditPart) {
        List<EventTriggerType> types = new ArrayList<EventTriggerType>();

        if (hostEditPart instanceof EventEditPart) {
            EventEditPart event = (EventEditPart) hostEditPart;

            EventFlowType flowType = event.getEventAdapter().getFlowType();

            Set<ProcessEditorObjectType> excludedObjectTypes =
                    event.getProcessWidget().getExcludedObjectTypes();

            if (EventFlowType.FLOW_START_LITERAL.equals(flowType)) {
                if (ProcessWidgetType.DECISION_FLOW.equals(event
                        .getProcessWidgetType())) {
                    addTypeIfNotExcluded(types,
                            excludedObjectTypes,
                            flowType,
                            EventTriggerType.EVENT_NONE_LITERAL);
                    addTypeIfNotExcluded(types,
                            excludedObjectTypes,
                            flowType,
                            EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL);

                } else {
                    addTypeIfNotExcluded(types,
                            excludedObjectTypes,
                            flowType,
                            EventTriggerType.EVENT_NONE_LITERAL);
                    addTypeIfNotExcluded(types,
                            excludedObjectTypes,
                            flowType,
                            EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL);
                    addTypeIfNotExcluded(types,
                            excludedObjectTypes,
                            flowType,
                            EventTriggerType.EVENT_TIMER_LITERAL);
                    addTypeIfNotExcluded(types,
                            excludedObjectTypes,
                            flowType,
                            EventTriggerType.EVENT_CONDITIONAL_LITERAL);
                    addTypeIfNotExcluded(types,
                            excludedObjectTypes,
                            flowType,
                            EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL);
                    addTypeIfNotExcluded(types,
                            excludedObjectTypes,
                            flowType,
                            EventTriggerType.EVENT_MULTIPLE_CATCH_LITERAL);
                }

            } else if (EventFlowType.FLOW_INTERMEDIATE_LITERAL.equals(flowType)) {
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        flowType,
                        EventTriggerType.EVENT_NONE_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        flowType,
                        EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        flowType,
                        EventTriggerType.EVENT_MESSAGE_THROW_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        flowType,
                        EventTriggerType.EVENT_TIMER_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        flowType,
                        EventTriggerType.EVENT_CONDITIONAL_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        flowType,
                        EventTriggerType.EVENT_LINK_CATCH_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        flowType,
                        EventTriggerType.EVENT_LINK_THROW_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        flowType,
                        EventTriggerType.EVENT_MULTIPLE_CATCH_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        flowType,
                        EventTriggerType.EVENT_MULTIPLE_THROW_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        flowType,
                        EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        flowType,
                        EventTriggerType.EVENT_SIGNAL_THROW_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        flowType,
                        EventTriggerType.EVENT_ERROR_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        flowType,
                        EventTriggerType.EVENT_COMPENSATION_CATCH_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        flowType,
                        EventTriggerType.EVENT_COMPENSATION_THROW_LITERAL);
                addTypeIfNotExcluded(types,
                        excludedObjectTypes,
                        flowType,
                        EventTriggerType.EVENT_CANCEL_LITERAL);

            } else if (EventFlowType.FLOW_END_LITERAL.equals(flowType)) {
                if (ProcessWidgetType.DECISION_FLOW.equals(event
                        .getProcessWidgetType())) {
                    addTypeIfNotExcluded(types,
                            excludedObjectTypes,
                            flowType,
                            EventTriggerType.EVENT_NONE_LITERAL);
                    addTypeIfNotExcluded(types,
                            excludedObjectTypes,
                            flowType,
                            EventTriggerType.EVENT_MESSAGE_THROW_LITERAL);

                } else {
                    addTypeIfNotExcluded(types,
                            excludedObjectTypes,
                            flowType,
                            EventTriggerType.EVENT_NONE_LITERAL);
                    addTypeIfNotExcluded(types,
                            excludedObjectTypes,
                            flowType,
                            EventTriggerType.EVENT_MESSAGE_THROW_LITERAL);
                    addTypeIfNotExcluded(types,
                            excludedObjectTypes,
                            flowType,
                            EventTriggerType.EVENT_MULTIPLE_THROW_LITERAL);
                    addTypeIfNotExcluded(types,
                            excludedObjectTypes,
                            flowType,
                            EventTriggerType.EVENT_SIGNAL_THROW_LITERAL);
                    addTypeIfNotExcluded(types,
                            excludedObjectTypes,
                            flowType,
                            EventTriggerType.EVENT_ERROR_LITERAL);
                    addTypeIfNotExcluded(types,
                            excludedObjectTypes,
                            flowType,
                            EventTriggerType.EVENT_COMPENSATION_THROW_LITERAL);
                    addTypeIfNotExcluded(types,
                            excludedObjectTypes,
                            flowType,
                            EventTriggerType.EVENT_CANCEL_LITERAL);
                    addTypeIfNotExcluded(types,
                            excludedObjectTypes,
                            flowType,
                            EventTriggerType.EVENT_TERMINATE_LITERAL);
                }
            }
        }

        return types.toArray();
    }

    /**
     * Add the event trigger type to set if not excluded by some
     * processEditorConfiguration/ObjectTypeExclusion.
     * 
     * @param types
     * @param flowType
     * @param triggerType
     * @param excludedObjectTypes
     */
    private void addTypeIfNotExcluded(List<EventTriggerType> types,
            Set<ProcessEditorObjectType> excludedObjectTypes,
            EventFlowType flowType, EventTriggerType triggerType) {
        if (!excludedObjectTypes.contains(triggerType
                .getProcessEditorObjectType(flowType))) {
            types.add(triggerType);
        }
    }
}