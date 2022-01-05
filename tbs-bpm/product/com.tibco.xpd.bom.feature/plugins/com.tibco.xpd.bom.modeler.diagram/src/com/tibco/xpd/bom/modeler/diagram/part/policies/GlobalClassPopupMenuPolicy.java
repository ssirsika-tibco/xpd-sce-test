/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.modeler.diagram.part.policies;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.IPopupMenuContributionPolicy;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassEditPart;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Checks if the right click menu on a Class should contain the convert to
 * Global entry
 * 
 */
public class GlobalClassPopupMenuPolicy implements IPopupMenuContributionPolicy {

    /**
     * @see org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.IPopupMenuContributionPolicy#appliesTo(org.eclipse.jface.viewers.ISelection,
     *      org.eclipse.core.runtime.IConfigurationElement)
     * 
     * @param selection
     * @param configuration
     * @return
     */
    @Override
    public boolean appliesTo(ISelection selection,
            IConfigurationElement configuration) {
        // Check everything in the selection to ensure that ALL the selection is
        // valid for this operation
        if (selection instanceof StructuredSelection) {
            StructuredSelection elements = ((StructuredSelection) selection);
            for (Object object : elements.toList()) {
                if ((object != null) && (object instanceof ClassEditPart)) {
                    ClassEditPart editPart = (ClassEditPart) object;
                    Class toMutateClass = editPart.getElement();

                    // IF the BOM is read only then don't allow changes
                    WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(toMutateClass);
                    if (wc != null && wc.isReadOnly()) {
                        return false;
                    }

                    // If the global data profile is not applied, don't show the
                    // convert
                    if (!BOMGlobalDataUtils.hasGlobalDataProfile(toMutateClass
                            .getModel())) {
                        return false;
                    }

                    // Check if the class being selected is either undefined or
                    // is already a global class, in which case we do not want
                    // to show the menu item
                    if ((toMutateClass == null)
                            || GlobalDataProfileManager.getInstance()
                                    .isGlobal(toMutateClass)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}