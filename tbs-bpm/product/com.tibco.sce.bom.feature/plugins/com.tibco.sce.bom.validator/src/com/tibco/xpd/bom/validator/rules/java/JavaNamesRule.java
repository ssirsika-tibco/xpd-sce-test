/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.bom.validator.JavaIssueIds;
import com.tibco.xpd.bom.validator.rules.BOMValidationRule;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.bom.validator.util.JavaKeywords;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Validate if UML element name has valid name.
 * 
 * Note:<br>
 * Validator of the UML Class scope
 * 
 * @author wzurek
 */
public class JavaNamesRule extends BOMValidationRule {

    /** */
    private static final String JAVA_NAMES_VALIDATION = "JavaNamesValidation"; //$NON-NLS-1$

    public Class<?> getTargetClass() {
        return NamedElement.class;
    }

    /**
     * Get the issue id for an invalid identifier.
     * 
     * @return
     */
    protected String getIdentifierIssueId() {
        return JavaIssueIds.INVALID_JAVA_IDENTIFIER;
    }

    /**
     * Get the issue id for names that do not follow the java convention.
     * 
     * @return
     */
    protected String getConventionIssueId() {
        return JavaIssueIds.NAME_NO_JAVA_CONVENTION;
    }

    /**
     * Get java keyword issue id.
     * 
     * @return
     */
    protected String getKeywordIssueId() {
        return JavaIssueIds.NAME_IS_JAVA_KEYWORD;
    }

    public void validate(IValidationScope scope, Object o) {
        if (!enabledInCapabilities()) {
            return;
        }
        // special case handled by operation (see below)
        if (o instanceof Parameter) {
            return;
        }

        if (o instanceof Association) {
            return;
        }

        if (o instanceof NamedElement) {
            NamedElement prop = (NamedElement) o;
            String uri = prop.eResource().getURIFragment(prop);
            String name = prop.getName();

            if (name == null || name.length() == 0) {
                // it should be handled by generic validation
                return;
            }

            if (o instanceof org.eclipse.uml2.uml.Package) {
                // Potentially user might specify 'com.tibco.something' as a
                // package name. Validate each segment separately.
                String[] names = name.split("\\."); //$NON-NLS-1$
                for (String n : names) {
                    if (n.length() == 0) {
                        n = " "; //$NON-NLS-1$
                    }
                    if (!validateIdentifier(prop, n, scope, uri)) {
                        return;
                    }
                }
                // special case for names ending with '.'
                if (name.endsWith(".")) { //$NON-NLS-1$
                    if (!validateIdentifier(prop, " ", scope, uri)) { //$NON-NLS-1$
                        return;
                    }
                }
            } else if (o instanceof EnumerationLiteral) {
                name = ((EnumerationLiteral) o).getName();

                // Enum literal name should be upper case
                if (!name.equals(name.toUpperCase())) {
                    scope.createIssue(getConventionIssueId(), BOMValidationUtil
                            .getLocation((NamedElement) o), uri, Collections
                            .singleton(name));
                }
            } else {
                // validate the ID
                validateIdentifier(prop, name, scope, uri);
            }

            // special case: if validating operation, validate the parameters as
            // well, but add the issued to the operation.
            if (o instanceof Operation) {
                EList<Parameter> parameters =
                        ((Operation) o).getOwnedParameters();
                for (Parameter param : parameters) {
                    if (param.getDirection() == ParameterDirectionKind.IN_LITERAL) {
                        validateIdentifier((NamedElement) o,
                                param.getName(),
                                scope,
                                uri);
                    }
                }
            }
        }
    }

    protected boolean validateIdentifier(NamedElement element, String name,
            IValidationScope scope, String uri) {

        if (name == null || name.length() == 0) {
            // this should be handled by different rule
            return true;
        }

        List<String> additionalMessages = new ArrayList<String>();
        additionalMessages.add(name);

        // keyword
        if (JavaKeywords.getReservedWords().contains(name)) {
            scope.createIssue(getKeywordIssueId(), BOMValidationUtil
                    .getLocation(element), uri, additionalMessages);
            return false;
        }

        // java id
        if (!Character.isJavaIdentifierStart(name.charAt(0))) {
            scope.createIssue(getIdentifierIssueId(), BOMValidationUtil
                    .getLocation(element), uri, additionalMessages);

            return false;
        } else {
            for (int i = 1; i < name.length(); i++) {
                if (!Character.isJavaIdentifierPart(name.charAt(i))) {
                    scope.createIssue(getIdentifierIssueId(), BOMValidationUtil
                            .getLocation(element), uri, additionalMessages);

                    return false;
                }
            }
        }

        // java convention
        char startChar = name.charAt(0);
        if (Character.isUpperCase(startChar)
                || Character.isLowerCase(startChar)) {
            // java convention make sense for characters than can be upper or
            // lower (i.e. not applicable to Japanese characters)
            boolean type = element instanceof Type;
            if (Character.isUpperCase(name.charAt(0)) != type) {
                scope.createIssue(getConventionIssueId(), BOMValidationUtil
                        .getLocation(element), uri, additionalMessages);
                return false;
            }
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.bom.validator.rules.BOMValidationRule#getPluginContributon
     * ()
     */
    @Override
    public IPluginContribution getPluginContributon() {
        return new IPluginContribution() {
            public String getLocalId() {
                return JAVA_NAMES_VALIDATION;
            }

            public String getPluginId() {
                return BOMValidatorActivator.PLUGIN_ID;
            }
        };
    }
}
