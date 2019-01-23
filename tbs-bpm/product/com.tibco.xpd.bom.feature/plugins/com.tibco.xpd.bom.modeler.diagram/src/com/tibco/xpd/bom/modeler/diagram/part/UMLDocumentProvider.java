/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.part;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IStorageEditorInput;

import com.tibco.xpd.bom.resources.wc.InputStreamBOMWorkingCopy;
import com.tibco.xpd.resources.wc.gmf.AbstractGMFWorkingCopy;
import com.tibco.xpd.resources.wc.gmf.WorkingCopyDocumentProvider;

/**
 * @generated
 */
public class UMLDocumentProvider extends WorkingCopyDocumentProvider {

    private final Map<IStorageEditorInput, AbstractGMFWorkingCopy> workingCopyMap =
            new HashMap<IStorageEditorInput, AbstractGMFWorkingCopy>();

    @Override
    protected boolean isInputValid(Object editorInput) {
        boolean isValid = super.isInputValid(editorInput);
        // Allow files opened from revision history
        if (!isValid && editorInput instanceof IStorageEditorInput) {
            isValid = true;
        }
        return isValid;
    }

    @Override
    protected void disposeElementInfo(Object element, ElementInfo info) {
        super.disposeElementInfo(element, info);
        if (element instanceof IStorageEditorInput) {
            AbstractGMFWorkingCopy wc = workingCopyMap.get(element);
            if (wc != null) {
                wc.dispose();
                workingCopyMap.remove(element);
            }
        }
    }

    @Override
    protected AbstractGMFWorkingCopy getWorkingCopy(IEditorInput editorInput)
            throws CoreException {
        /*
         * Add support for resources opened from revision history
         */
        AbstractGMFWorkingCopy wc = super.getWorkingCopy(editorInput);
        if (wc == null && editorInput instanceof IStorageEditorInput) {
            wc = workingCopyMap.get(editorInput);
            if (wc == null) {
                IStorage storage =
                        ((IStorageEditorInput) editorInput).getStorage();
                IFile file =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getFile(storage.getFullPath());

                wc = new InputStreamBOMWorkingCopy(file, storage);
                workingCopyMap.put((IStorageEditorInput) editorInput, wc);
            }
        }
        return wc;
    }

}
