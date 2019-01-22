/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.bom.wsdltransform.api;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.wst.validation.ValidationFramework;
import org.eclipse.wst.validation.ValidationResults;
import org.eclipse.wst.validation.ValidatorMessage;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Import;
import org.eclipse.wst.wsdl.util.WSDLResourceImpl;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.util.XSDResourceImpl;
import org.w3c.dom.Element;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.bom.wsdltransform.Activator;
import com.tibco.xpd.bom.wsdltransform.exports.BOM2WSDLTransformer;
import com.tibco.xpd.bom.wsdltransform.imports.WSDL2BOMTransformer;
import com.tibco.xpd.bom.wsdltransform.internal.Messages;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.api.XSDSchemaResolver;
import com.tibco.xpd.bom.xsdtransform.utils.NamespaceURIToJavaPackageMapper;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.DependencySorter;
import com.tibco.xpd.resources.util.DependencySorter.Arc;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;
import com.tibco.xpd.wsdl.ui.WsdlWorkingCopy;

/**
 * This class provides set of utilities used in accordance with WSDL to BOM
 * transformations.
 * 
 * 
 * @author rsomayaj
 * @since 3.3 (30 Mar 2010)
 */
public class WSDLTransformUtil {

    /***
     * this validation marker type will be used to remove the validation problem
     * markers that must have been already added as part of previous valdiation
     * run
     */
    private static final String XSD_VALIDATION_MARKER_TYPE =
            "org.eclipse.xsd.diagnostic"; //$NON-NLS-1$

    /**
     * Check if BDS support is enabled for this WSDL.
     * 
     * @param wsdlFile
     *            the WSDL to check
     * @return <code>false</code> if the TIBCO extension "bdsSupport" attribute
     *         is set to false in the wsdl file, <code>true</code> otherwise.
     */
    public static boolean isBDSSupportEnabled(IFile wsdlFile) {
        if (wsdlFile != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(wsdlFile);
            if (wc != null && wc.getRootElement() instanceof Definition) {
                Element element =
                        ((Definition) wc.getRootElement()).getElement();

                if (element != null
                        && element
                                .hasAttribute(Activator.TIBEX_BDSSUPPORT_ATTR)) {
                    return Boolean.parseBoolean(element
                            .getAttribute(Activator.TIBEX_BDSSUPPORT_ATTR));
                }
            }
        }
        return true;
    }

    /**
     * Utility mainly used by operations which utilize WSDL imported BOMs.
     * 
     * @param bomFile
     * @param typeName
     * @return BOM Object for the given name in the given file. If it can't find
     *         the classifier in the given file, then it looks at all the
     *         classes which have "isAnon" true
     * @since 3.3
     */
    public static Classifier getBOMObject(IFile bomFile, String typeName) {
        Classifier cls = null;
        if (bomFile != null && bomFile.exists()) {
            WorkingCopy bomWC =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
            EObject bomElement =
                    findElementInPackage(bomWC.getRootElement(), typeName);
            if (bomElement instanceof org.eclipse.uml2.uml.Class) {
                cls = (org.eclipse.uml2.uml.Class) bomElement;
                return cls;
            }

            // If the class was not found, need to iterate through all the
            // classes in the BOM, find the ones those are
            // anonymous(xsdIsAnonType) and match the (xsdName)
            return findAnonymousClassNameXsdName(bomWC.getRootElement(),
                    typeName);
        }
        return null;
    }

    /**
     * @param rootElement
     * @param typeName
     */
    private static Classifier findAnonymousClassNameXsdName(
            EObject rootElement, String typeName) {
        if (rootElement instanceof org.eclipse.uml2.uml.Package) {
            org.eclipse.uml2.uml.Package umlPackage =
                    (org.eclipse.uml2.uml.Package) rootElement;
            if (!umlPackage.getPackagedElements().isEmpty() && typeName != null) {
                for (Iterator<PackageableElement> iter =
                        umlPackage.getPackagedElements().iterator(); iter
                        .hasNext();) {
                    PackageableElement obj = iter.next();
                    if (obj instanceof Class) {
                        Class cls = (Class) obj;
                        Stereotype xsdBasedClassSteroType =
                                getAppliedStereotype(cls,
                                        XsdStereotypeUtils.XSD_BASED_CLASS);
                        if (xsdBasedClassSteroType != null) {
                            Object value =
                                    cls.getValue(xsdBasedClassSteroType,
                                            XsdStereotypeUtils.XSD_PROPERTY_IS_ANON_TYPE);
                            if (Boolean.TRUE.equals(value)) {
                                Object xsdPropertyName =
                                        cls.getValue(xsdBasedClassSteroType,
                                                XsdStereotypeUtils.XSD_PROPERTY_NAME);
                                if (typeName.equals(xsdPropertyName)) {
                                    return cls;
                                }
                            }
                        }

                    }
                }
            }
        }
        return null;
    }

    /**
     * Applied stereotype method on the Classifier wasn't returning expected
     * values.
     * 
     * @param cls
     * @param stereotypeName
     * @return
     */
    private static Stereotype getAppliedStereotype(Classifier cls,
            String stereotypeName) {

        List<Stereotype> appliedStereotypes = cls.getAppliedStereotypes();
        for (Stereotype stereotype : appliedStereotypes) {
            if (stereotype.getName().equals(stereotypeName)) {
                return stereotype;
            }
        }
        return null;
    }

    /**
     * @param rootElement
     * @param typeName
     * @return
     */
    private static EObject findElementInPackage(EObject rootElement,
            String typeName) {
        if (rootElement instanceof Package && typeName != null) {
            Package umlPackage = (Package) rootElement;
            if (!umlPackage.getPackagedElements().isEmpty() && typeName != null) {
                for (Iterator<PackageableElement> iter =
                        umlPackage.getPackagedElements().iterator(); iter
                        .hasNext();) {
                    PackageableElement elem = iter.next();
                    if (typeName.equals(iter.next().getName())) {
                        return elem;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get the file names of the BOMs that will be generated from the given WSDL
     * or XSD.
     * 
     * @param wsdlOrXsdFile
     *            the WSDL or XSD file to get the output BOM names of.
     * @return collection of bom file names, empty if the WSDL/XSD file will not
     *         produce any models or the WSDL/XSD cannot be read.
     */
    public static Collection<String> getOutputBOMNames(IFile wsdlOrXsdFile) {

        Set<String> names = new HashSet<String>();
        Resource resource = null;

        if (wsdlOrXsdFile != null) {

            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(wsdlOrXsdFile);
            if (wc != null) {

                EObject element = wc.getRootElement();
                if (element != null) {

                    resource = element.eResource();
                }
            }
        }

        Set<String> namespaces = new HashSet<String>();

        if (resource instanceof WSDLResourceImpl) {

            Definition definition =
                    ((WSDLResourceImpl) resource).getDefinition();
            if (definition != null) {

                /* Get definition namespace */
                String tns = definition.getTargetNamespace();
                if (tns != null) {

                    namespaces.add(tns);
                }

                /*
                 * Wsdl can import wsdl or xsd. Collect namespaces of all wsdl
                 * imports wsdl and wsdl imports xsd schemas
                 */
                Set<XSDSchema> referencedSchemas =
                        getReferencedSchemas(definition);
                for (XSDSchema xsdSchema : referencedSchemas) {
                    if (null != xsdSchema.getTargetNamespace()) {
                        namespaces.add(xsdSchema.getTargetNamespace());
                    }
                }

            }
        } else if (resource instanceof XSDResourceImpl) {

            XSDSchema schema = ((XSDResourceImpl) resource).getSchema();
            XSDSchemaResolver schemaResolver = XSDSchemaResolver.getInstance();
            Set<XSDSchema> referencedSchemas =
                    schemaResolver.getReferencedSchemas(schema);

            for (XSDSchema xsdSchema : referencedSchemas) {

                namespaces.add(xsdSchema.getTargetNamespace());
            }

        }
        /*
         * for each target namespace work out the resulting model name and add
         * to list
         */
        for (String tns : namespaces) {
            if (tns != null) {
                String modelName =
                        NamespaceURIToJavaPackageMapper
                                .getJavaPackageNameFromNamespaceURI(wsdlOrXsdFile
                                        .getProject(),
                                        tns);
                if (modelName != null) {
                    names.add(modelName.concat("." //$NON-NLS-1$
                            + BOMResourcesPlugin.BOM_FILE_EXTENSION));
                }
            }
        }

        return names;
    }

    /**
     * Run the WST validation on the given WSDL Resource. Note that this does
     * not run our schema validation on any schemas used by this WSDL - for that
     * use {@link #validate(Resource)}.
     * 
     * @param wsdlFile
     *            WSDL File
     * @return list of the result status. Empty list if no problems found.
     */
    public static List<IStatus> validateWSDL(IFile wsdlFile) {
        List<IStatus> statusArr = new ArrayList<IStatus>();

        if (wsdlFile != null) {
            /*
             * XPD-3660: Try to get the working copy of this WSDL. If successful
             * then call the validate method in the working copy (this ignore
             * some WS-I rules). If no working copy is found then fall-back to
             * the previous method of validating the WSDL.
             */
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(wsdlFile);
            if (wc instanceof WsdlWorkingCopy) {
                /*
                 * Check if there is cyclic dependencies between WSDLs (WSDL
                 * imports with the same target namespace as the source) so
                 * return error here as the WST validation will otherwise go
                 * into a stack overflow.
                 */
                IStatus result = CyclicWsdlImportValidator.validate(wsdlFile);

                if (!result.isOK()) {
                    statusArr.add(result);
                    return statusArr;
                }

                result = ((WsdlWorkingCopy) wc).forceValidate();

                // Only report errors
                if (result != null && result.getSeverity() == IStatus.ERROR) {
                    if (result instanceof MultiStatus) {
                        for (IStatus status : result.getChildren()) {
                            if (status.getSeverity() == IStatus.ERROR) {
                                statusArr.add(status);
                            }
                        }
                    } else {
                        statusArr.add(result);
                    }
                }

                /*
                 * XPD-4556: if we get hold of the wsdl working copy to validate
                 * the wsdl, then we also are supposed to validate all the
                 * xsd(s) related to this wsdl
                 */

                IPreferenceStore preferenceStore =
                        BOMValidatorActivator.getDefault().getPreferenceStore();
                boolean validateXSD =
                        preferenceStore
                                .getBoolean(BOMValidationUtil.VALIDATE_XSD);

                final Set<IResource> xsdResSet = new HashSet<IResource>();
                /* recursively get all imported/included xsd(s) for the wsdl */
                getAllReferencedWsdlAndXsdForWsdl(wsdlFile, xsdResSet);

                if (validateXSD) {
                    /*
                     * iterate thru the set of xsd(s) and validate each one of
                     * them
                     */
                    for (IResource xsdRes : xsdResSet) {
                        IStatus validateXsdResult = validateXsd(xsdRes);
                        if (null != validateXsdResult
                                && validateXsdResult.getSeverity() == IStatus.ERROR) {
                            statusArr.add(validateXsdResult);
                        }
                    }
                } else {
                    /*
                     * if the user decides not to have any validation on xsd(s),
                     * then we must remove the problem marker that must already
                     * have been added as part of previous validation. (The
                     * problem markers wont get removed automatically as they
                     * have been added by eclipse validator, so we get the
                     * markers list for the type we are interested in and
                     * manually delete those markers)
                     */

                    WorkspaceJob job =
                            new WorkspaceJob(
                                    Messages.XSDValidation_removeMarkers_shortdesc) {

                                @Override
                                public IStatus runInWorkspace(
                                        IProgressMonitor monitor)
                                        throws CoreException {
                                    for (IResource xsdRes : xsdResSet) {
                                        IMarker[] markers =
                                                xsdRes.findMarkers(XSD_VALIDATION_MARKER_TYPE,
                                                        false,
                                                        IResource.DEPTH_ZERO);

                                        for (IMarker iMarker : markers) {
                                            iMarker.delete();
                                        }
                                    }
                                    return Status.OK_STATUS;
                                }
                            };
                    job.setRule(wsdlFile.getProject());
                    job.schedule();
                }
            } else {
                try {
                    ValidationResults validateResults = validateFile(wsdlFile);

                    for (ValidatorMessage validatorMessage : validateResults
                            .getMessages()) {
                        int attribute =
                                validatorMessage.getAttribute(IMarker.SEVERITY,
                                        IMarker.SEVERITY_WARNING);
                        if (attribute == IMarker.SEVERITY_ERROR) {
                            String errorMsg =
                                    validatorMessage
                                            .getAttribute(IMarker.MESSAGE, ""); //$NON-NLS-1$
                            if (errorMsg.trim().length() > 0) {
                                statusArr.add(new Status(IStatus.ERROR,
                                        Activator.PLUGIN_ID, errorMsg));
                            }
                        }
                    }
                } catch (CoreException e) {
                    statusArr.add(new Status(IStatus.ERROR,
                            Activator.PLUGIN_ID, e.getLocalizedMessage()));
                }
            }
        }

        return statusArr;
    }

    /***
     * call the validation framework's validate on each xsd
     * 
     * @param xsdRes
     * @return
     */
    public static IStatus validateXsd(IResource xsdRes) {

        IStatus result = Status.OK_STATUS;

        try {

            ValidationResults validateResults = validateFile((IFile) xsdRes);
            for (ValidatorMessage validatorMessage : validateResults
                    .getMessages()) {
                int attribute =
                        validatorMessage.getAttribute(IMarker.SEVERITY,
                                IMarker.SEVERITY_WARNING);
                if (attribute == IMarker.SEVERITY_ERROR) {
                    String errorMsg =
                            validatorMessage.getAttribute(IMarker.MESSAGE, ""); //$NON-NLS-1$
                    if (errorMsg.trim().length() > 0) {
                        result =
                                new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                                        errorMsg);
                    }
                }
            }
        } catch (CoreException e) {
            result =
                    new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                            e.getLocalizedMessage());
        }

        return result;
    }

    /**
     * Validate and redirect std error to eclipse log, so it's not sprayed
     * around in the cmd line output.
     * 
     * See also: XPD-6130
     * 
     * @param file
     *            the file to validate.
     * @return results of the validation.
     * @throws CoreException
     */
    private static ValidationResults validateFile(IFile file)
            throws CoreException {
        PrintStream oldErr = System.err;
        /*
         * Only allow logging of messages not starting with "[Fatal Error...."
         * unless studio is running in debug mode.
         */
        LoggingOutputStream.Predicate notContentNotAllowedInProlog =
                new LoggingOutputStream.Predicate() {
                    private boolean inDebugMode = Platform.inDebugMode();

                    public boolean apply(String el) {
                        return inDebugMode
                                || (el != null && !el
                                        .startsWith("[Fatal Error] :1:1: Content is not allowed in prolog.")); //$NON-NLS-1$
                    };
                };
        LoggingOutputStream los =
                new LoggingOutputStream(oldErr,
                        "Validation Error Stream from: " //$NON-NLS-1$
                                + file.getFullPath().toString(),
                        notContentNotAllowedInProlog);
        System.setErr(new PrintStream(los, true));
        try {
            return ValidationFramework.getDefault().validate(file,
                    new NullProgressMonitor());
        } finally {
            try {
                los.flush();
            } catch (IOException e) {
                Activator.getDefault().getLogger().error(e);
            }
            System.setErr(oldErr);
        }
    }

    /**
     * 
     * recursively get all the xsd(s) for the given wsdl
     * 
     * @param wsdlFile
     * @param xsdResSet
     *            (contains only xsd resources and does not have the wsdl that
     *            it starts with)
     * @return
     */
    public static void getAllReferencedWsdlAndXsdForWsdl(IFile wsdlFile,
            Set<IResource> xsdResSet) {

        Collection<XSDSchema> referencedSchemas =
                WSDLTransformUtil.getReferencedSchemas(wsdlFile);

        /*
         * XPD-6062 Used to go thru all schemas recursive re-looking at imports
         * and trying to build all schemaLcoation URIs relative to main WSDL
         * file (which doesn't work if you have multiple nested schemas and
         * folders, each schemaLcoation in that case is relative to the ref'iong
         * XSD not original WSDL
         * 
         * As getReferencedSchemas() above is already fully recursive then all
         * we need to do is add the file in which each of those schemas resides.
         * - Simples!
         */
        for (EObject eObject : referencedSchemas) {

            IFile file = WorkingCopyUtil.getFile(eObject);
            if (file != null) {
                xsdResSet.add(file);
            }
        }

    }

    /**
     * Run the WST validation on the given WSDL Resource. Note that this does
     * not run our schema validation on any schemas used by this WSDL - for that
     * use {@link #validate(Resource)}.
     * 
     * @param resource
     *            WSDL Resource
     * @return list of the result status. Empty list if no problems found.
     */
    public static List<IStatus> validateWSDL(Resource resource) {
        List<IStatus> statusArr = new ArrayList<IStatus>();
        if (resource != null) {
            try {
                IFile tempFile = WorkspaceSynchronizer.getFile(resource);
                if (tempFile == null) {
                    Activator
                            .getDefault()
                            .getLogger()
                            .info(Messages.XtendTransformer_cannot_validate_external_wsdl_message);
                    statusArr
                            .add(new Status(
                                    IStatus.WARNING,
                                    Activator.PLUGIN_ID,
                                    Messages.XtendTransformer_cannot_validate_external_wsdl_message));
                } else {
                    tempFile.refreshLocal(IResource.DEPTH_ZERO,
                            new NullProgressMonitor());

                    statusArr = validateWSDL(tempFile);
                }
            } catch (CoreException e) {
                statusArr.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e
                        .getLocalizedMessage()));
            }
        }
        return statusArr;
    }

    /**
     * Validates against WSDL schema and then against the supported BOM
     * constructs for a particular WSDL resource.
     * <p>
     * This will also validate all schemas used by the WSDL.
     * </p>
     * 
     * @param resource
     * @return array of IStatus suitable to display to the user.
     */
    public static List<IStatus> validate(Resource resource) {
        return WSDL2BOMTransformer.validate(resource);
    }

    /**
     * Transforms the WSDL file to a BOM file
     * 
     * @param source
     * @param bomFilePath
     * @param monitor
     * @return
     */
    public static List<IStatus> transformWSDLtoBOM(File source,
            IPath bomFilePath, IProgressMonitor monitor) {
        return new WSDL2BOMTransformer()
                .transform(source, bomFilePath, monitor);
    }

    public static List<IStatus> transformWSDLtoBOM(File source,
            IPath bomFilePath, IProgressMonitor monitor, boolean doMerge) {
        WSDL2BOMTransformer transformer = new WSDL2BOMTransformer(doMerge);
        return transformer.transform(source, bomFilePath, monitor);
    }

    public static List<IStatus> transformWSDLtoBOM(File source,
            IPath bomFilePath, IProgressMonitor monitor, boolean doMerge,
            boolean doOverwrite) {
        WSDL2BOMTransformer transformer =
                new WSDL2BOMTransformer(doMerge, doOverwrite);
        return transformer.transform(source, bomFilePath, monitor);
    }

    /**
     * @param source
     * @param wsdlFilePath
     * @param monitor
     * @return
     */
    public static List<IStatus> transformBOMtoWSDL(IFile source,
            IPath wsdlFilePath, IProgressMonitor monitor) {
        return new BOM2WSDLTransformer().transform(source,
                wsdlFilePath,
                monitor);
    }

    /**
     * Studio provides the option to generate WSDLs for Service tasks, Send One
     * ways request activities and API activities. Utility identifies whether
     * the given WSDL is one that is generated by Studio.
     * 
     * The usage of this method here is to see that the WSDL is not validated
     * for WSDL to BOM validation rules.
     * 
     * @param defn
     * @return true if the given definition has the attribute which marks the
     *         WSDL as generated; false otherwise
     */
    public static boolean isGeneratedWsdl(Definition defn) {
        if (defn != null && defn.getElement() != null) {
            String svcTaskAttrib =
                    defn.getElement()
                            .getAttribute(WsdlUIPlugin.TIBEX_SERVICE_TASK);

            /*
             * Want to avoid warning for Service tasks that have been generated
             * from the XPDL ignore if the tibex:ServiceTask is set.
             */
            if (svcTaskAttrib == null || svcTaskAttrib.length() == 0) {
                String tibexXpdlAttribVal =
                        defn.getElement()
                                .getAttribute(WsdlUIPlugin.TIBEX_XPDL_PLACEHOLDER);

                if (tibexXpdlAttribVal != null
                        && tibexXpdlAttribVal.length() > 0) {
                    return true;
                }
            } else {
                return true;
            }
        }

        return false;
    }

    /**
     * @param resource
     * @return All schema imports (wsdl/xsd) and includes (xsd) for a given
     *         resource
     */
    public static Collection<XSDSchema> getReferencedSchemas(IResource resource) {

        Set<XSDSchema> schemaImports = new HashSet<XSDSchema>();
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(resource);

        if (wc != null) {
            EObject root = wc.getRootElement();
            XSDSchemaResolver schemaResolver = XSDSchemaResolver.getInstance();

            if (root instanceof Definition) {
                schemaImports.addAll(schemaResolver
                        .getReferencedSchemas((Definition) root));
            }
        }

        return schemaImports;
    }

    /**
     * @param wsdlDefOrSchema
     * @return All schema imports (wsdl/xsd) and includes (xsd) for a given wsdl
     *         definition or xsd schema
     */
    public static Set<XSDSchema> getReferencedSchemas(EObject wsdlDefOrSchema) {

        Set<XSDSchema> schemaImports = new HashSet<XSDSchema>();

        if (wsdlDefOrSchema instanceof Definition
                || wsdlDefOrSchema instanceof XSDSchema) {

            XSDSchemaResolver schemaResolver = XSDSchemaResolver.getInstance();
            schemaImports.addAll(schemaResolver
                    .getReferencedSchemas(wsdlDefOrSchema));
        }

        return schemaImports;
    }

    /**
     * @param schemaURI
     *            Import/included URI of schema
     * @return resource (if it exists) corresponding to passed in URI
     */
    public static IResource getResource(URI schemaURI) {

        if (schemaURI != null) {
            IResource resource = null;
            String platformString = schemaURI.toPlatformString(true);

            if (platformString != null) {
                resource =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getFile(new Path(platformString));
            }

            if (resource != null && resource.exists()) {
                return resource;
            }
        }

        return null;
    }

    /**
     * Validates cyclic imports in WSDLs (only where the imported WSDL has the
     * same namespace).
     * 
     * @author njpatel
     */
    private static class CyclicWsdlImportValidator {

        private final IFile wsdlFile;

        /**
         * Validates cyclic imports in WSDLs(only where the imported WSDL has
         * the same namespace).
         */
        public CyclicWsdlImportValidator(IFile wsdlFile) {
            this.wsdlFile = wsdlFile;
        }

        /**
         * Validate the given WSDL for cyclic (WSDL) imports (for WSDLs with
         * same namespace).
         * 
         * @param wsdlFile
         * @return OK status if no cyclic reference found, ERROR otherwise.
         */
        public static IStatus validate(IFile wsdlFile) {
            return new CyclicWsdlImportValidator(wsdlFile).validate();
        }

        /**
         * Validate the WSDL for cyclic WSDL import (of same namespace).
         * 
         * @return
         */
        public IStatus validate() {
            Set<IFile> wsdlFileSet = new HashSet<IFile>();

            List<Arc<WsdlImport>> arcs = new ArrayList<Arc<WsdlImport>>();
            prepareDependencyArcs(arcs, wsdlFile, wsdlFileSet);

            if (arcs.size() > 1) {

                /*
                 * call getOrderedList on DependencySorter. if throws any
                 * exception then that means it has got cyclic dependency
                 */
                DependencySorter<WsdlImport> dependencySorter =
                        new DependencySorter<WsdlImport>(arcs, null);
                try {
                    dependencySorter.getOrderedList();
                } catch (IllegalArgumentException e) {
                    return new Status(
                            IStatus.ERROR,
                            Activator.PLUGIN_ID,
                            Messages.WSDLTransformUtil_wsdlHasCyclicImport_error_longdesc);
                }
            }

            return Status.OK_STATUS;
        }

        /**
         * Prepare the dependency arcs.
         * 
         * @param arcs
         * @param wsdlFile
         * @param alreadyVisitedWsdls
         */
        private void prepareDependencyArcs(List<Arc<WsdlImport>> arcs,
                IFile wsdlFile, Set<IFile> alreadyVisitedWsdls) {

            if (wsdlFile != null && !alreadyVisitedWsdls.contains(wsdlFile)) {
                alreadyVisitedWsdls.add(wsdlFile);

                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(wsdlFile);
                if (wc != null) {
                    EObject root = wc.getRootElement();

                    if (root instanceof Definition) {

                        String wsdlTargetNamespace =
                                ((Definition) root).getTargetNamespace();
                        // WSDL: Imports
                        EList<?> eImports = ((Definition) root).getEImports();
                        WsdlImport fromArc =
                                new WsdlImport(wsdlFile, wsdlTargetNamespace);

                        for (Object impt : eImports) {
                            if (impt instanceof Import) {
                                Import import1 = (Import) impt;

                                // Resolve the import to the actual file
                                IFile importedWsdlFile =
                                        resolveImport(wsdlFile, import1);

                                if (importedWsdlFile != null) {
                                    // Get the actual namespace from the file
                                    // (in case the namespace in the import
                                    // statement is incorrect)
                                    String namespaceURI =
                                            getTargetNamespace(importedWsdlFile);
                                    if (namespaceURI == null) {
                                        namespaceURI =
                                                import1.getNamespaceURI();
                                    }

                                    /*
                                     * Only add arc if namespaces match, not
                                     * interested in differing namespaces
                                     */
                                    if (wsdlTargetNamespace
                                            .equals(namespaceURI)) {
                                        arcs.add(new Arc<WsdlImport>(fromArc,
                                                new WsdlImport(
                                                        importedWsdlFile,
                                                        namespaceURI)));
                                    }

                                    prepareDependencyArcs(arcs,
                                            importedWsdlFile,
                                            alreadyVisitedWsdls);
                                }
                            }
                        }
                    }
                }
            }
        }

        /**
         * Get the target namespace from the given WSDL.
         * 
         * @param wsdlFile
         * @return namespace or <code>null</code> if the model cannot be loaded.
         */
        private String getTargetNamespace(IFile wsdlFile) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(wsdlFile);
            if (wc != null && wc.getRootElement() instanceof Definition) {
                return ((Definition) wc.getRootElement()).getTargetNamespace();
            }
            return null;
        }

        /**
         * Resolve the WSDL import to the WSLD file in the workspace.
         * 
         * @param sourceWsdl
         * @param import1
         * @return resolved file, <code>null</code> if file not found.
         */
        private IFile resolveImport(IFile sourceWsdl, Import import1) {
            URI wsdlFileUri =
                    URI.createPlatformResourceURI(sourceWsdl.getFullPath()
                            .toString(), true);
            URI importFileUri = URI.createURI(import1.getLocationURI());
            URI resolve = importFileUri.resolve(wsdlFileUri);
            return (IFile) WSDLTransformUtil.getResource(resolve);
        }

        /**
         * Used in the dependency sorter arc to identify cyclic dependencies
         * between WSDLs with the same namespace.
         * 
         */
        private class WsdlImport {
            private final IFile importFile;

            private final String namespace;

            /**
             * 
             */
            public WsdlImport(IFile importFile, String namespace) {
                this.importFile = importFile;
                this.namespace = namespace;
            }

            /**
             * @see java.lang.Object#hashCode()
             * 
             * @return
             */
            @Override
            public int hashCode() {
                return importFile.hashCode() + namespace.hashCode();
            }

            /**
             * @see java.lang.Object#equals(java.lang.Object)
             * 
             * @param other
             * @return
             */
            @Override
            public boolean equals(Object other) {
                if (other == this) {
                    return true;
                }

                if (other instanceof WsdlImport) {
                    return importFile.equals(((WsdlImport) other).importFile)
                            && namespace.equals(((WsdlImport) other).namespace);
                }

                return false;
            }

            /**
             * @see java.lang.Object#toString()
             * 
             * @return
             */
            @Override
            public String toString() {
                return importFile.getFullPath() + "[" + namespace + "]"; //$NON-NLS-1$//$NON-NLS-2$
            }
        }
    }

    /**
     * The output stream to log standard stream (out or err) to eclipse log.
     * 
     * @author jarciuch
     * @since 11 Jul 2014
     */
    private static class LoggingOutputStream extends ByteArrayOutputStream {
        private String lineSeparator;

        private PrintStream ps;

        private String preamble;

        private Predicate predicate;

        public static interface Predicate {
            boolean apply(String element);
        }

        public LoggingOutputStream(PrintStream ps, String preamble,
                Predicate predicate) {
            super();
            this.ps = ps;
            this.preamble = preamble;
            this.predicate = predicate;
            lineSeparator = System.getProperty("line.separator"); //$NON-NLS-1$
        }

        /**
         * On flush() write the existing content of the OutputStream to the
         * logger as a log record.
         * 
         * @throws java.io.IOException
         *             in case of error
         */
        @Override
        public void flush() throws IOException {
            synchronized (this) {
                String record = null;
                super.flush();
                record = this.toString();
                super.reset();
                if (record.length() == 0 || record.equals(lineSeparator)) {
                    // avoid empty records
                    return;
                }
                if (predicate != null && !predicate.apply(record)) {
                    // Only continue (with logging) when predicate applies.
                    return;
                }
                String message = preamble + ": \n" + record; //$NON-NLS-1$
                ps.print(message);
            }
        }
    }
}
