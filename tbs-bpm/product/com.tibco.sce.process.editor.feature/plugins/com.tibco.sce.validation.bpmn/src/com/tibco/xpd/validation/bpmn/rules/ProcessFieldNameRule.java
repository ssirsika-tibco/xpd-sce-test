/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * @author nwilson
 */
public class ProcessFieldNameRule extends ProcessValidationRule {

    /** The issue ID. */
    public static final String ID = "bpmn.duplicateFieldName"; //$NON-NLS-1$

    /**
     * @param process The process to check.
     */
    @Override
    public void validate(Process process) {
        Package pckg = process.getPackage();
        List<String> names = new ArrayList<String>();
        List<String> duplicates = new ArrayList<String>();
        List pckgFields = pckg.getDataFields();
        for (Object next : pckgFields) {
            DataField field = (DataField) next;
            String name = field.getName();
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
        List fields = process.getDataFields();
        for (Object next : fields) {
            DataField field = (DataField) next;
            String name = field.getName();
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
        List fpList = ProcessInterfaceUtil.getAllFormalParameters(process);
        for (Object next : fpList) {
            FormalParameter fp = (FormalParameter) next;
            String name = fp.getName();
            if (!names.contains(name)) {
                names.add(name);
            } else {
                if (!duplicates.contains(name)) {
                    duplicates.add(name);
                }
            }
        }
        for (Object next : pckgFields) {
            DataField field = (DataField) next;
            String name = field.getName();
            if (name == null) {
                name = "unnamed"; //$NON-NLS-1$
            }
            if (duplicates.contains(name)) {
                addIssue(ID, field);
            }
        }
        for (Object next : fields) {
            DataField field = (DataField) next;
            String name = field.getName();
            if (name == null) {
                name = "unnamed"; //$NON-NLS-1$
            }
            if (duplicates.contains(name)) {
                addIssue(ID, field);
            }
        }
        for (Object next : fpList) {
            FormalParameter fp = (FormalParameter) next;
            String name = fp.getName();
            if (duplicates.contains(name)) {
                addIssue(ID, fp);
            }
        }
    }

    @Override
    protected void validateFlowContainer(Process process, EList<Activity> activities, EList<Transition> transitions) { 
    }

}
