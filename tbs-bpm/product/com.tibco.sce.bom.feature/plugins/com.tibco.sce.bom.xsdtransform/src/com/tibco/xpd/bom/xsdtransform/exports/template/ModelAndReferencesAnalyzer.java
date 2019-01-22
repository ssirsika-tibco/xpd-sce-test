/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.exports.template;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.utils.NamespaceURIToJavaPackageMapper;
import com.tibco.xpd.bom.xsdtransform.utils.TopLevelAttributeWrapper;
import com.tibco.xpd.bom.xsdtransform.utils.TopLevelElementWrapper;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * 
 * 
 * @author aallway
 * @since 11 Apr 2011
 */
public class ModelAndReferencesAnalyzer {

    /**
     * @param rootModel
     * @param parseOperations
     * @return
     */
    public static Set<Model> getModelAndReferences(Model rootModel,
            boolean parseOperations) {

        /*
         * This is a map of XsdBaseProperty namespaces to BOM resource for every
         * bom in same project as root model.
         */
        Map<String, IResource> xsdNamespaceToBomFileMap =
                TransformHelper
                        .getXsdNamespaceToBomFileMapForXsdXrefs(WorkingCopyUtil
                                .getProjectFor(rootModel), null);

        HashSet<Model> modelSet = new HashSet<Model>();

        internalGetModelAndReferences(modelSet,
                rootModel,
                parseOperations,
                xsdNamespaceToBomFileMap);

        /* Don't return the root level model in the set. */
        modelSet.remove(rootModel);

        return modelSet;
    }

    /**
     * @param completeModelSet
     *            Target model set - the model and every referenced model found
     *            so far..
     * @param rootModel
     * @param parseOperations
     * @param xsdNamespaceToBomFileMap
     * @return
     */
    private static void internalGetModelAndReferences(
            HashSet<Model> completeModelSet, Model sourceModel,
            boolean parseOperations,
            Map<String, IResource> xsdNamespaceToBomFileMap) {

        /* Only parse model if not already done. */
        if (!completeModelSet.contains(sourceModel)) {
            completeModelSet.add(sourceModel);

            /*
             * Get all the properties / generalizations and oeprations from
             * classes defined in this model.
             */
            HashSet<Property> properties = new HashSet<Property>();
            HashSet<Generalization> generalizations =
                    new HashSet<Generalization>();
            HashSet<Operation> operations = new HashSet<Operation>();

            gatherPropsGensAndOps(sourceModel,
                    properties,
                    generalizations,
                    operations);

            /*
             * Build a set of the Models reference directly from this model. We
             * will recurs at the end for any model that has not already been
             * parsed.
             */
            HashSet<Model> refdFromThisModel = new HashSet<Model>();

            if (parseOperations) {
                parseOperations(sourceModel, operations, refdFromThisModel);
            }

            parseProperties(sourceModel,
                    properties,
                    refdFromThisModel,
                    xsdNamespaceToBomFileMap);

            parseGeneralizations(generalizations, refdFromThisModel);

            refdFromThisModel.addAll(getModelTopLevelDependencies(sourceModel));

            // parseTopLevelElementReferences(sourceModel, refdFromThisModel);

            parseUnionReferences(sourceModel, refdFromThisModel);

            /*
             * Ok we should haver all the references to other models DIRECTLY
             * from the sourceModel
             * 
             * We need to recurs for any one that is not already in the
             * completeModelSet Remove all the already processed models from the
             * directly referenced by sourceModel set and recurs for each one.
             */
            refdFromThisModel.removeAll(completeModelSet);

            for (Model refdModel : refdFromThisModel) {
                internalGetModelAndReferences(completeModelSet,
                        refdModel,
                        parseOperations,
                        xsdNamespaceToBomFileMap);
            }

        }

        return;
    }

    /**
     * Gather all the properties, geenalizations and operations defined in the
     * given model.
     * 
     * @param sourceModel
     * @param properties
     * @param generalizations
     * @param operations
     */
    private static void gatherPropsGensAndOps(Model sourceModel,
            Set<Property> properties, Set<Generalization> generalizations,
            Set<Operation> operations) {

        for (Iterator<EObject> iterator = sourceModel.eAllContents(); iterator
                .hasNext();) {
            EObject eObject = iterator.next();

            if (eObject instanceof Classifier) {
                Classifier classifier = (Classifier) eObject;

                properties.addAll(classifier.getAttributes());

                generalizations.addAll(classifier.getGeneralizations());

                operations.addAll(classifier.getOperations());
            }
        }
        return;
    }

    public static Set<Model> getModelTopLevelDependencies(Model sourceModel) {
        Set<Model> bomResources = new HashSet<Model>();

        // only need to check if we have a working copy that is Model as root
        // element

        EList<?> listAttrs = null;
        EList<?> listElems = null;

        IFile currentFile = WorkingCopyUtil.getFile(sourceModel);

        // get all top level attributes
        Stereotype topLevelAttributesStereotype =
                getAppliedStereotype(sourceModel,
                        sourceModel,
                        XsdStereotypeUtils.TOP_LEVEL_ATTRIBUTES);
        if (topLevelAttributesStereotype != null) {
            Object attributes =
                    sourceModel
                            .getValue(topLevelAttributesStereotype,
                                    XsdStereotypeUtils.XSD_TOP_LEVEL_ATTRIBUTE_ATTRIBUTES);
            if (attributes instanceof EList) {
                listAttrs = (EList<?>) attributes;
            }
        }

        // get all top level elements
        Stereotype topLevelAElementsStereotype =
                getAppliedStereotype(sourceModel,
                        sourceModel,
                        XsdStereotypeUtils.TOP_LEVEL_ELEMENTS);
        if (topLevelAElementsStereotype != null) {
            Object elements =
                    sourceModel.getValue(topLevelAElementsStereotype,
                            XsdStereotypeUtils.XSD_TOP_LEVEL_ELEMENT_ELEMENTS);
            if (elements instanceof EList) {
                listElems = (EList<?>) elements;
            }
        }

        // go through and see if any external BOMS are referenced with these
        // top level attributes
        if (listAttrs != null) {
            Iterator<?> iterator = listAttrs.iterator();
            while (iterator.hasNext()) {
                Object topLevelAttr = iterator.next();
                TopLevelAttributeWrapper topLevelAttributeWrapper =
                        new TopLevelAttributeWrapper((EObject) topLevelAttr);
                if (topLevelAttributeWrapper.getType() != null) {
                    IFile file =
                            WorkingCopyUtil.getFile(topLevelAttributeWrapper
                                    .getType());
                    if (!currentFile.equals(file)) {
                        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);
                        if (wc != null && wc.getRootElement() instanceof Model) {
                            bomResources.add((Model) wc.getRootElement());
                        }
                    }
                }

            }
        }

        // go through and see if any external BOMS are referenced with these
        // top level elements
        if (listElems != null) {
            Iterator<?> iterator = listElems.iterator();
            while (iterator.hasNext()) {
                Object topLevelElem = iterator.next();
                TopLevelElementWrapper topLevelElementWrapper =
                        new TopLevelElementWrapper((EObject) topLevelElem);
                if (topLevelElementWrapper.getType() != null) {
                    IFile file =
                            WorkingCopyUtil.getFile(topLevelElementWrapper
                                    .getType());
                    if (!currentFile.equals(file)) {
                        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);
                        if (wc != null && wc.getRootElement() instanceof Model) {
                            bomResources.add((Model) wc.getRootElement());
                        }
                    }
                }

            }
        }
        return bomResources;
    }

    /**
     * Parses the current Model to find all top level element stereotypes on all
     * the classifiers. It then checks to see if any top level element is
     * referring to a type outside the current BOM and if so adds to the
     * parsedModels HashMap for processing later on.
     * 
     * @param sourceModel
     * @param modelSet
     */
    public static void parseTopLevelElementReferences(Model sourceModel,
            HashSet<Model> modelSet) {
        // get current XML Schema namespace for this BOM
        String targetNamespace = BOMUtils.getNamespace(sourceModel, true);

        IFile rootModelFile =
                WorkspaceSynchronizer.getFile(sourceModel.eResource());
        try {
            // get all projects that depend on this BOM and also the current BOM
            // Project and then collect all BOM's from them all ready for
            // parsing
            HashSet<IFile> allBomFiles = new HashSet<IFile>();
            HashSet<IProject> allProjects = new HashSet<IProject>();
            ProjectUtil.getReferencingProjectsHierarchy(rootModelFile
                    .getProject(), allProjects);
            allProjects.add(rootModelFile.getProject());
            for (IProject project : allProjects) {
                allBomFiles.addAll(getAllBomFiles(project));
            }

            // for each BOM we scan all its classifiers for top level element
            // stereotypes applied and see if it
            // references a type in another BOM and if so add it to the
            // parseModels Set
            for (IFile bomFile : allBomFiles) {
                WorkingCopy wc =
                        XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
                if (wc != null) {
                    EObject model = wc.getRootElement();
                    if (model != null && model instanceof Model
                            && !modelSet.contains(model)) {
                        // Get top level elements and attributes
                        List<TopLevelElementWrapper> topLevelElements =
                                TransformHelper
                                        .getTopLevelElementWrappers((Model) model);
                        List<TopLevelAttributeWrapper> topLevelAttributes =
                                TransformHelper
                                        .getTopLevelAttributeWrappers((Model) model);

                        // if referencing type outside BOM add to Set
                        if (topLevelElements != null) {
                            for (TopLevelElementWrapper xsdElem : topLevelElements) {
                                Model elementTypeModel =
                                        xsdElem.getType().getModel();
                                if (!modelSet.contains(elementTypeModel)) {
                                    modelSet.add(elementTypeModel);
                                }
                            }
                        }

                        // if referencing type outside BOM add to Set
                        if (topLevelAttributes != null) {
                            for (TopLevelAttributeWrapper xsdAttr : topLevelAttributes) {
                                Model attributeTypeModel =
                                        xsdAttr.getType().getModel();
                                if (!modelSet.contains(attributeTypeModel)) {
                                    modelSet.add(attributeTypeModel);
                                }
                            }
                        }
                    }
                }
            }
        } catch (CoreException e) {
            Activator
                    .getDefault()
                    .getLogger()
                    .error(e,
                            "Ignoring exception during during parseTopLevelelements()");
        }

        return;
    }

    /**
     * Parses the current Model to find all union members stereotypes on all the
     * classifiers. It then checks to see if any union is referring to a type
     * outside the current BOM and if so adds to the parsedModels HashMap for
     * processing later on.
     * 
     * @param sourceModel
     * @param modelSet
     */
    public static void parseUnionReferences(Model sourceModel,
            HashSet<Model> modelSet) {
        IFile rootModelFile =
                WorkspaceSynchronizer.getFile(sourceModel.eResource());
        IProject rootProject = rootModelFile.getProject();

        EList<PackageableElement> packagedElements =
                sourceModel.getPackagedElements();

        // search each classifier in BOM
        for (PackageableElement packageableElement : packagedElements) {
            if (packageableElement instanceof DataType) {
                Stereotype stereotype =
                        getAppliedStereotype(sourceModel,
                                packageableElement,
                                XsdStereotypeUtils.XSD_BASED_UNION);
                if (stereotype != null) {
                    if (packageableElement.getValue(stereotype,
                            XsdStereotypeUtils.XSD_UNION_MEMBER_TYPES) != null) {
                        String memberTypes =
                                (String) packageableElement
                                        .getValue(stereotype,
                                                XsdStereotypeUtils.XSD_UNION_MEMBER_TYPES);
                        String[] splitStr =
                                memberTypes.replace("[", "").replace("]", "")
                                        .split(",");
                        if (splitStr != null && splitStr.length > 0) {
                            for (int i = 0; i < splitStr.length; i++) {
                                String namespaceAndName = splitStr[i].trim();
                                String[] split = namespaceAndName.split("}");
                                String namespace =
                                        split[0].trim().replace("{", "");
                                String packageName =
                                        NamespaceURIToJavaPackageMapper
                                                .getJavaPackageNameFromNamespaceURI(rootProject,
                                                        namespace);

                                // get all projects that depend on this BOM and
                                // also the current BOM
                                // Project and then collect all BOM's from them
                                // all ready for
                                // parsing
                                HashSet<IFile> allBomFiles =
                                        new HashSet<IFile>();
                                HashSet<IProject> allProjects =
                                        new HashSet<IProject>();
                                ProjectUtil
                                        .getReferencingProjectsHierarchy(rootProject,
                                                allProjects);
                                allProjects.add(rootProject);
                                try {
                                    for (IProject project : allProjects) {
                                        allBomFiles
                                                .addAll(getAllBomFiles(project));
                                    }

                                    // for each BOM we scan all its classifiers
                                    // for top level element
                                    // stereotypes applied and see if it
                                    // references a type in another BOM and if
                                    // so add it to the
                                    // parseModels Set
                                    for (IFile bomFile : allBomFiles) {
                                        WorkingCopy wc =
                                                XpdResourcesPlugin
                                                        .getDefault()
                                                        .getWorkingCopy(bomFile);
                                        if (wc != null) {
                                            EObject model = wc.getRootElement();
                                            if (model != null
                                                    && model instanceof Model
                                                    && !modelSet
                                                            .contains(model)) {
                                                if (((Model) model).getName()
                                                        .equals(packageName)) {
                                                    modelSet.add((Model) model);
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                } catch (CoreException e) {
                                    Activator
                                            .getDefault()
                                            .getLogger()
                                            .error(e,
                                                    "Ignoring exception during during parseUnionReferences()");
                                }
                            }
                        }
                    }
                }
            }
        }
        return;
    }

    /**
     * @param generalizations
     * @param modelSet
     */
    private static void parseGeneralizations(
            Collection<Generalization> generalizations,
            HashSet<Model> refdFromThisModel) {
        if (generalizations != null) {

            for (Generalization generalization : generalizations) {
                Classifier classifier = generalization.getGeneral();

                if (classifier != null
                        && classifier.eResource() != null
                        && !generalization.eResource()
                                .equals(classifier.eResource())) {

                    WorkingCopy bomWc =
                            WorkingCopyUtil.getWorkingCopyFor(classifier);

                    if (bomWc != null) {
                        EObject model = bomWc.getRootElement();

                        if (model instanceof Model) {
                            refdFromThisModel.add((Model) model);
                        }
                    }
                }
            }
        }

        return;
    }

    /**
     * @param sourceModel
     * @param properties
     * @param xsdNamespaceToBomFileMap
     * @param modelSet
     */
    private static void parseProperties(Model sourceModel,
            Collection<Property> properties, HashSet<Model> refdFromThisModel,
            Map<String, IResource> xsdNamespaceToBomFileMap) {

        if (properties != null) {

            for (Property property : properties) {

                if (property != null && property.eResource() != null) {
                    /*
                     * Add model reference via XsdBasedProperty stereo type ->
                     * xRef
                     */
                    IResource bomFile =
                            TransformHelper.getXsdBasedXrefBomFile(property,
                                    xsdNamespaceToBomFileMap);

                    if (bomFile != null) {

                        WorkingCopy bomWc =
                                WorkingCopyUtil.getWorkingCopy(bomFile);

                        EObject model = bomWc.getRootElement();

                        if (model instanceof Model) {
                            refdFromThisModel.add((Model) model);
                        }
                    }

                    /*
                     * Add model references for types of the properties
                     */
                    Type propertyType = property.getType();
                    if (propertyType != null
                            && !(property.eResource().equals(propertyType
                                    .eResource()))) {

                        Collection<String> standardPrimtiveTypeNames =
                                PrimitivesUtil
                                        .getStandardPrimtiveTypeNames(property
                                                .eResource().getResourceSet());

                        boolean isDifferentURI = true;
                        for (String primName : standardPrimtiveTypeNames) {
                            PrimitiveType standardPrimType =
                                    PrimitivesUtil
                                            .getStandardPrimitiveTypeByName(property
                                                    .eResource()
                                                    .getResourceSet(),
                                                    primName);
                            if (propertyType.equals(standardPrimType)) {
                                isDifferentURI = false;
                                break;
                            }
                        }

                        if (isDifferentURI) {
                            WorkingCopy bomWc =
                                    WorkingCopyUtil
                                            .getWorkingCopyFor(propertyType);
                            if (bomWc != null) {
                                EObject model = bomWc.getRootElement();

                                if (model instanceof Model) {
                                    refdFromThisModel.add((Model) model);
                                }
                            }
                        }
                    }
                }
            }
        }

        return;
    }

    /**
     * @param sourceModel
     * @param operations
     * @param modelSet
     */
    private static void parseOperations(Model sourceModel,
            Collection<Operation> operations, HashSet<Model> refdFromThisModel) {

        if (operations != null) {

            for (Operation operation : operations) {

                if (operation.eResource() != null) {

                    for (Parameter parameter : operation.getOwnedParameters()) {
                        Type parameterType = parameter.getType();

                        if (parameterType != null
                                && !(operation.eResource().equals(parameterType
                                        .eResource()))) {

                            Collection<String> standardPrimtiveTypeNames =
                                    PrimitivesUtil
                                            .getStandardPrimtiveTypeNames(operation
                                                    .eResource()
                                                    .getResourceSet());

                            boolean isDifferentURI = true;

                            for (String primName : standardPrimtiveTypeNames) {
                                PrimitiveType standardPrimType =
                                        PrimitivesUtil
                                                .getStandardPrimitiveTypeByName(operation
                                                        .eResource()
                                                        .getResourceSet(),
                                                        primName);
                                if (parameterType.equals(standardPrimType)) {
                                    isDifferentURI = false;
                                    break;
                                }
                            }

                            if (isDifferentURI) {
                                WorkingCopy bomWc =
                                        WorkingCopyUtil
                                                .getWorkingCopyFor(parameterType);
                                if (bomWc != null) {
                                    EObject model = bomWc.getRootElement();

                                    if (model instanceof Model) {
                                        refdFromThisModel.add((Model) model);
                                    }
                                }
                            }
                        }
                    } /* Next parameter. */
                }
            }
        }
    }

    /**
     * @param model
     * @param element
     * @param stereotypeName
     * @return stereo type of given name,
     */
    private static Stereotype getAppliedStereotype(Model model,
            Element element, String stereotypeName) {
        Stereotype stereotype = null;
        Iterator<Profile> profilesIter = model.getAppliedProfiles().iterator();
        while (profilesIter.hasNext()) {
            Profile profile = profilesIter.next();
            if (profile.getName()
                    .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                Object obj = profile.getPackagedElement(stereotypeName);
                if (obj instanceof Stereotype) {
                    stereotype = (Stereotype) obj;
                    if (stereotype.getName().equals(stereotypeName)) {
                        Stereotype alreadyAppliedStereotype =
                                element.getAppliedStereotype(stereotype
                                        .getQualifiedName());
                        return alreadyAppliedStereotype;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get all the BOM files in the given project. This will return BOM files
     * that are contained in BOM special folder(s).
     * 
     * @param project
     * @return all BOM files in the given project, empty list if none found
     * @throws CoreException
     */
    public static List<IFile> getAllBomFiles(IProject project)
            throws CoreException {
        final List<IFile> files = new ArrayList<IFile>();

        List<SpecialFolder> folders =
                SpecialFolderUtil.getAllSpecialFoldersOfKind(project,
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);

        if (folders != null) {
            for (SpecialFolder sf : folders) {
                IFolder folder = sf.getFolder();
                if (folder != null && folder.isAccessible()) {
                    folder.accept(new IResourceProxyVisitor() {
                        @Override
                        public boolean visit(IResourceProxy proxy)
                                throws CoreException {
                            if (proxy.getType() == IResource.FILE
                                    && proxy.getName()
                                            .endsWith("." //$NON-NLS-1$
                                                    + BOMResourcesPlugin.BOM_FILE_EXTENSION)) {
                                IResource resource = proxy.requestResource();
                                if (resource instanceof IFile) {
                                    files.add((IFile) resource);
                                    return false;
                                }
                            }
                            return true;
                        }
                    },
                            0);
                }
            }
        }

        return files;
    }

}
