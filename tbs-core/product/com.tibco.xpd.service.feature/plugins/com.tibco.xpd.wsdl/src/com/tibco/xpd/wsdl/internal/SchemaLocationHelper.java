/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.wsdl.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.wsdl.Definition;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.wst.wsdl.Import;
import org.eclipse.xsd.XSDAnnotation;
import org.eclipse.xsd.XSDFactory;
import org.eclipse.xsd.XSDImport;
import org.eclipse.xsd.XSDInclude;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSchemaDirective;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.tibco.xpd.util.WsdlIndexerUtil;

/**
 * Helper class for wsdl/xsd imports/includes with ../ paths or http locations
 * in the referenced schemas. if any ../ path or http location reference in the
 * wsdl/xsd leads to having the resource copied outside SD folder (anywhere
 * under the project or sometimes it might lead to outside project), then we
 * will copy them under SD folder by changing the schema locations in the
 * imported/included resources (to make them relative to SD folder)
 * 
 * @author bharge
 * @since 26 Feb 2013
 */
public class SchemaLocationHelper {

    /**
     * 
     * if the res has ../ or http remote url in the schema location making it
     * refer to a location above wsdl file, then this method adjusts the schema
     * location to make it relative to service descriptors folder
     * 
     * @param targetSplFolderPath
     *            - service descriptors folder
     * @param originalSrcUri
     *            - uri to the full path of the source wsdl
     * @param wsdlFile
     * @param targetResourceSet
     * @throws IOException
     */
    public void adjustDotDotSlashInSchemaLocation(
            IContainer targetSplFolderPath, URI originalSrcUri, IFile wsdlFile,
            List<Resource> resources) throws IOException {

        URI srcUri =
                URI.createPlatformResourceURI(wsdlFile.getFullPath().toString(),
                        true);

        for (Resource res : resources) {
            EObject eo = res.getContents().get(0);

            if (eo instanceof Definition) {
                /* Look at all imports/includes */
                org.eclipse.wst.wsdl.Definition definition =
                        (org.eclipse.wst.wsdl.Definition) eo;

                adjustSchemaLocationForSchemaInstances(targetSplFolderPath,
                        originalSrcUri,
                        definition,
                        srcUri);

                EList<?> eImports = definition.getEImports();

                if (null != eImports && eImports.size() > 0) {
                    adjustSchemaLocationForWsdlImports(targetSplFolderPath,
                            originalSrcUri,
                            srcUri,
                            eo,
                            eImports);
                }

                org.eclipse.wst.wsdl.Types eTypes = definition.getETypes();
                if (null != eTypes) {
                    List<?> schemas = eTypes.getSchemas();
                    for (Object schema : schemas) {
                        if (schema instanceof XSDSchema) {
                            XSDSchema xsdSchema = (XSDSchema) schema;
                            adjustSchemaLocationForXSDSchemas(targetSplFolderPath,
                                    originalSrcUri,
                                    srcUri,
                                    eo,
                                    xsdSchema);
                        }
                    }
                }

            } else if (eo instanceof XSDSchema) {
                XSDSchema xsdSchema = (XSDSchema) eo;
                adjustSchemaLocationForXSDSchemas(targetSplFolderPath,
                        originalSrcUri,
                        srcUri,
                        eo,
                        xsdSchema);
            }
        }

    }

    /**
     * adjusts the schema location for schema instances
     * (xsi:noNamespaceSchemaLocation and xsi:schemaLocation)
     * 
     * @param targetSplFolderPath
     * @param originalSrcUri
     * @param definition
     * @param srcUri
     */
    private void adjustSchemaLocationForSchemaInstances(
            IContainer targetSplFolderPath, URI originalSrcUri,
            org.eclipse.wst.wsdl.Definition definition, URI srcUri) {

        /*
         * source: http://msdn.microsoft.com/en-us/library/ms256100.aspx
         * 
         * To specify the location for an XML Schema that does not have a target
         * namespace, the noNamespaceSchemaLocation attribute is used. The XML
         * Schema referenced in this attribute cannot have a target namespace.
         * Because this attribute does not take a list of URLs, you can have
         * only one schema location.
         */
        adjustSchemaLocationForSchemaInstanceWithNoNamespace(targetSplFolderPath,
                originalSrcUri,
                definition,
                srcUri);

        /*
         * use of xsi:schemaLocation attribute provides location information for
         * several XML Schema documents.
         */
        adjustSchemaLocationForSchemaInstanceWithNamespace(targetSplFolderPath,
                originalSrcUri,
                definition,
                srcUri);

    }

    /***
     * adjust the schema location for xsi:noNamespaceSchemaLocation schema
     * instances
     * 
     * @param targetSplFolderPath
     * @param originalSrcUri
     * @param definition
     * @param srcUri
     */
    private void adjustSchemaLocationForSchemaInstanceWithNoNamespace(
            IContainer targetSplFolderPath, URI originalSrcUri,
            org.eclipse.wst.wsdl.Definition definition, URI srcUri) {

        /*
         * booleans to determine wsdl/xsd are from file system or from remote
         * locations
         */
        boolean isWsdlImportedFromFileSystem = false;
        boolean isXsdFromFileSystem = false;

        boolean isWsdlImportedFromURl = false;
        boolean isXsdFromRemoteUrl = false;

        if (isSupportedProtocol(originalSrcUri.scheme())) {
            isWsdlImportedFromURl = true;
        } else {
            isWsdlImportedFromFileSystem = true;
        }

        Node namedItem = null;
        Element element = definition.getElement();

        if (null != element && null != element.getAttributes()) {
            namedItem =
                    element.getAttributes()
                            .getNamedItem("xsi:noNamespaceSchemaLocation"); //$NON-NLS-1$
        }

        if (null != namedItem) {

            String adjustedSchemaLocation = null;
            String xsdSchemaLocation = namedItem.getTextContent();

            if (null != xsdSchemaLocation) {

                if (isSupportedProtocol(xsdSchemaLocation)) {
                    isXsdFromRemoteUrl = true;
                } else {
                    isXsdFromFileSystem = true;
                }

                /*
                 * source: http://msdn.microsoft.com/en-us/library/ms256100.aspx
                 * 
                 * To specify the location for an XML Schema that does not have
                 * a target namespace, the noNamespaceSchemaLocation attribute
                 * is used. The XML Schema referenced in this attribute cannot
                 * have a target namespace. Because this attribute does not take
                 * a list of URLs, you can have only one schema location.
                 * 
                 * so adjusting the available schema location
                 */

                if (isWsdlImportedFromFileSystem && isXsdFromFileSystem) {

                    if (xsdSchemaLocation.startsWith("../")) { //$NON-NLS-1$

                        URI schemaUri = URI.createURI(xsdSchemaLocation);
                        schemaUri =
                                schemaUri.resolve(definition.eResource()
                                        .getURI());
                        URI relativePath = getRelativePath(srcUri, schemaUri);

                        if (relativePath.toString().startsWith("..")) { //$NON-NLS-1$

                            IPath path = new Path(relativePath.toString());
                            path = getRelativePathToSDFolder(path);
                            adjustedSchemaLocation = path.toString();
                            /*
                             * setting the element node content adjusted schema
                             * location
                             */
                            if (null != adjustedSchemaLocation) {
                                namedItem
                                        .setTextContent(adjustedSchemaLocation);
                            }
                        }
                    }

                } else if (isWsdlImportedFromFileSystem && isXsdFromRemoteUrl) {

                    adjustedSchemaLocation =
                            getAdjustedSchemaLocationForFileSystemAndRemoteUrl(targetSplFolderPath,
                                    srcUri,
                                    xsdSchemaLocation);
                    /*
                     * setting the element node content adjusted schema location
                     */
                    if (null != adjustedSchemaLocation) {
                        namedItem.setTextContent(adjustedSchemaLocation);
                    }
                    WsdlIndexerUtil.addSourceUri(definition,
                            definition,
                            xsdSchemaLocation);

                } else if (isWsdlImportedFromURl && isXsdFromFileSystem) {

                    URI parentSchemaUri = definition.eResource().getURI();

                    if (xsdSchemaLocation.startsWith("../")) { //$NON-NLS-1$
                        adjustedSchemaLocation =
                                getAdjustedSchemaLocationForFileSystem(originalSrcUri,
                                        parentSchemaUri,
                                        definition,
                                        xsdSchemaLocation);
                    }
                    /*
                     * setting the element node content adjusted schema location
                     */
                    if (null != adjustedSchemaLocation) {
                        namedItem.setTextContent(adjustedSchemaLocation);
                    } else {
                        namedItem.setTextContent(xsdSchemaLocation);
                    }

                } else if (isWsdlImportedFromURl && isXsdFromRemoteUrl) {

                    adjustedSchemaLocation =
                            getAdjustedSchemaLocationForRemoteUrls(targetSplFolderPath,
                                    originalSrcUri,
                                    srcUri,
                                    xsdSchemaLocation);
                    /*
                     * setting the element node content adjusted schema location
                     */
                    if (null != adjustedSchemaLocation) {
                        namedItem.setTextContent(adjustedSchemaLocation);
                    }
                    WsdlIndexerUtil.addSourceUri(definition,
                            definition,
                            xsdSchemaLocation);
                }
            }
        }

    }

    /**
     * 
     * @param protocolUsedInLocation
     * @return <code>true</code> if the protocol used in import/include location
     *         matches with the ones we support
     */
    private boolean isSupportedProtocol(String protocolUsedInLocation) {
        /*
         * file:/// is to support file(s) (simulating ftp) referred from local
         * hard disk instead of from ftp server
         */
        return protocolUsedInLocation.startsWith("http") || protocolUsedInLocation.startsWith("ftp") || protocolUsedInLocation.startsWith("file:///"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    /***
     * adjust the schema location for xsi:schemaLocation schema instances
     * 
     * @param targetSplFolderPath
     * @param originalSrcUri
     * @param definition
     * @param srcUri
     */
    private void adjustSchemaLocationForSchemaInstanceWithNamespace(
            IContainer targetSplFolderPath, URI originalSrcUri,
            org.eclipse.wst.wsdl.Definition definition, URI srcUri) {

        /*
         * booleans to determine wsdl/xsd are from file system or from remote
         * locations
         */
        boolean isWsdlImportedFromFileSystem = false;
        boolean isXsdFromFileSystem = false;

        boolean isWsdlImportedFromURl = false;
        boolean isXsdFromRemoteUrl = false;

        if (isSupportedProtocol(originalSrcUri.scheme())) {
            isWsdlImportedFromURl = true;
        } else {
            isWsdlImportedFromFileSystem = true;
        }

        Node namedItem = null;

        Element element = definition.getElement();
        if (null != element && null != element.getAttributes()) {
            namedItem =
                    element.getAttributes().getNamedItem("xsi:schemaLocation"); //$NON-NLS-1$
        }

        if (null != namedItem) {
            String adjustedSchemaLocation = null;

            String xsiNamespaceSchemaLoc = namedItem.getTextContent();

            if (null != xsiNamespaceSchemaLoc) {
                /*
                 * use of xsi:schemaLocation attribute that provides location
                 * information for several XML Schema documents. this will have
                 * namespace followed by urls separated by space. so need to
                 * adjust the schema locations for all the urls specified
                 */

                String[] namespaceSchemaLocations =
                        xsiNamespaceSchemaLoc.split(" "); //$NON-NLS-1$

                List<String> adjustedLocations = new ArrayList<String>();

                for (int i = 1; i < namespaceSchemaLocations.length; i++) {

                    String xsdLocation = namespaceSchemaLocations[i];

                    if (isSupportedProtocol(xsdLocation)) {
                        isXsdFromRemoteUrl = true;
                    } else {
                        isXsdFromFileSystem = true;
                    }

                    if (null != xsdLocation) {

                        if (isWsdlImportedFromFileSystem && isXsdFromFileSystem) {

                            if (xsdLocation.startsWith("../")) { //$NON-NLS-1$

                                URI schemaUri = URI.createURI(xsdLocation);
                                schemaUri =
                                        schemaUri.resolve(definition
                                                .eResource().getURI());
                                URI relativePath =
                                        getRelativePath(srcUri, schemaUri);

                                if (relativePath.toString().startsWith("..")) { //$NON-NLS-1$

                                    IPath path =
                                            new Path(relativePath.toString());
                                    path = getRelativePathToSDFolder(path);
                                    adjustedSchemaLocation = path.toString();
                                    adjustedLocations
                                            .add(adjustedSchemaLocation);
                                }
                            }
                        } else if (isWsdlImportedFromFileSystem
                                && isXsdFromRemoteUrl) {

                            adjustedSchemaLocation =
                                    getAdjustedSchemaLocationForFileSystemAndRemoteUrl(targetSplFolderPath,
                                            srcUri,
                                            xsdLocation);
                            adjustedLocations.add(adjustedSchemaLocation);
                            WsdlIndexerUtil.addSourceUri(definition,
                                    definition,
                                    xsdLocation);

                        } else if (isWsdlImportedFromURl && isXsdFromFileSystem) {

                            URI parentSchemaUri =
                                    definition.eResource().getURI();

                            if (xsdLocation.startsWith("../")) { //$NON-NLS-1$
                                adjustedSchemaLocation =
                                        getAdjustedSchemaLocationForFileSystem(originalSrcUri,
                                                parentSchemaUri,
                                                definition,
                                                xsdLocation);
                            }
                            /*
                             * setting the element node content adjusted schema
                             * location
                             */
                            if (null != adjustedSchemaLocation) {
                                adjustedLocations.add(adjustedSchemaLocation);
                            } else {
                                adjustedLocations.add(xsdLocation);
                            }

                        } else if (isWsdlImportedFromURl && isXsdFromRemoteUrl) {
                            adjustedSchemaLocation =
                                    getAdjustedSchemaLocationForRemoteUrls(targetSplFolderPath,
                                            originalSrcUri,
                                            srcUri,
                                            xsdLocation);
                            adjustedLocations.add(adjustedSchemaLocation);
                            WsdlIndexerUtil.addSourceUri(definition,
                                    definition,
                                    xsdLocation);
                        }
                    }
                    /*
                     * starting the loop counter 'i' with 1 and incrementing by
                     * 2 because Multiple pairs of URI references can be listed,
                     * each with a different namespace name part. For instance
                     * 
                     * xsi:schemaLocation= "http://contoso.com/People
                     * http://contoso.com/schemas/people.xsd
                     * http://contoso.com/schemas/Vehicles
                     * http://contoso.com/schemas/vehicles.xsd
                     * http://contoso.com/schemas/People
                     * http://contoso.com/schemas/people.xsd">
                     * 
                     * where "http://contoso.com/People" is the namespace and
                     * "http://contoso.com/schemas/people.xsd" is the schema
                     * location, "http://contoso.com/schemas/Vehicles" is the
                     * namespace and "http://contoso.com/schemas/vehicles.xsd"
                     * is the schema location and so on
                     */
                    i = i + 2;
                }

                /*
                 * setting the element node content with namespace separated by
                 * space and adjusted schema location
                 */
                int j = 0;
                StringBuilder sb = new StringBuilder();
                // new StringBuilder(namespaceSchemaLocations[j]);
                for (String location : adjustedLocations) {
                    sb.append(namespaceSchemaLocations[j]);
                    sb.append(" "); //$NON-NLS-1$
                    sb.append(location);
                    j = j + 2;
                }

                if (sb.toString().trim().length() > 0) {
                    namedItem.setTextContent(sb.toString().trim());
                }
            }
        }

    }

    /**
     * 
     * this method adjusts the schema location for wsdl imports
     * 
     * @param targetSplFolderPath
     * @param originalSrcUri
     * @param srcUri
     * @param eo
     * @param eImports
     */
    private void adjustSchemaLocationForWsdlImports(
            IContainer targetSplFolderPath, URI originalSrcUri, URI srcUri,
            EObject eo, EList<?> eImports) {

        /*
         * booleans to determine wsdl/xsd are from file system or from remote
         * locations
         */
        boolean isWsdlImportedFromFileSystem = false;
        boolean isXsdFromFileSystem = false;

        boolean isWsdlImportedFromURl = false;
        boolean isXsdFromRemoteUrl = false;

        if (isSupportedProtocol(originalSrcUri.scheme())) {
            isWsdlImportedFromURl = true;
        } else {
            isWsdlImportedFromFileSystem = true;
        }

        for (Object eImport : eImports) {

            if (eImport instanceof Import) {
                Import import1 = (Import) eImport;
                String locationURI = import1.getLocationURI();

                if (null != locationURI) {

                    if (isSupportedProtocol(locationURI)) {
                        isXsdFromRemoteUrl = true;
                    } else {
                        isXsdFromFileSystem = true;
                    }

                    if (isWsdlImportedFromFileSystem && isXsdFromFileSystem) {
                        /* handle ../ file system references */

                        String adjustedSchemaLocation =
                                getAdjustedSchemaLocationForFileSystem(originalSrcUri,
                                        srcUri,
                                        eo,
                                        locationURI);
                        if (null != adjustedSchemaLocation) {
                            import1.setLocationURI(adjustedSchemaLocation);
                        }

                    } else if (isWsdlImportedFromFileSystem
                            && isXsdFromRemoteUrl) {
                        /*
                         * handle http references between file system and remote
                         * url. in this case we will have a sub folder for
                         * domain name having xsds copied under that sub folder.
                         * so need to adjust the schema location accordingly
                         */

                        URI parentSchemaUri = eo.eResource().getURI();

                        String adjustedSchemaLocation =
                                getAdjustedSchemaLocationForFileSystemAndRemoteUrl(targetSplFolderPath,
                                        parentSchemaUri,
                                        locationURI);

                        if (null != adjustedSchemaLocation) {
                            import1.setLocationURI(adjustedSchemaLocation);
                            /*
                             * we are trying to maintain the history of the
                             * original http location from where this resource
                             * is imported
                             * 
                             * for wsdl import we add it as a tibex:src
                             * attribute
                             */
                            if (eo instanceof Definition) {
                                WsdlIndexerUtil
                                        .addSourceUri((org.eclipse.wst.wsdl.Definition) eo,
                                                import1,
                                                locationURI);
                            }
                        }
                    } else if (isWsdlImportedFromURl && isXsdFromFileSystem) {

                        /*
                         * need to handle ../ file system references if any for
                         * the xsds
                         */

                        String adjustedSchemaLocation = null;

                        URI parentSchemaUri = eo.eResource().getURI();

                        if (locationURI.startsWith("../")) { //$NON-NLS-1$

                            adjustedSchemaLocation =
                                    getAdjustedSchemaLocationForFileSystem(originalSrcUri,
                                            parentSchemaUri,
                                            eo,
                                            locationURI);
                        }

                        if (null != adjustedSchemaLocation) {
                            import1.setLocationURI(adjustedSchemaLocation);
                        }

                    } else if (isWsdlImportedFromURl && isXsdFromRemoteUrl) {
                        /*
                         * handle http references between remote urls. in this
                         * case we will not have a sub folder for domain name
                         * having xsds copied under that sub folder unless the
                         * xsd is coming from different domain to that of the
                         * wsdl. so need to adjust the schema location
                         * accordingly
                         */

                        URI parentSchemaUri = eo.eResource().getURI();

                        String adjustedSchemaLocation =
                                getAdjustedSchemaLocationForRemoteUrls(targetSplFolderPath,
                                        originalSrcUri,
                                        parentSchemaUri,
                                        locationURI);

                        if (null != adjustedSchemaLocation) {
                            import1.setLocationURI(adjustedSchemaLocation);
                            /*
                             * we are trying to maintain the history of the
                             * original http location from where this resource
                             * is imported
                             * 
                             * for wsdl import we add it as a tibex:src
                             * attribute
                             */
                            if (eo instanceof Definition) {
                                WsdlIndexerUtil
                                        .addSourceUri((org.eclipse.wst.wsdl.Definition) eo,
                                                import1,
                                                locationURI);
                            }
                        }

                    }
                }
            }
        }

    }

    /**
     * this method adjusts the schema locations in imported xsds
     * 
     * @param targetSplFolderPath
     * @param originalSrcUri
     * @param srcUri
     * @param eo
     * @param xsdSchema
     */
    private void adjustSchemaLocationForXSDSchemas(
            IContainer targetSplFolderPath, URI originalSrcUri, URI srcUri,
            EObject eo, XSDSchema xsdSchema) {

        /*
         * booleans to determine wsdl/xsd are from file system or from remote
         * locations
         */
        boolean isWsdlImportedFromFileSystem = false;
        boolean isXsdFromFileSystem = false;

        boolean isWsdlImportedFromURl = false;
        boolean isXsdFromRemoteUrl = false;

        if (isSupportedProtocol(originalSrcUri.scheme())) {
            isWsdlImportedFromURl = true;
        } else {
            isWsdlImportedFromFileSystem = true;
        }

        for (EObject child : xsdSchema.getContents()) {

            if (child instanceof XSDSchemaDirective) {
                XSDSchemaDirective directive = (XSDSchemaDirective) child;

                String schemaLocation = directive.getSchemaLocation();

                if (null != schemaLocation) {

                    if (isSupportedProtocol(schemaLocation)) {
                        isXsdFromRemoteUrl = true;
                    } else {
                        isXsdFromFileSystem = true;
                    }

                    if (isWsdlImportedFromFileSystem && isXsdFromFileSystem) {
                        /* handle ../ file system references */
                        handleDotDotSlashRefs(originalSrcUri,
                                srcUri,
                                eo,
                                directive,
                                schemaLocation);

                    } else if (isWsdlImportedFromFileSystem
                            && isXsdFromRemoteUrl) {
                        /*
                         * handle http references between file system and remote
                         * url. we will have a sub folder for domain name having
                         * xsds copied under that sub folder. so need to adjust
                         * the schema location accordingly
                         */

                        URI parentSchemaUri = xsdSchema.eResource().getURI();
                        String adjustedSchemaLocation =
                                getAdjustedSchemaLocationForFileSystemAndRemoteUrl(targetSplFolderPath,
                                        parentSchemaUri,
                                        schemaLocation);

                        if (null != adjustedSchemaLocation) {
                            directive.setSchemaLocation(adjustedSchemaLocation);
                            /*
                             * we are trying to maintain the history of the
                             * original http location from where this resource
                             * is imported
                             */
                            setOriginalLocationInAnnotation(directive,
                                    schemaLocation);
                        }
                    } else if (isWsdlImportedFromURl && isXsdFromFileSystem) {
                        /*
                         * need to handle ../ file system references if any for
                         * the xsds
                         */
                        String adjustedSchemaLocation = null;

                        URI parentSchemaUri = xsdSchema.eResource().getURI();

                        if (schemaLocation.startsWith("../")) { //$NON-NLS-1$

                            adjustedSchemaLocation =
                                    getAdjustedSchemaLocationForFileSystem(originalSrcUri,
                                            parentSchemaUri,
                                            eo,
                                            schemaLocation);
                        }

                        if (null != adjustedSchemaLocation) {
                            directive.setSchemaLocation(adjustedSchemaLocation);
                        }

                    } else if (isWsdlImportedFromURl && isXsdFromRemoteUrl) {
                        /*
                         * handle http references between remote urls. in this
                         * case we will not have a sub folder for domain name
                         * having xsds copied under that sub folder unless the
                         * xsd is coming from different domain to that of the
                         * wsdl. so need to adjust the schema location
                         * accordingly
                         */

                        URI parentSchemaUri = xsdSchema.eResource().getURI();

                        String adjustedSchemaLocation =
                                getAdjustedSchemaLocationForRemoteUrls(targetSplFolderPath,
                                        originalSrcUri,
                                        parentSchemaUri,
                                        schemaLocation);

                        if (null != adjustedSchemaLocation) {
                            directive.setSchemaLocation(adjustedSchemaLocation);
                            /*
                             * we are trying to maintain the history of the
                             * original http location from where this resource
                             * is imported
                             */
                            setOriginalLocationInAnnotation(directive,
                                    schemaLocation);
                        }

                    }
                }
            }
        }
    }

    /**
     * adjusts the schema location if the wsdl being imported from file system,
     * references resources from remote location
     * 
     * @param targetSplFolderPath
     * @param srcUri
     * @param schemaLocation
     * @return
     */
    private String getAdjustedSchemaLocationForFileSystemAndRemoteUrl(
            IContainer targetSplFolderPath, URI srcUri, String schemaLocation) {

        URI schemaUri = URI.createURI(schemaLocation);
        String schemaUriPath = schemaUri.path();

        IPath path = new Path(schemaUriPath).makeRelative();

        IPath schemaUriFullPath =
                targetSplFolderPath.getFullPath().append(path);
        URI importOrIncludeSchemaUri =
                URI.createPlatformResourceURI(schemaUriFullPath.toString(),
                        true);

        URI deresolve = importOrIncludeSchemaUri.deresolve(srcUri);

        String adjustedSchemaLocation = deresolve.path();
        return adjustedSchemaLocation;
    }

    /**
     * adjusts the schema location if wsdl being imported from url, references
     * resources from remote location
     * 
     * @param targetSplFolderPath
     * @param originalSrcUri
     * @param srcUri
     * @param schemaLocation
     * @return
     */
    private String getAdjustedSchemaLocationForRemoteUrls(
            IContainer targetSplFolderPath, URI originalSrcUri, URI srcUri,
            String schemaLocation) {

        URI schemaUri = URI.createURI(schemaLocation);
        String schemaUriPath = schemaUri.path();

        IFile schemaUriFile =
                targetSplFolderPath.getFile(new Path(schemaUriPath));
        /*
         * check if both the src and the imported/included schema are from the
         * same domain
         */
        boolean doesBelongToSameDomain =
                doesBelongToSameDomain(originalSrcUri, schemaUri);

        if (doesBelongToSameDomain) {

            /*
             * check if the imported/included schema belongs to the service
             * descriptors folder or a sub folder under service descriptors
             * folder
             */
            if (null != schemaUriFile
                    && !targetSplFolderPath.equals(schemaUriFile.getParent())) {

                URI relativePath = getRelativePath(originalSrcUri, schemaUri);

                schemaUriPath = relativePath.path();
            }
        }

        IPath path = new Path(schemaUriPath).makeRelative();

        IPath schemaUriFullPath =
                targetSplFolderPath.getFullPath().append(path);
        URI importOrIncludeSchemaUri =
                URI.createPlatformResourceURI(schemaUriFullPath.toString(),
                        true);

        URI deresolve = importOrIncludeSchemaUri.deresolve(srcUri);

        String adjustedSchemaLocation = deresolve.path();
        return adjustedSchemaLocation;
    }

    /**
     * checks if the parent schema and the imported/included schema belong to
     * the same domain
     * 
     * @param originalSrcUri
     * @param schemaUri
     * @return <code>true</code> if both the src and the imported/included
     *         schema are from the same domain, <code>false</code> otherwise
     */
    private boolean doesBelongToSameDomain(URI originalSrcUri, URI schemaUri) {

        String originalUriSegement = originalSrcUri.segment(0);
        String schemaUriSegment = schemaUri.segment(0);
        if (originalUriSegement.equals(schemaUriSegment)) {
            return true;
        }
        return false;

    }

    /**
     * to maintain the history of original schema location, add annotation to
     * parent schema's import/include tag
     * 
     * @param directive
     * @param schemaLocation
     */
    private void setOriginalLocationInAnnotation(XSDSchemaDirective directive,
            String schemaLocation) {

        if (directive instanceof XSDImport) {
            XSDImport xsdImport = (XSDImport) directive;
            XSDAnnotation annotation =
                    XSDFactory.eINSTANCE.createXSDAnnotation();
            xsdImport.setAnnotation(annotation);
            setAnnotationInfo(schemaLocation, annotation);

        } else if (directive instanceof XSDInclude) {
            XSDInclude xsdInclude = (XSDInclude) directive;
            XSDAnnotation annotation =
                    XSDFactory.eINSTANCE.createXSDAnnotation();
            xsdInclude.setAnnotation(annotation);

            setAnnotationInfo(schemaLocation, annotation);
        }
    }

    /**
     * set the annotation with original http schema location
     * 
     * @param schemaLocation
     * @param annotation
     */
    private void setAnnotationInfo(String schemaLocation,
            XSDAnnotation annotation) {

        Element info = annotation.createUserInformation(null);
        info.setTextContent("ORIGIN :: " + schemaLocation); //$NON-NLS-1$
        annotation.getElement().appendChild(info);
    }

    /**
     * handles ../ references and adjusts the schema location accordingly
     * 
     * @param srcUri
     * @param eo
     * @param directive
     * @param schemaLocation
     */
    private void handleDotDotSlashRefs(URI originalSrcUri, URI srcUri,
            EObject eo, XSDSchemaDirective directive, String schemaLocation) {

        if (schemaLocation.startsWith("../")) { //$NON-NLS-1$

            String adjustedSchemaLocation =
                    getAdjustedSchemaLocationForFileSystem(originalSrcUri,
                            srcUri,
                            eo,
                            schemaLocation);

            if (null != adjustedSchemaLocation) {
                directive.setSchemaLocation(adjustedSchemaLocation);
            }

        }
    }

    /**
     * adjusts the schema location for a resource that refers to a location
     * above wsdl (making it relative to service descriptors folder where the
     * wsdl file is copied)
     * 
     * @param srcUri
     * @param eo
     * @param schemaLocation
     * @return
     */
    private String getAdjustedSchemaLocationForFileSystem(URI originalSrcUri,
            URI srcUri, EObject eo, String schemaLocation) {

        URI schemaUri = URI.createURI(schemaLocation);
        schemaUri = schemaUri.resolve(eo.eResource().getURI());
        URI relativePath = getRelativePath(srcUri, schemaUri);

        if (relativePath.toString().startsWith("..")) { //$NON-NLS-1$
            IPath path = new Path(relativePath.toString());
            path = getRelativePathToSDFolder(path);
            return path.toString();
        } else {
            relativePath = getRelativePath(originalSrcUri, schemaUri);
            if (relativePath.toString().startsWith("..")) { //$NON-NLS-1$
                IPath path = new Path(relativePath.toString());
                path = getRelativePathToSDFolder(path);
                return path.toString();
            }
        }
        return null;
    }

    /**
     * returns the path relative to SD folder if there is any .. found in the
     * path (leading to make the resource fall outside SD folder referencing at
     * a location above wsdl file)
     * 
     * @param path
     *            (with .. in it)
     * @return
     */
    public IPath getRelativePathToSDFolder(IPath path) {
        /*
         * makeAbsolute gives the path with leading path separator. we want to
         * remove this leading path separator and make it relative to the
         * service descriptors folder. so we call makeRelative
         */
        path = path.makeAbsolute().makeRelative();
        return path;
    }

    /**
     * Get the uri's relative path to the base URI.
     * 
     * @param base
     *            the URI of the WSDL file being imported
     * @param uri
     *            the relative path of the given <i>uri</i>.
     * @return
     */
    private URI getRelativePath(URI base, URI uri) {
        return uri.deresolve(base, false, true, false);
    }

}
