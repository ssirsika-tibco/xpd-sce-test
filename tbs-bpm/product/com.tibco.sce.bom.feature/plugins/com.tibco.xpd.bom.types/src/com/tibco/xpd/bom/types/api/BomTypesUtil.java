/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.bom.types.api;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * 
 * 
 * @author rgreen
 * @since 18 Aug 2011
 */
public class BomTypesUtil {

    public static boolean isPropertyXsdAnyType(Property prop) {

        boolean isAnyType = false;

        ResourceSet rSet =
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet();

        // Get the type of the property
        Type type = prop.getType();

        if (type instanceof PrimitiveType) {

            PrimitiveType propPT = (PrimitiveType) type;

            // Retrieve PrimitiveType of base facet Object type
            PrimitiveType baseObjectType =
                    PrimitivesUtil.getStandardPrimitiveTypeByName(rSet,
                            PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME);

            if (propPT == baseObjectType) {

                // We have identified a Property of type Object. Next we need to
                // identify which subtype it is.
                Object value =
                        PrimitivesUtil
                                .getFacetPropertyValue(propPT,
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_OBJECT_SUBTYPE,
                                        prop);

                // Subtypes are represented in an Enumeration
                if (value instanceof EnumerationLiteral) {
                    String litName = ((EnumerationLiteral) value).getName();
                    if (litName
                            .equals(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYTYPE)) {
                        isAnyType = true;
                    }
                }
            }
        }

        return isAnyType;
    }

    /**
     * Sets association end's label to a default value.
     * 
     * @param associationEnd
     *            the association end property.
     * @param syncWithName
     *            if the label should be synchronized with name.
     */
    public static void setAssociationEndDefaultLabel(Property associationEnd,
            boolean syncWithName) {
        /**
         * XPD:4025 Association source/target label should display class label
         * instead of the class name.
         */
        Classifier propertyType = (Classifier) associationEnd.getType();
        if (propertyType != null) {
            String endLabel = PrimitivesUtil.getDisplayLabel(propertyType);
            PrimitivesUtil.setDisplayLabel(associationEnd,
                    endLabel,
                    syncWithName);
        }
    }

}
