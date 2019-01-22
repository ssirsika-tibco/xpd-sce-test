/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.resources.xpdl2.utils.ProcessDeveloperUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Resolution to select a REST Service participant from the list of REST service
 * participants available in the package.
 * 
 * @author sajain
 * @since Aug 19, 2015
 */
public class SelectRESTServiceParticipantResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof Activity) {

            Activity activity = (Activity) target;

            return ProcessDeveloperUtil
                    .getSetRESTServiceParticipantFromPickerCommand(editingDomain,
                            activity);
        }

        return null;
    }
}
