/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.om.validator.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.om.core.om.Authorizable;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.PrivilegeAssociation;
import com.tibco.xpd.om.core.om.SystemAction;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Validation resolution to delete any 'empty' privilege association on system
 * actions.
 * 
 * @author sajain
 * @since Mar 5, 2014
 */
public class DeleteEmptyPrivilegeAssociationsResolution extends
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
        CompoundCommand cmd = new CompoundCommand();
        if (target instanceof Authorizable) {
            Authorizable authrzbl = (Authorizable) target;
            /*
             * Fetch all system actions from the authorizable.
             */
            EList<SystemAction> sysActions = authrzbl.getSystemActions();
            for (SystemAction eachSysAction : sysActions) {
                /*
                 * Fetch all privilege associations of current system action.
                 */
                EList<PrivilegeAssociation> privAssocs =
                        eachSysAction.getPrivilegeAssociations();
                for (PrivilegeAssociation eachPrivAssoc : privAssocs) {
                    /*
                     * Check if the current privilege association is an empty
                     * privilege association i.e., a privilege association
                     * without any privilege.
                     */
                    if (eachPrivAssoc.getPrivilege() == null) {
                        /*
                         * Append command to remove an empty privilege
                         * association.
                         */
                        cmd.append(RemoveCommand.create(editingDomain,
                                eachSysAction,
                                OMPackage.eINSTANCE.getPrivilegeAssociation(),
                                eachPrivAssoc));
                    }
                }
            }
            return cmd;
        }
        return null;
    }
}
