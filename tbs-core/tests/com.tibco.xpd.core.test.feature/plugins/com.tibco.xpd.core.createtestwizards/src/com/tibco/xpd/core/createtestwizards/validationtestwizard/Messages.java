/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.createtestwizards.validationtestwizard;

import org.eclipse.osgi.util.NLS;

/**
 * Messages
 * 
 * 
 * @author aallway
 * @since 3.3 (27 Jul 2009)
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.core.createtestwizards.validationtestwizard.messages"; //$NON-NLS-1$

    public static String CreateValidationTestPage_DontExistsMarker_label;

    public static String CreateValidationTestPage_ExistsValidationBtn_label;

    public static String CreateValidationTestPage_group_label;

    public static String CreateValidationTestPage_MustSelectOneProbMarker_warning1;

    public static String CreateValidationTestPage_ProblemsToInc_label;

    public static String CreateValidationTestPage_ProblemsToInc_tooltip;

    public static String CreateValidationTestPage_QuickFixToExec_label;

    public static String CreateValidationTestPage_QuickFixToExec_tooltip;

    public static String CreateValidationTestPage_QuickShouldntDisplayDialog_label;

    public static String CreateValidationTestPage_SelectValdiationProblems_title;

    public static String CreateValidationTestPage_SelectValidationProblems_longdesc;

    public static String CreateValidationTestWizard_CreateValidationTest_title;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
