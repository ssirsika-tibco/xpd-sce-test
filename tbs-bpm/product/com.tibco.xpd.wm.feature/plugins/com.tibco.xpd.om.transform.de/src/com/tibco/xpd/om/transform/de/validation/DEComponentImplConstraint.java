package com.tibco.xpd.om.transform.de.validation;

/**
 * Validates Directory Engine (DE) component's implementation model and
 * validates DE model against XSD.
 * 
 * @since 3.3
 * @author Jan Arciuchiewicz
 */
public class DEComponentImplConstraint {
    /*
     * TODO Sid ACE-125 - had to comment out for now as DEImplementation cause
     * an indirect reference to AMX features and plugins...
     * 
     * The type com.tibco.amf.sca.model.extensionpoints.Implementation cannot be
     * resolved. It is indirectly referenced from required .class files
     */
    // extends AbstractModelConstraint {
    //
    //
    // @Override
    // public IStatus validate(IValidationContext ctx) {
    // EObject target = ctx.getTarget();
    // EMFEventType eventType = ctx.getEventType();
    //
    // if (target instanceof DEImplementation) {
    // DEImplementation deImpl = ((DEImplementation) target);
    // // In case of batch mode.
    // if (eventType == EMFEventType.NULL) {
    // String org = deImpl.getOrganization();
    // IStatus orgCheckStatus = checkOrganization(ctx, org);
    // if (orgCheckStatus != Status.OK_STATUS) {
    // return orgCheckStatus;
    // }
    // String version = deImpl.getVersion();
    // IStatus versionCheckStatus = versionCheck(ctx, version);
    // if (versionCheckStatus != Status.OK_STATUS) {
    // return versionCheckStatus;
    // }
    //
    // } else {// In case of live mode.
    // EStructuralFeature feature = ctx.getFeature();
    // if (feature == CommonPackage.Literals.DE_IMPLEMENTATION__ORGANIZATION) {
    // String org = (String) ctx.getFeatureNewValue();
    // IStatus workSpecStatus = checkOrganization(ctx, org);
    // if (workSpecStatus != Status.OK_STATUS) {
    // return workSpecStatus;
    // }
    // } else if (feature == CommonPackage.Literals.DE_IMPLEMENTATION__VERSION)
    // {
    // String version = (String) ctx.getFeatureNewValue();
    // IStatus versionCheckStatus = versionCheck(ctx, version);
    // if (versionCheckStatus != Status.OK_STATUS) {
    // return versionCheckStatus;
    // }
    // }
    // }
    // }
    //
    // return ctx.createSuccessStatus();
    // }
    //
    // private IStatus versionCheck(IValidationContext ctx, String version) {
    // if (version == null || version.trim().length() == 0) {
    // return ctx.createFailureStatus(
    // Messages.DEComponentImplConstraint_versionNeeded_message);
    // }
    // return Status.OK_STATUS;
    // }
    //
    // private IStatus checkOrganization(IValidationContext ctx,
    // String orgModelLocation) {
    // if (orgModelLocation == null || orgModelLocation.trim().length() == 0) {
    // return ctx.createFailureStatus(
    // Messages.DEComponentImplConstraint_modelNotProvided_message);
    //
    // }
    // EObject target = ctx.getTarget();
    // Resource resource = target.eResource();
    // if (resource != null && resource.getResourceSet() != null) {
    // ResourceSet rs = resource.getResourceSet();
    // URI baseURI = N2Utils.getCompositeBaseURI(resource.getURI());
    // IPath relativeLocation =
    // (new Path(orgModelLocation)).makeRelative();
    // String systemId = relativeLocation.lastSegment();
    // URI uri = URI.createURI(relativeLocation.toPortableString())
    // .resolve(baseURI);
    //
    // Source document;
    // try {
    // document = new StreamSource(
    // rs.getURIConverter().createInputStream(uri), systemId);
    // IStatus validationStatus = XMLUtil.validateAgainstXMLSchema(
    // DESchemaUtil.getDESchemas(),
    // document);
    // if (validationStatus.getSeverity() != IStatus.OK) {
    // return ctx
    // .createFailureStatus(validationStatus.getMessage());
    // }
    // } catch (Exception e) {
    // return ctx.createFailureStatus(String.format(
    // Messages.DEComponentImplConstraint_validationProblem_message,
    // orgModelLocation));
    // }
    // }
    // return Status.OK_STATUS;
    // }
}
