/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.casedata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.fieldassist.ContentProposal;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.n2.process.globalsignal.controls.AbstractSignalTypeWithInitialiserEventHandlerControlsSection;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.CommandContentAssistTextHandler;
import com.tibco.xpd.processeditor.xpdl2.properties.util.ContentAssistText;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.EventHandlerFlowStrategy;
import com.tibco.xpd.xpdExtension.SignalType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Signal Type section that contributes the 'Case Data:' Signal Type Button and
 * the and the necessary controls to configure a case data signal event.
 * 
 * @author sajain
 * @since Mar 5, 2015
 */
public class CaseDataSignalTypeSection extends
        AbstractSignalTypeWithInitialiserEventHandlerControlsSection {

    /**
     * Text control to enter case data.
     */
    private Text caseDataText;

    /**
     * Signal Type section that contributes the 'Case Data:' Signal Type Button
     * and the and the necessary controls to configure a case data signal event.
     */
    public CaseDataSignalTypeSection() {
        /*
         * Default constructor.
         */
    }

    /**
     * Signal Type section that contributes the 'Case Data:' Signal Type Button
     * and the and the necessary controls to configure a case data signal event.
     * 
     * @param eClass
     */
    public CaseDataSignalTypeSection(EClass eClass) {
        super();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.AbstractSignalTypeSection#isSelectedSignalType()
     * 
     * @return <code>true</code> if SignalType is set as Case Data else return
     *         <code>false</code>
     */
    @Override
    public boolean isSelectedSignalType() {

        Activity activity = getActivity();

        if (activity != null) {

            return isCaseDataEvent(activity);
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.AbstractSignalTypeSection#getSetSelectedSignalTypeCommand(java.lang.Object)
     * 
     * @param obj
     * @return Command to set the Signal Event Signal Type to Case Data else
     *         <code>null</code>
     */
    @Override
    public Command getSetSelectedSignalTypeCommand(Object obj) {

        Activity activity = getActivity();

        EditingDomain editingDomain = getEditingDomain();

        /*
         * Check if activity is not null.
         */
        if (activity != null && editingDomain != null) {

            Event event = activity.getEvent();

            /*
             * Activity must be an event.
             */
            if (event != null) {

                EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();

                /*
                 * Event must be signal event.
                 */
                if (eventTriggerTypeNode instanceof TriggerResultSignal) {

                    TriggerResultSignal signal =
                            (TriggerResultSignal) eventTriggerTypeNode;

                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.CaseDataSignalTypeSection_SetSignalToCaseDataCommand_label);

                    Object assoParam =
                            Xpdl2ModelUtil
                                    .getOtherElement(activity,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_AssociatedParameters());

                    if (assoParam instanceof AssociatedParameters) {

                        /*
                         * By default the implicit data association should be
                         * available for case signals.
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

                    /*
                     * By default, flow strategy should be set to 'serialize
                     * concurrent flows' for case data signals.
                     */
                    cmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    signal,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_EventHandlerFlowStrategy(),
                                    EventHandlerFlowStrategy.SERIALIZE_CONCURRENT));

                    cmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(getEditingDomain(),
                                    signal,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_SignalType(),
                                    SignalType.CASE_DATA));

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
                        && isCaseDataEvent(activity)) {

                    TriggerResultSignal signal =
                            (TriggerResultSignal) eventTriggerTypeNode;

                    String signalName = signal.getName();

                    /*
                     * Signal name must not be null.
                     */
                    if (signalName != null && !signalName.isEmpty()) {

                        updateText(caseDataText, signalName);
                    }

                } else {

                    updateText(caseDataText, ""); //$NON-NLS-1$
                }
            }
        }
    }

    /**
     * Return <code>true</code> if the passed activity is a Case Data event.
     * 
     * @param activity
     *            Activity to be checked.
     * 
     * @return <code>true</code> if the passed activity is a Case Data event.
     */
    private boolean isCaseDataEvent(Activity activity) {

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

                if (otherAttribute instanceof SignalType) {

                    SignalType sigType = (SignalType) otherAttribute;

                    return SignalType.CASE_DATA.equals(sigType) ? true : false;
                }
            }
        }

        return false;
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
         * Content assist text control to enter case reference data.
         */
        ContentAssistText contentAssistText =
                new ContentAssistText(root, toolkit,
                        new CaseReferenceTypeProposalProvider() {

                            @Override
                            protected Activity getInput() {

                                EObject input =
                                        CaseDataSignalTypeSection.this
                                                .getInput();

                                return (Activity) (input instanceof Activity ? input
                                        : null);
                            }
                        });

        manageControl(contentAssistText);

        /*
         * Get the text control from the content assist text control.
         */
        caseDataText = contentAssistText.getText();

        caseDataText
                .setToolTipText(Messages.CaseDataSignalTypeSection_CaseDataText_tooltip);

        GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);
        gd1.horizontalIndent = -6;
        contentAssistText.setLayoutData(gd1);

        return root;
    }

    /**
     * Provides the same functionality for ContentAssistText fields as the
     * manageControl methods in AbstractXpdSection do for SWT Controls.
     * 
     * @param control
     *            The content assist control to manage.
     */
    protected void manageControl(final ContentAssistText control) {

        new CommandContentAssistTextHandler(control, this);
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {

        boolean isCatchSignalEvent = false;

        if (toTest instanceof Activity) {

            Activity act = ((Activity) toTest);

            if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                    .equals(EventObjectUtil.getEventTriggerType(act))) {

                isCatchSignalEvent = true;
            }
        }

        /*
         * Should be enabled only for developer capability and catch signal
         * events.
         */
        return (CapabilityUtil.isDeveloperActivityEnabled() && isCatchSignalEvent) ? true
                : false;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {

        Command cmd = super.doGetCommand(obj);

        Activity activity = getActivity();

        EditingDomain editingDomain = getEditingDomain();

        if (activity != null && editingDomain != null) {

            Event event = activity.getEvent();

            if (event != null) {

                EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();

                if (eventTriggerTypeNode instanceof TriggerResultSignal) {

                    TriggerResultSignal signal =
                            (TriggerResultSignal) eventTriggerTypeNode;

                    /*
                     * Check if the current object is the case data text.
                     */
                    if (obj == caseDataText) {

                        String caseDataTxt = caseDataText.getText();

                        if (cmd == null) {

                            cmd = new CompoundCommand();
                        }

                        /*
                         * Update the case data in the model if the text control
                         * isn't blank.
                         */
                        if (caseDataTxt != null && !caseDataTxt.isEmpty()
                                && caseDataTxt != "") { //$NON-NLS-1$

                            ((CompoundCommand) cmd)
                                    .append(SetCommand
                                            .create(editingDomain,
                                                    signal,
                                                    Xpdl2Package.eINSTANCE
                                                            .getTriggerResultSignal_Name(),
                                                    caseDataTxt));
                        } else {

                            /*
                             * If the text control IS blank, then unset case
                             * data value in the model.
                             */
                            ((CompoundCommand) cmd)
                                    .append(SetCommand
                                            .create(editingDomain,
                                                    signal,
                                                    Xpdl2Package.eINSTANCE
                                                            .getTriggerResultSignal_Name(),
                                                    SetCommand.UNSET_VALUE));
                        }
                    }
                }
            }
        }

        return cmd;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.AbstractSignalTypeSection#getSignalTypeToolTip()
     * 
     * @return
     */
    @Override
    public String getSignalTypeToolTip() {

        return Messages.CaseDataSignalTypeSection_CaseSignalButton_tooltip;
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
         * show event sub process event handler controls for case data signal
         * events.
         */
        return isCaseDataEvent(activity);
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
        return false;
    }

    /**
     * Content proposal for case reference types.
     * 
     * @author sajain
     */
    private static abstract class CaseReferenceTypeProposalProvider implements
            IContentProposalProvider {

        @Override
        public IContentProposal[] getProposals(String contents, int position) {

            List<ContentProposal> proposals = new ArrayList<ContentProposal>();

            /*
             * Get all the parameters/data fields which match the content of the
             * content assist field
             */
            Set<ProcessRelevantData> dataInScope = getCaseRefTypesInScope();

            for (ProcessRelevantData data : dataInScope) {

                String displayName = Xpdl2ModelUtil.getDisplayName(data);

                if (doesProposalMatch(data.getName(), contents, position)) {

                    proposals.add(new ContentProposal(data.getName(),
                            displayName));
                }
            }

            return proposals.toArray(new IContentProposal[proposals.size()]);
        }

        /**
         * Get the input of the section.
         * 
         * @return
         */
        protected abstract Activity getInput();

        /**
         * Get all Case class reference data types that are in scope of the
         * input object.
         * 
         * @return
         */
        private Set<ProcessRelevantData> getCaseRefTypesInScope() {

            Set<ProcessRelevantData> items = new HashSet<ProcessRelevantData>();

            List<ProcessRelevantData> activityData =
                    ProcessInterfaceUtil
                            .getAllAvailableRelevantDataForActivity(getInput());

            for (ProcessRelevantData data : activityData) {

                if (data != null) {

                    DataType dataType = data.getDataType();

                    if (dataType instanceof RecordType) {

                        items.add(data);
                    }
                }
            }

            return items;
        }

        /**
         * Check if the value matches the content assist proposal.
         * 
         * @param value
         * @param contentAssistContents
         * @param contentAssistPosition
         * @return
         */
        public boolean doesProposalMatch(String value,
                String contentAssistContents, int contentAssistPosition) {

            if (value != null && !value.isEmpty()
                    && contentAssistContents != null
                    && !contentAssistContents.isEmpty()) {
                String toMatch = null;

                if (!contentAssistContents.isEmpty()) {
                    toMatch =
                            contentAssistPosition > 0 ? contentAssistContents
                                    .substring(0, contentAssistPosition)
                                    : contentAssistContents;
                }

                return toMatch == null || value.startsWith(toMatch);
            }

            return true;
        }

    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.AbstractSignalTypeSection#shouldDisableFlowControls()
     * 
     * @return
     */
    @Override
    public boolean shouldDisableFlowControls() {

        /*
         * Disable flow controls.
         */
        return true;
    }
}