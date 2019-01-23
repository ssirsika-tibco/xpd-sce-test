/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.RequiredAccessPrivileges;
import com.tibco.xpd.xpdl2.ExternalReference;

/**
 * Resolution that removes invalid privilege references (references to
 * privileges that are present in the xpdl model but are not available in the
 * referenced org model)
 * 
 * 
 * @author kthombar
 * @since May 5, 2015
 */
public abstract class AbstractRemoveInvalidPrivilegeReferenceResolution extends
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
    protected final Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target != null) {
            /*
             * get the required access privileges
             */
            RequiredAccessPrivileges requiredAccesssPrivileges =
                    getRequiredAccessPrivileges(target);

            if (null != requiredAccesssPrivileges) {

                EList<ExternalReference> privilegeReference =
                        requiredAccesssPrivileges.getPrivilegeReference();
                for (ExternalReference externalReference : privilegeReference) {

                    String xref = externalReference.getXref();
                    EObject eObject = OMUtil.getEObjectByID(xref);
                    if (null == eObject) {
                        /*
                         * If the privilege could not be found then delete the
                         * reference.
                         */
                        CompoundCommand cmd = new CompoundCommand();
                        cmd.append(RemoveCommand.create(editingDomain,
                                externalReference));
                        return cmd;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 
     * @param target
     *            the quick fix target
     * @return the {@link RequiredAccessPrivileges} in the passed target.
     */
    protected abstract RequiredAccessPrivileges getRequiredAccessPrivileges(
            EObject target);
}
