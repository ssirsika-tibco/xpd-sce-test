/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.process;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessResourceItemType;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ProcessInterfaceGroup;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.general.BizServPrivilegeConfigSection;
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
 * Business service process property section
 * 
 * @author bharge
 * @since 29 Jul 2014
 */
public class BusinessServicePropertySection extends ProcessPropertySection {

    private static final String BUSINESS_SERVICE_SECTION = "businessService"; //$NON-NLS-1$

    private PublishBusinessServiceSection publishBizServiceSection;

    private BizServPrivilegeConfigSection privilegesConfigurationSection;

    public BusinessServicePropertySection() {

        publishBizServiceSection = new PublishBusinessServiceSection();
        privilegesConfigurationSection = new BizServPrivilegeConfigSection();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.process.PageflowPropertySection#setInput(org.eclipse.ui.IWorkbenchPart,
     *      org.eclipse.jface.viewers.ISelection)
     * 
     * @param part
     * @param selection
     */
    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {

        super.setInput(part, selection);
        publishBizServiceSection.setInput(part, selection);
        privilegesConfigurationSection.setInput(part, selection);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.process.PageflowPropertySection#doRefresh()
     * 
     */
    @Override
    public void doRefresh() {

        super.doRefresh();
        publishBizServiceSection.refresh();
        privilegesConfigurationSection.refresh();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.process.PageflowPropertySection#createWizardProcessTemplateProvider()
     * 
     * @return
     */
    @Override
    protected ProcessTemplateProvider createWizardProcessTemplateProvider() {

        return new ProcessTemplateProvider((Package) getInputContainer(),
                TemplateProviderIdentifier.BUSINESSSERVICE) {
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
                 * XPD-7298: New Biz Service creation wizard should only list
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
                 * XPD-7298: New Biz Service creation wizard should only list
                 * process interface templates.
                 */
                return ProcessResourceItemType.PROCESSINTERFACE
                        .equals(processResourceType);
            }
        };
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.process.ProcessPropertySection#addExpandableSections(com.tibco.xpd.ui.properties.ExpandableSectionStacker)
     * 
     * @param headerController
     */
    @Override
    protected void addExpandableSections(
            ExpandableSectionStacker headerController) {

        headerController.addSection(BUSINESS_SERVICE_SECTION,
                Messages.PageflowPropertySection_BusinessServiceSection_title,
                SWT.DEFAULT,
                true,
                true);

        super.addExpandableSections(headerController);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.process.PageflowPropertySection#createExpandableSectionContent(java.lang.String,
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

        if (BUSINESS_SERVICE_SECTION.equals(sectionId)) {

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

        GridLayoutFactory.swtDefaults().applyTo(container);

        Composite sectionComposite = toolkit.createComposite(container);
        GridLayout sectionLayout = new GridLayout(3, false);
        sectionLayout.marginHeight = 0;
        sectionLayout.marginWidth = 0;
        sectionComposite.setLayout(sectionLayout);

        /* create publish business service composite and controls */

        Composite lhsComposite = toolkit.createComposite(sectionComposite);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(lhsComposite);
        publishBizServiceSection.createControls(lhsComposite,
                getPropertySheetPage());

        /* create separator */
        GridDataFactory
                .fillDefaults()
                .applyTo(toolkit.createSeparator(sectionComposite, SWT.VERTICAL));

        /* create privilege configuration section composite and controls */
        Composite privilegeSectionComposite =
                toolkit.createComposite(sectionComposite);
        GridDataFactory.fillDefaults().grab(true, true)
                .applyTo(privilegeSectionComposite);
        privilegesConfigurationSection
                .createControls(privilegeSectionComposite,
                        getPropertySheetPage());

        return sectionComposite;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.process.PageflowPropertySection#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {

        boolean ok = false;
        EObject eo = getBaseSelectObject(toTest);
        if (eo instanceof Process) {

            Process process = (Process) eo;
            ok =
                    Xpdl2ModelUtil.isPageflowBusinessService(process)
                            && !Xpdl2ModelUtil.isCaseService(process);
        }
        return ok;
    }
}
