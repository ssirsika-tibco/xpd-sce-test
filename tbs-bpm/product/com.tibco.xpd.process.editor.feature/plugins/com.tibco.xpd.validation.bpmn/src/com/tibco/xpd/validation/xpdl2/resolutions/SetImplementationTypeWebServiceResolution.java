/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ImplementationType;

/**
 * Resolution to set the Implementation type of the target activity(with the
 * problem marker.) to Web Service.
 * 
 * @author aallway
 * @since 3.3 (18 Feb 2010)
 */
public class SetImplementationTypeWebServiceResolution extends
        AbstractWorkingCopyResolution {

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     * getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            TaskType type = TaskObjectUtil.getTaskTypeStrict(activity);
            if (type != null) {
                if (TaskType.SERVICE_LITERAL.equals(type)
                        || TaskType.SEND_LITERAL.equals(type)
                        || TaskType.RECEIVE_LITERAL.equals(type)) {
                    return TaskObjectUtil
                            .getChangeTaskImplementationCommand(editingDomain,
                                    activity,
                                    type,
                                    TaskImplementationTypeDefinitions.WEB_SERVICE,
                                    ImplementationType.WEB_SERVICE_LITERAL
                                            .getLiteral());

                }
            } else if (activity.getEvent() != null) {
                EventTriggerType eventType =
                        EventObjectUtil.getEventTriggerType(activity);
                if (EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL
                        .equals(eventType)
                        || EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                                .equals(eventType)) {
                    return EventObjectUtil
                            .getChangeEventImplementationCommand(editingDomain,
                                    activity,
                                    TaskImplementationTypeDefinitions.WEB_SERVICE,
                                    ImplementationType.WEB_SERVICE_LITERAL
                                            .getLiteral());
                }
            }
        }
        return null;
    }
}
