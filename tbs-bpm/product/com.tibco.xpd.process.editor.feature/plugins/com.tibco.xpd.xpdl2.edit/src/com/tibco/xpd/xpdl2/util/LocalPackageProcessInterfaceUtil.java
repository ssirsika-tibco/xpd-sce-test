/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.xpdl2.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;

/**
 * Process interface related utilities
 * <p>
 * <b>NOTE: </b>These utilities will only function for process interfaces
 * defined in the SAME PACKAGE as a process implementation.
 * <p>
 * If you need to handlre interfaces defined in external packages then you
 * should use analyst.resources.xpdl2.ProcessInterfaceUtil which has access via
 * workingCopy to other packages.
 * 
 * @author aallway
 * 
 */
public class LocalPackageProcessInterfaceUtil {

    public static List<FormalParameter> getAllFormalParameters(Process process) {
        List<FormalParameter> formalParams = new ArrayList<FormalParameter>();

        ProcessInterface processIfc = getImplementedProcessInterface(process);
        if (processIfc != null) {
            formalParams.addAll(processIfc.getFormalParameters());
        }

        formalParams.addAll(process.getFormalParameters());

        return formalParams;
    }

    public static ProcessInterface getImplementedProcessInterface(
            Process process) {
        ImplementedInterface implementedInterface =
                (ImplementedInterface) Xpdl2ModelUtil.getOtherElement(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementedInterface());

        if (implementedInterface != null) {
            String interfaceId = implementedInterface.getProcessInterfaceId();
            String packageRef = implementedInterface.getPackageRef();
            if (packageRef == null) {
                Package xpdl2Package = process.getPackage();
                return getProcessInterface(xpdl2Package, interfaceId);
            }
        }
        return null;
    }

    /**
     * Retrieve a Process Interface given a Package reference and a Process
     * Interface Id.
     * 
     * @param pkg
     * @param processInterfaceId
     * @return
     */
    public static ProcessInterface getProcessInterface(Package pkg,
            String processInterfaceId) {
        ProcessInterfaces processInterfaces =
                (ProcessInterfaces) pkg
                        .getOtherElement(XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ProcessInterfaces().getName());

        if (processInterfaces != null) {
            return ((ProcessInterface) EMFSearchUtil
                    .findInList(processInterfaces.getProcessInterface(),
                            Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(),
                            processInterfaceId));
        }

        return null;
    }

    /**
     * Utility to return the StartMethod implemented by an Activity returns null
     * if none present
     * 
     * @param activity
     * @return
     */
    public static StartMethod getImplementedStartMethod(Activity activity) {
        StartMethod startMethod = null;
        Object obj =
                Xpdl2ModelUtil.getOtherAttribute(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_Implements());
        if (obj != null) {
            String implementedMethodId = (String) obj;
            Object impInt =
                    activity.getProcess()
                            .getOtherElement(XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ImplementedInterface()
                                    .getName());
            if (impInt != null) {
                if (getImplementedProcessInterface(activity.getProcess()) != null) {
                    startMethod =
                            (StartMethod) EMFSearchUtil
                                    .findInList(getImplementedProcessInterface(activity
                                            .getProcess()).getStartMethods(),
                                            Xpdl2Package.eINSTANCE
                                                    .getUniqueIdElement_Id(),
                                            implementedMethodId);
                }
            }
        }
        return startMethod;
    }

    /**
     * Utility to retrieve IntermediateMethod if IntermediateEvent implements
     * any returns null if none exists.
     * 
     * @param activity
     * @return
     */
    public static IntermediateMethod getImplementedIntermediateMethod(
            Activity activity) {
        IntermediateMethod intermediateMethod = null;
        Object obj =
                Xpdl2ModelUtil.getOtherAttribute(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_Implements());
        if (obj != null) {
            String implementedMethodId = (String) obj;
            Object impInt =
                    activity.getProcess()
                            .getOtherElement(XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ImplementedInterface()
                                    .getName());
            if (impInt != null) {
                if (getImplementedProcessInterface(activity.getProcess()) != null) {
                    intermediateMethod =
                            (IntermediateMethod) EMFSearchUtil
                                    .findInList(getImplementedProcessInterface(activity
                                            .getProcess())
                                            .getIntermediateMethods(),
                                            Xpdl2Package.eINSTANCE
                                                    .getUniqueIdElement_Id(),
                                            implementedMethodId);
                }
            }
        }
        return intermediateMethod;
    }

    /**
     * Get the associated parameters for given activity.
     * 
     * @param activity
     * 
     * @return Associated Parameters list or <b>null</b> if there are none.
     */
    public static EList getActivityAssociatedParameters(Activity activity) {
        Object obj =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AssociatedParameters());
        if (obj instanceof AssociatedParameters) {
            AssociatedParameters assocParams = (AssociatedParameters) obj;

            return assocParams.getAssociatedParameter();
        }

        return null;
    }

    /**
     * Get processes that implement the given process interface
     * <p>
     * <b>NOTE This works for implementing processes in the process interface's
     * package ONLY</b>
     * 
     * @param processInterface
     * @return
     */
    public static List<Process> getImplementingProcesses(
            ProcessInterface processInterface) {
        String processInterfaceId = processInterface.getId();

        List<Process> implementingProcesses = new ArrayList<Process>();

        Package processInterfacePkg =
                Xpdl2ModelUtil.getPackage(processInterface);
        if (processInterfacePkg != null) {
            for (Iterator iterator =
                    processInterfacePkg.getProcesses().iterator(); iterator
                    .hasNext();) {
                Process process = (Process) iterator.next();

                ProcessInterface implementedIfc =
                        getImplementedProcessInterface(process);
                if (implementedIfc != null
                        && processInterfaceId.equals(implementedIfc.getId())
                        && processInterfacePkg == Xpdl2ModelUtil
                                .getPackage(implementedIfc)) {

                    implementingProcesses.add(process);
                }
            }

        }

        return implementingProcesses;
    }

    /**
     * Get all the data in the scope of the given embedded sub-process (as
     * defined by it's activity set.
     * 
     * @param actSet
     * @return all the data (NOT including the process /package data).
     */
    public static List<ProcessRelevantData> getEmbeddedSubProcessSetScopeData(
            ActivitySet actSet) {
        List<ProcessRelevantData> data = new ArrayList<ProcessRelevantData>();

        addEmbeddedSubProcScopeData(actSet, data);

        return data;
    }

    /**
     * @param actSet
     * @param data
     */
    private static void addEmbeddedSubProcScopeData(ActivitySet actSet,
            List<ProcessRelevantData> data) {
        Process p = actSet.getProcess();

        Activity embSubProcAct =
                Xpdl2ModelUtil
                        .getEmbSubProcActivityForActSet(p, actSet.getId());
        if (embSubProcAct != null) {
            data.addAll(embSubProcAct.getDataFields());

            if (embSubProcAct.eContainer() instanceof ActivitySet) {
                addEmbeddedSubProcScopeData((ActivitySet) embSubProcAct.eContainer(),
                        data);
            }
        }
    }

    /**
     * Retrieves {@link ProcessInterface} for a given {@link Package}.
     * 
     * @param pkg
     * @return
     */
    public static ProcessInterfaces getProcessInterfaces(Package pkg) {
        ProcessInterfaces processInterfaces = null;
        if (pkg != null
                && pkg.getOtherElement(XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ProcessInterfaces().getName()) != null) {
            processInterfaces =
                    (ProcessInterfaces) pkg
                            .getOtherElement(XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ProcessInterfaces()
                                    .getName());
        }
        return processInterfaces;
    }

    /**
     * @param activity
     * @return <code>true</code> if implicit data association is disabled (not
     *         including correlation data association).
     */
    public static boolean isImplicitAssociationDisabled(Activity activity) {
        boolean isImplicitDisabled = false;

        if (activity != null) {
            /* Non implementing event. */
            Object assocParams =
                    Xpdl2ModelUtil.getOtherElement(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AssociatedParameters());
            if (assocParams instanceof AssociatedParameters) {
                isImplicitDisabled =
                        ((AssociatedParameters) assocParams)
                                .isDisableImplicitAssociation();

            }
        }
        return isImplicitDisabled;
    }

    /**
     * @param interfaceMethod
     * @return <code>true</code> if implicit data association is disabled (not
     *         including correlation data association).
     */
    public static boolean isImplicitAssociationDisabled(
            AssociatedParametersContainer interfaceMethod) {
        boolean isImplicitDisabled = false;

        if (interfaceMethod != null) {
            isImplicitDisabled = interfaceMethod.isDisableImplicitAssociation();
        }
        return isImplicitDisabled;
    }
}
