/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.impl;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.fragments.internal.FragmentConsts;
import com.tibco.xpd.fragments.internal.FragmentsExtensionHelper.FragmentsProvider;

/**
 * Fragment element. Implementation of {@link IFragmentElement}.
 * 
 * @author njpatel
 * 
 */
public abstract class FragmentElementImpl implements IFragmentElement {

    private static final String NULL_KEY = "~NULL~"; //$NON-NLS-1$

    private final IResource resource;
    private String id;
    private String name;
    private String nameLabel;
    private String description;
    private String descriptionLabel;
    private String key;

    /**
     * Fragment element.
     * 
     * @param resource
     *            resource that represents this fragment element.
     */
    public FragmentElementImpl(final IResource resource) {
        Assert.isNotNull(resource, "resource"); //$NON-NLS-1$
        this.resource = resource;
    }

    /**
     * Get the fragment provider that contributed this element.
     * 
     * @return Fragments provider.
     */
    public abstract FragmentsProvider getProvider();

    /**
     * Get the fragment element resource.
     * 
     * @return resource.
     */
    public IResource getResource() {
        return resource;
    }

    /**
     * Get all resources associated with this fragment element.
     * 
     * @return collection of resources.
     */
    public Collection<IResource> getAllResources() {
        return Collections.singleton(getResource());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragmentElement#getId()
     */
    public String getId() {
        if (id == null && getResource() != null) {
            id = getResource().getName();

            if (getResource() instanceof IFile && id.lastIndexOf('.') > 0) {
                // Remove file extension
                id = id.substring(0, id.lastIndexOf('.'));
            }
        }
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragmentElement#getKey()
     */
    public String getKey() {
        if (key == null) {
            FragmentProperties props = getProperties();

            if (props != null) {
                key = props.getProperty(getPropertyKey(FragmentConsts.KEY));
            }

            // Set a NULL value so that it doesn't keep reading the properties
            // for the value if the key is not provided by the provider
            if (key == null) {
                key = NULL_KEY;
            }
        }

        return !key.equals(NULL_KEY) ? key : null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragmentElement#getName()
     */
    public String getName() {

        if (name == null) {
            FragmentProperties props = getProperties();
            if (props != null) {
                name = props.getProperty(getPropertyKey(FragmentConsts.NAME));
            }

            if (name == null) {
                name = ""; //$NON-NLS-1$
            }
        }
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragmentElement#getNameLabel()
     */
    public String getNameLabel() {
        if (nameLabel == null) {
            // Get the localized name from the fragment provider
            if (isSystem()) {
                try {
                    if (getProvider() != null
                            && getProvider().getProviderClass() != null) {

                        String localizedName = getProvider().getProviderClass()
                                .getLocalizedName(this);
                        if (localizedName != null) {
                            nameLabel = localizedName;
                        }
                    }
                } catch (CoreException e) {
                    FragmentsActivator.getDefault().getLogger().error(e);
                }
            }

            if (nameLabel == null) {
                nameLabel = getName();
            }
        }

        return nameLabel;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragmentElement#getDescription()
     */
    public String getDescription() {
        if (description == null) {
            FragmentProperties props = getProperties();
            if (props != null) {
                description = props
                        .getProperty(getPropertyKey(FragmentConsts.DESCRIPTION));
            }

            if (description == null) {
                description = ""; //$NON-NLS-1$
            }
        }
        return description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragmentElement#getDescriptionLabel()
     */
    public String getDescriptionLabel() {
        if (descriptionLabel == null) {
            // Get localized description from the fragment provider
            if (isSystem()) {
                try {
                    if (getProvider() != null
                            && getProvider().getProviderClass() != null) {
                        String localizedDescription = getProvider()
                                .getProviderClass().getLocalizedDescription(
                                        this);

                        if (localizedDescription != null) {
                            descriptionLabel = localizedDescription;
                        }
                    }
                } catch (CoreException e) {
                    FragmentsActivator.getDefault().getLogger().error(e);
                }
            }

            if (descriptionLabel == null) {
                descriptionLabel = getDescription();
            }
        }

        return descriptionLabel;
    }

    /**
     * Get the fragment properties.
     * 
     * @return properties
     */
    protected abstract FragmentProperties getProperties();

    /**
     * Set the details of this fragment element
     * 
     * @param key
     *            unique key provided by the fragment provider for this element,
     *            this can be <code>null</code>.
     * @param name
     *            name of this element.
     * @param description
     *            short description of this element, this can be
     *            <code>null</code>.
     * @throws CoreException
     *             thrown if failed to update the properties.
     */
    public void setDetails(String key, String name, String description)
            throws CoreException {
        this.key = key != null ? key : NULL_KEY;
        this.name = name;
        this.description = description;

        // Reset the name and description labels
        this.nameLabel = null;
        this.descriptionLabel = null;

        FragmentProperties props = getProperties();

        if (props != null) {

            // Description may be blank string so set to null, otherwise it will
            // add unnecessary line in properties file
            if (description != null && description.length() == 0) {
                description = null;
            }

            props.setProperty(getPropertyKey(FragmentConsts.KEY), key);
            props.setProperty(getPropertyKey(FragmentConsts.NAME), name);
            props.setProperty(getPropertyKey(FragmentConsts.DESCRIPTION),
                    description);

            props.save();
        }
    }

    /**
     * Clear the details of this element from the fragments properties.
     * 
     * @throws CoreException
     */
    protected void clearDetails() throws CoreException {
        FragmentProperties props = getProperties();
        if (props != null) {
            props.remove(getPropertyKey(FragmentConsts.KEY));
            props.remove(getPropertyKey(FragmentConsts.NAME));
            props.remove(getPropertyKey(FragmentConsts.DESCRIPTION));

            props.save();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragmentElement#dispose()
     */
    public void dispose() {
        // Do nothing
    }

    @Override
    public int hashCode() {
        if (resource != null) {
            return resource.hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (obj instanceof FragmentElementImpl) {
            FragmentElementImpl other = (FragmentElementImpl) obj;

            if (resource != null && resource.equals(other.getResource())) {
                return true;
            }
        }

        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getName() + "(" //$NON-NLS-1$
                + getResource().getProjectRelativePath().toString() + ")"; //$NON-NLS-1$
    }

    /**
     * Get the property key for the part.
     * 
     * @param part
     *            key part of this element to store in the properties.
     * @return
     */
    protected String getPropertyKey(String part) {
        return FragmentProperties.getPropertyKey(getId(), part);
    }

}
