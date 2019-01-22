/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.ReferenceTaskUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Resolution to reference task referencing library taks.
 * 
 * 
 * @author Jan Arciuchiewicz
 * @since 3.3
 */
public class RemoveTaskLibraryTaskRefResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain ed, EObject target,
            IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            if (ReferenceTaskUtil.isTaskLibraryTaskReference(activity)) {
                return ReferenceTaskUtil.getSetReferencedLocalTaskCommand(ed,
                        activity,
                        null);
            }
        }

        return null;
    }

}
