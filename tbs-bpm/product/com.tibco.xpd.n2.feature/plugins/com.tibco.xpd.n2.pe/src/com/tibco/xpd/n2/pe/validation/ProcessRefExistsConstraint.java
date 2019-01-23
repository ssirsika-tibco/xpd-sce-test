/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.pe.validation;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.IValidationContext;

import com.tibco.bx.amx.model.service.N2PEServiceImplementation;
import com.tibco.bx.core.model.Process;
import com.tibco.bx.model.service.ServiceImplementation;
import com.tibco.xpd.n2.pe.Messages;
import com.tibco.xpd.n2.pe.PEActivator;
import com.tibco.xpd.n2.resources.BundleActivator;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Checks Process Engine implementation element for PE component.
 * 
 * @since 3.3
 * @author Jan Arciuchiewicz
 */
public class ProcessRefExistsConstraint extends AbstractModelConstraint {

    private static final Logger LOG = BundleActivator.getDefault().getLogger();

    @Override
    public IStatus validate(IValidationContext ctx) {
        EObject target = ctx.getTarget();
        EMFEventType eventType = ctx.getEventType();

        Resource resource = target.eResource();
        if (resource != null && resource.getResourceSet() != null) {
            ResourceSet rs = resource.getResourceSet();

            // In case of batch mode.
            if (eventType == EMFEventType.NULL) {
                if (target instanceof N2PEServiceImplementation) {
                    N2PEServiceImplementation n2peImpl =
                            (N2PEServiceImplementation) target;
                    URI baseURI =
                            N2Utils.getCompositeBaseURI(resource.getURI());
                    ServiceImplementation serviceModel =
                            n2peImpl.getServiceModel();
                    if (serviceModel != null) {

                        List<IPath> invalidReferencedPaths =
                                new ArrayList<IPath>();
                        for (Process bxProcess : serviceModel.getProcesses()) {
                            IPath uriPath =
                                    new Path(bxProcess.getProcessFileName())
                                            .makeRelative();
                            URI uri =
                                    URI.createURI(uriPath.toPortableString())
                                            .resolve(baseURI);
                            IStatus resourceExistsStatus =
                                    resourceExists(rs, uri);
                            if (resourceExistsStatus.getSeverity() > IStatus.OK) {
                                invalidReferencedPaths.add(uriPath);
                            }
                        }
                        if (invalidReferencedPaths.isEmpty()) {
                            return ctx.createSuccessStatus();
                        } else {

                            StringBuilder s = new StringBuilder();
                            for (IPath path : invalidReferencedPaths) {
                                if (s.length() != 0) {
                                    s.append(", "); //$NON-NLS-1$
                                }
                                s.append(path.toPortableString());
                            }
                            return ctx
                                    .createFailureStatus(String
                                            .format(Messages.ProcessRefExistsConstraint_invalidRef_message,
                                                    serviceModel
                                                            .getModuleName(),
                                                    s.toString()));
                        }
                    }
                }
            } else {// In case of live mode.
                LOG
                        .warn(String
                                .format("Life validation for for the rule '%s' is not supported.", //$NON-NLS-1$
                                        getClass().getName()));
            }
        }

        return ctx.createSuccessStatus();
    }

    /**
     * Check if URI can be opened to read.
     * 
     * @param rs
     *            context resource set.
     * @param uri
     *            the URI to check.
     * @return OK status if resource was open to read successfully or failure
     *         status otherwise (resource doesn't exist or is not readable).
     */
    private IStatus resourceExists(ResourceSet rs, URI uri) {
        InputStream inputStream = null;
        try {
            inputStream = rs.getURIConverter().createInputStream(uri);
        } catch (IOException e) {
            return new Status(
                    IStatus.ERROR,
                    PEActivator.PLUGIN_ID,
                    String
                            .format(Messages.ProcessRefExistsConstraint_cantOpenURI_message,
                                    uri), e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOG.error(e);
                }
            }
        }
        return Status.OK_STATUS;

    }
}
