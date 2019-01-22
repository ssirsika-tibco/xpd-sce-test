/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.wizards.newproject;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.wizard.Wizard;

import com.tibco.xpd.resources.projectconfig.projectassets.IAssetConfigurationProvider;

/**
 * Abstract class for the Asset wizard that is part of the New XPD project
 * wizard. This extends <code>Wizard</code> with a method to get the
 * configuration object for a given asset type.
 * <p>
 * <b>NOTE</b>: Use
 * <code>{@link #performFinish(IProgressMonitor, IProject)}</code> instead of
 * <code>{@link #performFinish()}</code>
 * </p>
 * 
 * @see Wizard
 * 
 * @author njpatel
 * 
 */
public abstract class AssetWizard extends Wizard implements
        IAssetConfigurationProvider {

    /**
     * USE <code>{@link #performFinish(IProgressMonitor, IProject)}</code>
     * INSTEAD.
     * 
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    public final boolean performFinish() {
        return true;
    }

    /**
     * Perform the operation of this wizard.
     * 
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     * 
     * @param monitor
     *            The wizard progress monitor. Use this to create a
     *            <code>SubProgressMonitor</code>.
     * @param project
     *            The project that has been created by the parent wizard to
     *            which this asset type needs to be configured.
     * @return
     */
    public abstract boolean performFinish(IProgressMonitor monitor,
            IProject project) throws CoreException;
}
