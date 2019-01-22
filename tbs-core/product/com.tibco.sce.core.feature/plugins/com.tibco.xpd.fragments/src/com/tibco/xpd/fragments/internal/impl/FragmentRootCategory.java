/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.impl;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.internal.FragmentsExtensionHelper.FragmentsProvider;

/**
 * Fragment root category.
 * 
 * @author njpatel
 * 
 */
public class FragmentRootCategory extends FragmentCategoryImpl {

    private String fragVer;
    private final FragmentsProvider provider;
    private String name;
    private String description;

    /**
     * Fragment root category
     * 
     * @param folder
     *            root folder
     * @param provider
     *            fragments provider of this root.
     */
    public FragmentRootCategory(IFolder folder, FragmentsProvider provider) {
        super(folder);
        Assert.isNotNull(provider, "Fragment provider"); //$NON-NLS-1$
        this.provider = provider;

        // Use the name and description from the extension
        if (provider.getName() != null) {
            name = provider.getName();
        }

        if (provider.getDescription() != null) {
            description = provider.getDescription();
        }

        // Set the provider's root category
        try {
            this.provider.getProviderClass().setRootCategory(this);
        } catch (CoreException e) {
            FragmentsActivator.getDefault().getLogger().error(e);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNameLabel() {
        /*
         * The name is already translated as it comes from the extension.
         */
        return getName();
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getDescriptionLabel() {
        /*
         * The description is already translated as it comes from the extension.
         */
        return getDescription();
    }

    @Override
    public String getKey() {
        // Root category has no key
        return null;
    }

    @Override
    public boolean isSystem() {
        return true;
    }

    @Override
    public void setDetails(String key, String name, String description)
            throws CoreException {
        // Do nothing - the details will come from the provider extension.
    }

    @Override
    public void setIsSystem(boolean value) throws CoreException {
        // Do nothing as this is a system category
    }

    /**
     * Set the fragment contribution version.
     * 
     * @param version
     * @throws CoreException
     */
    public void setFragmentVersion(String version) throws CoreException {
        FragmentProperties props = getProperties();

        if (props != null) {
            props.setProperty(provider.getId(), version);
            props.save();
        }
    }

    /**
     * Get the current fragment contribution version.
     * 
     * @return
     */
    public String getFragmentVersion() {

        if (fragVer == null) {
            FragmentProperties props = getProperties();

            if (props != null) {
                fragVer = props.getProperty(provider.getId());
            }
        }

        return fragVer;
    }

    @Override
    public FragmentsProvider getProvider() {
        return provider;
    }

}
