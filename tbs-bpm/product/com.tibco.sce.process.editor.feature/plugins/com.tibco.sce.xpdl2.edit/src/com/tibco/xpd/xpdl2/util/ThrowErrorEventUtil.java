/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.xpdl2.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.internal.Messages;

/**
 * ThrowErrorEventUtil
 * <p>
 * Helper methods for Throw Error End Event configuration.
 * 
 * @author aallway
 * @since 3.3 (24 Nov 2009)
 */
public class ThrowErrorEventUtil {

    /**
     * @param activity
     * 
     * @return true if activity is a throw error end event that is configured to
     *         throw a process /sub-process error.
     */
    public static boolean isThrowProcessErrorEvent(Activity activity) {
        ResultError resultError = getResultError(activity);
        if (resultError != null) {
            String requestActivityId =
                    (String) Xpdl2ModelUtil.getOtherAttribute(resultError,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_RequestActivityId());
            if (requestActivityId == null) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param activity
     * 
     * @return true if activity is a throw error end event that is configured to
     *         throw a fault message (<b>regardless of whether that
     *         configuration is valid or not!</b>).
     */
    public static boolean isThrowFaultMessageErrorEvent(Activity activity) {
        ResultError resultError = getResultError(activity);
        if (resultError != null) {
            String requestActivityId =
                    (String) Xpdl2ModelUtil.getOtherAttribute(resultError,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_RequestActivityId());
            if (requestActivityId != null) {
                return true;
            }
        }

        return false;
    }

    /**
     * Return true if the given throw error end event is one for which data
     * mappings should be generated automatically. This will be so for any throw
     * error event that is configured to throw error for an incoming request
     * activity that is having its operation generated.
     * <p>
     * This is <b>except</b> for a non-implementing throw error event that is
     * configured for an interface implementing request activity. In this
     * circumstance, even though the request activity operation is
     * auto-generated it is 'outside of process' and hence the user must map to
     * it as normal).
     * 
     * @param activity
     * @return true if the given throw error end event is one for which data
     *         mappings should be generated automatically.
     */
    public static boolean shouldGenerateMappingsForErrorEvent(Activity activity) {
        if (ThrowErrorEventUtil.isThrowFaultMessageErrorEvent(activity)) {
            Activity requestActivity =
                    ThrowErrorEventUtil.getRequestActivity(activity);
            if (requestActivity != null
                    && Xpdl2ModelUtil
                            .isGeneratedRequestActivity(requestActivity)) {
                /*
                 * It's a throw fault message error event and the referenced
                 * requestActivity has generated operation.
                 * 
                 * Should generate data mappings UNLESS the request activity is
                 * implemented and the error activity is not.
                 */
                if (!Xpdl2ModelUtil.isEventImplemented(requestActivity)) {
                    /*
                     * Request activity doesn't implement interface activity so
                     * the throw error event is defining a fault message payload
                     * from its assoc params - therefore we should generated
                     * datamappings for it.
                     */
                    return true;

                } else {
                    /*
                     * Request activity is implementing interface event, so only
                     * generate data mappings if the error event is also one
                     * created to implement an interface error for the
                     * operation.
                     * 
                     * Otherwise it's an extra throw error created by user and
                     * hence the user will have to map data manually.
                     */
                    if (Xpdl2ModelUtil.isEventImplemented(activity)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param requestActivity
     * 
     * @return A list of all the throw error event activities configured to
     *         throw error for given incoming message request activity.
     */
    public static List<Activity> getThrowErrorEvents(Activity requestActivity) {
        String lookForId = requestActivity.getId();

        Process process = requestActivity.getProcess();

        return getThrowErrorEvents(process, lookForId);
    }

    /**
     * @param process
     * @param requestActivityId
     * 
     * @return A list of all the throw error event activities configured to
     *         throw error for given incoming message request activity.
     */
    public static List<Activity> getThrowErrorEvents(Process process,
            String requestActivityId) {
        ArrayList<Activity> throwErrorActivities = new ArrayList<Activity>();

        if (process != null && requestActivityId != null) {
            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);

            for (Iterator iterator = activities.iterator(); iterator.hasNext();) {
                Activity act = (Activity) iterator.next();

                /*
                 * Get the request act id (if activity is configured as a throw
                 * error for an incoming message activity)
                 */

                String requestActId = getRequestActivityId(act);

                if (requestActId != null
                        && requestActId.equals(requestActivityId)) {
                    throwErrorActivities.add(act);
                }
            }
        }

        return throwErrorActivities;
    }

    /**
     * @param throwFaultErrorEvent
     * @return The xpdl2:ResultError/ErrorCode value if set else null.
     */
    public static String getThrowErrorCode(Activity throwFaultErrorEvent) {
        ResultError resultError = getResultError(throwFaultErrorEvent);
        if (resultError != null) {
            return resultError.getErrorCode();
        }

        return null;
    }

    /**
     * Get the FaultName for a throw error event configured to throw fault
     * message.
     * 
     * @param throwFaultErrorEvent
     * 
     * @return name or null if not found.
     */
    public static String getThrowErrorFaultName(Activity throwFaultErrorEvent) {
        Message faultMessage = getThrowErrorFaultMessage(throwFaultErrorEvent);

        if (faultMessage != null) {
            return faultMessage.getFaultName();
        }

        return null;
    }

    /**
     * Get the xpdExt:FaultMessage for a throw error event configured to throw
     * fault message.
     * 
     * @param throwFaultErrorEvent
     * 
     * @return xpdExt:FaultMessage or null if not found.
     */
    public static Message getThrowErrorFaultMessage(
            Activity throwFaultErrorEvent) {
        ResultError resultError = getResultError(throwFaultErrorEvent);
        if (resultError != null) {
            return (Message) Xpdl2ModelUtil.getOtherElement(resultError,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_FaultMessage());
        }

        return null;
    }

    /**
     * @param editingDomain
     * @param throwErrorEvent
     * 
     * @return The command to configure the throw error end event to throw a
     *         process/sub-process error.
     */
    public static Command getConfigureAsProcessErrorCommand(
            EditingDomain editingDomain, Activity throwErrorEvent,
            String errorCode) {
        ResultError resultError = getResultError(throwErrorEvent);
        if (resultError != null) {
            /*
             * To configure to throw process error all we have to do is remove
             * the xpdExt:RequestActivityId and the xpdExt:FaultMessage (if
             * present).
             */
            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.ThrowErrorEventUtil_SetThrowProcessError_menu);

            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            resultError,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_RequestActivityId(),
                            null));

            Message faultMessage =
                    (Message) Xpdl2ModelUtil.getOtherElement(resultError,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_FaultMessage());
            if (faultMessage != null) {
                cmd.append(Xpdl2ModelUtil
                        .getRemoveOtherElementCommand(editingDomain,
                                resultError,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_FaultMessage(),
                                faultMessage));
            }

            if (errorCode != null) {
                errorCode = errorCode.trim();
            }

            cmd.append(SetCommand.create(editingDomain,
                    resultError,
                    Xpdl2Package.eINSTANCE.getResultError_ErrorCode(),
                    errorCode));

            return cmd;
        }

        return UnexecutableCommand.INSTANCE;
    }

    /**
     * 
     * @param editingDomain
     * @param throwErrorEvent
     * @param requestActivityId
     *            can be null
     * @param errorCode
     *            can be null.
     * 
     * @return The command to configure the throw error end event to throw a
     *         fault message.
     */
    public static Command getConfigureAsFaultMessageErrorCommand(
            EditingDomain editingDomain, Activity throwErrorEvent,
            String requestActivityId, String errorCode) {

        ResultError resultError = getResultError(throwErrorEvent);
        if (resultError != null) {
            if (errorCode != null) {
                errorCode = errorCode.trim();
            }

            /*
             * To do initial configure to throw fault message add the request
             * activity id and create a new fault message..
             */
            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.ThrowErrorEventUtil_SetThrowFaultMessage_menu);

            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            resultError,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_RequestActivityId(),
                            requestActivityId != null ? requestActivityId : "")); //$NON-NLS-1$

            /*
             * Keep error code and fault name in synch.
             */
            cmd.append(SetCommand.create(editingDomain,
                    resultError,
                    Xpdl2Package.eINSTANCE.getResultError_ErrorCode(),
                    errorCode));

            Message faultMessage = createBaseFaultMessage(errorCode);

            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                    resultError,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_FaultMessage(),
                    faultMessage));

            return cmd;
        }

        return UnexecutableCommand.INSTANCE;
    }

    /**
     * @param editingDomain
     * @param throwFaultErrorEvent
     * @param newFaultMessage
     * 
     * @return command to reset the fault message element for the given throw
     *         error end event configured to throw fault for incoming request
     *         activity.
     */
    public static Command getResetFaultMessageCommand(
            EditingDomain editingDomain, Activity throwFaultErrorEvent,
            Message newFaultMessage) {
        ResultError resultError = getResultError(throwFaultErrorEvent);
        if (resultError != null) {
            Command cmd =
                    Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                            resultError,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_FaultMessage(),
                            newFaultMessage);
            return cmd;
        }
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * @param errorCode
     * 
     * @return Create the base message element for an
     *         xpdl2:ResultError/xpdExt:FaultMessage
     */
    public static Message createBaseFaultMessage(String errorCode) {
        /*
         * Always reset the fault message element by recreating it, this will
         * ensure that all the old data mappings get ditched.
         */
        Message faultMessage = Xpdl2Factory.eINSTANCE.createMessage();
        if (errorCode != null) {
            /* Set fault name to tokenised version of error code. */
            String faultName = getFaultNameForErrorCode(errorCode);
            faultMessage.setFaultName(faultName);
        }
        return faultMessage;
    }

    /**
     * @param editingDomain
     * @param throwErrorEvent
     * @param errorCode
     * 
     * @return Command to set the error code.
     */
    public static Command getSetErrorCodeCommand(EditingDomain editingDomain,
            Activity throwErrorEvent, String errorCode) {
        ResultError resultError = getResultError(throwErrorEvent);
        if (resultError != null) {
            if (errorCode != null) {
                errorCode = errorCode.trim();
            }

            if (isThrowProcessErrorEvent(throwErrorEvent)) {
                /*
                 * For internal sub-process invocation errors just set the code.
                 */
                return SetCommand.create(editingDomain,
                        resultError,
                        Xpdl2Package.eINSTANCE.getResultError_ErrorCode(),
                        errorCode);

            } else if (isThrowFaultMessageErrorEvent(throwErrorEvent)) {
                /*
                 * Setting error code should only be done for throw error for
                 * request activity with auto-generated operation BECAUSE the
                 * generated faulty message name is derived from the user
                 * entered error code.
                 * 
                 * For request activity with user-defined operation then the
                 * error code is derived from the fault message name and hence
                 * you should use getConfigureAsFaultMessageErrorCommand().
                 */
                Activity requestActivity = getRequestActivity(throwErrorEvent);
                if (requestActivity != null) {
                    if (Xpdl2ModelUtil
                            .isGeneratedRequestActivity(requestActivity)) {

                        CompoundCommand cmd = new CompoundCommand();

                        cmd.append(SetCommand.create(editingDomain,
                                resultError,
                                Xpdl2Package.eINSTANCE
                                        .getResultError_ErrorCode(),
                                errorCode));

                        /*
                         * Set fault name to tokenised version of error code.
                         */
                        Message faultMessage =
                                getThrowErrorFaultMessage(throwErrorEvent);
                        if (faultMessage != null) {
                            String faultName =
                                    getFaultNameForErrorCode(errorCode);

                            cmd.append(SetCommand.create(editingDomain,
                                    faultMessage,
                                    Xpdl2Package.eINSTANCE
                                            .getMessage_FaultName(),
                                    faultName));
                        }

                        /*
                         * Only do the synch if the error event has not already
                         * got explicit associations, if it has then it's better
                         * for the user experience to let them deal with the
                         * validation problem that should be raised if they're
                         * wrong.
                         */
                        EList associatedParameters =
                                LocalPackageProcessInterfaceUtil
                                        .getActivityAssociatedParameters(throwErrorEvent);
                        if (associatedParameters == null
                                || associatedParameters.isEmpty()) {
                            Command synchCmd =
                                    getSynchWithExistingAutoGenErrorCommand(editingDomain,
                                            throwErrorEvent,
                                            errorCode);
                            if (synchCmd != null) {
                                cmd.append(synchCmd);
                            }
                        }
                        return cmd;
                    }
                }
            }
        }

        return UnexecutableCommand.INSTANCE;
    }

    /**
     * If this event is one that is going to generate a fault message and we
     * defining a nth error event for the same request activity with the SAME
     * error code then the associated parameters HAVE to be the same (we will
     * generate only one fault per error of a given name). (There will also be a
     * validation rule to check this).
     * 
     * @param ed
     * @param throwErrorActivity
     * @param newErrorCode
     * 
     * @return Command to synch the given activity's assoc params with the first
     *         existing error for same request and error code or NULL if
     *         unnecessary.
     */
    private static Command getSynchWithExistingAutoGenErrorCommand(
            EditingDomain ed, Activity throwErrorActivity, String newErrorCode) {
        String newFaultName =
                ThrowErrorEventUtil.getFaultNameForErrorCode(newErrorCode);
        if (newFaultName != null && newFaultName.length() > 0) {
            if (ThrowErrorEventUtil
                    .isThrowFaultMessageErrorEvent(throwErrorActivity)) {
                Activity requestActivity =
                        ThrowErrorEventUtil
                                .getRequestActivity(throwErrorActivity);
                if (requestActivity != null
                        && Xpdl2ModelUtil
                                .isGeneratedRequestActivity(requestActivity)) {
                    if (!Xpdl2ModelUtil.isEventImplemented(requestActivity)) {
                        /*
                         * Input is a throw error event for request activity
                         * that is generating a WSDL operation so the fault
                         * message will be generated too
                         */
                        Process process = throwErrorActivity.getProcess();
                        if (process != null) {
                            /*
                             * Find the first existing throw error for same
                             * request with same error code.
                             */
                            Activity errorEventForSameRequestAndCode = null;
                            for (Activity siblingActivity : Xpdl2ModelUtil
                                    .getAllActivitiesInProc(process)) {
                                if (!throwErrorActivity.equals(siblingActivity)) {
                                    String requestActivityId =
                                            ThrowErrorEventUtil
                                                    .getRequestActivityId(siblingActivity);
                                    if (requestActivityId != null
                                            && requestActivityId
                                                    .equals(requestActivity
                                                            .getId())) {
                                        /*
                                         * Found existing error for same
                                         * request.
                                         */
                                        Message faultMessage =
                                                ThrowErrorEventUtil
                                                        .getThrowErrorFaultMessage(siblingActivity);
                                        if (faultMessage != null
                                                && newFaultName
                                                        .equals(faultMessage
                                                                .getFaultName())) {
                                            /*
                                             * Found existing error for same
                                             * event with same fault name.
                                             */
                                            errorEventForSameRequestAndCode =
                                                    siblingActivity;
                                        }
                                    }
                                }
                            }

                            if (errorEventForSameRequestAndCode != null) {
                                CompoundCommand synchParamsCmd =
                                        new CompoundCommand();

                                /*
                                 * Update our activity's associated params to
                                 * match the existing error event with same
                                 * error code for same request
                                 */
                                AssociatedParameters existingEventAssocParams =
                                        (AssociatedParameters) Xpdl2ModelUtil
                                                .getOtherElement(errorEventForSameRequestAndCode,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_AssociatedParameters());
                                if (existingEventAssocParams != null) {
                                    /*
                                     * Copy to match other event's assoc params.
                                     */
                                    EObject copyAssocParam =
                                            EcoreUtil
                                                    .copy(existingEventAssocParams);
                                    synchParamsCmd
                                            .append(Xpdl2ModelUtil
                                                    .getSetOtherElementCommand(ed,
                                                            throwErrorActivity,
                                                            XpdExtensionPackage.eINSTANCE
                                                                    .getDocumentRoot_AssociatedParameters(),
                                                            copyAssocParam));
                                } else {
                                    /* Remove our event's assoc params. */
                                    AssociatedParameters thisEventAssocParams =
                                            (AssociatedParameters) Xpdl2ModelUtil
                                                    .getOtherElement(throwErrorActivity,
                                                            XpdExtensionPackage.eINSTANCE
                                                                    .getDocumentRoot_AssociatedParameters());
                                    if (thisEventAssocParams != null) {
                                        synchParamsCmd
                                                .append(Xpdl2ModelUtil
                                                        .getRemoveOtherElementCommand(ed,
                                                                throwErrorActivity,
                                                                XpdExtensionPackage.eINSTANCE
                                                                        .getDocumentRoot_AssociatedParameters(),
                                                                thisEventAssocParams));
                                    }
                                }

                                if (!synchParamsCmd.isEmpty()) {
                                    return synchParamsCmd;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param errorCode
     * @return fault name for given error code
     */
    public static String getFaultNameForErrorCode(String errorCode) {
        if (errorCode != null) {
            return NameUtil.getInternalName(errorCode, false);
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * @param throwFaultErrorEvent
     * 
     * @return If the given activity is a throw error end event configured to
     *         throw a fault message return the referenced incoming request
     *         activity else <code>null</code>
     */
    public static Activity getRequestActivity(Activity throwFaultErrorEvent) {
        String requestActivityId = getRequestActivityId(throwFaultErrorEvent);
        if (requestActivityId != null && requestActivityId.length() > 0) {

            Process process = throwFaultErrorEvent.getProcess();
            if (process != null) {
                return Xpdl2ModelUtil.getActivityById(process,
                        requestActivityId);
            }
        }

        return null;
    }

    /**
     * @param throwFaultErrorEvent
     * 
     * @return If the given activity is a throw error end event configured to
     *         throw a fault message return the referenced incoming request
     *         activity else <code>null</code>
     */
    public static String getRequestActivityId(Activity throwFaultErrorEvent) {
        ResultError resultError = getResultError(throwFaultErrorEvent);
        if (resultError != null) {
            return (String) Xpdl2ModelUtil.getOtherAttribute(resultError,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_RequestActivityId());
        }

        return null;
    }

    /**
     * @param activity
     * @return The xpdl2:ResultError element if the activity is a throw error
     *         end event or null if not
     */
    public static ResultError getResultError(Activity activity) {
        if (activity.getEvent() instanceof EndEvent) {
            return ((EndEvent) activity.getEvent()).getResultError();
        }
        return null;
    }

}
