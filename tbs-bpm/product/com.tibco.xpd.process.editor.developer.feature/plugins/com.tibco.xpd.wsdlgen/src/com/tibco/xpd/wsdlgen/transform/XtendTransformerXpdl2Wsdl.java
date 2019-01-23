/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.wsdlgen.transform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.wst.wsdl.WSDLFactory;
import org.eclipse.wst.wsdl.XSDSchemaExtensibilityElement;
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.expression.ExceptionHandler;
import org.openarchitectureware.expression.ExecutionContext;
import org.openarchitectureware.expression.ExecutionContextImpl;
import org.openarchitectureware.expression.TypeSystemImpl;
import org.openarchitectureware.expression.ast.SyntaxElement;
import org.openarchitectureware.type.emf.EmfMetaModel;
import org.openarchitectureware.uml2.profile.ProfileMetaModel;
import org.openarchitectureware.workflow.issues.IssuesImpl;
import org.openarchitectureware.xsd.XSDMetaModel;
import org.openarchitectureware.xtend.XtendFacade;
import org.osgi.framework.Bundle;
import org.w3c.dom.Document;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.gmf.XpdEditingDomainFactory;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;
import com.tibco.xpd.wsdl.ui.WsdlWorkingCopy;
import com.tibco.xpd.wsdlgen.Messages;
import com.tibco.xpd.wsdlgen.WsdlGenBuilderTransformer;
import com.tibco.xpd.wsdlgen.WsdlgenPlugin;
import com.tibco.xpd.wsdlgen.transform.template.TransformHelper;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.SoapBindingStyle;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Transform XPDL Model to multiple XPDL models with each process in turn
 * populated. This transformation accounts for changes through Ecore.
 * 
 * @author rsomayaj
 */
public class XtendTransformerXpdl2Wsdl {

    private static final String NS_URI_CHECKSUM_DELIMITER = "~"; //$NON-NLS-1$

    private static final String SERVICETASKWSDLCREATION =
            "createPortTypeForServiceTask"; //$NON-NLS-1$

    private static final String UPDATESCHEMA_EXPRESSION = "updateSchema"; //$NON-NLS-1$

    private static final String XPDL2WSDLTRANSFORM =
            "com::tibco::xpd::wsdlgen::transform::template::Xpdl2Wsdl"; //$NON-NLS-1$

    private static final String TRANSFORM_EXPRESSION = "transformXpdlToWsdl"; //$NON-NLS-1$

    private IssuesImpl issues = null;

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    public IssuesImpl getIssues() {
        return issues;
    }

    private final String xpdl2MetaModelEcoreURL = "/model/xpdl2.ecore"; //$NON-NLS-1$

    private final String xpdExtensionEcoreURL = "/model/XpdExtension.ecore"; //$NON-NLS-1$

    private final static String WSDL_PLUGIN = "org.eclipse.wst.wsdl"; //$NON-NLS-1$

    private final static String UML_PLUGIN = "org.eclipse.uml2.uml"; //$NON-NLS-1$

    private final static String XSD_PLUGIN = "org.eclipse.xsd"; //$NON-NLS-1$

    private final String wsdlEcoreUrl = "/model/WSDL.ecore"; //$NON-NLS-1$

    private final String umlEcoreUrl = "/model/UML.ecore"; //$NON-NLS-1$

    private final String xsdEcoreUrl = "/model/XSD.ecore"; //$NON-NLS-1$

    private static final String XPDL_PLUGIN_ID = "com.tibco.xpd.xpdl2"; //$NON-NLS-1$

    protected static final String XML_SCHEMA_PATH = "model/XMLSchema.xsd"; //$NON-NLS-1$

    protected static final String WSDL_SCHEMA_PATH = "model/WSDLSchema.xsd"; //$NON-NLS-1$

    protected static final String XSD_METAMODEL_ID = "xsdmm"; //$NON-NLS-1$

    protected static final String WSDL_METAMODEL_ID = "wsdlmm"; //$NON-NLS-1$

    protected static final String PROC_METAMODEL_ID = "procmm"; //$NON-NLS-1$

    private static final String REMOVE_SCHEMAS_EXPRESSION =
            "removeSchemasFromWSDL"; //$NON-NLS-1$

    //    private static final String PROCXSD_SCHEMA_PATH = "model/ProcXSD.xsd"; //$NON-NLS-1$

    protected static XSDMetaModel xsdSchemaMetaModel = null;

    protected static ProfileMetaModel profileMetaModel = null;

    /**
     * Execution context for the transformation with a runtime exception handler
     * that reports errors to the log. (XPD-5121)
     * 
     * @author njpatel
     * @since 3.6.0
     */
    private class Xpdl2WsdlExecutionContext extends ExecutionContextImpl {

        private final Set<String> errors;

        private final IFile xpdlFile;

        /**
         * @param xpdlFile
         * 
         */
        public Xpdl2WsdlExecutionContext(final IFile xpdlFile) {
            super(new TypeSystemImpl());
            this.xpdlFile = xpdlFile;

            errors = new LinkedHashSet<String>();

            exceptionHandler = new ExceptionHandler() {

                @Override
                public void handleRuntimeException(RuntimeException ex,
                        SyntaxElement element, ExecutionContext ctx,
                        Map<String, Object> additionalContextInfo) {

                    ex.printStackTrace();

                    /*
                     * Collect all the error messages before reporting after the
                     * transformation is complete. This will ensure we don't
                     * report duplicate messages.
                     */
                    errors.add(ex.getLocalizedMessage());

                }
            };
        }

        /**
         * Check if this execution has errors.
         * 
         * @return
         */
        public boolean hasErrors() {
            return errors != null && !errors.isEmpty();
        }

        /**
         * Report any errors during the transformation in the error log. If
         * there were no errors then this method will do nothing.
         */
        private void reportErrors() {
            if (!errors.isEmpty()) {
                IStatus[] statusList = new IStatus[errors.size()];

                int x = 0;
                for (String error : errors) {
                    statusList[x++] =
                            new Status(IStatus.ERROR, WsdlgenPlugin.PLUGIN_ID,
                                    error);
                }

                LOG.log(new MultiStatus(
                        WsdlgenPlugin.PLUGIN_ID,
                        0,
                        statusList,
                        String.format(Messages.XtendTransformerXpdl2Wsdl_unableToGenerateWsdl_error_longdesc,
                                xpdlFile.getFullPath().toString()), null));
            }
        }
    }

    public boolean transformXpdlToWsdl(IFile xpdlFile, final IFile wsdlFile,
            final IFile bomFile) throws Exception {
        TransactionalEditingDomain imMemoryEditDomain =
                XpdEditingDomainFactory.INSTANCE.createEditingDomain();
        /*
         * XPD-5121: Register our own execution context so we can control
         * exception handling.
         */
        Xpdl2WsdlExecutionContext ctx = new Xpdl2WsdlExecutionContext(xpdlFile);
        try {
            final XtendFacade f = XtendFacade.create(ctx, XPDL2WSDLTRANSFORM);
            configureXpdlToWsdlWorkflow(f);
            runXpdlToWsdlWorkflow(f,
                    ctx,
                    imMemoryEditDomain,
                    xpdlFile,
                    wsdlFile,
                    bomFile);
        } finally {
            /*
             * XPD-5121: Report any errors (to the log) that occurred during the
             * transformation.
             */
            ctx.reportErrors();

            imMemoryEditDomain.dispose();
        }
        return true;
    }

    /**
     * Called while creating a WSDL for a web-service task.
     * 
     * @param definition
     * @param serviceTaskActivity
     * @param bindingType
     * @return
     */
    public boolean transformServiceTaskToWsdl(Definition definition,
            Activity serviceTaskActivity, SoapBindingStyle bindingType) {
        final XtendFacade f = XtendFacade.create(XPDL2WSDLTRANSFORM);
        configureXpdlToWsdlWorkflow(f);
        runServiceTaskToWsdlWorkflow(f,
                definition,
                serviceTaskActivity,
                bindingType);
        return true;
    }

    /**
     * @param f
     * @param definition
     * @param serviceTaskActivity
     * @param bindingType
     */
    private void runServiceTaskToWsdlWorkflow(XtendFacade f,
            Definition definition, Activity serviceTaskActivity,
            SoapBindingStyle bindingType) {
        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Transaction.OPTION_UNPROTECTED, Boolean.TRUE);
        Transaction transaction = null;
        try {
            transaction =
                    ((InternalTransactionalEditingDomain) ed)
                            .startTransaction(false, attrs);

            convertServiceTaskToWsdl(f,
                    definition,
                    serviceTaskActivity,
                    bindingType);
        } catch (InterruptedException e1) {
            LOG.error(e1);
        } finally {
            if (transaction != null) {
                try {
                    transaction.commit();
                } catch (RollbackException e) {
                    LOG.error(e);
                }
            }
        }

        try {
            Resource resource = definition.eResource();
            if (resource != null) {
                resource.save(Collections.emptyMap());
            } else {
                LOG.error("Cannot Save Wsdl file after transformation"); //$NON-NLS-1$
            }
        } catch (IOException exception) {
            LOG.error(exception,
                    Messages.XtendTransformerXpdl2Wsdl_ErrSave_shortdesc);
        }

    }

    /**
     * Sid XPD-2878 - We no longer create a new WSDL file (or update existing
     * one) with empty Definition element in the calling
     * {@link WsdlGenBuilderTransformer}. This was causing all sorts of
     * problems.
     * 
     * Prior to XPD-2878 we used to recreate the generated WSDL as blank before
     * transforming it in {@link WsdlGenBuilderTransformer}. This was done by
     * replacing the current Definition element in the WSDL's emf Resource with
     * a new one and saving the WSDL file BEHIND THE WSDL working copy's back.
     * 
     * This meant that the original Definition element became orphaned from the
     * emf resource set (but working copy did not know this). Therefore when
     * working copy was requested to reload and it tried to do a cleanup() this
     * WOULD NOT UNLOAD the resource because old-Defintion.eResource() would
     * return null because the definition had been orphaned from resource set.
     * 
     * Once you get into this kind of situation then VERY often, when attempting
     * to reload the new WSDL definition into resource set, EMF would say
     * "I've already got it so no need to do anything" - however, (timing
     * dependent) the one it already has could be the empty one that used to be
     * created here by createWSDLFile().
     * 
     * So now this xpdl2WsdlTransformer transforms into a new wsdl Definition in
     * an in-memory emf resource in a separate editing domain and then when we
     * do a save on that resource, the original working copy will get an event
     * and reload the working copy via the usual channels.
     * 
     * @param f
     * @param ctx
     * @param inMemoryEditDomain
     * @param stack
     * @param xpdlFile
     * @param wsdlFile
     * @param bomFile
     * @param xsdSchemas
     * @throws IOException
     */
    private void runXpdlToWsdlWorkflow(final XtendFacade f,
            Xpdl2WsdlExecutionContext ctx,
            TransactionalEditingDomain inMemoryEditDomain, IFile xpdlFile,
            final IFile wsdlFile, final IFile bomFile) throws IOException {

        Error runtimeError = null;

        boolean success = false;

        final boolean generateChecksum = shouldGenerateChecksum();

        Transaction transaction = null;

        try {
            /*
             * XPD-2910 - If the WSDL working copy is already loaded, then we
             * will force a reload here (rather than wait for working copy to
             * reload it self when it gets the workspace-propertychanegd for the
             * file being saved.
             */
            boolean workingCopyAlreadyLoaded = true;
            if (wsdlFile.exists()) {
                WsdlWorkingCopy wsdlWorkingCopy =
                        (WsdlWorkingCopy) WorkingCopyUtil
                                .getWorkingCopy(wsdlFile);

                if (wsdlWorkingCopy.isLoaded()) {
                    workingCopyAlreadyLoaded = true;
                }
            }

            URI uri =
                    URI.createPlatformResourceURI(wsdlFile.getFullPath()
                            .toString(), true);

            Map<String, Object> attrs = new HashMap<String, Object>();
            attrs.put(Transaction.OPTION_UNPROTECTED, Boolean.TRUE);

            transaction =
                    ((InternalTransactionalEditingDomain) inMemoryEditDomain)
                            .startTransaction(false, attrs);

            /*
             * Create an IN MEMORY emf resource to do the transform into (this
             * shouldnt' touch the original file OR the copy in global editong
             * domain
             */
            Resource wsdlInMemoryResource =
                    inMemoryEditDomain.createResource(uri.toString());

            Definition newWsdlDefinition =
                    createInitialWsdlDefinition(xpdlFile, wsdlInMemoryResource);

            /*
             * Perform the transformation.
             */
            boolean deleteWsdlFile = false;
            try {
                executeXPDL2WSDLTransform(xpdlFile,
                        newWsdlDefinition,
                        bomFile,
                        f);

                deleteWsdlFile = ctx.hasErrors();

            } catch (Exception e) {
                deleteWsdlFile = true;
                throw new RuntimeException(
                        "Xpdl2Wsdl transformation failed for: " //$NON-NLS-1$
                                + xpdlFile.getFullPath().toString(), e);
            } finally {
                if (deleteWsdlFile) {
                    /*
                     * Set success to false and return here. This will cause the
                     * overall "finally" block to run which will commit the
                     * transaction and delete the WSDL due to unsuccessful
                     * transformation.
                     */
                    success = false;
                    return;
                }
            }

            /*
             * Order the elements in WSDL in a consistent manner so that if a
             * checksum is generated for this WSDL it will be consistent if
             * content hasn't changed (XPD-1982).
             */
            fixWsdlForChecksumGeneration(newWsdlDefinition);

            try {
                wsdlInMemoryResource.save(Collections.EMPTY_MAP);

                wsdlFile.setDerived(true);

                if (generateChecksum) {
                    /*
                     * Update the checksum in the target namespace
                     */

                    /*
                     * If there are no port types left in the definition then
                     * delete the WSDL.
                     */
                    if (!newWsdlDefinition.getEPortTypes().isEmpty()) {
                        addChecksumToNamespace(wsdlInMemoryResource);
                        wsdlInMemoryResource.save(Collections.EMPTY_MAP);
                    } else {
                        wsdlFile.delete(true, null);
                    }
                }

                /*
                 * XPD-2910 - If the WSDL working copy is already loaded, then
                 * we will force a reload here (rather than wait for working
                 * copy to reload it self when it gets the
                 * workspace-propertychanegd for the file being saved.
                 * 
                 * Originally we wanted to get rid of this (by saving via
                 * working-copy itself but that blows-away the dual javax/wst
                 * wsdl stuff under the covers (things like
                 * javax.wsdl.PartgetTypeName() return null when they should
                 * not).
                 * 
                 * But now, forcing reload should be much safer as this no
                 * longer synch's with UI thread.
                 */
                if (workingCopyAlreadyLoaded) {
                    if (wsdlFile.exists()) {
                        WsdlWorkingCopy wsdlWorkingCopy =
                                (WsdlWorkingCopy) WorkingCopyUtil
                                        .getWorkingCopy(wsdlFile);

                        if (wsdlWorkingCopy != null) {
                            wsdlWorkingCopy.reLoad();
                        }
                    }
                }

                /* Yey! */
                success = true;

            } catch (CoreException exception) {
                LOG.error(exception);
            } catch (IOException exception) {
                LOG.error(exception,
                        Messages.XtendTransformerXpdl2Wsdl_ErrSave_shortdesc);
            }

        } catch (Exception e1) {
            LOG.error(e1);
        } catch (Throwable e2) {
            LOG.error(e2);
            if (e2 instanceof Error) {
                runtimeError = (Error) e2;
            }
        }
        /*
         * XPD-6363: Previously the transaction commit and cleaning code was in
         * the finally clause. This was causing "Inconsistent stackmap frames"
         * if the code was compiled with a Java 7 compiler. The code without
         * finally should behave similarly, because additionally now Error is
         * also caught and re-thrown at the end of this method.
         */

        if (transaction != null) {
            try {
                transaction.commit();
            } catch (RollbackException e) {
                LOG.error(e);
            }
        }

        /*
         * If we did not succeeed then delete the target wsdl (rtaher than
         * leaving an old or empty one lying around.
         */
        if (!success) {
            if (wsdlFile.exists()) {
                LOG.error(String
                        .format("Failed to generate WSDL '%1$s' for XPDL file '%2$s' - removing existing WSDL", //$NON-NLS-1$
                                wsdlFile.getFullPath().toString(),
                                xpdlFile.getFullPath().toString()));
                try {
                    wsdlFile.delete(true, null);
                } catch (CoreException e) {
                    LOG.error(e);
                }
            } else {
                LOG.error(String
                        .format("Failed to generate WSDL '%1$s' for XPDL file '%2$s'", //$NON-NLS-1$
                                wsdlFile.getFullPath().toString(),
                                xpdlFile.getFullPath().toString()));
            }
        }

        if (runtimeError != null) {
            throw runtimeError;
        }
    }

    /**
     * @param xpdlFile
     * @param wsdlInMemoryResource
     * 
     * @return The initial definition element to pass to transform.
     * 
     * @throws ParserConfigurationException
     */
    private Definition createInitialWsdlDefinition(IFile xpdlFile,
            Resource wsdlInMemoryResource) throws ParserConfigurationException,
            IllegalStateException {

        WorkingCopy xpdlWc = WorkingCopyUtil.getWorkingCopy(xpdlFile);

        if (xpdlWc == null || !(xpdlWc.getRootElement() instanceof Package)) {
            throw new IllegalStateException(
                    "Could not access working copy for xpdlFile: " //$NON-NLS-1$
                            + xpdlFile.getFullPath().toString());
        }

        Definition definition = WSDLFactory.eINSTANCE.createDefinition();

        TransformHelper.updateNamespaces(definition,
                (Package) xpdlWc.getRootElement());

        Document document = null;
        document =
                DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .newDocument();
        definition.setDocument(document);

        wsdlInMemoryResource.getContents().add(definition);
        definition.updateElement();

        if (definition.getElement() != null) {
            IPath specialFolderRelativePath =
                    SpecialFolderUtil.getSpecialFolderRelativePath(xpdlFile);

            if (specialFolderRelativePath != null) {
                definition.getElement()
                        .setAttribute(WsdlUIPlugin.TIBEX_XPDL_PLACEHOLDER,
                                specialFolderRelativePath.toPortableString());
            }
        }

        return definition;
    }

    /**
     * Check if the checksum should be generated and added to the WSDL target
     * namespace.
     * 
     * @return
     */
    public static boolean shouldGenerateChecksum() {
        return Xpdl2ResourcesPlugin
                .getDefault()
                .getPreferenceStore()
                .getBoolean(Xpdl2ResourcesConsts.PREF_GENERATE_CHECKSUM_FOR_GENERATED_WSDL_NAMESPACE);

    }

    public static String getNameSpaceWithoutCheckSum(String nameSpaceURI) {
        // just to be on safer side checking for HASh_STRING
        if (nameSpaceURI.contains(NS_URI_CHECKSUM_DELIMITER)
                && shouldGenerateChecksum()) {
            int indexOf = nameSpaceURI.indexOf(NS_URI_CHECKSUM_DELIMITER);
            if (indexOf > 0) {
                nameSpaceURI = nameSpaceURI.substring(0, indexOf);
            }
        }
        return nameSpaceURI;
    }

    /**
     * Run the transformation.
     * 
     * @param xpdlFile
     * @param rootElement
     * @param bomFile
     * @param f
     */
    protected void executeXPDL2WSDLTransform(IFile xpdlFile,
            EObject rootElement, IFile bomFile, XtendFacade f) {
        final WorkingCopy xpdlWC =
                XpdResourcesPlugin.getDefault().getWorkingCopy(xpdlFile);

        EObject xpdlRootElement = null;
        if (xpdlWC != null) {
            xpdlRootElement = xpdlWC.getRootElement();
        }

        Object wsdlRootElement = rootElement;

        WorkingCopy bomWorkingCopy = null;
        if (bomFile != null) {
            bomWorkingCopy =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
        }

        EObject bomRootElement = null;
        if (bomWorkingCopy != null) {
            bomRootElement = bomWorkingCopy.getRootElement();
        }
        if (bomRootElement != null && wsdlRootElement != null
                && xpdlRootElement != null) {

            // If the bom element is being updated, then the bomRootElement
            // will not be null; This approach is used to track the impact
            // of the BOM changes on the WSDL
            if (isBOMUsedByXpdlForWsdl(xpdlRootElement, bomFile)) {
                /*
                 * If a checksum is going to be generated then we need to
                 * recreate the full WSDL. This is to ensure that we accurately
                 * recreate the wsdl between incremental and full clean builds
                 * to create an accurate checksum.
                 */
                if (shouldGenerateChecksum()) {
                    f.call(TRANSFORM_EXPRESSION,
                            xpdlRootElement,
                            wsdlRootElement);
                } else {
                    f.call(UPDATESCHEMA_EXPRESSION,
                            xpdlRootElement,
                            wsdlRootElement,
                            bomRootElement);
                }
            }

        } else if (wsdlRootElement != null && xpdlRootElement != null) {
            if (wsdlRootElement instanceof Definition
                    && xpdlRootElement instanceof Package) {
                f.call(TRANSFORM_EXPRESSION, xpdlRootElement, wsdlRootElement);
            }
        }

    }

    /**
     * @param xpdlRootElement
     * @param bomRootElement
     * @return
     */
    private boolean isBOMUsedByXpdlForWsdl(EObject xpdlRootElement,
            IFile bomFile) {
        if (xpdlRootElement instanceof Package) {

            Package xpdlPackage = (Package) xpdlRootElement;
            /*
             * If the Package has a process or process interface that has a
             * formal parameter with external reference to the bomelement then
             * this BOM is considered used by the XPDL for generating the WSDL.
             */
            // Get all relevant boms
            Set<Resource> bomResources =
                    getRelevantBOMFiles(bomFile, new HashSet<IFile>());
            List<Process> processes = xpdlPackage.getProcesses();
            for (Process process : processes) {
                List<FormalParameter> allFormalParameters =
                        ProcessInterfaceUtil.getAllFormalParameters(process);
                if (doFormalParamsRefBOMResource(bomResources,
                        allFormalParameters)) {
                    return true;
                }
            }
            ProcessInterfaces processInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(xpdlPackage);
            if (processInterfaces != null) {
                List<ProcessInterface> procIfcList =
                        processInterfaces.getProcessInterface();
                for (ProcessInterface procIfc : procIfcList) {
                    if (doFormalParamsRefBOMResource(bomResources,
                            procIfc.getFormalParameters())) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    /**
     * Get all the BOMs that are affected by a change to the given BOM
     * (including indirect dependents).
     * 
     * @param bomFile
     *            file to get dependents of
     * @param alreadyProcessedBOMs
     *            temporary cache to register all processed boms (to avoid
     *            cyclic processing)
     * @return set of all affected Resources including the Resource of the
     *         passed BOM file.
     */
    private Set<Resource> getRelevantBOMFiles(IFile bomFile,
            Set<IFile> alreadyProcessedBOMs) {
        Set<Resource> dependents = new HashSet<Resource>();

        if (bomFile != null && !alreadyProcessedBOMs.contains(bomFile)) {
            alreadyProcessedBOMs.add(bomFile);

            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(bomFile);
            if (wc != null && wc.getRootElement() != null) {
                Resource resource = wc.getRootElement().eResource();
                if (resource != null) {
                    dependents.add(resource);

                    // Process all dependents
                    for (IResource res : WorkingCopyUtil
                            .getAffectedResources(bomFile)) {
                        if (res instanceof IFile && isBOM((IFile) res)) {
                            dependents.addAll(getRelevantBOMFiles((IFile) res,
                                    alreadyProcessedBOMs));
                        }
                    }
                }
            }
        }

        return dependents;
    }

    /**
     * Check if the given file is a BOM file.
     * 
     * @param resource
     * @return
     */
    private boolean isBOM(IFile resource) {
        if (BOMResourcesPlugin.BOM_FILE_EXTENSION.equalsIgnoreCase(resource
                .getFileExtension())) {
            return true;
        }
        return false;
    }

    /**
     * @param bomResources
     * @param allFormalParameters
     */
    private boolean doFormalParamsRefBOMResource(Set<Resource> bomResources,
            List<FormalParameter> allFormalParameters) {
        for (FormalParameter formalParameter : allFormalParameters) {
            if (formalParameter.getDataType() instanceof ExternalReference) {
                ExternalReference extRef =
                        (ExternalReference) formalParameter.getDataType();
                if (extRef.getXref() != null) {
                    for (Resource bomResource : bomResources) {
                        if (bomResource.getEObject(extRef.getXref()) != null) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param definition
     * @param serviceTaskActivity
     * @param f
     */
    private void convertServiceTaskToWsdl(XtendFacade f, Definition definition,
            Activity serviceTaskActivity, SoapBindingStyle bindingType) {
        try {
            f.call(SERVICETASKWSDLCREATION,
                    definition,
                    serviceTaskActivity,
                    bindingType.getLiteral());

        } catch (Exception e) {
            LOG.error(e,
                    Messages.XtendTransformerXpdl2Wsdl_ErrTransform_shortdesc);
        }

    }

    /**
     * @param f
     */
    private void configureXpdlToWsdlWorkflow(final XtendFacade f) {
        URL url = null;
        Bundle xpdl2Bundle = Platform.getBundle(XPDL_PLUGIN_ID);
        // register ecore package
        EmfMetaModel ecoreMetaModel =
                new EmfMetaModel(
                        (EcorePackage) EPackage.Registry.INSTANCE
                                .get(EcorePackage.eNS_URI));
        f.registerMetaModel(ecoreMetaModel);

        // add ecore model for xpdl2
        url = xpdl2Bundle.getEntry(xpdl2MetaModelEcoreURL);
        EmfMetaModel xpdl2MetaModel = new EmfMetaModel();
        xpdl2MetaModel.setMetaModelFile(url.toString());
        f.registerMetaModel(xpdl2MetaModel);

        url = xpdl2Bundle.getEntry(xpdExtensionEcoreURL);
        EmfMetaModel xpdExtMetaModel = new EmfMetaModel();
        xpdExtMetaModel.setMetaModelFile(url.toString());
        f.registerMetaModel(xpdExtMetaModel);

        Bundle wsdlBundle = Platform.getBundle(WSDL_PLUGIN);
        url = wsdlBundle.getEntry(wsdlEcoreUrl);
        EmfMetaModel wsdlMetaModel = new EmfMetaModel();
        wsdlMetaModel.setMetaModelFile(url.toString());
        f.registerMetaModel(wsdlMetaModel);

        Bundle xsdBundle = Platform.getBundle(XSD_PLUGIN);
        url = xsdBundle.getEntry(xsdEcoreUrl);
        EmfMetaModel xsdMetaModel = new EmfMetaModel();
        xsdMetaModel.setMetaModelFile(url.toString());
        f.registerMetaModel(xsdMetaModel);

        Bundle umlBundle = Platform.getBundle(UML_PLUGIN);
        url = umlBundle.getEntry(umlEcoreUrl);
        EmfMetaModel umlMetaModel = new EmfMetaModel();
        umlMetaModel.setMetaModelFile(url.toString());
        f.registerMetaModel(umlMetaModel);

    }

    /**
     * @param xpdlFile
     * @param wsdlFile
     * @param namespacesForModel
     */
    public void removeSchemasFromWsdl(IFile xpdlFile, IFile wsdlFile,
            List<String> namespacesForModel) {
        TransactionalEditingDomain ed =
                XpdEditingDomainFactory.INSTANCE.createEditingDomain();
        try {
            // XpdResourcesPlugin.getDefault().getEditingDomain();
            final XtendFacade f = XtendFacade.create(XPDL2WSDLTRANSFORM);
            configureXpdlToWsdlWorkflow(f);
            runRemoveSchemasWorkflow(f,
                    ed,
                    xpdlFile,
                    wsdlFile,
                    namespacesForModel);
        } catch (IOException exception) {
            LOG.error(exception, "Error while removing Schemas"); //$NON-NLS-1$
        } finally {
            ed.dispose();
        }
    }

    /**
     * @param f
     * @param ed
     * @param stack
     * @param xpdlFile
     * @param wsdlFile
     * @param xsdFile
     * @param xsdSchemas
     * @throws IOException
     */
    private void runRemoveSchemasWorkflow(final XtendFacade f,
            TransactionalEditingDomain ed, IFile xpdlFile,
            final IFile wsdlFile, final List<String> namespacesForModel)
            throws IOException {

        URI uri =
                URI.createPlatformResourceURI(wsdlFile.getFullPath().toString(),
                        true);
        Resource resource = ed.getResourceSet().getResource(uri, true);
        EObject rootElement = resource.getContents().get(0);

        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Transaction.OPTION_UNPROTECTED, Boolean.TRUE);
        Transaction transaction = null;
        try {
            transaction =
                    ((InternalTransactionalEditingDomain) ed)
                            .startTransaction(false, attrs);
            executeRemoveSchemasFromWSDL(xpdlFile,
                    rootElement,
                    namespacesForModel,
                    f);
        } catch (InterruptedException e1) {
            LOG.error(e1);
        } finally {
            if (transaction != null) {
                try {
                    transaction.commit();
                } catch (RollbackException e) {
                    LOG.error(e);
                }
            }
        }

        try {
            resource.save(Collections.EMPTY_MAP);
        } catch (IOException exception) {
            LOG.error(exception,
                    Messages.XtendTransformerXpdl2Wsdl_ErrSave_shortdesc);
        }
    }

    /**
     * @param xpdlFile
     * @param rootElement
     * @param namespacesForModel
     * @param f
     */
    private void executeRemoveSchemasFromWSDL(IFile xpdlFile,
            EObject rootElement, List<String> namespacesForModel, XtendFacade f) {
        final WorkingCopy xpdlWC =
                XpdResourcesPlugin.getDefault().getWorkingCopy(xpdlFile);

        EObject xpdlRootElement = null;
        if (xpdlWC != null) {
            xpdlRootElement = xpdlWC.getRootElement();
        }
        if (rootElement != null && xpdlRootElement != null) {
            if (rootElement instanceof Definition
                    && xpdlRootElement instanceof Package) {
                try {
                    f.call(REMOVE_SCHEMAS_EXPRESSION,
                            xpdlRootElement,
                            rootElement,
                            namespacesForModel);
                } catch (Exception e) {
                    LOG.error(e,
                            Messages.XtendTransformerXpdl2Wsdl_ErrTransform_shortdesc);
                }
            }
        }
    }

    /**
     * Sort all elements in the WSDL (that have no order significance) to
     * generate a consistent checksum.
     * 
     * @param definition
     */
    private void fixWsdlForChecksumGeneration(Definition definition) {
        sortPortTypes(definition);
        sortMessages(definition);
        sortQNamePrefixes(definition);
    }

    /**
     * Orders all the schema namespaces and then regenerates the prefixes for
     * each Schema so we have consistency when creating the WSDL each time
     * 
     * @param definition
     */
    private void sortQNamePrefixes(Definition definition) {
        final String prefixStr = "tns"; //$NON-NLS-1$
        if (definition.getTypes() != null) {
            Iterator<?> iterator =
                    definition.getTypes().getExtensibilityElements().iterator();
            while (iterator.hasNext()) {
                Object next = iterator.next();
                if (next instanceof XSDSchemaExtensibilityElement) {
                    XSDSchema schema =
                            ((XSDSchemaExtensibilityElement) next).getSchema();

                    HashMap<String, String> oldPrefixForNamespacesMap =
                            new HashMap<String, String>();
                    SortedSet<String> namespacesSet = new TreeSet<String>();
                    for (Entry<String, String> entry : schema
                            .getQNamePrefixToNamespaceMap().entrySet()) {
                        if (entry.getKey() != null
                                && entry.getKey().contains(prefixStr)) {
                            oldPrefixForNamespacesMap.put(entry.getValue(),
                                    entry.getKey());
                            namespacesSet.add(entry.getValue());
                        }
                    }

                    Iterator<String> iter = namespacesSet.iterator();
                    int index = 1;
                    while (iter.hasNext()) {
                        String namespace = iter.next();
                        String oldKey =
                                oldPrefixForNamespacesMap.get(namespace);
                        schema.getQNamePrefixToNamespaceMap().remove(oldKey);

                        schema.getQNamePrefixToNamespaceMap().put(prefixStr
                                + index,
                                namespace);
                        index++;
                    }

                    schema.updateDocument();
                    schema.updateElement(true);
                    schema.update(true);
                }
            }
        }
    }

    /**
     * Sort the port types in the given definition.
     * 
     * @param definition
     */
    @SuppressWarnings("unchecked")
    private void sortPortTypes(Definition definition) {
        if (definition.getEPortTypes() != null) {
            ECollections.sort(definition.getEPortTypes(),
                    new Comparator<PortType>() {
                        @Override
                        public int compare(PortType o1, PortType o2) {
                            String o1Name = ""; //$NON-NLS-1$
                            String o2Name = ""; //$NON-NLS-1$
                            if (o1.getQName() != null) {
                                o1Name =
                                        o1.getQName().getLocalPart() != null ? o1
                                                .getQName().getLocalPart() : o1
                                                .getQName().toString();
                            }
                            if (o2.getQName() != null) {
                                o2Name =
                                        o2.getQName().getLocalPart() != null ? o2
                                                .getQName().getLocalPart() : o2
                                                .getQName().toString();
                            }
                            return o1Name.compareTo(o2Name);
                        }
                    });

            /*
             * For each port type sort the operations too
             */
            for (Object next : definition.getEPortTypes()) {
                if (next instanceof PortType) {
                    sortOperations((PortType) next);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void sortMessages(Definition definition) {
        if (definition.getEMessages() != null) {
            ECollections.sort(definition.getEMessages(),
                    new Comparator<Message>() {
                        @Override
                        public int compare(Message o1, Message o2) {
                            String o1Name = ""; //$NON-NLS-1$
                            String o2Name = ""; //$NON-NLS-1$

                            if (o1.getQName() != null) {
                                o1Name =
                                        o1.getQName().getLocalPart() != null ? o1
                                                .getQName().getLocalPart() : ""; //$NON-NLS-1$
                            }

                            if (o2.getQName() != null) {
                                o2Name =
                                        o2.getQName().getLocalPart() != null ? o2
                                                .getQName().getLocalPart() : ""; //$NON-NLS-1$
                            }

                            return o1Name.compareTo(o2Name);
                        }
                    });
        }
    }

    /**
     * Sort the operations of the given port type in alphabetic order.
     * 
     * @param portType
     */
    @SuppressWarnings("unchecked")
    private void sortOperations(PortType portType) {
        if (portType.getEOperations() != null) {
            ECollections.sort(portType.getEOperations(),
                    new Comparator<Operation>() {
                        @Override
                        public int compare(Operation o1, Operation o2) {
                            String o1Name =
                                    o1.getName() != null ? o1.getName() : ""; //$NON-NLS-1$
                            String o2Name =
                                    o2.getName() != null ? o2.getName() : ""; //$NON-NLS-1$

                            return o1Name.compareTo(o2Name);
                        }
                    });
        }
    }

    /**
     * Generate and checksum to the target namespace of the WSDL in the given
     * resource.
     * 
     * @param wsdlResource
     */
    private void addChecksumToNamespace(Resource wsdlResource) {
        IFile file = WorkspaceSynchronizer.getFile(wsdlResource);
        if (file != null) {
            Definition definition = getDefinition(wsdlResource);

            if (definition != null) {
                String targetNamespace = definition.getTargetNamespace();
                if (targetNamespace != null) {
                    try {
                        String checkSum = generateCheckSum(file);

                        if (checkSum != null) {

                            targetNamespace =
                                    targetNamespace
                                            .concat(NS_URI_CHECKSUM_DELIMITER
                                                    + checkSum);

                            TransformHelper.updateNamespaces(definition,
                                    targetNamespace,
                                    null);
                        }
                    } catch (Exception e) {
                        LOG.error(e,
                                "Problem generating checksum to add to wsdl target namespace"); //$NON-NLS-1$
                    }
                }
            }
        }
    }

    /**
     * Get the {@link Definition} from the given WSDL resource.
     * 
     * @param wsdlResource
     * @return
     */
    private Definition getDefinition(Resource wsdlResource) {
        for (EObject eo : wsdlResource.getContents()) {
            if (eo instanceof Definition) {
                return (Definition) eo;
            }
        }
        return null;
    }

    /**
     * Generate a checksum for the given file.
     * 
     * @param wsdlFile
     * @return
     * @throws IOException
     * @throws CoreException
     * @throws NoSuchAlgorithmException
     */
    private String generateCheckSum(IFile wsdlFile) throws IOException,
            CoreException, NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256"); //$NON-NLS-1$
        InputStream inputStream = null;
        InputStreamReader isReader = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = wsdlFile.getContents();
            isReader =
                    new InputStreamReader(inputStream, Charset.forName("UTF-8")); //$NON-NLS-1$
            bufferedReader = new BufferedReader(isReader);
            String expLine = null;
            while ((expLine = bufferedReader.readLine()) != null) {
                expLine = expLine.trim();
                if (expLine.length() > 0) {
                    md.update(expLine.getBytes());
                }
            }

            byte[] mdbytes = md.digest();

            // convert the byte to hex format
            StringBuffer sb = new StringBuffer(""); //$NON-NLS-1$
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            return sb.toString();
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }

            if (isReader != null) {
                isReader.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

}
