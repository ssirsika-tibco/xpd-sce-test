/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.resources;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.osgi.framework.Version;

import com.tibco.xpd.analyst.resources.xpdl2.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectStatus;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.refactoring.IProjectRefactorChange;
import com.tibco.xpd.resources.refactoring.ProjectVersionRefactorCommandChange;
import com.tibco.xpd.resources.refactoring.ProjectVersionRefactoringArguments;
import com.tibco.xpd.resources.refactoring.ProjectVersionRefactoringParticipant;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.PublicationStatusType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author bharge
 * 
 */
public class TaskLibraryRefactorParticipant extends
        ProjectVersionRefactoringParticipant {

    private final List<IFile> files;

    public TaskLibraryRefactorParticipant() {
        files = new ArrayList<IFile>();
    }

    @Override
    public RefactoringStatus checkConditions(IProgressMonitor pm,
            CheckConditionsContext context) throws OperationCanceledException {
        RefactoringStatus status = new RefactoringStatus();

        IProject project = getChangeArgument().getProject();

        ProjectConfig config = XpdResourcesPlugin.getDefault()
                .getProjectConfig(project);
        if (config != null) {
            EList<SpecialFolder> folders = config
                    .getSpecialFolders()
                    .getFoldersOfKind(
                            Xpdl2ResourcesConsts.TASK_LIBRARY_SPECIAL_FOLDER_KIND);
            if (null != folders && !folders.isEmpty()) {
                for (SpecialFolder folder : folders) {
                    IFolder resFolder = folder.getFolder();
                    if (null != resFolder && resFolder.isAccessible()) {
                        try {
                            resFolder.accept(new IResourceProxyVisitor() {
                                public boolean visit(IResourceProxy proxy)
                                        throws CoreException {
                                    if (proxy.getType() == IResource.FILE) {
                                        if (proxy
                                                .getName()
                                                .endsWith(
                                                        "."     + Xpdl2ResourcesConsts.TASKS_EXTENSION)) { //$NON-NLS-1$
                                            IResource resource = proxy
                                                    .requestResource();
                                            if (resource != null
                                                    && resource.exists()) {
                                                files.add((IFile) resource);
                                            }
                                        }

                                        return false;
                                    }
                                    return true;
                                }
                            }, 0);
                        } catch (CoreException e) {
                            IStatus exceptionStatus = e.getStatus();
                            if (exceptionStatus != null) {
                                switch (exceptionStatus.getSeverity()) {
                                case IStatus.ERROR:
                                    status.addError(exceptionStatus
                                            .getMessage());
                                    break;
                                case IStatus.WARNING:
                                    status.addWarning(exceptionStatus
                                            .getMessage());
                                    break;
                                default:
                                    status
                                            .addInfo(exceptionStatus
                                                    .getMessage());
                                    break;
                                }
                            } else {
                                status.addError(e.getLocalizedMessage());
                            }
                        }
                    }
                }
            }

        } else {
            status
                    .addFatalError(String
                            .format(
                                    Messages.PackageVersionRefactorParticipant_projectConfigNotFound_error_shortdesc,
                                    project.getName()));
        }

        return status;
    }

    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {
        List<Change> changes = new ArrayList<Change>();

        if (files != null && !files.isEmpty()) {
            TransactionalEditingDomain ed = XpdResourcesPlugin.getDefault()
                    .getEditingDomain();
            for (IFile file : files) {
                // create innerChanges list to hold the changes in TaskLibrary
                // Version, Status and Destination(s). Create a
                // CompositeChange for a TaskLibrary with this
                // innerChanges list. Add this innerChanges list to the changes
                // list and return the final CompositeChange
                List<Change> innerChanges = new ArrayList<Change>();
                List<Process> processes = new ArrayList<Process>();

                WorkingCopy wc = XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(file);
                if (wc != null) {
                    int compare = compareVersion(ed, wc);
                    if (compare != 0) {
                        innerChanges
                                .add(new TaskLibraryPackageVersionChange(
                                        ed,
                                        wc,
                                        compare > 0 ? "Task Library Version" // "Task Library Version and Status" wc.getName() //$NON-NLS-1$
                                                : String
                                                        .format(
                                                                Messages.PackageVersionRefactorParticipant_versionDowngrade_message,
                                                                wc.getName()),
                                        getChangeArgument(),
                                        IProjectRefactorChange.REFACTOR_VERSION)); // |
                                                                                   // IProjectRefactorChange
                                                                                   // .
                                                                                   // REFACTOR_STATUS
                    }

                    compare = compareStatus(ed, wc);
                    if (compare != 0) {
                        innerChanges
                                .add(new TaskLibraryPackageStatusChange(
                                        ed,
                                        wc,
                                        compare > 0 ? "Task Library Status" // "Task Library Version and Status" wc.getName() //$NON-NLS-1$
                                                : String
                                                        .format(
                                                                Messages.PackageVersionRefactorParticipant_versionDowngrade_message,
                                                                wc.getName()),
                                        getChangeArgument(),
                                        IProjectRefactorChange.REFACTOR_STATUS));
                    }

                    // if a task library has processes then add process
                    // destinations to the innerChanges list
                    EObject rootElement = wc.getRootElement();
                    if (rootElement instanceof Package) {
                        Package package1 = (Package) rootElement;
                        if (package1.getProcesses().size() > 0) {
                            processes.addAll(package1.getProcesses());
                            for (Process process : processes) {

                                int diff = compareProcessDestinations(ed, wc,
                                        process);
                                if (diff != 0) {
                                    innerChanges
                                            .add(new TaskLibraryProcessDestinationsChange(
                                                    ed,
                                                    wc,
                                                    diff > 0 ? Xpdl2ModelUtil
                                                            .getDisplayName(process)
                                                            + " (Process)" //$NON-NLS-1$
                                                            : String
                                                                    .format(
                                                                            Messages.PackageVersionRefactorParticipant_versionDowngrade_message,
                                                                            Xpdl2ModelUtil
                                                                                    .getDisplayName(process)),
                                                    getChangeArgument(),
                                                    IProjectRefactorChange.REFACTOR_DESTINATIONS,
                                                    process));
                                }
                            }
                        }
                        if (!innerChanges.isEmpty()) {
                            changes.add(new CompositeChange(wc.getName(),
                                    innerChanges
                                            .toArray(new Change[innerChanges
                                                    .size()])));
                        }
                    }
                }
            }
        }
        if (!changes.isEmpty()) {
            return new CompositeChange(
                    Messages.TaskLibraryRefactorParticipant_ChangesToTaskLibrary_shortdesc,
                    changes.toArray(new Change[changes.size()]));
        }

        return new NullChange(
                Messages.TaskLibraryRefactorParticipant_NoTaskLibraryAffected_shortdesc);
    }

    /**
     * Compare the project version to the task library version.
     * 
     * @param ed
     * @param wc
     * @return A negative integer, zero, or a positive integer if the package
     *         version is less than, equal to, or greater than the project
     *         version.
     */
    private int compareVersion(TransactionalEditingDomain ed, WorkingCopy wc) {
        EObject element = wc.getRootElement();
        Integer result = 0;
        ProjectVersionRefactoringArguments args = getChangeArgument();

        if (null != args && element instanceof Package) {
            try {
                if (null != args.getVersion()) {
                    result = TransactionUtil.runExclusive(ed,
                            new CheckTaskLibraryPackageVersion(wc, Version
                                    .parseVersion(args.getVersion())));
                }
                return result;
            } catch (IllegalArgumentException e) {
                // As version is not a valid OSGI version format then return
                // result to indicate that version has changed
            } catch (InterruptedException e) {
                // Do nothing
            }

        }

        return 1;
    }

    /**
     * Compare the project status to the process package's status.
     * 
     * @param ed
     * @param wc
     * @return A negative integer, zero, or a positive integer if the package
     *         version is less than, equal to, or greater than the project
     *         version.
     */
    private int compareStatus(TransactionalEditingDomain ed, WorkingCopy wc) {
        EObject element = wc.getRootElement();
        ProjectVersionRefactoringArguments args = getChangeArgument();
        Integer result = 0;

        if (element instanceof Package && null != args) {
            try {
                if (null != args.getStatus()) {
                    ProjectStatus pStatus = args.getStatus();

                    result = TransactionUtil.runExclusive(ed,
                            new CheckTaskLibraryPackageStatus(wc, pStatus
                                    .name()));

                    return result;
                }

                return result;
            } catch (IllegalArgumentException e) {
                // As version is not a valid OSGI version format then return
                // result to indicate that version has changed
            } catch (InterruptedException e) {
                // Do nothing
            }
        }
        return 1;
    }

    /**
     * Compare the project destinations to the task library's process
     * destinations.
     * 
     * @param ed
     * @param wc
     * @return A negative integer, zero, or a positive integer if the process
     *         destination is less than, equal to, or greater than the project
     *         destination.
     */
    private int compareProcessDestinations(TransactionalEditingDomain ed,
            WorkingCopy wc, Process process) {
        ProjectVersionRefactoringArguments args = getChangeArgument();
        if (null != args && null != process) {
            try {
                String[] destinations = args.getDestinations();
                for (String dest : destinations) {
                    Integer result = TransactionUtil.runExclusive(ed,
                            new CheckTaskLibraryProcessDestinations(wc, dest,
                                    process));
                    return result;
                }
            } catch (IllegalArgumentException e) {
                // As version is not a valid OSGI version format then return
                // result to indicate that version has changed
            } catch (InterruptedException e) {
                // Do nothing
            }
        }
        return 1;
    }

    /**
     * Compare the task library package version to the project version. Returns
     * an int (compare result) as the result.
     * 
     * @author bharge
     * 
     */
    private class CheckTaskLibraryPackageVersion implements
            RunnableWithResult<Integer> {

        private int result = 1;

        private final WorkingCopy wc;

        private final Version version;

        public CheckTaskLibraryPackageVersion(WorkingCopy wc, Version version) {
            this.wc = wc;
            this.version = version;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.transaction.RunnableWithResult#getResult()
         */
        public Integer getResult() {
            return result;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.transaction.RunnableWithResult#getStatus()
         */
        public IStatus getStatus() {
            return Status.OK_STATUS;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.emf.transaction.RunnableWithResult#setStatus(org.eclipse
         * .core.runtime.IStatus)
         */
        public void setStatus(IStatus status) {

        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Runnable#run()
         */
        public void run() {
            try {
                EObject element = wc.getRootElement();
                if (element instanceof Package
                        && ((Package) element).getRedefinableHeader()
                                .getVersion() != null) {
                    Version packageVersion = Version
                            .parseVersion(((Package) element)
                                    .getRedefinableHeader().getVersion());
                    result = version.compareTo(packageVersion);
                }
            } catch (IllegalArgumentException e) {
                // Do nothing, as the version is not a valid OSGI version format
                // return a result to indicate change of version to be safe
            }
        }

    }

    /**
     * Task Library participants change object to update version of the Task
     * Libray.
     * 
     * @author bharge
     * 
     */
    private class TaskLibraryPackageVersionChange extends
            ProjectVersionRefactorCommandChange {

        private Package pckg;

        public TaskLibraryPackageVersionChange(EditingDomain editingDomain,
                WorkingCopy workingCopy, String label,
                ProjectVersionRefactoringArguments changeArguments,
                int refactorHint) {
            super(editingDomain, workingCopy, label, changeArguments,
                    refactorHint); // refactorHint
        }

        @Override
        protected Command getRefactorCommand() {
            CompoundCommand cCmd = new CompoundCommand();
            String version = getChangeArguments().getVersion();

            cCmd.append(SetCommand.create(getEditingDomain(), getPackage()
                    .getRedefinableHeader(), Xpdl2Package.eINSTANCE
                    .getRedefinableHeader_Version(), version));

            return cCmd;
        }

        @Override
        protected Object getCurrentValue(int type) {
            try {
                Package pckg = getPackage();
                if (isValid(new NullProgressMonitor()).isOK() && pckg != null) {
                    switch (type) {
                    case REFACTOR_VERSION:
                        return pckg.getRedefinableHeader().getVersion();
                    }
                }
            } catch (OperationCanceledException e) {
                // Do nothing
            } catch (CoreException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public Object getModifiedElement() {
            return getPackage();
        }

        @Override
        public RefactoringStatus isValid(IProgressMonitor pm)
                throws CoreException, OperationCanceledException {
            RefactoringStatus status = super.isValid(pm);

            if (status.isOK()) {
                pm
                        .beginTask(
                                Messages.PackageVersionRefactorParticipant_validatingPackage_progress_shortdesc,
                                1);
                Package pckg = getPackage();
                if (pckg == null) {
                    status
                            .addError(String
                                    .format(
                                            Messages.PackageVersionRefactorParticipant_NoPackageContainer_error_shortdesc,
                                            getWorkingCopy().getName()));
                }
                pm.worked(1);
            }
            return status;
        }

        /**
         * Get the base Task Library Package of the model being updated by this
         * change.
         * 
         * @return
         */
        private Package getPackage() {
            if (pckg == null) {
                WorkingCopy wc = getWorkingCopy();
                EObject root = wc.getRootElement();
                if (root instanceof Package) {
                    pckg = (Package) root;
                }
            }
            return pckg;
        }

    }

    /**
     * Compare the task library package status to the project status. Returns an
     * int (compare result) as the result.
     * 
     * @author bharge
     * 
     */
    private class CheckTaskLibraryPackageStatus implements
            RunnableWithResult<Integer> {
        private int result = 1;

        private final WorkingCopy wc;

        private final String projectStatus;

        public CheckTaskLibraryPackageStatus(WorkingCopy wc,
                String projectStatus) {
            this.wc = wc;
            this.projectStatus = projectStatus;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.transaction.RunnableWithResult#getResult()
         */
        public Integer getResult() {
            return result;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.transaction.RunnableWithResult#getStatus()
         */
        public IStatus getStatus() {
            return Status.OK_STATUS;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.emf.transaction.RunnableWithResult#setStatus(org.eclipse
         * .core.runtime.IStatus)
         */
        public void setStatus(IStatus status) {

        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Runnable#run()
         */
        public void run() {
            try {
                EObject element = wc.getRootElement();
                if (element instanceof Package
                        && ((Package) element).getRedefinableHeader()
                                .getPublicationStatus() != null) {

                    String publicationStatus = ((Package) element)
                            .getRedefinableHeader().getPublicationStatus()
                            .getName();
                    result = projectStatus.compareTo(publicationStatus);

                }
            } catch (IllegalArgumentException e) {
                // Do nothing, as the version is not a valid OSGI version format
                // return a result to indicate change of version to be safe
            }
        }
    }

    /**
     * Task Library participants change object to update status of the Task
     * Libray.
     * 
     * @author bharge
     * 
     */
    private class TaskLibraryPackageStatusChange extends
            ProjectVersionRefactorCommandChange {

        private Package pckg;

        public TaskLibraryPackageStatusChange(EditingDomain editingDomain,
                WorkingCopy workingCopy, String label,
                ProjectVersionRefactoringArguments changeArguments,
                int refactorHint) {
            super(editingDomain, workingCopy, label, changeArguments,
                    refactorHint); // refactorHint
        }

        @Override
        protected Command getRefactorCommand() {
            CompoundCommand cCmd = new CompoundCommand();

            ProjectStatus pStatus = getChangeArguments().getStatus();
            PublicationStatusType publicationStatusType = PublicationStatusType
                    .get(pStatus.name());

            cCmd.append(SetCommand.create(getEditingDomain(), getPackage()
                    .getRedefinableHeader(), Xpdl2Package.eINSTANCE
                    .getRedefinableHeader_PublicationStatus(),
                    publicationStatusType));

            return cCmd;
        }

        @Override
        protected Object getCurrentValue(int type) {
            try {
                Package pckg = getPackage();
                if (isValid(new NullProgressMonitor()).isOK() && pckg != null) {
                    switch (type) {
                    case REFACTOR_STATUS:
                        return pckg.getRedefinableHeader()
                                .getPublicationStatus().getName();
                    }
                }
            } catch (OperationCanceledException e) {
                // Do nothing
            } catch (CoreException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public Object getModifiedElement() {
            return getPackage();
        }

        @Override
        public RefactoringStatus isValid(IProgressMonitor pm)
                throws CoreException, OperationCanceledException {
            RefactoringStatus status = super.isValid(pm);

            if (status.isOK()) {
                pm
                        .beginTask(
                                Messages.PackageVersionRefactorParticipant_validatingPackage_progress_shortdesc,
                                1);
                Package pckg = getPackage();
                if (pckg == null) {
                    status
                            .addError(String
                                    .format(
                                            Messages.PackageVersionRefactorParticipant_NoPackageContainer_error_shortdesc,
                                            getWorkingCopy().getName()));
                }
                pm.worked(1);
            }
            return status;
        }

        /**
         * Get the base Task Library Package of the model being updated by this
         * change.
         * 
         * @return
         */
        private Package getPackage() {
            if (pckg == null) {
                WorkingCopy wc = getWorkingCopy();
                EObject root = wc.getRootElement();
                if (root instanceof Package) {
                    pckg = (Package) root;
                }
            }
            return pckg;
        }

    }

    /**
     * Compare the task library process destination to the project destination.
     * Returns an int (compare result) as the result.
     * 
     * @author bharge
     * 
     */
    private class CheckTaskLibraryProcessDestinations implements
            RunnableWithResult<Integer> {
        /** The destination environment xpdl2 extended attribute name. */
        public static final String DESTINATION_ATTRIBUTE = "Destination"; //$NON-NLS-1$

        private int result = 1;

        private final String destination;

        private final Process process;

        public CheckTaskLibraryProcessDestinations(WorkingCopy wc,
                String destination, Process process) {
            this.destination = destination;
            this.process = process;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.transaction.RunnableWithResult#getResult()
         */
        public Integer getResult() {
            return result;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.transaction.RunnableWithResult#getStatus()
         */
        public IStatus getStatus() {
            return Status.OK_STATUS;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.emf.transaction.RunnableWithResult#setStatus(org.eclipse
         * .core.runtime.IStatus)
         */
        public void setStatus(IStatus status) {

        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Runnable#run()
         */
        public void run() {
            try {
                if (null != process) {
                    EList<ExtendedAttribute> extendedAttributes = process
                            .getExtendedAttributes();

                    for (ExtendedAttribute extendedAttribute : extendedAttributes) {
                        if (DESTINATION_ATTRIBUTE.equals(extendedAttribute
                                .getName())) {
                            result = destination.compareTo(extendedAttribute
                                    .getValue());
                        }
                    }
                }
            } catch (IllegalArgumentException e) {
                // Do nothing, as the version is not a valid OSGI version format
                // return a result to indicate change of version to be safe
            }
        }
    }

    /**
     * Task Library participants change object to update the destinations of a
     * process in a task library.
     * 
     * @author bharge
     * 
     */
    private class TaskLibraryProcessDestinationsChange extends
            ProjectVersionRefactorCommandChange {
        /** The destination environment xpdl2 extended attribute name. */
        public static final String DESTINATION_ATTRIBUTE = "Destination"; //$NON-NLS-1$

        private Process process;

        public TaskLibraryProcessDestinationsChange(
                EditingDomain editingDomain, WorkingCopy workingCopy,
                String label,
                ProjectVersionRefactoringArguments changeArguments,
                int refactorHint, Process process) {
            super(editingDomain, workingCopy, label, changeArguments,
                    refactorHint); // refactorHint
            this.process = process;
        }

        @Override
        protected Command getRefactorCommand() {
            CompoundCommand cCmd = new CompoundCommand();
            String[] destinations = getChangeArguments().getDestinations();
            Process process = getProcess();

            if (null != process) {
                for (String dest : destinations) {
                    ExtendedAttribute att = Xpdl2Factory.eINSTANCE
                            .createExtendedAttribute();
                    att.setName(DESTINATION_ATTRIBUTE);
                    att.setValue(dest);
                    cCmd
                            .append(AddCommand.create(getEditingDomain(),
                                    process, Xpdl2Package.eINSTANCE
                                            .getExtendedAttribute(), att));
                }
            }

            return cCmd;
        }

        @Override
        protected Object getCurrentValue(int type) {
            try {
                Process proc = getProcess();
                if (isValid(new NullProgressMonitor()).isOK() && null != proc) {
                    switch (type) {
                    case REFACTOR_DESTINATIONS:
                        EList<ExtendedAttribute> extendedAttributes = proc
                                .getExtendedAttributes();
                        for (ExtendedAttribute extendedAttribute : extendedAttributes) {
                            if (DESTINATION_ATTRIBUTE.equals(extendedAttribute
                                    .getName())) {
                                return extendedAttribute.getValue();
                            }
                        }
                    }
                }

            } catch (OperationCanceledException e) {
                // Do nothing
            } catch (CoreException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public Object getModifiedElement() {
            return getProcess();
        }

        @Override
        public RefactoringStatus isValid(IProgressMonitor pm)
                throws CoreException, OperationCanceledException {
            RefactoringStatus status = super.isValid(pm);

            if (status.isOK()) {
                pm
                        .beginTask(
                                Messages.ProcessDestinationRefactorParticipant_validatingProcess_progress_shortdesc,
                                1);
                Process process = getProcess();
                if (process == null) {
                    status
                            .addError(String
                                    .format(
                                            Messages.ProcessDestinationRefactorParticipant_NoProcessContainer_error_shortdesc,
                                            getWorkingCopy().getName()));
                }
                pm.worked(1);
            }
            return status;
        }

        /**
         * Get the base Process of the model being updated by this change.
         * 
         * @return
         */
        private Process getProcess() {
            return process;
        }
    }
}
