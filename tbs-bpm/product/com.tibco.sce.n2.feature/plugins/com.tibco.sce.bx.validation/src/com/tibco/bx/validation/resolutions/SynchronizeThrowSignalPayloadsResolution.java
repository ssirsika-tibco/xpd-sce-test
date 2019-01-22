/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to the validation issue
 * "All throw signals for the same signal (%1$s) must have the same associated data payload."
 * <p>
 * Finds all other throw signal events that throw the same signal as the target
 * and matches heir payload definition (associated data definition) to the
 * target throw signal event activity.
 * 
 * @author aallway
 * @since 14 May 2012
 */
public class SynchronizeThrowSignalPayloadsResolution extends
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

        if (target instanceof Activity) {
            Activity throwSignalEvent = (Activity) target;

            Process process = throwSignalEvent.getProcess();
            if (process != null) {
                String signalName =
                        EventObjectUtil.getSignalName(throwSignalEvent);

                if (signalName != null && !signalName.isEmpty()) {
                    /*
                     * Get the data actually associated with the throw event so
                     * we can check it's valid scope to set on the other events.
                     */
                    Collection<ActivityInterfaceData> throwSignalData =
                            ActivityInterfaceDataUtil
                                    .getActivityInterfaceData(throwSignalEvent);

                    /*
                     * Get a list of the other throwers of the same signal.
                     */
                    List<Activity> otherThrowers =
                            getOtherSignalThrowers(process,
                                    throwSignalEvent,
                                    signalName);

                    if (!otherThrowers.isEmpty()) {
                        CompoundCommand cmd =
                                new CompoundCommand(
                                        Messages.SynchronizeThrowSignalPayloadsResolution_SynchPayloads_menu);

                        /* Just need to duplicate the associated data elements. */

                        AssociatedParameters assocParams =
                                (AssociatedParameters) Xpdl2ModelUtil
                                        .getOtherElement(throwSignalEvent,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_AssociatedParameters());

                        for (Activity otherThrower : otherThrowers) {

                            /*
                             * Check that all the data in source signal's
                             * payload data is in scope of the other activity.
                             */
                            if (!checkSourceSignalDataInScope(throwSignalData,
                                    otherThrower)) {
                                return null;
                            }

                            AssociatedParameters otherAssocParams =
                                    (AssociatedParameters) Xpdl2ModelUtil
                                            .getOtherElement(otherThrower,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_AssociatedParameters());

                            if (assocParams == null) {
                                if (otherAssocParams != null) {
                                    /*
                                     * Remove AssociatedParameters to match
                                     * target signal event's lack of
                                     * AssociateParameters element.
                                     */
                                    cmd.append(Xpdl2ModelUtil
                                            .getSetOtherElementCommand(editingDomain,
                                                    otherThrower,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_AssociatedParameters(),
                                                    null));
                                }

                            } else {
                                /*
                                 * Copy target signal's AssociatedParameters to
                                 * other thrower.
                                 */
                                AssociatedParameters copyAssocParams =
                                        (AssociatedParameters) EcoreUtil
                                                .copy(assocParams);

                                cmd.append(Xpdl2ModelUtil
                                        .getSetOtherElementCommand(editingDomain,
                                                otherThrower,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_AssociatedParameters(),
                                                copyAssocParams));
                            }

                        }

                        return cmd;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Check that all the data in source signal's payload data is in scope of
     * the other activity - or if the signal data is associated with the event
     * implicitly and the throw signal doesn't have the same data then complain.
     * 
     * @param throwSignalData
     * @param otherThrower
     * @return <code>false</code> if any of the data in signal is out of scope
     *         of the given signal OR if the signal data is explicitly
     *         associated and the implicit data sets are different.
     */
    private boolean checkSourceSignalDataInScope(
            Collection<ActivityInterfaceData> throwSignalData,
            Activity otherThrower) {

        if (isImplicitAssociation(throwSignalData)) {
            /*
             * Check if all the implicit data set for the otherThrower is the
             * same as the original signal payload (if not it means that the
             * other thrower has local data that will be implicitly associated
             * and that is not in same scope as source signal thrower).
             */

            List<ProcessRelevantData> otherThrowerInScopeData =
                    ProcessInterfaceUtil
                            .getAllAvailableRelevantDataForActivity(otherThrower);

            boolean isSame = true;
            if (otherThrowerInScopeData.size() != throwSignalData.size()) {
                isSame = false;

            } else {
                for (ProcessRelevantData otherThrowerData : otherThrowerInScopeData) {
                    boolean found = false;
                    for (ActivityInterfaceData signalData : throwSignalData) {
                        if (signalData.getData().equals(otherThrowerData)) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        isSame = false;
                        break;
                    }
                }
            }

            if (!isSame) {
                if (Display.getCurrent() != null) {
                    String name = Xpdl2ModelUtil.getDisplayName(otherThrower);
                    if (name == null || name.isEmpty()) {
                        name =
                                Messages.SynchronizeThrowSignalPayloadsResolution_UnnamedTag_label;
                    }

                    MessageDialog
                            .openError(Display.getCurrent().getActiveShell(),
                                    Messages.SynchronizeThrowSignalPayloadsResolution_CannotSynchPayloads_title,
                                    String.format(Messages.SynchronizeThrowSignalPayloadsResolution_ImplicitPayloadWouldCauseDifferentPayloads_message,
                                            name));
                }

                return false;
            }

        } else {
            /*
             * If the source signal's data is explicitly associated, then we
             * should check that all of that data is in the scope of
             * otherThrower (else we'd be adding associated data that's out of
             * scope of that activity.
             */
            List<ProcessRelevantData> otherThrowerInScopeData =
                    ProcessInterfaceUtil
                            .getAllAvailableRelevantDataForActivity(otherThrower);

            for (ActivityInterfaceData signalData : throwSignalData) {
                boolean found = false;
                for (ProcessRelevantData otherThrowerData : otherThrowerInScopeData) {
                    if (otherThrowerData.equals(signalData.getData())) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    String name = Xpdl2ModelUtil.getDisplayName(otherThrower);
                    if (name == null || name.isEmpty()) {
                        name =
                                Messages.SynchronizeThrowSignalPayloadsResolution_UnnamedTag_label;
                    }

                    MessageDialog
                            .openError(Display.getCurrent().getActiveShell(),
                                    Messages.SynchronizeThrowSignalPayloadsResolution_CannotSynchPayloads_title,
                                    String.format(Messages.SynchronizeThrowSignalPayloadsResolution_SelectedSignalHasDataNotInScopeOfOther_message,
                                            name));

                    return false;
                }
            }

        }

        return true;
    }

    /**
     * @param process
     * @param throwSignalEvent
     * @param signalName
     * @return list of events that throw the same signal as the given
     *         throwSignalEvent
     */
    public List<Activity> getOtherSignalThrowers(Process process,
            Activity throwSignalEvent, String signalName) {
        List<Activity> otherThrowers = new ArrayList<Activity>();

        for (Activity activity : Xpdl2ModelUtil.getAllActivitiesInProc(process)) {
            if (activity != throwSignalEvent) {
                if (EventObjectUtil.getEventTriggerType(activity) == EventTriggerType.EVENT_SIGNAL_THROW_LITERAL) {
                    if (signalName.equals(EventObjectUtil
                            .getSignalName(activity))) {
                        otherThrowers.add(activity);
                    }
                }
            }
        }
        return otherThrowers;
    }

    /**
     * @param throwSignalData
     * @return <code>true</code> if the activity's data is implicitly
     *         associated.
     */
    private boolean isImplicitAssociation(
            Collection<ActivityInterfaceData> throwSignalData) {
        for (ActivityInterfaceData interfaceData : throwSignalData) {
            if (interfaceData.isImplicitAssociation()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionLabel(java.lang.String,
     *      org.eclipse.core.resources.IMarker)
     * 
     * @param propertiesLabel
     * @param marker
     * @return
     */
    @Override
    protected String getResolutionLabel(String propertiesLabel, IMarker marker) {
        String signalName = null;
        try {
            EObject target = getTarget(marker);
            if (target instanceof Activity) {
                signalName = EventObjectUtil.getSignalName((Activity) target);
            }

        } catch (CoreException e) {
        }

        if (signalName == null || signalName.isEmpty()) {
            signalName = "???"; //$NON-NLS-1$
        }

        return String.format(propertiesLabel, signalName);
    }

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionDescription(java.lang.String,
     *      org.eclipse.core.resources.IMarker)
     * 
     * @param propertiesDescription
     * @param marker
     * @return
     */
    @Override
    protected String getResolutionDescription(String propertiesDescription,
            IMarker marker) {
        return getResolutionLabel(propertiesDescription, marker);
    }
}
