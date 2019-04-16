package com.tibco.bds.designtime.validator.rules;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.bds.designtime.validator.CDSIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * A validation rule that prevents names in BOM that will cause duplicated 'get'
 * method names in generated EMF package class. e.g. A class called 'A_B' will
 * result in a getA_B() method. The attribute 'B' of a class 'A' will also
 * prompt a method with that name, causing uncompilable Java code. This wouldn't
 * be a problem in plain EMF and only becomes a problem because BDS has relaxed
 * EMF naming rules to allow underscores in class names (normally, the
 * underscore is assumed to be a special separator character in such method
 * names).
 * 
 * @author smorgan
 * 
 */
public class GetterNounClashRule implements IValidationRule,
        IExecutableExtension {

    private enum Option {
        Property, Classifier;
    }

    private Option option;

    @Override
    public Class<?> getTargetClass() {
        Class<?> result = null;
        if (option != null) {
            switch (option) {
            case Property:
                result = Property.class;
                break;
            case Classifier:
                result = Classifier.class;
                break;
            }
        }
        return result;
    }

    @Override
    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {
        if (data instanceof String) {
            option = Option.valueOf((String) data);
        }
    }

    protected String generateGetterNoun(NamedElement elem) {
        String result = null;
        if (elem instanceof Classifier) {
            result = elem.getName();
        } else if (elem instanceof Property) {
            result =
                    ((Property) elem).getClass_().getName() + "_"
                            + elem.getName();
        }
        return result;
    }

    protected Map<NamedElement, String> gatherGetterNouns(
            org.eclipse.uml2.uml.Package pkg) {
        Map<NamedElement, String> map = new HashMap<NamedElement, String>();
        for (Iterator<PackageableElement> iter =
                pkg.getPackagedElements().iterator(); iter.hasNext();) {
            PackageableElement elem = iter.next();
            if (elem instanceof org.eclipse.uml2.uml.Class
                    || elem instanceof Enumeration
                    || elem instanceof PrimitiveType) {
                String getterNoun = generateGetterNoun(elem);
                map.put(elem, getterNoun.toLowerCase());
                if (elem instanceof org.eclipse.uml2.uml.Class) {
                    for (Property prop : ((org.eclipse.uml2.uml.Class) elem)
                            .getAllAttributes()) {
                        // Ignore if property does not belong to this class
                        // (i.e. is inherited)
                        if (prop.getClass_() == elem) {
                            String propGetterNoun = generateGetterNoun(prop);
                            map.put(prop, propGetterNoun.toLowerCase());
                        }
                    }
                }
            }
        }
        return map;
    }

    protected org.eclipse.uml2.uml.Package getContainingPackage(
            NamedElement elem) {
        org.eclipse.uml2.uml.Package result = null;
        if (elem instanceof Classifier) {
            result = ((Classifier) elem).getPackage();
        } else if (elem instanceof Property) {
            result = ((Property) elem).getClass_().getPackage();
        }
        return result;
    }

    @Override
    public void validate(IValidationScope scope, Object obj) {
        boolean shouldValidate = false;
        if (obj instanceof Property) {
            Property prop = (Property) obj;
            shouldValidate = (prop.getClass_() != null);
        } else if (obj instanceof org.eclipse.uml2.uml.Class
                || obj instanceof Enumeration || obj instanceof PrimitiveType) {
            shouldValidate = true;
        }
        if (shouldValidate) {
            NamedElement elem = (NamedElement) obj;
            org.eclipse.uml2.uml.Package pkg = getContainingPackage(elem);
            if (pkg != null) {
                Map<NamedElement, String> map = gatherGetterNouns(pkg);
                String getterNoun = generateGetterNoun(elem).toLowerCase();
                for (Entry<NamedElement, String> entry : map.entrySet()) {
                    if (entry.getKey() != elem
                            && entry.getValue().equals(getterNoun)) {
                        NamedElement clashElement = entry.getKey();
                        // Note: If this class clashes with another class.
                        // then we WON'T raise an error, as we can assume
                        // that a 'more fundamental' validation will fail
                        // (i.e. name clash); Raising another error here
                        // simply clutters the UI.
                        if (clashElement instanceof Classifier
                                && !(elem instanceof Classifier)) {

                            String issueId = null;
                            if (clashElement instanceof org.eclipse.uml2.uml.Class) {
                                issueId =
                                        CDSIssueIds.NAME_CLASH_GETTER_NOUN_CLASS;
                            } else if (clashElement instanceof Enumeration) {
                                issueId =
                                        CDSIssueIds.NAME_CLASH_GETTER_NOUN_ENUMERATION;
                            } else {
                                issueId =
                                        CDSIssueIds.NAME_CLASH_GETTER_NOUN_PRIMITIVETYPE;
                            }
                            scope.createIssue(issueId,
                                    BOMValidationUtil.getLocation(elem),
                                    elem.eResource().getURIFragment(elem),
                                    Collections.singleton(clashElement
                                            .getName()));
                        } else if (clashElement instanceof Property) {
                            boolean sameClass =
                                    elem instanceof Property
                                            && ((Property) elem).getClass_() == ((Property) clashElement)
                                                    .getClass_();
                            // If clash is between two attributes in the same
                            // class, then it implies that a more fundamental
                            // clash is present (i.e. the attributes have
                            // colliding names). Raising this error too clutters
                            // the UI and could cause confusion.
                            if (!sameClass) {
                                scope
                                        .createIssue(CDSIssueIds.NAME_CLASH_GETTER_NOUN_PROPERTY,
                                                BOMValidationUtil
                                                        .getLocation(elem),
                                                elem.eResource()
                                                        .getURIFragment(elem),
                                                Arrays
                                                        .asList(new String[] {
                                                                clashElement
                                                                        .getName(),
                                                                ((Property) clashElement)
                                                                        .getClass_()
                                                                        .getName() }));
                            }
                        }
                    }
                }
            }
        }
    }
}
