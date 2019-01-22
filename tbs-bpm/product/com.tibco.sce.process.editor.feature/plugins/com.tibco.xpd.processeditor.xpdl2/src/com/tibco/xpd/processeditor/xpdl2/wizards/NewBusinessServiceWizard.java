/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Wizard that creates a new business service under Processes logical group in
 * its parent process package
 * 
 * @author bharge
 * @since 29 Jul 2014
 */
public class NewBusinessServiceWizard extends NewProcessWizard {

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.wizards.NewPageflowProcessWizard#getProcessWizardTitle()
     * 
     * @return
     */
    @Override
    protected String getProcessWizardTitle() {

        return Messages.NewBusinessServiceWizard_WizardTitle;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.wizards.NewPageflowProcessWizard#getProcessWizardIcon()
     * 
     * @return
     */
    @Override
    protected Image getProcessWizardIcon() {

        return Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                .get(Xpdl2ResourcesConsts.WIZARD_NEW_BUSINESS_SERVICE);
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
                XpdModelType.PAGE_FLOW);
        Xpdl2ModelUtil.setOtherAttribute(input, XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_PublishAsBusinessService(), Boolean.TRUE);
        return input;
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
                        Messages.NewBusinessServiceWizard_AddProcessCmd_label);
        Command addProcessCommand =
                super.createFinalAddProcessCommand(ed,
                        containerWorkingCopy,
                        process,
                        createDefaultProcessCmd);
        cmd.append(addProcessCommand);

        /* Set the default category on the new business service created */
        AddBusinessServiceCategoryCommand categoryCmd =
                new AddBusinessServiceCategoryCommand(ed, process);
        if (null != categoryCmd) {

            cmd.append(categoryCmd);
        }
        /*
         * Some of the business service fragment templates have extended
         * attributes information that is mainly used when business services are
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

    /**
     * Late execute command class that sets the default business service
     * category on the new business service created
     * 
     * 
     * @author bharge
     * @since 13 Aug 2014
     */
    private class AddBusinessServiceCategoryCommand extends
            AbstractLateExecuteCommand {

        Process process;

        /**
         * @param editingDomain
         * @param contextObject
         */
        public AddBusinessServiceCategoryCommand(EditingDomain editingDomain,
                Object contextObject) {

            super(editingDomain, contextObject);
            if (contextObject instanceof Process) {

                this.process = (Process) contextObject;
            }
        }

        /**
         * @see com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand#createCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      java.lang.Object)
         * 
         * @param editingDomain
         * @param contextObject
         * @return
         */
        @Override
        protected Command createCommand(EditingDomain editingDomain,
                Object contextObject) {

            if (null != process) {

                CompoundCommand cmd = new CompoundCommand();
                IProject project = WorkingCopyUtil.getProjectFor(process);
                String defaultCategory =
                        Xpdl2ModelUtil
                                .getBusinessServiceDefaultCategory(project
                                        .getName(), process.getPackage()
                                        .getName());
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                process,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_BusinessServiceCategory(),
                                defaultCategory));
                return cmd;
            }
            return null;
        }

    }
}
