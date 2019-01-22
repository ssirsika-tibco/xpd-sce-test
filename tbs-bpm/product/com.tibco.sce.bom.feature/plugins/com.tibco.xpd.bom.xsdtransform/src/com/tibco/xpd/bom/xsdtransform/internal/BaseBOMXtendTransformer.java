/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.SimpleLog;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.wst.common.uriresolver.internal.provisional.URIResolver;
import org.eclipse.wst.common.uriresolver.internal.provisional.URIResolverPlugin;
import org.openarchitectureware.emf.EcoreUtil2;
import org.openarchitectureware.type.impl.java.JavaMetaModel;
import org.openarchitectureware.type.impl.java.beans.JavaBeansStrategy;
import org.openarchitectureware.uml2.UML2MetaModel;
import org.openarchitectureware.uml2.profile.ProfileMetaModel;
import org.openarchitectureware.workflow.WorkflowInterruptedException;
import org.openarchitectureware.xsd.OawXMLResource;
import org.openarchitectureware.xsd.XMLReaderImpl;
import org.openarchitectureware.xsd.XSDMetaModel;
import org.openarchitectureware.xsd.util.XSDLog;
import org.openarchitectureware.xsd.util.XSDLog.XSDLogFactory;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.BOMScopeProvider;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.bom.xsdtransform.imports.template.ImportTransformationData;
import com.tibco.xpd.resources.PreferenceStoreKeys;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.explicit.Validator;
import com.tibco.xpd.validation.provider.IIssue;
import com.tibco.xpd.validation.provider.IssueInfo;
import com.tibco.xpd.validation.provider.ValidationException;

/**
 * Base class for XSD / BOM transformations. Ensures we only initialise the
 * metamodels once to avoid any memory issues
 * <p>
 * <i>Created: 24 July 2008</i>
 * </p>
 * 
 * @author Gary Lewis, njpatel
 */
public class BaseBOMXtendTransformer {
    public static SimpleLog simpleLog = new SimpleLog("XSD");

    /**
     * static block that fundamentally disables logging in oaw transformations
     * by forcing a logger with all levels switched off
     */
    static {
        simpleLog.setLevel(SimpleLog.LOG_LEVEL_OFF);
        XSDLogFactory factory = new XSDLogFactory() {
            @Override
            public Log getLog(java.lang.Class<?> clazz) {
                return simpleLog;
            }
        };
        XSDLog.setLogFactory(factory);
    }

    private static final String WSDL_SCHEMA_PATH = "model/WSDLSchema.xsd"; //$NON-NLS-1$   

    public static final String XSD_METAMODEL_ID = "xsd"; //$NON-NLS-1$

    /**
     * The XSD destination environment id.
     */
    public static final String XSD_DESTINATION =
            BOMValidatorActivator.VALIDATION_DEST_ID_XSD;

    /**
     * The WSDL destination environment id.
     */
    public static final String WSDL_DESTINATION =
            BOMValidatorActivator.VALIDATION_DEST_ID_WSDL;

    /**
     * The BOM generic destination enviroment id.
     */
    public static final String BOM_GENERIC_DESTINATION =
            BOMValidatorActivator.VALIDATION_DEST_ID_BOMGENERIC;

    public static final String[] typeTemplateNames = new String[] {
            "byte_StudioGeneratedTemplate", "double_StudioGeneratedTemplate", //$NON-NLS-1$ //$NON-NLS-2$
            "float_StudioGeneratedTemplate", "long_StudioGeneratedTemplate", //$NON-NLS-1$ //$NON-NLS-2$
            "nonnegativeinteger_StudioGeneratedTemplate", //$NON-NLS-1$
            "positiveinteger_StudioGeneratedTemplate", //$NON-NLS-1$
            "short_StudioGeneratedTemplate", //$NON-NLS-1$
            "unsignedbyte_StudioGeneratedTemplate", //$NON-NLS-1$
            "unsignedint_StudioGeneratedTemplate", //$NON-NLS-1$
            "unsignedlong_StudioGeneratedTemplate", //$NON-NLS-1$
            "unsignedshort_StudioGeneratedTemplate" }; //$NON-NLS-1$

    public static final String[] xsdTypes = new String[] {
            "anyType", "anySimpleType", //$NON-NLS-1$ //$NON-NLS-2$ 
            "gYearMonth", "gYear",//$NON-NLS-1$ //$NON-NLS-2$
            "gMonthDay", "gDay", //$NON-NLS-1$ //$NON-NLS-2$
            "gMonth", "QName", //$NON-NLS-1$ //$NON-NLS-2$
            "NOTATION", "normalizedString", //$NON-NLS-1$ //$NON-NLS-2$
            "token", "nonPositiveInteger", //$NON-NLS-1$ //$NON-NLS-2$ 
            "long", "nonNegativeInteger", //$NON-NLS-1$ //$NON-NLS-2$
            "language", "Name", //$NON-NLS-1$ //$NON-NLS-2$
            "NMTOKEN", "negativeInteger", //$NON-NLS-1$ //$NON-NLS-2$
            "int", "unsignedLong", //$NON-NLS-1$ //$NON-NLS-2$
            "positiveInteger", "NCName", //$NON-NLS-1$ //$NON-NLS-2$ 
            "NMTOKENS", "short", //$NON-NLS-1$ //$NON-NLS-2$
            "unsignedInt", "IDREF", //$NON-NLS-1$ //$NON-NLS-2$
            "ENTITY", "byte", //$NON-NLS-1$ //$NON-NLS-2$
            "unsignedShort", "IDREFS", //$NON-NLS-1$ //$NON-NLS-2$
            "ENTITIES", "unsignedByte", "hexBinary", "base64binary" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

    private static final XSDMetaModel xsdMetaModel =
            createXSDMetamodelWithSchema(XSD_METAMODEL_ID);

    private static final ProfileMetaModel profileMetaModel =
            createProfileMetaModel();

    private static final UML2MetaModel umlMetaModel = new UML2MetaModel();

    private static final JavaMetaModel javaMetaModel = new JavaMetaModel(
            "JavaMM", new JavaBeansStrategy()); //$NON-NLS-1$

    /**
     * Get the XSD meta model that contains the XSD schema and also the TIBCO
     * extension schema.
     * 
     * @return
     */
    public static XSDMetaModel getXsdMetaModel() {
        return xsdMetaModel;
    }

    /**
     * Get the primitive types profile meta model.
     * 
     * @return
     */
    public static ProfileMetaModel getProfileMetaModel() {
        return profileMetaModel;
    }

    /**
     * Get the UML2 meta model.
     * 
     * @return
     */
    public static UML2MetaModel getUML2MetaModel() {
        return umlMetaModel;
    }

    /**
     * Get the Java meta model (allows the passing of java beans to the OAW
     * transformation).
     * 
     * @return
     */
    public static JavaMetaModel getJavaMetaModel() {
        return javaMetaModel;
    }

    /**
     * @param name
     * @return
     */
    protected boolean isNameClashWithSimpleTypeTemplateNames(String name) {
        for (String templateName : typeTemplateNames) {
            if (name.equals(templateName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create the XSD meta model containing the XSD schema and the TIBCO
     * extension schema.
     * 
     * @param systemId
     * @return
     */
    protected static XSDMetaModel createXSDMetamodelWithSchema(String systemId) {
        LOG(String
                .format("BaseBOMXtendTransformer: createXSDMetamodelWithSchema (%s)", //$NON-NLS-1$
                        systemId));
        long start = System.currentTimeMillis();
        XSDMetaModel xsdMM = new XSDMetaModel();

        URI schemaURI = URI.createURI("http://www.w3.org/2001/XMLSchema.xsd"); //$NON-NLS-1$
        xsdMM.addSchemaFile(schemaURI.toString());

        URL url = Activator.getDefault().getBundle().getEntry(WSDL_SCHEMA_PATH);
        xsdMM.addSchemaFile(url.toString());

        // TIBCO extensions schema.
        URI tibexURI =
                URI.createPlatformPluginURI("com.tibco.xpd.wsdl/schema/bstibex.xsd", //$NON-NLS-1$
                        true);
        xsdMM.addSchemaFile(tibexURI.toString());

        xsdMM.setId(systemId);
        LOG(String
                .format("BaseBOMXtendTransformer: createXSDMetamodelWithSchema (%1$s): %2$d", //$NON-NLS-1$
                        systemId,
                        (System.currentTimeMillis() - start)));
        return xsdMM;
    }

    /**
     * Create the primitive type profile meta model.
     */
    private static ProfileMetaModel createProfileMetaModel() {
        LOG("BaseBOMXtendTransformer: createProfileMetaModel..."); //$NON-NLS-1$
        long start = System.currentTimeMillis();
        // add profile meta model for primitive type restrictions etc
        ProfileMetaModel profileMetaModel = new ProfileMetaModel();
        profileMetaModel.setErrorIfStereotypeMissing(true);
        profileMetaModel
                .setProfile(PrimitivesUtil.BOM_PRIMITIVE_TYPES_FACETS_PROFILE_URI);
        LOG("XSD BaseBOMXtendTransformer: createProfileMetaModel complete: " //$NON-NLS-1$
                + (System.currentTimeMillis() - start));
        return profileMetaModel;
    }

    /**
     * This method checks to see if the given BOM resource is in a project that
     * has Bom2Xsd, BOM Generic and (optionally) Bom2Wsdl validations enabled
     * AND has passed those validaitons with no error level problems.
     * <p>
     * The BOM resource's dependencies (the BOM's it references) are also
     * checked.
     * <p>
     * The results will be cached in the cachedResults map - <b>this map is the
     * caller's responsibility</b> - i.e. it is the caller's responsibility to
     * define the life time and scope of this map.
     * 
     * @param bomFile
     *            The resource to validate (plus all its dependencies).
     * @param includeWSDLMarkers
     *            Whether to include BOM2WSDL validation markers.
     * @param cachedResults
     *            Map of previous results or <code>null</code> to not cache.
     * 
     * @return <code>true</code> if the given BOM file has bom2xsd, BOM generic
     *         and (optionally bom2wsdl) validations enabled for project and has
     *         passed those validations without error or <code>false</code> if
     *         not.
     */
    public static boolean hasPassedBom2XsdValidation(IFile bomFile,
            boolean includeWSDLMarkers, Map<IResource, Boolean> cachedResults) {

        /*
         * See if result is already cached.
         */
        if (cachedResults != null) {
            if (cachedResults.containsKey(bomFile)) {
                return cachedResults.get(bomFile);
            }
        }

        /*
         * Not cached - so get look up the problems markers for this file.
         */
        boolean hasPassedValidations = false;

        if (bomFile.isAccessible()
                && hasBom2XsdAndGenericDestinationsEnabled(bomFile,
                        includeWSDLMarkers)) {

            try {
                IMarker[] problemMarkers =
                        bomFile.findMarkers(XpdConsts.VALIDATION_MARKER_TYPE,
                                true,
                                IResource.DEPTH_ZERO);

                boolean foundErrors = false;

                if (problemMarkers != null && problemMarkers.length > 0) {
                    /*
                     * Go thru makers looking for errors in the destinations
                     * we're interested in.
                     */
                    for (IMarker problemMarker : problemMarkers) {
                        String destinationId =
                                problemMarker
                                        .getAttribute(IIssue.DESTINATION_ID, ""); //$NON-NLS-1$

                        /*
                         * Always check BOMgeneric and bom2xsd and optionally
                         * bom2wsdl.
                         */
                        if (BOM_GENERIC_DESTINATION.equals(destinationId)
                                || XSD_DESTINATION.equals(destinationId)
                                || (includeWSDLMarkers && WSDL_DESTINATION
                                        .equals(destinationId))) {
                            int severity =
                                    problemMarker
                                            .getAttribute(IMarker.SEVERITY, 0);
                            if (severity == IMarker.SEVERITY_ERROR) {
                                foundErrors = true;
                                /* S'all we need to know! */
                                break;
                            }
                        }
                    }
                }

                if (!foundErrors) {
                    /*
                     * If we found no errors - recursively check BOM's we depend
                     * upon.
                     */
                    Collection<IResource> bomReferences =
                            getImmediateDependencies(bomFile);

                    for (IResource bomReference : bomReferences) {
                        if (bomReference instanceof IFile) {
                            if (!hasPassedBom2XsdValidation((IFile) bomReference,
                                    includeWSDLMarkers,
                                    cachedResults)) {
                                /* And underlying BOM has errors */
                                foundErrors = true;
                                break;
                            }
                        }
                    }

                    if (!foundErrors) {
                        /*
                         * If we did not find errors in the bom or the
                         * underlying BOM's then it's safe to say that our BOM
                         * has passed validations.
                         */
                        hasPassedValidations = true;
                    }

                }

            } catch (CoreException e) {
                Activator
                        .getDefault()
                        .getLogger()
                        .error(e,
                                "Exception accessing markers for BOM resource: " //$NON-NLS-1$
                                        + bomFile.getFullPath().toString());
            }
        }

        /* Cache the result for next time if required */
        if (cachedResults != null) {
            cachedResults.put(bomFile, hasPassedValidations);
        }

        return hasPassedValidations;
    }

    /**
     * @param bomFile
     * @param includeWsdlValidations
     * @return <code>true</code> if bom file is in aproject which has Bom2Xsd,
     *         Bom generic and (optionally) Bom2Wsdl valdiations enabled.
     */
    private static boolean hasBom2XsdAndGenericDestinationsEnabled(
            IFile bomFile, boolean includeWsdlValidations) {
        IProject project = bomFile.getProject();
        if (project != null) {
            if (BOMScopeProvider.isValidationDestinationEnabled(project,
                    BOM_GENERIC_DESTINATION)
                    && BOMScopeProvider.isValidationDestinationEnabled(project,
                            XSD_DESTINATION)) {

                if (!includeWsdlValidations) {
                    return true;
                }

                if (BOMScopeProvider.isValidationDestinationEnabled(project,
                        WSDL_DESTINATION)) {
                    return true;
                }

            }
        }
        return false;
    }

    /**
     * Checks all bom generic and xsd destination and if for wsdl it also checks
     * wsdl destination to see if any errors in the bom and its referenced bom
     * resources.
     * 
     * @param source
     *            resource to check for errors on
     * @param validationMarkerType
     *            the type of validation marker to check for
     * @param destinationId
     *            validation destination id
     * @param result
     *            populates the list with validation errors/warnings/info, can
     *            be <code>null</code> if no result is required
     * @return <code>true</code> if no errors were reported, <code>false</code>
     *         otherwise.
     */
    public static boolean isDestinationAndNoErrors(final IResource source,
            final String validationMarkerType, final String destinationId,
            List<IStatus> result) throws ValidationException {

        if (result == null) {
            result = new ArrayList<IStatus>();
        }
        return isDestinationAndNoErrors((IFile) source,
                validationMarkerType,
                destinationId,
                result,
                new HashSet<IResource>());
    }

    /**
     * Checks all bom generic and xsd destination and if for wsdl it also checks
     * wsdl destination to see if any errors in the bom and its referenced bom
     * resources.
     * 
     * @param source
     * @param validationMarkerType
     * @param destinationId
     * @param result
     *            the result of the validation (will report errors, warnings and
     *            info).
     * @param parsedResources
     * @return <code>true</code> if validation successful, <code>false</code> if
     *         errors found.
     * @throws ValidationException
     */
    private static boolean isDestinationAndNoErrors(final IFile source,
            final String validationMarkerType, final String destinationId,
            List<IStatus> result, Set<IResource> parsedResources)
            throws ValidationException {
        LOG("BaseBOMXtendTransformer: isDestinationAndNoErrors..."); //$NON-NLS-1$
        long start = System.currentTimeMillis();

        try {
            parsedResources.add(source);
            // set up destinations to check
            Set<Destination> destinations = new HashSet<Destination>();
            Destination dest =
                    ValidationActivator.getDefault()
                            .getDestination(BOM_GENERIC_DESTINATION);
            if (dest != null) {
                destinations.add(dest);
            }
            dest =
                    ValidationActivator.getDefault()
                            .getDestination(XSD_DESTINATION);
            if (dest != null) {
                destinations.add(dest);
            }

            if (destinationId.equals(WSDL_DESTINATION)) {
                dest =
                        ValidationActivator.getDefault()
                                .getDestination(WSDL_DESTINATION);
                if (dest != null) {
                    destinations.add(dest);
                }
            }

            // find if any errors for the particular bom resource for the
            // destinations
            Validator validator = new Validator(destinations);
            Collection<IIssue> issues = validator.validate(source);

            // Update the result list with the outcome of the validation
            for (IIssue issue : issues) {
                int severity;
                switch (issue.getSeverity()) {
                case IMarker.SEVERITY_WARNING:
                    severity = IStatus.WARNING;
                    break;
                case IMarker.SEVERITY_INFO:
                    severity = IStatus.INFO;
                    break;
                default:
                    severity = IStatus.ERROR;
                }

                IssueInfo info = validator.getIssueInfo(issue);
                issue.createMessage(info.getMessage());
                result.add(new Status(severity, Activator.PLUGIN_ID, issue
                        .getMessage()));
            }

            // Continue if no errors, otherwise return false
            if (getErrors(result).isEmpty()) {
                /*
                 * if no errors check the dependency bom resources for errors
                 * also
                 */
                Set<IFile> bomResources = new HashSet<IFile>();
                getAllDependencies(source, bomResources);
                bomResources.removeAll(parsedResources);

                /*
                 * for each dependency bom resource check if this has errors for
                 * the destination environments and only return true if all pass
                 * without errors
                 */
                for (IFile tmpResource : bomResources) {
                    if (!isDestinationAndNoErrors(tmpResource,
                            validationMarkerType,
                            destinationId,
                            result,
                            parsedResources)) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        } finally {
            LOG("BaseBOMXtendTransformer: isDestinationAndNoErrors(" + source.getFullPath().toString() + ") complete: " //$NON-NLS-1$
                    + (System.currentTimeMillis() - start));
        }
        return true;
    }

    /**
     * Add all resources that the given resource depends upon (recursively)
     * 
     * @param source
     *            source to get dependencies of
     * @param dependencies
     * 
     */
    public static void getAllDependencies(IFile source, Set<IFile> dependencies) {

        if (source != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(source);
            if (wc != null) {
                List<IResource> dependency = wc.getDependency();
                if (dependency != null) {
                    for (IResource resource : dependency) {

                        // If this file has not already been processed then
                        // process it
                        if (resource instanceof IFile
                                && !dependencies.contains(resource)) {
                            dependencies.add((IFile) resource);

                            // Get all the dependents of this resource
                            getAllDependencies((IFile) resource, dependencies);
                        }
                    }
                }
            }
        }

        return;
    }

    /**
     * Get files that the given source is immediately dependent upon
     * (non-recursive).
     * 
     * @param source
     * 
     * @return Files that source is immediately dependent upon.
     */
    public static Collection<IResource> getImmediateDependencies(
            IResource source) {
        Set<IResource> dependencies = new HashSet<IResource>();

        if (source != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(source);
            if (wc != null) {
                List<IResource> wcDependencies = wc.getDependency();
                if (wcDependencies != null) {
                    for (IResource wcDependency : wcDependencies) {
                        if (!wcDependencies.equals(source)) {
                            dependencies.add(wcDependency);
                        }
                    }
                }
            }
        }

        return dependencies;
    }

    /**
     * Start a transaction. If a read-write transaction is already active then
     * this method will do nothing and return <code>null</code>. If the active
     * transaction is read-only then this will start an unprotected write
     * transaction and return. Othewise, this will start a new transaction and
     * return.
     * 
     * @param domain
     *            editing doman
     * @return {@link Transaction} if transaction created, <code>null</code>
     *         otherwise.
     * @throws InterruptedException
     */
    protected final Transaction startTransaction(
            InternalTransactionalEditingDomain editingDomain)
            throws InterruptedException {

        return Activator.startTransaction(editingDomain);
    }

    /**
     * Clean up the transformation editing domain. This will unload all
     * workspace resources from the editing domain but keep any internal
     * resources.
     * 
     * @param data
     * 
     * @throws InterruptedException
     */
    protected void cleanEditingDomain(TransactionalEditingDomain editingDomain) {

        final Set<Resource> toRemove = new HashSet<Resource>();
        final EList<Resource> resources =
                editingDomain.getResourceSet().getResources();
        for (Resource res : resources) {
            URI uri = res.getURI();
            if (!(uri.isPlatformPlugin() || "pathmap".equalsIgnoreCase(uri //$NON-NLS-1$
                    .scheme()))) {
                toRemove.add(res);
            }
        }

        if (!toRemove.isEmpty()) {
            try {
                editingDomain.runExclusive(new Runnable() {

                    @Override
                    public void run() {
                        // Remove resources from the resourceset
                        for (Resource res : toRemove) {
                            resources.remove(res);
                        }
                    }
                });
            } catch (InterruptedException e) {
                Activator.getDefault().getLogger()
                        .error(e, "Problem encountered in unloading resources"); //$NON-NLS-1$
            }
        }
    }

    /**
     * Return all error status objects from the given list.
     * 
     * @param statusList
     * @return error objects if any, empty list otherwise.
     */
    protected static List<IStatus> getErrors(List<IStatus> statusList) {
        List<IStatus> errors = new ArrayList<IStatus>();

        if (statusList != null) {
            for (IStatus status : statusList) {
                if (status.getSeverity() == IStatus.ERROR) {
                    errors.add(status);
                }
            }
        }
        return errors;
    }

    /**
     * Get the model from the given source file. This uses {@link XMLReaderImpl}
     * to read the XML file which will return an OAW resource.
     * 
     * @param data
     * @param sourceFile
     * @return
     */
    protected EObject getModel(ImportTransformationData data, URI srcFile)
            throws CoreException {
        try {
            /* SID XPD-5079 Moved readXML() for caching. */
            return data.readXML(URI.createFileURI(data.getSourceLocation()),
                    srcFile.toString(),
                    true);
        } catch (Exception e) {
            throw new CoreException(
                    new Status(
                            IStatus.ERROR,
                            Activator.PLUGIN_ID,
                            Messages.BaseBOMXtendTransformer_problemLoadingResource_error_shortdesc,
                            e));
        }
    }

    /**
     * Log the given message.
     * 
     * @param message
     */
    protected static void LOG(String message) {
        // System.out.println(message);
    }

    /** Sid XPD-5079: readXML() moved to ImportTransformationData */

    /**
     * Load the model from the given schema at the given namespace url. Uses
     * {@link OawXMLResource} to load the resource.
     * 
     * @param rSet
     * @param namespace
     * @return the OAW resource schema document root if successful,
     *         <code>null</code> otherwise.
     */
    public static Object readXMLFromURL(ResourceSet rSet, String namespace) {
        EObject schema = null;
        if (rSet != null && namespace != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            options.put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);
            options.put(XMLResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$
            options.put(XMLResource.NO_NAMESPACE_SCHEMA_LOCATION, Boolean.TRUE); //$NON-NLS-1$

            rSet.setPackageRegistry(new EPackageRegistryImpl(getXsdMetaModel()
                    .getXsdManager().getPackageRegistry()));
            URI xmlUri = EcoreUtil2.getURI(namespace);
            XMLResource res = new OawXMLResource(xmlUri, getXsdMetaModel());
            rSet.getResources().add(res);

            InputStream inputStream = null;
            try {
                // Get the HTTP connection timeout value from the preferences
                int connectionTimeout =
                        XpdResourcesPlugin.getDefault().getPreferenceStore()
                                .getInt(PreferenceStoreKeys.HTTP_TIMEOUT);
                if (connectionTimeout == 0) {
                    // If we have an invalid value then reset to 1 minute
                    connectionTimeout = 60000;
                }

                // read from physical location specified in XML Catalog if it
                // exists
                URL url = new URL(namespace);
                URIResolver resolver = URIResolverPlugin.createResolver();
                String logicalResult = resolver.resolve("", namespace, ""); //$NON-NLS-1$ //$NON-NLS-2$
                if (logicalResult != null && logicalResult.trim().length() > 0) {
                    String physicalResult =
                            resolver.resolvePhysicalLocation("", namespace, logicalResult); //$NON-NLS-1$
                    if (physicalResult != null
                            && physicalResult.trim().length() > 0) {
                        url = new URL(physicalResult);
                    }
                }

                // connect
                URLConnection connection = url.openConnection();
                connection.setReadTimeout(connectionTimeout);

                inputStream = connection.getInputStream();

                res.load(inputStream, options);

                if (getXsdMetaModel().getXsdManager().hasErrors())
                    throw new WorkflowInterruptedException(
                            "There were errors loading the XSD meta models."); //$NON-NLS-1$

                if (res.getContents().size() < 1)
                    throw new WorkflowInterruptedException(
                            "Error loading XML file: contents is empty"); //$NON-NLS-1$

                EObject docroot = res.getContents().get(0);

                if (docroot.eContents().size() < 1)
                    throw new WorkflowInterruptedException(
                            "Error loading XML file: DocumentRoot is empty"); //$NON-NLS-1$

                schema = docroot.eContents().get(0);

            } catch (StackOverflowError e) {
                throw new WorkflowInterruptedException(
                        "Possible cyclic dependency detected: " + e); //$NON-NLS-1$
            } catch (Throwable e) {
                throw new WorkflowInterruptedException(
                        "Error loading XML file XML-File:" + xmlUri + ": " + e); //$NON-NLS-1$ //$NON-NLS-2$
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        throw new WorkflowInterruptedException(
                                "Error closing input stream: " + e); //$NON-NLS-1$
                    }
                }
            }
        }
        return schema;
    }
}
