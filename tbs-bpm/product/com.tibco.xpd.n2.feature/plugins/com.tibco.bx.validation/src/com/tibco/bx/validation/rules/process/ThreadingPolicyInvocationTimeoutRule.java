/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdExtension.BpmRuntimeConfiguration;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rule that complains incoming request timeout (on process package) being not set if any process has incoming
 * correlation activities with replies
 * 
 * @author bharge
 * @since 24 Jul 2014
 * 
 * @deprecated Sid ACE-7608 Hide the advanced property "BPM Runtime Configuration... Incoming Request Timeout (seconds)"
 *             as no longer applicable to BPMe 5.x (disabled in plugin.xml contributions)
 */
@Deprecated
public class ThreadingPolicyInvocationTimeoutRule extends
        ProcessActivitiesValidationRule {

    public static final String INVOCATION_TIMEOUT_REQUIRED =
            "bx.invocationTimeoutRequired"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {

		if (Xpdl2ModelUtil.isCorrelatingActivity(activity))
		{

			if (ReplyActivityUtil.hasReplyActivities(activity))
			{

				/*
				 * check if the process package has bpm runtime configuration invocation time out set. if not raise a
				 * warning
				 */
				Package procPkg = activity.getProcess().getPackage();
				BpmRuntimeConfiguration bpmRuntimeConfiguration = (BpmRuntimeConfiguration) Xpdl2ModelUtil
						.getOtherElement(procPkg,
								XpdExtensionPackage.eINSTANCE.getDocumentRoot_BpmRuntimeConfiguration());

				if (null == bpmRuntimeConfiguration || null == bpmRuntimeConfiguration.getIncomingRequestTimeout())
				{

					addIssue(INVOCATION_TIMEOUT_REQUIRED, activity);
				}
			}
		}
    }

}
