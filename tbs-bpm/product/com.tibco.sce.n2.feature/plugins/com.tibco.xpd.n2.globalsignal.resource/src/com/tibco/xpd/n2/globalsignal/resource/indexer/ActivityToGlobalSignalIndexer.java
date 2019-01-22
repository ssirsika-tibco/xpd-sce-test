/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.indexer;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Indexer provider for activity to global signal reference.
 * 
 * @author sajain
 * @since May 15, 2015
 */
public class ActivityToGlobalSignalIndexer implements WorkingCopyIndexProvider {

    /**
     * Indexer type attribute.
     */
    public static final String ACTIVITY_TO_GLOBAL_SIGNAL_INDEXER_TYPE =
            "ActivityToGlobalSignalReference"; //$NON-NLS-1$

    /**
     * Indexer ID.
     */
    public static final String ID =
            "com.tibco.xpd.n2.globalsignal.resource.indexer.ActivityToGSIndexerProvider"; //$NON-NLS-1$

    /**
     * Global signal Qualified Name attribute.
     */
    public static final String INDEXER_ATTRIBUTE_GS_QUALIFIED_NAME =
            "globalSignalQualifiedName"; //$NON-NLS-1$

    /**
     * @param wc
     * @param resource
     */
    @Override
    public Collection<IndexerItem> getIndexItems(WorkingCopy wc) {

        ArrayList<IndexerItem> items = new ArrayList<IndexerItem>();

        String path = ProcessUIUtil.createPath(wc);

        IResource resource = wc.getEclipseResources().get(0);

        String projectName = null;

        if (resource != null && resource.getProject() != null) {

            projectName = resource.getProject().getName();
        }

        if (wc instanceof Xpdl2WorkingCopyImpl) {

            EObject root = wc.getRootElement();

            if (root instanceof Package) {

                updateActivityIndex(items, projectName, path, (Package) root);
            }

        }

        return items;
    }

    /**
     * Update activity index.
     * 
     * @param items
     * @param projectName
     * @param path
     * @param pkg
     */
    private void updateActivityIndex(Collection<IndexerItem> items,
            String projectName, String path, Package pkg) {

        EList<Process> processes = pkg.getProcesses();

        /*
         * Go through all processes to index the activities that refer a global
         * signal.
         */
        for (Process process : processes) {

            /*
             * Get activities referencing global signals in the form of
             * Actgivity->Global Signal map.
             */
            Map<Activity, GlobalSignal> activitiesReferencingGlobalSignals =
                    getActivitiesReferencingGlobalSignals(process);

            if (activitiesReferencingGlobalSignals != null
                    && !activitiesReferencingGlobalSignals.isEmpty()) {

                Set<Activity> keySet =
                        activitiesReferencingGlobalSignals.keySet();

                /*
                 * Go through these activities to create resource items for each
                 * activity.
                 */
                for (Activity activity : keySet) {

                    GlobalSignal globalSignal =
                            activitiesReferencingGlobalSignals.get(activity);

                    String activityUri =
                            ProcessUIUtil.getURIString(activity, true);

                    String gsQualifiedName =
                            GlobalSignalUtil
                                    .getGlobalSignalQualifiedName(globalSignal);

                    createResourceItem(items,
                            activity.getName(),
                            activityUri,
                            ACTIVITY_TO_GLOBAL_SIGNAL_INDEXER_TYPE,
                            gsQualifiedName);

                }
            }
        }
    }

    /**
     * This method returns activities referencing Global signals for a process
     * element.
     * 
     * @param element
     * @return List of activities referencing Global signals for a process
     *         element.
     **/
    public static Map<Activity, GlobalSignal> getActivitiesReferencingGlobalSignals(
            Process proc) {

        Map<Activity, GlobalSignal> activityToGlobalSignalMap =
                new HashMap<Activity, GlobalSignal>();

        /*
         * Get all activities in the specified process.
         */
        Collection<Activity> allActivities =
                Xpdl2ModelUtil.getAllActivitiesInProc(proc);

        if (allActivities != null && !allActivities.isEmpty()) {

            for (Activity eachActivity : allActivities) {

                /*
                 * Check if the current activity is a global signal event.
                 */
                if (!activityToGlobalSignalMap.keySet().contains(eachActivity)
                        && GlobalSignalUtil.isGlobalSignalEvent(eachActivity)) {

                    /*
                     * Check if it's referencing a global signal.
                     */
                    GlobalSignal referencedGlobalSignal =
                            GlobalSignalUtil
                                    .getReferencedGlobalSignal(eachActivity);

                    if (referencedGlobalSignal != null) {

                        /*
                         * If there's a reference, then put the activity and
                         * global signal into the map.
                         */
                        activityToGlobalSignalMap.put(eachActivity,
                                referencedGlobalSignal);
                    }
                }
            }

        }

        return activityToGlobalSignalMap;
    }

    /**
     * Create an resource item entry.
     * 
     * @param list
     * @param activityName
     * @param activityUri
     * @param indexerType
     * @param globalSignalQualifiedName
     */
    private void createResourceItem(Collection<IndexerItem> list,
            String activityName, String activityUri, String indexerType,
            String globalSignalQualifiedName) {

        HashMap<String, String> map = new HashMap<String, String>();

        map.put(INDEXER_ATTRIBUTE_GS_QUALIFIED_NAME, globalSignalQualifiedName);

        IndexerItem item =
                new IndexerItemImpl(activityName, indexerType, activityUri, map);

        list.add(item);
    }

    @Override
    public boolean isAffectingEvent(PropertyChangeEvent event) {

        boolean isAffectingEvent = false;

        if (event != null
                && event.getNewValue() instanceof ResourceSetChangeEvent) {

            ResourceSetChangeEvent changeEvent =
                    (ResourceSetChangeEvent) event.getNewValue();

            List<?> notifications = changeEvent.getNotifications();

            for (Object object : notifications) {

                if (object instanceof Notification) {

                    Notification notification = (Notification) object;

                    int eventType = notification.getEventType();

                    Object feature = notification.getFeature();

                    Object container = null;

                    if (feature instanceof EReference) {

                        container = ((EReference) feature).getContainerClass();
                    }

                    Object notifier = notification.getNotifier();

                    Object newValue = notification.getNewValue();

                    boolean isInterestingSetOrUnset = true;

                    switch (eventType) {

                    case Notification.ADD:

                        if (isInterestingObject(newValue)) {

                            isAffectingEvent = true;
                        }

                        break;

                    case Notification.ADD_MANY:

                        if (isInterestingObject(newValue)) {

                            isAffectingEvent = true;

                        }

                        break;

                    case Notification.MOVE:

                        if (isInterestingObject(newValue)) {

                            isAffectingEvent = true;

                        }

                        break;

                    case Notification.REMOVE:

                        if (isInterestingObject(container)) {

                            isAffectingEvent = true;

                        }

                        break;

                    case Notification.SET:

                        if (isInterestingSetOrUnset
                                && isInterestingObject(notifier)) {

                            isAffectingEvent = true;

                        }

                        break;

                    case Notification.UNSET:

                        if (isInterestingSetOrUnset
                                && isInterestingObject(notifier)) {

                            isAffectingEvent = true;

                        }

                        break;
                    }

                }

                if (isAffectingEvent) {
                    break;
                }
            }
        }

        return isAffectingEvent;
    }

    /**
     * Filter the objects that we are interested in.
     * 
     * @param o
     *            Object.
     * 
     * @return <code>true</code> if we are interested in the object,
     *         <code>false</code> otherwise.
     */
    private boolean isInterestingObject(Object o) {

        if (o instanceof Collection) {
            for (Object subO : ((Collection<?>) o)) {
                if (isInterestingObject(subO)) {
                    return true;
                }
            }
        }

        if (o instanceof Process) {
            return true;
        }

        if (o == Process.class) {
            return true;
        }

        if (o instanceof Activity) {
            return true;
        }

        if (o == Activity.class) {
            return true;
        }

        return false;
    }

}
