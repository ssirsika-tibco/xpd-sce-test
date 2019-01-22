/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.imports.template;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMapUtil.FeatureEList;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xml.type.internal.QName;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.openarchitectureware.xsd.XMLReaderImpl;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.bom.xsdtransform.dynamiceobjectreaders.DeoXsdComplexTypeReader;
import com.tibco.xpd.bom.xsdtransform.dynamiceobjectreaders.DeoXsdSchemaReader;
import com.tibco.xpd.bom.xsdtransform.dynamiceobjectreaders.DeoXsdSimpleTypeReader;
import com.tibco.xpd.bom.xsdtransform.internal.BaseBOMXtendTransformer;
import com.tibco.xpd.bom.xsdtransform.utils.NamespaceURIToJavaPackageMapper;
import com.tibco.xpd.bom.xsdtransform.utils.TopLevelAttributeWrapper;
import com.tibco.xpd.bom.xsdtransform.utils.TopLevelElementWrapper;
import com.tibco.xpd.bom.xsdtransform.utils.XSDSequenceWrapper;
import com.tibco.xpd.bom.xsdtransform.utils.XsdTypes;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * Java extension used by the OAW transformation (Xtend) during the import of
 * the XSD/WSDLs.
 * 
 * @author glewis
 * 
 */
public class TransformHelper {

    private static final String ELEM_REPLACED_NAME = "*"; //$NON-NLS-1$

    private static final String COMPLEX_TYPE_EXTENSION = "Type"; //$NON-NLS-1$

    private static final String PRIMITIVETYPE_FACET_URI =
            PrimitivesUtil.BOM_PRIMITIVE_TYPES_FACETS_PROFILE_URI;

    public static final String XSD_NOTATION_URI =
            "pathmap://XSD_NOTATION_TYPES/XsdNotation.profile.uml"; //$NON-NLS-1$

    /**
     * Load the given schema. This uses the {@link XMLReaderImpl} to load the
     * XML and so will create a OAW XSD resource.
     * 
     * @param data
     * @param schemaLocation
     * @return OAW XSD schema document if the schema was loaded,
     *         <code>null</code> otherwise.
     */
    public static Object readXML(ImportTransformationData data,
            Object schemaType, String schemaLocation) {

        URI baseUri = resolveBaseURI(data, schemaType);

        /* SID XPD-5079 Moved readXML() for caching. */
        return data.readXML(baseUri, schemaLocation, false);
    }

    /**
     * XPD-6062 Return the full resolved URI of the schema referenced by the
     * schemaLocation passed as relative to the file containing the
     * anyObjectFromReferencingSchema parameter
     * 
     * @param data
     * @param objectFromReferencingSchema
     *            Any object (probably the import/include element) from teh
     *            schema that references the schemaLocation (for imports and
     *            includes processing) OR <code>null</code> can be passed if not
     *            a reference (i.e. schemaLocation is also passed as
     *            <code>null</code> cos dealing with original schema being
     *            transformed.
     * @param schemaLocation
     * 
     * @return The full URI of the referenced schema.
     */
    private static URI resolveReferencedSchemaLocationURI(
            ImportTransformationData data, Object objectFromReferencingSchema,
            String schemaLocation) {
        URI baseUri = resolveBaseURI(data, objectFromReferencingSchema);

        return data.getSchemaURI(baseUri, schemaLocation);
    }

    /***
     * 
     * @param data
     * @param definitions
     * @param schemaLocation
     * @return Already Cached xs:schema or wsdl:definitions element for given
     *         baseURI and schemaLocation or <code>null</code> if not cached.
     */
    public static Object getCachedReadXMLExt(ImportTransformationData data,
            Object definitions, String schemaLocation) {

        URI baseUri = resolveBaseURI(data, definitions);
        return data.getCachedReadXMLFromData(baseUri, schemaLocation);
    }

    /**
     * @param data
     * @param schemaType
     * @return
     */
    public static URI resolveBaseURI(ImportTransformationData data,
            Object schemaType) {
        URI baseUri = null;

        /*
         * XPD-4781: if the imports/includes of xsd were in certain folder
         * structure, transformation was failing because of the incorrect base
         * and relative uri's (w.r.to wsdl and imported/included xsd) being set.
         */
        if (schemaType instanceof DynamicEObjectImpl) {

            Resource eResource = ((DynamicEObjectImpl) schemaType).eResource();

            if (null != eResource) {
                baseUri = eResource.getURI();
            }
        }

        /*
         * if by any chance the new way of getting base uri above returns null,
         * then we will get it the old way as a fall back mechanism.
         */
        if (null == baseUri) {
            baseUri = data.getCurrentBaseURIFromImportsAndIncludes();
        }
        return baseUri;
    }

    /**
     * Loads the schema at the given namespace uri and creates an OAW XSD
     * Resource from it.
     * 
     * @param data
     * @param namespace
     * @return OAW XSD schema document if the schema was loaded,
     *         <code>null</code> otherwise.
     */
    public static Object readXMLFromURL(ImportTransformationData data,
            String namespace) {
        return BaseBOMXtendTransformer.readXMLFromURL(data.getOAWResourceSet(),
                namespace);
    }

    /**
     * @param rootURI
     * @param modelName
     * @return
     */
    private static URI resolveBOMUri(URI rootURI, String modelName) {
        URI bomURI = rootURI.trimSegments(1).appendSegment(modelName + ".bom"); //$NON-NLS-1$
        return bomURI;
    }

    /**
     * @param data
     * @param model
     */
    public static void addOtherResultModel(ImportTransformationData data,
            Model model) {
        if (data.getModelResourceMap().get(model) == null) {
            URI uri = data.getDiagramResource().getURI();
            uri = resolveBOMUri(uri, model.getName());
            Resource resource = data.getEditingDomain().getResourceSet()
                    .getResource(uri, true);
            Resource tempXSDProfileResource =
                    data.getEditingDomain().getResourceSet()
                            .getResource(URI.createURI(XSD_NOTATION_URI), true);
            applyProfile(model, tempXSDProfileResource);

            Resource tempPrimitivesProfileResource = resource.getResourceSet()
                    .getResource(URI.createURI(PRIMITIVETYPE_FACET_URI), true);
            applyProfile(model, tempPrimitivesProfileResource);

            // sometimes wsdls can contain an xml schema of same target
            // namespace as
            // each other and in order to prevent
            // the builder from concatenating the model/diagrams into the same
            // resource we need to clean any existing contents
            // before adding a new model and new transformation process.
            resource.getContents().clear();
            resource.getContents().add(model);

            data.getOtherResultModels().add(model);
            data.getModelResourceMap().put(model, resource);
        }
    }

    /**
     * Test if a URL exists
     * 
     * @param url
     * @return
     */
    private static boolean exists(String url) throws Exception {
        boolean exists = false;

        URL myUrl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();
        try {
            con.connect();
            exists = true;
        } catch (UnknownHostException uhe) {
        }
        con.disconnect();
        return exists;
    }

    /**
     * @param model
     * @param elementFormDefault
     * @param attributeFormDefault
     */
    public static void setFormDefaults(Model model, String elementFormDefault,
            String attributeFormDefault) {
        if ((elementFormDefault == null && attributeFormDefault == null)
                || (elementFormDefault.trim().length() == 0
                        && attributeFormDefault.trim().length() == 0)) {
            return;
        }
        Stereotype stereotype = null;
        Iterator<Stereotype> stereoTypeIter =
                model.getAppliedStereotypes().iterator();
        while (stereoTypeIter.hasNext()) {
            stereotype = stereoTypeIter.next();
            if (stereotype.getName()
                    .equals(XsdStereotypeUtils.XSD_BASED_MODEL)) {
                if (elementFormDefault != null
                        && elementFormDefault.trim().length() > 0) {
                    model.setValue(stereotype,
                            XsdStereotypeUtils.XSD_MODEL_ELEMENT_FORM_DEFAULT,
                            elementFormDefault);

                }
                if (attributeFormDefault != null
                        && attributeFormDefault.trim().length() > 0) {
                    model.setValue(stereotype,
                            XsdStereotypeUtils.XSD_MODEL_ATTRIBUTE_FORM_DEFAULT,
                            attributeFormDefault);
                }
            }
        }

        Iterator<Profile> profilesIter = model.getAppliedProfiles().iterator();
        while (profilesIter.hasNext()) {
            Profile profile = profilesIter.next();
            if (profile.getName()
                    .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                Object obj = profile
                        .getPackagedElement(XsdStereotypeUtils.XSD_BASED_MODEL);
                if (obj instanceof Stereotype) {
                    stereotype = (Stereotype) obj;
                    try {
                        if (elementFormDefault != null
                                && elementFormDefault.trim().length() > 0) {
                            model.setValue(stereotype,
                                    XsdStereotypeUtils.XSD_MODEL_ELEMENT_FORM_DEFAULT,
                                    elementFormDefault);

                        }
                        if (attributeFormDefault != null
                                && attributeFormDefault.trim().length() > 0) {
                            model.setValue(stereotype,
                                    XsdStereotypeUtils.XSD_MODEL_ATTRIBUTE_FORM_DEFAULT,
                                    attributeFormDefault);
                        }
                    } catch (Exception e) {
                    }
                }

            }
        }
    }

    /**
     * Returns the same schema location passed in if it is a valid href else we
     * can assume it is a relative path and we resolved the uri using the root
     * location of the selected xsd file for transformation.
     * 
     * @param schemaLocation
     * @return
     */
    public static String _createURIForSchemaLocation(
            ImportTransformationData data, String schemaLocation) {
        /*
         * Sid XPD-1741: This method is called to resolve import/include schema
         * locations which might be relative to importing schema file location
         * (not necessarily the top-level file because there could be multiple
         * nesting).
         * 
         * However, NOW that BaseBOMXtendTransformer.readXML() sets the baseURI
         * and import-uri in the OAW XMLReaderImpl AND we correctly pass the
         * baseURI or the schema/wsdl we are currently dealing with (rather than
         * always the folder containing top-level wsdl/xsd) THEN the relative
         * imports work using the actual-importing file as the base URI
         * 
         * So now all we need to do is return schemaLocation as-is safe in the
         * knowledge that other things will sort it out for us. Just for go
         * measure we'll convert it to URI and back again without unescaping so
         * that things like space get URI escaped (changed to %20 and so on) so
         * that it's a proper URI string.
         */
        return URI.createURI(schemaLocation, true).toString();

    }

    /**
     * Outputs text to system console
     * 
     * @param temp
     * @return
     */
    public static String traceMe(String temp) {
        // if (Platform.inDebugMode() || Platform.inDevelopmentMode()) {
        // Activator.getDefault().getLogger().debug(temp);
        // System.out.println(temp);
        // }
        return ""; //$NON-NLS-1$
    }

    /**
     * Indent for {@link #traceMeDebug(String)}
     */
    static int debugTraceIndent = 0;

    public static String traceMeDebug(String temp) {
        boolean debugTraceEnabled = false;

        if (!debugTraceEnabled) {
            return ""; //$NON-NLS-1$
        }

        // if (Platform.inDebugMode() || Platform.inDevelopmentMode()) {
        // Activator.getDefault().getLogger().debug(temp);
        if (temp.equals("resetIndent")) { //$NON-NLS-1$
            debugTraceIndent = 0;
            return ""; //$NON-NLS-1$
        }
        if ((temp.startsWith("<=") || temp.startsWith("<-")) //$NON-NLS-1$ //$NON-NLS-2$
                && debugTraceIndent > 1) {
            debugTraceIndent--;
        }

        StringBuffer sb = new StringBuffer();
        sb.append(new Date().toString() + ":: "); //$NON-NLS-1$

        for (int i = 0; i < debugTraceIndent; i++) {
            sb.append("  "); //$NON-NLS-1$
        }

        System.out.println(sb.toString() + temp);

        if (temp.startsWith("==>") || temp.startsWith("=>") //$NON-NLS-1$ //$NON-NLS-2$
                || temp.startsWith("->") || temp.startsWith("-->")) { //$NON-NLS-1$ //$NON-NLS-2$
            debugTraceIndent++;
        }

        // }
        return ""; //$NON-NLS-1$

    }

    /**
     * Returns a Primitive Type for an xsd type
     * 
     * @param xsdType
     * @return
     */
    public static Type getPrimitiveType(ImportTransformationData data,
            String xsdType) {
        Type element = null;
        String searchBomElement = XSDUtil.getBOMPrimitiveType(xsdType);
        element = PrimitivesUtil.getStandardPrimitiveTypeByName(
                data.getDiagramResource().getResourceSet(),
                searchBomElement);

        return element;
    }

    /**
     * Returns an Object Primitive Type
     * 
     * @return
     */
    public static Type getObjectPrimitiveType(ImportTransformationData data) {
        Type element = null;
        String searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME;
        element = PrimitivesUtil.getStandardPrimitiveTypeByName(
                data.getDiagramResource().getResourceSet(),
                searchBomElement);

        return element;
    }

    /**
     * XPD-6842: Should set undefined restrictions on text/string type if
     * nothing is specified in the schema (instead of defaulting it some value
     * say 50 as is the case with process types). Should come here only for
     * text/string types (Should not come for integer/decimal types)
     * 
     * @param namedElement
     */
    public static void setUndefinedRestrictions(NamedElement namedElement) {

        if (namedElement instanceof PrimitiveType) {

            PrimitiveType tmpPrim = (PrimitiveType) namedElement;
            PrimitiveType basePrimitiveType =
                    PrimitivesUtil.getBasePrimitiveType(tmpPrim);
            setUndefinedRestrictions(namedElement, basePrimitiveType);
        } else if (namedElement instanceof Property) {

            Property tmpProperty = (Property) namedElement;
            if (tmpProperty.getType() instanceof PrimitiveType) {

                PrimitiveType basePrimitiveType =
                        (PrimitiveType) tmpProperty.getType();
                setUndefinedRestrictions(tmpProperty, basePrimitiveType);
            }
        }
    }

    /**
     * XPD-6842: Should set undefined restrictions on text/string type if
     * nothing is specified in the schema (instead of defaulting it some value
     * say 50 as is the case with process types). Should come here only for
     * text/string types (Should not come for integer/decimal types)
     * 
     * @param namedElement
     */
    public static void setUndefinedRestrictions(NamedElement namedElement,
            DataType basePrimitiveType) {

        if (namedElement instanceof PrimitiveType) {

            PrimitiveType tmpPrim = (PrimitiveType) namedElement;
            if (basePrimitiveType.getName()
                    .equals(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME)) {

                PrimitivesUtil.setFacetPropertyValue(tmpPrim,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH,
                        -1);
            }

            /**
             * XPD-6842: Should set undefined restrictions only on text/string
             * type, not for integer/decimal types)
             */

            // else if (basePrimitiveType.getName()
            // .equals(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {
            // PrimitivesUtil.setFacetPropertyValue(tmpPrim,
            // PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH,
            // -1);
            // } else if (basePrimitiveType.getName()
            // .equals(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
            // PrimitivesUtil.setFacetPropertyValue(tmpPrim,
            // PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH,
            // -1);
            // PrimitivesUtil.setFacetPropertyValue(tmpPrim,
            // PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES,
            // -1);
            // }
        } else if (namedElement instanceof Property) {

            Property tmpProperty = (Property) namedElement;
            if (tmpProperty.getType() != null && tmpProperty.getType().getName()
                    .equals(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME)) {

                if (tmpProperty.getType() instanceof PrimitiveType) {

                    if (basePrimitiveType instanceof PrimitiveType) {

                        PrimitivesUtil.setFacetPropertyValue(
                                (PrimitiveType) basePrimitiveType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH,
                                -1,
                                tmpProperty);
                    } else if (basePrimitiveType instanceof Enumeration) {
                        PrimitivesUtil.setFacetPropertyValue(
                                (Enumeration) basePrimitiveType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH,
                                -1,
                                tmpProperty);
                    }
                } else if (tmpProperty.getType() instanceof Enumeration) {
                    // Enumeration baseEnumeration =
                    // (Enumeration) tmpProperty.getType();
                    /*
                     * PrimitivesUtil.setFacetPropertyValue(baseEnumeration,
                     * PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH, -1,
                     * tmpProperty);
                     */
                }
            }
            /**
             * XPD-6842: Should set undefined restrictions only on text/string
             * type, not for integer/decimal types)
             */
            // else if (tmpProperty.getType() != null
            // && tmpProperty.getType().getName()
            // .equals(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {
            // if (tmpProperty.getType() instanceof PrimitiveType) {
            // if (basePrimitiveType instanceof PrimitiveType) {
            // PrimitivesUtil
            // .setFacetPropertyValue((PrimitiveType) basePrimitiveType,
            // PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH,
            // -1,
            // tmpProperty);
            // } else if (basePrimitiveType instanceof Enumeration) {
            // PrimitivesUtil
            // .setFacetPropertyValue((Enumeration) basePrimitiveType,
            // PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH,
            // -1,
            // tmpProperty);
            // }
            // } else if (tmpProperty.getType() instanceof Enumeration) {
            // // Enumeration baseEnumeration =
            // // (Enumeration) tmpProperty.getType();
            // /*
            // * PrimitivesUtil.setFacetPropertyValue(baseEnumeration,
            // * PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH, -1,
            // * tmpProperty);
            // */
            // }
            // } else if (tmpProperty.getType() != null
            // && tmpProperty.getType().getName()
            // .equals(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
            // if (tmpProperty.getType() instanceof PrimitiveType) {
            // if (basePrimitiveType instanceof PrimitiveType) {
            // PrimitivesUtil
            // .setFacetPropertyValue((PrimitiveType) basePrimitiveType,
            // PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH,
            // -1,
            // tmpProperty);
            // PrimitivesUtil
            // .setFacetPropertyValue((PrimitiveType) basePrimitiveType,
            // PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES,
            // -1,
            // tmpProperty);
            // } else if (basePrimitiveType instanceof Enumeration) {
            // PrimitivesUtil
            // .setFacetPropertyValue((Enumeration) basePrimitiveType,
            // PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH,
            // -1,
            // tmpProperty);
            // PrimitivesUtil
            // .setFacetPropertyValue((Enumeration) basePrimitiveType,
            // PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES,
            // -1,
            // tmpProperty);
            // }
            // } else if (tmpProperty.getType() instanceof Enumeration) {
            // // Enumeration baseEnumeration =
            // // (Enumeration) tmpProperty.getType();
            // /*
            // * PrimitivesUtil.setFacetPropertyValue(baseEnumeration,
            // * PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH, -1,
            // * tmpProperty);
            // * PrimitivesUtil.setFacetPropertyValue(baseEnumeration,
            // * PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES, -1,
            // * tmpProperty);
            // */
            // }
            // }
        }
    }

    /**
     * Needs this method to create a uml2.uml.PrimitiveType due to the conflict
     * of ocl.uml.PrimitiveTypes in target platform!
     * 
     * @return
     */
    public static Type createPrimitiveType() {
        PrimitiveType primitiveType =
                UMLFactory.eINSTANCE.createPrimitiveType();
        return primitiveType;
    }

    /**
     * 
     * XPD-4938: gets the unique primitive type name in case of name clash
     * 
     * @param origName
     * @param primitiveTypesList
     * @param object
     * @return
     */
    public static String _getUniquePrimitiveTypeName(String origName,
            List<?> primitiveTypesList) {

        int count = 0;

        if (null != origName && primitiveTypesList.size() > 0) {

            for (Object pt : primitiveTypesList) {

                if (pt instanceof PrimitiveType) {

                    PrimitiveType primType = (PrimitiveType) pt;
                    String primTypeName = primType.getName();

                    if (origName.equals(primTypeName)) {

                        origName = origName + "Type"; //$NON-NLS-1$
                        count = getPrimitiveTypeNameIndex(primitiveTypesList,
                                origName,
                                count);
                        break;
                    }
                }
            }
        }

        if (count == 0) {
            return origName;
        } else {
            return origName + count;
        }

    }

    /**
     * gets the incremented index count for the given name in case of name clash
     * 
     * @param primitiveTypesList
     * @param startName
     * @param count
     * @return
     */
    private static int getPrimitiveTypeNameIndex(List<?> primitiveTypesList,
            String startName, int count) {

        int newCount = count;

        for (Object obj : primitiveTypesList) {

            if (obj instanceof PrimitiveType) {

                PrimitiveType primType = (PrimitiveType) obj;
                String primTypeName = primType.getName();

                if (primTypeName.equals(startName)) {

                    newCount = getPrimitiveTypeNameIndex(primitiveTypesList,
                            startName,
                            count + 1);
                }
            }
        }

        return newCount;
    }

    /**
     * @param tmpCls
     * @param isContainer
     * @return
     */
    public static boolean setAnonStereotypeValue(Classifier tmpCls,
            Boolean isContainer) {
        Stereotype appliedStereotype = null;
        if (tmpCls instanceof Class) {
            appliedStereotype = applyAndGetAppliedStereotype(tmpCls.getModel(),
                    tmpCls,
                    XsdStereotypeUtils.XSD_BASED_CLASS);
        } else if (tmpCls instanceof DataType) {
            appliedStereotype = applyAndGetAppliedStereotype(tmpCls.getModel(),
                    tmpCls,
                    XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE);
        }
        if (appliedStereotype != null) {
            if (isContainer) {
                setStereotypeValue(tmpCls,
                        appliedStereotype,
                        XsdStereotypeUtils.XSD_PROPERTY_IS_ANON_CONTAINER,
                        true);
            } else {
                setStereotypeValue(tmpCls,
                        appliedStereotype,
                        XsdStereotypeUtils.XSD_PROPERTY_IS_ANON_TYPE,
                        true);
            }
        }
        return true;
    }

    /**
     * @param primitiveType
     * @param simpleTypeName
     * @param simpleTypeId
     * @param simpleTypeFinal
     * @param simpleTypeRestrictionId
     * @param simpleTypeRestrictionBase
     * @return
     */
    public static boolean applyPrimitiveStereotypeValues(Element primitiveType,
            String simpleTypeName, String simpleTypeId, List simpleTypeFinal,
            String simpleTypeRestrictionId, String simpleTypeRestrictionBase) {
        Stereotype appliedStereotype =
                applyAndGetAppliedStereotype(primitiveType.getModel(),
                        primitiveType,
                        XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE);
        if (appliedStereotype != null) {
            setStereotypeValue(primitiveType,
                    appliedStereotype,
                    XsdStereotypeUtils.XSD_PROPERTY_SIMPLETYPE_ID,
                    simpleTypeId);
            setStereotypeValue(primitiveType,
                    appliedStereotype,
                    XsdStereotypeUtils.XSD_PROPERTY_SIMPLETYPE_NAME,
                    simpleTypeName);
            String strFinal = getFinalStrValue(simpleTypeFinal);
            if (strFinal.length() > 0) {
                setStereotypeValue(primitiveType,
                        appliedStereotype,
                        XsdStereotypeUtils.XSD_PROPERTY_SIMPLETYPE_FINAL,
                        strFinal);
            }
            setStereotypeValue(primitiveType,
                    appliedStereotype,
                    XsdStereotypeUtils.XSD_PROPERTY_RESTRICTION_ID,
                    simpleTypeRestrictionId);
            if (simpleTypeRestrictionBase != null
                    && simpleTypeRestrictionBase.charAt(0) == ':') {
                simpleTypeRestrictionBase =
                        simpleTypeRestrictionBase.replaceFirst(":", ""); //$NON-NLS-1$ //$NON-NLS-2$
            }
            setStereotypeValue(primitiveType,
                    appliedStereotype,
                    XsdStereotypeUtils.XSD_PROPERTY_RESTRICTION_BASE,
                    getLocalPart(simpleTypeRestrictionBase));
        }
        return true;
    }

    /**
     * Remove the last element in the given list.
     * 
     * @param list
     * @return
     */
    public static boolean removeLast(List<?> list) {
        list.remove(list.size() - 1);
        return true;
    }

    /**
     * @param name
     * @param tmpClass
     * @param explicitGroupTree
     * @param objectPosition
     * @return
     */
    public static boolean setExplicitGroupTree(String name, Class tmpClass,
            String explicitGroupTree, String objectPosition,
            Object parentSequence) {
        if (name != null) {
            EList<Property> ownedAttributes = tmpClass.getOwnedAttributes();
            String tmpNameWithoutElem = ""; //$NON-NLS-1$
            if (name.indexOf(ELEM_REPLACED_NAME) != -1) {
                tmpNameWithoutElem =
                        name.substring(0, name.indexOf(ELEM_REPLACED_NAME));
            }
            for (Property property : ownedAttributes) {
                Stereotype appliedStereotype =
                        applyAndGetAppliedStereotype(tmpClass.getModel(),
                                property,
                                XsdStereotypeUtils.XSD_BASED_PROPERTY);
                String realPropertyName =
                        (String) property.getValue(appliedStereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_NAME);
                boolean isAttribute =
                        (Boolean) property.getValue(appliedStereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_IS_ATTRIBUTE);
                if (!isAttribute && (property.getName().equals(name)
                        || property.getName()
                                .equals(name + COMPLEX_TYPE_EXTENSION)
                        || realPropertyName.equals(name)
                        || property.getName().equals(tmpNameWithoutElem)
                        || (realPropertyName != null && realPropertyName
                                .equals(tmpNameWithoutElem)))) {
                    if (appliedStereotype != null) {
                        Object value = property.getValue(appliedStereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_EXPLICIT_GROUP_HIERARCHY);
                        if (value == null) {
                            setStereotypeValue(property,
                                    appliedStereotype,
                                    XsdStereotypeUtils.XSD_PROPERTY_EXPLICIT_GROUP_HIERARCHY,
                                    getExplicityGroupHierarchy(explicitGroupTree
                                            + ",=" + objectPosition)); //$NON-NLS-1$
                            if (parentSequence != null
                                    && parentSequence instanceof XSDSequenceWrapper) {
                                setStereotypeValue(property,
                                        appliedStereotype,
                                        XsdStereotypeUtils.XSD_PROPERTY_PARENT_SEQUENCE,
                                        ((XSDSequenceWrapper) parentSequence)
                                                .getEObject());
                                int lower = XSDUtil
                                        .calcUMLLowerBoundFromParentSequences(
                                                property);
                                boolean containedInChoice =
                                        XSDUtil.isContainedInChoice(property);
                                if (containedInChoice) {
                                    property.setLower(0);
                                } else {
                                    property.setLower(lower);
                                }
                                int upper = XSDUtil
                                        .calcUMLUpperBoundFromParentSequences(
                                                property);
                                if (upper == -1) {
                                    property.setUpper(
                                            LiteralUnlimitedNatural.UNLIMITED);
                                } else {
                                    property.setUpper(upper);
                                }
                            }

                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Generate the stereotype information for sequence and choice hierarchy for
     * mapper to be able to work out each properties choice/sequence position.
     * 
     * @param explicitGroupTree
     * @return
     */
    private static String getExplicityGroupHierarchy(String explicitGroupTree) {
        String explicitGroupHierarchy = ""; //$NON-NLS-1$
        explicitGroupTree = explicitGroupTree + "|"; //$NON-NLS-1$
        String[] levelInformation = explicitGroupTree.split("\\|"); //$NON-NLS-1$
        int allIndex = 0;
        int sequenceIndex = 0;
        int choiceIndex = 0;
        for (String currentLevel : levelInformation) {
            String[] explicitGroups = currentLevel.split(","); //$NON-NLS-1$
            for (String currentExplicitGroup : explicitGroups) {
                if (currentExplicitGroup.equals("A")) { //$NON-NLS-1$
                    allIndex++;
                }
                if (currentExplicitGroup.equals("S")) { //$NON-NLS-1$
                    sequenceIndex++;
                }
                if (currentExplicitGroup.equals("C")) { //$NON-NLS-1$
                    choiceIndex++;
                }
                if (currentExplicitGroup.equals("~")) { //$NON-NLS-1$
                    explicitGroupHierarchy = explicitGroupHierarchy.substring(0,
                            explicitGroupHierarchy.length() - 3);
                }
            }
            int lastIdxAll = currentLevel.lastIndexOf("A"); //$NON-NLS-1$
            int lastIdxSequence = currentLevel.lastIndexOf("S"); //$NON-NLS-1$
            int lastIdxChoice = currentLevel.lastIndexOf("C"); //$NON-NLS-1$

            if (lastIdxAll > lastIdxSequence && lastIdxAll > lastIdxChoice) {
                explicitGroupHierarchy =
                        explicitGroupHierarchy + "A" + allIndex + ":"; //$NON-NLS-1$ //$NON-NLS-2$
            } else {
                if (lastIdxSequence > lastIdxChoice) {
                    explicitGroupHierarchy =
                            explicitGroupHierarchy + "S" + sequenceIndex + ":"; //$NON-NLS-1$ //$NON-NLS-2$
                } else if (lastIdxChoice > lastIdxSequence) {
                    explicitGroupHierarchy =
                            explicitGroupHierarchy + "C" + choiceIndex + ":"; //$NON-NLS-1$ //$NON-NLS-2$
                }
            }
        }

        return explicitGroupHierarchy.substring(0,
                explicitGroupHierarchy.length() - 1);
    }

    /**
     * @param tempClass
     * @param complexType
     * @param complexContent
     * @param name
     * @param id
     * @param complexTypeMixed
     * @param complexContentMixed
     * @param block
     * @param tempFinal
     * @param tempAbstract
     * @param isComplexType
     * @param isGroup
     * @param isAttributeGroup
     * @return
     */
    public static boolean applyClassStereotypeValues(Element tempClass,
            Object complexType, Object complexContent, String name, String id,
            String complexTypeMixed, String complexContentMixed, List block,
            List tempFinal, String tempAbstract, Boolean isComplexType,
            Boolean isGroup, Boolean isAttributeGroup) {

        Stereotype appliedStereotype =
                applyAndGetAppliedStereotype(tempClass.getModel(),
                        tempClass,
                        XsdStereotypeUtils.XSD_BASED_CLASS);
        if (appliedStereotype != null) {
            setStereotypeValue(tempClass,
                    appliedStereotype,
                    XsdStereotypeUtils.XSD_PROPERTY_NAME,
                    name);
            setStereotypeValue(tempClass,
                    appliedStereotype,
                    XsdStereotypeUtils.XSD_PROPERTY_ID,
                    id);
            setStereotypeValue(tempClass,
                    appliedStereotype,
                    XsdStereotypeUtils.XSD_PROPERTY_IS_COMPLEX_TYPE,
                    isComplexType);
            setStereotypeValue(tempClass,
                    appliedStereotype,
                    XsdStereotypeUtils.XSD_PROPERTY_IS_GROUP,
                    isGroup);
            setStereotypeValue(tempClass,
                    appliedStereotype,
                    XsdStereotypeUtils.XSD_PROPERTY_IS_ATTRIBUTE_GROUP,
                    isAttributeGroup);
            if (isComplexType.booleanValue() == true) {
                /*
                 * XPD-4386: if complex type has complex content then check if
                 * mixed is set on complex type. if so complex type will take
                 * its own mixed value, else complex type will take mixed value
                 * from complex content
                 */
                if (null != complexType && null != complexContent) {

                    boolean mixedSetOnComplexType = isMixedSet(complexType);
                    String mixedValueToSet = null;

                    if (mixedSetOnComplexType) {
                        mixedValueToSet = complexTypeMixed;
                    } else {
                        mixedValueToSet = complexContentMixed;
                    }
                    setStereotypeValue(tempClass,
                            appliedStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_MIXED,
                            mixedValueToSet);
                } else {
                    setStereotypeValue(tempClass,
                            appliedStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_MIXED,
                            complexTypeMixed);
                }
                String strBlock = getBlockStrValue(block);
                if (strBlock.length() > 0) {
                    setStereotypeValue(tempClass,
                            appliedStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_BLOCK,
                            strBlock);
                }
                String strFinal = getFinalStrValue(tempFinal);
                if (strFinal.length() > 0) {
                    setStereotypeValue(tempClass,
                            appliedStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_FINAL,
                            strFinal);
                }
                setStereotypeValue(tempClass,
                        appliedStereotype,
                        XsdStereotypeUtils.XSD_PROPERTY_ABSTRACT,
                        tempAbstract);
            }
        }
        return true;
    }

    /**
     * 
     * @param complexTypeOrComplexContent
     * @return true if mixed attribute is set at all, false otherwise
     */
    private static boolean isMixedSet(Object complexTypeOrComplexContent) {

        if (complexTypeOrComplexContent instanceof DynamicEObjectImpl) {
            DynamicEObjectImpl eobj =
                    (DynamicEObjectImpl) complexTypeOrComplexContent;
            for (EStructuralFeature feat : eobj.eClass()
                    .getEAllStructuralFeatures()) {
                if (feat instanceof EAttribute
                        && feat.getName().equals("mixed")) { //$NON-NLS-1$
                    boolean mixedIsSet = eobj.eIsSet(feat);
                    /*
                     * whether true or false checks if mixed attribute is set on
                     * a complex type and returns true if it is set
                     */
                    if (mixedIsSet) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param property
     */
    public static void setAttributeStereotype(Property property) {
        Stereotype appliedStereotype =
                applyAndGetAppliedStereotype(property.getModel(),
                        property,
                        XsdStereotypeUtils.XSD_BASED_PROPERTY);
        setStereotypeValue(property,
                appliedStereotype,
                XsdStereotypeUtils.XSD_PROPERTY_IS_ATTRIBUTE,
                true);
    }

    /**
     * @param model
     * @param tempClass
     * @param name
     * @param element
     * @param minOccurs
     * @param maxOccurs
     * @return
     */
    public static Property createProperty(ImportTransformationData data,
            Model model, Class tempClass, String name, Type element,
            String form, String defaultVal, String id, String fixed, String ref,
            String use, String type, String minOccurs, Object maxOccurs,
            String enumLiterals, String nillable, List namespaces,
            String processContents, List block, Boolean isSimpleType) {
        Property property = null;
        boolean isObjectType = false;
        if (tempClass != null && model != null) {
            if (ref == null && type == null && element == null) {
                type = "anyType"; //$NON-NLS-1$
                if (name.contains("anyAttribute")) { //$NON-NLS-1$
                    type = "anyAttribute"; //$NON-NLS-1$
                    element = getObjectPrimitiveType(data);
                } else {
                    element = getPrimitiveType(data, type);
                }
                isObjectType = true;
            } else if (element != null && element.getName()
                    .equals(PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME)) {
                if (type != null && type.indexOf(":") != -1) { //$NON-NLS-1$
                    type = type.substring(type.indexOf(":") + 1); //$NON-NLS-1$
                }
                if (name.contains("anyAttribute")) { //$NON-NLS-1$
                    type = "anyAttribute"; //$NON-NLS-1$
                }
                if (name.equals("any") || name.contains("any*")) { //$NON-NLS-1$ //$NON-NLS-2$
                    type = "any"; //$NON-NLS-1$
                }
                isObjectType = true;
            }

            String namespaceStr = null;
            if (namespaces != null) {
                namespaceStr = ""; //$NON-NLS-1$
                for (Object tmpNamespace : namespaces) {
                    if (tmpNamespace != null) {
                        namespaceStr += tmpNamespace.toString();
                        namespaceStr += " "; //$NON-NLS-1$
                    }
                }
                namespaceStr = namespaceStr.trim();
            }
            boolean propertyNameExists = isPropertyNameExist(tempClass, name);

            property = tempClass.createOwnedAttribute(name, null);
            setMinMaxOccurs(property, minOccurs, maxOccurs);

            /*
             * XPD-5089 we now validate that all owned attributes have an
             * aggregation attribute. So we MUST ensure that all generated
             * properties have a aggregation set.
             */
            property.setAggregation(AggregationKind.COMPOSITE_LITERAL);

            if (element != null && element.getName() != null) {
                property.setType(element);
                setUndefinedRestrictions(property);
                if (isObjectType) {
                    if (type != null && type.equalsIgnoreCase("anyAttribute")) { //$NON-NLS-1$
                        EnumerationLiteral createEnumerationLiteral =
                                UMLFactory.eINSTANCE.createEnumerationLiteral();
                        createEnumerationLiteral.setName("xsdAnyAttribute"); //$NON-NLS-1$
                        PrimitivesUtil.setFacetPropertyValue(
                                (PrimitiveType) element,
                                "objectSubType", //$NON-NLS-1$
                                createEnumerationLiteral,
                                property);
                        setMinMaxOccurs(property, "0", "1"); //$NON-NLS-1$ //$NON-NLS-2$
                    } else if (type != null
                            && type.equalsIgnoreCase("anytype")) { //$NON-NLS-1$
                        EnumerationLiteral createEnumerationLiteral =
                                UMLFactory.eINSTANCE.createEnumerationLiteral();
                        createEnumerationLiteral.setName("xsdAnyType"); //$NON-NLS-1$
                        PrimitivesUtil.setFacetPropertyValue(
                                (PrimitiveType) element,
                                "objectSubType", //$NON-NLS-1$
                                createEnumerationLiteral,
                                property);
                    } else if (type != null
                            && type.equalsIgnoreCase("anysimpletype")) { //$NON-NLS-1$
                        EnumerationLiteral createEnumerationLiteral =
                                UMLFactory.eINSTANCE.createEnumerationLiteral();
                        createEnumerationLiteral.setName("xsdAnySimpleType"); //$NON-NLS-1$
                        PrimitivesUtil.setFacetPropertyValue(
                                (PrimitiveType) element,
                                "objectSubType", //$NON-NLS-1$
                                createEnumerationLiteral,
                                property);
                    } else if (type != null && type.equalsIgnoreCase("any")) { //$NON-NLS-1$
                        EnumerationLiteral createEnumerationLiteral =
                                UMLFactory.eINSTANCE.createEnumerationLiteral();
                        createEnumerationLiteral.setName("xsdAny"); //$NON-NLS-1$
                        PrimitivesUtil.setFacetPropertyValue(
                                (PrimitiveType) element,
                                "objectSubType", //$NON-NLS-1$
                                createEnumerationLiteral,
                                property);
                    }
                    if (type == null) {
                        type = "anyType"; //$NON-NLS-1$
                        EnumerationLiteral createEnumerationLiteral =
                                UMLFactory.eINSTANCE.createEnumerationLiteral();
                        createEnumerationLiteral.setName("xsdAnyType"); //$NON-NLS-1$
                        PrimitivesUtil.setFacetPropertyValue(
                                (PrimitiveType) element,
                                "objectSubType", //$NON-NLS-1$
                                createEnumerationLiteral,
                                property);
                    }
                }
            }

            Stereotype appliedStereotype = applyAndGetAppliedStereotype(model,
                    property,
                    XsdStereotypeUtils.XSD_BASED_PROPERTY);
            if (appliedStereotype != null) {
                if (ref != null && ref.charAt(0) == ':') {
                    ref = ref.replaceFirst(":", ""); //$NON-NLS-1$ //$NON-NLS-2$
                }
                if (ref != null && ref.trim().length() > 0) {
                    int startIndex = ref.indexOf(":"); //$NON-NLS-1$
                    if (startIndex == -1) {
                        startIndex = 0;
                    } else {
                        startIndex++;
                    }
                    property.setName(ref.substring(startIndex));
                }
                if (type != null && type.charAt(0) == ':') {
                    type = type.replaceFirst(":", ""); //$NON-NLS-1$ //$NON-NLS-2$
                }

                setStereotypeValue(property,
                        appliedStereotype,
                        XsdStereotypeUtils.XSD_PROPERTY_NAME,
                        name);
                setStereotypeValue(property,
                        appliedStereotype,
                        XsdStereotypeUtils.XSD_PROPERTY_FORM,
                        form);
                setStereotypeValue(property,
                        appliedStereotype,
                        XsdStereotypeUtils.XSD_PROPERTY_DEFAULT,
                        defaultVal);
                if (defaultVal != null) {
                    setUMLDefaults(property, String.valueOf(defaultVal));
                }
                setStereotypeValue(property,
                        appliedStereotype,
                        XsdStereotypeUtils.XSD_PROPERTY_ID,
                        id);
                setStereotypeValue(property,
                        appliedStereotype,
                        XsdStereotypeUtils.XSD_PROPERTY_FIXED,
                        fixed);
                setStereotypeValue(property,
                        appliedStereotype,
                        XsdStereotypeUtils.XSD_PROPERTY_REF,
                        ref);
                setStereotypeValue(property,
                        appliedStereotype,
                        XsdStereotypeUtils.XSD_PROPERTY_USE,
                        use);
                setStereotypeValue(property,
                        appliedStereotype,
                        XsdStereotypeUtils.XSD_PROPERTY_NILLABLE,
                        nillable);
                String strBlock = getBlockStrValue(block);
                if (strBlock.length() > 0) {
                    setStereotypeValue(property,
                            appliedStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_BLOCK,
                            strBlock);
                }
                if (namespaceStr != null) {
                    setStereotypeValue(property,
                            appliedStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_NAMESPACE,
                            namespaceStr);
                }
                setStereotypeValue(property,
                        appliedStereotype,
                        XsdStereotypeUtils.XSD_PROPERTY_PROCESS_CONTENTS,
                        processContents);

                if (enumLiterals != null && !enumLiterals.equals("null") //$NON-NLS-1$
                        && !enumLiterals.equals("[]") //$NON-NLS-1$
                        && enumLiterals.trim().length() > 0) {
                    enumLiterals = enumLiterals.replace("[", ""); //$NON-NLS-1$ //$NON-NLS-2$
                    enumLiterals = enumLiterals.replace("]", ""); //$NON-NLS-1$ //$NON-NLS-2$
                    setStereotypeValue(property,
                            appliedStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_ENUM_LITERALS,
                            enumLiterals);
                    Enumeration typeEnumeration =
                            UMLFactory.eINSTANCE.createEnumeration();
                    typeEnumeration.setName(
                            property.getName() + COMPLEX_TYPE_EXTENSION);
                    typeEnumeration.setPackage(property.getNearestPackage());
                    applyPrimitiveStereotypeValues(typeEnumeration,
                            property.getName() + COMPLEX_TYPE_EXTENSION,
                            "", //$NON-NLS-1$
                            new ArrayList(),
                            "", //$NON-NLS-1$
                            type);
                    setAnonStereotypeValue(typeEnumeration, false);
                    setUniqueId(data, typeEnumeration);
                    String[] splitEnums = enumLiterals.split(","); //$NON-NLS-1$
                    ArrayList<String> enumLitIds = new ArrayList<String>();
                    ArrayList<Object> enumLitValues = new ArrayList<Object>();
                    for (String tmpEnumLit : splitEnums) {
                        tmpEnumLit = tmpEnumLit.trim();
                        enumLitIds.add(tmpEnumLit);
                        enumLitValues.add(tmpEnumLit);
                        /*
                         * EnumerationLiteral createEnumerationLiteral =
                         * UMLFactory.eINSTANCE.createEnumerationLiteral();
                         * createEnumerationLiteral.setName(tmpEnumLit);
                         * typeEnumeration
                         * .getOwnedLiterals().add(createEnumerationLiteral);
                         */
                    }
                    createEnumerationLiteral(typeEnumeration,
                            enumLitIds,
                            enumLitValues);
                    // set type somewhere
                    property.setType(typeEnumeration);

                    // set isAnonType to true so we can distinguish this new
                    // enum type
                    Stereotype appliedXSDBasedEnumerationStereotype =
                            applyAndGetAppliedStereotype(model,
                                    typeEnumeration,
                                    XsdStereotypeUtils.XSD_BASED_ENUMERATION);
                    setStereotypeValue(typeEnumeration,
                            appliedXSDBasedEnumerationStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_IS_ANON_TYPE,
                            true);

                }

                Property existingValueProp = null;
                for (Property tmpProp : tempClass.getAllAttributes()) {
                    String tmpName = tmpProp.getName();
                    if (tmpName != null
                            && tmpName.toLowerCase().equals("value")) { //$NON-NLS-1$
                        existingValueProp = tmpProp;
                        break;
                    }
                }

                if ((name != null) && ((name.equals("_contents_ext_") //$NON-NLS-1$
                        || name.equals("_contents_res_")))) { //$NON-NLS-1$
                    setMinMaxOccurs(property, "1", "1"); //$NON-NLS-1$ //$NON-NLS-2$
                    property.setName("value"); //$NON-NLS-1$
                    if (name.equals("_contents_ext_")) { //$NON-NLS-1$
                        setStereotypeValue(property,
                                appliedStereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_IS_SIMPLE_CONTENT_EXTENSION,
                                true);
                    } else {
                        setStereotypeValue(property,
                                appliedStereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_IS_SIMPLE_CONTENT_RESTRICTION,
                                true);
                    }
                    if (existingValueProp != null) {
                        int i = 1;
                        while (isPropertyNameExist(tempClass, "value" + i)) { //$NON-NLS-1$
                            i++;
                        }
                        existingValueProp.setName("value" + i); //$NON-NLS-1$
                    }
                    setStereotypeValue(property,
                            appliedStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_NAME,
                            property.getName());
                } else if (name != null) {
                    if (name.toLowerCase().equals("value") //$NON-NLS-1$
                            && existingValueProp != null
                            && propertyNameExists) {
                        int i = 1;
                        while (isPropertyNameExist(tempClass, "value" + i)) { //$NON-NLS-1$
                            i++;
                        }
                        property.setName("value" + i); //$NON-NLS-1$
                        setStereotypeValue(property,
                                appliedStereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_NAME,
                                property.getName());
                    } else if (propertyNameExists) {
                        int i = 1;
                        while (isPropertyNameExist(tempClass, name + i)) {
                            i++;
                        }
                        property.setName(name + i);
                        setStereotypeValue(property,
                                appliedStereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_NAME,
                                name);
                    }
                }

                if (type != null) {
                    setStereotypeValue(property,
                            appliedStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_TYPE,
                            getLocalPart(type));
                    setOtherStereotypeValues(property,
                            type,
                            appliedStereotype,
                            property.getType(),
                            isSimpleType);
                }
            }
        }
        setUniqueId(data, property);

        return property;
    }

    /**
     * @param originalName
     * @return
     */
    private static String stripIllegalChars(String originalName) {
        if (originalName != null) {
            originalName = originalName.replace("$", ""); //$NON-NLS-1$ //$NON-NLS-2$
            originalName = originalName.replace("£", ""); //$NON-NLS-1$ //$NON-NLS-2$
            originalName = originalName.replace("%", ""); //$NON-NLS-1$ //$NON-NLS-2$
            originalName = originalName.replace("&", ""); //$NON-NLS-1$ //$NON-NLS-2$
            originalName = originalName.replace("¬", ""); //$NON-NLS-1$ //$NON-NLS-2$
            originalName = originalName.replace("`", ""); //$NON-NLS-1$ //$NON-NLS-2$
            originalName = originalName.replace("*", ""); //$NON-NLS-1$ //$NON-NLS-2$
        }
        return originalName;
    }

    /**
     * Get unique id of the given named element.
     * 
     * @param namedElement
     * @return
     */
    private static String getUniqueIDName(NamedElement namedElement) {
        String name = namedElement.getName();
        if (namedElement.eContainer() instanceof NamedElement) {
            name = ((NamedElement) namedElement.eContainer()).getName() + "*" //$NON-NLS-1$
                    + name;
            name = stripIllegalChars(name);
        }
        return name;
    }

    /**
     * Sets identifier of the object. The object have to be contained in the XML
     * resource to correctly set the ID. We need to check that an ID is not
     * already in use else it will cause duplicate ID problems later on the
     * export stage.
     * 
     * @param object
     *            the object to set ID.
     */

    public static String setUniqueId(ImportTransformationData data,
            Object object) {
        String uniqueId = ""; //$NON-NLS-1$
        if (object instanceof EObject) {
            EObject eObject = (EObject) object;
            if (eObject.eResource() != null
                    && eObject.eResource() instanceof XMLResource
                    && eObject instanceof NamedElement) {
                boolean isSet = false;
                uniqueId = getUniqueIDName((NamedElement) eObject);
                int index = 2;
                while (!isSet) {
                    Object existingId = data.getUniqueIds().get(uniqueId);
                    if (existingId == null) {
                        ((XMLResource) eObject.eResource()).setID(eObject,
                                uniqueId);
                        data.getUniqueIds().put(uniqueId, object);
                        isSet = true;
                    } else {
                        uniqueId = uniqueId + index;
                    }
                    index++;
                }
            }
        }
        return uniqueId;
    }

    /**
     * Check if a Property with the given name exists in the given class.
     * 
     * @param tmpClass
     * @param name
     * @return
     */
    private static boolean isPropertyNameExist(Class tmpClass, String name) {
        for (Property tmpProp : tmpClass.getAllAttributes()) {
            if (tmpProp.getName() != null
                    && tmpProp.getName().equalsIgnoreCase((name))) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param property
     * @param type
     * @param appliedStereotype
     * @param tmpType
     */
    public static void setOtherStereotypeValues(Property property, String type,
            Stereotype appliedStereotype, Element tmpType,
            Boolean isSimpleType) {
        if (type != null && !isSimpleType) {
            int index = type.indexOf(":"); //$NON-NLS-1$
            String xsdType = type.substring(index + 1).trim();

            if (xsdType.equalsIgnoreCase("float")) { //$NON-NLS-1$
                applyPropertyDecimalValues(property,
                        "-3.4028235E38", //$NON-NLS-1$
                        "3.4028235E38", //$NON-NLS-1$
                        false,
                        tmpType);
            } else if (xsdType.equalsIgnoreCase("decimal")) { //$NON-NLS-1$
                applyPropertyDecimalValues(property, null, null, true, tmpType);
            } else if (xsdType.equalsIgnoreCase("double")) { //$NON-NLS-1$
                applyPropertyDecimalValues(property,
                        "-1.7976931348623157E308", //$NON-NLS-1$
                        "1.7976931348623157E308", //$NON-NLS-1$
                        false,
                        tmpType);
            } else if (xsdType.equalsIgnoreCase("int")) { //$NON-NLS-1$
                applyPropertyIntegerValues(property,
                        null,
                        null,
                        false,
                        tmpType);
            } else if (xsdType.equalsIgnoreCase("integer")) { //$NON-NLS-1$
                applyPropertyIntegerValues(property, null, null, true, tmpType);
            } else if (xsdType.equalsIgnoreCase("id")) { //$NON-NLS-1$
                applyPropertyPattern(property, "[\\i-[:]][\\c-[:]]*", tmpType); //$NON-NLS-1$
            } else if (xsdType.equalsIgnoreCase("hexbinary")) { //$NON-NLS-1$
                applyPropertyPattern(property,
                        "([0-9a-fA-F][0-9a-fA-F])*", //$NON-NLS-1$
                        tmpType);
            } else if (xsdType.equalsIgnoreCase("base64Binary")) { //$NON-NLS-1$
                applyPropertyPattern(property,
                        "(([A-Za-z0-9+/] ?[A-Za-z0-9+/] ?[A-Za-z0-9+/] ?[A-Za-z0-9+/] ?)*(([A-Za-z0-9+/] ?[A-Za-z0-9+/] ?[A-Za-z0-9+/] ?[A-Za-z0-9+/])|([A-Za-z0-9+/] ?[A-Za-z0-9+/] ?[AEIMQUYcgkosw048] ?=)|([A-Za-z0-9+/] ?[AQgw] ?= ?=)))?", //$NON-NLS-1$
                        tmpType);
            } else if (xsdType.equalsIgnoreCase("byte")) { //$NON-NLS-1$
                applyPropertyIntegerValues(property,
                        "-128", //$NON-NLS-1$
                        "127", //$NON-NLS-1$
                        false,
                        tmpType);
            } else if (xsdType.equalsIgnoreCase("entities")) { //$NON-NLS-1$
                applyPropertyPattern(property,
                        "[\\i-[:]][\\c-[:]]*( [\\i-[:]][\\c-[:]]*)*", //$NON-NLS-1$
                        tmpType);
                /*
                 * setStereotypeValue(property, appliedStereotype,
                 * XsdStereotypeUtils.XSD_PROPERTY_TYPE, "ENTITY");
                 * setMinMaxOccurs(property, "1", "unbounded"); //$NON-NLS-1$
                 * //$NON-NLS-2$
                 */
            } else if (xsdType.equalsIgnoreCase("entity")) { //$NON-NLS-1$
                applyPropertyPattern(property, "[\\i-[:]][\\c-[:]]*", tmpType); //$NON-NLS-1$
            } else if (xsdType.equalsIgnoreCase("gday")) { //$NON-NLS-1$
                applyPropertyPattern(property,
                        "\\-\\-\\-(0[1-9]|[12][0-9]|3[01])(|Z|[+-](0[0-9]|[1][0-3]):(0[0-9]|[12345][0-9]))", //$NON-NLS-1$
                        tmpType);
            } else if (xsdType.equalsIgnoreCase("gmonth")) { //$NON-NLS-1$
                applyPropertyPattern(property,
                        "\\-\\-(0[1-9]|[1][0-2])(|Z|[+-](0[0-9]|[1][0-3]):(0[0-9]|[12345][0-9]))", //$NON-NLS-1$
                        tmpType);
            } else if (xsdType.equalsIgnoreCase("gmonthday")) { //$NON-NLS-1$
                applyPropertyPattern(property,
                        "\\-\\-(0[1-9]|[1][0-2])\\-(0[1-9]|[12][0-9]|3[01])(|Z|[+-](0[0-9]|[1][0-3]):(0[0-9]|[12345][0-9]))", //$NON-NLS-1$
                        tmpType);
            } else if (xsdType.equalsIgnoreCase("gyear")) { //$NON-NLS-1$
                applyPropertyPattern(property,
                        "(\\-\\d{4,}|\\d{4,})(|Z|[+-](0[0-9]|[1][0-3]):(0[0-9]|[12345][0-9]))", //$NON-NLS-1$
                        tmpType);
            } else if (xsdType.equalsIgnoreCase("gyearmonth")) { //$NON-NLS-1$
                applyPropertyPattern(property,
                        "(\\-\\d{4,}|\\d{4,})(|Z|[+-](0[0-9]|[1][0-3]):(0[0-9]|[12345][0-9]))\\-(0[1-9]|[1][0-2])(|Z|[+-](0[0-9]|[1][0-3]):(0[0-9]|[12345][0-9]))", //$NON-NLS-1$
                        tmpType);
            } else if (xsdType.equalsIgnoreCase("idref")) { //$NON-NLS-1$
                applyPropertyPattern(property, "[\\i-[:]][\\c-[:]]*", tmpType); //$NON-NLS-1$
            } else if (xsdType.equalsIgnoreCase("idrefs")) { //$NON-NLS-1$
                applyPropertyPattern(property,
                        "[\\i-[:]][\\c-[:]]*( [\\i-[:]][\\c-[:]]*)*", //$NON-NLS-1$
                        tmpType);
                /*
                 * (setStereotypeValue(property, appliedStereotype,
                 * XsdStereotypeUtils.XSD_PROPERTY_TYPE, "IDREF");
                 * setMinMaxOccurs(property, "1", "unbounded"); //$NON-NLS-1$
                 * //$NON-NLS-2$
                 */
            } else if (xsdType.equalsIgnoreCase("language")) { //$NON-NLS-1$
                applyPropertyPattern(property,
                        "([a-zA-Z]{2}|[iI]-[a-zA-Z]+|[xX]-[a-zA-Z]{1,8})(-[a-zA-Z]{1,8})*", //$NON-NLS-1$
                        tmpType);
            } else if (xsdType.equalsIgnoreCase("long")) { //$NON-NLS-1$
                applyPropertyIntegerValues(property,
                        "-9223372036854775808", //$NON-NLS-1$
                        "9223372036854775807", //$NON-NLS-1$
                        true,
                        tmpType);
            } else if (xsdType.equalsIgnoreCase("name")) { //$NON-NLS-1$
                applyPropertyPattern(property, "\\i\\c*", tmpType); //$NON-NLS-1$
            } else if (xsdType.equalsIgnoreCase("ncname")) { //$NON-NLS-1$
                applyPropertyPattern(property, "[\\i-[:]][\\c-[:]]*", tmpType); //$NON-NLS-1$
            } else if (xsdType.equalsIgnoreCase("negativeinteger")) { //$NON-NLS-1$
                applyPropertyIntegerValues(property, null, "-1", true, tmpType); //$NON-NLS-1$
            } else if (xsdType.equalsIgnoreCase("nmtoken")) { //$NON-NLS-1$
                applyPropertyPattern(property, "\\c+", tmpType); //$NON-NLS-1$
            } else if (xsdType.equalsIgnoreCase("nmtokens")) { //$NON-NLS-1$
                applyPropertyPattern(property, "\\c+( \\c+)*", tmpType); //$NON-NLS-1$
                /*
                 * setStereotypeValue(property, appliedStereotype,
                 * XsdStereotypeUtils.XSD_PROPERTY_TYPE, "NMTOKEN");
                 * setMinMaxOccurs(property, "1", "unbounded"); //$NON-NLS-1$
                 * //$NON-NLS-2$
                 */
            } else if (xsdType.equalsIgnoreCase("nonnegativeinteger")) { //$NON-NLS-1$
                applyPropertyIntegerValues(property, "0", null, true, tmpType); //$NON-NLS-1$ //$NON-NLS-2$
            } else if (xsdType.equalsIgnoreCase("nonpositiveinteger")) { //$NON-NLS-1$
                applyPropertyIntegerValues(property, null, "0", true, tmpType); //$NON-NLS-1$ //$NON-NLS-2$
            } else if (xsdType.equalsIgnoreCase("positiveinteger")) { //$NON-NLS-1$
                applyPropertyIntegerValues(property, "1", null, true, tmpType); //$NON-NLS-1$ //$NON-NLS-2$
            } else if (xsdType.equalsIgnoreCase("normalizedstring")) { //$NON-NLS-1$
                applyPropertyPattern(property, "[^\t\n\r]*", tmpType); //$NON-NLS-1$
            } else if (xsdType.equalsIgnoreCase("qname")) { //$NON-NLS-1$
                applyPropertyPattern(property,
                        "([\\i-[:]][\\c-[:]]*:)?[\\i-[:]][\\c-[:]]*", //$NON-NLS-1$
                        tmpType);
            } else if (xsdType.equalsIgnoreCase("short")) { //$NON-NLS-1$
                applyPropertyIntegerValues(property,
                        "-32768", //$NON-NLS-1$
                        "32767", //$NON-NLS-1$
                        false,
                        tmpType);
            } else if (xsdType.equalsIgnoreCase("token")) { //$NON-NLS-1$
                applyPropertyPattern(property,
                        "[^ \\t\\n\\r]+([ ][^ \\t\\n\\r]+)*", //$NON-NLS-1$
                        tmpType);
            } else if (xsdType.equalsIgnoreCase("unsignedbyte")) { //$NON-NLS-1$
                applyPropertyIntegerValues(property,
                        "0", //$NON-NLS-1$
                        "255", //$NON-NLS-1$
                        false,
                        tmpType);
            } else if (xsdType.equalsIgnoreCase("unsignedint")) { //$NON-NLS-1$
                applyPropertyIntegerValues(property,
                        "0", //$NON-NLS-1$
                        "4294967295", //$NON-NLS-1$
                        true,
                        tmpType);
            } else if (xsdType.equalsIgnoreCase("unsignedlong")) { //$NON-NLS-1$
                applyPropertyIntegerValues(property,
                        "0", //$NON-NLS-1$
                        "18446744073709551615", //$NON-NLS-1$
                        true,
                        tmpType);
            } else if (xsdType.equalsIgnoreCase("unsignedshort")) { //$NON-NLS-1$
                applyPropertyIntegerValues(property,
                        "0", //$NON-NLS-1$
                        "65535", //$NON-NLS-1$
                        false,
                        tmpType);
            }
        }
    }

    /**
     * @param property
     * @param patternValue
     * @param type
     */
    private static void applyPropertyPattern(Property property,
            String patternValue, Element type) {
        try {
            if (type instanceof PrimitiveType) {
                Stereotype stereotype = null;
                if (property != null) {
                    stereotype = applyAndGetAppliedXSDStereotype(property);
                } else {
                    stereotype = applyAndGetAppliedXSDStereotype(type);
                }
                PrimitiveType primitiveType = (PrimitiveType) type;
                if (property == null) {
                    PrimitivesUtil.setFacetPropertyValue(primitiveType,
                            "textPatternValue", //$NON-NLS-1$
                            patternValue);
                    setStereotypeValue(primitiveType,
                            stereotype,
                            XsdStereotypeUtils.XSD_PATTERN_VALUE,
                            patternValue);
                } else {
                    PrimitivesUtil.setFacetPropertyValue(primitiveType,
                            "textPatternValue", //$NON-NLS-1$
                            patternValue,
                            property);
                    setStereotypeValue(property,
                            stereotype,
                            XsdStereotypeUtils.XSD_PATTERN_VALUE,
                            patternValue);
                }
                // }else if (type instanceof Enumeration){
                // Enumeration enumeration = (Enumeration) type;
                // if (property == null){
                // PrimitivesUtil.setFacetPropertyValue(enumeration,
                // "textPatternValue",
                // patternValue); //$NON-NLS-1$
                // }else{
                // PrimitivesUtil.setFacetPropertyValue(enumeration,
                // "textPatternValue",
                // patternValue,
                // property); //$NON-NLS-1$
                // }
            }
        } catch (Exception e) {
            Activator.getDefault().getLogger().warn(e.getLocalizedMessage());
        }
    }

    /**
     * @param property
     * @param minExclusive
     * @param maxExclusive
     * @param isFixed
     * @param type
     */
    private static void applyPropertyIntegerValues(Property property,
            String minExclusive, String maxExclusive, boolean isFixed,
            Element type) {
        Stereotype stereotype = null;
        if (property != null) {
            stereotype = applyAndGetAppliedXSDStereotype(property);
        } else {
            stereotype = applyAndGetAppliedXSDStereotype(type);
        }

        if (stereotype != null) {
            if (minExclusive != null && minExclusive.trim().length() > 0) {
                if (property != null) {
                    setStereotypeValue(property,
                            stereotype,
                            XsdStereotypeUtils.XSD_MIN_INCLUSIVE_VALUE,
                            minExclusive);
                    setStereotypeValue(property,
                            stereotype,
                            XsdStereotypeUtils.XSD_MIN_EXCLUSIVE_VALUE,
                            ""); //$NON-NLS-1$
                } else {
                    setStereotypeValue(type,
                            stereotype,
                            XsdStereotypeUtils.XSD_MIN_INCLUSIVE_VALUE,
                            minExclusive);
                    setStereotypeValue(type,
                            stereotype,
                            XsdStereotypeUtils.XSD_MIN_EXCLUSIVE_VALUE,
                            ""); //$NON-NLS-1$
                }
            }
            if (maxExclusive != null && maxExclusive.trim().length() > 0) {
                if (property != null) {
                    setStereotypeValue(property,
                            stereotype,
                            XsdStereotypeUtils.XSD_MAX_INCLUSIVE_VALUE,
                            maxExclusive);
                    setStereotypeValue(property,
                            stereotype,
                            XsdStereotypeUtils.XSD_MAX_EXCLUSIVE_VALUE,
                            ""); //$NON-NLS-1$
                } else {
                    setStereotypeValue(type,
                            stereotype,
                            XsdStereotypeUtils.XSD_MAX_INCLUSIVE_VALUE,
                            maxExclusive);
                    setStereotypeValue(type,
                            stereotype,
                            XsdStereotypeUtils.XSD_MAX_EXCLUSIVE_VALUE,
                            ""); //$NON-NLS-1$
                }

            }
        }

        try {
            if (type instanceof PrimitiveType) {
                PrimitiveType primitiveType = (PrimitiveType) type;

                EnumerationLiteral createEnumerationLiteral =
                        UMLFactory.eINSTANCE.createEnumerationLiteral();
                if (isFixed) {
                    createEnumerationLiteral.setName("fixedLength"); //$NON-NLS-1$
                    if (property == null) {
                        PrimitivesUtil.setFacetPropertyValue(primitiveType,
                                "integerSubType", //$NON-NLS-1$
                                createEnumerationLiteral);
                    } else {
                        PrimitivesUtil.setFacetPropertyValue(primitiveType,
                                "integerSubType", //$NON-NLS-1$
                                createEnumerationLiteral,
                                property);
                    }
                } else {
                    createEnumerationLiteral.setName("signedInteger"); //$NON-NLS-1$
                    if (property == null) {
                        PrimitivesUtil.setFacetPropertyValue(primitiveType,
                                "integerSubType", //$NON-NLS-1$
                                createEnumerationLiteral);
                    } else {
                        PrimitivesUtil.setFacetPropertyValue(primitiveType,
                                "integerSubType", //$NON-NLS-1$
                                createEnumerationLiteral,
                                property);
                    }
                }

                /*
                 * XPD-5517: no need to set integerLength to a default value of
                 * -1 if nothing is specified in the schema
                 */
                // PrimitivesUtil.setFacetPropertyValue(primitiveType,
                // PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH,
                // -1,
                // property);

                // }else if (type instanceof Enumeration){
                // Enumeration enumeration = (Enumeration) type;
                // if (minExclusive != null) {
                // if (property == null){
                // PrimitivesUtil.setFacetPropertyValue(enumeration,
                // "integerLower",
                // minExclusive); //$NON-NLS-1$
                // }else{
                // PrimitivesUtil.setFacetPropertyValue(enumeration,
                // "integerLower",
                // minExclusive,
                // property); //$NON-NLS-1$
                // }
                // }
                // if (maxExclusive != null) {
                // if (property == null){
                // PrimitivesUtil.setFacetPropertyValue(enumeration,
                // "integerUpper",
                // maxExclusive); //$NON-NLS-1$
                // }else{
                // PrimitivesUtil.setFacetPropertyValue(enumeration,
                // "integerUpper",
                // maxExclusive,
                // property); //$NON-NLS-1$
                // }
                // }
                //
                // EnumerationLiteral createEnumerationLiteral =
                // UMLFactory.eINSTANCE.createEnumerationLiteral();
                // if (isFixed) {
                // createEnumerationLiteral.setName("fixedLength");
                // //$NON-NLS-1$
                // if (property == null){
                // PrimitivesUtil.setFacetPropertyValue(enumeration,
                // "integerSubType",
                // createEnumerationLiteral); //$NON-NLS-1$
                // }else{
                // PrimitivesUtil.setFacetPropertyValue(enumeration,
                // "integerSubType",
                // createEnumerationLiteral,
                // property); //$NON-NLS-1$
                // }
                // } else {
                // createEnumerationLiteral.setName("signedInteger");
                // //$NON-NLS-1$
                // if (property == null){
                // PrimitivesUtil.setFacetPropertyValue(enumeration,
                // "integerSubType",
                // createEnumerationLiteral); //$NON-NLS-1$
                // }else{
                // PrimitivesUtil.setFacetPropertyValue(enumeration,
                // "integerSubType",
                // createEnumerationLiteral,
                // property); //$NON-NLS-1$
                // }
                // }
                if (minExclusive != null) {
                    if (property == null) {
                        PrimitivesUtil.setFacetPropertyValue(primitiveType,
                                "integerLower", //$NON-NLS-1$
                                minExclusive);
                    } else {
                        PrimitivesUtil.setFacetPropertyValue(primitiveType,
                                "integerLower", //$NON-NLS-1$
                                minExclusive,
                                property);
                    }
                }
                if (maxExclusive != null) {
                    if (property == null) {
                        PrimitivesUtil.setFacetPropertyValue(primitiveType,
                                "integerUpper", //$NON-NLS-1$
                                maxExclusive);
                    } else {
                        PrimitivesUtil.setFacetPropertyValue(primitiveType,
                                "integerUpper", //$NON-NLS-1$
                                maxExclusive,
                                property);
                    }
                }
            }
        } catch (Exception e) {
            Activator.getDefault().getLogger().warn(e.getLocalizedMessage());
        }
    }

    private static Stereotype applyAndGetAppliedXSDStereotype(Element elem) {
        Stereotype stereotype = applyAndGetAppliedStereotype(elem.getModel(),
                elem,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION);
        if (stereotype == null) {
            Resource tempXSDProfileResource = elem.eResource().getResourceSet()
                    .getResource(URI.createURI(XSD_NOTATION_URI), true);
            applyProfile(elem.getModel(), tempXSDProfileResource);
            stereotype = applyAndGetAppliedStereotype(elem.getModel(),
                    elem,
                    XsdStereotypeUtils.XSD_BASED_RESTRICTION);
        }
        return stereotype;
    }

    /**
     * @param property
     * @param minExclusive
     * @param maxExclusive
     * @param isFixed
     * @param type
     */
    private static void applyPropertyDecimalValues(Property property,
            String minExclusive, String maxExclusive, boolean isFixed,
            Element type) {
        Stereotype stereotype = null;
        if (property != null) {
            stereotype = applyAndGetAppliedXSDStereotype(property);
        } else {
            stereotype = applyAndGetAppliedXSDStereotype(type);
        }

        if (stereotype != null) {
            if (minExclusive != null && minExclusive.trim().length() > 0) {
                if (property != null) {
                    setStereotypeValue(property,
                            stereotype,
                            XsdStereotypeUtils.XSD_MIN_INCLUSIVE_VALUE,
                            minExclusive);
                    setStereotypeValue(property,
                            stereotype,
                            XsdStereotypeUtils.XSD_MIN_EXCLUSIVE_VALUE,
                            ""); //$NON-NLS-1$
                } else {
                    setStereotypeValue(type,
                            stereotype,
                            XsdStereotypeUtils.XSD_MIN_INCLUSIVE_VALUE,
                            minExclusive);
                    setStereotypeValue(type,
                            stereotype,
                            XsdStereotypeUtils.XSD_MIN_EXCLUSIVE_VALUE,
                            ""); //$NON-NLS-1$
                }

            }
            if (maxExclusive != null && maxExclusive.trim().length() > 0) {
                if (property != null) {
                    setStereotypeValue(property,
                            stereotype,
                            XsdStereotypeUtils.XSD_MAX_INCLUSIVE_VALUE,
                            maxExclusive);
                    setStereotypeValue(property,
                            stereotype,
                            XsdStereotypeUtils.XSD_MAX_EXCLUSIVE_VALUE,
                            ""); //$NON-NLS-1$
                } else {
                    setStereotypeValue(type,
                            stereotype,
                            XsdStereotypeUtils.XSD_MAX_INCLUSIVE_VALUE,
                            maxExclusive);
                    setStereotypeValue(type,
                            stereotype,
                            XsdStereotypeUtils.XSD_MAX_EXCLUSIVE_VALUE,
                            ""); //$NON-NLS-1$
                }
            }
        }

        try {
            if (type instanceof PrimitiveType) {
                PrimitiveType primitiveType = (PrimitiveType) type;
                if (minExclusive != null) {
                    if (property == null) {
                        PrimitivesUtil.setFacetPropertyValue(primitiveType,
                                "decimalLower", //$NON-NLS-1$
                                minExclusive);
                    } else {
                        PrimitivesUtil.setFacetPropertyValue(primitiveType,
                                "decimalLower", //$NON-NLS-1$
                                minExclusive,
                                property);
                    }
                }
                if (maxExclusive != null) {
                    if (property == null) {
                        PrimitivesUtil.setFacetPropertyValue(primitiveType,
                                "decimalUpper", //$NON-NLS-1$
                                maxExclusive);
                    } else {
                        PrimitivesUtil.setFacetPropertyValue(primitiveType,
                                "decimalUpper", //$NON-NLS-1$
                                maxExclusive,
                                property);
                    }
                }

                EnumerationLiteral createEnumerationLiteral =
                        UMLFactory.eINSTANCE.createEnumerationLiteral();
                if (isFixed) {
                    createEnumerationLiteral.setName("fixedPoint"); //$NON-NLS-1$
                    if (property == null) {
                        PrimitivesUtil.setFacetPropertyValue(primitiveType,
                                "decimalSubType", //$NON-NLS-1$
                                createEnumerationLiteral);
                    } else {
                        PrimitivesUtil.setFacetPropertyValue(primitiveType,
                                "decimalSubType", //$NON-NLS-1$
                                createEnumerationLiteral,
                                property);
                    }
                } else {
                    createEnumerationLiteral.setName("floatingPoint"); //$NON-NLS-1$
                    if (property == null) {
                        PrimitivesUtil.setFacetPropertyValue(primitiveType,
                                "decimalSubType", //$NON-NLS-1$
                                createEnumerationLiteral);
                    } else {
                        PrimitivesUtil.setFacetPropertyValue(primitiveType,
                                "decimalSubType", //$NON-NLS-1$
                                createEnumerationLiteral,
                                property);
                    }
                }
                // }else if (type instanceof Enumeration){
                // Enumeration enumeration = (Enumeration) type;
                // if (minExclusive != null) {
                // if (property == null){
                // PrimitivesUtil.setFacetPropertyValue(enumeration,
                // "decimalLower",
                // minExclusive); //$NON-NLS-1$
                // }else{
                // PrimitivesUtil.setFacetPropertyValue(enumeration,
                // "decimalLower",
                // minExclusive,
                // property); //$NON-NLS-1$
                // }
                // }
                // if (maxExclusive != null) {
                // if (property == null){
                // PrimitivesUtil.setFacetPropertyValue(enumeration,
                // "decimalUpper",
                // maxExclusive); //$NON-NLS-1$
                // }else{
                // PrimitivesUtil.setFacetPropertyValue(enumeration,
                // "decimalUpper",
                // maxExclusive,
                // property); //$NON-NLS-1$
                // }
                // }
                //
                // EnumerationLiteral createEnumerationLiteral =
                // UMLFactory.eINSTANCE.createEnumerationLiteral();
                // if (isFixed) {
                // createEnumerationLiteral.setName("fixedPoint"); //$NON-NLS-1$
                // if (property == null){
                // PrimitivesUtil.setFacetPropertyValue(enumeration,
                // "decimalSubType",
                // createEnumerationLiteral); //$NON-NLS-1$
                // }else{
                // PrimitivesUtil.setFacetPropertyValue(enumeration,
                // "decimalSubType",
                // createEnumerationLiteral,
                // property); //$NON-NLS-1$
                // }
                // } else {
                // createEnumerationLiteral.setName("floatingPoint");
                // //$NON-NLS-1$
                // if (property == null){
                // PrimitivesUtil.setFacetPropertyValue(enumeration,
                // "decimalSubType",
                // createEnumerationLiteral); //$NON-NLS-1$
                // }else{
                // PrimitivesUtil.setFacetPropertyValue(enumeration,
                // "decimalSubType",
                // createEnumerationLiteral,
                // property); //$NON-NLS-1$
                // }
                // }
            }
        } catch (Exception e) {
            Activator.getDefault().getLogger().warn(e.getLocalizedMessage());
        }
    }

    /**
     * @param element
     * @param stereotype
     * @param propertyName
     * @param propertyValue
     */
    public static void setStereotypeValue(Element element,
            Stereotype stereotype, String propertyName, Object propertyValue) {
        if (element != null && stereotype != null && propertyValue != null
                && propertyValue != "null") { //$NON-NLS-1$
            try {
                element.setValue(stereotype, propertyName, propertyValue);
            } catch (Exception e) {
                Activator.getDefault().getLogger()
                        .warn(e.getLocalizedMessage());
            }
        }
    }

    /**
     * @param model
     * @param element
     * @param stereotypeName
     * @return
     */
    public static Stereotype applyAndGetAppliedStereotype(Model model,
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
                                element.getAppliedStereotype(
                                        stereotype.getQualifiedName());
                        if (alreadyAppliedStereotype == null) {
                            try {
                                element.applyStereotype(stereotype);
                                Stereotype appliedStereotype =
                                        element.getAppliedStereotype(
                                                stereotype.getQualifiedName());
                                return appliedStereotype;
                            } catch (Exception e) {
                                return null;
                            }
                        }
                        return alreadyAppliedStereotype;
                    }
                }
            }
        }
        return null;
    }

    /*
     * public static boolean setOriginalTargetNamespace(Model model, Package
     * tempPackage, String targetNamespace) { Stereotype stereotype = null;
     * Iterator<Profile> profilesIter = model.getAppliedProfiles().iterator();
     * while (profilesIter.hasNext()) { Profile profile = profilesIter.next();
     * if (profile.getName().equals(XSD_NOTATION_PROFILE_NAME)) {
     * Iterator<Stereotype> ownedStereotypesIter = profile
     * .getOwnedStereotypes().iterator(); while (ownedStereotypesIter.hasNext())
     * { stereotype = ownedStereotypesIter.next(); if
     * (stereotype.getName().equals(XSD_BASED_MODEL)) {
     * tempPackage.applyStereotype(stereotype); tempPackage.setValue(stereotype,
     * XSD_TARGET_NAMESPACE, targetNamespace); return true; } } } } return
     * false; }
     */

    /**
     * @param enumType
     * @param value
     * @return
     */
    public static boolean createEnumerationLiteral(Enumeration enumType,
            List<String> enumIDList, List<Object> enumValueList) {
        if (enumIDList != null && enumValueList != null) {
            Iterator<Object> valueIter = enumValueList.iterator();
            Iterator<String> idIter = enumIDList.iterator();
            while (valueIter.hasNext() && idIter.hasNext()) {
                String id = idIter.next();
                Object value = valueIter.next();
                if (value != null && value instanceof String) {
                    EnumerationLiteral enumerationLiteral =
                            UMLFactory.eINSTANCE.createEnumerationLiteral();
                    enumerationLiteral.setName((String) value);
                    enumType.getOwnedLiterals().add(enumerationLiteral);
                    enumerationLiteral.getModel();
                    Stereotype appliedStereotype = applyAndGetAppliedStereotype(
                            enumType.getModel(),
                            enumerationLiteral,
                            XsdStereotypeUtils.XSD_BASED_ENUMERATION_LITERAL);
                    if (appliedStereotype != null) {
                        setStereotypeValue(enumerationLiteral,
                                appliedStereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_VALUE,
                                value);
                        if (id != null) {
                            setStereotypeValue(enumerationLiteral,
                                    appliedStereotype,
                                    XsdStereotypeUtils.XSD_PROPERTY_ID,
                                    id);
                        }

                    }
                }
            }
        }
        return true;
    }

    /**
     * @param property
     * @param minOccurs
     * @param maxOccurs
     */
    private static void setMinMaxOccurs(Property property, String minOccurs,
            Object maxOccurs) {
        if (property != null) {
            Stereotype appliedStereotype =
                    applyAndGetAppliedStereotype(property.getModel(),
                            property,
                            XsdStereotypeUtils.XSD_BASED_PROPERTY);

            String tempMax = "0"; //$NON-NLS-1$
            if (minOccurs != null) {
                property.setLower(Integer.valueOf(minOccurs));
                setStereotypeValue(property,
                        appliedStereotype,
                        XsdStereotypeUtils.XSD_PROPERTY_MIN_OCCURS,
                        minOccurs);
            }
            if (maxOccurs != null) {
                tempMax = String.valueOf(maxOccurs);
                if (tempMax.equalsIgnoreCase("unbounded")) { //$NON-NLS-1$
                    tempMax = "*"; //$NON-NLS-1$
                    property.setUpper(LiteralUnlimitedNatural.UNLIMITED);
                } else {
                    property.setUpper(Integer.valueOf(tempMax));
                }
                setStereotypeValue(property,
                        appliedStereotype,
                        XsdStereotypeUtils.XSD_PROPERTY_MAX_OCCURS,
                        tempMax);
            }
        }
    }

    public static void setIsAllMultiplicity(Class tmpClass, String minOccurs,
            String maxOccurs) {
        Stereotype appliedStereotype =
                applyAndGetAppliedStereotype(tmpClass.getModel(),
                        tmpClass,
                        XsdStereotypeUtils.XSD_BASED_CLASS);
        if (appliedStereotype != null) {
            if (minOccurs.equals("0") && maxOccurs.equals("1")) { //$NON-NLS-1$ //$NON-NLS-2$
                setStereotypeValue(tmpClass,
                        appliedStereotype,
                        XsdStereotypeUtils.XSD_PROPERTY_IS_ALL_MULTIPLICITY_SET,
                        true);
            }
        }
    }

    /**
     * @param currentPackage
     * @param parentClass
     * @param childClass
     * @param parentAssocName
     * @param childAssocName
     * @param parentMinOccurs
     * @param parentMaxOccurs
     * @param childMinOccurs
     * @param childMaxOccurs
     * @return
     */
    public static Property createCompositionProperty(
            ImportTransformationData data, Package currentPackage,
            Classifier parentClass, Classifier childClass,
            String parentAssocName, String childAssocName,
            String childMinOccurs, Object childMaxOccurs,
            String parentOriginalAssocName, String form, String defaultVal,
            String id, String fixed, String nillable, List block) {

        Property property = null;
        if (parentClass != null && childClass != null) {

            int parentMinOccurs = 1;
            int parentMaxOccurs = 1;

            if (childMinOccurs == null || childMinOccurs.equals("null")) { //$NON-NLS-1$
                childMinOccurs = new String("1"); //$NON-NLS-1$
            }
            if (childMaxOccurs == null || childMaxOccurs.equals("null")) { //$NON-NLS-1$
                childMaxOccurs = new String("1"); //$NON-NLS-1$
            }

            if (childMaxOccurs.equals("unbounded")) { //$NON-NLS-1$
                childMaxOccurs = LiteralUnlimitedNatural.UNLIMITED;
            }

            Association association = null;
            parentAssocName = stripIllegalChars(parentAssocName.toLowerCase());
            association = parentClass.createAssociation(true,
                    AggregationKind.COMPOSITE_LITERAL,
                    childAssocName,
                    Integer.parseInt(String.valueOf(childMinOccurs)),
                    Integer.parseInt(String.valueOf(childMaxOccurs)),
                    childClass,
                    false,
                    AggregationKind.NONE_LITERAL,
                    parentAssocName,
                    parentMinOccurs,
                    parentMaxOccurs);

            association.setName("Composition" //$NON-NLS-1$
                    + data.getCreatedCompositions().size());

            association.setPackage(currentPackage);
            setUniqueId(data, association);
            for (Property prop : association.getAllAttributes()) {
                setUniqueId(data, prop);
                if (prop.getName().equals(childAssocName)) {
                    property = prop;
                }
            }
            data.getCreatedCompositions().add(association);
        }

        if (property != null) {
            Stereotype appliedStereotype =
                    applyAndGetAppliedStereotype(property.getModel(),
                            property,
                            XsdStereotypeUtils.XSD_BASED_PROPERTY);
            if (appliedStereotype != null) {

                setStereotypeValue(property,
                        appliedStereotype,
                        XsdStereotypeUtils.XSD_PROPERTY_FORM,
                        form);
                setStereotypeValue(property,
                        appliedStereotype,
                        XsdStereotypeUtils.XSD_PROPERTY_DEFAULT,
                        defaultVal);
                if (defaultVal != null) {
                    setUMLDefaults(property, String.valueOf(defaultVal));
                }
                setStereotypeValue(property,
                        appliedStereotype,
                        XsdStereotypeUtils.XSD_PROPERTY_ID,
                        id);
                setStereotypeValue(property,
                        appliedStereotype,
                        XsdStereotypeUtils.XSD_PROPERTY_FIXED,
                        fixed);
                setStereotypeValue(property,
                        appliedStereotype,
                        XsdStereotypeUtils.XSD_PROPERTY_NILLABLE,
                        nillable);
                String strBlock = getBlockStrValue(block);
                if (strBlock.length() > 0) {
                    setStereotypeValue(property,
                            appliedStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_BLOCK,
                            strBlock);
                }

                // this checks to see if anonymous type property or not - if
                // this breaks then the logic is wrong but works ok from all
                // tests so far
                Stereotype stereotype =
                        applyAndGetAppliedStereotype(childClass.getModel(),
                                childClass,
                                XsdStereotypeUtils.XSD_BASED_CLASS);
                if (parentOriginalAssocName.equals(childAssocName) || (data
                        .getElementNamesMap().containsValue(childAssocName)
                        && childAssocName.contains(parentOriginalAssocName))) {

                    setStereotypeValue(property,
                            appliedStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_NAME,
                            parentOriginalAssocName);

                    Object xsdType = childClass.getName();
                    if (stereotype != null) {
                        Object xsdName = childClass.getValue(stereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_NAME);
                        if (xsdName != null) {
                            xsdType = xsdName;
                        }
                    }
                    setStereotypeValue(property,
                            appliedStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_TYPE,
                            getLocalPart(xsdType.toString()));

                } else {
                    if (stereotype != null) {
                        Object xsdName = childClass.getValue(stereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_NAME);
                        if (xsdName != null) {
                            setStereotypeValue(property,
                                    appliedStereotype,
                                    XsdStereotypeUtils.XSD_PROPERTY_NAME,
                                    xsdName);
                        }
                    }
                }
            }
        }
        if (property != null) {
            setMinMaxOccurs(property,
                    property.getLower() + "", //$NON-NLS-1$
                    property.getUpper() + ""); //$NON-NLS-1$
        }
        return property;
    }

    public static boolean isAnonClass(Classifier childClass) {
        boolean isAnonCls = false;
        Stereotype xsdBasedModelStereotype =
                com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper
                        .getXSDNotationStereotype(childClass,
                                XsdStereotypeUtils.XSD_BASED_CLASS);
        if (xsdBasedModelStereotype != null) {
            Object notationProperty =
                    com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper
                            .getXSDNotationProperty(childClass,
                                    xsdBasedModelStereotype,
                                    XsdStereotypeUtils.XSD_PROPERTY_IS_ANON_TYPE);
            if (notationProperty != null && String.valueOf(notationProperty)
                    .equalsIgnoreCase(Boolean.TRUE.toString())) {
                isAnonCls = true;
            }
        }
        return isAnonCls;
    }

    /**
     * @param currentPackage
     * @param sourceClass
     * @param targetClass
     * @param name
     * @param type
     * @param minOccurs
     * @param maxOccurs
     * @return
     */
    public static Property createRefCompositionProperty(
            ImportTransformationData data, Package currentPackage,
            Classifier sourceClass, Classifier targetClass, String origName,
            String name, String type, String minOccurs, Object maxOccurs) {
        Property property = null;
        Type element = targetClass;

        if (targetClass == null) {
            element = getPrimitiveType(data, type);
        }

        if (sourceClass != null) {
            if (element != null && sourceClass instanceof Class) {
                property =
                        ((Class) sourceClass).createOwnedAttribute(name, null);
                property.setType(element);
                setUniqueId(data, property);

                /*
                 * XPD-5089 we now validate that all owned attributes have an
                 * aggregation attribute. So we MUST ensure that all generated
                 * properties have a aggregation set.
                 */
                property.setAggregation(AggregationKind.COMPOSITE_LITERAL);

            } else if (targetClass != null) {
                // create composition
                Association association = sourceClass.createAssociation(true,
                        AggregationKind.COMPOSITE_LITERAL,
                        name,
                        Integer.parseInt(String.valueOf(minOccurs)),
                        Integer.parseInt(String.valueOf(maxOccurs)),
                        targetClass,
                        false,
                        AggregationKind.NONE_LITERAL,
                        targetClass.getName(),
                        1,
                        1);

                association.setName(
                        "Composition" + data.getCreatedCompositions().size()); //$NON-NLS-1$
                association.setPackage(currentPackage);
                setUniqueId(data, association);
                for (Property prop : association.getAllAttributes()) {
                    setUniqueId(data, prop);
                    if (prop.getName().equals(name)) {
                        property = prop;
                    }
                }
                data.getCreatedCompositions().add(association);
            }
        }
        if (property != null) {
            Stereotype appliedStereotype =
                    applyAndGetAppliedStereotype(property.getModel(),
                            property,
                            XsdStereotypeUtils.XSD_BASED_PROPERTY);
            if (appliedStereotype != null) {
                Stereotype stereotype =
                        applyAndGetAppliedStereotype(currentPackage.getModel(),
                                sourceClass,
                                XsdStereotypeUtils.XSD_BASED_CLASS);
                if (stereotype != null) {
                    setStereotypeValue(property,
                            appliedStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_NAME,
                            name);
                    setStereotypeValue(property,
                            appliedStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_REF,
                            origName);
                    /*
                     * if (property.getType() instanceof PrimitiveType){
                     * PrimitiveType tmpPrimType =
                     * (PrimitiveType)property.getType(); }
                     */
                    // if (!(property.getType() instanceof Class)){
                    /*
                     * PrimitivesUtil.setFacetPropertyValue((PrimitiveType)
                     * property .getType(),
                     * PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH, -1,
                     * property);
                     * PrimitivesUtil.setFacetPropertyValue((PrimitiveType
                     * )property.getType(),
                     * PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH, -1,
                     * property);
                     * PrimitivesUtil.setFacetPropertyValue((PrimitiveType
                     * )property.getType(),
                     * PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH, -1,
                     * property);
                     */
                    // }
                }

            }
        }
        setMinMaxOccurs(property, minOccurs, maxOccurs);
        return property;
    }

    /**
     * @param data
     * @param tempPackage
     */
    public static void applyProfile(ImportTransformationData data,
            Package tempPackage) {
        boolean xsdNotationProfileApplied = false;

        Iterator<Profile> profileIter =
                tempPackage.getAppliedProfiles().iterator();
        while (profileIter.hasNext()) {
            Profile profile = profileIter.next();
            if (profile.getName()
                    .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                xsdNotationProfileApplied = true;
            }
        }

        if (!xsdNotationProfileApplied) {
            applyProfile(tempPackage, data.getXsdProfileResource());
        }
    }

    /**
     * @param tempPackage
     * @param res
     */
    private static void applyProfile(Package tempPackage, Resource res) {
        Profile profile = null;
        if (res != null) {
            EObject object = res.getContents().get(0);

            if (object instanceof Profile) {
                profile = (Profile) object;
                tempPackage.applyProfile(profile);
            }
        }
    }

    /**
     * @param data
     * @param classifier
     * @param xsdType
     */
    public static void addAnyTypeDetails(ImportTransformationData data,
            Classifier classifier, String xsdType) {
        if (classifier.getGenerals().size() > 0) {
            Classifier tmpClassifier = classifier.getGenerals().get(0);
            if (tmpClassifier.getName()
                    .equals(PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME)) {
                if (xsdType == null || xsdType.equalsIgnoreCase("anytype")) { //$NON-NLS-1$
                    EnumerationLiteral createEnumerationLiteral =
                            UMLFactory.eINSTANCE.createEnumerationLiteral();
                    createEnumerationLiteral.setName("xsdAnyType"); //$NON-NLS-1$
                    if (classifier instanceof PrimitiveType) {
                        PrimitivesUtil.setFacetPropertyValue(
                                (PrimitiveType) classifier,
                                "objectSubType", //$NON-NLS-1$
                                createEnumerationLiteral);
                    } else if (classifier instanceof Enumeration) {
                        PrimitivesUtil.setFacetPropertyValue(
                                (Enumeration) classifier,
                                "objectSubType", //$NON-NLS-1$
                                createEnumerationLiteral);
                    }
                } else if (xsdType.equalsIgnoreCase("anysimpletype")) { //$NON-NLS-1$
                    EnumerationLiteral createEnumerationLiteral =
                            UMLFactory.eINSTANCE.createEnumerationLiteral();
                    createEnumerationLiteral.setName("xsdAnySimpleType"); //$NON-NLS-1$
                    if (classifier instanceof PrimitiveType) {
                        PrimitivesUtil.setFacetPropertyValue(
                                (PrimitiveType) classifier,
                                "objectSubType", //$NON-NLS-1$
                                createEnumerationLiteral);
                    } else if (classifier instanceof Enumeration) {
                        PrimitivesUtil.setFacetPropertyValue(
                                (Enumeration) classifier,
                                "objectSubType", //$NON-NLS-1$
                                createEnumerationLiteral);
                    }
                }
            }
        }
    }

    /**
     * Creates a Top Level Element Wrapper and sets the appropriate attributes
     * inside it before adding the actual XSD EObject instance to the top level
     * element stereotype applied to the current Package.
     * 
     * @param data
     * @param classifier
     * @param name
     * @param isAnonymous
     * @param isBaseXSDType
     * @param id
     * @param fixed
     * @param tmpFinal
     * @param block
     * @param nillable
     * @param tmpAbstract
     * @param substitutionGroup
     * @param myDefault
     * @param targetNamespace
     * @param tmpPackage
     */
    public static void addTopLevelElement(ImportTransformationData data,
            Classifier classifier, String name, Boolean isAnonymous,
            Boolean isBaseXSDType, String id, String fixed, List tmpFinal,
            List block, Boolean nillable, Boolean tmpAbstract,
            String substitutionGroup, String myDefault, String targetNamespace,
            Package tmpPackage) {

        applyTopLevelElementsStereotype(tmpPackage);

        TopLevelElementWrapper inst = TopLevelElementWrapper
                .create(classifier.eResource().getResourceSet());

        // set the attributes
        inst.setName(name);
        inst.setType(classifier);
        inst.setTargetNamespace(stripIllegalChars(targetNamespace));
        inst.setIsAnonymous(isAnonymous);
        inst.setisBaseXSDType(isBaseXSDType);
        inst.setID(id);
        inst.setFixed(fixed);
        String strFinal = getFinalStrValue(tmpFinal);
        if (strFinal.length() > 0) {
            inst.setFinal(strFinal);
        }
        String strBlock = getBlockStrValue(block);
        if (strBlock.length() > 0) {
            inst.setBlock(strBlock);
        }
        inst.setNillable(nillable);
        inst.setAbstract(tmpAbstract);
        inst.setSubstitutionGroup(substitutionGroup);
        inst.setDefault(myDefault);

        // get top level elements stereotype and set the element value
        Stereotype topLevelElementsStereotype =
                applyAndGetAppliedStereotype(tmpPackage.getModel(),
                        tmpPackage,
                        XsdStereotypeUtils.TOP_LEVEL_ELEMENTS);

        Object elements = tmpPackage.getValue(topLevelElementsStereotype,
                XsdStereotypeUtils.XSD_TOP_LEVEL_ELEMENT_ELEMENTS);
        if (elements instanceof EList) {
            EList<EObject> elementsList = (EList<EObject>) elements;
            boolean found = false;
            for (EObject obj : elementsList) {
                TopLevelElementWrapper tmpElem =
                        new TopLevelElementWrapper(obj);
                if (tmpElem.getName().equals(name)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                elementsList.add(inst.getEObject());
            }
        }
    }

    public static void updateTopLevelElementType(Classifier tmpRefClass,
            Classifier baseClass, String refLocalPart) {
        Stereotype topLevelElementsStereotype =
                applyAndGetAppliedStereotype(tmpRefClass.getModel(),
                        tmpRefClass.getModel(),
                        XsdStereotypeUtils.TOP_LEVEL_ELEMENTS);
        Object elements =
                tmpRefClass.getModel().getValue(topLevelElementsStereotype,
                        XsdStereotypeUtils.XSD_TOP_LEVEL_ELEMENT_ELEMENTS);
        if (elements != null && elements instanceof EList) {
            EList<EObject> elementsList = (EList<EObject>) elements;
            for (EObject obj : elementsList) {
                TopLevelElementWrapper tmpElem =
                        new TopLevelElementWrapper(obj);
                if (tmpElem.getName().equals(refLocalPart)
                        && tmpElem.getType().equals(tmpRefClass)) {
                    tmpElem.setType(baseClass);
                    break;
                }
            }
        }
    }

    /**
     * Creates a Top Level Attribute Wrapper and sets the appropriate attributes
     * inside it before adding the actual XSD EObject instance to the top level
     * attribute stereotype applied to the current Package.
     * 
     * @param data
     * @param classifier
     * @param name
     * @param isAnonymous
     * @param isBaseXSDType
     * @param id
     * @param fixed
     * @param myDefault
     * @param targetNamespace
     * @param tmpPackage
     */
    public static void addTopLevelAttribute(ImportTransformationData data,
            Classifier classifier, String name, Boolean isAnonymous,
            Boolean isBaseXSDType, String id, String fixed, String myDefault,
            String targetNamespace, Package tmpPackage) {

        applyTopLevelAttributesStereotype(tmpPackage);

        TopLevelAttributeWrapper inst = TopLevelAttributeWrapper
                .create(tmpPackage.eResource().getResourceSet());

        // set the attributes
        inst.setName(name);
        inst.setType(classifier);
        inst.setTargetNamespace(stripIllegalChars(targetNamespace));
        inst.setIsAnonymous(isAnonymous);
        inst.setIsBaseXSDType(isBaseXSDType);
        inst.setID(id);
        inst.setFixed(fixed);
        inst.setDefault(myDefault);

        // get top level attributes stereotype and set the attribute value
        Stereotype topLevelAttributesStereotype =
                applyAndGetAppliedStereotype(tmpPackage.getModel(),
                        tmpPackage,
                        XsdStereotypeUtils.TOP_LEVEL_ATTRIBUTES);

        Object attributes = tmpPackage.getValue(topLevelAttributesStereotype,
                XsdStereotypeUtils.XSD_TOP_LEVEL_ATTRIBUTE_ATTRIBUTES);
        if (attributes instanceof EList) {
            EList<EObject> attributesList = (EList<EObject>) attributes;
            boolean found = false;
            for (EObject obj : attributesList) {
                TopLevelAttributeWrapper tmpAttr =
                        new TopLevelAttributeWrapper(obj);
                if (tmpAttr.getName().equals(name)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                attributesList.add(inst.getEObject());
            }
        }
    }

    /**
     * Add the given package to the diagram resource in the
     * ImportTransformationData if the package is not already contained in a
     * resource.
     * 
     * @param data
     * @param currentPackage
     */
    public static void addPackageToResource(ImportTransformationData data,
            Package currentPackage) {
        final Package tempPackage = currentPackage;
        Resource resource = currentPackage.eResource();
        if (resource == null) {
            data.getDiagramResource().getContents().add(tempPackage);
        }
    }

    /**
     * @param data
     * @param currentPackage
     * @param baseType
     * @param primitiveType
     *            this is expected to be a {@link PrimitiveType}
     * @param id
     * @param enumeration
     * @param whiteSpace
     * @param length
     * @param maxExclusive
     * @param maxInclusive
     * @param maxLength
     * @param minExclusive
     * @param minInclusive
     * @param minLength
     * @param pattern
     */
    public static void createRestriction(ImportTransformationData data,
            Package currentPackage, Object baseType, Object primitiveType,
            String id, List<?> enumeration, List<?> whiteSpace, List<?> length,
            List<?> maxExclusive, List<?> maxInclusive, List<?> maxLength,
            List<?> minExclusive, List<?> minInclusive, List<?> minLength,
            List<?> pattern, String xsdType, Boolean isSimpleType) {
        if (baseType != null) {
            Stereotype stereotype = null;
            if (primitiveType instanceof PrimitiveType) {
                stereotype = PrimitivesUtil.getApplicableFacetStereotype(
                        (PrimitiveType) primitiveType);
            } else if (primitiveType instanceof Enumeration) {
                stereotype = PrimitivesUtil.getApplicableFacetStereotype(
                        (Enumeration) primitiveType);
            }
            if (stereotype == null) {
                Resource tempPrimitivesProfileResource =
                        currentPackage.eResource().getResourceSet().getResource(
                                URI.createURI(PRIMITIVETYPE_FACET_URI),
                                true);
                applyProfile(currentPackage, tempPrimitivesProfileResource);
                if (primitiveType instanceof PrimitiveType) {
                    stereotype = PrimitivesUtil.getApplicableFacetStereotype(
                            (PrimitiveType) primitiveType);
                    ((PrimitiveType) primitiveType).applyStereotype(stereotype);
                } else if (primitiveType instanceof Enumeration) {
                    stereotype = PrimitivesUtil.getApplicableFacetStereotype(
                            (Enumeration) primitiveType);
                    if (stereotype != null) {
                        ((Enumeration) primitiveType)
                                .applyStereotype(stereotype);
                    }
                }
            } else {
                ((DataType) primitiveType).applyStereotype(stereotype);
            }
            if (stereotype != null) {
                try {
                    setOtherStereotypeValues(null,
                            xsdType,
                            null,
                            (DataType) primitiveType,
                            isSimpleType);
                    setUndefinedRestrictions((DataType) primitiveType,
                            (DataType) baseType);
                    setStereoTypePropertyValues(stereotype,
                            (DataType) primitiveType,
                            (DataType) baseType,
                            id,
                            enumeration,
                            whiteSpace,
                            length,
                            maxExclusive,
                            maxInclusive,
                            maxLength,
                            minExclusive,
                            minInclusive,
                            minLength,
                            pattern);
                } catch (Exception e) {
                }
            }

            setUniqueId(data, primitiveType);
        }
    }

    /**
     * @param classifier
     * @return
     */
    public static boolean applyDocumentRoot(Classifier classifier) {
        Stereotype stereotype = null;
        Iterator<Profile> profilesIter =
                classifier.getPackage().getAppliedProfiles().iterator();
        while (profilesIter.hasNext()) {
            Profile profile = profilesIter.next();
            if (profile.getName()
                    .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                Object obj = profile
                        .getPackagedElement(XsdStereotypeUtils.XSD_BASED_CLASS);
                if (obj instanceof Stereotype) {
                    stereotype = (Stereotype) obj;
                    if (stereotype.getName()
                            .equals(XsdStereotypeUtils.XSD_BASED_CLASS)) {
                        boolean isApplied = classifier.getAppliedStereotypes()
                                .contains(stereotype);
                        try {
                            if (!isApplied) {
                                classifier.applyStereotype(stereotype);
                            }
                            classifier.setValue(stereotype,
                                    XsdStereotypeUtils.XSD_DOCUMENT_ROOT,
                                    true);
                        } catch (Exception e) {
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param element
     * @param currentPackage
     * @return
     */
    public static boolean applyElementStereotype(Element element,
            Package currentPackage) {
        Stereotype stereotype = null;
        Iterator<Profile> profilesIter =
                currentPackage.getAppliedProfiles().iterator();
        while (profilesIter.hasNext()) {
            Profile profile = profilesIter.next();
            if (profile.getName()
                    .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                Object obj = profile.getPackagedElement(
                        XsdStereotypeUtils.XSD_BASED_ELEMENT);
                if (obj instanceof Stereotype) {
                    stereotype = (Stereotype) obj;
                    if (stereotype.getName()
                            .equals(XsdStereotypeUtils.XSD_BASED_ELEMENT)) {
                        try {
                            element.applyStereotype(stereotype);
                        } catch (Exception e) {
                        }
                        // element
                        // .setValue(stereotype, XSD_DIRTY, false);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param currentPackage
     * @return
     */
    public static boolean applyTopLevelElementsStereotype(
            Package currentPackage) {
        Stereotype stereotype = null;
        Iterator<Profile> profilesIter =
                currentPackage.getAppliedProfiles().iterator();
        while (profilesIter.hasNext()) {
            Profile profile = profilesIter.next();
            if (profile.getName()
                    .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                Object obj = profile.getPackagedElement(
                        XsdStereotypeUtils.TOP_LEVEL_ELEMENTS);
                if (obj instanceof Stereotype) {
                    stereotype = (Stereotype) obj;
                    if (stereotype.getName()
                            .equals(XsdStereotypeUtils.TOP_LEVEL_ELEMENTS)) {
                        try {
                            if (currentPackage != null) {
                                currentPackage.applyStereotype(stereotype);
                            } else {
                                return false;
                            }
                        } catch (Exception e) {
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param currentPackage
     * @return
     */
    public static boolean applyTopLevelAttributesStereotype(
            Package currentPackage) {
        Stereotype stereotype = null;
        Iterator<Profile> profilesIter =
                currentPackage.getAppliedProfiles().iterator();
        while (profilesIter.hasNext()) {
            Profile profile = profilesIter.next();
            if (profile.getName()
                    .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                Iterator<Stereotype> ownedStereotypesIter =
                        profile.getOwnedStereotypes().iterator();
                while (ownedStereotypesIter.hasNext()) {
                    stereotype = ownedStereotypesIter.next();
                    if (stereotype.getName()
                            .equals(XsdStereotypeUtils.TOP_LEVEL_ATTRIBUTES)) {
                        try {
                            currentPackage.applyStereotype(stereotype);
                        } catch (Exception e) {
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param model
     * @param tempPackage
     * @param targetNamespace
     * @return
     */
    public static boolean setOriginalTargetNamespace(Model model,
            Package tempPackage, String targetNamespace) {
        Stereotype stereotype = null;
        Iterator<Profile> profilesIter = model.getAppliedProfiles().iterator();
        while (profilesIter.hasNext()) {
            Profile profile = profilesIter.next();
            if (profile.getName()
                    .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                Iterator<Stereotype> ownedStereotypesIter =
                        profile.getOwnedStereotypes().iterator();
                while (ownedStereotypesIter.hasNext()) {
                    stereotype = ownedStereotypesIter.next();
                    if (stereotype.getName()
                            .equals(XsdStereotypeUtils.XSD_BASED_MODEL)) {
                        try {
                            tempPackage.applyStereotype(stereotype);
                            tempPackage.setValue(stereotype,
                                    XsdStereotypeUtils.XSD_TARGET_NAMESPACE,
                                    stripIllegalChars(targetNamespace));
                        } catch (Exception e) {
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param data
     * @param model
     * @param tempPackage
     * @param objectFromReferencingSchema
     *            Any object (probably the import/include element) from teh
     *            schema that references the schemaLocation (for imports and
     *            includes processing) OR <code>null</code> can be passed if not
     *            a reference (i.e. schemaLocation is also passed as
     *            <code>null</code> cos dealing with original schema being
     *            transformed.
     * @param schemaLocation
     * @return
     */
    public static boolean setOriginalSchemaLocation(
            ImportTransformationData data, Model model, Package tempPackage,
            Object objectFromReferencingSchema, String schemaLocation) {
        Stereotype stereotype = null;

        IProject project = data.getDestinationFolder().getProject();

        if (schemaLocation == null) {
            schemaLocation = getSchemaLocation(data.getSourceFile(), project);

        } else {
            /*
             * XPD-6062 Convert schema location that is passed in as 'relative
             * to referencing file' into 'relative to ServiceDescriptors folder
             * (instead of using simple file name, which was always going to be
             * a disaster!)
             */
            URI referencedSchemaURI = resolveReferencedSchemaLocationURI(data,
                    objectFromReferencingSchema,
                    schemaLocation);

            schemaLocation = getSpecialFolderRelativeSchemaLocation(
                    new Path(referencedSchemaURI.toFileString()),
                    project);

        }

        Iterator<Profile> profilesIter = model.getAppliedProfiles().iterator();
        while (profilesIter.hasNext()) {
            Profile profile = profilesIter.next();
            if (profile.getName()
                    .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                Iterator<Stereotype> ownedStereotypesIter =
                        profile.getOwnedStereotypes().iterator();
                while (ownedStereotypesIter.hasNext()) {
                    stereotype = ownedStereotypesIter.next();
                    if (stereotype.getName()
                            .equals(XsdStereotypeUtils.XSD_BASED_MODEL)) {
                        Stereotype appliedStereotype =
                                tempPackage.getAppliedStereotype(
                                        stereotype.getQualifiedName());
                        try {
                            if (appliedStereotype != null) {
                                stereotype = appliedStereotype;
                            } else {
                                tempPackage.applyStereotype(stereotype);
                            }

                            /*
                             * XPD-4166: check for the given model if the schema
                             * location stereotype is already set. if yes then
                             * append the new schema location separated by
                             * schema location delimiter. this is basically to
                             * append schema locations for included schemas.
                             * 
                             * if this is not set then the indexer
                             * (WsdlBomIndexerProvider which looks at schema
                             * location for indexing xsd elements of this
                             * schema) does not index any elements that might be
                             * later referenced by parts in the wsdl from this
                             * schema
                             * 
                             * XPD-6062 - source files are now stored in the
                             * schemaLocation stereotype as correct special
                             * folder relative paths (v3.7.0) instead of simple
                             * files names.
                             */
                            Object value = tempPackage.getValue(stereotype,
                                    XsdStereotypeUtils.XSD_SCHEMA_LOCATION);
                            if (value instanceof String) {
                                String existingValue = (String) value;

                                String[] paths = existingValue.split(
                                        XsdStereotypeUtils.SCHEMA_LOCATION_DELIMITER);

                                if (paths != null
                                        && !contains(paths, schemaLocation)) {
                                    existingValue = existingValue.concat(
                                            XsdStereotypeUtils.SCHEMA_LOCATION_DELIMITER
                                                    + schemaLocation);
                                    tempPackage.setValue(stereotype,
                                            XsdStereotypeUtils.XSD_SCHEMA_LOCATION,
                                            existingValue);
                                }
                            } else if (schemaLocation != null
                                    && !schemaLocation.isEmpty()) {
                                tempPackage.setValue(stereotype,
                                        XsdStereotypeUtils.XSD_SCHEMA_LOCATION,
                                        schemaLocation);
                            }
                        } catch (Exception e) {
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check if the given array of strings contains the given string value.
     * 
     * @param values
     * @param valueToSearch
     * @return <code>true</code> if the array already contains the given string.
     */
    private static boolean contains(String[] values, String valueToSearch) {
        if (values != null && valueToSearch != null)
            for (String value : values) {
                if (valueToSearch.equals(value)) {
                    return true;
                }
            }
        return false;
    }

    /**
     * Get the schema location of the given source file. If this source is
     * contained in a workspace project then this will return the Services
     * Descriptor special folder path to the source file, otherwise it will
     * return the absolute path from the source.
     * 
     * @param source
     * @param project
     * @return
     */
    private static String getSchemaLocation(File source, IProject project) {
        if (source != null) {
            IPath path = new Path(source.getAbsolutePath());
            /* XPD-6062 extracted method. */
            return getSpecialFolderRelativeSchemaLocation(path, project);
        }

        return null;
    }

    /**
     * @param sourceSchemaAbsolutePath
     * @param project
     * 
     * @return the schema location of the given source file. If this source is
     *         contained in a workspace project then this will return the
     *         Services Descriptor special folder path to the source file,
     *         otherwise it will return the absolute path from the source.
     */
    private static String getSpecialFolderRelativeSchemaLocation(
            IPath sourceSchemaAbsolutePath, IProject project) {
        String location = null;

        IPath projectLocation = project.getLocation();

        if (projectLocation.isPrefixOf(sourceSchemaAbsolutePath)) {
            IPath projectRelativePath = sourceSchemaAbsolutePath
                    .removeFirstSegments(sourceSchemaAbsolutePath
                            .matchingFirstSegments(projectLocation))
                    .setDevice(null);

            if (projectRelativePath != null
                    && projectRelativePath.segmentCount() > 0) {
                IResource member = project.findMember(projectRelativePath);
                if (member != null) {
                    IPath sfRelativePath = SpecialFolderUtil
                            .getSpecialFolderRelativePath(member,
                                    com.tibco.xpd.wsdl.Activator.WSDL_SPECIALFOLDER_KIND);
                    if (sfRelativePath != null) {
                        location = sfRelativePath.toString();
                    }
                }
            }

        } else {

            location = sourceSchemaAbsolutePath.toString();

        }

        return location != null ? location
                : sourceSchemaAbsolutePath.toString();
    }

    /**
     * @param stereotype
     * @param primitiveType
     * @param id
     * @param enumeration
     * @param whiteSpace
     * @param length
     * @param maxExclusive
     * @param maxInclusive
     * @param maxLength
     * @param minExclusive
     * @param minInclusive
     * @param minLength
     * @param pattern
     */
    private static void setStereoTypePropertyValues(final Stereotype stereotype,
            final DataType primitiveType, final DataType baseType, String id,
            List<?> enumeration, List<?> whiteSpace, List<?> length,
            List<?> maxExclusive, List<?> maxInclusive, List<?> maxLength,
            List<?> minExclusive, List<?> minInclusive, List<?> minLength,
            List<?> pattern) {
        DataType tmpBaseType = baseType;
        if (baseType instanceof PrimitiveType) {
            tmpBaseType = PrimitivesUtil
                    .getBasePrimitiveType((PrimitiveType) baseType);
        }

        if (tmpBaseType.getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME)) {
            setDefaultValue(stereotype,
                    primitiveType,
                    enumeration,
                    "textDefaultValue"); //$NON-NLS-1$
            setLength(stereotype, primitiveType, maxLength, "textLength"); //$NON-NLS-1$
            if (maxLength == null || maxLength.size() == 0) {
                setLength(stereotype, primitiveType, length, "textLength"); //$NON-NLS-1$
            }
            setPatternValue(stereotype,
                    primitiveType,
                    pattern,
                    "textPatternValue"); //$NON-NLS-1$
        } else if (tmpBaseType.getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
            boolean isLowerInclusive = setLowerInclusiveValue(stereotype,
                    primitiveType,
                    minInclusive,
                    minExclusive,
                    "decimalLowerInclusive"); //$NON-NLS-1$
            boolean isUpperInclusive = setUpperInclusiveValue(stereotype,
                    primitiveType,
                    maxInclusive,
                    maxExclusive,
                    "decimalUpperInclusive"); //$NON-NLS-1$
            if (isLowerInclusive) {
                setLowerValue(stereotype,
                        primitiveType,
                        minInclusive,
                        "decimalLower"); //$NON-NLS-1$
            } else {
                setLowerValue(stereotype,
                        primitiveType,
                        minExclusive,
                        "decimalLower"); //$NON-NLS-1$
            }
            if (isUpperInclusive) {
                setUpperValue(stereotype,
                        primitiveType,
                        maxInclusive,
                        "decimalUpper"); //$NON-NLS-1$
            } else {
                setUpperValue(stereotype,
                        primitiveType,
                        maxExclusive,
                        "decimalUpper"); //$NON-NLS-1$

            }
            setLengthValue(stereotype,
                    primitiveType,
                    maxLength,
                    "decimalLength"); //$NON-NLS-1$
            if (maxLength == null || maxLength.size() == 0) {
                setLengthValue(stereotype,
                        primitiveType,
                        length,
                        "decimalLength"); //$NON-NLS-1$
            }
            // setDecimalPlacesValue(stereotype, primitiveType, enumeration,
            // "decimalPlaces");
            setDefaultValue(stereotype,
                    primitiveType,
                    enumeration,
                    "decimalDefaultValue"); //$NON-NLS-1$
        } else if (tmpBaseType.getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {
            setLengthValue(stereotype,
                    primitiveType,
                    maxLength,
                    "integerLength"); //$NON-NLS-1$
            if (maxLength == null || maxLength.size() == 0) {
                setLengthValue(stereotype,
                        primitiveType,
                        length,
                        "integerLength"); //$NON-NLS-1$
            }
            setDefaultValue(stereotype,
                    primitiveType,
                    enumeration,
                    "integerDefaultValue"); //$NON-NLS-1$
            if (minInclusive.size() > 0) {
                setLowerValue(stereotype,
                        primitiveType,
                        minExclusive,
                        "integerLower"); //$NON-NLS-1$
            }
            if (maxInclusive.size() > 0) {
                setUpperValue(stereotype,
                        primitiveType,
                        maxInclusive,
                        "integerUpper"); //$NON-NLS-1$
            }
            if (minExclusive.size() > 0) {
                ArrayList<String> adjustedValuesList = new ArrayList<String>();
                Object object = minExclusive.get(0);
                Long minExclusiveIntVal = Long.valueOf("" + object); //$NON-NLS-1$
                minExclusiveIntVal = minExclusiveIntVal + 1;
                adjustedValuesList.add(String.valueOf(minExclusiveIntVal));
                setLowerValue(stereotype,
                        primitiveType,
                        adjustedValuesList,
                        "integerLower"); //$NON-NLS-1$
            }
            if (maxExclusive.size() > 0) {
                ArrayList<String> adjustedValuesList = new ArrayList<String>();
                Object object = maxExclusive.get(0);
                Long maxExclusiveIntVal = Long.valueOf("" + object); //$NON-NLS-1$
                maxExclusiveIntVal = maxExclusiveIntVal - 1;
                adjustedValuesList.add(String.valueOf(maxExclusiveIntVal));
                setUpperValue(stereotype,
                        primitiveType,
                        adjustedValuesList,
                        "integerUpper"); //$NON-NLS-1$
            }
            /*
             * XPD-5517: no need to set integerLength to a default value of -1
             * if nothing is specified in the schema
             */
            // if (maxExclusive != null && maxExclusive.size() > 0) {
            // String upperVal = String.valueOf(maxExclusive.get(0));
            // if (upperVal != null && upperVal.trim().length() > 0
            // && (maxLength == null || maxLength.size() == 0)) {
            // if (primitiveType instanceof PrimitiveType) {
            // PrimitivesUtil
            // .setFacetPropertyValue((PrimitiveType) primitiveType,
            // PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH,
            // -1);
            // } else if (primitiveType instanceof Enumeration) {
            // PrimitivesUtil
            // .setFacetPropertyValue((Enumeration) primitiveType,
            // PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH,
            // -1);
            // }
            // }
            // }
        } else if (tmpBaseType.getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME)) {
            setDefaultValue(stereotype,
                    primitiveType,
                    enumeration,
                    "booleanDefaultValue"); //$NON-NLS-1$
        } else if (tmpBaseType.getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME)) {
            setDefaultValue(stereotype,
                    primitiveType,
                    enumeration,
                    "dateDefaultValue"); //$NON-NLS-1$
        } else if (tmpBaseType.getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME)) {
            setDefaultValue(stereotype,
                    primitiveType,
                    enumeration,
                    "timeDefaultValue"); //$NON-NLS-1$
        } else if (tmpBaseType.getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME)) {
            setDefaultValue(stereotype,
                    primitiveType,
                    enumeration,
                    "dateTimeDefaultValue"); //$NON-NLS-1$
        }
    }

    /**
     * @param stereotype
     * @param primitiveType
     * @param enumeration
     * @param propertyName
     */
    private static void setDefaultValue(final Stereotype stereotype,
            final DataType primitiveType, List<?> enumeration,
            String propertyName) {
        if (enumeration != null && enumeration.size() > 0) {
            Object value = enumeration.get(0);
            primitiveType.setValue(stereotype, propertyName, value);
        }
    }

    /**
     * @param stereotype
     * @param primitiveType
     * @param maxLength
     * @param propertyName
     */
    private static void setLength(final Stereotype stereotype,
            final DataType primitiveType, List<?> maxLength,
            String propertyName) {
        if (maxLength != null && maxLength.size() > 0) {
            Object value = maxLength.get(0);
            primitiveType.setValue(stereotype, propertyName, value);
        }
    }

    /**
     * @param stereotype
     * @param primitiveType
     * @param pattern
     * @param propertyName
     */
    private static void setPatternValue(final Stereotype stereotype,
            final DataType primitiveType, List<?> pattern,
            String propertyName) {
        if (pattern != null && pattern.size() > 0) {
            Object value = pattern.get(0);
            primitiveType.setValue(stereotype, propertyName, value);
        }
    }

    /**
     * @param stereotype
     * @param primitiveType
     * @param maxExclusive
     * @param propertyName
     */
    private static void setUpperValue(final Stereotype stereotype,
            final DataType primitiveType, List<?> maxExclusive,
            String propertyName) {
        if (maxExclusive != null && maxExclusive.size() > 0) {
            Object value = maxExclusive.get(0);
            primitiveType.setValue(stereotype, propertyName, value);
        }
    }

    /**
     * @param stereotype
     * @param primitiveType
     * @param maxInclusive
     * @param propertyName
     */
    private static boolean setUpperInclusiveValue(final Stereotype stereotype,
            final DataType primitiveType, List<?> maxInclusive,
            List<?> maxExclusive, String propertyName) {
        if ((maxInclusive == null || maxInclusive.size() == 0)
                && (maxExclusive == null || maxExclusive.size() == 0)) {
            primitiveType.setValue(stereotype, propertyName, Boolean.TRUE);
            return true;
        } else {
            if (maxInclusive != null && maxInclusive.size() > 0) {
                primitiveType.setValue(stereotype, propertyName, Boolean.TRUE);
                return true;
            } else {
                primitiveType.setValue(stereotype, propertyName, Boolean.FALSE);
                return false;
            }
        }
    }

    /**
     * @param stereotype
     * @param primitiveType
     * @param minExclusive
     * @param propertyName
     */
    private static void setLowerValue(final Stereotype stereotype,
            final DataType primitiveType, List<?> minExclusive,
            String propertyName) {
        if (minExclusive != null && minExclusive.size() > 0) {
            Object value = minExclusive.get(0);
            primitiveType.setValue(stereotype, propertyName, value);
        }
    }

    /**
     * @param stereotype
     * @param primitiveType
     * @param minInclusive
     * @param propertyName
     */
    private static boolean setLowerInclusiveValue(final Stereotype stereotype,
            final DataType primitiveType, List<?> minInclusive,
            List<?> minExclusive, String propertyName) {
        if ((minInclusive == null || minInclusive.size() == 0)
                && (minExclusive == null || minExclusive.size() == 0)) {
            primitiveType.setValue(stereotype, propertyName, Boolean.TRUE);
            return true;
        } else {
            if (minInclusive != null && minInclusive.size() > 0) {
                primitiveType.setValue(stereotype, propertyName, Boolean.TRUE);
                return true;
            } else {
                primitiveType.setValue(stereotype, propertyName, Boolean.FALSE);
                return false;
            }
        }
    }

    /**
     * @param stereotype
     * @param primitiveType
     * @param maxLength
     * @param propertyName
     */
    private static void setLengthValue(final Stereotype stereotype,
            final DataType primitiveType, List<?> maxLength,
            String propertyName) {
        if (maxLength != null && maxLength.size() > 0) {
            Object value = maxLength.get(0);
            primitiveType.setValue(stereotype, propertyName, value);
        }
    }

    /**
     * @param element
     * @param id
     * @param fixed
     * @param value
     * @param type
     * @return
     */
    public static boolean setOtherMaxLengthValues(Element element, String id,
            Boolean fixed, Object value, Type type) {
        Stereotype stereotype = applyAndGetAppliedStereotype(element.getModel(),
                element,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION);
        if (stereotype != null) {
            if (id != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_MAX_LENGTH_ID,
                        id);
            }

            if (fixed != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_MAX_LENGTH_FIXED,
                        fixed.toString());
            }

            if (value != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_MAX_LENGTH_VALUE,
                        value.toString());

                if (type != null && type instanceof PrimitiveType) {
                    type = PrimitivesUtil
                            .getBasePrimitiveType((PrimitiveType) type);
                }

                if (element instanceof PrimitiveType && type != null
                        && type.getName().equals(
                                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME)) {
                    PrimitivesUtil.setFacetPropertyValue(
                            (PrimitiveType) element,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH,
                            value.toString(),
                            null);
                } else if (element instanceof Property
                        && ((Property) element).getType().getName().equals(
                                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME)) {
                    Property tmpProperty = (Property) element;
                    if (tmpProperty.getType() instanceof PrimitiveType) {
                        PrimitiveType basePrimitiveType =
                                (PrimitiveType) tmpProperty.getType();
                        PrimitivesUtil.setFacetPropertyValue(basePrimitiveType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH,
                                value.toString(),
                                tmpProperty);
                    } else if (tmpProperty.getType() instanceof Enumeration) {
                        // ready for when enumerations support base primitive
                        // types
                        // Enumeration baseEnumeration =
                        // (Enumeration) tmpProperty.getType();
                        /*
                         * PrimitivesUtil.setFacetPropertyValue(baseEnumeration,
                         * PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH,
                         * value.toString(), tmpProperty);
                         */
                    }
                }
            }
        }
        return true;
    }

    /**
     * @param element
     * @param id
     * @param fixed
     * @param value
     * @param type
     * @return
     */
    public static boolean setOtherFractionDigitsValues(Element element,
            String id, Boolean fixed, Object value, Type type) {
        Stereotype stereotype = applyAndGetAppliedStereotype(element.getModel(),
                element,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION);
        if (stereotype != null) {
            if (id != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_FRACTION_DIGITS_ID,
                        id);
            }

            if (fixed != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_FRACTION_DIGITS_FIXED,
                        fixed.toString());
            }

            if (value != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_FRACTION_DIGITS_VALUE,
                        value.toString());
                if (element instanceof PrimitiveType && type != null
                        && type.getName().equals(
                                PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
                    PrimitivesUtil.setFacetPropertyValue(
                            (PrimitiveType) element,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES,
                            value.toString(),
                            null);
                } else if (element instanceof Property
                        && ((Property) element).getType().getName().equals(
                                PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
                    Property tmpProperty = (Property) element;
                    if (tmpProperty.getType() instanceof PrimitiveType) {
                        PrimitiveType basePrimitiveType =
                                (PrimitiveType) tmpProperty.getType();
                        PrimitivesUtil.setFacetPropertyValue(basePrimitiveType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES,
                                value.toString(),
                                tmpProperty);
                    } else if (tmpProperty.getType() instanceof Enumeration) {
                        // ready for when enumerations support base primitive
                        // types
                        // Enumeration baseEnumeration =
                        // (Enumeration) tmpProperty.getType();
                        /*
                         * PrimitivesUtil.setFacetPropertyValue(baseEnumeration,
                         * PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES,
                         * value.toString(), tmpProperty);
                         */
                    }
                }
            }
        }
        return true;
    }

    /**
     * @param element
     * @param id
     * @param fixed
     * @param value
     * @param type
     * @return
     */
    public static boolean setOtherLengthValues(Element element, String id,
            Boolean fixed, Object value, Type type) {
        Stereotype stereotype = applyAndGetAppliedStereotype(element.getModel(),
                element,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION);
        if (stereotype != null) {
            if (id != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_LENGTH_ID,
                        id);
            }

            if (fixed != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_LENGTH_FIXED,
                        fixed.toString());
            }

            if (value != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_LENGTH_VALUE,
                        value.toString());
            }
        }
        return true;
    }

    /**
     * @param element
     * @param id
     * @param fixed
     * @param value
     * @param type
     * @return
     */
    public static boolean setOtherMaxExclusiveValues(Element element, String id,
            Boolean fixed, Object value, Type type) {
        if (type == null && element instanceof Property) {
            type = ((Property) element).getType();
        }
        Stereotype stereotype = applyAndGetAppliedStereotype(element.getModel(),
                element,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION);
        if (stereotype != null) {
            if (id != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_MAX_EXCLUSIVE_ID,
                        id);
            }

            if (fixed != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_MAX_EXCLUSIVE_FIXED,
                        fixed.toString());
            }

            if (value != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_MAX_EXCLUSIVE_VALUE,
                        value.toString());
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_MAX_INCLUSIVE_VALUE,
                        ""); //$NON-NLS-1$
                if (element instanceof PrimitiveType && type != null) {
                    if (type.getName().equals(
                            PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
                        PrimitivesUtil.setFacetPropertyValue(
                                (PrimitiveType) element,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER,
                                value.toString(),
                                null);
                        PrimitivesUtil.setFacetPropertyValue(
                                (PrimitiveType) element,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER_INCLUSIVE,
                                false,
                                null);
                    } else if (type.getName().equals(
                            PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {
                        Long maxExclusiveIntVal =
                                Long.valueOf(value.toString());
                        maxExclusiveIntVal = maxExclusiveIntVal - 1;
                        PrimitivesUtil.setFacetPropertyValue(
                                (PrimitiveType) element,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER,
                                String.valueOf(maxExclusiveIntVal),
                                null);
                        /*
                         * XPD-5517: no need to set integerLength to a default
                         * value of -1 if nothing is specified in the schema
                         */
                        // PrimitivesUtil
                        // .setFacetPropertyValue((PrimitiveType) element,
                        // PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH,
                        // -1);
                    }
                } else if (element instanceof Property) {
                    Property tmpProperty = (Property) element;
                    if (type instanceof PrimitiveType) {
                        PrimitiveType basePrimitiveType =
                                (PrimitiveType) tmpProperty.getType();
                        if (type.getName().equals(
                                PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
                            PrimitivesUtil.setFacetPropertyValue(
                                    basePrimitiveType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER,
                                    value.toString(),
                                    tmpProperty);
                            PrimitivesUtil.setFacetPropertyValue(
                                    basePrimitiveType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER_INCLUSIVE,
                                    false,
                                    tmpProperty);
                        } else if (type.getName().equals(
                                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {
                            Long maxExclusiveIntVal =
                                    Long.valueOf(value.toString());
                            maxExclusiveIntVal = maxExclusiveIntVal - 1;
                            PrimitivesUtil.setFacetPropertyValue(
                                    basePrimitiveType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER,
                                    String.valueOf(maxExclusiveIntVal),
                                    tmpProperty);
                            /*
                             * XPD-5517: no need to set integerLength to a
                             * default value of -1 if nothing is specified in
                             * the schema
                             */
                            // PrimitivesUtil
                            // .setFacetPropertyValue(basePrimitiveType,
                            // PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH,
                            // -1,
                            // tmpProperty);
                        }
                    } else if (type instanceof Enumeration) {
                        // ready for when enumerations support base primitive
                        // types
                        // Enumeration baseEnumeration =
                        // (Enumeration) tmpProperty.getType();
                        /*
                         * PrimitivesUtil.setFacetPropertyValue(baseEnumeration,
                         * PrimitivesUtil .BOM_PRIMITIVE_FACET_DECIMAL_UPPER,
                         * value.toString(), tmpProperty);
                         */
                    }
                }
            }
        }
        return true;
    }

    /**
     * @param element
     * @param id
     * @param fixed
     * @param value
     * @param type
     * @return
     */
    public static boolean setOtherMaxInclusiveValues(Element element, String id,
            Boolean fixed, Object value, Type type) {
        if (type == null && element instanceof Property) {
            type = ((Property) element).getType();
        }
        Stereotype stereotype = applyAndGetAppliedStereotype(element.getModel(),
                element,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION);
        if (stereotype != null) {
            if (id != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_MAX_INCLUSIVE_ID,
                        id);
            }

            if (fixed != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_MAX_INCLUSIVE_FIXED,
                        fixed.toString());
            }

            if (value != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_MAX_INCLUSIVE_VALUE,
                        value.toString());
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_MAX_EXCLUSIVE_VALUE,
                        ""); //$NON-NLS-1$
                if (element instanceof PrimitiveType && type != null
                        && type.getName().equals(
                                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {
                    PrimitivesUtil.setFacetPropertyValue(
                            (PrimitiveType) element,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER,
                            value.toString(),
                            null);
                    /*
                     * XPD-5517: no need to set integerLength to a default value
                     * of -1 if nothing is specified in the schema
                     */
                    // PrimitivesUtil
                    // .setFacetPropertyValue((PrimitiveType) element,
                    // PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH,
                    // -1);

                } else if (element instanceof Property
                        && ((Property) element).getType().getName().equals(
                                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {
                    Property tmpProperty = (Property) element;
                    if (tmpProperty.getType() instanceof PrimitiveType) {
                        PrimitiveType basePrimitiveType =
                                (PrimitiveType) tmpProperty.getType();
                        PrimitivesUtil.setFacetPropertyValue(basePrimitiveType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER,
                                value.toString(),
                                tmpProperty);
                        /*
                         * XPD-5517: no need to set integerLength to a default
                         * value of -1 if nothing is specified in the schema
                         */
                        // PrimitivesUtil
                        // .setFacetPropertyValue(basePrimitiveType,
                        // PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH,
                        // -1,
                        // tmpProperty);
                    } else if (tmpProperty.getType() instanceof Enumeration) {
                        // ready for when enumerations support base primitive
                        // types
                        // Enumeration baseEnumeration =
                        // (Enumeration) tmpProperty.getType();
                        /*
                         * PrimitivesUtil.setFacetPropertyValue(baseEnumeration,
                         * PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER,
                         * value.toString(), tmpProperty);
                         */
                    }
                } else if (element instanceof PrimitiveType && type != null
                        && type.getName().equals(
                                PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
                    PrimitivesUtil.setFacetPropertyValue(
                            (PrimitiveType) element,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER,
                            value.toString(),
                            null);
                    PrimitivesUtil.setFacetPropertyValue(
                            (PrimitiveType) element,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER_INCLUSIVE,
                            true,
                            null);
                } else if (element instanceof Property
                        && ((Property) element).getType().getName().equals(
                                PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
                    Property tmpProperty = (Property) element;
                    if (tmpProperty.getType() instanceof PrimitiveType) {
                        PrimitiveType basePrimitiveType =
                                (PrimitiveType) tmpProperty.getType();
                        PrimitivesUtil.setFacetPropertyValue(basePrimitiveType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER,
                                value.toString(),
                                tmpProperty);
                        PrimitivesUtil.setFacetPropertyValue(basePrimitiveType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER_INCLUSIVE,
                                true,
                                tmpProperty);
                    } else if (tmpProperty.getType() instanceof Enumeration) {
                        // ready for when enumerations support base primitive
                        // types
                        // Enumeration baseEnumeration =
                        // (Enumeration) tmpProperty.getType();
                        /*
                         * PrimitivesUtil.setFacetPropertyValue(baseEnumeration,
                         * PrimitivesUtil
                         * .BOM_PRIMITIVE_FACET_DECIMAL_UPPER_INCLUSIVE,
                         * value.toString(), tmpProperty);
                         */
                    }
                }
            }
        }
        return true;
    }

    /**
     * @param element
     * @param id
     * @param fixed
     * @param value
     * @param type
     * @return
     */
    public static boolean setOtherMinExclusiveValues(Element element, String id,
            Boolean fixed, Object value, Type type) {
        if (type == null && element instanceof Property) {
            type = ((Property) element).getType();
        }
        Stereotype stereotype = applyAndGetAppliedStereotype(element.getModel(),
                element,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION);
        if (stereotype != null) {
            if (id != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_MIN_EXCLUSIVE_ID,
                        id);
            }

            if (fixed != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_MIN_EXCLUSIVE_FIXED,
                        fixed.toString());
            }

            if (value != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_MIN_EXCLUSIVE_VALUE,
                        value.toString());
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_MIN_INCLUSIVE_VALUE,
                        ""); //$NON-NLS-1$
                if (element instanceof PrimitiveType && type != null) {
                    if (type.getName().equals(
                            PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
                        PrimitivesUtil.setFacetPropertyValue(
                                (PrimitiveType) element,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER,
                                value.toString(),
                                null);
                        PrimitivesUtil.setFacetPropertyValue(
                                (PrimitiveType) element,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER_INCLUSIVE,
                                false,
                                null);
                    } else if (type.getName().equals(
                            PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {
                        Long minExlusiveIntVal = Long.valueOf(value.toString());
                        minExlusiveIntVal = minExlusiveIntVal + 1;
                        PrimitivesUtil.setFacetPropertyValue(
                                (PrimitiveType) element,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER,
                                String.valueOf(minExlusiveIntVal),
                                null);
                    }
                } else if (element instanceof Property) {
                    Property tmpProperty = (Property) element;
                    if (tmpProperty.getType() instanceof PrimitiveType) {
                        PrimitiveType basePrimitiveType =
                                (PrimitiveType) tmpProperty.getType();
                        if (basePrimitiveType.getName().equals(
                                PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
                            PrimitivesUtil.setFacetPropertyValue(
                                    basePrimitiveType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER,
                                    value.toString(),
                                    tmpProperty);
                            PrimitivesUtil.setFacetPropertyValue(
                                    basePrimitiveType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER_INCLUSIVE,
                                    false,
                                    tmpProperty);
                        } else if (basePrimitiveType.getName().equals(
                                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {
                            Long minExlusiveIntVal =
                                    Long.valueOf(value.toString());
                            minExlusiveIntVal = minExlusiveIntVal + 1;
                            PrimitivesUtil.setFacetPropertyValue(
                                    basePrimitiveType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER,
                                    String.valueOf(minExlusiveIntVal),
                                    tmpProperty);
                        }
                    } else if (tmpProperty.getType() instanceof Enumeration) {
                        // ready for when enumerations support base primitive
                        // types
                        // Enumeration baseEnumeration =
                        // (Enumeration) tmpProperty.getType();
                        /*
                         * PrimitivesUtil.setFacetPropertyValue(baseEnumeration,
                         * PrimitivesUtil .BOM_PRIMITIVE_FACET_DECIMAL_LOWER,
                         * value.toString(), tmpProperty);
                         */
                    }
                }
            }
        }
        return true;
    }

    /**
     * @param element
     * @param id
     * @param fixed
     * @param value
     * @param type
     * @return
     */
    public static boolean setOtherMinInclusiveValues(Element element, String id,
            Boolean fixed, Object value, Type type) {
        if (type == null && element instanceof Property) {
            type = ((Property) element).getType();
        }
        Stereotype stereotype = applyAndGetAppliedStereotype(element.getModel(),
                element,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION);
        if (stereotype != null) {
            if (id != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_MIN_INCLUSIVE_ID,
                        id);
            }

            if (fixed != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_MIN_INCLUSIVE_FIXED,
                        fixed.toString());
            }

            if (value != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_MIN_INCLUSIVE_VALUE,
                        value.toString());
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_MIN_EXCLUSIVE_VALUE,
                        ""); //$NON-NLS-1$
                if (element instanceof PrimitiveType && type != null
                        && type.getName().equals(
                                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {
                    PrimitivesUtil.setFacetPropertyValue(
                            (PrimitiveType) element,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER,
                            value.toString(),
                            null);
                } else if (element instanceof Property
                        && ((Property) element).getType().getName().equals(
                                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {
                    Property tmpProperty = (Property) element;
                    if (tmpProperty.getType() instanceof PrimitiveType) {
                        PrimitiveType basePrimitiveType =
                                (PrimitiveType) tmpProperty.getType();
                        PrimitivesUtil.setFacetPropertyValue(basePrimitiveType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER,
                                value.toString(),
                                tmpProperty);
                    } else if (tmpProperty.getType() instanceof Enumeration) {
                        // ready for when enumerations support base primitive
                        // types
                        // Enumeration baseEnumeration =
                        // (Enumeration) tmpProperty.getType();
                        /*
                         * PrimitivesUtil.setFacetPropertyValue(baseEnumeration,
                         * PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER,
                         * value.toString(), tmpProperty);
                         */
                    }
                } else if (element instanceof PrimitiveType && type != null
                        && type.getName().equals(
                                PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
                    PrimitivesUtil.setFacetPropertyValue(
                            (PrimitiveType) element,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER,
                            value.toString(),
                            null);
                    PrimitivesUtil.setFacetPropertyValue(
                            (PrimitiveType) element,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER_INCLUSIVE,
                            true,
                            null);
                } else if (element instanceof Property
                        && ((Property) element).getType().getName().equals(
                                PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
                    Property tmpProperty = (Property) element;
                    if (tmpProperty.getType() instanceof PrimitiveType) {
                        PrimitiveType basePrimitiveType =
                                (PrimitiveType) tmpProperty.getType();
                        PrimitivesUtil.setFacetPropertyValue(basePrimitiveType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER,
                                value.toString(),
                                tmpProperty);
                        PrimitivesUtil.setFacetPropertyValue(basePrimitiveType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER_INCLUSIVE,
                                true,
                                tmpProperty);
                    } else if (tmpProperty.getType() instanceof Enumeration) {
                        // ready for when enumerations support base primitive
                        // types
                        // Enumeration baseEnumeration =
                        // (Enumeration) tmpProperty.getType();
                        /*
                         * PrimitivesUtil.setFacetPropertyValue(baseEnumeration,
                         * PrimitivesUtil
                         * .BOM_PRIMITIVE_FACET_DECIMAL_LOWER_INCLUSIVE,
                         * value.toString(), tmpProperty);
                         */
                    }
                }
            }
        }
        return true;
    }

    /**
     * @param element
     * @param id
     * @param fixed
     * @param value
     * @param type
     * @return
     */
    public static boolean setOtherMinLengthValues(Element element, String id,
            Boolean fixed, Object value, Type type) {
        if (type == null && element instanceof Property) {
            type = ((Property) element).getType();
        }
        Stereotype stereotype = applyAndGetAppliedStereotype(element.getModel(),
                element,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION);
        if (stereotype != null) {
            if (id != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_MIN_LENGTH_ID,
                        id);
            }

            if (fixed != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_MIN_LENGTH_FIXED,
                        fixed.toString());
            }

            if (value != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_MIN_LENGTH_VALUE,
                        value.toString());
            }
        }
        return true;
    }

    /**
     * @param element
     * @param id
     * @param fixed
     * @param value
     * @param type
     * @return
     */
    public static boolean setOtherPatternValues(Element element, String id,
            Boolean fixed, Object value, Type type) {
        Stereotype stereotype = applyAndGetAppliedStereotype(element.getModel(),
                element,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION);
        if (stereotype != null) {
            if (id != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_PATTERN_ID,
                        id);
            }

            if (fixed != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_PATTERN_FIXED,
                        fixed.toString());
            }

            if (value != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_PATTERN_VALUE,
                        value.toString());
            }
        }

        if (element instanceof Property && value != null) {
            applyPropertyPattern((Property) element,
                    (value + ""), //$NON-NLS-1$
                    ((Property) element).getType());
        }

        return true;

    }

    /**
     * @param element
     * @param id
     * @param fixed
     * @param value
     * @param type
     * @return
     */
    public static boolean setOtherTotalDigitsValues(Element element, String id,
            Boolean fixed, Object value, Type type) {
        Stereotype stereotype = applyAndGetAppliedStereotype(element.getModel(),
                element,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION);
        if (stereotype != null) {
            if (id != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_TOTAL_DIGITS_ID,
                        id);
            }

            if (fixed != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_TOTAL_DIGITS_FIXED,
                        fixed.toString());
            }

            if (value != null) {
                if (element instanceof PrimitiveType && type != null
                        && type.getName().equals(
                                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {
                    PrimitivesUtil.setFacetPropertyValue(
                            (PrimitiveType) element,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH,
                            value.toString(),
                            null);
                    setStereotypeValue(element,
                            stereotype,
                            XsdStereotypeUtils.XSD_TOTAL_DIGITS_VALUE,
                            value.toString());
                } else if (element instanceof Property
                        && ((Property) element).getType().getName().equals(
                                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {
                    Property tmpProperty = (Property) element;
                    setStereotypeValue(element,
                            stereotype,
                            XsdStereotypeUtils.XSD_TOTAL_DIGITS_VALUE,
                            value.toString());
                    if (tmpProperty.getType() instanceof PrimitiveType) {
                        PrimitiveType basePrimitiveType =
                                (PrimitiveType) tmpProperty.getType();
                        PrimitivesUtil.setFacetPropertyValue(basePrimitiveType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH,
                                value.toString(),
                                tmpProperty);
                    } else if (tmpProperty.getType() instanceof Enumeration) {
                        // ready for when enumerations support base primitive
                        // types
                        // Enumeration baseEnumeration =
                        // (Enumeration) tmpProperty.getType();
                        /*
                         * PrimitivesUtil.setFacetPropertyValue(baseEnumeration,
                         * PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH,
                         * value.toString(), tmpProperty);
                         */
                    }
                } else if (element instanceof PrimitiveType && type != null
                        && type.getName().equals(
                                PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
                    PrimitivesUtil.setFacetPropertyValue(
                            (PrimitiveType) element,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH,
                            value.toString(),
                            null);
                } else if (element instanceof Property
                        && ((Property) element).getType().getName().equals(
                                PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
                    Property tmpProperty = (Property) element;
                    if (tmpProperty.getType() instanceof PrimitiveType) {
                        PrimitiveType basePrimitiveType =
                                (PrimitiveType) tmpProperty.getType();
                        PrimitivesUtil.setFacetPropertyValue(basePrimitiveType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH,
                                value.toString(),
                                tmpProperty);
                    } else if (tmpProperty.getType() instanceof Enumeration) {
                        // ready for when enumerations support base primitive
                        // types
                        // Enumeration baseEnumeration =
                        // (Enumeration) tmpProperty.getType();
                        /*
                         * PrimitivesUtil.setFacetPropertyValue(baseEnumeration,
                         * PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH,
                         * value.toString(), tmpProperty);
                         */
                    }
                }
            }
        }
        return true;
    }

    /**
     * @param element
     * @param id
     * @param fixed
     * @param value
     * @param type
     * @return
     */
    public static boolean setOtherWhitespaceValues(Element element, String id,
            Boolean fixed, Object value, Type type) {
        Stereotype stereotype = applyAndGetAppliedStereotype(element.getModel(),
                element,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION);
        if (stereotype != null) {
            if (id != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_WHITESPACE_ID,
                        id);
            }

            if (fixed != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_WHITESPACE_FIXED,
                        fixed.toString());
            }

            if (value != null) {
                setStereotypeValue(element,
                        stereotype,
                        XsdStereotypeUtils.XSD_WHITESPACE_VALUE,
                        value.toString());
            }
        }
        return true;
    }

    /**
     * @param element
     * @param value
     * @return
     */
    public static boolean setOtherDefaultValues(Element element, Object value) {
        Stereotype stereotype = applyAndGetAppliedStereotype(element.getModel(),
                element,
                XsdStereotypeUtils.XSD_BASED_RESTRICTION);
        if (stereotype != null) {
            if (value != null) {
                Property tmpProperty = (Property) element;
                if (tmpProperty.getType() instanceof PrimitiveType) {
                    PrimitiveType basePrimitiveType =
                            PrimitivesUtil.getBasePrimitiveType(
                                    (PrimitiveType) tmpProperty.getType());
                    String primTypeName = basePrimitiveType.getName();
                    String propertyStr =
                            (primTypeName.charAt(0) + "").toLowerCase() //$NON-NLS-1$
                                    + primTypeName.substring(1)
                                    + "DefaultValue"; //$NON-NLS-1$
                    if (propertyStr.toLowerCase().startsWith("uri")) { //$NON-NLS-1$
                        propertyStr = "uriDefaultValue"; //$NON-NLS-1$
                    }

                    /*
                     * Sid XPD-8430 In older EMF you can set boolean default
                     * value as a string, in new EMF it has to be boolean else
                     * get a ClassCaseException. In this case it is in the
                     * WSDL/XSD to BPM conversion, it was setting the boolean
                     * default value as "false" instead of Boolean.FALSE
                     * 
                     * So now we convert the string value to boolean if needed
                     * for boolean properties
                     */
                    if (PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME
                            .equals(primTypeName)) {
                        Boolean boolValue = null;
                        if (value instanceof Boolean) {
                            boolValue = (boolean) value;

                        } else if (value instanceof String) {
                            boolValue = Boolean.parseBoolean(((String) value));
                        } else {
                            System.err.println(TransformHelper.class.getName()
                                    + "setOtherDefaultValue(): value for default boolean is not boolean or string equivalent of boolean");
                        }

                        if (boolValue != null) {
                            PrimitivesUtil.setFacetPropertyValue(
                                    (PrimitiveType) tmpProperty.getType(),
                                    propertyStr,
                                    boolValue,
                                    tmpProperty);
                        }

                    } else if (!propertyStr.toLowerCase()
                            .startsWith("attachment") //$NON-NLS-1$
                            && !propertyStr.toLowerCase().startsWith("duration") //$NON-NLS-1$
                            && !propertyStr.toLowerCase().startsWith("id")) { //$NON-NLS-1$
                        PrimitivesUtil.setFacetPropertyValue(
                                (PrimitiveType) tmpProperty.getType(),
                                propertyStr,
                                value.toString(),
                                tmpProperty);
                    }
                } else if (tmpProperty.getType() instanceof Enumeration) {
                    // ready for when enumerations support base primitive
                    // types
                    // Enumeration baseEnumeration =
                    // (Enumeration) tmpProperty.getType();
                    /*
                     * PrimitivesUtil.setFacetPropertyValue(baseEnumeration,
                     * propertyStr, value.toString(), tmpProperty);
                     */
                }
            }
        }
        return true;
    }

    /**
     * @param data
     * @return
     */
    public static String resolveFileName(ImportTransformationData data) {
        if (data.getSourceFile() != null) {
            return data.getSourceFile().getName().replace(".xsd", ""); //$NON-NLS-1$ //$NON-NLS-2$
        } else {
            return "package"; //$NON-NLS-1$
        }
    }

    /**
     * Tranforms the given namespace URI into a legal Java package name.
     * 
     * @param nsURI
     *            the namespace URI
     * @return the corresponding Java package name that should be used for
     *         generated code corresponding to the schema with the provided
     *         namespace URI
     */
    private static String getJavaPackageNameFromNamespaceURI(IProject project,
            String nsURI) {
        // Delegate to implementation in util class
        if (nsURI != null) {
            return NamespaceURIToJavaPackageMapper
                    .getJavaPackageNameFromNamespaceURI(project, nsURI);
        } else {
            return ""; //$NON-NLS-1$
        }
    }

    /**
     * @param classifier
     * @return
     */
    public static List<Object> getTopLevelElements(Model model) {
        return com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper
                .getTopLevelElements(model);
    }

    /**
     * @param xsdElement
     * @return
     */
    public static String getElementName(Object xsdElement) {
        return com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper
                .getElementName(xsdElement);
    }

    /**
     * @param xsdElement
     * @return
     */
    public static Classifier getElementType(Object xsdElement) {
        return com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper
                .getElementType(xsdElement);
    }

    private static String getBlockStrValue(List block) {
        String blockStr = ""; //$NON-NLS-1$
        if (block != null) {
            for (Object tmpBlock : block) {
                if (tmpBlock != null && !tmpBlock.equals("null")) { //$NON-NLS-1$
                    blockStr += tmpBlock.toString();
                    blockStr += " "; //$NON-NLS-1$
                }
            }
            blockStr = blockStr.trim();
        }
        return blockStr;
    }

    private static String getFinalStrValue(List finalVals) {
        String finalStr = ""; //$NON-NLS-1$
        if (finalVals != null) {
            for (Object tmpFinal : finalVals) {
                if (tmpFinal != null && !tmpFinal.equals("null")) { //$NON-NLS-1$
                    finalStr += tmpFinal.toString();
                    finalStr += " "; //$NON-NLS-1$
                }
            }
            finalStr = finalStr.trim();
        }
        return finalStr;
    }

    private static void setUMLDefaults(Property property, String defaultValue) {
        Type type = property.getType();
        if (type != null && type instanceof PrimitiveType) {
            PrimitiveType basePrimitive =
                    PrimitivesUtil.getBasePrimitiveType((PrimitiveType) type);
            if (basePrimitive.getName()
                    .equals(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME)) {
                PrimitivesUtil.setFacetPropertyValue(basePrimitive,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_DEFAULT_VALUE,
                        defaultValue,
                        property);
            } else if (basePrimitive.getName()
                    .equals(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {
                PrimitivesUtil.setFacetPropertyValue(basePrimitive,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_DEFAULT_VALUE,
                        defaultValue,
                        property);
            } else if (basePrimitive.getName()
                    .equals(PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME)) {
                PrimitivesUtil.setFacetPropertyValue(basePrimitive,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_BOOLEAN_DEFAULT_VALUE,
                        defaultValue,
                        property);
            } else if (basePrimitive.getName()
                    .equals(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
                PrimitivesUtil.setFacetPropertyValue(basePrimitive,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_DEFAULT_VALUE,
                        defaultValue,
                        property);
            } else if (basePrimitive.getName()
                    .equals(PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME)) {
                PrimitivesUtil.setFacetPropertyValue(basePrimitive,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DATE_DEFAULT_VALUE,
                        defaultValue,
                        property);
            } else if (basePrimitive.getName()
                    .equals(PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME)) {
                PrimitivesUtil.setFacetPropertyValue(basePrimitive,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DATE_TIME_DEFAULT_VALUE,
                        defaultValue,
                        property);
            } else if (basePrimitive.getName()
                    .equals(PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME)) {
                PrimitivesUtil.setFacetPropertyValue(basePrimitive,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DATE_TIME_TZ_DEFAULT_VALUE,
                        defaultValue,
                        property);
            } else if (basePrimitive.getName()
                    .equals(PrimitivesUtil.BOM_PRIMITIVE_URI_NAME)) {
                PrimitivesUtil.setFacetPropertyValue(basePrimitive,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_URI_DEFAULT_VALUE,
                        defaultValue,
                        property);
            }
        }
    }

    public static void setClassRestriction(Class tmpClass) {
        Stereotype appliedStereotype =
                applyAndGetAppliedStereotype(tmpClass.getModel(),
                        tmpClass,
                        XsdStereotypeUtils.XSD_BASED_CLASS);
        if (appliedStereotype != null) {
            setStereotypeValue(tmpClass,
                    appliedStereotype,
                    XsdStereotypeUtils.XSD_PROPERTY_IS_RESTRICTION,
                    true);
        }
    }

    public static void applyXSDUnionMembers(ImportTransformationData data,
            Type newPrim, String memberTypes, List<String> anonSimpleTypeNames,
            String namespace) {
        Stereotype stereotype = applyAndGetAppliedStereotype(newPrim.getModel(),
                newPrim,
                XsdStereotypeUtils.XSD_BASED_UNION);
        if (stereotype == null) {
            Resource tempXSDProfileResource =
                    newPrim.eResource().getResourceSet()
                            .getResource(URI.createURI(XSD_NOTATION_URI), true);
            applyProfile(newPrim.getModel(), tempXSDProfileResource);
            stereotype = applyAndGetAppliedStereotype(newPrim.getModel(),
                    newPrim,
                    XsdStereotypeUtils.XSD_BASED_UNION);
        }
        if (memberTypes != null && memberTypes.trim().length() > 0) {
            if (memberTypes.equals("null")) { //$NON-NLS-1$
                memberTypes = ""; //$NON-NLS-1$
            }
            String anonSimpleTypeMembers = ""; //$NON-NLS-1$
            Iterator<String> iterator = anonSimpleTypeNames.iterator();
            while (iterator.hasNext()) {
                String anonSimpleTypeName = iterator.next();
                anonSimpleTypeMembers += ", {"; //$NON-NLS-1$
                anonSimpleTypeMembers += namespace;
                anonSimpleTypeMembers += "}"; //$NON-NLS-1$
                anonSimpleTypeMembers += anonSimpleTypeName;
            }
            if (memberTypes.trim().length() == 0) {
                anonSimpleTypeMembers =
                        anonSimpleTypeMembers.replaceFirst(", ", ""); //$NON-NLS-1$ //$NON-NLS-2$
            }
            setStereotypeValue(newPrim,
                    stereotype,
                    XsdStereotypeUtils.XSD_UNION_MEMBER_TYPES,
                    memberTypes.replace("]", "") + anonSimpleTypeMembers + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }
    }

    public static void applyIsAnonUnionSimpleType(Type dataType) {
        Stereotype stereotype =
                applyAndGetAppliedStereotype(dataType.getModel(),
                        dataType,
                        XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE);
        if (stereotype == null) {
            Resource tempXSDProfileResource =
                    dataType.eResource().getResourceSet()
                            .getResource(URI.createURI(XSD_NOTATION_URI), true);
            applyProfile(dataType.getModel(), tempXSDProfileResource);
            stereotype = applyAndGetAppliedStereotype(dataType.getModel(),
                    dataType,
                    XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE);
        }
        setStereotypeValue(dataType,
                stereotype,
                XsdStereotypeUtils.XSD_PROPERTY_IS_ANON_UNION_SIMPLE_TYPE,
                true);
    }

    public static void applyIsStandardUnionSimpleType(Type dataType) {
        Stereotype stereotype =
                applyAndGetAppliedStereotype(dataType.getModel(),
                        dataType,
                        XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE);
        if (stereotype == null) {
            Resource tempXSDProfileResource =
                    dataType.eResource().getResourceSet()
                            .getResource(URI.createURI(XSD_NOTATION_URI), true);
            applyProfile(dataType.getModel(), tempXSDProfileResource);
            stereotype = applyAndGetAppliedStereotype(dataType.getModel(),
                    dataType,
                    XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE);
        }
        setStereotypeValue(dataType,
                stereotype,
                XsdStereotypeUtils.XSD_PROPERTY_IS_STANDARD_UNION_SIMPLE_TYPE,
                true);
    }

    public static void addSequenceDetails(ImportTransformationData data,
            Object rootExplicitGroup, String name, Class umlClass,
            Boolean isChoice, Boolean isAll, String minOccurs, String maxOccurs,
            Object parentSequence, String explicitGroupTree) {
        if (umlClass != null) {
            XSDSequenceWrapper inst = XSDSequenceWrapper
                    .create(umlClass.eResource().getResourceSet());
            if (inst != null) {
                if (parentSequence == null) {
                    Object groupRefParent =
                            data.getGroupRefParent(rootExplicitGroup);
                    if (groupRefParent != null) {
                        parentSequence =
                                data.getSequenceForExplicitGroup(groupRefParent,
                                        umlClass);
                    }
                }

                inst.setName(name);
                inst.setMinOccurs(new Integer(minOccurs));
                if (maxOccurs.equals("unbounded")) { //$NON-NLS-1$
                    inst.setMaxOccurs(new Integer(-1));
                } else {
                    inst.setMaxOccurs(new Integer(maxOccurs));
                }
                inst.setParent(null);
                if (parentSequence != null
                        && parentSequence instanceof XSDSequenceWrapper) {
                    inst.setParent(
                            ((XSDSequenceWrapper) parentSequence).getEObject());
                    Object childrenList =
                            ((XSDSequenceWrapper) parentSequence).getChildren();
                    if (childrenList instanceof EList) {
                        ((EList) childrenList).add(inst.getEObject());
                    }
                }
                inst.setIsChoice(isChoice);
                inst.setIsAll(isAll);
                data.addXsdSequences(rootExplicitGroup, inst, umlClass);

                Stereotype xsdBasedClassStereotype =
                        applyAndGetAppliedStereotype(umlClass.getModel(),
                                umlClass,
                                XsdStereotypeUtils.XSD_BASED_CLASS);

                Object elementsList = umlClass.getValue(xsdBasedClassStereotype,
                        XsdStereotypeUtils.XSD_PROPERTY_SEQUENCE_LIST);
                if (elementsList instanceof EList) {
                    ((EList) elementsList).add(inst.getEObject());
                }
            }
        }
    }

    public static Object getSequenceForExplicitGroup(
            ImportTransformationData data, Object explicitGroup, Class cls) {
        return data.getSequenceForExplicitGroup(explicitGroup, cls);
    }

    public static String getLocalPart(String sourceText) {
        if (sourceText != null) {
            if (sourceText.indexOf(":") != -1) { //$NON-NLS-1$
                String[] tmpSplited = sourceText.split(":"); //$NON-NLS-1$
                return tmpSplited[tmpSplited.length - 1];
            } else {
                return sourceText;
            }
        }
        return null;
    }

    /**
     * @param model
     */
    public static void setStudioVersion(Model model) {
        String version = getBundleVersion(Activator.getDefault().getBundle());
        Stereotype stereotype = null;
        Iterator<Profile> profilesIter = model.getAppliedProfiles().iterator();
        while (profilesIter.hasNext()) {
            Profile profile = profilesIter.next();
            if (profile.getName()
                    .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                Iterator<Stereotype> ownedStereotypesIter =
                        profile.getOwnedStereotypes().iterator();
                while (ownedStereotypesIter.hasNext()) {
                    stereotype = ownedStereotypesIter.next();
                    if (stereotype.getName()
                            .equals(XsdStereotypeUtils.XSD_BASED_MODEL)) {
                        Stereotype appliedStereotype =
                                model.getAppliedStereotype(
                                        stereotype.getQualifiedName());
                        try {
                            if (appliedStereotype != null) {
                                stereotype = appliedStereotype;
                            } else {
                                model.applyStereotype(stereotype);
                            }
                            model.setValue(stereotype,
                                    XsdStereotypeUtils.XSD_STUDIO_VERSION,
                                    version);
                            break;
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
    }

    /**
     * @param bundle
     * @return
     */
    static public String getBundleVersion(Bundle bundle) {
        Dictionary headers = bundle.getHeaders();
        Object version = headers.get(Constants.BUNDLE_VERSION);
        if (version != null) {
            return version.toString();
        }
        return null;

    }

    /**
     * 
     * If the SimpleType passed in is a restriction of an existing
     * PrimitiveType, and has facets extending/restricting those already in the
     * base SimpleTypePrimitiveType, then a new PrimitiveType is created and a
     * generalization set between the two.
     * 
     * 
     * @param data
     * @param model
     * @param pkg
     * @param primType
     * @param simpleType
     * @param elementName
     * @param simpleTypeNamespaceURI
     * @param simpleTypeLocalPart
     * @param restrictionContents
     * @return
     */
    public static PrimitiveType _isSimpleTypeARestrictionWithFacetsOfAnExistingPrimitiveType(
            ImportTransformationData data, Model model, Package pkg,
            DataType primType, Object simpleType, String elementName,
            String simpleTypeNamespaceURI, String simpleTypeLocalPart,
            Object restrictionContents) {

        if (!(primType instanceof PrimitiveType)) {
            return null;
        }
        PrimitiveType newPT = null;

        if (restrictionContents instanceof BasicFeatureMap) {
            BasicFeatureMap restrictionsList =
                    (BasicFeatureMap) restrictionContents;

            if (!restrictionsList.isEmpty() && !simpleTypeNamespaceURI
                    .equals("http://www.w3.org/2001/XMLSchema")) { //$NON-NLS-1$
                // The type has restrictions. Therefore, we SHOULD need a BOM
                // type to represent the restrictions.
                // If the restricted anonymous type is an extension of an
                // existing type (which will already have a corresponding BOM
                // type), then will will need to create 1) a new BOM type and 2)
                // a generalization from the new type to its super-type
                newPT = Xsd2BomFactory.createPrimitiveType(data,
                        pkg,
                        elementName,
                        (DynamicEObjectImpl) simpleType);

                applyPrimitiveStereotypeValues(newPT,
                        newPT.getName() + "Type", //$NON-NLS-1$
                        null,
                        null,
                        null,
                        simpleTypeLocalPart);

                // Create generalization
                Generalization genz = Xsd2BomFactory.createGeneralization();
                genz.setSpecific(newPT);
                genz.setGeneral(primType);
            }
        }
        return newPT;
    }

    /**
     * 
     * If the SimpleType passed in is a restriction of an existing
     * PrimitiveType, and has facets extending/restricting those already in the
     * base SimpleTypePrimitiveType, then a new PrimitiveType is created and a
     * generalization set between the two.
     * 
     * 
     * @param data
     * @param model
     * @param pkg
     * @param enumeration
     * @param simpleType
     * @param elementName
     * @param simpleTypeNamespaceURI
     * @param simpleTypeLocalPart
     * @param restrictionContents
     * @return
     */
    public static Enumeration _isSimpleTypeARestrictionWithFacetsOfAnExistingEnumeration(
            ImportTransformationData data, Model model, Package pkg,
            Enumeration enumeration, Object simpleType, String elementName,
            String simpleTypeNamespaceURI, String simpleTypeLocalPart,
            Object restrictionContents) {

        Enumeration newEnum = null;

        return newEnum;
    }

    /**
     * 
     * XPD-3197: Some anonymous SimpleType constructs in Top Level Elements do
     * not need to be interpreted as "anonymous" is the BOM sense as there will
     * already be a BOM type created for use as the type of the anonymous type.
     * 
     * @param simpleTypeNamespaceURI
     * @param simpleTypeLocalPart
     * @param restrictionContents
     * @return
     */
    public static boolean _isTypeReallyAnonymous(ImportTransformationData data,
            Model model, Package pkg, DataType primType, Object simpleType,
            String elementName, String simpleTypeNamespaceURI,
            String simpleTypeLocalPart, Object restrictionContents) {

        boolean ret = false;

        if (restrictionContents instanceof BasicFeatureMap) {
            BasicFeatureMap restrictionsList =
                    (BasicFeatureMap) restrictionContents;

            // if (restrictionsList.isEmpty()) {
            if (simpleTypeNamespaceURI
                    .equals("http://www.w3.org/2001/XMLSchema")) { //$NON-NLS-1$
                for (XsdTypes type : XsdTypes.values()) {
                    String s = type.toString();

                    if (s.equals(simpleTypeLocalPart.toUpperCase())) {
                        ret = true;
                    }
                }
            }
            // }
        }

        return ret;

    }

    /**
     * 
     * XPD-3197: Some anonymous SimpleType constructs in Top Level Elements do
     * not need to be interpreted as "anonymous" is the BOM sense as there will
     * already be a BOM type created for use as the type of the anonymous type.
     * 
     * @param simpleTypeNamespaceURI
     * @param simpleTypeLocalPart
     * @param restrictionContents
     * @return
     */
    public static boolean _isSimpleTypeReallyRepresentingAnonymousTLE(
            Object simpleTypeDeo) {
        boolean ret = false;
        DeoXsdSimpleTypeReader stReader =
                new DeoXsdSimpleTypeReader((DynamicEObjectImpl) simpleTypeDeo);
        String namespaceURI = stReader.getBaseType().getNamespaceURI();
        String localPart = stReader.getBaseType().getLocalPart();

        if (namespaceURI != null && localPart != null) {
            if (namespaceURI.equals("http://www.w3.org/2001/XMLSchema")) { //$NON-NLS-1$
                for (XsdTypes type : XsdTypes.values()) {
                    String s = type.toString();

                    if (s.equals(localPart.toUpperCase())) {
                        ret = true;
                    }
                }
            }
        }
        return ret;
    }

    /**
     * Implemented for XPD-2272. A WSDL Part may reference another type or
     * element in an imported WSDL.
     * 
     * This method accepts the Part as a DynamicEobject and its parent Message
     * as a QName.
     * 
     * The details required to generate a proxy data type are returned as a
     * concatenated string in the format:</br>
     * </br>
     * 
     * partName::namespaceURI::bomTypeName
     * 
     * 
     * @param data
     * @param part
     * @param parentMessage
     * @return
     */

    /*
     * XPD-5203: no longer need this method. as wsdl importing wsdl case is
     * handled slightly differently in wsdl2bom.ext
     */

    // public static String _collectWsdlPartElementOrTypeDetailsForURI(
    // ImportTransformationData data, Object part, Object parentMessage) {
    // String bomTypeUriDetails = null;
    // QName qPartElement = null;
    // QName qPartType = null;
    // String partName = null;
    // String namespaceURI = null;
    //
    // if (part instanceof DynamicEObjectImpl) {
    // // Pull out all the information (feature/value) from the
    // // DynamicEbject representing the Part
    // DynamicEObjectImpl eo = (DynamicEObjectImpl) part;
    //
    // for (EStructuralFeature feat : eo.eClass().getEStructuralFeatures()) {
    // if (feat.getName().equals("name")) { //$NON-NLS-1$
    // Object value = eo.eGet(feat);
    // if (value instanceof String) {
    // partName = (String) value;
    // }
    // } else if (feat.getName().equals("type")) { //$NON-NLS-1$
    // Object value = eo.eGet(feat);
    // if (value instanceof QName) {
    // qPartType = (QName) value;
    // namespaceURI = qPartType.getNamespaceURI();
    // }
    // } else if (feat.getName().equals("element")) { //$NON-NLS-1$
    // Object value = eo.eGet(feat);
    // if (value instanceof QName) {
    // qPartElement = (QName) value;
    // namespaceURI = qPartElement.getNamespaceURI();
    // }
    // }
    // }
    //
    // // Load this message part from its WSDL model so that we can access
    // // the
    // if (parentMessage instanceof QName) {
    // QName qParentMessage = (QName) parentMessage;
    // TransactionalEditingDomain ed = data.getEditingDomain();
    // String path = data.getCtx().getSourceFileAbsolutePath();
    // URI srcURI = URI.createFileURI(path);
    // Resource wsdlResource = ed.loadResource(srcURI.toString());
    //
    // if (wsdlResource.getContents().get(0) instanceof Definition) {
    // Definition def =
    // (Definition) wsdlResource.getContents().get(0);
    // Message mesg = (Message) def.getMessage(qParentMessage);
    // Part pt = (Part) mesg.getPart(partName);
    // String bomTypeName = null;
    // if (qPartElement != null) {
    // XSDElementDeclaration elementDeclaration =
    // pt.getElementDeclaration();
    // bomTypeName =
    // getBomTypeNameFromTopLevelElement(elementDeclaration);
    // } else if (qPartType != null) {
    // javax.xml.namespace.QName ptTypeQname =
    // pt.getTypeName();
    // bomTypeName = ptTypeQname.getLocalPart();
    // namespaceURI = ptTypeQname.getNamespaceURI();
    // }
    // bomTypeUriDetails =
    // partName + "::" + namespaceURI + "::" + bomTypeName; //$NON-NLS-1$
    // //$NON-NLS-2$
    // }
    // }
    // }
    // return bomTypeUriDetails;
    // }

    /**
     * 
     * A message part element/type may need to be resolved to an external schema
     * element/type, via a wsdl:import, for example to create a proxy data type
     * in the BOM UML. This method resolves the data type and concatenates the
     * information needed to generate a proxy in the following string format:
     * 
     * partName::namespaceURI::bomTypeName
     * 
     * @param data
     * @param message
     * @return
     */

    /*
     * XPD-5203: no longer need this method. as wsdl importing wsdl case is
     * handled slightly differently in wsdl2bom.ext
     */

    // public static List<String> _collectWsdlMessagePartElementTypeUriInfo(
    // ImportTransformationData data, Object message) {
    // QName qMessage = null;
    // List<String> typeURIs = new ArrayList<String>();
    //
    // if (message instanceof QName) {
    // qMessage = (QName) message;
    //
    // /*
    // * Step1. Load the schema model corresponding to the namespace in
    // * the QName.
    // */
    //
    // /*
    // * The QName's namespaceURI should match the namespace of an
    // * imported WSDL, the detail of which should already be cached in
    // * the data context. It is used as the key to retrieve the wsdl
    // * definition
    // */
    // DynamicEObjectImpl dynamicEObjectImpl =
    // data.getCtx().getWsdlDefinitions()
    // .get(qMessage.getNamespaceURI());
    //
    // if (dynamicEObjectImpl instanceof Definition) {
    // Definition def = (Definition) dynamicEObjectImpl;
    //
    // Message importedMessage = (Message) def.getMessage(qMessage);
    // Map parts = importedMessage.getParts();
    // Collection<Part> values = parts.values();
    //
    // /*
    // * For each part, extract the extract the required details and
    // * concatenate.
    // */
    // for (Part pt : values) {
    // String ptName = pt.getName();
    // String bomTypeName = null;
    // String namespaceURI = null;
    // if (pt.getElementName() != null) {
    // javax.xml.namespace.QName ptElemQname =
    // pt.getElementName();
    // namespaceURI = ptElemQname.getNamespaceURI();
    // XSDElementDeclaration elementDeclaration =
    // pt.getElementDeclaration();
    // bomTypeName =
    // getBomTypeNameFromTopLevelElement(elementDeclaration);
    // } else if (pt.getTypeName() != null) {
    // javax.xml.namespace.QName ptTypeQname =
    // pt.getTypeName();
    // bomTypeName = ptTypeQname.getLocalPart();
    // namespaceURI = ptTypeQname.getNamespaceURI();
    // }
    // typeURIs.add(ptName + "::" + namespaceURI + "::" //$NON-NLS-1$
    // //$NON-NLS-2$
    // + bomTypeName);
    // }
    // }
    // }
    // return typeURIs;
    // }

    /**
     * Appends the "Type" suffix if an anonymous complex type is embedded in the
     * Element
     * 
     * @param elem
     * @return
     */
    // private static String getBomTypeNameFromTopLevelElement(
    // XSDElementDeclaration elem) {
    // String name = null;
    // XSDTypeDefinition type = elem.getType();
    //
    // if (type != null && type.getName() == null) {
    // if (type.getName() == null) {
    // // Type is anonymous so append "Type"
    // name = elem.getName() + "Type";
    // }
    // } else {
    // name = elem.getName();
    // }
    // return name;
    // }

    /**
     * 
     * Parses a string of format:
     * 
     * partName::namespaceURI::bomTypeName
     * 
     * returned by _collectWsdlMessagePartElementTypeUriInfo() and uses the
     * pieces to generate a proxy data type. *
     * 
     * @param data
     * @param element
     * @param details
     */
    public static void _setDetailsForProxy(ImportTransformationData data,
            TypedElement element, String details) {
        // ParseDetails
        String[] pieces = details.split("::"); //$NON-NLS-1$
        if (pieces.length == 3) {

            String elementName = pieces[0];
            String namespace = pieces[1];
            String bomType = pieces[2];

            element.setName(elementName);
            EObject proxyType = UMLPackage.eINSTANCE.getDataType().getEPackage()
                    .getEFactoryInstance()
                    .create(UMLPackage.eINSTANCE.getDataType());

            // Convert namespace to Bom model name
            String umlModelName =
                    getJavaPackageNameFromNamespaceURI(data.getProject(),
                            namespace);
            URI uri = URI.createURI(umlModelName + ".bom"); //$NON-NLS-1$
            uri = uri.appendFragment(umlModelName + bomType);
            ((InternalEObject) proxyType).eSetProxyURI(uri);

            if (proxyType instanceof Type) {
                element.setType((Type) proxyType);
            }
        }
    }

    private static Type createProxyType(IProject project, String namespace,
            String trgTypename) {
        EObject proxyType = UMLPackage.eINSTANCE.getDataType().getEPackage()
                .getEFactoryInstance()
                .create(UMLPackage.eINSTANCE.getDataType());

        // Convert namespace to Bom model name
        String umlModelName =
                getJavaPackageNameFromNamespaceURI(project, namespace);
        URI uri = URI.createURI(umlModelName + ".bom"); //$NON-NLS-1$
        uri = uri.appendFragment(umlModelName + trgTypename);
        ((InternalEObject) proxyType).eSetProxyURI(uri);

        if (proxyType instanceof Type) {
            return (Type) proxyType;
        } else {
            return null;
        }

    }

    /**
     * Implemented for XPD-3202
     * 
     * The context in which the previous OAW code was used was to match a
     * namespace string to a schemaType (dynamicEObject) from a list of
     * schemaTypes, and that schemaType would contain the type whose name
     * corresponds to the localPart string.
     * 
     * However, the OAW code only looked for the first occurence of the
     * namespace in the list of schemas. THIS IS AN INVALID ASSUMPTION since
     * schemaType objects can have duplicate namspaces. The list may contain
     * schematypes with the same namespace originating from xsd:import and
     * xsd:include constructs.
     * 
     * @param data
     * @param schemaType
     * @param prefix
     * @param namespaceURI
     * @param localPart
     * @return
     */
    public static Object _FindSchemaTypeForNamespaceContainingThisType(
            ImportTransformationData data, Object schemaType, String prefix,
            String namespaceURI, String localPart, Object schemaTypeList) {

        Object returnSchemaType = null;

        if (!(schemaType instanceof DynamicEObjectImpl)) {
            return null;
        }

        DeoXsdSchemaReader parentSchemaReader =
                new DeoXsdSchemaReader((DynamicEObjectImpl) schemaType);

        if (parentSchemaReader.getTargetNamespace().equals(namespaceURI)) {
            // First, check that the type is in this schema
            if (isNamedComplexTypeInThisSchema(parentSchemaReader, localPart)) {
                returnSchemaType = schemaType;
            } else {
                // It could be in INLCUDED schemas which will be in the list of
                // schemas passed in
                if (!(parentSchemaReader.getIncludes().isEmpty())) {
                    for (Object schemaFromList : (ArrayList<?>) schemaTypeList) {
                        if (schemaFromList instanceof DynamicEObjectImpl) {
                            DeoXsdSchemaReader schRdr = new DeoXsdSchemaReader(
                                    (DynamicEObjectImpl) schemaFromList);
                            if (namespaceURI
                                    .equals(schRdr.getTargetNamespace())) {
                                // Again, there may be more than one schema with
                                // this tns, so inspect the schema for the type
                                boolean found =
                                        isNamedComplexTypeInThisSchema(schRdr,
                                                localPart);
                                if (found) {
                                    returnSchemaType = schemaFromList;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (returnSchemaType == null) {
            // The type exists in an external schema probably with a different
            // namespace and should be in the schemaType list.
            if (schemaTypeList instanceof ArrayList<?>) {
                ArrayList<?> lstSchemas = (ArrayList<?>) schemaTypeList;
                for (Object schObj : lstSchemas) {
                    if (schObj instanceof DynamicEObjectImpl) {
                        DeoXsdSchemaReader schRdr = new DeoXsdSchemaReader(
                                (DynamicEObjectImpl) schObj);

                        if (namespaceURI.equals(schRdr.getTargetNamespace())) {
                            // Again, there may be more than one schema with
                            // this tns, so inspect the schema for the type
                            boolean found =
                                    isNamedComplexTypeInThisSchema(schRdr,
                                            localPart);
                            if (found) {
                                returnSchemaType = schObj;
                                break;
                            }
                        }
                    }
                }
            }
        }

        return returnSchemaType;

    }

    /**
     * @param schema
     * @param complexTypeName
     * @return
     */
    private static boolean isNamedComplexTypeInThisSchema(
            DeoXsdSchemaReader schema, String complexTypeName) {
        FeatureEList<?> complexTypes = schema.getComplexTypes();

        for (Object obj : complexTypes) {
            DeoXsdComplexTypeReader ctr =
                    new DeoXsdComplexTypeReader((DynamicEObjectImpl) obj);
            if (complexTypeName.equals(ctr.getName())) {
                return true;
            }
        }

        return false;

    }

    /**
     * The schemaType passed in from Oaw is a dynamicEObject.
     * 
     * @param SchemaType
     * @return
     */
    public static String _getSchemaTypeNamespace(Object SchemaType) {
        if (SchemaType != null) {
            DeoXsdSchemaReader schRdr =
                    new DeoXsdSchemaReader((DynamicEObjectImpl) SchemaType);
            return schRdr.getTargetNamespace();
        } else {
            return "null"; //$NON-NLS-1$
        }
    }

    /**
     * XPD-3877
     * 
     * This method is called when the following tasks are required:</br>
     * 
     * <ul>
     * <li>create enumeration represented by an anonymous SimpleType contained
     * within a top level element</li>
     * <li></li>
     * </ul>
     * 
     * Note that this is called from the OAW method that parses TLE so we know
     * that new Enumeration will be top level
     * 
     * @return
     */
    public static Enumeration _createEnumerationForAnonSimpleType(
            ImportTransformationData data, String name, Type enumType,
            Object simpleType, Object schemaType, Model model, Package pkg,
            List enumIDList, List enumValueList) {

        Enumeration enumeration = UMLFactory.eINSTANCE.createEnumeration();
        enumeration.setName(name);
        enumeration.setPackage(pkg);
        setUniqueId(data, enumeration);
        // setAnonStereotypeValue(enumeration, false);

        DeoXsdSimpleTypeReader stReader =
                new DeoXsdSimpleTypeReader((DynamicEObjectImpl) simpleType);
        QName baseType = stReader.getBaseType();

        if (stReader.getRestriction() != null) {
            // Need to set a generalization
            Generalization generalzn =
                    UMLFactory.eINSTANCE.createGeneralization();
            generalzn.setSpecific(enumeration);
            EObject pxy =
                    EcoreUtil.create(UMLPackage.eINSTANCE.getEnumeration());
            // Convert namespace to Bom model name
            String umlModelName =
                    getJavaPackageNameFromNamespaceURI(data.getProject(),
                            baseType.getNamespaceURI());
            URI uri = URI.createURI(umlModelName + ".bom"); //$NON-NLS-1$
            uri = uri.appendFragment(umlModelName + baseType.getLocalPart());
            ((InternalEObject) pxy).eSetProxyURI(uri);
            generalzn.setGeneral((Classifier) pxy);

            applyPrimitiveStereotypeValues(enumeration,
                    name + "Type", //$NON-NLS-1$
                    stReader.getID(),
                    null,
                    stReader.getRestrictionID(),
                    stReader.getBaseType().getLocalPart());

            if (stReader.getBaseType().getNamespaceURI()
                    .equals("http://www.w3.org/2001/XMLSchema")) { //$NON-NLS-1$
                for (XsdTypes type : XsdTypes.values()) {
                    String s = type.toString();

                    if (s.equals(stReader.getBaseType().getLocalPart()
                            .toUpperCase())) {
                    }
                }
            }

            createEnumerationLiteral(enumeration, enumIDList, enumValueList);
        }

        return enumeration;
    }

    /**
     * 
     * XPD-3877
     * 
     * This method is called when the following tasks are required:</br>
     * 
     * <ul>
     * <li>create a generalisation between the Enumeration passed in (specific)
     * to the Enumeration(/XPD-3896: PrimitiveType) referenced inside the
     * simpleType restriction (general)</li>
     * </ul>
     * 
     * @param data
     * @param enumSpecific
     * @param simpleTypeWithRestriction
     * @param superType
     *            (XPD-3896) - type of the parent/super class of the enumeration
     *            to be set in generalisation
     */
    public static void _setGeneralizationForSimpleTypeRestrictionOfEnumeration(
            ImportTransformationData data, Enumeration enumSpecific,
            Object simpleTypeWithRestriction, Type superType) {

        if (simpleTypeWithRestriction instanceof DynamicEObjectImpl) {

            DeoXsdSimpleTypeReader stReader = new DeoXsdSimpleTypeReader(
                    (DynamicEObjectImpl) simpleTypeWithRestriction);

            // QName baseType = stReader.getBaseType();

            if (stReader.getRestriction() != null) {
                // Need to set a generalisation
                Generalization generalzn =
                        UMLFactory.eINSTANCE.createGeneralization();
                generalzn.setSpecific(enumSpecific);
                // EObject pxy =
                // EcoreUtil.create(UMLPackage.eINSTANCE.getEnumeration());
                // // Convert namespace to Bom model name
                // String umlModelName =
                // getJavaPackageNameFromNamespaceURI(baseType
                // .getNamespaceURI());
                // URI uri = URI.createURI(umlModelName + ".bom"); //$NON-NLS-1$
                // //$NON-NLS-2$
                // uri =
                // uri.appendFragment(umlModelName
                // + baseType.getLocalPart()); //$NON-NLS-1$
                // ((InternalEObject) pxy).eSetProxyURI(uri);
                // generalzn.setGeneral((Classifier) pxy);

                /*
                 * XPD-3896: An enumeration can be generalised from another
                 * enumeration or base primitive type. setting the super type in
                 * generalisation if it is enumeration or primitive type
                 */
                if (superType instanceof Enumeration
                        || superType instanceof PrimitiveType) {
                    generalzn.setGeneral((Classifier) superType);
                }
            }
        }
    }

    /**
     * searches for the given class name in its package first, if not found then
     * looks in other packages
     * 
     * @param data
     * @param umlPkg
     * @param classNameToSearch
     * @param lookInOtherPackages
     * @return <code>uml Classifier</code> for the class name being searched
     *         <code>null</code> otherwise
     */
    public static Classifier _getClassByNameExt(ImportTransformationData data,
            Package umlPkg, String classNameToSearch,
            Boolean lookInOtherPackages) {

        EList<PackageableElement> packagedElements =
                umlPkg.getPackagedElements();
        for (PackageableElement packageableElement : packagedElements) {

            if (packageableElement instanceof Classifier) {

                Classifier classifier = (Classifier) packageableElement;
                boolean anonClass = isAnonClass(classifier);
                if (!anonClass
                        && classifier.getName().equals(classNameToSearch)) {

                    return (Classifier) packageableElement;
                }
            }
        }

        if (lookInOtherPackages) {

            List<Model> otherResultModels = data.getOtherResultModels();
            for (Model model : otherResultModels) {

                if (model != umlPkg) {

                    EList<PackageableElement> packagedElements2 =
                            model.getPackagedElements();
                    for (PackageableElement packageableElement : packagedElements2) {

                        if (packageableElement instanceof Classifier) {

                            Classifier classifier =
                                    (Classifier) packageableElement;
                            boolean anonClass = isAnonClass(classifier);
                            if (!anonClass && classifier.getName()
                                    .equals(classNameToSearch)) {

                                return (Classifier) packageableElement;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * get the max length based on the restriction's base type and the specified
     * initial octet max length
     * 
     * @param data
     * @param baseType
     * @param maxValue
     * @return
     */
    public static Object _getActualLengthFromRestrictionForSimpleType(
            ImportTransformationData data, String baseType, Object maxValue) {

        if (null != maxValue) {

            String maxValueStr = maxValue.toString();
            int originalLength = Integer.parseInt(maxValueStr);

            if ("base64binary".equalsIgnoreCase(baseType)) { //$NON-NLS-1$
                /*
                 * if base data type is base64binary then compute the max length
                 * as mentioned in http://en.wikipedia.org/wiki/Base64
                 * 
                 * The wiki page gives the formula as 4[n/3], where the special
                 * brackets mean ceiling (i.e. the division rounds up rather
                 * than down).
                 * 
                 * something like-> 4 * ((originalLength / 3) + ((originalLength
                 * % 3 != 0 ? 1 : 0)))
                 * 
                 * which can be derived to simple formula as used below
                 */

                int actualLength = 4 * ((originalLength + 2) / 3);
                return actualLength;
            } else if ("hexbinary".equalsIgnoreCase(baseType)) { //$NON-NLS-1$

                /*
                 * if base data type is hexbinary then the actual length is
                 * original length times 2
                 */
                int actualLength = originalLength * 2;
                return actualLength;
            }
            /*
             * if it is any other type then max length is the original length
             */
            return originalLength;
        }
        /* return null if max value is not specified */
        return maxValue;
    }
}
