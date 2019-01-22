/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.resources.xpdl2.Activator;
import com.tibco.xpd.implementer.script.NamespacePrefixMapUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.NamespacePrefixMap;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Quick fix to add the namespace prefix map to the given web service activity.
 * 
 * @author aallway
 * @since 1 Jun 2012
 */
public class AddNamespacePrefixMapResolution extends
        AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            try {
                NamespacePrefixMap namespacePrefixMap =
                        NamespacePrefixMapUtil
                                .createNamespacePrefixMap(activity);

                return NamespacePrefixMapUtil
                        .getSetNamespacePrefixMapCommand(editingDomain,
                                activity,
                                namespacePrefixMap);

            } catch (Exception e) {
                Activator.getDefault().getLogger()
                        .error(e, "Cannot create namespace prefix map"); //$NON-NLS-1$
            }

        }
        return null;
    }

}
