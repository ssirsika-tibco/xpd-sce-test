/*
* ENVIRONMENT:    Java Generic
*
* COPYRIGHT:      (C) 2008 TIBCO Software Inc
*/
package com.tibco.n2.ut.resources.rules;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.AllocationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class UserActivityResourceAllocationRule extends ProcessValidationRule
{
    private static final String NO_RESOURCE_ALLOCATION = "n2.ut.noResourceAllocation"; //$NON-NLS-1$
    private static final String UNSUPPORTED_RESOURCE_ALLOCATION = "n2.ut.unsupportedResourceAllocation"; //$NON-NLS-1$

	@Override
	protected void validateFlowContainer( Process           process,
										  EList<Activity>   activities,
										  EList<Transition> transitions )
	{
		// Check each activity for a Resource Pattern
		// This way we can ensure that the Mode Type used is always supported
		// before a conversion is attempted
        for (Activity activity : activities)
        {
            TaskType taskType = TaskObjectUtil.getTaskType(activity);

            if( taskType.equals(TaskType.USER_LITERAL) )
            {
                Object objARP = Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_ActivityResourcePatterns());
                
     	       	// Make sure a resource pattern is specified
     	       	if( ! (objARP instanceof ActivityResourcePatterns) )
     	       	{
            		addIssue(NO_RESOURCE_ALLOCATION, activity);	       		
     	       	}
     	       	else
     	       	{
     	       	    // For now we do not support Offer To One - so error in this case
     	            AllocationStrategy allStrategy = ((ActivityResourcePatterns) objARP).getAllocationStrategy();

     	            if (allStrategy != null) {
     	                if (AllocationType.OFFER_ONE == allStrategy.getOffer()) {
     	                   addIssue(UNSUPPORTED_RESOURCE_ALLOCATION, activity);
     	                }
     	            }

     	       	}
            }
        }
	}

}
