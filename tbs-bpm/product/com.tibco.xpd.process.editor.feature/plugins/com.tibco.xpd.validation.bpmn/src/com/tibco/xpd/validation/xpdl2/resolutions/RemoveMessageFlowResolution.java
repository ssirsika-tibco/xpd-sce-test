/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.validations.bpmn.internal.Messages;

/**
 * Resolution to remove message flow.
 * 
 * @author aallway
 * @since 3.2
 */
public class RemoveMessageFlowResolution extends AbstractRemoveObjectResolution {

    @Override
    protected String getCommandLabel(EObject validationMarkerTarget,
            IMarker marker) {
        return Messages.RemoveMessageFlowResolution_RemoveMsgFlow_menu;
    }

}
