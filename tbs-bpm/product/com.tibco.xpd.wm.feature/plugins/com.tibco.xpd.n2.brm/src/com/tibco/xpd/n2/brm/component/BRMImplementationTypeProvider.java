/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.brm.component;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

import com.tibco.amf.sca.componenttype.implementation.ImplementationTypeProvider;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.n2.model.common.BRMImplementation;
import com.tibco.n2.model.common.CommonFactory;
import com.tibco.xpd.daa.internal.util.CompositeUtil;
import com.tibco.xpd.n2.brm.utils.BRMUtils;
import com.tibco.xpd.n2.daa.utils.N2PECompositeUtil;

/**
 * Class to create the {@link Implementation} for a given brm file path
 * 
 * @author mtorres
 * 
 */
public class BRMImplementationTypeProvider implements
        ImplementationTypeProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.amf.sca.componenttype.implementation.ImplementationTypeProvider
     * #createImplementation(java.lang.String, org.eclipse.emf.common.util.URI)
     */
    public Implementation createImplementation(String brmFilePath,
            URI compositeURI) {
        // Note: uri is the location of the composite
        // If the implementation needs to make a reference to the composite, it
        // has to be relative (or emf reference)
        if (brmFilePath != null) {
            IResource resource =
                    CompositeUtil.getFileFromWorkspace(new Path(brmFilePath));
            if (resource != null) {
                String uri = getBrmImplementationURI(resource, compositeURI);
                if (uri != null) {
                    BRMImplementation brmImplementation =
                            CommonFactory.eINSTANCE.createBRMImplementation();
                    brmImplementation.setWorkSpecification(uri);
                    // TODO take the version from the project
                    brmImplementation.setVersion(CompositeUtil
                            .getVersionNumber(resource.getProject()));
                    return brmImplementation;
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
    public boolean isSupported(String brmFilePath) {
        if (brmFilePath != null) {
            IFile file =
                    CompositeUtil.getFileFromWorkspace(new Path(brmFilePath));
            if (file != null && isSupportedWTFile(file)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSupportedWTFile(IFile file) {
        if (file.getName() != null
                && file.getName().equals(BRMUtils.BRM_MODEL_FILENAME)) {
            return true;
        }
        return false;
    }

    private String getBrmImplementationURI(IResource resource, URI compositeURI) {
        return resource.getFullPath().toString();
    }

}
