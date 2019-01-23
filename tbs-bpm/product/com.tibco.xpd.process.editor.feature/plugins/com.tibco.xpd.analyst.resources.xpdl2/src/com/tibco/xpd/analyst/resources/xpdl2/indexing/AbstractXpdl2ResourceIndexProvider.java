/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.indexing;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * 
 * 
 * @author aallway
 * @since 3.3 (18 Jan 2010)
 */
public abstract class AbstractXpdl2ResourceIndexProvider implements
        WorkingCopyIndexProvider {

    private boolean logNotifications = false;

    public AbstractXpdl2ResourceIndexProvider() {
        super();
    }

    /**
     * @param logNotifications
     *            the logNotifications to set
     */
    public void setLogNotifications(boolean logNotifications) {
        this.logNotifications = logNotifications;
    }

    /**
     * @param wc
     * @param resource
     */
    public Collection<IndexerItem> getIndexItems(WorkingCopy wc) {
        ArrayList<IndexerItem> items = new ArrayList<IndexerItem>();
        String path = ProcessUIUtil.createPath(wc);
        IResource resource = wc.getEclipseResources().get(0);
        EObject root = wc.getRootElement();
        if (root instanceof Package) {
            addIndexedItemsForPackage(items,
                    resource.getProject().getName(),
                    path,
                    (Package) root);
        }

        if (logNotifications) {
            System.out
                    .println("New Indexed Items For: " + resource.getLocationURI().toString()); //$NON-NLS-1$
            System.out
                    .println("--------------------------------------------------------------------------------");//$NON-NLS-1$
            for (IndexerItem indexerItem : items) {
                System.out.println("  " + indexerItem.getType() + ": " //$NON-NLS-1$ //$NON-NLS-2$
                        + indexerItem.getName()
                        + "(" //$NON-NLS-1$
                        + indexerItem
                                .get(Xpdl2ResourcesPlugin.ATTRIBUTE_ITEM_ID)
                        + ")" //$NON-NLS-1$
                        + "(" //$NON-NLS-1$
                        + indexerItem.getURI() + ")"); //$NON-NLS-1$
            }
            System.out
                    .println("=========================================================================================");//$NON-NLS-1$
        }
        return items;
    }

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

                    if (logNotifications) {
                        outputNotfication(notification);
                    }

                    int eventType = notification.getEventType();

                    Object notifier = notification.getNotifier();
                    Object newValue = notification.getNewValue();
                    Object oldValue = notification.getOldValue();

                    switch (eventType) {
                    case Notification.ADD:
                        /*
                         * If a package, proces or interface is added then the
                         * whatever xpdl2 thing we are indexing is likely to be
                         * beneath that so always reindex on add/remove of
                         * package/process/interface (saves every subclass doing
                         * it in shouldReIndexForObject()
                         */
                        if (isPackageOrProcessOrInterface(newValue)
                                || isInterestingObject(newValue, notification)) {
                            isAffectingEvent = true;
                        }
                        break;
                    case Notification.ADD_MANY:
                        /*
                         * If a package, proces or interface is added then the
                         * whatever xpdl2 thing we are indexing is likely to be
                         * beneath that so always reindex on add/remove of
                         * package/process/interface (saves every subclass doing
                         * it in shouldReIndexForObject()
                         */
                        if (isPackageOrProcessOrInterface(newValue)
                                || isInterestingObject(newValue, notification)) {
                            isAffectingEvent = true;
                        }
                        break;
                    case Notification.MOVE:
                        /*
                         * If a package, proces or interface is added then the
                         * whatever xpdl2 thing we are indexing is likely to be
                         * beneath that so always reindex on add/remove of
                         * package/process/interface (saves every subclass doing
                         * it in shouldReIndexForObject()
                         */
                        if (isPackageOrProcessOrInterface(newValue)
                                || isInterestingObject(newValue, notification)) {
                            isAffectingEvent = true;
                        }
                        break;
                    case Notification.REMOVE:
                        /*
                         * If a package, proces or interface is added then the
                         * whatever xpdl2 thing we are indexing is likely to be
                         * beneath that so always reindex on add/remove of
                         * package/process/interface (saves every subclass doing
                         * it in shouldReIndexForObject()
                         */
                        if (isPackageOrProcessOrInterface(oldValue)
                                || isInterestingObject(oldValue, notification)) {
                            isAffectingEvent = true;
                        }
                        break;
                    case Notification.SET:
                        if (isInterestingObject(notifier, notification)) {
                            isAffectingEvent = true;
                        }
                        break;
                    case Notification.UNSET:
                        if (isInterestingObject(notifier, notification)) {
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

        if (logNotifications) {
            System.out
                    .println("isAffectingEvent(): " + isAffectingEvent + "  (" //$NON-NLS-1$ //$NON-NLS-2$
                            + event + ")"); //$NON-NLS-1$
        }

        return isAffectingEvent;
    }

    /**
     * @param newValue
     * @return true if the given individual object that was added / removed /
     *         modified by a command should cause a re-index on it's parent
     *         package.
     */
    private boolean isInterestingObject(Object o, Notification notification) {
        /*
         * For collections, recurs and do individual members.
         */
        if (o instanceof Collection) {
            for (Object subO : ((Collection) o)) {
                if (isInterestingObject(subO, notification)) {
                    return true;
                }
            }
        }

        return shouldReIndexForObject(o, notification);
    }

    /**
     * Add entries to the indexedItems list for each item that should be indexed
     * for or within the given package.
     * 
     * @param indexedItems
     * @param projectName
     * @param xpdlPath
     * @param pkg
     */
    protected abstract void addIndexedItemsForPackage(
            ArrayList<IndexerItem> indexedItems, String projectName,
            String xpdlPath, Package pkg);

    /**
     * @param o
     * @param notification
     *            just in case you wish top be more specific about the times
     *            when an object change should cause a re-index.
     * 
     * @return true if the given individual object that was added / removed /
     *         modified by a command should cause a re-index on it's parent
     *         package.
     */
    protected abstract boolean shouldReIndexForObject(Object o,
            Notification notification);

    /**
     * @param o
     * 
     * @return true if the object is a process package, process or process
     *         interface (or class thereof).
     */
    private boolean isPackageOrProcessOrInterface(Object o) {
        if (o instanceof Process || o instanceof ProcessInterface
                || o instanceof Package || o instanceof ProcessInterfaces) {
            return true;
        }

        if (o == Process.class || o == ProcessInterface.class
                || o == Package.class || o == ProcessInterfaces.class) {
            return true;
        }
        return false;
    }

    /**
     * Output the event notification detail for debug purposes.
     * 
     * @param notification
     */
    protected static void outputNotfication(Notification notification) {
        System.out.println("  Notification: "); //$NON-NLS-1$
        System.out
                .println("  ----------------------------------------------------------------------"); //$NON-NLS-1$
        System.out.println("        Type: " + getEventTypeName(notification)); //$NON-NLS-1$
        System.out
                .println("    Notifier: " + (notification.getNotifier() != null ? notification.getNotifier().getClass().getSimpleName() : "???")); //$NON-NLS-1$ //$NON-NLS-2$
        System.out
                .println("     Feature: " + (notification.getFeature() instanceof EStructuralFeature ? ((EStructuralFeature) notification.getFeature()).getName() : "???")); //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println("    OldValue: " + notification.getOldValue()); //$NON-NLS-1$
        System.out.println("    NewValue: " + notification.getNewValue()); //$NON-NLS-1$
        System.out
                .println("  ----------------------------------------------------------------------\n"); //$NON-NLS-1$
    }

    protected static String getEventTypeName(Notification notification) {
        switch (notification.getEventType()) {
        case Notification.CREATE:
            return "CREATE"; //$NON-NLS-1$
        case Notification.SET:
            return "SET"; //$NON-NLS-1$
        case Notification.UNSET:
            return "UNSET"; //$NON-NLS-1$
        case Notification.ADD:
            return "ADD"; //$NON-NLS-1$
        case Notification.REMOVE:
            return "REMOVE"; //$NON-NLS-1$
        case Notification.ADD_MANY:
            return "ADD_MANY"; //$NON-NLS-1$
        case Notification.REMOVE_MANY:
            return "REMOVE_MANY"; //$NON-NLS-1$
        case Notification.MOVE:
            return "MOVE"; //$NON-NLS-1$
        case Notification.REMOVING_ADAPTER:
            return "REMOVING_ADAPTER"; //$NON-NLS-1$
        case Notification.RESOLVE:
            return "RESOLVE"; //$NON-NLS-1$
        case Notification.EVENT_TYPE_COUNT:
            return "EVENT_TYPE_COUNT"; //$NON-NLS-1$
        }
        return notification.getEventType() + "<Unknown>"; //$NON-NLS-1$

    }
}