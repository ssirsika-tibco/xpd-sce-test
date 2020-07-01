/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.validation.xpdl2.rules.Xpdl2ValidationRule;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Abstract class for validation to check values inserted for Initial Values on
 * Data Fields or Formal Parameters
 * 
 * @author glewis
 */
public abstract class DataTypeInitialValueRule extends Xpdl2ValidationRule {

    protected String INVALID_VALUE;

    protected String INVALID_LENGTH;

    protected String DATE_FORMAT;

    protected String INVALID_NOT_ARRAY;

    protected static final String INITIAL_VALUE_SEPERATOR = "~"; //$NON-NLS-1$

    protected static final Locale DEFAULT_LOCALE = Locale.getDefault();

    private static final String ALPHA_REGEX = "[a-zA-Z]"; //$NON-NLS-1$

    private static final String iso8601DatePattern = "yyyy-MM-dd"; //$NON-NLS-1$ 

    private final DateFormat localisedTimeFormat = DateFormat
            .getTimeInstance(DateFormat.SHORT);

    private static char standardisedGroupSeperator =
            ((DecimalFormat) DecimalFormat.getInstance(Locale.ENGLISH))
                    .getDecimalFormatSymbols().getGroupingSeparator();

    /**
     * @param pd
     * @return
     */
    protected abstract String[] initialValueSetup(ProcessRelevantData pd);

    public void validateFieldsOrParams(List<ProcessRelevantData> fields) {
        for (ProcessRelevantData pd : fields) {
            String[] initialValues = initialValueSetup(pd);
            if (initialValues.length > 0) {
                if (pd.getDataType() instanceof BasicType) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(pd.getName());
                    BasicType basicType = (BasicType) pd.getDataType();
                    if (null != basicType.getLength()
                            && basicType.getType()
                                    .equals(BasicTypeType.STRING_LITERAL)) {
                        for (String initialValue : initialValues) {
                            /* Sid ACE-4093 allow for leading / terminating quotes. */
                            if (initialValue != null
                                    && getTextInitialValueLength(pd,
                                            initialValue) > getDataFieldOrParamLength(basicType)) {
                                addIssue(INVALID_LENGTH, pd);
                            }
                        }
                    }
                    if (basicType.getType()
                            .equals(BasicTypeType.INTEGER_LITERAL)) {
                        for (String initialValue : initialValues) {
                            try {
                                Long.parseLong(initialValue);
                                String integerPattern =
                                        "\\d{1," //$NON-NLS-1$
                                                + basicType.getPrecision()
                                                        .getValue() + "}"; //$NON-NLS-1$
                                if (!Pattern.matches(integerPattern,
                                        initialValue))
                                    addIssue(INVALID_LENGTH, pd);

                            } catch (NumberFormatException numberFormatException) {
                                addIssue(INVALID_VALUE, pd, messages);
                            }
                        }
                    }
                    if (basicType.getType().equals(BasicTypeType.FLOAT_LITERAL)) {
                        for (String initialValue : initialValues) {
                            try {
                                double tempDouble =
                                        Double.parseDouble(initialValue);
                                String tempInititalValue =
                                        DecimalFormat
                                                .getInstance(Locale.ENGLISH)
                                                .format(tempDouble);
                                tempInititalValue =
                                        tempInititalValue
                                                .replace(standardisedGroupSeperator,
                                                        ' ');
                                tempInititalValue =
                                        tempInititalValue.replace(" ", ""); //$NON-NLS-1$ //$NON-NLS-2$
                                // String decimalPattern =
                                //                                        "\\d{1," //$NON-NLS-1$
                                // + basicType.getPrecision()
                                // .getValue()
                                //                                                + "}(\\.)?(\\d{1," //$NON-NLS-1$
                                // + basicType.getScale()
                                //                                                        .getValue() + "})?"; //$NON-NLS-1$

                                // if (!Pattern.matches(decimalPattern,
                                // tempInititalValue))
                                // addIssue(INVALID_LENGTH, pd);

                                // XPD-4 - pattern must consider decimal places
                                // while calculating length. should allow -, +
                                short precision =
                                        basicType.getPrecision().getValue();
                                short scale = basicType.getScale().getValue();
                                String decimalPattern =
                                        "^[-+]?\\d{1," //$NON-NLS-1$
                                                + (precision - scale)
                                                + "}(\\.)?(\\d{0," //$NON-NLS-1$
                                                + basicType.getScale()
                                                        .getValue() + "})?"; //$NON-NLS-1$
                                if (scale >= precision) {
                                    addIssue(INVALID_LENGTH, pd);
                                } else {
                                    if (!Pattern.matches(decimalPattern,
                                            tempInititalValue)) {
                                        addIssue(INVALID_LENGTH, pd);
                                    }
                                }

                            } catch (NumberFormatException numberFormatException) {
                                addIssue(INVALID_VALUE, pd, messages);
                            }

                        }

                    }
                    if (basicType.getType()
                            .equals(BasicTypeType.BOOLEAN_LITERAL)) {
                        for (String initialValue : initialValues) {
                            if (initialValue != null) {
                                if (!initialValue
                                        .trim()
                                        .equalsIgnoreCase(ProcessDataUtil.MODEL_BOOLEAN_TRUE)
                                        && !initialValue
                                                .trim()
                                                .equalsIgnoreCase(ProcessDataUtil.MODEL_BOOLEAN_FALSE)) {
                                    addIssue(INVALID_VALUE, pd, messages);
                                }
                            }
                        }
                    }

                    /* Sid ACE-3665 don't validate date-time initial value (this is now disallowed). */
                    // if (basicType.getType()
                    // .equals(BasicTypeType.DATETIME_LITERAL)) {
                    // SimpleDateFormat simpleDateFormat =
                    // new SimpleDateFormat();
                    // simpleDateFormat.setLenient(false);
                    // for (String initialValue : initialValues) {
                    // try {
                    // simpleDateFormat.parse(initialValue);
                    // } catch (Exception ex) {
                    // ArrayList<String> arrList =
                    // new ArrayList<String>();
                    // String localisedPattern =
                    // new SimpleDateFormat()
                    // .toLocalizedPattern();
                    // arrList.add(localisedPattern);
                    // addIssue(DATE_FORMAT, pd, arrList);
                    // }
                    // }
                    // }

                    // if (basicType.getType().equals(BasicTypeType.DATE_LITERAL)) {
                    // for (String initialValue : initialValues) {
                    // if (initialValue.trim().length() > 0) {
                    // try {
                    // SimpleDateFormat simpleDateFormat =
                    // new SimpleDateFormat();
                    // simpleDateFormat
                    // .applyPattern(iso8601DatePattern);
                    // simpleDateFormat.setLenient(false);
                    // String alphaStripped =
                    // initialValue
                    // .replaceAll(ALPHA_REGEX, ""); //$NON-NLS-1$
                    // if (!alphaStripped.equals(initialValue)
                    // || alphaStripped.indexOf(",") != -1) { //$NON-NLS-1$
                    // throw new Exception();
                    // }
                    // } catch (Exception ex) {
                    // String localisedPattern =
                    // new SimpleDateFormat("dd/MM/yy").toLocalizedPattern(); //$NON-NLS-1$
                    // ArrayList<String> arrList =
                    // new ArrayList<String>();
                    // arrList.add(localisedPattern);
                    // addIssue(DATE_FORMAT, pd, arrList);
                    // }
                    // }
                    // }
                    // }
                    // if (basicType != null
                    // && basicType.getType()
                    // .equals(BasicTypeType.TIME_LITERAL)) {
                    // for (String initialValue : initialValues) {
                    // if (initialValue.trim().length() > 0) {
                    // try {
                    // localisedTimeFormat.setLenient(false);
                    // localisedTimeFormat.parse(initialValue);
                    // initialValue =
                    // initialValue.toLowerCase()
                    // .replaceFirst(" am", ""); //$NON-NLS-1$ //$NON-NLS-2$
                    // initialValue =
                    // initialValue.toLowerCase()
                    // .replaceFirst(" pm", ""); //$NON-NLS-1$ //$NON-NLS-2$
                    // String alphaStripped =
                    // initialValue
                    // .replaceAll(ALPHA_REGEX, ""); //$NON-NLS-1$
                    // if (!alphaStripped.equals(initialValue)
                    // || alphaStripped.indexOf(",") != -1) { //$NON-NLS-1$
                    // throw new Exception();
                    // }
                    // } catch (Exception ex) {
                    // String localisedPattern =
                    // new SimpleDateFormat("HH:mm").toLocalizedPattern(); //$NON-NLS-1$
                    // ArrayList<String> arrList =
                    // new ArrayList<String>();
                    // arrList.add(localisedPattern);
                    // addIssue(DATE_FORMAT, pd, arrList);
                    // }
                    // }
                    // }
                    // }
                }

            }
        }
    }

    /**
     * Sid ACE-4093 allow for leading / terminating quotes (which should not be counted for formal parameter allowed
     * values as these are a requirement for correct functioning (since XPD-8195).
     * 
     * @param pd
     * 
     * @param initialValue
     * 
     * @return The length of the text initial value
     */
    public int getTextInitialValueLength(ProcessRelevantData pd, String initialValue) {
        if (initialValue == null) {
            return 0;
        }

        int len = initialValue.length();

        if (!(pd instanceof FormalParameter)) {
            return len;
        }

        if (initialValue.startsWith("\"")) { //$NON-NLS-1$
            len--;
        }

        if (initialValue.endsWith("\"")) { //$NON-NLS-1$
            len--;
        }
        return len;
    }

    /**
     * @param basicType
     * @return
     */
    private int getDataFieldOrParamLength(BasicType basicType) {
        int length = 0;
        if (basicType != null) {
            Length typeLength = basicType.getLength();
            if (typeLength != null) {
                String lengthValue = typeLength.getValue();
                try {
                    length = Integer.parseInt(lengthValue);
                } catch (NumberFormatException ex) {
                    // Some problems with the length
                    // should not be handled here, do nothing
                }
            }
        }
        return length;
    }
}
