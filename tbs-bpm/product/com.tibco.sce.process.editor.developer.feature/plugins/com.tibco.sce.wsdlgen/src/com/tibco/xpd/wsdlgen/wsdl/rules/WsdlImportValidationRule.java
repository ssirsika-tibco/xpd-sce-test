/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.wsdlgen.wsdl.rules;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Import;
import org.eclipse.wst.wsdl.Types;
import org.eclipse.xsd.XSDImport;
import org.eclipse.xsd.XSDInclude;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSchemaContent;
import org.eclipse.xsd.XSDSchemaDirective;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Rule to validate if a wsdl referencing schemas (by imports or includes) has
 * any relative path references ("../" reference) outside wsdl folder tree.
 * 
 * This is a temporary rule until ABPM-567 (and/or BX-2454) is fixed or when
 * XPD-2956 would be re-visited later. - NOT TEMPORARY
 * 
 * XPD-2956 is fixed but decided not to get rid of this rule. Because this will
 * be helpful in handling the cases when the user copies wsdl with ../
 * reference. (XPD-2956 handles the case when wsdl is imported using import
 * wizard)
 * 
 * From XPD-6146 onwards this rule also checks if the referenced wsdl/schema
 * exists at all. (one of the reasons for resource not existing can be
 * schemaLocation value not identical to actual path on disk case sensitively)
 * 
 * @author bharge
 * @since 11 Oct 2012
 */
public class WsdlImportValidationRule implements IValidationRule {

    private static final String UNSUPPORTED_SCHEMA_REFERENCE =
            "bpmn.dev.unsupportedSchemaReferenceMessage_1"; //$NON-NLS-1$

    private static final String IMPORT_FILE_DOESNT_EXIST =
            "bpmn.dev.importFileDoesntExist"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return
     */
    @Override
    public Class<?> getTargetClass() {
        return Definition.class;
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param o
     */
    @Override
    public void validate(IValidationScope scope, Object o) {

        if (o instanceof Definition) {
            Definition definition = (Definition) o;

            /* get all imported and included schema contents */
            Set<EObject> importedAndIncludedSchemaContents =
                    getAllReferencedSchemasContents(definition);

            /*
             * validate the schema contents to see if any schema location has
             * relative path reference having "../"
             */
            validateSchemaLocation(scope,
                    definition,
                    importedAndIncludedSchemaContents);

        }

    }

    /**
     * validates the schema location by iterating thru all the referenced schema
     * contents
     * 
     * @param scope
     * @param definition
     * @param importedAndIncludedSchemaContents
     */
    private void validateSchemaLocation(IValidationScope scope,
            Definition definition,
            Set<EObject> importedAndIncludedSchemaContents) {

        IFile wsdlFile = WorkingCopyUtil.getFile(definition);
        String schemaLocation = null;

        IPath wsdlFullPath = wsdlFile.getParent().getFullPath();

        /*
         * iterate thru the imported and included schema contents to get the
         * schema location. if any first found schema location contains relative
         * path with "../" report the issue
         */
        for (EObject schemaImportOrInclude : importedAndIncludedSchemaContents) {
            IFile referencingFile =
                    WorkingCopyUtil.getFile(schemaImportOrInclude);

            if (schemaImportOrInclude instanceof Import) {

                schemaLocation =
                        ((Import) schemaImportOrInclude).getLocationURI();

            } else if (schemaImportOrInclude instanceof XSDImport
                    || schemaImportOrInclude instanceof XSDInclude) {

                schemaLocation =
                        ((XSDSchemaDirective) schemaImportOrInclude)
                                .getSchemaLocation();
            }

            if (null != schemaLocation) {

                IFile referencedFile =
                        referencingFile.getParent().getFile(new Path(
                                schemaLocation));

                if (schemaLocation.startsWith("../")) { //$NON-NLS-1$

                    /*
                     * if any wsdl invoke references a type using ../ reference
                     * which is outside wsdl folder, it is a problem with bpel
                     * generation.
                     */
                    if (!wsdlFullPath.isPrefixOf(referencedFile.getFullPath())) {

                        createIssueForUnsupportedWsdlSchemaRef(scope,
                                definition,
                                referencingFile,
                                schemaLocation);
                    }
                }

                /*
                 * XPD-6146: Check that schemaLocation value is identical to
                 * actual path on disk case sensitively.
                 */
                if (!referencedFile.exists()) {

                    scope.createIssue(IMPORT_FILE_DOESNT_EXIST,
                            referencedFile.getName(),
                            referencingFile.getName(),
                            Arrays.asList(new String[] { schemaLocation,
                                    referencingFile.getName() }));
                }
            }
        }
    }

    /**
     * @param scope
     * @param definition
     * @param file
     * @param schemaLocation
     */
    private void createIssueForUnsupportedWsdlSchemaRef(IValidationScope scope,
            Definition definition, IFile file, String schemaLocation) {

        scope.createIssue(UNSUPPORTED_SCHEMA_REFERENCE,
                definition.getLocation(),
                EcoreUtil.getURI(definition).toString(),
                Arrays.asList(new String[] { schemaLocation, file.getName() }));
    }

    /**
     * get the schema contents for all the schemas referenced by the given wsdl
     * definition
     * 
     * @param definition
     * @return
     */
    private Set<EObject> getAllReferencedSchemasContents(Definition definition) {

        Set<EObject> importedAndIncludedSchemaContents =
                new LinkedHashSet<EObject>();

        EList<?> eImports = definition.getEImports();
        for (Object eImport : eImports) {
            if (eImport instanceof Import) {
                importedAndIncludedSchemaContents.add((EObject) eImport);
            }
        }
        Types eTypes = definition.getETypes();

        if (null != eTypes) {
            List<?> schemas = eTypes.getSchemas();
            Set<XSDSchema> allReferencedSchemas =
                    new LinkedHashSet<XSDSchema>();

            for (Object schema : schemas) {
                /* recursively get all the referenced schemas by this schema */
                if (schema instanceof XSDSchema) {
                    allReferencedSchemas
                            .addAll(getAllReferencedSchemas(allReferencedSchemas,
                                    (XSDSchema) schema));
                }
            }
            /* add schema contents for all the referenced schemas */
            for (XSDSchema schema : allReferencedSchemas) {
                addXsdSchemaImportsOrIncludes(importedAndIncludedSchemaContents,
                        schema);
            }
        }
        return importedAndIncludedSchemaContents;
    }

    /***
     * 
     * recursively get all the schemas referenced (imported/included) by this
     * schema
     * 
     * @param xsdSchemasSet
     * 
     * @param xsdSchema
     * @return
     */
    private Set<XSDSchema> getAllReferencedSchemas(
            Set<XSDSchema> xsdSchemasSet, XSDSchema xsdSchema) {

        xsdSchemasSet.add(xsdSchema);

        EList<XSDSchemaContent> xsdSchemaContents = xsdSchema.getContents();

        for (XSDSchemaContent xsdSchemaContent : xsdSchemaContents) {
            if (xsdSchemaContent instanceof XSDImport
                    || xsdSchemaContent instanceof XSDInclude) {
                XSDSchema resolvedSchema =
                        ((XSDSchemaDirective) xsdSchemaContent)
                                .getResolvedSchema();
                if (null != resolvedSchema) {
                    if (!xsdSchemasSet.contains(resolvedSchema)) {
                        getAllReferencedSchemas(xsdSchemasSet, resolvedSchema);
                    }
                }
            }
        }

        return xsdSchemasSet;
    }

    /**
     * adds the schema contents for each xsd imported/included schema
     * 
     * @param schemaImportsAndIncludes
     * @param eObject
     */
    private void addXsdSchemaImportsOrIncludes(
            Set<EObject> schemaImportsAndIncludes, XSDSchema xsdSchema) {

        EList<XSDSchemaContent> contents = xsdSchema.getContents();

        for (XSDSchemaContent xsdSchemaContent : contents) {
            if (xsdSchemaContent instanceof XSDImport
                    || xsdSchemaContent instanceof XSDInclude) {
                schemaImportsAndIncludes.add(xsdSchemaContent);
            }
        }
    }

}
