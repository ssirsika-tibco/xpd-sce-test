/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.SashSection;
import com.tibco.xpd.processeditor.xpdl2.properties.adhoc.AdhocConfigurationSection;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldChangedListener;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldProposalProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.util.PriorityValue;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.AsyncExecutionMode;
import com.tibco.xpd.xpdExtension.SubProcessStartStrategy;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExecutionType;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Priority;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Lifecycle expandable section of a call sub-process activity.
 * 
 * @author sajain
 * @since Jan 5, 2015
 */
public class CallSubProcessLifecycleSection extends
        AbstractFilteredTransactionalSection implements SashSection {

    /*
     * XPD-5475 - Switch to radio buttons instead of combo box for process
     * priority so that we can sensibly provide a user defined option as well as
     * indicating the 5 main options.
     */

    private Composite businessSubProcessConfigContainer;

    private Composite startStrategyBtnContainer;

    private PriorityValue[] priorityValues = PriorityValue
            .getSubProcessTaskPriorityValues();

    private Button priorityButtons[];

    private static String CUSTOM_PRIORITY_BTN_DATA = "CUSTOM_PRIORITY_DATA"; //$NON-NLS-1$

    private DecoratedField customPriorityText;

    private Button customPriorityButton;

    private Button startImmediateButton;

    private Button scheduleStartButton;

    private Label startSchedulingLabel;

    private Label invocationModeLabel;

    private Label suspensionLabel;

    private Button synchronousButton;

    private Button asynchronousDetachedButton;

    private Button asynchronousAttachedButton;

    private Button suspendResumeWithParentButton;

    private Composite priorityBtnContainer;

    private static final String DEFAULT_SUBPROCID = "-unknown-"; //$NON-NLS-1$

    /**
     * Lifecycle expandable section of a call sub-process activity.
     * 
     * @param eClass
     */
    public CallSubProcessLifecycleSection() {

        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#setInput(java.util.Collection)
     * 
     * @param items
     */
    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);

        /*
         * We need to ensure that once user has explicitly selected custom and
         * is same value as one of the default value, that we don't reselect
         * default on next refresh because value in text box not changed yet.
         * i.e. we don't want to deselect custom when user types one of the
         * preset value (at least not until next set input.
         * 
         * So all we need to do is ensure that we deselect customPriorityButton
         * before we start, then first refresh after doing setInput will set to
         * "custom" if a preset values is not the current priority - then
         * subsequently it will say "if custom button is already selected" then
         * select it and put value in text box in preference to preset value.
         */

        if (customPriorityButton != null) {
            if (customPriorityButton.getSelection()) {
                customPriorityButton.setSelection(false);
            }
        }
    }

    /**
     * 
     * @see com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection#select(java.lang.Object)
     * 
     * @param toTest
     * @return <code>true</code> always, let
     *         {@link AdhocConfigurationSection#select(Object)} do the
     *         filtering.
     */
    @Override
    public boolean select(Object toTest) {

        return true;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.SashSection#shouldSashSectionRefresh(java.util.List)
     * 
     * @param notifications
     * @return
     */
    @Override
    public boolean shouldSashSectionRefresh(List<Notification> notifications) {
        return true;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        Activity act = getActivity();

        if (act != null) {

            /*
             * handle the start strategy controls
             */
            boolean enableInvocationMode =
                    (act.getImplementation() instanceof SubFlow && getSubProcessId(act) != null);

            invocationModeLabel.setEnabled(enableInvocationMode);
            synchronousButton.setEnabled(enableInvocationMode);
            asynchronousAttachedButton.setEnabled(enableInvocationMode);
            asynchronousDetachedButton.setEnabled(enableInvocationMode);

            startSchedulingLabel.setEnabled(enableInvocationMode);
            startImmediateButton.setEnabled(enableInvocationMode);
            scheduleStartButton.setEnabled(enableInvocationMode);

            suspensionLabel.setEnabled(enableInvocationMode);
            suspendResumeWithParentButton.setEnabled(enableInvocationMode);

            /*
             * Disable priority by default and later on enable it when schedule
             * start is set.
             */
            setEnabled(priorityBtnContainer, false);

            if (enableInvocationMode) {
                SubFlow subFlow = (SubFlow) act.getImplementation();

                Object execModeObject =
                        Xpdl2ModelUtil.getOtherAttribute(subFlow,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_AsyncExecutionMode());

                if (execModeObject == null) {

                    synchronousButton.setSelection(true);
                    asynchronousDetachedButton.setSelection(false);
                    asynchronousAttachedButton.setSelection(false);

                } else if (execModeObject instanceof AsyncExecutionMode) {

                    AsyncExecutionMode execMode =
                            (AsyncExecutionMode) execModeObject;

                    if (AsyncExecutionMode.DETACHED.equals(execMode)) {

                        asynchronousDetachedButton.setSelection(true);
                        synchronousButton.setSelection(false);
                        asynchronousAttachedButton.setSelection(false);

                        /*
                         * ABPM-897: Saket: 'Suspend/resume with Sub-Process
                         * With Parent Process' is disabled for all asynchronous
                         * detached invocations (because the sub-process runs
                         * independently).
                         */
                        suspendResumeWithParentButton.setSelection(false);
                        suspendResumeWithParentButton.setEnabled(false);

                    } else if (AsyncExecutionMode.ATTACHED.equals(execMode)) {

                        asynchronousAttachedButton.setSelection(true);
                        synchronousButton.setSelection(false);
                        asynchronousDetachedButton.setSelection(false);

                    }
                }

                SubProcessStartStrategy startStrategy =
                        (SubProcessStartStrategy) Xpdl2ModelUtil
                                .getOtherAttribute(subFlow,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_StartStrategy());

                if (SubProcessStartStrategy.START_IMMEDIATELY
                        .equals(startStrategy)) {

                    scheduleStartButton.setSelection(false);
                    startImmediateButton.setSelection(true);

                    setEnabled(priorityBtnContainer, false);

                    for (Button btn : priorityButtons) {
                        btn.setSelection(false);
                    }

                    customPriorityButton.setSelection(false);

                    updateText((Text) customPriorityText.getControl(), ""); //$NON-NLS-1$

                    /*
                     * AMX BPM Process Engine only supports suspend resume with
                     * parent = true with StartImmediately
                     */
                    suspendResumeWithParentButton.setEnabled(false);

                } else if (SubProcessStartStrategy.SCHEDULE_START
                        .equals(startStrategy)) {
                    startImmediateButton.setSelection(false);
                    scheduleStartButton.setSelection(true);

                    suspendResumeWithParentButton.setEnabled(true);

                    /*
                     * Handle priority drop down.
                     */
                    setEnabled(priorityBtnContainer, true);

                    Priority priority = act.getPriority();

                    String currPriority = null;
                    if (priority != null) {
                        /*
                         * Priority element present means its either defined or
                         * empty for "inherit from parent" (val="") if empty.
                         */
                        currPriority = priority.getValue();
                        if (currPriority == null) {
                            currPriority = ""; //$NON-NLS-1$
                        }
                    }
                    if (customPriorityButton.getSelection()) {
                        if (!customPriorityText.getControl().getEnabled()) {
                            customPriorityText.getControl().setEnabled(true);
                        }
                        updateText((Text) customPriorityText.getControl(),
                                currPriority);

                    } else {
                        boolean presetSelected = false;

                        for (Button btn : priorityButtons) {
                            PriorityValue priorityValue =
                                    (PriorityValue) btn.getData();

                            if (priorityValue.getValue().equals(currPriority)) {
                                presetSelected = true;
                                if (!btn.getSelection()) {
                                    btn.setSelection(true);
                                    btn.update();
                                }
                            } else {
                                if (btn.getSelection()) {
                                    btn.setSelection(false);
                                    btn.update();
                                }
                            }
                        }

                        if (!presetSelected) {
                            /*
                             * If not selected a preset value then select and
                             * enable custom.
                             */
                            customPriorityButton.setSelection(true);

                            if (!customPriorityText.getControl().getEnabled()) {
                                customPriorityText.getControl()
                                        .setEnabled(true);
                            }

                            updateText((Text) customPriorityText.getControl(),
                                    currPriority);

                        } else {
                            /*
                             * If we're not in custom edit mode then disable and
                             * empty the custom text
                             */
                            if (customPriorityText.getControl().getEnabled()) {
                                customPriorityText.getControl()
                                        .setEnabled(false);
                            }

                            updateText((Text) customPriorityText.getControl(),
                                    ""); //$NON-NLS-1$
                        }
                    }
                }

                boolean suspendWithParent =
                        Xpdl2ModelUtil
                                .getOtherAttributeAsBoolean(subFlow,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_SuspendResumeWithParent());

                suspendResumeWithParentButton.setSelection(suspendWithParent);

            } else {
                /*
                 * Deselect choice until user selects a sub-process or interface
                 * (because the default is different for both).
                 */
                startImmediateButton.setSelection(false);
                scheduleStartButton.setSelection(false);

                setEnabled(priorityBtnContainer, false);
                for (Button btn : priorityButtons) {
                    btn.setSelection(false);
                }
                customPriorityButton.setSelection(false);
                updateText((Text) customPriorityText.getControl(), ""); //$NON-NLS-1$

                suspendResumeWithParentButton.setSelection(false);
            }

            /*
             * ABPM-897: Saket: Hide suspendResumeWithParentButton for
             * pageflows.
             */
            if (Xpdl2ModelUtil.isPageflow(act.getProcess())) {
                suspensionLabel.setVisible(false);
                suspendResumeWithParentButton.setVisible(false);
            } else {
                suspensionLabel.setVisible(true);
                suspendResumeWithParentButton.setVisible(true);
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

        GridLayout gl = new GridLayout(3, false);
        gl.marginHeight = 0;
        gl.marginBottom = 5;
        root.setLayout(gl);

        /*
         * New start strategy controls for XPD-3445.
         * 
         * Old Priority control is now part of the start strategy container
         */
        businessSubProcessConfigContainer = toolkit.createComposite(root);

        GridData data = createBusinessprocessConfigGridData();
        businessSubProcessConfigContainer.setLayoutData(data);

        gl = new GridLayout(2, false);
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        businessSubProcessConfigContainer.setLayout(gl);

        invocationModeLabel =
                toolkit.createLabel(businessSubProcessConfigContainer,
                        Messages.TaskTypeIndependentSubProcSection_InvocationStrategy_label);
        GridData gd1 = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
        gd1.verticalIndent = 4;
        invocationModeLabel.setLayoutData(gd1);

        Composite invocationModeContainer =
                toolkit.createComposite(businessSubProcessConfigContainer);
        invocationModeContainer.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));
        GridLayout gl1 = new GridLayout(3, false);
        invocationModeContainer.setLayout(gl1);

        synchronousButton =
                toolkit.createButton(invocationModeContainer,
                        Messages.TaskTypeIndependentSubProcSection_Synchronous_Label,
                        SWT.RADIO);
        synchronousButton
                .setToolTipText(Messages.TaskTypeIndependentSubProcSection_Synchronous_Tooltip);
        synchronousButton.setLayoutData(new GridData());
        manageControl(synchronousButton);

        asynchronousDetachedButton =
                toolkit.createButton(invocationModeContainer,
                        Messages.TaskTypeIndependentSubProcSection_AsyncDetached_Label,
                        SWT.RADIO);
        asynchronousDetachedButton
                .setToolTipText(Messages.TaskTypeIndependentSubProcSection_AsyncDetached_Tooltip);
        asynchronousDetachedButton.setLayoutData(new GridData());
        manageControl(asynchronousDetachedButton);

        asynchronousAttachedButton =
                toolkit.createButton(invocationModeContainer,
                        Messages.TaskTypeIndependentSubProcSection_AsyncAttahced_Label,
                        SWT.RADIO);
        asynchronousAttachedButton
                .setToolTipText(Messages.TaskTypeIndependentSubProcSection_AsyncAttached_Tooltip);
        asynchronousAttachedButton.setLayoutData(new GridData());
        manageControl(asynchronousAttachedButton);

        startSchedulingLabel =
                toolkit.createLabel(businessSubProcessConfigContainer,
                        Messages.TaskTypeIndependentSubProcSection_StartScheduling_label);
        GridData gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
        gd.verticalIndent = 4;
        startSchedulingLabel.setLayoutData(gd);

        startStrategyBtnContainer =
                toolkit.createComposite(businessSubProcessConfigContainer);
        startStrategyBtnContainer.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));
        gl = new GridLayout(2, false);
        startStrategyBtnContainer.setLayout(gl);

        startImmediateButton =
                toolkit.createButton(startStrategyBtnContainer,
                        Messages.TaskTypeIndependentSubProcSection_StartImmediately_label,
                        SWT.RADIO);
        startImmediateButton
                .setToolTipText(Messages.TaskTypeIndependentSubProcSection_StartImmediately_tooltip);
        startImmediateButton.setLayoutData(new GridData());
        manageControl(startImmediateButton);

        scheduleStartButton =
                toolkit.createButton(startStrategyBtnContainer,
                        Messages.TaskTypeIndependentSubProcSection_ScheduleStart_label,
                        SWT.RADIO);
        scheduleStartButton
                .setToolTipText(Messages.TaskTypeIndependentSubProcSection_ScheduleStart_tooltip);
        scheduleStartButton.setLayoutData(new GridData());
        manageControl(scheduleStartButton);

        Label filler =
                toolkit.createLabel(businessSubProcessConfigContainer, ""); //$NON-NLS-1$
        gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
        filler.setLayoutData(gd);

        /*
         * Priority control is now only available for Schedule Start Request
         * strategy.
         */
        Composite innerPriorityContainer =
                toolkit.createComposite(businessSubProcessConfigContainer);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        innerPriorityContainer.setLayoutData(gd);
        gl = new GridLayout(2, false);
        innerPriorityContainer.setLayout(gl);

        createPriorityControls(innerPriorityContainer, toolkit);

        /*
         * Sub-process life cycle options.
         */

        suspensionLabel =
                toolkit.createLabel(businessSubProcessConfigContainer,
                        Messages.TaskTypeIndependentSubProcSection_Suspension_label);
        GridData gd2 = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
        gd2.verticalIndent = 4;
        suspensionLabel.setLayoutData(gd2);

        suspendResumeWithParentButton =
                toolkit.createButton(businessSubProcessConfigContainer,
                        Messages.TaskTypeIndependentSubProcSection_SuspendResumeWithParet_checkbox,
                        SWT.CHECK);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.verticalIndent += 4;
        suspendResumeWithParentButton.setLayoutData(gd);
        manageControl(suspendResumeWithParentButton);

        return root;
    }

    /**
     * Create the priority controls.
     * 
     * @param innerPriorityContainer
     * @param toolkit
     */
    private void createPriorityControls(Composite innerPriorityContainer,
            XpdFormToolkit toolkit) {
        /*
         * Add set priority value buttons.
         */
        priorityBtnContainer =
                toolkit.createComposite(innerPriorityContainer, SWT.BORDER);
        priorityBtnContainer.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        GridLayout gl = new GridLayout(2, false);

        gl.marginHeight = 5;
        gl.marginWidth = 5;
        gl.marginLeft = 5;
        gl.marginRight = 5;

        priorityBtnContainer.setLayout(gl);
        priorityButtons = new Button[priorityValues.length];

        int btnIdx = 0;
        for (PriorityValue priorityValue : priorityValues) {
            Button btn =
                    toolkit.createButton(priorityBtnContainer,
                            priorityValue.getLabel(),
                            SWT.RADIO,
                            "priorityInstrumentation"); //$NON-NLS-1$
            btn.setData(priorityValue);

            GridData gd2 = new GridData();
            gd2.horizontalSpan = 1;
            btn.setLayoutData(gd2);

            manageControl(btn);

            priorityButtons[btnIdx++] = btn;

            Label filler = toolkit.createLabel(priorityBtnContainer, ""); //$NON-NLS-1$
            GridData gdFiller = new GridData(GridData.FILL_HORIZONTAL);
            filler.setLayoutData(gdFiller);
        }

        /*
         * Add custom button and text
         */
        customPriorityButton =
                toolkit.createButton(priorityBtnContainer,
                        Messages.ProcessResourceSection_CustomPriority_button_label,
                        SWT.RADIO,
                        "priorityInstrumentation"); //$NON-NLS-1$
        customPriorityButton.setData(CUSTOM_PRIORITY_BTN_DATA);
        customPriorityButton.setLayoutData(new GridData());
        manageControl(customPriorityButton);

        /*
         * Custom priority is now content assist allowing selection of in-scope
         * process data and / or explicit numeric value.
         */
        FixedValueFieldProposalProvider proposalProvider =
                new FixedValueFieldAssistHelper.FixedValueFieldProposalProvider() {
                    @Override
                    public Object[] getProposals() {
                        List<String> dataNames = new ArrayList<String>();

                        Activity activity = getActivity();

                        if (activity != null) {
                            List<ProcessRelevantData> dataForActivity =
                                    ProcessInterfaceUtil
                                            .getAllAvailableRelevantDataForActivity(activity);

                            for (ProcessRelevantData data : dataForActivity) {
                                dataNames.add(data.getName());
                            }
                        }

                        return dataNames.toArray();
                    }
                };

        FixedValueFieldAssistHelper refTaskHelper =
                new FixedValueFieldAssistHelper(toolkit, priorityBtnContainer,
                        proposalProvider, false);

        refTaskHelper
                .addValueChangedListener(new FixedValueFieldChangedListener() {

                    @Override
                    public void fixedValueFieldChanged(Object newValue) {
                        Activity activity = getActivity();

                        if (activity != null && newValue instanceof String) {
                            Command cmd =
                                    createSetPriorityCommand(getEditingDomain(),
                                            activity,
                                            newValue != null ? (String) newValue
                                                    : ""); //$NON-NLS-1$

                            if (cmd != null && cmd.canExecute()) {
                                getEditingDomain().getCommandStack()
                                        .execute(cmd);
                            }

                        }
                    }
                });

        customPriorityText = refTaskHelper.getDecoratedField();
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalIndent = -120;
        customPriorityText.getLayoutControl().setLayoutData(gd);
        customPriorityText.getControl().setEnabled(false);

        customPriorityText.getLayoutControl()
                .setBackground(priorityBtnContainer.getBackground());

        customPriorityText
                .getControl()
                .setToolTipText(Messages.TaskTypeIndependentSubProcSection_CustomPriority_tooltip);

    }

    /**
     * Get xpdl2 Activity from the input
     * 
     * @return <code>Activity</code> if input is valid, <b>null</b> otherwise.
     */
    private Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            return (Activity) o;
        }
        return null;
    }

    /**
     * @param ed
     * @param act
     * @param modelPriorityValue
     * @return A command to set the activity priority to the given value.
     */
    private Command createSetPriorityCommand(EditingDomain ed, Activity act,
            String modelPriorityValue) {
        Command cmd;
        cmd =
                new CompoundCommand(
                        Messages.TaskTypeIndependentSubProcSection_SetSubProcTaskPriority_menu);

        Priority priority = Xpdl2Factory.eINSTANCE.createPriority();

        priority = Xpdl2Factory.eINSTANCE.createPriority();
        priority.setValue(modelPriorityValue);
        ((CompoundCommand) cmd).append(SetCommand.create(ed,
                act,
                Xpdl2Package.eINSTANCE.getActivity_Priority(),
                priority));
        return cmd;
    }

    /**
     * @return grid data for start strategy container.
     */
    protected GridData createBusinessprocessConfigGridData() {
        GridData data;
        data = new GridData(GridData.FILL_HORIZONTAL | SWT.V_SCROLL);
        data.horizontalSpan = 2;
        return data;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {

        Command cmd = null;

        Activity act = getActivity();
        EditingDomain ed = getEditingDomain();

        if (act != null && ed != null) {
            if (obj instanceof Button
                    && CUSTOM_PRIORITY_BTN_DATA
                            .equals(((Button) obj).getData())) {
                /*
                 * Custom priority. The value does not change immediately, so
                 * just force a refresh which will say "if custom radio is
                 * selected then setup text box".
                 */
                refresh();

                return null;

            } else if (obj instanceof Button
                    && ((Button) obj).getData() instanceof PriorityValue) {
                /*
                 * Preset priority values.
                 */
                PriorityValue priorityValue =
                        (PriorityValue) ((Button) obj).getData();
                String textPriority = priorityValue.getValue();

                cmd = createSetPriorityCommand(ed, act, textPriority);

            } else if (obj == synchronousButton) {
                if (act.getImplementation() instanceof SubFlow) {

                    SubFlow subFlow = (SubFlow) (act.getImplementation());

                    Object execModeObject =
                            Xpdl2ModelUtil
                                    .getOtherAttribute(subFlow,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_AsyncExecutionMode());

                    if (execModeObject instanceof AsyncExecutionMode) {

                        cmd =
                                new CompoundCommand(
                                        Messages.TaskTypeIndependentSubProcSection_SetSubProcInvocationMode_menu);

                        ((CompoundCommand) cmd)
                                .append(Xpdl2ModelUtil
                                        .getSetOtherAttributeCommand(ed,
                                                subFlow,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_AsyncExecutionMode(),
                                                null));

                        if (subFlow.isSetExecution()) {

                            ((CompoundCommand) cmd).append(SetCommand
                                    .create(ed,
                                            subFlow,
                                            Xpdl2Package.eINSTANCE
                                                    .getSubFlow_Execution(),
                                            SetCommand.UNSET_VALUE));

                        }
                    }
                }
            } else if (obj == asynchronousDetachedButton) {
                if (act.getImplementation() instanceof SubFlow) {

                    SubFlow subFlow = (SubFlow) (act.getImplementation());

                    cmd =
                            new CompoundCommand(
                                    Messages.TaskTypeIndependentSubProcSection_SetSubProcInvocationMode_menu);

                    ((CompoundCommand) cmd)
                            .append(Xpdl2ModelUtil
                                    .getSetOtherAttributeCommand(ed,
                                            subFlow,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_AsyncExecutionMode(),
                                            AsyncExecutionMode.DETACHED));

                    if (!subFlow.isSetExecution()) {

                        ((CompoundCommand) cmd).append(SetCommand.create(ed,
                                subFlow,
                                Xpdl2Package.eINSTANCE.getSubFlow_Execution(),
                                ExecutionType.ASYNCHR_LITERAL));
                    }
                }
            } else if (obj == asynchronousAttachedButton) {
                if (act.getImplementation() instanceof SubFlow) {

                    SubFlow subFlow = (SubFlow) (act.getImplementation());

                    cmd =
                            new CompoundCommand(
                                    Messages.TaskTypeIndependentSubProcSection_SetSubProcInvocationMode_menu);

                    ((CompoundCommand) cmd)
                            .append(Xpdl2ModelUtil
                                    .getSetOtherAttributeCommand(ed,
                                            subFlow,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_AsyncExecutionMode(),
                                            AsyncExecutionMode.ATTACHED));

                    if (!subFlow.isSetExecution()) {

                        ((CompoundCommand) cmd).append(SetCommand.create(ed,
                                subFlow,
                                Xpdl2Package.eINSTANCE.getSubFlow_Execution(),
                                ExecutionType.ASYNCHR_LITERAL));
                    }
                }
            } else if (obj == startImmediateButton) {
                if (act.getImplementation() instanceof SubFlow) {
                    cmd =
                            new CompoundCommand(
                                    Messages.TaskTypeIndependentSubProcSection_SetSubProcStartStrategy_menu);

                    ((CompoundCommand) cmd).append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(ed,
                                    ((SubFlow) act.getImplementation()),
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_StartStrategy(),
                                    SubProcessStartStrategy.START_IMMEDIATELY));
                }
            } else if (obj == scheduleStartButton) {
                if (act.getImplementation() instanceof SubFlow) {
                    cmd =
                            new CompoundCommand(
                                    Messages.TaskTypeIndependentSubProcSection_SetSubProcStartStrategy_menu);

                    ((CompoundCommand) cmd).append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(ed,
                                    ((SubFlow) act.getImplementation()),
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_StartStrategy(),
                                    SubProcessStartStrategy.SCHEDULE_START));
                }
            } else if (obj == suspendResumeWithParentButton) {
                if (act.getImplementation() instanceof SubFlow) {
                    cmd =
                            new CompoundCommand(
                                    Messages.TaskTypeIndependentSubProcSection_SetSuspendResumeBehaviour_menu);

                    ((CompoundCommand) cmd)
                            .append(Xpdl2ModelUtil
                                    .getSetOtherAttributeCommand(ed,
                                            ((SubFlow) act.getImplementation()),
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_SuspendResumeWithParent(),
                                            suspendResumeWithParentButton
                                                    .getSelection()));
                }
            }
        }

        return cmd;
    }

    private String getSubProcessId(Activity act) {
        String processId = null;
        if (act != null) {
            Implementation implementation = act.getImplementation();
            if (implementation instanceof SubFlow) {
                SubFlow subFlow = (SubFlow) implementation;
                processId = subFlow.getProcessId();
                if (processId != null && processId.equals(DEFAULT_SUBPROCID)) {
                    processId = null;
                }
            }
        }
        return processId;
    }

}
