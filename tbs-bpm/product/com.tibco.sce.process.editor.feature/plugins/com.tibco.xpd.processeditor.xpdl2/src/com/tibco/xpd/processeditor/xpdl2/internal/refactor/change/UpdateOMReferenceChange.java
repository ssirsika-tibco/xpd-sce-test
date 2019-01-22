/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.internal.refactor.change;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * Refactor change to update the XPDL when a referenced OM has been renamed or
 * moved.
 * 
 * @author njpatel
 */
public class UpdateOMReferenceChange extends UpdateReferenceChange {

    /**
     * 
     */
    public UpdateOMReferenceChange() {
        super();
    }

    /**
     * @param wc
     * @param currFiles
     * @param refactoredFiles
     */
    public UpdateOMReferenceChange(Xpdl2WorkingCopyImpl wc, IFile[] currFiles,
            IFile[] refactoredFiles) {
        super(wc, currFiles, refactoredFiles);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.internal.refactor.change.UpdateReferenceChange#perform(org.eclipse.core.runtime.IProgressMonitor,
     *      org.eclipse.core.resources.IFile, org.eclipse.core.resources.IFile)
     * 
     * @param pm
     * @param currentFile
     * @param refactoredFile
     * @return
     */
    @Override
    protected List<Command> perform(IProgressMonitor pm, IFile currentFile,
            IFile refactoredFile) {
        List<Command> cmds = new ArrayList<Command>();

        List<ExternalReference> extRefs = new ArrayList<ExternalReference>();

        String oldLocation = null;
        String newLocation = null;

        IPath path = getSpecialFolderRelativePath(currentFile);
        if (path != null) {
            oldLocation = path.toString();
        }

        path = getSpecialFolderRelativePath(refactoredFile);
        if (path != null) {
            newLocation = path.toString();
        }

        if (oldLocation != null && newLocation != null) {
            pm.beginTask(String
                    .format(Messages.UpdateOMReferenceChange_updateOMReferences_progress_shortdesc,
                            wc.getName()), 1);
            try {

                for (Participant participant : pkg.getParticipants()) {
                    ExternalReference ref = participant.getExternalReference();
                    if (ref != null) {
                        extRefs.add(ref);
                    }
                }

                if (!extRefs.isEmpty()) {
                    // Update the old location with the new
                    for (ExternalReference ref : extRefs) {
                        if (oldLocation.equals(ref.getLocation())) {
                            cmds.add(SetCommand.create(editingDomain,
                                    ref,
                                    Xpdl2Package.eINSTANCE
                                            .getExternalReference_Location(),
                                    newLocation));
                        }
                    }

                }

            } finally {
                pm.done();
            }
        }

        return cmds;
    }

}
