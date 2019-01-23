/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.linkhelper;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.n2.globalsignal.resource.editor.GSDEditorInputFactory;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.AbstractProjectExplorerLinkHelper;

/**
 * Link editor helper for the Project Explorer to link the selected GSD to it's
 * editor part and vice versa.
 * 
 * @author sajain
 * @since Feb 25, 2015
 */
public class GSDEditorProjectExplorerLinkHelper extends
        AbstractProjectExplorerLinkHelper {

    @Override
    protected Object findMainSelection(IEditorInput editorInput) {

        /*
         * Check if the editor selected is the file editor
         */
        if (editorInput instanceof FileEditorInput) {

            return ((FileEditorInput) editorInput).getFile();
        }
        return null;
    }

    @Override
    protected boolean isChild(Object selObject, Object mainSelection) {

        /*
         * mainSelection is always what we returned from findMainSelection.
         */
        if (mainSelection instanceof IFile) {

            IFile mainSelFile = (IFile) mainSelection;

            if (mainSelFile.getFileExtension().equalsIgnoreCase("gsd")) { //$NON-NLS-1$

                /*
                 * See whether the selected object is a child of the GSD file
                 * for activated editor.
                 */

                if (selObject instanceof EObject) {

                    IFile fileContainingSelObject =
                            WorkingCopyUtil.getFile((EObject) selObject);

                    if (fileContainingSelObject != null
                            && mainSelFile.equals(fileContainingSelObject)) {

                        return true;
                    }

                } else if (selObject instanceof INavigatorGroup) {

                    INavigatorGroup navigatorGroup =
                            (INavigatorGroup) selObject;

                    if (navigatorGroup.getParent() instanceof EObject) {

                        EObject parent = (EObject) (navigatorGroup.getParent());

                        IFile fileContainingSelObject =
                                WorkingCopyUtil.getFile(parent);

                        if (fileContainingSelObject != null
                                && mainSelFile.equals(fileContainingSelObject)) {

                            return true;
                        }
                    }
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
    @Override
    public void activateEditor(IWorkbenchPage aPage,
            IStructuredSelection aSelection) {

        /*
         * Check if the first item selected is an EObject
         */
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

                /*
                 * Find the GSD file for selected object.
                 */
                while (selObject != null) {

                    if (selObject instanceof GlobalSignalDefinitions) {

                        break;
                    }

                    selObject = selObject.eContainer();
                }

                if (selObject instanceof GlobalSignalDefinitions) {

                    /*
                     * Get selected element as GSD file.
                     */
                    GlobalSignalDefinitions selGSD =
                            (GlobalSignalDefinitions) selObject;

                    WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(selGSD);

                    if (wc != null) {

                        IFile file = WorkingCopyUtil.getFile(selGSD);

                        GSDEditorInputFactory fact =
                                new GSDEditorInputFactory();

                        if (fact != null) {

                            IEditorInput gsdEditorInput =
                                    fact.getEditorInputFor(file);

                            if (gsdEditorInput != null) {

                                IEditorPart editor =
                                        aPage.findEditor(gsdEditorInput);

                                /*
                                 * If the editor is open then bring to top
                                 */
                                if (editor != null) {
                                    aPage.bringToTop(editor);
                                }
                            }
                        }
                    }
                }
            }
        }
        return;
    }

}