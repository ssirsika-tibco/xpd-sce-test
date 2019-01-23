/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.wizards;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.CaseService;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Wizard that creates a new case service under Processes logical group in its
 * parent process package
 * 
 * @author bharge
 * @since 29 Jul 2014
 */
public class NewCaseServiceWizard extends NewProcessWizard {

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.wizards.NewPageflowProcessWizard#getProcessWizardTitle()
     * 
     * @return
     */
    @Override
    protected String getProcessWizardTitle() {

        return Messages.NewCaseServiceWizard_WizardTitle;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.wizards.NewPageflowProcessWizard#getProcessWizardIcon()
     * 
     * @return
     */
    @Override
    protected Image getProcessWizardIcon() {

        return Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                .get(Xpdl2ResourcesConsts.WIZARD_NEW_CASE_SERVICE);
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
        Xpdl2ModelUtil.setOtherAttribute(input,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_IsCaseService(),
                Boolean.TRUE);
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
                        Messages.NewCaseServiceWizard_AddProcessCmd_label);
        Command addProcessCommand =
                super.createFinalAddProcessCommand(ed,
                        containerWorkingCopy,
                        process,
                        createDefaultProcessCmd);
        cmd.append(addProcessCommand);

        Package xpdlPackage = (Package) getInputContainer();

        /*
         * Call the post process command to resolve the data fields, resolve
         * formal parameters to reference the correct bom and case class types,
         * update the script and global data task - if case class is selected
         * from the picker in the new wizard
         */
        CaseService caseService =
                (CaseService) Xpdl2ModelUtil.getOtherElement(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_CaseService());
        if (null != caseService) {

            ExternalReference caseServiceExtRef =
                    caseService.getCaseClassType();
            Class caseClass =
                    (Class) ProcessUIUtil
                            .getReferencedClassifier(caseServiceExtRef,
                                    WorkingCopyUtil.getProjectFor(xpdlPackage));
            if (null != caseClass) {

                CaseServicePostProcessCommand postProcessCmd =
                        new CaseServicePostProcessCommand(ed, process,
                                xpdlPackage, caseClass);
                if (null != postProcessCmd) {

                    cmd.append(postProcessCmd);
                }
            }
        }

        /*
         * Some of the case service fragment templates have extended attributes
         * information that are mainly used when case services are auto
         * generated from case classes. They are not harmful but getting rid of
         * them when we can!
         */
        RemoveExtendedAttributesCommand extAttrCmd =
                new RemoveExtendedAttributesCommand(ed, process);
        if (null != extAttrCmd) {

            cmd.append(extAttrCmd);
        }

        return cmd;
    }

}
