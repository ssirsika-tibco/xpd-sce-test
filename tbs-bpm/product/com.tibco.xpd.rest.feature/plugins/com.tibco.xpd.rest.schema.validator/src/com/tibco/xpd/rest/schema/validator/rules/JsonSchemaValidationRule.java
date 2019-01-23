/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rest.schema.validator.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rest.schema.JsonSchemaUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * JSON Schema validation rules.
 * 
 * @author nwilson
 * @since 12 Mar 2015
 */
public class JsonSchemaValidationRule implements IValidationRule {

    /**
     * Property '%1$s' in Type '%2$s' references an invalid Type.
     */
    private static final String MISSING_REFERENCED_TYPE =
            "rest.schema.missingReferencedType"; //$NON-NLS-1$

    /**
     * Missing project reference to '%1$s' from property '%2$s' type '%3$s'.
     */
    private static final String MISSING_PROJECT_REFERENCE =
            "rest.schema.missingProjectReference"; //$NON-NLS-1$

    /**
     * Type name '%1$s' must be unique within the JSON Schema file.
     */
    private static final String DUPLICATE_CLASS_NAME =
            "rest.schema.duplicateClassName"; //$NON-NLS-1$

    /**
     * Property name '%1$s' is invalid for Type '%2$s'.
     */
    private static final String INVALID_PROPERTY_NAME =
            "rest.schema.invalidPropertyName"; //$NON-NLS-1$

    /**
     * Property Type for property '%1$s' in Type '%2$s' has not been set.
     */
    private static final String PROPERTY_TYPE_NOT_SET =
            "rest.schema.propertyTypeNotSet"; //$NON-NLS-1$

    /**
     * Empty Property name for Type '%1$s' is not allowed.
     */
    private static final String EMPTY_PROPERTY_NAME =
            "rest.schema.emptyPropertyName";//$NON-NLS-1$

    /**
     * Property name '%1$s' must be unique within the JSON Type '%2$s'.
     */
    private static final String DUPLICATE_PROP_NAME =
            "rest.schema.duplicatePropertyName";//$NON-NLS-1$

    /**
     * Empty Type name is not allowed in a JSON Schema file.
     */
    private static final String EMPTY_CLASS_NAME = "rest.schema.emptyClassName";//$NON-NLS-1$

    /**
     * Property name '%1$s' in Type '%2$s' should start with a lower case
     * letter.
     */
    private static final String LOWER_CASE_PROPERTY_NAME_ISSUE =
            "warning.rest.schema.propertyNameShouldStartWithLowerCase";//$NON-NLS-1$

    /**
     * Type name '%1$s' should start with a capital letter.
     */
    private static final String UPPER_CASE_TYPE_NAME_ISSUE =
            "warning.rest.schema.typeNameShouldStartWithCapLetter";//$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return Package.class.
     */
    @Override
    public java.lang.Class<?> getTargetClass() {
        return Package.class;
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule
     *      #validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     *            The validation scope.
     * @param o
     *            The Package to validate.
     */
    @Override
    public void validate(IValidationScope scope, Object o) {
        Package pkg = (Package) o;
        Map<String, Class> names = new HashMap<>();
        Set<Class> duplicates = new HashSet<>();
        for (PackageableElement element : pkg.getPackagedElements()) {
            if (element instanceof Class) {
                Class cls = (Class) element;
                validateClass(scope, cls);
                String name = cls.getName();
                Class other = names.get(name);
                if (other == null) {
                    names.put(name, cls);
                } else {
                    duplicates.add(cls);
                    duplicates.add(other);
                }
            }
        }
        for (Class duplicate : duplicates) {
            Collection<String> messages = new ArrayList<>();
            messages.add(duplicate.getName());
            scope.createIssue(DUPLICATE_CLASS_NAME,
                    duplicate.getName(),
                    getUri(duplicate),
                    messages);
        }
    }

    /**
     * @param scope
     *            The validation scope.
     * @param cls
     *            The Class to validate.
     */
    private void validateClass(IValidationScope scope, Class cls) {

        String name = cls.getName();

        if (name == null || name.length() == 0) {
            /*
             * Do not allow empty type names
             */
            IFile file = WorkingCopyUtil.getFile(cls);

            if (file != null) {

                scope.createIssue(EMPTY_CLASS_NAME, file.getName(), getUri(cls));
            }

        } else {
            char charAt = name.charAt(0);

            if (Character.isLetter(charAt)) {
                /*
                 * Class names should start with capital letter
                 */
                if (Character.isLowerCase(charAt)) {

                    Collection<String> messages = new ArrayList<>();
                    messages.add(name);
                    scope.createIssue(UPPER_CASE_TYPE_NAME_ISSUE,
                            cls.getName(),
                            getUri(cls),
                            messages);

                }
            }
        }

        for (Property property : cls.getOwnedAttributes()) {
            validateProperty(scope, cls, property);
        }
    }

    /**
     * Validate the property
     * 
     * @param scope
     * @param cls
     *            the class containing the property
     * @param property
     *            the property to validate
     */
    private void validateProperty(IValidationScope scope, Class cls,
            Property property) {

        String name = property.getName();

        if (name == null || name.length() == 0) {
            /*
             * Do not allow empty property names
             */
            Collection<String> messages = new ArrayList<>();
            messages.add(cls.getName());
            scope.createIssue(EMPTY_PROPERTY_NAME,
                    cls.getName(),
                    getUri(property),
                    messages);

        } else {
            if (!JsonSchemaUtil.isValidPropertyName(name)) {
                /*
                 * check of invalid property names
                 */
                Collection<String> messages = new ArrayList<>();
                messages.add(name);
                messages.add(cls.getName());
                scope.createIssue(INVALID_PROPERTY_NAME,
                        cls.getName(),
                        getUri(property),
                        messages);
            } else if (JsonSchemaUtil.isDuplicatePropertyName(property,
                    cls,
                    name)) {
                /*
                 * Check for suplicate property names in the same type
                 */

                Collection<String> messages = new ArrayList<>();
                messages.add(name);
                messages.add(cls.getName());
                scope.createIssue(DUPLICATE_PROP_NAME,
                        cls.getName(),
                        getUri(property),
                        messages);

            }
            char charAt = name.charAt(0);

            if (Character.isLetter(charAt)) {
                /*
                 * Property names should start with a lower case letter.
                 */

                if (Character.isUpperCase(charAt)) {

                    Collection<String> messages = new ArrayList<>();
                    messages.add(name);
                    messages.add(cls.getName());
                    scope.createIssue(LOWER_CASE_PROPERTY_NAME_ISSUE,
                            cls.getName(),
                            getUri(property),
                            messages);

                }
            }
        }
        Type type = property.getType();
        if (type instanceof Class) {
            Class propertyClass = (Class) type;
            URI uri = ((InternalEObject) propertyClass).eProxyURI();
            if (uri == null) {
                uri = EcoreUtil.getURI(propertyClass);
            }
            if (uri != null) {

                Collection<IndexerItem> results =
                        JsonSchemaUtil.getJSonSchemaIndexerItemList(uri);

                int count = results.size();

                if (count == 0) {
                    Collection<String> messages = new ArrayList<>();
                    messages.add(property.getName());
                    messages.add(cls.getName());
                    scope.createIssue(MISSING_REFERENCED_TYPE, cls.getName()
                            + "." + property.getName(), //$NON-NLS-1$
                            getUri(property),
                            messages);
                } else if (count == 1) {
                    IndexerItem ii = results.iterator().next();
                    IProject project = WorkingCopyUtil.getProjectFor(cls);
                    String itemProjectName =
                            ii.get(IndexerServiceImpl.ATTRIBUTE_PROJECT);
                    IProject itemProject =
                            project.getWorkspace().getRoot()
                                    .getProject(itemProjectName);
                    // If IProject is the not same or is not in
                    // ProjectUtil.getReferencedProjectsHierarchy then throw
                    // an
                    // error as we have a missing project reference.
                    if (!project.equals(itemProject)) {
                        Set<IProject> projects = new HashSet<>();
                        ProjectUtil.getReferencedProjectsHierarchy(project,
                                projects);
                        if (!projects.contains(itemProject)) {
                            Collection<String> messages = new ArrayList<>();
                            messages.add(itemProjectName);
                            messages.add(property.getName());
                            messages.add(ii.getName());
                            scope.createIssue(MISSING_PROJECT_REFERENCE,
                                    cls.getName() + "." + property.getName(), //$NON-NLS-1$
                                    getUri(property),
                                    messages);
                        }
                    }
                }
            }

        } else if (type == null) {

            /*
             * XPD-7764: Saket: Raise an error when property Type for property
             * has not been set.
             */

            Collection<String> messages = new ArrayList<>();

            messages.add(property.getName());

            messages.add(cls.getName());

            scope.createIssue(PROPERTY_TYPE_NOT_SET, cls.getName()
                    + "." + property.getName(), //$NON-NLS-1$
                    getUri(property),
                    messages);

        }
    }

    /**
     * Returns a URI for an EObject.
     * 
     * @param eo
     *            The EObject.
     * @return The URI.
     */
    private String getUri(EObject eo) {
        return eo.eResource().getURIFragment(eo);
    }
}
