/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IStorageEditorInput;

import com.tibco.xpd.processeditor.xpdl2.AbstractProcessDiagramEditor;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInputFactory;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;
import com.tibco.xpd.wm.tasklibrary.editor.resources.TaskLibraryFactory;
import com.tibco.xpd.wm.tasklibrary.editor.resources.TaskLibraryPropertyTester;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyForTemporaryStream;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl.Xpdl2FileType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * The TaskModeler editor - initially, each task library is a SINGLE process in
 * an XPDL formatted file BUT with an extension of
 * {@link TaskLibraryEditorPlugin#TASKLIBRARY_FILE_EXTENSION} (.tasks)
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryEditor extends AbstractProcessDiagramEditor {

    public static String EDITOR_ID = "com.tibco.xpd.wm.tasklibrary.editor";//$NON-NLS-1$

    private WorkingCopy workingCopy;

    private boolean workingCopyCreatedHere = false;

    @Override
    protected String getEditorId() {
        return EDITOR_ID;
    }

    @Override
    protected ProcessEditorInput adaptToProcessEditorInput(
            IEditorSite editorSite, IEditorInput editorInput) {
        //
        // Nominally we should be getting File as our input, but just in case
        // we'll allow the expected.
        if (editorInput instanceof ProcessEditorInput) {
            return (ProcessEditorInput) editorInput;
        }

        //
        // Get the Working copy for the file.
        if (editorInput instanceof IFileEditorInput) {
            IFile file = ((IFileEditorInput) editorInput).getFile();

            Process process = TaskLibraryFactory.INSTANCE.getTaskLibrary(file);
            if (process != null) {
                workingCopy = WorkingCopyUtil.getWorkingCopyFor(process);
                return (ProcessEditorInput) ProcessEditorInputFactory.INSTANCE
                        .getEditorInputFor(process);
            }

        } else if (editorInput instanceof IStorageEditorInput) {
            /*
             * XPD-1140: StorageEditorInput is what we get for anything that's
             * an xpdl file but is not a woprkspce file (like for opening from
             * repository etc).
             */
            IStorageEditorInput storageInput =
                    (IStorageEditorInput) editorInput;

            try {
                /*
                 * IStorageEditorInput has the full path of the original
                 * resource (not the one in repo). So In order to try and make
                 * things consistent in the working copy we will find the actual
                 * resource and pass that in (although the EMF resource will be
                 * loaded from the inputStream from repo version)
                 */
                IPath fullPath = storageInput.getStorage().getFullPath();
                IResource resource =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getFile(fullPath);
                if (resource instanceof IFile) {
                    workingCopy =
                            new Xpdl2WorkingCopyForTemporaryStream(resource,
                                    Xpdl2FileType.PROCESS, storageInput
                                            .getStorage().getContents());

                    workingCopyCreatedHere = true;

                    Process taskLibrary =
                            TaskLibraryFactory.INSTANCE
                                    .getTaskLibrary((Package) workingCopy
                                            .getRootElement());
                    if (taskLibrary != null) {
                        return (ProcessEditorInput) ProcessEditorInputFactory.INSTANCE
                                .getEditorInputFor(taskLibrary);
                    }
                }

            } catch (CoreException e) {
                TaskLibraryEditorPlugin
                        .getDefault()
                        .getLogger()
                        .error(e,
                                "Failed loading XPDL package from temporary input stream."); //$NON-NLS-1$
            }
        }

        throw new IllegalStateException(
                "Unexpected EditorInput: " + editorInput); //$NON-NLS-1$
    }

    @Override
    protected ProcessWidgetType getProcessWidgetType() {
        return ProcessWidgetType.TASK_LIBRARY;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#dispose()
     */
    @Override
    public void dispose() {
        if (workingCopyCreatedHere && workingCopy != null) {
            ((AbstractWorkingCopy) workingCopy).dispose();
        }
        super.dispose();
    }

    public static class TaskLibraryProcessOrActivityFilter implements IFilter {

        public boolean select(Object toTest) {
            if (toTest instanceof Activity) {
                toTest = Xpdl2ModelUtil.getProcess((Activity) toTest);
            }

            if (toTest instanceof Process) {
                return TaskLibraryPropertyTester
                        .isTaskLibraryProcess((Process) toTest);
            }
            return false;
        }

    }

}
