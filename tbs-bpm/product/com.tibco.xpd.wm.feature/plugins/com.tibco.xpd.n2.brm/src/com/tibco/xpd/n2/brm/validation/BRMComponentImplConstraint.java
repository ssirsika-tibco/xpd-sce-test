/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.brm.validation;

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

import com.tibco.n2.model.common.BRMImplementation;
import com.tibco.n2.model.common.CommonPackage;
import com.tibco.xpd.n2.brm.internal.Messages;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.n2.resources.validation.ValidationPrivateRSClientSelector;

/**
 * Checks BRM implementation element for BRM component.
 * 
 * @since 3.3
 * @author Jan Arciuchiewicz
 */
public class BRMComponentImplConstraint extends AbstractModelConstraint {

    @Override
    public IStatus validate(IValidationContext ctx) {
        EObject target = ctx.getTarget();
        EMFEventType eventType = ctx.getEventType();

        if (target instanceof BRMImplementation) {
            BRMImplementation brmImpl = ((BRMImplementation) target);
            // In case of batch mode.
            if (eventType == EMFEventType.NULL) {
                String workSpec = brmImpl.getWorkSpecification();
                IStatus workSpecCheckStatus =
                        checkWorkSpecification(ctx, workSpec);
                if (workSpecCheckStatus != Status.OK_STATUS) {
                    return workSpecCheckStatus;
                }
                String version = brmImpl.getVersion();
                IStatus versionCheckStatus = versionCheck(ctx, version);
                if (versionCheckStatus != Status.OK_STATUS) {
                    return versionCheckStatus;
                }

            } else {// In case of live mode.
                EStructuralFeature feature = ctx.getFeature();
                if (feature == CommonPackage.Literals.BRM_IMPLEMENTATION__WORK_SPECIFICATION) {
                    String workSpec = (String) ctx.getFeatureNewValue();
                    IStatus workSpecStatus =
                            checkWorkSpecification(ctx, workSpec);
                    if (workSpecStatus != Status.OK_STATUS) {
                        return workSpecStatus;
                    }
                } else if (feature == CommonPackage.Literals.BRM_IMPLEMENTATION__VERSION) {
                    String version = (String) ctx.getFeatureNewValue();
                    IStatus versionCheckStatus = versionCheck(ctx, version);
                    if (versionCheckStatus != Status.OK_STATUS) {
                        return versionCheckStatus;
                    }
                }

            }
        }

        return ctx.createSuccessStatus();
    }

    private IStatus versionCheck(IValidationContext ctx, String version) {
        if (version == null || version.trim().length() == 0) {
            return ctx.createFailureStatus(Messages.BRMComponentImplConstraint_versionNeeded_message);
        }
        return Status.OK_STATUS;
    }

    private IStatus checkWorkSpecification(IValidationContext ctx,
            String workSpec) {
        if (workSpec == null || workSpec.trim().length() == 0) {
            return ctx
                    .createFailureStatus(Messages.BRMComponentImplConstraint__wpDescriptorNotProvided_message);

        }
        EObject target = ctx.getTarget();
        Resource resource = target.eResource();
        if (resource != null && resource.getResourceSet() != null) {

            URI baseURI = N2Utils.getCompositeBaseURI(resource.getURI());

            ResourceSet rs =
                    ValidationPrivateRSClientSelector
                            .createBRMResourceSet(baseURI);
            IPath relativeWorkSpec = (new Path(workSpec)).makeRelative();
            URI uri =
                    URI.createURI(relativeWorkSpec.toPortableString())
                            .resolve(baseURI);
            try {
                Resource r = rs.getResource(uri, true);
                if (r != null && !r.getContents().isEmpty()) {
                    IBatchValidator validator =
                            ModelValidationService.getInstance()
                                    .newValidator(EvaluationMode.BATCH);
                    validator.setIncludeLiveConstraints(true);
                    IStatus status = validator.validate(r.getContents());
                    return status.isOK() ? Status.OK_STATUS : status;

                }
                throw new Exception("Resource loading problem."); //$NON-NLS-1$
            } catch (Exception e) {
                String problemMessage =
                        String
                                .format(Messages.BRMComponentImplConstraint_loadingProblem_message,
                                        uri);
                return ctx.createFailureStatus(problemMessage);
            }
        }
        return Status.OK_STATUS;
    }
}
