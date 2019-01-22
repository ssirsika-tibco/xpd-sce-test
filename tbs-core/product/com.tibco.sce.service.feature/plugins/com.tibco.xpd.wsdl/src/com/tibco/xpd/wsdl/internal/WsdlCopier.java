/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.wsdl.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.wsdl.Definition;
import javax.wsdl.Types;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wst.wsdl.Import;
import org.eclipse.wst.wsdl.XSDSchemaExtensibilityElement;
import org.eclipse.wst.wsdl.util.WSDLResourceFactoryRegistry;
import org.eclipse.wst.wsdl.util.WSDLResourceImpl;
import org.eclipse.xsd.XSDImport;
import org.eclipse.xsd.XSDInclude;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSchemaContent;
import org.eclipse.xsd.XSDSchemaDirective;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.tibco.xpd.resources.PreferenceStoreKeys;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.wsdl.Activator;
import com.tibco.xpd.wsdl.CertificateAcceptanceTracker;
import com.tibco.xpd.wsdl.CertificateManager;
import com.tibco.xpd.wsdl.WsdlCopyResult;

/**
 * Helper class to import wsdls and embedded xsd files from a url or as a move
 * of local files.
 */
public class WsdlCopier {

    private static final Logger LOG = Activator.getDefault().getLogger();

    /**
     * Private constructor.
     */
    private WsdlCopier() {

    }

    /**
     * Perform the copy of a wsdl from a url to a local file.
     * 
     * @deprecated please use
     *             {@link #copyWithResult(String, IFile, File, CertificateAcceptanceTracker, boolean, IProgressMonitor)}
     *             instead.
     */
    @Deprecated
    public static void copy(String wsdlSourceUrl, final IFile targetFile,
            final File keyStoreFile,
            final CertificateAcceptanceTracker certificateAcceptanceTracker,
            final IProgressMonitor monitor) throws IOException {

        WsdlCopyResult result =
                copyWithResult(wsdlSourceUrl,
                        targetFile,
                        keyStoreFile,
                        certificateAcceptanceTracker,
                        true,
                        monitor);

        /* If there is an ERROR then it will always carry IOException. */
        if (result.getStatus().getSeverity() == IStatus.ERROR) {

            if (result.getStatus().getException() instanceof IOException) {
                throw (IOException) result.getStatus().getException();
            }
        }
    }

    /**
     * @deprecated please use
     *             {@link #copyWithResult(String, IFile, File, CertificateAcceptanceTracker, boolean, IProgressMonitor)}
     *             instead.
     */
    @Deprecated
    public static void copy(String wsdlSourceUrl, final IFile targetFile,
            final File keyStoreFile,
            final CertificateAcceptanceTracker certificateAcceptanceTracker,
            final boolean isOverwriteExistingResources,
            final IProgressMonitor monitor) throws IOException {

        WsdlCopyResult result =
                copyWithResult(wsdlSourceUrl,
                        targetFile,
                        keyStoreFile,
                        certificateAcceptanceTracker,
                        isOverwriteExistingResources,
                        monitor);

        /* If there is an ERROR then it will always carry IOException. */
        if (result.getStatus().getSeverity() == IStatus.ERROR) {

            if (result.getStatus().getException() instanceof IOException) {
                throw (IOException) result.getStatus().getException();
            }
        }
    }

    /**
     * Perform the copy of a wsdl imported from a file system/url to a Service
     * Descriptors folder.
     * 
     * @param wsdlSourceUrl
     * @param targetFile
     * @param keyStoreFile
     * @param certificateAcceptanceTracker
     * @param isOverwriteExistingResources
     * @param monitor
     * @return {@link WsdlCopyResult} which contains a map of wsdl/xsd files
     *         with their original location, and {@link IStatus} object
     * 
     * @since 3.6 This was requested by SDS team. see SCF-188
     */
    public static WsdlCopyResult copyWithResult(String wsdlSourceUrl,
            final IFile targetFile, final File keyStoreFile,
            final CertificateAcceptanceTracker certificateAcceptanceTracker,
            final boolean isOverwriteExistingResources,
            final IProgressMonitor monitor) {

        if (monitor != null && monitor.isCanceled()) {
            // If user has cancelled then quit here.
            return null;
        }

        WsdlCopyResult wsdlCopierRes = new WsdlCopyResult();

        // a resource set is an ecore set of Resource
        final ResourceSet sourceResourceSet = new ResourceSetImpl();

        try {
            final URI srcUri = URI.createURI(wsdlSourceUrl);

            // we need the parent so that:
            // 1. We have a location into which we can write xsd files
            // 2. We can refresh it once the import is complete
            final IContainer targetParent = targetFile.getParent();

            // move resource needs a target URI
            final URI targetFileURI =
                    URI.createPlatformResourceURI(ensureExtensionIsLowerCase(targetFile
                            .getFullPath().toString(),
                            targetFile.getFileExtension()),
                            true);

            // we use our own stream so that we can talk through a proxy
            sourceResourceSet.setURIConverter(new TrackingURIConverter(
                    keyStoreFile, certificateAcceptanceTracker));

            // a WSDLResourceFactoryRegistry copes with funny file name
            // extensions
            // we make the resource into it by calling createResource()
            sourceResourceSet
                    .setResourceFactoryRegistry(new WSDLResourceFactoryRegistry(
                            Resource.Factory.Registry.INSTANCE));

            final Resource sourceEmfResource =
                    sourceResourceSet.createResource(srcUri);
            // Get the HTTP connection timeout value from the preferences
            int connectionTimeout =
                    XpdResourcesPlugin.getDefault().getPreferenceStore()
                            .getInt(PreferenceStoreKeys.HTTP_TIMEOUT);
            if (connectionTimeout == 0) {
                // If we have an invalid value then reset to 1 minute
                connectionTimeout = 60000;
            }

            URL url = new URL(wsdlSourceUrl);
            URLConnection connection = url.openConnection();
            connection.setReadTimeout(connectionTimeout);
            monitor.beginTask(Messages.WsdlCopier_attemptToReadWSDL_progress_message,
                    connectionTimeout / 1000);
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    monitor.worked(1);
                }
            };
            /*
             * load the wsdl resource into emf resource and add the tibex
             * attribute on to the wsdl definition
             */
            loadWsdlAsEmfResAndAddTibexAttrib(wsdlSourceUrl,
                    monitor,
                    sourceEmfResource,
                    connection,
                    task);

            final ArrayList<Resource> resources = new ArrayList<Resource>();
            resources.addAll(sourceResourceSet.getResources());
            for (Resource tmpResource : resources) {
                /* Load imported files that are not referenced */
                loadUnreferencedImports(tmpResource,
                        sourceResourceSet,
                        targetFileURI,
                        connectionTimeout);
            }

            /*
             * clear and re-add resources as more may have come in at this point
             * from loading the unreferenced imports above and we need to save
             * these resources.
             */
            resources.clear();
            resources.addAll(sourceResourceSet.getResources());

            if (monitor != null && monitor.isCanceled()) {
                /* If user has cancelled then quit here. */
                unloadResources(sourceResourceSet);
                return null;
            }

            /*
             * Validate the resources being imported. This will ensure that the
             * includes in the WSDL haven't got absolute paths or relative
             * references upstream
             */
            ArrayList<Resource> absoluteURLResources =
                    new ArrayList<Resource>();

            /*
             * TODO: we need not do the validations any more as we support http
             * and ftp protocols now. this validation was required earlier
             * during import to check for these protocols being used and stop
             * the import. but now that we support, we dont need to validate
             * them here. However there is a separate validation
             * (WsdlValidationRule i think) that checks for anything that is not
             * supported when wsdls/schemas are copied.
             * 
             * leaving this for now as it is. must have to revisit in 3.7 time
             * frame.
             */
            boolean validateResources =
                    validateResources(targetParent,
                            sourceEmfResource,
                            resources,
                            absoluteURLResources);

            if (validateResources) {
                resources.removeAll(absoluteURLResources);

                /*
                 * XPD-4079: contains resources that already exists under SD
                 * folder and are chosen not to be copied again
                 */
                ArrayList<Resource> existingResources =
                        new ArrayList<Resource>();
                /*
                 * Files being imported don't already exist in the workspace
                 * (user will be asked if they wish to overwrite)
                 */
                checkResAlreadyExistsAndNeedsOverwriting(targetParent,
                        sourceEmfResource,
                        resources,
                        existingResources,
                        isOverwriteExistingResources);

                /* use ecore objects to copy the wsdl resource contents */
                final ResourceSet targetResourceSet = new ResourceSetImpl();
                moveResource(sourceEmfResource,
                        targetResourceSet,
                        targetFileURI);

                wsdlCopierRes.getCopiedResources().put(targetFile,
                        srcUri.toString());
                /*
                 * This will make sure that 1. all sub folders are created, if
                 * required. 2. copies only those resources that are chosen to
                 * be overwritten. 3. saves the resources
                 */
                createFolderCopyAndSaveRes(targetFile,
                        monitor,
                        srcUri,
                        targetParent,
                        sourceEmfResource,
                        resources,
                        existingResources,
                        targetResourceSet,
                        wsdlCopierRes);
            }
        } catch (IOException e) {
            LOG.error(e);
            Status status =
                    new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                            e.getMessage(), e);
            wsdlCopierRes.setStatus(status);
            return wsdlCopierRes;
        } catch (CoreException e) {
            LOG.error(e);
            if (e.getCause() instanceof IOException) {
                Status status =
                        new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                                e.getMessage(), e.getCause());
                wsdlCopierRes.setStatus(status);
                return wsdlCopierRes;
            } else {
                Status status =
                        new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                                e.getMessage(), new IOException(e.getMessage(),
                                        e));
                wsdlCopierRes.setStatus(status);
                return wsdlCopierRes;
            }
        } finally {
            // Unload all resources
            unloadResources(sourceResourceSet);
        }
        return wsdlCopierRes;
    }

    /**
     * @param wsdlSourceUrl
     * @param monitor
     * @param sourceEmfResource
     * @param connection
     * @param task
     * @throws IOException
     */
    private static void loadWsdlAsEmfResAndAddTibexAttrib(String wsdlSourceUrl,
            final IProgressMonitor monitor, final Resource sourceEmfResource,
            URLConnection connection, TimerTask task) throws IOException {

        Timer timer = new Timer();
        timer.schedule(task, 0, 1000);
        InputStream inputStream = null;
        try {
            inputStream = connection.getInputStream();
            /* load the contents into the sourceEmfResource */
            Map<Object, Object> options = null;
            if (monitor != null) {
                options = new HashMap<Object, Object>();
                options.put(WSDLResourceImpl.WSDL_PROGRESS_MONITOR, monitor);
            }
            sourceEmfResource.load(inputStream, options);
            /*
             * Add the source URL to the the Definition element in the given
             * resource.
             */
            WsdlIndexerUtil.addSourceUri(sourceEmfResource, wsdlSourceUrl);
        } catch (StackOverflowError error) {
            /*
             * This happens if there is a cyclic import between wsdl files.
             * We're catching it here because there is nothing in the stack
             * trace to show its source otherwise.
             */
            Activator.getDefault().getLogger().error(error, wsdlSourceUrl);
        } finally {
            timer.cancel();
            monitor.done();
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * 
     * This will make sure that 1. all sub folders are created if required. 2.
     * copies only those resources that are chosen to be overwritten. 3. saves
     * the resources
     * 
     * @param targetFile
     * @param monitor
     * @param srcUri
     * @param targetParent
     * @param sourceEmfResource
     * @param resources
     * @param existingResources
     * @param targetResourceSet
     * @param wsdlCopierRes
     *            - SCF-188: contains status and map to store wsdl/xsd file and
     *            its original location. This was requested by SDS team.
     * @throws CoreException
     */
    private static void createFolderCopyAndSaveRes(final IFile targetFile,
            final IProgressMonitor monitor, final URI srcUri,
            final IContainer targetParent, final Resource sourceEmfResource,
            final ArrayList<Resource> resources,
            final ArrayList<Resource> existingResources,
            final ResourceSet targetResourceSet,
            final WsdlCopyResult wsdlCopierRes) throws CoreException {

        try {

            ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
                public void run(IProgressMonitor monitor) throws CoreException {
                    try {
                        for (Resource srcResource : resources) {
                            if (srcResource != sourceEmfResource) {

                                URI path =
                                        getRelativePath(srcUri,
                                                srcResource.getURI());
                                if (!path.hasEmptyPath()) {
                                    IFile target;
                                    /*
                                     * XPD-2956: making it relative to service
                                     * descriptors folder
                                     */
                                    if (path.toString().startsWith("../")) { //$NON-NLS-1$
                                        Path p = new Path(path.toString());
                                        target =
                                                targetParent.getFile(p
                                                        .makeAbsolute());
                                    } else {
                                        target =
                                                targetParent.getFile(new Path(
                                                        path.path()));
                                    }
                                    /*
                                     * Make sure that all subfolders are
                                     * created, if required
                                     */
                                    if (!targetParent.equals(target.getParent())) {
                                        ProjectUtil.createFolder(target
                                                .getParent(),
                                                false,
                                                new NullProgressMonitor());
                                    }
                                    /*
                                     * XPD-4079: check to see if the resource
                                     * that already exists under SD folder is
                                     * chosen not to be copied again
                                     */
                                    if (!existingResources
                                            .contains(srcResource)) {
                                        Resource resource =
                                                moveResource(srcResource,
                                                        targetResourceSet,
                                                        URI.createPlatformResourceURI(target
                                                                .getFullPath()
                                                                .toString(),
                                                                true));
                                        if (resource instanceof WSDLResourceImpl) {
                                            /*
                                             * This is a WSDL resource so update
                                             * with the source URL
                                             */
                                            WsdlIndexerUtil
                                                    .addSourceUri(resource,
                                                            srcResource
                                                                    .getURI()
                                                                    .toString());
                                        }
                                    }
                                    wsdlCopierRes.getCopiedResources()
                                            .put(target,
                                                    srcResource.getURI()
                                                            .toString());
                                }
                            }
                        }
                        /*
                         * XPD-2956: if any resource in the resources list has
                         * ../ in the schema location, change it w.r.to the
                         * service descriptors folder
                         */
                        List<Resource> newResources =
                                new ArrayList<Resource>(targetResourceSet
                                        .getResources());
                        SchemaLocationHelper schemaLocationHelper =
                                new SchemaLocationHelper();
                        schemaLocationHelper
                                .adjustDotDotSlashInSchemaLocation(targetParent,
                                        srcUri,
                                        targetFile,
                                        newResources);
                        /* Save all imported resources */
                        for (Resource r : newResources) {
                            r.save(null);
                        }
                    } catch (IOException e) {
                        if (targetFile.exists()) {
                            try {
                                targetFile.delete(true, null);
                            } catch (CoreException exception) {
                                LOG.error(exception);
                                Status status =
                                        new Status(IStatus.ERROR,
                                                Activator.PLUGIN_ID, exception
                                                        .getMessage(),
                                                new IOException(exception
                                                        .getMessage(),
                                                        exception));
                                wsdlCopierRes.setStatus(status);
                            }
                        }
                        LOG.error(e);
                        Status status =
                                new Status(
                                        IStatus.ERROR,
                                        Activator.PLUGIN_ID,
                                        Messages.WsdlCopier_errorImportWsdl_error_message,
                                        e);
                        wsdlCopierRes.setStatus(status);
                        throw new CoreException(status);
                    }
                }
            },
                    monitor);
        } finally {
            // Unload all resources from the target resource set
            unloadResources(targetResourceSet);
        }
    }

    /**
     * This method loads in the resultset the imports that have not been loaded
     * automatically when loading the sourceEmfResource.
     * 
     * This typically happens when the import is not internally referenced from
     * the wsdl file
     * 
     * @param sourceEmfResource
     * @param sourceResourceSet
     * @param targetURI
     * @param connectionTimeout
     * @throws IOException
     * @throws CoreException
     */
    private static void loadUnreferencedImports(Resource sourceEmfResource,
            ResourceSet sourceResourceSet, URI targetURI, int connectionTimeout)
            throws IOException, CoreException {

        if (sourceEmfResource != null && sourceResourceSet != null) {
            List<URI> importResourcesURI =
                    getImportResourcesURI(sourceEmfResource);

            if (importResourcesURI != null && !importResourcesURI.isEmpty()) {

                Set<URI> alreadyLoadedResources = new HashSet<URI>();
                EList<Resource> resources = sourceResourceSet.getResources();
                for (Resource resource : resources) {
                    alreadyLoadedResources.add(resource.getURI());
                }

                for (URI importedURI : importResourcesURI) {

                    if (!alreadyLoadedResources.contains(importedURI)) {

                        final Resource importResource =
                                sourceResourceSet.createResource(importedURI);
                        URL url = new URL(importedURI.toString());
                        URLConnection connection = url.openConnection();
                        connection.setReadTimeout(connectionTimeout);
                        final NullProgressMonitor monitor =
                                new NullProgressMonitor();
                        monitor.beginTask(Messages.WsdlCopier_attemptToReadWSDL_progress_message,
                                connectionTimeout / 1000);
                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                monitor.worked(1);
                            }
                        };
                        Timer timer = new Timer();
                        timer.schedule(task, 0, 1000);
                        InputStream inputStream = null;

                        try {
                            inputStream = connection.getInputStream();
                            /* load the contents into the sourceEmfResource */
                            Map<Object, Object> options = null;
                            if (monitor != null) {
                                options = new HashMap<Object, Object>();
                                options.put(WSDLResourceImpl.WSDL_PROGRESS_MONITOR,
                                        monitor);
                            }
                            if (null != inputStream) {
                                importResource.load(inputStream, options);
                                sourceResourceSet.getResources()
                                        .add(importResource);
                            }
                            /*
                             * recursively load imported files that are not
                             * referenced
                             */
                            loadUnreferencedImports(importResource,
                                    sourceResourceSet,
                                    targetURI,
                                    connectionTimeout);

                        } catch (StackOverflowError error) {
                            /*
                             * This happens if there is a cyclic import between
                             * wsdl files. We're catching it here because there
                             * is nothing in the stack trace to show its source
                             * otherwise.
                             */
                            Activator.getDefault().getLogger().error(error);
                        } finally {
                            timer.cancel();
                            monitor.done();
                            if (inputStream != null) {
                                inputStream.close();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns a list of the imported resources in a wsdl file
     * 
     * @param sourceEmfResource
     * @return
     * @throws CoreException
     */
    private static List<URI> getImportResourcesURI(Resource sourceEmfResource)
            throws CoreException {
        if (sourceEmfResource != null
                && sourceEmfResource.getContents() != null) {
            List<URI> importedResources = new ArrayList<URI>();
            EList<EObject> contents = sourceEmfResource.getContents();

            for (EObject object : contents) {
                if (object instanceof Definition) {
                    Definition definition = (Definition) object;

                    /*
                     * add xsd schema instance(s) referred in the wsdl to
                     * imported resources
                     */
                    addSchemaLocationXsdsToImportedResources(sourceEmfResource,
                            importedResources,
                            definition);
                    /*
                     * add the no namespace XML Schema Instance in the wsdl to
                     * imported resources
                     */
                    addNoNamespaceSchemaLocationXsdsToImportedResources(sourceEmfResource,
                            importedResources,
                            definition);
                    /* add xsd(s) to imported resources */
                    addXsdImportsToImportedResources(sourceEmfResource,
                            importedResources,
                            definition);
                    /* add type definition imports to imported resources */
                    addTypeDefinitionImportsToImportedResources(sourceEmfResource,
                            importedResources,
                            definition);

                } else if (object instanceof XSDSchema) {
                    XSDSchema schema = (XSDSchema) object;
                    parseSchemaContents(sourceEmfResource,
                            importedResources,
                            schema);
                }
            }
            return importedResources;
        }
        return Collections.emptyList();
    }

    /**
     * add the no namespace XML Schema Instance in the wsdl to imported
     * resources
     * 
     * @param sourceEmfResource
     * @param importedResources
     * @param definition
     */
    private static void addNoNamespaceSchemaLocationXsdsToImportedResources(
            Resource sourceEmfResource, List<URI> importedResources,
            Definition definition) {

        if (definition instanceof org.eclipse.wst.wsdl.Definition) {
            Element element =
                    ((org.eclipse.wst.wsdl.Definition) definition).getElement();
            if (null != element && null != element.getAttributes()) {
                /*
                 * Once the XML Schema Instance namespace is available in the
                 * wsdl we can use the no namespace schemaLocation attribute
                 */
                Node namedItem =
                        element.getAttributes()
                                .getNamedItem("xsi:noNamespaceSchemaLocation"); //$NON-NLS-1$
                /*
                 * This no namespace schema location attribute has one value
                 * that is the location of the XML schema to use
                 */
                if (null != namedItem) {
                    String xsiNoNamespaceSchemaLoc = namedItem.getTextContent();
                    if (null != xsiNoNamespaceSchemaLoc) {
                        String[] noNamespaceSchemaLocations =
                                xsiNoNamespaceSchemaLoc.split(" "); //$NON-NLS-1$
                        for (int i = 0; i < noNamespaceSchemaLocations.length; i++) {
                            String xsdLocation = noNamespaceSchemaLocations[i];
                            if (null != xsdLocation) {
                                /*
                                 * resolving the referenced schema file by
                                 * sending the xsd name extracted from schema
                                 * location attribute
                                 */
                                try {
                                    URI resolve =
                                            resolveReferencedFile(xsdLocation,
                                                    sourceEmfResource.getURI());
                                    if (resolve != null) {
                                        importedResources.add(resolve);
                                    }
                                } catch (URISyntaxException e) {
                                    try {
                                        throw new CoreException(
                                                new Status(
                                                        IStatus.ERROR,
                                                        Activator.PLUGIN_ID,
                                                        Messages.WsdlCopier_ErrorExportingSchemaFile,
                                                        e));
                                    } catch (CoreException e1) {
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }

    }

    /**
     * @param sourceEmfResource
     * @param importedResources
     * @param definition
     * @throws CoreException
     */
    private static void addTypeDefinitionImportsToImportedResources(
            Resource sourceEmfResource, List<URI> importedResources,
            Definition definition) throws CoreException {
        Types types = definition.getTypes();
        if (types != null && types.getExtensibilityElements() != null) {
            List extensibilityElements = types.getExtensibilityElements();
            if (extensibilityElements != null
                    && !extensibilityElements.isEmpty()) {
                for (Object extensibilityElement : extensibilityElements) {
                    if (extensibilityElement instanceof XSDSchemaExtensibilityElement) {
                        XSDSchemaExtensibilityElement ee =
                                (XSDSchemaExtensibilityElement) extensibilityElement;
                        XSDSchema schema = ee.getSchema();
                        parseSchemaContents(sourceEmfResource,
                                importedResources,
                                schema);
                    }
                }
            }
        }
    }

    /**
     * @param sourceEmfResource
     * @param importedResources
     * @param definition
     * @throws CoreException
     */
    private static void addXsdImportsToImportedResources(
            Resource sourceEmfResource, List<URI> importedResources,
            Definition definition) throws CoreException {
        Map imports = definition.getImports();
        if (imports != null) {
            for (Object key : imports.keySet()) {
                if (key != null) {
                    Object importValue = imports.get(key);
                    if (importValue instanceof List) {
                        List importValues = (List) importValue;
                        if (!importValues.isEmpty()) {
                            for (Object imp : importValues) {
                                if (imp instanceof Import) {
                                    Import i = (Import) imp;
                                    try {
                                        URI resolve =
                                                resolveReferencedFile(i.getLocationURI(),
                                                        sourceEmfResource
                                                                .getURI());
                                        if (resolve != null) {
                                            importedResources.add(resolve);
                                        }
                                    } catch (URISyntaxException e) {
                                        throw new CoreException(
                                                new Status(
                                                        IStatus.ERROR,
                                                        Activator.PLUGIN_ID,
                                                        Messages.WsdlCopier_ErrorExportingSchemaFile,
                                                        e));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * * add namespace xml schema instance(s) referred in the wsdl to imported
     * resources
     * 
     * @param sourceEmfResource
     * @param importedResources
     * @param definition
     * @throws CoreException
     */
    private static void addSchemaLocationXsdsToImportedResources(
            Resource sourceEmfResource, List<URI> importedResources,
            Definition definition) throws CoreException {
        /*
         * XPD-2956: if xsd schema instance is referred in the wsdl then the xsd
         * for that schema instance was not getting imported
         */
        if (definition instanceof org.eclipse.wst.wsdl.Definition) {
            Element element =
                    ((org.eclipse.wst.wsdl.Definition) definition).getElement();
            if (null != element && null != element.getAttributes()) {
                /*
                 * Once the XML Schema Instance namespace is available in the
                 * wsdl we can use the schemaLocation attribute
                 */
                Node namedItem =
                        element.getAttributes()
                                .getNamedItem("xsi:schemaLocation"); //$NON-NLS-1$
                /*
                 * This schema location attribute has two values, separated by a
                 * space. The first value is the namespace to use. The second
                 * value is the location of the XML schema to use for that
                 * namespace
                 */
                if (null != namedItem) {
                    String xsiSchemaLoc = namedItem.getTextContent();

                    if (null != xsiSchemaLoc) {
                        String[] schemaLocations = xsiSchemaLoc.split(" "); //$NON-NLS-1$

                        for (int i = 1; i < schemaLocations.length;) {
                            String xsdLocation = schemaLocations[i];

                            if (null != xsdLocation) {
                                /*
                                 * resolving the referenced schema file by
                                 * sending the xsd name extracted from schema
                                 * location attribute
                                 */
                                try {
                                    URI resolve =
                                            resolveReferencedFile(xsdLocation,
                                                    sourceEmfResource.getURI());
                                    if (resolve != null) {
                                        importedResources.add(resolve);
                                    }
                                } catch (URISyntaxException e) {
                                    throw new CoreException(
                                            new Status(
                                                    IStatus.ERROR,
                                                    Activator.PLUGIN_ID,
                                                    Messages.WsdlCopier_ErrorExportingSchemaFile,
                                                    e));
                                }
                            }
                            /*
                             * starting the loop counter 'i' with 1 and
                             * incrementing by 2 because Multiple pairs of URI
                             * references can be listed, each with a different
                             * namespace name part. For instance
                             * 
                             * xsi:schemaLocation= "http://contoso.com/People
                             * http://contoso.com/schemas/people.xsd
                             * http://contoso.com/schemas/Vehicles
                             * http://contoso.com/schemas/vehicles.xsd
                             * http://contoso.com/schemas/People
                             * http://contoso.com/schemas/people.xsd">
                             * 
                             * where "http://contoso.com/People" is the
                             * namespace and
                             * "http://contoso.com/schemas/people.xsd" is the
                             * schema location,
                             * "http://contoso.com/schemas/Vehicles" is the
                             * namespace and
                             * "http://contoso.com/schemas/vehicles.xsd" is the
                             * schema location
                             */
                            i = i + 2;
                        }
                    }
                }
            }
        }
    }

    /**
     * @param sourceEmfResource
     * @param importedResources
     * @param schema
     * @throws CoreException
     */
    private static void parseSchemaContents(Resource sourceEmfResource,
            List<URI> importedResources, XSDSchema schema) throws CoreException {

        EList<XSDSchemaContent> schemaContents = schema.getContents();

        /*
         * XPD-2956: bharti: schema instances (if any) used in the xsd(s) might
         * have to be added to the imported resources. I did not come across any
         * case that would need a fix here. if any case is found then may be we
         * might to do addSchemaLocationXsdsToImportedResources and
         * addNoNamespaceSchemaLocationXsdsToImportedResources here
         */
        if (schemaContents != null && !schemaContents.isEmpty()) {
            for (XSDSchemaContent schemaContent : schemaContents) {

                if (schemaContent instanceof XSDImport
                        || schemaContent instanceof XSDInclude) {
                    String schemaLocation =
                            ((XSDSchemaDirective) schemaContent)
                                    .getSchemaLocation();
                    if (schemaLocation != null) {
                        try {
                            URI resolve =
                                    resolveReferencedFile(schemaLocation,
                                            sourceEmfResource.getURI());
                            if (resolve != null) {
                                if (!importedResources.contains(resolve)) {
                                    importedResources.add(resolve);
                                }
                            }
                        } catch (Exception e) {
                            // Report error
                            throw new CoreException(
                                    new Status(
                                            IStatus.ERROR,
                                            Activator.PLUGIN_ID,
                                            Messages.WsdlCopier_ErrorExportingSchemaFile,
                                            e));
                        }
                    }
                }
            }
        }
    }

    /**
     * Resolves a relative location
     * 
     * @param schemaLocation
     * @param sourceFileURI
     * @return
     * @throws URISyntaxException
     */
    private static URI resolveReferencedFile(String schemaLocation,
            URI sourceFileURI) throws URISyntaxException {

        if (schemaLocation != null && sourceFileURI != null) {
            URI importedURI = URI.createURI(schemaLocation);
            if (importedURI != null) {
                URI resolve = importedURI.resolve(sourceFileURI);
                return resolve;
            }
        }
        return null;
    }

    /**
     * Unload all resources from the resource set.
     * 
     * @param rSet
     */
    private static void unloadResources(ResourceSet rSet) {
        for (Resource res : rSet.getResources()) {
            if (res.isLoaded()) {
                res.unload();
            }
        }
        rSet.getResources().clear();
    }

    /**
     * Validate the resources being imported. This will ensure that the includes
     * in the WSDL haven't got absolute paths or relative references upstream
     * 
     * @param targetParent
     *            target folder
     * @param sourceEmfResource
     *            the wsdl file being imported
     * @param resources
     *            all additional resources (including the wsdl) being imported
     * @return
     */
    private static boolean validateResources(IContainer targetParent,
            Resource sourceEmfResource, List<Resource> resources,
            List<Resource> absoluteURLResources) {

        URI base = sourceEmfResource.getURI();

        if (resources.size() > 1) {
            final Display display = XpdResourcesPlugin.getStandardDisplay();
            for (Resource res : resources) {
                if (res != sourceEmfResource) {
                    URI deresolve = getRelativePath(base, res.getURI());

                    if (!deresolve.hasEmptyPath()) {
                        /*
                         * If an include is referenced at a http location, or
                         * has an absolute path, then that is not supported
                         */
                        if ((deresolve.hasAbsolutePath()
                                && deresolve.scheme() != null
                                && !deresolve.scheme().contains("http") && !deresolve.scheme().contains("ftp"))) {//$NON-NLS-1$ //$NON-NLS-2$
                            showUnsupportedRefMessageDialog(display,
                                    deresolve.toString());
                            return false;
                        } else {
                            if (deresolve.hasAbsolutePath()
                                    && (deresolve.scheme() == null)) {
                                /*
                                 * file:/// resembles the usage of ftp and when
                                 * we deresolve it we come back with ///. so
                                 * need to support it
                                 */
                                if (!deresolve.toString().startsWith("///")) { //$NON-NLS-1$
                                    absoluteURLResources.add(res);
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    /***
     * 
     * This method will check if the files being imported don't already exist in
     * the workspace (user will be asked if they wish to overwrite)
     * 
     * @param targetParent
     *            target SD special folder
     * @param sourceEmfResource
     *            the wsdl file being imported
     * @param resources
     *            all additional resources (including the wsdl) being imported
     * @param existingResources
     * @param isOverwriteExistingResources
     */
    private static void checkResAlreadyExistsAndNeedsOverwriting(
            IContainer targetParent, Resource sourceEmfResource,
            List<Resource> resources, List<Resource> existingResources,
            boolean isOverwriteExistingResources) {

        URI base = sourceEmfResource.getURI();

        final Display display = XpdResourcesPlugin.getStandardDisplay();
        boolean overwriteAll = isOverwriteExistingResources;
        for (Resource res : resources) {
            if (res != sourceEmfResource) {
                URI deresolve = getRelativePath(base, res.getURI());

                if (!deresolve.hasEmptyPath()) {
                    // If an include is referenced at a location above
                    // the wsdl file, or has an absolute path, then that is
                    // not supported
                    String path = deresolve.path();

                    /*
                     * XPD-2956: if the res has ../ in the schema location
                     * making it refer to a location above wsdl file, then we
                     * make it relative to service descriptors folder
                     */
                    if (path.startsWith("..")) { //$NON-NLS-1$
                        IPath newPath = new Path(path);
                        SchemaLocationHelper schemaLocationHelper =
                                new SchemaLocationHelper();
                        newPath =
                                schemaLocationHelper
                                        .getRelativePathToSDFolder(newPath);
                        path = newPath.toString();
                    }

                    // Check if a file with the same path exists
                    IFile file = targetParent.getFile(new Path(path));
                    if (file.exists() && !overwriteAll) {
                        int ret = showAskOverwriteFileDialog(display, path);
                        if (ret == 1) {
                            // User chose not to overwrite file
                            // return false;
                            /*
                             * XPD-4079: User chose not to overwrite file. So
                             * add them to the existing resources that are
                             * chosen not to be copied again
                             */
                            existingResources.add(res);
                        } else if (ret == 2) {
                            // Overwrite all so don't ask again
                            overwriteAll = true;
                        }
                    }
                }
            }
        }
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
    private static URI getRelativePath(URI base, URI uri) {
        return uri.deresolve(base, false, true, false);
    }

    /**
     * Show the unsupported reference message dialog.
     * 
     * @param display
     * @param path
     */
    private static void showUnsupportedRefMessageDialog(final Display display,
            final String path) {
        display.syncExec(new Runnable() {
            public void run() {
                MessageDialog.openError(display.getActiveShell(),
                        Messages.WsdlCopier_wsdlImportError_dialog_title,
                        String.format(Messages.WsdlCopier_wsdlImportError_dialog_unsupportedReference_message,
                                path));
            }
        });
    }

    /**
     * Ask the user if they wish for the file to be overwritten.
     * 
     * @param display
     * @param path
     * @return 0 to overwrite, 1 to not overwrite and 2 to overwrite all
     */
    private static int showAskOverwriteFileDialog(final Display display,
            final String path) {
        final Integer[] ret = new Integer[] { 0 };

        display.syncExec(new Runnable() {
            public void run() {
                MessageDialog dlg =
                        new MessageDialog(
                                display.getActiveShell(),
                                Messages.WsdlCopier_wsdlImportError_dialog_title,
                                null,
                                String.format(Messages.WsdlCopier_wsdlImportError_dialog_fileExists_message,
                                        path),
                                MessageDialog.QUESTION,
                                new String[] { IDialogConstants.YES_LABEL,
                                        IDialogConstants.NO_LABEL,
                                        Messages.WsdlCopier_overwriteAll_button },
                                0);
                ret[0] = dlg.open();
            }
        });

        return ret[0];
    }

    private static String ensureExtensionIsLowerCase(String fullString,
            String extensionPart) {
        // emf is case sensitive towards the extension
        // but the extension could be valid on windows in upper case
        // So therefore we need to convert the extension to lower case if
        // we are running on Windows.

        if (!Platform.getOS().equals(Platform.OS_WIN32)) {
            return fullString;
        }

        if (fullString != null && extensionPart != null
                && extensionPart.length() > 0) {
            fullString =
                    fullString.substring(0,
                            fullString.length() - extensionPart.length() - 1);
            fullString += "." + extensionPart.toLowerCase(); //$NON-NLS-1$
        }
        return fullString;
    }

    /**
     * public for the sake of testing
     * 
     * @param stemIFile
     *            the preferred file name, but which may already exist
     * @return the stem file with a unique number appended if necessary
     */
    public static IFile generateUniqueTargetFile(final IFile stemIFile) {
        if (!stemIFile.exists()) {
            return stemIFile;
        }
        final IContainer parent = stemIFile.getParent();
        final String stemFileNameWithExtension = stemIFile.getName();
        final int lastDotIndex = stemFileNameWithExtension.lastIndexOf('.');
        final String stemFileNameWithoutExtension =
                stemFileNameWithExtension.substring(0, lastDotIndex);
        final String extension =
                stemFileNameWithExtension.substring(lastDotIndex);
        String numericEnding = getNumericEnding(stemFileNameWithoutExtension);
        final String stemFileNameWithoutNumericEnding =
                numericEnding == null ? stemFileNameWithoutExtension
                        : stemFileNameWithoutExtension.substring(0,
                                stemFileNameWithoutExtension.length()
                                        - numericEnding.length());
        IFile result = stemIFile;
        if (numericEnding == null) {
            result = parent.getFile(new Path(stemFileNameWithoutExtension + "2" //$NON-NLS-1$
                    + extension));
        }
        while (result.exists()) {
            numericEnding = getNumericEnding(stemFileNameWithoutExtension);
            int nextNumber = Integer.parseInt(numericEnding) + 1;
            result =
                    parent.getFile(new Path(stemFileNameWithoutNumericEnding
                            + Integer.toString(nextNumber) + extension));
        }
        return result;
    }

    private static String getNumericEnding(String string) {
        if (!Character.isDigit(string.charAt(string.length() - 1))) {
            return null;
        }
        for (int index = string.length() - 1; index >= 0; index--) {
            if (!Character.isDigit(string.charAt(index))) {
                return string.substring(index + 1);
            }
        }
        return null;
    }

    /**
     * @param sourceResource
     *            The resource to move.
     * @param targetSet
     *            The target set to move it to.
     * @param targetURI
     *            The target URI for the resource.
     * @return The target resource.
     * @throws IOException
     *             If there was a problem moving the resource.
     */
    private static Resource moveResource(Resource sourceResource,
            ResourceSet targetSet, URI targetURI) throws IOException {
        Resource targetResource = targetSet.createResource(targetURI);
        EList<EObject> sourceContents = sourceResource.getContents();
        targetResource.getContents().addAll(sourceContents);
        return targetResource;
    }

    static class TrackingURIConverter extends ExtensibleURIConverterImpl {
        File keyStoreFile;

        CertificateAcceptanceTracker certificateAcceptanceTracker;

        /**
         * 
         */
        public TrackingURIConverter(File keyStoreFile,
                CertificateAcceptanceTracker certificateAcceptanceTracker) {
            this.keyStoreFile = keyStoreFile;
            this.certificateAcceptanceTracker = certificateAcceptanceTracker;
        }

        @Override
        public InputStream createInputStream(URI uri) throws IOException {
            // TODO if it is an https url, try to connect through the proxy
            if ("https".equals(uri.scheme())) { //$NON-NLS-1$
                try {
                    java.net.URI javaDotNetSourceURI =
                            new java.net.URI(uri.toString());
                    try {
                        CertificateManager
                                .checkCertificate(javaDotNetSourceURI,
                                        keyStoreFile,
                                        certificateAcceptanceTracker);
                    } catch (Exception e) {
                        LOG.error(e);
                    }
                    if (certificateAcceptanceTracker.isTrusted()) {
                        try {
                            return CertificateManager
                                    .getSSLInputStream(javaDotNetSourceURI,
                                            keyStoreFile);
                        } catch (Exception e) {
                            LOG.error(e);
                        }
                    } else {
                        throw new SecurityException(
                                Messages.WsdlCopier_security_error);
                    }
                } catch (URISyntaxException e1) {
                    LOG.error(e1);
                }
            }
            return super.createInputStream(uri);
        }
    }
}
