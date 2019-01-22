/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.Transition;

/**
 * Rule to check that user activities have at least one associated data field.
 * 
 * @author nwilson
 */
public class DataFieldToActivityRule extends ProcessValidationRule {

    /** Parameter issue ID. */
    public static final String ID = "sim.userActivityWithoutParameter"; //$NON-NLS-1$   

    @Override
    protected void validateFlowContainer(Process process, EList<Activity> activities, EList<Transition> transitions) {        
        for (Object next : activities) {
            Activity activity = (Activity) next;
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task) {
                Task task = (Task) implementation;
                TaskUser user = task.getTaskUser();
                if (user != null) {
                    Object[] formals = getAssociatedParameters(activity);                    
                    if (formals.length == 0){
                        addIssue(ID, activity);
                    }
                }
            }
        }        
    }

    /**
     * Checks to see if any associated parameters have been assigned to activity. 
     * (i.e. actually in the list not just the generic all process data)
     * @param input
     * @return
     */
    public Object[] getAssociatedParameters(Object input) {
        List<String> elements = new ArrayList<String>();
        if (input != null && input instanceof Activity) {            
            List associatedParameters =
                    new ArrayList<AssociatedParameter>();
            Activity act = (Activity) input;
            AssociatedParameters parameters =
                    (AssociatedParameters) act
                            .getOtherElement(XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AssociatedParameters()
                                    .getName());
            if (ProcessInterfaceUtil.isEventImplemented(act)) {
                List implementedMethodAssociatedParams =
                        ProcessInterfaceUtil
                                .getImplementedMethodAssociatedParameters(act);
                if (!implementedMethodAssociatedParams.isEmpty()) {                    
                    associatedParameters
                            .addAll(implementedMethodAssociatedParams);
                }
            } else if (parameters == null
                    || (parameters != null && parameters
                            .getAssociatedParameter().isEmpty())) {               
               return new Object[]{}; 
            }

            if (parameters != null) {
                associatedParameters.addAll(parameters
                        .getAssociatedParameter());
            }

            return associatedParameters.toArray();            
        }

        return elements.toArray();
    }
}
