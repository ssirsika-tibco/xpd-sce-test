/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.wsdltobom.indexer;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Import;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDNamedComponent;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSchemaDirective;
import org.eclipse.xsd.XSDSimpleTypeDefinition;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.BOMProfileUtils;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.wsdltransform.builder.BusinessObjectRelevantData;
import com.tibco.xpd.bom.wsdltransform.builder.XSDRelevantData;
import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.bom.xsdtransform.utils.Bom2XsdUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderFileBindingUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;
import com.tibco.xpd.wsdl.ui.WsdlWorkingCopy;

/**
 * Interested in indexing the XSD Elements in the WSDL to the BOM that is
 * generated for the WSDL. Also, for auto-generated WSDLs, indexes the BOMs that
 * have been referenced by the generating XPDL.
 * 
 * @author rsomayaj
 * 
 */

public class WsdlBomIndexerProvider implements WorkingCopyIndexProvider {

    /**
     * This is to mark top level elements as "TLE" in the indexer.
     */
    public static final String TYPE_OR_DERIVED_FROM_TYPE_TLE = "TLE"; //$NON-NLS-1$

    /**
     * 
     */
    public static final String WSDL_INDEX_TYPE = "WSDL"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String BLANK_ASCI = "%20"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String BLANK = " "; //$NON-NLS-1$

    public static final String ATTRIBUTE_XSD_NAMESPACE = "xsd_namespace"; //$NON-NLS-1$

    public static final String ATTRIBUTE_BOM_FILE_NAME = "bom_file_name"; //$NON-NLS-1$

    public static final String ATTRIBUTE_DERIVED_FROM_TYPE =
            "derived_from_type"; //$NON-NLS-1$

    public static final String ATTRIBUTE_BOM_NAMESPACE = "bom_namespace"; //$NON-NLS-1$

    public static final String ATTRIBUTE_BOM_ELEMENT_NAME = "bom_element_name"; //$NON-NLS-1$

    public static final String ATTRIBUTE_XSD_ELEMENT_NAME = "xsd_element_name"; //$NON-NLS-1$

    public static final String ATTRIBUTE_WSDL_FILE_NAME = "wsd_file_name"; //$NON-NLS-1$

    public static final String ATTRIBUTE_BOM_ID = "bom_element_id"; //$NON-NLS-1$

    private boolean debug = false;

    /**
     * 
     */
    public WsdlBomIndexerProvider() {
    }

    /**
     * @see com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider#getIndexItems(com.tibco.xpd.resources.WorkingCopy)
     * 
     * @param wc
     * @return
     */
    public Collection<IndexerItem> getIndexItems(WorkingCopy wc) {
        ArrayList<IndexerItem> items = new ArrayList<IndexerItem>();
        IResource resource = wc.getEclipseResources().get(0);
        String projectName = null;
        if (resource != null && resource.getProject() != null) {
            projectName = resource.getProject().getName();
        }
        if (wc instanceof BOMWorkingCopy) {
            // This is when the BOM is generated from the WSDL. Needs to listen
            // to the BOM Working Copy and not the WSDL Working Copy because
            // we're unsure that the indexer will be triggered much ahead of the
            // BOMs being generated from the WSDL.

            /*
             * When we are passed a BOM resource that was derived from a WSDL we
             * want to create index items that cross-reference BOM classes with
             * the WSDL schema elements that they were generated to represent.
             * 
             * We do this by finding the WSDL that this BOM was generated for
             * (including when a separate BOM is generated for an XSD schema
             * that is imported from the WSDL.
             * 
             * We drill down thru the WSDL and it's imported / inline schemas
             * looking for schema elements that are in the same namespace as
             * xsdTargetNamespace stored the BOM file (the namespace of the
             * original wsdl / imported xsd it was generated from).
             */

            /*
             * Get the xsdTargetNamespace from BOM model XsdBaseModel
             * stereotype, we will be indexing all elements in this namespace
             * that we find in/under the wsdl.
             */
            String bomsXsdTargetNamespace =
                    findBomsXsdTargetNamespace(resource);

            /*
             * XPD-6062: Use Bom2XsdUtil method (removed local copy of same
             * method).
             */
            Set<IResource> srcFiles =
                    Bom2XsdUtil.findRelevantWSDLOrXSDFiles(resource);

            Set<IPath> processedSchemaPaths = new HashSet<IPath>();
            if (bomsXsdTargetNamespace != null
                    && bomsXsdTargetNamespace.length() > 0) {
                for (IResource wsdlOrXsdFile : srcFiles) {
                    if (processedSchemaPaths.contains(wsdlOrXsdFile
                            .getFullPath())) {
                        // This XSD has already been processed
                        continue;
                    }
                    WorkingCopy wsdlorXsdWC =
                            XpdResourcesPlugin.getDefault()
                                    .getWorkingCopy(wsdlOrXsdFile);
                    EObject wsdlOrXsdRootElement = wsdlorXsdWC.getRootElement();
                    String wsdlOrXsdPath =
                            wsdlOrXsdFile.getFullPath().toPortableString();
                    if (wsdlOrXsdRootElement != null) {
                        updateWSDLIndex(items,
                                projectName,
                                wsdlOrXsdPath,
                                wsdlOrXsdPath,
                                wsdlOrXsdRootElement,
                                bomsXsdTargetNamespace,
                                processedSchemaPaths);
                    }
                }
            }
        } else if (wc instanceof WsdlWorkingCopy) {
            WsdlWorkingCopy wsdlWC = (WsdlWorkingCopy) wc;

            // This is when the WSDL file is "derived" from the XPDL process.
            // This creates index items that relates the BOM that is referred to
            // by the XPDL and WSDL that the XPDL generates.
            if ((containsXpdlAttribute(resource) || resource.isDerived())
                    && isWSDLContainedInSpecialFolder(resource)) {
                // Just to get the naming right
                IResource wsdlFile = resource;
                IProject project = wsdlFile.getProject();
                IFile xpdlFile = findCorrespondingProcess(wsdlFile);
                String formattedXpdlLocation = null;
                if (xpdlFile != null) {
                    IPath fullPath = xpdlFile.getFullPath();
                    Set<IResource> queryReferencingBomResources =
                            Collections.emptySet();

                    // The hightlight of the problem with having to retrieve
                    // data for the WSDL to BOM Indexer using the Process to BOM
                    // Indexer is that the sequence of indexer triggering cannot
                    // be decided.

                    // For indexing service task WSDLs, need to go to the model,
                    // and fetch the data because the Process to BOM indexer
                    // doesn't have the required data ready.
                    if (isServiceTaskWsdl(wsdlFile)) {
                        queryReferencingBomResources =
                                getBOMResourcesFromModel(wsdlFile);
                    } else {
                        // For API activities - it is still retrieved from the
                        // Process to BOM Indexer.
                        queryReferencingBomResources =
                                getBOMResourcesFromIndexer(project,
                                        formattedXpdlLocation,
                                        fullPath);
                    }
                    for (IResource bomRes : queryReferencingBomResources) {
                        WorkingCopy bomWorkingCopy =
                                XpdResourcesPlugin.getDefault()
                                        .getWorkingCopy(bomRes);

                        // For each of the BOM resources
                        String bomPath =
                                bomRes.getFullPath().toPortableString();
                        updateBOMIndex(items,
                                projectName,
                                wsdlFile.getFullPath().toPortableString(),
                                bomWorkingCopy.getRootElement(),
                                wsdlWC,
                                bomPath);
                    }
                }
            }

        }

        return items;
    }

    /**
     * 
     * TODO: understand the impact if the BOM resources are in sub-folders
     * within the special folders.
     * 
     * @param wsdlFile
     * @return
     */
    private Set<IResource> getBOMResourcesFromModel(IResource wsdlFile) {
        Set<IResource> bomResources = new HashSet<IResource>();
        Definition definition = getDefinition(wsdlFile);
        if (definition != null && definition.getETypes() != null) {
            /**
             * Identify the BOMs from the schema namespaces of the schemas that
             * exist in the WSDL.
             * 
             * Earlier, we used to use the Service task id to identify the
             * process and find the BOMs that are referred from the Process.
             * Think that this is too indirect and should be able to directly
             * identify the BOMs from the namespace of the schemas
             */

            List schemas = definition.getETypes().getSchemas();
            IProject project = wsdlFile.getProject();

            Set<IProject> referencingProjects = new HashSet<IProject>();

            referencingProjects.add(project);

            Set<IProject> projHierarchy = Collections.emptySet();
            // If it is a service task WSDL then the WSDL can contain XSDs =
            // to BOM's where BOM can refer to BOMs in referenced Projects.
            projHierarchy =
                    ProjectUtil.getReferencedProjectsHierarchy(project,
                            referencingProjects);
            for (Object object : schemas) {
                if (object instanceof XSDSchema) {
                    XSDSchema xsdSchema = (XSDSchema) object;
                    String targetNamespace = xsdSchema.getTargetNamespace();
                    if (targetNamespace.equals(definition.getTargetNamespace())) {
                        continue;
                    }

                    String javaPackageNameFromNamespaceURI =
                            XSDUtil.getJavaPackageNameFromNamespaceURI(wsdlFile
                                    .getProject(), targetNamespace);

                    /**
                     * 
                     */
                    boolean bomFound = false;
                    for (IProject prj : projHierarchy) {
                        ArrayList<IResource> resourcesInSpecialFolderOfKind =
                                SpecialFolderUtil
                                        .getResourcesInSpecialFolderOfKind(prj,
                                                BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND,
                                                BOMResourcesPlugin.BOM_FILE_EXTENSION);

                        for (IResource resource : resourcesInSpecialFolderOfKind) {
                            WorkingCopy workingCopy =
                                    WorkingCopyUtil.getWorkingCopy(resource);
                            if (workingCopy != null
                                    && workingCopy.getRootElement() instanceof Model) {
                                String modelName =
                                        ((Model) workingCopy.getRootElement())
                                                .getName();
                                if (javaPackageNameFromNamespaceURI
                                        .equals(modelName)) {
                                    bomResources.add(resource);
                                    bomFound = true;
                                    break;

                                }
                            }

                        }
                        if (bomFound) {
                            break;
                        }
                    }

                }
            }

        }
        return bomResources;
    }

    /**
     * @param correspondingXpdl
     */
    private com.tibco.xpd.xpdl2.Package getPackage(IFile correspondingXpdl) {
        WorkingCopy workingCopy =
                XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(correspondingXpdl);
        if (workingCopy != null) {
            EObject rootElement = workingCopy.getRootElement();
            if (rootElement instanceof com.tibco.xpd.xpdl2.Package) {
                return (com.tibco.xpd.xpdl2.Package) rootElement;
            }
        }
        return null;
    }

    /**
     * @param wsdlFile
     * @return
     */
    private boolean isServiceTaskWsdl(IResource wsdlFile) {
        Definition definition = getDefinition(wsdlFile);
        if (null != definition) {
            String serviceTaskAttrib =
                    definition.getElement()
                            .getAttribute(WsdlUIPlugin.TIBEX_SERVICE_TASK);
            if (null != serviceTaskAttrib && serviceTaskAttrib.length() > 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param wsdlFile
     * @return
     */
    private Definition getDefinition(IResource wsdlFile) {
        WorkingCopy workingCopy =
                XpdResourcesPlugin.getDefault().getWorkingCopy(wsdlFile);
        EObject rootElement = workingCopy.getRootElement();
        if (rootElement instanceof Definition) {
            return (Definition) rootElement;
        }
        return null;
    }

    /**
     * @param project
     * @param formattedXpdlLocation
     * @param fullPath
     * @return
     */
    private Set<IResource> getBOMResourcesFromIndexer(IProject project,
            String formattedXpdlLocation, IPath fullPath) {
        URI platformResourceURI =
                org.eclipse.emf.common.util.URI
                        .createPlatformResourceURI(fullPath.toPortableString(),
                                false);
        if (null != platformResourceURI) {
            formattedXpdlLocation =
                    platformResourceURI.toString().replace(BLANK, BLANK_ASCI);
        }

        Set<IResource> queryReferencingBomResources = Collections.emptySet();
        if (null != formattedXpdlLocation) {
            queryReferencingBomResources =
                    ProcessUIUtil.queryReferencingBomResources(project,
                            formattedXpdlLocation,
                            false);
        } else {
            if (null != fullPath) {
                queryReferencingBomResources =
                        ProcessUIUtil.queryReferencingBomResources(project,
                                fullPath.toPortableString(),
                                false);
            }
        }
        return queryReferencingBomResources;
    }

    /**
     * @param resource
     * @return
     */
    private boolean containsXpdlAttribute(IResource resource) {
        WorkingCopy workingCopy =
                XpdResourcesPlugin.getDefault().getWorkingCopy(resource);
        if (null != workingCopy) {
            if (workingCopy instanceof WsdlWorkingCopy) {
                return ((WsdlWorkingCopy) workingCopy).isWsdlStudioGenerated();
            }
        }
        return false;
    }

    /**
     * The XPDL/DFLOW file that corresponds to a WSDL file is that one with the
     * same name but with the XPDL/DFLOW extension. Searched for all those in
     * the appropriate special folder
     * 
     * @param wsdlFile
     * @return
     */
    private IFile findCorrespondingProcess(IResource wsdlFile) {
        Definition definition = getDefinition(wsdlFile);
        if (null != definition) {
            String xpdlAttrib =
                    definition.getElement()
                            .getAttribute(WsdlUIPlugin.TIBEX_XPDL_PLACEHOLDER);
            if (xpdlAttrib != null && xpdlAttrib.length() > 0) {
                IProject project = wsdlFile.getProject();
                String[] specialFolderKinds = getSpecialFolderKinds(xpdlAttrib);

                if (specialFolderKinds.length > 0) {
                    /*
                     * Look for the file in the local project first
                     */
                    for (String folderKind : specialFolderKinds) {
                        IFile file =
                                SpecialFolderUtil
                                        .resolveSpecialFolderRelativePath(project,
                                                folderKind,
                                                xpdlAttrib);

                        if (file != null) {
                            return file;
                        }
                    }

                    /*
                     * File not found in local project so search referencing
                     * projects
                     */
                    Set<IProject> referencingProjects =
                            new LinkedHashSet<IProject>();

                    referencingProjects =
                            ProjectUtil
                                    .getReferencingProjectsHierarchy(project,
                                            referencingProjects);

                    for (IProject prj : referencingProjects) {
                        for (String folderKind : specialFolderKinds) {
                            IFile file =
                                    SpecialFolderUtil
                                            .resolveSpecialFolderRelativePath(prj,
                                                    folderKind,
                                                    xpdlAttrib);
                            if (file != null) {
                                return file;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get the special folder kinds that will potentially contain a file with
     * the given path.
     * 
     * @param filePath
     * @return array of special folder kinds, empty array if none found.
     */
    private String[] getSpecialFolderKinds(String filePath) {
        IPath path = new Path(filePath);
        return SpecialFolderFileBindingUtil.getInstance()
                .getSpecialFolderKinds(path.lastSegment());
    }

    /**
     * @param bomFile
     * 
     * @return The target namespace of the wsdl/xsd that the given BOM was
     *         generated from.
     */
    private String findBomsXsdTargetNamespace(IResource bomFile) {
        WorkingCopy bomWC =
                XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
        if (bomWC != null) {
            EObject rootElement = bomWC.getRootElement();
            if (rootElement instanceof Model) {
                Model model = (Model) rootElement;
                List<Stereotype> appliedStereotypes =
                        model.getAppliedStereotypes();
                Stereotype stereoType = null;
                for (Stereotype st : appliedStereotypes) {
                    if (st.getName().equals(XsdStereotypeUtils.XSD_BASED_MODEL)) {
                        stereoType = st;
                        break;
                    }
                }

                if (stereoType != null) {
                    Object xsdTargetNamespace =
                            model.getValue(stereoType,
                                    XsdStereotypeUtils.XSD_TARGET_NAMESPACE);
                    if (xsdTargetNamespace != null
                            && xsdTargetNamespace instanceof String) {
                        return (String) xsdTargetNamespace;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get an URI for the given EObject. For a workspace resource this will
     * return a truncated URI (with the prefix "platform:/resource" stripped
     * out).
     * 
     * @param eo
     * @return
     */
    private String getURI(EObject eo) {
        if (eo != null) {
            URI uri = EcoreUtil.getURI(eo);
            if (uri.isPlatformResource()) {
                String fragment = uri.fragment();
                String uriStr = uri.toPlatformString(true);

                if (fragment != null) {
                    uriStr += "#" + fragment; //$NON-NLS-1$
                }
                return uriStr;
            } else {
                return uri.toString();
            }
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * create an resource item entry
     * 
     * @param string
     * 
     * @param name
     * @param uri
     * @param type
     * @param bomId
     */
    void createResourceItem(Collection<IndexerItem> list, String projectName,
            String type, String wsdlPath, String wsdlFilePath,
            String wsdlXsdNamespace, String derivedFromType,
            String wsdlXsdElementName, String bomEObjectURI,
            String bomElementName, String bomFileURI, String bomNamespace,
            String bomId) {

        if (debug) {
            System.out.println("____________________________________"); //$NON-NLS-1$
            System.out.println("Type-->" + type); //$NON-NLS-1$
            System.out.println("wsdlPath-->" + wsdlPath); //$NON-NLS-1$
            System.out.println("wsdlFilePath-->" + wsdlFilePath); //$NON-NLS-1$
            System.out.println("wsdlXsdNamespace-->" + wsdlXsdNamespace); //$NON-NLS-1$
            System.out.println("derivedFromType-->" + derivedFromType); //$NON-NLS-1$
            System.out.println("wsdlXsdElementName-->" + wsdlXsdElementName); //$NON-NLS-1$
            System.out.println("bomEObjectURI-->" + bomEObjectURI); //$NON-NLS-1$
            System.out.println("bomElementName-->" + bomElementName); //$NON-NLS-1$
            System.out.println("bomFileURI-->" + bomFileURI); //$NON-NLS-1$
            System.out.println("bomNamespace-->" + bomNamespace); //$NON-NLS-1$
            System.out.println("bomId-->" + bomId); //$NON-NLS-1$
            System.out.println("____________________________________"); //$NON-NLS-1$
        }

        HashMap<String, String> map = new HashMap<String, String>();
        // XSD Element name
        map.put(ATTRIBUTE_XSD_ELEMENT_NAME, wsdlXsdElementName);
        // WSDL element name
        map.put(ATTRIBUTE_XSD_NAMESPACE, wsdlXsdNamespace);

        // Derived BOM File URI
        map.put(ATTRIBUTE_BOM_FILE_NAME, bomFileURI);
        // Derived from Type
        map.put(ATTRIBUTE_DERIVED_FROM_TYPE, derivedFromType);
        // Derived BOM Package Namespace URI
        map.put(ATTRIBUTE_BOM_NAMESPACE, bomNamespace);
        // Derived BOM Class Name
        map.put(ATTRIBUTE_BOM_ELEMENT_NAME, bomElementName);
        // WSDL Path as the path sets itself to the working copy related path
        // value
        map.put(ATTRIBUTE_WSDL_FILE_NAME, wsdlFilePath);
        map.put(ATTRIBUTE_BOM_ID, bomId);

        /*
         * XPD-2272: Use an extended IndexerItemImpl that has the hashCode and
         * equals methods implemented. This means we can have a unique list of
         * indexer items passed back to the indexer. In some include/import
         * chains this indexer was adding multiple entries in the list for the
         * same schema causing a duplicate primary key violation in the
         * database.
         */
        MyIndexerItem item =
                new MyIndexerItem(wsdlXsdElementName, type, bomEObjectURI, map);

        if (!list.contains(item)) {
            list.add(item);
        }
    }

    /**
     * Called while indexing the WSDL <code>IResource</code>
     * 
     * @param items
     * @param projectName
     * @param path
     * @param bomPath
     * @param wsdlPath
     * @param root
     */
    private void updateBOMIndex(ArrayList<IndexerItem> items,
            String projectName, String path, EObject bomElement,
            WsdlWorkingCopy wsdlWC, String bomPath) {

        IResource wsdlResource = wsdlWC.getEclipseResources().get(0);
        String wsdlPath = wsdlResource.getFullPath().toPortableString();

        List<EObject> contents = bomElement.eContents();
        Iterator<EObject> it = contents.iterator();

        if (bomElement instanceof Package) {

            Package umlPkg = (Package) bomElement;
            String bomElementName = umlPkg.getName();
            String derivedFromType = umlPkg.eClass().getName();
            String bomPkgName = bomElementName;
            String bomEObjectURI = wsdlPath + "#BOMELEMENT#" + getURI(umlPkg); //$NON-NLS-1$

            // Need to map the WSDL schema elements to the BOM elements. In
            // the XPDL to WSDL builder, there were XSD Schema elements
            // generated for the WSDL from the BOM, if we could index those,
            // then we should have covered indexing most of the BOMs

            XSDRelevantData xsdRelevantData =
                    new XSDRelevantData(wsdlWC.getRootElement(), bomElement);
            if (xsdRelevantData != null
                    && xsdRelevantData.getXsdElementName() != null) {
                createResourceItem(items,
                        projectName,
                        derivedFromType,
                        wsdlPath,
                        wsdlPath,
                        xsdRelevantData.getTargetNamespace(),
                        derivedFromType,
                        xsdRelevantData.getXsdElementName(),
                        bomEObjectURI,
                        bomElementName,
                        bomPath,
                        bomPkgName,
                        bomElement.eResource().getURIFragment(bomElement));
            }

            if (isImportedFromXsd(umlPkg)) {
                /*
                 * Dealing with a BOM generated from XSD. Create index entries
                 * for any top level elements (for which we do not have a real
                 * class, just sterotypes on the BOM uml model element.
                 */
                List<Object> topLevelElements =
                        XSDUtil.getTopLevelElements(umlPkg.getModel());

                if (topLevelElements != null) {
                    for (Object tle : topLevelElements) {
                        Classifier cls = XSDUtil.getTopLevelElementType(tle);

                        if (cls instanceof org.eclipse.uml2.uml.Class) {

                            bomElementName = cls.getName();

                            /*
                             * XPD-4838: marking tle as tle (earlier they were
                             * marked as class)
                             */
                            derivedFromType = TYPE_OR_DERIVED_FROM_TYPE_TLE;
                            bomPkgName = cls.getPackage().getName();
                            bomEObjectURI =
                                    wsdlPath + "#BOMELEMENT#" + getURI(cls); //$NON-NLS-1$

                            String topLevelElementName =
                                    XSDUtil.getTopLevelElementName(tle);

                            String uri = bomEObjectURI + "#ELEMENT#" //$NON-NLS-1$
                                    + topLevelElementName;
                            createResourceItem(items,
                                    projectName,
                                    derivedFromType,
                                    wsdlPath,
                                    wsdlPath,
                                    XSDUtil.getNamespaceForPackage(cls
                                            .getNearestPackage()),
                                    derivedFromType,
                                    topLevelElementName,
                                    uri,
                                    bomElementName,
                                    bomPath,
                                    bomPkgName,
                                    bomElement.eResource().getURIFragment(cls));
                        }
                    }
                }
            }
        } else if (bomElement instanceof org.eclipse.uml2.uml.Class) {

            // If the BOM has not been derived from
            org.eclipse.uml2.uml.Class cls =
                    (org.eclipse.uml2.uml.Class) bomElement;

            String bomElementName = cls.getName();
            String bomPkgName = cls.getPackage().getName();
            String bomEObjectURI = wsdlPath + "#BOMELEMENT#" + getURI(cls); //$NON-NLS-1$

            /*
             * Need to map the WSDL schema elements to the BOM elements. In the
             * XPDL to WSDL builder, there were XSD Schema elements generated
             * for the WSDL from the BOM, if we could index those, then we
             * should have covered indexing most of the BOMs
             */
            XSDRelevantData xsdRelevantData =
                    new XSDRelevantData(wsdlWC.getRootElement(), bomElement);
            if (xsdRelevantData != null
                    && xsdRelevantData.getXsdElementName() != null) {
                String derivedFromType = cls.eClass().getName();
                createResourceItem(items,
                        projectName,
                        derivedFromType,
                        wsdlPath,
                        wsdlPath,
                        xsdRelevantData.getTargetNamespace(),
                        derivedFromType,
                        xsdRelevantData.getXsdElementName(),
                        bomEObjectURI,
                        bomElementName,
                        bomPath,
                        bomPkgName,
                        bomElement.eResource().getURIFragment(bomElement));

                /*
                 * Need to check whether the BOM classifier has been marked for
                 * no element creation
                 */
                if (BOMUtils.isExportAsTLE(cls)) {
                    String topLevelElementName =
                            getGenWsdlTopLevelElementForClass(cls);
                    /*
                     * XPD-4838: marking tle as tle (earlier they were marked as
                     * class)
                     * 
                     * XPD-7025: Only index TLE's if we are going to index the
                     * type itself! If the type isn't referenced in schema in
                     * WSDL then we won't need the TLE for it either. If we
                     * don't so this then
                     * WsdlBomIndexerUtil.queryBOMsForWSDL(wsdlResource) will
                     * return BOMs that are referenced by source XPDL of
                     * generated WSDL _just as data fields_ not necessarily as
                     * params to WSDL. This then can mess up anything trying to
                     * establish what BOMs are associated with a generated WSDL
                     * (Like SccriptDescriptor building).
                     */
                    String uri =
                            bomEObjectURI + "#ELEMENT#" + topLevelElementName; //$NON-NLS-1$
                    createResourceItem(items,
                            projectName,
                            TYPE_OR_DERIVED_FROM_TYPE_TLE,
                            wsdlPath,
                            wsdlPath,
                            XSDUtil.getNamespaceForPackage(cls
                                    .getNearestPackage()),
                            TYPE_OR_DERIVED_FROM_TYPE_TLE,
                            topLevelElementName,
                            uri,
                            bomElementName,
                            bomPath,
                            bomPkgName,
                            bomElement.eResource().getURIFragment(bomElement));
                }

            }
        } else if (bomElement instanceof Enumeration) {

        } else if (bomElement instanceof PrimitiveType) {

        }

        while (it.hasNext()) {
            updateBOMIndex(items, projectName, path, it.next(), wsdlWC, bomPath);
        }
    }

    /**
     * When adding classes to generated wsdls we add equivalent top level
     * elements for them, this method returns the name of that.
     * 
     * @param cls
     * @return
     */
    private String getGenWsdlTopLevelElementForClass(
            org.eclipse.uml2.uml.Class cls) {

        return cls.getName() + "Element"; //$NON-NLS-1$
    }

    /**
     * Utility method to check whether the BOM being worked on is a BOM imported
     * from an XSD.
     * 
     * @param eObj
     * @return
     */
    private boolean isImportedFromXsd(EObject eObj) {

        WorkingCopy bomWC = WorkingCopyUtil.getWorkingCopyFor(eObj);
        if (bomWC != null) {
            EObject rootElement = bomWC.getRootElement();
            if (rootElement instanceof Model) {
                Model model = (Model) rootElement;
                return BOMProfileUtils.isProfileAppliedToModel(model,
                        Activator.PATHMAP_XSDNOTATION_PROFILE);
            }
        }
        return false;
    }

    /**
     * @param items
     * @param projectName
     * @param indexingWsdlOrXsdPath
     * @param xsdSchemaPath
     * @param element
     * @param bomsXsdTargetNamespace
     *            WSDL or XSD schema xsdTargetNamespace name space that BOM was
     *            created for. Only index the BOM items for these WSDL items if
     *            they were in the wsdl/xsd namespace othewise...
     * 
     *            When iterating thru a WSDL for a ".wsdl.bom we might recurs
     *            down thru an imported different namespace schema, in this case
     *            it will have it's OWN BOM file generated and we don't want to
     *            duplicate index items for that.
     * 
     *            When iterating through a WSDL for a bom that was generated
     *            from an imported different namespace XSD schema then we don't
     *            want to duplicate index items for the top level WSDL elements
     *            because they will have been indexed when the BOM generated
     *            from the WSDL was indexed.
     * @param schemaLocations
     *            list of schema locations already parsed. A schema location
     *            will contain XSD elements/complex types. In a given WSDL it
     *            may be imported more than once. We wouldn't want to index the
     *            same XSD over and again. So we
     */
    private void updateWSDLIndex(ArrayList<IndexerItem> items,
            String projectName, String indexingWsdlOrXsdPath,
            String xsdSchemaPath, EObject element,
            String bomsXsdTargetNamespace, Set<IPath> schemaLocations) {

        List<EObject> contents = element.eContents();
        Iterator<EObject> it = contents.iterator();
        String wsdlURI = ProcessUIUtil.getURIString(element, true);

        // Do we need to add a row to map file names.
        if (element instanceof XSDSchema
                || element instanceof XSDComplexTypeDefinition
                || element instanceof XSDSimpleTypeDefinition
                || element instanceof XSDElementDeclaration) {

            String targetNamespace = null;
            String wsdlElementName = null;
            String derivedFromType = null;
            if (element instanceof XSDSchema) {
                targetNamespace = ((XSDSchema) element).getTargetNamespace();

            } else if (element instanceof XSDElementDeclaration) {
                XSDElementDeclaration elementDeclaration =
                        (XSDElementDeclaration) element;
                XSDSchema schema = elementDeclaration.getSchema();
                wsdlElementName = elementDeclaration.getName();
                targetNamespace = schema.getTargetNamespace();

            } else if (element instanceof XSDNamedComponent) {
                XSDNamedComponent namedComponent = (XSDNamedComponent) element;
                targetNamespace = namedComponent.getTargetNamespace();
                wsdlElementName = namedComponent.getName();
            }
            derivedFromType = element.eClass().getName();

            /*
             * Only index for WSDL items that are in the same namespace as the
             * on either BOM was generated for.
             */
            if (bomsXsdTargetNamespace.equals(targetNamespace)) {
                // WSDL part can only be associated with root level elements or
                // complex types.
                if (element.eContainer() instanceof XSDSchema) {

                    BusinessObjectRelevantData bomData =
                            getBomData(element, indexingWsdlOrXsdPath);
                    // There are some classes in BOM which do not correspond to
                    // any element/complex type in the WSDLs XSD. Therefore, it
                    // may be ideal not to index them.
                    if (bomData.isValid()) {
                        createResourceItem(items,
                                projectName,
                                WSDL_INDEX_TYPE,
                                wsdlURI,
                                xsdSchemaPath,
                                targetNamespace,
                                derivedFromType,
                                wsdlElementName,
                                bomData.getObjectURI(),
                                bomData.getElementName(),
                                bomData.getBomFilePath(),
                                bomData.getNamespaceFromBOM(),
                                bomData.getBomId());
                    }
                }
            }
        } else if (element instanceof XSDSchemaDirective) {
            XSDSchemaDirective xsdSchemaDirective =
                    (XSDSchemaDirective) element;

            XSDSchema resolvedSchema = xsdSchemaDirective.getResolvedSchema();
            if (null != resolvedSchema) {
                if (!isInlineSchema(resolvedSchema.getSchemaLocation(),
                        indexingWsdlOrXsdPath)) {
                    IFile xsdResolvedFile =
                            WorkingCopyUtil.getFile(resolvedSchema);
                    if (null != xsdResolvedFile && xsdResolvedFile.exists()) {
                        if (!schemaLocations.contains(xsdResolvedFile
                                .getFullPath())) {
                            schemaLocations.add(xsdResolvedFile.getFullPath());
                            updateWSDLIndex(items,
                                    projectName,
                                    indexingWsdlOrXsdPath,
                                    xsdResolvedFile.getFullPath().toString(),
                                    resolvedSchema,
                                    bomsXsdTargetNamespace,
                                    schemaLocations);
                        }
                    }
                }
            }

        } else if (element instanceof Import) {
            Import wsdlImport = (Import) element;
            XSDSchema schema = wsdlImport.getESchema();

            if (null != schema) {
                IFile xsdResolvedFile = WorkingCopyUtil.getFile(schema);
                if (null != xsdResolvedFile && xsdResolvedFile.exists()) {
                    if (!schemaLocations
                            .contains(xsdResolvedFile.getFullPath())) {
                        schemaLocations.add(xsdResolvedFile.getFullPath());
                        updateWSDLIndex(items,
                                projectName,
                                indexingWsdlOrXsdPath,
                                xsdResolvedFile.getFullPath().toString(),
                                schema,
                                bomsXsdTargetNamespace,
                                schemaLocations);
                    }
                }
            }

        }
        while (it.hasNext()) {
            updateWSDLIndex(items,
                    projectName,
                    indexingWsdlOrXsdPath,
                    xsdSchemaPath,
                    it.next(),
                    bomsXsdTargetNamespace,
                    schemaLocations);
        }
    }

    /**
     * For an inline schema the default schema location is that of the WSDL
     * location.
     * 
     * @param schemaLocation
     * @param wsdlPath
     * @return
     */
    private boolean isInlineSchema(String schemaLocation, String wsdlPath) {
        URI resourceURI = URI.createPlatformResourceURI(wsdlPath, true);
        if (null != resourceURI
                && resourceURI.toString().equals(schemaLocation)) {
            return true;
        }
        return false;
    }

    /**
     * @param schema
     * @return
     */
    private BusinessObjectRelevantData getBomData(EObject object,
            String wsdlOrXsdPath) {
        BusinessObjectRelevantData bomData =
                new BusinessObjectRelevantData(object, wsdlOrXsdPath);
        return bomData;
    }

    /**
     * @see com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider#isAffectingEvent(java.beans.PropertyChangeEvent)
     * 
     * @param event
     * @return
     */
    public boolean isAffectingEvent(PropertyChangeEvent event) {
        boolean isAffectingEvent = false;

        if (event != null
                && event.getNewValue() instanceof ResourceSetChangeEvent) {
            ResourceSetChangeEvent changeEvent =
                    (ResourceSetChangeEvent) event.getNewValue();
            List<?> notifications = changeEvent.getNotifications();
            for (Object object : notifications) {
                if (object instanceof Notification) {
                    Notification notification = (Notification) object;
                    int eventType = notification.getEventType();
                    Object feature = notification.getFeature();

                    Object container = null;
                    if (feature instanceof EReference) {
                        container = ((EReference) feature).getContainerClass();
                    }
                    Object notifier = notification.getNotifier();
                    Object newValue = notification.getNewValue();

                    boolean isInterestingSetOrUnset = true;
                    // Check if we are setting or unsetting the property we are
                    // interested in
                    // if(feature instanceof EReference){
                    // EReference ref = (EReference)feature;
                    // TODO: We should filter here the set and unset activities
                    // }

                    switch (eventType) {
                    case Notification.ADD:
                        if (isInterestingObject(newValue)) {
                            isAffectingEvent = true;
                        }
                        break;
                    case Notification.ADD_MANY:
                        if (isInterestingObject(newValue)) {
                            isAffectingEvent = true;
                        }
                        break;
                    case Notification.MOVE:
                        if (isInterestingObject(newValue)) {
                            isAffectingEvent = true;
                        }
                        break;
                    case Notification.REMOVE:
                        if (isInterestingObject(container)) {
                            isAffectingEvent = true;
                        }
                        break;
                    case Notification.SET:
                        if (isInterestingSetOrUnset
                                && isInterestingObject(notifier)) {
                            isAffectingEvent = true;
                        }
                        break;
                    case Notification.UNSET:
                        if (isInterestingSetOrUnset
                                && isInterestingObject(notifier)) {
                            isAffectingEvent = true;
                        }
                        break;
                    }

                }

                if (isAffectingEvent) {
                    break;
                }
            }
        }
        return isAffectingEvent;
    }

    /**
     * Working copy for WSDLs are created even when they are not in the WSDL
     * special folder. While creating a DAA, the WSDL is copied into another
     * folder for which a working copy is created. This WSDL needn't be indexed.
     * The below method is a utility to check whether the given WSDL is
     * contained within a WSDL special folder.
     * 
     * @param notifier
     * @returns true if the file is null or not a WSDL file
     */
    private boolean isWSDLContainedInSpecialFolder(IResource wsdlFile) {
        // If the object belongs to a WSDL file, check whether the WSDL file
        // is contained in a WSDL Special folder.
        if (wsdlFile != null
                && wsdlFile.exists()
                && wsdlFile.getFileExtension() != null
                && wsdlFile.getFileExtension()
                        .equals(WsdlUIPlugin.WSDL_FILE_EXTENSION)) {

            ProjectConfig projectConfig =
                    XpdResourcesPlugin.getDefault()
                            .getProjectConfig(wsdlFile.getProject());
            if (projectConfig != null) {
                IPath relativePath =
                        SpecialFolderUtil
                                .getSpecialFolderRelativePath(wsdlFile,
                                        WsdlUIPlugin.WSDL_FILE_EXTENSION);
                IFile resolvedFile =
                        SpecialFolderUtil
                                .resolveSpecialFolderRelativePath(wsdlFile
                                        .getProject(),
                                        WsdlUIPlugin.WSDL_FILE_EXTENSION,
                                        relativePath.toPortableString(),
                                        false);
                return resolvedFile != null;
            }
        }
        return true;
    }

    /**
     * @param notifier
     * @return
     */
    private boolean isInterestingObject(Object o) {
        if (o instanceof Collection) {
            for (Object subO : ((Collection) o)) {
                if (isInterestingObject(subO)) {
                    return true;
                }
            }
        }
        if (o instanceof Class) {
            return true;
        }

        if (o == Class.class) {
            return true;
        }

        if (o instanceof PrimitiveType) {
            return true;
        }

        if (o == PrimitiveType.class) {
            return true;
        }

        if (o instanceof org.eclipse.uml2.uml.Package) {
            return true;
        }

        if (o == org.eclipse.uml2.uml.Package.class) {
            return true;
        }

        if (o instanceof XSDSchema) {
            return true;
        }
        if (o == XSDSchema.class) {
            return true;
        }

        if (o instanceof XSDComplexTypeDefinition) {
            return true;
        }
        if (o == XSDComplexTypeDefinition.class) {
            return true;
        }

        if (o instanceof XSDSimpleTypeDefinition) {
            return true;
        }
        if (o == XSDSimpleTypeDefinition.class) {
            return true;
        }

        return false;
    }

    /**
     * XPD-2272: Extend the standard {@link IndexerItemImpl} to allow the check
     * for uniqueness when adding to a list (by implementating hashCode() and
     * equals()).
     */
    private class MyIndexerItem extends IndexerItemImpl {

        private Map<String, String> attributes;

        /**
         * @param name
         * @param type
         * @param uri
         * @param additionalAttributes
         */
        public MyIndexerItem(String name, String type, String uri,
                Map<String, String> additionalAttributes) {
            super(name, type, uri, additionalAttributes);
            this.attributes = additionalAttributes;
        }

        /**
         * @see java.lang.Object#hashCode()
         * 
         * @return
         */
        @Override
        public int hashCode() {
            /*
             * URI is the primary key in the indexer.
             */
            if (getURI() != null) {
                return getURI().hashCode();
            }
            return super.hashCode();
        }

        /**
         * @see java.lang.Object#equals(java.lang.Object)
         * 
         * @param obj
         * @return
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            if (obj instanceof MyIndexerItem) {
                MyIndexerItem other = (MyIndexerItem) obj;

                if (match(getName(), other.getName())
                        && match(getType(), other.getType())
                        && match(getURI(), other.getURI())) {

                    if (attributes == null && other.attributes == null) {
                        return true;
                    }

                    if (attributes != null && other.attributes != null
                            && attributes.size() == other.attributes.size()) {
                        boolean match = true;
                        for (Entry<String, String> entry : attributes
                                .entrySet()) {
                            if (!match(entry.getValue(),
                                    other.attributes.get(entry.getKey()))) {
                                match = false;
                                break;
                            }
                        }

                        return match;
                    }
                }
            }

            return false;
        }

        /**
         * Check if the two values match.
         * 
         * @param value1
         * @param value2
         * @return
         */
        private boolean match(String value1, String value2) {
            if (value1 == value2) {
                return true;
            }

            if (value1 != null && value2 != null) {
                return value1.equals(value2);
            }
            return false;
        }
    }
}
