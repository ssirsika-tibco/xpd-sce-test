/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationField;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Interface tab section with associated data AND associated correlation data
 * tables.
 * <p>
 * Start Message Event, Intermediate Catch Message Event and Receive Task.
 * <p>
 * These activities have a separate table for explicitly associating correlation
 * data fields with the activity.
 * <p>
 * For start process api activities there is also a
 * "no correlation data initialization required" checkbox. This is needed
 * because by default start activities have all correlation data associated in
 * "initialization" mode. This means that if the process needs to contain
 * correlation data for correlation of in-flow api activities later on (and the
 * data is initialised by other means like script tasks) then the user must be
 * able to state that the correlation data should not be implicitly associated
 * with start.
 * <p>
 * This prevents the validation
 * "[implicitly associated] correlation data must be mapped" being raised when
 * not applicable to the process.
 * 
 * @author NWilson
 * @since 3.2
 */
public class AssociatedParameterWithCorrelationSection extends
        ActivityAssociatedParameterSection implements IFilter {

    protected static final String ASSOCIATED_CORRELATION_DATA_SECTION_ID =
            "associatedCorrelationDataSectionId"; //$NON-NLS-1$

    protected CorrelationDataAssociationTable corrDataAssocTable;

    private Button disableImplicit;

    public AssociatedParameterWithCorrelationSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    @Override
    protected void addStackerSections(ExpandableSectionStacker stacker) {
        stacker.addSection(ASSOCIATED_PARAMETER_SECTION_ID,
                Messages.AssociatedParameterSection_ParametersSectionTitle,
                100,
                true,
                true);
        stacker.addSection(ASSOCIATED_CORRELATION_DATA_SECTION_ID,
                Messages.AssociatedParameterSection_CorrelationDataSectionTitle,
                100,
                true,
                true);
    }

    @Override
    protected void doRefresh() {
        super.doRefresh();

        EObject input = getInput();
        if (input instanceof Activity) {
            Activity activity = (Activity) input;

            //
            // Normally the general section for events/tasks detects a change of
            // task type and fires a refresh tabs appropriately.
            //
            // However, in some circumstances - such as selecting an event, then
            // its interface tab, then selecting a task means that ONLY the
            // interface section is loaded NOT the task general section.
            //
            // Therefore we attempt to compensate for this by hiding the
            // correlation data expandable section if the input no longer
            // matches the section filter.
            Section correlSection =
                    getSectionStacker()
                            .getExpandableSection(ASSOCIATED_CORRELATION_DATA_SECTION_ID);
            if (!select(activity)) {
                if (correlSection.getVisible()) {
                    correlSection.setVisible(false);
                }
            } else {
                if (!correlSection.getVisible()) {
                    correlSection.setVisible(true);
                }

                boolean isDisableImplicit = getIsDisableImplicit(activity);

                if (corrDataAssocTable != null) {
                    corrDataAssocTable.getViewer().setInput(input);
                    updateActionEnablement();
                }

                if (Xpdl2ModelUtil.isMessageProcessStartActivity(activity)) {
                    //
                    // If it's a message start activity then we need to show /
                    // update the "No correlation data init reqd" checkbox but
                    if (disableImplicit != null
                            && !disableImplicit.isDisposed()) {

                        disableImplicit.setSelection(isDisableImplicit);

                        // Show the checkbox and relayout if necessary.
                        if (!disableImplicit.getVisible()) {
                            disableImplicit.setLayoutData(new GridData(
                                    GridData.FILL_HORIZONTAL));

                            disableImplicit.setVisible(true);

                            disableImplicit.getParent().layout(true, true);
                        }
                    }
                } else {
                    //
                    // Otherwise for (in-flow request api activities) hide the
                    // "No correlation data init reqd" checkbox
                    if (disableImplicit != null
                            && !disableImplicit.isDisposed()) {
                        if (disableImplicit.getVisible()) {
                            GridData layoutData =
                                    new GridData(GridData.FILL_HORIZONTAL);
                            layoutData.heightHint = 1;
                            disableImplicit.setLayoutData(layoutData);

                            disableImplicit.setVisible(false);

                            disableImplicit.getParent().layout(true, true);
                        }
                    }
                }
            }
        }
    }

    @Override
    public Control createExpandableSectionContent(String sectionId,
            Composite container, XpdFormToolkit toolkit) {
        Control control = null;
        if (ASSOCIATED_CORRELATION_DATA_SECTION_ID.equals(sectionId)) {
            Composite correlationContainer = toolkit.createComposite(container);
            GridLayout layout = new GridLayout(1, false);
            layout.marginHeight = 0;
            layout.marginWidth = 0;
            correlationContainer.setLayout(layout);

            disableImplicit =
                    toolkit.createButton(correlationContainer,
                            Messages.AssociatedParameterWithCorrelationSection_DisableImplicitLabel,
                            SWT.CHECK,
                            "AssociatedParameterWithCorrelationSection.disableImplicit"); //$NON-NLS-1$
            GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
            layoutData.heightHint = 1;
            disableImplicit.setVisible(false);
            disableImplicit.setLayoutData(layoutData);
            manageControl(disableImplicit);

            corrDataAssocTable =
                    new CorrelationDataAssociationTable(correlationContainer,
                            toolkit, getEditingDomain());
            corrDataAssocTable.setLayoutData(new GridData(GridData.FILL_BOTH));

            control = correlationContainer;
            corrDataAssocTable
                    .getViewer()
                    .addSelectionChangedListener(new ISelectionChangedListener() {

                        @Override
                        public void selectionChanged(SelectionChangedEvent event) {
                            // When selection changes ensure that when disable
                            // implicit association is set we override the
                            // default behaviour of table that says "enable
                            // remove button when object when row is selected.
                            updateActionEnablement();
                        }

                    });

        } else {
            control =
                    super.createExpandableSectionContent(sectionId,
                            container,
                            toolkit);
        }
        return control;
    }

    /**
     * Set the state of the table actions according to various criteria.
     * <p>
     * The Add button should be disabled if the "No correlation data init reqd"
     * option is checked.
     * <p>
     * The remove button should be disabled if the
     * "No correlation data init reqd" option is checked OR if the current
     * selection is "[All Correlation Data]" OR there is no current selection.
     * 
     * @since MR 39296 - The correlation data association table controls are
     *        enabled even when the
     *        "No correlation data initialisation required." check box is
     *        selected.
     */
    private void updateActionEnablement() {
        if (getInput() instanceof Activity) {

            boolean isDisableImplicitSelected = disableImplicit.getSelection();
            if (isDisableImplicitSelected) {
                corrDataAssocTable.setEnableActions(false);
            } else {
                corrDataAssocTable.setEnableActions(true);
            }

        }

    }

    private boolean getIsDisableImplicit(Activity activity) {
        return ProcessInterfaceUtil
                .isImplicitCorrelationAssociationDisabled(activity);
    }

    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;
        if (obj == disableImplicit) {
            EObject input = getInput();
            if (input instanceof Activity && disableImplicit != null) {
                Activity activity = (Activity) input;
                boolean isDisableImplicit = disableImplicit.getSelection();

                if (isDisableImplicit != getIsDisableImplicit(activity)) {
                    Object other =
                            Xpdl2ModelUtil
                                    .getOtherElement(activity,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_AssociatedCorrelationFields());
                    if (other instanceof AssociatedCorrelationFields
                            && !isDisableImplicit) {
                        AssociatedCorrelationFields fieldContainer =
                                (AssociatedCorrelationFields) other;
                        cmd =
                                SetCommand
                                        .create(getEditingDomain(),
                                                fieldContainer,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getAssociatedCorrelationFields_DisableImplicitAssociation(),
                                                isDisableImplicit);
                    } else {
                        AssociatedCorrelationFields fields =
                                XpdExtensionFactory.eINSTANCE
                                        .createAssociatedCorrelationFields();
                        fields.setDisableImplicitAssociation(isDisableImplicit);

                        CompoundCommand ccmd = new CompoundCommand();
                        cmd = ccmd;
                        ccmd.append(Xpdl2ModelUtil
                                .getSetOtherElementCommand(getEditingDomain(),
                                        activity,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_AssociatedCorrelationFields(),
                                        fields));
                        EList<AssociatedCorrelationField> assocData =
                                fields.getAssociatedCorrelationField();
                        if (!assocData.isEmpty()) {
                            ccmd.append(RemoveCommand
                                    .create(getEditingDomain(), assocData));
                        }

                    }
                }
            }
        } else {
            cmd = super.doGetCommand(obj);
        }
        return cmd;
    }

    /**
     * Filter for section.
     */
    @Override
    public boolean select(Object toTest) {
        EObject eo = getBaseSelectObject(toTest);
        if (eo != null) {
            if (Xpdl2Package.eINSTANCE.getActivity().isSuperTypeOf(eo.eClass())) {
                Activity activity = (Activity) eo;

                /*
                 * SDA-39. Don't use section with correlation data for Decision
                 * Flow.
                 */
                if (!DecisionFlowUtil.isDecisionFlow(activity.getProcess())) {
                    /*
                     * Sid ACE-6338 Allow correlation data association for Incoming Request intermediate events, receive
                     * tasks and Incoming Request start events in event sub-processes
                     */
					/*
					 * ACE-6836 Re-enable Correlation Data and Hence Event initialisers for Incoming Request Event
					 * handlers and Event Sub-processes
					 */
                	if (activity.getEvent() instanceof IntermediateEvent) {
                            IntermediateEvent interEvent = (IntermediateEvent) activity.getEvent();

                            if (TriggerType.NONE_LITERAL.equals(interEvent.getTrigger())) {
                                return true;
                            }
                        } else if (activity.getEvent() instanceof StartEvent) {
                            StartEvent startEvent = (StartEvent) activity.getEvent();

                            if (EventObjectUtil.isEventSubProcessStartRequestEvent(activity)) {

                                return true;
                            }
                        }
                        else if (TaskType.RECEIVE_LITERAL.equals(TaskObjectUtil.getTaskTypeStrict(activity))) {
                            return true;
                        }
                }
            }
        }
        return false;
    }

}
