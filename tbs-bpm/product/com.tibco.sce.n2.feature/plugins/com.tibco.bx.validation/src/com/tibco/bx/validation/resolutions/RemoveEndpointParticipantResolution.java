/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Performers;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * RemoveEndpointParticipantResolution - to remove the endpoint alias for a task
 * referring the generated wsdl in the same project
 * 
 * 
 * @author bharge
 * @since 3.3 (29 Mar 2010)
 */
public class RemoveEndpointParticipantResolution extends
        AbstractWorkingCopyResolution {

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
        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            ActivityMessageProvider messageProvider =
                    ActivityMessageProviderFactory.INSTANCE
                            .getMessageProvider(activity);
            if (messageProvider != null) {
                WebServiceOperation webServiceOperation =
                        messageProvider.getWebServiceOperation(activity);
                CompoundCommand cmd = new CompoundCommand();
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                webServiceOperation,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Alias(),
                                "")); //$NON-NLS-1$

                // Delete the current performer(s)
                EList<Performer> oldPerformers = activity.getPerformerList();
                if (oldPerformers != null && oldPerformers.size() > 0) {
                    cmd.append(RemoveCommand.create(editingDomain,
                            oldPerformers));

                    // Remove empty performers sequence if there is one
                    Performers perfsSeqParent = activity.getPerformers();
                    if (perfsSeqParent != null) {
                        cmd.append(RemoveCommand.create(editingDomain,
                                perfsSeqParent));
                    }
                }
                return cmd;
            }
        }
        return null;
    }

}
