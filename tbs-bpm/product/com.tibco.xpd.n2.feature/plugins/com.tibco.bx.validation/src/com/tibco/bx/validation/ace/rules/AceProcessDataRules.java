/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.bx.validation.ace.rules;

import java.util.Collections;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TypeDeclaration;

/**
 * ACE destination specific validation rules.
 *
 * @author aallway
 * @since 23 Apr 2019
 */
public class AceProcessDataRules extends PackageValidationRule {

    private static final String ACE_ISSUE_RESERVED_PREFIX = "ace.invalid.data.name.reserved.prefix"; //$NON-NLS-1$

    private static final String ACE_ISSUE_RESERVED_NAME = "ace.invalid.data.name.reserved.word"; //$NON-NLS-1$

    public static String ACE_ISSUE_INVALID_BASE_DATATYPE =
            "ace.invalid.base.datatype"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(com.tibco.xpd.xpdl2.Package)
     *
     * @param pckg
     */
    @Override
    public void validate(Package pckg) {
        /*
         * Validate all data in the package, processes, process-activities and
         * process interfaces.
         */
        for (ProcessRelevantData data : ProcessInterfaceUtil
                .getAllDataInPackage(pckg)) {
            if (!isValidDataType(data.getDataType())) {
                addIssue(ACE_ISSUE_INVALID_BASE_DATATYPE, data);
            }

            /*
             * Sid ACE-1118 Used to only enforce these rules in the UI. Need to
             * do validation as well in case we're migrating from earlier
             * projects.
             */
            if (ReservedWords.isReservedWord(data.getName())) {
                addIssue(ACE_ISSUE_RESERVED_NAME, data);
            }

            String reservedPrefix = ReservedWords.getReservedPrefix(data.getName());
            if (reservedPrefix != null) {
                addIssue(ACE_ISSUE_RESERVED_PREFIX, data, Collections.singletonList(reservedPrefix));
            }
        }

        /*
         * Validate type declarations
         */
        for (TypeDeclaration typeDeclaration : pckg.getTypeDeclarations()) {
            if (!isValidDataType(typeDeclaration.getBasicType())) {
                addIssue(ACE_ISSUE_INVALID_BASE_DATATYPE, typeDeclaration);
            }
        }
    }

    /**
     * If data type is a basic type it is only valid if it is one of the
     * following base types.
     * 
     * Text, Number, Date, Time, Date-Time with Timezone, Performer and Boolean
     * 
     * @param dataType
     * @return <code>true</code> if the data type is valid
     */
    public static boolean isValidDataType(DataType dataType) {
        if (dataType instanceof BasicType) {
            BasicTypeType basicType = ((BasicType) dataType).getType();

            if (!BasicTypeType.STRING_LITERAL.equals(basicType)
                    && !BasicTypeType.FLOAT_LITERAL.equals(basicType)
                    && !BasicTypeType.DATE_LITERAL.equals(basicType)
                    && !BasicTypeType.TIME_LITERAL.equals(basicType)
                    && !BasicTypeType.DATETIME_LITERAL.equals(basicType)
                    && !BasicTypeType.PERFORMER_LITERAL.equals(basicType)
                    && !BasicTypeType.BOOLEAN_LITERAL.equals(basicType)) {
                return false;
            }
        }

        return true;
    }

}
