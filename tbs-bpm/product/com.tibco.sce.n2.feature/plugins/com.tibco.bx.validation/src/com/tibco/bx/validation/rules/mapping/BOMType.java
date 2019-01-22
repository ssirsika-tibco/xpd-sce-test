/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.bx.validation.rules.mapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;

/**
 * @author rsomayaj
 * 
 */
public enum BOMType implements WebServiceJavaScriptMappingTypeCompatibility {

    BIGINTEGER {
        @Override
        public WebServiceJavaScriptMappingIssue[] check(Entity from, Entity to) {
            return to.getType().fromBigInteger(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromInteger(Entity from,
                Entity to) {
            // For an integer the number of digits of the upper limit or the
            // IntegerMax length must be less than

            return validateLength(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromBigDecimal(Entity from,
                Entity to) {
            return arr(validateLength(from, to),
                    WebServiceJavaScriptMappingIssue.DECIMAL_TO_INTEGER);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromText(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.TEXT_NUMERIC);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDecimal(Entity from,
                Entity to) {
            WebServiceJavaScriptMappingIssue[] validateLength =
                    validateLength(from, to);
            return arr(validateLength,
                    WebServiceJavaScriptMappingIssue.DECIMAL_TO_INTEGER);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromBigInteger(Entity from,
                Entity to) {
            return validateLength(from, to);
        }

        @Override
        public int getLength(Entity e) {
            return FacetObjectUtils.getPrimitiveTypeSteroTypeValues(e
                    .getConceptPath(),
                    PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH);
        }
    },

    BIGDECIMAL {
        @Override
        public WebServiceJavaScriptMappingIssue[] check(Entity from, Entity to) {
            return to.getType().fromBigDecimal(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromInteger(Entity from,
                Entity to) {
            return validateLength(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromBigDecimal(Entity from,
                Entity to) {
            return validateLength(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDecimal(Entity from,
                Entity to) {
            return validateLength(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromText(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.TEXT_NUMERIC);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromBigInteger(Entity from,
                Entity to) {
            return validateLength(from, to);
        }

        @Override
        public int getLength(Entity e) {
            // Return value of xsdTotalDigits - xsdFractionDigitsValue
            int totalDigits =
                    FacetObjectUtils.getPrimitiveTypeSteroTypeValues(e
                            .getConceptPath(),
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH);
            int fractionDigits =
                    FacetObjectUtils.getPrimitiveTypeSteroTypeValues(e
                            .getConceptPath(),
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES);

            int length = totalDigits - fractionDigits;

            if (length < -1) {
                return -1;
            }
            return length;
        }
    },

    ENUMERATION() {
        /**
         * @see com.tibco.bx.validation.rules.mapping.BOMType#fromText(com.tibco.bx.validation.rules.mapping.Entity,
         *      com.tibco.bx.validation.rules.mapping.Entity)
         * 
         * @param arg0
         * @param arg1
         * @return
         */
        @Override
        public WebServiceJavaScriptMappingIssue[] fromText(Entity arg0,
                Entity arg1) {
            return arr(WebServiceJavaScriptMappingIssue.TEXT_TO_ENUM);
        }
    },

    BOM_PRIMITIVE_OBJECT(PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME) {
        /**
         * @see com.tibco.bx.validation.rules.mapping.BOMType#fromObject(com.tibco.bx.validation.rules.mapping.Entity,
         *      com.tibco.bx.validation.rules.mapping.Entity)
         * 
         * @param from
         * @param to
         * @return
         */
        @Override
        public WebServiceJavaScriptMappingIssue[] fromObject(Entity from,
                Entity to) {
            return null;
        }
    },
    BOM_PRIMITIVE_BOOLEAN(PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME) {
        @Override
        public WebServiceJavaScriptMappingIssue[] check(Entity from, Entity to) {
            return to.getType().fromBoolean(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromInteger(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.NUMERIC_TO_BOOLEAN);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromBigInteger(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.NUMERIC_TO_BOOLEAN);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromBigDecimal(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.NUMERIC_TO_BOOLEAN);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDecimal(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.NUMERIC_TO_BOOLEAN);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromText(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.TEXT_BOOLEAN);
        }
    },

    BOM_PRIMITIVE_INTEGER(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME) {
        @Override
        public WebServiceJavaScriptMappingIssue[] check(Entity from, Entity to) {
            return to.getType().fromInteger(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromInteger(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.LENGTH_TRUNCATED);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromBigDecimal(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.LENGTH_TRUNCATED,
                    WebServiceJavaScriptMappingIssue.DECIMAL_TO_INTEGER);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDecimal(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.LENGTH_TRUNCATED,
                    WebServiceJavaScriptMappingIssue.DECIMAL_TO_INTEGER);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromText(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.TEXT_NUMERIC);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromBoolean(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.NUMERIC_TO_BOOLEAN);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromBigInteger(Entity from,
                Entity to) {
            // the length of the big integer must be less than either the upper
            // limit if available or the Integer max value.
            return validateLength(from, to);
        }

        @Override
        public int getLength(Entity e) {
            int maxValue =
                    FacetObjectUtils.getPrimitiveTypeSteroTypeValues(e
                            .getConceptPath(),
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER);
            int length = -1;
            if (maxValue != -1) {
                length = Integer.toString(maxValue).length();
            } else {
                length = Integer.toString(Integer.MAX_VALUE).length();
            }

            // The first digit of the integer is responsible for the sign of the
            // integer, therefore considering the length as length -1
            return length - 1;
        }
    },

    BOM_PRIMITIVE_DECIMAL(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME) {
        @Override
        public WebServiceJavaScriptMappingIssue[] check(Entity from, Entity to) {
            return to.getType().fromDecimal(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromInteger(Entity from,
                Entity to) {
            return validateLength(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDecimal(Entity from,
                Entity to) {
            return validateLength(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromText(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.TEXT_NUMERIC);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromBoolean(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.NUMERIC_TO_BOOLEAN);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromBigInteger(Entity from,
                Entity to) {
            return validateLength(from, to);
        }

        @Override
        public int getLength(Entity e) {
            String maxValue =
                    FacetObjectUtils.getUpperValue(e.getConceptPath(),
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER);
            int length = -1;
            if (maxValue != null) {

                // The value is in the exponential form (e.g 1.445E110 ); to
                // find the
                // length of such a number it needs to be reduced from its
                // exponential form.

                double log = Math.log(Double.valueOf(maxValue));

                length = 1 + (int) (log / Math.log(10));

            } else {
                length = Float.toString(Float.MAX_VALUE).length();
            }

            // The first digit of the integer is responsible for the sign of the
            // integer, therefore considering the length as length -1
            return length - 1;
        }
    },

    BOM_PRIMITIVE_TEXT(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME) {
        @Override
        public WebServiceJavaScriptMappingIssue[] check(Entity from, Entity to) {
            return to.getType().fromText(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromInteger(Entity from,
                Entity to) {
            return validateLength(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromBigDecimal(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.DECIMAL_TO_TEXT);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDecimal(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.DECIMAL_TO_TEXT);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromText(Entity from,
                Entity to) {
            WebServiceJavaScriptMappingIssue[] validateLength =
                    validateLength(from, to);
            List<WebServiceJavaScriptMappingIssue> warningsList =
                    Collections.EMPTY_LIST;
            if (null != validateLength) {
                warningsList = Arrays.asList(validateLength);
            }
            WebServiceJavaScriptMappingIssue doesntMatchPattern =
                    doesMatchPattern(from, to);
            if (null != doesntMatchPattern) {
                warningsList =
                        new ArrayList<WebServiceJavaScriptMappingIssue>();
                warningsList.add(doesntMatchPattern);
            }
            return warningsList
                    .toArray(new WebServiceJavaScriptMappingIssue[warningsList
                            .size()]);
        }

        private WebServiceJavaScriptMappingIssue doesMatchPattern(Entity from,
                Entity to) {
            String fromPattern = null;
            if (from.getConceptPath().getType() instanceof PrimitiveType) {
                String tempPattern =
                        FacetObjectUtils
                                .getTextPattern(from.getConceptPath(),
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_PATTERN_VALUE);
                if (null != tempPattern) {
                    fromPattern = tempPattern.replace("\\", "\\\\"); //$NON-NLS-1$//$NON-NLS-2$
                }
            }
            String toPattern = null;
            if (to.getConceptPath().getType() instanceof PrimitiveType) {
                String tempPattern =
                        FacetObjectUtils
                                .getTextPattern(to.getConceptPath(),
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_PATTERN_VALUE);
                if (null != tempPattern) {
                    toPattern = tempPattern.replace("\\", "\\\\"); //$NON-NLS-1$//$NON-NLS-2$
                }
            }
            if (null != fromPattern && null != toPattern) {
                if (!(fromPattern.matches(toPattern))) {
                    return WebServiceJavaScriptMappingIssue.PATTERN_DOESNT_MATCH;
                }
            }
            return null;
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDate(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.DATETYPES_TO_TEXT);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromTime(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.DATETYPES_TO_TEXT);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDateTime(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.DATETYPES_TO_TEXT);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromBigInteger(Entity from,
                Entity to) {
            return validateLength(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDateTimeTimeZone(
                Entity arg0, Entity arg1) {
            return arr(WebServiceJavaScriptMappingIssue.DATETYPES_TO_TEXT);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDuration(Entity arg0,
                Entity arg1) {
            return arr(WebServiceJavaScriptMappingIssue.DATETYPES_TO_TEXT);
        }

        @Override
        public int getLength(Entity e) {
            return FacetObjectUtils.getPrimitiveTypeSteroTypeValues(e
                    .getConceptPath(),
                    PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH);
        }
    },

    BOM_PRIMITIVE_URI(PrimitivesUtil.BOM_PRIMITIVE_URI_NAME) {

        @Override
        public WebServiceJavaScriptMappingIssue[] fromText(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.TEXT_TO_URIANDID);
        }

    },

    BOM_PRIMITIVE_ID(PrimitivesUtil.BOM_PRIMITIVE_ID_NAME) {

        @Override
        public WebServiceJavaScriptMappingIssue[] fromText(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.TEXT_TO_URIANDID);
        }

    },

    BOM_PRIMITIVE_DATE(PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME) {

        @Override
        public WebServiceJavaScriptMappingIssue[] check(Entity from, Entity to) {
            return to.getType().fromDate(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDateTime(Entity arg0,
                Entity arg1) {
            return arr(WebServiceJavaScriptMappingIssue.DATETIMETZ_TO_DATETYPES);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDateTimeTimeZone(
                Entity arg0, Entity arg1) {
            return arr(WebServiceJavaScriptMappingIssue.DATETIMETZ_TO_DATETYPES,
                    WebServiceJavaScriptMappingIssue.DTORDTTZ_TO_DATE);
        }
    },

    BOM_PRIMITIVE_TIME(PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME) {
        @Override
        public WebServiceJavaScriptMappingIssue[] check(Entity from, Entity to) {
            return to.getType().fromTime(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDateTimeTimeZone(
                Entity arg0, Entity arg1) {
            return arr(WebServiceJavaScriptMappingIssue.DATETIMETZ_TO_DATETYPES);
        }
    },

    BOM_PRIMITIVE_DATETIME(PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME) {
        @Override
        public WebServiceJavaScriptMappingIssue[] check(Entity from, Entity to) {
            return to.getType().fromDateTime(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDateTimeTimeZone(
                Entity arg0, Entity arg1) {
            return arr(WebServiceJavaScriptMappingIssue.DATETIMETZ_TO_DATETYPES);
        }
    },

    BOM_PRIMITIVE_DATETIMETZ(PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME) {

        @Override
        public WebServiceJavaScriptMappingIssue[] check(Entity from, Entity to) {
            return to.getType().fromDateTimeTimeZone(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDate(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.DATETYPES_TO_DATETIMETZ);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDateTime(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.DATETYPES_TO_DATETIMETZ);
        }
    },

    BOM_PRIMITIVE_DURATION(PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME) {
        @Override
        public WebServiceJavaScriptMappingIssue[] check(Entity from, Entity to) {
            return to.getType().fromDuration(from, to);
        }
    },

    BOM_PRIMITIVE_ATTACHMENT(PrimitivesUtil.BOM_PRIMITIVE_ATTACHMENT_NAME);

    private final String type;

    private BOMType() {
        this(null);
    }

    private BOMType(String type) {
        this.type = type;
    }

    public static WebServiceJavaScriptMappingTypeCompatibility getType(
            ConceptPath cp) {
        if (cp.getType() instanceof Enumeration) {
            return ENUMERATION;
        }

        if (FacetObjectUtils.isBigInteger(cp)) {
            return BIGINTEGER;
        }
        if (FacetObjectUtils.isBigDecimal(cp)) {
            return BIGDECIMAL;
        }

        Classifier basePrimitiveType = null;
        if (cp.getType() instanceof PrimitiveType) {
            basePrimitiveType =
                    PrimitivesUtil.getBasePrimitiveType((PrimitiveType) cp
                            .getType());
        } else {
            basePrimitiveType = cp.getType();
        }
        String primitiveTypeName = null;
        if (null != basePrimitiveType) {
            primitiveTypeName = basePrimitiveType.getName();
        } else {
            primitiveTypeName = cp.getType().getName();
        }
        for (BOMType type : values()) {
            if (type.type != null && type.type.equals(primitiveTypeName)) {
                return type;
            }
        }
        throw new IllegalArgumentException(
                "Unsupported type [" + primitiveTypeName + "]"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    protected WebServiceJavaScriptMappingIssue[] validateLength(Entity from,
            Entity to) {
        int fromLen = from.getLength();
        int toLen = to.getLength();
        if (toLen < fromLen) {
            return arr(WebServiceJavaScriptMappingIssue.LENGTH_TRUNCATED);
        }
        return null;
    }

    private static WebServiceJavaScriptMappingIssue[] arr(
            WebServiceJavaScriptMappingIssue... warnings) {
        return warnings;
    }

    /**
     * Merger utility
     * 
     * @param warningList
     * @param warnings
     * @return
     */
    private static WebServiceJavaScriptMappingIssue[] arr(
            WebServiceJavaScriptMappingIssue[] warningList,
            WebServiceJavaScriptMappingIssue... warnings) {

        List<WebServiceJavaScriptMappingIssue> listWarning =
                new ArrayList<WebServiceJavaScriptMappingIssue>();

        if (warningList != null) {
            for (WebServiceJavaScriptMappingIssue warning : warningList) {
                listWarning.add(warning);
            }
        }
        if (warnings != null) {
            for (WebServiceJavaScriptMappingIssue warning : warnings) {
                listWarning.add(warning);
            }
        }
        return listWarning
                .toArray(new WebServiceJavaScriptMappingIssue[listWarning
                        .size()]);
    }

    /*
     * Start visitor methods
     */
    public WebServiceJavaScriptMappingIssue[] fromBigInteger(Entity from,
            Entity to) {
        return null;
    }

    public WebServiceJavaScriptMappingIssue[] fromInteger(Entity from, Entity to) {
        return null;
    }

    public WebServiceJavaScriptMappingIssue[] fromBigDecimal(Entity from,
            Entity to) {
        return null;
    }

    public WebServiceJavaScriptMappingIssue[] fromDecimal(Entity from, Entity to) {
        return null;
    }

    public WebServiceJavaScriptMappingIssue[] fromText(Entity from, Entity to) {
        return null;
    }

    public WebServiceJavaScriptMappingIssue[] fromBoolean(Entity from, Entity to) {
        return null;
    }

    public WebServiceJavaScriptMappingIssue[] fromDate(Entity from, Entity to) {
        return null;
    }

    public WebServiceJavaScriptMappingIssue[] fromDateTime(Entity from,
            Entity to) {
        return null;
    }

    public WebServiceJavaScriptMappingIssue[] fromTime(Entity from, Entity to) {
        return null;
    }

    public WebServiceJavaScriptMappingIssue[] fromDateTimeTimeZone(Entity from,
            Entity to) {
        return null;
    }

    public WebServiceJavaScriptMappingIssue[] fromDuration(Entity from,
            Entity to) {
        return null;
    }

    public WebServiceJavaScriptMappingIssue[] fromObject(Entity from, Entity to) {
        return arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
    }

    /*
     * End visitor methods
     */

    public int getLength(Entity e) {
        return -1;
    }

    public WebServiceJavaScriptMappingIssue[] validate(Entity source,
            Entity target) {
        return check(source, target);
    }

    public WebServiceJavaScriptMappingIssue[] check(Entity source, Entity target) {
        return null;
    }
}
