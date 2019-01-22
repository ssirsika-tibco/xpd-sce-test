/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.imports.validations;

import java.util.Comparator;

import org.eclipse.core.runtime.IStatus;

/**
 * [Copied from IPM com.tibco.xpd.ipm plugin]. Data object that represent a
 * validation error reported from the Import from iProcess
 * 
 * @author aprasad
 */
public class ImportValidationError {
    /**
     * Validated XPDL file name
     */
    private String xpdl;

    /**
     * Validation message
     */
    private String message;

    /**
     * status can be either <code>IStatus.OK</code> or
     * <code>IStatus.WARNING</code>
     */
    private final int severity;

    /**
     * @return xpdl file name
     */
    public String getXpdl() {
        return xpdl;
    }

    /**
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return the severity
     */
    public int getSeverity() {
        return severity;
    }

    /**
     * Constructor
     * 
     * @param xpdl
     * @param message
     */
    public ImportValidationError(String xpdl, String message, int status) {
        super();
        this.xpdl = xpdl;
        this.message = message;
        this.severity = status;

    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ImportValidationError) {

            ImportValidationError error = (ImportValidationError) obj;
            if (severity != error.severity) {
                return false;
            }
            if (message != null) {
                /* Messages when not null should match */
                if (!message.equals(error.message)) {

                    return false;
                }
            } else if (error.message != null) {
                /* Both messages should be null to match */
                return false;
            }

            if (xpdl != null) {
                /* Originating xpdl when not null should match */
                if (!xpdl.equals(error.xpdl)) {

                    return false;
                }
            } else if (error.xpdl != null) {
                /* Both Originating XPDL should be null to match */
                return false;
            }
        }
        return true;
    }

    public static class ValidationMessageComparator implements Comparator {

        /**
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         * 
         *      Sort Validation Messages entries in sequence to show Errors
         *      first followed by Warnings and then Errors and Warnings
         *      internally sorted alphabeticall and then on originating files
         * 
         * @param o1
         * @param o2
         * @return
         */
        @Override
        public int compare(Object o1, Object o2) {

            /*
             * Can compare only when both are ImportValidationError instances,
             * return otherwise
             */
            if (!(o1 instanceof ImportValidationError && o2 instanceof ImportValidationError)) {
                return 0;
            }

            ImportValidationError valMsg1 = (ImportValidationError) o1;
            ImportValidationError valMsg2 = (ImportValidationError) o2;

            switch (valMsg1.severity) {

            case IStatus.ERROR:
                /*
                 * When first is ERROR, we only need to sort when the other one
                 * is ERROR to, else no need to swap
                 */
                if (valMsg2.severity == IStatus.ERROR) {

                    return compareValMsgWithSameSeverity(valMsg1, valMsg2);

                }
                /*
                 * When other element is Warning OR Info, Push Errors Up by
                 * giving less value
                 */
                return -1;

            case IStatus.WARNING:

                /*
                 * When other one is Error , just swap as Errors should come
                 * before Warnings
                 */

                if (valMsg2.severity == IStatus.ERROR) {
                    return 1;
                }
                /*
                 * When the other Element is Warning then Sort on XPDL and then
                 * Message String
                 */
                if (valMsg2.severity == IStatus.WARNING) {

                    /*
                     * When both are WARNING, try to sort on Originating XPDL
                     * first
                     */
                    return compareValMsgWithSameSeverity(valMsg1, valMsg2);

                }
                /*
                 * When other element is Info, no need to swap as Warnings
                 * Should appear before Info
                 */
                break;

            case IStatus.INFO:
                /*
                 * Info should be at the end of list, after Errors and Warnings,
                 * hence push it down when other element is an Error or Warning
                 */
                if ((valMsg2.severity == IStatus.ERROR)
                        || (valMsg2.severity == IStatus.WARNING)) {
                    return 1;
                } else {
                    return compareValMsgWithSameSeverity(valMsg1, valMsg2);

                }

            default:
                /*
                 * For unknown types push it to the bottom after all
                 * ERROR/WARNING/INFO
                 */

                if ((valMsg2.severity == IStatus.ERROR)
                        || (valMsg2.severity == IStatus.WARNING)
                        || (valMsg2.severity == IStatus.INFO)) {

                    return 1;
                }

            }

            /* In All other cases leave as is */
            return 0;
        }

        /**
         * @param valMsg1
         * @param valMsg2
         * @return
         */
        private int compareValMsgWithSameSeverity(
                ImportValidationError valMsg1, ImportValidationError valMsg2) {

            int isSameXPDL = isStringValueEqual(valMsg1.xpdl, valMsg2.xpdl);

            if (isSameXPDL == 0) {

                /* When they Originate from same XPDL, sort on Message */
                return isStringValueEqual(valMsg1.message, valMsg2.message);

            } else {
                return isSameXPDL;
            }
        }

        /**
         * Compares given String such that the null String should be at the
         * bottom.
         * 
         * @param strVal1
         * @param strVal2
         * @return 0 when both are null or when no swap is required, for example
         *         strVal1 is not null but StrVal2 is null, as we intend to keep
         *         null values at bottom, return 1 to swap when strVal1 is null
         *         and strVal2 is not null so as to push null values
         *         down.Returns the normal String Comparision value when both
         *         are not null.
         */
        private int isStringValueEqual(String strVal1, String strVal2) {

            /* Even null but both are same , no swap needed */
            if (strVal1 == null && strVal2 == null) {
                return 0;
            }
            /* Push the null val Down */
            if (strVal1 == null) {
                return 1;
            }
            /* When sec val is null let is return 0, we we do not intend to swap */
            if (strVal2 != null) {
                /*
                 * Reaching this point means both are not null, return noral
                 * String compare
                 */
                return strVal1.compareTo(strVal2);
            }
            return 0;
        }
    }
}
