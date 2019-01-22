package com.tibco.xpd.bom.resources.utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.Image;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.util.UMLUtil;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.internal.Messages;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;

public class UML2ModelUtil extends UMLUtil {

    private static final String BE_LIBRARY_URI =
            "pathmap://BOM_TYPES/BomPrimitiveTypes.library.uml";

    private static EList bePrimitives;

    /**
     * Types of the class properties.
     */
    public enum PropertyType {
        UNKNOWN, PROPERTY, ASSOCIATION
    }

    /**
     * Types of the associations.
     */
    public enum ClassRelationType {
        ASSOCIATION, AGGREGATION, COMPOSITION;
    }

    /**
     * Get the fully qualified label for the given <code>NamedElement</code>.
     * 
     * @see NamedElement#getLabel()
     * 
     * @param elem
     *            <code>NamedElement</code> to get the label of.
     * @return Fully qualified label of the element.
     */
    public static String getQualifiedLabel(NamedElement elem) {
        String label = null;

        label = elem.getLabel();

        if (elem.getNamespace() != null) {
            String containerLabel = getQualifiedLabel(elem.getNamespace());

            if (containerLabel != null) {
                label = containerLabel + NamedElement.SEPARATOR + label;
            }
        }

        return label;
    }

    /**
     * Identify if given property should be displayed as a property or
     * association.
     * 
     * @param uml2ClassProperty
     * @return
     */
    public static PropertyType identify(Property uml2ClassProperty) {
        PropertyType result;
        if (uml2ClassProperty.getAssociation() != null)
            result = PropertyType.ASSOCIATION;
        else
            result = PropertyType.PROPERTY;
        return result;
    }

    /**
     * True if the association is bidirectional.
     * 
     * @param association
     * @return
     */
    public static boolean isAssociationBiDirectional(Association association) {
        return association.getOwnedEnds().isEmpty();
    }

    /**
     * @param association
     * @return collection of {@link Class} elements corresponding to the
     *         classifiers on either end of the association relationship. If the
     *         relationship is directional the source class will be held in the
     *         first element and the target class in the second. Empty
     *         collection returned if failed to determine both end classes for
     *         the relationship.
     */
    public static List<Class> getAssociationEnds(Association association) {

        if (UML2ModelUtil.isAssociationBiDirectional(association)) {
            return UML2ModelUtil.getBiDirectionalAssociationEnds(association);
        } else {
            return UML2ModelUtil.getUniDirectionalAssociationEnds(association);
        }
    }

    /**
     * @param association
     * @return collection of two {@link Class} elements: source class in the
     *         first and target class in the second. Empty collection if failed
     *         to determine end classes for the relationship.
     */
    private static List<Class> getUniDirectionalAssociationEnds(
            Association association) {

        // This can be the case if a 'Refactor To Association Class' on an
        // association is done
        if (association.getOwnedEnds().size() == 2) {
            return Collections.<Class> emptyList();
        }

        Assert.isTrue(association.getOwnedEnds().size() == 1,
                "Attempted method call with an eclipse.uml2.uml.Association instance that appears not to describe a unidirectional relationship"); //$NON-NLS-1$

        Property pTarget = association.getOwnedEnds().iterator().next();
        if (pTarget.getType() instanceof Class) {
            Class source = (Class) pTarget.getType();
            for (Property sAttrib : source.getOwnedAttributes()) {
                Association associationAttrib = sAttrib.getAssociation();
                if (associationAttrib != null
                        && associationAttrib.equals(association)) {
                    if (sAttrib.getType() instanceof Class) {
                        Class target = (Class) sAttrib.getType();

                        List<Class> endClasses = new ArrayList<Class>();
                        if (endClasses.add(source)) {
                            if (endClasses.add(target)) {
                                return endClasses;
                            }
                        }
                    }
                }
            }
        }
        return Collections.<Class> emptyList();
    }

    /**
     * @param association
     * @return collection of {@link Class} elements corresponding to the member
     *         ends of a bi-directional relationship. Empty collection if failed
     *         to determine end classes for the relationship.
     */
    private static List<Class> getBiDirectionalAssociationEnds(
            Association association) {

        Assert.isTrue(isAssociationBiDirectional(association),
                "Attempted method call with an eclipse.uml2.uml.Association instance that appears not to describe a bidirectional relationship"); //$NON-NLS-1$

        Iterator<Property> iterator = association.getMemberEnds().iterator();

        if (iterator.hasNext()) {
            List<Class> endClasses = new ArrayList<Class>();
            boolean added;

            do {
                added = false;
                Property pEnd = iterator.next();
                if (pEnd.getType() instanceof Class) {
                    added = endClasses.add((Class) pEnd.getType());
                }
            } while (iterator.hasNext() && added);

            if (added) {
                return endClasses;
            }
            ;
        }
        return Collections.<Class> emptyList();
    }

    /**
     * 
     * Checks whether the association is of type Aggregation, Composition or
     * None.
     * 
     * @param Association
     *            association
     * @return AggregationKind result
     */
    @SuppressWarnings("unchecked")
    public static AggregationKind getAggregationType(Association association) {
        List<Property> memberEnds = association.getMemberEnds();
        AggregationKind result = AggregationKind.NONE_LITERAL;
        for (Property property : memberEnds) {
            if (property.getAggregation() == AggregationKind.SHARED_LITERAL)
                result = AggregationKind.SHARED_LITERAL;
            else if (property.getAggregation() == AggregationKind.COMPOSITE_LITERAL)
                result = AggregationKind.COMPOSITE_LITERAL;
        }
        return result;
    }

    /**
     * 
     * Returns a Class name unique within the parent package
     * 
     * @param parentPackage
     * @return String
     */
    public static String createUniqueElementName(Package parentPackage,
            String prefix) {

        String result =
                UniquenessStrategy.getInstance()
                        .genUniqueElementName(parentPackage, prefix);
        return result;
    }

    /**
     * 
     * Returns a Package name unique within the parent package
     * 
     * @param parentPackage
     * @return String
     */
    public static String createUniquePackageName(Package parentPackage) {
        String result =
                UniquenessStrategy.getInstance()
                        .genUniquePackageName(parentPackage);
        return result;
    }

    /**
     * 
     * Returns a Package name unique within the parent package
     * 
     * @param parentPackage
     * @return String
     */
    public static String createUniquePackageName(Package parentPackage,
            String prefix) {
        String result =
                UniquenessStrategy.getInstance()
                        .genUniquePackageName(parentPackage, prefix);
        return result;
    }

    /**
     * 
     * Returns an AssociationClass name unique within the parent package
     * 
     * @param parentPackage
     * @return String
     */
    public static String createUniqueAssociationClassName(Package parentPackage) {

        String result =
                UniquenessStrategy.getInstance()
                        .genUniqueAssociationClassName(parentPackage);
        return result;
    }

    /**
     * 
     * Returns a Class name unique within the parent package
     * 
     * @param parentPackage
     * @return String
     */
    public static String createUniqueClassName(Package parentPackage) {

        String result =
                UniquenessStrategy.getInstance()
                        .genUniqueClassName(parentPackage);
        return result;
    }

    /**
     * 
     * Returns a Class name unique within the parent package
     * 
     * @param parentPackage
     * @return String
     */
    public static String createUniqueClassName(Package parentPackage,
            String prefix) {

        String result =
                UniquenessStrategy.getInstance()
                        .genUniqueClassName(parentPackage, prefix);
        return result;
    }

    /**
     * 
     * Returns a Concept name unique within the parent package
     * 
     * @param parentPackage
     * @return String
     */
    public static String createUniqueConceptName(Package parentPackage) {

        String result =
                UniquenessStrategy.getInstance()
                        .genUniqueConceptName(parentPackage);
        return result;
    }

    /**
     * 
     * Returns a Primitive Type name unique within the parent package
     * 
     * @param parentPackage
     * @return String
     */
    public static String createUniquePrimitiveTypeName(Package parentPackage) {

        String result =
                UniquenessStrategy.getInstance()
                        .genUniquePrimitiveTypeName(parentPackage);
        return result;
    }

    /**
     * 
     * Returns a Primitive Type name unique within the parent package
     * 
     * @param parentPackage
     * @return String
     */
    public static String createUniquePrimitiveTypeName(Package parentPackage,
            String prefix) {

        String result =
                UniquenessStrategy.getInstance()
                        .genUniquePrimitiveTypeName(parentPackage, prefix);
        return result;
    }

    /**
     * 
     * Returns an Enumeration name unique within the parent package
     * 
     * @param parentPackage
     * @return String
     */
    public static String createUniqueEnumerationName(Package parentPackage) {

        String result =
                UniquenessStrategy.getInstance()
                        .genUniqueEnumerationName(parentPackage);
        return result;
    }

    /**
     * 
     * Returns an Enumeration name unique within the parent package
     * 
     * @param parentPackage
     * @return String
     */
    public static String createUniqueEnumerationName(Package parentPackage,
            String prefix) {

        String result =
                UniquenessStrategy.getInstance()
                        .genUniqueEnumerationName(parentPackage, prefix);
        return result;
    }

    /**
     * 
     * Returns a Property name unique within its containing class
     * 
     * @param Property
     *            property
     * @return String
     */
    public static String createUniqueEnumLitName(Enumeration parentEn) {

        String result =
                UniquenessStrategy.getInstance().genUniqueEnumLitName(parentEn);
        return result;
    }

    /**
     * 
     * Returns a Property name unique within its containing class
     * 
     * @param Property
     *            property
     * @return String
     */
    public static String createUniqueEnumLitName(Enumeration parentEn,
            String prefix) {

        String result =
                UniquenessStrategy.getInstance().genUniqueEnumLitName(parentEn,
                        prefix);
        return result;
    }

    /**
     * 
     * Returns a Property name unique within its containing class
     * 
     * @param Property
     *            property
     * @return String
     */
    public static String createUniquePropertyName(Property prop) {

        String result =
                UniquenessStrategy.getInstance().genUniquePropertyName(prop);
        return result;
    }

    /**
     * 
     * Returns a Property name unique within its containing class
     * 
     * @param Property
     *            property
     * @return String
     */
    public static String createUniquePropertyName(Property prop, String prefix) {

        String result =
                UniquenessStrategy.getInstance().genUniquePropertyName(prop,
                        prefix);
        return result;
    }

    /**
     * Get the unique <code>Property</code> name in the given <code>Class</code>
     * .
     * 
     * @param parentClass
     *            <code>Class</code> to which the <code>Property</code> will be
     *            added.
     * @return unique property name. Empty string if name cannot be determined.
     */
    public static String createUniquePropertyName(Class parentClass) {
        return UniquenessStrategy.getInstance()
                .getUniquePropertyName(parentClass);
    }

    /**
     * Get the unique <code>Property</code> name in the given <code>Class</code>
     * .
     * 
     * @param parentClass
     *            <code>Class</code> to which the <code>Property</code> will be
     *            added.
     * @return unique property name. Empty string if name cannot be determined.
     */
    public static String createUniquePropertyName(Class parentClass,
            String prefix) {
        return UniquenessStrategy.getInstance()
                .getUniquePropertyName(parentClass, prefix);
    }

    /**
     * 
     * Returns a Property name unique within its containing class
     * 
     * @param Property
     *            property
     * @return String
     */
    public static String createUniqueOperationName(Operation op, String prefix) {

        String result =
                UniquenessStrategy.getInstance().genUniqueOperationName(op,
                        prefix);
        return result;
    }

    /**
     * Get the unique <code>Operation</code> name in the given
     * <code>Class</code> .
     * 
     * @param parentClass
     *            <code>Class</code> to which the <code>Operation</code> will be
     *            added.
     * @return unique operation name. Empty string if name cannot be determined.
     */
    public static String createUniqueOperationName(Class parentClass) {
        return UniquenessStrategy.getInstance()
                .getUniqueOperationName(parentClass);
    }

    /**
     * 
     * Returns an Association name unique within its containing parent package.
     * Handles all types of association i.e. Association, Aggregation,
     * Composition.
     * 
     * @param Property
     *            property
     * @return String
     */
    public static String createUniqueAssociationName(Package parentPackage,
            Association assoc) {

        String result =
                UniquenessStrategy.getInstance()
                        .genUniqueAssociationName(parentPackage, assoc);
        return result;
    }

    /**
     * 
     * Returns an Association name unique within its containing parent package.
     * Handles all types of association i.e. Association, Aggregation,
     * Composition.
     * 
     * @param Property
     *            property
     * @return String
     */
    public static String createUniqueAssociationName(Package parentPackage,
            Association assoc, String prefix) {

        String result =
                UniquenessStrategy.getInstance()
                        .genUniqueAssociationName(parentPackage, assoc, prefix);
        return result;
    }

    /**
     * Check if property is simple.
     * 
     * @param uml2Property
     *            Property to check
     * @return true if simple
     */
    public static boolean isSimpleProperty(Property uml2Property) {
        boolean result;

        AggregationKind aggregation = uml2Property.getAggregation();
        result = aggregation == AggregationKind.NONE_LITERAL;

        return result;
    }

    /**
     * Check if property is composite.
     * 
     * @param uml2Property
     *            property to check
     * @return true if composite
     */
    public static boolean isCompositeProperty(Property uml2Property) {
        boolean result;

        AggregationKind aggregation = uml2Property.getAggregation();
        result = aggregation == AggregationKind.COMPOSITE_LITERAL;

        return result;
    }

    /**
     * Get fully qualified name of the given package. If this is the concept
     * model itself then empty string will be returned.
     * 
     * @param parentPackage
     * @return
     */
    public static String getPackageQualifier(Package parentPackage) {
        String qualifier = ""; //$NON-NLS-1$

        if (parentPackage != null) {
            if (parentPackage.eContainer() instanceof Package) {
                qualifier = parentPackage.getName();
                String parent =
                        getPackageQualifier((Package) parentPackage
                                .eContainer());

                if (parent.length() > 0) {
                    qualifier = parent + "." + qualifier; //$NON-NLS-1$
                }
            }
        }

        return qualifier;
    }

    /**
     * Recursive method to search Class within subpackages.
     * 
     * @param pkg
     *            Package to search.
     * @param className
     *            Qualified class name to search for.
     * @param objectList
     *            List of objects found.
     */
    @SuppressWarnings("unchecked")
    public static void searchPackage(Package pkg, String className,
            List<Class> objectList) {
        if (pkg != null) {
            List<PackageableElement> pkgElements = pkg.getPackagedElements();
            for (PackageableElement element : pkgElements) {
                if (element instanceof Package) {
                    searchPackage((Package) element, className, objectList);
                }
                if (element instanceof Class
                        && element.getQualifiedName() != null
                        && getName(element.getQualifiedName())
                                .equals(className)) {
                    objectList.add((Class) element);
                }
            }
        }
    }

    /**
     * Recursive method to search a package and its subpackages for Classifiers
     * with the same name as the supplied Classifier.
     * 
     * @param pkg
     *            Package to search.
     * @param classifierName
     *            Qualified classifier name to search for.
     * @param objectList
     *            List of objects found.
     */
    public static void searchPackageForClassifer(Package pkg,
            String classifierName, List<Classifier> objectList) {
        if (pkg != null) {
            List<PackageableElement> pkgElements = pkg.getPackagedElements();
            for (PackageableElement element : pkgElements) {
                if (element instanceof Package) {
                    searchPackageForClassifer((Package) element,
                            classifierName,
                            objectList);
                }
                if (element instanceof Classifier
                        && element.getQualifiedName() != null
                        && getName(element.getQualifiedName())
                                .equals(classifierName)) {
                    objectList.add((Classifier) element);
                }
            }
        }
    }

    /**
     * Recursive method to search a Package and collect all the classes
     * conatined within it and its sub-packages.
     * 
     * @param pkg
     *            Package to search.
     * @param classList
     *            List of objects found.
     */
    @SuppressWarnings("unchecked")
    public static void collectClassesInPackage(Package pkg,
            List<EObject> classList) {
        if (pkg == null) {
            return;
        }
        List<PackageableElement> pkgElements = pkg.getPackagedElements();
        for (PackageableElement element : pkgElements) {
            if (element instanceof Package) {
                collectClassesInPackage((Package) element, classList);
            }
            if (element instanceof Class) {
                classList.add(element);
            }
        }
    }

    /**
     * Recursive method to search a Package and collect all the classes
     * conatined within it and its sub-packages.
     * 
     * @param pkg
     *            Package to search.
     * @param classList
     *            List of objects found.
     */
    @SuppressWarnings("unchecked")
    public static void collectPrimitiveTypesInPackage(Package pkg,
            List<EObject> classList) {
        if (pkg == null) {
            return;
        }
        List<PackageableElement> pkgElements = pkg.getPackagedElements();
        for (PackageableElement element : pkgElements) {
            if (element instanceof Package) {
                collectClassesInPackage((Package) element, classList);
            }
            if (element instanceof PrimitiveType) {
                classList.add(element);
            }
        }
    }

    /**
     * Recursive method to search a Package and collect all the Enumeration
     * contained within it and its sub-packages.
     * 
     * @param pkg
     *            Package to search.
     * @param classList
     *            List of objects found.
     */
    @SuppressWarnings("unchecked")
    public static void collectEnumerationsInPackage(Package pkg,
            List<EObject> enumList) {
        if (pkg == null) {
            return;
        }
        List<PackageableElement> pkgElements = pkg.getPackagedElements();
        for (PackageableElement element : pkgElements) {
            if (element instanceof Package) {
                collectClassesInPackage((Package) element, enumList);
            }
            if (element instanceof Enumeration) {
                enumList.add(element);
            }
        }
    }

    /**
     * Recursive method to search Class within subpackages.
     * 
     * @param pkg
     *            Package to search.
     * @param className
     *            Qualified class name to search for.
     * @param objectList
     *            List of objects found.
     */
    @SuppressWarnings("unchecked")
    public static void searchPackageForPrimType(Package pkg, String typeName,
            List<PrimitiveType> objectList) {
        List<PackageableElement> pkgElements = pkg.getPackagedElements();
        for (PackageableElement element : pkgElements) {
            if (element instanceof Package) {
                searchPackageForPrimType((Package) element,
                        typeName,
                        objectList);
            }
            if (element instanceof PrimitiveType
                    && element.getQualifiedName() != null
                    && getName(element.getQualifiedName()).equals(typeName)) {
                objectList.add((PrimitiveType) element);
            }
        }
    }

    /**
     * Recursive method to search for Enumeration types within a package.
     * 
     * @param pkg
     *            Package to search.
     * @param className
     *            Qualified class name to search for.
     * @param objectList
     *            List of objects found.
     */
    @SuppressWarnings("unchecked")
    public static void searchPackageForEnumeration(Package pkg,
            String typeName, List<Classifier> objectList) {
        List<PackageableElement> pkgElements = pkg.getPackagedElements();
        for (PackageableElement element : pkgElements) {
            if (element instanceof Package) {
                searchPackageForEnumeration((Package) element,
                        typeName,
                        objectList);
            }
            if (element instanceof Enumeration
                    && element.getQualifiedName() != null
                    && getName(element.getQualifiedName()).equals(typeName)) {
                objectList.add((Classifier) element);
            }
        }
    }

    /**
     * Get the name from the given fully qualified name.
     * 
     * @param qualifiedName
     * @return name, can be <code>null</code> if the qualified name is
     *         <code>null</code>.
     */
    private static String getName(String qualifiedName) {
        String name = qualifiedName;

        if (name != null && name.indexOf(NamedElement.SEPARATOR) > 0) {
            name =
                    name.substring(name.lastIndexOf(NamedElement.SEPARATOR)
                            + NamedElement.SEPARATOR.length());
        }

        return name;
    }

    public static String createAssociationName(NamedElement namedElement) {
        String selectedName = namedElement.getName();
        char charAt = selectedName.toLowerCase().charAt(0);
        StringBuilder assocName = new StringBuilder();
        assocName.append(charAt);
        if (selectedName.length() > 1) {
            assocName.append(selectedName.substring(1));
        }
        return assocName.toString();
    }

    public static boolean isMultiple(Property uml2Property) {
        boolean result;
        ValueSpecification upperValueSpec = uml2Property.getUpperValue();
        if (upperValueSpec instanceof LiteralInteger) {
            int value = ((LiteralInteger) upperValueSpec).getValue();
            result = Boolean.valueOf(value > 1);
        } else {
            result = true;
        }
        return result;
    }

    public static boolean isSelfReference(Association association) {
        if (association.getEndTypes().size() < 2)
            return true;
        return false;
    }

    public static String getAssociationType(Association association) {
        String associationType = null;
        AggregationKind associationAggregation =
                UML2ModelUtil.getAggregationType(association);
        switch (associationAggregation.getValue()) {
        case AggregationKind.NONE:
            associationType = "3002"; //$NON-NLS-1$
            break;
        case AggregationKind.SHARED:
            associationType = "3003"; //$NON-NLS-1$
            break;
        case AggregationKind.COMPOSITE:
            associationType = "3004"; //$NON-NLS-1$
            break;
        default:
            break;
        }
        return associationType;
    }

    /**
     * Derives the List of Objects for the specified qualified class name by
     * searching all projects in the workspace.
     * 
     * @param classQualifiedName
     *            - qualified name of the class
     * @return
     */
    private static List<Class> deriveObject(String classQualifiedName) {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        List<Class> objectList = new ArrayList<Class>();
        IProject[] projects = root.getProjects();
        for (IProject project : projects) {
            List<Class> foundObjectList =
                    deriveObject(classQualifiedName, project);
            objectList.addAll(foundObjectList);
        }
        return objectList;
    }

    /**
     * Derives the List of Objects for the specified class name (optionally
     * qualified with package) within the given project. If project is null then
     * search all projects in worspace.
     * 
     * @param classQualifiedName
     *            Name or qualified name to search for. Use the UML :: notation,
     *            with dots between packages. If no package specified then all
     *            matching classes will be returned.
     * @param project
     *            Search target or null for all projects.
     * @return List of found objects.
     */
    public static List<Class> deriveObject(final String classQualifiedName,
            IProject project) {
        if (project == null) {
            return deriveObject(classQualifiedName);
        }
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        final List<Class> foundObjectList = new ArrayList<Class>();
        if (config != null) {
            EList<SpecialFolder> conceptFolders =
                    config.getSpecialFolders()
                            .getFoldersOfKind(BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
            if (conceptFolders != null) {
                for (SpecialFolder rsrs : conceptFolders) {
                    SpecialFolder conceptFolder = rsrs;
                    if (conceptFolder.getFolder() != null) {
                        try {
                            conceptFolder.getFolder()
                                    .accept(new IResourceVisitor() {
                                        @Override
                                        public boolean visit(IResource resource)
                                                throws CoreException {
                                            return deriveInResource(classQualifiedName,
                                                    resource,
                                                    foundObjectList);
                                        }
                                    });
                        } catch (CoreException e) {
                            Logger logger =
                                    BOMResourcesPlugin.getDefault().getLogger();
                            String message =
                                    String.format(Messages.UML2ModelUtil_search_error_message,
                                            classQualifiedName,
                                            conceptFolder.getLocation());
                            logger.error(e, message);
                        }
                    }
                }
            }
        }
        return foundObjectList;
    }

    /**
     * Visit a resource looking for the classQualifiedName. Classes are added to
     * the found object list.
     * 
     * @param classQualifiedName
     * @param resource
     * @param foundObjectList
     * @return true to keep on looking in child resources.
     */
    protected static boolean deriveInResource(String classQualifiedName,
            IResource resource, List<Class> foundObjectList) {
        if (resource instanceof IFile) {
            if (resource.getFileExtension() != null
                    && resource.getFileExtension()
                            .equals(BOMResourcesPlugin.BOM_FILE_EXTENSION)) {
                WorkingCopy wc =
                        XpdResourcesPlugin.getDefault()
                                .getWorkingCopy(resource);

                if (wc instanceof BOMWorkingCopy) {
                    org.eclipse.uml2.uml.Package pkg =
                            (org.eclipse.uml2.uml.Package) wc.getRootElement();
                    if (pkg != null) {
                        searchPackage(pkg, classQualifiedName, foundObjectList);
                    }
                }
            }
            return false;
        } else {
            // keep looking
            return true;
        }
    }

    /**
     * Checks if the property is an association end property representing an
     * extension. Usually it is used to check if the property is used for an
     * extension of of the stereotype.
     * 
     * @param property
     *            property to check.
     * @return true if the property is used as an extension end.
     * @throws NullPointerException
     *             if the property is null.
     */
    public static boolean isExtensionProperty(Property property) {
        return property != null
                && property.getAssociation() instanceof Extension;
    }

    private static EList getBEPrimitives() {
        if (bePrimitives == null) {
            ResourceSet rSet = new ResourceSetImpl();
            URI resourceURI = URI.createURI(BE_LIBRARY_URI);
            Resource resource = rSet.getResource(resourceURI, true);
            EList contents = resource.getContents();
            Package primitivesPackage =
                    (org.eclipse.uml2.uml.Package) EcoreUtil
                            .getObjectByType(contents,
                                    UMLPackage.Literals.PACKAGE);
            bePrimitives = primitivesPackage.getPackagedElements();
        }
        return bePrimitives;
    }

    @SuppressWarnings("unchecked")
    public static synchronized ArrayList<Type> getPrimitives() {
        ArrayList<Type> primitiveTypes = new ArrayList<Type>();
        // primitiveTypes.addAll(UML2ModelUtil.getUMLPrimitives());
        primitiveTypes.addAll(UML2ModelUtil.getBEPrimitives());
        sortTypes(primitiveTypes);
        return primitiveTypes;
    }

    public static void sortTypes(ArrayList<Type> types) {
        Collections.sort(types, new Comparator<Type>() {
            @Override
            public int compare(Type o1, Type o2) {
                return -(o1.getLabel().compareTo(o2.getLabel()));
            }
        });
    }

    /**
     * 
     * Returns the ImageDescriptor of the first UML2 Image in the stereotype's
     * list of icons.
     * 
     * 
     * @param stereo
     * @return ImageDescriptor or null
     */
    public static ImageDescriptor getStereotypeImageDescriptor(Stereotype stereo) {

        ImageDescriptor iDesc = null;
        Resource eResource = stereo.eResource();

        if (eResource != null) {
            ResourceSet resourceSet = eResource.getResourceSet();

            if (resourceSet != null) {
                URIConverter uriConverter = resourceSet.getURIConverter();
                URI normalizedURI = uriConverter.normalize(eResource.getURI());

                EList<Image> icons = stereo.getIcons();
                if (!icons.isEmpty()) {
                    Image icon = stereo.getIcons().get(0);
                    String location = icon.getLocation();

                    if (!UML2Util.isEmpty(location)) { //$NON-NLS-1$

                        URI uri =
                                URI.createURI(location).resolve(normalizedURI);

                        try {
                            URL url =
                                    new URL(uriConverter.normalize(uri)
                                            .toString());
                            iDesc = ImageDescriptor.createFromURL(url);

                        } catch (Exception e) {
                            // ignore
                        }
                    }
                }
            }
        }

        return iDesc;

    }

    /**
     * 
     * Returns the location of the first UML2 Image in the stereotype's list of
     * icons.
     * 
     * 
     * @param stereo
     * @return String or null
     */
    public static String getStereotypeImageLocation(Stereotype stereo) {
        Resource eResource = stereo.eResource();
        String location = null;

        if (eResource != null) {
            ResourceSet resourceSet = eResource.getResourceSet();

            if (resourceSet != null) {
                EList<Image> icons = stereo.getIcons();
                if (!icons.isEmpty()) {
                    Image icon = stereo.getIcons().get(0);
                    location = icon.getLocation();
                }
            }
        }

        return location;
    }

}
