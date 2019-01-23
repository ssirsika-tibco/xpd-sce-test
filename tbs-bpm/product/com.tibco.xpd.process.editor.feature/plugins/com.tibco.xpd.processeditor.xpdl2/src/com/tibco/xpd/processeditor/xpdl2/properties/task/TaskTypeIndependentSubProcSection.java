/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.task;

import org.eclipse.emf.common.command.Command;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Task type independent sub-process section
 * 
 * @author aallway
 */
public class TaskTypeIndependentSubProcSection extends
        AbstractFilteredTransactionalSection implements IPluginContribution,
        ISectionContentCreator {

    /**
     * Section stacker to add two expandable sections: sub-process details and
     * lifecycle.
     */
    private ExpandableSectionStacker sectionStacker;

    /**
     * Section ID for call-subprocess details expandable section.
     */
    private static final String CALL_SUBPROC_DETAILS_SECTION =
            "CallSubProcessDetailsSection"; //$NON-NLS-1$

    /**
     * Section ID for call-subprocess lifecycle expandable section.
     */
    private static final String CALL_SUB_PROCESS_LIFECYCLE_SECTION =
            "CallSubProcessLifecycleSection"; //$NON-NLS-1$

    /**
     * Call sub-process details section instance.
     */
    private CallSubProcessDetailsSection callSubProcDetailsSection;

    /**
     * Call sub-process lifecycle section instance.
     */
    private CallSubProcessLifecycleSection callSubProcLifecycleSection;

    /**
     * @param eClass
     */
    public TaskTypeIndependentSubProcSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());

        /*
         * Initialize stacker.
         */
        sectionStacker =
                new ExpandableSectionStacker(getClass().getName()
                        + ".sectionStacker"); //$NON-NLS-1$

        /*
         * Add call sub-process details expandable section.
         */
        sectionStacker
                .addSection(CALL_SUBPROC_DETAILS_SECTION,
                        Messages.TaskTypeIndependentSubProcSection_SubProcDetails_label,
                        100,
                        true,
                        true);

        /*
         * Add call sub-process lifecycle expandable section.
         */
        sectionStacker.addSection(CALL_SUB_PROCESS_LIFECYCLE_SECTION,
                Messages.TaskTypeIndependentSubProcSection_Lifecycle_label,
                100,
                true,
                true);

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

        /*
         * Create expandable sections.
         */
        sectionStacker.createExpandableSections(parent, toolkit, this);

        return parent;
    }

    /**
     * @see com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator#createExpandableSectionContent(java.lang.String,
     *      org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param sectionId
     * @param container
     * @param toolkit
     * @return
     */
    @Override
    public Control createExpandableSectionContent(String sectionId,
            Composite container, XpdFormToolkit toolkit) {

        /*
         * Check whether the section being processed is call sub-process details
         * section or call sub-process lifecycle section and process
         * accordingly.
         */
        if (CALL_SUBPROC_DETAILS_SECTION.equals(sectionId)) {

            /*
             * Process call sub-process details section.
             */

            callSubProcDetailsSection = new CallSubProcessDetailsSection();
            callSubProcDetailsSection.createControls(container,
                    getPropertySheetPage());

            return container;

        } else if (CALL_SUB_PROCESS_LIFECYCLE_SECTION.equals(sectionId)) {

            /*
             * Process call sub-process lifecycle section.
             */


            callSubProcLifecycleSection = new CallSubProcessLifecycleSection();

            callSubProcLifecycleSection.createControls(container,
                    getPropertySheetPage());

            return container;
        }

        return null;

    }

    /**
     * @see com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator#expandableSectionStateChanged(java.lang.String)
     * 
     * @param sectionId
     */
    @Override
    public void expandableSectionStateChanged(String sectionId) {
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

        if (callSubProcDetailsSection != null) {
            callSubProcDetailsSection.setInput(part, selection);
        }

        if (callSubProcLifecycleSection != null) {
            callSubProcLifecycleSection.setInput(part, selection);
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {

        if (callSubProcDetailsSection != null) {
            callSubProcDetailsSection.refresh();
        }

        if (callSubProcLifecycleSection != null) {
            callSubProcLifecycleSection.refresh();
        }

        /*
         * Call sub-process lifecycle section should only be visible if BPM
         * destination is set on the process.
         */
        if (!ProcessDestinationUtil
                .isBPMDestinationSelected(((Activity) getInput()).getProcess())) {

            sectionStacker
                    .setSectionVisible(CALL_SUB_PROCESS_LIFECYCLE_SECTION,
                            false);

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
        /* Everything is delegated to stacker sections. */
        return null;
    }

    @Override
    public String getLocalId() {
        return "analyst.indieSupProcSection"; //$NON-NLS-1$    
    }

    @Override
    public String getPluginId() {
        return Xpdl2ProcessEditorPlugin.ID;
    }

}
