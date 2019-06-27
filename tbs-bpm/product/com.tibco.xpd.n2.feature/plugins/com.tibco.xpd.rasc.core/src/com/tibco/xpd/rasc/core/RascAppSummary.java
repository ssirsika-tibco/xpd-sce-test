/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.core;

import java.util.Collection;

import org.eclipse.core.runtime.CoreException;

import com.tibco.bpm.dt.rasc.Version;
import com.tibco.bpm.dt.rasc.VersionRange;

/**
 * Provides summary information about the application being deployed.
 *
 * @see RascContext#getAppSummary()
 * @author pwatson
 * @since 25 June 2019
 */
public interface RascAppSummary {
    /**
     * Returns the name of the application. This is it's user friendly name.
     */
    public String getName();

    /**
     * Returns the internal, namespaced name of the application. This is used to
     * uniquely identify the application. Together With the version number, this
     * will identify a version of the same application.
     */
    public String getInternalName();

    /**
     * Returns the OSGi version of the deployed Application for which the RASC
     * is being generated.
     * 
     * @return the version number of the deployed Application.
     */
    public Version getVersion();

    /**
     * Returns the range that should be applied to dependencies on this
     * application. This is used when iterating the applications on which the
     * deployed application depends.
     * 
     * @return the application dependency range
     */
    public VersionRange getDependencyRange();

    /**
     * Returns the projects referenced by the deployed application. This
     * includes both the static and dynamic references. The returned projects
     * need not exist in the workspace. The result will not contain duplicates.
     * Returns an empty array if there are no referenced projects.
     * 
     * @return this projects referenced by the deployed application.
     * @throws CoreException
     */
    public Collection<RascAppSummary> getReferencedProjects()
            throws CoreException;

    /**
     * Determines whether the application contains a model asset of the given
     * Asset ID. Asset IDs are arbitrary, but well-known, string identifies. For
     * example; see com.tibco.xpd.bom.resources.ui.Activator#BOM_ASSET_ID.
     * 
     * @param aAssetTypeId
     *            the ID of the asset type to be tested.
     * @return <code>true</code> if the referenced application contains a model
     *         asset of the given ID.
     */
    public boolean hasAssetType(String aAssetTypeId);
}
