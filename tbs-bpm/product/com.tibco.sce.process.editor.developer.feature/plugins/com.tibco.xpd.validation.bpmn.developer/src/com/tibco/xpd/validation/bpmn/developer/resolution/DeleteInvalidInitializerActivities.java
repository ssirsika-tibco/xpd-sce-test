/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.bpmn.developer.internal.Messages;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdExtension.EnablementType;
import com.tibco.xpd.xpdExtension.InitializerActivitiesType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Removes the deleted Initializer Activity information from the Adhoc
 * Activities. [i.e. from the model]
 * 
 * @author kthombar
 * @since 13-Aug-2014
 */
public class DeleteInvalidInitializerActivities extends
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

        CompoundCommand ccmd =
                new CompoundCommand(Messages.DeleteInvalidInitializerActivities_RemoveInvalidInitializerActivitiesResolution_label);

        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            Object adhocConfig =
                    Xpdl2ModelUtil.getOtherElement(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AdHocTaskConfiguration());

            if (adhocConfig instanceof AdHocTaskConfigurationType) {

                AdHocTaskConfigurationType adhocConfigType =
                        (AdHocTaskConfigurationType) adhocConfig;
                EnablementType enablement = adhocConfigType.getEnablement();

                if (enablement != null) {
                    InitializerActivitiesType initializerActivities =
                            enablement.getInitializerActivities();

                    if (initializerActivities != null) {
                        EList<ActivityRef> activityRef =
                                initializerActivities.getActivityRef();

                        List<ActivityRef> referencesToRemove =
                                new ArrayList<ActivityRef>();

                        for (ActivityRef eachActivityRef : activityRef) {

                            String idRef = eachActivityRef.getIdRef();

                            if (idRef != null && !idRef.isEmpty()) {

                                Process process = activity.getProcess();

                                Activity activityById =
                                        Xpdl2ModelUtil.getActivityById(process,
                                                idRef);

                                if (activityById == null) {
                                    /*
                                     * collect all the unavailable Activity
                                     * references so that they can be removed.
                                     */
                                    referencesToRemove.add(eachActivityRef);
                                }
                            }
                        }
                        ccmd.append(RemoveCommand.create(editingDomain,
                                referencesToRemove));
                    }
                }
            }
        }
        return ccmd;
    }

}
