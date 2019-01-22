/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ConstantPeriod;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Checks if the correlation time out days, hours, minutes or seconds exceeds
 * the maximum Integer value
 * 
 * @author bharge
 * @since 13 Jun 2012
 */
public class CorrelationTimeoutRule extends ProcessValidationRule {

    private static final String ISSUE_CANNOT_EXCEED_MAX_VALUE =
            "bx.correlationTimeoutMaxValueExceeded"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.util.EList,
     *      org.eclipse.emf.common.util.EList)
     * 
     * @param process
     * @param activities
     * @param transitions
     * @deprecated
     */
    @Deprecated
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        for (Activity activity : activities) {
            EStructuralFeature feature =
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_CorrelationTimeout();
            ConstantPeriod constantPeriod =
                    (ConstantPeriod) Xpdl2ModelUtil.getOtherElement(activity,
                            feature);

            if (null != constantPeriod) {
                if (constantPeriod.eIsSet(XpdExtensionPackage.eINSTANCE
                        .getConstantPeriod_Days())) {
                    validatMaxAllowedValue(Messages.CorrelationTimeoutRule_timeoutDays,
                            constantPeriod.getDays(),
                            activity);
                }

                if (constantPeriod.eIsSet(XpdExtensionPackage.eINSTANCE
                        .getConstantPeriod_Hours())) {
                    validatMaxAllowedValue(Messages.CorrelationTimeoutRule_timeoutHours,
                            constantPeriod.getHours(),
                            activity);
                }
                if (constantPeriod.eIsSet(XpdExtensionPackage.eINSTANCE
                        .getConstantPeriod_Minutes())) {
                    validatMaxAllowedValue(Messages.CorrelationTimeoutRule_timeoutMinutes,
                            constantPeriod.getMinutes(),
                            activity);
                }
                if (constantPeriod.eIsSet(XpdExtensionPackage.eINSTANCE
                        .getConstantPeriod_Seconds())) {
                    validatMaxAllowedValue(Messages.CorrelationTimeoutRule_timeoutSeconds,
                            constantPeriod.getSeconds(),
                            activity);
                }
            }
        }
    }

    /**
     * @param correlationTimeoutRule_timeoutSeconds
     * @param seconds
     * @param activity
     */
    private void validatMaxAllowedValue(String correlationTimeoutProperty,
            BigInteger actualValue, Activity activity) {

        List<String> messages = new ArrayList<String>();
        messages.add(correlationTimeoutProperty);
        messages.add(actualValue.toString());
        messages.add(String.valueOf(Integer.MAX_VALUE));

        Map<String, String> addlInfo = new HashMap<String, String>();
        addlInfo.put(correlationTimeoutProperty, actualValue.toString());

        Integer value = new Integer(String.valueOf(actualValue.intValue()));
        if (value <= 0 || value > Integer.MAX_VALUE) {
            addIssue(ISSUE_CANNOT_EXCEED_MAX_VALUE,
                    activity,
                    messages,
                    addlInfo);
        }

    }
}
