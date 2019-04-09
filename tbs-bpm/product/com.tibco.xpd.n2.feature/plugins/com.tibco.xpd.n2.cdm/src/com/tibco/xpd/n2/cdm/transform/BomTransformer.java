/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.cdm.transform;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.bpm.da.dm.api.Attribute;
import com.tibco.bpm.da.dm.api.BaseType;
import com.tibco.bpm.da.dm.api.DataModel;
import com.tibco.bpm.da.dm.api.StructuredType;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.n2.cdm.internal.Messages;

/**
 * Transforms BOM model to Case Data Model (CDM).
 *
 * @author jarciuch
 * @since 5 Mar 2019
 */
public class BomTransformer {

    /**
     * Name of the system property that if set to 'true" will allow to retrieve
     * fully qualified type names from the unresolved proxys.
     */
    private static final String ALLOW_EXPORT_BOM_UNRESOLVED_PROXY =
            "allowExportBomUnresolvedProxy"; //$NON-NLS-1$

    /** Transforms BOM constraints to CDM constraints. */
    private static final BomConstraintTransformer CONSTRANT_TRANSFORMER =
            new BomConstraintTransformer();

    /**
     * Prefix for CDM base type.
     */
    private final static String BASE_PREFIX = "base:"; //$NON-NLS-1$

    /* CDM base type literals. */
    private final static String CDM_BASE_TEXT_TYPE =
            BASE_PREFIX + BaseType.TEXT.getName();

    private final static String CDM_BASE_NUMBER_TYPE =
            BASE_PREFIX + BaseType.NUMBER.getName();

    private final static String CDM_BASE_DATE_TYPE =
            BASE_PREFIX + BaseType.DATE.getName();

    private final static String CDM_BASE_TIME_TYPE =
            BASE_PREFIX + BaseType.TIME.getName();

    private final static String CDM_BASE_BOOLEAN_TYPE =
            BASE_PREFIX + BaseType.BOOLEAN.getName();

    /**
     * Transforms BOM model.
     * 
     * @param bomModel
     *            the source BOM model.
     * @return the Case Data Model.
     */
    public DataModel transformBomModel(Model bomModel) {
        DataModel cdmModel = DataModel.newDataModel();
        cdmModel.setNamespace(bomModel.getName());
        for (PackageableElement packageableElement : bomModel
                .getPackagedElements()) {
            if (packageableElement instanceof org.eclipse.uml2.uml.Class) {
                Class bomClass = (Class) packageableElement;
                StructuredType cdmStructuredType =
                        transformClass(bomClass, cdmModel);
            }
        }
        return cdmModel;
    }

    /**
     * Transforms BOM class to structured class.
     * 
     * @param bomClass
     *            the source BOM class.
     * @param cdmModel
     *            the Case Data Model.
     * @return StructuredType representing transformed BOM class.
     */
    private StructuredType transformClass(Class bomClass, DataModel cdmModel) {
        StructuredType cdmType = cdmModel.newStructuredType();
        cdmType.setName(bomClass.getName());
        cdmType.setLabel(getLabel(bomClass));
        cdmType.setDescription(getFirstOwnedComment(bomClass));

        // Attributes.
        for (Property bomAttribute : bomClass.getAttributes()) {
            Attribute cdmAttribute = transformAttribute(bomAttribute, cdmType);
        }

        return cdmType;
    }

    /**
     * Transforms BOM Property (aka attribute). The resulting attribute is also
     * added to a cdmType.
     * 
     * @param bomAttribute
     *            the BOM attribute to be transformed.
     * @param cdmType
     *            the CDM type that will contain the transformed attribute.
     * @return the CDM attribute.
     */
    private Attribute transformAttribute(Property bomAttribute,
            StructuredType cdmType) {
        Attribute cdmAttribute = cdmType.newAttribute();
        cdmAttribute.setName(bomAttribute.getName());
        cdmAttribute.setLabel(getLabel(bomAttribute));
        cdmAttribute.setDescription(getFirstOwnedComment(bomAttribute));
        String cdmAttributeType =
                transformType(bomAttribute.getType(), bomAttribute);
        cdmAttribute.setType(cdmAttributeType);
        int upper = bomAttribute.getUpper();
        int lower = bomAttribute.getLower();
        cdmAttribute.setIsMandatory(lower != 0);
        // -1 means +infinity (unbounded).
        cdmAttribute.setIsArray(upper == -1 || upper > 1);
        CONSTRANT_TRANSFORMER.getContraints(bomAttribute).stream().forEach(
                c -> cdmAttribute.newConstraint(c.getName(), c.getValue()));
        CONSTRANT_TRANSFORMER.getAllowedValues(bomAttribute).stream()
                .forEach(literal -> cdmAttribute.newAllowedValue(
                        /* label */ getLabel(literal),
                        /* value */ literal.getName()));
        String defaultValue =
                CONSTRANT_TRANSFORMER.getDefaultValue(bomAttribute);
        if (defaultValue != null) {
            cdmAttribute.setDefaultValue(defaultValue);
        }
        return cdmAttribute;
    }

    /**
     * Transforms BOM type to CDM string type representation.
     * 
     * @param bomType
     *            the BOM type.
     * @param contextProperty
     *            context bom property that has the type.
     * @return the string representation of a CDM type.
     */
    private String transformType(Type bomType, Property contextProperty) {
        if (bomType instanceof PrimitiveType) {
            String baseType = PrimitivesUtil
                    .getBasePrimitiveType((PrimitiveType) bomType).getName();
            switch (baseType) {
            case PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME:
                return CDM_BASE_TEXT_TYPE;
            case PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME:
                return CDM_BASE_NUMBER_TYPE;
            case PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME:
                return CDM_BASE_NUMBER_TYPE;
            case PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME:
                return CDM_BASE_BOOLEAN_TYPE;
            case PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME:
                return CDM_BASE_DATE_TYPE;
            case PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME:
                return CDM_BASE_TIME_TYPE;
            case PrimitivesUtil.BOM_PRIMITIVE_ENUMERATION_NAME:
                return CDM_BASE_TEXT_TYPE;

            // TODO These cases will be supported when the CDM model is updated.
            // case PrimitivesUtil.BOM_PRIMITIVE_ID_NAME:
            // return BASE_PREFIX + BaseType.ID;
            // case PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME:
            // return BASE_PREFIX + BaseType.DATE_TIME_TZ;
            // case PrimitivesUtil.BOM_PRIMITIVE_URI_NAME:
            // return BASE_PREFIX + BaseType.URI;
            default:
                return CDM_BASE_TEXT_TYPE; // $NON-NLS-1$
            }
        } else if (bomType instanceof Class) {
            if (!bomType.eIsProxy()) {
                String typePackageName = bomType.getPackage().getName();
                String typeName = bomType.getName();
                String localPackageName = getPackageName(contextProperty);
                if (localPackageName != null
                        && localPackageName.equals(typePackageName)) {
                    return typeName;
                }
                return typePackageName + BOMWorkingCopy.JAVA_PACKAGE_SEPARATOR
                        + typeName;
            } else {

                // Unresolved proxy situation. Should never happen if all
                // necessary referenced files are in the workspace.
                String javaFQType = getTypeNameFromUnresolvedProxy(bomType);
                boolean allowBomUnresolvedProxy = Boolean.getBoolean(
                        System.getProperty(ALLOW_EXPORT_BOM_UNRESOLVED_PROXY,
                                "false")); //$NON-NLS-1$
                if (javaFQType != null && allowBomUnresolvedProxy) {
                    return javaFQType;
                }
                String errorMessage =
                        String.format(Messages.BomTransformer_cantResolveType,
                                (javaFQType != null) ? javaFQType : ""); //$NON-NLS-1$
                throw new RuntimeException(errorMessage);
            }
        } else if (bomType instanceof Enumeration) {
            return CDM_BASE_TEXT_TYPE; // $NON-NLS-1$
        }
        return null;
    }

    /**
     * Returns package name of the attribute or null.
     * 
     * @param attribute
     *            the attribute
     * @return package name of an attribute or null.
     */
    private String getPackageName(Property attribute) {
        if (attribute.getOwner() instanceof Type
                && ((Type) attribute.getOwner()).getPackage() != null) {
            return ((Type) attribute.getOwner()).getPackage().getName();
        }
        return null;
    }

    /**
     * Gets first comment body from an element.
     * 
     * @param element
     *            element.
     * @return body of the first element's comment or 'null'.
     */
    private String getFirstOwnedComment(Element element) {
        if (element != null && !element.getOwnedComments().isEmpty()) {
            Comment firstComment = element.getOwnedComments().get(0);
            return firstComment != null ? firstComment.getBody() : null;
        }
        return null;
    }

    /** Gets label for a BOM named element. */
    private String getLabel(NamedElement element) {
        return PrimitivesUtil.getDisplayLabel(element);
    }

    /**
     * Gets the name of the type from unresolved proxy.
     * 
     * @param proxy
     *            the proxy of a type.
     * @return fully qualified name of the type or null if the type information
     *         is missing in the proxy.
     */
    private String getTypeNameFromUnresolvedProxy(Type proxy) {
        // Try to get the type from proxy.
        URI proxyURI = ((InternalEObject) proxy).eProxyURI();
        if (proxyURI != null) {
            String fragment = URI.decode(proxyURI.fragment());
            // The fragment is in form: <objectId>?<package>::<class>?
            int startIndex = fragment.indexOf('?') + 1;
            int endIndex = fragment.lastIndexOf('?');
            if (startIndex != -1 && endIndex != -1) {
                String umlFQType = fragment.substring(startIndex, endIndex);
                String javaFQType =
                        umlFQType.replace(BOMWorkingCopy.UML_PACKAGE_SEPARATOR,
                                BOMWorkingCopy.JAVA_PACKAGE_SEPARATOR);
                return javaFQType;
            }
        }
        return null; // $NON-NLS-1$
    }

}
