/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.ui.wizards.datatransfer.IImportStructureProvider;

import com.tibco.xpd.fragments.internal.FragmentsManager;
import com.tibco.xpd.fragments.internal.utils.FragmentsUtil;

/**
 * Implemented by the <code>com.tibco.xpd.fragments.fragmentsImport</code>
 * extension to import an external fragments project into the workspace
 * fragments.
 * <p>
 * NOTE: If two or more extensions use instances of the same
 * <code>FragmentsImporter</code> sub-class then only one of the instances will
 * be run for the import of all projects defined in these extensions.
 * </p>
 * 
 * @author njpatel
 * 
 */
public abstract class FragmentsImporter {

    /**
     * Import the given external project into the workspace fragments.
     * <p>
     * The <i>project</i> can be a file system resource or a resource in a
     * jar/tar compressed file. Use the {@link IImportStructureProvider
     * structureProvider} to interrogate the project for it's structure and file
     * content.
     * </p>
     * <p>
     * Use
     * {@link #createCategory(IFragmentCategory, String, String, String, boolean, IProgressMonitor)
     * createCategory} and
     * {@link #createFragment(IFragmentCategory, String, String, String, String, ImageData, IProgressMonitor)
     * createFragment} to create a category and fragment respectively.
     * </p>
     * 
     * @param project
     *            the external project
     * @param structureProvider
     *            import structure provider
     * @param rootCategory
     *            import's target root category - <code>null</code> if
     *            {@link #getProviderId()} returns <code>null</code>
     * @param monitor
     *            progress monitor
     * @throws CoreException
     */
    public abstract void importProject(Object project,
            IImportStructureProvider structureProvider,
            IFragmentCategory rootCategory, IProgressMonitor monitor)
            throws CoreException;

    /**
     * Get the fragment provider id of the contributor this import will service.
     * 
     * @return provider id. If this is <code>null</code> then the
     *         {@link IFragmentCategory root category} passed to
     *         {@link #importProject(Object, IImportStructureProvider, IFragmentCategory, IProgressMonitor)
     *         importProject} will be <code>null</code>.
     */
    public abstract String getProviderId();

    /**
     * Create a category under the given parent.
     * 
     * @param parent
     *            parent category. This should not be <code>null</code>.
     * @param key
     *            unique key to identify this category in the future. This can
     *            be <code>null</code>.
     * @param name
     *            name of the category. This should not be <code>null</code>.
     * @param description
     *            short description of the category, <code>null</code> if no
     *            description required.
     * @param isSystem
     *            <code>true</code> if this is a system category,
     *            <code>false</code> if it is a user category.
     * @param monitor
     *            progress monitor if progress required, <code>null</code>
     *            otherwise.
     * @return created fragment category
     * @throws CoreException
     */
    protected IFragmentCategory createCategory(IFragmentCategory parent,
            String key, String name, String description, boolean isSystem,
            IProgressMonitor monitor) throws CoreException {
        Assert.isNotNull(parent, "Parent category when creating new category"); //$NON-NLS-1$
        Assert.isNotNull(name, "Category name when creating new category"); //$NON-NLS-1$

        return FragmentsManager.getInstance().createCategory(parent,
                FragmentsUtil.createUniqueID(),
                key,
                name,
                description,
                isSystem,
                monitor);
    }

    /**
     * Create a fragment under the given parent.
     * 
     * @param parent
     *            parent category. This should not be <code>null</code>.
     * @param key
     *            unique key to identify this category in the future. This can
     *            be <code>null</code>.
     * @param name
     *            name of the fragment. This should not be <code>null</code>.
     * @param description
     *            short description of the fragment, <code>null</code> if no
     *            description required.
     * @param data
     *            fragment data, this cannot be <code>null</code>.
     * @param imgData
     *            image data of the fragment image, or <code>null</code> if no
     *            image is available.
     * @param monitor
     *            progress monitor if progress required, <code>null</code>.
     *            otherwise
     * @return created fragment
     * @throws CoreException
     */
    protected IFragment createFragment(IFragmentCategory parent, String key,
            String name, String description, String data, ImageData imgData,
            IProgressMonitor monitor) throws CoreException {

        Assert.isNotNull(parent, "Parent category when creating new fragment"); //$NON-NLS-1$
        Assert.isNotNull(name, "Fragment name when creating new fragment"); //$NON-NLS-1$

        return FragmentsManager.getInstance().createFragment(parent,
                FragmentsUtil.createUniqueID(),
                key,
                name,
                description,
                data,
                imgData,
                monitor);
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        /*
         * If two or more extensions use instances of the same class then run
         * only one of the instances for the import of all projects defined in
         * these extensions.
         */
        if (this == obj) {
            return true;
        }

        if (obj.getClass() == getClass()) {
            return true;
        }

        return false;
    }

}
