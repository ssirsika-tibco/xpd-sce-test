/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.brm.validation;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.IValidationContext;

import com.tibco.n2.model.brm.BRMPackage;
import com.tibco.n2.model.brm.Descriptor;
import com.tibco.xpd.n2.brm.internal.Messages;
import com.tibco.xpd.n2.brm.utils.BRMSchemaUtil;
import com.tibco.xpd.n2.resources.validation.ValidationPrivateRSClientSelector.N2ResourceSetImpl;
import com.tibco.xpd.resources.util.XMLUtil;

/**
 * Checks work specification model element for BRM component.
 * 
 * @since 3.3
 * @author Jan Arciuchiewicz
 */
public class WorkSpecificationConstraint extends AbstractModelConstraint {

    @Override
    public IStatus validate(IValidationContext ctx) {
        EObject target = ctx.getTarget();
        EMFEventType eventType = ctx.getEventType();

        // In the case of batch mode.
        if (eventType == EMFEventType.NULL) {
            if (target instanceof Descriptor) {
                IStatus status;
                String location = ((Descriptor) target).getLocation();
                status = checkLocation(ctx, location);
                if (status != Status.OK_STATUS) {
                    return status;
                }
                String version = ((Descriptor) target).getVersion();
                status = checkVersion(ctx, version);
                if (status != Status.OK_STATUS) {
                    return status;
                }
            }

        } else {// In the case of live mode.
            String newValue = (String) ctx.getFeatureNewValue();
            EStructuralFeature feature = ctx.getFeature();
            if (target instanceof Descriptor) {
                IStatus status;
                if (feature == BRMPackage.Literals.DESCRIPTOR__LOCATION) {
                    status = checkLocation(ctx, newValue);
                    if (status != Status.OK_STATUS) {
                        return status;
                    }
                } else if (feature == BRMPackage.Literals.DESCRIPTOR__VERSION) {
                    status = checkVersion(ctx, newValue);
                    if (status != Status.OK_STATUS) {
                        return status;
                    }
                }
            }
        }

        return ctx.createSuccessStatus();
    }

    protected IStatus checkVersion(IValidationContext ctx, String version) {
        if (version == null || version.trim().length() == 0) {
            return ctx.createFailureStatus(Messages.WorkSpecificationConstraint_versionNeeded_message);
        }
        return Status.OK_STATUS;
    }

    protected IStatus checkLocation(IValidationContext ctx, String location) {
        if (location == null || location.trim().length() == 0) {
            return ctx
                    .createFailureStatus(Messages.WorkSpecificationConstraint_descriptorLocationMissing_message);

        }
        EObject target = ctx.getTarget();
        Resource resource = target.eResource();
        if (resource != null
                && resource.getResourceSet() instanceof N2ResourceSetImpl) {
            ResourceSet rs = resource.getResourceSet();
            URI baseURI = (URI) ((N2ResourceSetImpl) rs).getData();
            IPath relativeLocation = (new Path(location)).makeRelative();
            String systemId = relativeLocation.lastSegment();
            URI uri =
                    URI.createURI(relativeLocation.toPortableString())
                            .resolve(baseURI);

            Source document;
            try {
                document =
                        new StreamSource(rs.getURIConverter()
                                .createInputStream(uri), systemId);
                IStatus validationStatus =
                        XMLUtil.validateAgainstXMLSchema(BRMSchemaUtil
                                .getBRMSchemas(), document);
                if (validationStatus.getSeverity() != IStatus.OK) {
                    return ctx.createFailureStatus(validationStatus
                            .getMessage());
                }
            } catch (Exception e) {
                return ctx
                        .createFailureStatus(String
                                .format(Messages.WorkSpecificationConstraint_validationProblem_message,
                                        location));
            }
        }
        return Status.OK_STATUS;
    }
}
