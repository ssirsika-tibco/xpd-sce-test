/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.bds.designtime.generator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDFactory;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDParticleContent;
import org.eclipse.xsd.XSDProcessContents;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.XSDWildcard;
import org.eclipse.xsd.util.XSDResourceImpl;

import com.tibco.bds.designtime.generator.internal.Messages;
import com.tibco.xpd.bom.gen.biz.GenerationException;

/**
 * Class that deals with the custom validation templates and schema used
 * 
 */
public class ValidationTemplate {

    /**
     * All the details required about each schema
     */
    private class SchemaDetails {
        // URI of this schema
        public URI schemaURI;

        // The schema representation
        public XSDResourceImpl xsdMainResource = null;

        // Schemas this one depends on
        public List<URI> dependencies = new ArrayList<URI>();

        // CONSTRUCTOR
        public SchemaDetails(URI uri, XSDResourceImpl xsdMainRes) {
            schemaURI = uri;
            xsdMainResource = xsdMainRes;
        }

        // Returns the name-space of this schema
        public String getNamespace() {
            if (xsdMainResource == null) {
                return null;
            }
            return xsdMainResource.getSchema().getTargetNamespace();
        }

    }

    private final static String schemaExtension = ".xsd";

    private final static String XSD_VALIDATION_DIR = "model/validation";

    private final IProject bdsFolder;

    private List<SchemaDetails> allOrderedSchemas =
            new ArrayList<SchemaDetails>();

    /**
     * Constructor
     * 
     * @param bdsFolderIn
     *            BDS project folder
     */
    public ValidationTemplate(IProject bdsFolderIn) {
        bdsFolder = bdsFolderIn;
    }

    /**
     * Loads the schemas in the BDS project directory and process them ready for
     * validation code generation
     * 
     * @throws CoreException
     * @throws IOException
     * @throws GenerationException
     */
    public void loadSchemas() throws CoreException, IOException,
            GenerationException {

        List<SchemaDetails> unorderedSchemas = new ArrayList<SchemaDetails>();

        // Search the model folder for schemas
        for (IResource origSchemafile : bdsFolder.getFolder("model").members()) {
            if (origSchemafile.getType() == IResource.FILE) {
                if (origSchemafile.getName().endsWith(schemaExtension)) {
                    // Read in each schema and generate a version for validation
                    // Storing the data into a schema details object
                    URI schemaURI =
                            URI.createFileURI(origSchemafile.getLocation()
                                    .toString());
                    SchemaDetails schemaDetails = processXSD(schemaURI);
                    unorderedSchemas.add(schemaDetails);
                }
            }
        }

        List<URI> processedSchemas = new ArrayList<URI>();

        // Order the schemas so that the ones with least dependencies are at
        // the start of the list so that they are loaded before they are used
        // by schemas later in the list
        while (unorderedSchemas.size() > 0) {
            int numBeforeProcessing = unorderedSchemas.size();

            // Sort the schemas
            for (Iterator<SchemaDetails> schemaIt = unorderedSchemas.iterator(); schemaIt
                    .hasNext();) {
                SchemaDetails next = schemaIt.next();

                // Check the case where there are no dependencies
                // or all required dependencies are present
                if (next.dependencies.isEmpty()
                        || processedSchemas.containsAll(next.dependencies)) {
                    processedSchemas.add(next.schemaURI);
                    allOrderedSchemas.add(next);
                    schemaIt.remove();
                }

            }

            // Make sure at least one schema was processed. If it wasn't then
            // it means we have a case of cyclic dependency, this should be
            // prevented by the BOM, but just in case we catch it here
            // so we don't go into an infinite loop
            if (numBeforeProcessing <= unorderedSchemas.size()) {
                throw new GenerationException(
                        Messages
                                .getString("OawTeneoGenerator_genmodelTemplateSetupFail")); //$NON-NLS-1$
            }
        }
    }

    /**
     * Save the altered schemas into the validation directory
     * 
     * @throws CoreException
     * @throws IOException
     */
    public void saveSchemas() throws CoreException, IOException {
        // Set the directory that the validation XSDs will be stored in
        IFolder validationDir = bdsFolder.getFolder(XSD_VALIDATION_DIR);
        // If the directory already exists, then remove it and
        // recreate as it may have old data in
        if (validationDir.exists()) {
            validationDir.delete(true, null);
        }
        validationDir.create(true, true, null);

        // Go through each of the schemas and save them to the
        // validation directory in the BDS project
        for (SchemaDetails schemaDetails : allOrderedSchemas) {
            // Create the target
            ByteArrayOutputStream saveStream = new ByteArrayOutputStream();

            // Perform the save
            schemaDetails.xsdMainResource.save(saveStream, null);
            saveStream.close();

            // Now create the file using the data stored in the output stream
            ByteArrayInputStream byteArrayInputStream =
                    new ByteArrayInputStream(saveStream.toByteArray());
            validationDir.getFile(schemaDetails.schemaURI.lastSegment())
                    .create(byteArrayInputStream, true, null);
            byteArrayInputStream.close();
        }
    }

    /**
     * Retrieves the list of schema files that are used for validation Ensures
     * they are in dependency order
     * 
     * @return String list of schema files
     */
    public String getSchemaListString() {
        // Text list of schemas to use for validation
        StringBuffer schemaString = new StringBuffer();

        for (SchemaDetails schemaDetails : allOrderedSchemas) {
            // Now add this schema to the list
            if (schemaString.length() > 0) {
                schemaString.append(", ");
            }

            schemaString.append("\"/");
            schemaString.append(XSD_VALIDATION_DIR);
            schemaString.append("/");
            schemaString.append(schemaDetails.schemaURI.lastSegment());
            schemaString.append("\"");
        }

        return schemaString.toString();
    }

    /**
     * Retrieves a list of name-spaces that are used for validation
     * 
     * @return
     */
    public String getNamespaceListString() {
        // Text list of schemas to use for validation
        StringBuffer namespaceString = new StringBuffer();

        for (SchemaDetails schemaDetails : allOrderedSchemas) {
            // Now add this schema to the list
            if (namespaceString.length() > 0) {
                namespaceString.append(", ");
            }

            namespaceString.append("\"");
            namespaceString.append(schemaDetails.getNamespace());
            namespaceString.append("\"");
        }

        return namespaceString.toString();
    }

    /**
     * Given an Schema file will create a schema suitable for validation using
     * the SAX parser
     * 
     * @param xsdFile
     *            Source schema file
     * @return Schema representation, updated for validation
     * @throws IOException
     * @throws CoreException
     */
    private SchemaDetails processXSD(URI xsdFile) throws IOException,
            CoreException {

        // Create a resource set, create a schema resource, and load the main
        // schema file into it.
        ResourceSet resourceSet = new ResourceSetImpl();
        XSDResourceImpl xsdMainResource =
                (XSDResourceImpl) resourceSet.createResource(URI
                        .createURI("*.xsd"));
        xsdMainResource.setURI(xsdFile);
        xsdMainResource.load(resourceSet.getLoadOptions());

        // Create our schema representation
        SchemaDetails schemaDetails =
                new SchemaDetails(xsdFile, xsdMainResource);

        for (Object resource : resourceSet.getResources()) {
            if (resource instanceof XSDResourceImpl) {
                // Only process the one that matches the schema we are
                // processing (The original load will have loaded
                // it's dependencies as well)
                XSDResourceImpl xsdResource = (XSDResourceImpl) resource;
                if (!xsdResource.getURI().equals(xsdFile)) {
                    // Store the dependencies
                    schemaDetails.dependencies.add(xsdResource.getURI());
                    continue;
                }

                XSDSchema xsdSchema = xsdResource.getSchema();

                // Check all of the data so that any xsd:any with processContent
                // as strict can be converted to lax so that validation will
                // pass. This is because even if the schema of what is put in
                // the XML is specified at run-time there will be no way to get
                // hold of the schema as we might not know about it at
                // design-time
                for (Iterator<EObject> i = xsdSchema.eAllContents(); i
                        .hasNext();) {
                    EObject o = i.next();
                    if (o instanceof XSDParticle) {
                        XSDParticleContent content =
                                ((XSDParticle) o).getContent();
                        if ((content != null)
                                && (content instanceof XSDWildcard)) {
                            // For xsd:any support
                            XSDWildcard anyData = (XSDWildcard) content;
                            if (anyData.getProcessContents() == XSDProcessContents.STRICT_LITERAL) {
                                anyData
                                        .setProcessContents(XSDProcessContents.LAX_LITERAL);
                            }
                        }
                    } else if (o instanceof XSDWildcard) {
                        // For xsd:anyAttribute Support
                        XSDWildcard anyAttrib = (XSDWildcard) o;
                        if (anyAttrib.getProcessContents() == XSDProcessContents.STRICT_LITERAL) {
                            anyAttrib
                                    .setProcessContents(XSDProcessContents.LAX_LITERAL);
                        }
                    }
                }

                // Get a list of top level elements that already exist
                List<String> tleNames = new ArrayList<String>();

                for (XSDElementDeclaration elementDeclaration : xsdSchema
                        .getElementDeclarations()) {
                    // Only interested in elements in the same namespace/schema
                    if (elementDeclaration.getTargetNamespace()
                            .equals(schemaDetails.getNamespace())) {
                        tleNames.add(elementDeclaration.getName());
                    }
                }

                // Go through all of the types checking the Complex types
                for (XSDTypeDefinition typeDefinition : xsdSchema
                        .getTypeDefinitions()) {
                    if (typeDefinition instanceof XSDComplexTypeDefinition) {
                        // Check to see if there is already an element with the
                        // same name as the complex type, and that this is the
                        // namespace we are interested in
                        if (!tleNames.contains(typeDefinition.getName())
                                && typeDefinition.getTargetNamespace()
                                        .equals(schemaDetails.getNamespace())) {
                            // Now create the element with a name that matches
                            // the type
                            XSDElementDeclaration elemDecl =
                                    XSDFactory.eINSTANCE
                                            .createXSDElementDeclaration();
                            elemDecl.setName(typeDefinition.getName());
                            elemDecl.setTypeDefinition(typeDefinition);

                            // Add the element to the schema
                            xsdSchema.getContents().add(elemDecl);
                        }
                    }
                }
            }
        }

        return schemaDetails;
    }
}
