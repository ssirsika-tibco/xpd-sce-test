/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.GlobalDataReservedWords;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Precision;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Scale;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rules for all process data / type declarations.
 * 
 * @author aallway
 * @since 3.3 (9 Mar 2010)
 */
public class ProcessDataValidationRules extends PackageValidationRule {

    private static final String ISSUE_WARN_NUMBER_FORMAT_NOT_ENFORCED =
            "bx.specificNumberFormatNotEnforced"; //$NON-NLS-1$

    private static final String ISSUE_DATANAME_CANNOT_BE_GLOBALDATAKEYWORD =
            "bx.dataNameCanNotBeGlobalDataKeyword"; //$NON-NLS-1$

    private static final String ISSUE_UNQUOTED_TEXT_ALLOWED_PARAMS =
            "bx.unquotedTextAllowedParams"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(com
     * .tibco.xpd.xpdl2.Package)
     */
    @Override
    public void validate(Package pckg) {
        Collection<ProcessRelevantData> allData =
                ProcessInterfaceUtil.getAllDataInPackage(pckg);

        for (ProcessRelevantData data : allData) {
            DataType dataType = data.getDataType();

            if (dataType instanceof BasicType) {
                validateBasicType((BasicType) dataType, data);
            }
        }

        for (TypeDeclaration typeDeclaration : pckg.getTypeDeclarations()) {
            BasicType basicType = typeDeclaration.getBasicType();
            if (basicType != null) {
                validateBasicType(basicType, typeDeclaration);
            }
        }
        /*
         * XPD-5657: Saket: Add a validation marker if we have a
         * datafield/parameter with a name which is a global data keyword.
         */
        for (ProcessRelevantData data : allData) {
            String dataName = data.getName();
            if (isGlobalDataKeyWord(dataName)) {
                addIssue(ISSUE_DATANAME_CANNOT_BE_GLOBALDATAKEYWORD,
                        data,
                        Collections.singletonList(dataName));
            }

            /* Sid XPD-8195 Allowed values of text parameters must have quotes. */
            checkAllowedValues(data, dataName);
        }
        return;
    }

    /**
     * Sid XPD-8195 Allowed values of text parameters must have quotes
     * 
     * @param data
     * @param dataName
     */
    protected void checkAllowedValues(ProcessRelevantData data, String dataName) {
        if (data instanceof FormalParameter) {
            FormalParameter fp = (FormalParameter) data;

            Object other =
                    Xpdl2ModelUtil.getOtherElement(fp,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_InitialValues());
            if (other instanceof InitialValues) {
                InitialValues values = (InitialValues) other;

                BasicType basicType =
                        BasicTypeConverterFactory.INSTANCE.getBasicType(data);

                if (basicType != null
                        && BasicTypeType.STRING_LITERAL.equals(basicType
                                .getType())) {
                    for (String value : values.getValue()) {
                        if (value != null && (!value.startsWith("\"") || !value //$NON-NLS-1$
                                .endsWith("\""))) { //$NON-NLS-1$
                            addIssue(ISSUE_UNQUOTED_TEXT_ALLOWED_PARAMS,
                                    data,
                                    Collections
                                            .singletonList(trimQuotes(value)));
                        }
                    }
                }
            }
        }
    }

    /**
     * Remove the start or end quote (so that the string makes sense in problem
     * Description
     * 
     * @param value
     * @return value with leading or trailing space removed.
     */
    private String trimQuotes(String value) {
        return value.replaceAll("^\"|\"$", ""); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Checks whether the passed string is a Global data key word or not.
     * 
     * @param nameText
     * @return true if the passed string is a Global data key word, false
     *         otherwise.
     */
    protected boolean isGlobalDataKeyWord(String nameText) {
        List<String> symbolTableKeywords =
                GlobalDataReservedWords.getSymbolTableKeyWords();
        if (symbolTableKeywords.contains(nameText)) {
            return true;
        }
        return false;
    }

    /**
     * @param data
     */
    private void validateBasicType(BasicType basicType,
            EObject dataOrTypeDeclaration) {

        if (BasicTypeType.FLOAT_LITERAL.equals(basicType.getType())
                || BasicTypeType.INTEGER_LITERAL.equals(basicType.getType())) {
            Length length = basicType.getLength();
            if (length != null) {
                String value = length.getValue();
                if (value != null && value.length() > 0) {
                    addIssue(ISSUE_WARN_NUMBER_FORMAT_NOT_ENFORCED,
                            dataOrTypeDeclaration);
                }

            } else {
                Precision precision = basicType.getPrecision();
                if (precision != null) {
                    addIssue(ISSUE_WARN_NUMBER_FORMAT_NOT_ENFORCED,
                            dataOrTypeDeclaration);

                } else {
                    Scale scale = basicType.getScale();
                    if (scale != null) {
                        addIssue(ISSUE_WARN_NUMBER_FORMAT_NOT_ENFORCED,
                                dataOrTypeDeclaration);
                    }
                }
            }
        }

        return;
    }
}
