/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.resources.xpdl2.precommit.FixWsdlEndpointLocationPreCommitListener;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;

/**
 * FixWsdlEndPointLocationResolution
 * 
 * 
 * @author aallway
 * @since 3.3 (8 Oct 2009)
 */
public class FixWsdlEndPointLocationResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        Command cmd = null;
        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            ActivityMessageProvider messageProvider =
                    ActivityMessageProviderFactory.INSTANCE
                            .getMessageProvider(activity);
            if (messageProvider != null) {
                cmd =
                        FixWsdlEndpointLocationPreCommitListener
                                .getFixLocationCommand(editingDomain,
                                        activity,
                                        messageProvider);
            }

        }

        return cmd;
    }

}
