/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

/**
 * Interface used by the project asset extension point. The implementation of
 * this interface will be called during the creation of the new XPD project to
 * configure the asset type. The asset configuration object will come from the
 * associated wizard pages of this asset type.
 * <p>
 * If access to configuration objects of other asset types is required then use
 * {@link IAssetConfigurator2}.
 * </p>
 * 
 * @see IAssetConfigurator2
 * 
 * @author njpatel
 * 
 */
public interface IAssetConfigurator {

    /**
     * Configure this asset type for the given XPD <i>project</i>. The
     * <i>configuration</i> is acquired from the associated wizard pages of
     * this asset type in the project asset extension. Note that the
     * <i>configuration</i> can be null.
     * <p>
     * Note that this operation will run in a
     * <code>WorkspaceModifyOperation</code>.
     * </p>
     * 
     * @param project
     *            XPD project to configure.
     * @param configuration
     *            Configuration information collected from the associated wizard
     *            pages. This can be <b>null</b>.
     * 
     * @throws CoreException
     */
    public void configure(IProject project, Object configuration)
            throws CoreException;
}
