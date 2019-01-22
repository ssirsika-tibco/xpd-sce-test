/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.IContainedFragmentElement;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.fragments.internal.FragmentConsts;
import com.tibco.xpd.fragments.internal.FragmentsManager;
import com.tibco.xpd.fragments.internal.FragmentsViewPart;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.fragments.internal.FragmentsExtensionHelper.FragmentsProvider;

/**
 * Fragment category. Implementation of {@link IFragmentCategory}.
 * 
 * @author njpatel
 * 
 */
public class FragmentCategoryImpl extends FragmentElementImpl implements
        IFragmentCategory {

    private FragmentCategoryImpl parent;
    private final FragmentResourceListener listener;
    private Boolean isSystem = null;
    private Set<IFragmentElement> children;
    private FragmentProperties properties;

    // Pattern to match a localized fragment file name
    private static final Pattern LOCALIZED_FRAGMENT_NAME_PATTERN = Pattern
            .compile("_.+_(_[a-zA-Z]{2,3})+\\." //$NON-NLS-1$
                    + FragmentConsts.FRAGMENT_FILE_EXT);

    /**
     * Fragment category.
     * 
     * @param folder
     *            holder of this category in the workspace.
     */
    public FragmentCategoryImpl(IFolder folder) {
        super(folder);

        listener = new FragmentResourceListener(getResource());
        FragmentsManager.getInstance().addResourceListener(listener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragmentCategory#getChildren()
     */
    public Collection<IFragmentElement> getChildren() {

        try {
            if (children == null) {
                children = new HashSet<IFragmentElement>();
                final IResource res = getResource();

                if (res != null && res.exists()) {
                    res.accept(new IResourceVisitor() {

                        public boolean visit(IResource resource)
                                throws CoreException {

                            if (resource.equals(res)) {
                                return true;
                            }
                            FragmentElementImpl elem = null;
                            if (resource instanceof IFolder) {
                                elem = new FragmentCategoryImpl(
                                        (IFolder) resource);
                            } else if (resource instanceof IFile) {
                                IFile file = (IFile) resource;

                                if (file.getFileExtension() != null
                                        && file
                                                .getFileExtension()
                                                .equals(
                                                        FragmentConsts.FRAGMENT_FILE_EXT)) {
                                    // Don't include localized fragment files
                                    Matcher matcher = LOCALIZED_FRAGMENT_NAME_PATTERN
                                            .matcher(file.getName());
                                    if (!matcher.matches()) {
                                        elem = new FragmentImpl(
                                                (IFile) resource);
                                    }
                                }
                            }

                            if (elem != null) {
                                setElementsParent(elem,
                                        FragmentCategoryImpl.this);
                                children.add(elem);
                            }
                            return false;
                        }

                    });
                }
            }
        } catch (CoreException e) {
            FragmentsActivator.getDefault().getLogger().error(e);
        }

        return new HashSet<IFragmentElement>(children);
    }

    /**
     * Add the given element to the children list of this category. If the
     * element is a {@link IContainedFragmentElement contained} element then
     * it's parent will be updated.
     * 
     * @param element
     */
    private void addChild(IFragmentElement element) {
        if (element != null) {
            setElementsParent(element, this);
            if (children == null) {
                children = new HashSet<IFragmentElement>();
            }
            children.add(element);
        }
    }

    /**
     * Set this category as this element's parent.
     * 
     * @param element
     *            element to set parent of.
     * @param parent
     *            the parent
     */
    private void setElementsParent(IFragmentElement element,
            IFragmentCategory parent) {
        if (element instanceof FragmentCategoryImpl) {
            ((FragmentCategoryImpl) element).setParent(parent);
        } else if (element instanceof FragmentImpl) {
            ((FragmentImpl) element).setParent(parent);
        }
    }

    /**
     * Create a child fragment of this category.
     * 
     * @param fragmentFile
     *            fragment file
     * @return fragment
     */
    public FragmentImpl createChildFragment(IFile fragmentFile) {
        Assert.isNotNull(fragmentFile, "Fragment file"); //$NON-NLS-1$
        FragmentImpl frag = new FragmentImpl(fragmentFile);
        addChild(frag);

        return frag;
    }

    /**
     * Create child category of this category.
     * 
     * @param categoryFolder
     *            category folder
     * @return category
     */
    public FragmentCategoryImpl createChildCategory(IFolder categoryFolder) {
        Assert.isNotNull(categoryFolder, "Fragment category folder"); //$NON-NLS-1$
        FragmentCategoryImpl cat = new FragmentCategoryImpl(categoryFolder);
        addChild(cat);

        return cat;
    }

    /**
     * Remove the given element from the children list of the category. If the
     * element is a {@link IContainedFragmentElement contained} element then
     * it's parent will be set to <code>null</code>.
     * 
     * @param element
     */
    public void removeChild(IFragmentElement element) {
        if (children != null) {
            // Clear the details from the properties
            try {
                ((FragmentElementImpl) element).clearDetails();
            } catch (CoreException e) {
                FragmentsActivator.getDefault().getLogger().error(e);
            }

            setElementsParent(element, null);
            children.remove(element);
        }
    }

    /**
     * Clear the children list. If a child is a category then call dispose on it
     * to remove any listeners. This will force re-load of the children from the
     * workspace when {@link #getChildren()} is next called.
     */
    public void clearChildren() {
        if (children != null) {
            for (IFragmentElement child : children) {
                child.dispose();
            }
            children.clear();
            children = null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragmentElement#isSystem()
     */
    public boolean isSystem() {
        if (isSystem == null) {
            FragmentProperties props = getProperties();

            if (props != null) {
                String value = props
                        .getProperty(getPropertyKey(FragmentConsts.SYSTEM));

                if (value != null) {
                    isSystem = Boolean.parseBoolean(value);
                }

                if (value == null) {
                    isSystem = Boolean.FALSE;
                }
            }
        }
        return isSystem != null ? isSystem : false;
    }

    /**
     * Set the isSystem property.
     * 
     * @param value
     *            <code>true</code> if this is a system category,
     *            <code>false</code> otherwise.
     * @throws CoreException
     */
    public void setIsSystem(boolean value) throws CoreException {
        isSystem = value;
        // Update the resource property
        FragmentProperties props = getProperties();

        if (props != null) {
            props.setProperty(getPropertyKey(FragmentConsts.SYSTEM),
                    value ? Boolean.toString(value) : null);
            props.save();
        }
    }

    @Override
    protected void clearDetails() throws CoreException {
        FragmentProperties props = getProperties();
        if (props != null) {
            props.remove(getPropertyKey(FragmentConsts.SYSTEM));
        }

        super.clearDetails();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.fragments.internal.impl.FragmentElementImpl#getProvider()
     */
    public FragmentsProvider getProvider() {
        return parent != null ? parent.getProvider() : null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.internal.impl.FragmentElementImpl#dispose()
     */
    public void dispose() {
        FragmentsManager.getInstance().removeResourceListener(listener);

        if (children != null) {
            for (IFragmentElement child : children) {
                child.dispose();
            }
        }
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
     * Fragment resource listener.
     * 
     * @author njpatel
     * 
     */
    private class FragmentResourceListener implements IResourceChangeListener {

        private final IResource resource;

        /**
         * Fragment resource listener
         * 
         * @param resource
         */
        public FragmentResourceListener(IResource resource) {
            this.resource = resource;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.core.resources.IResourceChangeListener#resourceChanged
         * (org.eclipse.core.resources.IResourceChangeEvent)
         */
        public void resourceChanged(IResourceChangeEvent event) {
            boolean reloadChildren = false;
            IResourceDelta delta = event.getDelta().findMember(
                    resource.getFullPath());
            if (delta != null) {
                IResourceDelta[] affectedChildren = delta.getAffectedChildren();

                for (IResourceDelta resourceDelta : affectedChildren) {
                    if (resourceDelta.getKind() != IResourceDelta.CHANGED) {
                        // Resource has been added or removed
                        reloadChildren = true;
                    } else {
                        // Check if content of file has changed
                        reloadChildren = (resourceDelta.getFlags() & IResourceDelta.CONTENT) != 0;
                    }

                    // If has children and need reloading then do so and update
                    // the view
                    if (children != null && reloadChildren) {
                        clearChildren();
                        FragmentsViewPart viewPart = FragmentsManager
                                .getInstance().getFragmentsViewPart();
                        if (viewPart != null) {
                            viewPart.refresh(FragmentCategoryImpl.this);
                        }
                        break;
                    }
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.fragments.IContainedFragmentElement#setParent(com.tibco
     * .xpd.fragments.IFragmentCategory)
     */
    public void setParent(IFragmentCategory parent) {
        this.parent = (FragmentCategoryImpl) parent;
    }

    /**
     * Get the next available default child category name in this category.
     * 
     * @return category name
     */
    public String getNextCategoryName() {
        return getUniqueName(
                Messages.FragmentCategoryImpl_newCategoryName_label,
                Messages.FragmentCategoryImpl_newCategoryName_pattern_label,
                IFragmentCategory.class);
    }

    /**
     * Get the next available default child fragment name in this category.
     * 
     * @return fragment name
     */
    public String getNextFragmentName() {
        return getUniqueName(
                Messages.FragmentCategoryImpl_newFragmentName_label,
                Messages.FragmentCategoryImpl_newFragmentName_pattern_label,
                IFragment.class);
    }

    /**
     * Find the next available unique name.
     * 
     * @param name
     *            default name
     * @param pattern
     *            name pattern
     * @return unique name under this category
     */
    private String getUniqueName(String name, String pattern,
            Class<? extends IFragmentElement> elementType) {

        // Get all the used names
        Collection<IFragmentElement> children = getChildren();
        Set<String> names = new HashSet<String>();

        for (IFragmentElement child : children) {
            if (elementType.isInstance(child)) {
                String childName = child.getName();

                if (childName != null) {
                    names.add(childName);
                }
            }
        }
        int idx = 1;
        while (names.contains(name)) {
            name = String.format(pattern, idx++);
        }

        return name;
    }

    @Override
    public FragmentProperties getProperties() {

        if (properties == null && getResource() instanceof IFolder) {
            IFile file = ((IFolder) getResource())
                    .getFile(FragmentConsts.FRAGMENTS_PROPERTIES);
            try {
                properties = new FragmentProperties(file);
            } catch (CoreException e) {
                FragmentsActivator.getDefault().getLogger().error(e);
            }
        }

        return properties;
    }

}
