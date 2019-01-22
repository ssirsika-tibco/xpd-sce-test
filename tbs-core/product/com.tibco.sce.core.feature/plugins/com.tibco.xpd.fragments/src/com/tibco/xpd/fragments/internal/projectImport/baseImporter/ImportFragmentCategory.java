/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.projectImport.baseImporter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.wizards.datatransfer.IImportStructureProvider;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.fragments.internal.FragmentConsts;
import com.tibco.xpd.fragments.internal.FragmentsManager;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.fragments.internal.FragmentsExtensionHelper.FragmentsProvider;
import com.tibco.xpd.fragments.internal.impl.FragmentCategoryImpl;
import com.tibco.xpd.fragments.internal.impl.FragmentImpl;
import com.tibco.xpd.fragments.internal.impl.FragmentProperties;

/**
 * The fragment category in the external project being imported.
 * 
 * @author njpatel
 * 
 */
public class ImportFragmentCategory extends ImportFragmentElement implements
        IFragmentCategory {

    private IFragmentCategory parent;
    private Collection<IFragmentElement> children;
    private boolean isSystem = false;
    private final FragmentsManager manager;

    /**
     * Fragment and its image file.
     * 
     * @author njpatel
     * 
     */
    private class Fragment {
        Object fragmentResource;
        Object imgResource;
    }

    /**
     * The fragment category in the external project being imported.
     * 
     * @param provider
     *            fragment provider that contributed this category
     * @param importCategorySource
     *            category source in the external project
     * @param structureProvider
     *            import structure provider
     * @param defaultName
     *            default category name incase the original name is not found in
     *            the category's properties.
     */
    public ImportFragmentCategory(FragmentsProvider provider,
            Object importCategorySource,
            IImportStructureProvider structureProvider, String defaultName) {
        super(provider, importCategorySource, structureProvider,
                getSourceProperties(importCategorySource, structureProvider),
                defaultName);

        manager = FragmentsManager.getInstance();

        FragmentProperties prop = getProperties();

        // Determine system status
        if (prop != null) {
            String val = prop.getProperty(FragmentProperties.getPropertyKey(
                    getId(), FragmentConsts.SYSTEM));
            if (val != null) {
                isSystem = Boolean.parseBoolean(val);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragmentCategory#getChildren()
     */
    public Collection<IFragmentElement> getChildren() {

        if (children == null) {
            children = new ArrayList<IFragmentElement>();

            List<?> exportChildren = getStructureProvider().getChildren(
                    getImportSource());

            if (exportChildren != null) {
                Map<String, Fragment> importFragments = new HashMap<String, Fragment>();
                int catIdx = 1;
                int fragIdx = 1;

                for (Object exportChild : exportChildren) {
                    if (getStructureProvider().isFolder(exportChild)) {
                        // Folder found so assume it's a category
                        ImportFragmentCategory cat = new ImportFragmentCategory(
                                getProvider(),
                                exportChild,
                                getStructureProvider(),
                                String
                                        .format(
                                                Messages.ImportFragmentCategory_categoryDefaultName_label,
                                                catIdx++));
                        cat.setParent(this);
                        children.add(cat);
                    } else {
                        // File found so check if fragment file or image file
                        String fileName = getStructureProvider().getLabel(
                                exportChild);

                        if (fileName != null) {
                            if (fileName.endsWith("." //$NON-NLS-1$
                                    + FragmentConsts.FRAGMENT_FILE_EXT)
                                    || (fileName.endsWith("." //$NON-NLS-1$
                                            + FragmentImpl.IMG_FILE_EXT))) {
                                // Fragments or image file
                                String id = fileName.substring(0, fileName
                                        .lastIndexOf('.'));

                                Fragment fragment = importFragments.get(id);

                                if (fragment == null) {
                                    fragment = new Fragment();
                                    importFragments.put(id, fragment);
                                }

                                if (fileName.endsWith("." //$NON-NLS-1$
                                        + FragmentConsts.FRAGMENT_FILE_EXT)) {
                                    // Fragment file
                                    fragment.fragmentResource = exportChild;
                                } else {
                                    // Image file
                                    fragment.imgResource = exportChild;
                                }
                            }
                        }
                    }
                }

                // Process all found fragments
                if (!importFragments.isEmpty()) {
                    for (Entry<String, Fragment> entry : importFragments
                            .entrySet()) {

                        Fragment fragment = entry.getValue();

                        if (fragment.fragmentResource != null) {
                            ImportFragment frag = new ImportFragment(
                                    getProvider(),
                                    fragment.fragmentResource,
                                    fragment.imgResource,
                                    getStructureProvider(),
                                    getProperties(),
                                    String
                                            .format(
                                                    Messages.ImportFragmentCategory_defaultFragmentName_label,
                                                    fragIdx++));
                            frag.setParent(this);
                            children.add(frag);
                        }
                    }
                }
            }
        }
        return children;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragmentElement#isSystem()
     */
    public boolean isSystem() {
        return isSystem;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IContainedFragmentElement#getParent()
     */
    public IFragmentCategory getParent() {
        return parent;
    }

    /**
     * Set the category's parent.
     * 
     * @param parent
     *            category
     */
    public void setParent(IFragmentCategory parent) {
        this.parent = parent;
    }

    @Override
    public void doImport(FragmentCategoryImpl targetCategory,
            String fragmentVersion, boolean needsUpdate) throws CoreException {
        if (targetCategory != null
                && targetCategory.getResource() instanceof IFolder
                && targetCategory.getResource().isAccessible()) {
            IFolder targetFolder = (IFolder) targetCategory.getResource();
            String folderName = getResourceName();

            if (folderName != null) {
                FragmentCategoryImpl category = null;
                // Check if the folder with the same name already exists in the
                // workspace
                final IFolder folder = targetFolder.getFolder(folderName);

                if (!folder.exists()) {
                    // Create the category folder
                    manager.runWorkspaceOperation(new IWorkspaceRunnable() {

                        public void run(IProgressMonitor monitor)
                                throws CoreException {
                            folder.create(false, true, monitor);
                        }

                    }, targetFolder, null);

                    category = targetCategory.createChildCategory(folder);
                    category.setDetails(getKey(), getName(), getDescription());

                } else {
                    // Folder already exists in the workspace fragments so find
                    // category that this folder is associated with
                    Collection<IFragmentElement> children = targetCategory
                            .getChildren();
                    for (IFragmentElement child : children) {
                        if (child instanceof FragmentCategoryImpl
                                && ((FragmentCategoryImpl) child).getResource()
                                        .equals(folder)) {
                            category = (FragmentCategoryImpl) child;
                            break;
                        }
                    }
                }

                if (category != null) {
                    // Create each child
                    Collection<IFragmentElement> myChildren = getChildren();

                    if (myChildren != null) {
                        for (IFragmentElement child : myChildren) {
                            ((ImportFragmentElement) child).doImport(category,
                                    fragmentVersion, needsUpdate);
                        }
                    }
                }
            }
        }
    }

    /**
     * Get the properties file from the source (external) category.
     * 
     * @param category
     *            category object in the external project
     * @param structureProvider
     *            import structure provider
     * @return <code>FragmentProperties</code> if found, <code>null</code>
     *         otherwise.
     * @throws CoreException
     */
    public static FragmentProperties getSourceProperties(Object category,
            IImportStructureProvider structureProvider) {
        FragmentProperties prop = null;

        if (category != null && structureProvider != null) {
            List<?> children = structureProvider.getChildren(category);

            if (children != null) {
                for (Object child : children) {
                    if (structureProvider.getLabel(child).equals(
                            FragmentConsts.FRAGMENTS_PROPERTIES)) {

                        InputStream contents = structureProvider
                                .getContents(child);
                        try {
                            prop = new FragmentProperties(contents);
                        } catch (CoreException e) {
                            FragmentsActivator
                                    .getDefault()
                                    .getLogger()
                                    .warn(
                                            e,
                                            Messages.ImportFragmentCategory_errorAccessingProperties_message);
                            prop = null;
                        } finally {
                            try {
                                contents.close();
                            } catch (IOException e) {
                                // Do nothing
                            }
                        }
                        break;
                    }
                }
            }
        }

        return prop;
    }

}
