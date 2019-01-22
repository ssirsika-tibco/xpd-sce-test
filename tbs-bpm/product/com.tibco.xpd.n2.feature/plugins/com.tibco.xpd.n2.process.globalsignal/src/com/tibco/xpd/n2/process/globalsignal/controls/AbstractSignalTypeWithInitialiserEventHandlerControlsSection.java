/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.controls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.ActivityPickerControl;
import com.tibco.xpd.processeditor.xpdl2.properties.ActivityPickerControl.IActivityPickerListener;
import com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.AbstractSignalTypeSection;
import com.tibco.xpd.processeditor.xpdl2.util.ActivityPickerFilters.ValidEventHandlerInitialiserActivityFilter;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.EventHandlerFlowStrategy;
import com.tibco.xpd.xpdExtension.EventHandlerInitialisers;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Abstract Signal Type class that creates Initialiser event handler controls
 * via the
 * {@link #createAdditionalEventHandlerInitializerControls(Composite, XpdFormToolkit)}
 * method. All classes which require to create these event handler initialiser
 * controls should extend this class.
 * <p>
 * Note: Sub-classes should call the super {@link #doRefresh()} and
 * {@link #doGetCommand(Object)} methods else the functionality of Event handler
 * controls will break.
 * 
 * @author kthombar
 * @since Feb 16, 2015
 */
public abstract class AbstractSignalTypeWithInitialiserEventHandlerControlsSection
        extends AbstractSignalTypeSection {

    /**
     * serialize concurrent flow button
     */
    private Button serializeConcurrentFlows;

    /**
     * allow concurrent flow button
     */
    private Button allowConcurrentFlows;

    /**
     * container that holds the flow containers.
     */
    private Composite flowControlsContainer;

    /**
     * container that holds the initialiser activity controls.
     */
    private Composite initialiserActivityContainer;

    /**
     * Activity picker control
     */
    private ActivityPickerControl initialiserPickerControl;

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.AbstractSignalTypeSection#createAdditionalEventHandlerInitializerControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    public Composite createAdditionalEventHandlerInitializerControls(
            Composite parent, XpdFormToolkit toolkit) {
        Composite rootComposite = toolkit.createComposite(parent);
        GridLayout gridLayout = new GridLayout(1, false);
        gridLayout.marginLeft = -10;
        gridLayout.marginRight = -10;
        rootComposite.setLayout(gridLayout);

        /*
         * Create Flow controls
         */
        createConcurrentFlowsControls(rootComposite, toolkit);

        /*
         * Create the initialiser activity control with the picker.
         */
        createInititialisersControls(rootComposite, toolkit);

        return rootComposite;
    }

    /**
     * Creates the Initialser Activity Picker control.
     * 
     * @param rootComposite
     * @param toolkit
     */
    private void createInititialisersControls(Composite rootComposite,
            XpdFormToolkit toolkit) {
        initialiserActivityContainer = toolkit.createComposite(rootComposite);
        GridLayout gridLayout = new GridLayout(1, false);
        gridLayout.marginHeight = -7;
        initialiserActivityContainer.setLayout(gridLayout);

        GridData initialiserActivityContainerContainerGD =
                new GridData(GridData.FILL_HORIZONTAL);

        initialiserActivityContainer
                .setLayoutData(initialiserActivityContainerContainerGD);
        Composite initialisersConatiner =
                toolkit.createComposite(initialiserActivityContainer, SWT.NONE);
        GridData gdContainer = new GridData(GridData.FILL_HORIZONTAL);
        initialisersConatiner.setLayoutData(gdContainer);

        GridLayout gl = new GridLayout(2, false);
        gl.marginHeight = gl.marginWidth = 7;
        initialisersConatiner.setLayout(gl);

        Label initialisersLabel =
                toolkit.createLabel(initialisersConatiner,
                        Messages.AbstractSignalTypeWithInitialiserEventHandlerControlsSection_EventHandlerInitializer_label,
                        SWT.NONE);
        initialisersLabel
                .setToolTipText(Messages.AbstractSignalTypeWithInitialiserEventHandlerControlsSection_EventHandlerInitializer_tooltip);
        GridData gdLabel = new GridData(SWT.NONE, SWT.BEGINNING, true, false);
        gdLabel.horizontalSpan = 2;
        initialisersLabel.setLayoutData(gdLabel);

        initialiserPickerControl =
                new ActivityPickerControl(
                        initialisersConatiner,
                        toolkit,
                        Messages.AbstractSignalTypeWithInitialiserEventHandlerControlsSection_EventHandlerInitializerPicker_title,
                        SWT.MULTI) {
                    @Override
                    protected IFilter createActivityFilter() {
                        return new ValidEventHandlerInitialiserActivityFilter();
                    }
                };

        initialiserPickerControl.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        initialiserPickerControl
                .addActivitiesPickedListener(new IActivityPickerListener() {

                    @Override
                    public void activitiesPicked(
                            Collection<Activity> selectedActivities) {
                        EditingDomain ed = getEditingDomain();
                        Activity eventAct = getActivity();

                        if (eventAct != null
                                && eventAct.getEvent() != null
                                && eventAct.getEvent()
                                        .getEventTriggerTypeNode() instanceof TriggerResultSignal
                                && ed != null) {

                            TriggerResultSignal signalTrigger =
                                    (TriggerResultSignal) eventAct.getEvent()
                                            .getEventTriggerTypeNode();

                            Command cmd =
                                    selectCorrelationInitialisersCommand(ed,
                                            signalTrigger,
                                            eventAct,
                                            selectedActivities);

                            if (cmd != null) {
                                ed.getCommandStack().execute(cmd);
                            }
                        }
                    }
                });

    }

    /**
     * Select initialiser activities via a picker dialog and update the
     * <b>EventHandlerInitialisers</b> element to reflect the selection
     * 
     * @param ed
     *            Editing domain
     * @param trs
     * @param activity
     * @return COmmand to reset initialisers or null if nothing to do.
     */
    private CompoundCommand selectCorrelationInitialisersCommand(
            EditingDomain ed, TriggerResultSignal trs, Activity activity,
            Collection<Activity> selectedInitialisingActivities) {

        if (activity.getProcess() != null) {

            // Get any current activities
            List<Activity> currentInitialisingActivities =
                    Collections.<Activity> emptyList();

            EventHandlerInitialisers evtHdlInitialisers =
                    (EventHandlerInitialisers) Xpdl2ModelUtil
                            .getOtherElement(trs, XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_EventHandlerInitialisers());

            if (evtHdlInitialisers != null) {
                currentInitialisingActivities =
                        getEvtHandlerInitialisingActivities(evtHdlInitialisers);
            }

            // Any model amendments

            if (!isEqualsActivityLists(selectedInitialisingActivities,
                    currentInitialisingActivities)) {

                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.AbstractSignalTypeWithInitialiserEventHandlerControlsSection_EventHandlerInitializerCommand_label);

                removeEventHandlerInitialisers(ed, trs, evtHdlInitialisers, cmd);

                if (!selectedInitialisingActivities.isEmpty()) {
                    evtHdlInitialisers =
                            createEventHandlerInitialisers(ed, trs, cmd);

                    for (ActivityRef actRef : getListOfActivityRef(selectedInitialisingActivities)) {
                        evtHdlInitialisers.getActivityRef().add(actRef);
                    }
                }

                return cmd;
            }
        }

        return null;
    }

    /**
     * Remove event handler initialiers
     * 
     * @param ed
     * @param evtHdlInitialisers
     * @param cCmd
     *            CompoundCommand
     */
    private void removeEventHandlerInitialisers(EditingDomain ed,
            TriggerResultSignal trs,
            EventHandlerInitialisers evtHdlInitialisers, CompoundCommand cCmd) {

        if (evtHdlInitialisers != null) {
            cCmd.append(Xpdl2ModelUtil.getRemoveOtherElementCommand(ed,
                    trs,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_EventHandlerInitialisers(),
                    evtHdlInitialisers));
        }
    }

    /**
     * @param arrActivities1
     * @param arrActivities2
     * @return @return true if lists contain the exactly the same elements.
     *         Element order is ignored.
     */
    private boolean isEqualsActivityLists(Collection<Activity> arrActivities1,
            Collection<Activity> arrActivities2) {

        boolean arrActivities1IsNull = arrActivities1 == null;
        boolean arrActivities2IsNull = arrActivities2 == null;

        if (arrActivities1IsNull ^ arrActivities2IsNull) {
            return false;
        } else if (arrActivities1IsNull && arrActivities2IsNull) {
            return true;
        }

        if (arrActivities1 != null && arrActivities2 != null
                && (arrActivities1.size() == arrActivities2.size())) {

            int elemNum = arrActivities1.size();

            Comparator<Activity> comp = new Comparator<Activity>() {

                @Override
                public int compare(Activity o1, Activity o2) {
                    if ((o1 != null) || (o2 != null)) {
                        return o1.getId().compareTo(o2.getId());
                    }
                    return 0;
                }

            };

            // put in sorted collections
            if (elemNum >= 1) {
                Queue<Activity> q1 = new PriorityQueue<Activity>(elemNum, comp);
                for (Activity act : arrActivities1)
                    q1.offer(act);
                Queue<Activity> q2 = new PriorityQueue<Activity>(elemNum, comp);
                for (Activity act : arrActivities2)
                    q2.offer(act);

                // compare element-by-element
                while (!q1.isEmpty() && !q2.isEmpty()) {
                    String q1_ID = q1.remove().getId();
                    String q2_ID = q2.remove().getId();
                    if (!q1_ID.equals(q2_ID)) {
                        return false;
                    }
                }
            }

            return true;
        }

        return false;
    }

    /**
     * @param ed
     *            Editing domain
     * @param trs
     *            TriggerResultSignal to attach EventHandlerInitialiser to
     */
    private EventHandlerInitialisers createEventHandlerInitialisers(
            EditingDomain ed, TriggerResultSignal trs, CompoundCommand cCmd) {

        EventHandlerInitialisers model =
                XpdExtensionFactory.eINSTANCE.createEventHandlerInitialisers();
        cCmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                trs,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_EventHandlerInitialisers(),
                model));

        return model;
    }

    /**
     * Construct a list of <code>ActivityRef</code>s from a collection of
     * <code>Activity</code>s.
     * 
     * @param activities
     * @return List of <code>ActivityRef</code>s
     */
    private List<ActivityRef> getListOfActivityRef(
            Collection<Activity> activities) {

        // create list of EObjects for activities
        List<ActivityRef> activityRefs = new ArrayList<ActivityRef>();

        for (Activity activity : activities) {
            ActivityRef activityRef =
                    XpdExtensionFactory.eINSTANCE.createActivityRef();
            activityRef.setIdRef(activity.getId());
            activityRefs.add(activityRef);
        }

        return activityRefs;
    }

    /**
     * @param evtHdlInitialisers
     * @return the activities explicitly mentioned that an Event Handler may be
     *         initialised by. Null if no Event Handler Initialising element
     *         present.
     */
    private List<Activity> getEvtHandlerInitialisingActivities(
            EventHandlerInitialisers evtHdlInitialisers) {

        List<Activity> ret = Collections.<Activity> emptyList();

        if (evtHdlInitialisers != null) {

            List<ActivityRef> activityRefs =
                    evtHdlInitialisers.getActivityRef();
            if (activityRefs != null && !activityRefs.isEmpty()) {

                ret = new ArrayList<Activity>();
                for (ActivityRef activityRef : activityRefs) {
                    ret.add(activityRef.getActivity());
                }
            }

        }

        return ret;
    }

    /**
     * Creates the Flow strategy controls.
     * 
     * @param rootComposite
     * @param toolkit
     */
    private void createConcurrentFlowsControls(Composite rootComposite,
            XpdFormToolkit toolkit) {

        flowControlsContainer = toolkit.createComposite(rootComposite);
        GridLayout gridLayout = new GridLayout(1, false);
        gridLayout.marginTop = -5;

        flowControlsContainer.setLayout(gridLayout);

        GridData flowControlsContainerGD =
                new GridData(GridData.FILL_HORIZONTAL);

        flowControlsContainer.setLayoutData(flowControlsContainerGD);
        /*
         * Create Serialize button
         */
        serializeConcurrentFlows =
                toolkit.createButton(flowControlsContainer,
                        Messages.AbstractSignalTypeWithInitialiserEventHandlerControlsSection_SerializeConcurrentFlowButton_label,
                        SWT.RADIO);
        serializeConcurrentFlows
                .setToolTipText(Messages.AbstractSignalTypeWithInitialiserEventHandlerControlsSection_SerializeConcurrentFlowButton_tooltip);
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        serializeConcurrentFlows.setLayoutData(gridData);
        manageControl(serializeConcurrentFlows);
        /*
         * Create Allow Concurrent flow button
         */
        allowConcurrentFlows =
                toolkit.createButton(flowControlsContainer,
                        Messages.AbstractSignalTypeWithInitialiserEventHandlerControlsSection_AllowConcurrentFlowButton_label,
                        SWT.RADIO);
        allowConcurrentFlows
                .setToolTipText(Messages.AbstractSignalTypeWithInitialiserEventHandlerControlsSection_AllowConcurrentFlowButton_tooltip);
        allowConcurrentFlows.setLayoutData(gridData);
        manageControl(allowConcurrentFlows);
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {

        if (isInitialiserActivityControlsApplicable()) {
            /*
             * if inilialiser Activity controls are applibale and hidden then
             * show them.
             */

            if (!initialiserActivityContainer.isVisible()) {
                initialiserActivityContainer.setVisible(true);

                GridData initialiserActivityContainerContainerGD =
                        new GridData(GridData.FILL_HORIZONTAL);

                initialiserActivityContainer
                        .setLayoutData(initialiserActivityContainerContainerGD);
                initialiserActivityContainer.getParent().layout();
            }

        } else {
            /*
             * if inilialiser Activity controls are not applicable and visible
             * then hide them.
             */
            if (initialiserActivityContainer.isVisible()) {

                initialiserActivityContainer.setVisible(false);
                GridData gd = new GridData();
                gd.heightHint = 0;
                initialiserActivityContainer.setLayoutData(gd);
                initialiserActivityContainer.getParent().layout();

            }
        }

        if (isFlowControlsApplicable()) {
            /* if flow controls are applicable and hidden then show them. */
            if (!flowControlsContainer.isVisible()) {
                flowControlsContainer.setVisible(true);

                GridData flowControlContainerGD =
                        new GridData(GridData.FILL_HORIZONTAL);

                flowControlsContainer.setLayoutData(flowControlContainerGD);
                flowControlsContainer.getParent().layout();
            }

            /*
             * XPD-7049: Saket: Disable flow controls if needed.
             */
            if (shouldDisableFlowControls()) {

                allowConcurrentFlows.setEnabled(false);
                serializeConcurrentFlows.setEnabled(false);

            } else {

                allowConcurrentFlows.setEnabled(true);
                serializeConcurrentFlows.setEnabled(true);

            }

        } else {
            /* if flow controls are not applicable and visible then hide them. */
            if (flowControlsContainer.isVisible()) {

                flowControlsContainer.setVisible(false);
                GridData gd = new GridData();
                gd.heightHint = 0;
                flowControlsContainer.setLayoutData(gd);
                flowControlsContainer.getParent().layout();

            }
        }

        Activity activity = getActivity();
        if (activity != null) {

            Event event = activity.getEvent();

            if (event != null) {

                EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();

                if (eventTriggerTypeNode instanceof TriggerResultSignal) {

                    TriggerResultSignal signal =
                            (TriggerResultSignal) eventTriggerTypeNode;

                    /*
                     * event handler control refresh
                     */
                    List<Activity> currentInitialisingActivities =
                            Collections.<Activity> emptyList();

                    EventHandlerInitialisers evtHdlInitialisers =
                            (EventHandlerInitialisers) Xpdl2ModelUtil
                                    .getOtherElement(signal,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_EventHandlerInitialisers());

                    if (evtHdlInitialisers != null) {

                        currentInitialisingActivities =
                                getEvtHandlerInitialisingActivities(evtHdlInitialisers);
                    }
                    /*
                     * initialise the picker.
                     */
                    initialiserPickerControl.setActivities(activity
                            .getProcess(), currentInitialisingActivities);

                    Object otherAttribute =
                            Xpdl2ModelUtil
                                    .getOtherAttribute(signal,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_EventHandlerFlowStrategy());

                    if (otherAttribute instanceof EventHandlerFlowStrategy) {

                        EventHandlerFlowStrategy flowStrategy =
                                (EventHandlerFlowStrategy) otherAttribute;
                        /*
                         * show the appropriate flow strategy
                         */
                        if (EventHandlerFlowStrategy.SERIALIZE_CONCURRENT
                                .equals(flowStrategy)) {

                            serializeConcurrentFlows.setSelection(true);
                            allowConcurrentFlows.setSelection(false);
                        } else if (EventHandlerFlowStrategy.ALLOW_CONCURRENT
                                .equals(flowStrategy)) {

                            serializeConcurrentFlows.setSelection(false);
                            allowConcurrentFlows.setSelection(true);
                        }
                    }
                }
            }
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        Activity activity = getActivity();
        EditingDomain editingDomain = getEditingDomain();

        if (activity.getEvent() != null
                && activity.getEvent().getEventTriggerTypeNode() instanceof TriggerResultSignal) {

            /*
             * Get the event trigger type node.
             */
            TriggerResultSignal triggerResultSignal =
                    (TriggerResultSignal) activity.getEvent()
                            .getEventTriggerTypeNode();

            CompoundCommand ccmd = new CompoundCommand();

            if (obj == serializeConcurrentFlows) {

                ccmd.setLabel(Messages.AbstractSignalTypeWithInitialiserEventHandlerControlsSection_SealializeFlowStrategyCommand_label);

                ccmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                triggerResultSignal,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_EventHandlerFlowStrategy(),
                                EventHandlerFlowStrategy.SERIALIZE_CONCURRENT));

            } else if (obj == allowConcurrentFlows) {

                ccmd.setLabel(Messages.AbstractSignalTypeWithInitialiserEventHandlerControlsSection_AllowFlowStrategyCommand_label);

                ccmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                triggerResultSignal,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_EventHandlerFlowStrategy(),
                                EventHandlerFlowStrategy.ALLOW_CONCURRENT));

            }

            if (!ccmd.isEmpty()) {
                return ccmd;
            }
        }
        return null;
    }
}
