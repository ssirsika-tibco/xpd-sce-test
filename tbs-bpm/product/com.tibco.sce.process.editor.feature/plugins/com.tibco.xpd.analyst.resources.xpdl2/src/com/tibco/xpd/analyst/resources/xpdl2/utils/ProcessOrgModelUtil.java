/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.utils;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.om.resources.ui.commonpicker.OMTypeQuery;
import com.tibco.xpd.om.resources.ui.types.OMTypesFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.IndexerService;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantsContainer;

/**
 * Organisation Model related utilities for process.
 * 
 * @author aallway
 * @since 3.2
 */
public class ProcessOrgModelUtil {

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    /** OM indexer id. */
    public static final String OM_INDEXER =
            "com.tibco.xpd.om.resources.indexing.omIndexer"; //$NON-NLS-1$

    /** Special folder kind for OM special folders. */
    public final static String OM_SPECIAL_FOLDER_KIND = "om"; //$NON-NLS-1$

    /**
     * Check if this {@link Participant} has an external referenced entity.
     * 
     * @param participantsContainer
     * @param participant
     * @return
     * @since 3.3
     */
    public static boolean hasReferencedOrgModelEntity(
            ParticipantsContainer participantsContainer, Participant participant) {
        boolean ret = false;

        if (participant.getExternalReference() != null) {
            ExternalReference extRef = participant.getExternalReference();

            String sfRelativeLoc = extRef.getLocation();
            String orgModelEntityId = extRef.getXref();

            ret =
                    sfRelativeLoc != null && sfRelativeLoc.length() > 0
                            && orgModelEntityId != null
                            && orgModelEntityId.length() > 0;
        }

        return ret;
    }

    /**
     * Return the {@link IndexerItem} of the organisation model entity
     * referenced by the given participant.
     * 
     * @param participant
     * 
     * @return Org unit <code>IndexerItem</code> of <code>null</code> if not
     *         found (or participant is not external reference).
     * @since 3.3
     */
    public static IndexerItem getReferencedOrgModelEntity(
            ParticipantsContainer participantsContainer, Participant participant) {
        IndexerItem item = null;

        if (participant.getExternalReference() != null) {
            ExternalReference extRef = participant.getExternalReference();

            String sfRelativeLoc = extRef.getLocation();
            String orgModelEntityId = extRef.getXref();

            IProject contextProject =
                    WorkingCopyUtil.getProjectFor(participantsContainer);
            IndexerService service =
                    XpdResourcesPlugin.getDefault().getIndexerService();

            IndexerItemImpl criteria = new IndexerItemImpl();

            criteria.set("id", orgModelEntityId); //$NON-NLS-1$

            Collection<IndexerItem> items = service.query(OM_INDEXER, criteria);

            for (IndexerItem indexerItem : items) {
                String uriString = indexerItem.getURI();
                boolean resourceIsInContext = false;
                try {
                    IResource resourceFromURL = getIResourceFromURL(uriString);
                    if (resourceFromURL instanceof IFile) {
                        IProject urlProject = resourceFromURL.getProject();
                        // Check if resource in the same or referenced project.
                        if (contextProject.equals(urlProject)
                                || ProjectUtil
                                        .isProjectReferenced(contextProject,
                                                urlProject)) {
                            // And it is in the OM special folder.
                            ProjectConfig config =
                                    XpdResourcesPlugin.getDefault()
                                            .getProjectConfig(urlProject);

                            // Config may be null if not XPD project.
                            if (config != null) {
                                SpecialFolder sf =
                                        config.getSpecialFolders()
                                                .getFolderContainer(resourceFromURL);
                                if (sf != null
                                        && OM_SPECIAL_FOLDER_KIND.equals(sf
                                                .getKind())) {
                                    // Also check that SF relative path is the
                                    // same.
                                    IPath specialFolderRelativePath =
                                            SpecialFolderUtil
                                                    .getSpecialFolderRelativePath(resourceFromURL,
                                                            OM_SPECIAL_FOLDER_KIND);
                                    if (specialFolderRelativePath != null) {
                                        // MR41948 - requires ability to create
                                        // a participant containing an external
                                        // reference to an organisation model
                                        // with a space in the filename.
                                        URI fileURI =
                                                URI.createFileURI(specialFolderRelativePath
                                                        .toPortableString());
                                        if (sfRelativeLoc != null
                                                && sfRelativeLoc.equals(fileURI
                                                        .toString())) {
                                            resourceIsInContext = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    LOG.error(e);
                }

                if (resourceIsInContext) {
                    item = indexerItem;
                    break;
                }
            }
        }

        return item;
    }

    public static IndexerItem[] getReferencedOrgModelEntities(
            IProject contextProject) {

        Collection<IndexerItem> privilegeIndexerItems =
                new ArrayList<IndexerItem>();

        IndexerItemImpl criteria = new IndexerItemImpl();
        IndexerService service =
                XpdResourcesPlugin.getDefault().getIndexerService();

        Collection<IndexerItem> items = service.query(OM_INDEXER, criteria);
        for (IndexerItem indexerItem : items) {

            if (OMTypesFactory.TYPE_ID_PRIVILEGE.equals(indexerItem.getType())) {

                privilegeIndexerItems.add(indexerItem);
            }
        }

        return privilegeIndexerItems != null ? privilegeIndexerItems
                .toArray(new IndexerItem[privilegeIndexerItems.size()])
                : new IndexerItem[0];
    }

    public static Object[] getOMPrivilegeFromPicker(Shell shell,
            IProject contextProject, String initialPattern) {

        IndexerItem[] orgModelEntities =
                ProcessOrgModelUtil
                        .getReferencedOrgModelEntities(contextProject);
        Object[] selection =
                PickerService.getInstance().openMultiPickerDialog(shell,
                        new PickerTypeQuery[] { new OMTypeQuery(
                                OMTypeQuery.TYPE_ID_PRIVILEGE) },
                        null,
                        null,
                        null,
                        null,
                        orgModelEntities);
        return selection;

    }

    /**
     * Return the FULL name of the organisation model entity referenced by the
     * given participant.
     * 
     * @param participant
     * 
     * @return Org unit name of null if not found (or participant is not
     *         external reference).
     */
    public static String getReferencedOrgModelEntityName(
            ParticipantsContainer participantsContainer, Participant participant) {
        IndexerItem item =
                getReferencedOrgModelEntity(participantsContainer, participant);
        return item != null ? item.getName() : null;
    }

    /**
     * Return the SIMPLE name of the organisation model entity referenced by the
     * given participant.
     * 
     * @param participant
     * 
     * @return Org unit name of null if not found (or participant is not
     *         external reference).
     */
    public static String getReferencedOrgModelEntitySimpleName(
            ParticipantsContainer participantsContainer, Participant participant) {
        String result = null;

        String fullName =
                ProcessOrgModelUtil
                        .getReferencedOrgModelEntityName(participantsContainer,
                                participant);
        if (fullName != null) {
            int idx = fullName.lastIndexOf("."); //$NON-NLS-1$
            if (idx >= 0) {
                result = fullName.substring(idx + 1);
            }
        }

        return result;
    }

    /**
     * Gives one of workspace resources linked with provided URI.
     * 
     * @param uri
     *            the URI of the workspace resource.
     * @return the one of the IResource associated with the URI. If URI does not
     *         point to valid resource location or if the resource does not
     *         exist the <code>null</null> value will be returned.
     */
    public static IResource getIResourceFromURL(String uri) {

        URI fileURI = URI.createURI(uri);
        String platformString = fileURI.toPlatformString(true);
        return ResourcesPlugin.getWorkspace().getRoot()
                .findMember(platformString);
    }

}
