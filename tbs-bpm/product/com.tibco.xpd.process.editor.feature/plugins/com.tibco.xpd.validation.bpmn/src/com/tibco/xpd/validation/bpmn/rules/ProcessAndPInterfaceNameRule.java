/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class ProcessAndPInterfaceNameRule extends PackageValidationRule {

    /** Issue ID. */
    public static final String ID = "bpmn.processAndPInterfaceName"; //$NON-NLS-1$

    /**
     * @param pckg The package to validate.
     * @see com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(
     *      com.tibco.xpd.xpdl2.Package)
     */
    @Override
    public void validate(Package pckg) {
        List<String> processNames = new ArrayList<String>();
        List<String> processInterfaceNames = new ArrayList<String>();
        List<String> duplicates = new ArrayList<String>();
        List processes = pckg.getProcesses();
        List<ProcessInterface> processInterfaces = new ArrayList<ProcessInterface>();
        if (ProcessInterfaceUtil.getProcessInterfaces(pckg) != null) {
            processInterfaces = ProcessInterfaceUtil
                    .getProcessInterfaces(pckg).getProcessInterface();
        }
        for (Object next : processes) {
            Process process = (Process) next;
            String name = process.getName();
            if (name == null) {
                name = "unnamed"; //$NON-NLS-1$
            }
            if (!processNames.contains(name)) {
                processNames.add(name);
            } 
        }
        for (ProcessInterface processInterface : processInterfaces) {
            String name = processInterface.getName();
            if (name == null) {
                name = "unnamed"; //$NON-NLS-1$
            }
            if (!processInterfaceNames.contains(name)) {
                processInterfaceNames.add(name);
            } 
        }
        
        for (Object next : processes) {
            Process process = (Process) next;
            String name = process.getName();
            if (name == null) {
                name = "unnamed"; //$NON-NLS-1$
            }
            if (processInterfaceNames.contains(name)) {
                addIssue(ID, process);
                List<ProcessInterface> affectedProcessInterfaces =
                        getProcessInterfaces(name, processInterfaces);
                for (ProcessInterface processInterface : affectedProcessInterfaces) {
                    addIssue(ID, processInterface);
                }
            }
        }
    }
    
    /**
     * This method returns the list of process interfaces for the given name
     * 
     * @param name the name to match.
     * @param processInterfaces the list of process interfaces
     * 
     * @return the list of process interfaces that match the given name
     */
    private List<ProcessInterface> getProcessInterfaces(String name,
            List<ProcessInterface> processInterfaces) {
        List<ProcessInterface> processInterfacesWithName =
                new ArrayList<ProcessInterface>();
        if (name != null) {
            for (ProcessInterface processInterface : processInterfaces) {
                if (processInterface != null
                        && processInterface.getName() != null
                        && processInterface.getName().equals(name)) {
                    processInterfacesWithName.add(processInterface);
                }
            }
        }
        return processInterfacesWithName;
    }

}
