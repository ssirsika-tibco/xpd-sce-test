/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.bx.validation.rules.mapping;

import java.util.Collection;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.xpdl2.FormalParameter;

/**
 * @author rsomayaj
 * 
 */
public class FacetObjectUtils {

    private static final Logger LOG =
            XpdResourcesPlugin.getDefault().getLogger();

    /**
     * @param cp
     * @return
     */
    public static int getPrimitiveTypeSteroTypeValues(ConceptPath cp,
            String facetType) {

        Object item = cp.getItem();
        if (item instanceof Property) {
            PrimitiveType type = (PrimitiveType) cp.getType();
            return getLengthOfFacetedObject(type, (Property) item, facetType);
        } else if (item instanceof FormalParameter) {
            FormalParameter formalParameter = (FormalParameter) item;
            if (formalParameter.getLength() != null) {
                String lengthVal = formalParameter.getLength().getValue();
                int length = -1;
                try {
                    length = Integer.parseInt(lengthVal);
                } catch (Exception e) {
                    LOG.info(lengthVal + " is not integer"); //$NON-NLS-1$
                }
                return length;
            }
        }
        return -1;
    }

    /**
     * @param cp
     * @param facetType
     * @return
     */
    public static String getUpperValue(ConceptPath cp, String facetType) {
        Object item = cp.getItem();
        if (item instanceof Property) {
            PrimitiveType type = (PrimitiveType) cp.getType();
            return getUpperLimitVal(type, (Property) item, facetType);
        }
        return null;
    }

    public static String getUpperLimitVal(PrimitiveType type,
            Property property, String facetType) {
        if (property != null) {
            Object primitiveType1LengthObj =
                    PrimitivesUtil.getFacetPropertyValue(type,
                            facetType,
                            property);
            if (primitiveType1LengthObj instanceof String) {
                try {
                    return (String) primitiveType1LengthObj;
                } catch (Exception e) {
                    LOG.error("Cannot find length->" + type.getName()); //$NON-NLS-1$
                }
            }
        }
        return null;
    }

    /**
     * @param cp
     * @param facetType
     * @return
     */
    public static String getTextPattern(ConceptPath cp, String facetType) {
        Object item = cp.getItem();
        if (item instanceof Property) {
            PrimitiveType type = (PrimitiveType) cp.getType();
            Object textPatternVal =
                    PrimitivesUtil.getFacetPropertyValue(type,
                            facetType,
                            (Property) item);
            if (textPatternVal instanceof String) {
                return ((String) textPatternVal);
            }
        }
        return null;
    }

    /**
     * @param primitiveType1
     * @param property1
     * @param lengthFacet
     * @return
     */
    private static int getLengthOfFacetedObject(PrimitiveType primitiveType,
            Property prop, String lengthFacet) {
        int lengthVal1 = -1;
        if (prop != null) {
            Object primitiveType1LengthObj =
                    PrimitivesUtil.getFacetPropertyValue(primitiveType,
                            lengthFacet,
                            prop);
            if (primitiveType1LengthObj instanceof String) {
                try {
                    lengthVal1 =
                            Integer.parseInt((String) primitiveType1LengthObj);
                } catch (Exception e) {
                    LOG.error("Cannot find length->" + primitiveType.getName()); //$NON-NLS-1$
                }
            } else if (primitiveType1LengthObj instanceof Integer) {
                lengthVal1 = (Integer) primitiveType1LengthObj;

            }
        }
        return lengthVal1;
    }

    /**
     * @param conceptPath
     * @return
     */
    public static boolean isBigInteger(ConceptPath conceptPath) {
        return isFacetedValue(conceptPath,
                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_SUBTYPE,
                PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);
    }

    /**
     * BIGDECIMAL = FIXEDPOINT
     * 
     * @param conceptPath
     * @return
     */
    public static boolean isBigDecimal(ConceptPath conceptPath) {
        return isFacetedValue(conceptPath,
                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE,
                PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT);
    }

    /**
     * @param conceptPath
     * @param bomPrimitiveFacet
     * @param subtypeLength
     * @return
     */
    private static boolean isFacetedValue(ConceptPath conceptPath,
            String bomPrimitiveFacet, String subtypeLength) {
        final Object item = conceptPath.getItem();
        final ResourceSet resourceSet =
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet();
        if (item instanceof Property) {
            Property property = (Property) item;
            PrimitiveType primitiveType = (PrimitiveType) conceptPath.getType();

            Collection<String> facetPropertiesNames =
                    PrimitivesUtil.getFacetPropertiesNames(resourceSet,
                            primitiveType,
                            property);
            if (facetPropertiesNames != null) {
                if (facetPropertiesNames.contains(bomPrimitiveFacet)) {
                    Object facetPropertyValue =
                            PrimitivesUtil.getFacetPropertyValue(primitiveType,
                                    bomPrimitiveFacet,
                                    property);
                    if (facetPropertyValue instanceof EnumerationLiteral) {
                        EnumerationLiteral facetPropEnum =
                                (EnumerationLiteral) facetPropertyValue;

                        if (subtypeLength.equals(facetPropEnum.getName())) {
                            return true;
                        }
                    }
                }

            }
        }
        return false;
    }

}
