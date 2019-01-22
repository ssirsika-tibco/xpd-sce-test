/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.controls;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.SignalType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Signal Type section that contributes the 'Global:' Signal Type Button and the
 * and the necessary Event Handler Initiliser controls(via the super class).
 * 
 * @author kthombar
 * @since Jan 27, 2015
 */
public class GlobalSignalTypeSection extends
        AbstractSignalTypeWithInitialiserEventHandlerControlsSection {

    /**
     * Global Signal Picker Control.
     */
    private GlobalSignalPickerControl globalSignalPickerControl;

    /**
     * 
     */
    public GlobalSignalTypeSection() {
        /*
         * Default constructor.
         */
    }

    /**
     * @param eClass
     */
    public GlobalSignalTypeSection(EClass eClass) {
        super();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.AbstractSignalTypeSection#isSelectedSignalType()
     * 
     * @return <code>true</code> if SignalType is set as Global else return
     *         <code>false</code>
     */
    @Override
    public boolean isSelectedSignalType() {
        Activity activity = getActivity();
        if (activity != null) {

            return GlobalSignalUtil.isGlobalSignalEvent(activity);
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.AbstractSignalTypeSection#getSetSelectedSignalTypeCommand(java.lang.Object)
     * 
     * @param obj
     * @return Command to set the Signal Event Signal Type to Global else
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
                                    Messages.GlobalSignalTypeSection_SetSignalToGlobalCommand_label);

                    Object assoParam =
                            Xpdl2ModelUtil
                                    .getOtherElement(activity,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_AssociatedParameters());

                    if (assoParam instanceof AssociatedParameters) {
                        /*
                         * By default the implicit data association should be
                         * available for global signals.
                         */
                        AssociatedParameters associatedParameters =
                                (AssociatedParameters) assoParam;

                        if (associatedParameters.isDisableImplicitAssociation()) {

                            cmd.append(SetCommand
                                    .create(editingDomain,
                                            associatedParameters,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getAssociatedParameters_DisableImplicitAssociation(),
                                            Boolean.FALSE));
                        }
                    }

                    cmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(getEditingDomain(),
                                    signal,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_SignalType(),
                                    SignalType.GLOBAL));

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
    @Override
    protected void doRefresh() {

        super.doRefresh();

        Activity activity = getActivity();

        if (activity != null) {

            Event event = activity.getEvent();

            if (event != null) {

                EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();

                if (eventTriggerTypeNode instanceof TriggerResultSignal
                        && GlobalSignalUtil.isGlobalSignalEvent(activity)) {

                    TriggerResultSignal signal =
                            (TriggerResultSignal) eventTriggerTypeNode;

                    String signalName = signal.getName();
                    /*
                     * Set the global signal and the parent activity.
                     */
                    globalSignalPickerControl
                            .setInitiallySelectedGlobalSignal(signalName,
                                    activity);
                } else {

                    /*
                     * Reset global signal controls.
                     */
                    globalSignalPickerControl
                            .setInitiallySelectedGlobalSignal(null, activity);
                }
            }
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
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout(1, false));

        /*
         * Create the global signal picker control.
         */
        globalSignalPickerControl =
                new GlobalSignalPickerControl(root, toolkit, "") { //$NON-NLS-1$

                    /**
                     * @see com.tibco.xpd.n2.process.globalsignal.controls.GlobalSignalPickerControl#setUnsetGlobalSignal(java.lang.Object)
                     * 
                     * @param globalSignal
                     */
                    @Override
                    protected void setUnsetGlobalSignal(
                            GlobalSignal globalSignal) {

                        Activity activity = getActivity();

                        EditingDomain ed = getEditingDomain();

                        if (activity != null && ed != null) {

                            String globalSignalModelNameFromGlobalSignal = null;

                            if (globalSignal != null) {
                                globalSignalModelNameFromGlobalSignal =
                                        GlobalSignalUtil
                                                .getGlobalSignalQualifiedName(globalSignal);
                            }

                            CompoundCommand ccmd = new CompoundCommand();
                            ccmd.setLabel(Messages.GlobalSignalTypeSection_SetUnsetGlobalSignalCommand_label);

                            ccmd.append(EventObjectUtil
                                    .getSetSignalNameCommand(ed,
                                            activity,
                                            globalSignalModelNameFromGlobalSignal));

                            Event event = activity.getEvent();

                            /*
                             * XPD-7549: Only clear the mappings and Scripts if
                             * the user clicks on the clear global signal
                             * button.
                             */
                            if (event != null
                                    && globalSignalModelNameFromGlobalSignal == null) {

                                EObject eventTriggerTypeNode =
                                        event.getEventTriggerTypeNode();

                                if (eventTriggerTypeNode instanceof TriggerResultSignal) {

                                    TriggerResultSignal signal =
                                            (TriggerResultSignal) eventTriggerTypeNode;

                                    Object otherElement =
                                            Xpdl2ModelUtil
                                                    .getOtherElement(signal,
                                                            XpdExtensionPackage.eINSTANCE
                                                                    .getDocumentRoot_SignalData());
                                    /*
                                     * Remove any previous mappings
                                     */
                                    if (otherElement != null) {
                                        ccmd.append(Xpdl2ModelUtil
                                                .getRemoveOtherElementCommand(ed,
                                                        signal,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_SignalData(),
                                                        otherElement));
                                    }

                                    Object scriptElement =
                                            Xpdl2ModelUtil
                                                    .getOtherElement(activity,
                                                            XpdExtensionPackage.eINSTANCE
                                                                    .getDocumentRoot_Script());
                                    /*
                                     * If the activity has script then remove it
                                     * vafter type is changed.
                                     */
                                    if (scriptElement != null) {
                                        ccmd.append(Xpdl2ModelUtil
                                                .getRemoveOtherElementCommand(ed,
                                                        activity,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_Script(),
                                                        scriptElement));
                                    }

                                }
                            }
                            /*
                             * Fire command to set/clear the global signal name.
                             */
                            ed.getCommandStack().execute(ccmd);
                        }
                    }
                };

        globalSignalPickerControl.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));
        return root;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {
        /*
         * should be enabled only for developer capability.
         */
        return CapabilityUtil.isDeveloperActivityEnabled() ? true : false;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {

        return super.doGetCommand(obj);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.AbstractSignalTypeSection#getSignalTypeToolTip()
     * 
     * @return
     */
    @Override
    public String getSignalTypeToolTip() {

        return Messages.GlobalSignalTypeSection_GlobalSignalButton_tooltip;
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
         * show event sub process event handler controls for gloabsl signal
         * events.
         */
        return GlobalSignalUtil.isGlobalSignalEvent(activity);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.AbstractSignalTypeSection#isFlowControlsApplicable()
     * 
     * @return
     */
    @Override
    public boolean isFlowControlsApplicable() {
        return true;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.AbstractSignalTypeSection#isInitialiserActivityControlsApplicable()
     * 
     * @return
     */
    @Override
    public boolean isInitialiserActivityControlsApplicable() {
        return true;
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
