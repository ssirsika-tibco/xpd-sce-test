/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * @author rsomayaj
 * 
 */
public class AddProjectNatureResolution implements IResolution {

    private static final String XSD_NATURE =
            "com.tibco.xpd.bom.xsdtransform.xsdNature"; //$NON-NLS-1$

    private static final String WSDL_NATURE =
            "com.tibco.xpd.wsdlgen.wsdlGenNature"; //$NON-NLS-1$

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    /**
     * @see com.tibco.xpd.validation.resolutions.IResolution#run(org.eclipse.core.resources.IMarker)
     * 
     * @param marker
     * @throws ResolutionException
     */
    @Override
    public void run(IMarker marker) throws ResolutionException {
        if (marker.getResource() instanceof IProject) {
            IProject project = (IProject) marker.getResource();
            try {
                ProjectUtil.addNature(project, XSD_NATURE);
                ProjectUtil.addNature(project, WSDL_NATURE);
            } catch (CoreException e) {
                LOG.error(e, "Unable to add WSDL Nature to project"); //$NON-NLS-1$
            }
        }
    }
}
