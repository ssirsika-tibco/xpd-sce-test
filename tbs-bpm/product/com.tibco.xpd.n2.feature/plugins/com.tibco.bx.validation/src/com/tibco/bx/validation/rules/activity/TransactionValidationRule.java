package com.tibco.bx.validation.rules.activity;

import com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule;
import com.tibco.xpd.xpdl2.Activity;

public class TransactionValidationRule extends ActivityValidationRule {

	public static final String ISSUE_NO_TRANSACTION = "n2pe.noTransaction"; //$NON-NLS-1$

	@Override
	public void validate(Activity activity) {
		//check embedded sub-process
		if (activity.getBlockActivity()!= null) {
            if (activity.isIsATransaction()) {
				addIssue(ISSUE_NO_TRANSACTION, activity);
            }
        }
	}

}
