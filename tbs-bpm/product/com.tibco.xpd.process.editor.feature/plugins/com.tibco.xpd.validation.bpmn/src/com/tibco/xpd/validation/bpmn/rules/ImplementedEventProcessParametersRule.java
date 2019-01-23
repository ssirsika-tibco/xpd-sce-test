/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation rule to ensure that if an implemented event has
 * {@link AssociatedParameter} then the {@link FormalParameter} linked must not
 * be mandatory.
 * 
 * @author rsomayaj
 * 
 * 
 */
public class ImplementedEventProcessParametersRule extends
        ProcessValidationRule {

    private static final String ISSUE_ID =
            "bpmn.processinterface.implementedEventProcessParameterOptional"; //$NON-NLS-1$

    @Override
    public void validate(Process process) {
        Collection<Activity> activities =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        for (Activity act : activities) {
            if (ProcessInterfaceUtil.isEventImplemented(act)) {
                AssociatedParameters associatedParameters =
                        (AssociatedParameters) Xpdl2ModelUtil
                                .getOtherElement(act,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_AssociatedParameters());
                if (associatedParameters != null) {
                    List<AssociatedParameter> associatedParameterList =
                            associatedParameters.getAssociatedParameter();
                    for (AssociatedParameter associatedParameter : associatedParameterList) {

                        if (associatedParameter.isMandatory()
                                && isAssocProcessFormalParam(associatedParameter)) {
                            addIssue(ISSUE_ID, act);
                        }
                    }
                }

            }
        }

    }

    /**
     * @param associatedParameter
     * @return
     */
    private boolean isAssocProcessFormalParam(
            AssociatedParameter associatedParameter) {
        ProcessRelevantData pRD =
                ProcessInterfaceUtil
                        .getProcessRelevantDataFromAssociatedParam(associatedParameter);
        if (pRD != null && pRD.eContainer() instanceof Process) {
            return true;
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.util.EList,
     *      org.eclipse.emf.common.util.EList)
     * 
     * @param process
     * @param activities
     * @param transitions
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
    }
}
