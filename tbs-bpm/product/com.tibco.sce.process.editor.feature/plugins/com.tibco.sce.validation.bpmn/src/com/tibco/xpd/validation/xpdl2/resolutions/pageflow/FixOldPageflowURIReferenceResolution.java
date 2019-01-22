/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions.pageflow;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * FixOldPageflowURIReferenceResolution
 * 
 * 
 * @author aallway
 * @since 3.3 (11 Nov 2009)
 */
public class FixOldPageflowURIReferenceResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            FormImplementation formImplementation =
                    TaskObjectUtil.getUserTaskFormImplementation(activity);
            if (formImplementation != null) {
                String uriString = formImplementation.getFormURI();
                if (uriString != null) {
                    URI uri = URI.createURI(uriString);
                    if (uri != null) {
                        String id = uri.fragment();
                        if (id != null && id.length() > 0) {
                            Process pageflow =
                                    Xpdl2WorkingCopyImpl.locateProcess(id);
                            if (pageflow != null) {
                                String newUri =
                                        TaskObjectUtil
                                                .getUserTaskFormURIFromPageflowProcess(pageflow);

                                return SetCommand
                                        .create(editingDomain,
                                                formImplementation,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getFormImplementation_FormURI(),
                                                newUri);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

}
