/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.linkhelpers;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.navigator.ILinkHelper;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInputFactory;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.AbstractProjectExplorerLinkHelper;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Link editor helper for the Project Explorer to link the selected Process to
 * it's editor part and vice versa
 * 
 * @author njpatel
 * 
 */
public class ProcessEditorProjectExplorerLinkHelper extends
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
        // mainSelection is always what we returned from findMainSelection.
        if (mainSelection instanceof Process) {
            Process process = (Process) mainSelection;

            //
            // See whether the selected object is a child of the main process
            // for activated editor.
            EObject selProcessAncestor = null;
            if (selObject instanceof EObject) {
                selProcessAncestor = Xpdl2ModelUtil.getProcess((EObject) selObject);
                
            } else if (selObject instanceof INavigatorGroup) {
                INavigatorGroup navigatorGroup =
                    (INavigatorGroup) selObject;
                if (navigatorGroup.getParent() instanceof EObject) {
                    selProcessAncestor = Xpdl2ModelUtil.getProcess((EObject) navigatorGroup.getParent());
                }
            }
            
            if (selProcessAncestor == mainSelection) {
                return true;
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
    public void activateEditor(IWorkbenchPage aPage,
            IStructuredSelection aSelection) {
        // Check if the first item selected is a Process
        if (aSelection != null && !aSelection.isEmpty()) {
            Object firstElement = aSelection.getFirstElement();

            EObject selObject = null;

            if (firstElement instanceof EObject) {
                selObject = (EObject) firstElement;
            } else if (firstElement instanceof INavigatorGroup) {
                if (((INavigatorGroup) firstElement).getParent() instanceof EObject) {
                    selObject =
                            (EObject) ((INavigatorGroup) firstElement)
                                    .getParent();
                }
            }

            if (selObject != null) {
                // Find the process for selected object.
                while (selObject != null) {
                    if (selObject instanceof Process) {
                        break;
                    }
                    selObject = selObject.eContainer();
                }

                if (selObject instanceof Process) {

                    // Get selected element as process or child of process.
                    Process selProcess = (Process) selObject;

                    ProcessEditorInputFactory fact =
                            new ProcessEditorInputFactory();

                    if (fact != null) {
                        IEditorInput processEditorInput =
                                fact.getEditorInputFor(selProcess);

                        if (processEditorInput != null) {
                            IEditorPart editor =
                                    aPage.findEditor(processEditorInput);

                            // If the editor is open then bring to top
                            if (editor != null) {
                                aPage.bringToTop(editor);
                            }
                        }
                    }
                }
            }
        }
        return;
    }

}
