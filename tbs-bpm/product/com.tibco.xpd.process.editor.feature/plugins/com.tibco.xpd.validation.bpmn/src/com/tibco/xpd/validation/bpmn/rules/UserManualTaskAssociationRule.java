/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.Collections;
import java.util.Map;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj (later abstracted by aallway)
 * 
 */
public class UserManualTaskAssociationRule extends
        AbstractUserManualTaskAssociationRule {

    private final static String ID = "bpmn.userTaskReadonlyAssociation_1"; //$NON-NLS-1$

    @Override
    protected void addReadOnlyDataMustBeModeInIssue(Activity activity,
            ProcessRelevantData data, Map<String, String> extraIssueInfo) {
        addIssue(ID, activity, Collections.singletonList(Xpdl2ModelUtil
                .getDisplayNameOrName(data)), extraIssueInfo);
    }

}
