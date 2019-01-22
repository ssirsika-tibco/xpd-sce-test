/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.component;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.wm.pageflow.WMPageflowPlugin;
import com.tibco.xpd.wm.pageflow.internal.Messages;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * class to update the default business service category with the new project
 * name when the project is renamed
 * 
 * @author bharge
 * @since 3.3 (27 Aug 2010)
 */
public class RenameProjectContributorParticForBSCategory extends
        RenameParticipant {

    private String oldProjectName;

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    public RenameProjectContributorParticForBSCategory() {
    }

    @Override
    protected boolean initialize(Object element) {
        if (element instanceof IProject) {
            IProject project = (IProject) element;

            try {
                if (project.hasNature(XpdConsts.PROJECT_NATURE_ID)) {
                    List<IResource> xpdlFiles =
                            SpecialFolderUtil
                                    .getAllDeepResourcesInSpecialFolderOfKind(project,
                                            Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND,
                                            Xpdl2ResourcesConsts.XPDL_EXTENSION,
                                            false);

                    if (!xpdlFiles.isEmpty()) {
                        oldProjectName = project.getName();
                        return true;
                    }
                }
            } catch (CoreException e) {
            }
        }
        return false;
    }

    @Override
    public RefactoringStatus checkConditions(IProgressMonitor pm,
            CheckConditionsContext context) throws OperationCanceledException {
        try {
            pm.beginTask("", 1); //$NON-NLS-1$

            return RefactoringStatus.create(Status.OK_STATUS);
        } finally {
            pm.done();
        }

    }

    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {

        try {
            pm.beginTask("", 1); //$NON-NLS-1$

            /*
             * By the time command gets executed it will have been renamed, so
             * pass it the project that will be there.
             */
            return new RenameBSCategoryChange(ResourcesPlugin.getWorkspace()
                    .getRoot().getProject(getArguments().getNewName()),
                    oldProjectName, getArguments().getNewName());
        } finally {
            pm.done();
        }

    }

    @Override
    public String getName() {
        return Messages.RenameProjectContributorParticForBSCategory_RenameBusinessService_refactorname;
    }

    /*
     * return true if category is not set or is set to default
     */
    public boolean isDefaultCategory(Process process) {
        if (null != process) {
            String category =
                    (String) Xpdl2ModelUtil.getOtherAttribute(process,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_BusinessServiceCategory());
            if (null == category || category.trim().length() == 0) {
                return true;
            }
            if (null != category) {
                IProject projectFor = WorkingCopyUtil.getProjectFor(process);
                String defaultCategory =
                        Xpdl2ModelUtil
                                .getBusinessServiceDefaultCategory(projectFor
                                        .getName(), process.getPackage()
                                        .getName());
                if (category.equals(defaultCategory)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Refactor Change participant for default business service category with
     * the new project name when the project is renamed
     * 
     * @author aallway
     * @since 25 May 2012
     */
    private static class RenameBSCategoryChange extends Change {

        private IProject project;

        private String oldProjectName;

        private String newProjectName;

        /**
         * 
         * @param project
         * @param oldProjectName
         * @param newProjectName
         */
        public RenameBSCategoryChange(IProject project, String oldProjectName,
                String newProjectName) {
            super();
            this.project = project;
            this.oldProjectName = oldProjectName;
            this.newProjectName = newProjectName;
        }

        /**
         * @see org.eclipse.ltk.core.refactoring.Change#getName()
         * 
         * @return
         */
        @Override
        public String getName() {
            return Messages.RenameProjectContributorParticForBSCategory_RenameBusinessService_refactorname;
        }

        /**
         * @see org.eclipse.ltk.core.refactoring.Change#initializeValidationData(org.eclipse.core.runtime.IProgressMonitor)
         * 
         * @param pm
         */
        @Override
        public void initializeValidationData(IProgressMonitor pm) {
        }

        /**
         * @see org.eclipse.ltk.core.refactoring.Change#isValid(org.eclipse.core.runtime.IProgressMonitor)
         * 
         * @param pm
         * @return
         * @throws CoreException
         * @throws OperationCanceledException
         */
        @Override
        public RefactoringStatus isValid(IProgressMonitor pm)
                throws CoreException, OperationCanceledException {
            return RefactoringStatus.create(Status.OK_STATUS);
        }

        /**
         * @see org.eclipse.ltk.core.refactoring.Change#perform(org.eclipse.core.runtime.IProgressMonitor)
         * 
         * @param pm
         * @return
         * @throws CoreException
         */
        @Override
        public Change perform(IProgressMonitor pm) throws CoreException {

            Map<WorkingCopy, Set<Process>> filesToChange = getFileChangeMap();

            if (!filesToChange.isEmpty()) {
                /*
                 * Now create a transaction outside of the editing domain
                 * (because we don't want to make this refactoring change in the
                 * ed domain and make it part of IT's undo.
                 */
                Map<String, Object> attrs = new HashMap<String, Object>();
                attrs.put(Transaction.OPTION_UNPROTECTED, Boolean.TRUE);
                Transaction transaction = null;

                try {
                    transaction =
                            ((InternalTransactionalEditingDomain) XpdResourcesPlugin
                                    .getDefault().getEditingDomain())
                                    .startTransaction(false, attrs);

                    for (Entry<WorkingCopy, Set<Process>> entry : filesToChange
                            .entrySet()) {

                        WorkingCopy workingCopy = entry.getKey();
                        Set<Process> businessServicesToChange =
                                entry.getValue();

                        for (Process process : businessServicesToChange) {
                            String category =
                                    Xpdl2ModelUtil
                                            .getBusinessServiceDefaultCategory(newProjectName,
                                                    process.getPackage()
                                                            .getName());
                            Xpdl2ModelUtil
                                    .setOtherAttribute(process,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_BusinessServiceCategory(),
                                            category);
                        }

                        workingCopy.save();
                    }

                } catch (IOException e) {
                    throw new CoreException(
                            new Status(
                                    IStatus.ERROR,
                                    WMPageflowPlugin.PLUGIN_ID,
                                    Messages.RenameProjectContributorParticForBSCategory_IOException_message
                                            + e.getLocalizedMessage()));

                } catch (InterruptedException e1) {
                    throw new CoreException(
                            new Status(
                                    IStatus.ERROR,
                                    WMPageflowPlugin.PLUGIN_ID,
                                    Messages.RenameProjectContributorParticForBSCategory_InteruptedException_message));
                } finally {
                    if (transaction != null) {
                        try {
                            transaction.commit();
                        } catch (RollbackException e) {
                            throw new CoreException(
                                    new Status(
                                            IStatus.ERROR,
                                            WMPageflowPlugin.PLUGIN_ID,
                                            Messages.RenameProjectContributorParticForBSCategory_RolledBackException_message));
                        }
                    }
                }
            }

            /*
             * The undo command is exactly what we just did but swap the old and
             * new names around. Undo is run BEFORE the project is renamed back
             * again so we should get our files from the new project.
             */
            return new RenameBSCategoryChange(ResourcesPlugin.getWorkspace()
                    .getRoot().getProject(newProjectName), newProjectName,
                    oldProjectName);
        }

        /**
         * @return A map of xpdlfile -> business-service-process set whose
         *         category needs to be changed.
         */
        public Map<WorkingCopy, Set<Process>> getFileChangeMap() {

            Map<WorkingCopy, Set<Process>> filesToChange =
                    new HashMap<WorkingCopy, Set<Process>>();

            List<IResource> xpdlFiles =
                    SpecialFolderUtil
                            .getAllDeepResourcesInSpecialFolderOfKind(project,
                                    Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND,
                                    Xpdl2ResourcesConsts.XPDL_EXTENSION,
                                    false);

            for (IResource xpdlFile : xpdlFiles) {
                WorkingCopy workingCopy =
                        XpdResourcesPlugin.getDefault()
                                .getWorkingCopy(xpdlFile);

                if (workingCopy != null) {
                    if (workingCopy.getRootElement() instanceof Package) {
                        Package xpdlPackage =
                                (Package) workingCopy.getRootElement();

                        Set<Process> businessServicesToChange =
                                new HashSet<Process>();

                        List<Process> processes = xpdlPackage.getProcesses();

                        for (Process process : processes) {
                            if (Xpdl2ModelUtil
                                    .isPageflowBusinessService(process)) {

                                Set<String> globalDestinationIds =
                                        DestinationUtil
                                                .getEnabledGlobalDestinationTypes(process);
                                if (globalDestinationIds
                                        .contains(N2Utils.N2_GLOBAL_DESTINATION_ID)) {
                                    /**
                                     * if the category is not set or is set to
                                     * default only then modify on rename.
                                     * otherwise if the user has specified his
                                     * own category then do not modify
                                     */
                                    if (isDefaultCategory(process)) {
                                        businessServicesToChange.add(process);
                                    }
                                }
                            }
                        }

                        if (!businessServicesToChange.isEmpty()) {
                            filesToChange.put(workingCopy,
                                    businessServicesToChange);
                        }
                    }
                }
            }

            return filesToChange;
        }

        /**
         * 
         * @param process
         * @return <code>true</code> if category is not set or is set to default
         */
        private boolean isDefaultCategory(Process process) {
            if (null != process) {
                String category =
                        (String) Xpdl2ModelUtil
                                .getOtherAttribute(process,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_BusinessServiceCategory());
                if (null == category || category.trim().length() == 0) {
                    return true;
                }
                if (null != category) {
                    IProject projectFor =
                            WorkingCopyUtil.getProjectFor(process);
                    String defaultCategory =
                            Xpdl2ModelUtil
                                    .getBusinessServiceDefaultCategory(oldProjectName,
                                            process.getPackage().getName());
                    if (category.equals(defaultCategory)) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * @see org.eclipse.ltk.core.refactoring.Change#getModifiedElement()
         * 
         * @return
         */
        @Override
        public Object getModifiedElement() {
            return null;
        }

    }
}
