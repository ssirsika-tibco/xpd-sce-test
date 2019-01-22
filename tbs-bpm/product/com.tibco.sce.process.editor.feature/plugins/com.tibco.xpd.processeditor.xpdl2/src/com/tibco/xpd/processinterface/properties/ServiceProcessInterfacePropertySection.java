/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processinterface.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.general.ServiceProcessDeploymentTargetSection;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Service Process Interface Properties section. This will be similar to
 * {@link ProcessInterfacePropertySection} with additional section stacker to
 * configure deployment targets for the interface
 * 
 * @author bharge
 * @since 28 Jan 2015
 */
public class ServiceProcessInterfacePropertySection extends
        ProcessInterfacePropertySection {

    Composite root;

    private final static String SERVICEPROCESSINTERFACE_DEPLOYMENT_TARGET_SECTION =
            "serviceProcessInterfaceDeploymentTarget"; //$NON-NLS-1$

    ServiceProcessDeploymentTargetSection deploymentTargetSection;

    /**
     * @param eClass
     */
    public ServiceProcessInterfacePropertySection() {

        super();
        deploymentTargetSection =
                new ServiceProcessDeploymentTargetSection(
                        Messages.ServiceProcessDeploymentTargetSection_description_text);
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    public void doRefresh() {

        super.doRefresh();
        if (null != deploymentTargetSection) {

            deploymentTargetSection.refresh();
        }
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
     * @see com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {

        EObject baseObj = getBaseSelectObject(toTest);
        if (baseObj instanceof ProcessInterface) {

            ProcessInterface processInterface = (ProcessInterface) baseObj;
            boolean isServiceProcessInterface =
                    Xpdl2ModelUtil.isServiceProcessInterface(processInterface);

            if (isServiceProcessInterface) {

                return true;
            }
        }

        return false;
    }

    /**
     * @param expandableSectionStacker
     */
    @Override
    protected void addExpandableSections(
            ExpandableSectionStacker expandableSectionStacker) {

        expandableSectionStacker
                .addSection(SERVICEPROCESSINTERFACE_DEPLOYMENT_TARGET_SECTION,
                        Messages.ServiceProcessPropertySection_DeploymentTargetSection_title,
                        SWT.DEFAULT,
                        true,
                        false);

        super.addExpandableSections(expandableSectionStacker);
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

        if (SERVICEPROCESSINTERFACE_DEPLOYMENT_TARGET_SECTION.equals(sectionId)) {

            Composite cmp = toolkit.createComposite(container);
            GridLayout fl = new GridLayout(1, false);
            fl.marginHeight = 0;
            fl.marginWidth = 0;
            cmp.setLayout(fl);

            deploymentTargetSection.createControls(container,
                    getPropertySheetPage());

            deploymentTargetSection.getControl().setLayoutData(new GridData(
                    GridData.FILL_BOTH));
            return cmp;
        } else {

            return super.createExpandableSectionContent(sectionId,
                    container,
                    toolkit);
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator#expandableSectionStateChanged(java.lang.String)
     * 
     * @param sectionId
     */
    @Override
    public void expandableSectionStateChanged(String sectionId) {
    }

}
