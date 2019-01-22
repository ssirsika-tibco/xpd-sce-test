/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.process;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessResourceItemType;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ProcessInterfaceGroup;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.general.ServiceProcessDeploymentTargetSection;
import com.tibco.xpd.processeditor.xpdl2.wizards.ProcessTemplateProvider;
import com.tibco.xpd.processeditor.xpdl2.wizards.ProcessTemplateProvider.TemplateProviderIdentifier;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Service Process property section
 * 
 * @author bharge
 * @since 21 Jan 2015
 */
public class ServiceProcessPropertySection extends ProcessPropertySection {

    ServiceProcessDeploymentTargetSection deploymentTargetSection;

    private static final String SERVICE_PROCESS_SECTION = "serviceProcess"; //$NON-NLS-1$

    /**
     * Constructor that creates target deployment configuration controls for a
     * Service Process
     * */
    public ServiceProcessPropertySection() {

        deploymentTargetSection =
                new ServiceProcessDeploymentTargetSection(
                        Messages.ServiceProcessDeploymentTargetSection_description_text);
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
        deploymentTargetSection.setInput(part, selection);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.process.ProcessPropertySection#doRefresh()
     * 
     */
    @Override
    public void doRefresh() {

        super.doRefresh();
        deploymentTargetSection.refresh();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.process.ProcessPropertySection#select(java.lang.Object)
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
            ok = Xpdl2ModelUtil.isServiceProcess(process);
        }
        return ok;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.process.ProcessPropertySection#createWizardProcessTemplateProvider()
     * 
     * @return
     */
    @Override
    protected ProcessTemplateProvider createWizardProcessTemplateProvider() {

        return new ProcessTemplateProvider((Package) getInputContainer(),
                TemplateProviderIdentifier.SERVICEPROCESS) {
            /**
             * @see com.tibco.xpd.processeditor.xpdl2.wizards.ProcessTemplateProvider#getApplicableInterfaces(com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ProcessInterfaceGroup)
             * 
             * @param interfaceGroup
             * @return
             */
            @Override
            protected List getApplicableInterfaces(
                    ProcessInterfaceGroup interfaceGroup) {

                List<Object> serviceProcessInterfaces = new ArrayList<Object>();

                List children = interfaceGroup.getChildren();
                /*
                 * XPD-7298: New Service processes creation wizard should only
                 * list service process interface templates.
                 */
                for (Object object : children) {

                    if (object instanceof ProcessInterface) {

                        if (Xpdl2ModelUtil
                                .isServiceProcessInterface((ProcessInterface) object)) {
                            serviceProcessInterfaces.add(object);
                        }
                    }
                }
                return serviceProcessInterfaces;
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
                 * XPD-7298: New Service processes creation wizard should only
                 * list service process interface templates.
                 */
                return ProcessResourceItemType.SERVICEPROCESSINTERFACE
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

        headerController
                .addSection(SERVICE_PROCESS_SECTION,
                        Messages.ServiceProcessPropertySection_DeploymentTargetSection_title,
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

        if (SERVICE_PROCESS_SECTION.equals(sectionId)) {

            return createServiceProcessControls(toolkit, container);
        } else {

            return super.createExpandableSectionContent(sectionId,
                    container,
                    toolkit);
        }
    }

    /**
     * @param toolkit
     * @param container
     * @return
     */
    private Control createServiceProcessControls(XpdFormToolkit toolkit,
            Composite container) {

        Composite cmp = toolkit.createComposite(container);
        GridLayout fl = new GridLayout(1, false);
        fl.marginHeight = 0;
        fl.marginWidth = 0;
        cmp.setLayout(fl);

        deploymentTargetSection.createControls(cmp, getPropertySheetPage());
        deploymentTargetSection.getControl().setLayoutData(new GridData(
                GridData.FILL_BOTH));

        return cmp;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.process.ProcessPropertySection#getProcInterfaceTemplatePreviewTitle()
     * 
     * @return
     */
    @Override
    protected String getProcInterfaceTemplatePreviewTitle() {
        /*
         * title for service process interface template.
         */
        return Messages.ServiceProcessPropertySection_ServiceProcessInterfaceTemplate_title;
    }
}
