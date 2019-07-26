/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.event;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.EventHandlerFlowStrategy;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * LHS property details section for intermediate event activity used at the
 * start of an event handler flow
 *
 * @author aallway
 * @since 26 Jul 2019
 */
public class EventTriggerTypeIntermediateRequestSection extends AbstractFilteredTransactionalSection {

    private Composite concurrentFlowsContainer;

    private Button serializeConcurrentFlows;

    private Button allowConcurrentFlows;

    private Group eventHandlerGroup;

    public EventTriggerTypeIntermediateRequestSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    @Override
    protected Composite doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        GridData gData;
        Composite root = toolkit.createComposite(parent);

        GridLayout gl = new GridLayout(2, false);
        root.setLayout(gl);

        eventHandlerGroup = new Group(root, SWT.SHADOW_ETCHED_IN);
        eventHandlerGroup.setText(Messages.EventTriggerTypeMessageSection_EventHandlerDetailGroupHeader_label);
        eventHandlerGroup.setBackground(root.getBackground());
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 2;
        eventHandlerGroup.setLayoutData(gridData);
        GridLayout gLayout = new GridLayout(1, false);
        gLayout.horizontalSpacing = 0;
        gLayout.verticalSpacing = 0;
        eventHandlerGroup.setLayout(gLayout);

        hideUIgroup(eventHandlerGroup, true);

        createConcurrentFlowsControls(toolkit, eventHandlerGroup);

        return root;
    }

    /**
     * Control the presence/absence of an entire group in the UI
     * 
     * @param group
     *            group to hide/show
     * @param hide
     */
    private void hideUIgroup(Group group, boolean hide) {

        GridData data = (GridData) group.getLayoutData();

        if (data.exclude != hide) {

            group.setVisible(!(data.exclude = hide));
            group.setLayoutData(data);
            group.layout();
            group.getParent().layout();

        }
    }

    /**
     * Sid XPD-3518: Added Concurrency handling configuration for event
     * handlers.
     * 
     * @param toolkit
     * @param root
     */
    private void createConcurrentFlowsControls(XpdFormToolkit toolkit, Composite root) {

        concurrentFlowsContainer = toolkit.createComposite(root, SWT.NONE);
        GridData gdLabel = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
        gdLabel.verticalIndent = 7;
        GridData gdContainer = new GridData(GridData.FILL_HORIZONTAL);
        gdContainer.horizontalSpan = 2;

        concurrentFlowsContainer.setLayoutData(gdContainer);
        concurrentFlowsContainer.setLayout(new GridLayout(1, false));

        serializeConcurrentFlows = toolkit.createButton(concurrentFlowsContainer,
                Messages.EventTriggerTypeMessageSection_SerializeConcurrent_button,
                SWT.RADIO);
        serializeConcurrentFlows.setToolTipText(Messages.EventTriggerTypeMessageSection_SerializeConcurrent_tooltip);
        serializeConcurrentFlows.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(serializeConcurrentFlows);

        allowConcurrentFlows = toolkit.createButton(concurrentFlowsContainer,
                Messages.EventTriggerTypeMessageSection_AllowConcurrent_label,
                SWT.RADIO);
        allowConcurrentFlows.setToolTipText(Messages.EventTriggerTypeMessageSection_AllowConcurrent_tooltip);
        allowConcurrentFlows.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        manageControl(allowConcurrentFlows);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang
     * .Object)
     */
    @Override
    protected Command doGetCommand(Object obj) {
        CompoundCommand cmd = null;

        EditingDomain ed = getEditingDomain();
        Activity activity = getActivity();

        if (activity != null && ed != null && activity.getEvent() instanceof IntermediateEvent) {

            IntermediateEvent intermediateEvent = (IntermediateEvent) activity.getEvent();

            if (obj == serializeConcurrentFlows) {
                cmd = new CompoundCommand(Messages.EventTriggerTypeMessageSection_SetEventHandlerFlowStrategy_menu);

                cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                        intermediateEvent,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerFlowStrategy(),
                        EventHandlerFlowStrategy.SERIALIZE_CONCURRENT));

            } else if (obj == allowConcurrentFlows) {
                cmd = new CompoundCommand(Messages.EventTriggerTypeMessageSection_SetEventHandlerFlowStrategy_menu);

                cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                        intermediateEvent,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerFlowStrategy(),
                        EventHandlerFlowStrategy.ALLOW_CONCURRENT));
            }
        }
        return cmd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {

        if (concurrentFlowsContainer == null || concurrentFlowsContainer.isDisposed() || getActivity() == null) {
            return;
        }

        /*
         * Only show event handler controls for event handler activity
         */
        Activity activity = getActivity();

        if (activity != null && activity.getEvent() instanceof IntermediateEvent) {

            boolean isEventHandler = Xpdl2ModelUtil.isEventHandlerActivity(activity);

            hideUIgroup(eventHandlerGroup,
                    !isEventHandler || !ProcessDestinationUtil.isBPMDestinationSelected(activity.getProcess()));

            if (isEventHandler) {

                EventHandlerFlowStrategy flowStrategy =
                        (EventHandlerFlowStrategy) Xpdl2ModelUtil.getOtherAttribute(activity.getEvent(),
                                XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerFlowStrategy());

                if (EventHandlerFlowStrategy.ALLOW_CONCURRENT.equals(flowStrategy)) {
                    serializeConcurrentFlows.setSelection(false);
                    allowConcurrentFlows.setSelection(true);

                } else if (EventHandlerFlowStrategy.SERIALIZE_CONCURRENT.equals(flowStrategy)) {
                    serializeConcurrentFlows.setSelection(true);
                    allowConcurrentFlows.setSelection(false);

                } else {
                    serializeConcurrentFlows.setSelection(false);
                    allowConcurrentFlows.setSelection(false);
                }

            }
        }
    }

    /**
     * Get the selected input object as an activity
     * 
     * @return activity for event or null on error.
     */
    private Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            Activity act = (Activity) o;

            return act;
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#shouldRefresh(java.util.List)
     * 
     * @param notifications
     * @return
     */
    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        if (super.shouldRefresh(notifications)) {
            return true;
        }

        Activity activity = getActivity();

        if (activity != null) {
            String activityId = activity.getId();

            for (Notification notification : notifications) {
                /*
                 * Refresh is connection made/removed from event (could change
                 * whether it is an event handler or not.
                 */
                if (Notification.SET == notification.getEventType()
                        && Xpdl2Package.eINSTANCE.getTransition_To().equals(notification.getFeature())
                        && notification.getNotifier() instanceof Transition) {

                    if (activityId.equals(notification.getOldValue())
                            || activityId.equals(notification.getNewValue())) {
                        return true;
                    }

                } else if (Notification.ADD == notification.getEventType()
                        && Xpdl2Package.eINSTANCE.getFlowContainer_Transitions().equals(notification.getFeature())
                        && notification.getNewValue() instanceof Transition) {

                    if (activityId.equals(((Transition) notification.getNewValue()).getTo())) {
                        return true;
                    }

                } else if (Notification.REMOVE == notification.getEventType()
                        && Xpdl2Package.eINSTANCE.getFlowContainer_Transitions().equals(notification.getFeature())
                        && notification.getOldValue() instanceof Transition) {

                    if (activityId.equals(((Transition) notification.getOldValue()).getTo())) {
                        return true;
                    }
                }

            }
        }

        return false;
    }

}
