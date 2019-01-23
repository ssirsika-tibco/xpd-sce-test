/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.Xpdl2ValidationRule;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * @author rsomayaj
 * 
 */
public class IntermediateMethodAssocParamRule extends Xpdl2ValidationRule {

    private static String ID = "bpmn.intermediateMethodOutAssocParam"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.Xpdl2ValidationRule#validate(java.lang.Object)
     * 
     * @param o
     */
    @Override
    protected void validate(Object o) {
        if (o instanceof ProcessInterface) {
            ProcessInterface processInterface = (ProcessInterface) o;
            List<IntermediateMethod> intermediateMethods =
                    processInterface.getIntermediateMethods();
            for (IntermediateMethod intermediateMethod : intermediateMethods) {
                List<AssociatedParameter> associatedParams =
                        intermediateMethod.getAssociatedParameters();
                if (TriggerType.MESSAGE_LITERAL == intermediateMethod
                        .getTrigger()) {
                    for (AssociatedParameter param : associatedParams) {
                        if (ModeType.OUT_LITERAL == ProcessInterfaceUtil
                                .getAssocParamModeType(param)) {
                            addIssue(ID, intermediateMethod);
                        }
                    }
                }
            }
        }
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return
     */
    public Class getTargetClass() {
        return ProcessInterface.class;
    }

}
