/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rest.ui.RestServicesUtil;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.Service;
import com.tibco.xpd.rsd.wc.RsdWorkingCopy;
import com.tibco.xpd.xpdExtension.RestServiceOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Util for activities that invoke REST services.
 * 
 * @author aallway
 * @since 11 Jun 2015
 */
public class RestServiceTaskUtil {

    /**
     * Check whether the given activity is a REST service invoke or not.
     * 
     * @param activity
     * @return <code>true</code> if the activity invokes a REST service.
     */
    public static boolean isRESTServiceActivity(Activity activity) {
        if (activity.getImplementation() instanceof Task) {
            String id =
                    TaskObjectUtil.getTaskImplementationExtensionId(activity);

            if (TaskImplementationTypeDefinitions.REST_SERVICE.equals(id)) {
                return true;
            }

        } else {
            Event event = activity.getEvent();

            if (event != null) {
                Object id =
                        Xpdl2ModelUtil.getOtherAttribute(event,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ImplementationType());
                if (TaskImplementationTypeDefinitions.REST_SERVICE.equals(id)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 
     * @param activity
     * @return <code>true</code> if the passed activity is an error event on the
     *         boundary of Rest Activity, else return <code>false</code>
     */
    public static boolean isBoundaryRestServiceErrorEvent(Activity activity) {

        Activity throwerActivity = getThrowerRestActivity(activity);

        return throwerActivity != null
                && isRESTServiceActivity(throwerActivity);
    }

    /**
     * If this is a REST catch activity catching a specific fault code then this
     * method will return the Activity that throws the associated fault.
     * 
     * @param activity
     *            The activity containing the mappings.
     * @return The throwing activity if this is a REST catch or null.
     */
    public static Activity getThrowerRestActivity(Activity activity) {
        Activity throwActivity = null;
        if (activity != null) {
            if (activity.getEvent() instanceof IntermediateEvent) {
                IntermediateEvent event =
                        (IntermediateEvent) activity.getEvent();

                if (TriggerType.ERROR_LITERAL.equals(event.getTrigger())) {
                    Object thrower =
                            BpmnCatchableErrorUtil.getErrorThrower(activity);
                    if (thrower instanceof Activity) {
                        throwActivity = (Activity) thrower;
                    }
                }
            }
        }
        return throwActivity;
    }

    /**
     * Gets the RestServiceOperation contained within the given
     * OtherElementsContainer if it exists. The OtherElementsContainer can be
     * obtained from an Activity using the getRSOContainer method.
     * 
     * @param activity
     * @return the RestServiceOperation or null if it doesn't exist.
     */
    public static RestServiceOperation getRESTServiceOperation(Activity activity) {
        OtherElementsContainer rsoParent = null;
        if (activity != null) {
            Implementation impl = activity.getImplementation();
            Event event = activity.getEvent();
            if (impl instanceof Task) {
                Task task = (Task) impl;
                rsoParent = task.getTaskService();
                if (rsoParent == null) {
                    rsoParent = task.getTaskSend();
                }

            } else if (event != null) {
                EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();
                if (eventTriggerTypeNode instanceof TriggerResultMessage) {
                    rsoParent = (TriggerResultMessage) eventTriggerTypeNode;
                }
            }
        }

        RestServiceOperation rso = null;
        if (rsoParent != null) {
            EObject element =
                    rsoParent.getOtherElement(XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_RestServiceOperation().getName());
            if (element instanceof RestServiceOperation) {
                rso = (RestServiceOperation) element;
            }
        }
        return rso;
    }

    /**
     * @param activity
     * 
     * @return The REST Service Descriptor Method referenced by activity or
     *         <code>null</code> if there is none selected, no longer exists or
     *         not a REST service activity
     */
    public static Method getRESTMethod(Activity activity) {

        RestServiceOperation rso = getRESTServiceOperation(activity);

        if (rso != null) {
            Service service = getRESTService(activity);
            if (service != null) {

                for (Resource rsdResource : service.getResources()) {

                    for (Method rsdMethod : rsdResource.getMethods()) {

                        if (rsdMethod.getId().equals(rso.getMethodId())) {
                            return rsdMethod;
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * @param activity
     * @return The REST Service Descriptor root element for Method referenced by
     *         activity or <code>null</code> if there is none selected, no
     *         longer exists or not a REST service activity
     */
    public static Service getRESTService(Activity activity) {
        Service service = null;
        RestServiceOperation rso = getRESTServiceOperation(activity);

        if (rso != null) {
            /* Resovle RSD file from Special older relative location. */
            String rsdFileLocation = rso.getLocation();

            IFile rsdFile =
                    SpecialFolderUtil
                            .resolveSpecialFolderRelativePath(WorkingCopyUtil
                                    .getProjectFor(activity),
                                    RestServicesUtil.REST_SPECIAL_FOLDER_KIND,
                                    rsdFileLocation,
                                    true);

            if (rsdFile != null && rsdFile.isAccessible()) {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(rsdFile);

                if (wc instanceof RsdWorkingCopy) {
                    service = ((RsdWorkingCopy) wc).getService();
                }
            }
        }
        return service;
    }
}
