/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.cdm.transform;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Element;
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
import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * Transforms BOM model to Case Data Model (CDM).
 *
 * @author jarciuch
 * @since 5 Mar 2019
 */
public class BomTransformer {

    /**
     * Prefix for base type.
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
    /* package */ StructuredType transformClass(Class bomClass,
            DataModel cdmModel) {
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
     * Transforms BOM Property (aka attribute).
     * 
     * @param bomAttribute
     * @param cdmType
     * @return
     */
    private Attribute transformAttribute(Property bomAttribute,
            StructuredType cdmType) {
        Attribute cdmAttribute = cdmType.newAttribute();
        cdmAttribute.setName(bomAttribute.getName());
        cdmAttribute.setLabel(getLabel(bomAttribute));
        cdmAttribute.setDescription(getFirstOwnedComment(bomAttribute));
        String cdmAttributeType = transformType(bomAttribute.getType());
        cdmAttribute.setType(cdmAttributeType);
        int upper = bomAttribute.getUpper();
        int lower = bomAttribute.getLower();
        cdmAttribute.setIsMandatory(lower != 0);
        cdmAttribute.setIsArray(upper == -1 || upper > 1);
        return cdmAttribute;
    }

    /**
     * Transforms BOM type to CMD string type representation.
     * 
     * @param bomType
     *            the BOM type.
     * @return the string representation of a CDM type.
     */
    private String transformType(Type bomType) {
        if (bomType instanceof PrimitiveType) {
            switch (bomType.getName()) {
            case PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME:
                return CDM_BASE_TEXT_TYPE;
            case PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME:
                return CDM_BASE_NUMBER_TYPE;
            case PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME:
                return CDM_BASE_NUMBER_TYPE;
            case PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME:
                return CDM_BASE_DATE_TYPE;
            case PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME:
                return CDM_BASE_TIME_TYPE;
            case PrimitivesUtil.BOM_PRIMITIVE_ENUMERATION_NAME:
                return CDM_BASE_TEXT_TYPE;

            // TODO These cases will be supported when the CDM model is updated.
            // case PrimitivesUtil.BOM_PRIMITIVE_ID_NAME:
            // return BASE_PREFIX + BaseType.ID;
            // case PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME:
            // return BASE_PREFIX + BaseType.BOOLEAN;
            // case PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME:
            // return BASE_PREFIX + BaseType.DATE_TIME_TZ;
            // case PrimitivesUtil.BOM_PRIMITIVE_URI_NAME:
            // return BASE_PREFIX + BaseType.URI;
            default:
                return CDM_BASE_TEXT_TYPE; // $NON-NLS-1$
            }
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
}
