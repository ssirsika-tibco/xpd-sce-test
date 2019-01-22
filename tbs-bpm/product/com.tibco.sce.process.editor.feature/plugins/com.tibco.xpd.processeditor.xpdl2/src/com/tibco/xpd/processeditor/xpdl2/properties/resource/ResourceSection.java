/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
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
 * @author nwilson
 */
public class ResourceSection extends SashContributionSection {

    /**
     * @param selectionFilterClass
     * @param actionIdPrefix
     */
    public ResourceSection() {
        super(Xpdl2Package.eINSTANCE.getActivity(), Xpdl2ProcessEditorPlugin.ID
                + ".ResourceSection"); //$NON-NLS-1$
        setShowInWizard(false);
    }

    /**
     * Filter out resource section for activities in Pageflow processes and
     * decision flow processes. (For filtering out Resource section for pageflow
     * or decision flow processes see ProcessTaskGroupsSection)
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.properties.SashContributionSection#
     *      select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {
        boolean select = false;
        EObject eo = getBaseSelectObject(toTest);
        if (eo != null) {
            Process process = Xpdl2ModelUtil.getProcess(eo);
            if (!Xpdl2ModelUtil.isPageflow(process)
                    && !DecisionFlowUtil.isDecisionFlow(process)) {
                select = true;
            }
        }
        if (select) {
            select = super.select(toTest);
        }
        return select;
    }

}
