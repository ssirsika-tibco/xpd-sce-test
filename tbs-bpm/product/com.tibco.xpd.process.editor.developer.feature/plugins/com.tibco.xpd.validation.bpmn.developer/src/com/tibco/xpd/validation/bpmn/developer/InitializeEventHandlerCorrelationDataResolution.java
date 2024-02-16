package com.tibco.xpd.validation.bpmn.developer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.bpmn.developer.internal.Messages;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.EventHandlerInitialisers;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Adds all activities that are considered viable event handler initialising
 * activities to the event handler's initialiser list. This will determine the
 * viable initialiser activities that are not currently present and add them to
 * the list.
 * 
 * @author patkinso
 * @since 6 Aug 2012
 */
public class InitializeEventHandlerCorrelationDataResolution extends
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

		if (target instanceof Activity)
		{
            CompoundCommand cCmd =
                    new CompoundCommand(
                            Messages.InitializeEventHandlerCorrelationDataResolution_InitializeListWithStartActivities_menu);

            Activity activity = (Activity) target;
            com.tibco.xpd.xpdl2.Process process = activity.getProcess();
            if (process != null) {

                List<Activity> viableInitialiserActivities =
                        new ArrayList<Activity>();

                /*
                 * SID XPD-3925. Need to take all process start activties as
                 * "places after which the user can assign correlation data in
                 * complete script before initialising event handler - not just
                 * incoming request.,
                 */
                for (Activity processActivity : process.getActivities()) {
                    if (Xpdl2ModelUtil.isStartProcessActivity(processActivity)) {
                        viableInitialiserActivities.add(processActivity);
                    }
                }

                // add any activity ref not present in initialiser list
                Event evt = activity.getEvent();
                if (evt != null) {
                    /*
                     * XPD-7075: Resolution valid event for Signal Events.
                     */
					/*
					 * ACE-6836 Re-enable Correlation Data and Hence Event initialisers for Incoming Request Event
					 * handlers and Event Sub-processes
					 */
					if (evt.getEventTriggerTypeNode() == null
							|| evt.getEventTriggerTypeNode() instanceof TriggerResultMessage
                            || evt.getEventTriggerTypeNode() instanceof TriggerResultSignal) {
                        EObject eventTriggerTypeNode =
                                evt.getEventTriggerTypeNode();

                        EventHandlerInitialisers evtHdlInitialisers =
                                getOrCreateEventHandlerInitialiser(editingDomain,
                                        cCmd,
										activity,
                                        eventTriggerTypeNode);

                        EList<ActivityRef> currentActivityRefs = null;
                        if (evtHdlInitialisers != null) {
                            currentActivityRefs =
                                    evtHdlInitialisers.getActivityRef();
                        }

                        List<Activity> activitiesToAdd =
                                findActivitiesToAdd(currentActivityRefs,
                                        viableInitialiserActivities);

                        if (!activitiesToAdd.isEmpty()) {
                            addEventHandlerInitialisers(editingDomain,
                                    evtHdlInitialisers,
                                    activitiesToAdd,
                                    cCmd);
                        }
                    }
                }
            }

            if (!cCmd.isEmpty()) {
                return cCmd;
            }

        }

        return null;
    }

    /**
     * @param ed
     * @param evtHdlInitialisers
     * @param activitiesToAdd
     * @param cCmd
     */
    private void addEventHandlerInitialisers(EditingDomain ed,
            EventHandlerInitialisers evtHdlInitialisers,
            List<Activity> activitiesToAdd, CompoundCommand cCmd) {

        for (Activity act : activitiesToAdd) {

            ActivityRef actRef =
                    XpdExtensionFactory.eINSTANCE.createActivityRef();
            actRef.setIdRef(act.getId());

            cCmd.append(AddCommand.create(ed,
                    evtHdlInitialisers,
                    XpdExtensionPackage.eINSTANCE
                            .getEventHandlerInitialisers_ActivityRef(),
                    actRef));
        }

    }

    /**
     * @param currentActivityRefs
     * @param viableInitialiserActivities
     * @return
     */
    private List<Activity> findActivitiesToAdd(
            EList<ActivityRef> currentActivityRefs,
            List<Activity> viableInitialiserActivities) {

        List<Activity> ret = new ArrayList<Activity>();

        for (Activity activity : viableInitialiserActivities) {
            String viableActivityName = activity.getName();

            boolean present = false;

            for (ActivityRef activityRef : currentActivityRefs) {
                String currentActivityName =
                        activityRef.getActivity().getName();

                if (viableActivityName.equals(currentActivityName)) {
                    present = true;
                }
            }

            if (!present) {
                ret.add(activity);
            }
        }

        return ret;
    }

    /**
     * @param editingDomain
     * @param cCmd
     * @param eventTriggerTypeNode
     * @return
     */
    private EventHandlerInitialisers getOrCreateEventHandlerInitialiser(
            EditingDomain editingDomain, CompoundCommand cCmd,
			Activity activity,
            EObject eventTriggerTypeNode) {

		/*
		 * ACE-6836 Re-enable Correlation Data and Hence Event initialisers for Incoming Request Event handlers and
		 * Event Sub-processes.
		 * 
		 * Have to put EventHandlerInitialisers element on the Activity for Incoming Request events as they do not have
		 * a trigger type node.
		 */
        EventHandlerInitialisers model =
                (EventHandlerInitialisers) Xpdl2ModelUtil
						.getOtherElement(
								eventTriggerTypeNode == null ? activity : (OtherElementsContainer) eventTriggerTypeNode,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_EventHandlerInitialisers());

        if (model == null) {

            // Add ext model <EventHandlerInitialisers> to ##OTHER
            model =
                    XpdExtensionFactory.eINSTANCE
                            .createEventHandlerInitialisers();
            cCmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
					eventTriggerTypeNode == null ? activity : (OtherElementsContainer) eventTriggerTypeNode,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_EventHandlerInitialisers(),
                    model));
        }

        return model;
    }

}
