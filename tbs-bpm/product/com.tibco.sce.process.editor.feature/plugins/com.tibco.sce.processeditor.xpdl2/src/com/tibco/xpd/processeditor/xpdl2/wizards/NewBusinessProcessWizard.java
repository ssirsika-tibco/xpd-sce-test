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
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author NWilson
 * 
 */
public class NewBusinessProcessWizard extends NewProcessWizard {

    /*
     * Predefined process id to distinguish between new decision flow process
     * for decision flow file and new business process for xpdl file. (Similar
     * such constant is defined in NewDecisionFlowWizard for uniqueness in dflow
     * file)
     */
    public static final String NEW_BUSINESSPROCESS_PROCESS_ID =
            "$$_NEW_BUSINESSPROCESS_PROCESS_ID_$$"; //$NON-NLS-1$

    @Override
    protected String getProcessWizardTitle() {
        return Messages.NewProcessWizard_Window_title;
    }

    @Override
    protected Image getProcessWizardIcon() {
        return Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                .get(Xpdl2ResourcesConsts.WIZARD_NEW_PROCESS);
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

        /*
         * PropertySection contribution filter needs to be able to distinguish
         * between new decision flow process for decision flow file and new
         * business process for normal xpdl.
         * 
         * The filter usually works on whether the decision flow is in a dflow
         * xpdl model but new processes are in the model yet!.
         * 
         * So we set a known predefined ID on the process here so that the
         * filter can detect that and filter in processes with this ID..
         * 
         * Then just before we create the command we will set it to something
         * unique again.
         */

        input.eSet(Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(),
                NEW_BUSINESSPROCESS_PROCESS_ID);
        return input;
    }

}
