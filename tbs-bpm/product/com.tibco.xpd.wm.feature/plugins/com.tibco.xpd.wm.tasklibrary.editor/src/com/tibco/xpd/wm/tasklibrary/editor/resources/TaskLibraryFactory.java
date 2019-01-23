/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.resources;

import org.eclipse.core.resources.IFile;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Methods for creating / accessing task libraries.
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryFactory {

    public static final TaskLibraryFactory INSTANCE = new TaskLibraryFactory();

    /**
     * Get the task library from the File
     * 
     * @param pkg
     * 
     * @return The xpdl:WorkflowProcess that represents the task library.
     */
    public Process getTaskLibrary(IFile file) {
        WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(file);
        if (wc == null || wc.isInvalidFile()
                || !(wc.getRootElement() instanceof Package)) {
            // throw new RuntimeException(
            //                    "Cannot access working copy for or badly formatted file: " //$NON-NLS-1$
            // + file.getName());
        }

        //
        // Get the first process in the file
        Package pkg = (Package) wc.getRootElement();

        return getTaskLibrary(pkg);
    }

    /**
     * Get the task library from the Working copy
     * 
     * @param pkg
     * 
     * @return The xpdl:WorkflowProcess that represents the task library.
     */
    public Process getTaskLibrary(WorkingCopy wc) {
        //
        // Get the first process in the file
        if (wc != null && (wc.getRootElement() instanceof Package)) {
            Package pkg = (Package) wc.getRootElement();

            return getTaskLibrary(pkg);
        } else {
            throw new RuntimeException(
                    "Bad working copy or badly formatted file."); //$NON-NLS-1$
        }

    }

    /**
     * Get the task library from the given XPDL package
     * 
     * @param pkg
     * 
     * @return The xpdl:WorkflowProcess that represents the task library.
     */
    public Process getTaskLibrary(Package pkg) {
        if (pkg != null) {
            if (pkg.getProcesses().size() != 1) {
                throw new IllegalStateException(
                        "Task Library packages are expected to contain exactly ONE xpdl:WorkflowProcess element."); //$NON-NLS-1$
            }

            Process process = pkg.getProcesses().get(0);
            return process;
        }
        return null;
    }

    // TODO create methods AccessbleObjectCommand createTaskLibrary(Package pkg)
    // Creates a command to add a task library to the given package and any
    // other ancilliary bits too (like pool etc).

}
