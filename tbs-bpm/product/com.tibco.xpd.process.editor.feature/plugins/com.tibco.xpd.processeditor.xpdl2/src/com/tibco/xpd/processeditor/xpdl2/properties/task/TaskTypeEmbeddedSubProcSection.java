/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.task;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Task type embedded/event sub-process section
 * 
 * @author aallway
 */
public class TaskTypeEmbeddedSubProcSection extends
        AbstractFilteredTransactionalSection implements IPluginContribution {

    private String instrumentationPrefixName;

    private Button embSubChainedBut;

    private Label chainingLbl;

    private Label typeLbl;

    private Button embSubProcBut;

    private Button eventSubProcBut;

    public TaskTypeEmbeddedSubProcSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    public TaskTypeEmbeddedSubProcSection(String instrumentationPrefixName) {
        super(Xpdl2Package.eINSTANCE.getActivity());
        this.instrumentationPrefixName = instrumentationPrefixName;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#setInput(org.eclipse.ui.IWorkbenchPart,
     *      org.eclipse.jface.viewers.ISelection)
     * 
     * @param part
     * @param selection
     */
    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {
        super.setInput(part, selection);
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        /*
         * Create root composite.
         */
        Composite root = toolkit.createComposite(parent);
        GridLayout gl2 = new GridLayout(2, false);
        gl2.marginHeight = 10;
        gl2.verticalSpacing = 25;
        root.setLayout(gl2);

        /*
         * Row 1: Fill up the first row to display sub process type label and
         * sub process types (embedded and event).
         */
        /*
         * 1. Type label.
         */
        typeLbl =
                toolkit.createLabel(root,
                        Messages.TaskGeneralSection_SUB_PROC_TYPE_LABEL);
        typeLbl.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));

        /*
         * 2. Composite to create the embedded sub process radio button and
         * event sub process radio button.
         */
        Composite comp1 = toolkit.createComposite(root);
        GridLayout gl3 = new GridLayout(2, false);
        gl3.marginHeight = 0;
        comp1.setLayout(gl3);

        /*
         * 2.1. Embedded sub process radio button.
         */
        embSubProcBut =
                toolkit.createButton(comp1,
                        Messages.TaskGeneralSection_EMB_SUB_PROC,
                        SWT.RADIO);
        embSubProcBut.setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_BEGINNING));
        embSubProcBut
                .setToolTipText(Messages.TaskGeneralSection_EMB_SUB_PROC_Tooltip);
        manageControl(embSubProcBut);

        /*
         * 2.2. Event sub process radio button.
         */
        eventSubProcBut =
                toolkit.createButton(comp1,
                        Messages.TaskGeneralSection_EVENT_SUB_PROC,
                        SWT.RADIO);
        eventSubProcBut.setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_BEGINNING));
        eventSubProcBut
                .setToolTipText(Messages.TaskGeneralSection_EVENT_SUB_PROC_Tooltip);
        manageControl(eventSubProcBut);



        /*
         * Don't need separator now as we are putting spaces between rows.
         */
        // Label sep =
        //                toolkit.createLabel(root, "", SWT.SEPARATOR | SWT.HORIZONTAL); //$NON-NLS-1$
        // sep.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        /*
         * Row 3: Fill up the second row to display chaining label and
         * "Chained Execution" check box.
         */
        /*
         * 1. Chaining label.
         */
        chainingLbl =
                toolkit.createLabel(root,
                        Messages.TaskGeneralSection_CHAINING_LABEL);
        chainingLbl.setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_BEGINNING));

        /*
         * 2. Composite to contain "Chained Execution" check box.
         */
        Composite comp3 = toolkit.createComposite(root);
        GridLayout gl5 = new GridLayout(1, false);
        gl5.marginHeight = 0;
        comp3.setLayout(gl5);

        /*
         * 2.1. "Chained Execution" check box.
         */
        embSubChainedBut =
                toolkit.createButton(comp3,
                        com.tibco.xpd.processeditor.xpdl2.internal.Messages.TaskTypeEmbeddedSubProcSection_ChainedExecutionRadioButtonLabel,
                        SWT.CHECK,
                        "isChainedExecution"); //$NON-NLS-1$
        embSubChainedBut.setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_BEGINNING));
        manageControl(embSubChainedBut);

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;
        EditingDomain ed = getEditingDomain();

        Activity act = getActivity();

        if (act != null && ed != null) {

            if (obj == embSubProcBut && embSubProcBut.getSelection()) {
                cmd =
                        TaskObjectUtil.getSetTaskTypeCommandEx(ed,
                                act,
                                TaskType.EMBEDDED_SUBPROCESS_LITERAL,
                                act.getProcess(),
                                true,
                                true,
                                true);

            } else if (obj == eventSubProcBut && eventSubProcBut.getSelection()) {

                cmd =
                        TaskObjectUtil.getSetTaskTypeCommandEx(ed,
                                act,
                                TaskType.EVENT_SUBPROCESS_LITERAL,
                                act.getProcess(),
                                true,
                                true,
                                true);

            }
            // ACE-2011: Remove Unsupported features. Transaction checkbox removed in SCE.
            //
            // else if (obj == embSubTransactionalBut) {
            // CompoundCommand setSubTrans = new CompoundCommand();
            // setSubTrans.append(TaskObjectUtil
            // .getSetSubProcessIsTransactionalCommand(ed,
            // act,
            // embSubTransactionalBut.getSelection()));
            // setSubTrans.append(TaskObjectUtil
            // .getSetSubProcessIsRequireNewCommand(ed,
            // act,
            // embSubTransactionalBut.getSelection()));
            // cmd = setSubTrans;
            // }
            else if (obj == embSubChainedBut) {
                cmd = TaskObjectUtil.getSetSubProcessIsChainedCommand(ed, act, embSubChainedBut.getSelection());
            }
            // else if (obj == embSubRequireNewBut) {
            // cmd = TaskObjectUtil.getSetSubProcessIsRequireNewCommand(ed, act, embSubRequireNewBut.getSelection());
            // }

        }
        return cmd;
    }

    @Override
    protected void doRefresh() {

        Activity act = getActivity();

        if (act != null) {

            setSubProcTypeRadioButtons(TaskType.EVENT_SUBPROCESS_LITERAL
                    .equals(TaskObjectUtil.getTaskTypeStrict(act)));

            embSubChainedBut.setSelection(TaskObjectUtil
                    .getSubprocessIsChained(act));
        }
    }

    /**
     * Set sub process type radio buttons to appropriate values.
     * 
     * @param isEventSubproc
     *            Selection with respect to event sub process type.
     */
    private void setSubProcTypeRadioButtons(boolean isEventSubproc) {
        if (eventSubProcBut.getSelection() != isEventSubproc) {
            eventSubProcBut.setSelection(isEventSubproc);

        }
        if (embSubProcBut.getSelection() == isEventSubproc) {
            embSubProcBut.setSelection(!isEventSubproc);
        }
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

    @Override
    public String getLocalId() {
        return "analyst.embedSubProcSection"; //$NON-NLS-1$
    }

    @Override
    public String getPluginId() {
        return Xpdl2ProcessEditorPlugin.ID;
    }

}
