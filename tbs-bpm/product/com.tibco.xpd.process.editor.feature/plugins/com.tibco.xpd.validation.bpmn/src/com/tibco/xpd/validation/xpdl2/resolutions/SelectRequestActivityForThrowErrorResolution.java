/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;

/**
 * SelectRequestActivityForThrowErrorResolution
 * 
 * 
 * @author aallway
 * @since 3.3 (7 Dec 2009)
 */
public class SelectRequestActivityForThrowErrorResolution extends
        AbstractSelectActivityReferenceResolution {

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.xpdl2.resolutions.
     * AbstractSelectActivityReferenceResolution
     * #getSetActivityReferenceCommand(org
     * .eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject,
     * com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    protected Command getSetActivityReferenceCommand(
            EditingDomain editingDomain, EObject resolutionTarget,
            Activity activity) {
        if (resolutionTarget instanceof Activity) {
            Activity targetActivity = (Activity) resolutionTarget;

            return ThrowErrorEventUtil
                    .getConfigureAsFaultMessageErrorCommand(editingDomain,
                            targetActivity,
                            activity.getId(),
                            ThrowErrorEventUtil.getThrowErrorCode(activity));
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.xpdl2.resolutions.
     * AbstractSelectActivityReferenceResolution
     * #getTitle(org.eclipse.emf.ecore.EObject)
     */
    @Override
    protected String getTitle(EObject resolutionTarget) {
        return Messages.SelectRequestActivityForThrowError_SelectRequestActivity_title;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.xpdl2.resolutions.
     * AbstractSelectActivityReferenceResolution
     * #isValidActivityForReference(org.eclipse.emf.ecore.EObject,
     * com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    protected boolean isValidActivityForReference(EObject resolutionTarget,
            Activity activity) {
        return ReplyActivityUtil.isIncomingRequestActivity(activity);
    }

}
