/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.ParameterToDataFieldCommand;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * ParameterToDataFieldResolution provides a quick fix to the warning thrown by
 * FormalParameterInBusinessServicePageflowRule class. It basically converts all
 * the formal parameter(s) present in a particular Business Service Pageflow to
 * Data Field(s).
 * 
 * @author sajain
 * @since Nov 28, 2012
 */
public class ParameterToDataFieldResolution extends
        AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof Process) {
            Process process = (Process) target;

            if (Xpdl2ModelUtil.isPageflowBusinessService(process)) {
                EList<FormalParameter> formalparameters =
                        process.getFormalParameters();

                if (!formalparameters.isEmpty()) {
                    CompoundCommand compoundCommand = new CompoundCommand();
                    for (FormalParameter parameter : formalparameters) {
                        compoundCommand.append(new ParameterToDataFieldCommand(
                                editingDomain, parameter));
                    }
                    return compoundCommand;
                }
            }
        }

        return null;
    }

}
