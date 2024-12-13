/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.types;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.util.UMLUtil;

import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.bom.types.api.BOMEntityNameCleanser;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.util.NameUtil;

/**
 * BOM Primitive types utility class.
 * 
 * This class' methods can modify model so if resource set is edited by
 * Transactional editing domain then client code should access this method
 * within transaction. This class also contains constants for all known base
 * simple types names:
 * 
 * <li>PRIMITIVE_BOOLEAN_NAME</li> <li>PRIMITIVE_TEXT_NAME</li> <li>
 * PRIMITIVE_INTEGER_NAME</li> <li>PRIMITIVE_DECIMAL_NAME</li> <li>
 * PRIMITIVE_DATETIME_NAME</li> <li>PRIMITIVE_DATE_NAME</li> <li>
 * PRIMITIVE_TIME_NAME</li>
 * 
 * <p>
 * <i>Created: 17 Oct 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
/**
 * @author rgreen
 * 
 */
public class PrimitivesUtil {

    /** Tagged property name for displayLabel LabeledElement stereotype. */
    public static final String DISPLAY_LABEL_PROPERTY = "displayLabel"; //$NON-NLS-1$

    /** */
    private static final String DEFAULT_VALUE_POSTFIX = "DefaultValue"; //$NON-NLS-1$

    public static final String TYPES_PATHMAP = "pathmap://BOM_TYPES/"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_TYPES_LIBRARY_URI = TYPES_PATHMAP
            + "BomPrimitiveTypes." + UMLResource.LIBRARY_FILE_EXTENSION; //$NON-NLS-1$

    public static final String BOM_PRIVATE_PRIMITIVE_TYPES_LIBRARY_URI =
            TYPES_PATHMAP + "BomPrivatePrimitiveTypes." + UMLResource.LIBRARY_FILE_EXTENSION; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_TYPES_FACETS_PROFILE_URI =
            TYPES_PATHMAP + "PrimitiveTypeFacets." //$NON-NLS-1$
                    + UMLResource.PROFILE_FILE_EXTENSION; // ;

    /** Name of Boolean type. */
    public static final String BOM_PRIMITIVE_BOOLEAN_NAME = "Boolean"; //$NON-NLS-1$

    /** Name of Object type. */
    public static final String BOM_PRIMITIVE_OBJECT_NAME = "Object"; //$NON-NLS-1$

    /** Name of Enumeration type. */
    public static final String BOM_PRIMITIVE_ENUMERATION_NAME = "Enumeration"; //$NON-NLS-1$

    /** Name of Text type. */
    public static final String BOM_PRIMITIVE_TEXT_NAME = "Text"; //$NON-NLS-1$

    /** Name of Integer type. */
    public static final String BOM_PRIMITIVE_INTEGER_NAME = "Integer"; //$NON-NLS-1$

    /** Name of Decimal type. */
    public static final String BOM_PRIMITIVE_DECIMAL_NAME = "Decimal"; //$NON-NLS-1$

    /** Name of DateTime type. */
    public static final String BOM_PRIMITIVE_DATETIME_NAME = "DateTime"; //$NON-NLS-1$

    /** Name of DateTime and TimeZone type. */
    public static final String BOM_PRIMITIVE_DATETIMETZ_NAME = "DateTimeTZ"; //$NON-NLS-1$

    /** Name of Date type. */
    public static final String BOM_PRIMITIVE_DATE_NAME = "Date"; //$NON-NLS-1$

    /** Name of Time type. */
    public static final String BOM_PRIMITIVE_TIME_NAME = "Time"; //$NON-NLS-1$

    /** Name of ID type. */
    public static final String BOM_PRIMITIVE_ID_NAME = "ID"; //$NON-NLS-1$

    /** Name of URI type. */
    public static final String BOM_PRIMITIVE_URI_NAME = "URI"; //$NON-NLS-1$

    /** Name of Duration type. */
    public static final String BOM_PRIMITIVE_DURATION_NAME = "Duration"; //$NON-NLS-1$

    /**
     * Name of BOM (private) Primitive type used for Case References (note this should be kept in-synch with
     * JsConsts.CASE_REFERENCE)
     */
    public static final String BOM_PRIMITIVE_CASEREFERENCE_NAME = "CaseReference"; //$NON-NLS-1$

    /** Name of Attachment type. */
    public static final String BOM_PRIMITIVE_ATTACHMENT_NAME = "Attachment"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_TIME_DEFAULT_VALUE =
            "timeDefaultValue"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_DATE_TIME_DEFAULT_VALUE =
            "dateTimeDefaultValue"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_DATE_TIME_TZ_DEFAULT_VALUE =
            "dateTimeTZDefaultValue"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_DATE_DEFAULT_VALUE =
            "dateDefaultValue"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_INTEGER_LOWER =
            "integerLower"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_INTEGER_UPPER =
            "integerUpper"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_INTEGER_DEFAULT_VALUE =
            "integerDefaultValue"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_INTEGER_LENGTH =
            "integerLength"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_INTEGER_SUBTYPE =
            "integerSubType"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_BOOLEAN_DEFAULT_VALUE =
            "booleanDefaultValue"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_DECIMAL_DEFAULT_VALUE =
            "decimalDefaultValue"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_DECIMAL_PLACES =
            "decimalPlaces"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_DECIMAL_LENGTH =
            "decimalLength"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_DECIMAL_LOWER_INCLUSIVE =
            "decimalLowerInclusive"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_DECIMAL_LOWER =
            "decimalLower"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_DECIMAL_UPPER_INCLUSIVE =
            "decimalUpperInclusive"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_DECIMAL_UPPER =
            "decimalUpper"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE =
            "decimalSubType"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_OBJECT_SUBTYPE =
            "objectSubType"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_TEXT_PATTERN_VALUE =
            "textPatternValue"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_TEXT_DEFAULT_VALUE =
            "textDefaultValue"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_TEXT_LENGTH = "textLength"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_URI_DEFAULT_VALUE =
            "uriDefaultValue"; //$NON-NLS-1$

    /** Format for storing DateTime as a string in stereotype attributes. */
    private static final String DATETIME_FORMAT_ISO_8601_STRING =
            "yyyy-MM-dd'T'HH:mm:ss"; //$NON-NLS-1$

    /** Format for storing Date as a string in stereotype attributes. */
    private static final String DATE_FORMAT_ISO_8601_STRING = "yyyy-MM-dd"; //$NON-NLS-1$

    /** Format for storing Time as a string in stereotype attributes. */
    private static final String TIME_FORMAT_ISO_8601_STRING = "HH:mm:ss"; //$NON-NLS-1$

    /** Name of the restricted */
    public static final String RESTRICTED_TYPE_STEREOTYPE_NAME =
            "RestrictedType"; //$NON-NLS-1$

    public static final String LABELED_ELEMENT_STEREOTYPE_NAME =
            "LabeledElement"; //$NON-NLS-1$

    private static final String UML_PRIMITIVE_BOOLEAN_NAME = "Boolean"; //$NON-NLS-1$

    private static final String UML_PRIMITIVE_STRING_NAME = "String"; //$NON-NLS-1$

    private static final String UML_PRIMITIVE_UNLIMITED_NATURAL_NAME =
            "UnlimitedNatural"; //$NON-NLS-1$

    private static final String UML_PRIMITIVE_INTEGER_NAME = "Integer"; //$NON-NLS-1$

    public static final String INTEGER_SUBTYPE_SIGNEDINTEGER = "signedInteger"; //$NON-NLS-1$

    public static final String DECIMAL_SUBTYPE_FLOATINGPOINT = "floatingPoint"; //$NON-NLS-1$

    public static final String INTEGER_SUBTYPE_FIXEDLENGTH = "fixedLength"; //$NON-NLS-1$

    /** Maximum length of FixedPoint numbers in ACE. */
    public static final int MAX_FIXED_POINT_NUMBER_LENGTH = 15;

    public static final String DECIMAL_SUBTYPE_FIXEDPOINT = "fixedPoint"; //$NON-NLS-1$

    public static final String OBJECT_SUBTYPE_XSD_ANY = "xsdAny"; //$NON-NLS-1$

    public static final String OBJECT_SUBTYPE_XSD_ANYTYPE = "xsdAnyType"; //$NON-NLS-1$

    public static final String OBJECT_SUBTYPE_XSD_ANYATTRIBUTE =
            "xsdAnyAttribute"; //$NON-NLS-1$

    public static final String OBJECT_SUBTYPE_XSD_ANYSIMPLETYPE =
            "xsdAnySimpleType"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_DURATION_PATTERN_VALUE =
            "durationPatternValue"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_URI_PATTERN_VALUE =
            "uriPatternValue"; //$NON-NLS-1$

    public static final String BOM_PRIMITIVE_FACET_ID_PATTERN_VALUE =
            "idPatternValue"; //$NON-NLS-1$

    /** Bom primitive types restrictions profile name. */
    public static final String PRIMITIVE_TYPE_FACETS_PROFILE_NAME =
            "PrimitiveTypeFacets"; //$NON-NLS-1$

    private PrimitivesUtil() {
    }

    protected static void saveUmlResource(ResourceSet rs, Package umlPackage,
            URI uri) {
        Resource resource = rs.createResource(uri);
        EList<EObject> contents = resource.getContents();
        contents.add(umlPackage);
        for (Iterator<?> allContents =
                UMLUtil.getAllContents(umlPackage, true, false); allContents
                        .hasNext();) {
            EObject eObject = (EObject) allContents.next();
            if (eObject instanceof Element) {
                contents.addAll(
                        ((Element) eObject).getStereotypeApplications());
            }
        }

        try {
            resource.save(null);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    protected static Package loadUmlResource(ResourceSet rs, String uriString) {
        Package umlPackage = null;
        try {
            URI uri = URI.createURI(uriString);
            Resource resource = getResource(rs, uri, true);

            umlPackage =
                    (Package) EcoreUtil.getObjectByType(resource.getContents(),
                            UMLPackage.Literals.PACKAGE);
        } catch (WrappedException we) {
            // TODO LOG
            throw we;
        }
        return umlPackage;
    }

    /**
     * Loads and applys Primitive Types Facets profile to package only if
     * package doesn't have the profile applied yet. The package have to be
     * connected to resource and resource set.
     * 
     * @param umlPackage
     *            UML package to which profile is going to be applied. Package
     *            have to be connected to resource and resource set.
     * @throws IllegalStateException
     *             if package is not connected to resource and resource set.
     */
    protected static void applyFacetsProfile(Package umlPackage) {
        ResourceSet resourceSet = getPackageResourceSet(umlPackage);
        if (resourceSet != null) {
            Profile facetProfile = (Profile) loadUmlResource(resourceSet,
                    BOM_PRIMITIVE_TYPES_FACETS_PROFILE_URI);
            if (umlPackage.getProfileApplication(facetProfile, false) == null) {
                umlPackage.applyProfile(facetProfile);
            }
        }
    }

    /**
     * Applys stereotype to the primitive type. Stereotype will be applied only
     * if it is not already applied to type.
     * 
     * @param primitiveType
     *            the primitive type.
     * @throws IllegalStateException
     *             if primitive type's package is not connected to resource and
     *             resource set.
     */
    protected static void applyFacetStereotype(PrimitiveType primitiveType) {
        // reseting all tagged values
        Package umlPackage = primitiveType.getModel();
        applyFacetsProfile(umlPackage);
        Stereotype st = getFacetsStereotype(getPackageResourceSet(umlPackage));
        if (st != null) {
            Stereotype aplicableStereotype = primitiveType
                    .getApplicableStereotype(st.getQualifiedName());
            if (aplicableStereotype != null) {

                // the stereotype should be already created if it is required
                if (primitiveType
                        .getAppliedStereotype(st.getQualifiedName()) == null) {
                    primitiveType.applyStereotype(aplicableStereotype);
                }
                for (Property p : st.getAllAttributes()) {
                    if (UML2ModelUtil.isExtensionProperty(p)) {
                        continue;
                    }
                    primitiveType
                            .setValue(aplicableStereotype, p.getName(), null);
                }
            }
        }
    }

    private static ResourceSet getPackageResourceSet(Package umlPackage) {
        if (umlPackage == null) {
            // MR 42752 - RS - When a BOM class is deleted, added to avoid a NPE
            return null;
        }
        if (umlPackage.eResource() != null
                && umlPackage.eResource().getResourceSet() != null) {
            return umlPackage.eResource().getResourceSet();
        }
        throw new IllegalStateException(
                "Package have to be connected to a resource and a valid resource set."); //$NON-NLS-1$
    }

    /**
     * Get the RestrictedType stereotype.
     * 
     * @since 3.3
     * 
     * @param rs
     * @return
     */
    public static Stereotype getFacetsStereotype(ResourceSet rs) {
        Profile facetProfile = (Profile) loadUmlResource(rs,
                BOM_PRIMITIVE_TYPES_FACETS_PROFILE_URI);
        return facetProfile.getOwnedStereotype(RESTRICTED_TYPE_STEREOTYPE_NAME);
    }

    /**
     * Get the LabeledElement stereotype.
     * 
     * @param rs
     * @return
     * @since 3.3
     */
    public static Stereotype getLabeledElementStereotype(ResourceSet rs) {
        Profile facetProfile = (Profile) loadUmlResource(rs,
                BOM_PRIMITIVE_TYPES_FACETS_PROFILE_URI);
        return facetProfile.getOwnedStereotype(LABELED_ELEMENT_STEREOTYPE_NAME);
    }

    /**
     * Imports all primitive type from the standard types library.
     * 
     * This method can modify model so if resource set is edited by
     * Transactional editing domain then client code should access this method
     * within transaction.
     * 
     * @param umlPackage
     *            UML package to which type will be imported.
     * @return collection of imported primitive types.
     * @throws IllegalStateException
     *             if the package is not connected to resource and resource set.
     */
    public static Collection<PrimitiveType> importPrimitiveTypes(
            Package umlPackage) {
        ResourceSet resourceSet = getPackageResourceSet(umlPackage);
        if (resourceSet != null) {
            Model primitiveLibrary = (Model) loadUmlResource(resourceSet,
                    BOM_PRIMITIVE_TYPES_LIBRARY_URI);
            List<PrimitiveType> importedPrimitiveTypes =
                    new ArrayList<PrimitiveType>();
            Collection<Object> rawPrimiveTypes =
                    EcoreUtil.getObjectsByType(primitiveLibrary.eContents(),
                            UMLPackage.Literals.PRIMITIVE_TYPE);
            for (Object o : rawPrimiveTypes) {
                PrimitiveType primitiveType = (PrimitiveType) o;
                umlPackage.createElementImport(primitiveType);
                importedPrimitiveTypes.add(primitiveType);
            }
            return importedPrimitiveTypes;
        }
        throw new IllegalStateException(
                "Package have to be connected to a resource and a valid resource set."); //$NON-NLS-1$
    }

    /**
     * Import primitive type from the library. To get imported type you can use
     * {@link Namespace#getImportedMember(String, boolean, org.eclipse.emf.ecore.EClass)}
     * .
     * 
     * @param umlPackage
     *            UML package to which type will be imported.
     * @param typeName
     *            the local name of the type.
     * @return type import.
     * @throws IllegalStateException
     *             if the package is not connected to resource and resource set.
     */
    public static ElementImport importPrimitiveType(Package umlPackage,
            String typeName) {
        ResourceSet resourceSet = getPackageResourceSet(umlPackage);
        if (resourceSet != null) {
            Model primitiveLibrary = (Model) loadUmlResource(resourceSet,
                    BOM_PRIMITIVE_TYPES_LIBRARY_URI);
            PrimitiveType primitiveType =
                    (PrimitiveType) primitiveLibrary.getOwnedType(typeName);
            return umlPackage.createElementImport(primitiveType);
        }
        throw new IllegalStateException(
                "Package have to be connected to a resource and a valid resource set."); //$NON-NLS-1$
    }

    /**
     * Creates new package's primitive type based on existing primitive type.
     * 
     * @param umlPackage
     *            package the type will be created in.
     * @param baseType
     *            base primitive type of the created type.
     * @param name
     *            the name for the created type.
     * @return the created primitive type.
     * @throws IllegalStateException
     *             if primitive type package is not connected to resource and
     *             resource set.
     */
    public static PrimitiveType createPackagePrimitiveType(Package umlPackage,
            PrimitiveType baseType, String name) {
        /*
         * The appropriate stereotype with facets will be applied (if necessary)
         * to the newly created type and all tagged values of the applied
         * stereotype will be reseted (set to null).
         */
        applyFacetsProfile(umlPackage);
        PrimitiveType primitiveType = umlPackage.createOwnedPrimitiveType(name);

        // applying base class
        if (baseType != null) {
            primitiveType.createGeneralization(baseType);
        }

        applyFacetStereotype(primitiveType);
        return primitiveType;
    }

    /**
     * Retrieves the facet's value of primitive type or if not set (null) then
     * from the type's ancestor(s).
     * 
     * @param type
     *            the primitive type for which the value is retrieved.
     * @param property
     *            the property of which value is to be retrieved.
     * @return the value of the primitive type stereotype.
     */
    public static Object getFacetPropertyValue(PrimitiveType type,
            String propertyName) {

        HashSet<PrimitiveType> visited = new HashSet<PrimitiveType>();
        PrimitiveType base = type;

        while (base != null) {

            // avoid hierarchy loops
            if (visited.contains(base)) {
                return null;
            }
            visited.add(base);

            // Get the super class, if any
            Classifier superClass = getSuper(base);
            PrimitiveType superPType =
                    (PrimitiveType) (superClass instanceof PrimitiveType
                            ? superClass
                            : null);

            Package umlPackage = base.getPackage();
            if (umlPackage == null) {
                // reference to detached type, it is only temporary situation.
                return null;
            }
            Stereotype stereotype =
                    getFacetsStereotype(getPackageResourceSet(umlPackage));

            if (!base.isStereotypeApplied(stereotype)) {
                // stereotype is not applied
                // continue to climb up in the hierarchy
                // // applyFacetsProfile(umlPackage);
            } else {
                // Check if the property is set on this primitive type
                boolean isSet =
                        isValueSet(base.getStereotypeApplication(stereotype),
                                propertyName);

                /*
                 * Sid XPD-8417 - the condition below used to be
                 * "isSet || superPType == null" The OR meant that even if the
                 * value was not set BUT there was no super-type then we would
                 * do getValue and this would get the default value if there was
                 * no value set.
                 * 
                 * We used to be able to get away with this because we USED to
                 * be able to set the default value to null by setting
                 * booleanDefaultValue="" in the PrimitiveTypeFacets.profile.uml
                 * (which caused EMF not to fall back on Boolean EdataType's
                 * base default of false because it caused EMF to say
                 * booleanDefaultValue IS SET but because value "" was not
                 * boolean, caused it to be set to null rather than boolean).
                 * 
                 * HOWEVER changes to EMF
                 * (EstructuralFeatureImpl.BOOLEAN_NOTIFICATION_CREATOR.
                 * createNotification()) EMF will not cope with 'is Set to null'
                 * for booleans anymore (because on change it always just does
                 * oldValue.booleanValue() without checking it for null).
                 * 
                 * BECAUSE of this we have to bypass doing base.getValue()
                 * unless the value is set BECAUSE we do not want to default to
                 * "false" if the value is not set for this property (because we
                 * want to tri-state unset|false|true for this facet
                 * 
                 * FOR NOW I have limited this to the booleanDefaultValue
                 * property to limit the impacts of the cahnge BUT we may find
                 * other boolean properties where we want to support tri-state
                 * value).
                 */
                if (BOM_PRIMITIVE_FACET_BOOLEAN_DEFAULT_VALUE
                        .equals(propertyName) && !isSet && superPType == null) {
                    return null;
                }

                /*
                 * If the value is set then return the value, otherwise get the
                 * super-type's value (if this primitive type has no super-type
                 * then return its value).
                 */
                if (isSet || superPType == null) {
                    Object value = base.getValue(stereotype, propertyName);
                    if (value != null) {
                        return value;
                    }
                }
            }
            // check the superclass for the value
            base = superPType;
        }
        return null;
    }

    /**
     * Get the superclass of the given Classifier.
     * 
     * @param classifier
     * @return superclass or <code>null</code> if classifier is not a subclass.
     */
    private static Classifier getSuper(Classifier classifier) {
        EList<Classifier> generals = classifier.getGenerals();
        if (!generals.isEmpty()) {
            return generals.get(0);
        }
        return null;
    }

    /**
     * Check if the given property is set in the Stereotype Application.
     * 
     * @param stereotypeApplication
     * @param property
     * @return <code>false</code> if the value is not set, <code>true</code>
     *         otherwise.
     */
    private static boolean isValueSet(EObject stereotypeApplication,
            String property) {
        if (stereotypeApplication != null) {
            EStructuralFeature feature = stereotypeApplication.eClass()
                    .getEStructuralFeature(property);
            if (feature != null) {
                return stereotypeApplication.eIsSet(feature);
            }
        }
        return true;
    }

    /**
     * Retrieves the value of the facet from the primitive type's application
     * context. If the value was not overwritten in the context of the type
     * application then it will be retrieved from the primitive type.
     * 
     * @param type
     *            the primitive type which value we want to check.
     * @param propertyName
     *            the name of property which value is to be retrieved.
     * @param context
     *            the property to which the primitive type is applied.
     * @return the value of the facet in application context.
     */
    public static Object getFacetPropertyValue(PrimitiveType type,
            String propertyName, Property context) {
        return getFacetPropertyValue(type, propertyName, context, true);
    }

    /**
     * Retrieves the value of the facet from the primitive type's application
     * context. If the fallbackToType flag is set true, and the value was not
     * overwritten in the context of the type application, then it will be
     * retrieved from the primitive type.
     * 
     * @param type
     *            the primitive type which value we want to check.
     * @param propertyName
     *            the name of property which value is to be retrieved.
     * @param context
     *            the property to which the primitive type is applied.
     * @param fallbackToType
     *            if true then value will be inherited from type when absent
     * @return the value of the facet in application context.
     */
    public static Object getFacetPropertyValue(PrimitiveType type,
            String propertyName, Property context, boolean fallbackToType) {
        if (context != null) {
            Stereotype stereotype = getFacetsStereotype(
                    getPackageResourceSet(type.getPackage()));
            EObject stereotypeApplication =
                    context.getStereotypeApplication(stereotype);
            // Get the value if it is actually set, otherwise get the value from
            // the
            // primitive type
            if (stereotypeApplication != null
                    && isValueSet(stereotypeApplication, propertyName)) {
                Object value = context.getValue(stereotype, propertyName);
                if (value != null) {
                    return value;
                }
            }
        }
        if (fallbackToType) {
            return getFacetPropertyValue(type, propertyName);
        } else {
            return null;
        }
    }

    /**
     * Sets the value of primitive type's stereotype.
     * 
     * @param type
     *            the primitive type for which value will be set.
     * @param property
     *            the property of which value will be set.
     * @param value
     *            the new value.
     */
    public static void setFacetPropertyValue(PrimitiveType type,
            String propertyName, Object value) {
        setFacetPropertyValue(type, propertyName, value, null);
    }

    /**
     * Sets the value of primitive type's stereotype in context of type
     * application.
     * 
     * @param type
     *            the primitive type for which value will be set.
     * @param propertyName
     *            the facet property name which value will be set.
     * @param value
     *            the new value.
     * @param context
     *            the application context property.
     */
    public static void setFacetPropertyValue(PrimitiveType type,
            String propertyName, Object value, Property context) {
        Stereotype stereotype =
                getFacetsStereotype(getPackageResourceSet(type.getPackage()));
        if (context == null) {
            if (!type.isStereotypeApplied(stereotype)) {
                applyFacetStereotype(type);
            }
            type.setValue(stereotype, propertyName, value);
            return;
        }
        applyFacetsProfile(context.getModel());
        EObject stereotypeApplication =
                context.getStereotypeApplication(stereotype);
        if (stereotypeApplication == null) {
            context.applyStereotype(stereotype);
            for (Property p : stereotype.getAllAttributes()) {
                if (UML2ModelUtil.isExtensionProperty(p)
                        || p.getName().equals(propertyName)) {
                    continue;
                }
                context.setValue(stereotype, p.getName(), null);
            }
        }

        if (stereotype != null && propertyName != null) {
            // Handle the special case of the text length field, this will
            // default to 50 when created initially, but if the user then clears
            // the field, then we want to ensure that it is set to an
            // "unlimited length". This will be a null when passed in, if we
            // just pass null then it will return to the default "50", for
            // unlimited we need to pass -1. It is a similar case for the
            // decimal places that will default to 2 if null is passed in
            if (value == null) {
                if (propertyName.equals(BOM_PRIMITIVE_FACET_TEXT_LENGTH)
                        || propertyName
                                .equals(BOM_PRIMITIVE_FACET_DECIMAL_PLACES)) {
                    value = "-1";
                }
            }

            /*
             * Sid XPD-8417 changes to EMF mean that context.setValue() WILL
             * ALWAYS default to false for Boolean facet properties EVEN IF we
             * set it to null (because it always eventually defers to Boolean
             * EDataType.getDefaultValue() which is always false).
             * 
             * Therefore for booleanDefaultValue facet property (which we need
             * to be tri-state unset|false|true) we need to bypass calling
             * setValue() which does this and use the raw eSet() which does not.
             * 
             * FOR NOW I have limited this to the booleanDefaultValue property
             * to limit the impacts of the cahnge BUT we may find other boolean
             * properties where we want to support tri-state value).
             */
            boolean setBooleanDefaultValueDone = false;

            if (BOM_PRIMITIVE_FACET_BOOLEAN_DEFAULT_VALUE
                    .equals(propertyName)) {
                if (stereotypeApplication != null) {
                    EClass eClass = stereotypeApplication.eClass();

                    if (eClass != null) {
                        EStructuralFeature eStructuralFeature =
                                eClass.getEStructuralFeature(propertyName);

                        if (eStructuralFeature != null) {
                            stereotypeApplication.eSet(eStructuralFeature,
                                    value);

                            setBooleanDefaultValueDone = true;
                        }
                    }
                }
            }

            /*
             * IF we haven't already property above, then set in the usual way
             */
            if (!setBooleanDefaultValueDone) {
                context.setValue(stereotype, propertyName, value);
            }

        }
    }

    /**
     * Returns list of standard primitive types local names.
     * 
     * @param resourceSet
     *            context resource set.
     * @return collection of standard primitive types names.
     */
    public static Collection<String> getStandardPrimtiveTypeNames(
            ResourceSet resourceSet) {
        Model primitiveLibrary = (Model) loadUmlResource(resourceSet,
                BOM_PRIMITIVE_TYPES_LIBRARY_URI);
        List<String> primitiveTypeNames = new ArrayList<String>();
        Collection<Object> rawPrimiveTypes =
                EcoreUtil.getObjectsByType(primitiveLibrary.eContents(),
                        UMLPackage.Literals.PRIMITIVE_TYPE);
        for (Object o : rawPrimiveTypes) {
            PrimitiveType primitiveType = (PrimitiveType) o;
            primitiveTypeNames.add(primitiveType.getName());
        }
        return primitiveTypeNames;
    }

    /**
     * Returns list of standard primitive types names.
     * 
     * @param resourceSet
     *            context resource set.
     * @param qualified
     *            if <code>true</code> the name will be qualified by a package,
     *            if false than local names will be returned.
     * @return collection of standard primitive types names.
     */
    public static Collection<String> getStandardPrimtiveTypeNames(
            ResourceSet resourceSet, boolean qualified) {
        Model primitiveLibrary = (Model) loadUmlResource(resourceSet,
                BOM_PRIMITIVE_TYPES_LIBRARY_URI);
        List<String> primitiveTypeNames = new ArrayList<String>();
        Collection<Object> rawPrimiveTypes =
                EcoreUtil.getObjectsByType(primitiveLibrary.eContents(),
                        UMLPackage.Literals.PRIMITIVE_TYPE);
        for (Object o : rawPrimiveTypes) {
            PrimitiveType primitiveType = (PrimitiveType) o;
            if (qualified) {
                primitiveTypeNames.add(primitiveType.getQualifiedName());
            } else {
                primitiveTypeNames.add(primitiveType.getName());
            }
        }
        return primitiveTypeNames;
    }

    /**
     * Returns the facets properties names which apply to the specified type.
     * 
     * @param resourceSet
     *            the context resource set.
     * @param standardPrimitiveTypeName
     *            local name of standard property.
     * @return collection of property names which apply to the specified type.
     */
    public static Collection<String> getFacetPropertiesNames(
            ResourceSet resourceSet, String standardPrimitiveTypeName) {

        Profile facetProfile = (Profile) loadUmlResource(resourceSet,
                BOM_PRIMITIVE_TYPES_FACETS_PROFILE_URI);
        Collection<Object> rawFacetEnumerations =
                EcoreUtil.getObjectsByType(facetProfile.eContents(),
                        UMLPackage.Literals.ENUMERATION);
        String expectedFacetEnumName = standardPrimitiveTypeName;
        for (Object o : rawFacetEnumerations) {
            Enumeration facetEnum = (Enumeration) o;
            if (expectedFacetEnumName.equals(facetEnum.getName())) {
                List<String> facetPropertiesNames = new ArrayList<String>();
                for (EnumerationLiteral literal : facetEnum
                        .getOwnedLiterals()) {
                    facetPropertiesNames.add(literal.getName());
                }
                return facetPropertiesNames;
            }
        }
        throw new IllegalArgumentException(
                "Can't find standard primitive description for type: " //$NON-NLS-1$
                        + standardPrimitiveTypeName);
    }

    /**
     * Returns the facets properties names which apply to the specified type.
     * Some facets may be filtered depending on values set (if any). This will
     * be:
     * <ul>
     * <li>If integer sub-type is "Signed Integer" then the integer length facet
     * will be hidden.</li>
     * <li>If decimal sub-type is "Floating Point" then the decimal length and
     * decimal places facets will be hidden.</li>
     * </ul>
     * 
     * @param resourceSet
     * @param target
     *            target <code>PrimitiveType</code> to get properties of.
     * @param property
     *            if target is {@link Property} then this should be set to that
     *            <code>Property</code> and the target set to the type of this
     *            property, otherwise set to <code>null</code>.
     * @return collection of facets property names if any, <code>null</code> if
     *         the primitive type is not a base type.
     * @since 3.3
     */
    public static Collection<String> getFacetPropertiesNames(
            ResourceSet resourceSet, PrimitiveType target, Property property) {
        if (resourceSet != null && target != null) {
            PrimitiveType base = getBasePrimitiveType(target);
            if (base != null) {
                PrimitiveType pt = getStandardPrimitiveTypeByName(resourceSet,
                        base.getName());
                // Check if this is a standard base type
                if (pt == base) {
                    Collection<String> facets =
                            getFacetPropertiesNames(resourceSet,
                                    base.getName());

                    if (facets != null) {
                        for (Iterator<String> iter = facets.iterator(); iter
                                .hasNext();) {
                            String facet = iter.next();
                            if (BOM_PRIMITIVE_FACET_INTEGER_LENGTH
                                    .equals(facet)) {
                                // The integer length attribute should be hidden
                                // if
                                // signed integer
                                Object value = getFacetPropertyValue(target,
                                        BOM_PRIMITIVE_FACET_INTEGER_SUBTYPE,
                                        property);
                                if (value instanceof EnumerationLiteral
                                        && INTEGER_SUBTYPE_SIGNEDINTEGER.equals(
                                                (((EnumerationLiteral) value)
                                                        .getName()))) {
                                    iter.remove();
                                }
                            } else if (BOM_PRIMITIVE_FACET_DECIMAL_PLACES
                                    .equals(facet)
                                    || BOM_PRIMITIVE_FACET_DECIMAL_LENGTH
                                            .equals(facet)) {
                                // The decimal places and length attribute
                                // should be
                                // hidden if floating point
                                Object value = getFacetPropertyValue(target,
                                        BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE,
                                        property);
                                if (value instanceof EnumerationLiteral
                                        && DECIMAL_SUBTYPE_FLOATINGPOINT.equals(
                                                (((EnumerationLiteral) value)
                                                        .getName()))) {
                                    iter.remove();
                                }
                            }
                        }
                    }
                    return facets;
                }
            }
        }
        return null;
    }

    /**
     * UML type of the primitive type facet.
     * 
     * @param propertyName
     * @return
     */
    public static Type getFacetType(ResourceSet rs, String propertyName) {
        return getFacetProperty(rs, propertyName).getType();
    }

    /**
     * Check if the given property is marked as private. Private would mean that
     * the user should not be able to see this property.
     * 
     * @param rs
     * @param propertyName
     * @return <code>true</code> if private, <code>false</code> otherwise.
     * @since 3.3
     */
    public static boolean isPrivate(ResourceSet rs, String propertyName) {
        Property prop = getFacetProperty(rs, propertyName);
        if (prop != null) {
            return prop.getVisibility() != null
                    && prop.getVisibility() == VisibilityKind.PRIVATE_LITERAL;
        }
        return false;
    }

    /**
     * Gets the local name of BOM primitive type representing facets type.
     * 
     * @param rs
     *            the context resource set.
     * @param propertyName
     *            the name of the facet property.
     * @return the name of the BOM primitive type representing facets type or
     *         <code>null</code> if BOM primitive type is not defined.
     */
    @SuppressWarnings("nls")
    public static String getFacetBOMTypeName(ResourceSet rs,
            String propertyName) {
        if (BOM_PRIMITIVE_FACET_TEXT_LENGTH.equals(propertyName)) {
            return BOM_PRIMITIVE_INTEGER_NAME;
        } else if (BOM_PRIMITIVE_FACET_TEXT_DEFAULT_VALUE
                .equals(propertyName)) {
            return BOM_PRIMITIVE_TEXT_NAME;
        } else if (BOM_PRIMITIVE_FACET_TEXT_PATTERN_VALUE
                .equals(propertyName)) {
            return BOM_PRIMITIVE_TEXT_NAME;
        } else if (BOM_PRIMITIVE_FACET_DECIMAL_UPPER.equals(propertyName)) {
            return BOM_PRIMITIVE_DECIMAL_NAME;
        } else if (BOM_PRIMITIVE_FACET_DECIMAL_UPPER_INCLUSIVE
                .equals(propertyName)) {
            return BOM_PRIMITIVE_BOOLEAN_NAME;
        } else if (BOM_PRIMITIVE_FACET_DECIMAL_LOWER.equals(propertyName)) {
            return BOM_PRIMITIVE_DECIMAL_NAME;
        } else if (BOM_PRIMITIVE_FACET_DECIMAL_LOWER_INCLUSIVE
                .equals(propertyName)) {
            return BOM_PRIMITIVE_BOOLEAN_NAME;
        } else if (BOM_PRIMITIVE_FACET_DECIMAL_LENGTH.equals(propertyName)) {
            return BOM_PRIMITIVE_INTEGER_NAME;
        } else if (BOM_PRIMITIVE_FACET_DECIMAL_PLACES.equals(propertyName)) {
            return BOM_PRIMITIVE_INTEGER_NAME;
        } else if (BOM_PRIMITIVE_FACET_DECIMAL_DEFAULT_VALUE
                .equals(propertyName)) {
            return BOM_PRIMITIVE_DECIMAL_NAME;
        } else if (BOM_PRIMITIVE_FACET_BOOLEAN_DEFAULT_VALUE
                .equals(propertyName)) {
            return BOM_PRIMITIVE_BOOLEAN_NAME;
        } else if (BOM_PRIMITIVE_FACET_INTEGER_LENGTH.equals(propertyName)) {
            return BOM_PRIMITIVE_INTEGER_NAME;
        } else if (BOM_PRIMITIVE_FACET_INTEGER_DEFAULT_VALUE
                .equals(propertyName)) {
            return BOM_PRIMITIVE_INTEGER_NAME;
        } else if (BOM_PRIMITIVE_FACET_INTEGER_UPPER.equals(propertyName)) {
            return BOM_PRIMITIVE_INTEGER_NAME;
        } else if (BOM_PRIMITIVE_FACET_INTEGER_LOWER.equals(propertyName)) {
            return BOM_PRIMITIVE_INTEGER_NAME;
        } else if (BOM_PRIMITIVE_FACET_DATE_DEFAULT_VALUE
                .equals(propertyName)) {
            return BOM_PRIMITIVE_DATE_NAME;
        } else if (BOM_PRIMITIVE_FACET_DATE_TIME_DEFAULT_VALUE
                .equals(propertyName)) {
            return BOM_PRIMITIVE_DATETIME_NAME;
        } else if (BOM_PRIMITIVE_FACET_TIME_DEFAULT_VALUE
                .equals(propertyName)) {
            return BOM_PRIMITIVE_TIME_NAME;

        }
        return null;
    }

    /**
     * Applys stereotype to the primitive type. Stereotype will be applied only
     * if it is not already applied to type.
     * 
     * @param primitiveType
     *            the primitive type.
     * @throws IllegalStateException
     *             if primitive type's package is not connected to resource and
     *             resource set.
     */
    public static Stereotype getApplicableFacetStereotype(
            PrimitiveType primitiveType) {
        return getApplicableFacetStereotypeValue(primitiveType);
    }

    /**
     * Applys stereotype to the enumeration type. Stereotype will be applied
     * only if it is not already applied to type.
     * 
     * @param enumeration
     *            the primitive type.
     * @throws IllegalStateException
     *             if primitive type's package is not connected to resource and
     *             resource set.
     */
    public static Stereotype getApplicableFacetStereotype(
            Enumeration enumeration) {
        return getApplicableFacetStereotypeValue(enumeration);
    }

    private static Stereotype getApplicableFacetStereotypeValue(
            DataType dataType) {
        // reseting all tagged values
        Package umlPackage = dataType.getModel();
        // applyFacetsProfile(umlPackage);
        Stereotype st = getFacetsStereotype(getPackageResourceSet(umlPackage));
        if (st != null) {
            Stereotype aplicableStereotype =
                    dataType.getApplicableStereotype(st.getQualifiedName());
            if (aplicableStereotype != null) {
                // the stereotype should be already created if it is required
                if (dataType
                        .getAppliedStereotype(st.getQualifiedName()) == null) {
                    dataType.applyStereotype(aplicableStereotype);
                }
                for (Property p : st.getAllAttributes()) {
                    try {
                        dataType.setValue(aplicableStereotype,
                                p.getName(),
                                null);
                    } catch (Exception e) {

                    }
                }
                return aplicableStereotype;
            }
        }
        return null;
    }

    /**
     * UML property of the primitive type facet.
     * 
     * @param propertyName
     * @return
     */
    public static Property getFacetProperty(ResourceSet rs,
            String propertyName) {
        Stereotype stereotype = getFacetsStereotype(rs);
        EList<Property> attributes = stereotype.getAttributes();
        for (Property prop : attributes) {
            if (propertyName.equals(prop.getName())) {
                return prop;
            }
        }
        throw new IllegalArgumentException(
                "Can't find standard primitive type facet: " //$NON-NLS-1$
                        + propertyName);
    }

    public static boolean isValidFacetValue(ResourceSet rs, String propertyName,
            String value) {

        Type type = getFacetType(rs, propertyName);
        if (type == null) {
            return false;
        }
        if (type.getName().equals(UML_PRIMITIVE_INTEGER_NAME)) {
            try {
                Integer.parseInt(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else if (type.getName()
                .equals(UML_PRIMITIVE_UNLIMITED_NATURAL_NAME)) {
            try {
                long val = Long.parseLong(value);

                // Set to -1 rather than 0 so that we can set values to
                // "undefined"
                return val >= -1;
            } catch (NumberFormatException e) {
                return false;
            }
        } else if (type.getName().equals(UML_PRIMITIVE_STRING_NAME)) {
            return true;
        } else if (type.getName().equals(UML_PRIMITIVE_BOOLEAN_NAME)) {
            return value != null && (value.equalsIgnoreCase("true") || value //$NON-NLS-1$
                    .equalsIgnoreCase("false")); //$NON-NLS-1$
        }
        return false;
    }

    /**
     * Returns human-readable label of the restriction facet.
     * 
     * @param rs
     * @param facet
     */
    public static String getFacetLabel(ResourceSet rs, String propertyName) {
        Stereotype stereotype = getFacetsStereotype(rs);
        EList<Property> attributes = stereotype.getAttributes();
        for (Property prop : attributes) {
            if (propertyName.equals(prop.getName())) {
                return prop.getLabel(true);
            }
        }
        throw new IllegalArgumentException(
                "Can't find standard primitive type facet: " //$NON-NLS-1$
                        + propertyName);
    }

    /**
     * Returns default primitive type that should be assigned to all new custom
     * primitive types and attributes.
     * 
     * @param rs
     * @return
     */
    public static PrimitiveType getDefaultPrimitiveType(ResourceSet rs) {
        return getStandardPrimitiveTypeByName(rs, BOM_PRIMITIVE_TEXT_NAME);
    }

    /**
     * Returns BOM standard primitive with provided name or null.
     * 
     * @param rs
     *            resource set.
     * @param name
     *            the local name of the primitive type.
     * @return BOM standard primitive with provided name or null.
     */
    public static PrimitiveType getStandardPrimitiveTypeByName(ResourceSet rs,
            String name) {
        Resource res = getResource(rs,
                URI.createURI(BOM_PRIMITIVE_TYPES_LIBRARY_URI),
                true);
        if (res != null) {
            EList<EObject> contents = res.getContents();
            for (EObject root : contents) {
                if (root instanceof Model) {
                    PackageableElement type =
                            ((Model) root).getPackagedElement(name);
                    if (type instanceof PrimitiveType) {
                        return (PrimitiveType) type;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns private primitive with provided name or null.
     * 
     * @param rs
     *            resource set.
     * @param name
     *            the local name of the primitive type.
     * @return BOM standard primitive with provided name or null.
     */
    public static PrimitiveType getPrivatePrimitiveTypeByName(ResourceSet rs, String name) {
        Resource res = getResource(rs, URI.createURI(BOM_PRIVATE_PRIMITIVE_TYPES_LIBRARY_URI), true);
        if (res != null) {
            EList<EObject> contents = res.getContents();
            for (EObject root : contents) {
                if (root instanceof Model) {
                    PackageableElement type = ((Model) root).getPackagedElement(name);
                    if (type instanceof PrimitiveType) {
                        return (PrimitiveType) type;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Compute most base primitive type, or return pt if there is no base type
     * or the hierarchy contains loops.
     * 
     * @param pt
     * @return
     */
    public static PrimitiveType getBasePrimitiveType(PrimitiveType pt) {
        if (pt == null) {
            return null;
        }

        PrimitiveType base = pt;
        HashSet<PrimitiveType> visited = new HashSet<PrimitiveType>();
        while (!base.getGenerals().isEmpty()) {
            if (visited.contains(base)) {
                // loop in the hierarchy
                return pt;
            }
            visited.add(base);

            Object gen = base.getGenerals().get(0);

            if (gen instanceof PrimitiveType) {
                base = (PrimitiveType) base.getGenerals().get(0);
            }

        }
        return base;
    }

    /**
     * Formats the storage facet's value using default locale.
     * 
     * @param rs
     *            the context resource set.
     * @param facetPropertyName
     *            the name of facets property value.
     * @param value
     *            the value of property to be formated according to type.
     * @return the formated value using locale settings.
     * @throws ParseException
     *             if there is a problem with parsing the value from its string
     *             representation.
     */
    public static String formatFacetValue(ResourceSet rs,
            String facetPropertyName, String value) throws ParseException {

        String bomTypeName =
                PrimitivesUtil.getFacetBOMTypeName(rs, facetPropertyName);
        if (PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME.equals(bomTypeName)) {
            return new BigDecimal(value).toEngineeringString();
        } else if (PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME
                .equals(bomTypeName)) {
            /*
             * For text length, number length and decimal places if the value is
             * -1 then return an empty string - as this will be treated as an
             * unset value.
             */
            if ("-1".compareTo(value) == 0) {
                if (BOM_PRIMITIVE_FACET_TEXT_LENGTH.equals(facetPropertyName)
                        || BOM_PRIMITIVE_FACET_DECIMAL_PLACES
                                .equals(facetPropertyName)
                        || BOM_PRIMITIVE_FACET_INTEGER_LENGTH
                                .equals(facetPropertyName)) {
                    return ""; //$NON-NLS-1$
                }
            }
            // Store into a BigInteger as Integer will not be large enough for
            // Fixed Integers
            BigInteger val = new BigInteger(value);

            return NumberFormat.getIntegerInstance().format(val);
        } else if (PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME
                .equals(bomTypeName)) {
            SimpleDateFormat dateTimeFormat =
                    new SimpleDateFormat(DATETIME_FORMAT_ISO_8601_STRING);
            return DateFormat.getDateTimeInstance()
                    .format(dateTimeFormat.parse(value));
        } else if (PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME.equals(bomTypeName)) {
            SimpleDateFormat dateFormat =
                    new SimpleDateFormat(DATE_FORMAT_ISO_8601_STRING);
            return DateFormat.getDateInstance().format(dateFormat.parse(value));
        } else if (PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME.equals(bomTypeName)) {
            SimpleDateFormat timeFormat =
                    new SimpleDateFormat(TIME_FORMAT_ISO_8601_STRING);
            return DateFormat.getTimeInstance().format(timeFormat.parse(value));
        } else if (PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME
                .equals(bomTypeName)) {
            // TODO convert form externalized name.
        }
        // not recognized value.
        return value;
    }

    final static int[] DATE_STYLES = new int[] { DateFormat.SHORT,
            DateFormat.MEDIUM, DateFormat.LONG, DateFormat.FULL };

    /**
     * Parses the facet's value in default locale, and transform into storage
     * string representation.
     * 
     * @param rs
     *            the context resource set.
     * @param facetPropertyName
     *            the name of facets property value.
     * @param value
     *            the value of property to be parsed according to its type and
     *            the locale settings.
     * @return the String representation of a value used for storing in profile
     *         application.
     * @throws ParseException
     *             if there is a problem with parsing the value.
     * @throws NumberFormatException
     *             if there is problem with parsing the number.
     * @throws IllegalArgumentException
     *             the passed argument cannot be accepted for storage.
     */
    public static String parseFacetValue(ResourceSet rs,
            String facetPropertyName, String value) throws ParseException {
        String bomTypeName =
                PrimitivesUtil.getFacetBOMTypeName(rs, facetPropertyName);
        String parsedValue = value;
        if (PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME.equals(bomTypeName)) {
            parsedValue = new BigDecimal(value).toEngineeringString();
        } else if (PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME
                .equals(bomTypeName)) {
            // This used to use NumberFormat.getIntegerInstance() but this
            // couldn't deal with large numbers
            // Make sure we remove the commas from any number
            parsedValue = new BigInteger(value.replace(",", "")).toString();
        } else if (PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME
                .equals(bomTypeName)) {
            SimpleDateFormat dateTimeFormat =
                    new SimpleDateFormat(DATETIME_FORMAT_ISO_8601_STRING);

            Date dateTime = null;
            outer: for (int dateStyle : DATE_STYLES) {
                for (int timeStyle : DATE_STYLES) {
                    try {
                        dateTime = DateFormat
                                .getDateTimeInstance(dateStyle, timeStyle)
                                .parse(value);
                    } catch (ParseException e) {
                        continue;
                    }
                    if (dateTime != null) {
                        break outer;
                    }
                }
            }
            if (dateTime == null) {
                throw new ParseException(
                        "Cannot parse dateTime time using any of the styles.", //$NON-NLS-1$
                        0);
            }
            parsedValue = dateTimeFormat.format(dateTime);
        } else if (PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME.equals(bomTypeName)) {
            SimpleDateFormat dateFormat =
                    new SimpleDateFormat(DATE_FORMAT_ISO_8601_STRING);
            Date date = null;
            for (int style : DATE_STYLES) {
                try {
                    date = DateFormat.getDateInstance(style).parse(value);
                } catch (ParseException e) {
                    continue;
                }
                if (date != null) {
                    break;
                }
            }
            if (date == null) {
                throw new ParseException(
                        "Cannot parse date using any of the styles.", 0); //$NON-NLS-1$
            }
            parsedValue = dateFormat.format(date);
        } else if (PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME.equals(bomTypeName)) {
            SimpleDateFormat timeFormat =
                    new SimpleDateFormat(TIME_FORMAT_ISO_8601_STRING);
            Date time = null;
            for (int timeStyle : DATE_STYLES) {
                try {
                    time = DateFormat.getTimeInstance(timeStyle).parse(value);
                } catch (ParseException e) {
                    continue;
                }
                if (time != null) {
                    break;
                }
            }
            if (time == null) {
                throw new ParseException(
                        "Cannot parse time using any of the styles.", 0); //$NON-NLS-1$
            }
            parsedValue = timeFormat.format(time);
        } else if (PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME
                .equals(bomTypeName)) {
            // TODO convert form externalized name.
        }
        // check if the parsed value is compatible with UML types restrictions.
        if (!PrimitivesUtil
                .isValidFacetValue(rs, facetPropertyName, parsedValue)) {
            throw new IllegalArgumentException();
        }
        return parsedValue;
    }

    /**
     * Returns the name of the default property for the type or
     * <code>null</code> if such property doesn't exist.
     * 
     * @param rs
     *            context ResourceSet.
     * @param standardPrimitiveTypeName
     *            the local name of the BOM standard primitive type name.
     * @return the name of the default property for the type or
     *         <code>null</code> if such property doesn't exist.
     */
    public static String getFacetDefaultValuePropertyName(ResourceSet rs,
            String standardPrimitiveTypeName) {
        final String tn = standardPrimitiveTypeName;
        if (tn == null || tn.length() == 0) {
            throw new IllegalArgumentException(
                    "Type name cannot be null or empty."); //$NON-NLS-1$
        }
        // Uses general naming convention to guess the name of default value
        // property.
        String defaultValuePropertyName = tn.substring(0, 1).toLowerCase()
                + tn.substring(1) + DEFAULT_VALUE_POSTFIX;

        // URI exception
        if (BOM_PRIMITIVE_URI_NAME.equals(tn)) {
            defaultValuePropertyName = BOM_PRIMITIVE_FACET_URI_DEFAULT_VALUE;
        }

        return getFacetPropertiesNames(rs, tn).contains(
                defaultValuePropertyName) ? defaultValuePropertyName : null;
    }

    /**
     * 
     * If the supplied PrimitiveType has a subtype and it contains an
     * EnumerationLiteral matching the propertyName then that Enumeration is
     * returned
     * 
     * @param propertyName
     *            subtype name
     * @param primType
     *            The primitive type that has a subtype
     * @return Enumeration
     */
    public static Enumeration getSubTypeEnumerationFromFacetPropertyValue(
            String propertyName, PrimitiveType primType) {
        Enumeration enumeration = null;

        Object facetPropertyValue =
                PrimitivesUtil.getFacetPropertyValue(primType, propertyName);

        if (facetPropertyValue instanceof EnumerationLiteral) {
            enumeration =
                    ((EnumerationLiteral) facetPropertyValue).getEnumeration();
        }

        return enumeration;

    }

    /**
     * Get the Labeledelement stereotype.
     * 
     * @since 3.3
     * 
     * @param rs
     * @return
     */
    protected static Stereotype getLabelededElementStereotype(ResourceSet rs) {
        Profile facetProfile = (Profile) loadUmlResource(rs,
                BOM_PRIMITIVE_TYPES_FACETS_PROFILE_URI);
        return facetProfile.getOwnedStereotype(LABELED_ELEMENT_STEREOTYPE_NAME);
    }

    protected static void applyLabelElementStereotype(NamedElement element) {
        Package umlPackage = element.getModel();
        applyFacetsProfile(umlPackage);
        Stereotype st = getLabelededElementStereotype(
                getPackageResourceSet(umlPackage));
        if (st != null) {
            Stereotype aplicableStereotype =
                    element.getApplicableStereotype(st.getQualifiedName());
            if (aplicableStereotype != null) {

                // the stereotype should be already created if it is required
                if (element
                        .getAppliedStereotype(st.getQualifiedName()) == null) {
                    element.applyStereotype(aplicableStereotype);
                }
            }
        }
    }

    /**
     * Returns label for a NamedElement. If will use UML getLabel() if label is
     * not set. </br>
     * This method is equivalent to: <b>getDisplayLabel(element, true)</b>.
     * 
     * @param element
     *            the context element.
     * @return label for a NamedElement.
     * @since 3.3
     * @see #getDisplayLabel(NamedElement, boolean)
     */
    public static String getDisplayLabel(NamedElement element) {
        return getDisplayLabel(element, true);
    }

    /**
     * Returns label for a NamedElement. If useFallback is <code>true</code>
     * then and label is not set it will return UML getLabel().
     * 
     * @param element
     *            the context element.
     * @param useFallback
     *            if fallback mechanism should be used.
     * @return label for a NamedElement.
     * @since 3.3
     */
    public static String getDisplayLabel(NamedElement element,
            boolean useFallback) {
        // Check for the case where the element is not set
        if (element == null) {
            return null;
        }

        String label = null;
        if (element.getModel() != null && element.eResource() != null
                && element.eResource().getResourceSet() != null) {
            Stereotype stereotype = getLabelededElementStereotype(
                    getPackageResourceSet(element.getModel()));
            EObject stereotypeApplication =
                    element.getStereotypeApplication(stereotype);
            if (stereotypeApplication != null) {
                Object value =
                        element.getValue(stereotype, DISPLAY_LABEL_PROPERTY);
                if (value instanceof String) {
                    label = (String) value;
                }
            }
        }
        if (label == null && useFallback) {
            if (!(element instanceof Association)) {
                label = element.getLabel();
            } else if ((element instanceof AssociationClass)) {
                label = element.getLabel();
            }
        }
        return label;
    }

    /**
     * Sets the label for a named element and synchronize name. </br>
     * This method is equivalent to: <b>setDisplayLabel(element, displayName,
     * true)</b>.
     * 
     * @param element
     *            the context element.
     * @param displayLabel
     *            the label to set.
     * @since 3.3
     */
    public static void setDisplayLabel(NamedElement element,
            String displayLabel) {
        setDisplayLabel(element, displayLabel, true);
    }

    /**
     * Sets the label for a named element.
     * 
     * @param element
     *            the context element.
     * @param displayLabel
     *            the label to set.
     * @param syncWithName
     *            true if label should be synchronized with name.
     * @since 3.3
     */
    public static void setDisplayLabel(NamedElement element,
            String displayLabel, boolean syncWithName) {

        if (syncWithName) {
            String oldLabel = getDisplayLabel(element);
            String newLabel = displayLabel;

            // oldRequiredName is the name that would be generated from the old
            // label but MIGHT have been changed subsequently by the user
            String oldRequiredName = null;
            if (oldLabel != null) {
                oldRequiredName = getBOMInternalName(oldLabel, false, element);
            }
            String oldActualName = element.getName();

            // Deal with Association as a special case since it can optionally
            // have a label or name
            if (element instanceof Association
                    && !(element instanceof AssociationClass)) {

                if (oldActualName != null && oldLabel == null) {
                    // Skip
                } else if ((oldRequiredName != null
                        && oldRequiredName.equals(oldActualName))
                        || (oldRequiredName == null && newLabel != null)) {
                    element.setName(newLabel != null
                            ? getBOMInternalName(newLabel, false, element)
                            : null);
                }

            } else {

                boolean isNameNotUserDefined = false;

                // was name fully synced with label (name already cleansing)
                isNameNotUserDefined = ((oldRequiredName != null)
                        && oldRequiredName.equals(oldActualName))
                        || (oldRequiredName == null && newLabel != null);

                // was name synced with label (name pending cleansing)
                if (!isNameNotUserDefined) {
                    /*
                     * If the oldname didn't EXACTLY match the expected default
                     * name for the old label then it might be that it was not
                     * already cleansed properly to be valid bom-token-name.
                     * 
                     * So do a quick cleanse on the old name and then check that
                     * that is correct default for old label.
                     */
                    String oldActualNameCleansed =
                            getBOMInternalName(oldActualName, false, element);
                    isNameNotUserDefined = (oldRequiredName != null)
                            && oldRequiredName.equals(oldActualNameCleansed);
                }

                // if name looks like it should be synced with label then update
                // name based on new label
                if (isNameNotUserDefined) {
                    String newName = (newLabel != null)
                            ? getBOMInternalName(newLabel, false, element)
                            : null;
                    element.setName(newName);
                }
            }
        }

        if (element.getModel() != null) {
            // sets displayLabel in stereotype.
            Stereotype stereotype = getLabelededElementStereotype(
                    getPackageResourceSet(element.getModel()));
            if (!element.isStereotypeApplied(stereotype)) {
                applyLabelElementStereotype(element);
            }
            element.setValue(stereotype, DISPLAY_LABEL_PROPERTY, displayLabel);
        }

    }

    /**
     * Removes the display label of a NamedElement by unapplying the Labeled
     * Element stereotype.
     * 
     * @param element
     */
    public static void unsetDisplayLabel(NamedElement element) {

        Stereotype stereotype = getLabelededElementStereotype(
                getPackageResourceSet(element.getModel()));

        if (element.isStereotypeApplied(stereotype)) {
            element.unapplyStereotype(stereotype);
        }

    }

    private static String PACKAGE_INVALID = "[^a-zA-Z0-9_\\.]"; //$NON-NLS-1$

    public static String getBOMInternalName(String displayName,
            boolean removeLeadingNumerics, NamedElement element) {

        BOMEntityNameCleanser cleanser = BOMEntityNameCleanser.getInstance();
        if (cleanser.isSupportedElement(element)) {
            // The cleanser can deal with this element
            return cleanser.cleanseNamedElementName(element, displayName);
        } else {
            // The cleanser doesn't support this element type, so apply the
            // logic that existed before the cleanser was introduced.
            if (element instanceof Model) {
                String newName = displayName.replaceAll(PACKAGE_INVALID, ""); //$NON-NLS-1$
                while (newName.length() > 0 && newName.charAt(0) == '.') {
                    newName = newName.substring(1);
                }
                if (removeLeadingNumerics) {
                    while (newName.length() > 0
                            && Character.isDigit(newName.charAt(0))) {
                        newName = newName.substring(1);
                    }
                }
                while (newName.length() > 0 && newName.charAt(0) == '.') {
                    newName = newName.substring(1);
                }
                return newName;
            }
            return NameUtil.getInternalName(displayName, false);
        }
    }

    /**
     * Sets the value of primitive type's stereotype.
     * 
     * @param enumeration
     *            the enumeration for which value will be set.
     * @param property
     *            the property of which value will be set.
     * @param value
     *            the new value.
     */
    public static void setFacetPropertyValue(Enumeration enumeration,
            String propertyName, Object value) {
        setFacetPropertyValue(enumeration, propertyName, value, null);
    }

    /**
     * Sets the value of primitive type's stereotype in context of type
     * application.
     * 
     * @param enumeration
     *            the enumeration for which value will be set.
     * @param propertyName
     *            the facet property name which value will be set.
     * @param value
     *            the new value.
     * @param context
     *            the application context property.
     */
    public static void setFacetPropertyValue(Enumeration enumeration,
            String propertyName, Object value, Property context) {
        Stereotype stereotype = getFacetsStereotype(
                getPackageResourceSet(enumeration.getPackage()));
        if (context == null) {
            if (!enumeration.isStereotypeApplied(stereotype)) {
                applyFacetStereotype(enumeration);
            }
            enumeration.setValue(stereotype, propertyName, value);
            return;
        }
        applyFacetsProfile(context.getModel());
        EObject stereotypeApplication =
                context.getStereotypeApplication(stereotype);
        if (stereotypeApplication == null) {
            context.applyStereotype(stereotype);
            for (Property p : stereotype.getAllAttributes()) {
                if (UML2ModelUtil.isExtensionProperty(p)
                        || p.getName().equals(propertyName)) {
                    continue;
                }
                context.setValue(stereotype, p.getName(), null);
            }
        }
        context.setValue(stereotype, propertyName, value);
    }

    /**
     * Applys stereotype to the enumeration. Stereotype will be applied only if
     * it is not already applied to type.
     * 
     * @param primitiveType
     *            the primitive type.
     * @throws IllegalStateException
     *             if primitive type's package is not connected to resource and
     *             resource set.
     */
    protected static void applyFacetStereotype(Enumeration enumeration) {
        // reseting all tagged values
        Package umlPackage = enumeration.getModel();
        applyFacetsProfile(umlPackage);
        Stereotype st = getFacetsStereotype(getPackageResourceSet(umlPackage));
        if (st != null) {
            Stereotype aplicableStereotype =
                    enumeration.getApplicableStereotype(st.getQualifiedName());
            if (aplicableStereotype != null) {

                // the stereotype should be already created if it is required
                if (enumeration
                        .getAppliedStereotype(st.getQualifiedName()) == null) {
                    enumeration.applyStereotype(aplicableStereotype);
                }
                for (Property p : st.getAllAttributes()) {
                    if (UML2ModelUtil.isExtensionProperty(p)) {
                        continue;
                    }
                    enumeration
                            .setValue(aplicableStereotype, p.getName(), null);
                }
            }
        }
    }

    /**
     * 
     * 
     * 
     * @param PrimitiveType
     * @return String
     */
    public static String getFacetNameForDefaultValue(
            PrimitiveType basePrimitiveType) {

        ResourceSet rSet = XpdResourcesPlugin.getDefault().getEditingDomain()
                .getResourceSet();

        if (basePrimitiveType == PrimitivesUtil.getStandardPrimitiveTypeByName(
                rSet,
                PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME)) {
            return PrimitivesUtil.BOM_PRIMITIVE_FACET_BOOLEAN_DEFAULT_VALUE;
        } else if (basePrimitiveType == PrimitivesUtil
                .getStandardPrimitiveTypeByName(rSet,
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
            return PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_DEFAULT_VALUE;
        } else if (basePrimitiveType == PrimitivesUtil
                .getStandardPrimitiveTypeByName(rSet,
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {
            return PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_DEFAULT_VALUE;
        } else if (basePrimitiveType == PrimitivesUtil
                .getStandardPrimitiveTypeByName(rSet,
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME)) {
            return PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_DEFAULT_VALUE;
        } else if (basePrimitiveType == PrimitivesUtil
                .getStandardPrimitiveTypeByName(rSet,
                        PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME)) {
            return PrimitivesUtil.BOM_PRIMITIVE_FACET_TIME_DEFAULT_VALUE;
        } else if (basePrimitiveType == PrimitivesUtil
                .getStandardPrimitiveTypeByName(rSet,
                        PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME)) {
            return PrimitivesUtil.BOM_PRIMITIVE_FACET_DATE_TIME_DEFAULT_VALUE;
        } else if (basePrimitiveType == PrimitivesUtil
                .getStandardPrimitiveTypeByName(rSet,
                        PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME)) {
            return PrimitivesUtil.BOM_PRIMITIVE_FACET_DATE_TIME_TZ_DEFAULT_VALUE;
        } else if (basePrimitiveType == PrimitivesUtil
                .getStandardPrimitiveTypeByName(rSet,
                        PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME)) {
            return PrimitivesUtil.BOM_PRIMITIVE_FACET_DATE_DEFAULT_VALUE;
        } else if (basePrimitiveType == PrimitivesUtil
                .getStandardPrimitiveTypeByName(rSet,
                        PrimitivesUtil.BOM_PRIMITIVE_URI_NAME)) {
            return PrimitivesUtil.BOM_PRIMITIVE_FACET_URI_DEFAULT_VALUE;
        }

        return null;

    }

    /**
     * SID XPD-1605: Calling resourceSet.getResource() was frequently hitting
     * ConcurrentModificationException's
     * <p>
     * This was because {@link ResourceSetImpl#getResource(URI, boolean)}
     * iterates thru a list which can change (namely because during start of
     * Bom2Xsd build we may delete xsd's previously generated from the BOM's;
     * Then we start the transformation of Bom2Xsd; Then the working copy (on a
     * different thread) receives notification of the delete and u8nloads the
     * Resource - this changes the resource set!
     * <p>
     * This method negates this problem by catching concurrent modification
     * exception and retrying.
     * 
     * @param resourceSet
     * 
     * @return Resource (as per {@link ResourceSet#getResource(URI, boolean)}
     */
    private static Resource getResource(ResourceSet resourceSet, URI uri,
            boolean loadOnDemand) {

        while (true) {
            try {
                Resource ret = resourceSet.getResource(uri, loadOnDemand);

                return ret;

            } catch (ConcurrentModificationException cme) {
                // System.out
                // .println(PrimitivesUtil.class.getName()
                // +
                // ".getResource(): Caught ConcurrentModificationException
                // getting resource; waiting 0.5 seconds before retry.");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    return null;
                }
            }
        }
    }

	/**
	 * Returns <code>true</code> if the Decimal property has a sub-type of fixedPoint.
	 * 
	 * @param type
	 *            the base primitive type of the property.
	 * @param property
	 *            the property.
	 * @return <code>true</code> if the Decimal property has a sub-type of fixedPoint.
	 */
	public static boolean isFixedPointDecimal(Property property)
	{
		if (property.getType() instanceof PrimitiveType)
		{
			Object decimalSubType = PrimitivesUtil.getFacetPropertyValue((PrimitiveType) property.getType(),
					PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE, property, /* falbackToBaseType */ true);
			return (decimalSubType instanceof EnumerationLiteral) && PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT
					.equals(((EnumerationLiteral) decimalSubType).getName());
		}
		return false;
	}

	/**
	 * Returns true if BOM attribute is array.
	 * 
	 * @param bomAttribute
	 *            the bom attribute.
	 * @return true if BOM attribute is array.
	 */
	public static boolean isArray(Property bomAttribute)
	{
		int upper = bomAttribute.getUpper();
		// -1 means +infinity (unbounded).
		return upper == -1 || upper > 1;
	}

}
