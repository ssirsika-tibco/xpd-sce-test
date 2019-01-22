/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.indexing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskObjectUtil;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.xpdExtension.BusinessProcess;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Indexer Provider which indexes Activities which reference processes.(i.e.
 * Sub-processes , Send Tasks, User tasks invoking pageflows)
 * 
 * @author kthombar
 * @since 03-Sep-2014
 */
public class ActivityInvokingProcessIndexProvider extends
        AbstractXpdl2ResourceIndexProvider {

    public static final String ACTIVITY_INVOKING_PROCESS_REFERENCE =
            "ACTIVITY_INVOKING_PROCESS_REFERENCE"; //$NON-NLS-1$

    public static final String COLUMN_ACTIVITY_ID = "activity_id"; //$NON-NLS-1$

    public static final String COLUMN_PROCESS_ID = "process_id"; //$NON-NLS-1$

    public static final String ACTIVITY_INVOKING_PROCESS_REFERENCE_INDEXER_ID =
            "com.tibco.xpd.analyst.resources.xpdl2.indexing.ActivityInvokingProcessIndexProvider"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.indexing.AbstractXpdl2ResourceIndexProvider#addIndexedItemsForPackage(java.util.ArrayList,
     *      java.lang.String, java.lang.String, com.tibco.xpd.xpdl2.Package)
     * 
     * @param indexedItems
     * @param projectName
     * @param xpdlPath
     * @param pkg
     */
    @Override
    protected void addIndexedItemsForPackage(
            ArrayList<IndexerItem> indexedItems, String projectName,
            String xpdlPath, Package pkg) {

        EList<Process> processes = pkg.getProcesses();

        for (Process process : processes) {

            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);

            for (Activity activity : allActivitiesInProc) {

                IndexerItem item = null;
                if (isReusableSubProcess(activity)) {

                    item = indexReusableSubProcActivity(activity);
                } else if (isSendTaskInvokingBusinessProcess(activity)) {

                    item = indexSendTaskActivity(activity);
                } else if (isUserTaskFormTypePageFlow(activity)) {
                    item = indexUserTaskPageflowForm(activity);
                }
                if (item != null) {
                    indexedItems.add(item);
                }
            }
        }
    }

    /**
     * 
     * @param activity
     * @return the {@link IndexerItem} of the User Task which references a
     *         PageFlow.
     */
    private IndexerItem indexUserTaskPageflowForm(Activity activity) {
        Process pageflowProcess =
                TaskObjectUtil.getUserTaskPageflowProcess(activity);

        if (pageflowProcess != null) {

            String pageFlowId = pageflowProcess.getId();

            if (pageFlowId != null && !pageFlowId.isEmpty()) {

                HashMap<String, String> map = new HashMap<String, String>();

                map.put(COLUMN_ACTIVITY_ID, activity.getId());

                map.put(COLUMN_PROCESS_ID, pageFlowId);

                IndexerItem item =
                        new IndexerItemImpl(
                                ProcessUIUtil
                                        .getActivityQualifiedName(activity),
                                ACTIVITY_INVOKING_PROCESS_REFERENCE,
                                ProcessUIUtil.getURIString(activity, true), map);

                return item;
            }
        }
        return null;
    }

    /**
     * @param activity
     * @return the {@link IndexerItem} of a Send task which invokes a Process
     */
    private IndexerItem indexSendTaskActivity(Activity activity) {

        Implementation impl = activity.getImplementation();

        if (impl instanceof Task) {
            Task task = (Task) impl;
            TaskSend taskSend = task.getTaskSend();

            if (taskSend != null) {

                Object otherElement =
                        Xpdl2ModelUtil.getOtherElement(taskSend,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_BusinessProcess());
                if (otherElement instanceof BusinessProcess) {

                    BusinessProcess sendTaskBusinessProc =
                            (BusinessProcess) otherElement;

                    String processId = sendTaskBusinessProc.getProcessId();

                    if (processId != null && !processId.isEmpty()) {

                        HashMap<String, String> map =
                                new HashMap<String, String>();

                        map.put(COLUMN_ACTIVITY_ID, activity.getId());

                        map.put(COLUMN_PROCESS_ID, processId);

                        IndexerItem item =
                                new IndexerItemImpl(
                                        ProcessUIUtil
                                                .getActivityQualifiedName(activity),
                                        ACTIVITY_INVOKING_PROCESS_REFERENCE,
                                        ProcessUIUtil.getURIString(activity,
                                                true), map);

                        return item;
                    }
                }
            }
        }

        return null;
    }

    /**
     * @param activity
     * @return the {@link IndexerItem} of the User task which invokes a process
     */
    private IndexerItem indexReusableSubProcActivity(Activity activity) {

        Implementation impl = activity.getImplementation();

        if (impl instanceof SubFlow) {
            SubFlow subFlow = (SubFlow) impl;

            String processId = subFlow.getProcessId();

            if (processId != null && !processId.isEmpty()) {
                HashMap<String, String> map = new HashMap<String, String>();

                map.put(COLUMN_ACTIVITY_ID, activity.getId());

                map.put(COLUMN_PROCESS_ID, processId);

                IndexerItem item =
                        new IndexerItemImpl(
                                ProcessUIUtil
                                        .getActivityQualifiedName(activity),
                                ACTIVITY_INVOKING_PROCESS_REFERENCE,
                                ProcessUIUtil.getURIString(activity, true), map);

                return item;
            }
        }
        return null;
    }

    /**
     * 
     * @param activity
     * @return <code>true</code> if the passed activity is an re-usable sub
     *         process <code>false</code> otherwise
     */
    private boolean isReusableSubProcess(Activity activity) {

        Implementation impl = activity.getImplementation();

        if (impl instanceof SubFlow) {
            return true;
        }
        return false;
    }

    /**
     * 
     * @param activity
     * @return <code>true</code> if the passed activity is an Send Task invoking
     *         Process <code>false</code> otherwise
     */
    private boolean isSendTaskInvokingBusinessProcess(Activity activity) {
        Implementation impl = activity.getImplementation();

        if (impl instanceof Task) {

            Task task = (Task) impl;
            TaskSend taskSend = task.getTaskSend();

            if (taskSend != null) {

                Object otherElement =
                        Xpdl2ModelUtil.getOtherElement(taskSend,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_BusinessProcess());
                if (otherElement instanceof BusinessProcess) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 
     * @param activity
     * @return <code>true</code> if the passed activity is an User task having
     *         Form type as pageflow <code>false</code> otherwise
     */
    private boolean isUserTaskFormTypePageFlow(Activity activity) {
        FormImplementation userTaskFormImplementation =
                TaskObjectUtil.getUserTaskFormImplementation(activity);

        return userTaskFormImplementation != null
                && FormImplementationType.PAGEFLOW
                        .equals(userTaskFormImplementation.getFormType());
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.indexing.AbstractXpdl2ResourceIndexProvider#shouldReIndexForObject(java.lang.Object,
     *      org.eclipse.emf.common.notify.Notification)
     * 
     * @param o
     * @param notification
     * @return
     */
    @Override
    protected boolean shouldReIndexForObject(Object o, Notification notification) {
        if (o instanceof Activity) {
            Activity activity = (Activity) o;

            /*
             * Index only when the Activity is Re-usable Sub process , Send task
             * invoking Business process or a User task having form type as
             * pageflow.
             */
            if (isReusableSubProcess(activity)
                    || isSendTaskInvokingBusinessProcess(activity)
                    || isUserTaskFormTypePageFlow(activity)) {
                return true;
            }
        }
        return false;
    }
}
