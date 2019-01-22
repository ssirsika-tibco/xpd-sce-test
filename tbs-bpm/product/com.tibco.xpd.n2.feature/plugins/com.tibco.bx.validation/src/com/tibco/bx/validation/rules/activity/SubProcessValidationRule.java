package com.tibco.bx.validation.rules.activity;

import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;

public class SubProcessValidationRule extends ActivityValidationRule {

	public static final String ISSUE_NO_N2_DESTINATION = "n2pe.noN2Destination.2"; //$NON-NLS-1$
	
	@Override
	public void validate(Activity activity) {
		if (activity.getImplementation() instanceof SubFlow) {
            SubFlow subFlow = (SubFlow) activity.getImplementation();
            Process subProcess = SubProcUtil.getSubProcess(subFlow);
            if (subProcess != null && !XPDLUtils.hasN2Destination(subProcess)) {
				addIssue(ISSUE_NO_N2_DESTINATION, activity);
			}
        }
	}

}
