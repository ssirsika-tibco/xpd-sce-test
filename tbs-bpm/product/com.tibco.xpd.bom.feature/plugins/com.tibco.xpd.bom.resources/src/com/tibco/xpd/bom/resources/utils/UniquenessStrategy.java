package com.tibco.xpd.bom.resources.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.resources.internal.Messages;

/**
 * This class is being left as a singleton for now so that we can revisit it in
 * the future to implement a uniqueness strategy on a project by project basis
 * similar to the previous Concept Modeller.
 * 
 * @author rgreen
 */
public class UniquenessStrategy {

    // private final Map<IProject, NameUniqueness> conceptCache;
    // private final Map<IProject, NameUniqueness> packageCache;

    // This code is guaranteed to be thread safe if singleton is instantiated
    // here
    public static final UniquenessStrategy INSTANCE = new UniquenessStrategy();

    private static final String DEFAULT_CONCEPT_NAME_PREFIX =
            Messages.UniquenessStrategy_Concept_Default_Prefix;

    private static final String DEFAULT_CLASS_NAME_PREFIX =
            Messages.UniquenessStrategy_Class_Default_Prefix;

    private static final String DEFAULT_PACKAGE_NAME_PREFIX =
            Messages.UniquenessStrategy_Package_Default_Prefix;

    private static final String DEFAULT_PRIMTYPE_NAME_PREFIX =
            Messages.UniquenessStrategy_PrimitiveType_Default_Prefix;

    private static final String DEFAULT_ENUM_NAME_PREFIX =
            Messages.UniquenessStrategy_Enumeration_Default_Prefix;

    private static final String DEFAULT_ATTRIBUTE_NAME_PREFIX =
            Messages.UniquenessStrategy_Attribute_Default_Prefix;

    private static final String DEFAULT_ASSOCIATION_NAME_PREFIX =
            Messages.UniquenessStrategy_Association_Default_Prefix;

    private static final String DEFAULT_AGGREGATION_NAME_PREFIX =
            Messages.UniquenessStrategy_Aggregation_Default_Prefix;

    private static final String DEFAULT_COMPOSITION_NAME_PREFIX =
            Messages.UniquenessStrategy_Composition_Default_Prefix;

    private static final String DEFAULT_ASSOCCLASS_NAME_PREFIX =
            Messages.UniquenessStrategy_AssocClass_Default_Prefix;

    private UniquenessStrategy() {
    }

    /**
     * @return
     */
    public static synchronized UniquenessStrategy getInstance() {

        // The Singleton has been created eagerly by the JVM
        return INSTANCE;
    }

    /**
     * 
     * Generates and returns a Package name unique within the supplied parent
     * package.
     * 
     * @param Package
     *            parentPackage
     * @return String name
     */
    public synchronized String genUniqueElementName(Package parentPackage,
            String prefix) {
        String name = ""; //$NON-NLS-1$

        // Build a HashSet of current class names
        HashSet<String> names = new HashSet<String>();

        List<?> elems = parentPackage.getPackagedElements();

        for (Object obj : elems) {
            if (obj instanceof Package) {
                Package cl = (Package) obj;
                names.add(cl.getName());
            }
            if (obj instanceof Class) {
                Class cl = (Class) obj;
                names.add(cl.getName());
            }
            if (obj instanceof PrimitiveType) {
                PrimitiveType primType = (PrimitiveType) obj;
                names.add(primType.getName());
            }
            if (obj instanceof Association) {
                Association asn = (Association) obj;
                names.add(asn.getName());
            }

        }

        int idx = 1;
        name = prefix + idx;

        while (names.contains(name)) {
            name = prefix + ++idx;
        }

        return name;

    }

    /**
     * 
     * Generates and returns a Package name unique within the supplied parent
     * package.
     * 
     * @param Package
     *            parentPackage
     * @return String name
     */
    public synchronized String genUniquePackageName(Package parentPackage) {
        String name = ""; //$NON-NLS-1$

        // Build a HashSet of current class names
        HashSet<String> packageNames = new HashSet<String>();

        List<?> elems = parentPackage.getPackagedElements();

        for (Object obj : elems) {
            if (obj instanceof Package) {
                Package cl = (Package) obj;
                packageNames.add(cl.getName());
            }
        }

        int idx = 1;
        name = String.format(DEFAULT_PACKAGE_NAME_PREFIX, idx);

        while (packageNames.contains(name)) {
            name = String.format(DEFAULT_PACKAGE_NAME_PREFIX, ++idx);
        }

        return name;

    }

    /**
     * 
     * Generates and returns a Package name unique within the supplied parent
     * package.
     * 
     * @param Package
     *            parentPackage
     * @return String name
     */
    public synchronized String genUniquePackageName(Package parentPackage,
            String prefix) {
        String name = ""; //$NON-NLS-1$

        // Build a HashSet of current class names
        HashSet<String> packageNames = new HashSet<String>();

        List<?> elems = parentPackage.getPackagedElements();

        for (Object obj : elems) {
            if (obj instanceof Package) {
                Package cl = (Package) obj;
                packageNames.add(cl.getName());
            }
        }

        int idx = 1;
        name = prefix + idx;

        while (packageNames.contains(name)) {
            name = prefix + ++idx;
        }

        return name;

    }

    /**
     * 
     * Generates and returns a Class name unique within the supplied parent
     * package.
     * 
     * @param Package
     *            parentPackage
     * @return String name
     */
    public synchronized String genUniqueAssociationClassName(
            Package parentPackage) {
        String name = ""; //$NON-NLS-1$

        // Build a HashSet of current class names
        HashSet<String> assocClassNames = new HashSet<String>();

        List<?> elems = parentPackage.getPackagedElements();

        for (Object obj : elems) {
            if (obj instanceof AssociationClass) {
                AssociationClass ac = (AssociationClass) obj;
                assocClassNames.add(ac.getName());
            }
        }

        int idx = 1;
        name = String.format(DEFAULT_ASSOCCLASS_NAME_PREFIX, idx);

        while (assocClassNames.contains(name)) {
            name = String.format(DEFAULT_ASSOCCLASS_NAME_PREFIX, ++idx);
        }

        return name;

    }

    /**
     * 
     * Generates and returns a Class name unique within the supplied parent
     * package.
     * 
     * @param Package
     *            parentPackage
     * @return String name
     */
    public synchronized String genUniqueClassName(Package parentPackage) {
        String name = ""; //$NON-NLS-1$

        // Build a HashSet of current class names
        HashSet<String> classNames = new HashSet<String>();

        List<?> elems = parentPackage.getPackagedElements();

        for (Object obj : elems) {
            if (obj instanceof Class) {
                Class cl = (Class) obj;
                classNames.add(cl.getName());
            }
        }

        int idx = 1;
        name = String.format(DEFAULT_CLASS_NAME_PREFIX, idx);

        while (classNames.contains(name)) {
            name = String.format(DEFAULT_CLASS_NAME_PREFIX, ++idx);
        }

        return name;

    }

    /**
     * 
     * Generates and returns a Class name unique within the supplied parent
     * package.
     * 
     * @param Package
     *            parentPackage
     * @return String name
     */
    public synchronized String genUniqueClassName(Package parentPackage,
            String prefix) {
        String name = ""; //$NON-NLS-1$

        // Build a HashSet of current class names
        HashSet<String> classNames = new HashSet<String>();

        List<?> elems = parentPackage.getPackagedElements();

        for (Object obj : elems) {
            if (obj instanceof Class) {
                Class cl = (Class) obj;
                classNames.add(cl.getName());
            }
        }

        int idx = 1;
        name = prefix + idx;

        while (classNames.contains(name)) {
            name = prefix + ++idx;
        }

        return name;

    }

    /**
     * 
     * Generates and returns a Class name unique within the supplied parent
     * package.
     * 
     * @param Package
     *            parentPackage
     * @return String name
     */
    public synchronized String genUniqueConceptName(Package parentPackage) {
        String name = ""; //$NON-NLS-1$

        // Build a HashSet of current class names
        HashSet<String> classNames = new HashSet<String>();

        List<?> elems = parentPackage.getPackagedElements();

        for (Object obj : elems) {
            if (obj instanceof Class) {
                Class cl = (Class) obj;
                classNames.add(cl.getName());
            }
        }

        int idx = 1;
        name = DEFAULT_CONCEPT_NAME_PREFIX + idx;

        while (classNames.contains(name)) {
            name = DEFAULT_CONCEPT_NAME_PREFIX + ++idx;
        }

        return name;

    }

    /**
     * 
     * Generates and returns a Primitive Type name unique within the supplied
     * parent package.
     * 
     * @param Package
     *            parentPackage
     * @return String name
     */
    public synchronized String genUniquePrimitiveTypeName(Package parentPackage) {
        String name = ""; //$NON-NLS-1$

        // Build a HashSet of current class names
        HashSet<String> primTypeNames = new HashSet<String>();

        List<?> elems = parentPackage.getPackagedElements();

        for (Object obj : elems) {
            if (obj instanceof PrimitiveType) {
                PrimitiveType primType = (PrimitiveType) obj;
                primTypeNames.add(primType.getName());
            }
        }

        int idx = 1;
        name = String.format(DEFAULT_PRIMTYPE_NAME_PREFIX, idx);

        while (primTypeNames.contains(name)) {
            name = String.format(DEFAULT_PRIMTYPE_NAME_PREFIX, ++idx);
        }

        return name;

    }

    /**
     * 
     * Generates and returns a Primitive Type name unique within the supplied
     * parent package.
     * 
     * @param Package
     *            parentPackage
     * @return String name
     */
    public synchronized String genUniquePrimitiveTypeName(
            Package parentPackage, String prefix) {
        String name = ""; //$NON-NLS-1$

        // Build a HashSet of current class names
        HashSet<String> primTypeNames = new HashSet<String>();

        List<?> elems = parentPackage.getPackagedElements();

        for (Object obj : elems) {
            if (obj instanceof PrimitiveType) {
                PrimitiveType primType = (PrimitiveType) obj;
                primTypeNames.add(primType.getName());
            }
        }

        int idx = 1;
        name = prefix + idx;

        while (primTypeNames.contains(name)) {
            name = prefix + ++idx;
        }

        return name;

    }

    /**
     * 
     * Generates and returns an Enumeration name unique within the supplied
     * parent package.
     * 
     * @param Package
     *            parentPackage
     * @return String name
     */
    public synchronized String genUniqueEnumerationName(Package parentPackage) {
        String name = ""; //$NON-NLS-1$

        // Build a HashSet of current class names
        HashSet<String> enumNames = new HashSet<String>();

        List<?> elems = parentPackage.getPackagedElements();

        for (Object obj : elems) {
            if (obj instanceof Enumeration) {
                Enumeration eN = (Enumeration) obj;
                enumNames.add(eN.getName());
            }
        }

        int idx = 1;
        name = String.format(DEFAULT_ENUM_NAME_PREFIX, idx);

        while (enumNames.contains(name)) {
            name = String.format(DEFAULT_ENUM_NAME_PREFIX, ++idx);
        }

        return name;

    }

    /**
     * 
     * Generates and returns an Enumeration name unique within the supplied
     * parent package.
     * 
     * @param Package
     *            parentPackage
     * @return String name
     */
    public synchronized String genUniqueEnumerationName(Package parentPackage,
            String prefix) {
        String name = ""; //$NON-NLS-1$

        // Build a HashSet of current class names
        HashSet<String> enumNames = new HashSet<String>();

        List<?> elems = parentPackage.getPackagedElements();

        for (Object obj : elems) {
            if (obj instanceof Enumeration) {
                Enumeration eN = (Enumeration) obj;
                enumNames.add(eN.getName());
            }
        }

        int idx = 1;
        name = prefix + idx;

        while (enumNames.contains(name)) {
            name = prefix + ++idx;
        }

        return name;

    }

    /**
     * 
     * Generates and returns a unique Attribute name.
     * 
     * @param Property
     *            prop
     * @return String name
     */
    public synchronized String genUniquePropertyName(Property prop) {
        String name = ""; //$NON-NLS-1$

        // Build a HashSet of current class names
        HashSet<String> propertyNames = new HashSet<String>();

        List<?> elems = prop.getClass_().getAttributes();

        for (Object obj : elems) {
            if (obj instanceof Property) {
                Property pty = (Property) obj;

                // Check its an attribute and not an Association
                if (pty.getAssociation() == null) {
                    propertyNames.add(pty.getName());
                }
            }
        }

        int idx = 1;
        name = String.format(DEFAULT_ATTRIBUTE_NAME_PREFIX, idx);

        while (propertyNames.contains(name)) {
            name = String.format(DEFAULT_ATTRIBUTE_NAME_PREFIX, ++idx);
        }

        return name;
    }

    /**
     * 
     * Generates and returns a unique Attribute name.
     * 
     * @param Property
     *            prop
     * @return String name
     */
    public synchronized String genUniquePropertyName(Property prop,
            String prefix) {
        String name = ""; //$NON-NLS-1$

        // Make sure first character is lower case
        prefix =
                prefix.replace(prefix.charAt(0), Character.toLowerCase(prefix
                        .charAt(0)));

        // Build a HashSet of current class names
        HashSet<String> propertyNames = new HashSet<String>();

        List<?> elems = prop.getClass_().getAttributes();

        for (Object obj : elems) {
            if (obj instanceof Property) {
                Property pty = (Property) obj;

                // Check its an attribute and not an Association
                if (pty.getAssociation() == null) {
                    propertyNames.add(pty.getName());
                }
            }
        }

        int idx = 1;
        name = prefix + idx;

        while (propertyNames.contains(name)) {
            name = prefix + ++idx;
        }

        return name;
    }

    /**
     * 
     * Generates and returns a unique Attribute name.
     * 
     * @param Property
     *            prop
     * @return String name
     */
    public synchronized String genUniqueOperationName(Operation operation,
            String prefix) {
        String name = ""; //$NON-NLS-1$

        // Make sure first character is lower case
        prefix =
                prefix.replace(prefix.charAt(0), Character.toLowerCase(prefix
                        .charAt(0)));

        // Build a HashSet of current class names
        HashSet<String> opNames = new HashSet<String>();

        List<?> elems = operation.getClass_().getOperations();

        for (Object obj : elems) {
            if (obj instanceof Operation) {
                Operation op = (Operation) obj;
                opNames.add(op.getName());
            }
        }

        int idx = 1;
        name = prefix + idx;

        while (opNames.contains(name)) {
            name = prefix + ++idx;
        }

        return name;
    }

    /**
     * Generate a unique attribute name in the given <code>Class</code>.
     * 
     * @param parentClass
     *            <code>Class</code> in which the <code>Property</code> will be
     *            added.
     * @return unique property name.
     */
    public synchronized String getUniquePropertyName(Class parentClass) {
        String name = ""; //$NON-NLS-1$      

        Set<String> propertyNames = new HashSet<String>();

        if (parentClass != null) {
            EList<Property> properties = parentClass.getAttributes();

            if (properties != null) {
                for (Property prop : properties) {
                    // Check its an attribute and not an Association
                    if (prop.getAssociation() == null) {
                        propertyNames.add(prop.getName());
                    }
                }
            }

            int idx = 1;
            name = String.format(DEFAULT_ATTRIBUTE_NAME_PREFIX, idx);

            while (propertyNames.contains(name)) {
                name = String.format(DEFAULT_ATTRIBUTE_NAME_PREFIX, ++idx);
            }
        }

        return name;
    }

    /**
     * Generate a unique attribute name in the given <code>Class</code>.
     * 
     * @param parentClass
     *            <code>Class</code> in which the <code>Property</code> will be
     *            added.
     * @return unique property name.
     */
    public synchronized String getUniquePropertyName(Class parentClass,
            String prefix) {
        String name = ""; //$NON-NLS-1$

        Set<String> propertyNames = new HashSet<String>();

        if (parentClass != null) {
            EList<Property> properties = parentClass.getAttributes();

            if (properties != null) {
                for (Property prop : properties) {
                    // Check its an attribute and not an Association
                    if (prop.getAssociation() == null) {
                        propertyNames.add(prop.getName());
                    }
                }
            }

            int idx = 1;
            name = prefix + idx;

            while (propertyNames.contains(name)) {
                name = prefix + ++idx;
            }
        }

        return name;
    }

    /**
     * Generate a unique operation name in the given <code>Class</code>.
     * 
     * @param parentClass
     *            <code>Class</code> in which the <code>Operation</code> will be
     *            added.
     * @return unique property name.
     */
    public synchronized String getUniqueOperationName(Class parentClass) {
        String name = ""; //$NON-NLS-1$

        Set<String> propertyNames = new HashSet<String>();

        if (parentClass != null) {
            EList<Operation> operations = parentClass.getOperations();

            if (operations != null) {
                for (Operation oper : operations) {
                    propertyNames.add(oper.getName());
                }
            }

            int idx = 1;
            name =
                    String
                            .format(Messages.UniquenessStrategy_Operation_Default_Prefix,
                                    idx);

            while (propertyNames.contains(name)) {
                name =
                        String
                                .format(Messages.UniquenessStrategy_Operation_Default_Prefix,
                                        ++idx);
            }
        }
        return name;
    }

    /**
     * Generate a unique operation name in the given <code>Class</code>.
     * 
     * @param parentClass
     *            <code>Class</code> in which the <code>Operation</code> will be
     *            added.
     * @return unique property name.
     */
    public synchronized String getUniqueOperationName(Class parentClass,
            String prefix) {
        String name = ""; //$NON-NLS-1$

        Set<String> propertyNames = new HashSet<String>();

        if (parentClass != null) {
            EList<Operation> operations = parentClass.getOperations();

            if (operations != null) {
                for (Operation oper : operations) {
                    propertyNames.add(oper.getName());
                }
            }

            int idx = 1;
            name = prefix + idx;

            while (propertyNames.contains(name)) {
                name = prefix + ++idx;
            }
        }
        return name;
    }

    /**
     * Generate a unique Enumeration Literal name in the given
     * <code>Enumeration</code>.
     * 
     * @param parentEnumeration
     *            <code>Enumeration</code> in which the
     *            <code>Enumeration Literal</code> will be added.
     * @return unique Enumeration Literal name.
     */
    public synchronized String genUniqueEnumLitName(Enumeration parentEnum) {
        String name = ""; //$NON-NLS-1$

        Set<String> litNames = new HashSet<String>();

        if (parentEnum != null) {
            EList<EnumerationLiteral> lits = parentEnum.getOwnedLiterals();

            if (lits != null) {
                for (EnumerationLiteral lit : lits) {
                    litNames.add(lit.getName());
                }
            }

            int idx = 1;
            name =
                    String
                            .format(Messages.UniquenessStrategy_EnumLit_Default_Prefix,
                                    idx);

            while (litNames.contains(name)) {
                name =
                        String
                                .format(Messages.UniquenessStrategy_EnumLit_Default_Prefix,
                                        ++idx);
            }
        }
        return name;
    }

    /**
     * Generate a unique Enumeration Literal name in the given
     * <code>Enumeration</code>.
     * 
     * @param parentEnumeration
     *            <code>Enumeration</code> in which the
     *            <code>Enumeration Literal</code> will be added.
     * @param prefix
     *            String to be used as the name of the Enumeration Literal to
     *            which a unique integer will be appended
     * @return unique Enumeration Literal name.
     */
    public synchronized String genUniqueEnumLitName(Enumeration parentEnum,
            String prefix) {
        String name = ""; //$NON-NLS-1$

        Set<String> litNames = new HashSet<String>();

        if (parentEnum != null) {
            EList<EnumerationLiteral> lits = parentEnum.getOwnedLiterals();

            if (lits != null) {
                for (EnumerationLiteral lit : lits) {
                    litNames.add(lit.getName());
                }
            }

            int idx = 1;
            name = prefix + idx;

            while (litNames.contains(name)) {
                name = prefix + ++idx;
            }
        }
        return name;
    }

    /**
     * 
     * Generates and returns an Association name unique within the supplied
     * parent package. Handles all association types i.e. Association,
     * Aggregation Composition.
     * 
     * @param Package
     *            parentPackage
     * @param Association
     *            assoc
     * @return String name
     */
    public synchronized String genUniqueAssociationName(Package parentPackage,
            Association assoc) {
        String name = ""; //$NON-NLS-1$

        // Build a HashSet of current class names
        HashSet<String> assocNames = new HashSet<String>();
        List<?> elems = parentPackage.getPackagedElements();

        for (Object obj : elems) {
            if (obj instanceof Association) {
                Association asn = (Association) obj;
                assocNames.add(asn.getName());
            }
        }

        AggregationKind assocAggregation =
                UML2ModelUtil.getAggregationType(assoc);

        int idx = 1;

        if (assocAggregation == AggregationKind.NONE_LITERAL) {
            name = String.format(DEFAULT_ASSOCIATION_NAME_PREFIX, idx);

            while (assocNames.contains(name)) {
                name = String.format(DEFAULT_ASSOCIATION_NAME_PREFIX, ++idx);
            }
        } else if (assocAggregation == AggregationKind.SHARED_LITERAL) {
            name = String.format(DEFAULT_AGGREGATION_NAME_PREFIX, idx);
            while (assocNames.contains(name)) {
                name = String.format(DEFAULT_AGGREGATION_NAME_PREFIX, ++idx);
            }
        } else if (assocAggregation == AggregationKind.COMPOSITE_LITERAL) {
            name = String.format(DEFAULT_COMPOSITION_NAME_PREFIX, idx);
            while (assocNames.contains(name)) {
                name = String.format(DEFAULT_COMPOSITION_NAME_PREFIX, ++idx);
            }
        }
        return name;

    }

    /**
     * 
     * Generates and returns an Association name unique within the supplied
     * parent package. Handles all association types i.e. Association,
     * Aggregation Composition.
     * 
     * @param Package
     *            parentPackage
     * @param Association
     *            assoc
     * @return String name
     */
    public synchronized String genUniqueAssociationName(Package parentPackage,
            Association assoc, String prefix) {
        String name = ""; //$NON-NLS-1$

        // Build a HashSet of current class names
        HashSet<String> assocNames = new HashSet<String>();
        List<?> elems = parentPackage.getPackagedElements();

        for (Object obj : elems) {
            if (obj instanceof Association) {
                Association asn = (Association) obj;
                assocNames.add(asn.getName());
            }
        }

        AggregationKind assocAggregation =
                UML2ModelUtil.getAggregationType(assoc);

        int idx = 1;

        if (assocAggregation == AggregationKind.NONE_LITERAL) {
            name = prefix + idx;

            while (assocNames.contains(name)) {
                name = prefix + ++idx;
            }
        } else if (assocAggregation == AggregationKind.SHARED_LITERAL) {
            name = prefix + idx;
            while (assocNames.contains(name)) {
                name = prefix + ++idx;
            }
        } else if (assocAggregation == AggregationKind.COMPOSITE_LITERAL) {
            name = prefix + idx;
            while (assocNames.contains(name)) {
                name = prefix + ++idx;
            }
        }
        return name;

    }

    /**
     * @param input
     * @return
     */
    public String getUniqueParameterName(Operation operation) {
        String name = ""; //$NON-NLS-1$

        Set<String> propertyNames = new HashSet<String>();

        if (operation != null) {
            EList<Parameter> parameters = operation.getOwnedParameters();

            if (parameters != null) {
                for (Parameter parameter : parameters) {
                    propertyNames.add(parameter.getName());
                }
            }

            int idx = 1;
            name =
                    String
                            .format(Messages.UniquenessStrategy_Parameter_Default_Prefix,
                                    idx);

            while (propertyNames.contains(name)) {
                name =
                        String
                                .format(Messages.UniquenessStrategy_Parameter_Default_Prefix,
                                        ++idx);
            }
        }
        return name;
    }
}
