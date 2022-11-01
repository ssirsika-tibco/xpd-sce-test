package com.tibco.bx.validation.rules.activity;

import java.util.Arrays;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdExtension.Retry;
import com.tibco.xpd.xpdExtension.ServiceProcessConfiguration;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Process;
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
                    boolean somethingSet = false;

                    // Intrinsic property validation: lower bounds
                    if (retry.eIsSet(XpdExtensionPackage.eINSTANCE
                            .getRetry_Max())) {
                        validateLowerBound(Messages.RetriesManagementRule_maxRetryProperty,
                                retry.getMax(),
                                0,
                                activity);

                        somethingSet = true;
                    }
                    if (retry.eIsSet(XpdExtensionPackage.eINSTANCE
                            .getRetry_InitialPeriod())) {
                        validateLowerBound(Messages.RetriesManagementRule_InitialRetryPeriod,
                                retry.getInitialPeriod(),
                                1,
                                activity);

                        somethingSet = true;
                    }
                    if (retry.eIsSet(XpdExtensionPackage.eINSTANCE
                            .getRetry_PeriodIncrement())) {
                        validateLowerBound(Messages.RetriesManagementRule_RetryPeriodIncrement,
                                retry.getPeriodIncrement(),
                                0,
                                activity);

                        somethingSet = true;
                    }

                    if (retry.eIsSet(XpdExtensionPackage.eINSTANCE.getRetry_MaxRetryAction())) {
                        somethingSet = true;
                    }

                    /*
                     * Sid ACE-6541 Service process with dual target / only pageflow target needs to have a
                     * warning/error respectively that retry properties are not applicable
                     */
                    if (somethingSet) {
                        warnOrErrorRetryForPageflowServiceProcess(activity);
                    }

                }
            }
        }

    }

    /**
     * Sid ACE-6541 Service process with dual target / only pageflow target needs to have a warning/error respectively
     * that retry properties are not applicable
     * 
     * @param activity
     */
    private void warnOrErrorRetryForPageflowServiceProcess(Activity activity) {
        Process process = activity.getProcess();
        
        if (process != null) {
            if (Xpdl2ModelUtil.isServiceProcess(process)) {
                
                ServiceProcessConfiguration config =
                        (ServiceProcessConfiguration) Xpdl2ModelUtil
                .getOtherElement(process,
                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ServiceProcessConfiguration());

                if (config != null) {
                    if (config.isDeployToPageflowRuntime()) {
                        /*
                         * Retry is not applicable to pageflow.
                         * 
                         * If dual deploy target then raise as warning
                         */
                        if (config.isDeployToProcessRuntime()) {
                            addIssue("ace.noRetryInPageflowWarning", activity); //$NON-NLS-1$
                        }
                        /* If only pageflow is selected raise issue as an error */
                        else {
                            addIssue("ace.noRetryInPageflowError", activity); //$NON-NLS-1$
                        }

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
