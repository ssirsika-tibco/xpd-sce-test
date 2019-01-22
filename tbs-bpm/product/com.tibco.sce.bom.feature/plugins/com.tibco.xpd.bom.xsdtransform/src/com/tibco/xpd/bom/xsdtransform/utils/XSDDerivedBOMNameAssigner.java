package com.tibco.xpd.bom.xsdtransform.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xsd.ecore.NameMangler;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.types.api.BOMEntityNameCleanser;
import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper;
import com.tibco.xpd.bom.xsdtransform.internal.Messages;

/**
 * This class provides methods for determining BOM entity names when creating a
 * BOM from an imported schema (WSDL or XSD).
 * 
 * The intended use is: (1) BOM is created with original XSD entity names
 * preserved in stereotypes. (2) The setNames(Model) method of this class is
 * invoked. This applies transformations to derive BOM entity names from their
 * corresponding XSD names, including numeric suffixing where collisions occur.
 * (3) Subsequent export of BOM to XSD should retrieve the original schema
 * entity names that have been preserved in BOM stereotypes (In other words, the
 * BOM entity names are not involved in the export process) and also include
 * names as ecore:name attributes. (4) CDS generation will name Java entities
 * using the same rules employed by this class using names propagated via
 * ecore:name attributes.
 * 
 * At the time of writing, XSD to BOM transformation will never produce a BOM
 * containing a sub-package (instead, a separate BOM is created). Therefore,
 * this class makes no attempt to process beyond the root package (Model).
 * 
 * @author smorgan
 * 
 */
public class XSDDerivedBOMNameAssigner extends NameMangler {

    private List<TopLevelAttributeWrapper> topLevelAttributeWrappers =
            new ArrayList<TopLevelAttributeWrapper>();

    private List<TopLevelElementWrapper> topLevelElementWrappers =
            new ArrayList<TopLevelElementWrapper>();

    class XSDNameNamedElementComparator implements Comparator<NamedElement> {
        public int compare(NamedElement ne1, NamedElement ne2) {
            int result = 0;
            try {
                if (ne1 instanceof Property && ne2 instanceof Property) {
                    boolean type1IsObject =
                            ((Property) ne1).getType().getQualifiedName()
                                    .equals("BomPrimitiveTypes::Object");
                    boolean type2IsObject =
                            ((Property) ne2).getType().getQualifiedName()
                                    .equals("BomPrimitiveTypes::Object");
                    if (type1IsObject && !type2IsObject) {
                        result = 1;
                    } else if (type2IsObject && !type1IsObject) {
                        result = -1;
                    }

                    // Two properties, neither of which are object datatypes,
                    // so see if one is an attribute (and therefore lower
                    // priority)
                    if (result == 0) {
                        Stereotype ne1Stereotype =
                                getStereotypeFromElementByName(ne1,
                                        XsdStereotypeUtils.XSD_BASED_PROPERTY);
                        Boolean ne1IsAttrib =
                                (Boolean) ne1
                                        .getValue(ne1Stereotype,
                                                XsdStereotypeUtils.XSD_PROPERTY_IS_ATTRIBUTE);

                        Stereotype ne2Stereotype =
                                getStereotypeFromElementByName(ne2,
                                        XsdStereotypeUtils.XSD_BASED_PROPERTY);
                        Boolean ne2IsAttrib =
                                (Boolean) ne2
                                        .getValue(ne2Stereotype,
                                                XsdStereotypeUtils.XSD_PROPERTY_IS_ATTRIBUTE);

                        if ((ne1IsAttrib == true) && (ne2IsAttrib == false)) {
                            result = 1;
                        } else if ((ne1IsAttrib == false)
                                && (ne2IsAttrib == true)) {
                            result = -1;
                        }
                    }
                }

                // With everything else equal, we differentiate
                // on name.
                if (result == 0) {
                    String name1 = getXsdNameFromElement(ne1);
                    String name2 = getXsdNameFromElement(ne2);
                    result = name1.compareTo(name2);
                }
            } catch (NameAssignmentException e) {
                // Log and continue. This seems preferable to failing the whole
                // process.
                Activator
                        .getDefault()
                        .getLogger()
                        .error(e,
                                String.format(Messages.XSDDerivedBOMNameAssigner_nameComparison,
                                        e.getMessage()));
            }
            return result;
        }
    }

    private static Stereotype getStereotypeFromElementByName(Element element,
            String name) {
        List<Stereotype> stereotypes = element.getAppliedStereotypes();
        Stereotype result = null;
        Iterator<Stereotype> iter = stereotypes.iterator();
        while (result == null & iter.hasNext()) {
            Stereotype aStereotype = iter.next();
            if (aStereotype.getName().equals(name)) {
                result = aStereotype;
            }
        }
        return result;
    }

    private static boolean hasElementGotProperty(Element element,
            String stName, String propName) {
        boolean result = false;
        Stereotype st = getStereotypeFromElementByName(element, stName);
        if (st != null) {
            result = element.hasValue(st, propName);
        }
        return result;
    }

    // Gets given property - fails if missing or null
    private static String getPropertyFromElement(Element element,
            String stName, String propName) throws NameAssignmentException {
        return getPropertyFromElement(element, stName, propName, false);
    }

    // Gets given property - fails if missing or null. Also, if
    // allowZeroLengthValue is false, fails if empty string
    private static String getPropertyFromElement(Element element,
            String stName, String propName, boolean allowZeroLengthValue)
            throws NameAssignmentException {
        String result = null;
        Stereotype st = getStereotypeFromElementByName(element, stName);
        if (st != null) {
            if (element.hasValue(st, propName)) {
                result = String.valueOf(element.getValue(st, propName));
            }
            if (result == null
                    || (!allowZeroLengthValue && result.length() == 0)) {
                throw new NameAssignmentException(
                        String.format(Messages.XSDDerivedBOMNameAssigner_propertyIsNullOrEmpty_error_message,
                                propName,
                                stName,
                                element));
            }
        } else {
            throw new NameAssignmentException(
                    String.format(Messages.XSDDerivedBOMNameAssigner_missingStereotype_error_message,
                            element,
                            stName));
        }
        return result;
    }

    // Gets the name of the element's corresponding XSD entity.
    // If the element maps to an anonymous entity in XSD, then
    // this gets the name of the containing XSD entity.
    private String getXsdNameFromElement(NamedElement element)
            throws NameAssignmentException {
        String result = null;

        // There is a case where anonymous types are explicitly flagged
        // in the BOM, that will later be make top level named
        // For these we need to work out a suitable name based on
        // their origin
        if ((element instanceof Classifier)
                && (TransformHelper
                        .isClassifierOriginAnonymous((Classifier) element))) {
            // The only current case for this is anonymous simple types
            // in a union, in which case this will be a generalisation of
            // a BOM primitive type. At some point in the past, it was
            // a generalisation of the 'virtual' type representing the union.
            // This code was originally intended to derive the name from there,
            // resulting in a name that was associated with the union,
            // but this apparently changed somewhere during 1.2 development.
            EList<Classifier> generals = ((Classifier) element).getGenerals();

            // There should only be one, so use that
            if (generals.size() > 0) {
                return generals.get(0).getName();
            }
        }

        if (element instanceof Property) {
            // Check for a 'xsdRef' property. This will be
            // present if the property was derived from an
            // element reference. At the time of writing,
            // xsdName has a trailing underscore appended in
            // this case, so it's important we get the name
            // from here instead.
            if (hasElementGotProperty(element,
                    XsdStereotypeUtils.XSD_BASED_PROPERTY,
                    XsdStereotypeUtils.XSD_PROPERTY_REF)) {
                String xsdRef =
                        getPropertyFromElement(element,
                                XsdStereotypeUtils.XSD_BASED_PROPERTY,
                                XsdStereotypeUtils.XSD_PROPERTY_REF);
                result = TransformHelper.getLocalPart(xsdRef);
            } else {
                result =
                        getPropertyFromElement(element,
                                XsdStereotypeUtils.XSD_BASED_PROPERTY,
                                XsdStereotypeUtils.XSD_PROPERTY_NAME);
            }
        } else if (element instanceof Enumeration
                || element instanceof PrimitiveType) {
            if (TransformHelper
                    .isAnonymousTopLevelAttribute((Classifier) element,
                            topLevelAttributeWrappers)
                    && TransformHelper
                            .getAttributeWrappers((Classifier) element).get(0)
                            .getIsBaseXSDType()) {
                result =
                        TransformHelper
                                .getAttributeWrappers((Classifier) element)
                                .get(0).getName();
            } else if (TransformHelper
                    .isAnonymousTopLevelElement((Classifier) element,
                            topLevelElementWrappers)
                    && TransformHelper.getElementWrappers((Classifier) element)
                            .get(0).getIsBaseXSDType()) {
                result =
                        TransformHelper
                                .getElementWrappers((Classifier) element)
                                .get(0).getName();
            } else {
                result =
                        getPropertyFromElement(element,
                                XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE,
                                XsdStereotypeUtils.XSD_PROPERTY_SIMPLETYPE_NAME);
            }
        } else if (element instanceof EnumerationLiteral) {
            // Note that a literal can legally have an
            // empty string as its name in the XSD.
            result =
                    getPropertyFromElement(element,
                            XsdStereotypeUtils.XSD_BASED_ENUMERATION_LITERAL,
                            XsdStereotypeUtils.XSD_PROPERTY_VALUE,
                            true);
        } else if (element instanceof org.eclipse.uml2.uml.Class) {
            if (TransformHelper
                    .isAnonymousTopLevelElement((Classifier) element,
                            topLevelElementWrappers)) {
                result =
                        TransformHelper
                                .getElementWrappers((Classifier) element)
                                .get(0).getName();
            } else {
                result =
                        getPropertyFromElement(element,
                                XsdStereotypeUtils.XSD_BASED_CLASS,
                                XsdStereotypeUtils.XSD_PROPERTY_NAME);
            }
        } else {
            // Unsupported element type
            throw new NameAssignmentException(
                    String.format(Messages.XSDDerivedBOMNameAssigner_unsupportedElement_error_message,
                            element));
        }

        return result;
    }

    // Suffixes supplied name to ensure it doesn't clash with anything
    // in the usedNames list. Adds the resulting name (lower-cased) to
    // the list.
    private String makeNameUnique(NamedElement element, String name,
            Map<NamedElement, String> usedNames,
            Map<NamedElement, String> getterNouns, boolean ignoreUnderscores) {
        String result = null;
        String lookupName = name.toLowerCase();
        // Ecore validation considers it illegal to have two two like-typed
        // entities (e.g. classifiers or features) with names that differ only
        // in underscore content (e.g. classes 'AA' and 'A_A' or features
        // 'BB' and 'B_B'). Therefore, we consider this here and ensure that
        // names are unique according to an 'underscore-insensitive' comparison.
        // The generated Ecore package class contains constants relating to each
        // entity in the package, including classes and features. These are
        // named CLASSNAME__PROPNAME; Note the double underscore delimiter and
        // upper-casing of class and property names. The class and feature names
        // are also stripped of underscores. This can't possibly create a clash,
        // as Ecore validation has already ensured that names are unique when
        // compared as underscore-insensitive, as explained above.
        // The generated package class also contains a 'getter' method to obtain
        // the definition of a class or feature. For classes, these are of the
        // form getClassname(). For features, getClassname_Featurename(). Note
        // the single underscore delimiter and preservation of case. As BDS
        // allows underscores in classnames and feature names, which EMF
        // normally wouldn't, this creates the potential for a method name
        // clash. For example, the feature 'B' of class 'A' would prompt a
        // getter 'getA_B'; A class named 'A_B' would prompt the same. To avoid
        // this, we keep track of the getter names are we assign names;
        // Actually, we know they begin 'get', so just note the remainder: the
        // 'getter noun', which is 'A_B' in these examples. If a name would
        // result in a getter noun that clashes with something already allocated
        // (i.e. something of a higher precedence) then it is changed.
        if (ignoreUnderscores) {
            lookupName = lookupName.replace("_", ""); //$NON-NLS-1$ //$NON-NLS-2$
        }

        // Determine the getter noun. Note that we lower-case it
        // in order to ignore case (i.e. we can't have 'getA_a()' and
        // 'getA_A()')
        String getterNoun = null;
        if (element instanceof Property) {
            // We can assume the property's parent class name has
            // already been determined at this point.
            getterNoun =
                    (((Property) element).getClass_().getName() + "_" + name)
                            .toLowerCase();
        } else if (element instanceof Classifier) {
            getterNoun = name.toLowerCase();
        }

        boolean usedNameClash = usedNames.containsValue(lookupName);
        boolean getterNounClash =
                getterNoun != null && getterNouns.containsValue(getterNoun);
        if (!usedNameClash && !getterNounClash) {
            // Unique, so use name as-is
            usedNames.put(element, lookupName);
            if (getterNoun != null) {
                getterNouns.put(element, getterNoun);
            }
            result = name;
        } else {
            // Clash, so find a suffix that makes it unique
            // Starting with 1, attempt to find a number that can be added to
            // the end, such
            // that the name and getterNoun do not conflict with any already
            // assigned.
            int suffix = 0;
            do {
                suffix++;
                usedNameClash = usedNames.containsValue(lookupName + suffix);
                getterNounClash =
                        getterNoun != null
                                && getterNouns.containsValue(getterNoun
                                        + suffix);
            } while (usedNameClash || getterNounClash);
            usedNames.put(element, lookupName + suffix);
            if (getterNoun != null) {
                getterNouns.put(element, getterNoun + suffix);
            }
            result = name + suffix;
        }
        return result;
    }

    /**
     * Checks whether the given Class corresponds to a type restricting another
     * in XSD.
     * 
     * @param clazz
     * @return
     */
    private boolean isClassAnXSDRestriction(org.eclipse.uml2.uml.Class clazz) {
        boolean result = false;
        Stereotype st =
                XsdStereotypeUtils.getAppliedXsdStereotype(clazz.getModel(),
                        clazz,
                        XsdStereotypeUtils.XSD_BASED_CLASS);
        if (st != null) {
            Object stValue =
                    clazz.getValue(st,
                            XsdStereotypeUtils.XSD_PROPERTY_IS_RESTRICTION);
            if (stValue instanceof Boolean) {
                result = ((Boolean) stValue).booleanValue();
            }
        }
        return result;
    }

    /**
     * Gets the properties that the given Class inherits from its supertype.
     * 
     * @param clazz
     * @return
     */
    private List<Property> getInheritedProperties(
            org.eclipse.uml2.uml.Class clazz) {
        List<Property> result = new ArrayList<Property>();
        for (Property aProp : clazz.getAllAttributes()) {
            // Only add to list if inherited
            if (aProp.getClass_() != clazz) {
                result.add(aProp);
            }
        }
        return result;
    }

    private void processProperties(org.eclipse.uml2.uml.Class clazz,
            Map<NamedElement, String> usedClassifierNames,
            Map<NamedElement, String> usedGetterNouns,
            List<org.eclipse.uml2.uml.Class> classesWithNamedProperties,
            boolean processAnonymousTypes) throws NameAssignmentException {

        // We can't work out this class's property names until
        // we're certain that any supertype(s) have property names
        // set. We need to consider supertype property names
        // to ensure we don't clash with them.
        // e.g. Parent.ATTR clashes with Child.attR, so one
        // needs renaming (and we'll give the parent priority
        // as it 'exists first').
        if (!clazz.getSuperClasses().isEmpty()) {
            org.eclipse.uml2.uml.Class superClass =
                    clazz.getSuperClasses().get(0);
            processProperties(superClass,
                    usedClassifierNames,
                    usedGetterNouns,
                    classesWithNamedProperties,
                    false);
        }

        // Get this class's properties (NOT including interited)
        List<Property> props = new ArrayList<Property>();
        props.addAll(clazz.getOwnedAttributes());
        // Sort by XSD name, ascending
        Collections.sort(props, new XSDNameNamedElementComparator());

        // Name this class's properties, unless we done it before
        if (!classesWithNamedProperties.contains(clazz)) {
            classesWithNamedProperties.add(clazz);

            // If this class is an XSD restriction of its supertype, then it
            // must _inherit_ the names from its supertype (i.e. they _must_
            // match), rather than renaming them to avoid clashes.
            List<Property> inheritedProperties = getInheritedProperties(clazz);
            if (isClassAnXSDRestriction(clazz)) {
                for (Property aProp : props) {
                    // Get the inherited property to which this
                    // corresponds and copy its name.
                    String xsdName = getXsdNameFromElement(aProp);
                    for (Property inheritedProp : inheritedProperties) {
                        String inheritedXSDName =
                                getXsdNameFromElement(inheritedProp);
                        if (inheritedXSDName.equals(xsdName)) {
                            // It's the prop that this one restricts, so
                            // ensure we use the same name
                            String inheritedName = inheritedProp.getName();
                            setName(aProp, inheritedName);
                            break;
                        }
                    }
                }
            } else {

                Map<NamedElement, String> usedPropertyNames =
                        new HashMap<NamedElement, String>();

                // Before we begin, populate used property name list with names
                // of properties from supertype(s). We can assume
                // they have already been assigned their final names.
                // This will ensure that any names we assign now won't clash
                // with inherited properties.
                for (Property aProp : inheritedProperties) {
                    usedPropertyNames.put(aProp, aProp.getName().toLowerCase());
                }

                for (Property prop : props) {
                    String xsdName = getXsdNameFromElement(prop);
                    String cleansedName =
                            BOMEntityNameCleanser.getInstance()
                                    .cleanseAttributeName(xsdName, true);
                    String uniqueName =
                            makeNameUnique(prop,
                                    cleansedName,
                                    usedPropertyNames,
                                    usedGetterNouns,
                                    true);

                    // Apply the name to the element.
                    setName(prop, uniqueName);
                }
            }
        }

        // If we've been asked to name this clazz's contained anon types,
        // do that now (and we rely on the caller to ensure we aren't asked
        // twice)

        if (processAnonymousTypes) {
            for (Property prop : props) {
                // If this property's type is an anonymous type class,
                // process it. Exclude the cases where they were anonymous
                // types, but when the schema was imported they were actually
                // converted into named types.
                Type propType = prop.getType();
                if (isAnonType(propType, topLevelElementWrappers)
                        && !TransformHelper
                                .isClassifierOriginAnonymous((Classifier) propType)
                        && (propType instanceof org.eclipse.uml2.uml.Class || propType instanceof Enumeration)) {
                    // The property's type is an anonymous type, so we
                    // *might* need to give it a name derived from the
                    // name of the property itself (e.g. property 'X' giving
                    // 'XType'). There are cases where we don't, such as where
                    // the type resides in a top level element (used here
                    // via a 'ref'). In such cases, the type will already
                    // have been named and will therefore already be
                    // in the used names map.
                    if (!usedClassifierNames.containsKey(propType)) {
                        // If attribute name has been altered to work around
                        // BX1439, then we do NOT want to pass that alteration
                        // down to nested types for naming purposes. Instead, we
                        // revert the affected characters back to their original
                        // state, whilst maintaining the rest of the name
                        // (including any numeric suffix that makes it unique)
                        String xsdName = getXsdNameFromElement(prop);
                        String cleansedName =
                                BOMEntityNameCleanser.getInstance()
                                        .cleanseAttributeName(xsdName, true);
                        String assignedName = prop.getName();
                        String nonBX1439Name =
                                BOMEntityNameCleanser.getInstance()
                                        .cleanseAttributeName(xsdName,
                                                true,
                                                true);
                        String parentName;
                        if (!cleansedName.equals(nonBX1439Name)) {
                            parentName =
                                    nonBX1439Name.substring(0, 2)
                                            + (assignedName.length() > 2 ? assignedName
                                                    .substring(2) : "");
                        } else {
                            parentName = assignedName;
                        }
                        processAnonymousType((Classifier) propType,
                                parentName,
                                usedClassifierNames,
                                usedGetterNouns,
                                classesWithNamedProperties);
                    }
                }
            }
        }
    }

    private void processAnonymousType(Classifier classifier, String parentName,
            Map<NamedElement, String> usedClassifierNames,
            Map<NamedElement, String> usedGetterNouns,
            List<org.eclipse.uml2.uml.Class> classesWithNamedProperties)
            throws NameAssignmentException {
        if (!usedClassifierNames.containsKey(classifier)) {
            String baseName = parentName + "Type"; //$NON-NLS-1$
            String transformedName = transformClassName(baseName);
            String uniqueName =
                    makeNameUnique(classifier,
                            transformedName,
                            usedClassifierNames,
                            usedGetterNouns,
                            true);
            setName(classifier, uniqueName);
            if (classifier instanceof org.eclipse.uml2.uml.Class) {
                processProperties((org.eclipse.uml2.uml.Class) classifier,
                        usedClassifierNames,
                        usedGetterNouns,
                        classesWithNamedProperties,
                        true);
            } else if (classifier instanceof Enumeration) {
                processEnumeration((Enumeration) classifier, usedGetterNouns);
            }
        }
    }

    private void processEnumeration(Enumeration enu,
            Map<NamedElement, String> usedGetterNouns)
            throws NameAssignmentException {

        Map<NamedElement, String> usedNames =
                new HashMap<NamedElement, String>();
        List<EnumerationLiteral> lits = new ArrayList<EnumerationLiteral>();
        lits.addAll(enu.getOwnedLiterals());
        // Sort ascending by name. This same sorting will be used by CDS
        // at generation time to ensure consistent conflict-resolution.
        Collections.sort(lits, new XSDNameNamedElementComparator());

        for (EnumerationLiteral lit : lits) {
            String name =
                    transformEnumerationLiteralName(getXsdNameFromElement(lit));
            String uniqueName =
                    makeNameUnique(lit, name, usedNames, usedGetterNouns, true);
            setName(lit, uniqueName);
        }
    }

    private static boolean isAnonType(Element element,
            List<TopLevelElementWrapper> topLevelElementWrappers)
            throws NameAssignmentException {
        boolean result = false;

        if ((element instanceof Classifier)
                && (TransformHelper
                        .isAnonymousTopLevelElement((Classifier) element,
                                topLevelElementWrappers))) {
            // It's a type resulting from the anonymous content of a TLE
            result = true;
        } else if ((element instanceof Classifier)
                && (TransformHelper
                        .isClassifierOriginAnonymous((Classifier) element))) {
            // There is a case where anonymous types are explicitly flagged
            // in the BOM, that will later be made top level named;
            // Catch this case of them originally being anonymous
            result = true;
        } else if (element instanceof Enumeration) {
            // Anonymous enumerations have the XsdBasedEnumeration stereotype,
            // whereas named enumerations have the XsdBasedPrimitiveType
            // stereotype, so we need to check that this enumeration has the
            // XsdBasedEnumeration type before attempting to read a property
            // from it.
            if (hasElementGotProperty(element,
                    XsdStereotypeUtils.XSD_BASED_ENUMERATION,
                    XsdStereotypeUtils.XSD_PROPERTY_IS_ANON_TYPE)) {
                result =
                        Boolean.parseBoolean(getPropertyFromElement(element,
                                XsdStereotypeUtils.XSD_BASED_ENUMERATION,
                                XsdStereotypeUtils.XSD_PROPERTY_IS_ANON_TYPE));
            }
        } else if (element instanceof org.eclipse.uml2.uml.Class) {
            if (hasElementGotProperty(element,
                    XsdStereotypeUtils.XSD_BASED_CLASS,
                    XsdStereotypeUtils.XSD_PROPERTY_IS_ANON_TYPE)) {
                result =
                        Boolean.parseBoolean(getPropertyFromElement(element,
                                XsdStereotypeUtils.XSD_BASED_CLASS,
                                XsdStereotypeUtils.XSD_PROPERTY_IS_ANON_TYPE));
            }
        }
        return result;
    }

    /**
     * Sets the names of the supplied Model and its entities to accurately match
     * what they will in the CDS-generated run-time.
     * 
     * @param model
     * @throws NameAssignmentException
     */
    public void setNames(Model model) throws NameAssignmentException {
        // A map to keep track of entities and their names as we assign
        // them, enabling uniqueness
        Map<NamedElement, String> usedClassifierNames =
                new HashMap<NamedElement, String>();
        Map<NamedElement, String> usedGetterNouns =
                new HashMap<NamedElement, String>();
        List<org.eclipse.uml2.uml.Class> classesWithNamedProperties =
                new ArrayList<org.eclipse.uml2.uml.Class>();

        // We need to prevent classifier names clashing with the first
        // segment of the containing package name (causes uncompilable
        // EMF Java code)
        String modelName = model.getName();
        if (modelName != null) {
            usedClassifierNames.put(model,
                    modelName.split("\\.")[0].toLowerCase());
        }

        // Priority

        // 1a) top-level types
        // 1b) top-level elements (the anonymous types within)
        // 2a) contents of top-level types
        // 2b) contents of top-level elements
        // 3) top-level attribute
        // 4) Top level Types that were marked as anonymous
        // These are created when there is an anonymous type under a union

        List<Classifier> topLevelTypes = new ArrayList<Classifier>();
        List<Classifier> topLevelElements = new ArrayList<Classifier>();
        List<Classifier> topLevelAttributes = new ArrayList<Classifier>();
        List<Classifier> topLevelAnonymousOrigin = new ArrayList<Classifier>();

        topLevelAttributeWrappers =
                TransformHelper.getTopLevelAttributeWrappers(model);
        topLevelElementWrappers =
                TransformHelper.getTopLevelElementWrappers(model);

        // Populate the lists with the appropriate elements.
        for (PackageableElement pe : model.getPackagedElements()) {
            if (pe instanceof org.eclipse.uml2.uml.Class
                    || pe instanceof Enumeration || pe instanceof PrimitiveType) {
                Classifier cfr = (Classifier) pe;
                // There are some cases where a type is anonymous in the
                // originally imported schema, but when generation is
                // made from the BOM it will be put as a top level type
                // this occurs in cases such as anonymous types under a
                // union. When these types are created we need to give them
                // the lowest priority in the naming order. When these type
                // are then output from the BOM they will be given an
                // ecore:name so that they match up in EMF
                if (TransformHelper.isClassifierOriginAnonymous(cfr)) {
                    topLevelAnonymousOrigin.add(cfr);
                } else if (TransformHelper.isAnonymousTopLevelAttribute(cfr,
                        topLevelAttributeWrappers)) {
                    topLevelAttributes.add(cfr);
                } else if (TransformHelper.isTopLevelElement(cfr,
                        topLevelElementWrappers)) {
                    if (TransformHelper.isAnonymousTopLevelElement(cfr,
                            topLevelElementWrappers)) {
                        // Anonymous top level element
                        topLevelElements.add(cfr);
                    } else {
                        // If it's an element of an XSD (simple) type, then
                        // we can assume it will mutate into an anonymous
                        // type during BOM->XSD
                        if (TransformHelper.isTopLevelElementXSDBaseType(cfr,
                                topLevelElementWrappers)) {
                            topLevelElements.add(cfr);

                        } else {
                            // Named type (that happens to be referenced by
                            // top level element)
                            topLevelTypes.add(cfr);
                        }
                    }
                } else if (!isAnonType(cfr, topLevelElementWrappers)) {
                    topLevelTypes.add(cfr);
                }
            }
        }

        // Sort each list by XSD name (i.e. the original name in
        // the schema - NOT the existing BOM element name)
        Collections.sort(topLevelTypes, new XSDNameNamedElementComparator());
        Collections.sort(topLevelElements, new XSDNameNamedElementComparator());
        Collections.sort(topLevelAttributes,
                new XSDNameNamedElementComparator());

        // Resolve names in the correct priority order
        assignNames(topLevelTypes, usedClassifierNames, usedGetterNouns, null);
        // Note that we always append 'Type' to TLE/TLA types. EMF would
        // normally only do this for anonymous types. However,
        // XSD->BOM->XSD transformation mutates all TLE/TLAs into anonymous
        // types, so we need to reflect that here. Remember that
        // those backed by global/named types aren't affected
        // as those types are named in their own right.
        assignNames(topLevelElements,
                usedClassifierNames,
                usedGetterNouns,
                "Type");
        assignNamesToContents(topLevelTypes,
                usedClassifierNames,
                usedGetterNouns,
                classesWithNamedProperties);
        assignNamesToContents(topLevelElements,
                usedClassifierNames,
                usedGetterNouns,
                classesWithNamedProperties);
        assignNames(topLevelAttributes,
                usedClassifierNames,
                usedGetterNouns,
                "Type");
        assignNames(topLevelAnonymousOrigin,
                usedClassifierNames,
                usedGetterNouns,
                "Member");
        assignNamesToContents(topLevelAttributes,
                usedClassifierNames,
                usedGetterNouns,
                classesWithNamedProperties);
        assignNamesToContents(topLevelAnonymousOrigin,
                usedClassifierNames,
                usedGetterNouns,
                classesWithNamedProperties);
    }

    private void assignNames(List<Classifier> classifiers,
            Map<NamedElement, String> usedClassifierNames,
            Map<NamedElement, String> usedGetterNouns, String classNameSuffix)
            throws NameAssignmentException {
        for (Classifier classifier : classifiers) {
            String xsdName = getXsdNameFromElement(classifier);
            if (classNameSuffix != null) {
                xsdName += classNameSuffix;
            }
            String transformedName =
                    classifier instanceof PrimitiveType ? transformPrimitiveTypeName(xsdName)
                            : transformClassName(xsdName);
            String uniqueName =
                    makeNameUnique(classifier,
                            transformedName,
                            usedClassifierNames,
                            usedGetterNouns,
                            true);
            setName(classifier, uniqueName);
        }
    }

    private void assignNamesToContents(List<Classifier> classifiers,
            Map<NamedElement, String> usedClassifierNames,
            Map<NamedElement, String> usedGetterNouns,
            List<org.eclipse.uml2.uml.Class> classesWithNamedProperties)
            throws NameAssignmentException {
        for (Classifier classifier : classifiers) {
            if (classifier instanceof org.eclipse.uml2.uml.Class) {
                org.eclipse.uml2.uml.Class clazz =
                        (org.eclipse.uml2.uml.Class) classifier;
                processProperties(clazz,
                        usedClassifierNames,
                        usedGetterNouns,
                        classesWithNamedProperties,
                        true);
            } else if (classifier instanceof Enumeration) {
                processEnumeration((Enumeration) classifier, usedGetterNouns);
            }
        }
    }

    public String transformAttributeName(String name) {
        // Previously, we would transform the name here as a 'prediction'
        // of what EMF would do during XSD to Ecore transformation. Now,
        // we've taken control of the latter and only alter names where they
        // are illegal further down the chain.
        String newName =
                BOMEntityNameCleanser.getInstance().cleanseAttributeName(name,
                        true);
        return newName;
    }

    public String transformClassName(String name) {
        String newName =
                BOMEntityNameCleanser.getInstance()
                        .cleanseClassName(name, true);
        return newName;
    }

    public String transformPrimitiveTypeName(String name) {
        String newName =
                BOMEntityNameCleanser.getInstance()
                        .cleansePrimitiveTypeName(name, true);
        return newName;
    }

    public String transformEnumerationLiteralName(String name) {
        String validName = validName(name, UNCHANGED_CASE, "_"); //$NON-NLS-1$
        String formatted =
                CodeGenUtil.format(validName, '_', null, false, true);
        String uCase = formatted.toUpperCase();
        return uCase;
    }

    private void setName(NamedElement ne, String name)
            throws NameAssignmentException {

        // Firstly, set the name
        ne.setName(name);

        // Now, set the label (a.k.a. 'display name')
        String label = null;

        // If it makes sense to get this from the original XSD (via
        // a value preserved in a stereotype), then we do that. If
        // it doesn't, then we simply copy the existing name to the label.
        if (!isAnonType(ne, topLevelElementWrappers)) {
            // It doesn't correspond to an XSD anonymous type. As long as it's
            // not a Property representing an XSD 'any' or
            // 'anyAttribute'...
            if (!(ne instanceof Property && (TransformHelper
                    .isObjectAnyType(ne, PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANY) || TransformHelper
                    .isObjectAnyType(ne,
                            PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYATTRIBUTE)))) {
                // Copy the XSD name to the display name (WRM-1636)
                String xsdName = getXsdNameFromElement(ne);
                if (xsdName != null) {
                    label = xsdName;
                }
            }
        }

        if (label == null) {
            // Not applicable to take a name from the XSD, so just copy the
            // name. This is not strictly necessary, as established convention
            // is to fall back to using the name where no label is present, but
            // this removes any possibility of confusion.
            label = name;
        }

        PrimitivesUtil.setDisplayLabel(ne, label, false);
    }
}
