/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.ui.projectexplorer;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.ILinkHelper;
import org.eclipse.ui.navigator.resources.ProjectExplorer;

import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Helper for the <-- --> link with editor button on the project explorer for
 * linking an Editor with selected content in the project explorer and visa
 * versa.
 * <p>
 * There is a flaw in the underlying framework in that it is possible to have
 * the editor activated for a number of different content items in the project
 * explorer (i.e. a file and all it's child content tree) <b>BUT</b> having
 * activated the editor, the framework then allows a 'bounce back' and does the
 * reverse. So a call to activateEditor() is followed by a call to
 * findSelection().
 * <p>
 * This means that if you <i>nominally</i> always link the editor with a file in
 * project explorer then the user will select some child element under the file,
 * then the editor is activated and then the <b>file</b> is selected - this is
 * not what the user just clicked on!
 * <p>
 * This class helps with this problem by checking (on findSelection() whether
 * the current project explorer selection is a child of the nominal object that
 * is linked with the editor. If so, then findSelection() an empty selection and
 * therefore the activateEditor will not get called.
 * <p>
 * <b>NOTE:</b> Of course, you must also configure the
 * org.eclipse.ui.navigator.linkHelper so that it does NOT filter out all of the
 * other sub-tree items you wish to link with the editor!
 * </p>
 * 
 * @author aallway
 * @since 3.2
 */
public abstract class AbstractProjectExplorerLinkHelper implements ILinkHelper {

    /**
     * Given an editor input, return the object in the project explorer tree
     * that should be linked with it.
     * <p>
     * The subclass should implement this <b>rather than
     * {@link ILinkHelper#findSelection(IEditorInput)}</b>
     * 
     * @param editorInput
     * 
     * @return The object in the project explorer content tree that is linked
     *         with the given editor input.
     */
    protected abstract Object findMainSelection(IEditorInput editorInput);

    /**
     * Check whether the given child object (a current selection in the project
     * explorer) is a child of the given mainSelection object (as returned by
     * findMainSelection()).
     * 
     * @param selObject
     * @param mainSelection
     * 
     * @return true if selObject is a child of the given mainSelection object.
     */
    protected abstract boolean isChild(Object selObject, Object mainSelection);

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.navigator.ILinkHelper#findSelection(org.eclipse.ui.
     * IEditorInput)
     */
    public final IStructuredSelection findSelection(IEditorInput editorInput) {
        //
        // Get the nominal single object that is linked with the editor.
        Object mainSelection = findMainSelection(editorInput);

        if (mainSelection != null) {
            //
            // Get current selection in project explorer view.
            IStructuredSelection projExpSel = getProjectExplorerSelection();

            if (projExpSel != null && !projExpSel.isEmpty()) {
                //
                // Check if all the selected objects are children of the
                // nominally object linked with editor.
                boolean allSelAreChildren = true;

                for (Iterator iterator = projExpSel.iterator(); iterator
                        .hasNext();) {
                    Object selObject = (Object) iterator.next();

                    if (!isChild(selObject, mainSelection)) {
                        allSelAreChildren = false;
                        break;
                    }
                }

                //
                // If all selected objects are children of nominal object that
                // is linked with the editor then the current selection is ok
                // for the editor so don't change it.
                if (allSelAreChildren) {
                    return StructuredSelection.EMPTY;
                }
            }

            //
            // If all else fails, select the one object that is nominally linked
            // with the editor.
            return new StructuredSelection(mainSelection);
        }
        return StructuredSelection.EMPTY;
    }

    /**
     * @return Get the current selected items in the project explorer.
     */
    private IStructuredSelection getProjectExplorerSelection() {
        if (PlatformUI.isWorkbenchRunning()) {
            IWorkbenchWindow window =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow();
            if (window != null) {
                IWorkbenchPage page = window.getActivePage();
                if (page != null) {
                    IViewReference viewRef =
                            page.findViewReference(ProjectExplorer.VIEW_ID);
                    if (viewRef != null) {
                        IViewPart view = viewRef.getView(false);
                        if (view instanceof CommonNavigator) {
                            CommonNavigator navigator = (CommonNavigator) view;

                            CommonViewer viewer = navigator.getCommonViewer();
                            if (viewer != null) {
                                ISelection selection = viewer.getSelection();
                                if (selection instanceof StructuredSelection) {
                                    return (StructuredSelection) selection;
                                }
                            }
                        }
                    }
                }
            }
        }

        return StructuredSelection.EMPTY;
    }
}
