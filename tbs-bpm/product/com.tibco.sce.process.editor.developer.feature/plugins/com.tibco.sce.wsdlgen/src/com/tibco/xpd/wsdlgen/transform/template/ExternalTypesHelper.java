/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.wsdlgen.transform.template;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.ExtensibilityElement;
import org.eclipse.wst.wsdl.Types;
import org.eclipse.wst.wsdl.WSDLFactory;
import org.eclipse.wst.wsdl.XSDSchemaExtensibilityElement;
import org.eclipse.wst.wsdl.util.WSDLConstants;
import org.eclipse.xsd.XSDAnnotation;
import org.eclipse.xsd.XSDConcreteComponent;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDFactory;
import org.eclipse.xsd.XSDImport;
import org.eclipse.xsd.XSDPackage;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSchemaContent;
import org.eclipse.xsd.XSDTypeDefinition;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.DependencyAnalyzer;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.xsdtransform.xsdindexing.XsdUIUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.wsdlgen.transform.XtendTransformerXpdl2Wsdl;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xsd.XSDWorkingCopy;

/**
 * Delegate class to assist the transform helper to handle external types.
 * 
 * External types - External References - Processes using BOM's - BOM's get
 * transformed into XSDs and XSDs are inlined into the WSDL
 * 
 * @author rsomayaj
 * @since 3.3 (28 Jan 2010)
 */
public class ExternalTypesHelper {

    /**
     * 
     */
    public static final String SCHEMA_FOR_SCHEMA_URI_2001 =
            org.eclipse.xsd.util.XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001;

    /**
     * 
     */
    private static final String TARGET_NAMESPACE_INDEX_LABEL =
            "target_namespace"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String SLASH_DELIMITER = "/"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String XSD_FILE_EXTENSION = "xsd"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String BOM_FILE_EXTENSION = "bom"; //$NON-NLS-1$

    /**
     * Searches for the given complex type in the WSDL definition schemas. If
     * there isn't one available, it searches for the schema, adds it to
     * 
     * @param definition
     * @param targetNs
     * @param complexTypeName
     */
    @SuppressWarnings("unchecked")
    protected static XSDTypeDefinition getComplexTypeInWsdlDefinition(
            Definition definition, IndexerItem indexedItem) {
        String targetNs = indexedItem.get(TARGET_NAMESPACE_INDEX_LABEL);
        if (definition.getETypes() == null) {
            createWsdlETypes(definition);
        }
        List<XSDSchema> schemas = definition.getETypes().getSchemas();
        XSDSchema relevantSchema = null;
        String complexTypeName = indexedItem.getName();
        for (XSDSchema schema : schemas) {
            if (schema.getTargetNamespace().equals(targetNs)) {
                relevantSchema = schema;
                EObject objInList =
                        EMFSearchUtil.findInList(schema.getTypeDefinitions(),
                                XSDPackage.eINSTANCE
                                        .getXSDNamedComponent_Name(),
                                complexTypeName);
                if (objInList instanceof XSDTypeDefinition) {
                    XSDTypeDefinition typeDefinition =
                            (XSDTypeDefinition) objInList;
                    // updateComplextTypeElement(definition,
                    // typeDefinition,
                    // relevantSchema);
                    return typeDefinition;
                }
            }
        }
        if (relevantSchema == null) {
            XSDTypeDefinition complexTypeAdded =
                    getTypeDefinitionAfterAddingSchemaToDefinition(definition,
                            indexedItem);
            return complexTypeAdded;
        } else {
            XSDSchemaContent schemaContent =
                    addSchemaContentToSchema(definition,
                            relevantSchema,
                            indexedItem,
                            false);
            if (schemaContent instanceof XSDTypeDefinition) {
                return (XSDTypeDefinition) schemaContent;
            }
        }
        return null;
    }

    /**
     * Adds the complex types that aren't in the WSDL schema definition to it.
     * 
     * @param definition
     * @param relevantSchema
     * @param indexedItem
     * @return
     */
    @SuppressWarnings("restriction")
    private static XSDSchemaContent addSchemaContentToSchema(
            Definition definition, XSDSchema relevantSchema,
            IndexerItem indexedItem, boolean isElement) {
        String attrPath = indexedItem.get(IndexerServiceImpl.ATTRIBUTE_PATH);
        String schemaContentName = indexedItem.getName();
        /**
         * schemaContent could be either a type definition or an element
         * declaration.
         */
        XSDSchemaContent schemaContent = null;
        IFile xsdFile = ResourcesPlugin.getWorkspace().getRoot()
                .getFile(new Path(attrPath));
        if (xsdFile.exists()
                && xsdFile.getFileExtension().equals(XSD_FILE_EXTENSION)) {
            WorkingCopy workingCopy =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(xsdFile);
            if (workingCopy.getRootElement() instanceof XSDSchema) {
                XSDSchema schema = (XSDSchema) workingCopy.getRootElement();
                if (isElement) {
                    // If the schemacontent to be updated is an XSD Element
                    // declaration.
                    List<EObject> elementInList = EMFSearchUtil.findManyInList(
                            schema.getElementDeclarations(),
                            XSDPackage.eINSTANCE.getXSDNamedComponent_Name(),
                            schemaContentName);
                    XSDSchema schemaInDefinition =
                            getSchemaInDefinition(definition,
                                    schema.getTargetNamespace());
                    for (EObject eObj : elementInList) {
                        if (eObj instanceof XSDElementDeclaration) {
                            XSDElementDeclaration elementDeclaration =
                                    (XSDElementDeclaration) eObj;
                            XSDConcreteComponent clonedElementDeclaration =
                                    elementDeclaration.cloneConcreteComponent(
                                            true,
                                            false);
                            if (clonedElementDeclaration instanceof XSDElementDeclaration) {
                                schemaContent =
                                        (XSDElementDeclaration) clonedElementDeclaration;
                                schemaInDefinition.getContents()
                                        .add(schemaContent);
                            }
                        }
                    }
                } else {

                    // If the schemaContent to be updated is an XSD Type
                    // Definition
                    List<EObject> complexTypesInList = EMFSearchUtil
                            .findManyInList(schema.getTypeDefinitions(),
                                    XSDPackage.eINSTANCE
                                            .getXSDNamedComponent_Name(),
                                    schemaContentName);
                    XSDSchema schemaInDefinition =
                            getSchemaInDefinition(definition,
                                    schema.getTargetNamespace());
                    for (EObject eObj : complexTypesInList) {
                        if (eObj instanceof XSDTypeDefinition) {
                            XSDTypeDefinition typeDefinition =
                                    (XSDTypeDefinition) eObj;
                            XSDConcreteComponent clonedTypeDefinition =
                                    typeDefinition.cloneConcreteComponent(true,
                                            false);
                            if (clonedTypeDefinition instanceof XSDTypeDefinition) {
                                schemaContent =
                                        (XSDTypeDefinition) clonedTypeDefinition;
                                schemaInDefinition.getContents()
                                        .add(schemaContent);
                            }
                        }
                    }
                }
            }
        }
        return schemaContent;
    }

    /**
     * @param definition
     * @param targetNamespace
     * @return the schema with the given target namespace in the WSDL definition
     */
    private static XSDSchema getSchemaInDefinition(Definition definition,
            String targetNamespace) {
        if (definition.getETypes() != null) {
            List schemas = definition.getETypes().getSchemas();
            for (Object obj : schemas) {
                if (obj instanceof XSDSchema) {
                    XSDSchema schema = (XSDSchema) obj;
                    if (schema.getTargetNamespace().equals(targetNamespace)) {
                        return schema;
                    }
                }
            }

        }
        return null;
    }

    /**
     * return the {@link XSDTypeDefinition} after adding the schema to the WSDL
     * 
     * 
     * @param definition
     * @param indexedItem
     */
    private static XSDTypeDefinition getTypeDefinitionAfterAddingSchemaToDefinition(
            Definition definition, IndexerItem indexedItem) {
        addSchemaToDefinition(definition, indexedItem);
        XSDSchemaContent schemaContent =
                findXSDSchemaContent(definition, indexedItem, false);
        if (schemaContent instanceof XSDTypeDefinition) {
            return (XSDTypeDefinition) schemaContent;
        }

        return null;
    }

    /**
     * Adds the schema which the indexed item belongs to to the WSDL
     * 
     * @param definition
     * @param indexedItem
     */
    @SuppressWarnings("restriction")
    private static void addSchemaToDefinition(Definition definition,
            IndexerItem indexedItem) {
        String attrPath = indexedItem.get(IndexerServiceImpl.ATTRIBUTE_PATH);

        IFile xsdFile = ResourcesPlugin.getWorkspace().getRoot()
                .getFile(new Path(attrPath));
        if (xsdFile.exists()
                && XSD_FILE_EXTENSION.equals(xsdFile.getFileExtension())) {
            String xsdProjectRelativeFileName =
                    getProjectRelativeFileName(xsdFile);
            String bomOriginFileName = XsdUIUtil
                    .queryBomOriginFilename(xsdProjectRelativeFileName);

            /*
             * Find the BOM file Iterate through it and add all the relevant
             * XSDs Find its dependencies and recursively add all XSDs that it
             * generates.
             */

            // XPD-8358: toFileString() on the URI now returns null (previously
            // it was the original sting. Hence now we pass directly
            // bomOriginFileName to get the file from workspace root.

            // URI createURI = URI.createURI(bomOriginFileName);
            // String bomFileString = createURI.toFileString();

            IFile bomFile = ResourcesPlugin.getWorkspace().getRoot()
                    .getFile(new Path(bomOriginFileName));

            if (bomFile != null && bomFile.exists()) {
                addSchemasToDefinition(definition, getSchemasForBom(bomFile));
            }
        }
    }

    /**
     * 
     * @param xsdsFromBom
     * @param bomResource
     */
    private static void appendXsdsFromBomsToSet(Set<String> xsdsFromBom,
            IResource bomResource) {
        xsdsFromBom.addAll(
                XsdUIUtil.queryGeneratedXsdsFromBom(bomResource.getProject(),
                        bomResource.getFullPath().toString()));
        WorkingCopy bomWC = WorkingCopyUtil.getWorkingCopy(bomResource);
        if (null != bomWC) {
            List<IResource> dependency = bomWC.getDependency();
            for (IResource resource : dependency) {
                if (resource != null && BOM_FILE_EXTENSION
                        .equals(resource.getFileExtension())) {
                    appendXsdsFromBomsToSet(xsdsFromBom, resource);
                }

            }
        }

    }

    /**
     * This method returns an {@link XSDSchemaContent} for the given definition,
     * indexeditem and a flag whether to look for elements or type definitions
     * 
     * @param definition
     * @param indexedItem
     * @param isElementRequired
     * @return
     */
    private static XSDSchemaContent findXSDSchemaContent(Definition definition,
            IndexerItem indexedItem, boolean isElementRequired) {
        Types types = definition.getETypes();
        String complexTypeName = indexedItem.getName();
        String targetNameSpace = indexedItem.get(TARGET_NAMESPACE_INDEX_LABEL);

        List schemas = types.getSchemas();

        if (targetNameSpace != null) {
            for (Object schObj : schemas) {
                if (schObj instanceof XSDSchema) {
                    XSDSchema schema = (XSDSchema) schObj;
                    if (targetNameSpace.equals(schema.getTargetNamespace())) {

                        if (isElementRequired) {

                            EObject objFound = EMFSearchUtil.findInList(
                                    schema.getElementDeclarations(),
                                    XSDPackage.eINSTANCE
                                            .getXSDNamedComponent_Name(),
                                    complexTypeName);
                            if (objFound instanceof XSDElementDeclaration) {
                                return (XSDElementDeclaration) objFound;
                            }
                        } else {
                            List<XSDTypeDefinition> typeDefinitions =
                                    schema.getTypeDefinitions();
                            EObject objFound = EMFSearchUtil.findInList(
                                    typeDefinitions,
                                    XSDPackage.eINSTANCE
                                            .getXSDNamedComponent_Name(),
                                    complexTypeName);
                            if (objFound instanceof XSDTypeDefinition) {
                                return (XSDTypeDefinition) objFound;
                            }
                        }
                    }

                }
            }
        }
        return null;
    }

    /**
     * Annotation to be cloned schema to BOMORIGIN
     * 
     * @param definition
     * @param clonedSchema
     * @param originalSchema
     */
    private static void addAnnotation(Definition definition,
            XSDSchema originalSchema, XSDSchema clonedSchema) {
        definition.updateElement(true);

        Document document = definition.getDocument();

        XSDAnnotation annotation = null;
        if (clonedSchema.getAnnotations().isEmpty()) {
            annotation = XSDFactory.eINSTANCE.createXSDAnnotation();
            clonedSchema.getAnnotations().add(annotation);
        } else {
            annotation = clonedSchema.getAnnotations().get(0);
        }

        Element documentationElement =
                document.createElementNS(WSDLConstants.XSD_NAMESPACE_URI,
                        "xsd:" + WSDLConstants.DOCUMENTATION_ELEMENT_TAG); //$NON-NLS-1$
        Element annotationElement = annotation.getElement();
        if (annotationElement != null) {
            annotationElement.insertBefore(documentationElement, null);
        } else {
            annotation.setElement(documentationElement);
        }

        Element docChild = document.createElement("DocumentationElement"); //$NON-NLS-1$
        String documentationAttributeValue = XsdUIUtil
                .getDocumentationAttributeValue(XsdUIUtil.BOM_ORIGIN_TAGNAME,
                        originalSchema);
        docChild.setAttribute(XsdUIUtil.BOM_ORIGIN_TAGNAME,
                documentationAttributeValue);
        documentationElement.appendChild(docChild);
        definition.updateElement(true);
    }

    /**
     * 
     * @param file
     * @return the project relative file name including the project name.
     */
    private static String getProjectRelativeFileName(IFile file) {
        String projectName = file.getProject().getName();
        String filePortableString =
                file.getProjectRelativePath().toPortableString();
        String projectRelativeFileName = SLASH_DELIMITER + projectName
                + SLASH_DELIMITER + filePortableString;
        return projectRelativeFileName;
    }

    /**
     * This method is called to clear the schema locations of the XSDImport. The
     * schema locations needs to cleared because when inlined in the WSDL these
     * are obfuscated and the WSDL becomes invalid.
     * 
     * @param clonedSchema
     */
    protected static void clearSchemaLocations(XSDSchema clonedSchema) {
        for (XSDSchemaContent schemaContent : clonedSchema.getContents()) {
            if (schemaContent instanceof XSDImport) {
                XSDImport xsdImport = (XSDImport) schemaContent;
                xsdImport.setSchemaLocation(null);
            }
        }
    }

    /**
     * Add the schema's target namespace to the list of namespaces in the given
     * definition.
     * 
     * @param definition
     * @param schema
     * @return the namespace prefix used for the namespace.
     */
    private static String addSchemaNamespaceToDefinition(Definition definition,
            XSDSchema schema) {

        String targetNamespace = schema.getTargetNamespace();

        if (targetNamespace != null) {
            /*
             * Using "ttns" rather than "tns" to distinguish this from the
             * namespace prefixes used in the inlined schemas. If they are the
             * same then the inline schemas will omit its namespace attribute
             */
            String prefix = "ttns"; //$NON-NLS-1$
            String prefixPattern = prefix + "%d"; //$NON-NLS-1$

            Set<?> usedPrefixes = definition.getNamespaces().keySet();
            int idx = 1;
            while (usedPrefixes.contains(prefix)) {
                prefix = String.format(prefixPattern, idx++);
            }

            definition.addNamespace(prefix, targetNamespace);
            return prefix;
        }

        return null;
    }

    /**
     * @param definition
     * @param schemaList
     */
    protected static void addSchemasToWsdlETypes(Definition definition,
            List schemaList) {
        Types types = definition.getETypes();
        Map<String, String> idMap = new HashMap<String, String>();
        if (types == null) {
            types = createWsdlETypes(definition);
        }

        for (Object obj : schemaList) {
            if (obj instanceof XSDSchema) {

                XSDSchemaExtensibilityElement extensibilityElement =
                        WSDLFactory.eINSTANCE
                                .createXSDSchemaExtensibilityElement();
                extensibilityElement.setEnclosingDefinition(definition);

                XSDSchema schema = (XSDSchema) obj;
                EList<XSDTypeDefinition> typeDefinitions =
                        schema.getTypeDefinitions();
                for (XSDTypeDefinition td : typeDefinitions) {
                    String idAttribute = td.getElement().getAttribute("id"); //$NON-NLS-1$
                    if (idAttribute != null) {
                        idMap.put(td.getName(), idAttribute);
                    }
                }

                XSDConcreteComponent clonedSchema =
                        schema.cloneConcreteComponent(true, false);

                schema.setSchemaForSchemaQNamePrefix(XSD_FILE_EXTENSION);
                if (clonedSchema instanceof XSDSchema) {
                    XSDSchema cSchema = (XSDSchema) clonedSchema;
                    clearSchemaLocations(cSchema);
                    removeSchemaExistInDefinition(types,
                            cSchema.getTargetNamespace());
                    extensibilityElement.setSchema(cSchema);
                    types.addExtensibilityElement(extensibilityElement);
                    addAnnotation(definition, schema, cSchema);
                }
            }
        }
    }

    /**
     * @param definition
     * @return
     */
    protected static Types createWsdlETypes(Definition definition) {
        Types types = definition.getETypes();

        if (XtendTransformerXpdl2Wsdl.shouldGenerateChecksum()) {
            /*
             * If generating a checksum then we need to re-create the types
             * (inline schemas) to be consistent between incremental and full
             * builds
             */
            if (types != null) {
                Set<String> namespacePrefixesToRemove = new HashSet<String>();
                String defNamespace = definition.getTargetNamespace();
                for (Object next : types.getEExtensibilityElements()) {
                    if (next instanceof XSDSchemaExtensibilityElement) {
                        String namespace =
                                ((XSDSchemaExtensibilityElement) next)
                                        .getSchema().getTargetNamespace();
                        if (namespace != null
                                && !namespace.equals(defNamespace)) {
                            String prefix = definition.getPrefix(namespace);
                            if (prefix != null) {
                                namespacePrefixesToRemove.add(prefix);
                            }
                        }
                    }
                }

                definition.setTypes(null);
                // Reset types so that it gets created below
                types = null;

                if (!namespacePrefixesToRemove.isEmpty()) {
                    removeNamespace(definition, namespacePrefixesToRemove);
                }
            }
        }

        if (types == null) {
            types = WSDLFactory.eINSTANCE.createTypes();
            XSDSchemaExtensibilityElement extensibilityElement =
                    WSDLFactory.eINSTANCE.createXSDSchemaExtensibilityElement();
            extensibilityElement.setEnclosingDefinition(definition);

            XSDSchema xsdSchema = XSDFactory.eINSTANCE.createXSDSchema();
            xsdSchema.setSchemaForSchemaQNamePrefix(XSD_FILE_EXTENSION);
            Map<String, String> qNamePrefixToNamespaceMap =
                    xsdSchema.getQNamePrefixToNamespaceMap();
            xsdSchema.setTargetNamespace(definition.getTargetNamespace());
            qNamePrefixToNamespaceMap.put(null,
                    definition.getTargetNamespace());
            qNamePrefixToNamespaceMap.put(
                    xsdSchema.getSchemaForSchemaQNamePrefix(),
                    SCHEMA_FOR_SCHEMA_URI_2001);

            extensibilityElement.setSchema(xsdSchema);
            types.addExtensibilityElement(extensibilityElement);
            definition.setETypes(types);
        }
        return types;
    }

    /**
     * Remove the given namespace prefixes from the Definition.
     * 
     * @param definition
     * @param prefixes
     */
    private static void removeNamespace(Definition definition,
            Collection<String> prefixes) {

        Element element = definition.getElement();
        for (String prefix : prefixes) {
            definition.addNamespace(prefix, null);
            /*
             * Also need to update the underlying element object. Just removing
             * it from the definition namespace list doesn't seem to work as the
             * saved wsdl file still has the namespaces.
             */
            if (element != null) {
                element.removeAttribute("xmlns:" + prefix); //$NON-NLS-1$
            }
        }
    }

    /**
     * @param types
     * @param targetNamespace
     */
    private static void removeSchemaExistInDefinition(Types types,
            String targetNamespace) {
        List schemas = types.getSchemas();
        List<XSDSchema> tempSchemas = new ArrayList<XSDSchema>();
        for (Object obj : schemas) {
            if (obj instanceof XSDSchema) {
                XSDSchema schema = (XSDSchema) obj;
                if (schema.getTargetNamespace().equals(targetNamespace)) {
                    tempSchemas.add(schema);
                }
            }
        }
        List<XSDSchemaExtensibilityElement> extensibilityElements =
                new ArrayList<XSDSchemaExtensibilityElement>();
        List existingElements = types.getEExtensibilityElements();

        for (Object obj : existingElements) {
            if (obj instanceof XSDSchemaExtensibilityElement) {
                XSDSchemaExtensibilityElement schemaExtensibilityElement =
                        (XSDSchemaExtensibilityElement) obj;
                if (tempSchemas
                        .contains(schemaExtensibilityElement.getSchema())) {
                    extensibilityElements.add(schemaExtensibilityElement);
                }

            }
        }
        if (!(extensibilityElements.isEmpty())) {
            types.getEExtensibilityElements().removeAll(extensibilityElements);
        }
    }

    /**
     * @param definition
     * @param schema
     */
    protected static void updateSchemaInDefinition(Definition definition,
            Model bomModel) {

        // If any change to the BOM - find all the schemas that referred to the
        // BOM, remove them and re-add them.

        IFile bomFile = WorkingCopyUtil.getFile(bomModel);
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(bomModel);

        if (wc != null) {
            List<String> dependencies = getBOMDependencies(wc);
            dependencies.add(bomFile.getFullPath().toString());

            /*
             * Remove all existing schemas that are going to be updated
             */
            EList<?> elements = null;
            if (definition.getETypes() != null) {
                elements = definition.getETypes().getEExtensibilityElements();
                EList<ExtensibilityElement> elementsToRemove =
                        new BasicEList<ExtensibilityElement>();

                for (Object next : elements) {
                    if (next instanceof XSDSchemaExtensibilityElement) {
                        XSDSchemaExtensibilityElement schemaElem =
                                (XSDSchemaExtensibilityElement) next;

                        XSDSchema schema = schemaElem.getSchema();
                        if (schema != null) {
                            String bomPath = getBOMOriginAnnotationValue(
                                    schema.getAnnotations());
                            if (bomPath != null
                                    && dependencies.contains(bomPath)) {
                                elementsToRemove.add(schemaElem);
                            }
                        }
                    }
                }
                if (!elementsToRemove.isEmpty()) {
                    elements.removeAll(elementsToRemove);

                    /*
                     * Remove all namespaces for the XSDSchemas that were just
                     * deleted
                     */
                    Set<String> namespacesToRemove = new HashSet<String>();
                    String defNamespace = definition.getTargetNamespace();

                    for (ExtensibilityElement elem : elementsToRemove) {
                        String targetNamespace =
                                ((XSDSchemaExtensibilityElement) elem)
                                        .getSchema().getTargetNamespace();
                        if (targetNamespace != null
                                && !targetNamespace.equals(defNamespace)) {
                            for (Object next : definition.getNamespaces()
                                    .entrySet()) {
                                if (next instanceof Entry<?, ?>) {
                                    if (targetNamespace.equals(
                                            ((Entry<?, ?>) next).getValue())) {
                                        namespacesToRemove.add(
                                                (String) ((Entry<?, ?>) next)
                                                        .getKey());
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    if (!namespacesToRemove.isEmpty()) {
                        removeNamespace(definition, namespacesToRemove);
                    }
                }
            } else {
                createWsdlETypes(definition);
            }

            // Re-add all the schemas
            addSchemasToDefinition(definition, getSchemasForBom(bomFile));

        }

    }

    /**
     * Remove all schemas that are no longer used in the wsdl.
     * 
     * @param definition
     * @param xpdlPackage
     */
    @SuppressWarnings("unchecked")
    public static void cleanupInlinedSchemas(Definition definition,
            Package xpdlPackage) {
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(xpdlPackage);
        if (wc != null) {
            List<String> bomPaths = getBOMDependencies(wc);

            EList<XSDSchemaExtensibilityElement> elementsToRemove =
                    new BasicEList<XSDSchemaExtensibilityElement>();

            for (Object next : definition.getETypes()
                    .getEExtensibilityElements()) {
                if (next instanceof XSDSchemaExtensibilityElement) {
                    XSDSchemaExtensibilityElement elem =
                            (XSDSchemaExtensibilityElement) next;
                    if (elem.getSchema() != null) {
                        String value = getBOMOriginAnnotationValue(
                                elem.getSchema().getAnnotations());

                        if (value != null && !bomPaths.contains(value)) {
                            elementsToRemove.add(elem);
                        }
                    }
                }
            }

            if (!elementsToRemove.isEmpty()) {
                Map<?, ?> namespaces = definition.getNamespaces();
                List<String> tnsToRemove = new ArrayList<String>();
                for (XSDSchemaExtensibilityElement elem : elementsToRemove) {
                    String tns = elem.getSchema().getTargetNamespace();

                    if (tns != null) {
                        for (Entry<?, ?> tnsEntry : namespaces.entrySet()) {
                            if (tns.equals(tnsEntry.getValue())) {
                                tnsToRemove.add((String) tnsEntry.getKey());
                                break;
                            }
                        }
                    }
                }

                definition.getETypes().getEExtensibilityElements()
                        .removeAll(elementsToRemove);

                if (!tnsToRemove.isEmpty()) {
                    removeNamespace(definition, tnsToRemove);
                }
            }
        }
    }

    /**
     * Get all direct and indirect BOM dependents for the given working copy.
     * 
     * @param wc
     * @return list of bom (workspace relative) paths
     */
    private static List<String> getBOMDependencies(WorkingCopy wc) {
        List<String> bomPaths = new ArrayList<String>();

        if (wc != null) {
            // Get all the BOMs that this working copy depends on
            List<IResource> dependencies = wc.getDependency();

            for (IResource resource : dependencies) {
                if (resource instanceof IFile
                        && BOMResourcesPlugin.BOM_FILE_EXTENSION
                                .equals(resource.getFileExtension())) {
                    String path = resource.getFullPath().toString();

                    if (!bomPaths.contains(path)) {
                        WorkingCopy workingCopy =
                                WorkingCopyUtil.getWorkingCopy(resource);
                        if (workingCopy instanceof BOMWorkingCopy) {
                            // Not already processed so process its dependencies
                            // too
                            bomPaths.add(path);
                            bomPaths.addAll(getBOMDependencies(workingCopy));
                        }
                    }
                }
            }
        }

        return bomPaths;
    }

    /**
     * Create the schema extensibiliy element and set the enclosing definition.
     * 
     * @param definition
     * @return
     */
    private static XSDSchemaExtensibilityElement createSchemaExtensibilityElement(
            Definition definition) {
        XSDSchemaExtensibilityElement extensibilityElement =
                WSDLFactory.eINSTANCE.createXSDSchemaExtensibilityElement();
        extensibilityElement.setEnclosingDefinition(definition);
        return extensibilityElement;
    }

    @SuppressWarnings("unchecked")
    private static void addSchemasToDefinition(Definition definition,
            List<XSDSchema> schemasToAdd) {

        if (definition.getETypes() == null) {
            return;
        }

        EList<XSDSchemaExtensibilityElement> elements =
                new BasicEList<XSDSchemaExtensibilityElement>();

        /*
         * Add all schemas in one go. This will avoid the problem where the
         * schema resource is trying to resolve and load an imported schema that
         * has not been in-lined yet (and ending up making a call on the net to
         * a non-existent schema).
         */
        Map<XSDSchema /* original schema */, XSDSchema /* cloned schema */> annotationsToAdd =
                new HashMap<XSDSchema, XSDSchema>();
        for (XSDSchema schema : schemasToAdd) {

            List<?> schemas = definition.getETypes()
                    .getSchemas(schema.getTargetNamespace());
            if (schemas != null && !schemas.isEmpty()) {
                // This schema is already inlined in the WSDL so move to the
                // next schema
                continue;
            }

            XSDSchemaExtensibilityElement extensibilityElement =
                    createSchemaExtensibilityElement(definition);
            XSDSchema clonedSchema = createClonedSchema(definition, schema);

            extensibilityElement.setSchema(clonedSchema);

            elements.add(extensibilityElement);

            /*
             * The annotations for the documentation (BOM Source) will be added
             * after the schemas have been added to the WSDL. If the annotation
             * is added to the model here then the wrapping xsd:annotation
             * around the xsd:document is getting lost.
             */
            annotationsToAdd.put(schema, clonedSchema);

            addSchemaNamespaceToDefinition(definition, schema);
        }

        if (!elements.isEmpty()) {
            definition.getETypes().getExtensibilityElements().addAll(elements);
        }

        // Update the annotations
        if (!annotationsToAdd.isEmpty()) {
            for (Entry<XSDSchema, XSDSchema> entry : annotationsToAdd
                    .entrySet()) {
                addAnnotation(definition, entry.getKey(), entry.getValue());
            }
        }

    }

    /**
     * Create a cloned of a schema.
     * 
     * @param definition
     * @param schema
     * @return
     */
    private static XSDSchema createClonedSchema(Definition definition,
            XSDSchema schema) {
        XSDSchema clonedSchema =
                (XSDSchema) schema.cloneConcreteComponent(true, false);

        clearSchemaLocations(clonedSchema);
        clonedSchema.setSchemaForSchemaQNamePrefix(XSD_FILE_EXTENSION);

        return clonedSchema;
    }

    /**
     * Get all the schemas that have been generated from the given BOM and all
     * the BOM that it DIRECTLY depends on.
     * 
     * @param bomFile
     * @return
     */
    private static List<XSDSchema> getSchemasForBom(IFile bomFile) {
        List<XSDSchema> schemas = new ArrayList<XSDSchema>();

        DependencyAnalyzer analyzer =
                new DependencyAnalyzer(Collections.singleton(bomFile));
        List<IFile> result = analyzer.getResult();

        for (IFile file : result) {
            Set<String> xsdPaths =
                    XsdUIUtil.queryGeneratedXsdsFromBom(file.getProject(),
                            file.getFullPath().toString());

            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
            for (String xsdPath : xsdPaths) {
                IResource resource = root.findMember(xsdPath);
                if (resource instanceof IFile) {
                    WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(resource);
                    if (wc instanceof XSDWorkingCopy) {
                        schemas.add((XSDSchema) wc.getRootElement());
                    }
                }
            }
        }

        /*
         * Sort the schemas as per target namespace. This is to ensure an
         * ordered list for checksum generation.
         */
        if (XtendTransformerXpdl2Wsdl.shouldGenerateChecksum()) {
            Collections.sort(schemas, new Comparator<XSDSchema>() {
                @Override
                public int compare(XSDSchema o1, XSDSchema o2) {
                    String o1tns = o1.getTargetNamespace() != null
                            ? o1.getTargetNamespace()
                            : ""; //$NON-NLS-1$
                    String o2tns = o2.getTargetNamespace() != null
                            ? o2.getTargetNamespace()
                            : ""; //$NON-NLS-1$

                    return o1tns.compareTo(o2tns);
                }
            });
        }

        return schemas;
    }

    /**
     * @param definition
     * @param bomFileName
     */
    private static List<XSDSchema> getInlineSchemasForBomFileName(
            Definition definition, String bomFileName) {
        Types types = definition.getETypes();
        List<XSDSchema> schemasForBom = new ArrayList<XSDSchema>();
        if (null != types) {
            List schemas = types.getSchemas();
            for (Object schObj : schemas) {
                if (schObj instanceof XSDSchema) {
                    String documentationAttributeValue =
                            getBOMOriginAnnotationValue(
                                    ((XSDSchema) schObj).getAnnotations());
                    if (bomFileName.equals(documentationAttributeValue)) {
                        schemasForBom.add((XSDSchema) schObj);
                    }
                }
            }

        }
        return schemasForBom;
    }

    /**
     * Get the path of the BOM source from the annotations.
     * 
     * @param attName
     * @param annotations
     * @return
     */
    private static String getBOMOriginAnnotationValue(
            EList<XSDAnnotation> annotations) {
        String attValue = null;
        if (annotations != null && !annotations.isEmpty()) {
            for (XSDAnnotation annotation : annotations) {
                if (annotation != null) {
                    EList<Element> userInformations =
                            annotation.getUserInformation();
                    if (userInformations != null
                            && !userInformations.isEmpty()) {
                        for (Element userInformation : userInformations) {
                            if (userInformation != null) {
                                Node node = userInformation.getFirstChild();
                                if (node != null) {
                                    Node nextSibling = node.getNextSibling();
                                    if (null != nextSibling) {
                                        if (null != nextSibling
                                                .getAttributes()) {
                                            Node item = nextSibling
                                                    .getAttributes().item(0);
                                            if (null != item && item
                                                    .getNodeName()
                                                    .equals(XsdUIUtil.BOM_ORIGIN_TAGNAME)) {
                                                return item.getNodeValue();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return attValue;
    }

    /**
     * 
     * 
     * @param definition
     * @param namespaces
     */
    protected static void removeSchemasFromWsdlDefnTypes(Definition definition,
            List namespaces) {
        if (definition.getETypes() != null) {
            List<ExtensibilityElement> extensibilityElementToBeDeleted =
                    new ArrayList<ExtensibilityElement>();
            List extensibilityElement =
                    definition.getETypes().getExtensibilityElements();
            for (Object obj : extensibilityElement) {
                if (obj instanceof XSDSchemaExtensibilityElement) {
                    XSDSchemaExtensibilityElement schemaExtensibilityElement =
                            (XSDSchemaExtensibilityElement) obj;
                    if (namespaces.contains(schemaExtensibilityElement
                            .getSchema().getTargetNamespace())) {
                        extensibilityElementToBeDeleted
                                .add((ExtensibilityElement) obj);
                    }
                }
            }
            if (!extensibilityElementToBeDeleted.isEmpty()) {
                definition.getETypes().getEExtensibilityElements()
                        .removeAll(extensibilityElementToBeDeleted);
            }
        }
    }

    /**
     * This methods queries for the element declaration in the WSDL schema. If
     * it isn't present then either adds the schema or the
     * {@link XSDElementDeclaration} to the schema depending on the state of the
     * schema.
     * 
     * @param definition
     * @param indexedItem
     * @return
     */
    public static XSDElementDeclaration getElementDeclarationInWsdlDefinition(
            Definition definition, IndexerItem indexedItem) {
        String targetNs = indexedItem.get(TARGET_NAMESPACE_INDEX_LABEL);
        if (definition.getETypes() == null) {
            createWsdlETypes(definition);
        }
        List<XSDSchema> schemas = definition.getETypes().getSchemas();
        XSDSchema relevantSchema = null;
        String elementDeclarationName = indexedItem.getName();
        for (XSDSchema schema : schemas) {
            if (schema.getTargetNamespace().equals(targetNs)) {
                relevantSchema = schema;
                EObject objInList = EMFSearchUtil.findInList(
                        schema.getElementDeclarations(),
                        XSDPackage.eINSTANCE.getXSDNamedComponent_Name(),
                        elementDeclarationName);
                if (objInList instanceof XSDElementDeclaration) {
                    XSDElementDeclaration elementDeclaration =
                            (XSDElementDeclaration) objInList;
                    return elementDeclaration;
                }
            }
        }
        if (relevantSchema == null) {

            addSchemaToDefinition(definition, indexedItem);
            XSDSchemaContent schemaContent =
                    findXSDSchemaContent(definition, indexedItem, true);
            if (schemaContent instanceof XSDElementDeclaration) {
                return (XSDElementDeclaration) schemaContent;
            }
        } else {
            XSDSchemaContent schemaContent =
                    addSchemaContentToSchema(definition,
                            relevantSchema,
                            indexedItem,
                            true);

            if (schemaContent instanceof XSDElementDeclaration) {
                return (XSDElementDeclaration) schemaContent;

            }
        }
        return null;
    }
}