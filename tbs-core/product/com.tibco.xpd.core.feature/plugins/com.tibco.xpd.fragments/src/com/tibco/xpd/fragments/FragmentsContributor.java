/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

import com.tibco.xpd.fragments.internal.FragmentsManager;
import com.tibco.xpd.fragments.internal.utils.FragmentsUtil;

/**
 * Fragments contributor abstract class to be implemented by contributors to the
 * <code>com.tibco.xpd.fragments.fragmentsProvider</code> extension point.
 * 
 * @author njpatel
 * 
 */
public abstract class FragmentsContributor {

    /**
     * UpdateResult used by
     * {@link FragmentsContributor#updateContent(IFragmentCategory, String, IProgressMonitor)
     * FragmentsContributor.updateContent} that is called during import of
     * fragments project. This will provide the updated fragment data and
     * indication of whether the fragment image should be imported.
     * 
     * @author njpatel
     * 
     */
    public interface UpdateResult {
        /**
         * Get the updated fragment data for the fragment being imported.
         * 
         * @return updated fragment data.
         */
        String getData();

        /**
         * Indicate whether the image for the fragment being imported should be
         * imported.
         * 
         * @return <code>true</code> if image should be imported along with the
         *         fragment, <code>false</code> otherwise.
         */
        boolean doClearImage();
    }

    /**
     * Fragment data collected from the clipboard. This contains the fragment
     * data and image descriptor. Optionally, the name and description of the
     * fragment can also be set.
     * 
     * @author njpatel
     * 
     */
    public class ClipboardFragmentData {
        private ImageData imageData;

        private String data;

        private String name;

        private String description;

        private IFragment fragment;

        /**
         * Fragment data collected from the clipboard.
         * 
         * @param data
         *            fragment data
         * @param imageData
         *            image data of the fragment image, <code>null</code> if no
         *            image is available.
         */
        public ClipboardFragmentData(String data, ImageData imageData) {
            Assert.isNotNull(data, "Fragment data"); //$NON-NLS-1$
            this.data = data;
            this.imageData = imageData;
        }

        /**
         * Fragment local transfer collected from the clipboard.
         * 
         * @param fragment
         *            copied fragment
         */
        public ClipboardFragmentData(IFragment fragment) {
            this.fragment = fragment;
            this.name = fragment.getNameLabel();
            this.description = fragment.getDescriptionLabel();
            this.data = fragment.getLocalizedData();
            this.imageData = fragment.getLocalizedImageData();
        }

        /**
         * Get the fragment that was copied if local selection transfer.
         * 
         * @return fragment if local selection transfer, <code>null</code>
         *         otherwise.
         */
        public IFragment getFragment() {
            return fragment;
        }

        /**
         * Set the name of the fragment that will be created.
         * 
         * @param name
         *            fragment name.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Set the description of the fragment that will be created.
         * 
         * @param description
         *            fragment description.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Get the name of the fragment.
         * 
         * @return fragment name, or <code>null</code> if no name is set.
         */
        public String getName() {
            return name;
        }

        /**
         * Get the description of the fragment.
         * 
         * @return fragment description, or <code>null</code> if no description
         *         is set.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Get the fragment data.
         * 
         * @return Fragment data.
         */
        public String getData() {
            return data;
        }

        /**
         * Get the image data of the fragment image.
         * 
         * @return image data, or <code>null</code> if no image available.
         */
        public ImageData getImageData() {
            return imageData;
        }
    }

    // Root category of this provider
    private IFragmentCategory rootCategory;

    /**
     * Fragments' contributor.
     */
    public FragmentsContributor() {
    }

    /**
     * Initialize the fragments of this provider. Use
     * {@link #createCategory(IFragmentCategory, String, String, String, IProgressMonitor)
     * createCategory} and
     * {@link #createFragment(IFragmentCategory, String, String, String, String, ImageData, IProgressMonitor)
     * createFragment} to create fragment categories and fragments respectively.
     * 
     * @param monitor
     *            progress monitor to report progress.
     */
    public abstract void initialize(IProgressMonitor monitor)
            throws CoreException;

    /**
     * Update the contents of this fragment contribution. This will be called
     * when the fragment {@link #getFragmentVersion() version} does not match
     * the stored version. This will allow this contributor to update/migrate
     * the fragments/categories.
     * <p>
     * Note - the stored fragment version of this contributor will be updated
     * after a call to this method.
     * </p>
     * 
     * @param rootCategory
     *            this contributor's root category
     * @param fragmentVersion
     *            the currently stored fragment version
     * @param monitor
     *            progress monitor
     * @throws CoreException
     */
    public abstract void updateContent(final IFragmentCategory rootCategory,
            String fragmentVersion, IProgressMonitor monitor)
            throws CoreException;

    /**
     * Get the updated content of this fragment based on the current fragment
     * version. This method will be called during import of external fragment
     * projects.
     * 
     * @param fragment
     *            fragment to update
     * @param fragmentVersion
     *            fragment version of the fragments project being imported.
     * @return <code>UpdateResult</code> containing the updated result and
     *         indication of whether to clear the associated image or import it.
     */
    public abstract UpdateResult updateContent(final IFragment fragment,
            String fragmentVersion);

    /**
     * Get the localized name of the given fragment element.
     * 
     * @param element
     *            fragment element.
     * @return localized name, or <code>null</code> if no translation is
     *         available in which case the default name will be used.
     */
    public abstract String getLocalizedName(IFragmentElement element);

    /**
     * Get the localized description of the given fragment element.
     * 
     * @param element
     *            fragment element
     * @return localized description, or <code>null</code> if no translation is
     *         available in which case the default description will be used.
     */
    public abstract String getLocalizedDescription(IFragmentElement element);

    /**
     * Get localized fragment data from the given locale.
     * <p>
     * NOTE: DO NOT call {@link IFragment#getLocalizedData()} as it initiates
     * the call to this method.
     * </p>
     * 
     * @param fragment
     *            fragment.
     * @param NL
     *            locale string name
     * @return localized fragment data, or <code>null</code> if no translation
     *         is available in which case the default data will be used.
     */
    public abstract String getLocalizedData(IFragment fragment, String NL);

    /**
     * Get the localized image data of the given fragment in the locale given.
     * If an image data is returned it will be stored locally for future use and
     * this contributor will not be asked again.
     * <p>
     * NOTE: DO NOT call {@link IFragment#getLocalizedImageData()} as it
     * initiates the call to this method.
     * </p>
     * 
     * @param fragment
     *            fragment to get the image of
     * @param NL
     *            locale
     * @return <code>ImageData</code>, or <code>null</code> if no translated
     *         image is available in which case it will fall back to the image
     *         it has.
     */
    public abstract ImageData getLocalizedImageData(IFragment fragment,
            final String NL);

    /**
     * Get the current fragment (contribution) version from the contributor.
     * 
     * @return fragment version.
     */
    public abstract String getFragmentVersion();

    /**
     * Get an icon for the fragment. This icon will be displayed in the fragment
     * view tree. Default implementation returns <code>null</code>.
     * 
     * @param fragment
     *            fragment to get icon for.
     * @return <code>Image</code> or <code>null</code> if the default image
     *         should be used.
     */
    public Image getIcon(IFragmentElement fragment) {
        return null;
    }

    /**
     * Copy the given fragment to the clipboard.
     * 
     * @param fragment
     *            fragment to copy to clipboard.
     */
    public abstract void copyToClipboard(IFragment fragment);

    /**
     * Get the data from the clipboard (if any) to create a fragment from.
     * 
     * @param cat
     *            target fragment category where the fragment will be created.
     * @return fragment data if a fragment can be created, <code>null</code>
     *         otherwise.
     */
    public abstract ClipboardFragmentData getFromClipboard(IFragmentCategory cat);

    /**
     * Set the fragment contributor root category.
     * 
     * @param category
     */
    public final void setRootCategory(IFragmentCategory category) {
        this.rootCategory = category;
    }

    /**
     * Get the root category for this contributor.
     * 
     * @return root fragment category.
     */
    public IFragmentCategory getRootCategory() {
        return rootCategory;
    }

    /**
     * Create a category under the given parent. NOTE: If the
     * {@link IFragmentCategory parent} is null then the root category of this
     * contributor will be used.
     * 
     * @param parent
     *            parent category. If <code>null</code> this will be set to the
     *            root category.
     * @param key
     *            unique key to identify this category in the future. This can
     *            be <code>null</code>.
     * @param name
     *            name of the category. This should not be <code>null</code>.
     * @param description
     *            short description of the category, <code>null</code> if no
     *            description required.
     * @param monitor
     *            progress monitor if progress required, <code>null</code>
     *            otherwise.
     * @return created fragment category
     * @throws CoreException
     */
    protected IFragmentCategory createCategory(IFragmentCategory parent,
            String key, String name, String description,
            IProgressMonitor monitor) throws CoreException {

        Assert.isNotNull(name, "Category name when creating new category"); //$NON-NLS-1$

        if (parent == null) {
            parent = getRootCategory();
        }

        return FragmentsManager.getInstance().createCategory(parent,
                FragmentsUtil.createUniqueID(),
                key,
                name,
                description,
                true,
                monitor);
    }

    /**
     * Delete the given fragment element.
     * 
     * @param element
     *            fragment element to delete
     * @param monitor
     *            progress monitor, <code>null</code> if no progress required.
     * @throws CoreException
     */
    protected void deleteFragmentElement(IFragmentElement element,
            IProgressMonitor monitor) throws CoreException {
        FragmentsManager.getInstance().deleteFragmentElement(element, monitor);
    }

    /**
     * Create a fragment under the given parent. NOTE: If the
     * {@link IFragmentCategory parent} is null then the root category of this
     * contributor will be used.
     * 
     * @param parent
     *            parent category. If <code>null</code> this will be set to the
     *            root category.
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

        Assert.isNotNull(name, "Fragment name when creating new fragment"); //$NON-NLS-1$

        if (parent == null) {
            parent = getRootCategory();
        }

        return FragmentsManager.getInstance().createFragment(parent,
                FragmentsUtil.createUniqueID(),
                key,
                name,
                description,
                data,
                imgData,
                monitor);
    }

}
