package com.tibco.bds.designtime.validator.rules;

import java.util.Collections;
import java.util.Iterator;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.bds.designtime.validator.CDSIssueIds;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Although BOM generic validation caters for exact match name clashes, CDS
 * considers names illegal if they clash during a case-insensitive comparison.
 * e.g. Classes called 'Hello' and 'HeLLO' can co-exist in BOM (as can
 * complextypes in XSD), but EMF (or Java) won't allow it.
 * 
 * @author smorgan
 * 
 */
public class NameClashCaseMismatchRule implements IValidationRule {

    public Class<?> getTargetClass() {
        return NamedElement.class;
    }

    private boolean isAffectedClassifier(Object o) {
        return (o instanceof org.eclipse.uml2.uml.Class
                || o instanceof Enumeration || o instanceof PrimitiveType);
    }

    public void validate(IValidationScope scope, Object o) {
        if (isAffectedClassifier(o)) {
            Classifier clazz = (Classifier) o;
            String name = clazz.getName();
            boolean clash = false;
            org.eclipse.uml2.uml.Package pkg = clazz.getPackage();
            if (pkg != null && name != null) {
                Iterator<PackageableElement> iter =
                        pkg.getPackagedElements().iterator();
                while (iter.hasNext() && !clash) {
                    PackageableElement pe = iter.next();
                    if (pe != clazz && isAffectedClassifier(pe)) {
                        // See if name matches (ignoring case)
                        String otherName =
                                stripAppendedUnderscores(pe.getName());
                        if (otherName != null
                                && otherName
                                        .equalsIgnoreCase(stripAppendedUnderscores(name))) {
                            // ... but is NOT an exact match (as that's
                            // dealt with by a generic rule)
                            if (!pe.getName().equals(name)) {
                                clash = true;
                                break;
                            }
                        }
                    }
                }
                if (clash) {
                    scope.createIssue(CDSIssueIds.NAME_CLASH_CLASSIFIER_IGNORECASE,
                            clazz.getQualifiedName(),
                            clazz.eResource().getURIFragment(clazz),
                            Collections.singleton(clazz.getName()));

                }
            }
        } else if (o instanceof Property) {
            Property prop = (Property) o;
            String name = prop.getName();
            boolean clash = false;
            Classifier clazz = prop.getClass_();
            if (clazz != null && name != null) {
                Iterator<Property> iter = clazz.getAllAttributes().iterator();
                while (iter.hasNext() && !clash) {
                    Property prop2 = iter.next();
                    // See if name matches (ignoring case)
                    if (prop2 != prop
                            && stripAppendedUnderscores(prop2.getName())
                                    .equalsIgnoreCase(stripAppendedUnderscores(name))) {
                        // ... but is NOT an exact match (as that's
                        // dealt with by a generic rule)
                        if (!prop2.getName().equals(name)) {
                            clash = true;
                            break;
                        }
                    }
                }
                if (clash) {
                    scope.createIssue(CDSIssueIds.NAME_CLASH_PROPERTY_IGNORECASE,
                            prop.getQualifiedName(),
                            prop.eResource().getURIFragment(prop),
                            Collections.singleton(prop.getName()));
                }
            }
        }

    }

    /**
     * Calculate the name without any underscores at the end of it
     * 
     * @param originalName      Source name
     * @return  Name without appended underscores
     */
    private String stripAppendedUnderscores(String originalName) {
        String strippedName = originalName;

        // Check the last character for an underscore and remove it
        while ((strippedName != null) && (strippedName.length() > 1)
                && strippedName.endsWith("_")) {
            strippedName = strippedName.substring(0, strippedName.length() - 1);
        }

        return strippedName;
    }
}
