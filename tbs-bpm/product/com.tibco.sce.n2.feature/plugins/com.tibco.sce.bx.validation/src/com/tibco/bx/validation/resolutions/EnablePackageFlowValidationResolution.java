/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.bx.validation.rules.process.ControlFlowValidationRule;
import com.tibco.xpd.processeditor.xpdl2.util.ValidationControlUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.ValidationIssueOverrideType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Resolution to re-enable process flow analyzer validation for all package
 * processes once it has been suppressed at package level.
 * 
 * @author aallway
 * @since 25 May 2013
 */
public class EnablePackageFlowValidationResolution extends
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
            Package pkg = ((Process) target).getPackage();

            if (pkg != null) {
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.EnableProcessFlowValidationResolution_EnableFlowValidation_menu);

                cmd.append(ValidationControlUtil
                        .getRemoveValidationIssueOverrideCommand(editingDomain,
                                pkg,
                                ControlFlowValidationRule.BX_FLOW_ANALYSER_SUPPRESSOR_ID));

                for (Process process : pkg.getProcesses()) {
                    ValidationIssueOverrideType flowAnalyzerOverride =
                            ValidationControlUtil
                                    .getValidationIssueOverrideType(process,
                                            ControlFlowValidationRule.BX_FLOW_ANALYSER_SUPPRESSOR_ID);

                    if (flowAnalyzerOverride != null) {
                        cmd.append(ValidationControlUtil
                                .getRemoveValidationIssueOverrideCommand(editingDomain,
                                        process,
                                        ControlFlowValidationRule.BX_FLOW_ANALYSER_SUPPRESSOR_ID));
                    }
                }

                return cmd;
            }

        }

        return null;
    }

}
