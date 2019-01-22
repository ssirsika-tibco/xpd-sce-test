/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.resources;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.processeditor.xpdl2.AbstractProcessDiagramEditor;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInputFactory;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.AbstractProjectExplorerLinkHelper;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Process;

/**
 * Helper for the <-- --> link with editor button on the project explorer for
 * linking Task Library Editor with selected content in the project explorer.
 * <p>
 * See {@link TaskLibraryPropertyTester} and plugin.xml for the kinds of things
 * that we can expect to be used as proj explorer selections for task library
 * editor (a .tasks file, one and only process in a .tasks file or any Eobject in
 * a .tasks file are all part of ONE task library).
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryProjectExplorerLinkHelper extends
        AbstractProjectExplorerLinkHelper {

    @Override
    protected Object findMainSelection(IEditorInput editorInput) {
        // Check if the editor selected is the Process editor
        if (editorInput instanceof ProcessEditorInput) {
            return ((ProcessEditorInput) editorInput).getProcess();
        }
        return null;
    }

    @Override
    protected boolean isChild(Object selObject, Object mainSelection) {
        // mainSelection is always what we returned
        if (mainSelection instanceof Process) {
            Process taskLibrary = (Process) mainSelection;

            IFile taskLibraryFile = WorkingCopyUtil.getFile(taskLibrary);
            if (taskLibraryFile != null) {

                //
                // Adapt from things we're likely to find under the task library
                // file in proj exp
                IFile selObjectFile = null;

                if (selObject instanceof INavigatorGroup) {
                    INavigatorGroup navigatorGroup =
                            (INavigatorGroup) selObject;

                    if (navigatorGroup.getParent() instanceof EObject) {
                        selObjectFile =
                                WorkingCopyUtil
                                        .getFile((EObject) navigatorGroup
                                                .getParent());
                    }

                } else if (selObject instanceof EObject) {
                    selObjectFile =
                            WorkingCopyUtil.getFile((EObject) selObject);

                } else if (selObject instanceof IFile) {
                    selObjectFile = (IFile) selObject;
                }

                if (taskLibraryFile.equals(selObjectFile)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.ui.navigator.ILinkHelper#activateEditor(org.eclipse.ui.
     * IWorkbenchPage, org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void activateEditor(IWorkbenchPage page,
            IStructuredSelection selection) {

        if (selection != null && !selection.isEmpty()) {
            Object selObject = selection.getFirstElement();

            // Find the TaskLibrary for the selection.
            Process taskLibrary = null;

            if (selObject instanceof INavigatorGroup) {
                selObject = ((INavigatorGroup) selObject).getParent();
            }

            if (selObject instanceof Process) {
                // The selection in a taskLibrary xpdl2:WorkflowProcess itself.
                taskLibrary = (Process) selObject;

            } else if (selObject instanceof EObject) {
                // The selection is an EObject in a
                EObject eo = (EObject) selObject;
                IFile file = WorkingCopyUtil.getFile(eo);

                if (file != null) {
                    taskLibrary =
                            TaskLibraryFactory.INSTANCE.getTaskLibrary(file);
                }

            } else if (selObject instanceof IFile) {
                // The selection is a
                taskLibrary =
                        TaskLibraryFactory.INSTANCE
                                .getTaskLibrary((IFile) selObject);
            }

            if (taskLibrary != null) {
                // If we are actviating editor for a specifc
                // actvity then change slection in editor to
                // reflect that. Otherwise preserve existing
                // selection.
                revealIfEditorOpen(page, taskLibrary, selObject, !(selObject instanceof Process));
            }
        }

        return;
    }

    /**
     * @param page
     * @param process
     * @param revealObject
     */
    public static void revealIfEditorOpen(IWorkbenchPage page, Process process,
            Object revealObject, boolean doSelect) {
        ProcessEditorInputFactory fact =
                new ProcessEditorInputFactory();

        if (fact != null) {
            IEditorInput processEditorInput =
                    fact.getEditorInputFor(process);

            if (processEditorInput != null) {
                IEditorPart editor =
                        page.findEditor(processEditorInput);

                // If the editor is open then bring to top
                if (editor != null) {
                    page.bringToTop(editor);

                    if (revealObject instanceof EObject
                            && editor instanceof AbstractProcessDiagramEditor) {
                        AbstractProcessDiagramEditor pde =
                                (AbstractProcessDiagramEditor) editor;

                        pde.gotoEObject(doSelect, (EObject) revealObject);

                    }
                }
            }
        }
    }

}
