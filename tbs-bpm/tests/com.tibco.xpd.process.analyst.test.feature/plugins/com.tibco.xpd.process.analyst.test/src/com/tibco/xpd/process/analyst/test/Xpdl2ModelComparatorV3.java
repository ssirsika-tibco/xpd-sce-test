/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.analyst.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public final class Xpdl2ModelComparatorV3 {

    /**
     * Private constructor.
     */
    private Xpdl2ModelComparatorV3() {
    }

    /**
     * @param upgradeRoot
     * @param referenceRoot
     * @throws ModelComparatorException
     */
    public static void checkEquivalent(EObject eo1, EObject eo2)
            throws ModelComparatorException {
        if (eo1 instanceof Package && eo2 instanceof Package) {
            Package pckg1 = (Package) eo1;
            Package pckg2 = (Package) eo2;
            check(pckg1, pckg2);
        }
    }

    /**
     * @param pckg1
     * @param pckg2
     * @return
     * @throws ModelComparatorException
     */
    @SuppressWarnings("unchecked")
    private static void check(Package pckg1, Package pckg2)
            throws ModelComparatorException {
        List<Process> processes1 = pckg1.getProcesses();
        List<Process> processes2 = pckg2.getProcesses();
        int size = processes1.size();
        if (size == processes2.size()) {
            for (int i = 0; i < size; i++) {
                Process process1 = processes1.get(i);
                Process process2 = processes2.get(i);
                check(process1, process2);
            }
        } else {
            throw new ModelComparatorException("Different process count"); //$NON-NLS-1$
        }
    }

    /**
     * @param process1
     * @param process2
     * @return
     * @throws ModelComparatorException
     */
    @SuppressWarnings("unchecked")
    private static void check(Process process1, Process process2)
            throws ModelComparatorException {
        if (process1.getName().equals(process2.getName())) {
            List<Activity> activities1 = process1.getActivities();
            List<Activity> activities2 = process2.getActivities();
            int size = activities1.size();
            if (size == activities2.size()) {
                for (int i = 0; i < size; i++) {
                    Activity activity1 = activities1.get(i);
                    Activity activity2 = activities2.get(i);
                    check(activity1, activity2);
                }
            } else {
                throw new ModelComparatorException("Different activity count"); //$NON-NLS-1$
            }
        } else {
            throw new ModelComparatorException("Different process names"); //$NON-NLS-1$
        }
    }

    /**
     * @param activity1
     * @param activity2
     * @return
     * @throws ModelComparatorException
     */
    @SuppressWarnings("unchecked")
    private static void check(Activity activity1, Activity activity2)
            throws ModelComparatorException {
        String name1 = activity1.getName();
        String name2 = activity2.getName();
        if (name1 != null || name2 != null) {
            if (name1 != null && name2 != null) {
                if (!name1.equals(name2)) {
                    throw new ModelComparatorException(
                            "Activity Name different (" + name1 + "," + name2 //$NON-NLS-1$ //$NON-NLS-2$
                                    + ")"); //$NON-NLS-1$
                }
            } else {
                throw new ModelComparatorException("Activity Name mismatch"); //$NON-NLS-1$
            }
        }
        Implementation impl1 = activity1.getImplementation();
        Implementation impl2 = activity2.getImplementation();
        if (impl1 != null || impl2 != null) {
            if (impl1 instanceof Task && impl2 instanceof Task) {
                Task task1 = (Task) impl1;
                Task task2 = (Task) impl2;
                TaskService service1 = task1.getTaskService();
                TaskService service2 = task2.getTaskService();
                if (service1 != null || service2 != null) {
                    if (service1 != null && service2 != null) {
                        check(service1.getMessageIn(), service2.getMessageIn());
                        check(service1.getMessageOut(), service2
                                .getMessageOut());
                    } else {
                        throw new ModelComparatorException(
                                "TaskService mismatch"); //$NON-NLS-1$
                    }
                }
                TaskSend send1 = task1.getTaskSend();
                TaskSend send2 = task2.getTaskSend();
                if (send1 != null || send2 != null) {
                    if (send1 != null && send2 != null) {
                        check(send1.getMessage(), send2.getMessage());
                    } else {
                        throw new ModelComparatorException("TaskSend mismatch"); //$NON-NLS-1$
                    }
                }
                TaskReceive receive1 = task1.getTaskReceive();
                TaskReceive receive2 = task2.getTaskReceive();
                if (receive1 != null || receive2 != null) {
                    if (receive1 != null && receive2 != null) {
                        check(receive1.getMessage(), receive2.getMessage());
                    } else {
                        throw new ModelComparatorException(
                                "TaskReceive mismatch"); //$NON-NLS-1$
                    }
                }
            } else if (impl1 instanceof SubFlow && impl2 instanceof SubFlow) {
                SubFlow subflow1 = (SubFlow) impl1;
                SubFlow subflow2 = (SubFlow) impl2;
                List<DataMapping> mappings1 = getDataMappings(subflow1);
                List<DataMapping> mappings2 = getDataMappings(subflow2);
                int size = mappings1.size();
                if (size == mappings2.size()) {
                    for (int i = 0; i < size; i++) {
                        DataMapping mapping1 = mappings1.get(i);
                        DataMapping mapping2 = mappings2.get(i);
                        check(mapping1, mapping2);
                    }
                } else {
                    throw new ModelComparatorException(
                            "Different mapping count"); //$NON-NLS-1$
                }
            } else {
                throw new ModelComparatorException("Implementation mismatch"); //$NON-NLS-1$
            }
        }
        Event event1 = activity1.getEvent();
        Event event2 = activity2.getEvent();
        if (event1 != null || event2 != null) {
            if (event1 instanceof StartEvent && event2 instanceof StartEvent) {
                StartEvent start1 = (StartEvent) event1;
                StartEvent start2 = (StartEvent) event2;
                TriggerResultMessage result1 = start1.getTriggerResultMessage();
                TriggerResultMessage result2 = start2.getTriggerResultMessage();
                check(result1, result2);
            } else if (event1 instanceof IntermediateEvent
                    && event2 instanceof IntermediateEvent) {
                IntermediateEvent intermediate1 = (IntermediateEvent) event1;
                IntermediateEvent intermediate2 = (IntermediateEvent) event2;
                TriggerResultMessage result1 = intermediate1
                        .getTriggerResultMessage();
                TriggerResultMessage result2 = intermediate2
                        .getTriggerResultMessage();
                check(result1, result2);
            } else if (event1 instanceof EndEvent && event2 instanceof EndEvent) {
                EndEvent end1 = (EndEvent) event1;
                EndEvent end2 = (EndEvent) event2;
                TriggerResultMessage result1 = end1.getTriggerResultMessage();
                TriggerResultMessage result2 = end2.getTriggerResultMessage();
                check(result1, result2);
            }
        }
        AssociatedParameters associated1 = (AssociatedParameters) Xpdl2ModelUtil
                .getOtherElement(activity1, XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_AssociatedParameters());
        AssociatedParameters associated2 = (AssociatedParameters) Xpdl2ModelUtil
                .getOtherElement(activity2, XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_AssociatedParameters());
        check(associated1, associated2);
    }

    /**
     * @param associated1
     * @param associated2
     * @throws ModelComparatorException
     */
    @SuppressWarnings("unchecked")
    private static void check(AssociatedParameters associated1,
            AssociatedParameters associated2) throws ModelComparatorException {
        if (associated1 != null || associated2 != null) {
            if (associated1 != null && associated2 != null) {
                Comparator<AssociatedParameter> comparator = new Comparator<AssociatedParameter>() {
                    public int compare(AssociatedParameter p1,
                            AssociatedParameter p2) {
                        String name1 = p1.getFormalParam();
                        String name2 = p2.getFormalParam();
                        return name1.compareTo(name2);
                    }
                };
                List<AssociatedParameter> sorted1 = new ArrayList<AssociatedParameter>(
                        associated1.getAssociatedParameter());
                List<AssociatedParameter> sorted2 = new ArrayList<AssociatedParameter>(
                        associated2.getAssociatedParameter());
                Collections.sort(sorted1, comparator);
                Collections.sort(sorted2, comparator);
                int size = sorted1.size();
                if (size == sorted2.size()) {
                    for (int i = 0; i < size; i++) {
                        AssociatedParameter param1 = sorted1.get(i);
                        AssociatedParameter param2 = sorted2.get(i);
                        check(param1, param2);
                    }
                } else {
                    throw new ModelComparatorException(
                            "Different AssociatedParameters count"); //$NON-NLS-1$
                }
            } else {
                throw new ModelComparatorException(
                        "AssociatedParameters mismatch"); //$NON-NLS-1$
            }
        }
    }

    /**
     * @param param1
     * @param param2
     * @throws ModelComparatorException
     */
    private static void check(AssociatedParameter param1,
            AssociatedParameter param2) throws ModelComparatorException {
        String formal1 = param1.getFormalParam();
        String formal2 = param2.getFormalParam();
        if (formal1 != null || formal2 != null) {
            if (formal1 != null && formal2 != null && !formal1.equals(formal2)) {
                throw new ModelComparatorException(
                        "AssociatedParameters Formal Name different (" //$NON-NLS-1$
                                + formal1 + "," + formal2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$
            }
        } else {
            throw new ModelComparatorException(
                    "AssociatedParameters Formal Name mismatch"); //$NON-NLS-1$
        }
        ModeType mode1 = param1.getMode();
        ModeType mode2 = param2.getMode();
        if (mode1 != null || mode2 != null) {
            if (mode1 != null && mode2 != null && !mode1.equals(mode2)) {
                throw new ModelComparatorException(
                        "AssociatedParameters Mode different (" + mode1 + "," //$NON-NLS-1$ //$NON-NLS-2$
                                + mode2 + ")"); //$NON-NLS-1$
            }
        } else {
            throw new ModelComparatorException(
                    "AssociatedParameters Mode mismatch"); //$NON-NLS-1$
        }
        Description description1 = param1.getDescription();
        Description description2 = param2.getDescription();
        check(description1, description2);
    }

    /**
     * @param description1
     * @param description2
     * @throws ModelComparatorException
     */
    private static void check(Description description1, Description description2)
            throws ModelComparatorException {
        if (description1 != null || description2 != null) {
            if (description1 != null && description2 != null) {
                String value1 = description1.getValue();
                String value2 = description2.getValue();
                if (value1 != null || value2 != null) {
                    if (value1 != null && value2 != null
                            && !value1.equals(value2)) {
                        throw new ModelComparatorException(
                                "Description different (" + value1 + "," //$NON-NLS-1$ //$NON-NLS-2$
                                        + value2 + ")"); //$NON-NLS-1$
                    }
                } else {
                    throw new ModelComparatorException(
                            "Description Value mismatch"); //$NON-NLS-1$
                }
            }
        } else {
            throw new ModelComparatorException("Description mismatch"); //$NON-NLS-1$
        }
    }

    /**
     * @param result1
     * @param result2
     * @throws ModelComparatorException
     */
    private static void check(TriggerResultMessage result1,
            TriggerResultMessage result2) throws ModelComparatorException {
        if (result1 != null || result2 != null) {
            if (result1 != null && result2 != null) {
                Message message1 = result1.getMessage();
                Message message2 = result2.getMessage();
                check(message1, message2);
            } else {
                throw new ModelComparatorException(
                        "TriggerResultMessage mismatch"); //$NON-NLS-1$
            }
        }
    }

    /**
     * @param subflow1
     * @return
     */
    @SuppressWarnings("unchecked")
    private static List<DataMapping> getDataMappings(SubFlow subflow) {
        List<DataMapping> mappings = new ArrayList<DataMapping>(subflow
                .getDataMappings());
        Collections.sort(mappings, new Comparator<DataMapping>() {
            public int compare(DataMapping o1, DataMapping o2) {
                String formal1 = o1.getFormal();
                String formal2 = o2.getFormal();
                int result = formal1.compareTo(formal2);
                if (result == 0) {
                    String direction1 = o1.getDirection().getName();
                    String direction2 = o2.getDirection().getName();
                    result = direction1.compareTo(direction2);
                }
                return result;
            }
        });
        return mappings;
    }

    /**
     * @param message1
     * @param message2
     * @return
     * @throws ModelComparatorException
     */
    @SuppressWarnings("unchecked")
    private static void check(Message message1, Message message2)
            throws ModelComparatorException {
        List<DataMapping> mappings1 = message1.getDataMappings();
        List<DataMapping> mappings2 = message2.getDataMappings();
        int size = mappings1.size();
        if (size == mappings2.size()) {
            for (int i = 0; i < size; i++) {
                DataMapping mapping1 = mappings1.get(i);
                DataMapping mapping2 = mappings2.get(i);
                check(mapping1, mapping2);
            }
        } else {
            throw new ModelComparatorException("Different mapping count"); //$NON-NLS-1$
        }
    }

    /**
     * @param mapping1
     * @param mapping2
     * @return
     * @throws ModelComparatorException
     */
    private static void check(DataMapping mapping1, DataMapping mapping2)
            throws ModelComparatorException {
        String formal1 = mapping1.getFormal();
        String formal2 = mapping2.getFormal();
        if (!formal1.equals(formal2)) {
            throw new ModelComparatorException("DataMapping Formal mismatch(" //$NON-NLS-1$
                    + formal1 + ", " + formal2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        Expression actual1 = mapping1.getActual();
        Expression actual2 = mapping2.getActual();
        if (actual1 != null || actual2 != null) {
            if (actual1 != null && actual2 != null
                    && actual1.getText().equals(actual2.getText())) {
                String actualtext1 = actual1.getText();
                String actualtext2 = actual2.getText();
                if (!actualtext1.equals(actualtext2)) {
                    throw new ModelComparatorException(
                            "DataMapping Actual mismatch(" + formal1 + ", " //$NON-NLS-1$ //$NON-NLS-2$
                                    + formal2 + ")"); //$NON-NLS-1$
                }
            } else {
                throw new ModelComparatorException(
                        "DataMapping Actual mismatch(" + actual1.getText() //$NON-NLS-1$
                                + ", " + actual2.getText() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        Object other1 = Xpdl2ModelUtil.getOtherElement(mapping1,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script());
        Object other2 = Xpdl2ModelUtil.getOtherElement(mapping2,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script());
        if ((other1 != null && other2 == null)
                || (other1 == null && other2 != null)) {
            throw new ModelComparatorException("ScriptInformation mismatch"); //$NON-NLS-1$
        }
    }

}
