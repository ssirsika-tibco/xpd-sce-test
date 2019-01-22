/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.v2.xpdl1.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class MultiplePoolRule extends FlowContainerValidationRule {

    /** The issue ID. */
    public static final String ID = "xpdl1.multiplePools"; //$NON-NLS-1$

    /**
     * @param container
     *            The container to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate(FlowContainer)
     */
    @Override
    public void validate(FlowContainer container) {
        if (container instanceof Process) {
            Process process = (Process) container;

            List<Pool> pools =
                    new ArrayList<Pool>(Xpdl2ModelUtil.getProcessPools(process));
            if (pools.size() > 1) {
                addIssue(ID, pools.get(1));
            }

        }
    }

}
