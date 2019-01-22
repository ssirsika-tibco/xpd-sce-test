/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.n2.brm.component;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

import com.tibco.amf.sca.componenttype.implementation.ImplementationTypeProvider;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.n2.model.common.CommonFactory;
import com.tibco.n2.model.common.ECImplementation;
import com.tibco.xpd.daa.internal.util.CompositeUtil;
import com.tibco.xpd.n2.brm.utils.BRMUtils;

/**
 * Class to create the {@link Implementation} for a given Event Collector
 * specification file path.
 * 
 * @author jarciuch
 * @since 10 Jan 2014
 */
public class EcImplementationTypeProvider implements ImplementationTypeProvider {

    @SuppressWarnings("restriction")
    public Implementation createImplementation(String ecSpecFilePath,
            URI compositeURI) {
        // Note: URI is the location of the composite
        // If the implementation needs to make a reference to the composite, it
        // has to be relative (or emf reference).
        if (ecSpecFilePath != null) {
            IResource resource =
                    CompositeUtil
                            .getFileFromWorkspace(new Path(ecSpecFilePath));
            if (resource != null) {
                String uri = resource.getFullPath().toPortableString();
                if (uri != null) {
                    ECImplementation ecImplementation =
                            CommonFactory.eINSTANCE.createECImplementation();
                    ecImplementation.setEcSpecification(uri);
                    ecImplementation.setVersion(CompositeUtil
                            .getVersionNumber(resource.getProject()));
                    return ecImplementation;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("restriction")
    public boolean isSupported(String ecSpecFilePath) {
        if (ecSpecFilePath != null) {
            IFile file =
                    CompositeUtil
                            .getFileFromWorkspace(new Path(ecSpecFilePath));
            if (file != null
                    && file.getName().equals(BRMUtils.EC_MODEL_FILENAME)) {
                return true;
            }
        }
        return false;
    }

}
