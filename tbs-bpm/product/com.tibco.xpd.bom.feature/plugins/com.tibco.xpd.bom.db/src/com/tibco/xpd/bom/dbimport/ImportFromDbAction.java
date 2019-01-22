/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.dbimport;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.bom.dbimport.internal.Messages;

/**
 * Import tables from database and represent them as Business Object Models
 * 
 * 
 * @author rsomayaj
 */
public class ImportFromDbAction extends BaseSelectionListenerAction {

    public static final String OUTPUT_FILE_EXTENSION = "bom"; //$NON-NLS-1$

    private String outputPath;

    /**
     * The constructor
     */
    public ImportFromDbAction() {
        super(Messages.ImportFromDbAction_ActionLabel_label);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        IStructuredSelection selection = getStructuredSelection();
        if (selection.isEmpty()
                || !(selection.getFirstElement() instanceof File)) {
            return;
        }

    }

    /**
     * Gets the output path. If the output path is not set it will return
     * default path which is the same as the source file but with and 'bom'
     * extension.
     * 
     * @return The output path. It will be the workspace root relative path.
     */
    public String getOutputPath() {
        if (outputPath == null) {
            return getDefaultOutputPath();
        }
        return outputPath;
    }

    /**
     * Sets the output path.
     * 
     * @param outputPath
     *            the outputPath to set. This must be workspace root relative
     *            path.
     */
    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    /**
     * @return
     */
    private String getDefaultOutputPath() {
        IStructuredSelection selection = getStructuredSelection();
        if (selection.isEmpty()
                || !(selection.getFirstElement() instanceof IFile)) {
            return null;
        }
        final IFile modelFile = (IFile) selection.getFirstElement();
        return modelFile.getFullPath().removeFileExtension()
                .addFileExtension(OUTPUT_FILE_EXTENSION).toPortableString();
    }
}
