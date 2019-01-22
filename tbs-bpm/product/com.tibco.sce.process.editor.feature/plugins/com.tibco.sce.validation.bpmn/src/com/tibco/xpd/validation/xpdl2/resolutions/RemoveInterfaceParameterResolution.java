package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.ecore.EObject;

public class RemoveInterfaceParameterResolution extends
        AbstractRemoveObjectResolution {

    /**
     * @see com.tibco.xpd.validation.xpdl2.resolutions.AbstractRemoveObjectResolution#getCommandLabel(org.eclipse.emf.ecore.EObject,
     *      org.eclipse.core.resources.IMarker)
     * 
     * @param validationMarkerTarget
     * @param marker
     * @return
     */
    @Override
    protected String getCommandLabel(EObject validationMarkerTarget,
            IMarker marker) {
        return Messages.RemoveInterfaceParameter;
    }

}
