/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.cds.utils;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;

import com.tibco.amf.model.productfeature.IncludedPlugin;
import com.tibco.bds.designtime.generator.CDSBOMIndexerService;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.gen.biz.GenerationException;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.daa.internal.util.CompositeUtil;
import com.tibco.xpd.n2.cds.CDSActivator;
import com.tibco.xpd.n2.cds.CdsConsts;
import com.tibco.xpd.n2.cds.script.CdsContentAssistIconProvider;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.model.util.ProcessUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessRelevantDataUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rest.schema.ui.internal.editor.UmlJsonSchemaLabelProvider;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.client.globaldata.CaseUMLScriptRelevantData;
import com.tibco.xpd.script.model.internal.client.ITypeResolution;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;

/**
 * Utility class to generate EMF projects for BOMs & custom feature file
 * 
 * @author mtorres, kupadhya
 * 
 */
public class CDSUtils {

    private static final String DOT_QUALIFIER = ".qualifier"; //$NON-NLS-1$

    public static String N2PE_DESTINATION = "n2pe1.x";//$NON-NLS-1$

    private static CdsContentAssistIconProvider cdsContentAssistIconProvider =
            null;

    protected static Map<String, String> N2JAVASCRIPT_TYPE;

    /**
     * This class invokes BOMGenerator API for generation of EMF projects
     * 
     * @author kupadhya
     * 
     */

    private static final class EMFProjectGenerationRunnable
            implements IWorkspaceRunnable {
        private final List<IncludedPlugin> generatedPlugins;

        private final LinkedHashMap<IResource, List<IProject>> bomsToBuildMap;

        private EMFProjectGenerationRunnable(
                List<IncludedPlugin> generatedPlugins,
                LinkedHashMap<IResource, List<IProject>> bomsToBuildMap) {
            this.generatedPlugins = generatedPlugins;
            this.bomsToBuildMap = bomsToBuildMap;
        }

        public void run(IProgressMonitor monitor) throws CoreException {
            Set<Entry<IResource, List<IProject>>> entrySet =
                    bomsToBuildMap.entrySet();
            // for (Entry<IResource, List<IProject>> entry : entrySet) {
            // IResource bomResource = entry.getKey();
            // List<IProject> dependencyProjects = entry.getValue();
            // String generatedPluginVersion =
            // BOMGenerator.resolveGeneratedPluginVersion(bomResource);
            // String generatedPluginId =
            // BOMGenerator.resolveGeneratedPluginId(bomResource);
            // try {
            // IProject generatedProject =
            // BOMGenerator.generateRuntimeModel(bomResource,
            // generatedPluginVersion,
            // generatedPluginId,
            // dependencyProjects,
            // true);
            // if (generatedProject == null || !generatedProject.isOpen()) {
            // return;
            // }
            // CDSUtils.storeBOMPersistentProperty(generatedProject,
            // bomResource);
            // if (generatedProject != null) {
            // IncludedPlugin rb =
            // ProductFeatureFactory.eINSTANCE
            // .createIncludedPlugin();
            // rb.setId(generatedPluginId);
            // rb.setVersion(generatedPluginVersion);
            // generatedPlugins.add(rb);
            // }
            // } catch (GenerationException e) {
            // e.printStackTrace();
            // } catch (BOMIllegalStateException e) {
            // // fail silently as BOM seems to be empty
            // }
            //
            // }
        }
    }

    private static final String BOM_SOURCE_PERSISTENT_PROPERTY =
            "BOM_RESOURCE_NAME"; //$NON-NLS-1$

    private static final String TOKEN_PATH_SEPARATOR = "."; //$NON-NLS-1$

    public static final String BOM_SPECIAL_FOLDER =
            BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND;

    public static final String BOM_FILE_EXTENSION =
            BOMResourcesPlugin.BOM_FILE_EXTENSION;

    /** cds special folder kind. */
    public static final String CDS_SPECIAL_FOLDER_KIND = "cdsOutput"; //$NON-NLS-1$

    /** cds special folder name. */
    public static final String CDS_SPECIAL_FOLDER_NAME = ".cdsOut"; //$NON-NLS-1$

    private static Class defaultMultipleClass = null;

    private static Class defaultPaginatedMultipleClass = null;

    public static String getCustomFeatureVersion(IProject project) {

        if (BOMUtils.isBusinessDataProject(project)) {

            return BOMUtils.getBusinessDataBuildVersion(project);
        }
        return CompositeUtil.getVersionNumber(project);
    }

    public static IFolder getCDSOutDestFolder(IProject project) {
        if (project != null) {
            return getCDSDestFolder(project, "");//$NON-NLS-1$
        }
        return null;
    }

    private static IFolder getCDSDestFolder(IProject project,
            String folderName) {
        if (project != null) {
            IPath distFolderPath = project.getFullPath()
                    .append(CDS_SPECIAL_FOLDER_NAME).append(folderName);
            IFolder distFolder =
                    project.getWorkspace().getRoot().getFolder(distFolderPath);
            return distFolder;
        }
        return null;
    }

    /**
     * returns a list of all BOM resources in the given Project
     * 
     * @param project
     * @return
     */
    public static List<IResource> getBomResources(IProject project) {
        if (project != null && project.isAccessible()) {
            List<IResource> bomResources = SpecialFolderUtil
                    .getAllDeepResourcesInSpecialFolderOfKind(project,
                            BOM_SPECIAL_FOLDER,
                            BOM_SPECIAL_FOLDER,
                            false);
            if (bomResources != null && !bomResources.isEmpty()) {
                return bomResources;
            }
        }
        return Collections.emptyList();
    }

    public static Set<Package> getReferencedBomPackages(Process process) {
        if (process != null) {
            Set<String> referencedBoms =
                    ProcessUIUtil.queryReferencedBoms(process, false);
            if (referencedBoms != null && !referencedBoms.isEmpty()) {
                IProject project = WorkingCopyUtil.getProjectFor(process);
                if (project != null) {
                    // Include indirect dependency
                    Set<IResource> referencedBomResources =
                            getReferencedBomResources(referencedBoms,
                                    true,
                                    project);
                    return getReferencedBomPackages(referencedBomResources);
                }
            }
        }
        return Collections.emptySet();
    }

    public static Set<Package> getReferencedBomPackages(
            com.tibco.xpd.xpdl2.Package xpdlPackage) {
        if (xpdlPackage != null) {
            Set<String> referencedBoms =
                    ProcessUIUtil.queryReferencedBoms(xpdlPackage, false);
            if (referencedBoms != null && !referencedBoms.isEmpty()) {
                IProject project = WorkingCopyUtil.getProjectFor(xpdlPackage);
                if (project != null) {
                    // Include indirect dependency
                    Set<IResource> referencedBomResources =
                            getReferencedBomResources(referencedBoms,
                                    true,
                                    project);
                    return getReferencedBomPackages(referencedBomResources);
                }
            }
        }
        return Collections.emptySet();
    }

    public static Set<Package> getReferencedBomPackages(
            Set<IResource> referencedBomResources) {
        if (referencedBomResources != null
                && !referencedBomResources.isEmpty()) {
            ResourceSet resourceSet = XpdResourcesPlugin.getDefault()
                    .getEditingDomain().getResourceSet();
            if (resourceSet != null) {
                Set<Package> referencedBomPackages = new HashSet<Package>();
                for (IResource referencedBomResource : referencedBomResources) {
                    if (referencedBomResource instanceof IFile) {
                        Set<URI> allPackagesURIs = CDSUtils.getAllPackagesURIs(
                                (IFile) referencedBomResource);
                        if (allPackagesURIs != null) {
                            for (URI uri : allPackagesURIs) {
                                EObject object =
                                        resourceSet.getEObject(uri, true);
                                if (object instanceof Package) {
                                    referencedBomPackages.add((Package) object);
                                }
                            }
                        }
                    }
                }
                return referencedBomPackages;
            }
        }
        return Collections.emptySet();
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

        Collection<Package> allPackages = CDSBOMIndexerService.getInstance()
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

    public static Set<IResource> getReferencedBomResources(
            Set<String> referencedBoms, boolean includeIndirectReferences,
            IProject project) {
        if (referencedBoms != null && project != null) {
            Set<IResource> processedResources = new HashSet<IResource>();
            for (String referencedBom : referencedBoms) {
                // Decode Refereced BOM
                try {
                    String decode = URLDecoder.decode(referencedBom, "UTF-8");//$NON-NLS-1$
                    referencedBom = decode;
                } catch (UnsupportedEncodingException e) {
                    XpdResourcesPlugin.getDefault().getLogger().error(e);
                }
                IFile bomResource = SpecialFolderUtil
                        .resolveSpecialFolderRelativePath(project,
                                N2PENamingUtils.BOM_SPECIALFOLDER_KIND,
                                referencedBom,
                                true);
                if (bomResource != null) {
                    if (includeIndirectReferences) {
                        addIndirectlyReferencedBomResources(bomResource,
                                processedResources);
                    } else {
                        processedResources.add(bomResource);
                    }
                }
            }
            return processedResources;
        }
        return Collections.emptySet();
    }

    private static void addIndirectlyReferencedBomResources(
            IResource bomResource, Set<IResource> processedResources) {
        if (!processedResources.contains(bomResource)) {
            WorkingCopy workingCopy =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(bomResource);
            if (workingCopy instanceof BOMWorkingCopy) {
                BOMWorkingCopy bomWc = (BOMWorkingCopy) workingCopy;
                processedResources.add(bomResource);
                // Add cross referenced boms
                List<IResource> dependency = bomWc.getDependency();
                for (IResource dependencyBom : dependency) {
                    addIndirectlyReferencedBomResources(dependencyBom,
                            processedResources);
                }
            }
        }
    }

    /**
     * 
     * @return the defautl list class.
     */
    public static Class getDefaultCDSMultipleClass() {
        initDefaultListClasses();

        return defaultMultipleClass;
    }

    /**
     * 
     * @return the default paginated list class.
     */
    public static Class getDefaultCDSPaginatedMultipleClass() {
        initDefaultListClasses();

        return defaultPaginatedMultipleClass;
    }

    /**
     * Initialise the defaultMultipleClass and defaultPaginatedMultipleClass
     * class fields IF they are not initialised yet.
     */
    protected static void initDefaultListClasses() {
        if (defaultMultipleClass == null
                || defaultPaginatedMultipleClass == null) {
            URL entry = CDSActivator.getDefault().getBundle()
                    .getEntry(CdsConsts.CDS_MODEL_FILE_NAME);
            if (entry != null) {
                URI uri = URI.createURI(entry.toExternalForm());
                ResourceSetImpl resourceSet = new ResourceSetImpl();
                resourceSet.getPackageRegistry().put(UMLPackage.eNS_URI,
                        UMLPackage.eINSTANCE);
                resourceSet.getResourceFactoryRegistry()
                        .getExtensionToFactoryMap()
                        .put(UMLResource.FILE_EXTENSION,
                                UMLResource.Factory.INSTANCE);
                try {
                    Resource resource = resourceSet.createResource(uri);
                    resource.load(Collections.EMPTY_MAP);

                    Package umlPackage = (Package) EcoreUtil.getObjectByType(
                            resource.getContents(),
                            UMLPackage.Literals.PACKAGE);

                    List<PackageableElement> packagedElements =
                            umlPackage.getPackagedElements();

                    if (packagedElements != null) {
                        for (PackageableElement element : packagedElements) {
                            if (element instanceof Class) {
                                Class umlClass = (Class) element;

                                if (umlClass.getName() != null) {
                                    if (JsConsts.LIST
                                            .equals(umlClass.getName())) {
                                        defaultMultipleClass = umlClass;
                                    } else if (JsConsts.PAGINATEDLIST
                                            .equals(umlClass.getName())) {
                                        defaultPaginatedMultipleClass =
                                                umlClass;
                                    }
                                }
                            }

                            if (defaultMultipleClass != null
                                    && defaultPaginatedMultipleClass != null) {
                                break;
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static QualifiedName getQualifierName() {
        return new QualifiedName(BOM_SOURCE_PERSISTENT_PROPERTY,
                BOM_SOURCE_PERSISTENT_PROPERTY);
    }

    /**
     * Store Persistent property on the project. This will help to identify the
     * Project for the specific BOM (when BOM is deleted)
     * 
     * @param emfProject
     * @param bomResource
     */
    private static void storeBOMPersistentProperty(IProject emfProject,
            IResource bomResource) {
        try {
            emfProject.setPersistentProperty(getQualifierName(),
                    bomResource.getFullPath().toPortableString());
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a project which has BOM specific persistent property (whose value
     * is the fullPath of the bom resource)
     * 
     * @param bomResource
     * @return
     */
    public static IProject getProjectWithPersistentProperty(
            IResource bomResource) {
        String portableString = bomResource.getFullPath().toPortableString();
        QualifiedName qualifierName = getQualifierName();
        IProject[] projectArr =
                ResourcesPlugin.getWorkspace().getRoot().getProjects();
        IProject toReturn = null;
        for (int index = 0; index < projectArr.length; index++) {
            IProject project = projectArr[index];
            try {
                if (project != null && project.isOpen()) {
                    String persistentProperty =
                            project.getPersistentProperty(qualifierName);
                    if (persistentProperty != null
                            && persistentProperty.equals(portableString)) {
                        toReturn = project;
                        break;
                    }
                }
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        return toReturn;
    }

    /**
     * Generate EMF Project for changed BOM
     * 
     * @param bomResource
     * @return
     * @throws CoreException
     */
    public static List<IncludedPlugin> generateEMFProject(IResource bomResource)
            throws CoreException {
        List<IncludedPlugin> generatedPlugins = new ArrayList<IncludedPlugin>();
        generateDependentEMFProjects(bomResource, generatedPlugins);
        return generatedPlugins;
    }

    /**
     * Generate EMF Projects for all changed BOM & all dependent BOMs (direct or
     * indirect)
     * 
     * @param bomResource
     * @param generatedPlugins
     * @throws CoreException
     */
    private static void generateDependentEMFProjects(IResource bomResource,
            final List<IncludedPlugin> generatedPlugins) throws CoreException {
        TokenTreeMap<IResource> tTreeMap = new TokenTreeMap<IResource>();
        String resourceName = getResourceName(bomResource);
        tTreeMap.put(resourceName, bomResource);
        boolean includeIndirectDependency = true;
        // workOutBOMExternalReference(bomResource,
        // tTreeMap,
        // resourceName,
        // includeIndirectDependency);
        List<IResource> list = tTreeMap.get(resourceName + ".**"); //$NON-NLS-1$
        LinkedHashMap<IResource, List<IProject>> bomsToBuildMap =
                new LinkedHashMap<IResource, List<IProject>>();
        for (int index = list.size() - 1; index > -1; index--) {
            // final IResource eachBomResource = list.get(index);
            // String emfProjectName =
            // BOMGenerator
            // .getOutputProjectNameForBOMResource(eachBomResource);
            // IProject project = CDSUtils.getProjectWithName(emfProjectName);
            // boolean generateEMFProject = false;
            // if (project != null && project.isAccessible()) {
            // long emfProjectCreationTimeStamp = project.getLocalTimeStamp();
            // long eachBOMResTimeStamp = eachBomResource.getLocalTimeStamp();
            // if (eachBOMResTimeStamp > emfProjectCreationTimeStamp) {
            // generateEMFProject = true;
            // }
            // } else {
            // // emf project does not exist
            // generateEMFProject = true;
            // }
            // if (generateEMFProject) {
            // final List<IProject> dependencyProjectList =
            // getEMFProjectDependencyList(list, index);
            // bomsToBuildMap.put(eachBomResource, dependencyProjectList);
            // }
        }
        if (!bomsToBuildMap.isEmpty()) {
            generateEMFProject(generatedPlugins, bomsToBuildMap);
        }

    }

    /**
     * return file name without extension
     * 
     * @param resource
     * @return
     */
    private static String getResourceName(IResource resource) {
        String name = resource.getName();
        int lastIndexOf = name.lastIndexOf(CDSUtils.TOKEN_PATH_SEPARATOR);
        if (lastIndexOf != -1) {
            name = name.substring(0, lastIndexOf);
        }
        return name;
    }

    /**
     * Work out the dependency tree
     * 
     * @param bomResource
     * @param dependencyBOMList
     * @param path
     * @param includeIndirectDependency
     */
    private static void workOutBOMExternalReference(IResource bomResource,
            TokenTreeMap<IResource> dependencyBOMList, String path,
            boolean includeIndirectDependency) {
        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(bomResource);
        if (workingCopy instanceof BOMWorkingCopy) {
            BOMWorkingCopy bomWC = (BOMWorkingCopy) workingCopy;
            List<IResource> dependencyList = bomWC.getDependency();
            if (dependencyList != null) {
                for (IResource dependencyRes : dependencyList) {
                    String resPath = path + CDSUtils.TOKEN_PATH_SEPARATOR
                            + getResourceName(dependencyRes);
                    dependencyBOMList.put(resPath, dependencyRes);
                    if (includeIndirectDependency) {
                        workOutBOMExternalReference(dependencyRes,
                                dependencyBOMList,
                                resPath,
                                includeIndirectDependency);
                    }

                }
            }
        }
    }

    /**
     * For EMF project to be generated, we need to pass the dependency Projects
     * 
     * @param list
     * @param index
     * @return
     */
    private static List<IProject> getEMFProjectDependencyList(
            List<IResource> list, int index) {
        List<IProject> toReturn = new ArrayList<IProject>();
        for (int i = index + 1; i < list.size(); i++) {
            // IResource resource = list.get(i);
            // String outputProjectNameForBOMResource =
            // BOMGenerator.getOutputProjectNameForBOMResource(resource);
            // IProject project2 =
            // ResourcesPlugin.getWorkspace().getRoot()
            // .getProject(outputProjectNameForBOMResource);
            // toReturn.add(project2);

        }
        return toReturn;
    }

    /**
     * generate EMF Project for the passed BOM
     * 
     * @param bomResource
     * @param generatedPlugins
     * @param dependencyProjects
     * @throws CoreException
     * @throws GenerationException
     */
    private static void generateEMFProject(
            final List<IncludedPlugin> generatedPlugins,
            final LinkedHashMap<IResource, List<IProject>> bomsToBuildMap)
            throws CoreException {
        ResourcesPlugin.getWorkspace().run(new EMFProjectGenerationRunnable(
                generatedPlugins, bomsToBuildMap), new NullProgressMonitor());

    }

    public static Image getCDSJavaScriptFactoryIcon() {
        return CDSActivator.getDefault().getImageRegistry()
                .get(CdsConsts.CDS_FACTORY);
    }

    private static Set<IProject> getExternalBOMProjectReferences(IFile file) {
        String fileExtension = file.getFileExtension();
        List<IResource> dependencyList = null;
        if (N2PENamingUtils.XPDL_FILE_EXTENSION.equals(fileExtension)) {
            dependencyList = getBOMDependencyForXpdlFile(file);
        } else if (N2PENamingUtils.BOM_SPECIALFOLDER_KIND
                .equals(fileExtension)) {
            dependencyList = getExternalBOMDependencyForBOMFile(file);
        } else {
            throw new IllegalArgumentException("File Extension not supported"); //$NON-NLS-1$
        }
        Set<IProject> projectSet = new HashSet<IProject>();
        IProject xpdlFileProject = file.getProject();
        for (IResource resource : dependencyList) {
            IProject project = resource.getProject();
            if (!xpdlFileProject.getName().equals(project.getName())) {
                projectSet.add(project);
            }

        }
        return projectSet;

    }

    private static List<IResource> getBOMDependencyForXpdlFile(IFile xpdlFile) {
        List<IResource> bomResourcesList = new ArrayList<IResource>();
        if (!N2Utils.hasN2ProcessesOrInterfaces(xpdlFile)) {
            return bomResourcesList;
        }

        WorkingCopy xpdlWC = WorkingCopyUtil.getWorkingCopy(xpdlFile);
        List<IResource> dependencyList = xpdlWC.getDependency();
        if (dependencyList != null) {
            for (IResource eachResource : dependencyList) {
                WorkingCopy eachResWC =
                        WorkingCopyUtil.getWorkingCopy(eachResource);
                if (eachResWC instanceof BOMWorkingCopy) {
                    bomResourcesList.add(eachResource);
                }
            }
        }

        return bomResourcesList;
    }

    private static List<IResource> getExternalBOMDependencyForBOMFile(
            IFile bomFile) {
        List<IResource> bomResourcesList = new ArrayList<IResource>();
        WorkingCopy xpdlWC = WorkingCopyUtil.getWorkingCopy(bomFile);
        List<IResource> dependencyList = xpdlWC.getDependency();
        if (dependencyList != null) {
            for (IResource eachResource : dependencyList) {
                WorkingCopy eachResWC =
                        WorkingCopyUtil.getWorkingCopy(eachResource);
                if (eachResWC instanceof BOMWorkingCopy) {
                    bomResourcesList.add(eachResource);
                }
            }
        }
        return bomResourcesList;
    }

    public static CdsContentAssistIconProvider getCdsContentAssistIconProvider() {
        if (cdsContentAssistIconProvider == null) {
            cdsContentAssistIconProvider = new CdsContentAssistIconProvider();
        }
        return cdsContentAssistIconProvider;
    }

    public static List<IScriptRelevantData> convertToScriptRelevantData(
            List<? extends ProcessRelevantData> processRelevantDataList,
            IProject project, List<JsClassDefinitionReader> readers) {
        List<IScriptRelevantData> psRelevantData =
                new ArrayList<IScriptRelevantData>();
        if (processRelevantDataList != null) {
            for (Iterator<? extends ProcessRelevantData> iterator =
                    processRelevantDataList.iterator(); iterator.hasNext();) {
                ProcessRelevantData processRelevantData = iterator.next();
                if (processRelevantData != null) {
                    IScriptRelevantData theProcessScriptRelevantData = CDSUtils
                            .convertToScriptRelevantData(processRelevantData,
                                    project,
                                    readers);
                    if (theProcessScriptRelevantData != null) {
                        psRelevantData.add(theProcessScriptRelevantData);
                    }
                }
            }
        }
        return psRelevantData;
    }

    public static IScriptRelevantData convertToScriptRelevantData(
            ProcessRelevantData processRelevantData, IProject project,
            List<JsClassDefinitionReader> readers) {
        if (processRelevantData == null) {
            return null;
        }
        String scriptRelevantDataType = ProcessUtil
                .getProcessScriptRelevantDataType(processRelevantData);
        IScriptRelevantData iScriptRelevantData = null;
        if (scriptRelevantDataType == null) {
            return null;
        }
        // External Reference
        else if (ProcessRelevantDataUtil.CASE_REFERENCE_TYPE
                .equals(scriptRelevantDataType)
                || scriptRelevantDataType.equals(ProcessJsConsts.REFERENCE)) {
            DataType dataType = processRelevantData.getDataType();
            if (dataType instanceof DeclaredType) {
                dataType = ProcessRelevantDataUtil
                        .getFinalDataType(processRelevantData);
            }
            if (dataType instanceof RecordType) {
                RecordType caseRefType = (RecordType) dataType;
                Member member = caseRefType.getMember().get(0);
                ExternalReference externalReference =
                        member.getExternalReference();
                iScriptRelevantData =
                        convertToCaseUMLScriptRelevantData(externalReference,
                                project,
                                ProcessUtil.getImage(processRelevantData),
                                null);
            }
            if (dataType instanceof ExternalReference) {
                iScriptRelevantData =
                        ProcessUtil.convertToUMLScriptRelevantData(
                                (ExternalReference) dataType,
                                project,
                                ProcessUtil.getImage(processRelevantData),
                                null);

                /*
                 * XPD-2209: if it is not a uml class and is a primitive type or
                 * enum return DefaultScriptRelevantData
                 */
                if (null == iScriptRelevantData) {

                    ComplexDataTypeReference complexDataTypeRef =
                            ProcessUtil.xpdl2RefToComplexDataTypeRef(
                                    (ExternalReference) dataType);

                    /*
                     * Sid Fixed NPE when field set to External reference type
                     * but has no reference set.
                     */
                    if (complexDataTypeRef != null) {
                        Object object =
                                ProcessUtil.referenceToComplexDataTypeModel(
                                        complexDataTypeRef,
                                        project);

                        if (object instanceof PrimitiveType
                                || object instanceof Enumeration) {
                            String basicType = ProcessUtil
                                    .getProcessScriptRelevantDataType(
                                            processRelevantData);

                            if (null != basicType && basicType.length() > 0) {
                                /*
                                 * XPD-2711: if the top level element is an
                                 * external reference to primitive type then
                                 * resolve it get the actual basic type
                                 */
                                if (ProcessJsConsts.REFERENCE
                                        .equals(basicType)) {
                                    Object baseType =
                                            BasicTypeConverterFactory.INSTANCE
                                                    .getBaseType(object, true);

                                    if (baseType instanceof BasicType) {
                                        basicType = ProcessUtil.getStrType(
                                                (BasicType) baseType);
                                    }
                                }
                                iScriptRelevantData =
                                        new DefaultScriptRelevantData(basicType,
                                                basicType, processRelevantData
                                                        .isIsArray());
                            }
                        }
                    }
                }
            }
        }
        // Other Basic Type Fields
        else {
            String basicType = ProcessUtil
                    .getProcessScriptRelevantDataType(processRelevantData);
            iScriptRelevantData = new DefaultScriptRelevantData(basicType,
                    basicType, processRelevantData.isIsArray());
            IScriptRelevantData resolvedScriptRelevantData =
                    ProcessUtil.resolveBasicTypeToUML(basicType,
                            processRelevantData.isIsArray(),
                            readers,
                            CDSUtils.getN2JavaScriptType());
            if (resolvedScriptRelevantData instanceof IUMLScriptRelevantData
                    && iScriptRelevantData instanceof ITypeResolution) {
                ((ITypeResolution) iScriptRelevantData).setTypesResolution(
                        Collections.singletonList(resolvedScriptRelevantData));
            }
        }
        /*
         * Pass the information form the processRelevantData to the
         * ScriptRelevantData
         */
        if (iScriptRelevantData != null) {
            iScriptRelevantData = ProcessUtil.setDetailsToScriptRelevantData(
                    processRelevantData,
                    iScriptRelevantData);
        }
        return iScriptRelevantData;
    }

    public static Map<String, String> getN2JavaScriptType() {
        if (N2JAVASCRIPT_TYPE != null) {
            return N2JAVASCRIPT_TYPE;
        }
        N2JAVASCRIPT_TYPE = new HashMap<String, String>();
        // From BOM Primitive types
        N2JAVASCRIPT_TYPE.put(PrimitivesUtil.BOM_PRIMITIVE_ATTACHMENT_NAME,
                PrimitivesUtil.BOM_PRIMITIVE_ATTACHMENT_NAME);
        N2JAVASCRIPT_TYPE.put(PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME,
                JsConsts.BOOLEAN);
        N2JAVASCRIPT_TYPE.put(PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME,
                JsConsts.XML_GREGORIAN_CALENDAR);
        N2JAVASCRIPT_TYPE.put(PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME,
                JsConsts.XML_GREGORIAN_CALENDAR);
        N2JAVASCRIPT_TYPE.put(PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME,
                JsConsts.XML_GREGORIAN_CALENDAR);
        N2JAVASCRIPT_TYPE.put(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME,
                JsConsts.NUMBER);
        N2JAVASCRIPT_TYPE.put(PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME,
                JsConsts.DURATION);
        N2JAVASCRIPT_TYPE.put(PrimitivesUtil.BOM_PRIMITIVE_ID_NAME,
                JsConsts.STRING);
        N2JAVASCRIPT_TYPE.put(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME,
                JsConsts.NUMBER);
        N2JAVASCRIPT_TYPE.put(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME,
                JsConsts.STRING);
        N2JAVASCRIPT_TYPE.put(PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME,
                JsConsts.XML_GREGORIAN_CALENDAR);
        N2JAVASCRIPT_TYPE.put(PrimitivesUtil.BOM_PRIMITIVE_URI_NAME,
                JsConsts.STRING);
        // From Process types
        N2JAVASCRIPT_TYPE.put(JsConsts.CHARACTER, JsConsts.STRING);
        N2JAVASCRIPT_TYPE.put(JsConsts.STRINGLC, JsConsts.STRING);
        N2JAVASCRIPT_TYPE.put(JsConsts.TEXT, JsConsts.STRING);
        N2JAVASCRIPT_TYPE.put(JsConsts.CHAR, JsConsts.STRING);
        N2JAVASCRIPT_TYPE.put(JsConsts.PERFORMER, JsConsts.STRING);
        N2JAVASCRIPT_TYPE.put(JsConsts.INT, JsConsts.NUMBER);
        N2JAVASCRIPT_TYPE.put(JsConsts.FLOAT, JsConsts.NUMBER);
        N2JAVASCRIPT_TYPE.put(JsConsts.PFLOAT, JsConsts.NUMBER);
        N2JAVASCRIPT_TYPE.put(JsConsts.DOUBLE, JsConsts.NUMBER);
        N2JAVASCRIPT_TYPE.put(JsConsts.PDOUBLE, JsConsts.NUMBER);
        N2JAVASCRIPT_TYPE.put(JsConsts.LONG, JsConsts.NUMBER);
        N2JAVASCRIPT_TYPE.put(JsConsts.PLONG, JsConsts.NUMBER);
        N2JAVASCRIPT_TYPE.put(JsConsts.SHORT, JsConsts.NUMBER);
        N2JAVASCRIPT_TYPE.put(JsConsts.DECIMAL, JsConsts.NUMBER);
        N2JAVASCRIPT_TYPE.put(JsConsts.BYTE, JsConsts.UNDEFINED_DATA_TYPE);
        N2JAVASCRIPT_TYPE.put(JsConsts.PBYTE, JsConsts.UNDEFINED_DATA_TYPE);
        // From N2 Factories
        N2JAVASCRIPT_TYPE.put(JsConsts.BIGINTEGER, JsConsts.BIGINTEGER);
        N2JAVASCRIPT_TYPE.put(JsConsts.BIGDECIMAL, JsConsts.BIGDECIMAL);
        N2JAVASCRIPT_TYPE.put(JsConsts.DATETIMELC,
                JsConsts.XML_GREGORIAN_CALENDAR);
        N2JAVASCRIPT_TYPE.put(JsConsts.DATETIMETZLC,
                JsConsts.XML_GREGORIAN_CALENDAR);
        N2JAVASCRIPT_TYPE.put(JsConsts.BOM_DATE,
                JsConsts.XML_GREGORIAN_CALENDAR);

        return N2JAVASCRIPT_TYPE;
    }

    public static boolean isEObjectType(String type) {
        if (type != null && type.equals(JsConsts.BOM_CLASS)) {
            return true;
        }
        return false;
    }

    /**
     * 
     * @param object
     * @return <code>true</code> if the given script relevant data represents a
     *         class from the BOM model, <code>false</code> otherwise
     */
    public static boolean isBDSObject(IScriptRelevantData object) {
        if (object != null && object instanceof IUMLScriptRelevantData) {
            IUMLScriptRelevantData umlData = (IUMLScriptRelevantData) object;
            JsClass jsClass = umlData.getJsClass();
            if (jsClass != null) {
                Class umlClass = jsClass.getUmlClass();
                if (umlClass != null) {
                    WorkingCopy workingCopyFor =
                            WorkingCopyUtil.getWorkingCopyFor(umlClass);
                    if (workingCopyFor instanceof BOMWorkingCopy) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean expectsEObjectParam(JsMethod jsMethod) {
        List<JsMethodParam> parameterType = jsMethod.getParameterType();
        if (parameterType != null) {
            for (JsMethodParam jsMethodParam : parameterType) {
                if (jsMethodParam != null
                        && CDSUtils.isEObjectType(jsMethodParam.getType())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method tells whether the given type can be assigned to an xsdAny
     * 
     * @param type
     * @return
     */
    public static boolean canBeAssignedToXsdAny(IScriptRelevantData type) {
        IScriptRelevantData dataTypeToProcess = type;
        if (JScriptUtils.isGenericType(type.getType())) {
            if (type instanceof ITypeResolution) {
                IScriptRelevantData genericContextType =
                        ((ITypeResolution) type).getGenericContextType();
                if (genericContextType != null) {
                    if (!genericContextType.isArray()
                            && CDSUtils.isBDSObject(genericContextType)) {
                        dataTypeToProcess = genericContextType;
                    }
                }
            }
        }
        if (dataTypeToProcess != null) {
            if (CDSUtils.isBDSObject(dataTypeToProcess)
                    || JScriptUtils.isXsdAny(type)
                    || JScriptUtils.isLiteralNull(dataTypeToProcess)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method tells whether the given type can be assigned to an xsdAnyType
     * 
     * @param type
     * @return
     */
    public static boolean canBeAssignedToXsdAnyType(IScriptRelevantData type) {
        IScriptRelevantData dataTypeToProcess = type;
        if (JScriptUtils.isGenericType(type.getType())) {
            if (type instanceof ITypeResolution) {
                IScriptRelevantData genericContextType =
                        ((ITypeResolution) type).getGenericContextType();
                if (genericContextType != null) {
                    if (!genericContextType.isArray()
                            && CDSUtils.isBDSObject(genericContextType)) {
                        dataTypeToProcess = genericContextType;
                    }
                }
            }
        }
        if (dataTypeToProcess != null) {
            if (JScriptUtils.isXsdAny(dataTypeToProcess)
                    || JScriptUtils.isXsdAnySimpleType(dataTypeToProcess)
                    || JScriptUtils.isXsdAnyAttribute(dataTypeToProcess)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method tells whether the given type can be assigned to an
     * xsdAnySimpleType
     * 
     * @param type
     * @return
     */
    public static boolean canBeAssignedToXsdAnySimpleType(
            IScriptRelevantData type) {
        IScriptRelevantData dataTypeToProcess = type;
        if (JScriptUtils.isGenericType(type.getType())) {
            if (type instanceof ITypeResolution) {
                IScriptRelevantData genericContextType =
                        ((ITypeResolution) type).getGenericContextType();
                if (genericContextType != null) {
                    if (!genericContextType.isArray()
                            && CDSUtils.isBDSObject(genericContextType)) {
                        dataTypeToProcess = genericContextType;
                    }
                }
            }
        }
        if (dataTypeToProcess != null) {
            if (JScriptUtils.isXsdAny(dataTypeToProcess)
                    || JScriptUtils.isXsdAnyType(dataTypeToProcess)
                    || JScriptUtils.isXsdAnyAttribute(dataTypeToProcess)
                    || CDSUtils.isBDSObject(dataTypeToProcess)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method tells whether the given type can be assigned to an any
     * attribute
     * 
     * @param type
     * @return
     */
    public static boolean canBeAssignedToXsdAnyAttribute(
            IScriptRelevantData type) {
        IScriptRelevantData dataTypeToProcess = type;
        if (JScriptUtils.isGenericType(type.getType())) {
            if (type instanceof ITypeResolution) {
                IScriptRelevantData genericContextType =
                        ((ITypeResolution) type).getGenericContextType();
                if (genericContextType != null) {
                    if (!genericContextType.isArray()
                            && CDSUtils.isBDSObject(genericContextType)) {
                        dataTypeToProcess = genericContextType;
                    }
                }
            }
        }
        if (dataTypeToProcess != null) {
            if (JScriptUtils.isXsdAnyAttribute(dataTypeToProcess)
                    || JScriptUtils.isLiteralNull(dataTypeToProcess)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUnion(IScriptRelevantData type) {
        org.eclipse.uml2.uml.DataType sourceDataType =
                JScriptUtils.getDataType(type);
        if (null != sourceDataType) {
            boolean isSourceUnion = XSDUtil.isUnion(sourceDataType);
            if (isSourceUnion) {
                return true;
            }
        }
        return false;
    }

    // XPD-3129 Global Data - moved from
    // CdsfactoriesJavaScriptRelevantDataProvider
    public static List<Package> getReferencedBomPackages(Activity activity,
            Process process, com.tibco.xpd.xpdl2.Package packaze,
            List<ProcessRelevantData> associatedProcessRelevantData) {
        List<Package> bomPackages = new ArrayList<Package>();
        if (activity != null) {
            if (associatedProcessRelevantData != null
                    && !associatedProcessRelevantData.isEmpty()) {
                Set<String> referencedBomFiles = new HashSet<String>();
                for (ProcessRelevantData processRelevantData : associatedProcessRelevantData) {
                    if (processRelevantData != null && (processRelevantData
                            .getDataType() instanceof ExternalReference
                            || processRelevantData
                                    .getDataType() instanceof RecordType)) {
                        ExternalReference externalRef = null;
                        // XPD-3129: Factories should also be available if only
                        // Case Ref types are in scope
                        if (processRelevantData
                                .getDataType() instanceof RecordType) {
                            RecordType record = (RecordType) processRelevantData
                                    .getDataType();
                            Member member = record.getMember().get(0);
                            externalRef = member.getExternalReference();

                        } else {
                            externalRef =
                                    (ExternalReference) processRelevantData
                                            .getDataType();
                        }
                        if (externalRef != null
                                && externalRef.getLocation() != null) {
                            referencedBomFiles.add(externalRef.getLocation());
                        }
                    }
                }
                if (referencedBomFiles != null
                        && !referencedBomFiles.isEmpty()) {
                    IProject project = WorkingCopyUtil.getProjectFor(activity);
                    if (project != null) {
                        // Include indirect dependency
                        Set<IResource> referencedBomResources = CDSUtils
                                .getReferencedBomResources(referencedBomFiles,
                                        true,
                                        project);
                        bomPackages.addAll(CDSUtils.getReferencedBomPackages(
                                referencedBomResources));
                    }
                }
            }
        } else {
            if (process != null) {
                bomPackages.addAll(CDSUtils.getReferencedBomPackages(process));
            }
            if (packaze != null) {
                com.tibco.xpd.xpdl2.Package xpdlPackage = packaze;
                bomPackages
                        .addAll(CDSUtils.getReferencedBomPackages(xpdlPackage));
            }
        }
        return bomPackages;
    }

    public static IScriptRelevantData convertToCaseScriptRelevantData(
            ProcessRelevantData processRelevantData, IProject project,
            List<JsClassDefinitionReader> readers) {

        IScriptRelevantData iScriptRelevantData = null;

        String scriptRelevantDataType = ProcessUtil
                .getProcessScriptRelevantDataType(processRelevantData);
        if (scriptRelevantDataType == null) {
            return null;
        }

        if (ProcessRelevantDataUtil.CASE_REFERENCE_TYPE
                .equals(scriptRelevantDataType)) {
            DataType dataType = processRelevantData.getDataType();
            if (dataType instanceof RecordType) {
                RecordType caseRefType = (RecordType) dataType;
                Member member = caseRefType.getMember().get(0);
                ExternalReference externalReference =
                        member.getExternalReference();
                iScriptRelevantData = ProcessUtil
                        .convertToUMLScriptRelevantData(externalReference,
                                project,
                                ProcessUtil.getImage(processRelevantData),
                                ProcessUtil
                                        .getMultipleClassFromReaders(readers));
            }
        }

        return iScriptRelevantData;
    }

    public static IUMLScriptRelevantData convertToCaseUMLScriptRelevantData(
            ExternalReference extRef, IProject project, Image icon,
            Class multipleUmlClass) {

        CaseUMLScriptRelevantData caseUMLScriptRelevantData = null;
        Class umlClass = null;

        if (null != extRef) {
            ComplexDataTypeReference complexDataTypeRef =
                    ProcessUtil.xpdl2RefToComplexDataTypeRef(extRef);
            if (null != project && null != complexDataTypeRef) {
                umlClass = ProcessUtil
                        .getComplexDataTypeModel(complexDataTypeRef, project);
                if (null != umlClass) {
                    caseUMLScriptRelevantData = JScriptUtils
                            .convertToCaseUMLScriptRelevantData(umlClass);
                }
            }
        }

        return caseUMLScriptRelevantData;
    }

    public static IUMLScriptRelevantData convertToUMLScriptRelevantData(
            ExternalReference extRef, IProject project,
            String specialFolderKind) {

        IUMLScriptRelevantData scriptRelevantData = null;
        Class umlClass = null;

        if (null != extRef) {
            ComplexDataTypeReference complexDataTypeRef =
                    ProcessUtil.xpdl2RefToComplexDataTypeRef(extRef);
            if (null != project && null != complexDataTypeRef) {
                umlClass =
                        ProcessUtil.getComplexDataTypeModel(complexDataTypeRef,
                                project,
                                specialFolderKind);
                if (null != umlClass) {
                    UmlJsonSchemaLabelProvider lp =
                            new UmlJsonSchemaLabelProvider();
                    scriptRelevantData = JScriptUtils
                            .convertToUMLScriptRelevantData(umlClass, lp);
                }
            }
        }

        return scriptRelevantData;
    }

}
