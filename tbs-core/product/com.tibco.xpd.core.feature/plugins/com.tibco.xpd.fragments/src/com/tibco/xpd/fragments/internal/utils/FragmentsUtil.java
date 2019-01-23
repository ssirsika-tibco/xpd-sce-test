package com.tibco.xpd.fragments.internal.utils;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.fragments.IContainedFragmentElement;
import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.fragments.internal.FragmentsManager;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.fragments.internal.impl.FragmentCategoryImpl;
import com.tibco.xpd.fragments.internal.operations.FragmentContext;
import com.tibco.xpd.resources.util.XpdEcoreUtil;

public final class FragmentsUtil {

    /**
     * Generate a unique ID.
     * 
     * @return UUID.
     */
    public static final String createUniqueID() {
        return XpdEcoreUtil.generateUUID();
    }

    /**
     * Get the active editor id.
     * 
     * @return active editor id, or <code>null</code> if no editor found.
     */
    public static String getActiveEditorID() {
        IEditorPart activeEditor = getActiveEditor();
        return activeEditor != null ? activeEditor.getEditorSite().getId()
                : null;
    }

    /**
     * Get the active editor.
     * 
     * @return {@link IEditorPart} of the active editor or <code>null</code> if
     *         no active editor is found.
     */
    public static IEditorPart getActiveEditor() {
        IEditorPart editorPart = null;

        if (PlatformUI.getWorkbench() != null
                && PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null
                && PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage() != null) {
            editorPart =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage().getActiveEditor();
        }

        return editorPart;
    }

    /**
     * Execute the undoable operation in the workspace operation history.
     * 
     * @param operation
     *            operation to execute
     * @param monitor
     *            progress monitor, or <code>null</code> if no progress required
     * @return the <code>IStatus</code> indicating whether the execution
     *         succeeded.
     * @throws ExecutionException
     * 
     * @see {@link IOperationHistory#execute(IUndoableOperation, IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
     *      IOperationHistory.execute}
     */
    public static IStatus execute(IUndoableOperation operation,
            IProgressMonitor monitor) throws ExecutionException {
        IStatus status = null;
        if (operation != null) {
            try {
                status =
                        PlatformUI.getWorkbench().getOperationSupport()
                                .getOperationHistory().execute(operation,
                                        monitor,
                                        null);
            } catch (ExecutionException e) {
                FragmentsManager.getInstance().refreshFragmentsView(null);
                throw e;
            }
        }
        return status;
    }

    /**
     * Flush the undo/redo operational history for the fragments context.
     */
    public static void flushUndoRedoHistory() {
        PlatformUI.getWorkbench().getOperationSupport().getOperationHistory()
                .dispose(FragmentContext.ALL, true, true, true);
    }

    /**
     * Create a name for the copied element. If the copy is made in the same
     * target as the original then the name will be prefixed with "Copy of".
     * 
     * @param target
     *            copy target
     * @param element
     *            element being copied
     * @return name
     */
    public static String getCopyOfName(FragmentCategoryImpl target,
            IFragmentElement element) {
        String name = null;

        if (target != null && element instanceof IContainedFragmentElement) {
            Set<String> existingNames = new HashSet<String>();
            // Get all existing names of the target's children
            for (IFragmentElement child : target.getChildren()) {
                if (child.getNameLabel() != null) {
                    existingNames.add(child.getName());
                }
            }

            String pattern;

            // If target is same as element then add "Copy of..." prefix to the
            // name
            if (target
                    .equals(((IContainedFragmentElement) element).getParent())) {

                name =
                        String
                                .format(Messages.FragmentsUtil_copyOfFragment_label,
                                        element.getName());
                pattern = Messages.FragmentsUtil_moreCopyOfFragment_label;
            } else {
                name = element.getName();
                pattern = "%2$s %1$d"; //$NON-NLS-1$
            }

            int idx = 1;
            boolean found = false;

            do {
                if (found = existingNames.contains(name)) {
                    name = String.format(pattern, idx++, element.getName());
                }

            } while (found);
        }

        return name;
    }
}
