/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Property tester specifically for ProcessEditorConfiguration (so that users of
 * that extension point have a coherent set of properties that they can test).
 * <p>
 * All properties designed to work on any descendant EObject of a
 * {@link Process}
 * 
 * @author aallway
 * @since 1 Nov 2011
 */
public class ProcessEditorConfigurationPropertyTester extends PropertyTester {

    private static final String IS_DESTINATION_ENABLED = "IsDestinationEnabled"; //$NON-NLS-1$

    private static final String IS_THE_ONLY_DESTINATION_ENABLED =
            "IsTheOnlyDestinationEnabled"; //$NON-NLS-1$

    private static final String IS_PROJECT_DESTINATION_ENABLED =
            "IsProjectDestinationEnabled"; //$NON-NLS-1$

    private static final String IS_ONLY_DESTINATION_ENABLED_ON_PROJECT =
            "IsOnlyDestinationEnabledOnProject"; //$NON-NLS-1$

    private static final String IS_FILE_EXTENSION = "IsFileExtension"; //$NON-NLS-1$

    private static final String IS_TASK_LIBRARY = "IsTaskLibrary"; //$NON-NLS-1$

    private static final String IS_DECISION_FLOW = "IsDecisionFlow"; //$NON-NLS-1$

    private static final String IS_BUSINESS_SERVICE = "IsBusinessService"; //$NON-NLS-1$

    private static final String IS_CASE_SERVICE = "IsCaseService"; //$NON-NLS-1$

    private static final String IS_BUSINESS_PROCESS = "IsBusinessProcess"; //$NON-NLS-1$

    private static final String IS_PAGEFLOW = "IsPageFlow"; //$NON-NLS-1$

    private static final String IS_PROCESS_INTERFACE = "IsProcessInterface"; //$NON-NLS-1$

    private static final String IS_SERVICE_PROCESS_INTERFACE =
            "IsServiceProcessInterface"; //$NON-NLS-1$

    private static final String IS_SERVICE_PROCESS = "IsServiceProcess"; //$NON-NLS-1$

    /**
     * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object,
     *      java.lang.String, java.lang.Object[], java.lang.Object)
     * 
     * @param receiver
     * @param property
     * @param args
     * @param expectedValue
     * @return <code>true</code> if the proeprty has the expected value.
     */
    @Override
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {

        if (receiver instanceof EObject) {

            Boolean booleanExpectedValue =
                    getBooleanExpectedValue(expectedValue);

            if (IS_FILE_EXTENSION.equals(property)) {
                /*
                 * Test that the given process is or isn't contained in a file
                 * with the given extension.
                 * 
                 * Args are the file extensions it may not seem to make sense to
                 * have multiples but then you can have "IsFileExtension"
                 * args="xpdl, tasks" value="false"
                 */
                IFile file = WorkingCopyUtil.getFile((EObject) receiver);

                if (file != null && args != null && args.length > 0) {
                    String fileExt = file.getFileExtension();

                    if (fileExt != null) {
                        boolean allTrue = true;
                        for (Object arg : args) {
                            if (!(arg instanceof String)
                                    || fileExt.equalsIgnoreCase((String) arg) != booleanExpectedValue) {
                                allTrue = false;
                                break;
                            }
                        }

                        if (allTrue) {
                            return true;
                        }
                    }
                }

            } else if (IS_PROCESS_INTERFACE.equals(property)) {

                EObject processInterface =
                        Xpdl2ModelUtil.getAncestor((EObject) receiver,
                                ProcessInterface.class);

                Boolean test;

                if (processInterface instanceof ProcessInterface) {
                    test = Boolean.TRUE;
                } else {
                    test = Boolean.FALSE;
                }

                return test.equals(booleanExpectedValue);
            } else if (IS_SERVICE_PROCESS_INTERFACE.equals(property)) {

                EObject processInterface =
                        Xpdl2ModelUtil.getAncestor((EObject) receiver,
                                ProcessInterface.class);
                if (processInterface instanceof ProcessInterface) {

                    Boolean test =
                            Xpdl2ModelUtil
                                    .isServiceProcessInterface((ProcessInterface) processInterface);
                    return test.equals(booleanExpectedValue);
                }

            } else if (IS_DESTINATION_ENABLED.equals(property)) {
                /*
                 * Test that the given process (or combined destinations of all
                 * processes if receiver is package or child of package) has all
                 * the global destination of given types (by global destination
                 * id) enabled
                 */
                if (args != null && args.length > 0) {

                    Set<String> enabledTypes = null;

                    if (receiver instanceof Package) {

                        if (doesPackageHaveProcessOrInterface((Package) receiver)) {
                            enabledTypes =
                                    DestinationUtil
                                            .getEnabledGlobalDestinationTypes((EObject) receiver);
                        } else {
                            /*
                             * If the package has no processes or interfaces in
                             * it then fetch the Destinations from the parent
                             * project.
                             */
                            IProject parentProject =
                                    WorkingCopyUtil
                                            .getProjectFor((EObject) receiver);

                            if (parentProject != null) {

                                enabledTypes =
                                        GlobalDestinationUtil
                                                .getEnabledGlobalDestinations(parentProject,
                                                        false);
                            }
                        }

                    } else {
                        enabledTypes =
                                DestinationUtil
                                        .getEnabledGlobalDestinationTypes((EObject) receiver);
                    }

                    boolean containsAll = true;

                    if (enabledTypes != null && !enabledTypes.isEmpty()) {
                        for (Object arg : args) {
                            if (!enabledTypes.contains(arg)) {
                                containsAll = false;
                                break;
                            }
                        }
                    } else {
                        containsAll = false;
                    }

                    return (containsAll == booleanExpectedValue);
                }

            } else if (IS_THE_ONLY_DESTINATION_ENABLED.equals(property)) {
                /*
                 * Test that the given process (or combined destinations of all
                 * processes if receiver is package or child of package) has all
                 * of the given destinations enabled AND does
                 */
                if (args != null && args.length > 0) {

                    Set<String> enabledTypes = null;

                    if (receiver instanceof Package) {

                        if (doesPackageHaveProcessOrInterface((Package) receiver)) {
                            enabledTypes =
                                    DestinationUtil
                                            .getEnabledGlobalDestinationTypes((EObject) receiver);
                        } else {
                            /*
                             * If the package has no processes or interfaces in
                             * it then fetch the Destinations from the parent
                             * project.
                             */
                            IProject parentProject =
                                    WorkingCopyUtil
                                            .getProjectFor((EObject) receiver);

                            if (parentProject != null) {

                                enabledTypes =
                                        GlobalDestinationUtil
                                                .getEnabledGlobalDestinations(parentProject,
                                                        false);
                            }
                        }

                    } else {
                        enabledTypes =
                                DestinationUtil
                                        .getEnabledGlobalDestinationTypes((EObject) receiver);
                    }

                    Set<String> requiredTypes = new HashSet<String>();

                    for (Object arg : args) {
                        if (arg instanceof String) {
                            requiredTypes.add((String) arg);
                        }
                    }

                    boolean hasAll = enabledTypes.containsAll(requiredTypes);

                    boolean hasOthers = false;
                    if (enabledTypes.size() > 0) {
                        for (String enabledType : enabledTypes) {
                            if (!requiredTypes.contains(enabledType)) {
                                hasOthers = true;
                                break;
                            }
                        }
                    }

                    return (hasAll && !hasOthers) == booleanExpectedValue;
                }

            } else if (IS_PROJECT_DESTINATION_ENABLED.equals(property)) {

                if (args != null && args.length > 0) {
                    /*
                     * get the parent project
                     */
                    IProject parentProject =
                            WorkingCopyUtil.getProjectFor((EObject) receiver);

                    if (parentProject != null) {
                        /*
                         * get the destinations enabled on parent project
                         */
                        Set<String> enabledTypes =
                                GlobalDestinationUtil
                                        .getEnabledGlobalDestinations(parentProject,
                                                false);

                        boolean containsAll = true;

                        if (enabledTypes != null && !enabledTypes.isEmpty()) {
                            for (Object arg : args) {
                                if (!enabledTypes.contains(arg)) {
                                    containsAll = false;
                                    break;
                                }
                            }
                        } else {
                            containsAll = false;
                        }

                        return (containsAll == booleanExpectedValue);
                    }
                }

            } else if (IS_ONLY_DESTINATION_ENABLED_ON_PROJECT.equals(property)) {

                if (args != null && args.length > 0) {
                    /*
                     * get the parent project
                     */
                    IProject parentProject =
                            WorkingCopyUtil.getProjectFor((EObject) receiver);

                    if (parentProject != null) {
                        /*
                         * get the destinations enabled on parent project
                         */
                        Set<String> enabledTypes =
                                GlobalDestinationUtil
                                        .getEnabledGlobalDestinations(parentProject,
                                                false);

                        Set<String> requiredTypes = new HashSet<String>();

                        for (Object arg : args) {
                            if (arg instanceof String) {
                                requiredTypes.add((String) arg);
                            }
                        }

                        boolean hasAll =
                                enabledTypes.containsAll(requiredTypes);

                        boolean hasOthers = false;
                        if (enabledTypes != null && !enabledTypes.isEmpty()) {
                            for (String enabledType : enabledTypes) {
                                if (!requiredTypes.contains(enabledType)) {
                                    hasOthers = true;
                                    break;
                                }
                            }
                        }

                        return (hasAll && !hasOthers) == booleanExpectedValue;
                    }
                }
            } else {
                /*
                 * Process specific tests... Get the process / process interface
                 * ancestor of the given EObject.
                 */
                Process process = Xpdl2ModelUtil.getProcess((EObject) receiver);

                if (process != null) {

                    if (IS_PAGEFLOW.equalsIgnoreCase(property)) {
                        Boolean test =
                                (Xpdl2ModelUtil.isPageflow(process) && !Xpdl2ModelUtil
                                        .isPageflowBusinessService(process));
                        return test.equals(booleanExpectedValue);

                    } else if (IS_BUSINESS_PROCESS.equalsIgnoreCase(property)) {
                        Boolean test =
                                Xpdl2ModelUtil.isBusinessProcess(process);
                        return test.equals(booleanExpectedValue);

                    } else if (IS_BUSINESS_SERVICE.equalsIgnoreCase(property)) {
                        Boolean test =
                                Xpdl2ModelUtil
                                        .isPageflowBusinessService(process);
                        return test.equals(booleanExpectedValue);
                    } else if (IS_CASE_SERVICE.equalsIgnoreCase(property)) {

                        Boolean test = Xpdl2ModelUtil.isCaseService(process);
                        return test.equals(booleanExpectedValue);
                    } else if (IS_TASK_LIBRARY.equalsIgnoreCase(property)) {
                        Boolean test = Xpdl2ModelUtil.isTaskLibrary(process);
                        return test.equals(booleanExpectedValue);

                    } else if (IS_DECISION_FLOW.equalsIgnoreCase(property)) {
                        Boolean test = DecisionFlowUtil.isDecisionFlow(process);
                        return test.equals(booleanExpectedValue);

                    } else if (IS_SERVICE_PROCESS.equalsIgnoreCase(property)) {

                        Boolean test = Xpdl2ModelUtil.isServiceProcess(process);
                        return test.equals(booleanExpectedValue);
                    }
                }
            }
        }

        return false;
    }

    /**
     * 
     * @param pkg
     * @return <code>true</code> if the passed Package has atleast one process
     *         or process interface, otherwise return <code>false</code>
     */
    private boolean doesPackageHaveProcessOrInterface(Package pkg) {

        EList<Process> processes = pkg.getProcesses();

        if (!processes.isEmpty()) {

            return true;

        } else {

            ProcessInterfaces processInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(pkg);

            if (processInterfaces != null) {

                EList<ProcessInterface> processInterface =
                        processInterfaces.getProcessInterface();

                if (!processInterface.isEmpty()) {

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Normally eclipse coerces the value "true" / "false" to boolean but that
     * won't happen if you use mixed case, so wwill do that
     * 
     * @param expectedValue
     * @return
     */
    protected Boolean getBooleanExpectedValue(Object expectedValue) {
        Boolean booleanExpectedValue;

        if (expectedValue instanceof Boolean) {
            booleanExpectedValue = (Boolean) expectedValue;

        } else if (expectedValue instanceof String
                && "TRUE".equalsIgnoreCase((String) expectedValue)) { //$NON-NLS-1$
            booleanExpectedValue = true;

        } else if (expectedValue instanceof String
                && "FALSE".equalsIgnoreCase((String) expectedValue)) { //$NON-NLS-1$
            booleanExpectedValue = false;

        } else {
            booleanExpectedValue = false;
        }
        return booleanExpectedValue;
    }
}
