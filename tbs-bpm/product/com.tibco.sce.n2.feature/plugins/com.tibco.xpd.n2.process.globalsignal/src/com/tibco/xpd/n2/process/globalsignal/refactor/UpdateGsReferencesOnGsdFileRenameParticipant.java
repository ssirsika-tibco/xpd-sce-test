package com.tibco.xpd.n2.process.globalsignal.refactor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RefactoringArguments;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;

import com.tibco.xpd.globalSignalDefinition.util.GsdConstants;
import com.tibco.xpd.globalSignalDefinition.workingcopy.GsdWorkingCopy;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Rename participant for GSD file rename which updates all the Global Signal
 * Activities which reference the GSD. This supports UNDO-REDO as well.
 * 
 * 
 * @author kthombar
 * @since Jun 22, 2015
 */
public class UpdateGsReferencesOnGsdFileRenameParticipant extends
        RenameParticipant {

    private IFile renamedGsdFile;

    private String newGsdFileName;

    private static final String GSD_FILE_EXTENSION = ".gsd"; //$NON-NLS-1$

    public UpdateGsReferencesOnGsdFileRenameParticipant() {
        // Do nothing here.
    }

    @Override
    protected boolean initialize(Object element) {
        /*
         * just return true from there the other overloaded
         * initialize(RefactoringProcessor, Object , RefactoringArguments)
         * method does the real computation
         */
        return true;
    }

    @Override
    public String getName() {

        return Messages.UpdateGsReferencesOnGsdFileRenameParticipant_RenameParticipant_name;
    }

    @Override
    public RefactoringStatus checkConditions(IProgressMonitor pm,
            CheckConditionsContext context) throws OperationCanceledException {

        pm.beginTask(Messages.UpdateGsReferencesOnGsdFileRenameParticipant_CheckValidFileExt_msg,
                1);

        RefactoringStatus status = new RefactoringStatus();
        ;
        try {

            if (WorkingCopyUtil.getWorkingCopy(renamedGsdFile) == null) {
                /*
                 * Check if the Working copy is accessible.
                 */
                status.addFatalError(String
                        .format(Messages.UpdateGsReferencesOnGsdFileRenameParticipant_CannotaccessWorkingCopy_msg,
                                renamedGsdFile.getName()));
            } else {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(renamedGsdFile);

                if (wc instanceof GsdWorkingCopy) {
                    status =
                            GsdRefactorHelper
                                    .checkWorkingCopyDirtyConditions(pm,
                                            context,
                                            Collections
                                                    .singletonList((GsdWorkingCopy) wc));
                }
            }

            if (status.isOK()) {

                if (!newGsdFileName.endsWith(GSD_FILE_EXTENSION)) {
                    /*
                     * verify that the new name has .gsd extension
                     */
                    status.addFatalError(String
                            .format(Messages.UpdateGsReferencesOnGsdFileRenameParticipant_InvalidFileExtension_msg,
                                    newGsdFileName));
                }
            }

            pm.worked(1);
        } finally {
            pm.done();
        }
        return status;
    }

    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {

        pm.beginTask(getName(), 1);
        /*
         * create a composite change, other changes will be a part of this super
         * composte change.
         */
        CompositeChange compositeChange = null;

        try {

            List<Activity> allRefrencingGlobalSignalActivities =
                    GsdRefactorHelper
                            .getAllRefrencingGlobalSignalActivities(renamedGsdFile);

            if (!allRefrencingGlobalSignalActivities.isEmpty()) {

                compositeChange =
                        new CompositeChange(
                                Messages.UpdateGsReferencesOnGsdFileRenameParticipant_UpdateReferencesChange_title);

                compositeChange.markAsSynthetic();

                /*
                 * stores all the changes to execute on the Global Signal
                 * Activities.
                 */
                List<UpdateGsReferencesOnGsdFileRefactorChange> allChangesToExecute =
                        new ArrayList<UpdateGsReferencesOnGsdFileRefactorChange>();

                for (Activity activity : allRefrencingGlobalSignalActivities) {
                    /*
                     * get the old name
                     */
                    String oldGlobalSignalQualifiedName =
                            EventObjectUtil.getSignalName(activity);
                    /*
                     * get global signal name has '#' in it which is folliwed by
                     * the signal name
                     */
                    int indexOfHash = oldGlobalSignalQualifiedName.indexOf('#');

                    Path path =
                            new Path(oldGlobalSignalQualifiedName.substring(0,
                                    indexOfHash));

                    IPath removeLastSegments = path.removeLastSegments(1);
                    /*
                     * create the new Signal name which the global signal
                     * activities should reference.
                     */
                    String newQualifiedSignalName =
                            removeLastSegments.append(newGsdFileName)
                                    .toString();

                    newQualifiedSignalName =
                            newQualifiedSignalName
                                    + oldGlobalSignalQualifiedName
                                            .substring(indexOfHash,
                                                    oldGlobalSignalQualifiedName
                                                            .length());
                    /*
                     * add all the changes to the list
                     */
                    allChangesToExecute
                            .add(new UpdateGsReferencesOnGsdFileRefactorChange(
                                    activity, oldGlobalSignalQualifiedName,
                                    newQualifiedSignalName, WorkingCopyUtil
                                            .getEditingDomain(activity)));

                }
                /*
                 * Add all changes to the parent composite change.
                 */
                compositeChange
                        .add(new CompositeChange(
                                Messages.UpdateGsReferencesOnGsdFileRenameParticipant_UpdateReferencesChange_title,
                                allChangesToExecute
                                        .toArray(new Change[allChangesToExecute
                                                .size()])));
            }

            pm.worked(1);
        } finally {
            pm.done();
        }
        return compositeChange;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#initialize(org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor,
     *      java.lang.Object,
     *      org.eclipse.ltk.core.refactoring.participants.RefactoringArguments)
     * 
     * @param processor
     * @param element
     * @param arguments
     * @return
     */
    @Override
    public boolean initialize(RefactoringProcessor processor, Object element,
            RefactoringArguments arguments) {

        if (element instanceof IFile
                && GsdConstants.GSD_FILE_EXTENSION.equals(((IFile) element)
                        .getFileExtension())) {
            renamedGsdFile = (IFile) element;
        }

        if (arguments instanceof RenameArguments) {
            newGsdFileName = ((RenameArguments) arguments).getNewName();
        }
        /*
         * return true only if the GSD file is not null and the File name is
         * available
         */
        return renamedGsdFile != null && newGsdFileName != null;
    }
}
