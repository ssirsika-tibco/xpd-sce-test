/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules.baserules;

/**
 * Enumeration used to define the compatibility check result in multiple
 * possible outcomes with details on if checks ran , failed or passed, this will
 * allow to control the validation flow in multiple ways for different
 * requirements.
 * 
 * @author aprasad
 * @since 08-Jan-2015
 */
public enum JavaScriptTypeCompatibilityResult {
    /**
     * Mapping scenario is dealt with by the checkJavaScriptTypeCompatibility()
     * method AND it looks ok
     */
    HANDLED_SCENARIO_CHECK_SUCCEEDED,
    /**
     * Mapping scenario is dealt with by the checkJavaScriptTypeCompatibility()
     * method AND it's invalid.
     */
    HANDLED_SCENARIO_CHECK_FAILED,
    /**
     * Returned if checkJavaScriptTypeCompatibility() method does not handle the
     * scenario at all so this is OK (when returning) OR sub-class should
     * continue to check it's own scenarios after suprt method returns this.
     */
    UNHANDLED_SCENARIO
}