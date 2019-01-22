/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.imports.template;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLFactory;

import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.dynamiceobjectreaders.DeoXsdSimpleTypeReader;

/**
 * 
 * 
 * @author rgreen
 * @since 24 Sep 2012
 */
public class Xsd2BomFactory {

    public Xsd2BomFactory INSTANCE = new Xsd2BomFactory();

    public static final String XSD_NOTATION_URI =
            "pathmap://XSD_NOTATION_TYPES/XsdNotation.profile.uml"; //$NON-NLS-1$

    private static Resource XsdNotationProfileResource;

    /**
     * 
     */
    private Xsd2BomFactory() {
    }

    /**
     * @param pkg
     * @param name
     * @param simpleType
     * @return
     */
    public static PrimitiveType createPrimitiveType(
            ImportTransformationData data, Package pkg, String name,
            DynamicEObjectImpl simpleType) {
        PrimitiveType primT = UMLFactory.eINSTANCE.createPrimitiveType();
        primT.setName(name);
        primT.setPackage(pkg);
        TransformHelper.setUniqueId(data, primT);
        applyPrimitiveTypeBaseStereotypes(primT);
        DeoXsdSimpleTypeReader stReader =
                new DeoXsdSimpleTypeReader(simpleType);
        applyPrimitiveTypeInitialStereotypeValues(primT, stReader);
        // TODO:
        // setAnnotations(primT, stReader);

        return primT;
    }

    /**
     * @param pkg
     * @param name
     * @param simpleType
     * @return
     */
    public static Enumeration createEnumeration(ImportTransformationData data,
            Package pkg, String name, DynamicEObjectImpl simpleType) {
        Enumeration en = UMLFactory.eINSTANCE.createEnumeration();
        en.setName(name);
        en.setPackage(pkg);
        TransformHelper.setUniqueId(data, en);
        applyEnumerationBaseStereotypes(en);
        DeoXsdSimpleTypeReader stReader =
                new DeoXsdSimpleTypeReader(simpleType);
        applyEnumerationInitialStereotypeValues(en, stReader);
        // TODO:
        // setAnnotations(primT, stReader);

        return en;
    }

    /**
     * @param primT
     * @param stReader
     */
    private static void setAnnotations(PrimitiveType primT,
            DeoXsdSimpleTypeReader stReader) {

        // TODO: HOW DO YOU EXTRACT ANNOTATION FROM THIS DEO?
        stReader.getRestrictionFacets().getAnnotation();
    }

    /**
     * @param pkg
     * @param name
     * @param simpleType
     * @return
     */
    public static void setPrimitiveType(ImportTransformationData data,
            PrimitiveType primT, Package pkg, String name,
            DynamicEObjectImpl simpleType) {
        primT.setName(name);
        primT.setPackage(pkg);
        TransformHelper.setUniqueId(data, primT);
        applyPrimitiveTypeBaseStereotypes(primT);
        applyPrimitiveTypeInitialStereotypeValues(primT,
                new DeoXsdSimpleTypeReader(simpleType));
    }

    /**
     * The vanilla PrimitiveType is already created and passed in.
     * 
     * @param data
     * @param pkg
     * @param name
     * @param simpleType
     * @return
     */
    public static void setPrimitiveTypeAsAnonymous(
            ImportTransformationData data, PrimitiveType pt, Package pkg,
            String name, DynamicEObjectImpl simpleType) {
        setPrimitiveType(data, pt, pkg, name, simpleType);
        applyPrimitiveTypeAnonymousStereotypes(pt, name);
        ;
    }

    /**
     * @param data
     * @param pkg
     * @param name
     * @param simpleType
     * @return
     */
    public static PrimitiveType createPrimitiveTypeAsAnonymous(
            ImportTransformationData data, Package pkg, String name,
            DynamicEObjectImpl simpleType) {
        PrimitiveType primT = createPrimitiveType(data, pkg, name, simpleType);
        // primT.setName(primT.getName() + "Type");
        // TransformHelper.setAnonStereotypeValue(primT, false);
        applyPrimitiveTypeAnonymousStereotypes(primT, name);
        return primT;
    }

    /**
     * @return
     */
    public static Generalization createGeneralization() {
        Generalization genzn = UMLFactory.eINSTANCE.createGeneralization();
        return genzn;
    }

    /**
     * @param primT
     */
    private static void applyPrimitiveTypeBaseStereotypes(PrimitiveType primType) {
        applyXsdNotationStereotype(primType,
                XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE);
        applyXsdNotationStereotype(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION);
        applyXsdNotationStereotype(primType,
                XsdStereotypeUtils.XSD_BASED_ELEMENT);
    }

    /**
     * @param primType
     * @param simpleType
     */
    public static void applyPrimitiveTypeInitialStereotypeValues(
            PrimitiveType primType, DeoXsdSimpleTypeReader stReader) {

        // XsdBasedPrimitiveType values (excluding anonymous)
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE,
                XsdStereotypeUtils.XSD_PROPERTY_SIMPLETYPE_ID,
                stReader.getID());
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE,
                XsdStereotypeUtils.XSD_PROPERTY_SIMPLETYPE_NAME,
                stReader.getName());
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE,
                XsdStereotypeUtils.XSD_PROPERTY_SIMPLETYPE_FINAL,
                stReader.getFinal());
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE,
                XsdStereotypeUtils.XSD_PROPERTY_RESTRICTION_ID,
                stReader.getRestrictionID());
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE,
                XsdStereotypeUtils.XSD_PROPERTY_RESTRICTION_BASE,
                stReader.getBaseType().getLocalPart());

        // XsdBasedRestriction values: Restriction Facets
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_FRACTION_DIGITS_VALUE,
                stReader.getRestrictionFacets().getFractionDigits());
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_FRACTION_DIGITS_ID,
                stReader.getRestrictionFacets().getFractionDigitsId());
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_FRACTION_DIGITS_FIXED,
                stReader.getRestrictionFacets().getFractionDigitsFixed());

        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_LENGTH_VALUE,
                stReader.getRestrictionFacets().getLength());
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_LENGTH_ID,
                stReader.getRestrictionFacets().getLengthId());
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_LENGTH_FIXED,
                stReader.getRestrictionFacets().getLengthFixed());

        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_MAX_EXCLUSIVE_VALUE,
                stReader.getRestrictionFacets().getMaxExclusive());
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_MAX_EXCLUSIVE_ID,
                stReader.getRestrictionFacets().getMaxExclusiveId());
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_MAX_EXCLUSIVE_FIXED,
                stReader.getRestrictionFacets().getMaxExclusiveFixed());

        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_MAX_INCLUSIVE_VALUE,
                stReader.getRestrictionFacets().getMaxInclusive());
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_MAX_INCLUSIVE_ID,
                stReader.getRestrictionFacets().getMaxInclusiveId());
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_MAX_INCLUSIVE_FIXED,
                stReader.getRestrictionFacets().getMaxInclusiveFixed());

        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_MIN_EXCLUSIVE_VALUE,
                stReader.getRestrictionFacets().getMinExclusive());
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_MIN_EXCLUSIVE_ID,
                stReader.getRestrictionFacets().getMinExclusiveId());
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_MIN_EXCLUSIVE_FIXED,
                stReader.getRestrictionFacets().getMinExclusiveFixed());

        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_MIN_INCLUSIVE_VALUE,
                stReader.getRestrictionFacets().getMinInclusive());
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_MIN_INCLUSIVE_ID,
                stReader.getRestrictionFacets().getMinInclusiveId());
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_MIN_INCLUSIVE_FIXED,
                stReader.getRestrictionFacets().getMinInclusiveFixed());

        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_PATTERN_VALUE,
                stReader.getRestrictionFacets().getPattern());
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_PATTERN_ID,
                stReader.getRestrictionFacets().getPatternId());
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_PATTERN_FIXED,
                stReader.getRestrictionFacets().getPatternFixed());

        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_TOTAL_DIGITS_VALUE,
                stReader.getRestrictionFacets().getTotalDigits());
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_TOTAL_DIGITS_ID,
                stReader.getRestrictionFacets().getTotalDigitsId());
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_TOTAL_DIGITS_FIXED,
                stReader.getRestrictionFacets().getTotalDigitsFixed());

        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_WHITESPACE_VALUE,
                stReader.getRestrictionFacets().getWhitespace());
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_WHITESPACE_ID,
                stReader.getRestrictionFacets().getWhitespaceId());
        setXsdNotationStereotypeValue(primType,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION,
                XsdStereotypeUtils.XSD_WHITESPACE_FIXED,
                stReader.getRestrictionFacets().getWhitespaceFixed());
    }

    /**
     * @param primT
     * @param name
     */
    private static void applyPrimitiveTypeAnonymousStereotypes(
            PrimitiveType primT, String name) {
        setXsdNotationStereotypeValue(primT,
                XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE,
                XsdStereotypeUtils.XSD_PROPERTY_IS_ANON_TYPE,
                "true");
        setXsdNotationStereotypeValue(primT,
                XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE,
                XsdStereotypeUtils.XSD_PROPERTY_SIMPLETYPE_NAME,
                name);
    }

    /**
     * @param primT
     */
    private static void applyEnumerationBaseStereotypes(Enumeration enumeration) {
        applyXsdNotationStereotype(enumeration,
                XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE);
        applyXsdNotationStereotype(enumeration,
                XsdStereotypeUtils.XSD_BASED_ELEMENT);
    }

    /**
     * @param enumeration
     * @param simpleType
     */
    public static void applyEnumerationInitialStereotypeValues(
            Enumeration enumeration, DeoXsdSimpleTypeReader stReader) {

        // XsdBasedPrimitiveType values (excluding anonymous)
        setXsdNotationStereotypeValue(enumeration,
                XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE,
                XsdStereotypeUtils.XSD_PROPERTY_SIMPLETYPE_ID,
                stReader.getID());
        setXsdNotationStereotypeValue(enumeration,
                XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE,
                XsdStereotypeUtils.XSD_PROPERTY_SIMPLETYPE_NAME,
                stReader.getName());
        setXsdNotationStereotypeValue(enumeration,
                XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE,
                XsdStereotypeUtils.XSD_PROPERTY_SIMPLETYPE_FINAL,
                stReader.getFinal());
        setXsdNotationStereotypeValue(enumeration,
                XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE,
                XsdStereotypeUtils.XSD_PROPERTY_RESTRICTION_ID,
                stReader.getRestrictionID());
        setXsdNotationStereotypeValue(enumeration,
                XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE,
                XsdStereotypeUtils.XSD_PROPERTY_RESTRICTION_BASE,
                stReader.getBaseType().getLocalPart());
    }

    /**
     * @param enumeration
     * @param name
     */
    private static void applyEnumerationAnonymousStereotypes(
            Enumeration enumeration, String name) {
        setXsdNotationStereotypeValue(enumeration,
                XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE,
                XsdStereotypeUtils.XSD_PROPERTY_IS_ANON_TYPE,
                "true");
        setXsdNotationStereotypeValue(enumeration,
                XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE,
                XsdStereotypeUtils.XSD_PROPERTY_SIMPLETYPE_NAME,
                name);
    }

    /**
     * @return
     */
    public static Profile getXsdNotationProfile() {
        Profile profile = null;
        if (XsdNotationProfileResource == null) {
            XsdNotationProfileResource =
                    Activator.getDefault().getTransformationEditingDomain()
                            .getResourceSet()
                            .getResource(URI.createURI(XSD_NOTATION_URI), true);
        }

        EObject object = XsdNotationProfileResource.getContents().get(0);
        if (object instanceof Profile) {
            profile = (Profile) object;
        }

        return profile;
    }

    /**
     * @param umlElement
     * @param stereotypeName
     */
    private static Stereotype applyXsdNotationStereotype(Element umlElement,
            String stereotypeName) {
        Profile xsdNotationProfile = getXsdNotationProfile();
        Stereotype ownedStereotype =
                xsdNotationProfile.getOwnedStereotype(stereotypeName);
        Stereotype appliedStereotype =
                umlElement.getAppliedStereotype(ownedStereotype
                        .getQualifiedName());
        if (appliedStereotype == null) {
            umlElement.applyStereotype(ownedStereotype);
            appliedStereotype =
                    umlElement.getAppliedStereotype(ownedStereotype
                            .getQualifiedName());
        }
        return appliedStereotype;
    }

    /**
     * @param umlElement
     * @param appliedSterotypeName
     * @param propertyName
     * @param newValue
     */
    private static void setXsdNotationStereotypeValue(Element umlElement,
            String appliedSterotypeName, String propertyName, String newValue) {
        Stereotype st =
                applyXsdNotationStereotype(umlElement, appliedSterotypeName);
        if (st != null) {
            umlElement.setValue(st, propertyName, newValue);
        }
    }

}
