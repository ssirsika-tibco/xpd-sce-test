/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.xpd.resources.ui.editorHandler.IDisplayEObject;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaUiPlugin;

/**
 * Utility class to assist with openeing REST Schema Editors.
 * 
 * @author nwilson
 * @since 12 Feb 2015
 */
public class JsonSchemaEditorOpener {
    public static final String JSON_SCHEMA_EDITOR_ID =
            "com.tibco.xpd.rest.schema.ui.json.editor"; //$NON-NLS-1$

    /**
     * Opens a JSON schema editor for a given JSD file.
     * 
     * @param file
     *            The JSD file to open.
     */
    public IEditorPart openEditor(IFile file) {
        IEditorInput editorInput;
        editorInput = new FileEditorInput(file);

        IWorkbenchPage page =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage();
        try {
            return page.openEditor(editorInput, JSON_SCHEMA_EDITOR_ID);
        } catch (PartInitException e) {
            RestSchemaUiPlugin.logError(e);
        }

        return null;

    }

    /**
     * Opens RSD editor for a given EObject.
     * 
     * @param eo
     *            EObject - must be contained in the resource.
     * @param select
     *            if object should be selected in the newly opened editor.
     * @return opened editor part or <code>null</code> in case of failure.
     */
    public IEditorPart openEditor(EObject eo, boolean select) {
        IWorkbenchWindow window =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        IFile file = WorkingCopyUtil.getFile(eo);
        if (window != null && file != null && file.isAccessible()) {
            IWorkbenchPage page = window.getActivePage();
            if (page != null) {
                try {
                    IEditorPart part =
                            IDE.openEditor(page,
                                    file,
                                    JSON_SCHEMA_EDITOR_ID,
                                    true);
                    if (select && part instanceof IDisplayEObject) {
                        ((IDisplayEObject) part).gotoEObject(true, eo);
                    }
                    return part;
                } catch (PartInitException e) {
                    RestSchemaUiPlugin.logError(e);
                }
            }
        }
        return null;
    }
}
