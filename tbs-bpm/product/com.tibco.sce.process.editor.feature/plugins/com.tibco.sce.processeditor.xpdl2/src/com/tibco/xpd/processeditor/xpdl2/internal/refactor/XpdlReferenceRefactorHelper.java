package com.tibco.xpd.processeditor.xpdl2.internal.refactor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.mapping.IResourceChangeDescriptionFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.ResourceChangeChecker;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.internal.refactor.change.IRefactorReferenceChangeProvider;
import com.tibco.xpd.processeditor.xpdl2.internal.refactor.change.UpdateReferenceChange;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * XPDL refactor helper. This contains common functionality required by the
 * rename and move refactor participants.
 * 
 * @author njpatel
 * @since 3.5.10
 */
public class XpdlReferenceRefactorHelper {

    /**
     * The refactor type.
     * 
     */
    protected enum RefactorType {
        BOM(BOMResourcesPlugin.BOM_FILE_EXTENSION,
                BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND), OM("om", "om"), //$NON-NLS-1$ //$NON-NLS-2$
        WSDL("wsdl", "wsdl"), XPDL(Xpdl2ResourcesConsts.XPDL_EXTENSION, //$NON-NLS-1$//$NON-NLS-2$
                Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND), FORM(
                "form", Xpdl2ResourcesConsts.FORMS_SPECIAL_FOLDER_KIND); //$NON-NLS-1$

        private final String fileExt;

        private final String specialFolderKind;

        RefactorType(String fileExt, String specialFolderKind) {
            this.fileExt = fileExt;
            this.specialFolderKind = specialFolderKind;
        }

        /**
         * @return the fileExt
         */
        public String getFileExt() {
            return fileExt;
        }

        /**
         * @return the specialFolderKind
         */
        public String getSpecialFolderKind() {
            return specialFolderKind;
        }

        /**
         * Get the type corresponding to the given file extension.
         * 
         * @param fileExt
         * @return
         */
        public static RefactorType getTypeFromFileExt(String fileExt) {

            if (fileExt != null && !fileExt.isEmpty()) {
                for (RefactorType type : RefactorType.values()) {
                    if (fileExt.equals(type.getFileExt())) {
                        return type;
                    }
                }
            }
            return null;
        }
    }

    /**
     * Get the Xpdl working copies of models that reference the given file.
     * 
     * @param file
     * @return
     */
    public Set<Xpdl2WorkingCopyImpl> getAffectedXpdls(IFile file) {
        Set<Xpdl2WorkingCopyImpl> wcs = new HashSet<Xpdl2WorkingCopyImpl>();

        Collection<IResource> resources = getAffectedResources(file);
        if (resources != null) {
            for (IResource res : resources) {
                if (res instanceof IFile
                        && getAffectedResourceFileExtension()
                                .equals(res.getFileExtension())) {
                    WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(res);
                    if (wc instanceof Xpdl2WorkingCopyImpl) {
                        wcs.add((Xpdl2WorkingCopyImpl) wc);
                    }
                }
            }
        }
        return wcs;
    }

    /**
     * Get the file extension of the potential resources affected from the
     * refactor.
     * 
     * @return The file extension of the potential resources affected from the
     *         refactor.
     */
    protected String getAffectedResourceFileExtension() {

        return Xpdl2ResourcesConsts.XPDL_EXTENSION;
    }

    /**
     * Get all the files from the given folder (and all subfolders) of the
     * target type given.
     * 
     * @param folder
     * @param type
     * @return
     * @throws CoreException
     */
    public List<IFile> getAllFiles(IFolder folder, RefactorType type)
            throws CoreException {
        final List<IFile> files = new ArrayList<IFile>();
        if (folder != null && type != null) {
            final String fileExt = type.getFileExt();

            folder.accept(new IResourceProxyVisitor() {
                @Override
                public boolean visit(IResourceProxy proxy) throws CoreException {
                    if (proxy.getType() == IResource.FILE) {
                        if (proxy.getName().endsWith("." + fileExt)) { //$NON-NLS-1$
                            IFile file = (IFile) proxy.requestResource();
                            if (file.exists()) {
                                files.add(file);
                            }
                        }
                        return false;
                    }
                    return true;
                }
            },
                    0);
        }

        return files;

    }

    /**
     * Get the resources that will be affected by the rename/move of the given
     * file.
     * 
     * @param file
     * @return
     */
    protected Collection<IResource> getAffectedResources(IFile file) {
        return WorkingCopyUtil.getAffectedResources(file);
    }

    /**
     * Check whether any affected working copies are in the dirty state. If they
     * are then the refactor cannot continue.
     * 
     * @see RefactoringParticipant#checkConditions(IProgressMonitor,
     *      CheckConditionsContext)
     * 
     * @param pm
     * @param context
     * @param affectedXpdls
     * @return
     * @throws OperationCanceledException
     */
    public RefactoringStatus checkWorkingCopyDirtyConditions(
            IProgressMonitor pm, CheckConditionsContext context,
            Collection<Xpdl2WorkingCopyImpl> affectedXpdls)
            throws OperationCanceledException {
        if (pm != null) {
            pm = SubProgressMonitorEx.createNestedSubProgressMonitor(pm, 1);
        } else {
            pm = new NullProgressMonitor();
        }

        ResourceChangeChecker checker =
                (ResourceChangeChecker) context
                        .getChecker(ResourceChangeChecker.class);
        IResourceChangeDescriptionFactory deltaFactory =
                checker.getDeltaFactory();

        /*
         * Check that none of the working copies being affected are in the dirty
         * state.
         */
        try {
            pm.beginTask(Messages.XpdlReferenceRefactorHelper_checkingWorkingCopy_progress_shortdesc,
                    affectedXpdls.size());
            for (Xpdl2WorkingCopyImpl wc : affectedXpdls) {
                List<IResource> resources = wc.getEclipseResources();
                if (!resources.isEmpty()) {
                    deltaFactory.change((IFile) resources.get(0));

                    if (wc.isWorkingCopyDirty()) {
                        return RefactoringStatus
                                .createFatalErrorStatus(String
                                        .format(Messages.XpdlReferenceRefactorHelper_workingCopyNeedsSaving_error_shortdesc,
                                                wc.getName()));
                    }
                }
                pm.worked(1);
            }
        } finally {
            pm.done();
        }

        return new RefactoringStatus();
    }

    /**
     * @param pm
     * @param affectedXpdls
     * @param oldFiles
     * @param newFiles
     * @param changeProvider
     * @return
     */
    public List<Change> createUpdateReferenceChanges(IProgressMonitor pm,
            Set<Xpdl2WorkingCopyImpl> affectedXpdls, IFile[] oldFiles,
            IFile[] newFiles, IRefactorReferenceChangeProvider changeProvider) {
        List<Change> changes = new ArrayList<Change>();

        /*
         * Create a change for each working copy that needs to be updated with
         * the new reference.
         */
        if (pm != null) {
            pm = SubProgressMonitorEx.createNestedSubProgressMonitor(pm, 1);
        } else {
            pm = new NullProgressMonitor();
        }

        pm.beginTask(Messages.XpdlReferenceRefactorHelper_creatingUpdateCommand_progress_shortdesc,
                affectedXpdls.size());
        for (Xpdl2WorkingCopyImpl wc : affectedXpdls) {
            if (wc.getRootElement() != null && !wc.getRootElement().eIsProxy()) {
                UpdateReferenceChange change =
                        changeProvider.createChange(wc, oldFiles, newFiles);
                if (change != null) {
                    changes.add(change);
                }
            }
            pm.worked(1);
        }
        pm.done();

        return changes;

    }

    /**
     * Check if the special folder relative paths of the two resources is
     * identical.
     * 
     * @param currentRes
     * @param refactoredRes
     * @return
     */
    public boolean isRelativePathsIdentical(IResource currentRes,
            IResource refactoredRes) {
        IPath currPath;
        IPath refactoredPath;

        SpecialFolder sf = SpecialFolderUtil.getRootSpecialFolder(currentRes);
        if (sf != null) {
            currPath =
                    SpecialFolderUtil.getSpecialFolderRelativePath(sf,
                            currentRes);
        } else {
            currPath = currentRes.getProjectRelativePath();
        }

        sf = SpecialFolderUtil.getRootSpecialFolder(refactoredRes);
        if (sf != null) {
            refactoredPath =
                    SpecialFolderUtil.getSpecialFolderRelativePath(sf,
                            refactoredRes);
        } else {
            refactoredPath = refactoredRes.getProjectRelativePath();
        }

        if (currPath != null && refactoredPath != null) {
            return currPath.equals(refactoredPath);
        }

        return false;
    }
}
