/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.wizards;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author NWilson
 * 
 */
public class NewPageflowProcessWizard extends NewProcessWizard {

    @Override
    protected String getProcessWizardTitle() {
        return Messages.NewPageflowProcessWizard_WizardTitle;
    }

    @Override
    protected Image getProcessWizardIcon() {
        return Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                .get(Xpdl2ResourcesConsts.WIZARD_NEW_PAGEFLOW_PROCESS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.CreationWizard.TemplateFactory#createTemplate
     * ()
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
        return input;
    }

    /**
     * @return The process.
     */
    public Process getProcess() {
        Process process = null;
        if (input instanceof Process) {
            process = (Process) input;
        }
        return process;
    }
}
