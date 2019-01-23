/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.api;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Import;
import org.eclipse.wst.wsdl.Types;
import org.eclipse.wst.wsdl.util.WSDLResourceImpl;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSchemaContent;
import org.eclipse.xsd.XSDSchemaDirective;
import org.eclipse.xsd.util.XSDResourceImpl;

/**
 * Provided a wsdl definition or schema get all the referenced imported/included
 * schemas
 * 
 * @author bharge
 * @since 11 Jul 2013
 */
public class XSDSchemaResolver {

    private static XSDSchemaResolver instance = new XSDSchemaResolver();

    /**
     * @return the instance
     */
    public static XSDSchemaResolver getInstance() {
        return instance;
    }

    /**
     * @return All schema imports (wsdl/xsd) and includes (xsd) for a given wsdl
     *         definition
     */
    public Set<XSDSchema> getReferencedSchemas(Definition wsdlDef) {

        Set<XSDSchema> referencedSchemas = new HashSet<XSDSchema>();

        if (null != wsdlDef) {
            /* WSDL: Imports */

            Set<Definition> allWsdlDefinitions =
                    getAllWsdlDefinitionsForImports(wsdlDef,
                            new HashSet<Definition>());

            for (Definition definition : allWsdlDefinitions) {

                EList<?> eImports = definition.getEImports();
                for (Object impt : eImports) {

                    XSDSchema schema = ((Import) impt).getSchema();
                    /*
                     * schema = null -> its wsdl:import importing another wsdl.
                     * schema not null -> its wsdl:import importing a xsd
                     */
                    if (null != schema) {

                        referencedSchemas.add(schema);
                        addReferencedXSDSchemas(schema, referencedSchemas);
                    }
                }

                /* WSDL: in-line schemas */
                addReferencedInlineSchemas(definition, referencedSchemas);
            }

        }

        return referencedSchemas;
    }

    /**
     * @return All schema imports (wsdl/xsd) and includes (xsd) for a given wsdl
     *         definition or xsd schema
     */
    public Set<XSDSchema> getReferencedSchemas(EObject wsdlDefOrSchema) {

        Set<XSDSchema> referencedSchemas = new HashSet<XSDSchema>();

        if (wsdlDefOrSchema instanceof Definition) {

            Set<XSDSchema> schemas =
                    getReferencedSchemas((Definition) wsdlDefOrSchema);
            referencedSchemas.addAll(schemas);

        } else if (wsdlDefOrSchema instanceof XSDSchema) {

            addReferencedXSDSchemas((XSDSchema) wsdlDefOrSchema,
                    referencedSchemas);
        }
        return referencedSchemas;
    }

    /**
     * adds the inline schema of a given wsdl definition to referenced schemas
     * set
     * 
     * @param definition
     * @param referencedSchemas
     *            Collection on which referenced XSD schemas should be added
     */
    private void addReferencedInlineSchemas(Definition definition,
            Set<XSDSchema> referencedSchemas) {

        Types types = definition.getETypes();
        if (types != null) {

            for (Object schema : types.getSchemas()) {

                if (schema instanceof XSDSchema) {
                    /* this is to add in-line schemas */
                    addReferencedXSDSchemas((XSDSchema) schema,
                            referencedSchemas);
                }
            }
        }
    }

    /**
     * recursively get all the wsdl definitions for a given wsdl definition. go
     * thru all the wsdl imports, load the resource for each imported wsdl and
     * get the definition for that imported wsdl
     * 
     * @param definition
     * @param alreadyProcessed
     *            Collection on which referenced wsdl definitions should be
     *            added
     * @return Collection of referenced wsdl definitions
     */
    private Set<Definition> getAllWsdlDefinitionsForImports(
            Definition definition, HashSet<Definition> alreadyProcessed) {

        if (null != definition && !alreadyProcessed.contains(definition)) {

            alreadyProcessed.add(definition);
            for (Object next : definition.getEImports()) {

                if (next instanceof Import) {

                    Import imp = (Import) next;

                    Resource resource =
                            loadResource(definition.eResource(),
                                    URI.createURI(imp.getLocationURI()));
                    if (resource instanceof WSDLResourceImpl) {

                        Definition def =
                                ((WSDLResourceImpl) resource).getDefinition();
                        if (null != def && null != def.getEImports()) {

                            getAllWsdlDefinitionsForImports(def,
                                    alreadyProcessed);
                        }
                    }

                }
            }
        }

        return alreadyProcessed;

    }

    /**
     * Load the resource from the given schema uri.
     * 
     * @param baseResource
     *            the resource that includes/imports the schema
     * @param schemaUri
     *            absolute or relative uri of the schema. If this is a relative
     *            uri then it will be resolved against the uri of the base
     *            resource provided.
     * @return
     */
    private Resource loadResource(Resource baseResource, URI schemaUri) {

        Resource resource = null;

        if (baseResource != null && baseResource.getResourceSet() != null
                && schemaUri != null) {

            if (schemaUri.isRelative() && !baseResource.getURI().isRelative()) {

                schemaUri = schemaUri.resolve(baseResource.getURI());
            }

            if (schemaUri != null && !schemaUri.isRelative()) {

                resource =
                        baseResource.getResourceSet().getResource(schemaUri,
                                true);
            }
        }

        return resource;

    }

    /**
     * @param schema
     * @param referencedSchemas
     *            Collection on which referenced XSD schemas should be added
     */
    private void addReferencedXSDSchemas(XSDSchema schema,
            Set<XSDSchema> referencedSchemas) {

        if (schema != null) {
            referencedSchemas.add(schema);
            for (XSDSchemaContent schemaRef : schema.getContents()) {

                if (schemaRef instanceof XSDSchemaDirective) {

                    XSDSchema resolvedSchema =
                            getResolvedSchemaForDirective((XSDSchemaDirective) schemaRef);

                    if (null != resolvedSchema
                            && !referencedSchemas.contains(resolvedSchema)) {
                        addReferencedXSDSchemas(resolvedSchema,
                                referencedSchemas);
                    }
                }
            }
        }
    }

    /***
     * resolve the schema for a given schema directive against the schema
     * location and base uri
     * 
     * @param directive
     * @return the resolved <code>XSDSchema</code> or <code>null</code>
     *         otherwise
     */
    public XSDSchema getResolvedSchemaForDirective(XSDSchemaDirective directive) {

        XSDSchema resolvedSchema = directive.getResolvedSchema();

        /*
         * wst returns resolved schema as null if there are no types/elements
         * defined in the schema or if the elements/types are defined but are
         * not referenced. so we will manually resolve the schema by getting the
         * schema location and resolving it against the base uri
         */
        if (null == resolvedSchema) {

            /*
             * Directive is not yet resolved, attempt to locate the referenced
             * XSDResource using the schema location information in the
             * directive
             */
            String schemaLocation = directive.getSchemaLocation();
            Resource eResource = directive.eResource();

            if (null != eResource && null != schemaLocation) {

                URI schemaLocationUri = URI.createURI(schemaLocation);
                URI baseLocationUri = eResource.getURI();

                if (baseLocationUri.isHierarchical()
                        && !baseLocationUri.isRelative()
                        && schemaLocationUri.isRelative()) {
                    schemaLocationUri =
                            schemaLocationUri.resolve(baseLocationUri);
                }

                Resource resource =
                        eResource.getResourceSet()
                                .getResource(schemaLocationUri, true);

                XSDResourceImpl refdResource = null;

                if (resource instanceof XSDResourceImpl) {
                    refdResource =
                            (XSDResourceImpl) eResource.getResourceSet()
                                    .getResource(schemaLocationUri, true);

                    if (null != refdResource) {
                        resolvedSchema = refdResource.getSchema();

                        if (null != resolvedSchema) {
                            return resolvedSchema;
                        }
                    }
                }
            }
        }
        return resolvedSchema;

    }

}
