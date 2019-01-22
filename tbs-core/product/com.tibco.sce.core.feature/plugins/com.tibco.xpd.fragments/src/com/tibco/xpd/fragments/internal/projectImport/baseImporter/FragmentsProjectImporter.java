/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.projectImport.baseImporter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.ui.wizards.datatransfer.IImportStructureProvider;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.FragmentsImporter;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.internal.FragmentsExtensionHelper;
import com.tibco.xpd.fragments.internal.FragmentsManager;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.fragments.internal.FragmentsExtensionHelper.FragmentsProvider;
import com.tibco.xpd.fragments.internal.impl.FragmentCategoryImpl;
import com.tibco.xpd.fragments.internal.impl.FragmentProperties;

/**
 * Fragments importer for the .bsProject project. This will copy all user
 * categories/fragments from the external fragments project into the workspace.
 * If any of the fragments are out-of-version then their contributors will be
 * asked to migrate them.
 * 
 * @author njpatel
 * 
 */
public class FragmentsProjectImporter extends FragmentsImporter {

    private FragmentsManager manager;
    private FragmentsExtensionHelper extensionHelper;

    /**
     * Fragments project importer.
     */
    public FragmentsProjectImporter() {
        manager = FragmentsManager.getInstance();
        extensionHelper = FragmentsExtensionHelper.getInstance();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragmentsImporter#getProviderId()
     */
    public String getProviderId() {
        // This is importing the fragments project so will be processing all
        // contributors, therefore return null
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.fragments.IFragmentsImporter#importProject(java.lang.Object
     * , org.eclipse.ui.wizards.datatransfer.IImportStructureProvider,
     * com.tibco.xpd.fragments.IFragmentCategory,
     * org.eclipse.core.runtime.IProgressMonitor)
     */
    public void importProject(Object project,
            IImportStructureProvider structureProvider,
            IFragmentCategory rootCategory, IProgressMonitor monitor)
            throws CoreException {

        if (project != null && structureProvider != null) {
            List<?> children = structureProvider.getChildren(project);
            Map<Object, FragmentsProvider> folders = new HashMap<Object, FragmentsProvider>();

            // Identify root categories and their corresponding providers.
            if (children != null) {
                for (Object child : children) {
                    if (structureProvider.isFolder(child)) {
                        String folderName = structureProvider.getLabel(child);

                        if (folderName != null) {
                            FragmentsProvider provider = extensionHelper
                                    .getProvider(folderName);

                            if (provider != null) {
                                folders.put(child, provider);
                            } else {
                                FragmentsActivator
                                        .getDefault()
                                        .getLogger()
                                        .warn(
                                                String
                                                        .format(
                                                                Messages.FragmentsProjectImporter_fragmentProviderNotFound_error_message,
                                                                folderName));
                            }
                        }
                    }
                }
            }

            if (!folders.isEmpty()) {
                monitor.beginTask("", folders.size()); //$NON-NLS-1$

                try {
                    // Process each root category from the external project
                    for (Entry<Object, FragmentsProvider> folder : folders
                            .entrySet()) {
                        FragmentsProvider provider = folder.getValue();
                        monitor.subTask(provider.getName());

                        // Get the corresponding fragment root category from the
                        // workspace fragments
                        FragmentCategoryImpl category = (FragmentCategoryImpl) manager
                                .getRootCategory(provider.getId());

                        if (category != null) {
                            String exportProjectProviderVersion = getProviderVersion(
                                    provider, folder.getKey(),
                                    structureProvider);
                            String currentProviderVersion = provider
                                    .getProviderClass().getFragmentVersion();
                            boolean needsUpdate = (exportProjectProviderVersion == null && currentProviderVersion != null)
                                    || (exportProjectProviderVersion != null && !exportProjectProviderVersion
                                            .equals(currentProviderVersion));

                            processRootCategory(provider, category, folder
                                    .getKey(), structureProvider,
                                    exportProjectProviderVersion, needsUpdate,
                                    new SubProgressMonitor(monitor, 1));

                        }
                    }
                } finally {
                    monitor.done();
                }
            }
        }
    }

    /**
     * Process the root category from the external project.
     * 
     * @param provider
     *            fragment provider that contributed this root category
     * @param rootCategory
     *            the root category
     * @param folder
     *            the root category external folder
     * @param structureProvider
     *            import structure provider
     * @param fragmentVersion
     *            fragment version of the external root category that is being
     *            imported
     * @param needsUpdate
     *            <code>true</code> if the fragments need updating as version
     *            has changed
     * @param monitor
     *            progress monitor
     * @throws CoreException
     */
    private void processRootCategory(FragmentsProvider provider,
            FragmentCategoryImpl rootCategory, Object folder,
            IImportStructureProvider structureProvider, String fragmentVersion,
            boolean needsUpdate, IProgressMonitor monitor) throws CoreException {
        // Root categories will only contain categories - both system and user.
        // Process user categories only
        List<?> children = structureProvider.getChildren(folder);

        if (children != null) {
            monitor.beginTask("", children.size()); //$NON-NLS-1$

            try {
                int idx = 1;
                for (Object child : children) {
                    if (structureProvider.isFolder(child)) {
                        ImportFragmentCategory cat = new ImportFragmentCategory(
                                provider,
                                child,
                                structureProvider,
                                String
                                        .format(
                                                Messages.FragmentsProjectImporter_defaultCategoryName_label,
                                                idx++));

                        if (!cat.isSystem()) {
                            cat.doImport(rootCategory, fragmentVersion,
                                    needsUpdate);
                        }
                        monitor.worked(1);
                    }
                }
            } finally {
                monitor.done();
            }
        }
    }

    /**
     * Get the fragment version of the contribution in the external project.
     * 
     * @param provider
     *            fragment provider of the root category
     * @param rootCategory
     *            the root category in the external project
     * @param structureProvider
     *            import structure provider
     * @return the fragment contribution version
     */
    private String getProviderVersion(FragmentsProvider provider,
            Object rootCategory, IImportStructureProvider structureProvider) {
        String ver = null;

        FragmentProperties properties = ImportFragmentCategory
                .getSourceProperties(rootCategory, structureProvider);

        if (properties != null) {
            ver = properties.getProperty(provider.getId());
        }

        return ver;
    }

}
