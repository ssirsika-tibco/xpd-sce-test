package com.tibco.bds.designtime.generator.emfmod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.xsd.XSDComponent;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDTerm;
import org.eclipse.xsd.ecore.XSDEcoreBuilder;

/**
 * A slightly modified XSDEcoreBuilder to: (1) Give naming priority to things
 * that had ecore:name attributes - (2) Prevent unsettability of non-nillable
 * things - (3) Ensure use of object-wrapping for primitives. Historically, this
 * class was much more complex, as it completely took over naming. This is no
 * longer necessary since BOM names are propagated via ecore:name.
 * 
 * IMPORTANT : If CDS is migrated to a different version of EMF, then the code
 * in this class should be verified and updated to reflect any changes
 * accordingly. At present, this class is intended to extend XSDEcoreBuilder in
 * EMF 2.4.2.
 * 
 * @author smorgan
 * 
 */
public class CDSXSDEcoreBuilder extends XSDEcoreBuilder {

    private static final String SGT_SUFFIX = "_StudioGeneratedTemplate";

    private static final String OBJECT = "Object";

    private static final String TYPE = "Type";

    private static final String ANONYMOUS_TYPE_SUFFIX = "_._type";

    private List<String> fixedFeatureNames = Arrays.asList(new String[] {
            "xMLNSPrefixMap", "xSISchemaLocation" });

    private List<String> processedNamespaces = new ArrayList<String>();

    private Map<XSDComponent, EModelElement> unnamedElements;

    /**
     * A comparator that places items that had an ecore:name earlier in the
     * ordering (i.e. Those that had ecore:name set in the source XSD). Secondly
     * sorts on XML name.
     * 
     * @author smorgan
     * 
     */
    class ClassifierComparator implements java.util.Comparator<EClassifier> {

        @Override
        public int compare(EClassifier o1, EClassifier o2) {

            boolean hadEcoreName1 = hadEcoreName(o1);
            boolean hadEcoreName2 = hadEcoreName(o2);

            if (hadEcoreName1 && !hadEcoreName2) {
                return -1;
            } else if (hadEcoreName2 && !hadEcoreName1) {
                return 1;
            } else {
                // Both either had or didn't have ecore:names, so
                // we need to further differentiate by ordering by
                // XML name. In the case of both having ecore:names,
                // we know we won't be renaming them, but still need
                // a fixed order.
                String o1XmlName = extendedMetaData.getName(o1);
                if (o1XmlName.endsWith(ANONYMOUS_TYPE_SUFFIX)) {
                    o1XmlName =
                            o1XmlName.substring(0, o1XmlName.length()
                                    - ANONYMOUS_TYPE_SUFFIX.length());
                }
                String o2XmlName = extendedMetaData.getName(o2);
                if (o2XmlName.endsWith(ANONYMOUS_TYPE_SUFFIX)) {
                    o2XmlName =
                            o2XmlName.substring(0, o2XmlName.length()
                                    - ANONYMOUS_TYPE_SUFFIX.length());
                }
                return o1XmlName.compareTo(o2XmlName);
            }
        }
    }

    /**
     * Sorts features based on having ecore name (named features come earlier),
     * then by being any (come later), then by being a special feature (come
     * later) and, finally, by XML name
     * 
     * @author smorgan
     * 
     */
    class StructuralFeatureComparator implements
            java.util.Comparator<EStructuralFeature> {

        // Detects if the given feature is one of the 'special'
        // built in ones.
        protected boolean isFeatureSpecial(EStructuralFeature sf) {
            EClass clazz = sf.getEContainingClass();
            EStructuralFeature pmFeature =
                    extendedMetaData.getXMLNSPrefixMapFeature(clazz);
            EStructuralFeature slFeature =
                    extendedMetaData.getXSISchemaLocationMapFeature(clazz);
            EStructuralFeature mFeature =
                    extendedMetaData.getMixedFeature(clazz);
            EStructuralFeature sFeature =
                    extendedMetaData.getSimpleFeature(clazz);
            boolean isSpecial =
                    (sf == pmFeature || sf == slFeature || sf == mFeature || sf == sFeature);
            return isSpecial;
        }

        public int compare(EStructuralFeature sf1, EStructuralFeature sf2) {
            int result;

            boolean hadEcoreName1 = hadEcoreName(sf1);
            boolean hadEcoreName2 = hadEcoreName(sf2);
            if (hadEcoreName1 && !hadEcoreName2) {
                result = -1;
            } else if (hadEcoreName2 && !hadEcoreName1) {
                result = 1;
            } else {
                EDataType fmDataType =
                        EcorePackage.eINSTANCE.getEFeatureMapEntry();
                EClassifier type1 = sf1.getEType();
                EClassifier type2 = sf2.getEType();
                if (type1 == fmDataType && type2 != fmDataType) {
                    // The first is a feature map (any), but the second isn't
                    result = 1;
                } else if (type1 != fmDataType && type2 == fmDataType) {
                    // The second is a feature map (any), but the first isn't
                    result = -1;
                } else {
                    // 'Special' features have lower naming priority than
                    // than user features.
                    boolean sf1IsSpecial = isFeatureSpecial(sf1);
                    boolean sf2IsSpecial = isFeatureSpecial(sf2);
                    if (sf1IsSpecial && !sf2IsSpecial) {
                        result = 1;
                    } else if (sf2IsSpecial && !sf1IsSpecial) {
                        result = -1;
                    } else {

                        // Both/neither have ecore:names, are feature maps
                        // (anys),
                        // or special, so differentiate on name instead.
                        String o1XmlName = extendedMetaData.getName(sf1);
                        String o2XmlName = extendedMetaData.getName(sf2);
                        result = o1XmlName.compareTo(o2XmlName);
                    }
                }
            }
            return result;
        }
    }

    @Override
    protected EStructuralFeature createFeature(EClass class1, String name,
            EClassifier type, XSDComponent xsdComponent, int minOccurs,
            int maxOccurs) {

        EStructuralFeature feature =
                super.createFeature(class1,
                        name,
                        type,
                        xsdComponent,
                        minOccurs,
                        maxOccurs);

        // Certain features should not be touched...
        if (!fixedFeatureNames.contains(name)) {

            if (xsdComponent instanceof XSDParticle) {
                XSDTerm xsdTerm = ((XSDParticle) xsdComponent).getTerm();
                if (xsdTerm instanceof XSDElementDeclaration) {
                    XSDElementDeclaration xsdElementDeclaration =
                            (XSDElementDeclaration) xsdTerm;
                    if (feature.isUnsettable()
                            && !xsdElementDeclaration.isNillable()) {
                        // If XSD element was non-nillable, then
                        // we don't want it to be unsettable. At
                        // code-generation time, we'll rely on
                        // this. See:
                        // http://www.eclipse.org/forums/index.php?t=msg&goto=649770&S=39fcd4ed10590f49db02916111e30a65#msg_649770
                        feature.setUnsettable(false);
                    }
                }
            }

            // If attribute naturally maps to a type that's backed by a Java
            // primitive, switch it to use the wrapper class (e.g. Integer
            // instead of int)

            if (feature instanceof EAttribute) {
                EAttribute attr = (EAttribute) feature;
                EDataType dt = attr.getEAttributeType();
                if (!canSupportNull(dt)) {
                    if (typeToTypeObjectMap.containsKey(dt)) {
                        EClassifier objectDT = typeToTypeObjectMap.get(dt);
                        attr.setEType(objectDT);
                        attr.setUnsettable(false);
                    }
                }
            }

            EClass documentRoot =
                    extendedMetaData.getDocumentRoot(class1.getEPackage());
            if (documentRoot != class1) {
                // Default behaviour may set the name to have its first
                // character lowercased (e.g. 'HELLO' becomes 'hELLO'). Rather
                // than replacing the whole method to change this behaviour, we
                // simply set it retrospectively to the intended name.
                // We don't interfere with document root features as they
                // neither relate to physical Java entity names or need
                // to be referenced from scripts.
                // Note: We don't care at this point whether the name
                // originates from an ecore:name; If it does, the 'name'
                // variable will have taken it into account already.
                String featName = feature.getName();
                if ((!featName.equals(name))
                        && featName.equals(name.substring(0, 1).toLowerCase()
                                + name.substring(1))) {
                    feature.setName(name);
                }
            }
        }
        return feature;
    }

    @Override
    protected void resolveNameConflict(
            Map<String, ? extends ENamedElement> map,
            ENamedElement eNamedElement, String suffix) {
        String name = eNamedElement.getName();
        if (!name.endsWith(suffix)) {
            name += suffix;
        }

        // Modified from overridden method to ignore underscores
        if (map.containsKey(name.replaceAll("_", "").toLowerCase())) {
            int index = 0;
            while (map.containsKey(name.replaceAll("_", "").toLowerCase()
                    + ++index)) {
                // Loop
            }
            name = name + index;
        }

        // If a name change is required, change it
        setNamedElementNameIfChanged(eNamedElement, name);
    }

    /**
     * Finds the feature that references the given anonymous type classifier.
     *  
     * @param classifier
     * @param containerObj
     * @return
     */
    protected EStructuralFeature getAnonymousStructuralFeature(
            EClassifier classifier, ENamedElement containerObj) {
        // If no containing object is given - use the EPackage root
        if (containerObj == null) {
            containerObj = classifier.getEPackage();
        }

        // Get the places where there is a type using this name
        Collection<Setting> typeUses =
                EcoreUtil.UsageCrossReferencer.find(classifier, containerObj);

        // For some reason each entry is returned twice, so store it into the
        // HashSet so that it will only store unique entries and also filter out
        // the entries that actually define the type as we only want the usage
        // of the type.
        HashSet<EStructuralFeature> uniqueSet =
                new HashSet<EStructuralFeature>();
        for (Setting setting : typeUses) {
            EObject object = setting.getEObject();

            if (object instanceof EStructuralFeature) {
                EClassifier objectEType =
                        ((EStructuralFeature) object).getEType();

                // Just double check that this is actually the
                // correct type it should be, but this extra
                // check can't hurt
                if ((objectEType != null)
                        && (objectEType.getClassifierID() == classifier
                                .getClassifierID())) {
                    uniqueSet.add((EStructuralFeature) object);
                }
            }
        }

        // If we have some how ended up with more than one entry
        // when we searched for occurrences of this it means that the
        // chances are one is the "ref" in the xsd schema to the top level
        // element which in turn is an anonymous type
        if (uniqueSet.size() > 1) {
            HashSet<EStructuralFeature> docRootUniqueSet =
                    new HashSet<EStructuralFeature>();
            for (EStructuralFeature usingFeature : uniqueSet) {
                // Check to see if this one's parent is a document
                // root
                EObject container = usingFeature.eContainer();
                if (container instanceof EClass) {
                    if (ExtendedMetaData.INSTANCE
                            .isDocumentRoot((EClass) container)) {
                        docRootUniqueSet.add(usingFeature);
                    }
                }
            }
            // If we found an instance where the container was the
            // document root - use that one
            if (!docRootUniqueSet.isEmpty()) {
                uniqueSet = docRootUniqueSet;
            }
        }

        EStructuralFeature usingFeature = null;
        if (!uniqueSet.isEmpty()) {
            usingFeature = uniqueSet.iterator().next();
        }

        return usingFeature;
    }

    /**
     * Finds the class that references the given anonymous type classifier.
     * 
     * @param classifier
     * @return
     */
    protected EClass getAnonymousTypeOwnerClass(EClassifier classifier) {
        EClass result = null;
        EStructuralFeature usingFeature =
                getAnonymousStructuralFeature(classifier, null);
        if (usingFeature != null) {
            result = usingFeature.getEContainingClass();
        }

        return result;
    }

    /**
     * Gets the name to use for an anonymous type, checking the ecore
     * name of the parent if needed
     * 
     * @param plainType     Type to get the name for
     * @param containerObj
     * @return
     */
    protected String getAnonymousTypeName(EDataType plainType,
            ENamedElement containerObj) {

        // Get the object that contains this type
        EStructuralFeature usingFeature =
                getAnonymousStructuralFeature(plainType, containerObj);

        String name = null;
        if (usingFeature != null) {
            // Try and get the ecore name of this object
            name = getEcoreName(usingFeature);

            // Fall back to the default name if no ecore name
            if (name == null) {
                name = usingFeature.getName();
            }
        }

        if (name == null) {
            name = ExtendedMetaData.INSTANCE.getName(plainType);
        }

        return name;
    }

    /**
     * Returns the supplied string, removing the ending if it matches the
     * supplied suffix.
     * 
     * @param name
     * @param suffix
     * @return
     */
    protected String removeSuffixIfPresent(String name, String suffix) {
        String result = name;
        if (result.endsWith(suffix)) {
            result = result.substring(0, result.length() - suffix.length());
        }
        return result;
    }

    /**
     * Assigns a unique name to the supplied EDataType (and it's object-wrapped
     * counterpart, where one exists)
     * 
     * @param plainType
     */
    protected void assignAnonymousDataTypeName(EDataType plainType) {
        EDataType wrappedType = (EDataType) typeToTypeObjectMap.get(plainType);

        if (wrappedType != null && hadEcoreName(wrappedType)) {
            throw new RuntimeException("DO NOT RENAME wrappedType "
                    + wrappedType);
        }

        EClass container = getAnonymousTypeOwnerClass(plainType);
        // If there is no container, then it could be that this
        // type has a wrapped counterpart and it's THAT that's referenced.
        if (container == null && wrappedType != null) {
            container = getAnonymousTypeOwnerClass(wrappedType);
        }

        // Only apply name change if type has an owner. If it's
        // orphaned, leave it alone.
        if (container != null) {
            String name = getAnonymousTypeName(plainType, container);

            // Get the base name then append suffixes for ownership hierarchy
            name = removeSuffixIfPresent(name, ANONYMOUS_TYPE_SUFFIX);
            while (container != null) {
                if (!ExtendedMetaData.INSTANCE.isDocumentRoot(container)) {
                    String containerName = getEcoreName(container);
                    if (containerName == null) {
                        containerName =
                                ExtendedMetaData.INSTANCE.getName(container);
                    }
                    containerName =
                            removeSuffixIfPresent(containerName,
                                    ANONYMOUS_TYPE_SUFFIX);
                    name += "_" + containerName;
                }
                if (ExtendedMetaData.INSTANCE.isAnonymous(container)) {
                    // If it was anonymous, then strip the keyword "Type" from the end
                    name = removeSuffixIfPresent(name, TYPE);
                    container = getAnonymousTypeOwnerClass(container);
                } else {
                    container = null;
                }
            }

            // We can assume that an anonymous enum will have
            // prompted a visible named Enumeration in the BOM
            // and will, therefore, have had a name passed
            // via ecore:name. We don't rename it, but still
            // set its XML name and both names of its
            // object-wrapped counterpart.
            if (!(plainType instanceof EEnum)) {
                String plainName = name + TYPE;
                setNamedElementNameIfChanged(plainType, plainName);
            }
            if (wrappedType != null) {
                String wrappedName = name + TYPE + OBJECT;
                setNamedElementNameIfChanged(wrappedType, wrappedName);
            }
            String plainXMLName = name + ANONYMOUS_TYPE_SUFFIX;
            ExtendedMetaData.INSTANCE.setName(plainType, plainXMLName);
            // This will propagate an appropriate XML name to wrappedType
            fixXMLName(plainType);
        }
    }

    /**
     * Assigns unique names to all anonymous types in the supplied package
     * 
     * @param pkg
     */
    protected void assignAnonymousTypeNames(EPackage pkg) {
        for (EObject obj : pkg.eContents()) {
            if (obj instanceof EDataType) {
                EDataType dt = (EDataType) obj;
                if (ExtendedMetaData.INSTANCE.isAnonymous(dt)) {
                    if (!hadEcoreName(dt)) {
                        if (typeToTypeObjectMap.containsKey(dt)) {
                            // A plainType with a wrapped counterpart
                            assignAnonymousDataTypeName(dt);
                        } else if (!typeToTypeObjectMap.values().contains(dt)) {
                            // A solo type with no counterpart
                            assignAnonymousDataTypeName(dt);
                        }
                    }
                }
            }
        }
    }

    /**
     * A slightly modified version of this method that ensures that classifiers
     * are sorted such that those based on XSD components with ecore:names get
     * priority in naming. Modified region is embraced by comments.
     */
    protected void resolveNameConflicts() {
        for (EPackage ePackage : targetNamespaceToEPackageMap.values()) {

            Map<String, EClassifier> eClassifierMap =
                    new HashMap<String, EClassifier>();

            //
            // TIBCO MOD BEGINS
            //

            // Add a check to make sure we haven't already processed this
            // namespace for renaming. This can be the case when we have
            // sub-packages in the BOM, resulting in being called for each
            // package once because it is a separate XSD, and another
            // because it is an import in one of the other schemas
            if (processedNamespaces.contains(ePackage.getNsURI())) {
                // Already processed so skip to the next one
                continue;
            }
            // As we are about to process this namespace - add it to the list
            processedNamespaces.add(ePackage.getNsURI());

            // Get all classifier from the package and sort them
            // to give priority to those that had ecore:name attributes
            // (i.e. position them earlier in the list, so they get
            // first pick of names)

            List<EClassifier> classifiers = new ArrayList<EClassifier>();

            // Note: We're using a copy of the classifier list; Not
            // actually changing the ordering permanently.
            classifiers.addAll(ePackage.getEClassifiers());
            assignAnonymousTypeNames(ePackage);

            Collections.sort(classifiers, new ClassifierComparator());

            for (EClassifier eClassifier : classifiers) {

                //
                // TIBCO MOD ENDS
                //

                EClassifier otherEClassifier =
                        eClassifierMap.get(eClassifier.getName().toLowerCase());
                if (otherEClassifier != null) {
                    resolveNameConflict(eClassifierMap, eClassifier, "");
                }
                eClassifierMap.put(eClassifier.getName().toLowerCase(),
                        eClassifier);

                String xmlName = extendedMetaData.getName(eClassifier);
                otherEClassifier = extendedMetaData.getType(ePackage, xmlName);
                if (otherEClassifier != eClassifier) {
                    if (xmlName.endsWith("_._type")) {
                        String baseName =
                                xmlName.substring(0, xmlName.length() - 7);
                        int index = 1;
                        while (extendedMetaData.getType(ePackage, baseName
                                + "_._" + index + "_._type") != null) {
                            ++index;
                        }
                        extendedMetaData.setName(eClassifier, baseName + "_._"
                                + index + "_._type");
                        fixXMLName(eClassifier);
                    }
                }

                if (eClassifier instanceof EClass) {
                    Map<String, EStructuralFeature> eFeatureMap =
                            new HashMap<String, EStructuralFeature>();

                    //
                    // TIBCO MOD BEGIN
                    //

                    // Although it's not critical, it makes sense to sort
                    // document root features by name, so that feature names
                    // are applied in a deliberate order. For other classes, all
                    // features will generally have ecore:names (and we'll check
                    // that later), but additional features can occur
                    // (e.g. a 'group' feature to represent a choice; and we
                    // want that to have low naming priority).
                    List<EStructuralFeature> features =
                            new ArrayList<EStructuralFeature>();
                    features.addAll(((EClass) eClassifier)
                            .getEAllStructuralFeatures());
                    Collections.sort(features,
                            new StructuralFeatureComparator());

                    for (EStructuralFeature eStructuralFeature : features) {
                        //
                        // TIBCO MOD END
                        //
                        resolveNameConflict(eFeatureMap, eStructuralFeature, "");
                        eFeatureMap.put(eStructuralFeature.getName()
                                .toLowerCase(), eStructuralFeature);
                    }
                } else if (eClassifier instanceof EEnum) {
                    Map<String, EEnumLiteral> eLiteralMap =
                            new HashMap<String, EEnumLiteral>();
                    for (EEnumLiteral eEnumLiteral : ((EEnum) eClassifier)
                            .getELiterals()) {
                        String literal = eEnumLiteral.getLiteral();
                        resolveNameConflict(eLiteralMap, eEnumLiteral, "");
                        if (!literal.equals(eEnumLiteral.getLiteral())) {
                            eEnumLiteral.setLiteral(literal);
                        }
                        eLiteralMap.put(eEnumLiteral.getName().toLowerCase(),
                                eEnumLiteral);
                    }
                }
            }
        }
    }

    /**
     * Determines whether the supplied model element was derived from an XSD
     * component with an ecore:name
     * 
     * @param elem
     * @return
     */
    protected boolean hadEcoreName(EModelElement elem) {
        return (getEcoreName(elem) != null);
    }

    /**
     * Checks a model element for the ecore name and returns it
     * 
     * @param elem
     *            Element to retrieve the eCore name from
     * @return
     */
    protected String getEcoreName(EModelElement elem) {
        String ecoreName = null;
        for (Entry<XSDComponent, EModelElement> entry : xsdComponentToEModelElementMap
                .entrySet()) {
            if (entry.getValue() == elem) {
                XSDComponent xsdComp = entry.getKey();
                ecoreName = getEcoreAttribute(xsdComp, "name");
                break;
            }
        }
        return ecoreName;
    }

    /**
     * Constructs a map of elements that had no ecore:name, but SHOULD have had
     * an ecore:name.
     * 
     * @return
     */
    protected Map<XSDComponent, EModelElement> buildUnnamedElementsMap() {
        Map<XSDComponent, EModelElement> map =
                new HashMap<XSDComponent, EModelElement>();
        for (Entry<XSDComponent, EModelElement> entry : xsdComponentToEModelElementMap
                .entrySet()) {
            XSDComponent xsdComp = entry.getKey();
            EModelElement eComp = entry.getValue();
            String ecoreName = getEcoreAttribute(xsdComp, "name");
            if (ecoreName == null) {
                if (eComp.eContainer() == XMLTypePackage.eINSTANCE) {
                    // ignore - it's not our entity
                } else if (eComp instanceof EEnumLiteral) {
                    // Enum literals should always have a name
                    map.put(xsdComp, eComp);
                } else if (eComp instanceof EClass) {
                    // A class needs a name unless it's part
                    // of the base Ecore package
                    EPackage pkg = ((EClass) eComp).getEPackage();
                    if (pkg != EcorePackage.eINSTANCE) {
                        map.put(xsdComp, eComp);
                    }
                } else if (eComp instanceof EEnum) {
                    // An enum always needs a name
                    map.put(xsdComp, eComp);
                } else if (eComp instanceof EDataType) {
                    // A data type needs a name, unless it's
                    // one of the special 'StudioGeneratedTemplate' base types
                    // or anonymous.
                    String xmlName =
                            ExtendedMetaData.INSTANCE
                                    .getName((EDataType) eComp);
                    if (!xmlName.endsWith(SGT_SUFFIX)) {
                        if (!ExtendedMetaData.INSTANCE
                                .isAnonymous(((EDataType) eComp))) {
                            map.put(xsdComp, eComp);
                        }
                    }

                } else if (eComp instanceof EStructuralFeature) {
                    // (We don't expect names on DocRoot features)
                    if (!ExtendedMetaData.INSTANCE
                            .isDocumentRoot((EClass) eComp.eContainer())) {
                        // Don't expect a name on a group feature
                        int featureKind =
                                ExtendedMetaData.INSTANCE
                                        .getFeatureKind((EStructuralFeature) eComp);
                        if (featureKind != ExtendedMetaData.GROUP_FEATURE) {
                            map.put(xsdComp, eComp);
                        }
                    }
                } else {
                    map.put(xsdComp, eComp);
                    throw new RuntimeException("UFO missing name");
                }
            }
        }
        return map;
    }

    public Collection<Object> generate(Collection<URI> uris) {
        Collection<Object> result = super.generate(uris);

        // Check for missing ecore:names and populate map.
        // The caller can obtain this later for error reporting.
        unnamedElements = buildUnnamedElementsMap();
        return result;
    }

    /**
     * Returns a list of XSD components and the corresponding model element that
     * should have had ecore:names, but didn't.
     * 
     * @return
     */
    public Map<XSDComponent, EModelElement> getUnnamedElements() {
        return unnamedElements;
    }

    /**
     * If the supplied element's name isn't already set to the supplied name,
     * this sets it. Fails with an exception if it's illegal to rename the
     * element due to its name being dictated by an ecore:name.
     * 
     * @param elem
     * @param name
     */
    protected void setNamedElementNameIfChanged(ENamedElement elem, String name) {
        if (!name.equals(elem.getName())) {
            if (hadEcoreName(elem)) {
                throw new RuntimeException(
                        "Unable to rename element, as it had an ecore:name "
                                + elem);
            }
            elem.setName(name);
        }
    }

}
