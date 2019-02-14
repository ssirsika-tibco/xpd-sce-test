/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.internal.refactor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.internal.refactor.XpdlReferenceRefactorHelper.RefactorType;
import com.tibco.xpd.processeditor.xpdl2.internal.refactor.change.IRefactorReferenceChangeProvider;
import com.tibco.xpd.processeditor.xpdl2.internal.refactor.change.UpdateBOMReferenceChange;
import com.tibco.xpd.processeditor.xpdl2.internal.refactor.change.UpdateOMReferenceChange;
import com.tibco.xpd.processeditor.xpdl2.internal.refactor.change.UpdateReferenceChange;
import com.tibco.xpd.processeditor.xpdl2.internal.refactor.change.UpdateXpdlReferenceChange;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * Refactoring participant to update all references in an XPDL model when a
 * referenced file is renamed. This class should be used with a parameter to
 * indicate the type of reference it needs to deal with. For example, if dealing
 * with BOM references then this class will be called with the parameter "bom"
 * for in the class attribute of the refactor extension:
 * 
 * <pre>
 * class="com.tibco.xpd.analyst.resources.xpdl2.internal.refactor.XpdlReferenceRenameParticipant: bom"
 * </pre>
 * 
 * @author njpatel
 * @since 3.5.10
 */
public class XpdlReferenceRenameParticipant extends RenameParticipant implements
        IRefactorReferenceChangeProvider, IExecutableExtension {

    private final XpdlReferenceRefactorHelper helper;

    /**
     * BOMs affected by this rename.
     */
    private Set<Xpdl2WorkingCopyImpl> affectedXpdls;

    /**
     * Files that have been renamed
     */
    private IFile[] oldFiles;

    /**
     * Files as they will be after rename
     */
    private IFile[] newFiles;

    /**
     * The type of file being renamed
     */
    private RefactorType type;

    /**
     * 
     */
    public XpdlReferenceRenameParticipant() {
        helper = createHelper();
    }

    /**
     * Create the participant helper class.
     * 
     * @param changeProvider
     * @return
     */
    protected XpdlReferenceRefactorHelper createHelper() {
        return new XpdlReferenceRefactorHelper();
    }

    @Override
    protected boolean initialize(Object element) {

        if (type == null) {
            return false;
        }

        if (element instanceof IFile) {
            oldFiles = new IFile[] { (IFile) element };
            IFile newFile = null;

            if (oldFiles[0].getParent() != null) {
                newFile =
                        oldFiles[0].getParent().getFile(new Path(getArguments()
                                .getNewName()));
            }

            if (newFile != null) {
                newFiles = new IFile[] { newFile };

                affectedXpdls = helper.getAffectedXpdls((IFile) element);
            }

        } else if (element instanceof IFolder) {
            List<IFile> files;
            try {
                files = helper.getAllFiles((IFolder) element, type);
                oldFiles = files.toArray(new IFile[files.size()]);
                newFiles = getNewFileNames(oldFiles, (IFolder) element);

                if (!files.isEmpty()) {
                    affectedXpdls = new HashSet<Xpdl2WorkingCopyImpl>();

                    for (IFile file : files) {
                        affectedXpdls.addAll(helper.getAffectedXpdls(file));
                    }
                }
            } catch (CoreException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return affectedXpdls != null && !affectedXpdls.isEmpty()
                && oldFiles.length > 0 && newFiles.length > 0;
    }

    /**
     * Get the list of the files as they will be after the rename of the folder
     * (corresponding to the list of files passed in).
     * 
     * @param oldFiles
     * @param currentFolder
     * @return
     */
    private IFile[] getNewFileNames(IFile[] oldFiles, IFolder currentFolder) {
        IFile[] newFiles = new IFile[oldFiles.length];

        int count = currentFolder.getFullPath().segmentCount();
        IFolder newFolder =
                currentFolder.getParent().getFolder(new Path(getArguments()
                        .getNewName()));
        for (int idx = 0; idx < oldFiles.length; idx++) {
            IPath relativePath =
                    oldFiles[idx].getFullPath().removeFirstSegments(count);
            newFiles[idx] = newFolder.getFile(relativePath);
        }

        return newFiles;
    }

    @Override
    public String getName() {
        return Messages.XpdlReferenceRenameParticipant_participant_shortdesc;
    }

    @Override
    public RefactoringStatus checkConditions(IProgressMonitor pm,
            CheckConditionsContext context) throws OperationCanceledException {

        return helper.checkWorkingCopyDirtyConditions(pm,
                context,
                affectedXpdls);
    }

    @Override
    public final Change createChange(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {
        List<Change> changes =
                helper.createUpdateReferenceChanges(pm,
                        affectedXpdls,
                        oldFiles,
                        newFiles,
                        this);

        return new CompositeChange(getUpdateReferenceMessage(),
                changes.toArray(new Change[changes.size()]));
    }

    /**
     * Get the update references message string to be shown in the refactor
     * preview.
     * 
     * @return The update references message string to be shown in the refactor
     *         preview.
     */
    protected String getUpdateReferenceMessage() {
        return Messages.XpdlReferenceRenameParticipant_updateReferenceChange_shortdesc;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.internal.refactor.change.IRefactorReferenceChangeProvider#createChange(com.tibco.xpd.resources.WorkingCopy,
     *      java.lang.String, java.lang.String)
     * 
     * @param wc
     * @param oldFiles
     * @param newFiles
     * @return
     */
    @Override
    public UpdateReferenceChange createChange(Xpdl2WorkingCopyImpl wc,
            IFile[] oldFiles, IFile[] newFiles) {

        UpdateReferenceChange change = null;
        /*
         * Create the appropriate reference change object for the type of
         * reference being refactored
         */
        switch (type) {
        case BOM:
            change = new UpdateBOMReferenceChange();
            break;
        case OM:
            change = new UpdateOMReferenceChange();
            break;

        case XPDL:
            change = new UpdateXpdlReferenceChange();
            break;
        }
        if (change != null) {
            change.setWorkingCopy(wc);
            change.setCurrentFiles(oldFiles);
            change.setRefactoredFiles(newFiles);
        }

        return change;
    }

    /**
     * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement,
     *      java.lang.String, java.lang.Object)
     * 
     * @param config
     * @param propertyName
     * @param data
     * @throws CoreException
     */
    @Override
    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {

        if (data instanceof String) {
            type = RefactorType.getTypeFromFileExt((String) data);

            if (type == null) {
                throw new CoreException(new Status(IStatus.ERROR,
                        Xpdl2ResourcesPlugin.PLUGIN_ID,
                        String.format(getInitializationDataExceptionMessage(),
                                (String) data)));
            }
        }
    }

    /**
     * Get the message for intialization data exception.
     * 
     * @return The message for intialization data exception.
     */
    protected String getInitializationDataExceptionMessage() {

        return "Invalid parameter '%s' passed to the XPDL Rename refactor participant"; //$NON-NLS-1$
    }
}
