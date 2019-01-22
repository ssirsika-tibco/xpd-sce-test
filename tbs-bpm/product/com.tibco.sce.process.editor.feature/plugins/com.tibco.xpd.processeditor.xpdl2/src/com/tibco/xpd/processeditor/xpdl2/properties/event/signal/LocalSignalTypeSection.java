/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event.signal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.AbstractSignalTypeSection;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldChangedListener;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldProposalProvider;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil.ReturnCatchThrowTypes;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.SignalType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Signal Type section that contributes the 'Local:' Signal Type Button and the
 * Content assist to select/specify the Signal Name.
 * 
 * @author kthombar
 * @since Jan 27, 2015
 */
public class LocalSignalTypeSection extends AbstractSignalTypeSection implements
        FixedValueFieldChangedListener {

    @SuppressWarnings("deprecation")
    private DecoratedField signalName;

    private Set<String> existingSignalNames = null;

    private Button eventHandlerButton;

    /**
     * 
     */
    public LocalSignalTypeSection() {

    }

    /**
     * @param eClass
     */
    public LocalSignalTypeSection(EClass eClass) {
        super(eClass);
    }

    /**
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.AbstractSignalTypeSection#isSelectedSignalType()
     * 
     * @return <code>true</code> if no SignalType is Set or SignalType is set as
     *         Local else return <code>false</code>
     */
    @Override
    public boolean isSelectedSignalType() {
        Activity activity = getActivity();
        if (activity != null) {

            Event event = activity.getEvent();
            if (event != null) {

                EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();

                if (eventTriggerTypeNode instanceof TriggerResultSignal) {
                    TriggerResultSignal signal =
                            (TriggerResultSignal) eventTriggerTypeNode;

                    Object otherAttribute =
                            Xpdl2ModelUtil.getOtherAttribute(signal,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_SignalType());
                    /*
                     * return true if Signal Type 'Local' is set or if no Signal
                     * Type is set at all.
                     */
                    if (otherAttribute != null) {
                        SignalType stype = (SignalType) otherAttribute;
                        return SignalType.LOCAL.equals(stype) ? true : false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.AbstractSignalTypeSection#getSetSelectedSignalTypeCommand(java.lang.Object)
     * 
     * @param obj
     * @return Command to set the Signal Event Signal Type to Local else
     *         <code>null</code>
     */
    @Override
    public Command getSetSelectedSignalTypeCommand(Object obj) {
        Activity activity = getActivity();
        EditingDomain editingDomain = getEditingDomain();
        if (activity != null && editingDomain != null) {

            Event event = activity.getEvent();
            if (event != null) {

                EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();

                if (eventTriggerTypeNode instanceof TriggerResultSignal) {

                    TriggerResultSignal signal =
                            (TriggerResultSignal) eventTriggerTypeNode;

                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.LocalSignalTypeSection_SetLocalSignalCommand_label);

                    cmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    signal,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_SignalType(),
                                    SignalType.LOCAL));

                    /*
                     * remove the signal name as the signal type is changed.
                     */
                    cmd.append(EventObjectUtil
                            .getSetSignalNameCommand(getEditingDomain(),
                                    activity,
                                    null));

                    return cmd;
                }
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @SuppressWarnings("deprecation")
    @Override
    protected void doRefresh() {

        // If controls have been disposed then unregister adapter
        if (signalName == null || signalName.getControl().isDisposed()) {
            return;
        }

        Activity activity = getActivity();

        if (activity != null) {
            // Populate the list of existing signals names.

            Collection<Activity> eventList =
                    EventObjectUtil.getProcessSignalEvents(Xpdl2ModelUtil
                            .getProcess(activity),
                            ReturnCatchThrowTypes.CATCH_AND_THROW);

            existingSignalNames = new HashSet<String>();

            for (Activity eachSignalEvent : eventList) {
                if (EventObjectUtil.isLocalSignalEvent(eachSignalEvent)) {
                    /*
                     * XPD-7075: Only add the names of local signals to the
                     * content assist.
                     */
                    String sigName = getEventSignalName(eachSignalEvent);

                    if (sigName != null && sigName.length() > 0) {
                        existingSignalNames.add(sigName);
                    }
                }
            }

            /*
             * Cancelling / Non cancelling event controls.
             */
            TriggerResultSignal triggerSignal =
                    EventObjectUtil.getTriggerSignal(activity);

            if (triggerSignal != null) {
                boolean isCatchSignal =
                        CatchThrow.CATCH.equals(triggerSignal.getCatchThrow());
                if (isCatchSignal
                        && Xpdl2ModelUtil.isEventHandlerActivity(activity)) {

                    boolean signalHandlerAsynchronous =
                            Xpdl2ModelUtil
                                    .getOtherAttributeAsBoolean(triggerSignal,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_SignalHandlerAsynchronous());
                    if (signalHandlerAsynchronous) {
                        eventHandlerButton.setSelection(false);
                    } else {
                        eventHandlerButton.setSelection(true);
                    }
                }
            }

        }

        if (existingSignalNames.size() < 1) {
            existingSignalNames = null;
        }

        if (isSelectedSignalType()) {
            ((Text) signalName.getControl())
                    .setText(getEventSignalName(activity));
        } else {
            ((Text) signalName.getControl()).setText(""); //$NON-NLS-1$
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @SuppressWarnings("deprecation")
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout(1, false));

        FixedValueFieldProposalProvider proposalProvider =
                new FixedValueFieldAssistHelper.FixedValueFieldProposalProvider() {
                    @Override
                    public Object[] getProposals() {
                        if (existingSignalNames != null) {
                            return existingSignalNames.toArray();
                        }
                        return null;
                    }
                };

        FixedValueFieldAssistHelper refTaskHelper =
                new FixedValueFieldAssistHelper(toolkit, root,
                        proposalProvider, false);

        refTaskHelper.addValueChangedListener(this);

        signalName = refTaskHelper.getDecoratedField();
        signalName.getControl().setData("name", "textEventErrorCode"); //$NON-NLS-1$ //$NON-NLS-2$

        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalIndent = -6;

        signalName.getLayoutControl().setLayoutData(gridData);

        signalName.getLayoutControl().setBackground(root.getBackground());

        return root;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldChangedListener#fixedValueFieldChanged(java.lang.Object)
     * 
     * @param newValue
     */
    @Override
    public void fixedValueFieldChanged(Object newValue) {
        Activity activity = getActivity();
        EditingDomain ed = getEditingDomain();

        if (ed != null && activity != null) {
            String oldValue = getEventSignalName(activity);

            if (oldValue == null) {
                oldValue = ""; //$NON-NLS-1$
            }
            if (!oldValue.equals(newValue)) {
                Command cmd =
                        EventObjectUtil.getSetSignalNameCommand(ed,
                                activity,
                                (String) newValue);
                if (cmd != null) {
                    ed.getCommandStack().execute(cmd);
                }
            }
        }
    }

    /**
     * 
     * @param activity
     * @return the Current Signal Name.
     */
    private String getEventSignalName(Activity activity) {
        String sigName = null;

        EObject trigTypeNode = activity.getEvent().getEventTriggerTypeNode();
        if (trigTypeNode instanceof TriggerResultSignal) {
            sigName = ((TriggerResultSignal) trigTypeNode).getName();
        }
        if (sigName == null) {
            sigName = ""; //$NON-NLS-1$
        }
        return sigName;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {
        return true;
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

        if (activity != null) {

            TriggerResultSignal triggerSignal =
                    EventObjectUtil.getTriggerSignal(activity);

            if (triggerSignal != null) {

                if (obj == eventHandlerButton) {

                    /*
                     * set event handler configuration
                     */
                    boolean signalHandlerAsynchronous =
                            !(eventHandlerButton.getSelection());

                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.EventTriggerTypeSignalSection_SetUnsetWaitAtInvokingSignal_menu);

                    cmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(getEditingDomain(),
                                    triggerSignal,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_SignalHandlerAsynchronous(),
                                    signalHandlerAsynchronous));
                    return cmd;
                }
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.AbstractSignalTypeSection#getSignalTypeToolTip()
     * 
     * @return
     */
    @Override
    public String getSignalTypeToolTip() {

        return Messages.LocalSignalTypeSection_LocalSignalButton_tooltip;
    }

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

        Composite root = toolkit.createComposite(parent);

        root.setLayout(new GridLayout(1, false));

        eventHandlerButton =
                toolkit.createButton(root,
                        Messages.EventHandlerConfiguration_description,
                        SWT.CHECK);
        GridData gd2 = new GridData();
        eventHandlerButton.setLayoutData(gd2);
        eventHandlerButton.setSelection(true);
        eventHandlerButton.setVisible(true);//
        manageControl(eventHandlerButton);

        return root;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.AbstractSignalTypeSection#isEventSubProcessEventHandlerControlsApplicabale(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    public boolean isEventSubProcessEventHandlerControlsApplicabale(
            Activity activity) {
        /*
         * no event sub process event handler controls required
         */
        return false;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.AbstractSignalTypeSection#isFlowControlsApplicable()
     * 
     * @return
     */
    @Override
    public boolean isFlowControlsApplicable() {
        /*
         * no event sub process event handler controls required
         */
        return false;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.AbstractSignalTypeSection#isInitialiserActivityControlsApplicable()
     * 
     * @return
     */
    @Override
    public boolean isInitialiserActivityControlsApplicable() {
        /*
         * no event sub process event handler controls required
         */
        return false;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.AbstractSignalTypeSection#shouldDisableFlowControls()
     * 
     * @return
     */
    @Override
    public boolean shouldDisableFlowControls() {
        /*
         * No need to disable flow controls.
         */
        return false;
    }
}
