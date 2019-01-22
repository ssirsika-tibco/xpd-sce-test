/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.errorEvents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.CatchErrorCodePickerDialog;
import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.ErrorThrowerInfo;
import com.tibco.xpd.xpdExtension.ErrorThrowerType;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utilities for discovery of catchable errors for bpmn error events. And
 * various utilities surrounding the catching of specific errors thrown by
 * specific activities.
 * <p>
 * Also some utilities for accessing extension parts of the model for catching
 * error events.
 * 
 * @author aallway
 * @since 3.2
 */
public class BpmnCatchableErrorUtil {

    public enum ErrorCatchType {
        /** Catch any error from any error thrower */
        CATCH_ALL,
        /** Catch error of given name from any thrower */
        CATCH_BY_NAME,
        /** Catch specific error thrown by specific thrower. */
        CATCH_SPECIFIC
    }

    /**
     * Get list of errors potentially thrown by the given activity.
     * 
     * @param errorThrowerActivity
     *            An activity that might throw an error (either directly or via
     *            a sub-process.
     * 
     * @return List of errors/folders of errors or empty list.
     */
    public static Collection<IBpmnCatchableErrorTreeItem> getCatchableErrors(
            Activity errorThrowerActivity) {

        // Just in case caller passes the attached error event.
        if (errorThrowerActivity.getEvent() instanceof IntermediateEvent
                && errorThrowerActivity.getEvent().getEventTriggerTypeNode() instanceof ResultError) {
            errorThrowerActivity = getAttachedToTask(errorThrowerActivity);
        }

        List<IBpmnCatchableErrorTreeItem> catchableErrorItems =
                new ArrayList<IBpmnCatchableErrorTreeItem>();

        if (errorThrowerActivity != null) {
            //
            // Get a list of potential catchable error providers for this
            // task we're attached to.
            //
            Collection<IBpmnCatchableErrorsContributor> errorProviders =
                    BpmnCatchableErrorExtPointHelper.INSTANCE
                            .getApplicableErrorBrowserContributions(errorThrowerActivity);
            if (errorProviders.size() > 0) {
                for (IBpmnCatchableErrorsContributor errorProvider : errorProviders) {
                    //
                    // Get the list of errors / error folders from each.
                    //
                    Collection<IBpmnCatchableErrorTreeItem> items =
                            errorProvider
                                    .getCatchableErrorTreeItems(errorThrowerActivity);
                    if (items != null && !items.isEmpty()) {
                        catchableErrorItems.addAll(items);
                    }
                }
            }
        }
        return catchableErrorItems;
    }

    /**
     * Get list of errors potentially thrown by the task that the given catch
     * error event is attached to AS A FLAT LIST (i.e. children are moved up
     * from their parent folders and folders are discarded).
     * <p>
     * <b>NOTE that the returned error's reference to parent folder is still
     * maintained (therefore you may still to catchableError.getParentFolder().
     * 
     * @param errorThrowerActivity
     *            An activity that might throw an error (either directly or via
     *            a sub-process.
     * 
     * @return List of errors/folders of errors or empty list.
     */
    public static Collection<IBpmnCatchableErrorTreeItem> getCatchableErrorsFlatList(
            Activity errorThrowerActivity) {
        //
        // Get the list in tree form.
        //
        Collection<IBpmnCatchableErrorTreeItem> catchableErrorTreeItems =
                getCatchableErrors(errorThrowerActivity);

        //
        // Then flatten it.
        //
        List<IBpmnCatchableErrorTreeItem> flatList =
                new ArrayList<IBpmnCatchableErrorTreeItem>();

        flattenCatchableErrorTree(catchableErrorTreeItems, flatList);

        return flatList;
    }

    /**
     * Locate the catchable error for the given catch error event.
     * 
     * @param catchErrorEvent
     * 
     * @return The BpmnCatchableError if the error referenced by the given error
     *         catch event is thrown by the task (or any sub-tasks of) that the
     *         event is attached to <b>or null if cannot be found</b> (no error
     *         set or thrower no longer throws the error or thrower no longer
     *         exists)
     */
    public static BpmnCatchableError locateCatchableErrorExact(
            Activity catchErrorEvent) {
        BpmnCatchableError catchableError = null;

        if (catchErrorEvent.getEvent() instanceof IntermediateEvent
                && catchErrorEvent.getEvent().getEventTriggerTypeNode() instanceof ResultError) {
            ResultError resError =
                    (ResultError) catchErrorEvent.getEvent()
                            .getEventTriggerTypeNode();
            String errorCode = resError.getErrorCode();
            if (errorCode != null && errorCode.length() > 0) {
                Activity attachedToTask = getAttachedToTask(catchErrorEvent);
                if (attachedToTask != null) {
                    ErrorThrowerInfo errorThrowerInfo =
                            (ErrorThrowerInfo) Xpdl2ModelUtil
                                    .getOtherElement(resError,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ErrorThrowerInfo());
                    if (errorThrowerInfo != null) {
                        String throwerId = errorThrowerInfo.getThrowerId();
                        String throwerContainerId =
                                errorThrowerInfo.getThrowerContainerId();

                        catchableError =
                                locateCatchableErrorExact(getCatchableErrors(attachedToTask),
                                        errorCode,
                                        throwerId,
                                        throwerContainerId);
                    }
                }
            }
        }

        return catchableError;
    }

    /**
     * Locate the catchable error for the given catch error event.
     * 
     * <p>
     * <b>If an exact match cannot be found then the closest match will be
     * returned.</b> The order of preference being that if the error thrower
     * still exists in the tree of errors then it's error will be returned
     * otherwise if an error by any thrower exists with the same errorCode then
     * that will be returned.</b>
     * 
     * @param catchErrorEvent
     * 
     * @return The closest matching BpmnCatchableError thrown by the task (or
     *         any sub-tasks of) that the event is attached to <b>or null if
     *         cannot be found</b> (no error set or thrower no longer throws the
     *         error or thrower no longer exists)
     */
    public static BpmnCatchableError locateCatchableErrorClosestMatch(
            Activity catchErrorEvent) {
        BpmnCatchableError catchableError = null;

        if (catchErrorEvent.getEvent() instanceof IntermediateEvent
                && catchErrorEvent.getEvent().getEventTriggerTypeNode() instanceof ResultError) {
            ResultError resError =
                    (ResultError) catchErrorEvent.getEvent()
                            .getEventTriggerTypeNode();
            String errorCode = resError.getErrorCode();
            if (errorCode != null && errorCode.length() > 0) {
                Activity attachedToTask = getAttachedToTask(catchErrorEvent);
                if (attachedToTask != null) {
                    ErrorThrowerInfo errorThrowerInfo =
                            (ErrorThrowerInfo) Xpdl2ModelUtil
                                    .getOtherElement(resError,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ErrorThrowerInfo());
                    if (errorThrowerInfo != null) {
                        String throwerId = errorThrowerInfo.getThrowerId();
                        String throwerContainerId =
                                errorThrowerInfo.getThrowerContainerId();

                        catchableError =
                                locateCatchableErrorClosestMatch(getCatchableErrors(attachedToTask),
                                        errorCode,
                                        throwerId,
                                        throwerContainerId);
                    }
                }
            }
        }

        return catchableError;
    }

    /**
     * Look in the given list of catchable errors/error folders and locate a
     * catchable error with the same id, from the same activity in the same
     * process.
     * <p>
     * This is faster that locateCatchableError(Activity errorEvent) if you have
     * already retrieved the catchableErrorTree for other purposes.
     * 
     * @param catchableErrorTree
     *            List of catchable errors / folders as returned by
     *            getCatchableErrors().
     * @param errorCode
     *            Error id to find catchable error for
     * @param throwerId
     *            The id of the activity that throws the given error
     * @param throwerContainerId
     *            The id of the process for the activity that throws the error.
     * 
     * @return The BpmnCatchableError that matches the given criteria or null if
     *         cannot be found in the reachable errors for the given error
     *         event.
     */
    public static BpmnCatchableError locateCatchableErrorExact(
            Collection<IBpmnCatchableErrorTreeItem> catchableErrorTree,
            String errorCode, String throwerId, String throwerContainerId) {

        return internalLocateCatchableError(catchableErrorTree,
                errorCode,
                throwerId,
                throwerContainerId,
                false,
                null);
    }

    /**
     * Look in the given list of catchable errors/error folders and locate a
     * catchable error with the same id, from the same activity in the same
     * process.
     * <p>
     * <b>If an exact match cannot be found then the closest match will be
     * returned.</b> The order of preference being that if the error thrower
     * still exists in the tree of errors then it's error will be returned
     * otherwise if an error by any thrower exists with the same errorCode then
     * that will be returned.</b>
     * <p>
     * The reason for this is that it is seen as a 'lesser' change if the
     * original error thrower still exists but the error code has bee
     * relabelled.
     * 
     * 
     * @param catchableErrorTree
     *            List of catchable errors / folders as returned by
     *            getCatchableErrors().
     * @param errorCode
     *            Error id to find catchable error for
     * @param throwerId
     *            The id of the activity that throws the given error
     * @param throwerContainerId
     *            The id of the process for the activity that throws the error.
     * 
     * @return The BpmnCatchableError that most closely matches the given
     *         criteria or null if cannot be found.
     */
    public static BpmnCatchableError locateCatchableErrorClosestMatch(
            Collection<IBpmnCatchableErrorTreeItem> catchableErrorTree,
            String errorCode, String throwerId, String throwerContainerId) {
        return internalLocateCatchableError(catchableErrorTree,
                errorCode,
                throwerId,
                throwerContainerId,
                true,
                null);
    }

    /**
     * Get the error catch type of the given catchy error event.
     * <p>
     * Unlike {@link #getCatchType(Activity)} this will return <code>null</code>
     * if the activity is not a catch error event (rather than catch all).
     * 
     * @param catchErrorEvent
     * @return <li>If an errorCode AND the extension element is present then
     *         {@link ErrorCatchType#CATCH_SPECIFIC} </li> <li>Else If just an
     *         errorCode is present then {@link ErrorCatchType#CATCH_BY_NAME}
     *         </li> <li>
     *         Otherwise {@link ErrorCatchType#CATCH_ALL} </li> <li>Otherwise if
     *         NOT a catch event then returns <code>null</code>
     */
    public static ErrorCatchType getCatchTypeStrict(Activity catchErrorEvent) {
        if (catchErrorEvent.getEvent() instanceof IntermediateEvent
                && catchErrorEvent.getEvent().getEventTriggerTypeNode() instanceof ResultError) {
            ResultError resError =
                    (ResultError) catchErrorEvent.getEvent()
                            .getEventTriggerTypeNode();

            if (resError.getErrorCode() != null
                    && resError.getErrorCode().length() > 0) {

                ErrorThrowerInfo errorThrowerInfo =
                        (ErrorThrowerInfo) Xpdl2ModelUtil
                                .getOtherElement(resError,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ErrorThrowerInfo());

                if (errorThrowerInfo != null) {
                    return ErrorCatchType.CATCH_SPECIFIC;
                } else {
                    return ErrorCatchType.CATCH_BY_NAME;
                }
            }

            /* No specific error caught */
            return ErrorCatchType.CATCH_ALL;
        }

        /* Not a catch error! */
        return null;
    }

    /**
     * Get the error catch type of the given catchy error event.
     * <p>
     * 
     * @param catchErrorEvent
     * @return <li>If an errorCode AND the extension element is present then
     *         {@link ErrorCatchType#CATCH_SPECIFIC} </li> <li>Else If just an
     *         errorCode is present then {@link ErrorCatchType#CATCH_BY_NAME}
     *         </li> <li>
     *         Otherwise {@link ErrorCatchType#CATCH_BY_NAME} </li>
     * 
     * @deprecated This method is dangerous because it will return CATCH_ALL
     *             even if the passed activity is not a catch error event at
     *             all. Use {@link #getCatchTypeStrict(Activity)} instead
     *             because it distinguishes this.
     */
    @Deprecated
    public static ErrorCatchType getCatchType(Activity catchErrorEvent) {

        ErrorCatchType catchTypeStrict = getCatchTypeStrict(catchErrorEvent);
        if (catchTypeStrict != null) {
            return catchTypeStrict;
        }

        return ErrorCatchType.CATCH_ALL;
    }

    /**
     * Get the task that the given errorActivity is attached to.
     * 
     * @param catchErrorEvent
     *            Error event to get attached task for.
     * 
     * @return Activity of task that event is attached to or null if not
     *         attached or no error event.
     */
    public static Activity getAttachedToTask(Activity catchErrorEvent) {
        if (catchErrorEvent != null
                && catchErrorEvent.getEvent() instanceof IntermediateEvent) {
            String target =
                    ((IntermediateEvent) catchErrorEvent.getEvent())
                            .getTarget();
            if (target != null && target.length() > 0) {
                Activity task =
                        catchErrorEvent.getFlowContainer().getActivity(target);
                if (task != null) {
                    return task;
                }
            }
        }
        return null;
    }

    /**
     * Display a picker dialog for errors thrown by given activity and return a
     * command to select the error.
     * 
     * @param parentShell
     * @param editingDomain
     * @param catchErrorEvent
     * 
     * @return Command to set the error code (null if user cancels etc).
     */
    public static Command selectErrorCode(Shell parentShell,
            EditingDomain editingDomain, Activity catchErrorEvent) {

        if (catchErrorEvent.getEvent() instanceof IntermediateEvent) {

            CatchErrorCodePickerDialog dlg =
                    new CatchErrorCodePickerDialog(parentShell, catchErrorEvent);

            if (dlg.open() == CatchErrorCodePickerDialog.OK
                    && dlg.getCatchableErrorResult() != null) {

                BpmnCatchableError catchableError =
                        dlg.getCatchableErrorResult();

                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.BpmnCatchableErrorUtil_SetErrorToCatch_menu);

                EObject trigTypeNode =
                        catchErrorEvent.getEvent().getEventTriggerTypeNode();
                if (trigTypeNode != null) {
                    cmd.append(RemoveCommand
                            .create(editingDomain, trigTypeNode));
                }

                ResultError resError =
                        Xpdl2Factory.eINSTANCE.createResultError();

                // Set the XPDL ErrorCode to the human readable form of the
                // error code.
                resError.setErrorCode(catchableError.getErrorCode());

                //
                // Create and add the extended catch error info.
                // BUT ONLY if we're not selecting unspecific error by name only
                // (in which case we only need the error code label
                //
                if (!dlg.isSelectUnspecificErrorByName()) {
                    ErrorThrowerInfo errorThrowerInfo =
                            XpdExtensionFactory.eINSTANCE
                                    .createErrorThrowerInfo();
                    errorThrowerInfo.setThrowerType(catchableError
                            .getErrorThrowerType());
                    errorThrowerInfo.setThrowerId(catchableError
                            .getErrorThrowerId());
                    errorThrowerInfo.setThrowerContainerId(catchableError
                            .getErrorThrowerContainerId());

                    Xpdl2ModelUtil.setOtherElement(resError,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ErrorThrowerInfo(),
                            errorThrowerInfo);

                    //
                    // Also always create a catch error mappings container
                    // up-front (mainly for the benefit of the mappers.
                    //
                    CatchErrorMappings catchErrorMappings =
                            XpdExtensionFactory.eINSTANCE
                                    .createCatchErrorMappings();
                    catchErrorMappings.setMessage(Xpdl2Factory.eINSTANCE
                            .createMessage());

                    Xpdl2ModelUtil.setOtherElement(resError,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_CatchErrorMappings(),
                            catchErrorMappings);
                }

                cmd.append(SetCommand.create(editingDomain, catchErrorEvent
                        .getEvent(), Xpdl2Package.eINSTANCE
                        .getIntermediateEvent_ResultError(), resError));

                return cmd;
            }
        }

        return null;
    }

    /**
     * Get Command to set the catch error activity to 'catch all'
     * 
     * @param catchErrorEvent
     * 
     * @return Command to set the catch error activity to 'catch all'
     */
    public static Command createSetCatchAllCommand(EditingDomain editingDomain,
            Activity catchErrorEvent) {
        if (catchErrorEvent.getEvent() instanceof IntermediateEvent) {
            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.BpmnCatchableErrorUtil_SetToCatchAll_menu);

            EObject trigTypeNode =
                    catchErrorEvent.getEvent().getEventTriggerTypeNode();
            if (trigTypeNode != null) {
                cmd.append(RemoveCommand.create(editingDomain, trigTypeNode));
            }

            // Catch all = a ResultError element with no error code (and no
            // xpdExt:CatchErrorInfo element)
            ResultError resError = Xpdl2Factory.eINSTANCE.createResultError();
            cmd.append(SetCommand.create(editingDomain,
                    catchErrorEvent.getEvent(),
                    Xpdl2Package.eINSTANCE.getIntermediateEvent_ResultError(),
                    resError));

            Object scriptObj =
                    Xpdl2ModelUtil.getOtherElement(catchErrorEvent,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script());
            if (scriptObj instanceof ScriptInformation) {
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(editingDomain,
                                catchErrorEvent,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Script(),
                                null));
            }
            return cmd;
        }

        return null;
    }

    /**
     * @param catchabeErrorTreeItem
     * @return the descriptive path up to (but not including) the given
     *         catchable error tree item
     */
    public static String getPathToErrorDescription(
            IBpmnCatchableErrorTreeItem catchabeErrorTreeItem) {
        String errorPath = null;

        if (catchabeErrorTreeItem != null) {
            IBpmnCatchableErrorTreeItem next =
                    catchabeErrorTreeItem.getParentFolder();

            StringBuilder error = new StringBuilder(""); //$NON-NLS-1$

            while (next != null) {
                String label = next.getLabel();
                if (label == null) {
                    label = ""; //$NON-NLS-1$
                }

                if (error.length() != 0) {
                    error.insert(0, " / "); //$NON-NLS-1$
                }
                error.insert(0, label);

                next = next.getParentFolder();
            }

            errorPath = error.toString();
        }

        if (errorPath == null || errorPath.length() == 0) {
            errorPath = "?"; //$NON-NLS-1$
        }

        return errorPath;
    }

    /**
     * Get the extension element of error thrower information for the given
     * catch error event.
     * <p>
     * Only intermediate catch error events that are set up to catch an error
     * thrown by a specific thrower (activity/interface event) will have an
     * ErrorThrowerInfo extension element in the model.
     * 
     * @param catchErrorEvent
     * @return ErrorThrowerInfo or null if activity is not a catch error event
     *         setup to catch specific error from specific thrower
     */
    public static ErrorThrowerInfo getExtendedErrorThrowerInfo(
            Activity catchErrorEvent) {
        ErrorThrowerInfo errorThrowerInfo = null;

        if (catchErrorEvent.getEvent() instanceof IntermediateEvent
                && catchErrorEvent.getEvent().getEventTriggerTypeNode() instanceof ResultError) {
            ResultError resError =
                    (ResultError) catchErrorEvent.getEvent()
                            .getEventTriggerTypeNode();

            errorThrowerInfo =
                    (ErrorThrowerInfo) Xpdl2ModelUtil.getOtherElement(resError,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ErrorThrowerInfo());
        }

        return errorThrowerInfo;
    }

    /**
     * Get the object that throws the error depending on the ErrorThrowerType in
     * the catch error event's ErrorThrowerInfo.
     * 
     * @param catchErrorEvent
     * 
     * @return The object that throws the error or null if it cannot be
     *         ascertained (event does not catch specific or thrower does not
     *         exist).
     */
    public static Object getErrorThrower(Activity catchErrorEvent) {
        ErrorThrowerInfo errorThrowerInfo =
                getExtendedErrorThrowerInfo(catchErrorEvent);

        if (errorThrowerInfo != null) {
            if (ErrorThrowerType.PROCESS_ACTIVITY.equals(errorThrowerInfo
                    .getThrowerType())) {
                //
                // The error is thrown by an activity, look for it.
                //
                String throwingProcessId =
                        errorThrowerInfo.getThrowerContainerId();
                if (throwingProcessId != null && throwingProcessId.length() > 0) {
                    Process throwingProcess =
                            Xpdl2WorkingCopyImpl
                                    .locateProcess(throwingProcessId);

                    if (throwingProcess != null) {
                        String throwingActId = errorThrowerInfo.getThrowerId();
                        if (throwingActId != null && throwingActId.length() > 0) {
                            Collection<Activity> activities =
                                    Xpdl2ModelUtil
                                            .getAllActivitiesInProc(throwingProcess);
                            for (Activity a : activities) {
                                if (throwingActId.equals(a.getId())) {
                                    // Throwing activity found!
                                    return a;
                                }
                            }
                        }
                    }
                }
            } else if (ErrorThrowerType.INTERFACE_EVENT.equals(errorThrowerInfo
                    .getThrowerType())) {
                String throwingInterfaceId =
                        errorThrowerInfo.getThrowerContainerId();
                if (throwingInterfaceId != null
                        && throwingInterfaceId.length() > 0) {
                    ProcessInterface throwingInterface =
                            Xpdl2WorkingCopyImpl
                                    .locateProcessInterface(throwingInterfaceId);

                    if (throwingInterface != null) {
                        String throwingEventId =
                                errorThrowerInfo.getThrowerId();
                        if (throwingEventId != null
                                && throwingEventId.length() > 0) {
                            InterfaceMethod throwingEvent = null;

                            for (StartMethod method : throwingInterface
                                    .getStartMethods()) {
                                if (throwingEventId.equals(method.getId())) {
                                    throwingEvent = method;
                                }
                            }

                            if (throwingEvent == null) {
                                for (IntermediateMethod method : throwingInterface
                                        .getIntermediateMethods()) {
                                    if (throwingEventId.equals(method.getId())) {
                                        throwingEvent = method;
                                    }
                                }
                            }

                            if (throwingEvent != null) {
                                // Throwing interface event found!
                                return throwingEvent;
                            }
                        }
                    }
                }

            } else {
                //
                // Ok, have to do it the hard way, locate the error in all the
                // errors reachable from the ctach error.
                //
                BpmnCatchableError catchableError =
                        locateCatchableErrorExact(catchErrorEvent);
                if (catchableError != null) {
                    return catchableError.getErrorThrower();
                }
            }
        }

        return null;

    }

    /**
     * Get the extension element of catch error mappings for the given catch
     * error event (i.e. mappings from data associated with thrown error and the
     * catch error's proces data).
     * <p>
     * Only intermediate catch error events are expected to contain this
     * element.
     * 
     * @param catchErrorEvent
     * 
     * @return CatchErrorMappings or null if activity has no mappings or is not
     *         a catch error event etc.
     */
    public static CatchErrorMappings getExtendedCatchErrorMappings(
            Activity catchErrorEvent) {
        CatchErrorMappings catchErrorMappings = null;

        if (catchErrorEvent.getEvent() instanceof IntermediateEvent
                && catchErrorEvent.getEvent().getEventTriggerTypeNode() instanceof ResultError) {
            ResultError resError =
                    (ResultError) catchErrorEvent.getEvent()
                            .getEventTriggerTypeNode();

            catchErrorMappings =
                    (CatchErrorMappings) Xpdl2ModelUtil
                            .getOtherElement(resError,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_CatchErrorMappings());
        }

        return catchErrorMappings;
    }

    /**
     * Flatten the tree item list into the given flatlist
     * 
     * @param catchableErrorTreeItems
     * @param flatList
     */
    private static void flattenCatchableErrorTree(
            Collection<IBpmnCatchableErrorTreeItem> catchableErrorTreeItems,
            List<IBpmnCatchableErrorTreeItem> flatList) {

        if (catchableErrorTreeItems != null) {
            for (IBpmnCatchableErrorTreeItem item : catchableErrorTreeItems) {
                if (item instanceof BpmnCatchableError) {
                    flatList.add(item);
                } else if (item instanceof BpmnCatchableErrorFolder) {
                    // recurs for children.
                    Collection<IBpmnCatchableErrorTreeItem> children =
                            item.getChildren();

                    flattenCatchableErrorTree(children, flatList);
                } else {
                    Xpdl2ResourcesPlugin
                            .getDefault()
                            .getLogger()
                            .error("Catchable Error Provider " //$NON-NLS-1$
                                    + " returned item that is not a BpmnCatchableError or BpmnCatchableFolderError (" //$NON-NLS-1$
                                    + item + ")"); //$NON-NLS-1$
                }
            }
        }

        return;
    }

    /**
     * Handle the inner workings of locateCatchableErrorExact() and
     * locateCatchableErrorClosestMatch()
     * 
     * @param catchableErrorTree
     * @param errorCode
     * @param throwerId
     * @param throwerContainerId
     * @param findClosestMatch
     * @param closestMatchSoFar
     *            Used only when looking for closes match (this will be returned
     *            if a closer match is not found.
     * 
     * @return Exact or closes matching error in catchable error tree.
     */
    private static BpmnCatchableError internalLocateCatchableError(
            Collection<IBpmnCatchableErrorTreeItem> catchableErrorTree,
            String errorCode, String throwerId, String throwerContainerId,
            boolean findClosestMatch, BpmnCatchableError closestMatchSoFar) {

        if (errorCode == null) {
            errorCode = ""; //$NON-NLS-1$
        }

        if (throwerId == null) {
            throwerId = ""; //$NON-NLS-1$
        }

        if (throwerContainerId == null) {
            throwerContainerId = ""; //$NON-NLS-1$
        }

        for (IBpmnCatchableErrorTreeItem item : catchableErrorTree) {
            BpmnCatchableError catchableError = null;

            if (item instanceof BpmnCatchableErrorFolder) {
                catchableError =
                        internalLocateCatchableError(((BpmnCatchableErrorFolder) item)
                                .getChildren(),
                                errorCode,
                                throwerId,
                                throwerContainerId,
                                findClosestMatch,
                                closestMatchSoFar);

            } else if (item instanceof BpmnCatchableError) {
                catchableError = (BpmnCatchableError) item;
            }

            if (catchableError != null) {
                //
                // First check for exact match!
                //
                if (errorCode.equals(catchableError.getErrorCode())
                        && throwerId.equals(catchableError.getErrorThrowerId())
                        && throwerContainerId.equals(catchableError
                                .getErrorThrowerContainerId())) {
                    // Exact match - they don't come closer than that!
                    closestMatchSoFar = catchableError;
                    break;

                } else if (findClosestMatch
                        && catchableError != closestMatchSoFar) {
                    //
                    // When matching with closest.
                    // Check if the error is a closer match than the one we
                    // started with.
                    //
                    if (throwerId.equals(catchableError.getErrorThrowerId())
                            && throwerContainerId.equals(catchableError
                                    .getErrorThrowerContainerId())) {
                        // The error we're just checking matches the error
                        // thrower. If the current closest doesn't (i.e. it only
                        // matches the code then the one we're checking is a
                        // closer match (i.e so we end up with first closes
                        // not last closest).
                        if (closestMatchSoFar != null) {
                            if (!throwerId.equals(closestMatchSoFar
                                    .getErrorThrowerId())
                                    || !throwerContainerId
                                            .equals(closestMatchSoFar
                                                    .getErrorThrowerContainerId())) {
                                // new checked one is closer than existing.
                                closestMatchSoFar = catchableError;
                            }
                        } else {
                            // Don't have a closest yet so this must be it.
                            closestMatchSoFar = catchableError;
                        }

                    } else if (errorCode.equals(catchableError.getErrorCode())) {
                        //
                        // Error we're checking matches only by name only
                        // replace closest so far if not yet assigned (i.e so we
                        // end up with first closes not last closest.
                        //
                        if (closestMatchSoFar == null) {
                            closestMatchSoFar = catchableError;
                        }
                    }
                }
            }
        }

        return closestMatchSoFar;
    }

    /**
     * 
     * @param activity
     * @return <code>true</code> if the given activity is catch error event
     *         attached to task that catches a specific sub-process/interface
     *         error event.
     */
    public static boolean isCatchSubProcessErrorEvent(Activity activity) {

        Object errorThrower = getErrorThrower(activity);

        /* Check if thrown by end error event. */
        if (errorThrower instanceof Activity) {
            Activity errorThrowerActivity = (Activity) errorThrower;

            if (errorThrowerActivity.getEvent() instanceof EndEvent
                    && errorThrowerActivity.getEvent()
                            .getEventTriggerTypeNode() instanceof ResultError) {

                /*
                 * Error end events for fault messages are not sub-process error
                 * events.
                 */
                if (!ThrowErrorEventUtil
                        .isThrowFaultMessageErrorEvent(errorThrowerActivity)) {
                    return true;
                }
            }
        }
        /* Check if thrown by Process interface event. */
        else if (errorThrower instanceof InterfaceMethod) {
            return true;
        }

        return false;
    }

    /**
     * @param activity
     * @return <code>true</code> if the given activity is Global Data Operation
     *         error event
     */
    public static boolean isCatchGlobalDataErrorEvent(Activity activity) {
        if (activity.getEvent() instanceof IntermediateEvent) {
            IntermediateEvent event = (IntermediateEvent) activity.getEvent();

            if (TriggerType.ERROR_LITERAL.equals(event.getTrigger())) {

                ErrorCatchType catchType =
                        BpmnCatchableErrorUtil.getCatchTypeStrict(activity);

                /*
                 * Check that this is a specific error for a Global Data task.
                 */
                if (catchType == ErrorCatchType.CATCH_SPECIFIC) {
                    Object thrower = getErrorThrower(activity);
                    if (thrower instanceof Activity) {
                        Implementation implementation =
                                ((Activity) thrower).getImplementation();

                        if (implementation instanceof Task) {
                            TaskService taskService =
                                    ((Task) implementation).getTaskService();
                            if (taskService != null) {
                                GlobalDataOperation globalDataOp =
                                        (GlobalDataOperation) Xpdl2ModelUtil
                                                .getOtherElement(taskService,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_GlobalDataOperation());

                                return globalDataOp != null;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
