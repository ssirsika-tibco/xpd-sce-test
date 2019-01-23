/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.moveprocess;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.iprocess.amxbpm.converter.IProcessAMXBPMConverterPlugin;
import com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages;
import com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.RedefinableHeader;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * Moves the {@link Process}'s contained in the {@link Package}'s (passed to the
 * constructor) to separate process packages(i.e. each process under the
 * original Package is moved to separate packages). The name of the new process
 * package created depends is the same as the process being moved.
 * 
 * @author kthombar
 * @since 27-Mar-2014
 */
public class ProcessPackageSeparator {

    /**
     * old Packages whose processes are to be moved.
     */
    private Collection<Package> processPackages;

    /**
     * the version of the new created packages.
     */
    private String packageVersion;

    /**
     * Constructor to move all the processes under the passed {@link Package}/es
     * to separate packages. User need to call the
     * {@link ProcessPackageSeparator#performMove()} method(which is the only
     * visible method) in order to trigger this conversion.
     * 
     * @param processPackages
     *            the List of Packages whose processes are to be moved.
     * @param projectVersion
     *            the version of the Project, this version will be set to the
     *            new package created.
     */
    public ProcessPackageSeparator(Collection<Package> processPackages,
            String projectVersion) {
        this.processPackages = processPackages;
        this.packageVersion = projectVersion;
    }

    /**
     * Triggers the move of processes to separate packages. The newly created
     * package name will be = "processBeingMovedName"
     * 
     * @param progressMonitor
     *            {@link IProgressMonitor} that will show the progress
     * @param separatedPackages
     *            {@link Set} of {@link Package}/es which will be populated with
     *            the packages created.
     * @param processOriginatingXpdl
     *            , map to be populated with process name and its originating
     *            iProcess xpdl file name.
     * @return {@link IStatus} to track the status.
     * @throws OperationCanceledException
     */
    public IStatus separateProcesses(IProgressMonitor progressMonitor,
            Set<Package> separatedPackages,
            Map<String, String> processOriginatingXpdl)
            throws OperationCanceledException {

        MultiStatus returnStatus =
                new MultiStatus(IProcessAMXBPMConverterPlugin.PLUGIN_ID, 0,
                        "", null); //$NON-NLS-1$

        try {

            progressMonitor
                    .beginTask(Messages.IProcessToStudioAMXBPMConverterFramework_SeparateIntoPackages_msg,
                            processPackages.size() + 1);
            progressMonitor
                    .setTaskName(Messages.IProcessToStudioAMXBPMConverterFramework_SeparateIntoPackages_msg);

            Set<Package> newCreatedPackages = new LinkedHashSet<Package>();

            /**
             * Map that keeps track of processOrIfcName to the wrapper that
             * wraps up all data necessary to fix references.
             */
            Map<String, FixReferencesWrapper> fixReferencesMap =
                    new HashMap<String, FixReferencesWrapper>();

            for (Package processPackage : processPackages) {

                progressMonitor
                        .subTask(String
                                .format(Messages.MoveProcessToSeparatePackages_MoveProcessProgressMonitor_label,
                                        processPackage.getName()));

                Set<EObject> processAndInterfacesInPackage =
                        new HashSet<EObject>();

                addProcessesAndInterfacesToSet(processPackage,
                        processAndInterfacesInPackage,
                        processOriginatingXpdl);
                // Null check should be the first check otherwise fails the
                // purpose
                if (processAndInterfacesInPackage != null
                        && !processAndInterfacesInPackage.isEmpty()) {

                    /*
                     * Now as we a sure that the package really has some data to
                     * move, we can create a copy of the original process
                     * package.
                     */

                    for (EObject objectToMove : processAndInterfacesInPackage) {

                        NamedElement element = (NamedElement) objectToMove;

                        FixReferencesWrapper fixReferencesWrapper =
                                fixReferencesMap.get(element.getName());

                        if (fixReferencesWrapper != null) {
                            /* if the process exists, ignore the duplicates */
                            returnStatus
                                    .add(new Status(
                                            IStatus.INFO,
                                            IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                                            String.format(Messages.ProcessPackageSeparator_DuplicateProcess_msg,
                                                    element.getName())));

                            fixReferencesWrapper
                                    .setAllIdsWithSameProcName(element.getId());
                            continue;
                        }
                        /*
                         * if the process is not a duplicate then populate the
                         * map
                         */
                        fixReferencesMap.put(element.getName(),
                                new FixReferencesWrapper(element.getName(),
                                        element.getId(), objectToMove));

                        newCreatedPackages
                                .add(getCreateNewPackage(processPackage,
                                        objectToMove));
                    }
                }
                progressMonitor.worked(1);

                if (progressMonitor.isCanceled()) {
                    progressMonitor.done();
                    throw new OperationCanceledException();
                }
            }

            progressMonitor
                    .subTask(Messages.MoveProcessToSeparatePackages_FixingReferencesProgressMonitor_desc);
            /*
             * Now that all processes have been moved to separate packages, its
             * time to fix broken references ;-)
             */
            FixReferencesHelper.fixReferences(newCreatedPackages,
                    fixReferencesMap);

            separatedPackages.addAll(newCreatedPackages);

            progressMonitor.worked(1);
            returnStatus
                    .add(new Status(
                            IStatus.INFO,
                            IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                            Messages.ProcessPackageSeparator_ProcessesSeparatedSuccessfullyIstatus_nsg));

        } finally {
            progressMonitor.done();
        }
        return returnStatus;
    }

    /**
     * Adds all the processes and interface to move to the passed Set.
     * 
     * @param packageFromResource
     * @param processAndInterfacesInPackage
     * @param processOriginatingXpdl
     *            , map to be populated with process name and its originating
     *            iProcess xpdl file name.
     */
    private void addProcessesAndInterfacesToSet(Package packageFromResource,
            Set<EObject> processAndInterfacesInPackage,
            Map<String, String> processOriginatingXpdl) {

        IFile iProcessXpdl = WorkingCopyUtil.getFile(packageFromResource);

        EList<Process> processes = packageFromResource.getProcesses();
        // Populate map of originating iProcess XPDL
        for (Process process : processes) {

            processOriginatingXpdl.put(process.getName(),
                    iProcessXpdl.getName());
        }

        processAndInterfacesInPackage.addAll(processes);

        ProcessInterfaces processInterfaces =
                ProcessInterfaceUtil.getProcessInterfaces(packageFromResource);

        if (processInterfaces != null) {
            EList<ProcessInterface> processInterface =
                    processInterfaces.getProcessInterface();
            // Populate map of originating iProcess XPDL
            for (ProcessInterface processInterface2 : processInterface) {

                processOriginatingXpdl.put(processInterface2.getName(),
                        iProcessXpdl.getName());
            }
            processAndInterfacesInPackage.addAll(processInterface);
        }
    }

    /**
     * Does the following
     * <p>
     * 1. Creates a new package
     * <p>
     * 2. Adds process/ process interface to the new package
     * <p>
     * 3. adds dependent data to the package(i.e. pools, artifacts, association)
     * 
     * @param originalPackage
     *            the original package
     * @param eobjectToMove
     *            object to move(process/interface)
     * @param pm
     *            {@link IProgressMonitor} to show the progress
     * @return the new created package.
     */
    private Package getCreateNewPackage(Package originalPackage,
            EObject eobjectToMove) {

        /* the package name is same as the object being moved */
        String newPackageName =
                Xpdl2ModelUtil.getDisplayName((NamedElement) eobjectToMove);

        /* Create package */
        Package createPackage =
                createPackageCopy(originalPackage, newPackageName);

        /* Add contents(i.e. process/interface) to new package */

        if (eobjectToMove instanceof Process) {
            addProcessToPackage(createPackage, (Process) eobjectToMove);
        } else {
            addProcessInterfaceToPackage(createPackage,
                    (ProcessInterface) eobjectToMove);
        }

        return createPackage;
    }

    /**
     * Creates a copy of the Process interface and adds the copy to the newly
     * created package.
     * 
     * @param pkg
     *            the new created package
     * @param processInterface
     *            the interface which is to be added to the new package
     */
    private void addProcessInterfaceToPackage(Package pkg,
            ProcessInterface processInterface) {

        ProcessInterface copyofInterface = EcoreUtil.copy(processInterface);

        /* copy process interface to new package */
        ProcessInterfaces createProcessInterfaces =
                XpdExtensionFactory.eINSTANCE.createProcessInterfaces();

        createProcessInterfaces.getProcessInterface().add(copyofInterface);

        Xpdl2ModelUtil.setOtherElement(pkg, XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_ProcessInterfaces(), createProcessInterfaces);

    }

    /**
     * Creates a copy of the passed process and adds the copy to the newly
     * created process package. Also the dependent pools , associations ,
     * artifacts are added to the new package.
     * 
     * @param createPackage
     *            the new created package
     * @param process
     *            the process which is to be added to the new package
     */
    private void addProcessToPackage(Package createPackage, Process process) {

        Process copyOfProcess = EcoreUtil.copy(process);

        createPackage.getProcesses().add(copyOfProcess);

        Collection<Pool> processPools = Xpdl2ModelUtil.getProcessPools(process);

        if (!processPools.isEmpty()) {
            createPackage.getPools().addAll(EcoreUtil.copyAll(processPools));
        }

        Collection<Artifact> allArtifactsInProcess =
                Xpdl2ModelUtil.getAllArtifactsInProcess(process);

        if (!allArtifactsInProcess.isEmpty()) {
            createPackage.getArtifacts()
                    .addAll(EcoreUtil.copyAll(allArtifactsInProcess));
        }

        Collection<Association> allAssociationsInProc =
                Xpdl2ModelUtil.getAllAssociationsInProc(process);

        if (!allAssociationsInProc.isEmpty()) {
            createPackage.getAssociations()
                    .addAll(EcoreUtil.copyAll(allAssociationsInProc));
        }
    }

    /**
     * Does the following
     * <p>
     * 1. Creates a copy of the original package
     * <p>
     * 2. Assigns unique id to the new copy of package
     * <p>
     * 3. Deletes the elements like Processes, Process interfaces, pools,
     * artifacts, associations, external package reference from the new created
     * copy.
     * 
     * @param pkg
     *            the package whose copy is to be created
     * @param pkgName
     *            the name of the new package
     * @return
     */
    private Package createPackageCopy(Package pkg, String pkgName) {

        /* create a copy of old package */
        Package newCopiedPackage = EcoreUtil.copy(pkg);

        /*
         * We regenerate a new Id for the copied package. else we will land up
         * with all packages having same Id's :0
         */
        EcoreUtil.setID(newCopiedPackage, EcoreUtil.generateUUID());

        /* set the name and display label */
        newCopiedPackage.setName(NameUtil.getInternalName(pkgName, false));
        Xpdl2ModelUtil.setOtherAttribute(newCopiedPackage,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                pkgName);

        if (newCopiedPackage.getPools() != null) {
            newCopiedPackage.getPools().removeAll(newCopiedPackage.getPools());
        }

        if (newCopiedPackage.getProcesses() != null) {
            newCopiedPackage.getProcesses()
                    .removeAll(newCopiedPackage.getProcesses());
        }

        if (newCopiedPackage.getArtifacts() != null) {
            newCopiedPackage.getArtifacts()
                    .removeAll(newCopiedPackage.getArtifacts());
        }

        if (newCopiedPackage.getAssociations() != null) {
            newCopiedPackage.getAssociations()
                    .removeAll(newCopiedPackage.getAssociations());
        }

        /* remove external package reference */

        if (newCopiedPackage.getExternalPackages() != null) {
            newCopiedPackage.getExternalPackages()
                    .removeAll(newCopiedPackage.getExternalPackages());
        }

        Object otherElement =
                Xpdl2ModelUtil.getOtherElement(newCopiedPackage,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ProcessInterfaces());

        if (otherElement != null) {
            /* remove interfaces */
            newCopiedPackage.eUnset(XpdExtensionPackage.eINSTANCE
                    .getDocumentRoot_ProcessInterfaces());
        }

        /*
         * Finally create a Redefinable header if not present , else set its
         * properties(author , version) appropriately
         */
        setCreateRedefinableHeader(newCopiedPackage);

        return newCopiedPackage;
    }

    /**
     * Creates a new Redefinable header if not already present. If there already
     * is a Redefinable header present then checks if its author name and
     * version is set, if not then sets it appropriately.
     * 
     * @param newCopiedPackage
     */
    private void setCreateRedefinableHeader(Package newCopiedPackage) {

        String userName = System.getProperty("user.name"); //$NON-NLS-1$
        RedefinableHeader header = newCopiedPackage.getRedefinableHeader();

        if (header == null) {

            RedefinableHeader redefinableHeader =
                    Xpdl2Factory.eINSTANCE.createRedefinableHeader();
            redefinableHeader.setAuthor(userName);
            redefinableHeader.setVersion(packageVersion);
            newCopiedPackage.setRedefinableHeader(redefinableHeader);
        } else {

            String author = header.getAuthor();
            if (author == null || author.isEmpty()) {
                header.setAuthor(userName);
            }

            String version = header.getVersion();
            if (version == null || version.isEmpty()) {
                header.setVersion(packageVersion);
            }
        }
    }

    /**
     * Helper class which helps in fixing references which might break while
     * process move. Also adds package reference if needed.
     * 
     * 
     * @author kthombar
     * @since 01-Apr-2014
     */
    private static class FixReferencesHelper {

        /**
         * fixes the references which might be broken during the process of
         * separating processes to different packages.
         * 
         * @param packages
         *            the packages whose content(process/activity) references
         *            are to be fixed.
         * @param fixReferencesMap
         *            map of EObject name to the data wrapper which contains all
         *            information to fix references.
         */
        public static void fixReferences(Set<Package> packages,
                Map<String, FixReferencesWrapper> fixReferencesMap) {

            for (Package eachPackage : packages) {

                EList<Process> processes = eachPackage.getProcesses();

                if (!processes.isEmpty()) {
                    /* we have only one process per package */
                    Process process = processes.get(0);

                    Collection<Activity> allActivitiesInProc =
                            Xpdl2ModelUtil.getAllActivitiesInProc(process);

                    for (Activity eachActivity : allActivitiesInProc) {

                        Implementation impl = eachActivity.getImplementation();

                        if (impl instanceof SubFlow) {

                            fixReusableSubProcReferences(process,
                                    eachPackage,
                                    eachActivity,
                                    fixReferencesMap);

                        } else {
                            // Fix other broken references(that might arise in
                            // future)
                            // over here.
                        }
                    }

                    fixProcessImplementingInterfaceReferences(process,
                            eachPackage,
                            fixReferencesMap);

                }
            }
        }

        /**
         * Fixes the references when a process implements a process interface.
         * Fixes the process references as well as the activity which implement
         * the process interface activities references.
         * 
         * @param process
         *            process whose references are to be fixed
         * @param newPackage
         *            the new package created after separation
         * @param fixReferencesMap
         *            map of EObject name to the data wrapper which contains all
         *            information to fix references.
         */
        private static void fixProcessImplementingInterfaceReferences(
                Process process, Package newPackage,
                Map<String, FixReferencesWrapper> fixReferencesMap) {

            Object otherElement =
                    Xpdl2ModelUtil.getOtherElement(process,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ImplementedInterface());

            if (otherElement instanceof ImplementedInterface) {
                /* go ahead only if the process implements an interface */
                ImplementedInterface implementedIfc =
                        (ImplementedInterface) otherElement;

                String processInterfaceId =
                        implementedIfc.getProcessInterfaceId();

                if (processInterfaceId != null && !processInterfaceId.isEmpty()) {

                    /* get the wrapper from the interface id passed. */
                    FixReferencesWrapper referenceWrapper =
                            getReferenceWrapper(processInterfaceId,
                                    fixReferencesMap);

                    if (referenceWrapper != null) {
                        implementedIfc.setProcessInterfaceId(referenceWrapper
                                .getProcessId());
                        /*
                         * form the package name (package name is the same as
                         * process name)
                         */
                        String packageName = referenceWrapper.getProcessName();

                        implementedIfc.setPackageRef(packageName);

                        /*
                         * Update the activity references if the activity is
                         * inherited from the process interface
                         */
                        IpmImportUtil
                                .updateImplementedActivityRefernces(process,
                                        referenceWrapper
                                                .getProcessOrInterface());

                        EList<ExternalPackage> externalPackages =
                                newPackage.getExternalPackages();

                        /*
                         * Checks if the package reference is already present.
                         * If not then add package external reference.
                         */
                        boolean isPackageRefAlreadyPresent = false;

                        for (ExternalPackage ePackageRef : externalPackages) {
                            if (ePackageRef.getHref().equals(packageName)) {
                                isPackageRefAlreadyPresent = true;
                                break;
                            }
                        }

                        if (!isPackageRefAlreadyPresent) {
                            /* Add package reference */
                            ExternalPackage ep =
                                    createPackageExternalReference(packageName);

                            externalPackages.add(ep);
                        }
                    } else {
                        /*
                         * XPD-6426:If the wrapper does not have the id then it
                         * might be the case that the id is picked from an
                         * already existing process/interface in workspace.
                         * Hence in this case we just get the package that the
                         * implementation referenced and add an external package
                         * reference to that package.
                         */
                        String packageRefId = implementedIfc.getPackageRef();
                        if (packageRefId != null && !packageRefId.isEmpty()) {
                            EList<ExternalPackage> externalPackages =
                                    newPackage.getExternalPackages();
                            ExternalPackage ep =
                                    createPackageExternalReference(packageRefId);

                            externalPackages.add(ep);
                        }
                    }
                }
            }
        }

        /**
         * fixes the Reusable sub process subflow reference to process or
         * interface. Also adds the external package referenced by the subflow.
         * 
         * @param newProcess
         * @param newPackage
         * @param eachActivity
         * @param fixReferencesMap
         */
        private static void fixReusableSubProcReferences(Process newProcess,
                Package newPackage, Activity eachActivity,
                Map<String, FixReferencesWrapper> fixReferencesMap) {
            Implementation impl = eachActivity.getImplementation();

            if (impl instanceof SubFlow) {

                SubFlow subFlow = (SubFlow) impl;
                String processId = subFlow.getProcessId();
                // Safe to call invoke methods on objects already checked for
                // null
                if (processId != null && !processId.isEmpty()
                        && !processId.equals(newProcess.getId())) {

                    FixReferencesWrapper referenceWrapper =
                            getReferenceWrapper(processId, fixReferencesMap);

                    if (referenceWrapper != null) {

                        subFlow.setProcessId(referenceWrapper.getProcessId());
                        /*
                         * form the package name (package name is the same as
                         * process name)
                         */
                        String packageName = referenceWrapper.getProcessName();

                        subFlow.setPackageRefId(packageName);

                        EList<ExternalPackage> externalPackages =
                                newPackage.getExternalPackages();

                        /*
                         * Checks if the package reference is already present.
                         * If not then add package external reference.
                         */
                        boolean isPackageRefAlreadyPresent = false;

                        for (ExternalPackage ePackageRef : externalPackages) {
                            if (ePackageRef.getHref().equals(packageName)) {
                                isPackageRefAlreadyPresent = true;
                                break;
                            }
                        }

                        if (!isPackageRefAlreadyPresent) {
                            /* Add package reference */
                            ExternalPackage ep =
                                    createPackageExternalReference(packageName);

                            externalPackages.add(ep);
                        }
                    } else {
                        /*
                         * XPD-6426:If the wrapper does not have the id then it
                         * might be the case that the id is picked from an
                         * already existing process/interface in workspace.
                         * Hence in this case we just get the package that the
                         * subflow referenced and add an external package
                         * reference to that package.
                         */
                        String packageRefId = subFlow.getPackageRefId();
                        if (packageRefId != null && !packageRefId.isEmpty()) {
                            EList<ExternalPackage> externalPackages =
                                    newPackage.getExternalPackages();
                            ExternalPackage ep =
                                    createPackageExternalReference(packageRefId);

                            externalPackages.add(ep);
                        }
                    }
                }
            }
        }

        /**
         * returns the {@link FixReferencesWrapper} object based on the process
         * id passed.
         * 
         * @param processId
         *            the id to fetch in the wrapper
         * @param fixReferencesMap
         *            map of process name to reference wrapper
         * @return
         */
        private static FixReferencesWrapper getReferenceWrapper(
                String processId,
                Map<String, FixReferencesWrapper> fixReferencesMap) {
            /* get all values */
            Collection<FixReferencesWrapper> values = fixReferencesMap.values();

            for (FixReferencesWrapper eachWrapper : values) {
                /*
                 * check if the process id matches with any of the id stored in
                 * the wrapper
                 */
                if (eachWrapper.getAllIdsWithSameProcName().contains(processId)) {
                    return eachWrapper;
                }
            }
            return null;
        }

        /**
         * Creates External Package references to the passed package Name
         * 
         * @param packageName
         *            the name of the package to which external reference is to
         *            be created.
         * @return the {@link ExternalPackage} created.
         */
        private static ExternalPackage createPackageExternalReference(
                String packageName) {
            ExternalPackage externalPackage =
                    Xpdl2Factory.eINSTANCE.createExternalPackage();
            externalPackage.setHref(packageName);

            ExtendedAttribute attrib =
                    Xpdl2Factory.eINSTANCE.createExtendedAttribute();
            attrib.setName("location"); //$NON-NLS-1$
            attrib.setValue("/" + packageName + Xpdl2ResourcesConsts.DEFAULT_PACKAGE_FILENAME_EXT); //$NON-NLS-1$

            externalPackage.getExtendedAttributes().add(attrib);
            return externalPackage;
        }
    }

    /**
     * 
     * Data wrapper class which wraps up all data necessary for fixing reusable
     * sub process references.
     * <p>
     * The wrapper stores all the processes / interfaces id's which have same
     * name together in {@link FixReferencesWrapper#allIdsWithSameProcName} and
     * store the process/interface id and name which will be used to fix
     * references in {@link FixReferencesWrapper#processId} and
     * {@link FixReferencesWrapper#processOrIfcName} respectively.
     * 
     * @author kthombar
     * @since 14-Apr-2014
     */
    private static class FixReferencesWrapper {

        private String processOrIfcName;

        private String processId;

        private EObject processOrInterface;

        private Set<String> allIdsWithSameProcName;

        public FixReferencesWrapper(String processName, String processId,
                EObject processOrInterface) {
            this.processOrIfcName = processName;
            this.processId = processId;
            this.processOrInterface = processOrInterface;
            allIdsWithSameProcName = new HashSet<String>();
            allIdsWithSameProcName.add(processId);
        }

        /**
         * @return the processOrIfcName
         */
        public String getProcessName() {
            return processOrIfcName;
        }

        /**
         * @return the processId
         */
        public String getProcessId() {
            return processId;
        }

        public EObject getProcessOrInterface() {
            return processOrInterface;
        }

        /**
         * @return the allIdsWithSameProcName
         */
        public Set<String> getAllIdsWithSameProcName() {
            return allIdsWithSameProcName;
        }

        /**
         * @param allIdsWithSameProcName
         *            the allIdsWithSameProcName to set
         */
        public void setAllIdsWithSameProcName(String id) {
            this.allIdsWithSameProcName.add(id);
        }
    }
}
