/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.internal;

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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
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

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.om.resources.OMResourcesActivator;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.refactoring.ProjectVersionRefactorCommandChange;
import com.tibco.xpd.resources.refactoring.ProjectVersionRefactoringArguments;
import com.tibco.xpd.resources.refactoring.ProjectVersionRefactoringParticipant;

/**
 * The project version refactor participant that will update the Organization
 * Models' version.
 * 
 * @author njpatel
 * 
 */
public class OMVersionRefactorParticipant extends
        ProjectVersionRefactoringParticipant {
    
    private final List<IFile> files;

    /**
     * Version refactor for Organization Models.
     */
    public OMVersionRefactorParticipant() {
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
            EList<SpecialFolder> folders = config.getSpecialFolders()
                    .getFoldersOfKind(
                            OMResourcesActivator.OM_SPECIAL_FOLDER_KIND);
            if (folders != null && !folders.isEmpty()) {
                for (SpecialFolder folder : folders) {
                    IFolder resFolder = folder.getFolder();
                    if (resFolder != null && resFolder.isAccessible()) {
                        try {
                            resFolder.accept(new IResourceProxyVisitor() {
                                public boolean visit(IResourceProxy proxy)
                                        throws CoreException {
                                    if (proxy.getType() == IResource.FILE) {
                                        if (proxy.getName().endsWith(
                                                "." + OMUtil.OM_FILE_EXTENSION)) { //$NON-NLS-1$
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
                                    Messages.OMVersionRefactorParticipant_projectConfigNotFound_error_shortdesc,
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
                WorkingCopy wc = XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(file);
                int compare = compareVersion(ed, wc);
                if (wc != null && compare != 0) {
                    changes
                            .add(new OMChange(
                                    ed,
                                    wc,
                                    compare > 0 ? wc.getName()
                                            : String
                                                    .format(
                                                            Messages.OMVersionRefactorParticipant_versionDowngrade_message,
                                                            wc.getName()),
                                    getChangeArgument()));
                }
            }
        }

        if (!changes.isEmpty()) {
            return new CompositeChange(
                    Messages.OMVersionRefactorParticipant_ChangesToOrgModel_shortdesc,
                    changes.toArray(new Change[changes.size()]));
        }

        return new NullChange(
                Messages.OMVersionRefactorParticipant_NoOMAffected_shortdesc);
    }

    /**
     * Compare the project version to the model's version.
     * 
     * @param ed
     * @param wc
     * @return A negative integer, zero, or a positive integer if the model
     *         version is less than, equal to, or greater than the project
     *         version.
     */
    private int compareVersion(TransactionalEditingDomain ed, WorkingCopy wc) {
        EObject element = wc.getRootElement();
        ProjectVersionRefactoringArguments args = getChangeArgument();
        if (element instanceof OrgModel && args != null
                && args.getVersion() != null) {
            try {
                Integer result = TransactionUtil.runExclusive(ed,
                        new CheckModelVersion(wc, Version.parseVersion(args
                                .getVersion())));

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
     * OM particiapants change object to update the version of an Organization
     * Model.
     * 
     * @author njpatel
     * 
     */
    private class OMChange extends ProjectVersionRefactorCommandChange {

        private OrgModel orgModel;

        /**
         * OM change object that will refactor the version of the OM.
         * 
         * @param editingDomain
         * @param workingCopy
         * @param label
         *            change label
         * @param args
         *            refactor arguments
         * @param downgrade
         *            <code>true</code> if the version is being downgraded
         */
        public OMChange(EditingDomain editingDomain, WorkingCopy workingCopy,
                String label, ProjectVersionRefactoringArguments args) {
            super(editingDomain, workingCopy, label, args, REFACTOR_VERSION);
        }

        @Override
        protected Command getRefactorCommand() {
            String version = getChangeArguments().getVersion();

            return SetCommand.create(getEditingDomain(), getModel(),
                    OMPackage.eINSTANCE.getBaseOrgModel_Version(), version);
        }

        @Override
        protected Object getCurrentValue(int type) {
            try {
                OrgModel model = getModel();
                if (isValid(new NullProgressMonitor()).isOK() && model != null) {
                    switch (type) {
                    case REFACTOR_VERSION:
                        return model.getVersion();
                    }
                }
            } catch (OperationCanceledException e) {
                // Do nothing
            } catch (CoreException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public Object getModifiedElement() {
            return getModel();
        }

        @Override
        public RefactoringStatus isValid(IProgressMonitor pm)
                throws CoreException, OperationCanceledException {
            RefactoringStatus status = super.isValid(pm);

            if (status.isOK()) {
                pm
                        .beginTask(
                                Messages.OMVersionRefactorParticipant_validatingOrgModel_progress_shortdesc,
                                1);
                OrgModel model = getModel();
                if (model == null) {
                    status
                            .addError(String
                                    .format(
                                            Messages.OMVersionRefactorParticipant_NoOrgModelContainer_error_shortdesc,
                                            getWorkingCopy().getName()));
                }
                pm.worked(1);
            }
            return status;
        }

        /**
         * Get the base Organization Model of the model being updated by this
         * change.
         * 
         * @return
         */
        private OrgModel getModel() {
            if (orgModel == null) {
                WorkingCopy wc = getWorkingCopy();
                EObject root = wc.getRootElement();
                if (root instanceof OrgModel) {
                    orgModel = (OrgModel) root;
                }
            }
            return orgModel;
        }
    }

    /**
     * Compare the model version to the project version. Returns an int (compare
     * result) as the result.
     * 
     * @author njpatel
     * 
     */
    private class CheckModelVersion implements RunnableWithResult<Integer> {

        private int result = 1;
        private final WorkingCopy wc;
        private final Version version;

        public CheckModelVersion(WorkingCopy wc, Version version) {
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
                if (element instanceof OrgModel
                        && ((OrgModel) element).getVersion() != null) {
                    Version modelVersion = Version
                            .parseVersion(((OrgModel) element).getVersion());
                    result = version.compareTo(modelVersion);
                }
            } catch (IllegalArgumentException e) {
                // Do nothing, as the version is not a valid OSGI version format
                // return a result to indicate change of version to be safe
            }
        }
    }
}
