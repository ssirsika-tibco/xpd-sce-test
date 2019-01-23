package com.tibco.bds.designtime.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Relationship;
import org.eclipse.uml2.uml.Type;

import com.tibco.bds.designtime.generator.anno.AnnotationManager;
import com.tibco.bds.designtime.generator.anno.UserAnnotationManager;
import com.tibco.bds.designtime.generator.common.BDSConstants;
import com.tibco.bds.shared.anno.JPAAnnotations;
import com.tibco.xpd.bom.gen.biz.GenerationException;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.SummaryInfoUtils;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Wraps a UML Model (BOM) and provides the ability to annotate an associated
 * collection of EPackages based on global data meta-data on the Model.
 * 
 * @author smorgan
 * 
 */
public class BDSBOMModel {

    private Model bomModel;

    private UmlToEmfMapper umlToEmfMapper = null;

    /**
     * Class to convert a UML class into the EMF class that represents it
     */
    class UmlToEmfMapper {
        private Map<String, Map<Type, EClass>> umlToEMFClassMap =
                new HashMap<String, Map<Type, EClass>>();

        public UmlToEmfMapper(Collection<EPackage> packages,
                Map<String, org.eclipse.uml2.uml.Package> umlPackageMap) {
            // Now store more detailed ecore class to UML mappings
            for (EPackage ePackage : packages) {
                String nsURI = ePackage.getNsURI();
                IProject project = WorkingCopyUtil.getProjectFor(ePackage,true);
                String javaPackageName =
                        XSDUtil.getJavaPackageNameFromNamespaceURI(project,
                                nsURI);
                org.eclipse.uml2.uml.Package umlPackage =
                        umlPackageMap.get(javaPackageName);

                // Construct a map of UML class to EMF Class
                Map<Type, EClass> umlToEMFMap = new HashMap<Type, EClass>();

                // Get each of the classes out of this UML Package
                for (Type type : umlPackage.getOwnedTypes()) {
                    if (type instanceof org.eclipse.uml2.uml.Class) {
                        EClassifier eClassifier =
                                ePackage.getEClassifier(type.getName());
                        if (eClassifier != null) {
                            umlToEMFMap.put((org.eclipse.uml2.uml.Class) type,
                                    (EClass) eClassifier);
                        }
                    }
                }
                // Add to the main map, keyed off of the package name
                umlToEMFClassMap
                        .put(umlPackage.getQualifiedName(), umlToEMFMap);
            }
        }

        /**
         * Given a UML class, returns the corresponding EMF Class
         * 
         * @param umlClass
         * @return
         */
        public EClass getEmfClass(Type umlClass) {
            EClass eClass = null;

            String pkgName = umlClass.getPackage().getQualifiedName();

            if (umlToEMFClassMap.containsKey(pkgName)) {
                eClass = umlToEMFClassMap.get(pkgName).get(umlClass);
            }

            return eClass;
        }
    }

    private BDSBOMModel() {
    }

    public static BDSBOMModel fromResource(IResource bomResource) {
        // Get root package from BOM
        org.eclipse.uml2.uml.Model model =
                (org.eclipse.uml2.uml.Model) XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(bomResource).getRootElement();
        return fromModel(model);
    }

    public static BDSBOMModel fromModel(Model model) {
        BDSBOMModel result = new BDSBOMModel();
        result.bomModel = model;
        return result;
    }

    public boolean isGlobalBOM() {
        return BOMGlobalDataUtils.isGlobalDataBOM(bomModel);
    }

    public void applyAnnotationsToPackages(Collection<EPackage> packages,
            String version) throws GenerationException {
        // Persist the mapping between UML Model and java package name
        Map<String, org.eclipse.uml2.uml.Package> umlPackageMap =
                new HashMap<String, org.eclipse.uml2.uml.Package>();
        umlPackageMap.put(bomModel.getName(), bomModel);
        addContainedPackagesToMap(bomModel, umlPackageMap, bomModel.getName());

        // Now store more detailed ecore class to UML mappings
        umlToEmfMapper = new UmlToEmfMapper(packages, umlPackageMap);

        // Process the packages ecore packages
        for (EPackage ePackage : packages) {
            String nsURI = ePackage.getNsURI();
            IProject project = WorkingCopyUtil.getProjectFor(ePackage, true);
            String javaPackageName =
                    XSDUtil.getJavaPackageNameFromNamespaceURI(project, nsURI);
            org.eclipse.uml2.uml.Package umlPackage =
                    umlPackageMap.get(javaPackageName);
            processPackage(umlPackage, ePackage, version);
        }
    }

    /**
     * Adds annotations that highlight the order of the values in a sequence -
     * this is to work around an EMF ordering problem in EMF
     * 
     * @param packages
     *            Packages to process
     */
    public static void applyAnnotationsForSequenceOrder(
            Collection<EPackage> packages) {

        for (EPackage ePackage : packages) {
            for (EClassifier eClassifier : ePackage.getEClassifiers()) {
                if (eClassifier instanceof EClass) {
                    EClass eClass = (EClass) eClassifier;
                    // Only add the sequence order annotation to mixed types
                    int contentKind =
                            ExtendedMetaData.INSTANCE.getContentKind(eClass);
                    if ((contentKind == ExtendedMetaData.MIXED_CONTENT)
                            && !ExtendedMetaData.INSTANCE
                                    .isDocumentRoot(eClass)) {
                        int i = getStartingSequenceOrderId(eClass);
                        EList<EStructuralFeature> eStructs =
                                eClass.getEStructuralFeatures();
                        for (EStructuralFeature eStruct : eStructs) {
                            AnnotationManager.addAnnotation(eStruct,
                                    BDSConstants.BDS_ANNOTATION,
                                    "SequenceOrder",
                                    Integer.valueOf(i).toString());
                            i++;
                        }
                    }
                }
            }
        }
    }

    /**
     * Adds an annotation to each package with the name of the BOM that it was
     * generated from
     * 
     * @param ctx
     * @param packages
     */
    public static void applyBomFileAnnotations(Context ctx,
            Collection<EPackage> packages) {

        IFile bomFile = ctx.getBOMFile();

        if (bomFile != null) {
            // For all packages and subpackages
            // add the BOM Filename that they were in
            for (EPackage ePackage : packages) {
                AnnotationManager.addAnnotation(ePackage,
                        BDSConstants.BDS_ANNOTATION,
                        BDSConstants.BDS_BOM_FILE_NAME_ANNO,
                        bomFile.toString());
            }
        }
    }

    /**
     * Calculate the number to use as the starting point for the sequence order
     * value
     * 
     * @param eClass
     *            Class to base the calculation from
     * @return Sequence Order starting value
     */
    static private int getStartingSequenceOrderId(EClass eClass) {
        int startingValue = 1;

        // Check to see if there is a base type
        EList<EClass> eSuperTypes = eClass.getESuperTypes();

        if ((eSuperTypes != null) && !eSuperTypes.isEmpty()) {
            for (EClass superType : eSuperTypes) {
                if (superType != null) {
                    // Check if this extends another class, make a recursive all
                    // as we need to account for all numbering values
                    int featureCount = getStartingSequenceOrderId(superType);

                    // At the number of features in the super class to the point
                    // where the superclass started from, this will give us the
                    // last number used
                    featureCount += superType.getFeatureCount();

                    // Now get the next rounded number, the following will
                    // ensure that each number being started will be one digit
                    // larger that the end of the previous number (e.g. last =
                    // 12, next = 101)
                    int nextNum =
                            (int) (Math.pow(10,
                                    Math.ceil(Math.log10(featureCount))) + 1);
                    if (nextNum > startingValue) {
                        startingValue = nextNum;
                    }
                }
            }
        }

        return startingValue;
    }

    private void addContainedPackagesToMap(org.eclipse.uml2.uml.Package pkg,
            Map<String, org.eclipse.uml2.uml.Package> map, String baseName) {
        for (PackageableElement element : pkg.getPackagedElements()) {
            if (element instanceof org.eclipse.uml2.uml.Package) {
                org.eclipse.uml2.uml.Package childPkg =
                        (org.eclipse.uml2.uml.Package) element;
                String name =
                        String.format("%s.%s", baseName, childPkg.getName());
                map.put(name, childPkg);
                addContainedPackagesToMap(childPkg, map, name);
            }
        }
    }

    private void processPackage(org.eclipse.uml2.uml.Package umlPackage,
            EPackage ePackage, String version) throws GenerationException {

        // Add the user annotations that are on the Model itself
        // so are global
        JPAAnnotations userAnnotations =
                UserAnnotationManager.getUserAnnotations(umlPackage.getModel());
        AnnotationManager.applyUserAnnotation(ePackage, userAnnotations);

        // Add major version annotation to the EPackage
        AnnotationManager.addVersionAnnotation(ePackage, version);

        // Add the Unique ID generator annotation
        AnnotationManager.addEPackageAnnotations(ePackage);

        // Add the version to the eCore so we know which release generated it
        AnnotationManager.addEcoreFormatAnnotation(ePackage);

        // Check to see if the Name-space Tag has been specified by the user
        String pkgTag = BOMGlobalDataUtils.getPackageTag(umlPackage);
        if ((pkgTag != null) && !pkgTag.isEmpty()) {
            AnnotationManager.addNamespaceTagAnnotation(ePackage, pkgTag);
        }

        // Store the label used in the BOM
        String label = PrimitivesUtil.getDisplayLabel(umlPackage);
        if ((label != null) && !label.isEmpty()) {
            AnnotationManager.addLabelAnnotation(ePackage, label);
        }

        // Store the description shown in the BOM
        String description = getDescription(umlPackage);
        if ((description != null) && !description.isEmpty()) {
            AnnotationManager.addDescriptionAnnotation(ePackage, description);
        }

        // Construct a map of UML name -> classifier
        Map<String, org.eclipse.uml2.uml.Classifier> umlClassifierMap =
                new HashMap<String, org.eclipse.uml2.uml.Classifier>();
        for (Type type : umlPackage.getOwnedTypes()) {
            if (type instanceof org.eclipse.uml2.uml.Classifier) {
                umlClassifierMap.put(type.getName(),
                        (org.eclipse.uml2.uml.Classifier) type);
            }
        }

        // Need to apply all the associations and aggregations to the
        // ecore, using the information in the BOM
        addAssociationsToEcore(umlPackage);

        // Perform mappings on each classifier in the EPackage
        for (EClassifier eClassifier : ePackage.getEClassifiers()) {
            if (eClassifier instanceof EClass) {
                // Obtain the corresponding UML class
                org.eclipse.uml2.uml.Classifier umlClassifier =
                        umlClassifierMap.get(eClassifier.getName());
                // Won't be found if it's the doc root
                if (umlClassifier != null && umlClassifier instanceof Class) {
                    processClass((Class) umlClassifier, (EClass) eClassifier);
                }
            } else if (eClassifier instanceof EEnum) {
                // Obtain the corresponding UML enum
                org.eclipse.uml2.uml.Classifier umlClassifier =
                        umlClassifierMap.get(eClassifier.getName());
                if (umlClassifier != null
                        && umlClassifier instanceof Enumeration) {
                    processEnumeration((Enumeration) umlClassifier,
                            (EEnum) eClassifier);
                }
            }
        }

        // Now add the BDS user overrides
        JPAAnnotations userBdsAnnotations =
                UserAnnotationManager.getBdsUserAnnotations(umlPackage
                        .getModel());
        AnnotationManager.applyBdsAnnotation(ePackage, userBdsAnnotations);
    }

    /**
     * Method that will search the BOM for associations and aggregations and
     * update the ecore to represent them
     * 
     * @param umlPackage
     */
    private void addAssociationsToEcore(org.eclipse.uml2.uml.Package umlPackage) {
        // Need to apply all the associations and aggregations to the
        // ecore, using the information in the BOM
        for (Type type : umlPackage.getOwnedTypes()) {
            if (type instanceof org.eclipse.uml2.uml.Class) {
                org.eclipse.uml2.uml.Class umlClass =
                        (org.eclipse.uml2.uml.Class) type;
                // Just get the attributes just on this class
                // (Not all subclasses as well)
                EList<Property> attribs = umlClass.getAttributes();
                for (Property prop : attribs) {
                    AggregationKind aggregationKind = prop.getAggregation();
                    // Both associations and aggregations are stored as
                    // associations in the UML model
                    Association association = prop.getAssociation();
                    if ((association != null)
                            && ((aggregationKind == AggregationKind.NONE_LITERAL) || (aggregationKind == AggregationKind.SHARED_LITERAL))) {
                        EReference assocRef =
                                EcoreFactory.eINSTANCE.createEReference();
                        assocRef.setName(prop.getName());
                        assocRef.setEType(umlToEmfMapper.getEmfClass(prop
                                .getType()));
                        assocRef.setUpperBound(prop.getUpper());
                        assocRef.setLowerBound(prop.getLower());

                        // Add the extended meta data for the name
                        ExtendedMetaData.INSTANCE.setFeatureKind(assocRef,
                                ExtendedMetaData.ELEMENT_FEATURE);
                        ExtendedMetaData.INSTANCE.setName(assocRef,
                                assocRef.getName());

                        // Get the other side of the association
                        for (Type endType : association.getEndTypes()) {
                            // Both ends of the association will be returned
                            // so we want the other end, which is the one
                            // that is not the type we are currently doing
                            if (endType != type) {
                                EClass targetClass =
                                        umlToEmfMapper.getEmfClass(endType);
                                // Need to get the name from the other side of
                                // the association
                                Property otherEnd = prop.getOtherEnd();

                                if ((targetClass != null) & (otherEnd != null)) {
                                    String otherEndName = otherEnd.getName();
                                    if (otherEndName != null) {
                                        EStructuralFeature oppRef =
                                                targetClass
                                                        .getEStructuralFeature(otherEndName);
                                        if ((oppRef != null)
                                                && (oppRef instanceof EReference)) {
                                            assocRef.setEOpposite((EReference) oppRef);
                                            ((EReference) oppRef)
                                                    .setEOpposite(assocRef);
                                        }
                                    }
                                }
                            }
                        }

                        EClass eClass = umlToEmfMapper.getEmfClass(type);
                        // Need to add in the correct order
                        insertAssociationIntoList(assocRef, eClass, umlClass);
                    }
                }
            }
        }
    }

    /**
     * Inserts the EReference for an association into an existing list in the
     * expected order according to the BOM
     * 
     * @param assocRef
     * @param eClass
     * @param umlClass
     */
    private void insertAssociationIntoList(EStructuralFeature assocRef,
            EClass eClass, org.eclipse.uml2.uml.Class umlClass) {

        // Get the order of the attributes in the uml model
        List<String> umlAttribList = new ArrayList<String>();
        for (Property prop : umlClass.getAttributes()) {
            umlAttribList.add(prop.getName());
        }
        int indexOfAssoc = umlAttribList.indexOf(assocRef.getName());

        int insertLocation = -1;
        EList<EStructuralFeature> eStructuralFeatures =
                eClass.getEStructuralFeatures();
        for (int i = 0; i < eStructuralFeatures.size(); i++) {
            String currentName = eStructuralFeatures.get(i).getName();

            // Find out where in the uml attribute list this appears
            if (indexOfAssoc < umlAttribList.indexOf(currentName)) {
                // The association needs to be inserted before this one
                insertLocation = i;
                break;
            }
        }

        if (insertLocation < 0) {
            eStructuralFeatures.add(assocRef);
        } else {
            eStructuralFeatures.add(insertLocation, assocRef);
        }
    }

    private void processClass(org.eclipse.uml2.uml.Class umlClass, EClass eClass)
            throws GenerationException {

        boolean persistable = false;

        if (BOMGlobalDataUtils.isGlobalClass(umlClass)) {
            AnnotationManager.addGlobalClassAnnotation(eClass);
            persistable = true;
        }

        if (BOMGlobalDataUtils.isCaseClass(umlClass)) {
            AnnotationManager.addCaseClassAnnotation(eClass);
            // If it is a Case Class then we need to also add the annotation for
            // the Summary Information
            String summaryAnnotationValue =
                    SummaryInfoUtils.getSummaryAnnotationValue(umlClass);
            if ((summaryAnnotationValue != null)
                    && !summaryAnnotationValue.isEmpty()) {
                // Add the summary annotation to the eCore
                AnnotationManager.addCaseClassSummaryAnnotation(eClass,
                        summaryAnnotationValue);
            }
            persistable = true;
        }

        // Add if the given class supports case documentation
        if (BOMGlobalDataUtils.isCaseDocumentationEnabled(umlClass)) {
            AnnotationManager.addCaseDocumentationAnnotation(eClass);
            persistable = true;
        }

        // Store the label used in the BOM
        String label = PrimitivesUtil.getDisplayLabel(umlClass);
        if ((label != null) && !label.isEmpty()) {
            AnnotationManager.addLabelAnnotation(eClass, label);
        }

        // Store the description shown in the BOM
        String description = getDescription(umlClass);
        if ((description != null) && !description.isEmpty()) {
            AnnotationManager.addDescriptionAnnotation(eClass, description);
        }

        if (persistable) {

            // Construct a map of UML property name -> UML property
            Map<String, Property> umlPropMap = new HashMap<String, Property>();
            for (Property umlProp : umlClass.getAttributes()) {
                umlPropMap.put(umlProp.getName(), umlProp);
            }

            // Perform mappings on each attribute of the EClass
            for (EStructuralFeature feat : eClass.getEAllStructuralFeatures()) {
                String featureName = feat.getName();
                // There will be no UML property if this is the target end
                // of a bi-directional reference.
                if (umlPropMap.containsKey(featureName)) {
                    // Get corresponding UML attribute
                    Property umlProp = umlPropMap.get(featureName);
                    // Only map if defined on *this* class (not inherited)
                    if (feat.eContainer() == eClass) {
                        processStructuralFeature(umlProp, feat);
                    }
                }
            }
        }

        // Now add the BDS user overrides, need to do this before setting the
        // Teneo annotations, as they may depend on the BDS annotations values
        JPAAnnotations userBdsAnnotations =
                UserAnnotationManager.getBdsUserAnnotations(umlClass);
        AnnotationManager.applyBdsAnnotation(eClass, userBdsAnnotations);

        AnnotationManager.applyTeneoAnnotations(eClass);

        // Add the user annotations to the eClass
        JPAAnnotations userAnnotations =
                UserAnnotationManager.getUserAnnotations(umlClass);
        AnnotationManager.applyUserAnnotation(eClass, userAnnotations);
    }

    /**
     * Adds annotations to the given enum indicating the BOM 'label' value for
     * it and its literals.
     * 
     * @param umlEnum
     * @param eEnum
     */
    private void processEnumeration(Enumeration umlEnum, EEnum eEnum) {

        // Store the label used in the BOM
        String label = PrimitivesUtil.getDisplayLabel(umlEnum);
        if ((label != null) && !label.isEmpty()) {
            AnnotationManager.addLabelAnnotation(eEnum, label);
        }

        // Get the BOM label for each literal and record it in the corresponding
        // ECore literal via an annotation.
        for (EEnumLiteral lit : eEnum.getELiterals()) {
            EnumerationLiteral umlLit = umlEnum.getOwnedLiteral(lit.getName());
            if (umlLit != null) {
                String litLabel = PrimitivesUtil.getDisplayLabel(umlLit);
                if (litLabel != null) {
                    AnnotationManager.addLabelAnnotation(lit, litLabel);
                }
            }
        }
    }

    // Gets the BOM primitive type that is the ultimate
    // base type of the supplied type. If the given type
    // is already a BOM primitive type, it will be, itself, returned.
    private Type getBaseType(Type t) {
        EList<Relationship> rList = t.getRelationships();
        Type general = null;
        for (Iterator<Relationship> iter = rList.iterator(); iter.hasNext();) {
            Relationship r = iter.next();
            if (r instanceof Generalization) {
                Generalization generalization = (Generalization) r;
                // If this type has super- and sub-types, where will
                // be relationships in both directions, so make sure this
                // class participates in a 'specific' (child) capacity.
                if (generalization.getSpecific() == t) {
                    // General is our super-type
                    general = generalization.getGeneral();
                }
            }
        }
        Type result = null;
        if (general != null) {
            result = getBaseType(general);
        } else {
            // No super-type, so return self
            result = t;
        }
        return result;
    }

    private void processStructuralFeature(Property umlProperty,
            EStructuralFeature feat) throws GenerationException {

        if (BOMGlobalDataUtils.isCID(umlProperty)) {
            // It's a CID, so add appropriate BDS & Teneo annotations
            AnnotationManager.addCIDAnnotation(feat);
        } else if (BOMGlobalDataUtils.isSearchable(umlProperty)) {
            // It's a Searchable Attribute, so add appropriate BDS & Teneo
            // annotations
            AnnotationManager.addSearchableAnnotation(feat);
        }

        if (feat instanceof EAttribute) {
            Type t = umlProperty.getType();
            // Don't set this if it's an enumeration
            if (!(t instanceof Enumeration)) {
                // Find the BOM primitive type that is the base type of this
                // attribute's type and add its name as an annotation.
                Type baseType = getBaseType(t);
                AnnotationManager.addBOMBaseTypeAnnotation((EAttribute) feat,
                        baseType.getName());
            }
        }

        // Check to see if this is an AutoCID
        if (BOMGlobalDataUtils.isAutoCID(umlProperty)) {
            AnnotationManager.addAutoCIDAnnotation(feat);
        }
        // Check if this is a composite CID
        if (BOMGlobalDataUtils.isCompositeCID(umlProperty)) {
            AnnotationManager.addCompositeCIDAnnotation(feat);
        }

        // Check to see if this is a Case State attribute
        if (BOMGlobalDataUtils.isCaseState(umlProperty)) {
            AnnotationManager.addCaseStateAnnotation(feat);
        }

        // Check if the batch size needs to be added
        Integer batchSize =
                BOMGlobalDataUtils.getFetchingBatchSize(umlProperty);
        if ((batchSize != null) && (batchSize.intValue() != 0)) {
            AnnotationManager.addBatchSizeAnnotation(feat, batchSize);
        }

        // Store the label used in the BOM
        String label = PrimitivesUtil.getDisplayLabel(umlProperty);
        if ((label != null) && !label.isEmpty()) {
            AnnotationManager.addLabelAnnotation(feat, label);
        }

        // Store the description shown in the BOM
        String description = getDescription(umlProperty);
        if ((description != null) && !description.isEmpty()) {
            AnnotationManager.addDescriptionAnnotation(feat, description);
        }

        // Now add the BDS user overrides, need to do this before setting the
        // Teneo annotations, as they may depend on the BDS annotations values
        JPAAnnotations userBdsAnnotations =
                UserAnnotationManager.getBdsUserAnnotations(umlProperty);
        AnnotationManager.applyBdsAnnotation(feat, userBdsAnnotations);

        if (feat instanceof EAttribute) {
            AnnotationManager.applyTeneoAnnotations((EAttribute) feat);
        } else if (feat instanceof EReference) {
            // Check for the case where the reference bridges two different
            // projects. We need to check this at the BOM level and then pass it
            // into the part that adds the annotations
            boolean isCrossProject = false;
            Class source = umlProperty.getClass_();
            Type type = umlProperty.getType();
            if (type instanceof Class) {
                if (!BOMUtils.hasSameProject(source, type)) {
                    isCrossProject = true;
                }
            }

            AnnotationManager.applyTeneoAnnotations((EReference) feat,
                    isCrossProject);
        }

        // Add the user specified annotations to this eStructurealFeature
        JPAAnnotations userAnnotations =
                UserAnnotationManager.getUserAnnotations(umlProperty);
        AnnotationManager.applyUserAnnotation(feat, userAnnotations);
    }

    /**
     * Get the description for a given class or property
     * 
     * @param element
     *            Element to get the description from
     * @return
     */
    private String getDescription(NamedElement element) {
        EList<Comment> ownedComments = element.getOwnedComments();
        StringBuilder desc = new StringBuilder();
        if (ownedComments != null) {
            for (Comment comment : ownedComments) {
                if (comment != null) {
                    String subdesc = comment.getBody();
                    if ((subdesc != null) && !subdesc.trim().isEmpty()) {
                        if (desc.length() > 0) {
                            desc.append('\n');
                        }
                        desc.append(subdesc);
                    }
                }
            }
        }
        return desc.toString();
    }
}
