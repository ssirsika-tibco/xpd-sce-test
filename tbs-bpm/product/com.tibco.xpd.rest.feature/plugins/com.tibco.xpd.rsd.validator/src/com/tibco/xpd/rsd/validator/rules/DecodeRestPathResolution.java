/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.validator.rules;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.Service;
import com.tibco.xpd.rsd.validator.internal.Messages;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Removes URL encoding from the REST service descriptor Context / Resource path
 * 
 * @author aallway
 * @since 17 Aug 2015
 */
public class DecodeRestPathResolution extends AbstractWorkingCopyResolution {

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

        if (target instanceof Service) {
            Service service = (Service) target;

            String contextPath = ((Service) target).getContextPath();

            if (contextPath != null) {
                CompoundCommand cmd =
                        new CompoundCommand(Messages.DecodeRestPathResolution_DecodeContextPath_menu);
                cmd.append(SetCommand.create(editingDomain,
                        service,
                        RsdPackage.eINSTANCE.getService_ContextPath(),
                        URI.decode(contextPath)));
                return cmd;
            }

        } else if (target instanceof Resource) {
            Resource resource = (Resource) target;

            String resourcePath = ((Resource) target).getPathTemplate();

            if (resourcePath != null) {
                CompoundCommand cmd =
                        new CompoundCommand(Messages.DecodeRestPathResolution_DecodeResourcePath_Decode);
                cmd.append(SetCommand.create(editingDomain,
                        resource,
                        RsdPackage.eINSTANCE.getResource_PathTemplate(),
                        URI.decode(resourcePath)));
                return cmd;
            }
        }

        return null;
    }

}
