/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
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
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * 
 * @author njpatel
 * @since 9 Jul 2012
 */
public class UpdateFormReferenceChange extends UpdateReferenceChange {

    /**
     * 
     */
    public UpdateFormReferenceChange() {
        super();
    }

    /**
     * @param wc
     * @param currentFile
     * @param refactoredFile
     */
    public UpdateFormReferenceChange(Xpdl2WorkingCopyImpl wc,
            IFile[] currentFiles, IFile[] refactoredFiles) {
        super(wc, currentFiles, refactoredFiles);
    }

    private static final String URI_PREFIX = "form://"; //$NON-NLS-1$

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

        String oldUri = getFormURI(getSpecialFolderRelativePath(currentFile));
        String newUri =
                getFormURI(getSpecialFolderRelativePath(refactoredFile));

        if (oldUri != null && newUri != null) {
            pm.beginTask(String.format(Messages.UpdateFormReferenceChange_updateFormsReference_progress_shortdesc,
                    wc.getName()), pkg.getProcesses().size());
            for (Process process : pkg.getProcesses()) {
                for (Activity activity : Xpdl2ModelUtil
                        .getAllActivitiesInProc(process)) {
                    FormImplementation formImplementation =
                            TaskObjectUtil
                                    .getUserTaskFormImplementation(activity);

                    if (formImplementation != null
                            && formImplementation.getFormType() == FormImplementationType.FORM) {
                        if (oldUri.equals(formImplementation.getFormURI())) {
                            // Update the form URI in the form implementation
                            cmds.add(SetCommand.create(editingDomain,
                                    formImplementation,
                                    XpdExtensionPackage.eINSTANCE
                                            .getFormImplementation_FormURI(),
                                    newUri));

                            // Update the extended attribute
                            ExtendedAttribute attr =
                                    TaskObjectUtil
                                            .getExtendedAttributeByName(activity,
                                                    TaskObjectUtil.USER_TASK_ATTR);
                            if (attr != null && oldUri.equals(attr.getValue())) {
                                cmds.add(SetCommand.create(editingDomain,
                                        attr,
                                        Xpdl2Package.eINSTANCE
                                                .getExtendedAttribute_Value(),
                                        newUri));
                            }
                        }
                    }
                }
                pm.worked(1);
            }
            pm.done();
        }

        return cmds;
    }

    /**
     * Convert the given path to the reference form URI.
     * 
     * @param path
     * @return
     */
    private String getFormURI(IPath path) {
        if (path != null) {
            return URI_PREFIX + path.toString();
        }
        return null;
    }

}
