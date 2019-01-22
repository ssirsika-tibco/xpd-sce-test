/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.editor;

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
import com.tibco.xpd.rsd.ui.RsdUIPlugin;

/**
 * Utility class to assist with opening REST Service Descriptor Editors.
 * 
 * @author nwilson
 * @since 12 Feb 2015
 */
public class RsdEditorOpener {

    private static final RsdEditorOpener INSTANCE = new RsdEditorOpener();

    /**
     * @return Returns default instance of this class.
     */
    public static RsdEditorOpener getDefault() {
        return INSTANCE;
    }

    /**
     * Opens an RSD editor for a given file.
     * 
     * @param file
     *            The RSD file to open.
     * @return opened editor part or <code>null</code> in case of failure.
     */
    public IEditorPart openEditor(IFile file) {
        if (file != null && file.isAccessible()) {
            IEditorInput editorInput = new FileEditorInput(file);

            IWorkbenchWindow window =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow();
            if (window != null) {
                IWorkbenchPage page = window.getActivePage();
                if (page != null) {
                    try {
                        return page.openEditor(editorInput,
                                RsdEditorPart.EDITOR_ID);
                    } catch (PartInitException e) {
                        RsdUIPlugin.getLogger().error(e);
                    }
                }
            }
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
                                    RsdEditorPart.EDITOR_ID,
                                    true);
                    if (select && part instanceof IDisplayEObject) {
                        ((IDisplayEObject) part).gotoEObject(true, eo);
                    }
                    return part;
                } catch (PartInitException e) {
                    RsdUIPlugin.getLogger().error(e);
                }
            }
        }
        return null;
    }
}
