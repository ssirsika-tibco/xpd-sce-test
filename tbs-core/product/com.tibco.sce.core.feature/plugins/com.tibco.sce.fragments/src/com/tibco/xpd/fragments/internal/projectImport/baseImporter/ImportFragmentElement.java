/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.projectImport.baseImporter;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.wizards.datatransfer.IImportStructureProvider;

import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.fragments.internal.FragmentConsts;
import com.tibco.xpd.fragments.internal.FragmentsExtensionHelper.FragmentsProvider;
import com.tibco.xpd.fragments.internal.impl.FragmentCategoryImpl;
import com.tibco.xpd.fragments.internal.impl.FragmentProperties;

/**
 * The fragment element in the external project being imported.
 * 
 * @author njpatel
 * 
 */
public abstract class ImportFragmentElement implements IFragmentElement {
    private String id;
    private String name;
    private String description;
    private String key;
    private final FragmentsProvider provider;
    private final Object importSource;
    private final IImportStructureProvider structureProvider;
    private final FragmentProperties properties;
    private String resourceName;

    /**
     * The fragment element in the external project being imported.
     * 
     * @param provider
     *            fragment provider that contributed this element
     * @param importSource
     *            element source in the external project
     * @param structureProvider
     *            import structure provider
     * @param properties
     *            fragment properties file for this element
     * @param defaultName
     *            default name of the element if the element's name is not found
     *            in the properties
     */
    public ImportFragmentElement(FragmentsProvider provider,
            Object importSource, IImportStructureProvider structureProvider,
            FragmentProperties properties, String defaultName) {
        this.properties = properties;
        Assert.isNotNull(importSource, "Fragment import source"); //$NON-NLS-1$
        Assert.isNotNull(structureProvider, "Import structure provider"); //$NON-NLS-1$
        this.provider = provider;
        this.importSource = importSource;
        this.structureProvider = structureProvider;

        this.resourceName = structureProvider.getLabel(importSource);

        if (properties != null) {
            id = structureProvider.getLabel(importSource);
            // Remove file extension
            int idx = id.lastIndexOf('.');
            if (idx > 0) {
                id = id.substring(0, idx);
            }

            key = properties.getProperty(FragmentProperties.getPropertyKey(id,
                    FragmentConsts.KEY));
            name = properties.getProperty(FragmentProperties.getPropertyKey(id,
                    FragmentConsts.NAME));
            description = properties.getProperty(FragmentProperties
                    .getPropertyKey(id, FragmentConsts.DESCRIPTION));
        }
        // Use default name if no name was found
        if (name == null) {
            name = defaultName;
        }

    }

    /**
     * Get the resource name of the external fragment element resource.
     * 
     * @return resource name
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * Get the source of this element in the external project
     * 
     * @return source
     */
    public Object getImportSource() {
        return importSource;
    }

    /**
     * Get the import source provider.
     * 
     * @return import source provider
     */
    public IImportStructureProvider getStructureProvider() {
        return structureProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragmentElement#dispose()
     */
    public void dispose() {
        // Nothing to dispose
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragmentElement#getDescription()
     */
    public String getDescription() {
        return description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragmentElement#getDescriptionLabel()
     */
    public String getDescriptionLabel() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragmentElement#getId()
     */
    public String getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragmentElement#getKey()
     */
    public String getKey() {
        return key;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragmentElement#getName()
     */
    public String getName() {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragmentElement#getNameLabel()
     */
    public String getNameLabel() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragmentElement#getProvider()
     */
    public FragmentsProvider getProvider() {
        return provider;
    }

    /**
     * Import this element from the external project.
     * 
     * @param targetCategory
     *            the target category of this import
     * @param fragmentVersion
     *            the fragment version in the external project
     * @param needsUpdate
     *            <code>true</code> if the fragment needs updating to current
     *            version
     * @throws CoreException
     */
    public abstract void doImport(FragmentCategoryImpl targetCategory,
            String fragmentVersion, boolean needsUpdate) throws CoreException;

    /**
     * Get the fragment properties
     * 
     * @return fragment properties if present, <code>null</code> otherwise.
     */
    protected FragmentProperties getProperties() {
        return properties;
    }

}
