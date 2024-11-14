/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.implementer.resources.xpdl2.errorEvents;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter.RsoType;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.DiagramDropObjectUtils;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdExtension.RestServiceOperation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Task;

/**
 * Sid ACE-8703 Super class for the shared parts of REST RSD and SWAGGER based service task catchable error
 * contributions.
 *
 * @author aallway
 * @since 23 Oct 2024
 */
public abstract class AbstractRestServiceErrorsContributor implements IBpmnCatchableErrorsContributor
{

	/**
	 * Get the REST service task reference type (RsoType.RSD | SWAGGER) for the sub-class's use case.
	 * 
	 * @return the REST service task reference type (RsoType.RSD | SWAGGER)
	 */
	protected abstract RsoType getApplicableRsoType();

	/**
	 * Checks to see if the given Activity has a REST Service Descriptor associated with it.
	 * 
	 * @see com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor#isApplicableErrorThrower(com.tibco.xpd.xpdl2.Activity)
	 * 
	 * @param errorTask
	 *            The Activity throwing the error.
	 * @return true if it has a REST Service Descriptor associated with it.
	 */
	@Override
	public boolean isApplicableErrorThrower(Activity errorTask)
	{
	    final RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
	    OtherElementsContainer rsoParent = rsta.getRSOContainer(errorTask);
	    RestServiceOperation rso = rsta.getRSO(rsoParent);
	
		/*
		 * Sid ACE-8703 Don't contribute to catch errors for this task unless it is specifically an RSD service
		 * reference.
		 */
		RsoType applicableRsoType = getApplicableRsoType();

		return rso != null && applicableRsoType.equals(rsta.getRsoType(errorTask));
	}


	/**
	 * @see com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor#getErrorImage(java.lang.Object,
	 *      java.lang.String)
	 * 
	 * @param errorThrower
	 *            The Activity throwing the error.
	 * @param errorId
	 *            The error code.
	 * @return The associated image.
	 */
	@Override
	public Image getErrorImage(Object errorThrower, String errorId)
	{
	    if (!XpdResourcesPlugin.isInHeadlessMode()) {
	        return Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
	                .get(Xpdl2ResourcesConsts.IMG_ERROR_EVENT_ICON12);
	    }
	    return null;
	}

	/**
	 * @see com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor#getErrorThrowerId(java.lang.Object)
	 * 
	 * @param errorThrower
	 *            The Activity throwing the error.
	 * @return The Activity ID.
	 */
	@Override
	public String getErrorThrowerId(Object errorThrower)
	{
	    // Safe to assume that errorThrower is the one we returned which is
	    // ALWAYS an activity.
	    return ((Activity) errorThrower).getId();
	}

	/**
	 * @see com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor#getErrorThrowerContainerId(java.lang.Object)
	 * 
	 * @param errorThrower
	 *            The Activity throwing the error.
	 * @return The containing process ID.
	 */
	@Override
	public String getErrorThrowerContainerId(Object errorThrower)
	{
	    // Safe to assume that errorThrower is the one we returned which is
	    // ALWAYS an activity.
	    return ((Activity) errorThrower).getProcess().getId();
	}

	/**
	 * @param errorThrowerTaskOrEvent
	 *            The Activity throwing the error.
	 * @return The image for that activity.
	 */
	protected Image getActivityImage(Activity errorThrowerTaskOrEvent)
	{
	    if (errorThrowerTaskOrEvent.getImplementation() instanceof Task) {
	        return DiagramDropObjectUtils.getImageForTaskType(TaskObjectUtil
	                .getTaskType(errorThrowerTaskOrEvent));
	
	    } else if (errorThrowerTaskOrEvent.getEvent() != null) {
	        return DiagramDropObjectUtils.getImageForEventType(EventObjectUtil
	                .getFlowType(errorThrowerTaskOrEvent), EventObjectUtil
	                .getEventTriggerType(errorThrowerTaskOrEvent));
	    }
	    return null;
	}

	/**
	 * @param errorThrowerTaskOrEvent
	 *            The Activity throwing the error.
	 * @return The display name for that Activity.
	 */
	protected String getActivityName(Activity errorThrowerTaskOrEvent)
	{
	    String name = ProcessDataUtil.getActivityName(errorThrowerTaskOrEvent);
	    if (name == null || name.length() == 0) {
	        if (errorThrowerTaskOrEvent.getImplementation() instanceof Task) {
	            name =
	                    "<"     + TaskObjectUtil.getTaskType(errorThrowerTaskOrEvent) //$NON-NLS-1$
	                                    .toString() + ">"; //$NON-NLS-1$
	
	        } else if (errorThrowerTaskOrEvent.getEvent() != null) {
	            name =
	                    "<"     + EventObjectUtil.getFlowType(errorThrowerTaskOrEvent) //$NON-NLS-1$
	                                    .toString() + ">"; //$NON-NLS-1$
	        } else {
	            name = "?"; //$NON-NLS-1$
	        }
	    }
	
	    return name;
	}

}
