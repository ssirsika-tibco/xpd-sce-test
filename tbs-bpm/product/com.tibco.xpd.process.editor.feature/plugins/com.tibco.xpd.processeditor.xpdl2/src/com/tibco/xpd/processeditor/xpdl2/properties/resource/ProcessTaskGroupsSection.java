/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.resource;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.SashContributionSection;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * The Work resource property section for Process
 * 
 * 
 * @author aallway
 * @since v3.x
 */
public class ProcessTaskGroupsSection extends SashContributionSection {

    /**
     * Construct process resource section.
     * 
     * @param selectionFilterClass
     * @param sectionId
     */
    public ProcessTaskGroupsSection() {
        super(Xpdl2Package.eINSTANCE.getProcess(), Xpdl2ProcessEditorPlugin.ID
                + ".ProcessWorkResourceSection"); //$NON-NLS-1$

        setShowInWizard(false);
    }

    @Override
    public boolean select(Object toTest) {
        EObject eo = getBaseSelectObject(toTest);
        if (eo instanceof Process) {
            Process process = (Process) eo;

            if (!Xpdl2ModelUtil.isPageflow(process)
                    && !DecisionFlowUtil.isDecisionFlow(process)) {
                return super.select(toTest);
            }
        }
        return false;
    }

}
