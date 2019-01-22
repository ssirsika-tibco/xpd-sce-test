/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.bds.gd.internal.component;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

import com.tibco.amf.sca.componenttype.implementation.ImplementationTypeProvider;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.n2.model.common.BDSImplementation;
import com.tibco.n2.model.common.CommonFactory;
import com.tibco.xpd.daa.internal.util.CompositeUtil;

/**
 * Class to create the {@link Implementation} for a given bds file path
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class BDSImplementationTypeProvider implements
        ImplementationTypeProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.amf.sca.componenttype.implementation.ImplementationTypeProvider
     * #createImplementation(java.lang.String, org.eclipse.emf.common.util.URI)
     */
    public Implementation createImplementation(String bdsFilePath,
            URI compositeURI) {
        // Note: uri is the location of the composite
        // If the implementation needs to make a reference to the composite, it
        // has to be relative (or emf reference)
        if (bdsFilePath != null) {
            IResource resource =
                    CompositeUtil.getFileFromWorkspace(new Path(bdsFilePath));
            if (resource != null) {
                String bdsSpecURI = resource.getFullPath().toPortableString();
                if (bdsSpecURI != null) {
                    BDSImplementation bdsImplementation =
                            CommonFactory.eINSTANCE.createBDSImplementation();
                    bdsImplementation.setBDSSpecification(bdsSpecURI);
                    bdsImplementation.setVersion(CompositeUtil
                            .getVersionNumber(resource.getProject()));
                    return bdsImplementation;
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
    public boolean isSupported(String filePath) {
        if (filePath != null) {
            IFile file = CompositeUtil.getFileFromWorkspace(new Path(filePath));
            if (file != null
                    && BDSCompositeContributor.BDS_SPEC_FILENAME.equals(file
                            .getName())) {
                return true;
            }
        }
        return false;
    }
}
