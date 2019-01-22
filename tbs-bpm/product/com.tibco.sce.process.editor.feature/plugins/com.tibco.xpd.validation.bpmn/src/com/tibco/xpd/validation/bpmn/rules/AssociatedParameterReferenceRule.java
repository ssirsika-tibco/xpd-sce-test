/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;

/**
 * This rule checks that associated parameters reference valid parameters.
 * 
 * @author nwilson
 */
public class AssociatedParameterReferenceRule extends ProcessValidationRule {

    private static final String INVALID_REFERENCE =
            "bpmn.invalidAssociatedParameterReference"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Activity activity : activities) {
            for (AssociatedParameter assoc : getAssociatedParameters(activity)) {
                ProcessRelevantData data =
                        ProcessInterfaceUtil
                                .getProcessRelevantDataFromAssociatedParam(assoc);
                if (data == null) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(assoc.getFormalParam());
                    addIssue(INVALID_REFERENCE, assoc, messages);
                }

            }
        }
    }

    public List<AssociatedParameter> getAssociatedParameters(Activity act) {
        List<AssociatedParameter> associatedParameters =
                new ArrayList<AssociatedParameter>();

        EObject obj =
                act.getOtherElement(XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_AssociatedParameters().getName());
        if (obj != null && obj instanceof AssociatedParameters) {
            AssociatedParameters associatedParams = (AssociatedParameters) obj;
            associatedParameters.addAll(associatedParams
                    .getAssociatedParameter());
        }

        return associatedParameters;
    }

}
