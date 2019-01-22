/*
 * ENVIRONMENT:    Java Generic
 *
 * COPYRIGHT:      (C) 2008 TIBCO Software Inc
 */
package com.tibco.n2.ut.resources.rules;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class UserActivityModeTypeRule extends ProcessValidationRule {
    private static final String UNSUPPORTED_MODETYPE =
            "n2.ut.unsupportedModeType"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        // Check each activity for the Mode Types that may be within them
        // This way we can ensure that the Mode Type used is always supported
        // before a conversion is attempted
        for (Activity activity : activities) {
            // Get the activity's associated parameters from the XPDL
            Object apsObj =
                    Xpdl2ModelUtil.getOtherElement(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AssociatedParameters());

            if (apsObj instanceof AssociatedParameters
                    && ((AssociatedParameters) apsObj).getAssociatedParameter()
                            .size() > 0) {
                /* Handle explicit associations. */
                for (Object apObj : ((AssociatedParameters) apsObj)
                        .getAssociatedParameter()) {
                    if (apObj instanceof AssociatedParameter) {
                        if (!validateModeType(((AssociatedParameter) apObj)
                                .getMode())) {
                            addIssue(UNSUPPORTED_MODETYPE, activity);
                        }
                    }
                }
            } else {
                /* Handle implicit associations */
                /*
                 * Sid XPD-2087: Only use all data implicitly if implicit
                 * association has not been disabled.
                 */
                if (!ProcessInterfaceUtil
                        .isImplicitAssociationDisabled(activity)) {
                    for (FormalParameter formalParameter : activity
                            .getProcess().getFormalParameters()) {
                        if (!validateModeType(formalParameter.getMode())) {
                            addIssue(UNSUPPORTED_MODETYPE, activity);
                        }
                    }
                }

            }
        }
    }

    /*
     * ===================================================== METHOD :
     * validateModeType =====================================================
     */
    /**
     * Check that the ModeType is one of the supported types
     * 
     * @param param
     *            FormalParameter containing the Mode
     * @return
     */
    private boolean validateModeType(ModeType modeType) {
        if (modeType == null) {
            return false;
        }

        int modeValue = modeType.getValue();
        // Each value for one enumeration maps to one in the other.
        if ((modeValue != ModeType.IN) && (modeValue != ModeType.OUT)
                && (modeValue != ModeType.INOUT)) {
            return false;
        }

        return true;
    }

}
