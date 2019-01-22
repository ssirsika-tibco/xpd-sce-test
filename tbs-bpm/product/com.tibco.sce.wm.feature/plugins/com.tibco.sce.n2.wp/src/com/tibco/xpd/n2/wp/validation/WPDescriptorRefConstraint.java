/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.n2.wp.validation;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.IValidationContext;

import com.tibco.n2.wp.archive.service.ChannelExtentionType;
import com.tibco.n2.wp.archive.service.ChannelType;
import com.tibco.n2.wp.archive.service.FormType;
import com.tibco.n2.wp.archive.service.PageFlowType;
import com.tibco.xpd.n2.resources.validation.ValidationPrivateRSClientSelector.N2ResourceSetImpl;
import com.tibco.xpd.n2.wp.WPActivator;
import com.tibco.xpd.n2.wp.internal.Messages;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Validate resources referenced from WP descriptor file (wpModel.xml).
 * 
 * @since 3.3
 * @author Jan Arciuchiewicz
 */
public class WPDescriptorRefConstraint extends AbstractModelConstraint {

    private static final Logger LOG = WPActivator.getDefault().getLogger();

    @Override
    public IStatus validate(IValidationContext ctx) {
        EObject target = ctx.getTarget();
        EMFEventType eventType = ctx.getEventType();

        Resource resource = target.eResource();
        if (resource != null
                && resource.getResourceSet() instanceof N2ResourceSetImpl) {
            ResourceSet rs = resource.getResourceSet();
            URI baseURI = (URI) ((N2ResourceSetImpl) rs).getData();

            // In case of batch mode.
            if (eventType == EMFEventType.NULL) {
                // XPD-3544 : should not validate files for email
                if (!shouldValidate(target)) {
                    return ctx.createSuccessStatus();
                }
                if (target instanceof FormType) {
                    FormType formType = (FormType) target;
                    IPath uriPath =
                            new Path(formType.getRelativePath())
                                    .append(formType.getName()).makeRelative();
                    URI uri =
                            URI.createURI(uriPath.toPortableString())
                                    .resolve(baseURI);
                    return resourceExists(ctx, rs, uri);
                } else if (target instanceof ChannelExtentionType) {

                    ChannelExtentionType channelExtentionType =
                            (ChannelExtentionType) target;
                    IPath uriPath =
                            new Path(channelExtentionType.getLocation())
                                    .append(channelExtentionType.getFilename())
                                    .makeRelative();
                    URI uri =
                            URI.createURI(uriPath.toPortableString())
                                    .resolve(baseURI);
                    return resourceExists(ctx, rs, uri);
                } else if (target instanceof PageFlowType) {
                    PageFlowType pageFlowType = (PageFlowType) target;
                    IPath uriPath =
                            new Path(pageFlowType.getUrl()).makeRelative();
                    // Have to go up 3 levels. ".bpm/wp/wpModel.xml" so it's
                    // resolved base to the project.
                    URI uri =
                            URI.createURI(uriPath.toPortableString())
                                    .resolve(URI
                                            .createURI("../../..").resolve(baseURI)); //$NON-NLS-1$
                    return resourceExists(ctx, rs, uri);
                }

            } else {// In case of live mode.
                String newValue = (String) ctx.getFeatureNewValue();
                EStructuralFeature feature = ctx.getFeature();
                // TODO Do nothing at the moment. Live validation is not
                // supported
                // at the moment but may be in the feature.
            }
        }

        return ctx.createSuccessStatus();
    }

    /**
     * @param uri
     */
    private IStatus resourceExists(IValidationContext ctx, ResourceSet rs,
            URI uri) {
        InputStream inputStream = null;
        try {
            inputStream = rs.getURIConverter().createInputStream(uri);
        } catch (IOException e) {
            return ctx
                    .createFailureStatus(String
                            .format(Messages.WPDescriptorRefConstraint_missingReferencedResource,
                                    uri));
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOG.error(e);
                }
            }
        }
        return ctx.createSuccessStatus();
    }

    /**
     * 
     */
    private boolean shouldValidate(EObject eo) {
        ChannelType channelType = getChannelType(eo);
        if (channelType != null
                && channelType.getTargetChannelType() == com.tibco.n2.common.channeltype.ChannelType.EMAIL_CHANNEL) {
            return false;
        }
        return true;
    }

    private ChannelType getChannelType(EObject eo) {
        EObject eContainer = eo.eContainer();
        while (eContainer != null) {
            if (eContainer instanceof ChannelType) {
                return (ChannelType) eContainer;
            }
            eContainer = eContainer.eContainer();
        }
        return null;
    }
}
