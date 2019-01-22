/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.bom.wsdltransform.builder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDNamedComponent;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.bom.xsdtransform.utils.TopLevelAttributeWrapper;
import com.tibco.xpd.bom.xsdtransform.utils.TopLevelElementWrapper;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Given an XSD Object retrieves the corresponding BOM relevant data.
 * 
 * @author rsomayaj
 * 
 */
public class BusinessObjectRelevantData {

    /**
     * 
     */
    private static final String BOM_FILE_EXTN =
            "." + BOMResourcesPlugin.BOM_FILE_EXTENSION; //$NON-NLS-1$

    private final EObject xsdObject;

    private String bomFilePath;

    private String namespace;

    private String objectURI;

    private final String wsdlOrXsdPath;

    private String bomId;

    private String bomElementName;

    /**
     * @param object
     */
    public BusinessObjectRelevantData(EObject object, String wsdlOrXsdPath) {
        this.xsdObject = object;
        this.wsdlOrXsdPath = wsdlOrXsdPath;
        if (this.xsdObject == null || this.wsdlOrXsdPath == null) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return the objURI
     */
    public String getObjectURI() {
        if (objectURI == null) {
            objectURI = getURI(xsdObject);

            /*
             * At times when a namespace is changed in WSDL/XSD and saved this
             * will cause duplicate URI problem as the XSD file name hasn't
             * changed, just the namespace. When the BOM gets generated for the
             * XSD this indexer will index the XSD but the indexer will already
             * have the elements indexed from the previously generated BOM. So
             * to make it unique append the namespace to the URI.
             */
            if (xsdObject instanceof XSDNamedComponent) {
                String namespace =
                        ((XSDNamedComponent) xsdObject).getTargetNamespace();
                if (namespace != null) {
                    objectURI = objectURI.concat("?" + namespace); //$NON-NLS-1$
                }
            }
        }
        return objectURI;
    }

    /**
     * @param schema2
     */
    private void prepareBomData() {

        IFile wsdlOrXsdFile =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(new Path(wsdlOrXsdPath));
        if (wsdlOrXsdFile != null) {
            IFile bomFile = getBOMFileForEObject(wsdlOrXsdFile);
            if (bomFile != null) {
                WorkingCopy bomWC =
                        XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
                if (bomWC != null) {
                    EObject rootElement = bomWC.getRootElement();
                    if (rootElement instanceof Model) {
                        Model model = (Model) rootElement;
                        if (xsdObject instanceof XSDElementDeclaration) {
                            // If the xsdObject is an XSDElementDeclaration -
                            // will have to look in all the BOMs related to
                            // identify the element. For XSDElementDeclaration -
                            // we need to iterate through all the classes that.
                            findClassiferForXsdElement(model, bomFile);
                        } else {
                            // Search for BOM Object with the xsdName stereotype
                            // in the BOM that matches the name in XSD, then
                            // filter
                            // those which aren't contained in other BOM objects
                            findClassifierForTypeDefinitionsInModelElements(model);
                            bomFilePath = bomFile.getFullPath().toString();
                        }

                    }

                }
            }

        }
    }

    /**
     * Method to get the classifer given the model object. This compares the
     * XSD-object name with the stereotype name in the BOM Classifier.
     * 
     * @param model
     */
    private void findClassifierForTypeDefinitionsInModelElements(Model model) {
        List<PackageableElement> packagedElements = model.getPackagedElements();

        for (PackageableElement packageableElement : packagedElements) {
            Stereotype appliedStereotype =
                    getAppliedStereotype(packageableElement,
                            XsdStereotypeUtils.XSD_BASED_CLASS);
            if (appliedStereotype != null) {
                Object xsdNameAsPerTheBomStereotype =
                        packageableElement.getValue(appliedStereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_NAME);
                String xsdName = getXsdName();
                /**
                 * If the xsdElementName equals the xsdName
                 */
                if (xsdName.equals(xsdNameAsPerTheBomStereotype)) {
                    boolean isAnonymousComplexType =
                            isAnonymousComplexTypeEquivalent(packageableElement);
                    if (!isAnonymousComplexType) {
                        bomId =
                                packageableElement.eResource()
                                        .getURIFragment(packageableElement);
                        bomElementName = packageableElement.getName();
                        this.namespace =
                                XSDUtil.getXSDNamespaceFromPackage(packageableElement
                                        .getNearestPackage());
                        break;
                    }
                }

            }
        }
    }

    private Model getModelForNamespace(String targetNamespace,
            Set<IProject> projects) {
        Model bomModel = null;

        IFile bomFileXsdElementExistsIn =
                getBomFileForXsdElement(targetNamespace, projects);
        if (bomFileXsdElementExistsIn != null) {
            // Iterate through the
            WorkingCopy wc =
                    WorkingCopyUtil.getWorkingCopy(bomFileXsdElementExistsIn);

            bomModel = (Model) wc.getRootElement();
        }
        return bomModel;
    }

    /**
     * It is understood that the XSD top level elements are stored as stereotype
     * attributes in the BOM classes. These may not necessarily be in the BOM
     * generated for the specific XSD if the type for the XSD Element is in an
     * external schema.
     * 
     * To accommodate this construct,
     * 
     * @param model
     * @param bomFileForWsdlOrXsd
     */
    @SuppressWarnings("restriction")
    private void findClassiferForXsdElement(Model model,
            IFile bomFileForWsdlOrXsd) {
        ArrayList<TopLevelElementWrapper> topLevelElementWrappers =
                new ArrayList<TopLevelElementWrapper>();

        ArrayList<TopLevelAttributeWrapper> topLevelAttributeWrappers =
                new ArrayList<TopLevelAttributeWrapper>();

        XSDElementDeclaration elementDeclaration =
                (XSDElementDeclaration) xsdObject;

        String xsdElementTargetNs = elementDeclaration.getTargetNamespace();
        String xsdElementName = elementDeclaration.getName();

        // Search through all referenced projects - using the
        // bomFileToBeSearched path.
        IProject project = bomFileForWsdlOrXsd.getProject();

        Set<IProject> projects = new HashSet<IProject>();
        projects.add(project);
        projects =
                ProjectUtil.getReferencedProjectsHierarchy(project, projects);

        Model elemModel = getModelForNamespace(xsdElementTargetNs, projects);
        Model elemTypeModel = null;
        XSDTypeDefinition typeDefinition =
                elementDeclaration.getTypeDefinition();
        if (!typeDefinition.getTargetNamespace().equals(xsdElementTargetNs)) {
            elemTypeModel =
                    getModelForNamespace(typeDefinition.getTargetNamespace(),
                            projects);
            if (elemTypeModel != null) {
                xsdElementTargetNs = typeDefinition.getTargetNamespace();
            } else {
                elemTypeModel = elemModel;
            }
        } else {
            elemTypeModel = elemModel;
        }

        List<PackageableElement> packagedElements =
                elemTypeModel.getPackagedElements();

        // get all top level elements applied to model
        List<Object> topLevelElements = XSDUtil.getTopLevelElements(elemModel);
        List<Object> topLevelAttributes =
                XSDUtil.getTopLevelAttributes(elemModel);

        // load all top level elements into an array of wrapper classes for
        // each method access
        if (topLevelElements.size() > 0) {
            for (Object obj : topLevelElements) {
                topLevelElementWrappers.add(new TopLevelElementWrapper(
                        (EObject) obj));
            }
        }

        // load all top level attributes into an array of wrapper classes for
        // each method access
        if (topLevelAttributes.size() > 0) {
            for (Object obj : topLevelAttributes) {
                topLevelAttributeWrappers.add(new TopLevelAttributeWrapper(
                        (EObject) obj));
            }
        }

        // if more than one top level element then we can check all classifiers
        // for a match
        if (topLevelElementWrappers.size() > 0
                || topLevelAttributeWrappers.size() > 0) {
            // look at each classifier in turn for a match
            for (PackageableElement packageableElement : packagedElements) {
                if (packageableElement instanceof Classifier) {
                    for (TopLevelElementWrapper topLevelElementWrapper : topLevelElementWrappers) {

                        // if we have a match for top level element name being
                        // of type this classifier then we add details
                        if (xsdElementName.equals(topLevelElementWrapper
                                .getName())
                                && packageableElement
                                        .equals(topLevelElementWrapper
                                                .getType())) {
                            bomId =
                                    packageableElement.eResource()
                                            .getURIFragment(packageableElement);
                            bomElementName = packageableElement.getName();

                            bomFilePath =
                                    WorkingCopyUtil.getFile(packageableElement)
                                            .getFullPath().toString();
                            namespace = xsdElementTargetNs;
                            break;

                        }
                    }
                    for (TopLevelAttributeWrapper topLevelAttributeWrapper : topLevelAttributeWrappers) {

                        // if we have a match for top level element name being
                        // of type this classifier then we add details
                        if (xsdElementName.equals(topLevelAttributeWrapper
                                .getName())
                                && packageableElement
                                        .equals(topLevelAttributeWrapper
                                                .getType())) {
                            bomId =
                                    packageableElement.eResource()
                                            .getURIFragment(packageableElement);
                            bomElementName = packageableElement.getName();

                            bomFilePath =
                                    WorkingCopyUtil.getFile(packageableElement)
                                            .getFullPath().toString();
                            namespace = xsdElementTargetNs;
                            break;

                        }
                    }
                }

                // if found then break loop
                if (bomId != null && bomElementName != null
                        && bomFilePath != null && namespace != null) {
                    break;
                }
            }
        }
    }

    /**
     * @param targetNamespace
     * @param projects
     * @return
     */
    private IFile getBomFileForXsdElement(String targetNamespace,
            Set<IProject> projects) {
        IFile bomFileXsdElementExistsIn = null;
        for (IProject prj : projects) {
            String javaPackageNameFromNamespaceURI =
                    XSDUtil.getJavaPackageNameFromNamespaceURI(prj,
                            targetNamespace);

            // Assuming that the javapackagenamefromnamepace URI + .bom will
            // give us
            // the BOM file name

            IPath bomFileToBeSearched =
                    new Path(javaPackageNameFromNamespaceURI)
                            .addFileExtension(BOMResourcesPlugin.BOM_FILE_EXTENSION);
            List<SpecialFolder> specialFolderOfKind =
                    SpecialFolderUtil.getAllSpecialFoldersOfKind(prj,
                            BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
            if (specialFolderOfKind != null) {
                for (SpecialFolder splFolder : specialFolderOfKind) {
                    IFolder folder = splFolder.getFolder();
                    if (folder != null) {
                        IFile bomFileBeingSearched =
                                folder.getFile(bomFileToBeSearched);
                        if (bomFileBeingSearched.exists()
                                && bomFileBeingSearched.isAccessible()) {
                            bomFileXsdElementExistsIn = bomFileBeingSearched;
                            break;
                        }
                    }

                }
            }

            if (bomFileXsdElementExistsIn != null) {
                break;
            }
        }
        return bomFileXsdElementExistsIn;
    }

    /**
     * Gets the applied stereotype from the
     * 
     * @param element
     * @param stereotypeName
     * @return
     */
    private Stereotype getAppliedStereotype(PackageableElement element,
            String stereotypeName) {

        List<Stereotype> appliedStereotypes = element.getAppliedStereotypes();
        for (Stereotype stereotype : appliedStereotypes) {
            if (stereotype.getName().equals(stereotypeName)) {
                return stereotype;
            }

        }
        return null;
    }

    /**
     * 
     * @param associations
     * @param packageableElement
     * @return
     */
    private boolean isAnonymousComplexTypeEquivalent(
            PackageableElement packageableElement) {
        // there is a possibility that the class was originally generated from
        // XSD, so check for Stereotype information applied, TODO, use some
        // utility to check value of 'xsdIsAnonType'
        EList<Stereotype> applicableStereotypes =
                packageableElement.getApplicableStereotypes();
        for (Stereotype stereotype : applicableStereotypes) {
            if (XsdStereotypeUtils.XSD_BASED_CLASS.equals(stereotype.getName())) {
                Boolean boolVal =
                        (Boolean) packageableElement.getValue(stereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_IS_ANON_TYPE);
                return boolVal;
            }
        }
        return false;
    }

    /**
     * @param wsdlOrXsdFile
     * @param object
     * @return
     */
    private IFile getBOMFileForEObject(IFile wsdlOrXsdFile) {
        Set<IFile> bomFiles =
                WsdlToBomBuilder.getBOMFiles(wsdlOrXsdFile, false, false);

        if (xsdObject instanceof XSDElementDeclaration) {

            // TODO:Change BOM File for the EObject may be the same as that of
            // find the Classifer in the findClassifier for XSD Element
            // declaration.
            XSDElementDeclaration elementDeclaration =
                    (XSDElementDeclaration) xsdObject;
            String targetNamespace =
                    elementDeclaration.getSchema().getTargetNamespace();
            String packageName =
                    getPackageName(wsdlOrXsdFile.getProject(), targetNamespace);
            if (packageName != null) {
                /*
                 * XSDUtil.getJavaPackageNameFromNamespaceURI(targetNamespace,
                 * null);
                 */
                String bomFileName = packageName + BOM_FILE_EXTN;
                for (IFile bomFile : bomFiles) {
                    if (bomFile.getName().equals(bomFileName)) {
                        return bomFile;
                    }
                }
            }
        } else if (xsdObject instanceof XSDNamedComponent) {
            String targetNamespace =
                    ((XSDNamedComponent) xsdObject).getTargetNamespace();
            String packageName =
                    getPackageName(wsdlOrXsdFile.getProject(), targetNamespace);
            if (packageName != null) {
                /*
                 * XSDUtil.getJavaPackageNameFromNamespaceURI(targetNamespace,
                 * null);
                 */
                String bomFileName = packageName + BOM_FILE_EXTN;
                for (IFile bomFile : bomFiles) {
                    if (bomFile.getName().equals(bomFileName)) {
                        return bomFile;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 
     * @param targetNamespace
     * @return may return null if no target namespace is defined for schema.
     */
    public static String getPackageName(IProject project,
            String originalTargetNamespace) {
        if (originalTargetNamespace != null) {
            return XSDUtil.getJavaPackageNameFromNamespaceURI(project,
                    originalTargetNamespace);
        } else {
            return null;
        }
    }

    /**
     * @param modelElement
     * @return
     */
    public static String getURI(EObject modelElement) {
        Resource modelElementResource = modelElement.eResource();
        URI uri =
                modelElementResource
                        .getURI()
                        .appendFragment(modelElementResource.getURIFragment(modelElement));
        String uriToString = uri.toString();
        return uriToString;

    }

    /**
     * @return the bomFileURI
     */
    public String getBomFilePath() {
        if (bomFilePath == null) {
            prepareBomData();
        }
        return bomFilePath;
    }

    /**
     * @return the namespace
     */
    public String getNamespaceFromBOM() {
        if (namespace == null) {
            prepareBomData();
        }
        return namespace;
    }

    /**
     * @return the schema
     */
    public String getElementName() {
        if (bomElementName == null) {
            prepareBomData();
        }
        return bomElementName;
    }

    /**
     * @param xsdElementName
     * @return
     */
    private String getXsdName() {
        String xsdElementName = null;
        if (xsdObject instanceof XSDComplexTypeDefinition) {
            XSDComplexTypeDefinition complexTypeDefinition =
                    (XSDComplexTypeDefinition) xsdObject;
            xsdElementName = complexTypeDefinition.getName();

        } else if (xsdObject instanceof XSDSimpleTypeDefinition) {
            XSDSimpleTypeDefinition simpleTypeDefinition =
                    (XSDSimpleTypeDefinition) xsdObject;
            xsdElementName = simpleTypeDefinition.getName();
        } else if (xsdObject instanceof XSDElementDeclaration) {
            XSDElementDeclaration elementDeclaration =
                    (XSDElementDeclaration) xsdObject;
            xsdElementName = elementDeclaration.getName();
        }
        return xsdElementName;
    }

    /**
     * @return the bomId
     */
    public String getBomId() {
        if (bomId == null) {
            prepareBomData();
        }
        return bomId;
    }

    /**
     * @return
     */
    public boolean isValid() {
        if (getBomId() != null && getElementName() != null) {
            return true;
        }
        return false;
    }
}
