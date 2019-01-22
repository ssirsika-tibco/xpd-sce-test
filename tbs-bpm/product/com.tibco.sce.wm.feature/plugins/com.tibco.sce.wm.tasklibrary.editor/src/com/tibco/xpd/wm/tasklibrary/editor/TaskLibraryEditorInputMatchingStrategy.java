/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.wm.tasklibrary.editor.resources.TaskLibraryFactory;
import com.tibco.xpd.xpdl2.Process;

/**
 * Class that allows the given TaskLibrary editor is the current editor for the
 * given editor input.
 * <p>
 * This is necessary because the TaskLibraryEditor is actually a
 * ProcessDIagramEditor EXCEPT that as far as eclipse is concerned the whole
 * .tasks file is the input. Therefore TaskLibraryEditor adapts its
 * FileEditorInput to a ProcessEditorInput for the first process in the file.
 * <p>
 * So when eclipse tries to find the current editor when user double clicks on
 * .tasks we have to adapt that to a process editor input in the same way.
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryEditorInputMatchingStrategy implements
        IEditorMatchingStrategy {

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.ui.IEditorMatchingStrategy#matches(org.eclipse.ui.
     * IEditorReference, org.eclipse.ui.IEditorInput)
     */
    public boolean matches(IEditorReference editorRef, IEditorInput input) {
        Process inputTaskLibrary = null;

        if (input instanceof FileEditorInput) {
            if (TaskLibraryEditorPlugin.TASKLIBRARY_FILE_EXTENSION
                    .equals(((FileEditorInput) input).getFile()
                            .getFileExtension())) {
                inputTaskLibrary =
                        TaskLibraryFactory.INSTANCE
                                .getTaskLibrary(((FileEditorInput) input)
                                        .getFile());
            }

        } else if (input instanceof ProcessEditorInput) {
            inputTaskLibrary = ((ProcessEditorInput) input).getProcess();
        }

        if (inputTaskLibrary != null) {
            try {
                if (editorRef.getEditorInput() instanceof ProcessEditorInput) {
                    if (inputTaskLibrary == ((ProcessEditorInput) editorRef
                            .getEditorInput()).getProcess()) {
                        return true;
                    }
                } else {
                    throw new IllegalStateException(
                            "Unexpected existing editor input for TaskLibraryEditor; expected a ProcessEditorInput, got: " //$NON-NLS-1$
                                    + editorRef.getEditorInput());
                }
            } catch (PartInitException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

}
