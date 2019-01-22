/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.bx.validation.rules.mapping;

import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Precision;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Scale;

/**
 * @author rsomayaj
 * 
 */
public enum ProcessType implements WebServiceJavaScriptMappingTypeCompatibility {

    BOOLEAN_LITERAL(BasicTypeType.BOOLEAN_LITERAL) {

        @Override
        public WebServiceJavaScriptMappingIssue[] check(Entity from, Entity to) {
            return to.getType().fromBoolean(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromText(Entity arg0,
                Entity arg1) {
            return arr(WebServiceJavaScriptMappingIssue.TEXT_BOOLEAN);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromBigDecimal(Entity arg0,
                Entity arg1) {
            return arr(WebServiceJavaScriptMappingIssue.NUMERIC_TO_BOOLEAN);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromInteger(Entity arg0,
                Entity arg1) {
            return arr(WebServiceJavaScriptMappingIssue.NUMERIC_TO_BOOLEAN);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromBigInteger(Entity arg0,
                Entity arg1) {
            return arr(WebServiceJavaScriptMappingIssue.NUMERIC_TO_BOOLEAN);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDecimal(Entity arg0,
                Entity arg1) {
            return arr(WebServiceJavaScriptMappingIssue.NUMERIC_TO_BOOLEAN);
        }

    },
    INTEGER_LITERAL(BasicTypeType.INTEGER_LITERAL) {

        @Override
        public WebServiceJavaScriptMappingIssue[] check(Entity from, Entity to) {
            return to.getType().fromInteger(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromBigInteger(Entity from,
                Entity to) {
            return validateLength(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromBigDecimal(Entity arg0,
                Entity arg1) {
            return arr(WebServiceJavaScriptMappingIssue.DECIMAL_TO_INTEGER);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDecimal(Entity arg0,
                Entity arg1) {
            return arr(WebServiceJavaScriptMappingIssue.DECIMAL_TO_INTEGER);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromText(Entity arg0,
                Entity arg1) {
            return arr(WebServiceJavaScriptMappingIssue.TEXT_NUMERIC);
        }

        @Override
        public int getLength(Entity entity) {

            Precision precision =
                    ((BasicType) ((ProcessRelevantData) entity.getConceptPath()
                            .getItem()).getDataType()).getPrecision();
            if (precision != null) {
                return precision.getValue();
            }
            return -1;
        }
    },

    DECIMAL_LITERAL(BasicTypeType.FLOAT_LITERAL) {

        @Override
        public WebServiceJavaScriptMappingIssue[] check(Entity from, Entity to) {
            return to.getType().fromDecimal(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromBigInteger(Entity from,
                Entity to) {
            return validateLength(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromBigDecimal(Entity from,
                Entity to) {
            return validateLength(from, to);
        }

        public WebServiceJavaScriptMappingIssue[] fromDecimal(Entity from,
                Entity to) {
            return validateLength(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromText(Entity arg0,
                Entity arg1) {
            return arr(WebServiceJavaScriptMappingIssue.TEXT_NUMERIC);
        }

        @Override
        public int getLength(Entity entity) {
            Precision precision =
                    ((BasicType) ((ProcessRelevantData) entity.getConceptPath()
                            .getItem()).getDataType()).getPrecision();
            Scale scale =
                    ((BasicType) ((ProcessRelevantData) entity.getConceptPath()
                            .getItem()).getDataType()).getScale();
            short precisionVal = -1, scaleVal = -1;
            if (precision != null) {
                precisionVal = precision.getValue();
            }
            if (scale != null) {
                scaleVal = scale.getValue();
            }
            return precisionVal - scaleVal;
        }

    },

    STRING_LITERAL(BasicTypeType.STRING_LITERAL) {

        @Override
        public WebServiceJavaScriptMappingIssue[] check(Entity from, Entity to) {
            return to.getType().fromText(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromBigInteger(Entity from,
                Entity to) {
            return validateLength(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDecimal(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.DECIMAL_TO_TEXT);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromBigDecimal(Entity arg0,
                Entity arg1) {
            return arr(WebServiceJavaScriptMappingIssue.DECIMAL_TO_TEXT);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromText(Entity from,
                Entity to) {
            return validateLength(from, to);
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
        public WebServiceJavaScriptMappingIssue[] fromDateTimeTimeZone(
                Entity from, Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.DATETYPES_TO_TEXT);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDuration(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.DATETYPES_TO_TEXT);
        }

        @Override
        public int getLength(Entity entity) {
            Length length =
                    ((BasicType) ((ProcessRelevantData) entity.getConceptPath()
                            .getItem()).getDataType()).getLength();
            if (length != null && length.getValue().matches("[0-9]*")) { //$NON-NLS-1$
                return Integer.parseInt(length.getValue());
            }
            return -1;
        }

    },
    DATE_LITERAL(BasicTypeType.DATE_LITERAL) {

        @Override
        public WebServiceJavaScriptMappingIssue[] check(Entity from, Entity to) {
            return to.getType().fromDate(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDateTime(Entity from,
                Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.DTORDTTZ_TO_DATE);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDateTimeTimeZone(
                Entity from, Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.DATETIMETZ_TO_DATETYPES,
                    WebServiceJavaScriptMappingIssue.DTORDTTZ_TO_DATE);
        }

    },
    TIME_LITERAL(BasicTypeType.TIME_LITERAL) {

        @Override
        public WebServiceJavaScriptMappingIssue[] check(Entity from, Entity to) {
            return to.getType().fromTime(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDateTimeTimeZone(
                Entity from, Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.DATETIMETZ_TO_DATETYPES);
        }

    },
    DATETIME_LITERAL(BasicTypeType.DATETIME_LITERAL) {

        @Override
        public WebServiceJavaScriptMappingIssue[] check(Entity from, Entity to) {
            return to.getType().fromDateTime(from, to);
        }

        @Override
        public WebServiceJavaScriptMappingIssue[] fromDateTimeTimeZone(
                Entity from, Entity to) {
            return arr(WebServiceJavaScriptMappingIssue.DATETIMETZ_TO_DATETYPES);
        }

    },
    PERFORMER_LITERAL(BasicTypeType.PERFORMER_LITERAL) {

        @Override
        public WebServiceJavaScriptMappingIssue[] check(Entity from, Entity to) {
            return to.getType().fromText(from, to);
        }
    };

    public static WebServiceJavaScriptMappingTypeCompatibility getType(
            ConceptPath cp) {
        BasicTypeType t = null;
        if (cp.getItem() instanceof ProcessRelevantData) {
            ProcessRelevantData processRelevantData =
                    (ProcessRelevantData) cp.getItem();
            DataType dataType = processRelevantData.getDataType();
            if (dataType instanceof BasicType) {
                BasicType basicType = (BasicType) dataType;
                t = basicType.getType();
            }
        }

        for (ProcessType type : values()) {
            if (type.type != null && type.type.equals(t)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unsupported type [" + t + "]"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    private final BasicTypeType type;

    private ProcessType(BasicTypeType type) {
        this.type = type;
    }

    public BasicTypeType getType() {
        return type;
    }

    private static WebServiceJavaScriptMappingIssue[] arr(
            WebServiceJavaScriptMappingIssue... warnings) {
        return warnings;
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

    protected WebServiceJavaScriptMappingIssue[] validateLength(Entity from,
            Entity to) {
        int fromLen = from.getLength();
        int toLen = to.getLength();
        if (toLen < fromLen) {
            return arr(WebServiceJavaScriptMappingIssue.LENGTH_TRUNCATED);
        }
        return null;
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

    /**
     * @see com.tibco.bx.validation.rules.mapping.WebServiceJavaScriptMappingTypeCompatibility#check(com.tibco.bx.validation.rules.mapping.Entity,
     *      com.tibco.bx.validation.rules.mapping.Entity)
     * 
     * @param source
     * @param target
     * @return
     */
    public abstract WebServiceJavaScriptMappingIssue[] check(Entity source,
            Entity target);
}
