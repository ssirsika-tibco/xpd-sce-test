/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions.pageflow;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;

/**
 * Base class that underlies user task->pageflow interfaces where the quick fix
 * changes / adds pageflow parameters.
 * <p>
 * Basically it detects whether the pageflow is in a different package and then
 * will force a save of the pageflow package after changing it.
 * <p>
 * If this is not done then the rule can appear not to have been fixed until
 * both the pageflow package and referencing package are changed and resaved.
 * <p>
 * If the pageflow package is already dirty the user is asked to confirm a save
 * of the package.
 * 
 * @author aallway
 * @since 3.2
 */
public abstract class AbstractPageflowParamDataResolution extends
        AbstractUserTaskPageflowParamSynchResolution {

    protected abstract Command createResolutionCommand(
            EditingDomain editingDomain, EObject target, IMarker marker)
            throws ResolutionException;

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target != null) {
            Process pageflowProcess = getPageflowProcessFromMarker(marker);

            if (pageflowProcess != null) {
                IFile pageflowFile = WorkingCopyUtil.getFile(pageflowProcess);
                WorkingCopy pageflowWCToSave = null;

                IFile businessFile = WorkingCopyUtil.getFile(target);
                if (businessFile != null && pageflowFile != null) {

                    /*
                     * If the pageflow is in a different package then set the
                     * pageflowWCToSave to indicate to ExecuteAndSaveCommand
                     * that a save is required after executin the save
                     */
                    if (!businessFile.equals(pageflowFile)) {
                        pageflowWCToSave =
                                WorkingCopyUtil.getWorkingCopy(pageflowFile);

                        if (pageflowWCToSave != null) {
                            if (pageflowWCToSave.isWorkingCopyDirty()) {
                                if (!MessageDialog
                                        .openConfirm(Display.getCurrent()
                                                .getActiveShell(),
                                                Messages.MatchPageflowParamToUserTaskDataResolution_Save_title,
                                                Messages.MatchPageflowParamToUserTaskDataResolution_PageflowPackageMustBeSavedBeforeFix_longdesc)) {
                                    return null;
                                }

                                try {
                                    pageflowWCToSave.save();
                                } catch (IOException e) {
                                    throw new ResolutionException(
                                            "Failed saving pageflow resource", e); //$NON-NLS-1$
                                }

                            }
                        }
                    }

                    Command resCmd =
                            createResolutionCommand(editingDomain,
                                    target,
                                    marker);
                    if (resCmd != null) {
                        ExecuteAndSaveCommand cmd =
                                new ExecuteAndSaveCommand(pageflowWCToSave);
                        cmd.setLabel(resCmd.getLabel());

                        cmd.append(resCmd);
                        return cmd;
                    }

                }

            }
        }
        return null;
    }

    /**
     * Command that perfors a save on the given resource working copy after
     * executing its child commands.
     * 
     * 
     * @author aallway
     * @since 3.3 (29 Mar 2010)
     */
    private class ExecuteAndSaveCommand extends LateExecuteCompoundCommand {

        private WorkingCopy wcToSave;

        ExecuteAndSaveCommand(WorkingCopy wcToSave) {
            this.wcToSave = wcToSave;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand#execute()
         */
        @Override
        public void execute() {
            try {
                super.execute();

                if (wcToSave != null) {
                    wcToSave.save();
                }

            } catch (Exception e) {
                throw new RuntimeException(
                        "Failed to execute command or save file", e); //$NON-NLS-1$
            }
        }

    }

}
