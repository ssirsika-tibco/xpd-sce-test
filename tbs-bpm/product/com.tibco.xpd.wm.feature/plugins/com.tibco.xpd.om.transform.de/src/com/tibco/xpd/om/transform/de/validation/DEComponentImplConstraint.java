package com.tibco.xpd.om.transform.de.validation;

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

import com.tibco.n2.model.common.CommonPackage;
import com.tibco.n2.model.common.DEImplementation;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.om.transform.de.internal.Messages;
import com.tibco.xpd.om.transform.de.utils.DESchemaUtil;
import com.tibco.xpd.resources.util.XMLUtil;

/**
 * Validates Directory Engine (DE) component's implementation model and
 * validates DE model against XSD.
 * 
 * @since 3.3
 * @author Jan Arciuchiewicz
 */
public class DEComponentImplConstraint extends AbstractModelConstraint {

    @Override
    public IStatus validate(IValidationContext ctx) {
        EObject target = ctx.getTarget();
        EMFEventType eventType = ctx.getEventType();

        if (target instanceof DEImplementation) {
            DEImplementation deImpl = ((DEImplementation) target);
            // In case of batch mode.
            if (eventType == EMFEventType.NULL) {
                String org = deImpl.getOrganization();
                IStatus orgCheckStatus = checkOrganization(ctx, org);
                if (orgCheckStatus != Status.OK_STATUS) {
                    return orgCheckStatus;
                }
                String version = deImpl.getVersion();
                IStatus versionCheckStatus = versionCheck(ctx, version);
                if (versionCheckStatus != Status.OK_STATUS) {
                    return versionCheckStatus;
                }

            } else {// In case of live mode.
                EStructuralFeature feature = ctx.getFeature();
                if (feature == CommonPackage.Literals.DE_IMPLEMENTATION__ORGANIZATION) {
                    String org = (String) ctx.getFeatureNewValue();
                    IStatus workSpecStatus = checkOrganization(ctx, org);
                    if (workSpecStatus != Status.OK_STATUS) {
                        return workSpecStatus;
                    }
                } else if (feature == CommonPackage.Literals.DE_IMPLEMENTATION__VERSION) {
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
            return ctx.createFailureStatus(Messages.DEComponentImplConstraint_versionNeeded_message);
        }
        return Status.OK_STATUS;
    }

    private IStatus checkOrganization(IValidationContext ctx,
            String orgModelLocation) {
        if (orgModelLocation == null || orgModelLocation.trim().length() == 0) {
            return ctx
                    .createFailureStatus(Messages.DEComponentImplConstraint_modelNotProvided_message);

        }
        EObject target = ctx.getTarget();
        Resource resource = target.eResource();
        if (resource != null && resource.getResourceSet() != null) {
            ResourceSet rs = resource.getResourceSet();
            URI baseURI = N2Utils.getCompositeBaseURI(resource.getURI());
            IPath relativeLocation =
                    (new Path(orgModelLocation)).makeRelative();
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
                        XMLUtil.validateAgainstXMLSchema(DESchemaUtil
                                .getDESchemas(), document);
                if (validationStatus.getSeverity() != IStatus.OK) {
                    return ctx.createFailureStatus(validationStatus
                            .getMessage());
                }
            } catch (Exception e) {
                return ctx
                        .createFailureStatus(String
                                .format(Messages.DEComponentImplConstraint_validationProblem_message,
                                        orgModelLocation));
            }
        }
        return Status.OK_STATUS;
    }
}
