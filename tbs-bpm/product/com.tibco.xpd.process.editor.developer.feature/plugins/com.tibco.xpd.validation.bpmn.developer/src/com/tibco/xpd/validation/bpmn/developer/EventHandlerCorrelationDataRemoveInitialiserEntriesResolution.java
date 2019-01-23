/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.EventHandlerInitialisers;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Removes all specified activities which appear in an event handlers
 * <b>Initialiser</b> list. Activities for removal are considered from the
 * originating issue's <code>additionalInfo</code> map
 * 
 * @author patkinso
 * @since 6 Aug 2012
 */
public class EventHandlerCorrelationDataRemoveInitialiserEntriesResolution
        extends AbstractWorkingCopyResolution {

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

        CompoundCommand cCmd =
                new CompoundCommand(
                        Messages.EventHandlerCorrelationDataRemoveInitialiserEntriesResolution_RemovalOfHangingActivityReferences_menu);

        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            Object trmObj = activity.getEvent().getEventTriggerTypeNode();

            /*
             * XPD-7075: Resolution valid event for Signal Events.
             */
            if (trmObj instanceof TriggerResultMessage
                    || trmObj instanceof TriggerResultSignal) {

                EventHandlerInitialisers evtHdlInitialisers =
                        (EventHandlerInitialisers) Xpdl2ModelUtil
                                .getOtherElement((OtherElementsContainer) trmObj,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_EventHandlerInitialisers());

                EList<ActivityRef> activityRefs =
                        evtHdlInitialisers.getActivityRef();

                Properties props = ValidationUtil.getAdditionalInfo(marker);
                if (props != null) {

                    List<ActivityRef> refIDsToRemove =
                            new ArrayList<ActivityRef>();

                    int i = 0;
                    String k =
                            EventHandlerCorrelationDataInitializerRule.ACTIVITY_REF_ID
                                    + (++i);
                    while (props.containsKey(k)) {

                        String v = props.getProperty(k);
                        for (ActivityRef ref : activityRefs) {
                            if (ref.getIdRef().equals(v)) {
                                refIDsToRemove.add(ref);
                            }
                        }

                        k =
                                EventHandlerCorrelationDataInitializerRule.ACTIVITY_REF_ID
                                        + (++i);
                    }

                    // remove activity refs
                    cCmd.append(RemoveCommand.create(editingDomain,
                            evtHdlInitialisers,
                            XpdExtensionPackage.eINSTANCE
                                    .getEventHandlerInitialisers_ActivityRef(),
                            refIDsToRemove));
                }
            }
        }

        return cCmd;
    }

}
