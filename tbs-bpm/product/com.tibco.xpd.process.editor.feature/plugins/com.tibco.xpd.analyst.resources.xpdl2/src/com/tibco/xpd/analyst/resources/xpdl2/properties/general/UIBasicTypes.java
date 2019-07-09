/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.properties.general;

import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Precision;
import com.tibco.xpd.xpdl2.Scale;
import com.tibco.xpd.xpdl2.Xpdl2Factory;

/**
 * Sid ACE-1094 Type enumeration for types combo that distinguishes between
 * Fixed and FLoating point numbers. This is used to create a facade in the UI
 * that there are separate types for these in type selection.
 *
 * @author aallway
 * @since 8 Jul 2019
 */
public enum UIBasicTypes {

    String(BasicTypeType.STRING_LITERAL, new Short((short) 50), null, null),

    FixedPointNumber(BasicTypeType.FLOAT_LITERAL, null, new Short((short) 10), new Short((short) 2)),

    FloatingPointNumber(BasicTypeType.FLOAT_LITERAL, null, null, null),

    Integer(BasicTypeType.INTEGER_LITERAL, null, new Short((short) 9), null),

    Date(BasicTypeType.DATE_LITERAL, null, null, null),

    Time(BasicTypeType.TIME_LITERAL, null, null, null),

    DateTime(BasicTypeType.DATETIME_LITERAL, null, null, null),

    Boolean(BasicTypeType.BOOLEAN_LITERAL, null, null, null),

    Performer(BasicTypeType.PERFORMER_LITERAL, null, null, null);

    private BasicType basicType;

    UIBasicTypes(BasicTypeType basicTypeType, Short length, Short precision, Short scale) {
        basicType = Xpdl2Factory.eINSTANCE.createBasicType();

        basicType.setType(basicTypeType);

        if (length != null) {
            Length modelLength = Xpdl2Factory.eINSTANCE.createLength();
            modelLength.setValue(length.toString());
            basicType.setLength(modelLength);
        }

        if (precision != null) {
            Precision modelPrecision = Xpdl2Factory.eINSTANCE.createPrecision();
            modelPrecision.setValue(precision.shortValue());
            basicType.setPrecision(modelPrecision);
        }

        if (scale != null) {
            Scale modelScale = Xpdl2Factory.eINSTANCE.createScale();
            modelScale.setValue(scale.shortValue());
            basicType.setScale(modelScale);
        }

    }

    /**
     * @return the basicType constructed for this enumeration.
     */
    public BasicType getDefaultBasicType() {
        return basicType;
    }

    /**
     * @return create a clone of the basicType constructed for this enumeration.
     */
    public BasicType cloneDefaultBasicType() {
        return EcoreUtil.copy(basicType);
    }

    /**
     * Get the {@link UIBasicTypes} for a given basic type.
     * 
     * @param basicTypeType
     * @return
     */
    public static UIBasicTypes fromBasicType(BasicType basicType) {
        if (basicType != null) {
            for (UIBasicTypes uiBasicType : values()) {
                /*
                 * Type is comparable if it has the same basic type.
                 * 
                 * Plus... if it's a basic FLOAT type then treat as a
                 * FixedPointNumber if it has decimals defined
                 */
                if (uiBasicType.basicType.getType().equals(basicType.getType())) {
                    if (BasicTypeType.FLOAT_LITERAL.equals(basicType.getType())) {
                        if (basicType.getScale() != null) {
                            if (UIBasicTypes.FixedPointNumber.equals(uiBasicType)) {
                                /*
                                 * float with decimals is only equivalent to
                                 * this enum if this is the fix point enum.
                                 */
                                return uiBasicType;
                            }
                        } else {
                            if (UIBasicTypes.FloatingPointNumber.equals(uiBasicType)) {
                                /*
                                 * float without decimals is only equivalent to
                                 * this enum if this is the floating point enum.
                                 */
                                return uiBasicType;
                            }
                        }
                    } else {
                        // Types match and no a number
                        return uiBasicType;
                    }
                }
            }
        }
        return null;
    }

}