/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.RedefinableHeader;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Resolution to set the {@link Package} version to be the same as the project
 * lifecycle version.
 * 
 * @author njpatel
 * 
 */
public class SetPackageVersionToProjectVersionResolution extends
        AbstractWorkingCopyResolution {

    private final String USER_NAME_PROPERTY = "user.name"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     * getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Package) {
            IResource resource = marker.getResource();
            final String projectVersion =
                    ProjectUtil.getProjectVersion(resource.getProject());
            RedefinableHeader header =
                    ((Package) target).getRedefinableHeader();
            if (header != null) {
                if (resource != null && resource.getProject() != null) {
                    return SetCommand.create(editingDomain,
                            header,
                            Xpdl2Package.eINSTANCE
                                    .getRedefinableHeader_Version(),
                            projectVersion);
                }
            } else {
                /*
                 * first create the redefinable header for the package and then
                 * set the project version to the header
                 */
                final Package pckgWithoutVersion = (Package) target;
                editingDomain.getCommandStack().execute(new RecordingCommand(
                        (TransactionalEditingDomain) editingDomain) {

                    @Override
                    protected void doExecute() {
                        RedefinableHeader redefinableHeader =
                                Xpdl2Factory.eINSTANCE
                                        .createRedefinableHeader();
                        redefinableHeader.setAuthor(System
                                .getProperty(USER_NAME_PROPERTY));
                        redefinableHeader.setVersion(projectVersion);
                        pckgWithoutVersion
                                .setRedefinableHeader(redefinableHeader);
                    }

                });

            }
        }
        return null;
    }

}
