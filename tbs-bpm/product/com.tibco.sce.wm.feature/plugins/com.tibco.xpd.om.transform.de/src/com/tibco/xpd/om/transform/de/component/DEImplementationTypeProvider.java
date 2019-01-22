/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.transform.de.component;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

import com.tibco.amf.sca.componenttype.implementation.ImplementationTypeProvider;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.n2.model.common.CommonFactory;
import com.tibco.n2.model.common.DEImplementation;
import com.tibco.xpd.daa.internal.util.CompositeUtil;
import com.tibco.xpd.n2.daa.utils.N2PECompositeUtil;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;

/**
 * @author mtorres
 * 
 */
public class DEImplementationTypeProvider implements ImplementationTypeProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.amf.sca.componenttype.implementation.ImplementationTypeProvider
     * #createImplementation(java.lang.String, org.eclipse.emf.common.util.URI)
     */
    public Implementation createImplementation(String deFilePath,
            URI compositeURI) {
        // Note: uri is the location of the composite
        // If the implementation needs to make a reference to the composite, it
        // has to be relative (or emf reference)
        if (deFilePath != null) {
            IResource resource =
                CompositeUtil.getFileFromWorkspace(new Path(deFilePath));
            if (resource != null) {
                String uri = getDEImplementationURI(resource, compositeURI);
                if (uri != null) {
                    DEImplementation dei =
                            CommonFactory.eINSTANCE.createDEImplementation();
                    dei.setOrganization(uri);
                    dei.setVersion(DECompositeUtil
                            .getOMModelVersion((IFile) resource, resource
                                    .getProject()));
                    return dei;
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
    public boolean isSupported(String deFilePath) {
        if (deFilePath != null) {
            IFile file =
                CompositeUtil.getFileFromWorkspace(new Path(deFilePath));
            if (file != null && isSupportedDEFile(file)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSupportedDEFile(IFile file) {
        if (file.getName() != null
                && file.getFileExtension() != null
                && file.getFileExtension()
                        .equals(N2PENamingUtils.DE_FILEEXTENSION)) {
            return true;
        }
        return false;
    }

    private String getDEImplementationURI(IResource resource, URI compositeURI) {
        return resource.getFullPath().toString();
    }

}
