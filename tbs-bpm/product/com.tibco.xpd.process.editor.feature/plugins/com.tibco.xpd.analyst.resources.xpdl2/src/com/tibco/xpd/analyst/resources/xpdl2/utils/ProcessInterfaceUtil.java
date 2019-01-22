/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationField;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.ServiceProcessConfiguration;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.properties.general.ObjectReferencesItem;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyFactory;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.LocalPackageProcessInterfaceUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility class to provide bridge between {@link ProcessInterface} and
 * implementing {@link Process}.
 * 
 * @author rsomayaj
 */
public class ProcessInterfaceUtil {

    /**
     * Utility to resolve {@link ExternalReference} for a
     * {@link ProcessInterface}.
     * 
     * @param process
     *            {@link Process} which implements the external
     *            {@link ProcessInterface}
     * @param packageRef
     *            {@link Package} id of the {@link Package} that contains the
     *            {@link Process}
     * @param processInterfaceId
     *            {@link ProcessInterface} id that the {@link Process}
     *            implements
     * 
     * @return {@link ProcessInterface} even if it is in an external
     *         {@link Package}
     */
    public static ProcessInterface resolveExternalProcessInterface(
            Process process, String packageRef, String processInterfaceId) {

        if (packageRef == null) {
            return getProcessInterface(process.getPackage(), processInterfaceId);
        } else {
            Xpdl2WorkingCopyImpl wc =
                    (Xpdl2WorkingCopyImpl) WorkingCopyUtil
                            .getWorkingCopyFor(process);
            if (wc != null) {
                String externalPackageLocation =
                        wc.getExternalPackageLocation(packageRef);
                if (externalPackageLocation != null) {
                    List<IResource> resourceList =
                            ProcessUIUtil
                                    .getResourcesForLocation(WorkingCopyUtil
                                            .getProjectFor(process),
                                            externalPackageLocation,
                                            Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
                    if (resourceList != null) {
                        for (IResource rsrc : resourceList) {
                            IProject project = rsrc.getProject();
                            if (project != null) {
                                XpdProjectResourceFactory fact =
                                        XpdResourcesPlugin
                                                .getDefault()
                                                .getXpdProjectResourceFactory(project);
                                if (fact != null) {
                                    IResource resolveResourceReference =
                                            fact.resolveResourceReference(rsrc,
                                                    externalPackageLocation,
                                                    Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);

                                    if (resolveResourceReference != null) {
                                        WorkingCopy externalWC =
                                                fact.getWorkingCopy(resolveResourceReference);

                                        if (externalWC != null) {
                                            Package externalPkg =
                                                    (Package) externalWC
                                                            .getRootElement();
                                            ProcessInterface processInterface =
                                                    getProcessInterface(externalPkg,
                                                            processInterfaceId);
                                            if (processInterface != null) {
                                                return processInterface;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
        return null;
    }

    /**
     * Brings back all the processes that are implementing the interface passed
     * into this function.
     * 
     * @param processInterface
     *            the {@link ProcessInterface} whose references are required.
     * @return List of items in a tree view data structure called
     *         {@link ObjectReferencesItem}
     * @throws CoreException
     */
    public static List<ObjectReferencesItem> getProcessInterfaceObjectRefDependents(
            final ProcessInterface processInterface) throws CoreException {
        final List<ObjectReferencesItem> processArray =
                new ArrayList<ObjectReferencesItem>();
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IProject[] projects = root.getProjects();
        for (IProject project : projects) {
            if (project.isOpen()
                    && project.hasNature(XpdConsts.PROJECT_NATURE_ID)) {
                XpdResourcesPlugin resPlg = XpdResourcesPlugin.getDefault();
                ProjectConfig config = resPlg.getProjectConfig(project);
                List<SpecialFolder> sfolders =
                        config.getSpecialFolders()
                                .getFoldersOfKind(Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
                for (SpecialFolder sf : sfolders) {
                    if (sf.getFolder() != null) {
                        sf.getFolder().accept(new IResourceVisitor() {
                            @Override
                            public boolean visit(IResource resource)
                                    throws CoreException {
                                if (resource instanceof IFile
                                        && Xpdl2WorkingCopyFactory.XPDL_EXTENSION.equals(resource
                                                .getFileExtension())) {
                                    WorkingCopy wc =
                                            XpdResourcesPlugin.getDefault()
                                                    .getWorkingCopy(resource);
                                    if (wc instanceof Xpdl2WorkingCopyImpl
                                            && wc.getRootElement() instanceof Package) {
                                        Package externalPkg =
                                                (Package) wc.getRootElement();
                                        EList<Process> processes =
                                                externalPkg.getProcesses();
                                        for (Process process : processes) {
                                            ProcessInterface currentProcessInterface =
                                                    getImplementedProcessInterface(process);
                                            if (currentProcessInterface != null
                                                    && currentProcessInterface
                                                            .equals(processInterface)) {
                                                processArray
                                                        .add(ObjectReferencesItem
                                                                .create(process));
                                            }
                                        }
                                    }
                                }
                                return true;
                            }
                        });
                    }
                }
            }

        }

        return Collections.unmodifiableList(processArray);
    }

    /**
     * Returns the id of the implemented process interface if a {@link Process}
     * implements one, returns <code>null</code> otherwise.
     * 
     * @param process
     *            The process which implements the {@link ProcessInterface}
     * @return The id of the {@link ProcessInterface} being implemented.
     */
    public static String getImplementedProcessInterfaceId(Process process) {
        String interfaceId = null;
        ImplementedInterface implementedInterface =
                (ImplementedInterface) Xpdl2ModelUtil.getOtherElement(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementedInterface());

        if (implementedInterface != null) {
            interfaceId = implementedInterface.getProcessInterfaceId();
        }
        return interfaceId;
    }

    /**
     * Returns a {@link ProcessInterface} if a {@link Process} implements one,
     * returns <code>null</code> otherwise.
     * 
     * @param process
     *            The {@link Process} which implements the
     *            {@link ProcessInterface}
     * @return The {@link ProcessInterface} being implemented
     */
    public static ProcessInterface getImplementedProcessInterface(
            Process process) {

        ImplementedInterface implementedInterface =
                (ImplementedInterface) Xpdl2ModelUtil.getOtherElement(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementedInterface());

        if (implementedInterface != null) {

            String interfaceId = implementedInterface.getProcessInterfaceId();
            String packageRef = implementedInterface.getPackageRef();
            if (packageRef != null) {

                return resolveExternalProcessInterface(process,
                        packageRef,
                        interfaceId);
            }

            Package xpdl2Package = process.getPackage();
            return getProcessInterface(xpdl2Package, interfaceId);
        }
        return null;
    }

    /**
     * Returns whether a process is implementing a process interface in a
     * package that is no longer referenced. returns <code>null</code>
     * otherwise.
     * 
     * @param process
     *            The {@link Process} which implements the
     *            {@link ProcessInterface}
     * @return <code>TRUE</code> if the implemented {@link ProcessInterface} is
     *         not resolvable.
     */
    public static Boolean isUnresolvedImplementedProcessInterface(
            Process process) {
        ImplementedInterface implementedInterface =
                (ImplementedInterface) Xpdl2ModelUtil.getOtherElement(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementedInterface());

        if (implementedInterface != null) {
            String interfaceId = implementedInterface.getProcessInterfaceId();
            String packageRef = implementedInterface.getPackageRef();
            if (packageRef != null) {
                ProcessInterface processInterface =
                        resolveExternalProcessInterface(process,
                                packageRef,
                                interfaceId);
                if (processInterface == null) {
                    return Boolean.TRUE;
                }
            } else {
                Package xpdl2Package = process.getPackage();
                if (getProcessInterface(xpdl2Package, interfaceId) == null) {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    /**
     * Utility to retrieve a list of {@link StartEvent} and for a given
     * {@link Process}
     * 
     * @param process
     * @return
     */
    public static List<Activity> getStartEventList(Process process) {
        List<Activity> startEventList = new ArrayList<Activity>();

        // get start events from the process

        List<Activity> activityList = process.getActivities();
        for (Activity activity : activityList) {
            Event activityEvent = activity.getEvent();
            if (activityEvent instanceof com.tibco.xpd.xpdl2.StartEvent)
                startEventList.add(activity);
        }

        return Collections.unmodifiableList(startEventList);
    }

    /**
     * Utility to retrieve a list of {@link IntermediateEvent} for a given
     * {@link Process}
     * 
     * @param process
     * @return
     */
    public static List<EObject> getIntermediateEventList(Process process) {
        List<EObject> intermediateEventList = new ArrayList<EObject>();

        // get start events from the process
        List<Activity> activityList = process.getActivities();
        for (Activity activity : activityList) {
            Event activityEvent = activity.getEvent();
            if (activityEvent instanceof IntermediateMethod) {
                intermediateEventList.add(activityEvent);
            }
        }

        if (getImplementedProcessInterface(process) != null) {
            intermediateEventList
                    .addAll(getImplementedProcessInterface(process)
                            .getIntermediateMethods());
        }

        return Collections.unmodifiableList(intermediateEventList);
    }

    /**
     * Retrieve all the {@link FormalParameter} for a given {@link Process},
     * includes those of the {@link Process} and those of the implementing
     * {@link ProcessInterface}.
     * 
     * @param process
     *            {@link Process} whose {@link FormalParameter}s are requested
     *            for.
     * @return those of the {@link Process} and the {@link ProcessInterface} if
     *         it implements one
     */
    public static List<FormalParameter> getAllFormalParameters(Process process) {
        List<FormalParameter> allFormalParameters =
                new ArrayList<FormalParameter>();
        allFormalParameters.addAll(process.getFormalParameters());

        if (getImplementedProcessInterface(process) != null)
            allFormalParameters.addAll(getImplementedProcessInterface(process)
                    .getFormalParameters());

        return Collections.unmodifiableList(allFormalParameters);
    }

    /**
     * Retrieve all the mandatory {@link FormalParameter} for a given
     * {@link Process}, includes those of the {@link Process} and those of the
     * implementing {@link ProcessInterface}.
     * 
     * @param process
     *            {@link Process} whose mandatory {@link FormalParameter}s are
     *            requested for.
     * @return those of the {@link Process} and the {@link ProcessInterface} if
     *         it implements one
     */
    public static List<FormalParameter> getAllMandatoryFormalParameters(
            Process process) {
        List<FormalParameter> allFormalParameters =
                getAllFormalParameters(process);
        List<FormalParameter> mandatoryFormalParameters =
                new ArrayList<FormalParameter>();
        if (allFormalParameters != null) {
            for (FormalParameter parameter : allFormalParameters) {

                if (isFormalParamMandatory(parameter)) {
                    mandatoryFormalParameters.add(parameter);
                }
            }
        }
        return Collections.unmodifiableList(mandatoryFormalParameters);
    }

    /**
     * Checks whether a given {@link FormalParameter} is mandatory or not.
     * 
     * @param Formal
     *            Parameter
     * @return {@link Boolean}.<code>true</code> if it is mandatory
     */
    public static Boolean isFormalParamMandatory(FormalParameter parameter) {
        return parameter.isRequired();
    }

    /**
     * Retrieve the {@link AssociatedParameter} of the implemented method from
     * the {@link ProcessInterface}, for an {@link Activity} of a
     * {@link Process}.
     * 
     * @param activity
     *            activity(could be either a start message event, intermediate
     *            message event or a receive task) that implements an event in
     *            the process interface
     * @return List of Associated Parameters of the event in the Process
     *         Interface
     */
    public static List<AssociatedParameter> getImplementedMethodAssociatedParameters(
            Activity activity) {
        Object otherElement =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AssociatedParameters());
        if (otherElement != null
                && otherElement instanceof AssociatedParameters) {
            return Collections
                    .unmodifiableList(((AssociatedParameters) otherElement)
                            .getAssociatedParameter());
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * Utility to retrieve the Start event in the process interface implemented
     * by an {@link Activity}
     * 
     * @param activity
     *            which implements a start event in the process interface
     * @return returns <code>null</code> if none present
     */
    public static StartMethod getImplementedStartMethod(Activity activity) {
        StartMethod startMethod = null;
        Object obj =
                Xpdl2ModelUtil.getOtherAttribute(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_Implements());
        if (obj != null && activity.getProcess() != null) {
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
     * Utility to retrieve {@link IntermediateMethod} if
     * {@link IntermediateEvent} implements any, returns <code>null</code> if
     * none exists.
     * 
     * @param activity
     *            which implements an intermediate event in the process
     *            interface
     * @return <code>null</code> if none present
     */
    public static IntermediateMethod getImplementedIntermediateMethod(
            Activity activity) {
        IntermediateMethod intermediateMethod = null;
        Object obj =
                Xpdl2ModelUtil.getOtherAttribute(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_Implements());
        if (obj != null && activity.getProcess() != null) {
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
     * Returns the implemented interface method for a given activity.
     * 
     * @param act
     * @return
     * 
     */
    public static InterfaceMethod getImplementedMethod(Activity act) {
        InterfaceMethod method = getImplementedStartMethod(act);
        if (method == null) {
            method = getImplementedIntermediateMethod(act);
        }
        return method;
    }

    /**
     * Retrieves {@link ProcessInterfaces} for a given {@link Package}. This is
     * the container for all the process interface objects within the package.
     * 
     * @param XPDL
     *            package
     * @return {@link ProcessInterfaces}
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
     * Retrieve a {@link ProcessInterface} given a {@link Package} reference and
     * a {@link ProcessInterface} Id.
     * 
     * @param XPDL
     *            package
     * @param processinterfaceId
     * @return {@link ProcessInterface}
     */
    public static ProcessInterface getProcessInterface(Package pkg,
            String processInterfaceId) {

        if (getProcessInterfaces(pkg) != null) {

            return ((ProcessInterface) EMFSearchUtil
                    .findInList(getProcessInterfaces(pkg).getProcessInterface(),
                            Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(),
                            processInterfaceId));
        }

        return null;

    }

    /**
     * Return <code>true</code> if the specified process is a Service Process
     * which'd run on the Pageflow Engine, <code>false</code> otherwise. If the
     * given service process implements Service Process Interface then checks
     * the deployment target configured on the interface.
     * 
     * @param serviceProcess
     *            Process to be checked.
     * @return <code>true</code> if the specified process is a Service Process
     *         or implements a Service Process Interface which'd run on the
     *         Pageflow Engine, <code>false</code> otherwise.
     */
    public static boolean isPageflowEngineServiceProcess(Process serviceProcess) {

        ProcessInterface processInterface =
                getImplementedProcessInterface(serviceProcess);
        if (null != processInterface) {

            return isPageflowEngineServiceProcessInterface(processInterface);
        }

        if (Xpdl2ModelUtil.isServiceProcess(serviceProcess)) {

            ServiceProcessConfiguration serviceProcessConfig =
                    (ServiceProcessConfiguration) Xpdl2ModelUtil
                            .getOtherElement(serviceProcess,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ServiceProcessConfiguration());
            if (null != serviceProcessConfig) {

                boolean isDeployToPageflowRuntime =
                        serviceProcessConfig.isDeployToPageflowRuntime();
                return isDeployToPageflowRuntime;
            }
        }
        return false;
    }

    /**
     * Return <code>true</code> if the specified process is a Service Process
     * which'd run on Process Engine, <code>false</code> otherwise. If the given
     * service process implements Service Process Interface then checks the
     * deployment target configured on the interface.
     * 
     * @param serviceProcess
     *            Process to be checked.
     * @return <code>true</code> if the specified process is a Service Process
     *         or implements a Service Process Interface which'd run on Process
     *         Engine, <code>false</code> otherwise.
     */
    public static boolean isProcessEngineServiceProcess(Process serviceProcess) {

        ProcessInterface processInterface =
                getImplementedProcessInterface(serviceProcess);
        if (null != processInterface) {

            return isProcessEngineServiceProcessInterface(processInterface);
        }

        if (Xpdl2ModelUtil.isServiceProcess(serviceProcess)) {

            ServiceProcessConfiguration serviceProcessConfig =
                    (ServiceProcessConfiguration) Xpdl2ModelUtil
                            .getOtherElement(serviceProcess,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ServiceProcessConfiguration());
            if (null != serviceProcessConfig) {

                boolean isDeployToProcessRuntime =
                        serviceProcessConfig.isDeployToProcessRuntime();
                return isDeployToProcessRuntime;
            }
        }
        return false;
    }

    /**
     * Return <code>true</code> if the specified {@link EObject} is a Service
     * Process or a Service Process Interface which'd run on Process Engine,
     * <code>false</code> otherwise.
     * 
     * @param procOrIntfc
     *            - {@link Process} or {@link ProcessInterface}
     * @return <code>true</code> if the specified {@link EObject} is a Service
     *         Process or a Service Process Interface which'd run on Process
     *         Engine <code>false</code> otherwise.
     */
    public static boolean isProcessEngineServiceProcOrServiceProcIntfc(
            EObject procOrIntfc) {

        if (procOrIntfc instanceof Process) {

            return isProcessEngineServiceProcess((Process) procOrIntfc);
        } else if (procOrIntfc instanceof ProcessInterface) {

            return isProcessEngineServiceProcessInterface((ProcessInterface) procOrIntfc);
        }

        return false;
    }

    /**
     * Return <code>true</code> if the specified {@link EObject} is a Service
     * Process or a Service Process Interface which'd run on Pageflow Engine,
     * <code>false</code> otherwise.
     * 
     * @param procOrIntfc
     *            - {@link Process} or {@link ProcessInterface}
     * @return <code>true</code> if the specified {@link EObject} is a Service
     *         Process or a Service Process Interface which'd run on Pageflow
     *         Engine <code>false</code> otherwise.
     */
    public static boolean isPageflowEngineServiceProcOrServiceProcIntfc(
            EObject procOrIntfc) {

        if (procOrIntfc instanceof Process) {

            return isPageflowEngineServiceProcess((Process) procOrIntfc);
        } else if (procOrIntfc instanceof ProcessInterface) {

            return isPageflowEngineServiceProcessInterface((ProcessInterface) procOrIntfc);
        }

        return false;
    }

    /**
     * Checks if the given service process interface has Process Engine as
     * deployment target run-time
     * 
     * @param serviceProcessInterface
     * @return <code>true</code> if the given process interface has Process
     *         Engine as deployment target
     */
    public static boolean isProcessEngineServiceProcessInterface(
            ProcessInterface serviceProcessInterface) {

        if (Xpdl2ModelUtil.isServiceProcessInterface(serviceProcessInterface)) {

            ServiceProcessConfiguration serviceProcessConfiguration =
                    serviceProcessInterface.getServiceProcessConfiguration();
            if (null != serviceProcessConfiguration) {

                boolean deployToProcessRuntime =
                        serviceProcessConfiguration.isDeployToProcessRuntime();
                return deployToProcessRuntime;
            }
        }
        return false;
    }

    /**
     * Checks if the given service process interface has Pageflow Engine as
     * deployment target run-time
     * 
     * @param serviceProcessInterface
     * @return <code>true</code> if the given process interface has Pageflow
     *         Engine as deployment target
     */
    public static boolean isPageflowEngineServiceProcessInterface(
            ProcessInterface serviceProcessInterface) {

        if (Xpdl2ModelUtil.isServiceProcessInterface(serviceProcessInterface)) {

            ServiceProcessConfiguration serviceProcessConfiguration =
                    serviceProcessInterface.getServiceProcessConfiguration();
            if (null != serviceProcessConfiguration) {

                boolean deployToPageflowRuntime =
                        serviceProcessConfiguration.isDeployToPageflowRuntime();
                return deployToPageflowRuntime;
            }
        }
        return false;
    }

    /**
     * Utility to check whether an {@link Activity} implements any method in an
     * implemented {@link ProcessInterface}.
     * 
     * @param activity
     * @return
     */
    public static Boolean isEventImplemented(Activity activity) {
        return Xpdl2ModelUtil.isEventImplemented(activity);
    }

    /**
     * Return a {@link Package} reference for a provided
     * {@link ProcessInterface}.
     * 
     * @param processInterface
     * @return
     */
    public static Package getProcessInterfacePackage(
            ProcessInterface processInterface) {
        if (processInterface != null && processInterface.eContainer() != null
                && processInterface.eContainer().eContainer() != null)
            return (Package) processInterface.eContainer().eContainer();
        return null;
    }

    /**
     * Utility to get the Formal Parameters associated with an activity.
     * 
     * @param activity
     * @return unmodifiable list of {@link FormalParameter} for an
     *         {@link Activity} corresponding to the {@link AssociatedParameter}
     *         , if there are no {@link AssociatedParameter} for an
     *         {@link Activity}, the method returns the entire list of
     *         {@link FormalParameter} <b>unless the
     *         xpdExt:DisableImplicitAssociation is set <code>true</code> in
     *         which case an empty list is returned.</b>
     * 
     * @deprecated Use
     *             {@link ActivityInterfaceDataUtil#getActivityInterfaceData(Activity)}
     *             instead - which consolidates explicitly/implicitly associated
     *             data into a single class.
     */
    @Deprecated
    public static List<FormalParameter> getAssociatedFormalParameters(
            Activity activity) {
        List<FormalParameter> formalParameters =
                new ArrayList<FormalParameter>();

        // Local to the process.
        EObject obj =
                activity.getOtherElement(XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_AssociatedParameters().getName());
        List<FormalParameter> processFormalParameters =
                activity.getProcess().getFormalParameters();

        AssociatedParameters associatedParams = null;

        if (obj instanceof AssociatedParameters
                && !((AssociatedParameters) obj).getAssociatedParameter()
                        .isEmpty()) {
            associatedParams = (AssociatedParameters) obj;
            List<AssociatedParameter> associatedParamList =
                    associatedParams.getAssociatedParameter();
            formalParameters
                    .addAll(getAssociatedFormalParameters(associatedParamList,
                            processFormalParameters));
        } else {
            // Do NOT treat as "no-assoc params = all params" IF this activity
            // implements an interface event.
            if (!isEventImplemented(activity)) {
                /*
                 * Sid XPD-2087: Only return all data implicitly if implicit
                 * association has not been disabled.
                 */
                if (!isImplicitAssociationDisabled(activity)) {
                    formalParameters.addAll(processFormalParameters);
                }
            }
        }

        // From the Process Interface
        ProcessInterface processInterface =
                getImplementedProcessInterface(activity.getProcess());
        if (processInterface != null) {
            List<FormalParameter> processInterfaceFormalParameters =
                    processInterface.getFormalParameters();

            if (!isEventImplemented(activity)) {
                // For non-implemented events add any associated process
                // itneface params (or all if there are no associations at all.
                if (associatedParams != null
                        && !associatedParams.getAssociatedParameter().isEmpty()) {
                    formalParameters
                            .addAll(getAssociatedFormalParameters(associatedParams
                                    .getAssociatedParameter(),
                                    processInterfaceFormalParameters));
                } else {
                    /*
                     * Sid XPD-2087: Only return all data implicitly if implicit
                     * association has not been disabled.
                     */
                    if (!isImplicitAssociationDisabled(activity)) {
                        formalParameters
                                .addAll(processInterfaceFormalParameters);
                    }
                }

            } else {
                if (getImplementedStartMethod(activity) != null) {
                    StartMethod startMethod =
                            getImplementedStartMethod(activity);
                    formalParameters
                            .addAll(getStartMethodAssociatedFormalParameters(startMethod));
                } else if (getImplementedIntermediateMethod(activity) != null) {
                    IntermediateMethod intermediateMethod =
                            getImplementedIntermediateMethod(activity);
                    formalParameters
                            .addAll(getIntermediateMethodAssociatedFormalParameters(intermediateMethod));
                } else if (getImplementedErrorMethod(activity) != null) {
                    ErrorMethod errorMethod =
                            getImplementedErrorMethod(activity);
                    formalParameters
                            .addAll(getErrorMethodAssociatedFormalParameters(errorMethod));

                }
            }
        }
        return Collections.unmodifiableList(formalParameters);
    }

    /**
     * Utility to get all mandatory formal parameters associated with an
     * activity.
     * 
     * @param activity
     * @return A list of {@link FormalParameter} for an {@link Activity}
     *         corresponding to the {@link AssociatedParameter}, if there are no
     *         {@link AssociatedParameter} for an {@link Activity}, the method
     *         returns the entire list of {@link FormalParameter}.
     */
    public static List<FormalParameter> getMandatoryAssociatedFormalParameters(
            Activity activity) {
        List<FormalParameter> formalParameters =
                new ArrayList<FormalParameter>();

        // Local to the process.
        EObject obj = null;
        List<FormalParameter> processFormalParameters =
                new ArrayList<FormalParameter>();

        List<FormalParameter> mandatoryFormalParameters =
                new ArrayList<FormalParameter>();

        if (activity != null) {
            obj =
                    activity.getOtherElement(XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_AssociatedParameters().getName());
            processFormalParameters =
                    activity.getProcess().getFormalParameters();
            mandatoryFormalParameters = new ArrayList<FormalParameter>();
            for (FormalParameter parameter : processFormalParameters) {
                boolean mandatory = isFormalParamMandatory(parameter);
                if (mandatory) {
                    mandatoryFormalParameters.add(parameter);
                }
            }
        }

        if (obj != null && obj instanceof AssociatedParameters) {
            AssociatedParameters associatedParams = (AssociatedParameters) obj;
            List<AssociatedParameter> associatedParamList =
                    associatedParams.getAssociatedParameter();
            if (associatedParamList.isEmpty()) {
                // Do NOT treat as "no-assoc params = all params" IF this
                // activity implements an interface event.
                if (!isEventImplemented(activity)) {
                    /*
                     * Sid XPD-2087: Only return all data implicitly if implicit
                     * association has not been disabled.
                     */
                    if (!isImplicitAssociationDisabled(activity)) {
                        formalParameters.addAll(mandatoryFormalParameters);
                    }
                }

            } else {
                List<AssociatedParameter> mandatory =
                        new ArrayList<AssociatedParameter>();
                for (AssociatedParameter parameter : associatedParamList) {
                    if (parameter.isMandatory()) {
                        mandatory.add(parameter);
                    }
                }
                formalParameters
                        .addAll(getAssociatedFormalParameters(mandatory,
                                processFormalParameters));
            }
        } else {
            // Do NOT treat as "no-assoc params = all params" IF this
            // activity implements an interface event.
            if (!isEventImplemented(activity)) {
                formalParameters.addAll(mandatoryFormalParameters);
            }
        }

        if (activity != null) {
            // From the Process Interface
            ProcessInterface processInterface =
                    getImplementedProcessInterface(activity.getProcess());
            if (processInterface != null) {
                List<FormalParameter> processInterfaceFormalParameters =
                        processInterface.getFormalParameters();
                if (getImplementedStartMethod(activity) != null) {
                    StartMethod startMethod =
                            getImplementedStartMethod(activity);
                    formalParameters
                            .addAll(getStartMethodMandatoryAssociatedFormalParameters(startMethod));
                } else if (getImplementedIntermediateMethod(activity) != null) {
                    IntermediateMethod intermediateMethod =
                            getImplementedIntermediateMethod(activity);
                    formalParameters
                            .addAll(getIntermediateMethodAssociatedFormalParameters(intermediateMethod));
                } else {
                    formalParameters.addAll(processInterfaceFormalParameters);
                }
            }
        }
        return Collections.unmodifiableList(formalParameters);
    }

    /**
     * Utility to retrieve Formal parameters from a list of associated
     * parameters given a set of Formal parameters.
     * 
     * @param associatedParameters
     * @param formalParameters
     * @return a list of {@link FormalParameter} relevant to the
     *         {@link AssociatedParameter} in a given list of
     *         {@link FormalParameter}s
     * 
     * @deprecated Use
     *             {@link ActivityInterfaceDataUtil#getActivityInterfaceData(Activity)}
     *             instead - which consolidates explicitly/implicitly associated
     *             data into a single class.
     */
    @Deprecated
    public static List<FormalParameter> getAssociatedFormalParameters(
            List<AssociatedParameter> associatedParameters,
            List<FormalParameter> formalParameters) {
        return getAssociatedFormalParameters(associatedParameters,
                formalParameters,
                false);
    }

    /**
     * Utility to retrieve Formal parameters from a list of associated
     * parameters given a set of Formal parameters.
     * 
     * @param associatedParameters
     * @param formalParameters
     * @return a list of {@link FormalParameter} relevant to the
     *         {@link AssociatedParameter} in a given list of
     *         {@link FormalParameter}s
     * @deprecated use
     *             {@link ActivityInterfaceDataUtil#getActivityInterfaceData(Activity)}
     *             instead.
     */
    @Deprecated
    public static List<FormalParameter> getAssociatedFormalParameters(
            List<AssociatedParameter> associatedParameters,
            List<FormalParameter> formalParameters, boolean mandatoryOnly) {

        List<FormalParameter> associatedFormalParameters =
                new ArrayList<FormalParameter>();

        for (AssociatedParameter associatedParameter : associatedParameters) {
            EObject eObj =
                    EMFSearchUtil.findInList(formalParameters,
                            Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                            associatedParameter.getFormalParam());
            if (eObj != null && eObj instanceof FormalParameter) {
                if (!mandatoryOnly || associatedParameter.isMandatory()) {
                    associatedFormalParameters.add((FormalParameter) eObj);
                }
            }
        }
        return Collections.unmodifiableList(associatedFormalParameters);
    }

    /**
     * Retrieve the list of {@link FormalParameter}s associated with a Start
     * Method/Event of a Process interface
     * 
     * @param startMethod
     * @return a list of {@link FormalParameter} for a given {@link StartMethod}
     *         . It resolves the {@link AssociatedParameter}s that it contains
     *         to {@link FormalParameter}s.
     * 
     *         If the {@link StartMethod} does not contain any
     *         {@link AssociatedParameter}, the method returns the entire list
     *         of {@link FormalParameter}s for the {@link ProcessInterface} that
     *         contains the {@link StartMethod} <b>unless the
     *         xpdExt:DisableImplicitAssociation is set <code>true</code> in
     *         which case an empty list is returned.</b>
     * @deprecated use
     *             {@link ActivityInterfaceDataUtil#getInterfaceEventInterfaceData(AssociatedParametersContainer)}
     *             instead.
     */
    @Deprecated
    public static List<FormalParameter> getStartMethodAssociatedFormalParameters(
            StartMethod startMethod) {
        if (startMethod != null && startMethod.eContainer() != null
                && startMethod.eContainer() instanceof ProcessInterface) {
            ProcessInterface processInterface =
                    (ProcessInterface) startMethod.eContainer();
            List<FormalParameter> formalParamList =
                    getAssociatedFormalParameters(startMethod.getAssociatedParameters(),
                            processInterface.getFormalParameters());
            if (formalParamList.isEmpty()) {
                /*
                 * Sid XPD-2087: Only return all data implicitly if implicit
                 * association has not been disabled.
                 */
                if (!isImplicitAssociationDisabled(startMethod)) {
                    return Collections.unmodifiableList(processInterface
                            .getFormalParameters());
                }
            } else {
                return Collections.unmodifiableList(formalParamList);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Returns the list of formal parameters for a given interface method.
     * 
     * @param ifcMethod
     * @return the set of {@link FormalParameter}s associated, and if there
     *         aren't any then returns all the {@link FormalParameter}s in the
     *         contained {@link ProcessInterface} <b>unless the
     *         xpdExt:DisableImplicitAssociation is set <code>true</code> in
     *         which case an empty list is returned.</b>
     * @deprecated Use
     *             {@link ActivityInterfaceDataUtil#getInterfaceEventInterfaceData(AssociatedParametersContainer)}
     *             instead - which consolidates explicitly/implicitly associated
     *             data into a single class.
     */
    @Deprecated
    public static List<FormalParameter> getInterfaceMethodAssociatedFormalParameters(
            InterfaceMethod ifcMethod) {
        if (ifcMethod instanceof StartMethod) {
            return Collections
                    .unmodifiableList(getStartMethodAssociatedFormalParameters((StartMethod) ifcMethod));
        } else if (ifcMethod instanceof IntermediateMethod) {
            return Collections
                    .unmodifiableList(getIntermediateMethodAssociatedFormalParameters((IntermediateMethod) ifcMethod));
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * Checks whether a given start method(of a process interface) has any
     * associated parameters at all.
     * 
     * @param startMethod
     * @return
     */
    public static Boolean hasStartMethodAssociatedFormalParameters(
            StartMethod startMethod) {
        boolean hasParams = Boolean.FALSE;
        if (startMethod != null && startMethod.eContainer() != null
                && startMethod.eContainer() instanceof ProcessInterface) {
            ProcessInterface processInterface =
                    (ProcessInterface) startMethod.eContainer();
            List<FormalParameter> formalParamList =
                    getAssociatedFormalParameters(startMethod.getAssociatedParameters(),
                            processInterface.getFormalParameters());
            if (!formalParamList.isEmpty()) {
                hasParams = Boolean.TRUE;
            }
        }
        return hasParams;
    }

    /**
     * Utility to get all the mandatory formal parameters associated with a
     * start method
     * 
     * @param startMethod
     * @return a list of {@link FormalParameter} for a given {@link StartMethod}
     *         . It resolves the {@link AssociatedParameter}s that it contains
     *         to {@link FormalParameter}s.
     * 
     *         If the {@link StartMethod} does not contain any
     *         {@link AssociatedParameter}, the method returns the entire list
     *         of {@link FormalParameter}s for the {@link ProcessInterface} that
     *         contains the {@link StartMethod} <b>unless the
     *         xpdExt:DisableImplicitAssociation is set <code>true</code> in
     *         which case an empty list is returned.</b>
     */
    public static List<FormalParameter> getStartMethodMandatoryAssociatedFormalParameters(
            StartMethod startMethod) {
        if (startMethod != null && startMethod.eContainer() != null
                && startMethod.eContainer() instanceof ProcessInterface) {
            ProcessInterface processInterface =
                    (ProcessInterface) startMethod.eContainer();
            List<AssociatedParameter> associated =
                    startMethod.getAssociatedParameters();
            List<AssociatedParameter> mandatory =
                    new ArrayList<AssociatedParameter>();
            for (AssociatedParameter parameter : associated) {
                if (parameter.isMandatory()) {
                    mandatory.add(parameter);
                }
            }
            List<FormalParameter> assocFormalParamList =
                    getAssociatedFormalParameters(associated,
                            processInterface.getFormalParameters());
            if (assocFormalParamList.isEmpty()) {
                /*
                 * Sid XPD-2087: Only return all data implicitly if implicit
                 * association has not been disabled.
                 */
                if (!isImplicitAssociationDisabled(startMethod)) {
                    List<FormalParameter> mandatoryParams =
                            new ArrayList<FormalParameter>();
                    for (Object next : processInterface.getFormalParameters()) {
                        if (next instanceof FormalParameter) {
                            FormalParameter param = (FormalParameter) next;

                            if (isFormalParamMandatory(param)) {
                                mandatoryParams.add(param);
                            }
                        }
                    }
                    return Collections.unmodifiableList(mandatoryParams);
                }
            } else {
                List<FormalParameter> mandatoryParamList =
                        getAssociatedFormalParameters(mandatory,
                                processInterface.getFormalParameters());
                return Collections.unmodifiableList(mandatoryParamList);
            }
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * Utility to get all the mandatory formal parameters associated with a
     * intermediate method
     * 
     * @param intermediate
     * @return a list of {@link FormalParameter} for a given
     *         {@link IntermediateMethod} . It resolves the
     *         {@link AssociatedParameter}s that it contains to
     *         {@link FormalParameter}s.
     * 
     *         If the {@link IntermediateMethod} does not contain any
     *         {@link AssociatedParameter}, the method returns the entire list
     *         of {@link FormalParameter}s for the {@link ProcessInterface} that
     *         contains the {@link IntermediateMethod} <b>unless the
     *         xpdExt:DisableImplicitAssociation is set <code>true</code> in
     *         which case an empty list is returned.</b>
     */
    public static List<FormalParameter> getIntermediateMethodMandatoryAssociatedFormalParameters(
            IntermediateMethod intermediateMethod) {
        if (intermediateMethod != null
                && intermediateMethod.eContainer() != null
                && intermediateMethod.eContainer() instanceof ProcessInterface) {
            ProcessInterface processInterface =
                    (ProcessInterface) intermediateMethod.eContainer();
            List<AssociatedParameter> associated =
                    intermediateMethod.getAssociatedParameters();
            List<AssociatedParameter> mandatory =
                    new ArrayList<AssociatedParameter>();
            for (AssociatedParameter parameter : associated) {
                if (parameter.isMandatory()) {
                    mandatory.add(parameter);
                }
            }
            List<FormalParameter> assocFormalParamList =
                    getAssociatedFormalParameters(associated,
                            processInterface.getFormalParameters());
            if (assocFormalParamList.isEmpty()) {
                /*
                 * Sid XPD-2087: Only return all data implicitly if implicit
                 * association has not been disabled.
                 */
                if (!isImplicitAssociationDisabled(intermediateMethod)) {
                    List<FormalParameter> mandatoryParams =
                            new ArrayList<FormalParameter>();
                    for (Object next : processInterface.getFormalParameters()) {
                        if (next instanceof FormalParameter) {
                            FormalParameter param = (FormalParameter) next;

                            if (isFormalParamMandatory(param)) {
                                mandatoryParams.add(param);
                            }
                        }
                    }
                    return Collections.unmodifiableList(mandatoryParams);
                }
            } else {
                List<FormalParameter> mandatoryParamList =
                        getAssociatedFormalParameters(mandatory,
                                processInterface.getFormalParameters());
                return Collections.unmodifiableList(mandatoryParamList);
            }
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * Utility to retrieve list of formal parameters for a given intermediate
     * method(of a process interface).
     * 
     * @param intermediateMethod
     * @return A list of {@link FormalParameter}s for a given
     *         {@link IntermediateMethod}. It resolves the
     *         {@link AssociatedParameter}s that it contains to
     *         {@link FormalParameter}s.
     * 
     *         If the {@link IntermediateMethod} does not contain any
     *         {@link AssociatedParameter}, the method returns the entire list
     *         of {@link FormalParameter}s for the {@link ProcessInterface} that
     *         contains the {@link IntermediateMethod} <b>unless the
     *         xpdExt:DisableImplicitAssociation is set <code>true</code> in
     *         which case an empty list is returned.</b>
     */
    public static List<FormalParameter> getIntermediateMethodAssociatedFormalParameters(
            IntermediateMethod intermediateMethod) {
        if (intermediateMethod != null
                && intermediateMethod.eContainer() != null
                && intermediateMethod.eContainer() instanceof ProcessInterface) {
            ProcessInterface processInterface =
                    (ProcessInterface) intermediateMethod.eContainer();
            List<FormalParameter> formalParamList =
                    getAssociatedFormalParameters(intermediateMethod.getAssociatedParameters(),
                            processInterface.getFormalParameters());
            if (formalParamList.isEmpty()) {
                /*
                 * Sid XPD-2087: Only return all data implicitly if implicit
                 * association has not been disabled.
                 */
                if (!isImplicitAssociationDisabled(intermediateMethod)) {
                    return Collections.unmodifiableList(processInterface
                            .getFormalParameters());
                }
            } else {
                return Collections.unmodifiableList(formalParamList);
            }
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * Utility to retrieve list of formal parameters for a given error method(of
     * a process interface).
     * 
     * @param errorMethod
     * @return a list of {@link FormalParameter}s for a given
     *         {@link ErrorMethod}. It resolves the {@link AssociatedParameter}s
     *         that it contains to {@link FormalParameter}s.
     * 
     *         If the {@link ErrorMethod} does not contain any
     *         {@link AssociatedParameter}, the method returns the entire list
     *         of {@link FormalParameter}s for the {@link ProcessInterface} that
     *         contains the {@link ErrorMethod} <b>unless the
     *         xpdExt:DisableImplicitAssociation is set <code>true</code> in
     *         which case an empty list is returned.</b>
     */
    public static List<FormalParameter> getErrorMethodAssociatedFormalParameters(
            ErrorMethod errorMethod) {
        return getErrorMethodAssociatedFormalParameters(errorMethod, false);
    }

    /**
     * Utility to retrieve list of formal parameters for a given error method(of
     * a process interface).
     * 
     * @param errorMethod
     * @param mandatoryOnly
     * 
     * @return a list of {@link FormalParameter}s for a given
     *         {@link ErrorMethod}. It resolves the {@link AssociatedParameter}s
     *         that it contains to {@link FormalParameter}s.
     * 
     *         If the {@link ErrorMethod} does not contain any
     *         {@link AssociatedParameter}, the method returns the entire list
     *         of {@link FormalParameter}s for the {@link ProcessInterface} that
     *         contains the {@link ErrorMethod} <b>unless the
     *         xpdExt:DisableImplicitAssociation is set <code>true</code> in
     *         which case an empty list is returned.</b>
     */
    public static List<FormalParameter> getErrorMethodAssociatedFormalParameters(
            ErrorMethod errorMethod, boolean mandatoryOnly) {
        ProcessInterface processInterface =
                ProcessInterfaceUtil.getProcessInterface(errorMethod);
        if (processInterface != null) {
            List<FormalParameter> formalParamList =
                    getAssociatedFormalParameters(errorMethod.getAssociatedParameters(),
                            processInterface.getFormalParameters(),
                            mandatoryOnly);

            if (!formalParamList.isEmpty()) {
                return formalParamList;
            }

            /*
             * Sid XPD-2087: Only return all data implicitly if implicit
             * association has not been disabled.
             */
            if (!isImplicitAssociationDisabled(errorMethod)) {

                List<FormalParameter> allParams =
                        new ArrayList<FormalParameter>();
                for (FormalParameter param : processInterface
                        .getFormalParameters()) {
                    if (!mandatoryOnly || param.isRequired()) {
                        allParams.add(param);
                    }
                }

                return Collections.unmodifiableList(allParams);
            }

        }
        return Collections.EMPTY_LIST;
    }

    /**
     * Checks whether a given intermediate method(of a process interface) has
     * any associated parameters at all.
     * 
     * @param intermediateMethod
     * @return <code>true</code> if it does.
     */
    public static Boolean hasIntermediateMethodAssociatedFormalParameters(
            IntermediateMethod intermediateMethod) {
        boolean hasParam = Boolean.FALSE;
        if (intermediateMethod != null
                && intermediateMethod.eContainer() != null
                && intermediateMethod.eContainer() instanceof ProcessInterface) {
            ProcessInterface processInterface =
                    (ProcessInterface) intermediateMethod.eContainer();
            List<FormalParameter> formalParamList =
                    getAssociatedFormalParameters(intermediateMethod.getAssociatedParameters(),
                            processInterface.getFormalParameters());
            if (!formalParamList.isEmpty()) {
                hasParam = Boolean.TRUE;
            }
        }
        return hasParam;
    }

    /**
     * Utility to retrieve either a {@link FormalParameter} or a
     * {@link DataField} that is associated with an {@link AssociatedParameter}
     * 
     * @param associatedParameter
     * @return the {@link FormalParameter} or {@link DataField} that is
     *         associated with the {@link AssociatedParameter}.
     */
    public static ProcessRelevantData getProcessRelevantDataFromAssociatedParam(
            AssociatedParameter associatedParameter) {

        Collection availableData =
                getDataInScopeOfAssociatedParameter(associatedParameter);

        String assocDataName = associatedParameter.getFormalParam();

        ProcessRelevantData processRelevantData =
                findDataByName(availableData, assocDataName);

        return processRelevantData;

    }

    /**
     * Find process relevant data (field / param) in given list by it's given
     * name.
     * 
     * @param availableProcessRelevantData
     * @param dataName
     * @return data or null
     */
    private static ProcessRelevantData findDataByName(
            Collection availableProcessRelevantData, String dataName) {
        ProcessRelevantData processRelevantData = null;

        for (Iterator iterator = availableProcessRelevantData.iterator(); iterator
                .hasNext();) {
            ProcessRelevantData data = (ProcessRelevantData) iterator.next();

            if (dataName.equals(data.getName())) {
                processRelevantData = data;
                break;
            }
        }
        return processRelevantData;
    }

    /**
     * Get the correlation data field referenced by the associated correlation
     * data item for an activity.
     * 
     * @param associatedCorrelationField
     * 
     * @return data field or null if not found.
     */
    public static DataField getCorrelationFieldForCorrelationAssociation(
            AssociatedCorrelationField associatedCorrelationField) {
        Process process = Xpdl2ModelUtil.getProcess(associatedCorrelationField);

        ProcessRelevantData field =
                findDataByName(process.getDataFields(),
                        associatedCorrelationField.getName());
        if (field instanceof DataField) {
            return (DataField) field;
        }

        return null;
    }

    /**
     * Gets Processrelevant data in scope of the associated parameter.
     * 
     * @param associatedParameter
     * @return a collection of ProcessRelevantData objects
     *         (DataField/FormalParameter) that is available in the scope of the
     *         given AssociatedParameter.
     */
    public static Collection<? extends ProcessRelevantData> getDataInScopeOfAssociatedParameter(
            AssociatedParameter associatedParameter) {
        //
        // Get all the available data in scope of associated params container.
        EObject parentObject = associatedParameter.eContainer();
        List<? extends ProcessRelevantData> availableData =
                Collections.emptyList();

        if (parentObject instanceof InterfaceMethod
                || parentObject instanceof ErrorMethod) {
            ProcessInterface ifc =
                    ProcessInterfaceUtil.getProcessInterface(parentObject);
            if (ifc != null) {
                availableData = ifc.getFormalParameters();
            }

        } else if (parentObject instanceof AssociatedParameters) {
            if (parentObject.eContainer() instanceof Activity) {
                availableData =
                        ProcessInterfaceUtil
                                .getAllAvailableRelevantDataForActivity((Activity) parentObject
                                        .eContainer());
            }
        }
        return availableData;
    }

    /**
     * Create {@link AssociatedParameter} for a given {@link FormalParameter}.
     * Set the name, mode, description and mandatory settings on the associated
     * paramter.
     * 
     * @param param
     * @return Associated
     */
    public static AssociatedParameter createAssociatedParam(
            FormalParameter param) {
        AssociatedParameter associatedParameter =
                XpdExtensionFactory.eINSTANCE.createAssociatedParameter();
        associatedParameter.setFormalParam(param.getName());
        associatedParameter.setMode(param.getMode());

        if (param.getDescription() != null
                && param.getDescription().getValue() != null) {
            Description description =
                    Xpdl2Factory.eINSTANCE.createDescription();
            description.setValue(param.getDescription().getValue());
            associatedParameter.setDescription(description);
        }

        associatedParameter.setMandatory(param.isRequired());
        return associatedParameter;

    }

    /**
     * Create {@link AssociatedParameter} for a given {@link FormalParameter} or
     * {@link DataField} Set the name, mode, description and mandatory settings
     * on the associated paramter.
     * 
     * @param processRelevantData
     * @return
     */
    public static AssociatedParameter createAssociatedParam(
            ProcessRelevantData processRelevantData) {
        if (processRelevantData instanceof FormalParameter) {
            return createAssociatedParam((FormalParameter) processRelevantData);
        }

        AssociatedParameter associatedParameter =
                XpdExtensionFactory.eINSTANCE.createAssociatedParameter();
        associatedParameter.setFormalParam(processRelevantData.getName());
        associatedParameter.setMode(ModeType.INOUT_LITERAL);

        Description description = Xpdl2Factory.eINSTANCE.createDescription();
        if (processRelevantData.getDescription() != null) {
            description.setValue(processRelevantData.getDescription()
                    .getValue());
        }
        associatedParameter.setDescription(description);
        associatedParameter.setMandatory(false);
        return associatedParameter;

    }

    /**
     * This utility method, returns a list of {@link ProcessRelevantData} for
     * the activity of a certain modeType.
     * 
     * @param activity
     * @param modeType
     * @param includeInOutMode
     * 
     * @return a list of {@link ProcessRelevantData} given {@link ModeType}. If
     *         the {@link Activity} does not contain any
     *         {@link AssociatedParameter} then then method returns all the
     *         {@link FormalParameter} and {@link DataField} for the
     *         {@link Process}, all the {@link FormalParameter} of the
     *         {@link ProcessInterface} if the activity is an implemented
     *         {@link Activity} and all the {@link DataField} of the
     *         {@link Package} within which the {@link Process} is contained
     *         <b>unless the xpdExt:DisableImplicitAssociation is set
     *         <code>true</code> in which case an empty list is returned.</b>
     * 
     * @deprecated Use
     *             {@link ActivityInterfaceDataUtil#getActivityInterfaceData(Activity)}
     *             instead - which consolidates explicitly/implicitly associated
     *             data into a single class.
     */
    @Deprecated
    public static List<ProcessRelevantData> getAssociatedProcessRelevantDataForActivity(
            Activity act, ModeType modeType, boolean includeInOutMode) {

        List<ProcessRelevantData> processRelevantData = Collections.EMPTY_LIST;

        List<AssociatedParameter> associatedParameters =
                getActivityAssociatedParameters(act);

        if (associatedParameters != null && !associatedParameters.isEmpty()) {
            Collection availableData =
                    getDataInScopeOfAssociatedParameter(associatedParameters
                            .get(0));

            processRelevantData = new ArrayList<ProcessRelevantData>();

            for (AssociatedParameter param : associatedParameters) {
                if (param != null) {
                    ModeType paramModeType = param.getMode();
                    if (paramModeType != null) {
                        if (includeInOutMode
                                && (paramModeType
                                        .equals(ModeType.INOUT_LITERAL) || paramModeType
                                        .equals(modeType))) {
                            ProcessRelevantData data =
                                    findDataByName(availableData,
                                            param.getFormalParam());
                            if (data != null) {
                                processRelevantData.add(data);
                            }

                        } else if (paramModeType.equals(modeType)) {
                            ProcessRelevantData data =
                                    findDataByName(availableData,
                                            param.getFormalParam());
                            if (data != null) {
                                processRelevantData.add(data);
                            }
                        }
                    }
                }
            }
        }
        /*
         * Sid XPD-2087: Only return all data implicitly if implicit association
         * has not been disabled.
         */
        else if (!isImplicitAssociationDisabled(act)) {
            // if the activity does not contain any associated parameters then
            // return all the formal parameters and datafields for the process,
            // the formal parameters of the process interface if the activity is
            // an implemented event, and all the datafields of the package
            processRelevantData = getAllAvailableRelevantDataForActivity(act);
        }

        return Collections.unmodifiableList(processRelevantData);

    }

    /**
     * Utility to retrieve {@link ProcessRelevantData} for {@link Activity}
     * 
     * @param act
     * @return a list of {@link ProcessRelevantData} associated with the list of
     *         {@link AssociatedParameter} that is contained by a given
     *         {@link Activity}. If the {@link Activity} does not contain any
     *         {@link AssociatedParameter} then then method returns all the
     *         {@link FormalParameter} and {@link DataField} for the
     *         {@link Process}, all the {@link FormalParameter} of the
     *         {@link ProcessInterface} if the activity is an implemented
     *         {@link Activity} and all the {@link DataField} of the
     *         {@link Package} within which the {@link Process} is contained
     *         <b>unless the xpdExt:DisableImplicitAssociation is set
     *         <code>true</code> in which case an empty list is returned.</b>
     * 
     * @deprecated Use
     *             {@link ActivityInterfaceDataUtil#getActivityInterfaceData(Activity)}
     *             instead - which consolidates explicitly/implicitly associated
     *             data into a single class.
     */
    @Deprecated
    public static List<ProcessRelevantData> getAssociatedProcessRelevantDataForActivity(
            Activity act) {
        return getAssociatedProcessRelevantDataForActivity(act, false);
    }

    /**
     * Utility to retrieve {@link ProcessRelevantData} for {@link Activity}
     * <p>
     * Data is either explicitly associated OR all available data if none
     * explicitly associated.
     * 
     * @param act
     * @param mandatoryOnly
     * @return unmodifiable list.
     * 
     * @deprecated Use
     *             {@link ActivityInterfaceDataUtil#getActivityInterfaceData(Activity)}
     *             instead - which consolidates explicitly/implicitly associated
     *             data into a single class.
     */
    @Deprecated
    public static List<ProcessRelevantData> getAssociatedProcessRelevantDataForActivity(
            Activity act, boolean mandatoryOnly) {

        List<ProcessRelevantData> processRelevantData =
                new ArrayList<ProcessRelevantData>();

        List<AssociatedParameter> associatedParameters =
                getActivityAssociatedParameters(act);

        if (associatedParameters != null && !associatedParameters.isEmpty()) {
            Collection availableData =
                    getDataInScopeOfAssociatedParameter(associatedParameters
                            .get(0));

            for (AssociatedParameter param : associatedParameters) {
                ProcessRelevantData data =
                        findDataByName(availableData, param.getFormalParam());
                if (data != null) {
                    if (!mandatoryOnly || param.isMandatory()) {
                        processRelevantData.add(data);
                    }
                }
            }

        }
        /*
         * Sid XPD-2087: Only return all data implicitly if implicit association
         * has not been disabled.
         */
        else if (!isImplicitAssociationDisabled(act)) {

            // if the activity does not contain any associated parameters then
            // return all the formal parameters and datafields for the process,
            // the formal parameters of the process interface if the activity is
            // an implemented event, and all the datafields of the package
            List<ProcessRelevantData> allData =
                    getAllAvailableRelevantDataForActivity(act);

            for (ProcessRelevantData data : allData) {
                if (!mandatoryOnly
                        || (data instanceof FormalParameter && ((FormalParameter) data)
                                .isRequired())) {
                    processRelevantData.add(data);
                }
            }
        }

        return Collections.unmodifiableList(processRelevantData);
    }

    /**
     * List of correlation data fields explicitly / implicitly associated with
     * given activity.
     * <p>
     * <b>Note:</b These are assocaitions for correlation in request API
     * activities NOT standard association for other activity types).
     * 
     * @param activity
     * 
     * @return list of correlation data fields explicitly / implicitly
     *         associated (for correlation in request API activities NOT
     *         standard association for other activity types)
     */
    public static List<DataField> getAssociatedCorrelationDataForActivity(
            Activity activity) {
        return Collections.unmodifiableList(Xpdl2ModelUtil
                .getAssociatedCorrelationData(activity));
    }

    /**
     * Get all Co-relation Field for a Process.
     * 
     * @param process
     * @return List of all correlation data fields in this process.
     */
    public static List<DataField> getCorrelationDataFields(Process process) {
        return Collections.unmodifiableList(Xpdl2ModelUtil
                .getCorrelationDataFields(process));
    }

    /**
     * Return all the data fields and formal parameters that are in the scope of
     * the given activity.
     * <p>
     * That is, the parent process fields/params, the parent package fields and
     * the params of implemented interface (if process implements an interface.
     * </p>
     * 
     * @param act
     * @return List of all available relevant data for an activity.
     */
    public static List<ProcessRelevantData> getAllAvailableRelevantDataForActivity(
            Activity act) {
        List<ProcessRelevantData> processRelevantData =
                new ArrayList<ProcessRelevantData>();
        Process process = act.getProcess();
        if (process != null) {
            ProcessInterface procIfc = null;
            procIfc = getImplementedProcessInterface(process);
            Package pkg = process.getPackage();

            processRelevantData.addAll(process.getFormalParameters());

            processRelevantData.addAll(process.getDataFields());

            if (procIfc != null) {
                processRelevantData.addAll(procIfc.getFormalParameters());
            }
            processRelevantData.addAll(pkg.getDataFields());

            // MR 39410 - begin
            /**
             * add the embedded sub-proc data fields to the process relevant
             * data list
             * */
            // An actiivity should have access to it's own datafields
            processRelevantData.addAll(act.getDataFields());

            if (act.eContainer() instanceof ActivitySet) {
                /**
                 * if this embSubProcActivityForActSet is embedded in embedded
                 * sub proc then get the data fields of parent embedded sub proc
                 * */
                List<ProcessRelevantData> embData =
                        LocalPackageProcessInterfaceUtil
                                .getEmbeddedSubProcessSetScopeData((ActivitySet) act
                                        .eContainer());
                if (embData != null && !embData.isEmpty()) {
                    processRelevantData.addAll(embData);
                }

            }
            // MR 39410 - end
        }
        return Collections.unmodifiableList(processRelevantData);

    }

    /**
     * Return all the data fields and formal parameters that are in the scope of
     * the given activity - in scope order.
     * <p>
     * The scope order is most--local-first (i.e. local activity data first,
     * then parent embedded sub-process and then process and then process-
     * interface then package
     * </p>
     * 
     * @param act
     * @return List of all available relevant data for an activity.
     */
    public static List<ProcessRelevantData> getActivityDataInScopeOrder(
            Activity act) {
        List<ProcessRelevantData> processRelevantData =
                new ArrayList<ProcessRelevantData>();

        Process process = act.getProcess();
        if (process != null) {

            /**
             * add the embedded sub-proc data fields to the process relevant
             * data list
             * */
            // An actiivity should have access to it's own datafields
            processRelevantData.addAll(act.getDataFields());

            if (act.eContainer() instanceof ActivitySet) {
                /**
                 * if this embSubProcActivityForActSet is embedded in embedded
                 * sub proc then get the data fields of parent embedded sub proc
                 * */
                List<ProcessRelevantData> embData =
                        LocalPackageProcessInterfaceUtil
                                .getEmbeddedSubProcessSetScopeData((ActivitySet) act
                                        .eContainer());
                if (embData != null && !embData.isEmpty()) {
                    processRelevantData.addAll(embData);
                }

            }

            processRelevantData.addAll(process.getDataFields());

            processRelevantData.addAll(process.getFormalParameters());

            ProcessInterface procIfc = null;
            procIfc = getImplementedProcessInterface(process);

            if (procIfc != null) {
                processRelevantData.addAll(procIfc.getFormalParameters());
            }

            Package pkg = process.getPackage();

            processRelevantData.addAll(pkg.getDataFields());

        }
        return Collections.unmodifiableList(processRelevantData);

    }

    /**
     * Return all the data fields and formal parameters that are in the scope of
     * the given activity.
     * <p>
     * That is, the parent process fields/params, the parent package fields and
     * the params of implemented interface (if process implements an interface.
     * </p>
     * 
     * @param act
     * @return Map (name --> data) of all available relevant data for an
     *         activity.
     */
    public static Map<String, ProcessRelevantData> getAllAvailableRelevantDataMapForActivity(
            Activity act) {
        return Collections
                .unmodifiableMap(getNameToDataMap(getAllAvailableRelevantDataForActivity(act)));
    }

    /**
     * Get all associated parameters for a given activity.
     * 
     * @param activity
     * @return {@link AssociatedParameter} associated with an {@link Activity}.
     *         Includes those of the implemented method's as well.
     * @deprecated Use
     *             {@link ActivityInterfaceDataUtil#getActivityInterfaceData(Activity)}
     *             instead - which consolidates explicitly/implicitly associated
     *             data into a single class.
     */
    @Deprecated
    public static List<AssociatedParameter> getActivityAssociatedParameters(
            Activity activity) {
        List<AssociatedParameter> associatedParameters =
                new ArrayList<AssociatedParameter>();

        // if (isEventImplemented(act)
        // && getImplementedMethodAssociatedParameters(act) != null) {
        // associatedParameters
        // .addAll(getImplementedMethodAssociatedParameters(act));
        // }
        EObject obj =
                activity.getOtherElement(XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_AssociatedParameters().getName());
        if (obj != null && obj instanceof AssociatedParameters) {
            AssociatedParameters associatedParams = (AssociatedParameters) obj;

            associatedParameters.addAll(associatedParams
                    .getAssociatedParameter());
        }

        return Collections.unmodifiableList(associatedParameters);
    }

    /**
     * Retrieve the mode type of a given associated parameter
     * 
     * @param assocParam
     * @return ModeType
     */
    public static ModeType getAssocParamModeType(AssociatedParameter assocParam) {
        return assocParam.getMode();
    }

    /**
     * Get the mode of the given field/param in association with the given
     * activity.
     * <p>
     * If implicitly associations are in practice then DataField=INOUT or
     * FormalParam=ItsOwnMode
     * <p>
     * If some data is excplicitly associated with activity then the associated
     * param mode is returned unless the data is not associated in which case
     * <code>null</code>
     * 
     * @param data
     * @return The modeType or <code>null</code> if the data is explicitly
     *         disassociated from activity.
     */
    public static ModeType getAssociatedDataMode(Activity activity,
            ProcessRelevantData data) {
        ModeType mode = null;

        Collection<ActivityInterfaceData> activityInterfaceData =
                ActivityInterfaceDataUtil.getActivityInterfaceData(activity);

        for (ActivityInterfaceData ifcData : activityInterfaceData) {
            if (ifcData.getData().equals(data)) {
                mode = ifcData.getMode();
            }
        }

        if (mode == null) {
            /*
             * If we didn't get the mode from associations then get the default.
             * (might be that the data was explicitly disassociated)
             */
            if (data instanceof FormalParameter) {
                mode = ((FormalParameter) data).getMode();
            } else {
                mode = ModeType.INOUT_LITERAL;
            }
        }

        return mode;
    }

    /**
     * Checks whether an activity has any associated Formal Parameters
     * 
     * @param activity
     * @return <code>true</code> if it does
     */
    public static Boolean hasAssociatedFormalParameters(Activity activity) {
        boolean hasAssociated = Boolean.FALSE;
        EObject obj =
                activity.getOtherElement(XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_AssociatedParameters().getName());
        if (obj != null && obj instanceof AssociatedParameters) {
            AssociatedParameters associatedParams = (AssociatedParameters) obj;
            List<AssociatedParameter> associatedParamList =
                    associatedParams.getAssociatedParameter();
            if (!associatedParamList.isEmpty()) {
                hasAssociated = Boolean.TRUE;
            }
        }
        ProcessInterface processInterface =
                getImplementedProcessInterface(activity.getProcess());
        if (processInterface != null) {
            List<FormalParameter> processInterfaceFormalParameters =
                    processInterface.getFormalParameters();
            if (getImplementedStartMethod(activity) != null) {
                StartMethod startMethod = getImplementedStartMethod(activity);
                if (hasStartMethodAssociatedFormalParameters(startMethod)) {
                    hasAssociated = Boolean.TRUE;
                }
            } else if (getImplementedIntermediateMethod(activity) != null) {
                IntermediateMethod intermediateMethod =
                        getImplementedIntermediateMethod(activity);
                if (hasIntermediateMethodAssociatedFormalParameters(intermediateMethod)) {
                    hasAssociated = Boolean.TRUE;
                }
            }
        }
        return hasAssociated;
    }

    /**
     * Find a corresponding {@link EndEvent} for a given {@link StartMethod} in
     * a {@link Process}.
     * 
     * @param process
     * @param interfaceMethod
     * @return end event which follows the start that implements a process
     *         interface start event.
     */
    public static Activity findCorrespondingEndEvent(Process process,
            InterfaceMethod interfaceMethod) {
        Collection<Activity> activities =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        for (Activity act : activities) {
            InterfaceMethod implementedMethod = getImplementedMethod(act);
            if (implementedMethod != null
                    && implementedMethod == interfaceMethod) {
                if (act.getEvent() != null
                        && act.getEvent() instanceof EndEvent) {
                    return act;
                }
            }
        }
        return null;
    }

    /**
     * Find a corresponding {@link StartEvent} for a given
     * {@link InterfaceMethod} in a {@link Process}.
     * 
     * @param process
     * @param interfaceMethod
     * @return start event for that implements a certain start event in the
     *         process interface
     */
    public static Activity findCorrespondingStartEvent(Process process,
            InterfaceMethod interfaceMethod) {
        Collection<Activity> activities =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        for (Activity act : activities) {
            InterfaceMethod implementedMethod = getImplementedMethod(act);
            if (implementedMethod != null
                    && implementedMethod.equals(interfaceMethod)) {
                if (act.getEvent() != null
                        && act.getEvent() instanceof StartEvent) {
                    return act;
                }
            }
        }
        return null;
    }

    /**
     * Find a corresponding {@link IntermediateEvent} for a given
     * {@link InterfaceMethod} in a {@link Process}.
     * 
     * @param process
     * @param interfaceMethod
     * @return intermediate event for that implements a certain start event in
     *         the process interface
     */
    public static Activity findCorrespondingIntermediateEvent(Process process,
            InterfaceMethod interfaceMethod) {
        Collection<Activity> activities =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        for (Activity act : activities) {
            InterfaceMethod implementedMethod = getImplementedMethod(act);
            if (implementedMethod != null
                    && implementedMethod.equals(interfaceMethod)) {
                if (act.getEvent() != null
                        && act.getEvent() instanceof IntermediateEvent) {
                    return act;
                }
            }
        }
        return null;
    }

    /**
     * Find a corresponding {@link EndEvent} for a given {@link ErrorMethod} in
     * a {@link Process}.
     * 
     * @param process
     * @param interfaceMethod
     * @return end event for that implements a certain error event in the
     *         process interface
     */
    public static Activity findCorrespondingEndEvent(Process process,
            ErrorMethod errorMethod) {
        Collection<Activity> activities =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        for (Activity act : activities) {
            ErrorMethod implementedErrorMethod = getImplementedErrorMethod(act);
            if (errorMethod != null && implementedErrorMethod == errorMethod) {
                if (act.getEvent() != null
                        && act.getEvent() instanceof EndEvent) {
                    return act;
                }
            }
        }
        return null;
    }

    /**
     * This method returns all process data available to process.
     * 
     * @param process
     * @return list of {@link ProcessRelevantData}
     */
    public static List<ProcessRelevantData> getAllProcessRelevantData(
            Process process) {
        List<ProcessRelevantData> allRelevantData =
                new ArrayList<ProcessRelevantData>();
        allRelevantData.addAll(getAllFormalParameters(process));
        allRelevantData.addAll(process.getDataFields());
        Package pkg = process.getPackage();
        if (pkg != null) {
            allRelevantData.addAll(pkg.getDataFields());
        }

        return Collections.unmodifiableList(allRelevantData);
    }

    /**
     * 
     * @param data
     * @return Map of data name to data for given collection.
     */
    public static Map<String, ProcessRelevantData> getNameToDataMap(
            Collection<? extends ProcessRelevantData> data) {
        HashMap<String, ProcessRelevantData> map =
                new HashMap<String, ProcessRelevantData>();

        for (ProcessRelevantData d : data) {
            map.put(d.getName(), d);
        }

        return map;
    }

    /**
     * This method returns all external process data for an element.
     * 
     * @param process
     * @return list of external process relevant data if there exists any.
     */
    public static List<ProcessRelevantData> getAllExternalProcessRelevantData(
            EObject element) {
        List<ProcessRelevantData> allRelevantData =
                new ArrayList<ProcessRelevantData>();
        if (element instanceof Process) {
            allRelevantData
                    .addAll(getAllExternalProcessRelevantData((Process) element));
        } else if (element instanceof ProcessInterface) {
            allRelevantData
                    .addAll(getAllExternalProcessRelevantData((ProcessInterface) element));
        } else if (element instanceof Package) {
            allRelevantData
                    .addAll(getAllExternalProcessRelevantData((Package) element));
        }
        return Collections.unmodifiableList(allRelevantData);
    }

    /**
     * This method returns all external process data ONLY assigned to process.
     * 
     * @param process
     * @return list of external process relevant data for a given process
     */
    public static List<ProcessRelevantData> getAllExternalProcessRelevantData(
            Process process) {

        List<ProcessRelevantData> allExternalPrd =
                new ArrayList<ProcessRelevantData>();
        /*
         * XPD-3967: need to take into account embedded sub proc local data
         * fields also
         */
        Collection<ProcessRelevantData> allDataDefinedInProcess =
                ProcessInterfaceUtil.getAllDataDefinedInProcess(process);

        if (!allDataDefinedInProcess.isEmpty()) {

            for (Object obj : allDataDefinedInProcess) {

                if (obj instanceof ProcessRelevantData) {
                    ProcessRelevantData prd = (ProcessRelevantData) obj;

                    if (prd.getDataType() instanceof ExternalReference) {
                        allExternalPrd.add(prd);
                    } else if (prd.getDataType() instanceof RecordType) {
                        allExternalPrd.add(prd);
                    }
                }
            }
        }
        return Collections.unmodifiableList(allExternalPrd);
    }

    /**
     * This method returns all external process data ONLY assigned to process
     * interface
     * 
     * @param processInterface
     * @return list of external process relevant data for a given process
     *         interface
     */
    public static List<ProcessRelevantData> getAllExternalProcessRelevantData(
            ProcessInterface processInterface) {

        List<ProcessRelevantData> allRelevantData =
                new ArrayList<ProcessRelevantData>();
        List<ProcessRelevantData> allExternalPrd =
                new ArrayList<ProcessRelevantData>();
        allRelevantData.addAll(processInterface.getFormalParameters());

        if (!allRelevantData.isEmpty()) {

            for (Object obj : allRelevantData) {

                if (obj instanceof ProcessRelevantData) {

                    ProcessRelevantData prd = (ProcessRelevantData) obj;

                    if (prd.getDataType() instanceof ExternalReference) {
                        allExternalPrd.add(prd);
                    } else if (prd.getDataType() instanceof RecordType) {
                        allExternalPrd.add(prd);
                    }
                }
            }
        }
        return Collections.unmodifiableList(allExternalPrd);
    }

    /**
     * This method returns all external process data ONLY assigned to package.
     * 
     * @param processPackage
     * @return list of external process relevant data for a given xpdl package
     */
    public static List<ProcessRelevantData> getAllExternalProcessRelevantData(
            Package processPackage) {

        List<ProcessRelevantData> allRelevantData =
                new ArrayList<ProcessRelevantData>();
        List<ProcessRelevantData> allExternalPrd =
                new ArrayList<ProcessRelevantData>();
        allRelevantData.addAll(processPackage.getDataFields());

        if (!allRelevantData.isEmpty()) {

            for (Object obj : allRelevantData) {

                if (obj instanceof ProcessRelevantData) {
                    ProcessRelevantData prd = (ProcessRelevantData) obj;

                    if (prd.getDataType() instanceof ExternalReference) {
                        allExternalPrd.add(prd);
                    } else if (prd.getDataType() instanceof RecordType) {
                        allExternalPrd.add(prd);
                    }

                }
            }
        }
        return Collections.unmodifiableList(allExternalPrd);
    }

    /**
     * This method returns all case reference type data for a given xpdl
     * package.
     * 
     * PLEASE NOTE THAT case classes referenced from global data task(s) in a
     * process might also be required as case reference data in some situations.
     * But this method does not return that data. You need to get that data
     * separately. Please see
     * BDSCompositeUtil.getCaseBOMsInBusinessDataProjects() (in N2 feature) for
     * an example.
     * 
     * @param processPackage
     * @return list of case reference type process relevant data for a given
     *         xpdl package
     */
    public static List<ProcessRelevantData> getAllCaseRefData(
            Package processPackage) {

        List<ProcessRelevantData> allCaseRefData =
                new ArrayList<ProcessRelevantData>();
        List<ProcessRelevantData> allRelevantData =
                new ArrayList<ProcessRelevantData>();
        allRelevantData.addAll(processPackage.getDataFields());

        for (ProcessRelevantData processRelevantData : allRelevantData) {

            if (processRelevantData.getDataType() instanceof RecordType) {

                RecordType recordType =
                        (RecordType) processRelevantData.getDataType();
                EList<Member> memberList = recordType.getMember();
                Member member = memberList.get(0);

                if (null != member.getExternalReference()) {

                    allCaseRefData.add(processRelevantData);
                }
            }
        }

        return allCaseRefData;
    }

    /**
     * This method returns all case reference type process data for a given
     * process.
     * 
     * PLEASE NOTE THAT case classes referenced from global data task(s) in a
     * process might also be required as case reference data in some situations.
     * But this method does not return that data. You need to get that data
     * separately. Please see
     * BDSCompositeUtil.getCaseBOMsInBusinessDataProjects() (in N2 feature) for
     * an example.
     * 
     * @param process
     * @return list of case reference type process relevant data for a given
     *         process
     */
    public static List<ProcessRelevantData> getAllCaseRefData(Process process) {

        List<ProcessRelevantData> allCaseRefData =
                new ArrayList<ProcessRelevantData>();
        Collection<ProcessRelevantData> allDataDefinedInProcess =
                ProcessInterfaceUtil.getAllDataDefinedInProcess(process);

        for (ProcessRelevantData processRelevantData : allDataDefinedInProcess) {

            if (processRelevantData.getDataType() instanceof RecordType) {

                RecordType recordType =
                        (RecordType) processRelevantData.getDataType();
                EList<Member> memberList = recordType.getMember();
                Member member = memberList.get(0);

                if (null != member.getExternalReference()) {

                    allCaseRefData.add(processRelevantData);
                }
            }
        }
        return allCaseRefData;
    }

    /**
     * Returns the containing Process interface for a given EObject.
     * 
     * @param object
     * @return {@link ProcessInterface} parent.
     */
    public static ProcessInterface getProcessInterface(EObject object) {
        if (object != null) {
            if (object instanceof ProcessInterface) {
                return (ProcessInterface) object;
            }
            EObject parent = object.eContainer();
            while (parent != null) {
                if (parent instanceof ProcessInterface) {
                    return (ProcessInterface) parent;
                }
                parent = parent.eContainer();
            }
        }

        return null;
    }

    /**
     * Error end events are implemented in the process.
     * 
     * @since 3.2
     * @param activity
     * @return {@link ErrorMethod}
     */
    public static ErrorMethod getImplementedErrorMethod(Activity activity) {
        Object obj =
                Xpdl2ModelUtil.getOtherAttribute(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_Implements());
        if (obj != null && activity.getProcess() != null) {
            String implementedMethodId = (String) obj;
            Object impInt =
                    activity.getProcess()
                            .getOtherElement(XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ImplementedInterface()
                                    .getName());
            if (impInt != null) {
                ProcessInterface implementedProcessInterface =
                        getImplementedProcessInterface(activity.getProcess());
                if (implementedProcessInterface != null) {
                    for (StartMethod startMethod : implementedProcessInterface
                            .getStartMethods()) {
                        EObject eObj =
                                EMFSearchUtil.findInList(startMethod
                                        .getErrorMethods(),
                                        Xpdl2Package.eINSTANCE
                                                .getUniqueIdElement_Id(),
                                        implementedMethodId);
                        if (eObj instanceof ErrorMethod) {
                            return (ErrorMethod) eObj;
                        }
                    }

                    for (IntermediateMethod intermediateMethod : implementedProcessInterface
                            .getIntermediateMethods()) {
                        EObject eObj =
                                EMFSearchUtil.findInList(intermediateMethod
                                        .getErrorMethods(),
                                        Xpdl2Package.eINSTANCE
                                                .getUniqueIdElement_Id(),
                                        implementedMethodId);
                        if (eObj instanceof ErrorMethod) {
                            return (ErrorMethod) eObj;
                        }
                    }
                }

            }
        }
        return null;
    }

    /**
     * Find and return the activity in the given process that implements the
     * given interface method id.
     * <p>
     * <b>NOTE: this method will ignore end events even when they are flagged as
     * implementing the the given method id.</b>
     * 
     * @param process
     * @param interfaceMethodId
     * 
     * @return The activity that implements the given method or null if not
     *         found.
     */
    public static Activity getImplementingActivity(Process process,
            String interfaceMethodId) {
        Collection<Activity> allActs =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);

        for (Activity activity : allActs) {
            Object obj =
                    Xpdl2ModelUtil.getOtherAttribute(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Implements());
            if (interfaceMethodId.equals(obj)) {
                // Ignore end events (except error method implementing end
                // events)
                if (!(activity.getEvent() instanceof EndEvent)
                        || ResultType.ERROR_LITERAL.equals(((EndEvent) activity
                                .getEvent()).getResult())) {
                    return activity;
                }
            }
        }

        return null;
    }

    /**
     * Gets the list of {@link InterfaceMethod}s from a given
     * {@link ProcessInterface}.
     * 
     * @param processInterface
     * @return List of interface message methods of the given
     *         {@link ProcessInterface}
     */

    public static Collection<InterfaceMethod> getIfcMessageMethods(
            ProcessInterface processInterface) {

        List<? extends InterfaceMethod> ifcMethods =
                new ArrayList<InterfaceMethod>();
        List startMsgMethods =
                EMFSearchUtil
                        .findManyInList(processInterface.getStartMethods(),
                                XpdExtensionPackage.eINSTANCE
                                        .getInterfaceMethod_Trigger(),
                                TriggerType.MESSAGE_LITERAL);
        List intermediateMsgMethods =
                EMFSearchUtil.findManyInList(processInterface
                        .getIntermediateMethods(),
                        XpdExtensionPackage.eINSTANCE
                                .getInterfaceMethod_Trigger(),
                        TriggerType.MESSAGE_LITERAL);
        ifcMethods.addAll(startMsgMethods);
        ifcMethods.addAll(intermediateMsgMethods);
        return Collections.unmodifiableList(ifcMethods);
    }

    /**
     * Retrieves list of scoped process relevant data for a given activity.
     * 
     * @param activity
     * @return list of {@link ProcessRelevantData} for a given activity
     */
    public static List<? extends ProcessRelevantData> getScopedAssociatedProcessRelevantDataForActivity(
            Activity act) {
        if (Xpdl2ModelUtil.isProcessAPIActivity(act)) {
            return getAssociatedFormalParameters(act);
        } else {
            if (act.getEvent() != null
                    || act.getImplementation() instanceof Task
                    || act.getImplementation() instanceof SubFlow) {
                return getAssociatedProcessRelevantDataForActivity(act);
            }
        }
        return Collections.emptyList();
    }

    /**
     * @param eo
     * 
     * @return return parent method of given eObject.
     */
    public static AssociatedParametersContainer getParentMethod(EObject eo) {
        AssociatedParametersContainer associatedParametersContainer = null;

        if (eo instanceof AssociatedParametersContainer) {
            associatedParametersContainer = (AssociatedParametersContainer) eo;

        } else if (eo != null) {
            associatedParametersContainer = getParentMethod(eo.eContainer());
        }

        return associatedParametersContainer;
    }

    /**
     * @param pkg
     * 
     * @return Every data field/ formal parameter in
     *         package/processinterfaces/processes and embedded sub-processes.
     */
    public static Collection<ProcessRelevantData> getAllDataInPackage(
            Package pkg) {
        List<ProcessRelevantData> data = new ArrayList<ProcessRelevantData>();

        data.addAll(pkg.getDataFields());

        ProcessInterfaces ifcs = getProcessInterfaces(pkg);
        if (ifcs != null) {
            for (ProcessInterface processInterface : ifcs.getProcessInterface()) {
                data.addAll(processInterface.getFormalParameters());
            }
        }

        /*
         * when processes get added to a package (say for instance business
         * service generation from a case class), iterating thru processes on
         * the package getting modified causes ConcurrentModificationException.
         * So take the snapshot of processes in the pkg to avoid getting this
         * exception
         */
        EList<Process> processes = pkg.getProcesses();
        List<Process> processList = new ArrayList<Process>(processes);

        for (Process process : processList) {
            data.addAll(process.getFormalParameters());
            data.addAll(process.getDataFields());

            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            for (Activity activity : activities) {
                data.addAll(activity.getDataFields());
            }
        }

        return Collections.unmodifiableList(data);
    }

    /**
     * @param pkg
     * 
     * @return Every data field/ formal parameter / inherited parameter in
     *         process and embedded sub-processes and parent package.
     */
    public static Collection<ProcessRelevantData> getAllDataDefinedInProcess(
            Process process) {
        List<ProcessRelevantData> data = new ArrayList<ProcessRelevantData>();

        /*
         * Get process fields, params and params inherited from process
         * interface and fields from parent package
         */
        data.addAll(getAllProcessRelevantData(process));

        Collection<Activity> activities =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        for (Activity activity : activities) {
            data.addAll(activity.getDataFields());
        }

        return Collections.unmodifiableList(data);
    }

    /**
     * @param activity
     * @return <code>true</code> if implicit data association is disabled (not
     *         including correlation data association).
     */
    public static boolean isImplicitAssociationDisabled(Activity activity) {
        boolean isImplicitDisabled = false;

        if (activity != null) {
            /*
             * If this event is implementing an interface event then the
             * semantic should be "whatever the interface says PLUS any
             * additional explicitly associated parameters" So there is no
             * implicit process-local-data-association, so we will use the flag
             * on the implemented interface method.
             */
            if (isEventImplemented(activity)) {
                AssociatedParametersContainer implementedMethod =
                        getImplementedMethod(activity);
                if (implementedMethod == null) {
                    implementedMethod = getImplementedErrorMethod(activity);
                }

                if (implementedMethod != null) {
                    isImplicitDisabled =
                            isImplicitAssociationDisabled(implementedMethod);
                }

            } else {
                /* Non implementing event. */
                Object assocParams =
                        Xpdl2ModelUtil
                                .getOtherElement(activity,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_AssociatedParameters());
                if (assocParams instanceof AssociatedParameters) {
                    isImplicitDisabled =
                            ((AssociatedParameters) assocParams)
                                    .isDisableImplicitAssociation();

                }
            }
        }
        return isImplicitDisabled;
    }

    /**
     * 
     * @param activity
     * @return <code>true</code> if implicit correlation data association has
     *         been disabled for given activity.
     */
    public static boolean isImplicitCorrelationAssociationDisabled(
            Activity activity) {
        boolean isDisableImplicit = false;
        Object other =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AssociatedCorrelationFields());
        if (other instanceof AssociatedCorrelationFields) {
            AssociatedCorrelationFields fieldContainer =
                    (AssociatedCorrelationFields) other;
            isDisableImplicit = fieldContainer.isDisableImplicitAssociation();
        }
        return isDisableImplicit;
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

    /**
     * Get the IFile for the WSDL that <i>could</i> be derived from the given
     * xpdlFile.
     * <p>
     * A non-null return does not necessarily mean that the WSDL exists (or
     * needs to exist) as this is controlled by whether a selected destination
     * environment switches on WSDL generation and whether there are any API
     * activities in the xpdl package.
     * 
     * @param xpdlFile
     * 
     * @return The WSDL that could (or has) been derived from the given xpdl
     *         file's process api activities or the implemented interface's
     *         activity.
     */
    public static IFile getDerivedWsdlFile(Activity activity) {

        Process process = activity.getProcess();
        Package procPkg = process.getPackage();
        IFile xpdlFile = WorkingCopyUtil.getFile(procPkg);

        ProcessInterface implementedProcessInterface =
                ProcessInterfaceUtil.getImplementedProcessInterface(process);
        /*
         * Check if the process implements interface and the activity implements
         * interface method
         */
        if (null != implementedProcessInterface
                && ProcessInterfaceUtil.isEventImplemented(activity)) {

            /*
             * XPD-7845: We need to ensure that the implemented method is of
             * type method. Previously we only checked if it implements a
             * method, but did not check which method it was.
             */
            TriggerResultMessage result = null;
            if (ProcessInterfaceUtil.getImplementedStartMethod(activity) != null) {
                StartMethod implementedStartMethod =
                        ProcessInterfaceUtil
                                .getImplementedStartMethod(activity);
                result = implementedStartMethod.getTriggerResultMessage();

            } else if (ProcessInterfaceUtil
                    .getImplementedIntermediateMethod(activity) != null) {
                IntermediateMethod implementedIntermediateMethod =
                        ProcessInterfaceUtil
                                .getImplementedIntermediateMethod(activity);
                result =
                        implementedIntermediateMethod.getTriggerResultMessage();
            }
            if (result != null) {

                /*
                 * Start or Intermediate methods are defined. That means a wsdl
                 * might be generated for it. So get the process package for the
                 * implemented interface
                 */
                Package processInterfacePackage =
                        ProcessInterfaceUtil
                                .getProcessInterfacePackage(implementedProcessInterface);
                /*
                 * Process and implementing interface are from different xpdls
                 */
                if (procPkg != processInterfacePackage) {

                    IFile procIntfXpdlFile =
                            WorkingCopyUtil.getFile(processInterfacePackage);

                    if (null != procIntfXpdlFile && procIntfXpdlFile.exists()) {
                        return Xpdl2ModelUtil
                                .getDerivedWsdlFile(procIntfXpdlFile);
                    }
                }
            }
        }

        /*
         * Process is not implementing any interface or process implements an
         * interface with event method and both process and interface are in the
         * same package or process implements an interface and interface has no
         * event methods
         */
        return Xpdl2ModelUtil.getDerivedWsdlFile(xpdlFile);
    }
}
