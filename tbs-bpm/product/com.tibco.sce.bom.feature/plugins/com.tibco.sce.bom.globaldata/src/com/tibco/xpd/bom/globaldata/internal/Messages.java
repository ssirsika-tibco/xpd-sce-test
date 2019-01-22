/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.bom.globaldata.internal;

import org.eclipse.osgi.util.NLS;

/**
 *
 *
 * @author kthombar
 * @since Jul 6, 2015
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.bom.globaldata.internal.messages"; //$NON-NLS-1$

    public static String EnableDisableBizDataProfileChange_CannotAccessWorkingCopyError_msg;

    public static String EnableDisableBizDataProfileChange_DisableBizDataProfileChange_label;

    public static String EnableDisableBizDataProfileChange_EnableBizDataProfileChange_label;

    public static String ManageBizDataCapabilityOnBOMMoveParticipant_BizDataProfileParticipant_name;

    public static String ManageBizDataCapabilityOnBOMMoveParticipant_CannotAccessWorkingCopyError_msg;

    public static String ManageBizDataCapabilityOnBOMMoveParticipant_EnableBizDataProfileChange_name;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
