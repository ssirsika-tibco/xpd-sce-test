/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * EntityDisplayNameRule
 * 
 * This rule validates the creation of empty names created in the wizard in
 * Windows 7
 * 
 * @author bharge
 * @since 3.3 (20 Jan 2010)
 */
public class EntityDisplayNameRule extends PackageValidationRule {

    private static final String EMPTY_NAME = "bpmn.emptyName"; //$NON-NLS-1$

    private static final String EMPTY_LABEL = "bpmn.emptyLabel"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(com
     * .tibco.xpd.xpdl2.Package)
     */
    @Override
    public void validate(Package pckg) {
        if (pckg != null) {

            validateNamedElements(pckg.getDataFields(), pckg);

            validateNamedElements(pckg.getParticipants(), pckg);

            validateNamedElements(pckg.getTypeDeclarations(), pckg);

            ProcessInterfaces processInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(pckg);
            if (processInterfaces != null) {
                List<ProcessInterface> processInterfaceList =
                        processInterfaces.getProcessInterface();
                // Validate Process Interfaces
                validateProcessInterfaces(processInterfaceList, pckg);
            }

            // Validate Processes
            validateProcesses(pckg.getProcesses(), pckg);
        }
    }

    private void validateNamedElements(
            List<? extends NamedElement> namedElementList, EObject container) {
        if (namedElementList != null && container != null) {
            for (NamedElement namedElement : namedElementList) {

                String name = namedElement.getName();
                if (null == name || name.equals("") || name.length() == 0) { //$NON-NLS-1$
                    addIssue(EMPTY_NAME, namedElement);
                }
                String displayName =
                        Xpdl2ModelUtil.getDisplayName(namedElement);
                if (null == displayName || displayName.equals("") //$NON-NLS-1$
                        || displayName.length() == 0) {
                    addIssue(EMPTY_LABEL, namedElement);
                }
            }
        }
    }

    private void validateProcessInterfaces(
            List<ProcessInterface> processInterfaces, Package pkg) {
        if (processInterfaces != null && pkg != null) {
            for (ProcessInterface processInterface : processInterfaces) {
                String name = processInterface.getName();
                if (null == name || name.equals("") || name.length() == 0) { //$NON-NLS-1$
                    addIssue(EMPTY_NAME, processInterface);
                }
                String displayName =
                        Xpdl2ModelUtil.getDisplayName(processInterface);
                if (null == displayName || displayName.equals("") //$NON-NLS-1$
                        || displayName.length() == 0) {
                    addIssue(EMPTY_LABEL, processInterface);
                }
                validateNamedElements(processInterface.getFormalParameters(),
                        processInterface);

                validateNamedElements(processInterface.getStartMethods(),
                        processInterface);

                validateNamedElements(processInterface.getIntermediateMethods(),
                        processInterface);

            }
        }
    }

    private void validateProcesses(List<Process> processes, Package pkg) {
        if (processes != null && pkg != null) {
            for (Process process : processes) {
                String processDisplayName =
                        Xpdl2ModelUtil.getDisplayName(process);
                if (null == processDisplayName || processDisplayName.equals("") //$NON-NLS-1$
                        || processDisplayName.length() == 0) {
                    addIssue(EMPTY_LABEL, process);
                }
                String name = process.getName();
                if (null == name || name.equals("") || name.length() == 0) { //$NON-NLS-1$
                    addIssue(EMPTY_NAME, process);
                }

                validateNamedElements(process.getFormalParameters(), process);

                /*
                 * XPD-6949: Saket: We need to take the activity level
                 * datafields into account as well.
                 */
                List<DataField> allDataFieldsInProc =
                        new ArrayList<DataField>();

                allDataFieldsInProc.addAll(process.getDataFields());

                Collection<Activity> activities =
                        Xpdl2ModelUtil.getAllActivitiesInProc(process);

                for (Activity activity : activities) {

                    allDataFieldsInProc.addAll(activity.getDataFields());
                }

                validateNamedElements(allDataFieldsInProc, process);

                validateNamedElements(process.getParticipants(), process);
            }
        }
    }

}
