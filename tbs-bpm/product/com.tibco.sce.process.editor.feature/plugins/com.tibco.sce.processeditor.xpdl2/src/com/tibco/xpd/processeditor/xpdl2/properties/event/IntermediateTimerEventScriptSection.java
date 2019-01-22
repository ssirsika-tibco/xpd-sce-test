/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event;

import org.eclipse.emf.common.command.Command;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Property section for intermediate timer events (these have 2 expandable
 * script sections, the main one and the reschedule one).
 * 
 * @author aallway
 * @since 18 Mar 2013
 */
public class IntermediateTimerEventScriptSection extends
        AbstractFilteredTransactionalSection implements ISectionContentCreator {

    private ExpandableSectionStacker sectionStacker;

    private static final String MAIN_SCRIPT_SECTION = "MainScriptSection"; //$NON-NLS-1$

    private static final String RESCHEDULE_SCRIPT_SECTION =
            "RescheduleScriptSection"; //$NON-NLS-1$

    private TimerEventScriptSection mainScriptSection;

    private RescheduleTimerEventScriptSection rescheduleScriptSection;

    /**
     * @param eClass
     */
    public IntermediateTimerEventScriptSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());

        sectionStacker =
                new ExpandableSectionStacker(getClass().getName()
                        + ".sectionStacker"); //$NON-NLS-1$

        sectionStacker
                .addSection(MAIN_SCRIPT_SECTION,
                        Messages.IntermediateTimerEventScriptSection_InitialTimerScript_title,
                        100,
                        true,
                        true);

        sectionStacker
                .addSection(RESCHEDULE_SCRIPT_SECTION,
                        Messages.IntermediateTimerEventScriptSection_RescheduleTimerScript_title,
                        100,
                        false,
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
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new FillLayout());

        sectionStacker.createExpandableSections(root, toolkit, this);

        return root;
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
        if (MAIN_SCRIPT_SECTION.equals(sectionId)) {
            Composite sectContainer = toolkit.createComposite(container);

            FillLayout fillLayout = new FillLayout();
            fillLayout.marginHeight = 0;
            fillLayout.marginWidth = 0;
            sectContainer.setLayout(fillLayout);

            mainScriptSection = new TimerEventScriptSection();
            mainScriptSection.createControls(sectContainer,
                    getPropertySheetPage());

            return sectContainer;

        } else if (RESCHEDULE_SCRIPT_SECTION.equals(sectionId)) {
            Composite sectContainer = toolkit.createComposite(container);

            FillLayout fillLayout = new FillLayout();
            fillLayout.marginHeight = 0;
            fillLayout.marginWidth = 0;
            sectContainer.setLayout(fillLayout);

            rescheduleScriptSection = new RescheduleTimerEventScriptSection();

            rescheduleScriptSection.createControls(sectContainer,
                    getPropertySheetPage());

            return sectContainer;
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

        if (mainScriptSection != null) {
            mainScriptSection.setInput(part, selection);
        }

        if (rescheduleScriptSection != null) {
            rescheduleScriptSection.setInput(part, selection);
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        if (mainScriptSection != null) {
            mainScriptSection.refresh();
        }

        if (rescheduleScriptSection != null) {
            rescheduleScriptSection.refresh();
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

}
