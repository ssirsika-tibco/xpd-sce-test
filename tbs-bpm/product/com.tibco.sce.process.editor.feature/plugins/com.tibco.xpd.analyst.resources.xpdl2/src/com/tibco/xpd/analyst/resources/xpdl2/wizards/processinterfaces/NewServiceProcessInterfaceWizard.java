/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.wizards.processinterfaces;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ServiceProcessConfiguration;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdInterfaceType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Wizard that creates a new Service Process Interface under Process Interfaces
 * logical group
 * 
 * @author bharge
 * @since 27 Jan 2015
 */
public class NewServiceProcessInterfaceWizard extends NewProcessInterfaceWizard {

    private static final String WIZARD_NEW_SERVICE_PROCESS_INTERFACE =
            "icons/wizards/ServiceProcessInterfaceWizard.png"; //$NON-NLS-1$

    public NewServiceProcessInterfaceWizard() {

        setWindowTitle(Messages.NewServiceProcessInterfaceWizard_Dialog_title);
        setDefaultPageImageDescriptor(Xpdl2ResourcesPlugin
                .getImageDescriptor(WIZARD_NEW_SERVICE_PROCESS_INTERFACE));
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.wizards.processinterfaces.NewProcessInterfaceWizard#createTemplate()
     * 
     * @return
     */
    @Override
    public EObject createTemplate() {

        ProcessInterface processInterface =
                XpdExtensionFactory.eINSTANCE.createProcessInterface();
        processInterface
                .setName(NameUtil
                        .getInternalName(Messages.NewServiceProcessInterfaceWizard_DefaultName_value,
                                false));
        Xpdl2ModelUtil.setOtherAttribute(processInterface,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                Messages.NewServiceProcessInterfaceWizard_DefaultName_value);

        /* Set the interface type to ServiceProcessInterface enum type */
        processInterface
                .setXpdInterfaceType(XpdInterfaceType.SERVICE_PROCESS_INTERFACE);
        /*
         * Create and set the service process configuration on to the process
         * interface to make it Service Process Interface
         */
        ServiceProcessConfiguration serviceProcessConfiguration =
                XpdExtensionFactory.eINSTANCE
                        .createServiceProcessConfiguration();
        serviceProcessConfiguration.setDeployToPageflowRuntime(false);
        serviceProcessConfiguration.setDeployToProcessRuntime(true);

        processInterface
                .setServiceProcessConfiguration(serviceProcessConfiguration);
        return processInterface;
    }

}
