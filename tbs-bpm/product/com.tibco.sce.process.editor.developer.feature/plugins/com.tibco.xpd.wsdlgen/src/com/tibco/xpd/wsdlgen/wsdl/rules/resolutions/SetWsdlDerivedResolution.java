/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.wsdlgen.wsdl.rules.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;
import com.tibco.xpd.wsdlgen.WsdlgenPlugin;
import com.tibco.xpd.wsdlgen.internal.Messages;

/**
 * Resolution to set the derived flag on the target marker's resource
 * 
 * 
 * @author aallway
 * @since 3.3 (14 Apr 2010)
 */
public class SetWsdlDerivedResolution extends AbstractWorkingCopyResolution {

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
        IResource resource = marker.getResource();
        if (resource != null
                && resource.getFileExtension()
                        .equals(WsdlUIPlugin.WSDL_FILE_EXTENSION)) {
            if (!resource.isDerived()) {
                return new SetResourceDerivedCommand(resource, marker);
            }
        }
        return null;
    }

    private static class SetResourceDerivedCommand extends AbstractCommand {

        private IResource resource;

        private IMarker marker;

        /**
         * @param resource
         * @param marker
         */
        public SetResourceDerivedCommand(IResource resource, IMarker marker) {
            super();
            this.resource = resource;
            this.marker = marker;

            setLabel(String
                    .format(Messages.SetWsdlDerivedResolution_SetResourceDerivedCommand_menu,
                            resource.getName()));
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
         */
        @Override
        public boolean canExecute() {
            return resource != null && resource.exists();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.Command#execute()
         */
        public void execute() {
            if (resource.exists()) {
                try {
                    /* Set the wsdl file to derived. */
                    resource.setDerived(true);

                    /*
                     * Delete the marker directly (validation will not pick up
                     * changes to file PropertiesProvider.class
                     */
                    if (marker.exists()) {
                        marker.delete();
                    }
                } catch (CoreException e) {
                    WsdlgenPlugin
                            .getDefault()
                            .getLogger()
                            .warn(e,
                                    String
                                            .format("Caught exception setting resource '%s' to derived.", //$NON-NLS-1$
                                                    resource.getName()));
                }
            }
            return;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.Command#redo()
         */
        public void redo() {
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.AbstractCommand#undo()
         */
        @Override
        public void undo() {
        }

    }

}
