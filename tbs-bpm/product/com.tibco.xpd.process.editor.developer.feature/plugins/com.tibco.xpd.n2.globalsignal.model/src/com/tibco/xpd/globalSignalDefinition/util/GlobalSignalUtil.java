/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.globalSignalDefinition.util;

import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.globalSignalDefinition.indexer.GsdResourceIndexProvider;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.SignalType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Helper Utility class for standard global signal operations.
 * 
 * @author kthombar
 * @since Feb 10, 2015
 */
public final class GlobalSignalUtil {

    /**
     * Gets the referenced {@link GlobalSignal} for the passed signal name( Uses
     * the indexer to fetch the Global Signal) , returns <code>null</code> if no
     * Global Signal with the passed name was found.
     * 
     * @param globalSignalQualifiedName
     *            the global signal internal model name.
     * @return the referenced {@link GlobalSignal} for the passed signal name(
     *         Uses the indexer to fetch the Global Signal) , returns
     *         <code>null</code> if no Global Signal with the passed name was
     *         found.
     */
    @SuppressWarnings("restriction")
    public static GlobalSignal getGlobalSignalFromName(
            String globalSignalQualifiedName) {

        int indexOfSlash = globalSignalQualifiedName.indexOf("/"); //$NON-NLS-1$
        int indexOfHash = globalSignalQualifiedName.indexOf("#"); //$NON-NLS-1$

        /*
         * As per design the internal signal should have '/' and '#'
         */
        if (indexOfSlash != -1 && indexOfHash != -1) {

            String projectId = globalSignalQualifiedName.substring(0, indexOfSlash);

            String gsdSpecialFolderRelativePath =
                    globalSignalQualifiedName.substring(indexOfSlash + 1,
                            indexOfHash);

            IndexerItemImpl criteria = new IndexerItemImpl();

            String globalSignalName =
                    globalSignalQualifiedName.substring(indexOfHash + 1,
                            globalSignalQualifiedName.length());

            if (globalSignalName != null && !globalSignalName.isEmpty()) {
                /*
                 * pass the gsd file name and the signal name as the criteria
                 * for the indexer.
                 */
                criteria.set(GsdResourceIndexProvider.INDEXER_ATTRIBUTE_GSD_FILE_NAME,
                        gsdSpecialFolderRelativePath);

                criteria.set(IndexerServiceImpl.ATTRIBUTE_NAME,
                        globalSignalName);

                Collection<IndexerItem> query =
                        XpdResourcesPlugin
                                .getDefault()
                                .getIndexerService()
                                .query(GsdResourceIndexProvider.GSD_INDEXER_ID,
                                        criteria);

                for (IndexerItem indexerItem : query) {
                    /*
                     * get the project path from the indexer
                     */
                    String projectPathFromIndexer =
                            indexerItem
                                    .get(IndexerServiceImpl.ATTRIBUTE_PROJECT);

                    if (projectPathFromIndexer != null) {
                        /*
                         * get the project from the project path
                         */
                        IProject project =
                                ResourcesPlugin.getWorkspace().getRoot()
                                        .getProject(projectPathFromIndexer);

                        if (project != null && project.isAccessible()) {

                            String projectIdFromIndexer =
                                    ProjectUtil.getProjectId(project);
                            /*
                             * get the project id and compare it with the one
                             * stored in signal name.
                             */
                            if (projectIdFromIndexer != null
                                    && projectIdFromIndexer.equals(projectId)) {

                                /*
                                 * get the expected signal name from the indexer
                                 */
                                URI uri = URI.createURI(indexerItem.getURI());

                                /*
                                 * get the resource from the URI.
                                 */
                                IResource resource =
                                        ResourcesPlugin
                                                .getWorkspace()
                                                .getRoot()
                                                .findMember(uri.toPlatformString(true));

                                if (resource != null) {

                                    WorkingCopy wc =
                                            XpdResourcesPlugin.getDefault()
                                                    .getWorkingCopy(resource);

                                    if (wc != null) {

                                        GlobalSignalDefinitions globalSignalDefinitions =
                                                (GlobalSignalDefinitions) wc
                                                        .getRootElement();

                                        if (globalSignalDefinitions != null) {

                                            for (GlobalSignal globalSignal : globalSignalDefinitions
                                                    .getGlobalSignals()) {
                                                /*
                                                 * Scan through the global
                                                 * signals for the exact match.
                                                 */
                                                if (globalSignal
                                                        .getName()
                                                        .equals(globalSignalName)) {

                                                    return globalSignal;
                                                }
                                            }
                                        }
                                    }
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
     * 
     * @param activity
     * @return <code>true</code> if the passed activity is an is an Global
     *         signal event.
     */
    public static boolean isGlobalSignalEvent(Activity activity) {
        Event event = activity.getEvent();
        if (event != null) {
            EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();

            if (eventTriggerTypeNode instanceof TriggerResultSignal) {
                TriggerResultSignal signal =
                        (TriggerResultSignal) eventTriggerTypeNode;

                Object otherAttribute =
                        Xpdl2ModelUtil.getOtherAttribute(signal,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_SignalType());

                if (otherAttribute instanceof SignalType) {
                    SignalType sigType = (SignalType) otherAttribute;
                    return SignalType.GLOBAL.equals(sigType) ? true : false;
                }
            }
        }
        return false;
    }

    /**
     * Gets the internal model name for the passed global signal(i.e.
     * projectId/specialFolderRelativePathWithGsdExtension#signalName) else
     * return <code> null</code> if the signal is not in GSD Special folder(for
     * its sub-folders).
     * 
     * @param globalSignal
     *            the global signal
     * @return the internal model name for the passed gloabal signal(i.e.
     *         projectId/specialFolderRelativePathWithGsdExtension#signalName)
     *         else return <code> null</code> if the signal is not in GSD
     *         Special folder(for its sub-folders).
     */
    public static String getGlobalSignalQualifiedName(GlobalSignal globalSignal) {

        String signalModelName = null;

        String signalName = globalSignal.getName();

        IProject project = WorkingCopyUtil.getProjectFor(globalSignal);

        IPath specialFolderRelativePath =
                SpecialFolderUtil.getSpecialFolderRelativePath(globalSignal,
                        GsdConstants.GSD_SPECIAL_FOLDER_KIND);

        if (specialFolderRelativePath != null
                && !specialFolderRelativePath.isEmpty() && project != null) {

            String specialFolderGsdPathString =
                    specialFolderRelativePath.toString();

            specialFolderGsdPathString =
                    specialFolderGsdPathString.substring(0,
                            specialFolderGsdPathString.length());

            signalModelName =
                    ProjectUtil.getProjectId(project)
                            + "/" + specialFolderGsdPathString //$NON-NLS-1$
                            + "#" + signalName; //$NON-NLS-1$

        }

        return signalModelName;
    }

    /**
     * Gets the display name for the global signal.(i.e.
     * signalNAme(projectName/gsdFileName))
     * 
     * @param globalSignal
     *            the global signal.
     * @return the display name for the global signal.(i.e.
     *         signalNAme(projectName/gsdFileName))
     */
    public static String getSignalDisplayNameFromGlobalSignal(
            GlobalSignal globalSignal) {
        String displayName = globalSignal.getName();

        IProject project = WorkingCopyUtil.getProjectFor(globalSignal);

        IPath specialFolderRelativePath =
                SpecialFolderUtil.getSpecialFolderRelativePath(globalSignal,
                        GsdConstants.GSD_SPECIAL_FOLDER_KIND);

        if (project != null && specialFolderRelativePath != null
                && !specialFolderRelativePath.isEmpty()) {

            displayName = displayName + " (" + project.getName() + "/" //$NON-NLS-1$ //$NON-NLS-2$
                    + specialFolderRelativePath + ")"; //$NON-NLS-1$          
        }

        return displayName;
    }

    /**
     * Gets the referenced global signal by the activity else return
     * <code>null</code> if not a throw or catch global signal activity.
     * 
     * @param activity
     *            the activity.
     * @return the referenced global signal by the activity else return
     *         <code>null</code> if not a throw or catch global signal activity.
     */
    public static GlobalSignal getReferencedGlobalSignal(Activity activity) {

        GlobalSignal globalSignal = null;
        String signalName = EventObjectUtil.getSignalName(activity);

        if (signalName != null) {
            globalSignal = getGlobalSignalFromName(signalName);
        }
        return globalSignal;
    }
}
