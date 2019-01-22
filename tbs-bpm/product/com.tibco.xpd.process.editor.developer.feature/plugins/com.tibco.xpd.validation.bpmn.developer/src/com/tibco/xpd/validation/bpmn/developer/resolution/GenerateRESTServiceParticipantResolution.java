/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.Service;
import com.tibco.xpd.validation.bpmn.developer.internal.Messages;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.RestServiceOperation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performers;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to generate a REST service participant.
 * 
 * @author sajain
 * @since Aug 20, 2015
 */
public class GenerateRESTServiceParticipantResolution extends
        AbstractWorkingCopyResolution implements IResolution {

    public GenerateRESTServiceParticipantResolution() {
    }

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

            CompoundCommand compoundCmd =
                    new CompoundCommand(
                            Messages.GenerateRESTServiceParticipantResolution_command_label);

            /*
             * Construct a rest service task adapter.
             */
            RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();

            /*
             * Get method indexer item.
             */
            IndexerItem item = getMethodIndexerItem(rsta, activity);

            if (item != null) {

                /*
                 * Get the method from the indexer item.
                 */
                Method method = rsta.getRSOMethod(item);

                if (method != null) {

                    Package pkg = Xpdl2ModelUtil.getPackage(activity);

                    Service svc = rsta.getService(method);

                    Participant participant = rsta.createParticipant(svc);

                    compoundCmd.append(rsta
                            .getAddRESTServiceParticipantCommand(editingDomain,
                                    pkg,
                                    svc,
                                    participant));

                    Performers performers =
                            rsta.createPerformers(participant.getId());

                    compoundCmd.append(new SetCommand(editingDomain, activity,
                            Xpdl2Package.eINSTANCE.getActivity_Performers(),
                            performers));

                    return compoundCmd;
                }
            }
        }

        return null;
    }

    /**
     * Get the indexer item for the currently selected method.
     * 
     * @param rsta
     * @param activity
     * 
     @return The indexer item for the currently selected method.
     */
    private IndexerItem getMethodIndexerItem(RestServiceTaskAdapter rsta,
            Activity activity) {

        OtherElementsContainer rsoParent = rsta.getRSOContainer(activity);

        RestServiceOperation rso = rsta.getRSO(rsoParent);

        return rsta.getMethodIndexerItem(rso);
    }
}
