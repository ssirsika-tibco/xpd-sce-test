/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.process;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.FlowRoutingStyle;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Process diagram appearance property section.
 * 
 * @author aallway
 * @since 15 Mar 2012
 */
public class ProcessAppearancePropertySection extends
        AbstractFilteredTransactionalSection implements ISectionContentCreator {

    private ExpandableSectionStacker sectionStacker;

    private ScrolledComposite scrolledContainer;

    private Composite root;

    private Button routingMultiBtn;

    private Button routingSingleBtn;

    private Button routingUncenteredBtn;

    public final static String ROUTING_SECTION = "routing"; //$NON-NLS-1$

    /**
     * @param eClass
     */
    public ProcessAppearancePropertySection() {
        super(Xpdl2Package.eINSTANCE.getProcess());

        setShowInWizard(false);
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        if (getInput() instanceof Process) {
            Process process = (Process) getInput();
            FlowRoutingStyle routingStyle =
                    (FlowRoutingStyle) Xpdl2ModelUtil
                            .getOtherAttribute(process,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_FlowRoutingStyle());

            if (FlowRoutingStyle.SINGLE_ENTRY_EXIT.equals(routingStyle)) {
                routingSingleBtn.setSelection(true);
                routingMultiBtn.setSelection(false);
                routingUncenteredBtn.setSelection(false);

            } else if (FlowRoutingStyle.UNCENTERED_ON_TASKS
                    .equals(routingStyle)) {
                routingUncenteredBtn.setSelection(true);
                routingMultiBtn.setSelection(false);
                routingSingleBtn.setSelection(false);
            } else {
                routingMultiBtn.setSelection(true);
                routingUncenteredBtn.setSelection(false);
                routingSingleBtn.setSelection(false);
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
        scrolledContainer =
                toolkit.createScrolledComposite(parent, SWT.V_SCROLL
                        | SWT.H_SCROLL);

        scrolledContainer.setExpandHorizontal(true);
        scrolledContainer.setExpandVertical(true);

        root = toolkit.createComposite(scrolledContainer);
        GridLayout gl = new GridLayout(1, false);
        gl.marginLeft = 4;
        gl.marginWidth = 0;
        root.setLayout(gl);

        /*
         * Add expandable sections...
         */
        String sectPrefId =
                getSectionContainerType() + "." + "ProcessAppearanceSection"; //$NON-NLS-1$ //$NON-NLS-2$
        sectionStacker = new ExpandableSectionStacker(sectPrefId);

        /* Connection Routing section. */
        sectionStacker
                .addSection(ROUTING_SECTION,
                        Messages.ProcessAppearancePropertySection_ConnectionROuting_label,
                        SWT.DEFAULT,
                        true,
                        false);

        /*
         * Create the sections...
         */
        Control expandablesContainer =
                sectionStacker.createExpandableSections(root, toolkit, this);

        GridData gd =
                new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL
                        | GridData.GRAB_HORIZONTAL);
        expandablesContainer.setLayoutData(gd);

        // set contents of sections on to the scrolled composite
        scrolledContainer.setContent(root);

        Point prefSize = root.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        scrolledContainer.setMinSize(prefSize);

        setMinimumHeight(prefSize.y);

        return scrolledContainer;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        if (getInput() instanceof Process) {
            Process process = (Process) getInput();

            if (obj == routingMultiBtn) {
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.ProcessAppearancePropertySection_SetRoutingStyle_menu);

                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(getEditingDomain(),
                                process,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_FlowRoutingStyle(),
                                FlowRoutingStyle.MULTI_ENTRY_EXIT));
                return cmd;

            } else if (obj == routingSingleBtn) {
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.ProcessAppearancePropertySection_SetRoutingStyle_menu);

                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(getEditingDomain(),
                                process,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_FlowRoutingStyle(),
                                FlowRoutingStyle.SINGLE_ENTRY_EXIT));
                return cmd;

            } else if (obj == routingUncenteredBtn) {
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.ProcessAppearancePropertySection_SetRoutingStyle_menu);

                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(getEditingDomain(),
                                process,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_FlowRoutingStyle(),
                                FlowRoutingStyle.UNCENTERED_ON_TASKS));
                return cmd;
            }

        }
        return null;
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
        if (ROUTING_SECTION.equals(sectionId)) {
            Composite cmp = toolkit.createComposite(container);

            GridLayout gl = new GridLayout(2, false);
            cmp.setLayout(gl);

            Label lb =
                    toolkit.createLabel(cmp,
                            Messages.ProcessAppearancePropertySection_ConnectionRoutingStyle_label);
            lb.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

            Composite btnGroup = toolkit.createComposite(cmp);
            btnGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            gl = new GridLayout(1, false);
            gl.marginHeight = 0;
            btnGroup.setLayout(gl);

            lb =
                    toolkit.createLabel(btnGroup,
                            Messages.ProcessAppearancePropertySection_SelectFlowConnection_message);
            lb.setLayoutData(new GridData());

            routingMultiBtn =
                    toolkit.createButton(btnGroup,
                            Messages.ProcessAppearancePropertySection_MultiEntryExit_radiobutton,
                            SWT.RADIO);
            routingMultiBtn
                    .setToolTipText(Messages.ProcessAppearancePropertySection_MultiEntryExitDescription_tooltip);
            manageControl(routingMultiBtn);

            routingSingleBtn =
                    toolkit.createButton(btnGroup,
                            Messages.ProcessAppearancePropertySection_SingleEntryExit_radiobutton,
                            SWT.RADIO);
            routingSingleBtn
                    .setToolTipText(Messages.ProcessAppearancePropertySection_SingleEntryExitDescription_tooltip);
            manageControl(routingSingleBtn);

            routingUncenteredBtn =
                    toolkit.createButton(btnGroup,
                            Messages.ProcessAppearancePropertySection_UncenteredEntryExit_radiobutton,
                            SWT.RADIO);
            routingUncenteredBtn
                    .setToolTipText(Messages.ProcessAppearancePropertySection_UncenteredEntryExitDescription_tooltip);
            manageControl(routingUncenteredBtn);

            return cmp;
        }
        return null;
    }

    @Override
    public void expandableSectionStateChanged(String sectionId) {
        Point sz = root.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        scrolledContainer.setMinSize(sz);
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {

        if (getBaseSelectObject(toTest) instanceof Process) {
            return true;
        }
        return false;
    }
}
