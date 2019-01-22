/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.wizards;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ServiceProcessConfiguration;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Wizard that creates a new Service Process under Processes logical group in
 * its parent process package
 * 
 * @author bharge
 * @since 13 Jan 2015
 */
public class NewServiceProcessWizard extends NewProcessWizard {

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.wizards.NewProcessWizard#getProcessWizardTitle()
     * 
     * @return
     */
    @Override
    protected String getProcessWizardTitle() {

        return Messages.NewServiceProcessWizard_WizardTitle;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.wizards.NewProcessWizard#getProcessWizardIcon()
     * 
     * @return
     */
    @Override
    protected Image getProcessWizardIcon() {

        return Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                .get(Xpdl2ResourcesConsts.WIZARD_NEW_SERVICE_PROCESS);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.wizards.NewProcessWizard#createTemplate()
     * 
     * @return
     */
    @Override
    public EObject createTemplate() {

        Process input = Xpdl2Factory.eINSTANCE.createProcess();
        Xpdl2ModelUtil.setOtherAttribute(input,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                Xpdl2ResourcesConsts.DEFAULT_PROCESS_NAME);
        input.setName(NameUtil
                .getInternalName(Xpdl2ResourcesConsts.DEFAULT_PROCESS_NAME,
                        false));
        Xpdl2ModelUtil.setOtherAttribute(input,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_XpdModelType(),
                XpdModelType.SERVICE_PROCESS);
        return input;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.wizards.NewProcessWizard#appendAndExecuteAdditionalCommands(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.resources.WorkingCopy, com.tibco.xpd.xpdl2.Process)
     * 
     * @param ed
     * @param wc
     * @param process
     */
    @Override
    protected void appendAndExecuteAdditionalCommands(EditingDomain ed,
            WorkingCopy wc, Process process) {

        ProcessInterface implementedProcessInterface =
                ProcessInterfaceUtil.getImplementedProcessInterface(process);
        /*
         * If a service process is implementing a service process interface,
         * then the service process inherits the ServiceProcessConfiguration
         * from the interface. If it is not implementing a service process
         * interface then we need to add the ServiceProcessConfiguration
         */
        if (null == implementedProcessInterface) {

            ServiceProcessConfiguration serviceProcessConfiguration =
                    XpdExtensionFactory.eINSTANCE
                            .createServiceProcessConfiguration();
            serviceProcessConfiguration.setDeployToPageflowRuntime(false);
            serviceProcessConfiguration.setDeployToProcessRuntime(true);

            Command setServiceProcessConfigCmd =
                    Xpdl2ModelUtil
                            .getSetOtherElementCommand(ed,
                                    process,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ServiceProcessConfiguration(),
                                    serviceProcessConfiguration);

            ed.getCommandStack().execute(setServiceProcessConfigCmd);
        }
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.wizards.NewProcessWizard#createFinalAddProcessCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.resources.WorkingCopy, com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.command.Command)
     * 
     * @param ed
     * @param containerWorkingCopy
     * @param process
     * @param createDefaultProcessCmd
     * @return
     */
    @Override
    protected Command createFinalAddProcessCommand(EditingDomain ed,
            WorkingCopy containerWorkingCopy, Process process,
            Command createDefaultProcessCmd) {

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.NewServiceProcessWizard_AddProcessCmd_label);
        Command addProcessCommand =
                super.createFinalAddProcessCommand(ed,
                        containerWorkingCopy,
                        process,
                        createDefaultProcessCmd);

        cmd.append(addProcessCommand);
        /*
         * Some of the service process fragment templates have extended
         * attributes information that is mainly used when service processes are
         * auto generated from case classes. They are not harmful but getting
         * rid of them when we can!
         */
        RemoveExtendedAttributesCommand extAttrCmd =
                new RemoveExtendedAttributesCommand(ed, process);
        if (null != extAttrCmd) {

            cmd.append(extAttrCmd);
        }
        return cmd;
    }
}
