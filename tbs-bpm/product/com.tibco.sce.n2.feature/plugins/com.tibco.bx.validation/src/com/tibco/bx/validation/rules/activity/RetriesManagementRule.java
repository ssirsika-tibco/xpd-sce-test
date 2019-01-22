package com.tibco.bx.validation.rules.activity;

import java.util.Arrays;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdExtension.Retry;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class RetriesManagementRule extends FlowContainerValidationRule {

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate(com.tibco.xpd.xpdl2.FlowContainer)
     * 
     * @param container
     */
    @Override
    public void validate(FlowContainer container) {

        for (EObject obj : container.eContents()) {
            if (obj instanceof Activity) {
                Activity activity = (Activity) obj;
                EStructuralFeature feature =
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Retry();
                Retry retry =
                        (Retry) Xpdl2ModelUtil.getOtherElement(activity,
                                feature);

                if (retry != null) {

                    // Intrinsic property validation: lower bounds
                    if (retry.eIsSet(XpdExtensionPackage.eINSTANCE
                            .getRetry_Max())) {
                        validateLowerBound(Messages.RetriesManagementRule_maxRetryProperty,
                                retry.getMax(),
                                0,
                                activity);
                    }
                    if (retry.eIsSet(XpdExtensionPackage.eINSTANCE
                            .getRetry_InitialPeriod())) {
                        validateLowerBound(Messages.RetriesManagementRule_InitialRetryPeriod,
                                retry.getInitialPeriod(),
                                1,
                                activity);
                    }
                    if (retry.eIsSet(XpdExtensionPackage.eINSTANCE
                            .getRetry_PeriodIncrement())) {
                        validateLowerBound(Messages.RetriesManagementRule_RetryPeriodIncrement,
                                retry.getPeriodIncrement(),
                                0,
                                activity);
                    }

                }
            }
        }

    }

    private void validateLowerBound(String propertyName, int actual,
            int lowerBound, EObject element) {

        if (actual < lowerBound) {
            String[] args =
                    { propertyName, String.valueOf(lowerBound),
                            String.valueOf(actual) };
            this.addIssue("bx.advancedPropertiesRetryLowerbound", //$NON-NLS-1$
                    element,
                    Arrays.asList(args));
        }

    }

}
