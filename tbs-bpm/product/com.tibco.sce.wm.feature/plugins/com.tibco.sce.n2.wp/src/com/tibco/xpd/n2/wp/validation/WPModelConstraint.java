/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.wp.validation;

import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.runtime.Assert;
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
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.IBatchValidator;
import org.eclipse.emf.validation.service.ModelValidationService;

import com.tibco.n2.model.wp.BRMDescriptor;
import com.tibco.n2.model.wp.Descriptor;
import com.tibco.n2.model.wp.WPDescriptor;
import com.tibco.n2.model.wp.WPPackage;
import com.tibco.n2.wp.archive.service.util.WPResourceFactoryImpl;
import com.tibco.xpd.n2.brm.utils.BRMSchemaUtil;
import com.tibco.xpd.n2.resources.validation.ValidationPrivateRSClientSelector;
import com.tibco.xpd.n2.resources.validation.ValidationPrivateRSClientSelector.N2ResourceSetImpl;
import com.tibco.xpd.n2.wp.internal.Messages;
import com.tibco.xpd.n2.wp.utils.WPSchemaUtil;
import com.tibco.xpd.resources.util.XMLUtil;

/**
 * Validate WPDescriptor and BRMDescriptor in Work Presentation Model
 * (wpModel.xml).
 * 
 * @since 3.3
 * @author Jan Arciuchiewicz
 */
public class WPModelConstraint extends AbstractModelConstraint {

    /** */
    private static final String XML_EXTENSION = "xml"; //$NON-NLS-1$

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
                if (feature == WPPackage.Literals.DESCRIPTOR__LOCATION) {
                    status = checkLocation(ctx, newValue);
                    if (status != Status.OK_STATUS) {
                        return status;
                    }
                } else if (feature == WPPackage.Literals.DESCRIPTOR__VERSION) {
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
            return ctx
                    .createFailureStatus(Messages.WPModelConstraint_versionNeeded_message);
        }
        return Status.OK_STATUS;
    }

    protected IStatus checkLocation(IValidationContext ctx, String location) {
        if (location == null || location.trim().length() == 0) {
            return ctx
                    .createFailureStatus(Messages.WPModelConstraint_descriptorNotProvided_message);

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
                IStatus validationStatus = Status.OK_STATUS;
                if (target instanceof WPDescriptor) {
                    validationStatus =
                            XMLUtil.validateAgainstXMLSchema(WPSchemaUtil
                                    .getWPSchemas(), document);
                    if (validationStatus.isOK()) {
                        // Validate wpModel.xml EMF model. Only if it's valid
                        // according to schema.

                        // Mapping "xml" extension to WP factory.
                        Map<String, Object> extensionToFactoryMap =
                                Resource.Factory.Registry.INSTANCE
                                        .getExtensionToFactoryMap();
                        Object previousXMLFactory =
                                extensionToFactoryMap.get(XML_EXTENSION);
                        extensionToFactoryMap.put(XML_EXTENSION,
                                new WPResourceFactoryImpl());
                        try {
                            // Private resource set. Locations inside
                            // wpModel.xml are relative to wpModel.xml. (mostly
                            // :))

                            URI wpBaseURI =
                                    URI.createURI(relativeLocation.toPortableString())
                                            .resolve(baseURI);
                            ResourceSet rs1 =
                                    ValidationPrivateRSClientSelector
                                            .createBRMResourceSet(wpBaseURI);
                            Resource r = rs1.getResource(uri, true);
                            if (r != null && !r.getContents().isEmpty()) {
                                IBatchValidator validator =
                                        ModelValidationService
                                                .getInstance()
                                                .newValidator(EvaluationMode.BATCH);
                                validator.setIncludeLiveConstraints(true);
                                IStatus status =
                                        validator.validate(r.getContents());
                                return status.isOK() ? Status.OK_STATUS
                                        : status;

                            }
                            throw new Exception(
                                    String.format("Resource loading problem. (%1$s)", uri.toString())); //$NON-NLS-1$
                        } catch (Exception e) {
                            String problemMessage =
                                    String.format(Messages.WPModelConstraint_wpDescLoadingProblem_message2,
                                            uri);
                            return ctx.createFailureStatus(problemMessage);
                        } finally {
                            extensionToFactoryMap.put(XML_EXTENSION,
                                    previousXMLFactory);
                        }
                    }
                } else if (target instanceof BRMDescriptor) {
                    validationStatus =
                            XMLUtil.validateAgainstXMLSchema(BRMSchemaUtil
                                    .getBRMSchemas(), document);
                } else {
                    Assert.isLegal(false, "Ilegal descriptor class."); //$NON-NLS-1$
                }
                if (validationStatus.getSeverity() != IStatus.OK) {
                    return ctx.createFailureStatus(validationStatus
                            .getMessage());
                }
            } catch (Exception e) {
                return ctx
                        .createFailureStatus(String
                                .format(Messages.WPModelConstraint_validationProblem_message,
                                        location));
            }
        }
        return Status.OK_STATUS;
    }
}
