package com.tibco.bds.designtime.validator.rules;

import java.util.Collections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.bds.designtime.validator.CDSIssueIds;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.types.api.BOMEntityNameCleanser;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * A validation rule that checks BOM entity names for CDS-safeness. This
 * generally means they must be valid Java identifiers and not contain
 * characters that would mean BOM -> XSD -> Java transformation would result in
 * a change to the name.
 * 
 * @author smorgan
 * 
 */
public class CDSEntityNameRule implements IValidationRule {

    protected BOMEntityNameCleanser cleanser = BOMEntityNameCleanser
            .getInstance();

    public Class<?> getTargetClass() {
        return NamedElement.class;
    }

    protected void validateClassifier(IValidationScope scope, Classifier clazz) {
        String name = clazz.getName();
        String postCleanse =
                clazz instanceof PrimitiveType ? cleanser
                        .cleansePrimitiveTypeName(name, false) : cleanser
                        .cleanseClassName(name, false);
        if (!name.equals(postCleanse)) {
            scope.createIssue(CDSIssueIds.NAME_ILLEGAL_CLASS,
                    BOMValidationUtil.getLocation(clazz),
                    clazz.eResource().getURIFragment(clazz),
                    Collections.singleton(name));
        } else {
            // Check for reserved words separately, in order to
            // display a specific message.
            String rwCleansed =
                    clazz instanceof PrimitiveType ? cleanser
                            .cleansePrimitiveTypeName(name, true) : cleanser
                            .cleanseClassName(name, true);
            if (!rwCleansed.equals(name)) {
                scope.createIssue(CDSIssueIds.NAME_ILLEGAL_CLASS_RESERVED_WORD,
                        BOMValidationUtil.getLocation(clazz),
                        clazz.eResource().getURIFragment(clazz),
                        Collections.singleton(name));
            }
            Package pkg = clazz.getPackage();
            if (pkg != null) {
                String fqName = pkg.getQualifiedName().replaceAll("::", ".");
                if (fqName != null) {
                    String firstPackageSegment = fqName.split("\\.")[0];
                    if (firstPackageSegment.equalsIgnoreCase(rwCleansed)) {
                        // Raise error saying that name must not match first
                        // segment of fully-qualified package name (causes
                        // uncompilable EMF Java code).
                        scope.createIssue(CDSIssueIds.NAME_ILLEGAL_CLASS_PACKAGE,
                                BOMValidationUtil.getLocation(clazz),
                                clazz.eResource().getURIFragment(clazz),
                                Collections.singleton(name));
                    }
                }
            }
        }
    }

    protected void validateClass(IValidationScope scope,
            org.eclipse.uml2.uml.Class clazz) {
        validateClassifier(scope, clazz);
    }

    protected void validatePrimitiveType(IValidationScope scope,
            PrimitiveType pt) {
        validateClassifier(scope, pt);
    }

    protected void validateEnumeration(IValidationScope scope, Enumeration enu) {
        validateClassifier(scope, enu);
    }

    protected void validatePackage(IValidationScope scope,
            org.eclipse.uml2.uml.Package pkg) {
        String name = pkg.getName();
        // Note we don't allow dots in package (i.e. non-Model) names (XPD-453)
        String postCleanse =
                cleanser.cleansePackageName(name,
                        false,
                        !(pkg instanceof Model),
                        pkg instanceof Model);
        if (!name.equals(postCleanse)) {
            // Use appropriate issue for Model vs. Package
            String issueId =
                    (pkg instanceof Model ? CDSIssueIds.NAME_ILLEGAL_MODEL
                            : CDSIssueIds.NAME_ILLEGAL_PACKAGE);
            scope.createIssue(issueId,
                    BOMValidationUtil.getLocation(pkg),
                    pkg.eResource().getURIFragment(pkg),
                    Collections.singleton(name));
        } else {
            // Check for reserved words separately, in order to
            // display a specific message.
            String rwCleansed =
                    cleanser.cleansePackageName(name,
                            true,
                            !(pkg instanceof Model),
                            pkg instanceof Model);
            if (!rwCleansed.equals(name)) {
                scope.createIssue(CDSIssueIds.NAME_ILLEGAL_PACKAGE_RESERVED_WORDS,
                        BOMValidationUtil.getLocation(pkg),
                        pkg.eResource().getURIFragment(pkg),
                        Collections.singleton(name));

            }
        }
    }

    protected void validateProperty(IValidationScope scope, Property prop) {
        String name = prop.getName();
        String postCleanse = cleanser.cleanseAttributeName(name, false);
        if (!name.equals(postCleanse)) {
            scope.createIssue(CDSIssueIds.NAME_ILLEGAL_ATTRIBUTE,
                    BOMValidationUtil.getLocation(prop),
                    prop.eResource().getURIFragment(prop),
                    Collections.singleton(name));
        } else {
            // Check for reserved words separately, in order to
            // display a specific message.
            String rwCleansed = cleanser.cleanseAttributeName(name, true);
            if (!rwCleansed.equals(name)) {
                scope.createIssue(CDSIssueIds.NAME_ILLEGAL_ATTRIBUTE_RESERVED_WORD,
                        BOMValidationUtil.getLocation(prop),
                        prop.eResource().getURIFragment(prop),
                        Collections.singleton(name));

            }
        }
    }

    protected void validateEnumerationLiteral(IValidationScope scope,
            EnumerationLiteral lit) {
        String name = lit.getName();
        String postCleanse = cleanser.cleanseEnumerationLiteralName(name);
        if (!name.equals(postCleanse)) {
            scope.createIssue(CDSIssueIds.NAME_ILLEGAL_ENUMERATIONLITERAL,
                    BOMValidationUtil.getLocation(lit),
                    lit.eResource().getURIFragment(lit),
                    Collections.singleton(name));
        }
    }

    public void validate(IValidationScope scope, Object obj) {

        if (obj instanceof NamedElement) {
            NamedElement ne = (NamedElement) obj;
            // Get the containing model
            Model model = ne.getModel();
            if (model != null) {
                // Don't proceed with validation if this BOM is imported from
                // XSD. (In that case, the names are deliberately set to match
                // what EMF will generate and are not subsequently used during
                // BOM to XSD export).
                boolean isImportedFromXSD =
                        null != BOMUtils.getProfileApplied(model,
                                XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME);
                if (!isImportedFromXSD) {
                    // If the name is empty, don't bother validating; There
                    // are generic rules to deal with that.
                    String name = ((NamedElement) obj).getName();
                    if (name != null && name.length() != 0) {
                        if (obj instanceof org.eclipse.uml2.uml.Class) {
                            validateClass(scope,
                                    (org.eclipse.uml2.uml.Class) obj);
                        } else if (obj instanceof org.eclipse.uml2.uml.Package) {
                            validatePackage(scope,
                                    (org.eclipse.uml2.uml.Package) obj);
                        } else if (obj instanceof Property) {
                            EObject propContainer =
                                    ((Property) obj).eContainer();
                            if (propContainer instanceof org.eclipse.uml2.uml.Class) {
                                // We're only interested in Class properties
                                validateProperty(scope, (Property) obj);
                            }
                        } else if (obj instanceof EnumerationLiteral) {
                            validateEnumerationLiteral(scope,
                                    (EnumerationLiteral) obj);
                        } else if (obj instanceof PrimitiveType) {
                            validatePrimitiveType(scope, (PrimitiveType) obj);
                        } else if (obj instanceof Enumeration) {
                            validateEnumeration(scope, (Enumeration) obj);
                        }
                    }
                }
            }

        }
    }
}
