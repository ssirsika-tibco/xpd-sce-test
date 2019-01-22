/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.resources;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IFileEditorInput;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorPlugin;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Property tester for various eclipse contribution enablement state testing.
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryPropertyTester extends PropertyTester {

    /** Is a task library file. */
    private static final String IS_TASK_LIBRARY_FILE = "isTaskLibraryFile"; //$NON-NLS-1$

    /** Is a task library */
    private static final String IS_TASK_LIBRARY = "isTaskLibrary"; //$NON-NLS-1$

    /** Is an object that belongs to a task library. */
    private static final String IS_TASK_LIBRARY_CONTENT =
            "isTaskLibraryContent"; //$NON-NLS-1$

    /** Is an editor input for a task library. */
    private static final String IS_TASK_LIBRARY_EDITOR_INPUT =
            "isTaskLibraryEditorInput"; //$NON-NLS-1$

    /**
     * 
     */
    public TaskLibraryPropertyTester() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object,
     * java.lang.String, java.lang.Object[], java.lang.Object)
     */
    @Override
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        boolean ret = false;
        if (IS_TASK_LIBRARY_FILE.equals(property)) {
            if (receiver instanceof IFile) {
                ret = isTaskLibraryFile((IFile) receiver);
            }
        } else if (IS_TASK_LIBRARY.equals(property)) {
            if (receiver instanceof Process) {
                ret = isTaskLibraryProcess((Process) receiver);
            }

        } else if (IS_TASK_LIBRARY_CONTENT.equals(property)) {

            if (receiver instanceof EObject) {
                ret = isTaskLibraryContent((EObject) receiver);

            } else if (receiver instanceof INavigatorGroup) {
                Object o = ((INavigatorGroup) receiver).getParent();
                if (o instanceof EObject) {
                    ret = isTaskLibraryContent((EObject) o);
                }
            } else if (receiver instanceof IAdaptable) {
                Object eo = ((IAdaptable) receiver).getAdapter(EObject.class);
                if (eo != null) {
                    ret = isTaskLibraryContent((EObject) eo);
                }
            }

        } else if (IS_TASK_LIBRARY_EDITOR_INPUT.equals(property)) {
            if (receiver instanceof ProcessEditorInput) {
                ret =
                        isTaskLibraryProcess(((ProcessEditorInput) receiver)
                                .getProcess());
            } else if (receiver instanceof IFileEditorInput) {
                ret =
                        isTaskLibraryFile(((IFileEditorInput) receiver)
                                .getFile());
            }
        }

        return ret;
    }

    /**
     * @param file
     * @return true if the given file is a task library file.
     */
    public static boolean isTaskLibraryFile(IFile file) {
        if (file != null && file.isAccessible()) {
            if (TaskLibraryEditorPlugin.TASKLIBRARY_FILE_EXTENSION.equals(file
                    .getFileExtension())) {
                if (TaskLibraryFactory.INSTANCE.getTaskLibrary(file) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param process
     * @return true if the xpdl process represents a task library
     */
    public static boolean isTaskLibraryProcess(Process process) {
        if (process != null) {
            if (Xpdl2ModelUtil.isTaskLibrary(process)) {
                return true;
            }

            IFile file = WorkingCopyUtil.getFile(process);

            if (file != null) {
                return isTaskLibraryFile(file);
            }
        }

        return false;
    }

    /**
     * @param eObject
     * @return true if the given eObject if the content of a task library (or a
     *         task library itself).
     */
    public static boolean isTaskLibraryContent(EObject eObject) {
        // EVERYTHING inside a task library file is the content of a task
        // library.
        /*
         * XPD-1140: Though we may not have a file (as may have opened via a
         * stream from a repository version. So instead check if the object is
         * under a task library process or under a package that contains a task
         * library (package contains single library and no processes.
         */
        EObject toTest = eObject;
        while (toTest != null) {
            if (toTest instanceof Process) {
                return isTaskLibraryProcess((Process) toTest);

            } else if (toTest instanceof Package) {
                for (Process process : ((Package) toTest).getProcesses()) {
                    if (isTaskLibraryProcess(process)) {
                        return true;
                    }
                }
            }
            toTest = toTest.eContainer();
        }

        return false;
    }
}
