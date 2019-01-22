/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import com.tibco.xpd.validation.bpmn.rules.BaseInitialPriorityValueRule;

/**
 * InitialPriorityValueRule - 1. validates if the Initial Priority Value is in
 * the range of 0 and 100.
 * 
 * 2. The Process Initial Priority Value is one of 100 200 300 or 400 that
 * signifies 100 as Low 200 as Normal 300 as High and 400 as Exceptional.
 * 
 * 
 * 
 * @author bharge
 * @since 3.3 (21 Jan 2010)
 */
public class InitialPriorityValueRule extends BaseInitialPriorityValueRule {
    private static final String INITIAL_PRIORITY_ID =
            "bx.initialPriorityNotInRangeMAX"; //$NON-NLS-1$

    private static final String PROCESS_INITIAL_PRIORITY_ID =
            "bx.processInitialPriorityNotInRange"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.BaseInitialPriorityValueRule#get_ISSUE_INITIAL_PRIORITY_INVALID_RANGE()
     * 
     * @return
     */
    @Override
    protected String get_ISSUE_INITIAL_PRIORITY_INVALID_RANGE() {
        return INITIAL_PRIORITY_ID;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.BaseInitialPriorityValueRule#get_ISSUE_PROCESS_INITIAL_PRIORITY_INVALID_RANGE()
     * 
     * @return
     */
    @Override
    protected String get_ISSUE_PROCESS_INITIAL_PRIORITY_INVALID_RANGE() {
        return PROCESS_INITIAL_PRIORITY_ID;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.BaseInitialPriorityValueRule#isPriorityValidRange(java.lang.String)
     * 
     * @param priority
     * @return
     */
    @Override
    protected boolean isPriorityValidRange(String priority) {
        // XPD-3786 extended for range check to 0-100 to match runtime
        if (super.isPriorityValidRange(priority)) {
            if (null != priority && !priority.isEmpty()) {
                if (priority.matches("[-]?[\\d]+")) {//$NON-NLS-1$                    
                    if (Integer.parseInt(priority) < 0
                            || Integer.parseInt(priority) > 100) {
                        return false;
                    }
                } else {
                    return false;
                }

            }
        }
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.BaseInitialPriorityValueRule#isPriorityValidRange(java.lang.String)
     * 
     * @param priority
     * @return
     */
    @Override
    protected boolean isProcessPriorityValidRange(String priority) {
        if (priority == null || priority.length() == 0) {
            return true;
        }

        /*
         * XPD-5475: Now we allow any value between minimum and maximum (100 -
         * 400) or "" = server default
         */
        try {
            int intPriority = Integer.parseInt(priority);
            if (intPriority >= 100 && intPriority <= 400) {
                return true;
            }

        } catch (NumberFormatException e) {
        }

        return false;
    }

}
