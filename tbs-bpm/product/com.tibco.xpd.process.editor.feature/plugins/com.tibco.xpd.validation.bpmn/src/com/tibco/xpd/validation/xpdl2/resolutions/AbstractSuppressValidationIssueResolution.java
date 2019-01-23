/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.ValidationControlUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdExtension.ValidationIssueOverrideType;
import com.tibco.xpd.xpdl2.OtherElementsContainer;

/**
 * Quick fix resolutions for setting validation override for associated issue.
 * 
 * @author aallway
 * @since 18 Jul 2012
 */
abstract class AbstractSuppressValidationIssueResolution extends
        AbstractWorkingCopyResolution {

    private final ValidationIssueOverrideType overrideType;

    /**
     * @param overrideType
     */
    public AbstractSuppressValidationIssueResolution(
            ValidationIssueOverrideType overrideType) {
        super();
        this.overrideType = overrideType;
    }

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
        if (target instanceof OtherElementsContainer) {
            OtherElementsContainer container = (OtherElementsContainer) target;

            try {
                Object id = marker.getAttribute("issueId"); //$NON-NLS-1$

                if (id instanceof String) {
                    String issueId = (String) id;

                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.AbstractSuppressValidationIssueResolution_SetIssueOverride_menu);

                    cmd.append(ValidationControlUtil
                            .getSetValidationIssueOverrideCommand(editingDomain,
                                    container,
                                    issueId,
                                    overrideType));

                    return cmd;
                }

            } catch (CoreException e) {

            }
        }
        return null;
    }

    /**
     * Quick fix resolution for setting validation override for associated issue
     * to {@link ValidationIssueOverrideType#SUPPRESS_UNTIL_NEXT_FLOW_CHANGE}
     * 
     * @author aallway
     * @since 18 Jul 2012
     */
    public static class SuppressIssueUntilFlowChangeResolution extends
            AbstractSuppressValidationIssueResolution {

        public SuppressIssueUntilFlowChangeResolution() {
            super(ValidationIssueOverrideType.SUPPRESS_UNTIL_NEXT_FLOW_CHANGE);
        }

    }

    /**
     * Quick fix resolution for setting validation override for associated issue
     * to {@link ValidationIssueOverrideType#SUPPRESS_UNTIL_MANUAL_REACTIVATION}
     * 
     * @author aallway
     * @since 18 Jul 2012
     */
    public static class SuppressIssueManualReactivateResolution extends
            AbstractSuppressValidationIssueResolution {

        public SuppressIssueManualReactivateResolution() {
            super(
                    ValidationIssueOverrideType.SUPPRESS_UNTIL_MANUAL_REACTIVATION);
        }

    }
}
