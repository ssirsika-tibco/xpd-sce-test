/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.globalSignalDefinition.indexer;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalImpl;
import com.tibco.xpd.globalSignalDefinition.util.GsdConstants;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * Indexer for GSD resource that currebtly indexes all the Global Signals in the
 * gsd file.
 * 
 * @author kthombar
 * @since Feb 9, 2015
 */
public class GsdResourceIndexProvider implements WorkingCopyIndexProvider {

    public static final String GSD_INDEXER_ID =
            "com.tibco.xpd.n2.globalsignal.resource.indexer.gsdindexer"; //$NON-NLS-1$

    public static final String GLOBAL_SIGNAL_REF_INDEX_TYPE =
            "GlobalSignalReference"; //$NON-NLS-1$

    public static final String INDEXER_ATTRIBUTE_GSD_FILE_NAME =
            "gsdSpecialFolderRelPath"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider#getIndexItems(com.tibco.xpd.resources.WorkingCopy)
     * 
     * @param wc
     * @return
     */
    @Override
    public Collection<IndexerItem> getIndexItems(WorkingCopy wc) {
        Collection<IndexerItem> items = new ArrayList<IndexerItem>();
        if (isInSpecialFolder(wc)) {

            EObject root = wc.getRootElement();
            if (root instanceof GlobalSignalDefinitions) {

                GlobalSignalDefinitions gsds = (GlobalSignalDefinitions) root;

                /*
                 * we are interested only in indexing global signals
                 */
                /*
                 * get the special folder relative path.
                 */
                IPath gsdSpecialFolderRelativePath =
                        SpecialFolderUtil.getSpecialFolderRelativePath(gsds,
                                GsdConstants.GSD_SPECIAL_FOLDER_KIND);

                if (gsdSpecialFolderRelativePath != null
                        && !gsdSpecialFolderRelativePath.isEmpty()) {

                    for (GlobalSignal globalSignal : gsds.getGlobalSignals()) {
                        /*
                         * indexing each global signal
                         */
                        indexGlobalSignals(items,
                                globalSignal,
                                gsdSpecialFolderRelativePath.toString());
                    }
                }
            }
        }
        return items;
    }

    /**
     * Indexes the global signal
     * 
     * @param items
     *            the list to collect indexed elements
     * @param globalSignal
     *            the global signal to index
     * @param specialFolderRelativeGsdFileName
     *            the special folder relative path of the gsd file containing
     *            the global signal
     */
    @SuppressWarnings("restriction")
    private void indexGlobalSignals(Collection<IndexerItem> items,
            GlobalSignal globalSignal, String specialFolderRelativeGsdFileName) {

        String globalSignalName = globalSignal.getName();

        if (globalSignalName != null && globalSignalName.length() != 0) {
            Map<String, String> map = new HashMap<String, String>();

            map.put(IndexerServiceImpl.ATTRIBUTE_NAME, globalSignalName);
            map.put(INDEXER_ATTRIBUTE_GSD_FILE_NAME,
                    specialFolderRelativeGsdFileName);

            IndexerItem item =
                    new IndexerItemImpl(globalSignalName,
                            GLOBAL_SIGNAL_REF_INDEX_TYPE,
                            getURIString(globalSignal, true), map);

            items.add(item);
        }
    }

    /**
     * 
     * @param modelElement
     * @param appendFragment
     * @return the URI string of for the passed model element.
     */
    private String getURIString(EObject modelElement, boolean appendFragment) {
        URI uri = ProcessUIUtil.getURI(modelElement, appendFragment);
        String uriToString = null;
        if (uri != null) {
            uriToString = uri.toString();
        }
        return uriToString;
    }

    /**
     * 
     * @param wc
     *            the working copy to inspect
     * @return <code>true</code> if the working copy accessed is from a gsd
     *         resource which is in gsd special folder else return
     *         <code>false</code>
     */
    private boolean isInSpecialFolder(WorkingCopy wc) {
        IResource eclipseResource = wc.getEclipseResources().get(0);
        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault()
                        .getProjectConfig(eclipseResource.getProject());

        if (projectConfig != null) {
            SpecialFolders specialFolders = projectConfig.getSpecialFolders();
            SpecialFolder sf =
                    specialFolders.getFolderContainer(eclipseResource);
            return sf != null
                    && GsdConstants.GSD_SPECIAL_FOLDER_KIND
                            .equals(sf.getKind());
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider#isAffectingEvent(java.beans.PropertyChangeEvent)
     * 
     * @param event
     * @return
     */
    @Override
    final public boolean isAffectingEvent(PropertyChangeEvent event) {
        if (event != null
                && event.getNewValue() instanceof ResourceSetChangeEvent) {
            ResourceSetChangeEvent changeEvent =
                    (ResourceSetChangeEvent) event.getNewValue();
            List<?> notifications = changeEvent.getNotifications();
            for (Object object : notifications) {

                Notification notification = (Notification) object;
                int eventType = notification.getEventType();
                Object feature = notification.getFeature();

                Object container = null;
                if (feature instanceof EReference) {
                    container = ((EReference) feature).getContainerClass();
                }
                Object notifier = notification.getNotifier();
                Object newValue = notification.getNewValue();

                boolean isSet = false;

                switch (eventType) {
                case Notification.ADD:
                    if (isInterestingObject(newValue)) {
                        return true;
                    }
                    break;
                case Notification.ADD_MANY:
                    if (isInterestingObject(newValue)) {
                        return true;
                    }
                    break;
                case Notification.MOVE:
                    if (isInterestingObject(newValue)) {
                        return true;
                    }
                    break;
                case Notification.REMOVE:
                    if (isInterestingObject(container)) {
                        return true;
                    }
                    break;
                case Notification.SET:
                    isSet = true;
                    break;
                case Notification.UNSET:
                    isSet = true;
                    break;
                }

                if (notifier instanceof GlobalSignalImpl) {

                    return isSet;
                }
            }
        }
        return false;
    }

    /**
     * 
     * @param object
     * @return <code>true</code> if we are interested in indexing the passed
     *         object else return <code>false</code>
     */
    protected boolean isInterestingObject(Object object) {
        if (object instanceof GlobalSignalImpl) {

            return true;
        }
        return false;
    }
}
