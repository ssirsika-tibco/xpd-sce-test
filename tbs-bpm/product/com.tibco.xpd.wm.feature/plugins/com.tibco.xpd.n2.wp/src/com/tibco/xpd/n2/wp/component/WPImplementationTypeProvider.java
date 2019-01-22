/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.wp.component;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

import com.tibco.amf.sca.componenttype.implementation.ImplementationTypeProvider;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.n2.model.common.CommonFactory;
import com.tibco.n2.model.common.WPImplementation;
import com.tibco.xpd.daa.internal.util.CompositeUtil;
import com.tibco.xpd.n2.daa.utils.N2PECompositeUtil;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;

/**
 * @author mtorres
 * 
 */
public class WPImplementationTypeProvider implements ImplementationTypeProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.amf.sca.componenttype.implementation.ImplementationTypeProvider
     * #createImplementation(java.lang.String, org.eclipse.emf.common.util.URI)
     */
    public Implementation createImplementation(String presentationFilePath,
            URI compositeURI) {
        // Note: uri is the location of the composite
        // If the implementation needs to make a reference to the composite, it
        // has to be relative (or emf reference)
        if (presentationFilePath != null) {
            IResource resource = CompositeUtil.getFileFromWorkspace(new Path(
                    presentationFilePath));
            if (resource != null) {
                String uri = getWPImplementationURI(resource, compositeURI);
                if (uri != null) {
                    WPImplementation wpi = CommonFactory.eINSTANCE
                            .createWPImplementation();
                    wpi.setPresentation(uri);
                    // TODO take the version from the project
                    wpi.setVersion(CompositeUtil.getVersionNumber(resource
                            .getProject()));
                    return wpi;
                }
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.amf.sca.componenttype.implementation.ImplementationTypeProvider
     * #isSupported(java.lang.String)
     */
    public boolean isSupported(String presentationFilePath) {
        if (presentationFilePath != null) {
            IFile file = CompositeUtil.getFileFromWorkspace(new Path(
                    presentationFilePath));
            if (file != null && isSupportedWPFile(file)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSupportedWPFile(IFile file) {
        if (file.getName() != null
                && file.getName().equals(N2PENamingUtils.WP_FILENAME)) {
            return true;
        }
        return false;
    }

    private String getWPImplementationURI(IResource resource, URI compositeURI) {
        return resource.getFullPath().toString();
    }

}
