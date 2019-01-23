/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.ui.actions;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.ide.undo.CreateMarkersOperation;

import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Action Delegate To Bookmark a selected EObject element (or one that is
 * IAdaptable to EObject) to the eclipse standard Bookmarks view.
 * 
 * @author aallway
 * @since 3.2
 */
public class AddEObjectBookmarkActionDelegate implements IActionDelegate {

    private static final String ORG_ECLIPSE_CORE_RESOURCES_BOOKMARK = "org.eclipse.core.resources.bookmark"; //$NON-NLS-1$

    private CreateMarkersOperation createMarkersOperation = null;

    private String actionText = ""; //$NON-NLS-1$

    /**
     * 
     */
    public AddEObjectBookmarkActionDelegate() {
    }

    public void run(IAction action) {
        if (createMarkersOperation != null) {
            try {
                createMarkersOperation.execute(new NullProgressMonitor(), null);
            } catch (ExecutionException e) {
            }
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
        createMarkersOperation = null;
        actionText = ""; //$NON-NLS-1$
        boolean enabled = false;

        if (selection instanceof IStructuredSelection && actionText != null) {
            IStructuredSelection ssel = (IStructuredSelection) selection;

            actionText = action.getText();

            EObject eo = getEObject(ssel);

            if (eo != null) {
                createMarkersOperation = getCreateMarkersOperation(eo);
                if (createMarkersOperation != null
                        && createMarkersOperation.canExecute()) {
                    enabled = true;
                }
            }

            action.setEnabled(enabled);
        }
    }

    /**
     * @param eo
     * @return The CreateMarkersOperation for the given eObject
     */
    protected CreateMarkersOperation getCreateMarkersOperation(EObject eo) {
        String label = getBookmarkLabel(eo);
        String objectLocation = getEObjectLocation(eo);
        IFile file = getEObjectFile(eo);

        if (label != null && objectLocation != null && file != null) {
            Map<String, String> attrs = new HashMap<String, String>();
            attrs.put("message", label); //$NON-NLS-1$
            attrs.put("location", objectLocation); //$NON-NLS-1$

            return new CreateMarkersOperation(
                    ORG_ECLIPSE_CORE_RESOURCES_BOOKMARK, attrs, file,
                    actionText == null ? "" : actionText); //$NON-NLS-1$

        }
        return null;
    }

    /**
     * @param ssel
     * @return The EObject from teh selection - nominally this is possible if
     *         the selection contains a single EObject or is adaptable to
     *         EObject.
     */
    protected EObject getEObject(IStructuredSelection ssel) {
        EObject eo = null;

        if (ssel.size() == 1) {
            Object o = ssel.getFirstElement();

            if (o instanceof EObject) {
                eo = (EObject) o;
            } else if (o instanceof IAdaptable) {
                IAdaptable adp = (IAdaptable) o;

                eo = (EObject) adp.getAdapter(EObject.class);
            }
        }
        return eo;
    }

    /**
     * @param eo
     * @return The URI location (for marker view goto) of the eobject.
     */
    protected String getEObjectLocation(EObject eo) {
        URI uri = EcoreUtil.getURI(eo);
        if (uri != null) {
            return uri.fragment();
        }
        return null;
    }

    /**
     * @param eo
     * @return The label for the bookmark to be created for the eObject.
     */
    protected String getBookmarkLabel(EObject eo) {
        return WorkingCopyUtil.getText(eo);
    }

    /**
     * @param eo
     * @return The file for the eObject
     */
    protected IFile getEObjectFile(EObject eo) {
        return WorkingCopyUtil.getFile(eo);
    }

}
