/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * 
 * <p>
 * Abstract implementation of a resolution done through working copy.
 * <i>Created: 2 May 2007</i>
 * 
 * @author scrossle
 * 
 */
public abstract class AbstractWorkingCopyResolution implements IResolution,
        IFilter, IConfigurableResolutionLabel {

    /**
     * @param marker
     *            The problem marker.
     * @throws ResolutionException
     *             If there was a problem during resolution.
     * @see com.tibco.xpd.validation.resolutions.IResolution#run(org.eclipse.core.resources.IMarker)
     */
    public void run(IMarker marker) throws ResolutionException {
        // Marker may not exist if the previous resolution resolved this problem
        // too
        if (marker != null && marker.exists()) {
            try {
                EObject target = getTarget(marker);

                EditingDomain editingDomain = null;
                if (target != null) {
                    editingDomain = WorkingCopyUtil.getEditingDomain(target);
                }

                if (editingDomain != null) {
                    Command cmd =
                            getResolutionCommand(editingDomain, target, marker);
                    if (cmd != null && cmd.canExecute()) {
                        editingDomain.getCommandStack().execute(cmd);
                        performAdditionalOperations(editingDomain,
                                target,
                                marker);
                    }
                }
                // originally threw a ResolutionException here if editingDomain
                // was null - however
                // in some cases this was null and it was found better to ignore
                // rather than throw exception
            } catch (CoreException e) {
                throw new ResolutionException(e);
            }
        }
    }

    /**
     * Override this method in subclasses if you'd want to perform some
     * additional operations after the resolution command has been executed
     * (e.g. save working copy etc).
     * 
     * @param editingDomain
     * @param target
     * @param marker
     */
    protected void performAdditionalOperations(EditingDomain editingDomain,
            EObject target, IMarker marker) {

    }

    /**
     * Get the target EObject of the given marker.
     * 
     * @param marker
     * @return
     * @throws CoreException
     * 
     * @since 3.3
     */
    protected EObject getTarget(IMarker marker) throws CoreException {
        XpdProjectResourceFactory factory =
                XpdResourcesPlugin.getDefault()
                        .getXpdProjectResourceFactory(marker.getResource()
                                .getProject());
        WorkingCopy workingCopy = factory.getWorkingCopy(marker.getResource());
        // Clear the uri cache
        workingCopy.clearUriCache();
        String location = (String) marker.getAttribute(IMarker.LOCATION);
        EObject rootObject = workingCopy.getRootElement();
        Resource resource = null;
        if (rootObject != null) {
            resource = rootObject.eResource();
        }
        EObject target = null;
        if (resource != null) {
            target = workingCopy.resolveEObject(location);
        }
        return target;
    }

    /**
     * Initialise the resolution. This will be called before a cycle of problem
     * resolutions. This can be used, for instance, to reset some values in the
     * resolution before multiple problems are fixed.
     * <p>
     * Default implementation does nothing. Subclasses may override.
     * </p>
     * 
     * @since 3.0
     */
    protected void init() {
        // Do nothing
    }

    /**
     * Do the work.
     * 
     * @param editingDomain
     *            Ed.
     * @param target
     *            Target of the resolution.
     * @param marker
     *            The problem marker.
     * @return Command to do the resolution, or null.
     * @throws ResolutionException
     *             Any problem.
     */
    protected abstract Command getResolutionCommand(
            EditingDomain editingDomain, EObject target, IMarker marker)
            throws ResolutionException;

    /**
     * @see com.tibco.xpd.validation.resolutions.IConfigurableResolutionLabel#getResolutionLabel(java.lang.String,
     *      org.eclipse.core.resources.IMarker)
     * 
     * @param propertiesLabel
     * @param marker
     * @return
     */
    protected String getResolutionLabel(String propertiesLabel, IMarker marker) {
        return propertiesLabel;
    }

    /**
     * @see com.tibco.xpd.validation.resolutions.IConfigurableResolutionLabel#getConfigurableResolutionLabel(java.lang.String,
     *      org.eclipse.core.resources.IMarker)
     * 
     * @param propertiesLabel
     * @param marker
     * @return
     */
    public final String getConfigurableResolutionLabel(String propertiesLabel,
            IMarker marker) {
        return getResolutionLabel(propertiesLabel, marker);
    }

    /**
     * @see com.tibco.xpd.validation.resolutions.IConfigurableResolutionLabel#getResolutionDescription(java.lang.String,
     *      org.eclipse.core.resources.IMarker)
     * 
     * @param propertiesDescription
     * @param marker
     * @return
     */
    protected String getResolutionDescription(String propertiesDescription,
            IMarker marker) {
        return propertiesDescription;
    }

    /**
     * @see com.tibco.xpd.validation.resolutions.IConfigurableResolutionLabel#getConfigurableResolutionDescription(java.lang.String,
     *      org.eclipse.core.resources.IMarker)
     * 
     * @param propertiesDescription
     * @param marker
     * @return
     */
    public final String getConfigurableResolutionDescription(
            String propertiesDescription, IMarker marker) {
        return getResolutionDescription(propertiesDescription, marker);
    }

    /**
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * 
     * @param marker
     *            The filter is pased the IMarker.
     * 
     * @return Override to filter according to circumstance.
     */
    public boolean select(Object marker) {
        return true;
    }
}
