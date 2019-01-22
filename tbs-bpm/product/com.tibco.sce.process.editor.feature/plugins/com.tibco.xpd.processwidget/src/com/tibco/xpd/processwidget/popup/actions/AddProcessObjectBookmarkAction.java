/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.popup.actions;

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

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.BaseConnectionAdapter;
import com.tibco.xpd.processwidget.adapters.BaseGraphicalNodeAdapter;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.NamedElementAdapter;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;
import com.tibco.xpd.processwidget.parts.ProcessEditPart;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.actions.AddEObjectBookmarkActionDelegate;

/**
 * Action To Bookmark a selected process related Object element (or one that is
 * IAdaptable to EObject) to the eclipse standard Bookmarks view.
 * 
 * @author aallway
 * @since 3.2
 */
public class AddProcessObjectBookmarkAction extends AddEObjectBookmarkActionDelegate {

    private String parentProcessName = null;

    @Override
    protected EObject getEObject(IStructuredSelection ssel) {
        // Save parent process name for bookmark label later.
        parentProcessName = ""; //$NON-NLS-1$

        EObject eo = super.getEObject(ssel);
        if (eo != null) {
            Object o = ssel.getFirstElement();

            if (o instanceof ModelAdapterEditPart
                    && !(o instanceof ProcessEditPart)) {
                BaseProcessAdapter adapter =
                        ((ModelAdapterEditPart) o).getModelAdapter();

                if (adapter != null) {
                    Object p = adapter.getProcess();
                    if (p != null) {
                        ProcessDiagramAdapter pda =
                                (ProcessDiagramAdapter) adapter
                                        .getAdapterFactory()
                                        .adapt(p,
                                                ProcessWidgetConstants.ADAPTER_TYPE);
                        if (pda != null) {
                            parentProcessName = pda.getName();
                        }
                    }

                    if (adapter instanceof NamedElementAdapter) {
                        String name = ((NamedElementAdapter) adapter).getName();
                        if (name != null && name.length() > 0) {
                            parentProcessName = parentProcessName + ":" + name; //$NON-NLS-1$
                        }
                    }
                }

            }
        }
        return eo;
    }

    @Override
    protected String getBookmarkLabel(EObject eo) {
        // Add process name into it (bit difficult for user to see otherwise).
        if (parentProcessName != null && parentProcessName.length() > 0) {
            return parentProcessName;
        }

        return super.getBookmarkLabel(eo);
    }

}
