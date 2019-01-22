/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.rules.Xpdl2ValidationRule;
import com.tibco.xpd.wsdlgen.WsdlGenBuilderTransformer;
import com.tibco.xpd.wsdlgen.WsdlgenPlugin;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;
import com.tibco.xpd.xpdl2.util.XpdlSearchUtil;

/**
 * Looks at a given package to see whether it requires a WSDL generated. If it
 * does require a WSDL to be generated, and there is a WSDL in place but is not
 * marked DERIVED, then a warning level message is added on the XPDL.
 * 
 * @author rsomayaj
 * 
 */
public class WSDLGenDervivedWSDLRule extends Xpdl2ValidationRule {

    /**
     * Severity: Warning
     * 
     * Message:
     * "The WSDL file will not be updated with the XPDL changes because it is not marked as Derived."
     */
    private static final String ISSUE_ID = "bpmn.dev.wsdlGenNotDerived"; //$NON-NLS-1$

    @Override
    protected void validate(Object o) {
        // If the package requires a WSDL
        Package xpdlPackage = (Package) o;
        if (doesNeedWSDL(xpdlPackage)) {
            IFile xpdlFile = WorkingCopyUtil.getFile(xpdlPackage);
            IFile wsdlFile = WsdlGenBuilderTransformer.getWsdlFile(xpdlFile);
            if (wsdlFile != null && wsdlFile.exists() && !wsdlFile.isDerived()) {
                // WARN THE USER FOR NON DERIVED WSDL
                addIssue(ISSUE_ID, xpdlPackage);
            }
        }
    }

    @Override
    public Class<?> getTargetClass() {
        return Package.class;
    }

    /**
     * @param pkg
     * @return
     */
    private boolean doesNeedWSDL(Package pkg) {
        EList<Process> processes = pkg.getProcesses();
        for (Process proc : processes) {
            if (isPortTypeRequiredForProcess(proc)) {
                return true;
            }
        }
        ProcessInterfaces processInterfaces =
                ProcessInterfaceUtil.getProcessInterfaces(pkg);
        if (processInterfaces != null) {
            for (ProcessInterface procIfc : processInterfaces
                    .getProcessInterface()) {
                if (isPortTypeRequiredForProcIfc(procIfc)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether a port type is required by a process. Check is confirmed
     * if the process does not have a NOT GENERATE validation component set and
     * if it contains API activities
     * 
     * @param proc
     */
    private boolean isPortTypeRequiredForProcess(Process proc) {
        Boolean shouldGen =
                ProcessDestinationUtil
                        .shouldGenerateWSDLForProcessDestinations(proc);
        if (!shouldGen) {
            return false;
        }
        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(proc);

        for (Activity act : allActivitiesInProc) {
            if (act.getEvent() != null) {
                EventFlowType flowType = EventObjectUtil.getFlowType(act);
                EventTriggerType eventTriggerType =
                        EventObjectUtil.getEventTriggerType(act);
                if (flowType != null) {
                    if (EventFlowType.FLOW_START_LITERAL.equals(flowType)) {
                        if (act.eContainer() != null
                                && act.eContainer().equals(act.getProcess())) {
                            if (EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL
                                    .equals(eventTriggerType)) {
                                return true;
                            }
                            if (EventTriggerType.EVENT_NONE_LITERAL
                                    .equals(eventTriggerType)
                                    && doesContainRequiredExtendedAttribute(proc)) {
                                return true;
                            }
                        }
                    } else if (EventFlowType.FLOW_INTERMEDIATE_LITERAL
                            .equals(flowType)) {
                        if (EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL
                                .equals(eventTriggerType)) {
                            return true;
                        }
                    }
                }
            } else {
                TaskType taskType = TaskObjectUtil.getTaskType(act);
                if (TaskType.RECEIVE_LITERAL.equals(taskType)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true, if the process contains an extended attribute called
     * "InternalJmxDebug" with value "true"(Case ignored on the value).
     * 
     * @param process
     *            the process in consideration
     * 
     */
    public static boolean doesContainRequiredExtendedAttribute(Process process) {
        ExtendedAttribute internalJmxDebugExtAttr =
                XpdlSearchUtil.findExtendedAttribute(process,
                        WsdlgenPlugin.INTERNAL_JMX_DEBUG);
        if (internalJmxDebugExtAttr != null
                && internalJmxDebugExtAttr.getValue() != null
                && internalJmxDebugExtAttr.getValue().equalsIgnoreCase("true")) { //$NON-NLS-1$
            return true;
        }
        return false;
    }

    /**
     * Checks whether a port type is required by a process interface. Check is
     * confirmed if the process interface does not have a NOT GENERATE
     * validation component set and if it contains Message events (API
     * Activities).
     * 
     * @param procIfc
     * @return
     */
    private boolean isPortTypeRequiredForProcIfc(ProcessInterface procIfc) {
        Boolean shouldGen =
                ProcessDestinationUtil
                        .shouldGenerateWSDLForProcessInterfaceDestinations(procIfc);
        if (!shouldGen) {
            return false;
        }
        List<StartMethod> startMethods = procIfc.getStartMethods();
        if (startMethods.size() > 0) {
            return true;
        }

        List<IntermediateMethod> intermediateMethods =
                procIfc.getIntermediateMethods();
        for (IntermediateMethod intermediateMethod : intermediateMethods) {
            if (TriggerType.MESSAGE_LITERAL.equals(intermediateMethod
                    .getTrigger())) {
                return true;
            }
        }
        return false;

    }

}
