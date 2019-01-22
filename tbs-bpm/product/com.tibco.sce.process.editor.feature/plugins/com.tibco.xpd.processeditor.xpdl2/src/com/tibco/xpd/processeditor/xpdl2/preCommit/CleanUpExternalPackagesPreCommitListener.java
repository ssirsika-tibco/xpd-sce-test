/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.preCommit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.BusinessProcess;
import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.AbstractProcessPreCommitContributor;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This pre-commit Listener, handles clean up of External Package references
 * which are no more used by any element of the Process package. It handles
 * events specific to Elements which refer to External packages. 1. Process -
 * Implemented interface Handles notifications for change of implemented
 * Interface. 2. Send task Handles notifications for change of implementation
 * [Business process], Deletion of task, Change of Send task to any other
 * Activity type. 3. Reusable Subprocess Handles notifications for change of
 * process, Deletion of Activity, Change of Reusable SubProc Activity to any
 * other Activity Type. Finally Deletion of a process, which might have multiple
 * elements [as above] with External package reference. Does not remove a
 * reference if it is used by another existing element in the process package.
 * 
 * @author aprasad
 * @since 20 Mar 2013
 */
public class CleanUpExternalPackagesPreCommitListener extends
        AbstractProcessPreCommitContributor {

    /**
     * @see com.tibco.xpd.xpdl2.resources.IProcessPreCommitContributor#contributeCommand(org.eclipse.emf.transaction.ResourceSetChangeEvent,
     *      java.util.Collection)
     * 
     * @param event
     * @param notifications
     * @return
     * @throws RollbackException
     */
    @Override
    public Command contributeCommand(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications)
            throws RollbackException {

        TransactionalEditingDomain editingDomain = event.getEditingDomain();
        List<String> listExternalPackageHref = new ArrayList<String>();
        CompoundCommand cmd =
                new CompoundCommand("Remove Extrenal Package Reference"); //$NON-NLS-1$

        for (Notification notification : notifications) {
            if (notification.getNotifier() instanceof EObject) {
                switch (notification.getEventType()) {
                // Set notifications for implementation changes in Send task and
                // Reusable SubProc Activity
                case Notification.SET:
                    handleSetNotification(notification, listExternalPackageHref);
                    break;
                // Notifications for Deletion of Process, Reusable SubProc
                // Activity, Send task, change in Implemented Interface for a
                // Process.
                case Notification.REMOVE:
                    handleRemoveNotification(notification,
                            listExternalPackageHref);
                    break;
                }
                // if exists, External packages referred by modified elements
                if (null != listExternalPackageHref
                        && listExternalPackageHref.size() > 0) {
                    Xpdl2WorkingCopyImpl wc = null;
                    // get the working Copy
                    if (notification.getNotifier() instanceof EObject) {
                        wc =
                                (Xpdl2WorkingCopyImpl) WorkingCopyUtil
                                        .getWorkingCopyFor((EObject) notification
                                                .getNotifier());
                    }
                    for (String href : listExternalPackageHref) {
                        // check each External Package Reference
                        if (pkgNoMoreReferenced(href, wc)) {
                            // remove if no more referenced by any other
                            // element in the process package.
                            Xpdl2WorkingCopyImpl
                                    .appendRemoveReferenceCommand(editingDomain,
                                            cmd,
                                            href,
                                            wc);
                        }

                    }

                }
            }
        }
        if (cmd.canExecute()) {
            return cmd;
        }

        return null;
    }

    /**
     * Handles Deletion of Process, Send task and Reusable SubProcess Activity &
     * change of Implemented Interface for a Process.
     * 
     * @param notification
     * @param listExternalPackageHref
     * @return
     */
    private void handleRemoveNotification(Notification notification,
            List<String> listExternalPackageHref) {
        // Deletion of Process
        if (notification.getNotifier() instanceof Package
                && notification.getFeature()
                        .equals(Xpdl2Package.eINSTANCE.getPackage_Processes())) {
            getExternalPackage(notification.getOldValue(),
                    listExternalPackageHref);
        } else if (notification.getNotifier() instanceof Process) {
            if (notification
                    .getFeature()
                    .equals(Xpdl2Package.eINSTANCE.getFlowContainer_Activities())) {
                // Deletion of Send Task / Reusable SubProc Activity
                if (notification.getOldValue() instanceof EObject) {
                    Activity activity = (Activity) notification.getOldValue();
                    TaskType taskType =
                            TaskObjectUtil.getTaskTypeStrict(activity);
                    if (taskType != null) {
                        switch (taskType.getValue()) {
                        case TaskType.SEND:
                        case TaskType.SUBPROCESS:
                            getExternalPackage(activity,
                                    listExternalPackageHref);
                            break;
                        default:// DO NOTHING
                        }
                    }
                }
            } else if (notification
                    .getFeature()
                    .equals(XpdExtensionPackage.eINSTANCE.getDocumentRoot_ImplementedInterface())) {
                // change Implemented Interface, remove Implemented Interface
                // from Process
                getExternalPackage(notification.getOldValue(),
                        listExternalPackageHref);
            }
        }
    }

    /**
     * Handles the Set notifications for implementation change for an Activity.
     * 
     * @param notification
     * @param listExternalPackageHref
     * @return
     */
    private void handleSetNotification(Notification notification,
            List<String> listExternalPackageHref) {
        Object notifier = notification.getNotifier();
        if (notifier instanceof Activity) {
            // Implementation change for SubProcess Activity and Send Task
            if (notification
                    .getFeature()
                    .equals(Xpdl2Package.eINSTANCE.getActivity_Implementation())) {
                getExternalPackage(notification.getOldValue(),
                        listExternalPackageHref);
            }
        }
    }

    /**
     * @param externalPackageId
     * @param wc
     * @return
     */
    private static boolean pkgNoMoreReferenced(String externalPackageHrefId,
            Xpdl2WorkingCopyImpl wc) {
        if (externalPackageHrefId != null) {

            Package pkg = ((Package) wc.getRootElement());
            EList<Process> processes = pkg.getProcesses();
            for (Process process : processes) {
                List<String> extPackageHrefs = new ArrayList<String>();
                getExternalPackage(process, extPackageHrefs);
                for (String extPkgHref : extPackageHrefs) {
                    if (externalPackageHrefId.equalsIgnoreCase(extPkgHref)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Searches for External package references, from the objects provided.This
     * method handles Process, Send task, Reusable SubProc Activity, and
     * Implemented Interface.
     * 
     * @param listExternalPackageHref
     * 
     * @param notification
     * @return
     */
    private static void getExternalPackage(Object object,
            List<String> listExternalPackageHref) {

        if (object instanceof EObject) {
            EObject eObject = (EObject) object;
            String id = null;
            if (eObject instanceof Activity) {
                Activity activity = (Activity) eObject;
                Implementation impl = activity.getImplementation();
                // Handle only Reusable SubProc and Send task
                if (impl instanceof SubFlow || impl instanceof Task) {
                    getExternalPackage(impl, listExternalPackageHref);
                }
            } else if (eObject instanceof Process) {// Process Notification
                Process process = (Process) eObject;
                // Implemented Interface might belong to external package
                ImplementedInterface implementedInterface =
                        (ImplementedInterface) (process)
                                .getOtherElement(XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ImplementedInterface()
                                        .getName());
                if (implementedInterface != null) {
                    id = implementedInterface.getPackageRef();
                }

                /*
                 * XPD-6626: Saket: "process.getActivities()" doesn't take the
                 * activities inside an activity set into account. Should
                 * ideally use Xpdl2ModelUtil.getAllActivitiesInProc(process).
                 */
                for (Activity activity : Xpdl2ModelUtil
                        .getAllActivitiesInProc(process)) {
                    getExternalPackage(activity, listExternalPackageHref);
                }
            } else if (eObject instanceof ImplementedInterface) {
                id = ((ImplementedInterface) eObject).getPackageRef();
            } else if (eObject instanceof Task) {
                Task task = (Task) eObject;
                if (null != task.getTaskSend()) {
                    Object otherElement =
                            Xpdl2ModelUtil.getOtherElement(task.getTaskSend(),
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_BusinessProcess());
                    if (otherElement instanceof BusinessProcess) {
                        BusinessProcess businessProcess =
                                (BusinessProcess) otherElement;
                        id = businessProcess.getPackageRefId();
                    }
                }
            } else if (eObject instanceof SubFlow) {
                id = ((SubFlow) eObject).getPackageRefId();
            }
            if (id != null) {
                listExternalPackageHref.add(id);
            }
        }

    }

}
