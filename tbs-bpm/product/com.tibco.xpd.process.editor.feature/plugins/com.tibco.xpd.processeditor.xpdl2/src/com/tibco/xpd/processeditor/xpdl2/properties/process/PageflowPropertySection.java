/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.process;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessResourceItemType;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ProcessInterfaceGroup;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.general.PublishBusinessServiceSection;
import com.tibco.xpd.processeditor.xpdl2.wizards.ProcessTemplateProvider;
import com.tibco.xpd.processeditor.xpdl2.wizards.ProcessTemplateProvider.TemplateProviderIdentifier;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Pageflow specialisation of process property section.
 * 
 * @author aallway
 * @since 10 May 2011
 */
public class PageflowPropertySection extends ProcessPropertySection {

    private static final String PAGE_FLOW_BIZ_SERVICE_SECTION =
            "pageFlowBusinessService"; //$NON-NLS-1$

    private PublishBusinessServiceSection publishBizServiceSection;

    public PageflowPropertySection() {

        publishBizServiceSection = new PublishBusinessServiceSection();
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
        publishBizServiceSection.setInput(part, selection);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.process.ProcessPropertySection#addExpandableSections(com.tibco.xpd.ui.properties.ExpandableSectionStacker)
     * 
     * @param headerController
     */
    @Override
    protected void addExpandableSections(
            ExpandableSectionStacker headerController) {

        headerController.addSection(PAGE_FLOW_BIZ_SERVICE_SECTION,
                Messages.PageflowPropertySection_BusinessServiceSection_title,
                SWT.DEFAULT,
                true,
                false);

        super.addExpandableSections(headerController);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.process.ProcessPropertySection#createExpandableSectionContent(java.lang.String,
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

        if (PAGE_FLOW_BIZ_SERVICE_SECTION.equals(sectionId)) {

            return createBusinessServiceControls(toolkit, container);
        } else {

            return super.createExpandableSectionContent(sectionId,
                    container,
                    toolkit);
        }
    }

    /**
     * @param toolkit
     * @param container
     * @return business service configuration controls.
     */
    private Control createBusinessServiceControls(XpdFormToolkit toolkit,
            Composite container) {

        Composite cmp = toolkit.createComposite(container);
        FillLayout fl = new FillLayout();
        fl.marginHeight = 0;
        fl.marginWidth = 0;
        cmp.setLayout(fl);

        publishBizServiceSection.createControls(cmp, getPropertySheetPage());

        return cmp;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.process.ProcessPropertySection#doRefresh()
     * 
     */
    @Override
    public void doRefresh() {
        /*
         * Sid XPD-8227: Missing refresh caused inproper initial control config
         * when switching selection directly between BusinessService process
         * onto pageflow process.
         */
        publishBizServiceSection.refresh();

        super.doRefresh();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.process.ProcessPropertySection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public Command doGetCommand(Object obj) {

        return super.doGetCommand(obj);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.process.ProcessPropertySection#createWizardProcessTemplateProvider()
     * 
     * @return
     */
    @Override
    protected ProcessTemplateProvider createWizardProcessTemplateProvider() {

        return new ProcessTemplateProvider((Package) getInputContainer(),
                TemplateProviderIdentifier.PAGEFLOW) {
            /**
             * @see com.tibco.xpd.processeditor.xpdl2.wizards.ProcessTemplateProvider#getApplicableInterfaces(com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ProcessInterfaceGroup)
             * 
             * @param interfaceGroup
             * @return
             */
            @Override
            protected List getApplicableInterfaces(
                    ProcessInterfaceGroup interfaceGroup) {

                List<Object> processInterfaces = new ArrayList<Object>();

                List children = interfaceGroup.getChildren();
                /*
                 * XPD-7298: New Pageflow creation wizard should only list
                 * process interface templates.
                 */
                for (Object object : children) {

                    if (object instanceof ProcessInterface) {

                        if (Xpdl2ModelUtil
                                .isProcessInterface((ProcessInterface) object)) {
                            processInterfaces.add(object);
                        }
                    }
                }
                return processInterfaces;
            }

            /**
             * @see com.tibco.xpd.processeditor.xpdl2.wizards.ProcessTemplateProvider#isApplicableInterfaceType(java.lang.String)
             * 
             * @param type
             * @return
             */
            @Override
            protected boolean isApplicableInterfaceType(
                    ProcessResourceItemType processResourceType) {
                /*
                 * XPD-7298: New Pageflow creation wizard should only list
                 * process interface templates.
                 */
                return ProcessResourceItemType.PROCESSINTERFACE
                        .equals(processResourceType);
            }
        };
    }

    @Override
    public boolean select(Object toTest) {

        boolean ok = false;
        EObject eo = getBaseSelectObject(toTest);
        if (eo instanceof Process) {

            Process process = (Process) eo;
            ok =
                    Xpdl2ModelUtil.isPageflow(process)
                            && !Xpdl2ModelUtil
                                    .isPageflowBusinessService(process);
        }
        return ok;
    }

}
