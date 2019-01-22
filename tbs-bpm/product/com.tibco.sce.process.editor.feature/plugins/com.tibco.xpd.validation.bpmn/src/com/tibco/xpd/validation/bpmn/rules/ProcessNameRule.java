/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * @author nwilson
 */
public class ProcessNameRule extends PackageValidationRule {

    /** Issue ID. */
    public static final String ID = "bpmn.processName"; //$NON-NLS-1$

    /**
     * @param pckg The package to validate.
     * @see com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(
     *      com.tibco.xpd.xpdl2.Package)
     */
    @Override
    public void validate(Package pckg) {
        List<String> names = new ArrayList<String>();
        List<String> duplicates = new ArrayList<String>();
        List processes = pckg.getProcesses();
        for (Object next : processes) {
            Process process = (Process) next;
            String name = process.getName();
            if (name == null) {
                name = "unnamed"; //$NON-NLS-1$
            }
            if (!names.contains(name)) {
                names.add(name);
            } else {
                if (!duplicates.contains(name)) {
                    duplicates.add(name);
                }
            }
        }
        for (Object next : processes) {
            Process process = (Process) next;
            String name = process.getName();
            if (name == null) {
                name = "unnamed"; //$NON-NLS-1$
            }
            if (duplicates.contains(name)) {
                addIssue(ID, process);
            }
        }
    }

}
