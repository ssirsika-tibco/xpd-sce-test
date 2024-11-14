/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules.baserules;

import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Precision;
import com.tibco.xpd.xpdl2.Scale;

/**
 * This class handles concept path script mappings when source and target are
 * process basic types.
 * 
 * @author bharge
 * 
 */

// XPD-5969: Moved to its own class to be accessed/reused from other locations
// to raise truncation warnings on mappings.
public class BasicTypeHandler {

    /**
     * 
     * @param sourceType
     * @param targetType
     * @param directionType
     * @return
     */
    public static MappingTypeCompatibility handleSourceTargetBasicTypes(
            Object sourceType, Object targetType, DirectionType directionType) {

        MappingTypeCompatibility match = MappingTypeCompatibility.WRONGTYPE;
        /*
         * When both are BasicType's (or equivalent thereof) then we can do some
         * sensible type and length comparisons.
         */
        BasicType sourceBasicType = (BasicType) sourceType;
        BasicType targetBasicType = (BasicType) targetType;

        if (sourceBasicType.getType().equals(targetBasicType.getType())) {

            /* Types match, check size criteria for relevant types. */
            match = MappingTypeCompatibility.OK; /* set ok, unset if not. */
            match =
                    BasicTypeHandler.handleMatchingTypes(sourceBasicType,
                            targetBasicType,
                            directionType,
                            match);

        } else if (BasicTypeType.PERFORMER_LITERAL.equals(sourceBasicType
                .getType())
                && BasicTypeType.STRING_LITERAL.equals(targetBasicType
                        .getType())) {
            /*
             * XPD-859 - Performer field to String must be allowed for
             * sub-process mappings.
             */
            match = MappingTypeCompatibility.OK;
        } else if (BasicTypeType.STRING_LITERAL.equals(sourceBasicType
                .getType())
                && BasicTypeType.PERFORMER_LITERAL.equals(targetBasicType
                        .getType())) {
            /*
             * XPD-3479 (A) - String to Performer field must be allowed for
             * sub-process mappings.
             */
            match = MappingTypeCompatibility.OK;
        }
        /**
         * SIA-2:Float to Integer and Integer to float mappings are allowed in
         * iProcess therefore liberalizing the validation rules for these
         * circumstances.
         */
        else if (BasicTypeType.FLOAT_LITERAL.equals(sourceBasicType.getType())
                && BasicTypeType.INTEGER_LITERAL.equals(targetBasicType
                        .getType())) {
            match = MappingTypeCompatibility.OK;
        } else if (BasicTypeType.INTEGER_LITERAL.equals(sourceBasicType
                .getType())
                && BasicTypeType.FLOAT_LITERAL
                        .equals(targetBasicType.getType())) {
            match = MappingTypeCompatibility.OK;
        }
        /**
         * End SIA-2
         */

        return match;
    }

    /**
     * 
     * @param sourceBasicType
     * @param targetBasicType
     * @param directionType
     * @param match
     * @return
     */
    public static MappingTypeCompatibility handleMatchingTypes(
            BasicType sourceBasicType, BasicType targetBasicType,
            DirectionType directionType, MappingTypeCompatibility match) {

        if (BasicTypeType.STRING_LITERAL.equals(sourceBasicType.getType())) {
            match =
                    BasicTypeHandler.stringHandler(sourceBasicType,
                            targetBasicType,
                            directionType,
                            match);
        } else if (BasicTypeType.INTEGER_LITERAL.equals(sourceBasicType
                .getType())) {
            match =
                    BasicTypeHandler.integerHandler(sourceBasicType,
                            targetBasicType,
                            directionType,
                            match);
        } else if (BasicTypeType.FLOAT_LITERAL
                .equals(sourceBasicType.getType())) {
            match =
                    BasicTypeHandler.floatHandler(sourceBasicType,
                            targetBasicType,
                            directionType,
                            match);
        }
        return match;
    }

    /**
     * 
     * @param sourceBasicType
     * @param targetBasicType
     * @param directionType
     * @param match
     * @return
     */
    protected static MappingTypeCompatibility stringHandler(
            BasicType sourceBasicType, BasicType targetBasicType,
            DirectionType directionType, MappingTypeCompatibility match) {

        int sourceLength = 0;
        int targetLength = 0;

        Length ln1 = sourceBasicType.getLength();
        if (ln1 != null && ln1.getValue() != null
                && ln1.getValue().length() > 0) {
            sourceLength = Integer.parseInt(ln1.getValue());
        } else {
            sourceLength = Integer.MAX_VALUE;
        }

        Length ln2 = targetBasicType.getLength();
        if (ln2 != null && ln2.getValue() != null
                && ln2.getValue().length() > 0) {
            targetLength = Integer.parseInt(ln2.getValue());
        } else {
            targetLength = Integer.MAX_VALUE;
        }

        if (sourceLength != targetLength) {
            match = MappingTypeCompatibility.WRONGSIZE;
        }

        match =
                fitsLengthValidation(sourceLength,
                        targetLength,
                        directionType,
                        match,
                        false,
                        0,
                        0);

        return match;
    }

    /**
     * 
     * @param sourceBasicType
     * @param targetBasicType
     * @param directionType
     * @param match
     * @return
     */
    protected static MappingTypeCompatibility integerHandler(
            BasicType sourceBasicType, BasicType targetBasicType,
            DirectionType directionType, MappingTypeCompatibility match) {

        int sourcePrecision = 0;
        int targetPrecision = 0;

        Precision pr1 = sourceBasicType.getPrecision();
        if (pr1 != null) {
            sourcePrecision = pr1.getValue();
        }

        Precision pr2 = targetBasicType.getPrecision();
        if (pr2 != null) {
            targetPrecision = pr2.getValue();
        }

        if (sourcePrecision != targetPrecision) {
            match = MappingTypeCompatibility.WRONGSIZE;
        }

        match =
                fitsLengthValidation(sourcePrecision,
                        targetPrecision,
                        directionType,
                        match,
                        false,
                        0,
                        0);

        return match;
    }

    /**
     * 
     * @param sourceBasicType
     * @param targetBasicType
     * @param directionType
     * @param match
     * @return
     */
    protected static MappingTypeCompatibility floatHandler(
            BasicType sourceBasicType, BasicType targetBasicType,
            DirectionType directionType, MappingTypeCompatibility match) {

        int sourcePrecision = 0;
        int targetPrecision = 0;

        Precision pr1 = sourceBasicType.getPrecision();
        if (pr1 != null) {
            sourcePrecision = pr1.getValue();
        }

        Precision pr2 = targetBasicType.getPrecision();
        if (pr2 != null) {
            targetPrecision = pr2.getValue();
        }

        if (sourcePrecision != targetPrecision) {
            match = MappingTypeCompatibility.WRONGSIZE;
        }

        int sourceScale = 0;
        int targetScale = 0;

        Scale sc1 = sourceBasicType.getScale();
        if (sc1 != null) {
            sourceScale = sc1.getValue();
        }

        Scale sc2 = targetBasicType.getScale();
        if (sc2 != null) {
            targetScale = sc2.getValue();
        }

        if (sourceScale != targetScale) {
            match = MappingTypeCompatibility.WRONGSIZE;
        }

        match =
                fitsLengthValidation(sourcePrecision,
                        targetPrecision,
                        directionType,
                        match,
                        false,
                        sourceScale,
                        targetScale);
        return match;
    }

    /**
     * @param sourceLength
     * @param targetLength
     * @param directionType
     * @param tempMatch
     * @param isDecimal
     * @param sourceScale
     * @param targetScale
     * @return
     */
    private static MappingTypeCompatibility fitsLengthValidation(
            int sourceLength, int targetLength, DirectionType directionType,
            MappingTypeCompatibility tempMatch, boolean isDecimal,
            int sourceScale, int targetScale) {
        /* The default position is the whatever the caller passed in. */
        MappingTypeCompatibility match = tempMatch;

        if (isDecimal) {
            if (sourceLength <= targetLength) {
                if (sourceScale <= targetScale) {
                    match = MappingTypeCompatibility.OK;
                }
            }

        } else {
            if (sourceLength <= targetLength) {
                match = MappingTypeCompatibility.OK;
            }
        }

        return match;
    }

}