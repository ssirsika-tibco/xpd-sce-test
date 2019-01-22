/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.n2.scriptdescriptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;

import com.tibco.bds.designtime.generator.CDSBOMIndexerService;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.daa.internal.util.CompositeUtil;
import com.tibco.xpd.implementer.nativeservices.utilities.TaskServiceExtUtil;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.n2.scriptdescriptor.utils.ScriptDescriptorUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.descriptor.CacType;
import com.tibco.xpd.script.descriptor.DescriptorFactory;
import com.tibco.xpd.script.descriptor.DocumentRoot;
import com.tibco.xpd.script.descriptor.EnumType;
import com.tibco.xpd.script.descriptor.FactoryType;
import com.tibco.xpd.script.descriptor.ProcessType;
import com.tibco.xpd.script.descriptor.ScriptDescriptorType;
import com.tibco.xpd.script.descriptor.ScriptType;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.wsdltobom.indexer.WsdlBomIndexerUtil;
import com.tibco.xpd.xpdExtension.CaseAccessOperationsType;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Generates ScriptDescriptor for a project.
 * <p>
 * <i>Created: 10 Sep 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class ScriptDescriptorGenerator {

    private static final Logger LOG = Activator.getDefault().getLogger();

    public static String SCRIPT_DESCRIPTOR_SF_NAME = ".scriptModule"; //$NON-NLS-1$

    public static String SCRIPT_DESCRIPTOR_SF_KIND = "scriptsOutput"; //$NON-NLS-1$

    private static final String CASE_STEREOPTYPE =
            "BOM Global Data Profile::Case"; //$NON-NLS-1$

    /*
     * XPD-2064: Keep track of Enumeration by name, to detect and handle
     * ambiguity caused by multiple enumerations with same name,in xpdl package
     * scope.
     */
    private HashMap<String, List<EnumType>> enumsInProcessScope = null;

    private static ScriptDescriptorGenerator INSTANCE =
            new ScriptDescriptorGenerator();

    /**
     * Obtains instance of the generator.
     * 
     * @return ScriptDescriptorGenerator instance.
     */
    public static ScriptDescriptorGenerator getInstance() {
        return INSTANCE;
    }

    /**
     * Enum for Info Type to add to the scripts file.
     */
    private enum BOMInfo {
        SCRIPTTYPE, CACTYPE
    }

    /**
     * Private constructor. Use {@link #getInstance()} factory method instead.
     */
    private ScriptDescriptorGenerator() {
    }

    /**
     * Generate Script Descriptor related artifacts for a project without using
     * a workspace a job.
     * 
     * @param project
     *            the context project.
     * @param timeStamp
     * @param stagingFolder
     */
    public void generateScriptDescriptor(final IProject project,
            IFolder outFolder, String timeStamp) {
        // final IFolder outFolder = getScriptDescriptorSF(project);
        if (outFolder == null) {
            return;
        }
        try {
            String version =
                    ScriptDescriptorUtils.getUpdatedBundleVersion(CompositeUtil
                            .getVersionNumber(project), timeStamp);
            // ScriptDescriptor
            DescriptorFactory descFactory = DescriptorFactory.eINSTANCE;
            ScriptDescriptorType scriptDescriptorType =
                    descFactory.createScriptDescriptorType();
            scriptDescriptorType.setVersion(version);

            // generate descriptors
            generateScriptDescriptorType(project,
                    descFactory,
                    scriptDescriptorType);

            // Save script descriptor.
            if (scriptDescriptorType.getScript().size() > 0) {
                // Use a new resource set.
                DocumentRoot root =
                        DescriptorFactory.eINSTANCE.createDocumentRoot();
                root.setScriptdescriptor(scriptDescriptorType);
                URI uri =
                        ScriptDescriptorUtils
                                .getScriptDescriptorResourceURI(outFolder,
                                        project);
                saveResource(project, outFolder, root, uri);
            }
        } catch (final Exception e) {
            LOG.error(e);
        }
    }

    /**
     * @param project
     * @param outFolder
     * @param root
     * @throws IOException
     * @throws CoreException
     */
    private void saveResource(final IProject project, IFolder outFolder,
            EObject root, URI uri) throws IOException, CoreException {
        // Use a new resource set.
        ResourceSet rs = new ResourceSetImpl();
        Resource r = rs.createResource(uri);

        r.getContents().add(root);
        IFile file = WorkspaceSynchronizer.getFile(r);
        Map<Object, Object> defaultXMLSaveOptions =
                N2Utils.getDefaultXMLSaveOptions();
        defaultXMLSaveOptions.put(XMLResource.OPTION_EXTENDED_META_DATA,
                Boolean.TRUE);
        if (file == null) {
            // out of workspace resource.
            r.save(N2Utils.getDefaultXMLSaveOptions());
        } else {
            r.save(N2Utils.getDefaultXMLSaveOptions()); // overwrite
            file.refreshLocal(IResource.DEPTH_ZERO, null);
            file.setDerived(true);
        }
    }

    /**
     * Generate root element of script descriptor.
     * 
     * @param project
     *            the context project.
     * @param descriptor
     *            factory
     * @param scriptDescriptorType
     * @return the generated root of Script Descriptor document.
     */
    private void generateScriptDescriptorType(IProject project,
            DescriptorFactory descFactory,
            ScriptDescriptorType scriptDescriptorType) {

        Collection<Process> processes =
                ScriptDescriptorUtils.getN2Processes(project);

        for (Process process : processes) {

            /* fill Script Descriptor Data */
            ScriptType scriptType = descFactory.createScriptType();
            ProcessType processType = descFactory.createProcessType();
            processType.setPackageId(process.getPackage().getId());
            processType.setProcessId(process.getId());
            scriptType.setProcess(processType);
            fillScriptTypeForProcess(scriptType, process);
            /*
             * XPD-5556: script descriptor must have cac entries only if a
             * process references a case class by ref and not by val
             */
            fillCACTypeForProcess(descFactory, scriptType, process);
            /*
             * XPD-2064: Handle ambiguity caused by existence of multiple
             * enumeration with same name in xpdl scope.
             */
            if (!enumsInProcessScope.keySet().isEmpty()) {
                processSameNameEnumsInScope(descFactory, scriptType);
            }
            /* check if there are any factory (any dependent UML package) */
            if (scriptType.getFactory().size() > 0) {
                scriptDescriptorType.getScript().add(scriptType);
            }
        }

    }

    /**
     * Scans through all the activities in the process looking for Global Data
     * Service Task referencing Case Class via 'Case Access Operations' and
     * based on the value of the passed parameter 'bOMInfo' either fills the
     * Script Type for the global bom or fills the CAC type for global bom.
     * 
     * @param visitedResources
     * @param scriptType
     * @param process
     * @param bOMInfo
     *            the type to add (Script or CAC) to the script descriptor.
     */
    private void fillScriptOrCACTypeForGDServiceTasks(
            Set<IResource> visitedResources, ScriptType scriptType,
            Process process, BOMInfo bOMInfo) {

        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);

        for (Activity eachActivity : allActivitiesInProc) {

            CaseAccessOperationsType caseAccessOps =
                    getCACOperationFromActivity(eachActivity);
            if (caseAccessOps != null) {

                ExternalReference extRef =
                        caseAccessOps.getCaseClassExternalReference();
                if (extRef != null) {

                    IProject project =
                            WorkingCopyUtil.getProjectFor(eachActivity);
                    if (project != null) {

                        /* get the class from external reference */
                        EObject referencedCaseClass =
                                ProcessUIUtil.getReferencedClassifier(extRef,
                                        project);

                        if (referencedCaseClass != null
                                && BOMGlobalDataUtils
                                        .isCaseClass((Class) referencedCaseClass)) {

                            /* get BOM File */
                            IFile bomFile =
                                    WorkingCopyUtil
                                            .getFile(referencedCaseClass);

                            if (bomFile != null) {

                                if (BOMInfo.SCRIPTTYPE.equals(bOMInfo)) {

                                    /* fill the info for script descriptor */
                                    fillScriptTypeForBOM(scriptType,
                                            bomFile,
                                            visitedResources);

                                } else if (BOMInfo.CACTYPE.equals(bOMInfo)) {

                                    /* Fill the CAC types */
                                    fillCACTypeForGlobalBOM(scriptType,
                                            bomFile,
                                            visitedResources);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * get the {@link CaseAccessOperationsType} from given activity if it is a
     * global data task, with Case Access CLass operation selected or
     * <code>null</code> if it is none of these.
     * 
     * @param activity
     * @return
     * 
     * @since 3.7.0 (XPD-5981)
     */
    private CaseAccessOperationsType getCACOperationFromActivity(
            Activity activity) {
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {

            Task task = (Task) implementation;
            if (task.getTaskService() != null) {

                EObject extendedModel =
                        TaskServiceExtUtil.getExtendedModel(task
                                .getTaskService(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_GlobalDataOperation());
                if (extendedModel instanceof GlobalDataOperation) {

                    /* We are interested only in Global Data Operations */
                    GlobalDataOperation globalDataOp =
                            (GlobalDataOperation) extendedModel;
                    /*
                     * Return the CaseAccessOperationType if its defined, else
                     * will return null.
                     */
                    return globalDataOp.getCaseAccessOperations();
                }
            }
        }
        return null;
    }

    /**
     * fills in the cac entries in the script descriptor file only if the
     * process references a case class by ref
     * 
     * @param descFactory
     * @param scriptType
     * @param process
     */
    private void fillCACTypeForProcess(DescriptorFactory descFactory,
            ScriptType scriptType, Process process) {

        Set<IResource> visitedResources = new HashSet<IResource>();

        /*
         * fill CAC Type for all Process Data referencing Case Class
         */
        fillCACTypeForProcessData(visitedResources, scriptType, process);

        /*
         * fill CAC Type for all Global service tasks(within a process)
         * referencing Case Class
         */
        fillScriptOrCACTypeForGDServiceTasks(visitedResources,
                scriptType,
                process,
                BOMInfo.CACTYPE);
        /*
         * Kapil XPD-5391 : <<Code removed from here>>! The code for wsdl
         * referencing Case Class has been removed completely because, 1. CAC
         * entries should only be added where case-reference fields are used and
         * you cannot use case reference fields when generating in WSDLs 2. So
         * if we do the WSDL processing as we are then we will include CAC types
         * for a BOM just because a process has a WSDL that ref's that BOM not
         * because the process has case ref fields.
         */
    }

    /**
     * fills in the cac entries in the script descriptor file only if the
     * process data references a case class by ref
     * 
     * @param visitedResources
     * @param scriptType
     * @param process
     */
    private void fillCACTypeForProcessData(Set<IResource> visitedResources,
            ScriptType scriptType, Process process) {
        List<ProcessRelevantData> allProcessRelevantData =
                ProcessInterfaceUtil.getAllCaseRefData(process);
        for (ProcessRelevantData data : allProcessRelevantData) {

            /* We are only interested in Case types */
            Object baseType =
                    BasicTypeConverterFactory.INSTANCE.getBaseType(data, false);
            if (baseType instanceof EObject) {

                IFile bomResource = WorkingCopyUtil.getFile((EObject) baseType);
                if (bomResource != null) {

                    fillCACTypeForGlobalBOM(scriptType,
                            bomResource,
                            visitedResources);
                }
            }
        }
    }

    /**
     * Adds the entry of unqualified name for unambiguous enumerations [Only one
     * enumeration with given name exist in xpdl package scope].
     * 
     * @param f
     * @param scriptType
     * 
     */
    private void processSameNameEnumsInScope(DescriptorFactory f,
            ScriptType scriptType) {
        for (String enumName : enumsInProcessScope.keySet()) {
            List<EnumType> enumsWithSameName =
                    enumsInProcessScope.get(enumName);

            if (enumsWithSameName.size() == 1) {
                /*
                 * In case of unambiguous situation, There will be two entries
                 * for the enumeration, to support both unqualified and
                 * Qualified name.
                 */
                EnumType enumTypeWithQualifiedName = enumsWithSameName.get(0);
                // Add unqualified Name as well
                EnumType enumTypeWithDefaultName = f.createEnumType();
                enumTypeWithDefaultName
                        .setCanonicalClassName(enumTypeWithQualifiedName
                                .getCanonicalClassName());
                enumTypeWithDefaultName
                        .setScriptingName(getUnqualifiedNameForEnum(enumName));
                // add entry for unqualified just above the qualified one.
                scriptType.getEnum().add(scriptType.getEnum()
                        .indexOf(enumTypeWithQualifiedName),
                        enumTypeWithDefaultName);
            }
        }

    }

    /**
     * This methods formats the qualified name for the enumeration, to be used
     * in Scripting. [com.example::Color/com.example.Color -> com_example_Color]
     * 
     * @param qualifiedNameOfEnum
     * @return
     */
    public static String getQualifiedNameOfEnumForScript(
            String qualifiedNameOfEnum) {
        return NameUtil.formatQualifiedNameForScripting(qualifiedNameOfEnum);

    }

    /**
     * This methods extracts the unqualified name from the given qualified name
     * [of format com.example.model.Enum1].
     * 
     * @param qualifiedNameOfEnum
     * @return unqualified name
     */
    private static String getUnqualifiedNameForEnum(String qualifiedNameOfEnum) {

        return qualifiedNameOfEnum
                .replaceAll(BOMWorkingCopy.UML_PACKAGE_SEPARATOR,
                        BOMWorkingCopy.JAVA_PACKAGE_SEPARATOR)
                .substring(qualifiedNameOfEnum.lastIndexOf(BOMWorkingCopy.JAVA_PACKAGE_SEPARATOR) + 1);

    }

    /**
     * Finds referenced (also indirectly (via WSDL)) BOMs and fills ScriptData
     * for theirs types.
     * 
     * @param scriptType
     *            the context ScriptType to fill.
     * @param process
     *            the process to investigate.
     */
    private void fillScriptTypeForProcess(final ScriptType scriptType,
            final Process process) {
        /* initialise cache for xpdl-package-scope Enums */
        enumsInProcessScope = new HashMap<String, List<EnumType>>();
        Set<IResource> visitedResources = new HashSet<IResource>();

        /*
         * Kapil: XPD-5391 - Add 'only' those processes to the script descriptor
         * file which have process data referencing BOMs.
         */
        List<ProcessRelevantData> allProcessRelevantData =
                ProcessInterfaceUtil.getAllExternalProcessRelevantData(process);
        for (ProcessRelevantData data : allProcessRelevantData) {
            /*
             * We are interested in only process data having ext reference to
             * BOM.
             */
            Object baseType =
                    BasicTypeConverterFactory.INSTANCE.getBaseType(data, false);

            if (baseType instanceof EObject) {
                IResource resource =
                        WorkingCopyUtil.getFile((EObject) baseType);

                /* Check if the resource is a BOM resource */
                if (resource != null
                        && XpdResourcesPlugin.getDefault()
                                .getWorkingCopy(resource) instanceof BOMWorkingCopy) {
                    /* fill the info for script descriptor */
                    fillScriptTypeForBOM(scriptType, resource, visitedResources);
                }
            }
        }
        /*
         * XPD-7572: Add Script type info for BOM referenced from Global Data
         * Service Task.
         */
        fillScriptOrCACTypeForGDServiceTasks(visitedResources,
                scriptType,
                process,
                BOMInfo.SCRIPTTYPE);

        /*
         * XPD-5391: this block generates script descriptor file for processes
         * containing wsdl referencing BOMS(both auto generated and user
         * defined.)
         */
        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        Set<IResource> wsdlReferencedFromProcess = new HashSet<IResource>();
        /* Check all the activities of a process for wsdl reference */
        for (Activity eachActivity : allActivitiesInProc) {
            IFile wsdlFile = Xpdl2WsdlUtil.getWsdlFile(eachActivity);
            if (wsdlFile != null && wsdlFile.exists()) {
                wsdlReferencedFromProcess.add(wsdlFile);
            }
        }

        for (IResource wsdlResource : wsdlReferencedFromProcess) {
            /* Get all the BOM's referenced from the wsdl. */
            Set<IResource> queryBOMsForWSDL =
                    WsdlBomIndexerUtil.queryBOMsForWSDL(wsdlResource);

            for (IResource bomResource : queryBOMsForWSDL) {
                /* fill the info for script descriptor */
                fillScriptTypeForBOM(scriptType, bomResource, visitedResources);
            }
        }
    }

    /**
     * adds cac entry in the script descriptor file
     * 
     * @param scriptType
     * @param gdBomRes
     * @param visitedResources
     */
    private void fillCACTypeForGlobalBOM(ScriptType scriptType,
            IResource gdBomRes, Set<IResource> visitedResources) {

        if (!visitedResources.contains(gdBomRes)) {

            visitedResources.add(gdBomRes);
            if (gdBomRes instanceof IFile
                    && gdBomRes.exists()
                    && XpdResourcesPlugin.getDefault().getWorkingCopy(gdBomRes) instanceof BOMWorkingCopy) {

                Collection<Package> allPackages =
                        CDSBOMIndexerService.getInstance()
                                .getAllPackagesWithCDS((IFile) gdBomRes);
                for (Package pkg : allPackages) {

                    /*
                     * XPD-4291: existing script descriptor modified to have cac
                     * entries (instead of having separate script descriptor)
                     */
                    for (PackageableElement pkgElement : pkg
                            .getPackagedElements()) {

                        if (pkgElement instanceof org.eclipse.uml2.uml.Class
                                && null != pkgElement
                                        .getAppliedStereotype(CASE_STEREOPTYPE)) {

                            DescriptorFactory descFactory =
                                    DescriptorFactory.eINSTANCE;
                            CacType cacType = descFactory.createCacType();
                            cacType.setCanonicalClassName(ScriptDescriptorUtils
                                    .getCACCanonicalName((org.eclipse.uml2.uml.Class) pkgElement));
                            cacType.setScriptingName(ScriptDescriptorUtils
                                    .getCACScriptingName((Class) pkgElement));
                            scriptType.getCac().add(cacType);
                        }
                    }
                }

                /* process BOM dependencies */
                /**
                 * bharti: TODO: we are commenting this for now as we currently
                 * dont support case classes depending on case classes in other
                 * boms (by way of generalisation/composition). but when we
                 * remove that validation see
                 * {@link com.tibco.xpd.bom.validator.rules.globaldata .GeneralizationEndsRule}
                 * we might/might not (might not because we may still want to
                 * have cac entries only for those that are referenced by ref
                 * from a process) have to re-visit this. and/or requirements
                 * file requires-bundles
                 */
                // WorkingCopy wc =
                // XpdResourcesPlugin.getDefault()
                // .getWorkingCopy(gdBomRes);
                // for (IResource r : wc.getDependency()) {
                //
                // fillCACTypeForGlobalBOM(scriptType, r, visitedResources);
                // }
            }
        }

    }

    /**
     * Fills ScriptType with data from the BOM.
     * 
     * @param scriptType
     *            the context ScriptType to fill.
     * @param resource
     *            the workspace resource (IFile) containing BOM model.
     * @param visitedResources
     *            set of already visited resources.
     */
    private void fillScriptTypeForBOM(final ScriptType scriptType,
            final IResource resource, final Set<IResource> visitedResources) {

        if (!visitedResources.contains(resource)) {

            visitedResources.add(resource);
            if (resource instanceof IFile
                    && resource.exists()
                    && XpdResourcesPlugin.getDefault().getWorkingCopy(resource) instanceof BOMWorkingCopy) {

                DescriptorFactory descFactory = DescriptorFactory.eINSTANCE;

                /* process BOM packages */

                /*
                 * Sid XPD-3641: Switch to using non-bom2cds-index-hitting get
                 * packages method.
                 * 
                 * So converted the old get-info from indexeritem, to get the
                 * equivalent info direct from BOM package itself (after
                 * checking where the CDSBOMIndexer gets the same info from the
                 * package).
                 */
                Collection<Package> allPackages =
                        CDSBOMIndexerService.getInstance()
                                .getAllPackagesWithCDS((IFile) resource);

                for (Package pkg : allPackages) {

                    /*
                     * XPD-5711: for sub packages pkg.getName() is not the fully
                     * qualified name
                     */
                    String packageName =
                            BOMWorkingCopy.getQualifiedPackageName(pkg);

                    /*
                     * Can just use the raw 'get factory name for package'
                     * method that doesn't check whether package will get have
                     * CDS factory created because that's done already in the
                     * getPackageWithCDS() above.
                     */
                    String packageFactory =
                            CDSBOMIndexerService.getInstance()
                                    .getQualifiedFactoryName(pkg);

                    String packageNsUri =
                            CDSBOMIndexerService.getNamespaceUri(pkg);
                    FactoryType factoryType = descFactory.createFactoryType();
                    factoryType.setScriptingName(packageFactory);

                    factoryType
                            .setCanonicalClassName(getCanonicalClassName(packageName));

                    factoryType.setNamespace(packageNsUri);
                    scriptType.getFactory().add(factoryType);
                }

                /* process UML enumerations */
                for (String enumName : getBOMEnumNames((IFile) resource)) {

                    EnumType enumType = descFactory.createEnumType();
                    enumType.setCanonicalClassName(enumName);
                    /* added qualified name to be used for scripting */
                    enumType.setScriptingName(getQualifiedNameOfEnumForScript(enumType
                            .getCanonicalClassName()));
                    scriptType.getEnum().add(enumType);
                    /*
                     * add to temp cache, to be used to add unqualified name if
                     * suitable.
                     */
                    addToProjectScopeEnumCache(enumName, enumType);
                }

                /* process BOM dependencies */
                WorkingCopy wc =
                        XpdResourcesPlugin.getDefault()
                                .getWorkingCopy(resource);
                for (IResource r : wc.getDependency()) {

                    fillScriptTypeForBOM(scriptType, r, visitedResources);
                }
            }
        }
    }

    /**
     * Adds to the temporary cache , to be used add unqualified name entry.
     * 
     * @param enumName
     * @param enumType
     */
    private void addToProjectScopeEnumCache(String enumName, EnumType enumType) {
        String enumTypeName = getUnqualifiedNameForEnum(enumName);
        List<EnumType> enumsList = enumsInProcessScope.get(enumTypeName);
        if (enumsList == null) {
            enumsList = new ArrayList<EnumType>();
            enumsInProcessScope.put(enumTypeName, enumsList);
        }
        enumsList.add(enumType);
    }

    /**
     * @param packageName
     * @return
     */
    public static Set<URI> getAllPackagesURIs(IFile bomFile) {
        /*
         * Sid XPD_3641: Switch to using non-indexer-hitting get packages
         * method.
         */

        Collection<Package> allPackages =
                CDSBOMIndexerService.getInstance()
                        .getAllPackagesWithCDS(bomFile);

        if (allPackages != null) {
            Set<URI> allPackagesURIs = new HashSet<URI>();

            for (Package pkg : allPackages) {
                allPackagesURIs.add(EcoreUtil.getURI(pkg));
            }
            return allPackagesURIs;
        }
        return Collections.emptySet();
    }

    /**
     * @param packageName
     * @return
     * @deprecated This method returns info according what has been indexed by
     *             actually looking in the index (so that it can return
     *             <code>null</code> if nothing is being generated for package
     *             because it has no content that requires any cds stuff. Using
     *             {@link #getFactoryForPackage(Package)} does the same BUT is
     *             much more efficient because it doesn't go to the index.
     */
    @Deprecated
    public static String getFactoryForPackage(String packageName) {
        String factoryForPackage =
                CDSBOMIndexerService.getInstance()
                        .getCDSFactoryForPackage(packageName);
        return factoryForPackage;
    }

    /**
     * Get the CDS factory name that will be generated for the given BOM
     * package. If the BOM package will not generate a CDS factory because there
     * is no CDS'able content like classes etc then return null.
     * 
     * @param pkg
     * @return CDS factory name for BOM package or <code>null</code> if no CDS
     *         factory will be generated for it.
     */
    public static String getFactoryForPackage(Package pkg) {
        String factoryForPackage =
                CDSBOMIndexerService.getInstance().getCDSFactoryForPackage(pkg);
        return factoryForPackage;
    }

    /**
     * @param packageName
     * @param suffix
     * @return
     */
    private static String getCanonicalClassName(String packageName) {
        String[] splitPkgName = packageName.split("\\."); //$NON-NLS-1$
        String endName = splitPkgName[splitPkgName.length - 1];
        String firstChar = "" + endName.charAt(0); //$NON-NLS-1$
        firstChar = firstChar.toUpperCase();
        endName = firstChar + endName.substring(1);
        String factoryClass = packageName + "." + endName + "Factory"; //$NON-NLS-1$//$NON-NLS-2$
        return factoryClass;
    }

    private Collection<String> getBOMEnumNames(IFile resource) {
        /*
         * Sid XPD_3641: Switch to using non-indexer-hitting get packages
         * method.
         */
        Collection<String> enumNames =
                CDSBOMIndexerService.getInstance()
                        .getBOMEnumNamesFaster(resource);
        return enumNames;
    }

}
