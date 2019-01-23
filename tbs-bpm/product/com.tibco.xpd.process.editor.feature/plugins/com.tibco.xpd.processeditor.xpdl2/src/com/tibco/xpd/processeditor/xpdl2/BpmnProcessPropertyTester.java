/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Process editor related property testers (for extension point enablement
 * disablement).
 * 
 * @author aallway
 * @since 3.2
 */
public class BpmnProcessPropertyTester extends PropertyTester {

    /**
     * Is the ProcessEditorInput for a Workflow Process RATHER than, say, a
     * TaskLibrary.
     */
    private static final String IS_BPMN_PROCESS_EDITOR_INPUT =
            "isBpmnProcessEditorInput"; //$NON-NLS-1$

    /**
     * Is the given object an xpdl2 process for a BPmn process (RATHER than,
     * say, a TaskLibrary).
     */
    private static final String IS_BPMN_PROCESS = "isBpmnProcess"; //$NON-NLS-1$

    /**
     * Is the given object an xpdl2 process for a business process
     */
    private static final String IS_BUSINESS_PROCESS = "isBusinessProcess"; //$NON-NLS-1$

    /**
     * Is the given object an xpdl2 process for a Pageflow process
     */
    private static final String IS_PAGEFLOW_PROCESS = "isPageflowProcess"; //$NON-NLS-1$

    /**
     * Is the given object an xpdl2 process for a Service process
     */
    private static final String IS_SERVICE_PROCESS = "isServiceProcess"; //$NON-NLS-1$

    /**
     * Is the given object a service process interface
     */
    private static final String IS_SERVICE_PROCESS_INTERFACE =
            "isServiceProcessInterface"; //$NON-NLS-1$

    /**
     * Is the given object a process interface
     */
    private static final String IS_PROCESS_INTERFACE = "isProcessInterface"; //$NON-NLS-1$

    /**
     * Is the given object child content of a process in the project explorer.
     */
    private static final String IS_BPMN_PROCESS_CONTENT =
            "isBpmnProcessContent"; //$NON-NLS-1$

    /**
     * 
     */
    public BpmnProcessPropertyTester() {
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object,
     * java.lang.String, java.lang.Object[], java.lang.Object)
     */
    @Override
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {

        if (IS_BPMN_PROCESS_EDITOR_INPUT.equals(property)) {

            if (receiver instanceof ProcessEditorInput) {

                Process process = ((ProcessEditorInput) receiver).getProcess();
                return isBpmnProcess(process);
            }
        } else if (IS_BPMN_PROCESS.equals(property)) {

            if (receiver instanceof Process) {

                return isBpmnProcess((Process) receiver);
            }
        } else if (IS_PAGEFLOW_PROCESS.equals(property)) {

            if (receiver instanceof Process) {

                return isBpmnProcess((Process) receiver)
                        && Xpdl2ModelUtil.isPageflow((Process) receiver);
            }
        } else if (IS_BUSINESS_PROCESS.equals(property)) {

            if (receiver instanceof Process) {

                return isBpmnProcess((Process) receiver)
                        && Xpdl2ModelUtil.isBusinessProcess((Process) receiver);
            }
        } else if (IS_SERVICE_PROCESS.equals(property)) {

            if (receiver instanceof Process) {

                return Xpdl2ModelUtil.isServiceProcess((Process) receiver);
            }
        } else if (IS_PROCESS_INTERFACE.equals(property)) {

            if (receiver instanceof ProcessInterface) {

                return Xpdl2ModelUtil
                        .isProcessInterface((ProcessInterface) receiver);
            }
        } else if (IS_SERVICE_PROCESS_INTERFACE.equals(property)) {

            if (receiver instanceof ProcessInterface) {

                return Xpdl2ModelUtil
                        .isServiceProcessInterface((ProcessInterface) receiver);
            }
        } else if (IS_BPMN_PROCESS_CONTENT.equals(property)) {

            if (receiver instanceof EObject) {

                return isBpmnProcessContent((EObject) receiver);

            } else if (receiver instanceof INavigatorGroup) {

                Object o = ((INavigatorGroup) receiver).getParent();
                if (o instanceof EObject) {

                    return isBpmnProcessContent((EObject) o);
                }
            }
        }
        return false;
    }

    /**
     * @param process
     * @return true if xpdl2 process is a Bpmn flow process rather than
     *         something else like a task library.
     */
    public static boolean isBpmnProcess(Process process) {
        if (process != null) {
            /*
             * XPD-1140: Cannot rely on whether the process is in a .xpdl file
             * to say "it's a bpmn process" because when opening process from a
             * history revision there is no file! Use the later -introduced
             * isTaskLibrary attribute instead.
             */
            return (!Xpdl2ModelUtil.isTaskLibrary(process) && !DecisionFlowUtil
                    .isDecisionFlow(process));
        }
        return false;
    }

    /**
     * @param eObject
     * @return true if the given object is the child content of a bpmn process
     */
    public static boolean isBpmnProcessContent(EObject eObject) {
        while (eObject != null) {
            if (eObject instanceof Process) {
                if (isBpmnProcess((Process) eObject)) {
                    return true;
                }
            }
            eObject = eObject.eContainer();
        }
        return false;
    }
}
